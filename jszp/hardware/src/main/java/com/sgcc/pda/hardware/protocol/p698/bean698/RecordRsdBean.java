package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.List;
import java.util.Map;

//定义读取记录型对象属性的记录选择描述符属性
public class RecordRsdBean {
  
	  private String method;   //选择方法标识  1-10
	  private List<DataItem698> listItem;      //对象属性
	  private List<String> data;   //数值     用于方法1、    方法2、3中的数据间隔
	  //用于方法2、3
	  private List<Map<String,String>> beginData;  //起始值--------<数据类型、数据>
	  private List<Map<String,String>> endData;    //结束值--------<数据类型、数据>
	  
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
	  
	  //用于方法 4
	  private String startUpTime;   //采集启动时间
	  
	  //用于方法5
	  private String collStorTime;   //采集存储时间
	  
	  //用于方法 6、7、8
	  private String timeBegin;   //时间起始值
	  private String timeEnd;     //时间结束值次
	  
	  //时间间隔            
	  private List<TimeInterval> ti;
	  
	  private String record  ; //上N次记录
	  
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public List<DataItem698> getListItem() {
			return listItem;
		}
		public void setListItem(List<DataItem698> listItem) {
			this.listItem = listItem;
		}
		public List<String> getData() {
			return data;
		}
		public void setData(List<String> data) {
			this.data = data;
		}
		
		public List<Map<String, String>> getBeginData() {
			return beginData;
		}
		public void setBeginData(List<Map<String, String>> beginData) {
			this.beginData = beginData;
		}
		public List<Map<String, String>> getEndData() {
			return endData;
		}
		public void setEndData(List<Map<String, String>> endData) {
			this.endData = endData;
		}
		public String getMs() {
			return ms;
		}
		public void setMs(String ms) {
			this.ms = ms;
		}
		public String getStartUpTime() {
			return startUpTime;
		}
		public void setStartUpTime(String startUpTime) {
			this.startUpTime = startUpTime;
		}
		public String getCollStorTime() {
			return collStorTime;
		}
		public void setCollStorTime(String collStorTime) {
			this.collStorTime = collStorTime;
		}
		public String getTimeBegin() {
			return timeBegin;
		}
		public void setTimeBegin(String timeBegin) {
			this.timeBegin = timeBegin;
		}
		public String getTimeEnd() {
			return timeEnd;
		}
		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}
		public String getRecord() {
			return record;
		}
		public void setRecord(String record) {
			this.record = record;
		}
		public List<String> getMsData() {
			return msData;
		}
		public void setMsData(List<String> msData) {
			this.msData = msData;
		}
		public List<TimeInterval> getTi() {
			return ti;
		}
		public void setTi(List<TimeInterval> ti) {
			this.ti = ti;
		}
		public List<Region> getRegion() {
			return region;
		}
		public void setRegion(List<Region> region) {
			this.region = region;
		}
		
		
}
