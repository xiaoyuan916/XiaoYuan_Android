package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * 返回码信息
 */
public class BaseEntity implements Serializable {
    //返回码
    public String RT_F;
    //信息描述
    public String RT_D;
    //传输MAC
    public String MAC;


    public String getRT_F() {
        return RT_F;
    }

    public void setRT_F(String RT_F) {
        this.RT_F = RT_F;
    }

    public String getRT_D() {
        return RT_D;
    }

    public void setRT_D(String RT_D) {
        this.RT_D = RT_D;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
