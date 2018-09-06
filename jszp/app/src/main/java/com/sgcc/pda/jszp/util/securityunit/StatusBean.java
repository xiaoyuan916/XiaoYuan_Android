package com.sgcc.pda.jszp.util.securityunit;

/**
 * Created by qinling on 2018/8/16 13:10
 * Description:
 */
public class StatusBean {
    /**
     * statusCode   -1  SO库存在错误
     * statusCode   -2  获取单元信息失败
     * statusCode   -3  获取CEsam 信息失败
     * statusCode   -4  操作员密码校验错误
     * statusCode   -5  获取Yesam计数器失败
     * statusCode   -6   loginUid: 获取UID,以及M1,S1失败
     * statusCode   -7  doLoginIdauth: 安全单元应用层身份认证失败
     *
     */

    private int statusCode;
    private String errorMessage;

    public void setErrorStatus(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
