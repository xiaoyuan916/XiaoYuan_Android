package com.sgcc.pda.jszp.bean;

/**
 * author:赵锦
 * date:2018/9/7 0007 09:15
 */
public class DeviceBoxScanResultEntity extends BaseEntity {
    private TurnoverBoxInfo turnoverBoxInfo;

    public TurnoverBoxInfo getTurnoverBoxInfo() {
        return turnoverBoxInfo;
    }

    public void setTurnoverBoxInfo(TurnoverBoxInfo turnoverBoxInfo) {
        this.turnoverBoxInfo = turnoverBoxInfo;
    }
}
