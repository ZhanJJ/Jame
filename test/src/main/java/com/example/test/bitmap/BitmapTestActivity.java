package com.example.test.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.util.FileUtil;
import com.example.test.R;
import com.example.test.network.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.BitmapFactory.decodeResource;

public class BitmapTestActivity extends AppCompatActivity {
    public static String TAG = "Bitmap";
    Context mContext;

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.imgZoom)
    ImageView imgZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);
        ButterKnife.bind(this);
        mContext = this;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

//        img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_fetch_button));
//        img.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.ic_fetch_button, 100, 100));
    }

    @OnClick({R.id.img, R.id.imgZoom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img:
                if (NetWorkUtil.isNetworkAvailable(this)) {
                    Toast.makeText(this, "网络连接正常", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgZoom:
                final Bitmap bitmap = decodeResource(getResources(), R.drawable.szzt_sign_test);
                new Thread() {
                    @Override
                    public void run() {
//                        String path = BitmapUtil.saveBitmap(mContext, BitmapUtil.convertToBlackWhite(bitmap), "SZZT", "AAA.jpg");
//                        String path = BitmapUtil.saveBitmap(mContext, BitmapUtil.zoomImage(bitmap,128,64), "SZZT", "山治.jpg");
//                        Log.i(TAG, path);

//                        if (file.exists()) {
//                            try {
//                                FileInputStream fileInputStream = new FileInputStream(file);
//                                byte[] bytes = new byte[fileInputStream.available()];
//                                fileInputStream.read(bytes);
//                                fileInputStream.close();
//                                Log.i(TAG + "bytes--->", bytes.length + "byte");
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
                        String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AZZT" + "/";
//                        BitmapUtil.format2BMP(BitmapUtil.zoomImage(bitmap, 128, 64), path, "signature.bmp");

                        String signName = "szzt_sign_test.bmp";
                        try {
                            FileUtil.copyFile(getAssets().open(signName), storagePath + signName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File file = new File(storagePath + signName);
                        if (file.exists()) {
                            Log.i("Bitmap-->", file.getName());
                        }
                        BitmapUtil.format2BMP(BitmapUtil.zoomImage(bitmap,128,64), storagePath, "sign428.bmp");

                        super.run();
                    }
                }.start();
                break;
        }
    }
}
