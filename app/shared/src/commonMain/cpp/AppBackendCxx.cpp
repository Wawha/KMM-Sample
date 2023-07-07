#include <cstdio>
#include <jni.h>

//-------------------------------------------------------------------------------------------------
extern "C" JNIEXPORT jstring JNICALL Java_com_example_kmmsample_AppBackend_sayHello(JNIEnv* env, jobject obj)
{
    return env->NewStringUTF("Great string from C++!");
}
