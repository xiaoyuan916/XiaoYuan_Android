package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author:赵锦
 * date:2018/8/28 0028 14:36
 * 2.1.6.1.2.	出入库设备扫描结果查询
 */
public class ScanDeviceResultEntity extends BaseEntity {
    private ScanResultItem scanResult;

  public   class ScanResultItem implements Serializable {
        private int totalRecords;//总记录数
        private List<ScanDeviceDate> devData;//设备数据集

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public List<ScanDeviceDate> getDevData() {
            return devData;
        }

        public void setDevData(List<ScanDeviceDate> devData) {
            this.devData = devData;
        }
    }

    public ScanResultItem getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResultItem scanResult) {
        this.scanResult = scanResult;
    }
}
