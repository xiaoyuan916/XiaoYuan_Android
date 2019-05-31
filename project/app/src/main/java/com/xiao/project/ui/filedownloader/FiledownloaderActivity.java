package com.xiao.project.ui.filedownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiao.project.R;

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
    @BindView(R.id.filename_tv_1)
    TextView filenameTv1;
    @BindView(R.id.speed_tv_1)
    TextView speedTv1;
    @BindView(R.id.progressBar_1)
    ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedownloader);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start_btn_1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_1:
                Log.d(TAG, "onClick: "+"点击");
                break;
        }
    }
}
