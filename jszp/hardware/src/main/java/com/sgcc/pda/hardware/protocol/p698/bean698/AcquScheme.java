package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.List;

//采集方案
public class AcquScheme {
	
	 //方案编号
	private String schemeCode;
	 //存储深度
	private String storageDep;
	 //采集方式 : 采集类型  
	private String AcquType;
	//采集方式 :   采集内容
	private String AcquCont;
	//当采集方式为 3时 应用
	private  TimeInterval   ti;
	 //记录列选择   array  CSD
	private   RecordRcsdBean   rrb;
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
	 //存储时标选择 enum
	 /* 未定义    （0），
	  任务开始时间    （1），
	  相对当日0点0分    （2），
	  相对上日23点59分  （3），
	  相对上日0点0分    （4），
	  相对当月1日0点0分（5），
	  数据冻结时标        （6）*/
	 private String storageTimeScale;
	 
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getStorageDep() {
		return storageDep;
	}
	public void setStorageDep(String storageDep) {
		this.storageDep = storageDep;
	}
	public String getAcquType() {
		return AcquType;
	}
	public void setAcquType(String acquType) {
		AcquType = acquType;
	}
	public RecordRcsdBean getRrb() {
		return rrb;
	}
	public void setRrb(RecordRcsdBean rrb) {
		this.rrb = rrb;
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
	public String getStorageTimeScale() {
		return storageTimeScale;
	}
	public void setStorageTimeScale(String storageTimeScale) {
		this.storageTimeScale = storageTimeScale;
	}
	public String getAcquCont() {
		return AcquCont;
	}
	public void setAcquCont(String acquCont) {
		AcquCont = acquCont;
	}
	public TimeInterval getTi() {
		return ti;
	}
	public void setTi(TimeInterval ti) {
		this.ti = ti;
	}
    
	 
	 
}
