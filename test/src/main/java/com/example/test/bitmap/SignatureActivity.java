package com.example.test.bitmap;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.test.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * 签名页面
 */
public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "HXW";
    public final static String SIGNATURE = "signature";

    private Toolbar toolbar;
    private SignatureView signatureView;
    private Button btnCancel;
    private Button btnFix;
    private Button btnConfirm;

    private Bitmap mBitmap; //签名位图
    private ImageView imgSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
        init();
    }

    @Override
    protected void onDestroy() {
//        if (mBitmap != null && !mBitmap.isRecycled()) {
//            mBitmap.recycle();
//        }
        super.onDestroy();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signatureView = (SignatureView) findViewById(R.id.signatureView);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnFix = (Button) findViewById(R.id.btnFix);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel.setOnClickListener(this);
        btnFix.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        imgSign = (ImageView) findViewById(R.id.imgSign);
        imgSign.setOnClickListener(this);
    }

    private void init() {
    }

//    //获取剪切后的位图
//    private Bitmap getClipBitmap(View view, int x, int y, int width, int height) {
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//
//        Bitmap tempBitmap = view.getDrawingCache();
//
//        int offsetX = x - (int) SignatureView.HALF_STROKE_WIDTH;    //修正x坐标
//        int offsetY = y - (int) SignatureView.HALF_STROKE_WIDTH;    //修正y坐标
//        int offsetW = width + (int) SignatureView.STROKE_WIDTH;     //修正宽度
//        int offsetH = height + (int) SignatureView.STROKE_WIDTH;    //修正高度
//
//        offsetX = offsetX > 0 ? offsetX : x;
//        offsetY = offsetY > 0 ? offsetY : y;
//        offsetW = offsetW < tempBitmap.getWidth() - offsetX ? offsetW : tempBitmap.getWidth() - offsetX;
//        offsetH = offsetH < tempBitmap.getHeight() - offsetY ? offsetH : tempBitmap.getHeight() - offsetY;
//
//        Bitmap bitmap = Bitmap.createBitmap(tempBitmap, offsetX, offsetY, offsetW, offsetH);
//
//        view.setDrawingCacheEnabled(false);
//        if (!tempBitmap.isRecycled()) {
//            tempBitmap.recycle();
//        }
//
//        return bitmap;
//    }

    private Bitmap getClipBitmap(View view, int x, int y, int width, int height) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnFix:
                signatureView.clean();
                break;
            case R.id.btnConfirm:
                RectF rect = signatureView.getRect();
                mBitmap = getClipBitmap(signatureView,
                        (int) rect.left,
                        (int) rect.top,
                        (int) (rect.right - rect.left),
                        (int) (rect.bottom - rect.top));

                imgSign.setImageBitmap(mBitmap);

//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                Log.i("Bitmap byte", baos.toByteArray().length + "bytes");

                SaveBitmapTask saveBitmapTask = new SaveBitmapTask();
                saveBitmapTask.execute(mBitmap);

                break;
            default:
                break;
        }
    }

    //新线程保存图片
    class SaveBitmapTask extends AsyncTask<Bitmap, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Bitmap... params) {
            if (params[0] == null) {
                return false;
            }
            String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            BitmapUtil.saveBitmap(params[0], "SZZT", SIGNATURE + ".jpg");

            BitmapUtil.format2BMP(BitmapUtil.zoomImage(params[0],128,64), storagePath+"/AT/", "new_sign.bmp");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
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
