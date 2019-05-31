package com.xiao.project.ui.filedownloader;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.xiao.project.R;
import com.xiao.project.conf.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilesDownLoaderActivity extends AppCompatActivity {

    @BindView(R.id.start_btn_1)
    Button startBtn1;
    @BindView(R.id.pause_btn_1)
    Button pauseBtn1;
    @BindView(R.id.delete_btn_1)
    Button deleteBtn1;

    private String llsApkFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_down_loader);
        ButterKnife.bind(this);

        llsApkFilePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/down";
    }

    @OnClick({R.id.start_btn_1, R.id.pause_btn_1, R.id.delete_btn_1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_1:
                downFiles();
                break;
            case R.id.pause_btn_1:
                FileDownloader.getImpl().pauseAll();
                break;
            case R.id.delete_btn_1:
//                new File(llsApkFilePath).delete();
                break;
        }
    }

    private void downFiles() {
        boolean serial = true;
        ListFileDownloadListener listener = new ListFileDownloadListener();
        FileDownloadQueueSet queueSet = new FileDownloadQueueSet(listener);
        List<BaseDownloadTask> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(FileDownloader.getImpl()
                    .create(Constant.URLS[i]).setTag(i + 1)
                    .setPath(llsApkFilePath, true));
        }

        queueSet.disableCallbackProgressTimes(); // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadListener#progress`, 我们只关系每个任务是否完成, 所以这里这样设置可以很有效的减少ipc.
        // 所有任务在下载失败的时候都自动重试一次
        queueSet.setAutoRetryTimes(1);
//        queueSet.setCallbackProgressTimes(100);
//        queueSet.setCallbackProgressMinInterval(100);

        if (serial) {
            // 串行执行该任务队列
            queueSet.downloadSequentially(tasks);
        } else {
            // 并行执行该任务队列
            queueSet.downloadTogether(tasks);
        }
        // 最后你需要主动调用start方法来启动该Queue
        queueSet.start();
    }
}
