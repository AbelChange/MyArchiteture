package com.ablec.lib.ext

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ablec.library.BuildConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.Duration

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/3/7 9:40
 */

/**
 * editText.textChangeFlow() // 构建输入框文字变化流
 * .filter { it.isNotEmpty() } // 过滤空内容，避免无效网络请求
 * .debounce(300) // 300ms防抖
 * .flatMapLatest { searchFlow(it.toString()) } // 新搜索覆盖旧搜索
 * .flowOn(Dispatchers.IO) // 让搜索在异步线程中执行
 * .onEach { updateUi(it) } // 获取搜索结果并更新界面
 * .launchIn(mainScope) // 在主线程收集搜索结果
 */
fun EditText.textChangeFlow(): Flow<CharSequence> = callbackFlow {
    // 构建输入框监听器
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        // 在文本变化后向流发射数据
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { trySend(it) }
        }
    }
    addTextChangedListener(watcher) // 设置输入框监听器
    awaitClose { removeTextChangedListener(watcher) } // 阻塞以保证流一直运行
}

fun View.clickFlow() = callbackFlow {
    setOnClickListener { trySend(Unit) }
    awaitClose { setOnClickListener(null) }
}

/**
 * 按钮点击防抖
 * debounce 会有延迟，适用于editText
 */
fun <T> Flow<T>.throttle(timeWindow: Duration): Flow<T> = flow {
    var lastTime = 0L
    collect {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > timeWindow.inWholeMilliseconds) {
            emit(it)
            lastTime = currentTime
        }
    }
}

/**
 * 指数退避 + 抖动（随机化延迟）
 * 可减少请求模式的可预测性，避免所有客户端同时重试导致的 “雪崩效应”
 */
fun <T> Flow<T>.retryOnNetworkError(
    maxRetries: Int = 3,
    baseDelay: Long = 1000L, // 初始延迟 1000ms
    jitterFactor: Double = 0.2 // 20% 抖动
): Flow<T> = retryWhen { cause, attempt ->
    buffer(0)
    collectLatest {  }
    if (cause is IOException && attempt < maxRetries) { // ✅ 仅在网络异常时重试
        val exponentialDelay = (2.0.pow(attempt.toDouble()) * baseDelay).toLong()
        val jitter = (exponentialDelay * jitterFactor * Random.nextDouble(-1.0, 1.0)).toLong()
        val finalDelay = (exponentialDelay + jitter).coerceAtLeast(0L) // 确保 delay 不为负数
        delay(finalDelay)
        true // 继续重试
    } else {
        false // 不是网络异常，或达到最大重试次数，停止
    }
}




//flow自定义操作符，连续n帧 数据一致 才认为有效 合并成一帧
// 场景：010101连续抖动处理
// debounce操作符也可处理
fun <T> Flow<T>.countUntil(n: Int): Flow<T> = flow {
    var count = 0
    var lastValue: T? = null
    collect {
        if (lastValue == it) {
            ++count
        } else {
            lastValue = it
            count = 1
        }
        if (count >= n) {
            emit(it)
            count = 0
            lastValue = null
        }

    }
}


 fun <T> Flow<T>.debugLog(subject: String): Flow<T> =
    if (BuildConfig.DEBUG) {
        ThreadLocal<String>().asContextElement()
        onEach { Timber.d(">>> $subject: $it") }
    } else {
        this
    }

/**
 * 管理多事件超时
 * 已经存在的事件，再次收到只重新计时
 * 如果新事件不存在，才通知
 */
fun <T> Flow<T>.withEventTimeout(timeoutMillis: Long): Flow<List<T>> = channelFlow {
    val lastEvents = mutableMapOf<T, Job>() // 用来存储计时任务
    collect { event ->
        lastEvents[event]?.cancel() // 取消之前的计时任务
        lastEvents[event] = launch {//并行开启倒计时
            try {
                delay(timeoutMillis)
            } finally {
                lastEvents.remove(event) // 清理任务
                send(lastEvents.keys.toList())
            }
        }
        send(lastEvents.keys.toList())
    }
}

inline fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit,
): Job =
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(state = minActiveState) {
            collect { action(it) }
        }
    }

@Suppress("unused")
inline fun <T> Flow<T>.collectInViewLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit,
): Job =
    collectIn(
        owner = fragment.viewLifecycleOwner,
        minActiveState = minActiveState,
        action = action,
    )
