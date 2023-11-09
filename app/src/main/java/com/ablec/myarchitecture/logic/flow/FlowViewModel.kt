package com.ablec.myarchitecture.logic.flow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FlowViewModel(app: Application) : AndroidViewModel(app) {

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
    private val _speedFlow = MutableSharedFlow<Int>(0)

    //sharedFlow + distinctUntilChanged只有变化的值才会发到ui
    fun getSpeed(): Flow<Int> {
        return _speedFlow.distinctUntilChanged()
    }

    // StateFlow 天然具备distinctUntilChanged()
    private val _vehicleState = MutableStateFlow(VehicleState.PILOT);

    //超速提醒
    //map操作符：int流转化成boolean流
    fun getIfSpeedX(): Flow<Boolean> {
        return _speedFlow
            .onEach {
                Log.d(TAG, "rawValue:$it")
            }
            .map {
                it > MAX_SPEED
            }.onEach {
                Log.d(TAG, "是否超速:$it")
            }
    }

    //合并操作符 两条流合并成一条新流
    fun getIfSpeedX2(): Flow<Boolean> {
        return _speedFlow
            .combine(_vehicleState) { speed, state ->
                return@combine speed > MAX_SPEED && state == VehicleState.PARK
            }.onEach {
                Log.d(TAG, "是否超速:$it")
            }
    }

    fun test() {
        //模拟上游发送数据，实际来自sdk的子线程
        viewModelScope.launch {
            flowOf(20, 20, 32, 33, 33, 25, 26, 31, 32)
                .onEach {
                    delay(FRAME_INTERVAL)
                }.collect {
                    _speedFlow.emit(it)
                    //模拟 “31” 帧 超速了
                    if (it == 31){
                        _vehicleState.emit(VehicleState.PARK)
                    }else{
                        _vehicleState.emit(VehicleState.PILOT)
                    }
                }
        }
    }

    enum class VehicleState {
        PARK, PILOT
    }

}
