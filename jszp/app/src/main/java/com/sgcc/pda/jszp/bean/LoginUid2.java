package com.sgcc.pda.jszp.bean;


/**
 * Created by TL on 2017/11/7.
 * 获取UID
 */
public class LoginUid2 extends BaseEntity {
    /**
     * 密文M1
     */
    private String M1;
    /**
     * 用户编号
     */
    private String UID;

    /**
     * 签名S1
     */
    private String S1;

    public String getM1() {
        return M1;
    }

    public void setM1(String m1) {
        M1 = m1;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getS1() {
        return S1;
    }

    public void setS1(String s1) {
        S1 = s1;
    }
}
