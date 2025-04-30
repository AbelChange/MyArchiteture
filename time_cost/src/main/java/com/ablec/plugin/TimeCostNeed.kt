package com.ablec.plugin

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME) // 或 CLASS，用于 ASM 插桩时识别
annotation class TimeCostNeed