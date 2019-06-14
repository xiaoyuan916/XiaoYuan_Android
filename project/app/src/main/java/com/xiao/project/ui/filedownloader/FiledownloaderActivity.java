package com.xiao.project.ui.filedownloader;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.xiao.project.R;
import com.xiao.project.conf.Constant;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiledownloaderActivity extends AppCompatActivity {

    private static final String TAG = "FiledownloaderActivity";
    @BindView(R.id.start_btn_1)
    Button startBtn1;
    @BindView(R.id.pause_btn_1)
    Button pauseBtn1;
    @BindView(R.id.delete_btn_1)
    Button deleteBtn1;
//    @BindView(R.id.filename_tv_1)
//    TextView filenameTv1;
//    @BindView(R.id.speed_tv_1)
//    TextView speedTv1;
//    @BindView(R.id.progressBar_1)
//    ProgressBar progressBar1;

    private String llsApkFilePath;
    private int baseDownloadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedownloader);
        ButterKnife.bind(this);

        llsApkFilePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/down";

    }

    @OnClick({R.id.start_btn_1,R.id.pause_btn_1,R.id.delete_btn_1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_1:
                downFile();
                break;
            case R.id.pause_btn_1:
                FileDownloader.getImpl().pause(baseDownloadId);
                break;
            case R.id.delete_btn_1:
//                new File(llsApkFilePath).delete();
                break;
        }
    }

    private void downFile() {
        baseDownloadId = FileDownloader.getImpl()
                .create(Constant.THEME_URL)
                .setPath(llsApkFilePath, true)
                .setListener(new SingleFileDownloadListener()).start();
    }
}
