package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:赵锦
 * date:2018/8/22 0022 09:35
 *
 * 平库出库  列表结果集
 */
public class DeviceOutListResultEntity extends BaseEntity {
    private List<DeviceOutTaskItem> distTasks;

    public List<DeviceOutTaskItem> getDistTasks() {
        return distTasks;
    }

    public void setDistTasks(List<DeviceOutTaskItem> distTasks) {
        this.distTasks = distTasks;
    }
}
