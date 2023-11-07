#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ablec_module_1native_NativeLib_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_ablec_module_1native_NativeLib_setJavaCallback(JNIEnv *env, jobject thiz, jobject cb) {
    // 获取 CallBack 接口的类和方法
    jclass callbackClass = env->GetObjectClass(cb);
    jmethodID onStringGetMethod = env->GetMethodID(callbackClass, "onStringGet", "(Ljava/lang/String;)V");
    // 示例字符串
    jstring exampleString = env->NewStringUTF("java callback JNI holds");
    // 调用回调方法
    env->CallVoidMethod(cb, onStringGetMethod, exampleString);
    // 释放局部引用
    env->DeleteLocalRef(callbackClass);
    env->DeleteLocalRef(exampleString);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_ablec_module_1native_NativeLib_release(JNIEnv *env, jobject thiz) {


}