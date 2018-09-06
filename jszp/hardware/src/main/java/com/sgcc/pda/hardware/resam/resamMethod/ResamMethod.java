package com.sgcc.pda.hardware.resam.resamMethod;

import android.util.Log;

import com.cepri.dev.RESAM;
import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.resam.makeFrame.XorUtil;
import com.sgcc.pda.hardware.resam.parser.Parser;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.NewProtocol;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.StringUtil;


/**
 * Created by TL on 2017/4/27.
 */

public class ResamMethod {

    private static final int BUFFER_LENGTH = 1508;


    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static int getResamInfo(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0xFF,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0xFF,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0xFF,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static int getResamTest(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x23,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x23,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x23,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 序列号
     *
     * @return
     */
    public static int getResamNo(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }


    /**
     * 获取resam 开机外部认证1
     *
     * @return
     */
    public static int getResamOpen1(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x14, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x14, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x14, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 开机外部认证2
     *
     * @return
     */
    public static int getResamOpen2(String rand, StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] m = DataConvert.toBytes(rand);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x16, (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x08};
            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));
            ret = resamOption(newByte, upValue);
        } else {
            byte[] m = DataConvert.toBytes(rand);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x16, (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x08};

            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

            byte xorByte = XorUtil.getXor(newByte);
            byte[] up = new byte[newByte.length + 2];
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 开机外部认证2
     *
     * @return
     */
    public static int getResamOpen2(String no, String rand, StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] n = DataConvert.toBytes(no);
            byte[] m = DataConvert.toBytes(rand);
            byte[] byteFrame = new byte[]{(byte) 0x91, (byte) 0x16, (byte) 0x08, (byte) 0x01,
                    (byte) 0x00, (byte) 0x18};

            byte[] newByte = new byte[byteFrame.length + n.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(n, 0, newByte, byteFrame.length, n.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length + n.length, m.length);  //将数据域拼接到报文数组中

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOptionString(newByte, upValue);
        } else {
            byte[] n = DataConvert.toBytes(no);
            byte[] m = DataConvert.toBytes(rand);
            byte[] byteFrame = new byte[]{(byte) 0x91, (byte) 0x16, (byte) 0x08, (byte) 0x01,
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

            ret = resamOptionString(up, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 对称密钥版本
     *
     * @return
     */
    public static int getResamSeckeyVer(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 会话计数器
     *
     * @return
     */
    public static int getResamYasctr(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        if (ret == 0) {
            upValue.substring(0, 8);
        }
        return ret;
    }

    /**
     * 获取resam 终端证书版本号
     *
     * @return
     */
    public static int getResamTnmlVer(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x05,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x05,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x05,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        if (ret == 0) {
            upValue.substring(2, 4);
        }
        return ret;
    }

    /**
     * 获取resam 终端证书序列号
     *
     * @return
     */
    public static int getResamTnmlSn(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x09,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x09,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x09,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 主站证书
     *
     * @return
     */
    public static int getResamMasterCert(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0C,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0C,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0C,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }

    /**
     * 获取resam 终端证书
     *
     * @return
     */
    public static int getResamTmnlCert(StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);
        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0B,
                    (byte) 0x00, (byte) 0x00};
            ret = resamOption(byteFrame, upValue);
        } else {
            byte[] byteFrame1 = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0B,
                    (byte) 0x00, (byte) 0x00};
            byte[] byteFrame = new byte[]{(byte) 0x55, (byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x0B,
                    (byte) 0x00, (byte) 0x00, XorUtil.getXor(byteFrame1)};
            ret = resamOption(byteFrame, upValue);
        }
        return ret;
    }


    /**
     * 获取resam 加密
     *
     * @return
     */
    public static int getResamM1S1(String WesamNum, String hhxsjsq,StringBuffer upValue) {

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] m1 = DataConvert.toBytes(WesamNum);
            byte[] mac = DataConvert.toBytes(hhxsjsq);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x7C, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x11, (byte) 0x08, (byte) 0x09, (byte) 0x03, (byte) 0x04, (byte) 0x08};

            byte[] newByte = new byte[byteFrame.length + m1.length + mac.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m1, 0, newByte, byteFrame.length, m1.length);  //将数据域拼接到报文数组中
            System.arraycopy(mac, 0, newByte, byteFrame.length + m1.length, mac.length);  //将数据域拼接到报文数组中

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOption(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }

    /**
     * 获取resam 加密
     *
     * @return
     */
    public static int ResamMyxs(String WesamNum, String m2, String mac2,StringBuffer upValue) {
        int ret = 0;
        if (!SOConfig.isResamNew()) {
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

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOptionString(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOptionString(up,upValue);
        }
        return ret;
    }

    /**
     * 对数据进行安全安全等级为3的加密处理
     *
     * @param busiData 业务数据
     * @return
     */
    public static int ResamJiaMi(String busiData,StringBuffer upValue) {
        int ret = 0;
        if(StringUtil.isBlank(busiData))
        {
            return ErrorManager.ErrorType.ResamEncryptEmptyDataError.getValue();
        }
        if (!SOConfig.isResamNew()) {
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
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOptionString(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOptionString(up,upValue);
        }
        return ret;
    }

    /**
     * 对外设返回安全等级为3的数据解密处理
     *
     * @param busiData 业务数据
     * @return
     */
    public static int ResamJieMi(String busiData,StringBuffer upValue) {
        int ret = 0;
        if (StringUtil.isBlank(busiData)) {
            return ErrorManager.ErrorType.ResamDecryptEmptyDataError.getValue();
        }
        if (!SOConfig.isResamNew()) {
            byte[] data = DataConvert.toBytes(busiData);
            int len = 8 + data.length + 0x0F;
            int len2 = (data.length + 3 + 0x0F) / 16 * 16 + 4;
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x76, (byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) len,
                    (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x06, (byte) 0x81, (byte) 0x1c, (byte) 0x00, (byte) 0x13
                    , (byte) 0x00, (byte) len2};
            byte[] newByte = new byte[byteFrame.length + data.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(data, 0, newByte, byteFrame.length, data.length);  //将数据域拼接到报文数组中
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOptionString(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOptionString(up,upValue);
        }
        return ret;
    }


    /**
     * 获取resam 加密
     *
     * @return
     */
    public static int getResamM2S2(String m1, String s1,StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] m = DataConvert.toBytes(m1);
            byte[] s = DataConvert.toBytes(s1);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x12, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x60};

            byte[] newByte = new byte[byteFrame.length + m.length + s.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中
            System.arraycopy(s, 0, newByte, byteFrame.length + m.length, s.length);  //将数据域拼接到报文数组中

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOption(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }

    /**
     * 获取resam 置离线计数器
     *
     * @return
     */
    public static int getResamSetCounter(String m1,StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] m = DataConvert.toBytes(m1);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x62, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x10};

            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOptionString(newByte,upValue);
        } else {
            byte[] m = DataConvert.toBytes(m1);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x62, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x10};

            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

            byte xorByte = XorUtil.getXor(newByte);
            byte[] up = new byte[newByte.length + 2];
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOptionString(up,upValue);
        }
        return ret;
    }

    /**
     * 获取resam 转加密初始化
     *
     * @return
     */
    public static int getResamMiInit(String m1,StringBuffer upValue) {
//        MakeFrame makeFrame = new MakeFrame();
//        Frame frame = new Frame();
//        frame.setCla((byte) 0x90);
//        frame.setIns((byte) 0x46);
//        frame.setP1((byte) 0x00);
//        frame.setP2((byte) 0x02);
//        frame.setData(new byte[]{});
//        byte[] byteFrame = makeFrame.makeFrames(frame);

        int ret = 0;
        if (!SOConfig.isResamNew()) {
            byte[] m = DataConvert.toBytes(m1);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x60, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x20};

            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));

            ret = resamOption(newByte,upValue);
        } else {
            byte[] m = DataConvert.toBytes(m1);
            byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x60, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x20};

