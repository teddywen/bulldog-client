/**
 * Simple example of a C++ class that can be binded using the
 * automatic script generator
 */

#include "Util.h"
#include <android/log.h>
USING_NS_CC;

#define  LOG_TAG    "Util"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

USING_NS_CC;

Util* Util::m_instance = NULL;

Util::Util()
{
}

// empty destructor
Util::~Util()
{
}

Util* Util::getInstance()
{
    if (!Util::m_instance) {
        Util::m_instance = new Util();
    }
    return Util::m_instance;
}
