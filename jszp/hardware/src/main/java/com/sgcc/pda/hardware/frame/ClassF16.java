package com.sgcc.pda.hardware.frame;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

public class ClassF16 {

    private static final String TAG = "ClassF16";

    private byte[] apnName = new byte[32];// 虚拟专网用户名
    private byte[] apnPassword = new byte[32];// 虚拟专网密码

    private int mPostion = 0;

    public ClassF16 byteToClass(byte[] data) {
        if (data == null || data.length != 64) {
            // Toaster.getInstance().displayToast("数据错误");
            return null;
        }
        mPostion = 0;
        try {
            System.arraycopy(data, mPostion, apnName, 0, apnName.length);
            mPostion = mPostion + apnName.length;
            System.arraycopy(data, mPostion, apnPassword, 0, apnPassword.length);
            mPostion = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public byte[] classToByte() {
        byte[] data = new byte[64];
        mPostion = 0;
        try {
            System.arraycopy(apnName, 0, data, mPostion, apnName.length);
            mPostion = mPostion + apnName.length;
            System.arraycopy(apnPassword, 0, data, mPostion, apnPassword.length);
            mPostion = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getApnNameString() {
        return hexStr2Str(BcdUtils.binArrayToString(apnName).replaceAll("00",""));
    }

    public String getApnPasswordpString() {
        return hexStr2Str(BcdUtils.binArrayToString(apnPassword).replaceAll("00",""));
    }
    public void setApnNameString(String apnNameString) {

        try {
            if (apnNameString == null || apnNameString.trim().length() > 32) {
                return;
            }
            System.arraycopy(apnNameString.getBytes(), 0, apnName, 0, apnNameString.getBytes().length);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setApnPasswordString(String apnPasswordString) {

        try {
            if (apnPasswordString == null || apnPasswordString.trim().length() > 32) {
                return;
            }
            System.arraycopy(apnPasswordString.getBytes(), 0, apnPassword, 0, apnPasswordString.getBytes().length);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public byte[] getApnName() {
        return apnName;
    }

    public void setApnName(byte[] apnName) {
        this.apnName = apnName;
    }

    public byte[] getApnPassword() {
        return apnPassword;
    }

    public void setApnPassword(byte[] apnPassword) {
        this.apnPassword = apnPassword;
    }
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

}
