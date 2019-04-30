package com.xxyuan.service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.xxyuan.service.tcpserver.TcpServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private TcpServer tcpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        ExecutorService exec = Executors.newCachedThreadPool();
        Log.i("A", "onClick: 开始");
        tcpServer = new TcpServer(1234);
        exec.execute(tcpServer);
    }
}
