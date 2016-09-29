package org.cocos2dx.javascript;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by wen.xiang on 16/9/27.
 */

/**
 * 提供OSS上传下载接口
 */
public class OSSManager {
    private static final String _TAG = "OSSManager";
    private static final String _OSS_END_POINT = "oss-cn-shanghai.aliyuncs.com";
    private static final String _OSS_ACCESS_KEY_ID = "LTAI0kviP5aL8Ulj";
    private static final String _OSS_ACCESS_KEY_SECRET = "XfyPMbWTntFILC5NdvpS9alfu3UNpZ";

    private Context _mContext;
    private OSS _mOss;

    private OSSManager(Context context) {
        _mContext = context;
        _mOss = null;

        _init();
    }

    /**
     * 初始化oss对象
     */
    private void _init() {
        ClientConfiguration config = new ClientConfiguration();
        config.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        config.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        config.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        config.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(_OSS_ACCESS_KEY_ID, _OSS_ACCESS_KEY_SECRET);
        _mOss = new OSSClient(_mContext, _OSS_END_POINT, credentialProvider, config);
    }

    /**
     * 上传文件异步回调
     */
    public static interface IUploadFileCallback {
        public void onFinish(int errorCode, String objectKey);
        public void onFinish(int errorCode, String objectKey, HashMap<String, String> data);
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param objectKey
     * @param uploadPath
     * @param callback
     */
    public void uploadFile(String bucketName, final String objectKey, String uploadPath, final IUploadFileCallback callback) {
        Log.i(_TAG, "uploadFile");
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadPath);
        OSSAsyncTask task = _mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                Log.d(_TAG, "PutObject UploadSuccess");
                Log.d(_TAG, "ETag: " + putObjectResult.getETag());
                Log.d(_TAG, "RequestId: " + putObjectResult.getRequestId());

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ETag", putObjectResult.getETag());
                data.put("RequestId", putObjectResult.getRequestId());
                callback.onFinish(OSSErrorCode.SUCCESS, objectKey, data);
            }

            @Override
            public void onFailure(PutObjectRequest putObjectRequest, ClientException clientException, ServiceException serviceException) {
                boolean unknowError = true;

                // 请求异常
                if (clientException != null) {
                    unknowError = false;
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                    Log.e(_TAG, "Message: " + clientException.getMessage());

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("Message", clientException.getMessage());
                    callback.onFinish(OSSErrorCode.E_NETWORK, objectKey, data);
                }

                if (serviceException != null) {
                    unknowError = false;
                    // 服务异常
                    Log.e(_TAG, "ErrorCode: " + serviceException.getErrorCode());
                    Log.e(_TAG, "RequestId: " + serviceException.getRequestId());
                    Log.e(_TAG, "HostId: " + serviceException.getHostId());
                    Log.e(_TAG, "RawMessage: " + serviceException.getRawMessage());

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("ErrorCode", serviceException.getErrorCode());
                    data.put("RequestId", serviceException.getRequestId());
                    data.put("HostId", serviceException.getHostId());
                    data.put("RawMessage", serviceException.getRawMessage());
                    callback.onFinish(OSSErrorCode.E_SERVICE, objectKey, data);
                }

                if (unknowError) {
                    callback.onFinish(OSSErrorCode.E_UNKNOW, objectKey);
                }
            }
        });
    }

    /**
     * 上传文件异步回调
     */
    public static interface IDownloadFileCallback {
        public void onFinish(int errorCode, String objectKey);
        public void onFinish(int errorCode, String objectKey, HashMap<String, String> data);
    }

    /**
     * 下载文件
     *
     * @param bucketName
     * @param objectKey
     * @param audioPath
     * @param callback
     */
    public void downloadFile(String bucketName, final String objectKey, final String audioPath, final IDownloadFileCallback callback) {
        Log.i(_TAG, "downloadFile");
        GetObjectRequest get = new GetObjectRequest(bucketName, objectKey);
        OSSAsyncTask task = _mOss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest getObjectRequest, GetObjectResult getObjectResult) {
                Log.d(_TAG, "PutObject UploadSuccess");
                Log.d(_TAG, "RequestId: " + getObjectResult.getRequestId());

                // 请求成功
                InputStream inputStream = getObjectResult.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;

                try {
                    File audioFile = new File(audioPath);
                    OutputStream audioOutputStream = new FileOutputStream(audioFile);
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        audioOutputStream.write(buffer, 0, len);
                    }
                    audioOutputStream.close();
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("RequestId", getObjectResult.getRequestId());
                    callback.onFinish(OSSErrorCode.SUCCESS, objectKey, data);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("Message", ioException.getMessage());
                    callback.onFinish(OSSErrorCode.E_IO, objectKey, data);
                }
            }

            @Override
            public void onFailure(GetObjectRequest getObjectRequest, ClientException clientException, ServiceException serviceException) {
                boolean unknowError = true;

                // 请求异常
                if (clientException != null) {
                    unknowError = false;
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                    Log.e(_TAG, "Message: " + clientException.getMessage());

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("Message", clientException.getMessage());
                    callback.onFinish(OSSErrorCode.E_NETWORK, objectKey, data);
                }

                if (serviceException != null) {
                    unknowError = false;
                    // 服务异常
                    // 服务异常
                    Log.e(_TAG, "ErrorCode: " + serviceException.getErrorCode());
                    Log.e(_TAG, "RequestId: " + serviceException.getRequestId());
                    Log.e(_TAG, "HostId: " + serviceException.getHostId());
                    Log.e(_TAG, "RawMessage: " + serviceException.getRawMessage());

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("ErrorCode", serviceException.getErrorCode());
                    data.put("RequestId", serviceException.getRequestId());
                    data.put("HostId", serviceException.getHostId());
                    data.put("RawMessage", serviceException.getRawMessage());
                    callback.onFinish(OSSErrorCode.E_SERVICE, objectKey, data);
                }

                if (unknowError) {
                    callback.onFinish(OSSErrorCode.E_UNKNOW, objectKey);
                }
            }
        });
    }

    /**
     * 单例
     */
    private static OSSManager _mInstance;

    public synchronized static OSSManager getInstance(Context context) {
        if (_mInstance == null) {
            _mInstance = new OSSManager(context);
        }
        return _mInstance;
    }
}
