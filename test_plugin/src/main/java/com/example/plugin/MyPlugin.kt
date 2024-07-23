package com.example.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.objectweb.asm.ClassVisitor
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(project: Project) {
        val appExtension = project.extensions.getByType(
            AndroidComponentsExtension::class.java
        )
        //创建配置接口
        project.extensions.create("TimeCostConfig", TimeCostConfig::class.java,)
        appExtension.onVariants { variant ->
            //获取配置
            val config = project.extensions.getByType(
                TimeCostConfig::class.java
            )
            //可以通过variant来获取当前编译环境的一些信息，最重要的是可以 variant.name 来区分是debug模式还是release模式编译
            variant.instrumentation.transformClassesWith(TimeCostTransform::class.java, InstrumentationScope.ALL) {
                println("pluginConfig:>>>>>>>>>>>>>>>${config.logTag.get()}")
                //配置通过指定配置的类，携带到TimeCostTransform中
                it.packageNames.set(config.packageNames)
                it.methodNames.set(config.methodNames)
                it.logTag.set(config.logTag)
            }
            //InstrumentationScope.ALL 配合 FramesComputationMode.COPY_FRAMES可以指定该字节码转换器在全局生效，包括第三方lib
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }
}

/**
 * 必须抽象
 */
 abstract class TimeCostTransform : AsmClassVisitorFactory<TimeCostConfig> {
    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        //指定真正的ASM转换器
        return TimeCostClassVisitor(nextClassVisitor, parameters)
    }

    //通过classData中的当前类的信息，用来过滤哪些类需要执行字节码转换，这里支持通过类名，包名，注解，接口，父类等属性来组合判断
    override fun isInstrumentable(classData: ClassData): Boolean {
        val packageConfig = parameters.get().packageNames.get()
        if (packageConfig.isNotEmpty()) {
            return packageConfig.any { classData.className.contains(it) }
        }
        //指定包名执行
        return false
    }

}



