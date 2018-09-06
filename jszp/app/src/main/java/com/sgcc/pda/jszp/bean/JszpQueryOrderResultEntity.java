package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/4
 */
public class JszpQueryOrderResultEntity extends BaseEntity {
    private List<JszpQueryDistAppsItemEntity> distApps;

    public List<JszpQueryDistAppsItemEntity> getDistApps() {
        return distApps;
    }

    public void setDistApps(List<JszpQueryDistAppsItemEntity> distApps) {
        this.distApps = distApps;
    }
}
