package com.example.test.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kin on 2017/4/11.
 */

public class BitmapUtil {
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqHeight, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // First decode with inJustDecodeBounds=true to check dimensions
        // 设置 inJustDecodeBounds 属性为true可以在解码的时候避免内存的分配，它会返回一个null的Bitmap，
        // 但是可以获取到 outWidth, outHeight 与 outMimeType
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int reqHeight, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // First decode with inJustDecodeBounds=true to check dimensions
        // 设置 inJustDecodeBounds 属性为true可以在解码的时候避免内存的分配，它会返回一个null的Bitmap，
        // 但是可以获取到 outWidth, outHeight 与 outMimeType
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        // Raw height and width of image
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;

        int inSampleSize = 1;

        if (outHeight > reqHeight && outWidth > reqWidth) {
            int halfHeight = outHeight / 2;
            int halfWidth = outWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            //设置inSampleSize为2的幂是因为解码器最终还是会对非2的幂的数进行向下处理，获取到最靠近2的幂的数。详情参考inSampleSize的文档。
            while (halfHeight / inSampleSize > reqHeight &&
                    halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = view.getDrawingCache();
//        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static String saveBitmap(Bitmap bitmap, String folder, String name) {
        String path = "";
        String directory = Environment.getExternalStorageDirectory().getPath(); //获得SD卡目录/mnt/sdcard（获取的是手机外置sd卡的路径）
        File fileFd = new File(directory + "/" + folder);
        if (!fileFd.exists()) {
            fileFd.mkdirs();
        }

        path = directory + "/" + folder + "/" + name;
        File file = new File(fileFd.getAbsolutePath(), name);
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

    /**
     * 从路径中获取位图
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        String filePath = path;
        File file = new File(filePath);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(filePath); //解析为bitmap
        }
        return bitmap;
    }

    /**
     * 方法描述：对Bitmap进行质量压缩并保存在File中
     *
     * @param bitmap        原始图片
     * @param reqBitmapSize 期望压缩后Stream或者File形式的图片大小，单位KB
     * @param file          将压缩后的图片保存在文件中以便于上传
     */
    public static void compressImageByQuality(Bitmap bitmap, int reqBitmapSize, File file) {
        if (bitmap == null || file == null) {
            Log.i("Bitmap", "Bitmap or File can no be null!");
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 100;
        if (baos.toByteArray().length / 1024 > reqBitmapSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); //重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
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
    }

    /**
     * 方法描述：对Bitmap进行质量压缩
     *
     * @param bitmap        原始图片
     * @param reqBitmapSize 期望压缩后Stream或者File形式的图片大小，单位KB
     * @return 经质量压缩后的Bitmap
     */
    public static Bitmap compressImageByQuality(Bitmap bitmap, int reqBitmapSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        boolean compressResult = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > reqBitmapSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(bis);
    }

    /**
     * 缩放图片至指定尺寸
     *
     * @param bitmap
     * @param newHeight
     * @param newWidth
     * @return
     */
    public static Bitmap zoomImage(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            Log.e("BitmapUtil", "Bitmap can no be null!");
            return bitmap;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            Log.e("BitmapUtil", "Width or Height can no less than 0!");
            return bitmap;
        }

        double height = bitmap.getHeight();
        double width = bitmap.getWidth();

        Matrix matrix = new Matrix();
        double scaleHeight = newHeight / height;
        double scaleWidth = newWidth / width;

        matrix.postScale((float) scaleWidth, (float) scaleHeight);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bm;
    }

    /**
     * 将Bitmap存为 .bmp格式图片
     *
     * @param bitmap
     */
    public static boolean saveBitmap2Bmp(String path, String name, Bitmap bitmap) {
        boolean isSuccess = false;
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(name) || bitmap == null) {
            return isSuccess;
        }

        // 位图大小
        int nBmpWidth = bitmap.getWidth();
        int nBmpHeight = bitmap.getHeight();
        // 图像数据大小
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            File imgFile = new File(file.getAbsolutePath(), name); //图片文件
            Log.i("Bitmap path", imgFile.getAbsolutePath());
            if (imgFile.exists()) {
                imgFile.delete();
            }

            FileOutputStream fileos = new FileOutputStream(imgFile);
            // bmp文件头
            int bfType = 0x4d42;
            long bfSize = 14 + 40 + bufferSize;
            int bfReserved1 = 0;
            int bfReserved2 = 0;
            long bfOffBits = 14 + 40;
            // 保存bmp文件头
            writeWord(fileos, bfType);
            writeDword(fileos, bfSize);
            writeWord(fileos, bfReserved1);
            writeWord(fileos, bfReserved2);
            writeDword(fileos, bfOffBits);
            // bmp信息头
            long biSize = 40L;
            long biWidth = nBmpWidth;
            long biHeight = nBmpHeight;
            int biPlanes = 1;
            int biBitCount = 1;
            long biCompression = 0L;
            long biSizeImage = 0L;
            long biXpelsPerMeter = 0L;
            long biYPelsPerMeter = 0L;
            long biClrUsed = 0L;
            long biClrImportant = 0L;
            // 保存bmp信息头
            writeDword(fileos, biSize);
            writeLong(fileos, biWidth);
            writeLong(fileos, biHeight);
            writeWord(fileos, biPlanes);
            writeWord(fileos, biBitCount);
            writeDword(fileos, biCompression);
            writeDword(fileos, biSizeImage);
            writeLong(fileos, biXpelsPerMeter);
            writeLong(fileos, biYPelsPerMeter);
            writeDword(fileos, biClrUsed);
            writeDword(fileos, biClrImportant);
            // 像素扫描
            byte bmpData[] = new byte[bufferSize];
            int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);
            for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)
                for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {
                    int clr = bitmap.getPixel(wRow, nCol);
                    bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);
                }
            fileos.write(bmpData);
            fileos.flush();
            fileos.close();
            isSuccess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    private static void writeWord(FileOutputStream stream, int value) throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        stream.write(b);
    }

    private static void writeDword(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    private static void writeLong(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
        return resizeBmp;
    }

    /**
     * 将bitmap转换成bmp格式图片
     *
     * @param bitmap 要转换的bitmap
     * @param fos    bmp文件输出流
     */
    public static void format2BMP(Bitmap bitmap, FileOutputStream fos) {
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int[] pixels = new int[w * h];
            bitmap.getPixels(pixels, 0, w, 0, 0, w, h);//取得BITMAP的所有像素点

            byte[] rgb = addBMP_RGB_888(pixels, w, h);
            byte[] header = addBMPImageHeader(62 + rgb.length);
            byte[] infos = addBMPImageInfosHeader(w, h, rgb.length);
            byte[] colortable = addBMPImageColorTable();

            byte[] buffer = new byte[62 + rgb.length];

            System.arraycopy(header, 0, buffer, 0, header.length);
            System.arraycopy(infos, 0, buffer, 14, infos.length);
            System.arraycopy(colortable, 0, buffer, 54, colortable.length);
            System.arraycopy(rgb, 0, buffer, 62, rgb.length);
            try {
                fos.write(buffer);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 将bitmap转换成bmp格式图片
     *
     * @param bitmap 要转换的bitmap
     */
    public static boolean format2BMP(Bitmap bitmap, String path, String name) {
        boolean isSuccess = false;
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(name) || bitmap == null) {
            return isSuccess;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pixels = new int[w * h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);//取得BITMAP的所有像素点

        byte[] rgb = bitmap2Bytes(bitmap);
        byte[] header = addBMPImageHeader(62 + rgb.length);
        byte[] infos = addBMPImageInfosHeader(w, h, rgb.length);
        byte[] colortable = addBMPImageColorTable();

        byte[] buffer = new byte[62 + rgb.length];

        System.arraycopy(header, 0, buffer, 0, header.length);
        System.arraycopy(infos, 0, buffer, 14, infos.length);
        System.arraycopy(colortable, 0, buffer, 54, colortable.length);
        System.arraycopy(rgb, 0, buffer, 62, rgb.length);

        File file = new File(path); //创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }

        File imgFile = new File(file.getAbsolutePath(), name); //图片文件
        Log.i("Bitmap path", imgFile.getAbsolutePath());
        if (imgFile.exists()) {
            imgFile.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(imgFile);
            fos.write(buffer);
            fos.flush();
            fos.close();
            isSuccess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // BMP文件头
    public static byte[] addBMPImageHeader(int size) {
        byte[] buffer = new byte[14];
        buffer[0] = 0x42; //ASCII码 B
        buffer[1] = 0x4D; //ASCII码 D
        buffer[2] = (byte) (size >> 0);
        buffer[3] = (byte) (size >> 8);
        buffer[4] = (byte) (size >> 16);
        buffer[5] = (byte) (size >> 24);
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;

        buffer[10] = 0x3E; //从文件开始到位图数据之间的偏移量(14+40+4*（2^biBitCount）)
        buffer[11] = 0x00;
        buffer[12] = 0x00;
        buffer[13] = 0x00;
        return buffer;
    }

    // BMP文件信息头
    public static byte[] addBMPImageInfosHeader(int w, int h, int size) {

        Log.i("_DETEST_", "size=" + size);
        byte[] buffer = new byte[40];
        buffer[0] = 0x28; //位图图信息头长度:40
        buffer[1] = 0x00;
        buffer[2] = 0x00;
        buffer[3] = 0x00;

        buffer[4] = (byte) (w >> 0);
        buffer[5] = (byte) (w >> 8);
        buffer[6] = (byte) (w >> 16);
        buffer[7] = (byte) (w >> 24);

        buffer[8] = (byte) (h >> 0);
        buffer[9] = (byte) (h >> 8);
        buffer[10] = (byte) (h >> 16);
        buffer[11] = (byte) (h >> 24);

        buffer[12] = 0x01;
        buffer[13] = 0x00;

        buffer[14] = 0x01; //每个像素的位数,即biBitCount
        buffer[15] = 0x00;

        buffer[16] = 0x00; //压缩说明,0表示不压缩
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;

        buffer[20] = (byte) (size >> 0); //用字节数表示的位图数据的大小，该数必须是4的倍数，数值上等于（≥位图宽度的最小的4的倍数）×位图高度×每个像素位数
        buffer[21] = (byte) (size >> 8);
        buffer[22] = (byte) (size >> 16);
        buffer[23] = (byte) (size >> 24);

//  buffer[24] = (byte) 0xE0;
//  buffer[25] = 0x01;
        buffer[24] = (byte) 0xC4; //用象素/米表示的水平分辨率
        buffer[25] = 0x0E;
        buffer[26] = 0x00;
        buffer[27] = 0x00;

//  buffer[28] = 0x02;
//  buffer[29] = 0x03;
        buffer[28] = (byte) 0xC4; //用象素/米表示的垂直分辨率
        buffer[29] = 0x0E;
        buffer[30] = 0x00;
        buffer[31] = 0x00;

        buffer[32] = 0x00; //位图使用的颜色索引数。设为0的话，则说明使用所有调色板项。
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;

        buffer[36] = 0x00; //对图象显示有重要影响的颜色索引的数目。如果是0，表示都重要。
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;
        return buffer;
    }

    //BMP文件调色板
    public static byte[] addBMPImageColorTable() {
        byte[] buffer = new byte[8];
//        buffer[0] = (byte) 0xFF;
//        buffer[1] = (byte) 0xFF;
//        buffer[2] = (byte) 0xFF;
        buffer[0] = (byte) 0x00; //直接按照HXW图片格式修改
        buffer[1] = (byte) 0x00;
        buffer[2] = (byte) 0x00;
        buffer[3] = 0x00;

//        buffer[4] = 0x00;
//        buffer[5] = 0x00;
//        buffer[6] = 0x00;
        buffer[4] = (byte) 0xFF; //直接按照HXW图片格式修改
        buffer[5] = (byte) 0xFF;
        buffer[6] = (byte) 0xFF;
        buffer[7] = 0x00;
        return buffer;
    }

    public static byte[] addBMP_RGB_888(int[] b, int w, int h) {
        int len = w * h;
        int bufflen = 0;
        byte[] tmp = new byte[3];
        int index = 0, bitindex = 1;

        //将8字节变成1个字节,不足补0
        if (w * h % 8 != 0) {
            bufflen = w * h / 8 + 1;
        } else {
            bufflen = w * h / 8;
        }
        if (bufflen % 4 != 0)//BMP图像数据大小，必须是4的倍数，图像数据大小不是4的倍数时用0填充补足
        {
            bufflen = bufflen + bufflen % 4;
        }

        byte[] buffer = new byte[bufflen];

        for (int i = len - 1; i >= w; i -= w) {
            // DIB文件格式最后一行为第一行，每行按从左到右顺序
            int end = i, start = i - w + 1;
            for (int j = start; j <= end; j++) {

                tmp[0] = (byte) (b[j] >> 0);
                tmp[1] = (byte) (b[j] >> 8);
                tmp[2] = (byte) (b[j] >> 16);

                String hex = "";
                for (int g = 0; g < tmp.length; g++) {
                    String temp = Integer.toHexString(tmp[g] & 0xFF);
                    if (temp.length() == 1) {
                        temp = "0" + temp;
                    }
                    hex = hex + temp;
                }

                if (bitindex > 8) {
                    index += 1;
                    bitindex = 1;
                }

                if (!hex.equals("ffffff")) {
                    buffer[index] = (byte) (buffer[index] | (0x01 << 8 - bitindex));
                }
                bitindex++;
            }
        }

        return buffer;
    }

    public static byte[] bitmap2Bytes(Bitmap bmp) {
        int height = bmp.getHeight();
        int width = bmp.getWidth();
        byte[] data = new byte[width / 8 * height];
        int[] pixels = new int[width];
        int inx = 0;
        for (int i = 0; i < height; i++) {
            bmp.getPixels(pixels, 0, width, 0, i, width, 1);
            int offset = (height - i - 1) * (width / 8);
            inx = 0;
            for (int j = 0; j < width; ) {
                int bcolor = 0x00;

                for (int k = 0; k < 8; k++) {
                    if (j + k < width) {
                        int color = pixels[j + k];
                        if (color <= Color.GRAY) {
                            bcolor |= (1 << (7 - k));
                        }
                    }
                }
                data[offset + inx++] = (byte) (~(bcolor & 0xff));
                j += 8;
            }
        }

        return data;
    }

    public static List<BitmapPiece> bitmapSplit(Bitmap bitmap, int yPieces) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        List<BitmapPiece> bitmapPieces = new ArrayList<>();
        int ySize = height / yPieces;
        for (int i = 0; i < yPieces; i++) {
            BitmapPiece bitmapPiece = new BitmapPiece();
            bitmapPiece.bitmap = Bitmap.createBitmap(bitmap, 0, i * ySize, width, ySize);
            bitmapPiece.index = i;
            bitmapPieces.add(bitmapPiece);
        }
        return bitmapPieces;
    }
}


