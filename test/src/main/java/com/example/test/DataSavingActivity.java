package com.example.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataSavingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_saving);

        Context mContext = DataSavingActivity.this;
        mContext.getFilesDir(); //返回一个File，代表了我们app的internal目录。
        mContext.getCacheDir(); //getCacheDir() : 返回一个File，代表了我们app的internal缓存目录。请确保这个目录下的文件能够在一旦不再需要的时候马上被删除，并对其大小进行合理限制，例如1MB 。系统的内部存储空间不够时，会自行选择删除缓存文件。

        //在internal目录下创造一个文件
        File file = new File(mContext.getFilesDir(), "fileName");

        //也可以执行openFileOutput() 获取一个 FileOutputStream 用于写文件到internal目录
        try {
            FileOutputStream fos = openFileOutput("fileName", Context.MODE_PRIVATE);
            fos.write("Hello World".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getTempFile(mContext);
    }

    //需要缓存一些文件，可以使用createTempFile()
    private File getTempFile(Context mContext) {
        File fileTemp = null;
        try {
            fileTemp = File.createTempFile("prefix", "suffix", mContext.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return fileTemp;
    }
}