            byte[] newByte = new byte[byteFrame.length + m.length];
            System.arraycopy(byteFrame, 0, newByte, 0, byteFrame.length);  //将数据域拼接到报文数组中
            System.arraycopy(m, 0, newByte, byteFrame.length, m.length);  //将数据域拼接到报文数组中

            byte xorByte = XorUtil.getXor(newByte);
            byte[] up = new byte[newByte.length + 2];
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }

    /**
     * resam 认证一1
     *
     * @return
     */
    public static int getResamSureOne1(String uid,StringBuffer upValue) {
        int ret = 0;
        if (!SOConfig.isResamNew()) {
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
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));
            ret = resamOption(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }


    /**
     * resam 认证一2
     *
     * @return
     */
    public static int getResamSureOne2(String m16, String r4,StringBuffer upValue) {
        int ret = 0;
        if (!SOConfig.isResamNew()) {
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
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));
            ret = resamOption(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }

    /**
     * resam 认证一3
     *
     * @return
     */
    public static int getResamSureOne3(String m16, String t2,StringBuffer upValue) {
        int ret = 0;
        if (!SOConfig.isResamNew()) {
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
            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(newByte));
            ret = resamOption(newByte,upValue);
        } else {
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
            System.arraycopy(new byte[]{(byte) 0x55}, 0, up, 0, 1);  //将数据域拼接到报文数组中
            System.arraycopy(newByte, 0, up, 1, newByte.length);  //将数据域拼接到报文数组中
            up[newByte.length + 1] = xorByte;

            Log.e("tag", "------upFrame-----" + DataConvert.toHexString(up));

            ret = resamOption(up,upValue);
        }
        return ret;
    }

    public static int test() {
        RESAM rEsam = new RESAM();
        rEsam.init();
        String send = "9046000C0000";  //获取resam信息
        byte[] sendBytes = DataConvert.toBytes(send);
        int ret = resumSendData(sendBytes);
        if (ret == 0) {
            Log.e("tag", "------与RESAM通信发送成功");
            StringBuffer stringBuffer=new StringBuffer();
            ret = resumReceiveData(stringBuffer);
            if(ret==0){
                Log.e("tag", "------与RESAM通信接收成功");
                Log.e("tag", "------接收数据：" + stringBuffer.toString());
            }else {
                Log.e("tag", "------与RESAM通信接收失败");
            }
        } else {
            Log.e("tag", "------与RESAM通信发送失败");
        }
        return ret;
    }

