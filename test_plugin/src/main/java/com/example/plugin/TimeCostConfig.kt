package com.example.plugin

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
}

