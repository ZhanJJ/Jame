package com.example.test.bitmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BitmapSaveActivity extends AppCompatActivity {
    Context mContext;
    Bitmap bitmap;
    SharedPreferences sp;
    String spName = "image";
    public final static String imgKey = "image";
    public String path;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_save);
        ButterKnife.bind(this);
        mContext = this;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zoro);
        sp = getSharedPreferences(spName, MODE_PRIVATE);
        path = mContext.getFilesDir().getAbsolutePath() + "/zoro.jpg";
        Log.i("img path--->", path);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                Long oldTime = System.currentTimeMillis();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                String imgString = Base64.encodeToString(bytes, Base64.DEFAULT);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(imgKey, imgString);
                editor.commit();
                Log.i("time save--->", imgString + "length");
                Log.i("time save--->", System.currentTimeMillis() - oldTime + "millis");
                Toast.makeText(this, System.currentTimeMillis() - oldTime + "millis", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.button2: {
                Long oldTime = System.currentTimeMillis();
                String imgString = sp.getString(imgKey, "");
                byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.i("time get--->", imgString + "length");
                Log.i("time get--->", System.currentTimeMillis() - oldTime + "millis");
                Toast.makeText(this, System.currentTimeMillis() - oldTime + "millis", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.button3: {
                Long oldTime = System.currentTimeMillis();
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    Log.i("time get--->", System.currentTimeMillis() - oldTime + "millis");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.button4: {
                Long oldTime = System.currentTimeMillis();
                File file = new File(path);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Log.i("time get--->", System.currentTimeMillis() - oldTime + "millis");
                }
                break;
            }
        }
    }
}
