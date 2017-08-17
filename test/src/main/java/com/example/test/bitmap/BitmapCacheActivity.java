package com.example.test.bitmap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.test.R;
import com.example.test.cache.DiskLruCache;
import com.example.test.util.IOUtil;
import com.example.test.util.StringUtil;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class BitmapCacheActivity extends AppCompatActivity {

    private static final int DISK_CACHE_INDEX = 0;
    LruCache<String, Bitmap> bitmapLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);
        long maxSize = Runtime.getRuntime().maxMemory() / 1024; //当前运行设备可用内存
        long caseSize = maxSize / 8; //可用内存
        bitmapLruCache = new LruCache<String, Bitmap>((int) caseSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        int DISK_CACHE_SIZE = 1024 * 1024 * 50; //50M
        File file = new File(this.getCacheDir().getAbsolutePath(), "bitmap");
        if (!file.exists()) {
            file.mkdirs();
        }
        String urlString = "http://www.soomal.com/images/doc/20160714/00061928_01.jpg";
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(file, 1, 1, DISK_CACHE_SIZE);
            String cacheKey = StringUtil.hashKeyFromUrl(urlString);

            DiskLruCache.Editor editor = diskLruCache.edit(cacheKey);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(DISK_CACHE_INDEX);
                if (IOUtil.downloadUrlToString(urlString, os)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                diskLruCache.flush();
            }

            DiskLruCache.Snapshot snapshot = diskLruCache.get(StringUtil.hashKeyFromUrl(urlString));
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFileDescriptor(fileDescriptor,100,100);
            if (bitmap != null) {

            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (!TextUtils.isEmpty(key) && bitmap != null) {
            bitmapLruCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        if (!TextUtils.isEmpty(key)) {
            return bitmapLruCache.get(key);
        } else {
            return null;
        }
    }

}
