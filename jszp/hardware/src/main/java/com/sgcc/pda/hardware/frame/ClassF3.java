package com.sgcc.pda.hardware.frame;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

public class ClassF3 {
    private static final String TAG = "ClassF3";

    private byte[] mainIp = new byte[4];// 主Ip地址
    private byte[] mainPort = new byte[2];// 主端口号
    private byte[] extraIp = new byte[4];// 备用Ip地址
    private byte[] extraPort = new byte[2];// 备用端口号
    private byte[] apn = new byte[16];// apn

    private int mPostion = 0;

    public ClassF3 byteToClass(byte[] data) {
        if (data == null || data.length != 28) {
            // Toaster.getInstance().displayToast("数据错误");
            return null;
        }
        mPostion = 0;
        try {
            System.arraycopy(data, mPostion, mainIp, 0, mainIp.length);
            mPostion = mPostion + mainIp.length;
            System.arraycopy(data, mPostion, mainPort, 0, mainPort.length);
            mPostion = mPostion + mainPort.length;
            System.arraycopy(data, mPostion, extraIp, 0, extraIp.length);
            mPostion = mPostion + extraIp.length;
            System.arraycopy(data, mPostion, extraPort, 0, extraPort.length);
            mPostion = mPostion + extraPort.length;
            System.arraycopy(data, mPostion, apn, 0, apn.length);
            mPostion = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public byte[] classToByte() {
        byte[] data = new byte[28];
        mPostion = 0;
        try {
            System.arraycopy(mainIp, 0, data, mPostion, mainIp.length);
            mPostion = mPostion + mainIp.length;
            System.arraycopy(mainPort, 0, data, mPostion, mainPort.length);
            mPostion = mPostion + mainPort.length;
            System.arraycopy(extraIp, 0, data, mPostion, extraIp.length);
            mPostion = mPostion + extraIp.length;
            System.arraycopy(extraPort, 0, data, mPostion, extraPort.length);
            mPostion = mPostion + extraPort.length;
            System.arraycopy(apn, 0, data, mPostion, apn.length);
            LogUtil.i(TAG, "apn=" + BcdUtils.binArrayToString(apn));
            mPostion = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getApnString() {
        String str = "";
        for (int i = 0; i < apn.length; i++) {
            if (apn[i] == (byte) 0x0 || apn[i] == (byte) 0xff) {
                continue;
            }

            str += (char) apn[i];
        }
        return str;
    }

    public String getMainIpString() {
        String ip = "" + (mainIp[0] & 0xff) + "." + (mainIp[1] & 0xff) + "."
                + (mainIp[2] & 0xff) + "." + (mainIp[3] & 0xff);
        return ip;
    }

    public String getMainPortString() {
        int p = (mainPort[1] << 8) + (mainPort[0] & 0xff);
        return "" + p;
    }

    public String getExtraIpString() {
        String ip = "" + (extraIp[0] & 0xff) + "." + (extraIp[1] & 0xff) + "."
                + (extraIp[2] & 0xff) + "." + (extraIp[3] & 0xff);
        return ip;
    }

    public String getExtraPortString() {
        int p = (extraPort[1] << 8) + (extraPort[0] & 0xff);
        return "" + p;
    }

    public void setIpAndPortString(String ipAndPortStr) {

        try {
            if (ipAndPortStr == null || ipAndPortStr.trim().length() < 5) {
                return;
            }
            int startPos = ipAndPortStr.indexOf(",");
            String ipAndPort = ipAndPortStr.substring(startPos + 1,
                    ipAndPortStr.length());
            LogUtil.i(TAG,"ipAndPort=" + ipAndPort);
            String[] data = ipAndPort.split(":");
            String ipStr = data[0];
            String[] ipArray = ipStr.split("\\.");
            for (int i = 0; i < mainIp.length; i++) {
                if (ipArray[i].length() > 0) {
                    mainIp[i] = (byte) Integer.parseInt(ipArray[i]);
                }
            }
            LogUtil.i(TAG,BcdUtils.binArrayToString(mainIp));
            String portStr = data[1];
            int value = Integer.valueOf(portStr);
            mainPort[1] = (byte) ((value >> 8) & 0xFF);
            mainPort[0] = (byte) (value & 0xFF);
            LogUtil.i(TAG,BcdUtils.binArrayToString(mainPort));
            LogUtil.i(TAG,"end");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setExtraIpAndPortString(String extraIpAndPortStr) {

        try {
            if (extraIpAndPortStr == null || extraIpAndPortStr.trim().length() < 5) {
                return;
            }
            int startPos = extraIpAndPortStr.indexOf(",");
            String ipAndPort = extraIpAndPortStr.substring(startPos + 1,
                    extraIpAndPortStr.length());
            LogUtil.i(TAG,"ipAndPort=" + ipAndPort);
            // Toaster.getInstance().displayToast("ipAndPort=" + ipAndPort);
            String[] data = ipAndPort.split(":");
            String ipStr = data[0];
            String[] ipArray = ipStr.split("\\.");
            for (int i = 0; i < extraIp.length; i++) {
                if (ipArray[i].length() > 0) {
                    extraIp[i] = (byte) Integer.parseInt(ipArray[i]);
                }
            }
            LogUtil.i(TAG,BcdUtils.binArrayToString(extraIp));
            String portStr = data[1];
            int value = Integer.valueOf(portStr);
            extraPort[1] = (byte) ((value >> 8) & 0xFF);
            extraPort[0] = (byte) (value & 0xFF);
            LogUtil.i(TAG,BcdUtils.binArrayToString(extraPort));
            LogUtil.i(TAG,"end");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setApnString(String ipAndPort) {
        try {
            apn = new byte[apn.length];
            byte[] data = ipAndPort.getBytes();
            System.arraycopy(ipAndPort.getBytes(), 0, apn, 0, data.length);
            LogUtil.i(TAG,BcdUtils.binArrayToString(apn));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getMainIp() {
        return mainIp;
    }

    public void setMainIp(byte[] mainIp) {
        this.mainIp = mainIp;
    }

    public byte[] getMainPort() {
        return mainPort;
    }

    public void setMainPort(byte[] mainPort) {
        this.mainPort = mainPort;
    }

    public byte[] getExtraIp() {
        return extraIp;
    }

    public void setExtraIp(byte[] extraIp) {
        this.extraIp = extraIp;
    }

    public byte[] getExtraPort() {
        return extraPort;
    }

    public void setExtraPort(byte[] extraPort) {
        this.extraPort = extraPort;
    }

    public byte[] getApn() {
        return apn;
    }

    public void setApn(byte[] apn) {
        this.apn = apn;
    }

}
