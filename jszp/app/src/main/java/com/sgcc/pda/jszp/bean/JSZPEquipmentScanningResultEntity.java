package com.sgcc.pda.jszp.bean;

import java.util.ArrayList;

public class JSZPEquipmentScanningResultEntity extends BaseEntity {
    private JSZPEquipmentScanningScanDataEntity scanData;

    public JSZPEquipmentScanningScanDataEntity getScanData() {
        return scanData;
    }

    public void setScanData(JSZPEquipmentScanningScanDataEntity scanData) {
        this.scanData = scanData;
    }

    public class JSZPEquipmentScanningScanDataEntity {
        private int inputNum;
        private int discoverNum;
        private int nomralNum;
        private int exceptionNum;
        private ArrayList<JSZPEquipmentScanningDevData> nomarlDevData;
        private ArrayList<JSZPEquipmentScanningDevData> exceptionDevData;

        public int getInputNum() {
            return inputNum;
        }

        public void setInputNum(int inputNum) {
            this.inputNum = inputNum;
        }

        public int getDiscoverNum() {
            return discoverNum;
        }

        public void setDiscoverNum(int discoverNum) {
            this.discoverNum = discoverNum;
        }

        public int getNomralNum() {
            return nomralNum;
        }

        public void setNomralNum(int nomralNum) {
            this.nomralNum = nomralNum;
        }

        public int getExceptionNum() {
            return exceptionNum;
        }

        public void setExceptionNum(int exceptionNum) {
            this.exceptionNum = exceptionNum;
        }

        public ArrayList<JSZPEquipmentScanningDevData> getNomarlDevData() {
            return nomarlDevData;
        }

        public void setNomarlDevData(ArrayList<JSZPEquipmentScanningDevData> nomarlDevData) {
            this.nomarlDevData = nomarlDevData;
        }

        public ArrayList<JSZPEquipmentScanningDevData> getExceptionDevData() {
            return exceptionDevData;
        }

        public void setExceptionDevData(ArrayList<JSZPEquipmentScanningDevData> exceptionDevData) {
            this.exceptionDevData = exceptionDevData;
        }

        public class JSZPEquipmentScanningDevData {
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
