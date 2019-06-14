package com.xiao.serialport.utils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.xiao.serialport.constants.Constants;
import com.xiao.serialport.serialportapi.SerialComm2;
import com.xiao.serialport.serialportapi.frames.BusInfoFrame;
import com.xiao.serialport.serialportapi.frames.DriverInfoFrame;
import com.xiao.serialport.serialportapi.frames.InfoPushFrame;
import com.xiao.serialport.serialportapi.frames.LineInfoCheckFrame;
import com.xiao.serialport.serialportapi.frames.LineInfoCheckResponseFrame;
import com.xiao.serialport.serialportapi.frames.LineInfoPushFrame;
import com.xiao.serialport.serialportapi.frames.LineSyncFrame;
import com.xiao.serialport.serialportapi.frames.StopFrame;
import com.xiao.serialport.serialportapi.frames.TimeSyncFrame;

import java.util.Arrays;
import java.util.Random;

public class ProcessMessageUtils {
    /**
     * 屏线路信息查询----节站屏线路信息查询包
     * @param message
     */
    public static void lineinfocheck(byte[] message) {
        LineInfoCheckFrame lineInfoCheckFrame = LineInfoCheckFrame
                .processMessgae(message);

        String framePos = lineInfoCheckFrame.getAddress().getAddressValue();

        // 丢弃掉重复包。
        if (lineInfoCheckFrame.equals(Constants.lineInfoCheckFrame)) {
            LogUtils.d("MSG    丢弃掉重复包--节站屏线路信息查询包");
            return;
        }
        Constants.lineInfoCheckFrame = lineInfoCheckFrame;

        handleLineInfoCheckResponseFrame(lineInfoCheckFrame);

        LogUtils.d("MSG    ----节站屏线路信息查询包==设备地址："
                + lineInfoCheckFrame.getAddress().getAddressValue()
                + "  线路名称：" + lineInfoCheckFrame.getLineName());
    }

