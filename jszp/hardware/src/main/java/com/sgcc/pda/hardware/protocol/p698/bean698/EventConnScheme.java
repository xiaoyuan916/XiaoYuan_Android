package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.List;

//事件采集方案
public class EventConnScheme {
	
	//方案编号
	private String schemeCode;
	 //采集的事件数据
	private List<String> acqEvent;
	 /*
	  电表集合  MS
	  无电能表		[0]，
	  全部用户地址  	[1]，
	  组用户类型  	[2] 	SEQUENCE OF unsigned，
	  组用户地址  	[3] 	SEQUENCE OF TSA，
	  组配置序号  	[4] 	SEQUENCE OF long-unsigned，
	  组用户类型区间 [5] SEQUENCE OF Region，
	  组用户地址区间 [6] SEQUENCE OF Region，
	  组配置序号区间 [7] SEQUENCE OF Region
	  */
	  private String ms;  //电能表集合   表示选择执行那种电表集合
	  private List<String> msData;   //电能表集合数据
	  private List<Region> region;   //数据区间

	  //上报标识
	  private String reportFlag;
	  
	  //存储深度
	  private String strdeep;

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public List<String> getAcqEvent() {
		return acqEvent;
	}

	public void setAcqEvent(List<String> acqEvent) {
		this.acqEvent = acqEvent;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public List<String> getMsData() {
		return msData;
	}

	public void setMsData(List<String> msData) {
		this.msData = msData;
	}

	public List<Region> getRegion() {
		return region;
	}

	public void setRegion(List<Region> region) {
		this.region = region;
	}

	public String getReportFlag() {
		return reportFlag;
	}

	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	public String getStrdeep() {
		return strdeep;
	}

	public void setStrdeep(String strdeep) {
		this.strdeep = strdeep;
	}
	  
	  
}
