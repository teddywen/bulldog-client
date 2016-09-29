package org.cocos2dx.javascript;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by wen.xiang on 16/9/27.
 */

public class AudioErrorCode {
    public final static int SUCCESS = 200;
    public final static int E_NOSDCARD = 1001;
    public final static int E_STATE_RECORDING = 1002;
    public final static int E_STATE_PLAYING = 1003;
    public final static int E_IO = 1004;
    public final static int E_STATE_NOT_RECORDING = 1005;
    public final static int E_STATE_NOT_PLAYING = 1006;
    public final static int E_UNKNOW = 1999;

    public static String getErrorInfo(Context context, int type) throws Resources.NotFoundException {
        switch (type) {
            case SUCCESS:
                return "success";
            case E_NOSDCARD:
                return "no sdcard";
            case E_STATE_RECORDING:
                return "please stop record first";
            case E_STATE_PLAYING:
                return "please stop play record first";
            case E_IO:
                return "io error";
            case E_UNKNOW:
                ;
            default:
                return "unknow error";
        }
    }
}
