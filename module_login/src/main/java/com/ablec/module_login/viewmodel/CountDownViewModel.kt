package com.ablec.module_login.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/18 16:45
 */
@HiltViewModel
class CountDownViewModel @Inject constructor(
    val app: Application
) : ViewModel() {

    private var timeCountJob: Job? = null

    private val _countDownSecond = MutableLiveData<Int>()

    val countDownSecondExpose: LiveData<Int> get() = _countDownSecond

    private val _isCountDown = MutableLiveData<Boolean>(false)

    val isCountDownExpose: LiveData<Boolean> get() = _isCountDown

    fun startTimeCountDown(total: Int) {
        if (total <= 0) {
            return
        }
        _isCountDown.value = true
        _countDownSecond.value = total
        timeCountJob?.cancel()
        var countDownTime = total
        timeCountJob = viewModelScope.launch {
            repeat(_countDownSecond.value!!) {
                delay(100)
                countDownTime = countDownTime.dec()
                _countDownSecond.value = countDownTime
                if (countDownTime == 0) {
                    timeCountJob?.cancel()
                    _isCountDown.value = false
                }
            }
        }
    }
}