    /**
     * 应答查询信息
     * @param lineInfoCheckFrame
     */
    private static void handleLineInfoCheckResponseFrame(LineInfoCheckFrame
                                                                 lineInfoCheckFrame) {
        // -------------
        // 接收到的 节站屏线路信息查询包 要查询的 线路名称。
        String checkLineName = lineInfoCheckFrame.getLineName();
        // 添加延时处理机制,产生一个0~49的随机数，以50ms为基准，随机数
        Random rand = new Random();
        int randNum = rand.nextInt(50);
        Log.d("MessageObserver", "延时" + randNum * 50 + "ms");
        try {
            Thread.sleep(randNum * 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.d("MessageObserver", "延时完毕开始处理");
        if (Constants.isAllStations
                && Constants.upList != null
                && Constants.downList != null
                && Constants.upList.size() > 0
                && Constants.downList.size() > 0
                && checkLineName.equals(Constants.upList.get(0)
                .getLineName())
                && checkLineName.equals(Constants.downList.get(0)
                .getLineName())) {
            // 表示曾经接收到过 某条线路上的所有（上下行）站点数据，并且还是当前要查询的线路（最后一个判断条件）。
            // 即使版本不一致也没关系。
            LogUtils.d("MSG    有（是要查询的）");

            sendLineInfoCheckResponseFrame(lineInfoCheckFrame,
                    Constants.upList.get(0).getLineName(),
                    Constants.upList.get(0).getLienNameVersion());

        }else {
            //第一次查询信息
            sendLineInfoCheckResponseFrame(lineInfoCheckFrame, "",
                    0);
        }
    }
    /**
     * 组拼 节站屏线路信息应答包 并发送给车载机。
     *
     * @param lineInfoCheckFrame
     *            接收到的 节站屏线路信息查询包 。
     * @param lineName
     *            要应答的线路名称。
     * @param lienNameVersion
     *            要应答的线路名称版本。
     */
    private static void sendLineInfoCheckResponseFrame(
            LineInfoCheckFrame lineInfoCheckFrame, String lineName,
            int lienNameVersion) {
        // 组拼好的 节站屏线路信息应答包 .
        LineInfoCheckResponseFrame lineInfoCheckResponseFrame = new LineInfoCheckResponseFrame();
        lineInfoCheckResponseFrame.setAddress(lineInfoCheckFrame.getAddress());
        lineInfoCheckResponseFrame.setOrder(lineInfoCheckFrame.getOrder());
        lineInfoCheckResponseFrame.setFrameFlag(lineInfoCheckFrame.getFrameFlag());
        lineInfoCheckResponseFrame.setSelfStatus(0);
        lineInfoCheckResponseFrame.setLineName(lineName);
        lineInfoCheckResponseFrame.setLienNameVersion(lienNameVersion);

        String strResFrame = lineInfoCheckResponseFrame.toHEXString();
        LogUtils.d("MSG    send 节站屏线路信息应答包----"
                + lineInfoCheckResponseFrame.toString());
        SerialComm2 comm = SerialComm2.getInstance();
        comm.send(strResFrame);
    }

    /**
     * 线路站点信息广播，N帧，N=站点数量,无应答
     * @param message
     */
    public static void linestationsdownload(byte[] message) {
        LineInfoPushFrame lineInfoPushFrame = LineInfoPushFrame
                .processMessgae(message);

        LogUtils.d("MSG    线路名/版本号：" + lineInfoPushFrame.getLineName()
                + " / " + lineInfoPushFrame.getLienNameVersion() + "  方向："
                + lineInfoPushFrame.getDirection() + "  站点数："
                + lineInfoPushFrame.getStationCount() + "  当前站号/当前站名："
                + lineInfoPushFrame.getStationNo() + " / "
                + lineInfoPushFrame.getStationName());
    }

    /**
     *  车辆信息包
     * @param message
     */
    public static void businfo(byte[] message) {
        BusInfoFrame busInfoFrame = BusInfoFrame.processMessgae(message);
        // 丢弃掉重复包。
        if (busInfoFrame.equals(Constants.busInfoFrame)) {
            LogUtils.d("MSG    丢弃掉重复包--车辆信息包");
            return;
        }
        Constants.busInfoFrame = busInfoFrame;
        LogUtils.d("MSG    ----节站屏车辆信息包==" + busInfoFrame.toString());
    }

    /**
     * 司机信息同步包
     * @param message
     */
    public static void driverinfo(byte[] message) {
        // 车辆信息包。
        DriverInfoFrame driverInfoFrame = DriverInfoFrame
                .processMessgae(message);
        // 丢弃掉重复包。
        if (Arrays.equals(message, Constants.driverInfoFrameMessage)) {
            LogUtils.d("MSG    丢弃掉重复包--司机信息同步包");
            return;
        }
        Constants.driverInfoFrameMessage = message;
        LogUtils.d("MSG    ----节站屏车辆信息包==" + driverInfoFrame.toString());
    }

    /**
     * 时间校时
     * @param message
     */
    public static void timesync(byte[] message) {
        TimeSyncFrame timeSyncFrame = TimeSyncFrame.processMessgae(message);
        if (timeSyncFrame.getTime() != null
                && !timeSyncFrame.getTime().equals("")) {
            LogUtils.d("MSG    ----节站屏校时包=="
                    + timeSyncFrame.getTime().toGMTString());
        }
    }

    /**
     * 到站信息
     * @param message
     */
    public static void stationinfo(byte[] message) {
        StopFrame stopFrame = StopFrame.processMessgae(message);
        // 收到当前站为0的错误数据，丢弃处理
        if (stopFrame.getCurrStation() == 0) {
            return;
        }
        // 丢弃掉重复包。
        if (stopFrame.equals(Constants.stopFrame)) {
            Log.i("MSG", "丢弃掉重复包--STATIONINFO--");
        }

    }

    /**
     * 信息下载应答帧
     * @param message
     */
    public static void infodownload(byte[] message) {
        InfoPushFrame infoPushFrame = InfoPushFrame.processMessgae(message);
        // String framepos = infoPushFrame.getAddress().getAddressValue();
        if (infoPushFrame.getAddress() == null) {
            // 当前节站屏的地址和 节站屏线路信息查询包 中的地址不相同。
            // 丢弃该 节站屏线路信息查询包 。
            LogUtils.d("MSG    地址不匹配，丢弃！--节站屏线路信息查询包");
            return;
        }

    }

    /**
     * 线路广播，起步的广播
     * @param message
     */
    public static void linesync(byte[] message) {
        LineSyncFrame lineSyncFrame = LineSyncFrame.processMessgae(message);
        // 丢弃掉重复包。
        if (lineSyncFrame != null && Constants.lineSyncFrame != null
                && lineSyncFrame.equals(Constants.lineSyncFrame)) {
            LogUtils.d("MSG    丢弃掉重复包--节站屏线路同步包");
            return;
        }

        Constants.lineSyncFrame = lineSyncFrame;
        LogUtils.d("MSG    ----节站屏线路同步包==" + lineSyncFrame.toString());
    }
}
