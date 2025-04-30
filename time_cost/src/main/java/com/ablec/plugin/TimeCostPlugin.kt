package com.ablec.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.objectweb.asm.ClassVisitor
import org.gradle.api.Plugin
import org.gradle.api.Project

class TimeCostPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(project: Project) {
        val appExtension = project.extensions.getByType(AndroidComponentsExtension::class.java)

        // 创建配置扩展
        val extension = project.extensions.create("timeCostConfig", TimeCostExtension::class.java)

        appExtension.onVariants { variant ->
            if (variant.buildType == "debug") {
                variant.instrumentation.transformClassesWith(
                    TimeCostClassVisitorFactory::class.java,
                    InstrumentationScope.ALL
                ) {
                    // 尝试从扩展中获取配置项，如果为空则用默认值
                    println("TimeCostPluginConfigSuccess:>>>>>>>>>>>>>>>${extension}")
                    it.packageNames.set(extension.packageNames.ifEmpty { listOf("com.ablec.myarchitecture") })
                    it.methodNames.set(extension.methodNames.ifEmpty { listOf("onCreate","onStart","onCreateView") })
                    it.logTag.set(extension.logTag.ifBlank { "TimeCost" })
                    it.thresholdTime.set(extension.thresholdTime) // 设置默认阈值 500ms
                }

                variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
            }
        }
    }
}

/**
 * 必须抽象
 */
 abstract class TimeCostClassVisitorFactory : AsmClassVisitorFactory<TimeCostConfig> {
    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        //指定真正的ASM转换器
        val timeCostConfig = parameters.get()
        return TimeCostClassVisitor(nextClassVisitor, timeCostConfig)
    }

    //通过classData中的当前类的信息，用来过滤哪些类需要执行字节码转换，这里支持通过类名，包名，注解，接口，父类等属性来组合判断
    override fun isInstrumentable(classData: ClassData): Boolean {
        val packageConfig = parameters.get().packageNames.get()
        if (!packageConfig.isNullOrEmpty()) {
            return packageConfig.any { classData.className.contains(it) }
        }
        return false
    }

}



