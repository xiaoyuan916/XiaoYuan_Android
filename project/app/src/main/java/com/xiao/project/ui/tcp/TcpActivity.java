package com.xiao.project.ui.tcp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiao.project.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);

        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(new TcpClient("127.0.0.1",1234));
    }
}
