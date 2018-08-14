package com.sgcc.pda.hardware.frame.meter376.entity;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA3;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.StringUtil;

/**
 * Created by 闫亚锋 on 2017/12/14
 * desc：终端电量下发组帧的数据域实体
 */

public class Afn04Fn47Data {
    private String orderNum;
    private boolean freshFlag;
    private String buyValue;
    private String warningValue;
    private String tiaoZhaValue;

    public Afn04Fn47Data() {
    }

    public Afn04Fn47Data(String orderNum, boolean freshFlag, String buyValue, String warningValue, String tiaoZhaValue) {

        this.orderNum = orderNum;
        this.freshFlag = freshFlag;
        this.buyValue = buyValue;
        this.warningValue = warningValue;
        this.tiaoZhaValue = tiaoZhaValue;
    }

    public boolean isFreshFlag() {
        return freshFlag;
    }

    public void setFreshFlag(boolean freshFlag) {
        this.freshFlag = freshFlag;
    }

    public String getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(String buyValue) {
        this.buyValue = buyValue;
    }

    public String getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(String warningValue) {
        this.warningValue = warningValue;
    }

    public String getTiaoZhaValue() {
        return tiaoZhaValue;
    }

    public void setTiaoZhaValue(String tiaoZhaValue) {
        this.tiaoZhaValue = tiaoZhaValue;
    }

    public String getOrderNum() {

        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String fromString() {
        if (StringUtil.isBlank(orderNum)) {
            return "";
        }
        if (StringUtil.isBlank(buyValue)) {
            return "";
        }
        if (StringUtil.isBlank(warningValue)) {
            return "";
        }
        if (StringUtil.isBlank(tiaoZhaValue)) {
            return "";
        }
        StringBuffer result = null;
        try {
            result = new StringBuffer();
            orderNum= DataConvert.changeStringLength(orderNum,8);
            DataTypeA3 dataTypeA3 = new DataTypeA3(Long.parseLong(buyValue));
            buyValue = DataConvert.byte2HexStr(dataTypeA3.getArray());
            dataTypeA3.setValue(Long.parseLong(warningValue));
            warningValue = DataConvert.byte2HexStr(dataTypeA3.getArray());
            dataTypeA3.setValue(Long.parseLong(tiaoZhaValue));
            tiaoZhaValue = DataConvert.byte2HexStr(dataTypeA3.getArray());
            if (freshFlag) {
                result.append(orderNum + "AA" + buyValue + warningValue + tiaoZhaValue);
//                result.append(DataConvert.strReverse(orderNum,0,orderNum.length()) + "AA" + DataConvert.strReverse(buyValue,0,buyValue.length()) + DataConvert.strReverse(warningValue,0,warningValue.length()) + DataConvert.strReverse(tiaoZhaValue,0,tiaoZhaValue.length()));
            } else {
                result.append(orderNum + "55" + buyValue + warningValue + tiaoZhaValue);
//                result.append(DataConvert.strReverse(orderNum,0,orderNum.length()) + "55" + DataConvert.strReverse(buyValue,0,buyValue.length()) + DataConvert.strReverse(warningValue,0,warningValue.length()) + DataConvert.strReverse(tiaoZhaValue,0,tiaoZhaValue.length()));

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
        return result.toString();
    }
}
