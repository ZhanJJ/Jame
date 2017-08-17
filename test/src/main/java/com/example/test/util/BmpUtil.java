package com.example.test.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.example.test.bitmap.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kin on 2017/5/15.
 */

public class BmpUtil {
    /**
     * bitmap尺寸剪切
     *
     * @param view
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getClipBitmap(View view, int x, int y, int width, int height) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap tempBitmap = view.getDrawingCache();

        int offsetX = x - (int) SignatureView.HALF_STROKE_WIDTH;    //修正x坐标
        int offsetY = y - (int) SignatureView.HALF_STROKE_WIDTH;    //修正y坐标
        int offsetW = width + (int) SignatureView.STROKE_WIDTH;     //修正宽度
        int offsetH = height + (int) SignatureView.STROKE_WIDTH;    //修正高度

        offsetX = offsetX > 0 ? offsetX : x;
        offsetY = offsetY > 0 ? offsetY : y;
        offsetW = offsetW < tempBitmap.getWidth() - offsetX ? offsetW : tempBitmap.getWidth() - offsetX;
        offsetH = offsetH < tempBitmap.getHeight() - offsetY ? offsetH : tempBitmap.getHeight() - offsetY;

        Bitmap bitmap = Bitmap.createBitmap(tempBitmap, offsetX, offsetY, offsetW, offsetH);

        view.setDrawingCacheEnabled(false);
        if (!tempBitmap.isRecycled()) {
            tempBitmap.recycle();
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        view.buildDrawingCache();
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 保存bitmap
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveBitmap(Bitmap bitmap, String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        Log.i("Bitmap Path", file.getAbsolutePath());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
