package com.sgcc.pda.hardware.resam.resamMethod;

import android.util.Log;

import com.cepri.dev.RESAM;
import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.resam.makeFrame.XorUtil;
import com.sgcc.pda.hardware.resam.parser.TCHNParser;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.LogUtil;


/**
 * Created by TL on 2017/4/27.
 */

public class TCHNResamMethod {

    private static final int BUFFER_LENGTH = 1508;


    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static String getResamInfo() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0xFF,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0xFF,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }


    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static String getResamTest() {
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x23,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x23,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }

    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static String getResamNo() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x02,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x02,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }


    /**
     * 获取resam 开机外部认证1
     *
     * @return
     */
    public static String getResamOpen1() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x14, (byte) 0x00, (byte) 0x08,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x14, (byte) 0x00, (byte) 0x08,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }

    /**
     * 获取resam 开机外部认证2
     *
     * @return
     */
    public static String getResamOpen2(String rand) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] m = DataConvert.toBytes(rand);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x16, (byte) 0x00, (byte) 0x02,
                (byte) 0x00, (byte) 0x08};

        byte[] newByte = new byte[byteFrame.length + m.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    /**
     * 获取resam 开机外部认证2
     *
     * @return
     */
    public static String getResamOpen2(String no, String rand) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] n = DataConvert.toBytes(no);
        byte[] m = DataConvert.toBytes(rand);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x16, (byte) 0x08, (byte) 0x01,
                (byte) 0x00, (byte) 0x18};

        byte[] newByte = new byte[byteFrame.length + n.length + m.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(n, 0, newByte, byteFrame.length, n.length);  //将数据域拼接到报文数组中
        System.arraycopy(m, 0, newByte, byteFrame.length + n.length, m.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length + 1] = xorByte;

        Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

        String upValue = resamOptionString(up);
        return upValue;
    }

    /**
     * 获取resam 对称密钥版本
     *
     * @return
     */
    public static String getResamSeckeyVer() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }

    /**
     * 获取resam 会话计数器
     *
     * @return
     */
    public static String getResamYasctr() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x08,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x08,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue.substring(0, 8);
    }

    /**
     * 获取resam 终端证书版本号
     *
     * @return
     */
    public static String getResamTnmlVer() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x05,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x05,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue.substring(2, 4);
    }

    /**
     * 获取resam 终端证书序列号
     *
     * @return
     */
    public static String getResamTnmlSn() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x09,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x09,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }

    /**
     * 获取resam 主站证书
     *
     * @return
     */
    public static String getResamMasterCert() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0C,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0C,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }

    /**
     * 获取resam 终端证书
     *
     * @return
     */
    public static String getResamTmnlCert() {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0B,
                (byte) 0x00, (byte) 0x00};
        byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0B,
                (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
        String upValue = resamOption(byteFrame);
        return upValue;
    }


    /**
     * 获取resam 加密
     *
     * @return
     */
    public static String getResamM1S1(String WesamNum, String hhxsjsq) {
        byte[] m1 = DataConvert.toBytes(WesamNum);
        byte[] mac = DataConvert.toBytes(hhxsjsq);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x7C, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x11, (byte) 0x08, (byte) 0x09, (byte) 0x03, (byte) 0x04, (byte) 0x08};

        byte[] newByte = new byte[byteFrame.length + m1.length + mac.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(m1, 0, newByte, byteFrame.length, m1.length);  //将数据域拼接到报文数组中
        System.arraycopy(mac, 0, newByte, byteFrame.length + m1.length, mac.length);  //将数据域拼接到报文数组中


        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    /**
     * 获取resam 加密
     *
     * @return
     */
    public static String ResamMyxs(String WesamNum, String m2, String mac2) {
        byte[] wesamNum = DataConvert.toBytes(WesamNum);
        byte[] mw2 = DataConvert.toBytes(m2);
        byte[] mc2 = DataConvert.toBytes(mac2);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x7E, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x41, (byte) 0x08, (byte) 0x09, (byte) 0x03, (byte) 0x04, (byte) 0x08};

        byte[] newByte = new byte[byteFrame.length + wesamNum.length + mw2.length + mc2.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(wesamNum, 0, newByte, byteFrame.length, wesamNum.length);  //将数据域拼接到报文数组中
        System.arraycopy(mw2, 0, newByte, byteFrame.length + wesamNum.length, mw2.length);  //将数据域拼接到报文数组中
        System.arraycopy(mc2, 0, newByte, byteFrame.length + wesamNum.length + mw2.length, mc2.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOptionString(up);
        return upValue;
    }

    /**
     * 对数据进行安全安全等级为3的加密处理
     *
     * @param busiData 业务数据
     * @return
     */
    public static String ResamJiaMi(String busiData) {
        byte[] data = DataConvert.toBytes(busiData);
        LogUtil.d("TL", "data----------------" + data.length);
        int len = 8 + data.length + 0x0F;
        int len2 = (data.length + 3 + 0x0F) / 16 * 16 + 4;
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x76, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) len,
                (byte) 0x03, (byte) 0x02, (byte) 0x02, (byte) 0x06, (byte) 0x81, (byte) 0x1c, (byte) 0x33, (byte) 0x10
                , (byte) 0x00, (byte) len2};
        byte[] newByte = new byte[byteFrame.length + data.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(data, 0, newByte, byteFrame.length, data.length);  //将数据域拼接到报文数组中


        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

        String upValue = resamOptionString(up);
        return upValue;
    }

    /**
     * 对外设返回安全等级为3的数据解密处理
     *
     * @param busiData 业务数据
     * @return
     */
    public static String ResamJieMi(String busiData) {
        byte[] data = DataConvert.toBytes(busiData);
        int len = 8 + data.length + 0x0F;
        int len2 = (data.length + 3 + 0x0F) / 16 * 16 + 4;
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x76, (byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) len,
                (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x06, (byte) 0x81, (byte) 0x1c, (byte) 0x00, (byte) 0x13
                , (byte) 0x00, (byte) len2};
        byte[] newByte = new byte[byteFrame.length + data.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(data, 0, newByte, byteFrame.length, data.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOptionString(up);
        return upValue;
    }


    /**
     * 获取resam 加密
     *
     * @return
     */
    public static String getResamM2S2(String m1, String s1) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] m = DataConvert.toBytes(m1);
        byte[] s = DataConvert.toBytes(s1);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x12, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x60};

        byte[] newByte = new byte[byteFrame.length + m.length + s.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中
        System.arraycopy(s, 0, newByte, byteFrame.length + m.length, s.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    /**
     * 获取resam 置离线计数器
     *
     * @return
     */
    public static String getResamSetCounter(String m1) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] m = DataConvert.toBytes(m1);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x62, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x10};

        byte[] newByte = new byte[byteFrame.length + m.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOptionString(up);
        return upValue;
    }

    /**
     * 获取resam 转加密初始化
     *
     * @return
     */
    public static String getResamMiInit(String m1) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        byte[] m = DataConvert.toBytes(m1);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x60, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x20};

        byte[] newByte = new byte[byteFrame.length + m.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    /**
     * resam 认证一1
     *
     * @return
     */
    public static String getResamSureOne1(String uid) {
        byte[] data1 = new byte[]{(byte) 0x03, (byte) 0x04};
        byte[] data2 = DataConvert.toBytes(uid);
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x8C, (byte) 0x00, (byte) 0x00};
        byte[] newByte = new byte[byteFrame.length + 2 + data1.length + data2.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        String len = Integer.toHexString(data1.length + data2.length); //帧长度转化为十六进制字符串
        if (len.length() < 4) {
            //帧长度不足4位用零补齐
            len = "0000".substring(0, 4 - len.length()) + len;
        }
        //十六进制字符串转化byte数组  将帧长度拼接到报文数组中    从len的0位开始copy
        System.arraycopy(DataConvert.toBytes(len), 0, newByte, byteFrame.length, 2);
        System.arraycopy(data1, 0, newByte, byteFrame.length + 2, data1.length);  //将数据域拼接到报文数组中
        System.arraycopy(data2, 0, newByte, byteFrame.length + 2 + data1.length, data2.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }


    /**
     * resam 认证一2
     *
     * @return
     */
    public static String getResamSureOne2(String m16, String r4) {
        byte[] data1 = DataConvert.toBytes(m16);
        byte data2 = (byte) 0x00;
        byte[] data3 = DataConvert.toBytes(r4);
        byte[] byteFrame = new byte[]{(byte) 0x94, (byte) 0x6A, (byte) 0xFF, (byte) 0x00};
        byte[] newByte = new byte[byteFrame.length + 2 + data1.length + 1 + data3.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        String len = Integer.toHexString(data1.length + 1 + data3.length); //帧长度转化为十六进制字符串
        if (len.length() < 4) {
            //帧长度不足4位用零补齐
            len = "0000".substring(0, 4 - len.length()) + len;
        }
        //十六进制字符串转化byte数组  将帧长度拼接到报文数组中    从len的0位开始copy
        System.arraycopy(DataConvert.toBytes(len), 0, newByte, byteFrame.length, 2);
        System.arraycopy(data1, 0, newByte, byteFrame.length + 2, data1.length);  //将数据域拼接到报文数组中
        System.arraycopy(data2, 0, newByte, byteFrame.length + 2 + data1.length, 1);  //将数据域拼接到报文数组中
        System.arraycopy(data3, 0, newByte, byteFrame.length + 2 + data1.length + 1, data3.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    /**
     * resam 认证一3
     *
     * @return
     */
    public static String getResamSureOne3(String m16, String t2) {
        byte[] data1 = DataConvert.toBytes(m16);
        byte data2 = (byte) 0x00;
        byte[] data3 = DataConvert.toBytes(t2);
        byte[] byteFrame = new byte[]{(byte) 0x94, (byte) 0x6A, (byte) 0xFF, (byte) 0x01};
        byte[] newByte = new byte[byteFrame.length + 2 + data1.length + 1 + data3.length];
        System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
        String len = Integer.toHexString(data1.length + 1 + data3.length); //帧长度转化为十六进制字符串
        if (len.length() < 4) {
            //帧长度不足4位用零补齐
            len = "0000".substring(0, 4 - len.length()) + len;
        }
        //十六进制字符串转化byte数组  将帧长度拼接到报文数组中    从len的0位开始copy
        System.arraycopy(DataConvert.toBytes(len), 0, newByte, byteFrame.length, 2);
        System.arraycopy(data1, 0, newByte, byteFrame.length + 2, data1.length);  //将数据域拼接到报文数组中
        System.arraycopy(data2, 0, newByte, byteFrame.length + 2 + data1.length, 1);  //将数据域拼接到报文数组中
        System.arraycopy(data3, 0, newByte, byteFrame.length + 2 + data1.length + 1, data3.length);  //将数据域拼接到报文数组中

        byte xorByte = XorUtil.getXor(newByte);
        byte[] up = new byte[newByte.length + 2];
        System.arraycopy(new byte[]{(byte)0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
        System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
        up[newByte.length +1] = xorByte;

        Log.e("tag", "------upFrame-----"+ DataConvert.toHexString(up));

        String upValue = resamOption(up);
        return upValue;
    }

    public static String test() {
        RESAM rEsam = new RESAM();
        rEsam.init();
        String send = "9046000C0000";  //获取resam信息
        byte[] sendBytes = DataConvert.toBytes(send);
        int ret = rEsam.sendData(sendBytes, 0, sendBytes.length);
        if (ret == 0) {
            Log.e("tag", "------与RESAM通信发送失败");
        } else {
            Log.e("tag", "------与RESAM通信发送成功");
        }
        byte[] receive = new byte[5000];
        int offset = 0;
        int len = rEsam.recvData(receive, offset);
        String receiveStr = DataConvert.toHexString(receive);
        Log.e("tag", "------接收数据：" + receiveStr);
        return receiveStr.toString();
    }

//    /**
//     * 向resam发送命令 并解析响应帧
//     *
//     * @return
//     */
//    private static String resamOption(byte[] i) {
//        Shell.RESAM_init();//初始化
//        Shell.RESAM_setTimeOut(0,20000);
//        Shell.RESAM_clearSendCache();
//
//        // 发送数据
//        int send = Shell.RESAM_sendData(i, 0, i.length);
//
//        //接收数据
//        StringBuffer downFrame = new StringBuffer();
//        // 清空缓冲区，放置外来数据的干扰
//        downFrame.delete(0, downFrame.length());
//        //清除读取缓存
//        Shell.RESAM_clearRevCache();
//
//        Shell.RESAM_setTimeOut(1,20000);
//        //开始读取
//        byte[] buffer = new byte[BUFFER_LENGTH];
//        int offset = 0;
//        byte buf[];
//        int len;
//        do {
//            len = Shell.RESAM_recvData(buffer, offset, 1508);
//            if (len > 0) {
//                buf = new byte[len];
//                System.arraycopy(buffer, 0, buf, 0, len);
//                offset += len;
//                downFrame.append(Convert.toHexString(buf));
//            } else {
//                buf = null;
//                Log.e("tag", "-----read--error--");
//            }
//
//        } while (buf != null && buf.length >= BUFFER_LENGTH);
//        Log.e("tag", "--------downFrame----" + downFrame);
//
//        //900000085602000000000027
//        Parser parser = new Parser();
//        return parser.parser(DataConvert.toBytes(downFrame.toString())).getUpValue();
//    }


    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    public static String doTwo(byte[] i) {
        Shell.RESAM_init();//初始化
        Shell.RESAM_setTimeOut(1, 3000);

        // 发送数据
        int send = Shell.RESAM_sendData(i, 0, i.length);

        //接收数据
        StringBuffer downFrame = new StringBuffer();
        // 清空缓冲区，放置外来数据的干扰
        downFrame.delete(0, downFrame.length());
        //开始读取
        byte[] buffer = new byte[BUFFER_LENGTH];
        int offset = 0;
        byte buf[];
        int len;
        do {
            len = Shell.RESAM_recvData(buffer, offset, 1508);
            if (len > 0) {
                buf = new byte[len];
                System.arraycopy(buffer, 0, buf, 0, len);
                offset += len;
                downFrame.append(Convert.toHexString(buf));
            } else {
                buf = null;
                Log.e("tag", "-----read--error--");
            }

        } while (buf != null && buf.length >= BUFFER_LENGTH);
        Log.e("tag", "--------downFrame----" + downFrame);

        //900000085602000000000027
        return downFrame.toString();
    }

    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    private static String resamOption(byte[] i) {
        Shell.RESAM_init();//初始化
        Shell.RESAM_setTimeOut(1, 3000);

        // 发送数据
        int send = Shell.RESAM_sendData(i, 0, i.length);

        //接收数据
        StringBuffer downFrame = new StringBuffer();
        // 清空缓冲区，放置外来数据的干扰
        downFrame.delete(0, downFrame.length());
        //开始读取
        byte[] buffer = new byte[BUFFER_LENGTH];
        int offset = 0;
        byte buf[];
        int len;
        do {
            len = Shell.RESAM_recvData(buffer, offset, 1508);
            if (len > 0) {
                buf = new byte[len];
                System.arraycopy(buffer, 0, buf, 0, len);
                offset += len;
                downFrame.append(Convert.toHexString(buf));
            } else {
                buf = null;
                Log.e("tag", "-----read--error--");
            }

        } while (buf != null && buf.length >= BUFFER_LENGTH);
        Log.e("tag", "--------downFrame----" + downFrame);

        //900000085602000000000027
        TCHNParser parser = new TCHNParser();
        return parser.parser(DataConvert.toBytes(downFrame.toString())).getUpValue();
    }

    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    private static String resamOptionString(byte[] i) {
        Shell.RESAM_init();//初始化
        Shell.RESAM_setTimeOut(1, 3000);

        // 发送数据
        int send = Shell.RESAM_sendData(i, 0, i.length);

        //接收数据
        StringBuffer downFrame = new StringBuffer();
        // 清空缓冲区，放置外来数据的干扰
        downFrame.delete(0, downFrame.length());
        //开始读取
        byte[] buffer = new byte[BUFFER_LENGTH];
        int offset = 0;
        byte buf[];
        int len;
        do {
            len = Shell.RESAM_recvData(buffer, offset, 1508);
            if (len > 0) {
                buf = new byte[len];
                System.arraycopy(buffer, 0, buf, 0, len);
                offset += len;
                downFrame.append(Convert.toHexString(buf));
            } else {
                buf = null;
                Log.e("tag", "-----read--error--");
            }

        } while (buf != null && buf.length >= BUFFER_LENGTH);
        Log.e("tag", "--------downFrame----" + downFrame);

        //900000085602000000000027
        //TCHNParser parser = new TCHNParser();
        //return parser.parser(DataConvert.toBytes(downFrame.toString())).getUpValue();
        return downFrame.toString();
    }
}
