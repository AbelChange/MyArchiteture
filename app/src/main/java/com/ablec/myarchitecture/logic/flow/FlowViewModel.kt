package com.ablec.myarchitecture.logic.flow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class FlowViewModel @Inject constructor(app: Application) : ViewModel() {

    companion object {
        const val TAG = "FlowLearn"

        //帧间隔
        const val FRAME_INTERVAL = 1000L

        //超速速度
        const val MAX_SPEED = 30
    }

    //sharedFlow
    // 1.可以有多个下游(订阅者)
    // 2.replay- 可以缓存一些数据序列，在订阅时候重新发送
    // 3.BufferOverflow - 被压策略 - 1.挂起 2.丢弃新3.丢弃老  比如：pop消息
    private val _speedFlow = MutableStateFlow<Int>(0)
    private val _speedFlowShare = MutableSharedFlow<Int>(0)
    val speed: StateFlow<Int> = _speedFlow

    //sharedFlow + distinctUntilChanged只有变化的值才会发到ui
    fun getSpeed(): Flow<Int> {
        return _speedFlow
    }

    // StateFlow 天然具备distinctUntilChanged()
    private val _vehicleState = MutableStateFlow(VehicleState.PILOT);

    //超速提醒
    //map操作符：int流转化成boolean流
    fun getIfSpeedX(): Flow<Boolean> {
        return _speedFlow.map {
            it > MAX_SPEED
        }
    }

    //合并操作符 两条流合并成一条新流
    fun getIfSpeedX2(): Flow<Boolean> {
        return _speedFlow
            .combine(_vehicleState) { speed, state ->
                return@combine speed > MAX_SPEED && state == VehicleState.PARK
            }
//            .onEach {
//                Log.d(TAG, "是否超速:$it")
//            }
    }

    fun test() {
        //模拟上游发送数据，实际来自sdk的子线程
        viewModelScope.launch {
            flowOf(20, 20, 32, 33, 33, 25, 26, 31, 32)
                .onEach {
                    delay(FRAME_INTERVAL)
                    //节流 时间段内丢弃多余的   最新的值一定发射
                }.debounce{
                    if (it == 0 || it == 1) {
                        200.milliseconds
                    } else {
                        0.milliseconds
                    }
                }
                .distinctUntilChanged { old, new ->
                    new - old > 2
                }
                .collect {
                    _speedFlow.emit(it)
                    //模拟 “31” 帧 超速了
                    if (it == 31) {
                        _vehicleState.emit(VehicleState.PARK)
                    } else {
                        _vehicleState.emit(VehicleState.PILOT)
                    }
                }
        }
    }

    enum class VehicleState {
        PARK, PILOT
    }


    fun testCancelJob(){
        viewModelScope.launch {
            while (true){
                //内部监测取消的两种方式
                //1.需要响应取消的
                if (!isActive){
                    //收尾工作clearJob()
                    //抛异常来中断，这里与线程直接return有区别 谨记！！！
                    throw CancellationException()
                }
                //2.不需要响应取消
                ensureActive()
                //doJob()
                //3. 会自取消，切记不要捕捉异常
                try {
                    delay(500)
                    //doJob()
                } finally {
                    //收尾工作clearJob()
                }
            }
        }
    }


}
