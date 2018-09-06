package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;

public class JSZPOutboundScanQueryResultEntity extends BaseEntity {
    private JSZPOutboundScanQueryScanResultEntity scanResult;//扫描结果数据集

    public JSZPOutboundScanQueryScanResultEntity getScanResult() {
        return scanResult;
    }

    public void setScanResult(JSZPOutboundScanQueryScanResultEntity scanResult) {
        this.scanResult = scanResult;
    }

    public class JSZPOutboundScanQueryScanResultEntity {
        private int totalRecords;

        private ArrayList<JSZPOutboundScanQueryDevData> devData;

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public ArrayList<JSZPOutboundScanQueryDevData> getDevData() {
            return devData;
        }

        public void setDevData(ArrayList<JSZPOutboundScanQueryDevData> devData) {
            this.devData = devData;
        }

        public class JSZPOutboundScanQueryDevData {
            private String barCode;//条码
            private String equipCateg;//设备类别
            private String equipCode;//设备码
            private String equipDesc;//设备码描述
            private String statusCode;//设备状态
            private String excepDesc;//异常描述

            public String getBarCode() {
                return barCode;
            }

            public void setBarCode(String barCode) {
                this.barCode = barCode;
            }

            public String getEquipCateg() {
                return equipCateg;
            }

            public void setEquipCateg(String equipCateg) {
                this.equipCateg = equipCateg;
            }

            public String getEquipCode() {
                return equipCode;
            }

            public void setEquipCode(String equipCode) {
                this.equipCode = equipCode;
            }

            public String getEquipDesc() {
                return equipDesc;
            }

            public void setEquipDesc(String equipDesc) {
                this.equipDesc = equipDesc;
            }

            public String getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(String statusCode) {
                this.statusCode = statusCode;
            }

            public String getExcepDesc() {
                return excepDesc;
            }

            public void setExcepDesc(String excepDesc) {
                this.excepDesc = excepDesc;
            }
        }

    }
}
