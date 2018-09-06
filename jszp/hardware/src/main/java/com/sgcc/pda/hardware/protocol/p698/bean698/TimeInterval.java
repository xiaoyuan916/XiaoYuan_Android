package com.sgcc.pda.hardware.protocol.p698.bean698;

  //定义时间间隔数据类型类
public class TimeInterval {
	  
		 /*	 秒（0）     分（1）     时（2）     日（3）  月（4）  年（5）*/
	   private String timeUnit;   //时间单位
	   private String intervalValue;  //时间间隔值
	   
		public String getTimeUnit() {
			return timeUnit;
		}
		public void setTimeUnit(String timeUnit) {
			this.timeUnit = timeUnit;
		}
		public String getIntervalValue() {
			return intervalValue;
		}
		public void setIntervalValue(String intervalValue) {
			this.intervalValue = intervalValue;
		}
	   
	   

}
