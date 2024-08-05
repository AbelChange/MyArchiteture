package com.ablec.myarchitecture.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class CalculatorService : Service() {

    private val TAG = "CalculatorService"

    override fun onBind(intent: Intent?): IBinder {
        return object : ICalculator.Stub() {
            override fun add(a: Int, b: Int): Int {
                Log.d(TAG, "自定义binder执行a+b")
                return a + b;
            }

            override fun minus(a: Int, b: Int): Int {
                Log.d(TAG, "自定义binder执行a-b")
                return a - b;
            }
        }
    }


}