package org.cocos2dx.javascript;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by wen.xiang on 16/9/28.
 */

/**
 * 一局游戏对象,包装游戏中用到的操作,如:设置回合数,开始录音,停止录音,远端获取录音,播放录音等
 */
public class Game {
    private static final String _TAG = "Game";

    private Context _mContext;
    private int _mUid;
    private int _mGameId;
    private int _mRound;
    private int _mSpeakSN;
    private HashMap<String, String> _mFetchedRecord;

    /**
     * 新建并初始化一局游戏
     *
     * @param context
     * @param gameId
     */
    public Game(Context context, int gameId, int uid) {
        _mContext = context;
        _mGameId = gameId;
        _mUid = uid;
        _mRound = 0;
        _mFetchedRecord = null;

        _init();
    }

    private void _init() {
        // 每进一局游戏都要把之前的下载录音都删除 防止手机爆炸
        AudioPlayer.getInstance().clearFetchedDirPath();
    }

    /**
     * 设置游戏回合数
     *
     * @param round
     */
    public void setRound(int round) {
        _mRound = round;
        _mSpeakSN = 0;
    }

    /**
     * 累加发言序列并返回累加后的序列号
     *
     * @return
     */
    private int _incrSpeakSN() {
        return ++_mSpeakSN;
    }

    /**
     * 开始录音
     */
    public boolean startRecord() {
        Log.i(_TAG, "start record");
        int code = AudioRecorder.getInstance().startRecordAndFile();
        if (code == AudioErrorCode.SUCCESS) {
            return true;
        } else {
            Log.e(_TAG, "start record error code: " + String.valueOf(code));
            return  false;
        }
    }

    /**
     * 停止录音
     *
     * @param callback 上传后异步调用的回调,回调中获取oss object key
     * @return 录音文件路径
     */
    public boolean stopRecord(OSSManager.IUploadFileCallback callback) {
        Log.i(_TAG, "stop record");
        int code = AudioRecorder.getInstance().stopRecordAndFile();
        if (code == AudioErrorCode.SUCCESS) {
            long audioSize = AudioRecorder.getInstance().getRecordFileSize();
            String audioPath = AudioRecorder.getInstance().getRecordFilePath();
            Log.i(_TAG, "audio file size: " + String.valueOf(audioSize));
            Log.i(_TAG, "audio file path: " + audioPath);
            // 上传录音至OSS
            OSSManager.getInstance(_mContext).uploadFile("bulldog", _makeOSSObjectKey(), audioPath, callback);
            return true;
        } else {
            Log.e(_TAG, "stop record error code: " + String.valueOf(code));
            return  false;
        }
    }

    /**
     * 制作语音路径 格式为: audio/game_<gameId>/round_<round>/uid_<uid>/sn_<speakSN>.amr
     *
     * @return
     */
    private String _makeOSSObjectKey() {
        String audioFileName = AudioRecorder.getInstance().getRecordFileName();
        String ext = Util.getInstance().getFileExtension(audioFileName);

        String path = "audio";
        path += "/game_" + _mGameId;
        path += "/round_" + _mRound;
        path += "/uid_" + _mUid;
        path += "/sn_" + _incrSpeakSN() + "." + ext;
        return path;
    }

    /**
     * 从远端获取录音 回调返回object key和本地文件路径
     *
     * @param objectKey
     */
    public boolean fetchRecord(String objectKey, final OSSManager.IDownloadFileCallback callback) {
        Log.i(_TAG, "fetch record");
        final Game self = this;
        try {
            final String audioPath = AudioPlayer.getInstance().getFetchedFilePath(objectKey);
            OSSManager.getInstance(_mContext).downloadFile("bulldog", objectKey, audioPath, new OSSManager.IDownloadFileCallback() {
                @Override
                public void onFinish(int errorCode, String objectKey) {
                    if (errorCode == OSSErrorCode.SUCCESS) {
                        if (self._mFetchedRecord == null) {
                            self._mFetchedRecord = new HashMap<String, String>();
                        }
                        self._mFetchedRecord.put(objectKey, audioPath);
                    }
                    callback.onFinish(errorCode, objectKey);
                }

                @Override
                public void onFinish(int errorCode, String objectKey, HashMap<String, String> data) {
                    if (errorCode == OSSErrorCode.SUCCESS) {
                        if (self._mFetchedRecord == null) {
                            self._mFetchedRecord = new HashMap<String, String>();
                        }
                        self._mFetchedRecord.put(objectKey, audioPath);
                    }
                    callback.onFinish(errorCode, objectKey, data);
                }
            });
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(_TAG, "Message: " + e.getMessage());
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("Message", e.getMessage());
            return false;
        }
    }

    /**
     * 是否已获取完毕远端录音文件
     *
     * @param objectKey
     * @return
     */
    public boolean getIsFetched(String objectKey) {
        return _mFetchedRecord != null && _mFetchedRecord.containsKey(objectKey);
    }

    /**
     * 播放录音
     *
     * @param objectKey
     */
    public boolean startPlayRecord(String objectKey, MediaPlayer.OnCompletionListener completeCallback) {
        Log.i(_TAG, "play record");
        if (_mFetchedRecord != null && _mFetchedRecord.containsKey(objectKey)) {
            int code = AudioPlayer.getInstance().startPlay(_mFetchedRecord.get(objectKey), completeCallback);
            return code == AudioErrorCode.SUCCESS;
        } else {
            return false;
        }
    }

    /**
     * 结束播放录音
     */
    public boolean stopPlayRecord() {
        Log.i(_TAG, "stop record");
        int code = AudioPlayer.getInstance().stopPlay();
        return code == AudioErrorCode.SUCCESS;
    }
}
