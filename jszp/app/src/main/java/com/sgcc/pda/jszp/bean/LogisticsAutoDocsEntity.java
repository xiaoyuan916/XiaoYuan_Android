package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * 选择车辆  结果集
 */
public class LogisticsAutoDocsEntity extends BaseEntity {

    private List<AutoDocsItem> autoDocs;

    public List<AutoDocsItem> getAutoDocs() {
        return autoDocs;
    }

    public void setAutoDocs(List<AutoDocsItem> autoDocs) {
        this.autoDocs = autoDocs;
    }
}
