/**
 * Simple example of a C++ class that can be binded using the
 * automatic script generator
 */

#include "hc_bridge.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>

#define  LOG_TAG    "hc_bridge"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

USING_NS_CC;

Bridge::Bridge()
{
}

// empty destructor
Bridge::~Bridge()
{
}

void Bridge::startRecord()
{
    LOGD("Bridge::startRecord");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "startRecordSt", "()V"))
    {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
    } 
    else
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:startRecordSt is invalid");
    }
}

void Bridge::stopRecord()
{
	LOGD("Bridge::stopRecord");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "stopRecordSt", "()V"))
    {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:stopRecordSt is invalid");
    }
}

void Bridge::fetchRecord(const char *objectKey)
{
    LOGD("Bridge::fetchRecord");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "fetchRecordSt", "(Ljava/lang/String;)V"))
    {
        jstring jObjectKey = methodInfo.env->NewStringUTF(objectKey);
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jObjectKey);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:fetchRecordSt is invalid");
    }
}

void Bridge::startPlayRecord(const char *objectKey)
{
    LOGD("Bridge::startPlayRecord");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "startPlayRecordSt", "(Ljava/lang/String;)V"))
    {
        jstring jObjectKey = methodInfo.env->NewStringUTF(objectKey);
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jObjectKey);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:startPlayRecordSt is invalid");
    }
}

void Bridge::stopPlayRecord()
{
    LOGD("Bridge::stopPlayRecord");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "stopPlayRecordSt", "()V"))
    {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:stopPlayRecordSt is invalid");
    }
}

void Bridge::enterGame(int gameId, int uid)
{
    LOGD("Bridge::enterGame");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "enterGameSt", "(II)V"))
    {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, (jint)gameId, (jint)uid);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:enterGameSt is invalid");
    }
}

void Bridge::setRound(int round)
{
    LOGD("Bridge::setRound");
    JniMethodInfo methodInfo;
    if (JniHelper::getStaticMethodInfo(methodInfo, "org/cocos2dx/javascript/AppActivity", "setRoundSt", "(I)V"))
    {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, (jint)round);
    } 
    else 
    {
        LOGE("method org/cocos2dx/javascript/AppActivity:setRoundSt is invalid");
    }
}

