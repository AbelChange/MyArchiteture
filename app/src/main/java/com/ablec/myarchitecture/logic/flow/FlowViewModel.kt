package com.ablec.myarchitecture.logic.flow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
        const val SPEED = 30
    }

    //sharedFlow 可以有多个下游
    private val _speedFlow = MutableSharedFlow<Int>(0)

    // StateFlow value 变化时候才发到下游
    private val _vehicleState = MutableStateFlow<VehicleState>(VehicleState.PILOT);

    //sharedFlow + distinctUntilChanged只有变化的值才会发到ui
    fun getSpeed(): Flow<Int> {
        return _speedFlow.distinctUntilChanged()
    }

    //超速提醒(连续 n 帧 数据一致才认为 超速/未超速)
    fun getIfSpeedX(): Flow<Boolean> {
        return _speedFlow
            .onEach {
                Log.d(TAG, "rawValue:$it")
            }
            .map {
                it > SPEED
            }.onEach {
                Log.d(TAG, "是否超速:$it")
            }
    }

    fun test() {
        //模拟上游发送数据
        viewModelScope.launch {
            flowOf(20, 20, 32, 33, 33, 25, 26, 31, 32)
                .onEach {
                    delay(FRAME_INTERVAL)
                }.collect {
                    _speedFlow.emit(it)
                }
        }
    }

    enum class VehicleState {
        PARK, PILOT
    }

}
