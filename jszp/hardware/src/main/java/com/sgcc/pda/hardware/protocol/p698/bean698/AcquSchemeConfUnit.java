package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//采集档案配置单元
public class AcquSchemeConfUnit {
	
	  /** 
	    * 配置序号
	    *
	    * @generated
	    */
	   private String confCode;
	   
	   /** 
	    * 通讯地址
	    *
	    * @generated
	    */
	   private String mailAdd;
	   
	   /** 
	    * 波特率
	    *
	    * @generated
	    */
	   private String baudRate;
	   
	   /** 
	    * 规约类型
	    *
	    * @generated
	    */
	   private String protocolType;
	   
	   /** 
	    * 端口
	    *
	    * @generated
	    */
	   private String port;
	   
	   /** 
	    * 通信密码
	    *
	    * @generated
	    */
	   private String mailPass;
	   
	   /** 
	    * 费率个数
	    *
	    * @generated
	    */
	   private String rateNum;
	   
	   /** 
	    * 用户类型
	    *
	    * @generated
	    */
	   private String userType;
	   
	   /** 
	    * 接线方式
	    *
	    * @generated
	    */
	   private String connMode;
	   
	   /** 
	    * 额定电压
	    *
	    * @generated
	    */
	   private String ratedVoltage;
	   
	   /** 
	    * 额定电流
	    *
	    * @generated
	    */
	   private String ratedCurrent;
	   
	   /** 
	    * 采集器地址
	    *
	    * @generated
	    */
	   private String colleAdd;
	   
	   /** 
	    * 资产号
	    *
	    * @generated
	    */
	   private String assetNum;
	   
	   /** 
	    * PT
	    *
	    * @generated
	    */
	   private String pt;
	   
	   /** 
	    * CT
	    *
	    * @generated
	    */
	   private String ct;
	   
	  // 附属信息
	   private List<Map<DataItem698, String>> annex =  new ArrayList<Map<DataItem698, String>>();

	public String getConfCode() {
		return confCode;
	}

	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}

	public String getMailAdd() {
		return mailAdd;
	}

	public void setMailAdd(String mailAdd) {
		this.mailAdd = mailAdd;
	}

	public String getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}

	public String getRateNum() {
		return rateNum;
	}

	public void setRateNum(String rateNum) {
		this.rateNum = rateNum;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getConnMode() {
		return connMode;
	}

	public void setConnMode(String connMode) {
		this.connMode = connMode;
	}

	public String getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(String ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public String getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public String getColleAdd() {
		return colleAdd;
	}

	public void setColleAdd(String colleAdd) {
		this.colleAdd = colleAdd;
	}

	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public List<Map<DataItem698, String>> getAnnex() {
		return annex;
	}

	public void setAnnex(List<Map<DataItem698, String>> annex) {
		this.annex = annex;
	}
	   
	   

}
