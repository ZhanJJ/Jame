package com.example.test.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.test.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BitmapSplitActivity extends AppCompatActivity {

    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img3)
    ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_split);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_stub.jpg");
                    //decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap，从而节省了java层的空间
                    Bitmap stub = BitmapFactory.decodeStream(fis);
                    List<BitmapPiece> pieceList = BitmapUtil.bitmapSplit(stub, 4);
                    for (int i = 0; i < pieceList.size(); i++) {
                        if (i == 0) {
                            img.setImageBitmap(pieceList.get(0).bitmap);
                        }
                        if (i == 1) {
                            img1.setImageBitmap(pieceList.get(1).bitmap);
                        }
                        if (i == 2) {
                            img2.setImageBitmap(pieceList.get(2).bitmap);
                        }
                        if (i == 3) {
                            img3.setImageBitmap(pieceList.get(3).bitmap);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
