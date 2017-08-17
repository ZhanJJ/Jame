package com.example.test.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Kin on 2017/5/25.
 */

public class ImageLoader {
    Context mContext;
    private static long maxSize = Runtime.getRuntime().maxMemory() / 1024; //当前运行设备可用内存
    private static long caseSize = maxSize / 8; //可用内存
    LruCache<String, Bitmap> bitmapLruCache;

    private static int DISK_CACHE_SIZE = 1024 * 1024 * 50; //50m

    public ImageLoader(Context context){
        bitmapLruCache = new LruCache<String, Bitmap>((int) caseSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };


    }

}
