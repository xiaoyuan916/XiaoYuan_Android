package com.sgcc.pda.jszp.bean;




/**
 * Created by TL on 2017/11/7.
 * 身份认证
 */
public class LoginIdauth2 extends BaseEntity {
    //是否有新软件包升级
    private String IS_PU;
    //新软件包升级描述
    private String P_DESC;
    //是否有新知识库升级
    private String IS_K;
    //新知识库包升级描述
    private String K_DESC;
    //是否有地图升级
    private String IS_M;
    //新地图包升级描述
    private String M_DESC;
    //是否锁定现场作业终端
    private String IS_LOCK;
    //是否二次发行
    private String IS_TWO_SENT;
    //是否置离线计数器和转加密初始化
    private String IS_AUTH;
    //是否有新安全单元软件包升级
    private String IS_ESAM;
    //是否有新操作系统升级
    private String IS_SYS;


    public String getIS_PU() {
        return IS_PU;
    }

    public void setIS_PU(String IS_PU) {
        this.IS_PU = IS_PU;
    }

    public String getP_DESC() {
        return P_DESC;
    }

    public void setP_DESC(String p_DESC) {
        P_DESC = p_DESC;
    }

    public String getIS_K() {
        return IS_K;
    }

    public void setIS_K(String IS_K) {
        this.IS_K = IS_K;
    }

    public String getK_DESC() {
        return K_DESC;
    }

    public void setK_DESC(String k_DESC) {
        K_DESC = k_DESC;
    }

    public String getIS_M() {
        return IS_M;
    }

    public void setIS_M(String IS_M) {
        this.IS_M = IS_M;
    }

    public String getM_DESC() {
        return M_DESC;
    }

    public void setM_DESC(String m_DESC) {
        M_DESC = m_DESC;
    }



    public String getIS_LOCK() {
        return IS_LOCK;
    }

    public void setIS_LOCK(String IS_LOCK) {
        this.IS_LOCK = IS_LOCK;
    }

    public String getIS_TWO_SENT() {
        return IS_TWO_SENT;
    }

    public void setIS_TWO_SENT(String IS_TWO_SENT) {
        this.IS_TWO_SENT = IS_TWO_SENT;
    }

    public String getIS_AUTH() {
        return IS_AUTH;
    }

    public void setIS_AUTH(String IS_AUTH) {
        this.IS_AUTH = IS_AUTH;
    }

    public String getIS_ESAM() {
        return IS_ESAM;
    }

    public void setIS_ESAM(String IS_ESAM) {
        this.IS_ESAM = IS_ESAM;
    }

    public String getIS_SYS() {
        return IS_SYS;
    }

    public void setIS_SYS(String IS_SYS) {
        this.IS_SYS = IS_SYS;
    }


}
