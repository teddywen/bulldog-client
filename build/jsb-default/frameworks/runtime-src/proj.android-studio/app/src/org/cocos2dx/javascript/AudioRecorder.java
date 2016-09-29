package org.cocos2dx.javascript;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by wen.xiang on 16/9/27.
 */

/**
 * 提供录音相关接口
 */
public class AudioRecorder {
    private static final String _TAG = "AudioRecorder";

    private boolean _mIsRecord;

    private MediaRecorder _mMediaRecorder;

    private AudioRecorder() {
        _mIsRecord = false;
    }

    /**
     * 新建录音机对象
     */
    private void _createMediaRecord() {
        /* ①Initial：实例化MediaRecorder对象 */
        _mMediaRecorder = new MediaRecorder();

        /* setAudioSource/setVedioSource*/
        _mMediaRecorder.setAudioSource(AudioFileUtil.AUDIO_INPUT); //设置麦克风

        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
         */
        _mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
        _mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        /* 设置输出文件的路径 */
        File file = new File(AudioFileUtil.getAMRFilePath());
        if (file.exists()) {
            file.delete();
        }
        _mMediaRecorder.setOutputFile(AudioFileUtil.getAMRFilePath());
    }

    /**
     * 开始录音并存储至文件
     *
     * @return
     */
    public int startRecordAndFile() {
        Log.i(_TAG, "start record and file");
        //判断是否有外部存储设备sdcard
        if (!AudioFileUtil.getIsSDCardExit()) {
            return AudioErrorCode.E_NOSDCARD;
        }
        if (_mIsRecord) {
            return AudioErrorCode.E_STATE_RECORDING;
        }
        try {
            if (_mMediaRecorder == null) {
                _createMediaRecord();
            }
            _mMediaRecorder.prepare();
            _mMediaRecorder.start();
            // 让录制状态为true
            _mIsRecord = true;
            return AudioErrorCode.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return AudioErrorCode.E_UNKNOW;
        }
    }

    /**
     * 停止录音并存储至文件
     */
    public int stopRecordAndFile() {
        Log.i(_TAG, "stop record and file");
        if (!_mIsRecord) {
            return AudioErrorCode.E_STATE_NOT_RECORDING;
        }
        if (_mMediaRecorder != null) {
            _mIsRecord = false;
            _mMediaRecorder.stop();
            _mMediaRecorder.release();
            _mMediaRecorder = null;
            return AudioErrorCode.SUCCESS;
        }
        return AudioErrorCode.E_UNKNOW;
    }

    /**
     * 获取录音文件的大小 单位byte
     *
     * @return
     */
    public long getRecordFileSize() {
        return AudioFileUtil.getFileSize(AudioFileUtil.getAMRFilePath());
    }

    /**
     * 获得录音文件的文件路径
     *
     * @return
     */
    public String getRecordFilePath() {
        return AudioFileUtil.getAMRFilePath();
    }

    /**
     * 获得露营文件的文件名
     *
     * @return
     */
    public String getRecordFileName() {
        return AudioFileUtil.getAMRFileName();
    }

    /**
     * 单例
     */
    private static AudioRecorder _mInstance;

    public synchronized static AudioRecorder getInstance() {
        if (_mInstance == null) {
            _mInstance = new AudioRecorder();
        }
        return _mInstance;
    }
}
