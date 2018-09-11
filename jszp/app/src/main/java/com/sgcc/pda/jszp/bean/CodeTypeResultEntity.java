package com.sgcc.pda.jszp.bean;

import java.util.List;

public class CodeTypeResultEntity extends BaseEntity {
    private List<CodeTypeEntity> codes;


    public List<CodeTypeEntity> getCodes() {
        return codes;
    }

    public void setCodes(List<CodeTypeEntity> codes) {
        this.codes = codes;
    }
}
