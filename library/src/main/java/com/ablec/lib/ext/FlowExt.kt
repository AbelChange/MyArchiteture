package com.ablec.lib.ext

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


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


//连续n帧 数据一致 才认为有效 合并成一帧
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


inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}
