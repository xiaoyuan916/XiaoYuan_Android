package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/8/27 0027 11:04
 *
 * 返程装车  结果集
 */
public class ReturnCarResultEntity extends BaseEntity {
    private SplitTask splitTask;

    public SplitTask getSplitTask() {
        return splitTask;
    }

    public void setSplitTask(SplitTask splitTask) {
        this.splitTask = splitTask;
    }
}
