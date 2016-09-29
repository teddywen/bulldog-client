package org.cocos2dx.javascript;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wen.xiang on 16/9/28.
 */

public class Util {
    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 计算md5
     *
     * @param s
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String md5(String s) throws NoSuchAlgorithmException {
        final String MD5 = "MD5";
        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file
     */
    public boolean deleteFile(File file) {
        if (file.exists() == false) {
            return false;
        } else {
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();

            }
            return true;
        }
    }

    /**
     * 单例
     */
    private static Util _mInstance;

    public synchronized static Util getInstance() {
        if (_mInstance == null) {
            _mInstance = new Util();
        }
        return _mInstance;
    }
}
