package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:赵锦
 * date:2018/9/5 0005 14:19
 *
 * 出库任务  入库任务查询
 */
public class TaskQueryResultEntity extends BaseEntity{
    private List<IoTaskDets> ioTaskDets;

    public List<IoTaskDets> getIoTaskDets() {
        return ioTaskDets;
    }

    public void setIoTaskDets(List<IoTaskDets> ioTaskDets) {
        this.ioTaskDets = ioTaskDets;
    }
}
