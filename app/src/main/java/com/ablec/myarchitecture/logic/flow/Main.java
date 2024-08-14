package com.ablec.myarchitecture.logic.flow;

import androidx.annotation.NonNull;

import com.ablec.module_base.util.Coroutines;
import com.ablec.module_base.util.CoroutinesKt;
import com.ablec.module_base.util.SuspendResponse;

import kotlinx.coroutines.Dispatchers;

public class Main {
    public static void main(String[] args) {
        Coroutines.javaCallSuspend("request", CoroutinesKt.getContinuation(new SuspendResponse<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(@NonNull Throwable t) {

            }
        }, Dispatchers.getIO()));
    }


}
