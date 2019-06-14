package com.xiao.serialport.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.xiao.serialport.observer.SerialPortObserver;
import com.xiao.serialport.serialportapi.SerialComm2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;

public class SerialPortService extends Service {
    /**
     * 串口信息存储队列
     */
    public static BlockingQueue<Byte> bq = new LinkedBlockingQueue<Byte>();

    public SerialPortService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("串口服务开始：startId" + startId);
        //初始化串口
        initSerialPort();
        //初始化rxjava处理信息
        initRxJava();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化通知者
     */
    public static ObservableEmitter<byte[]> emitter2;

    /**
     * 初始化rxjava处理信息
     */
    private void initRxJava() {
        //创建一个上游 Observable：
        Observable<byte[]> observable = Observable.create(new ObservableOnSubscribe<byte[]>() {

            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                emitter2 = emitter;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new SerialPortObserver());
    }

    /**
     * 初始化串口
     */
    private void initSerialPort() {
        SerialComm2 comm = SerialComm2.getInstance();
        comm.open();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
