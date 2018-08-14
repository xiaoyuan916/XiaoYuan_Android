package com.sgcc.pda.hardware.resam.beans;

/**
 * Created by TL on 2017/4/27.
 */

public class LoginUid {
    //返回码
    public  String  RT_F;
    //信息描述
    public  String  RT_D;
    //UID
    public  String  UID;
    //密文M1
    public  String  M1;
    //签名S1
    public  String  S1;
    //传输MAC
    public  String  MAC;

    public String getRT_F() {
        return RT_F;
    }
    public String getRT_D() {
        return RT_D;
    }

    public String getUID() {
        return UID;
    }

    public String getM1() {
        return M1;
    }

    public String getS1() {
        return S1;
    }

    public String getMAC() {
        return MAC;
    }

    public void setRT_F(String RT_F) {
        this.RT_F = RT_F;
    }

    public void setRT_D(String RT_D) {
        this.RT_D = RT_D;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setM1(String m1) {
        M1 = m1;
    }

    public void setS1(String s1) {
        S1 = s1;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
