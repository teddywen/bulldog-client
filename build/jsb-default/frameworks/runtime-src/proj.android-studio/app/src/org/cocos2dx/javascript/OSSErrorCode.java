package org.cocos2dx.javascript;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by wen.xiang on 16/9/28.
 */

public class OSSErrorCode {
    public final static int SUCCESS = 200;
    public final static int E_NETWORK = 1001;
    public final static int E_SERVICE = 1002;
    public final static int E_IO = 1003;
    public final static int E_PATH = 1004;
    public final static int E_UNKNOW = 1999;

    public static String getErrorInfo(Context context, int type) throws Resources.NotFoundException {
        switch (type) {
            case SUCCESS:
                return "success";
            case E_NETWORK:
                return "network error";
            case E_SERVICE:
                return "service error";
            case E_IO:
                return "io error";
            case E_PATH:
                return "path error";
            case E_UNKNOW:
                ;
            default:
                return "unknow error";
        }
    }
}
