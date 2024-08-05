package com.ablec.myarchitecture.service

import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException

interface ICalculator {
    abstract class Stub : Binder(), ICalculator {
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if (code == 1) {
                val a = data.readInt()
                val b = data.readInt()
                reply?.writeInt(add(a, b))
                return true
            }

            if (code == 2) {
                val a = data.readInt()
                val b = data.readInt()
                reply?.writeInt(minus(a, b))
                return true
            }
            return super.onTransact(code, data, reply, flags)
        }

        abstract class Proxy(private val mRemote: IBinder) : ICalculator

    }

    @Throws(RemoteException::class)
    fun add(a: Int, b: Int): Int

    @Throws(RemoteException::class)
    fun minus(a: Int, b: Int): Int

}