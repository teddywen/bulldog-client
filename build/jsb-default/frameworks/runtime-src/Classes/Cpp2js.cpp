/**
 * Simple example of a C++ class that can be binded using the
 * automatic script generator
 */

#include "Cpp2js.h"
#include <android/log.h>
#include "jsapi.h"
#include "ScriptingCore.h"
#include <cstring>
 USING_NS_CC;

#define  LOG_TAG    "Cpp2js"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

USING_NS_CC;

Cpp2js* Cpp2js::m_instance = NULL;

Cpp2js::Cpp2js()
{
}

// empty destructor
Cpp2js::~Cpp2js()
{
}

void Cpp2js::recordStarted()
{
    jsval outVal;
    ScriptingCore::getInstance()->evalString("c2j.getInstance().raise('record_started')", &outVal);
}

void Cpp2js::recordStopped()
{
    jsval outVal;
    ScriptingCore::getInstance()->evalString("c2j.getInstance().raise('record_stopped')", &outVal);
}

void Cpp2js::recordUploaded(const char *objectKey)
{
    char script[255] = "";
    strcat(script, "c2j.getInstance().raise('record_uploaded', '");
    strcat(script, objectKey);
    strcat(script, "')");
    jsval outVal;
    ScriptingCore::getInstance()->evalString(script, &outVal);
}

void Cpp2js::recordFetchStarted(const char *objectKey)
{
    char script[255] = "";
    strcat(script, "c2j.getInstance().raise('record_fetch_started', '");
    strcat(script, objectKey);
    strcat(script, "')");
    jsval outVal;
    ScriptingCore::getInstance()->evalString(script, &outVal);
}

void Cpp2js::recordFetched(const char *objectKey)
{
    char script[255] = "";
    strcat(script, "c2j.getInstance().raise('record_fetched', '");
    strcat(script, objectKey);
    strcat(script, "')");
    jsval outVal;
    ScriptingCore::getInstance()->evalString(script, &outVal);
}

void Cpp2js::recordPlayStarted(const char *objectKey)
{
    char script[255] = "";
    strcat(script, "c2j.getInstance().raise('record_play_started', '");
    strcat(script, objectKey);
    strcat(script, "')");
    jsval outVal;
    ScriptingCore::getInstance()->evalString(script, &outVal);
}

void Cpp2js::recordPlayStopped()
{
    jsval outVal;
    ScriptingCore::getInstance()->evalString("c2j.getInstance().raise('record_play_stopped')", &outVal);
}

Cpp2js* Cpp2js::getInstance()
{
    if (!Cpp2js::m_instance) {
        Cpp2js::m_instance = new Cpp2js();
    }
    return Cpp2js::m_instance;
}
