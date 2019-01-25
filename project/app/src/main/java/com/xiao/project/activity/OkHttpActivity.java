package com.xiao.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OkHttpActivity extends AppCompatActivity
        implements OkHttpView {

    @BindView(R.id.bt_post)
    Button btPost;
    @BindView(R.id.tv_text)
    TextView tvText;


    private OkHttpPresenter mOkHttpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
        mOkHttpPresenter = new OkHttpPresenter(this);
    }


    @OnClick({R.id.bt_post})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_post:
                mOkHttpPresenter.obtainData();
                break;
        }
    }

    @Override
    public void logPrint(String str) {
        tvText.setText(str);
    }
}
