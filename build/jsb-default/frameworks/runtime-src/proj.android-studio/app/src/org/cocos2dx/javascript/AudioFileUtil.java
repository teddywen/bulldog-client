package org.cocos2dx.javascript;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;

/**
 * Created by wen.xiang on 16/9/27.
 */

public class AudioFileUtil {
    public final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;

    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    public final static int AUDIO_SAMPLE_RATE = 44100;  //44.1KHz,普遍使用的频率
    //录音输出文件
    private final static String _AUDIO_RAW_FILENAME = "RawAudio.raw";
    private final static String _AUDIO_WAV_FILENAME = "FinalAudio.wav";
    private final static String _AUDIO_AMR_FILENAME = "FinalAudio.amr";
    private final static String _BASE_DIRNAME = "bulldog";
    private final static String _RECORD_DIRNAME = "record_audio";
    private final static String _FETCH_DIRNAME = "fetch_audio";

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    public static boolean getIsSDCardExit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得录音文件目录
     *
     * @return
     */
    public static String getRecordDirPath() {
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(fileBasePath + "/" + _BASE_DIRNAME + "/" + _RECORD_DIRNAME);
        return (file.exists() || file.mkdirs()) ? file.getAbsolutePath() : "";
    }

    /**
     * 获得下载录音文件目录
     *
     * @return
     */
    public static String getFetchedDirPath() {
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(fileBasePath + "/" + _BASE_DIRNAME + "/" + _FETCH_DIRNAME);
        return (file.exists() || file.mkdirs()) ? file.getAbsolutePath() : "";
    }

    /**
     * 获取麦克风输入的原始音频流文件路径
     *
     * @return
     */
    public static String getRawFilePath() {
        String audioRawPath = "";
        if (getIsSDCardExit()) {
            audioRawPath = getRecordDirPath() + "/" + _AUDIO_RAW_FILENAME;
        }
        return audioRawPath;
    }

    /**
     * 获取编码后的WAV格式音频文件路径
     *
     * @return
     */
    public static String getWavFilePath() {
        String audioWavPath = "";
        if (getIsSDCardExit()) {
            audioWavPath = getRecordDirPath() + "/" + _AUDIO_WAV_FILENAME;
        }
        return audioWavPath;
    }

    /**
     * 获取编码后的AMR格式音频文件路径
     *
     * @return
     */
    public static String getAMRFilePath() {
        String audioAMRPath = "";
        if (getIsSDCardExit()) {
            audioAMRPath = getRecordDirPath() + "/" + _AUDIO_AMR_FILENAME;
        }
        return audioAMRPath;
    }

    /**
     * 获得AMR格式音频文件文件名
     *
     * @return
     */
    public static String getAMRFileName() {
        return _AUDIO_AMR_FILENAME;
    }

    /**
     * 获得文件大小
     *
     * @return
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return -1;
        } else {
            return file.length();
        }
    }


}
