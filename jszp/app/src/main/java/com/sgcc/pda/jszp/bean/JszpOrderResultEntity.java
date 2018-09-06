package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderResultEntity extends BaseEntity {
    private JszpQueryDistAppsItemEntity distApp;

    public JszpQueryDistAppsItemEntity getDistApp() {
        return distApp;
    }

    public void setDistApp(JszpQueryDistAppsItemEntity distApp) {
        this.distApp = distApp;
    }
}
