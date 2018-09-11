package com.sgcc.pda.jszp.bean;

public class CodeTypeRequestEntity extends BaseRequestEntity {
    private String codeType;
    private String codeValue;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
