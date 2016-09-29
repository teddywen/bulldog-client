package org.cocos2dx.javascript;

/**
 * Created by wen.xiang on 16/9/28.
 */

import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 提供播放相关接口
 */
public class AudioPlayer {

    private static final String _TAG = "AudioPlayer";

    private MediaPlayer _mMediaPlayer;
    private boolean _mIsPlaying;

    private AudioPlayer() {
        _mMediaPlayer = null;
        _mIsPlaying = false;
    }

    /**
     * 获取指定oss object key的本地下载文件路径
     *
     * @param objectKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String getFetchedFilePath(String objectKey) throws NoSuchAlgorithmException {
        String ext = Util.getInstance().getFileExtension(objectKey);
        return AudioFileUtil.getFetchedDirPath() + "/" + Util.getInstance().md5(objectKey) + "." + ext;
    }

    /**
     * 获取指定oss object key的本地下载文件名
     *
     * @param objectKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String getFetchedFileName(String objectKey) throws NoSuchAlgorithmException {
        String ext = Util.getInstance().getFileExtension(objectKey);
        return Util.getInstance().md5(objectKey) + "." + ext;
    }

    /**
     * 获取录音下载文件夹路径
     *
     * @return
     */
    public String getFetchedDirPath() {
        return AudioFileUtil.getFetchedDirPath();
    }

    /**
     * 清空录音下载文件夹路径
     *
     * @return
     */
    public boolean clearFetchedDirPath() {
        Log.i(_TAG, "clearFetchedDirPath");
        File file = new File(AudioFileUtil.getFetchedDirPath());
        Log.i(_TAG, "fetch dir path: " + file.getAbsolutePath());
        return Util.getInstance().deleteFile(file);
    }

    /**
     * 播放录音
     *
     * @param fetchedFilePath
     */
    public int startPlay(String fetchedFilePath, MediaPlayer.OnCompletionListener completeCallback) {
        Log.i(_TAG, "play fetchedFilePath: " + String.valueOf(fetchedFilePath));
        if (_mIsPlaying) {
            return AudioErrorCode.E_STATE_PLAYING;
        }
        if (_mMediaPlayer == null) {
            _createMediaPlayer();
        }
        try {
            final AudioPlayer self = this;
            _mMediaPlayer.setDataSource(fetchedFilePath);
            _mMediaPlayer.prepare();
            _mMediaPlayer.start();
            _mMediaPlayer.setOnCompletionListener(completeCallback);
            _mIsPlaying = true;
            return AudioErrorCode.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(_TAG, e.getMessage());
            return AudioErrorCode.E_IO;
        }
    }

    /**
     * 停止播放录音
     */
    public int stopPlay() {
        if (!_mIsPlaying) {
            return AudioErrorCode.E_STATE_NOT_PLAYING;
        }
        if (_mMediaPlayer != null) {
            _mIsPlaying = false;
            _mMediaPlayer.stop();
            _mMediaPlayer.release();
            _mMediaPlayer = null;
            return AudioErrorCode.SUCCESS;
        }
        return AudioErrorCode.E_UNKNOW;
    }

    /**
     * 创建新的播放器
     */
    private void _createMediaPlayer() {
        _mMediaPlayer = new MediaPlayer();
    }

    /**
     * 单例
     */
    private static AudioPlayer _mInstance;

    public synchronized static AudioPlayer getInstance() {
        if (_mInstance == null) {
            _mInstance = new AudioPlayer();
        }
        return _mInstance;
    }
}
