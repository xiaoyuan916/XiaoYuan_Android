package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

public class CodeTypeEntity implements Serializable{
    private String code;
    private String codeName;
    private boolean selected;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