//    /**
//     * 向resam发送命令 并解析响应帧
//     *
//     * @return
//     */
//    private static int resamOption(byte[] i) {
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
    public static int doTwo(byte[] i,StringBuffer upValue) {
        int ret = resumSendData(i);
        if (ret == 0) {
            ret = resumReceiveData(upValue);
        }
        return ret;
    }

    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    private static int resamOption(byte[] i, StringBuffer result) {
        int ret = resumSendData(i);
        if (ret == 0) {
            StringBuffer downFrame = new StringBuffer();
            ret = resumReceiveData(downFrame);
            if (ret == 0) {
                Parser parser = new Parser();
                result.append(parser.parser(DataConvert.toBytes(downFrame.toString())).getUpValue());
            }
        }
        return ret;
        //900000085602000000000027
    }

    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    private static int resamOptionString(byte[] i, StringBuffer result) {
        int ret = resumSendData(i);
        if (ret == 0) {
            ret = resumReceiveData(result);
        }
        return ret;
    }

    private static int resumSendData(byte[] i) {
        Shell.RESAM_init();//初始化
        Shell.RESAM_setTimeOut(1, 3000);
        Shell.RESAM_clearSendCache();
        int ret = Shell.RESAM_sendData(i, 0, i.length);
        if (NewProtocol.isNewProtocol()) {
            if (ret != 0) {
                ret = ErrorManager.ErrorType.ResamSendDataError.getValue();
            }
        } else {
            if (ret == i.length) {
                ret = 0;
            } else {
                ret = ErrorManager.ErrorType.ResamSendDataError.getValue();
            }
        }
        return ret;
    }

    private static int resumReceiveData(StringBuffer downFrame) {
        downFrame.delete(0, downFrame.length());
        byte[] buffer = new byte[BUFFER_LENGTH];
        int count = BUFFER_LENGTH;
        Shell.RESAM_clearRevCache();
        int ret;
        int offset = 0;
        byte buf[] = null;
        do {
            ret = Shell.RESAM_recvData(buffer, offset, count);
            if (NewProtocol.isNewProtocol()) {
                if (ret != 0) {
                    ret = ErrorManager.ErrorType.ResamReceiveDataError.getValue();
                } else {
                    buf = new byte[count];
                    System.arraycopy(buffer, 0, buf, 0, count);
                    offset += count;
                    downFrame.append(Convert.toHexString(buf));
                }

            } else {
                if (ret > 0) {
                    buf = new byte[ret];
                    System.arraycopy(buffer, 0, buf, 0, ret);
                    offset += ret;
                    downFrame.append(Convert.toHexString(buf));
                    ret = 0;
                } else {
                    ret = ErrorManager.ErrorType.ResamSendDataError.getValue();
                }
            }
        } while (buf != null && buf.length >= BUFFER_LENGTH);
        return ret;
    }
}
