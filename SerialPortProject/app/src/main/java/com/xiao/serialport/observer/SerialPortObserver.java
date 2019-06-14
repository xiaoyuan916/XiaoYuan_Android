package com.xiao.serialport.observer;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.xiao.serialport.serialportapi.bean.Order;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.frames.LineInfoPushFrame;
import com.xiao.serialport.serialportapi.frames.TimeSyncFrame;
import com.xiao.serialport.utils.ProcessMessageUtils;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SerialPortObserver implements Observer<byte[]> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(byte[] bytes) {
        try {
            processMessage(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void processMessage(byte[] message) {
        // Log.i("MSG", "MSG  接收到数据帧");
        // 根据命令生成不同的消息对象
        byte b = message[5];
        String ord = Utils.byteToHex(b);
        Order order = Order.getOrder(ord);
        if (order == null) {
            return;
        }
        // System.out.print("order:" + order);

        // 判断MainActivity是否处于前台。
        switch (order) {
            case STATIONINFO:
                // 到站广播，无应答
                LogUtils.d("MSG    到站广播----节站屏线路信息帧");
                ProcessMessageUtils.stationinfo(message);
                break;
            case INFODOWNLOAD:
                // 信息下载，既可以对单屏发送，也可以广播发送。
                // 信息下载应答帧（InfoPushResponseFrame）应不应答都可以！
                LogUtils.d("MSG    ----节站屏信息下载帧");
                ProcessMessageUtils.infodownload(message);
                break;
            case LINESTATIONSDOWNLOAD:
                // 线路站点信息广播，N帧，N=站点数量,无应答
                LogUtils.d("MSG    ----节站屏线路站名列表下载帧");
                ProcessMessageUtils.linestationsdownload(message);
                break;
            case LINEINFOCHECK:
                // 屏线路信息查询，对单屏发送，需应答LineInfoCheckResponse
                LogUtils.d("MSG    屏线路信息查询----节站屏线路信息查询包");
                ProcessMessageUtils.lineinfocheck(message);
                break;
            case LINESYNC:
                // 线路广播,无需应答
                LogUtils.d("MSG    ----节站屏线路同步包");
                ProcessMessageUtils.linesync(message);
                break;
            case TIMESYNC:
                // 时间同步广播，无需应答
                LogUtils.d("MSG    ----节站屏校时包");
                ProcessMessageUtils.timesync(message);
                break;
            case BUSINFO:
                // 车辆信息包。
                LogUtils.d("MSG    节站屏车辆信息包----");
                ProcessMessageUtils.businfo(message);
                break;
            case DRIVERINFO:
                LogUtils.d("MSG    司机信息同步包----");
                ProcessMessageUtils.driverinfo(message);
                break;
        }
    }
}
