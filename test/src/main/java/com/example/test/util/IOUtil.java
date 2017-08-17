package com.example.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kin on 2017/5/24.
 */

public class IOUtil {
    public static boolean downloadUrlToString(String urlString, OutputStream os) throws IOException {
        int IO_BUFFER_SIZE = 1024 * 1024 * 5; //5m
        HttpURLConnection urlConnection = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bos = new BufferedOutputStream(os, IO_BUFFER_SIZE);
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return false;
    }
}
