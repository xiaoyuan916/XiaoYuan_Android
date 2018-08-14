package com.sgcc.pda.hardware.frame.tiaoshicang;

import android.content.Context;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.ByteUtils;
import com.sgcc.pda.hardware.util.DateUtil;
import com.sgcc.pda.hardware.util.StringUtil;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 载波模块测试组帧
 * 发送准备，关闭测试，
 * 集中器整机测试，采集器整机测试，电能表整机测试
 * 模块测试（集中器，采集器，电能表）
 * SIM卡测试
 *
 * @Author: GuangJie-Wang
 * @Date: 2016/9/22
 * @Time: 18:30
 */
public class WaiSheFrame {
    private static final String TAG = "WaiSheFrame";

    //选择模块类型
    public static final int MK_TYPE_09 = 0;//09版载波模块
    public static final int MK_TYPE_13 = 1;//13版载波模块
    //选择电能表规约
    public static final int DLT645_2007 = 0;//07规约电能表
    public static final int DLT645_1997 = 1;//97规约电能表

    /**
     * 发送准备命令
     *
     * @return 准备命令帧
     */
    public static Map<String, byte[]> sendPerpare(int mkType) {
        Map<String, byte[]> param = new HashMap<>();
        byte[] perpare = new byte[]{(byte) 0xC9,
                (byte) 0x02, (byte) 0x08, (byte) 0x03, (byte) 0x00,
                (byte) 0xC9, (byte) 0x03, (byte) 0x01, (byte) 0x01, (byte) 0xA4,
                (byte) 0x9C};
        // 选择支持模块
        switch (mkType) {
            case 0://09模块
                perpare[8] = 0x01;
                perpare[9] = (byte) 0xA4;
                break;
            case 1://13模块
                perpare[8] = 0x02;
                perpare[9] = (byte) 0xA5;
                break;
            default:
                break;
        }
        param.put(System.currentTimeMillis() + "", perpare);
        LogUtil.i(TAG, "sendPerpare: " + ByteUtils.byteToHexString(perpare));
        return param;
    }

    /**
     * 关闭测试
     *
     * @return 发送关闭
     */
    public static Map<String, byte[]> sendClose() {
        Map<String, byte[]> param = new HashMap<>();
        byte[] close = new byte[]{(byte) 0xC9, (byte) 0x02,
                (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xC9,
                (byte) 0x04, (byte) 0x99, (byte) 0x9C};
        param.put(System.currentTimeMillis() + "", close);
        LogUtil.i(TAG, "sendClose: " + ByteUtils.byteToHexString(close));
        return param;
    }

    /**
     * 集中器整机测试
     *
     * @param gyType  规约类型
     * @param meterNo 电表通讯地址
     * @return 集中器整机测试帧
     */
    public static Map<String, byte[]> jzqCheck(int gyType, String meterNo) {
        byte[] buffer = null;
        Map<String, byte[]> param = new HashMap<>();
        byte[] bjdzByte = BcdUtils.reverseBytes(BcdUtils
                .stringToByteArray(meterNo));
        String date = DateUtil.getDate();
        byte[] datadate = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(date));
        LogUtil.i(TAG, "datadate: " + BcdUtils.binArrayToString(datadate));
        switch (gyType) {
            case 0://07规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b,
                        (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0xc9,
                        (byte) 0x01, (byte) 0x01, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 19, datadate.length);
                break;
            case 1://97规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b, (byte) 0x0c,
                        (byte) 0x0f, (byte) 0x00, (byte) 0xc9, (byte) 0x01,
                        (byte) 0x01, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0x01,
                        (byte) 0x10, (byte) 0xc0, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 17, datadate.length);
                break;
        }

