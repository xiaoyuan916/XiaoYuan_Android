package com.sgcc.pda.jszp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author:赵锦
 * date:2018/8/24 0024 10:58
 */
public class ScanResultEntity extends BaseEntity {
    private ScanDate scanData;

   public class ScanDate implements Serializable{
        private int inputNum;//预备扫描结果数
        private int discoverNum;//识别扫描结果数
        private int nomralNum;//正常扫描结果数
        private int exceptionNum;//异常扫描结果
        private List<ScanDeviceDate> nomarlDevData;//正常扫描结果集  isResult=false,不返回
        private List<ScanDeviceDate> exceptionDevData;//异常扫描结果集   isResult=false,不返回

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

       public List<ScanDeviceDate> getNomarlDevData() {
            return nomarlDevData;
        }

        public void setNomarlDevData(List<ScanDeviceDate> nomarlDevData) {
            this.nomarlDevData = nomarlDevData;
        }

        public List<ScanDeviceDate> getExceptionDevData() {
            return exceptionDevData;
        }

        public void setExceptionDevData(List<ScanDeviceDate> exceptionDevData) {
            this.exceptionDevData = exceptionDevData;
        }
    }


    public ScanDate getScanData() {
        return scanData;
    }

    public void setScanData(ScanDate scanData) {
        this.scanData = scanData;
    }
}
