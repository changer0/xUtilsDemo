package com.lulu.xutilsdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;



public class DownloadActivity extends AppCompatActivity {

    @ViewInject(R.id.download_progress_bar)
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        x.view().inject(this);



        // Android动态申请权限
        // 1. 检查是否已经有了权限
        int p = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        if (p == PackageManager.PERMISSION_DENIED) {
            //如果权限是拒绝的, 那么申请这个权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );
        } else {
            startDownload();
            startDownload();
        }








    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    int p = grantResults[i];
                    if (p == PackageManager.PERMISSION_GRANTED) {
                        startDownload();
                    }
                }
            }
        }
    }

    private void startDownload() {
        RequestParams requestParams = new RequestParams("http://10.0.153.80:8080/umeng-share-sdk.zip");
        //文件下载
        requestParams.setAutoResume(true);
        //下载文件, 如果文件已经存在, 那么自动重命名
        requestParams.setAutoRename(true);
        //文件保存在哪儿?
        String state = Environment.getExternalStorageState();
        File saveDir = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            saveDir = Environment.getExternalStorageDirectory();
        } else {
            saveDir = getFilesDir();
        }
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File target = new File(saveDir, "abc.zip");
        //保存
        requestParams.setSaveFilePath(target.getAbsolutePath());
        //对于文件下载, 结果就是一个File

        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                Snackbar.make(mProgressBar, "开始下载", Snackbar.LENGTH_INDEFINITE).show();

            }
            @Override
            public void onStarted() {
                // TODO: 2016/10/21 开始下载
            }

            /**
             * 文件下载或者上传的进度回调
             * @param total
             * @param current
             * @param isDownloading true代表文件下载, 否则上传
             */
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // TODO: 2016/10/21 下载中
                mProgressBar.setMax((int) total);
                mProgressBar.setProgress((int) current);
            }
            @Override
            public void onSuccess(File result) {

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Snackbar.make(mProgressBar, "下载完成", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }
}
