package com.ablec.myarchitecture.logic.flow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        const val TAG = "FlowViewModel"

        //帧间隔
        const val FRAME_INTERVAL = 1000L

        //超速速度
        const val SPEED = 30
    }

    // MutableStateFlow默认具备 liveData的distinctUntilChange特征，
    // value 变化时候才发到下游
    private val _speedFlow = MutableStateFlow<Int>(0);

    private val _vehicleState = MutableStateFlow<VehicleState>(VehicleState.PILOT);

    fun getSpeed(): Flow<Int> {
        return _speedFlow
    }

    fun getIfSpeedX(): Flow<Boolean> {
        return _speedFlow.map {
            it > SPEED
        }
    }

    fun test() {
        //模拟上游发送数据
        viewModelScope.launch {
            flowOf(20, 20, 32, 33, 33, 33, 25, 26)
                .onEach {
                    delay(1000)
                }.collect {
                    _speedFlow.emit(it)
                }
        }


    }

    enum class VehicleState {
        PARK, PILOT
    }

}
