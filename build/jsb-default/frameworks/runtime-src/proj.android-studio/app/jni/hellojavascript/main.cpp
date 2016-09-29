#include "AppDelegate.h"
#include "cocos2d.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>
#if PACKAGE_AS
#include "PluginJniHelper.h"
#include "SDKManager.h"
#endif
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
#include "Cpp2js.h"
#endif

#define  LOG_TAG    "main"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

using namespace cocos2d;
#if PACKAGE_AS
using namespace anysdk::framework;
#endif

void cocos_android_app_init (JNIEnv* env) {
    LOGD("cocos_android_app_init");
    AppDelegate *pAppDelegate = new AppDelegate();
#if PACKAGE_AS
    JavaVM* vm;
    env->GetJavaVM(&vm);
    PluginJniHelper::setJavaVM(vm);
#endif
}

extern "C"
{
	void Java_org_cocos2dx_javascript_SDKWrapper_nativeLoadAllPlugins(JNIEnv*  env, jobject thiz)
	{
#if PACKAGE_AS
    	SDKManager::getInstance()->loadAllPlugins();
#endif
	}

    // 在java中对应以下几个native函数
    // private native void nativeRecordStarted();
    // private native void nativeRecordStopped();
    // private native void nativeRecordUploaded(String objectKey);
    // private native void nativeRecordFetched(String objectKey);
    // private native void nativeRecordPlayStarted(String objectKey);
    // private native void nativeRecordPlayStopped();

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordStarted(JNIEnv*  env, jobject thiz)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        Cpp2js::getInstance()->recordStarted();
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordStopped(JNIEnv*  env, jobject thiz)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        Cpp2js::getInstance()->recordStopped();
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordUploaded(JNIEnv*  env, jobject thiz, jstring jObjectKey)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        const char *objectKey = env->GetStringUTFChars(jObjectKey, NULL);
        Cpp2js::getInstance()->recordUploaded(objectKey);
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordFetchStarted(JNIEnv*  env, jobject thiz, jstring jObjectKey)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        const char *objectKey = env->GetStringUTFChars(jObjectKey, NULL);
        Cpp2js::getInstance()->recordFetchStarted(objectKey);
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordFetched(JNIEnv*  env, jobject thiz, jstring jObjectKey)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        const char *objectKey = env->GetStringUTFChars(jObjectKey, NULL);
        Cpp2js::getInstance()->recordFetched(objectKey);
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordPlayStarted(JNIEnv*  env, jobject thiz, jstring jObjectKey)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        const char *objectKey = env->GetStringUTFChars(jObjectKey, NULL);
        Cpp2js::getInstance()->recordPlayStarted(objectKey);
#endif
    }

    void Java_org_cocos2dx_javascript_AppActivity_nativeRecordPlayStopped(JNIEnv*  env, jobject thiz)
    {
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID || CC_TARGET_PLATFORM == CC_PLATFORM_IOS) && PACKAGE_AS
        Cpp2js::getInstance()->recordPlayStopped();
#endif
    }
}
