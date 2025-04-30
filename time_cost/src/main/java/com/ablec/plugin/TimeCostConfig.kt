package com.ablec.plugin

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input


interface TimeCostConfig : InstrumentationParameters {
    @get:Input
    val packageNames: ListProperty<String>
    @get:Input
    val methodNames: ListProperty<String>
    @get:Input
    val logTag: Property<String>
    @get:Input
    val thresholdTime: Property<Long>
}

open class TimeCostExtension {
    var packageNames: List<String> = emptyList()
    var methodNames: List<String> = emptyList()
    var logTag: String = "TimeCost"
    var thresholdTime: Long = 500L

    override fun toString(): String {
        return "TimeCostExtension(packageNames=$packageNames, methodNames=$methodNames, logTag='$logTag', thresholdTime=$thresholdTime)"
    }

}
