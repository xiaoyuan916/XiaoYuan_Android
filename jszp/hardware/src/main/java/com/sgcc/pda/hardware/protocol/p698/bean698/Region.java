package com.sgcc.pda.hardware.protocol.p698.bean698;

//区间数据类型类
public class Region {
	
		  //单位  前闭后开  （0）、前开后闭 （1）、前闭后闭  （2）、前开后开  （3）
		  private String unit;
		  //起始值
		  private String beginData;
		  //结束值
		  private String endData;

		public String getUnit() {
			return unit;
		}
	
		public void setUnit(String unit) {
			this.unit = unit;
		}
	
		public String getBeginData() {
			return beginData;
		}
	
		public void setBeginData(String beginData) {
			this.beginData = beginData;
		}
	
		public String getEndData() {
			return endData;
		}
	
		public void setEndData(String endData) {
			this.endData = endData;
		}
	  
	  

}
