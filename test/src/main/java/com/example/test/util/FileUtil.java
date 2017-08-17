package com.example.test.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kin on 2017/4/24.
 */

public class FileUtil {
    public static String TAG = "FileUtil";

    public static boolean copyFile(InputStream inputStream, String saveFileName) {
        if (inputStream == null) {
            return false;
        }
        boolean result = false;
        File file = new File(saveFileName);
        byte[] value = new byte[1024];
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int length = 0;
            while ((length = inputStream.read(value)) > 0) {
                outputStream.write(value, 0, length);
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 文件转字节数组
     *
     * @param path
     * @return
     */
    public static byte[] file2Bytes(String path) {
        if (TextUtils.isEmpty(path)) {
            Log.e("FileUtil", "File path can no be null!");
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.e("FileUtil", "File  can no be null!");
            return null;
        }

        byte[] bytes = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(file);
            bytes = new byte[fis.available()];
            fis.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
