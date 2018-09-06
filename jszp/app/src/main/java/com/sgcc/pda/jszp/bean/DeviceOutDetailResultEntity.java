package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/8/22 0022 09:35
 *
 * 平库出库  详情结果集
 */
public class DeviceOutDetailResultEntity extends BaseEntity {
    private DeviceOutSubTaskItem ioTaskDet;

    public DeviceOutSubTaskItem getIoTaskDet() {
        return ioTaskDet;
    }

    public void setIoTaskDet(DeviceOutSubTaskItem ioTaskDet) {
        this.ioTaskDet = ioTaskDet;
    }
}
