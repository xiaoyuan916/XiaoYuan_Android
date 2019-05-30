package com.xiao.project.ui.okdownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiao.project.R;

public class OkDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_download);
        initDownFile();
    }

    private void initDownFile() {
//        final DownloadTask[] tasks = new DownloadTask[2];
//        tasks[0] = new DownloadTask.Builder("url1", "path", "filename1").build();
//        tasks[1] = new DownloadTask.Builder("url2", "path", "filename1").build();
//        DownloadTask.enqueue(tasks, new OkDownloadListener());
    }
}
