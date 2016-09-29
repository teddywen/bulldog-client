/****************************************************************************
 * Copyright (c) 2015 Chukong Technologies Inc.
 * <p>
 * http://www.cocos2d-x.org
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.javascript;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

// For JS and JAVA reflection test, you can delete it if it's your own project
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.AlertDialog;
// -------------------------------------

import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

public class AppActivity extends Cocos2dxActivity {

    private static final String _TAG = "AppActivity";
    private static AppActivity app = null;

    private Game _mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this;
        SDKWrapper.getInstance().init(this);
    }

    // 一堆c2j接口
    private native void nativeRecordStarted(); // 录音开始
    private native void nativeRecordStopped(); // 录音停止
    private native void nativeRecordUploaded(String objectKey); // 录音上传完成
    private native void nativeRecordFetchStarted(String objectKey); // 开始下载录音
    private native void nativeRecordFetched(String objectKey); // 录音下载完成
    private native void nativeRecordPlayStarted(String objectKey); // 开始播放录音
    private native void nativeRecordPlayStopped(); // 停止播放录音

    /**
     * 进入一局游戏
     *
     * @param gameId
     * @param uid
     */
    public static void enterGameSt(final int gameId, final int uid) {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.enterGame(gameId, uid);
            }
        });
    }
    public void enterGame(int gameId, int uid) {
        Log.i(_TAG, "enter game: " + String.valueOf(gameId) + ", " + String.valueOf(uid));
        _mGame = new Game(this, gameId, uid);
    }

    /**
     * 进入游戏的第round回合
     *
     * @param round
     */
    public static void setRoundSt(final int round) {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.setRound(round);
            }
        });
    }
    public void setRound(int round) {
        Log.i(_TAG, "enter round: " + String.valueOf(round));
        if (_mGame != null) {
            _mGame.setRound(round);
        }
    }

    /**
     * 开始录音
     */
    public static void startRecordSt() {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.startRecord();
            }
        });
    }
    public void startRecord() {
        Log.i(_TAG, "start record");
        if (_mGame != null && _mGame.startRecord()) {
            final AppActivity self = this;
            runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    self.nativeRecordStarted();
                }
            });
        }
    }

    /**
     * 停止录音 录音完成后上传OSS 上传完成后把object key返还给native处理
     */
    public static void stopRecordSt() {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.stopRecord();
            }
        });
    }
    public void stopRecord() {
        Log.i(_TAG, "stop record");
        final AppActivity self = this;
        OSSManager.IUploadFileCallback callback = new OSSManager.IUploadFileCallback() {
            @Override
            public void onFinish(int errorCode, String objectKey) {
                Log.d(_TAG, "stop record on finish object key: " + objectKey);
                if (errorCode == OSSErrorCode.SUCCESS) {
                    _nativeRecordUploaded(objectKey);
                }
            }

            @Override
            public void onFinish(int errorCode, String objectKey, HashMap<String, String> data) {
                Log.d(_TAG, "stop record on finish object key: " + objectKey);
                if (errorCode == OSSErrorCode.SUCCESS) {
                    _nativeRecordUploaded(objectKey);
                }
            }

            private void _nativeRecordUploaded(final String objectKey) {
                self.runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
                        self.nativeRecordUploaded(objectKey);
                    }
                });
            }
        };
        if (_mGame != null && _mGame.stopRecord(callback)) {
            runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    self.nativeRecordStopped();
                }
            });
        }
    }

    /**
     * 从远端下载指定录音文件 下载完成后把object key返还给native处理
     *
     * @param objectKey
     */
    public static void fetchRecordSt(final String objectKey) {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.fetchRecord(objectKey);
            }
        });
    }
    public void fetchRecord(final String objectKey) {
        Log.i(_TAG, "fetch record: " + objectKey);
        final AppActivity self = this;
        OSSManager.IDownloadFileCallback callback = new OSSManager.IDownloadFileCallback() {
            @Override
            public void onFinish(int errorCode, String objectKey) {
                Log.d(_TAG, "on finish fetch record error code: " + String.valueOf(errorCode) + ", object key: " + objectKey);
                if (errorCode == OSSErrorCode.SUCCESS) {
                    _nativeRecordFetched(objectKey);
                }
            }

            @Override
            public void onFinish(int errorCode, String objectKey, HashMap<String, String> data) {
                Log.d(_TAG, "on finish fetch record error code: " + String.valueOf(errorCode) + ", object key: " + objectKey + ", data: " + data.toString());
                if (errorCode == OSSErrorCode.SUCCESS) {
                    _nativeRecordFetched(objectKey);
                }
            }

            private void _nativeRecordFetched(final String objectKey) {
                self.runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
                        self.nativeRecordFetched(objectKey);
                    }
                });
            }
        };
        if (_mGame != null && _mGame.fetchRecord(objectKey, callback)) {
            runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    self.nativeRecordFetchStarted(objectKey);
                }
            });
        }
    }

    /**
     * 播放指定录音
     *
     * @param objectKey
     */
    public static void startPlayRecordSt(final String objectKey) {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.startPlayRecord(objectKey);
            }
        });
    }
    public void startPlayRecord(final String objectKey) {
        Log.i(_TAG, "start play record: " + objectKey);
        final AppActivity self = this;
        MediaPlayer.OnCompletionListener completeCallback = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                self.stopPlayRecord();
            }
        };
        if (_mGame != null && _mGame.startPlayRecord(objectKey, completeCallback)) {
            runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    self.nativeRecordPlayStarted(objectKey);
                }
            });
        }
    }

    /**
     * 停止播放录音
     */
    public static void stopPlayRecordSt() {
        AppActivity.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppActivity.app.stopPlayRecord();
            }
        });
    }
    public void stopPlayRecord() {
        Log.i(_TAG, "stop play record");
        if (_mGame != null && _mGame.stopPlayRecord()) {
            final AppActivity self = this;
            runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    self.nativeRecordPlayStopped();
                }
            });
        }
    }

    @Override
    public Cocos2dxGLSurfaceView onCreateView() {
        Cocos2dxGLSurfaceView glSurfaceView = new Cocos2dxGLSurfaceView(this);
        // TestCpp should create stencil buffer
        glSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);

        SDKWrapper.getInstance().setGLSurfaceView(glSurfaceView);

        return glSurfaceView;
    }

    // For JS and JAVA reflection test, you can delete it if it's your own project
    public static void showAlertDialog(final String title, final String message) {
        // Here be sure to use runOnUiThread
        app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(app).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SDKWrapper.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SDKWrapper.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKWrapper.getInstance().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SDKWrapper.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SDKWrapper.getInstance().onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SDKWrapper.getInstance().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SDKWrapper.getInstance().onStop();
    }
}
