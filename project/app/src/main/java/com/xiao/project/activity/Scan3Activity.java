//package com.xiao.project.activity;
//
//import android.util.Log;
//import android.view.SurfaceView;
//
//import com.xiao.project.R;
//
//import wangfei.scan2.Scan2Activity;
//import wangfei.scan2.client.android.AutoScannerView;
//
//public class Scan3Activity  extends Scan2Activity {
//
//    @Override
//    protected void initView() {
//        setContentView(R.layout.activity_scan3);
//    }
//
//    @Override
//    protected AutoScannerView getAutoScannerView() {
//        return (AutoScannerView) findViewById(R.id.autoScanner);
//    }
//
//    @Override
//    public SurfaceView getSurfaceView() {
//        return (SurfaceView) findViewById(R.id.surfaceView);
//    }
//
//    @Override
//    public void handleResult(String text) {
//        Log.d(getClass().getSimpleName(),"码值为"+text);
//        reScan();
//    }
//}
