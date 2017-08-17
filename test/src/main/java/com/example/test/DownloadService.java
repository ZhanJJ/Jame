package com.example.test;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

public class DownloadService extends Service {
    private String strDownload = ""; //下载链接字符串
    private Long downloadId; //执行下载时返回downloadId，可用于后面查询下载信息
    private String mimeType = "application/vnd.android.package-archive"; //下载文件的mineType，文件类型，具体对应apk类型
    private BroadcastReceiver mReceiver; //用以接收下载完成后的广播
    private SharedPreferences mSharedPreferences;

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (mSharedPreferences.getLong("enqueueId", 0) != intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)) {
                    return;
                }
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/YiQu.apk")),mimeType); //自动安装一个apk应用
                startActivity(intent);
            }
        };
        strDownload = intent.getStringExtra("strDownload");
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload(strDownload);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void startDownload(String strDownload) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(strDownload));
        request.setTitle("益趣生活") //设置下载中通知栏提示的标题
                .setDescription("益趣生活更新") //设置下载中通知栏提示的介绍
                .setMimeType(mimeType) //设置下载文件的mineType
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "YiQu.apk"); //设置文件存放位置和文件名
        downloadId = downloadManager.enqueue(request); //执行下载，返回downloadId，可用于下载完成后对比

        mSharedPreferences = getSharedPreferences("YiQu", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong("enqueueId", downloadId);
        editor.commit();
    }

}
