package com.ablec.plugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method
import org.objectweb.asm.*


class TimeCostClassVisitor(
    nextVisitor: ClassVisitor,
    private val config: TimeCostConfig
) : ClassVisitor(Opcodes.ASM9, nextVisitor) {

    private var className: String = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        className = name ?: "Unknown"
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)

        if (name == "<init>" || name == "<clinit>") return mv

        val methodNames = config.methodNames.get()
        if (methodNames.isNotEmpty() && name !in methodNames) return mv

        val tag = config.logTag.get()
        val threshold = config.thresholdTime.get()

        return object : AdviceAdapter(Opcodes.ASM9, mv, access, name, descriptor) {
            private var startTimeVar = 0

            override fun onMethodEnter() {
                invokeStatic(Type.getType(System::class.java), Method("currentTimeMillis", "()J"))
                startTimeVar = newLocal(Type.LONG_TYPE)
                storeLocal(startTimeVar)
            }

            override fun onMethodExit(opcode: Int) {
                val endTimeVar = newLocal(Type.LONG_TYPE)
                invokeStatic(Type.getType(System::class.java), Method("currentTimeMillis", "()J"))
                storeLocal(endTimeVar)

                val costVar = newLocal(Type.LONG_TYPE)
                loadLocal(endTimeVar)
                loadLocal(startTimeVar)
                math(SUB, Type.LONG_TYPE)
                storeLocal(costVar)

                val labelSkip = Label()

                // costTime <= threshold
                loadLocal(costVar)
                visitLdcInsn(threshold) // 👈 Long 类型必须用 visitLdcInsn
                mv.visitInsn(Opcodes.LCMP)
                ifZCmp(LE, labelSkip)

                // Thread.currentThread().name == "main"
                invokeStatic(Type.getType(Thread::class.java), Method("currentThread", "()Ljava/lang/Thread;"))
                invokeVirtual(Type.getType(Thread::class.java), Method("getName", "()Ljava/lang/String;"))
                push("main")
                invokeVirtual(Type.getType(String::class.java), Method("equals", "(Ljava/lang/Object;)Z"))
                ifZCmp(EQ, labelSkip)

                val sbType = Type.getType(StringBuilder::class.java)
                newInstance(sbType)
                dup()
                push("methodName===>$className&$name  threadName==>main  thresholdTime===>$threshold   costTime===>")
                invokeConstructor(sbType, Method("<init>", "(Ljava/lang/String;)V"))
                loadLocal(costVar)
                invokeVirtual(sbType, Method("append", "(J)Ljava/lang/StringBuilder;"))
                push("\n")
                invokeVirtual(sbType, Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"))

                invokeStatic(Type.getType(Thread::class.java), Method("currentThread", "()Ljava/lang/Thread;"))
                invokeVirtual(Type.getType(Thread::class.java), Method("getStackTrace", "()[Ljava/lang/StackTraceElement;"))
                val arrayVar = newLocal(Type.getType(Array<StackTraceElement>::class.java))
                storeLocal(arrayVar)

                val i = newLocal(Type.INT_TYPE)
                push(0)
                storeLocal(i)

                val loopStart = Label()
                val loopEnd = Label()

                mark(loopStart)
                loadLocal(i)
                loadLocal(arrayVar)
                arrayLength()
                ifICmp(GE, loopEnd)

                loadLocal(arrayVar)
                loadLocal(i)
                arrayLoad(Type.getType(StackTraceElement::class.java))
                invokeVirtual(Type.getType(StackTraceElement::class.java), Method("toString", "()Ljava/lang/String;"))
                invokeVirtual(sbType, Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
                push("\n")
                invokeVirtual(sbType, Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
                iinc(i, 1)
                goTo(loopStart)

                mark(loopEnd)

                invokeVirtual(sbType, Method("toString", "()Ljava/lang/String;"))
                push(tag)
                swap()
                invokeStatic(Type.getType("Landroid/util/Log;"), Method("i", "(Ljava/lang/String;Ljava/lang/String;)I"))
                pop()

                mark(labelSkip)
            }
        }
    }
}