        if (buffer != null) {
            System.arraycopy(bjdzByte, 0, buffer, 8, bjdzByte.length);
            byte checkCode = ByteUtils.getCheckCode(buffer, 0, buffer.length - 2);
            System.arraycopy(new byte[]{checkCode}, 0, buffer, buffer.length - 2, 1);
            LogUtil.i(TAG, "jzqCheck: " + BcdUtils.binArrayToString(buffer));
            param.put(System.currentTimeMillis() + "", buffer);
        }
        return param;

    }

    /**
     * 采集器整机测试
     *
     * @param gyType  规约类型
     * @param meterNo 电能表通讯地址
     * @return 采集器整机测试帧
     */
    public static Map<String, byte[]> cjqCheck(int gyType, String meterNo) {
        byte[] buffer = null;
        Map<String, byte[]> param = new HashMap<>();
        byte[] bjdzByte = BcdUtils.reverseBytes(BcdUtils
                .stringToByteArray(meterNo));
        String date = DateUtil.getDate();
        byte[] datadate = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(date));
        LogUtil.i(TAG, "datadate: " + BcdUtils.binArrayToString(datadate));
        switch (gyType) {
            case 0://07规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b,
                        (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0xc9,
                        (byte) 0x02, (byte) 0x01, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 19, datadate.length);
                break;
            case 1://97规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b, (byte) 0x0c,
                        (byte) 0x0f, (byte) 0x00, (byte) 0xc9, (byte) 0x02,
                        (byte) 0x01, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0x01,
                        (byte) 0x10, (byte) 0xc0, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 17, datadate.length);
                break;
        }
        if (buffer != null) {
            System.arraycopy(bjdzByte, 0, buffer, 8, bjdzByte.length);
            byte checkCode = ByteUtils.getCheckCode(buffer, 0, buffer.length - 2);
            System.arraycopy(new byte[]{checkCode}, 0, buffer, buffer.length - 2, 1);
            LogUtil.i(TAG, "cjqCheck: " + BcdUtils.binArrayToString(buffer));
            param.put(System.currentTimeMillis() + "", buffer);

        }
        return param;
    }

    /**
     * 电能表整机测试
     *
     * @param gyType  规约类型
     * @param meterNo 电能表通讯地址
     * @return 电能表整机测试帧
     */
    public static Map<String, byte[]> meterCheck(int gyType, String meterNo) {
        byte[] buffer = null;
        Map<String, byte[]> param = new HashMap<>();
        byte[] bjdzByte = BcdUtils.reverseBytes(BcdUtils
                .stringToByteArray(meterNo));
        String date = DateUtil.getDate();
        byte[] datadate = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(date));
        LogUtil.i(TAG, "datadate: " + BcdUtils.binArrayToString(datadate));
        switch (gyType) {
            case 0://07规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b,
                        (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0xc9,
                        (byte) 0x02, (byte) 0x01, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 19, datadate.length);
                break;
            case 1://97规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b, (byte) 0x0c,
                        (byte) 0x0f, (byte) 0x00, (byte) 0xc9, (byte) 0x02,
                        (byte) 0x01, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0x01,
                        (byte) 0x10, (byte) 0xc0, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 17, datadate.length);
                break;
        }
        if (buffer != null) {
            System.arraycopy(bjdzByte, 0, buffer, 8, bjdzByte.length);
            byte checkCode = ByteUtils.getCheckCode(buffer, 0, buffer.length - 2);
            System.arraycopy(new byte[]{checkCode}, 0, buffer, buffer.length - 2, 1);
            LogUtil.i(TAG, "meterCheck: " + BcdUtils.binArrayToString(buffer));
            param.put(System.currentTimeMillis() + "", buffer);
        }
        return param;
    }

    /**
     * 模块测试
     *
     * @param mklxType 模块类型（0：集中器，1：采集器 2：电能表）
     * @param gyType   电能表规约
     * @param meterNo  电能表地址
     * @return 模块测试报文
     */
    public static Map<String, byte[]> moduleCheck(int mklxType, int gyType, String meterNo) {
        byte csType = 0x04;
        byte mkType = 0x01;
        byte[] buffer = null;
        Map<String, byte[]> param = new HashMap<>();
        byte[] bjdzByte = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(meterNo));
        String date = DateUtil.getDate();
        byte[] datadate = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(date));
        LogUtil.i(TAG, "datadate: " + BcdUtils.binArrayToString(datadate));

        switch (mklxType) {
            case 0://集中器
                csType = 0x04;
                mkType = 0x02;
                break;
            case 1://采集器
                csType = 0x05;
                mkType = 0x01;
                break;
            case 2://电能表
                csType = 0x05;
                mkType = 0x01;
                break;
        }

        switch (gyType) {
            case 0://07规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b,
                        (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0xc9,
                        (byte) 0x04, (byte) 0x01, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 19, datadate.length);
                break;
            case 1://97规约
                buffer = new byte[]{(byte) 0xc9, (byte) 0x0b, (byte) 0x0c,
                        (byte) 0x0f, (byte) 0x00, (byte) 0xc9, (byte) 0x04,
                        (byte) 0x01, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
                        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0x01,
                        (byte) 0x10, (byte) 0xc0, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x9c};
                System.arraycopy(datadate, 0, buffer, 17, datadate.length);
                break;


        }

        if (buffer != null) {
            System.arraycopy(bjdzByte, 0, buffer, 8, bjdzByte.length);
            System.arraycopy(new byte[]{csType}, 0, buffer, 6, 1);
            System.arraycopy(new byte[]{mkType}, 0, buffer, 7, 1);
            byte checkCode = ByteUtils.getCheckCode(buffer, 0, buffer.length - 2);
            System.arraycopy(new byte[]{checkCode}, 0, buffer, buffer.length - 2, 1);
            LogUtil.i(TAG, "dmkCheck: " + BcdUtils.binArrayToString(buffer));
            param.put(System.currentTimeMillis() + "", buffer);
        }
        return param;
    }

    /**
     * IP 可登录的主站IP地址
     * （四个字节，BCD编码）；
     * LJDZ 逻辑地址
     * （四个字节，BCD编码）；
     * PN 可登录的主站IP地址对应的端口号
     * （两个字节，BIN编码）；
     * APN 网络APN字段
     * （低字节在前高字节在后，16个字节，不足补0）；
     * TU 01表示TCP传输方式；
     * 02表示UDP传输方式；
     * UN 用户名字段
     * （低字节在前高字节在后，16个字节，不足补0）；
     * PW 用户名密码字段
     * （低字节在前高字节在后，16个字节，不足补0）。
     *
     * @param ljdz 终端逻辑地址
     * @param ip   IP地址
     * @param port 端口号
     * @param apn  apn地址
     * @return
     */
    public static byte[] checkSIMParamSet(String ljdz, String ip, String port,
                                          String apn, Context mContext) {
//        Map<String, byte[]> param = new HashMap<>();
        try {
            if (ljdz == null || ljdz.trim().length() != 8) {
                ToastUtils.showToast(mContext, "逻辑地址错误");
                return null;
            }
            boolean ip1 = StringUtil.isIp(ip);
            if (!ip1) {
                ToastUtils.showToast(mContext, "ip地址错误");
                return null;
            }

            int parseInt = Integer.parseInt(port.trim());
            if (!(parseInt > 0 && parseInt < 65536)) {
                ToastUtils.showToast(mContext, "端口号错误");
                return null;
            }

            if (apn == null || apn.trim().length() == 0) {
                ToastUtils.showToast(mContext, "APN错误");
                return null;
            }

            byte[] mess;

            byte[] data01 = BcdUtils.reverseBytes(new byte[]{0x01});
            byte[] LJDZ = BcdUtils.reverseBytes(BcdUtils
                    .stringToByteArray(ljdz));
            String[] p = ip.split("\\.");
            byte[] pArgu = new byte[4];
            for (int i = 0; i < p.length; i++) {
                pArgu[i] = (byte) ((int) (Integer.valueOf(p[i])));
            }
            byte[] IP = BcdUtils.reverseBytes(pArgu);
            LogUtil.i(TAG, "ip=" + BcdUtils.binArrayToString(IP));

            byte[] PN = BcdUtils.reverseBytes(ByteUtils.intToBytes(
                    Integer.valueOf(port), 2));
            LogUtil.i(TAG, "PN=" + BcdUtils.binArrayToString(PN));
            byte[] apnTemp = new byte[16];
            System.arraycopy(apn.getBytes(), 0, apnTemp,
                    apnTemp.length - apn.length(), apn.length());
            byte[] APN = BcdUtils.reverseBytes(apnTemp);
            LogUtil.i(TAG, "APN=" + BcdUtils.binArrayToString(APN));
            byte[] TU = BcdUtils.reverseBytes(new byte[]{0x01});
            byte[] UN = BcdUtils.reverseBytes(new byte[16]);
            byte[] PW = BcdUtils.reverseBytes(new byte[16]);

            LogUtil.i(TAG, "LJDZ=" + BcdUtils.binArrayToString(LJDZ));
            byte[] data = BcdUtils.combBytes(data01, LJDZ, IP, PN, APN, TU, UN,
                    PW);
            mess = packMsg(new byte[]{0x04, 0x01}, data);
            LogUtil.i(BcdUtils.binArrayToString(mess));
            return mess;
        } catch (Exception e) {
            ToastUtils.showToast(mContext, "请检查参数是否合法");
            return null;
        }
    }


    private static byte[] packMsg(byte[] conf, byte[] msg645) {
        int data_filed_len = msg645.length;
        byte[] send_msg = new byte[8 + data_filed_len];
        byte[] filed_len = new byte[2];
        filed_len = ByteUtils.intToByteArray(data_filed_len, filed_len.length);
        send_msg[0] = (byte) 0xC9;
        System.arraycopy(conf, 0, send_msg, 1, conf.length);
        System.arraycopy(filed_len, 0, send_msg, 3, filed_len.length);
        send_msg[5] = (byte) 0xC9;
        System.arraycopy(msg645, 0, send_msg, 6, data_filed_len);
        send_msg[6 + data_filed_len] = ByteUtils.getCheckCode(send_msg,
                6 + data_filed_len);
        send_msg[7 + data_filed_len] = (byte) 0x9C;
        return send_msg;
    }

    /**
     * SIM卡测试
     *
     * @param nn 上行通信模块通道号：01 GPRS 02 CDMA
     * @return sim卡测试报文
     */
    public static Map<String, byte[]> simCheck(int nn) {
        Map<String, byte[]> param = new HashMap<>();
        byte[] simCheck = new byte[]{(byte) 0xc9, (byte) 0x0c,
                (byte) 0x03, (byte) 0x02, (byte) 0x00, (byte) 0xc9,
                (byte) 0x00, (byte) 0x01, (byte) 0xa4, (byte) 0x9c};
        // 选择支持模块
        switch (nn) {
            case 0:
                simCheck[7] = 0x01;
                simCheck[8] = (byte) 0xA4;
                break;
            case 1:
                simCheck[7] = 0x02;
                simCheck[8] = (byte) 0xA5;
                break;
            default:
                break;
        }
        param.put(System.currentTimeMillis() + "", simCheck);
        LogUtil.i(TAG, "sendPerpare: " + ByteUtils.byteToHexString(simCheck));
        return param;
    }
}
