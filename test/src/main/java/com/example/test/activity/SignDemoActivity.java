package com.example.test.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.test.util.BmpUtil;
import com.example.test.R;
import com.example.test.bitmap.SignatureView;

import static com.example.test.util.BmpUtil.saveBitmap;

public class SignDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    private SignatureView signatureView;
    private Button btnCancel;
    private Button btnFix;
    private Button btnConfirm;
    private ImageView imgSign;

    private Bitmap mBitmap; //签名位图
    private Bitmap mBitmapAll; //签名位图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_demo);
        this.mContext = this;
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null && !mBitmap.isRecycled()) { //回收bitmap资源
            mBitmap.recycle();
        }
        if (mBitmapAll != null && !mBitmapAll.isRecycled()) { //回收bitmap资源
            mBitmapAll.recycle();
        }
    }

    private void initView() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                mBitmapAll = BmpUtil.getBitmapFromView(signatureView);
                imgSign.setImageBitmap(mBitmapAll);

                //新线程保存图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = mContext.getFilesDir().getAbsolutePath();
                        String name = "sign.jpg";
                        saveBitmap(mBitmapAll, path + "/" + name);
                    }
                }).start();
                break;
            case R.id.btnFix:
                signatureView.clean();
                break;
            case R.id.btnConfirm:
                RectF rect = signatureView.getRect();
                mBitmap = BmpUtil.getClipBitmap(signatureView,
                        (int) rect.left,
                        (int) rect.top,
                        (int) (rect.right - rect.left),
                        (int) (rect.bottom - rect.top));

                imgSign.setImageBitmap(mBitmap);

                //新线程保存图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = mContext.getFilesDir().getAbsolutePath();
                        String name = "sign.jpg";
                        saveBitmap(mBitmap, path + "/" + name);
                    }
                }).start();
                break;
        }
    }
}
