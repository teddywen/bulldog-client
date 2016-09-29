#ifndef __UTIL_H__
#define __UTIL_H__

#include <string>
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
using namespace std;

class Util
{
private:
    static Util* m_instance;

public:

    Util();
	~Util();

    static Util* getInstance();
};

#endif
