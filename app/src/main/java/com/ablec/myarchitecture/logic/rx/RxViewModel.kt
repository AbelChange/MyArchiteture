package com.ablec.myarchitecture.logic.rx

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
open class RxViewModel @Inject constructor(app: Application) : ViewModel() {

    companion object {
        const val TAG = "RxViewModel"

        //帧间隔
        const val FRAME_INTERVAL = 1000L

        //超速速度
        const val MAX_SPEED = 30
    }


    @SuppressLint("BinaryOperationInTimber")
    fun flatMapVSConcatMap() {
        val subscribe = Observable.fromArray(-1, 2, -3, 4, 5)
            //1对1转换
            .map { abs(it) }
            //1对n转换，更加灵活
            .flatMap { i ->
                Observable
                    .fromArray(i, -i)
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        Log.d(TAG, "flatMap$it subscribeOn${Thread.currentThread().name}")
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(
                    TAG,
                    "Observe item " + it
                )

            } // Output may be out of order
    }




}