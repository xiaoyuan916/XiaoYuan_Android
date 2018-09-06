package com.sgcc.pda.hardware.protocol.p698.bean698;
//预连接对象
public class LinkBean {

	  //请求类型    0 登录    1 心跳    2 退出登录
	    private String reqType;
	  //心跳周期
	    private String heartCycle;
	  //请求时间  
	    private String reqTime;
	  
		public String getReqType() {
			return reqType;
		}
		public void setReqType(String reqType) {
			this.reqType = reqType;
		}
		public String getHeartCycle() {
			return heartCycle;
		}
		public void setHeartCycle(String heartCycle) {
			this.heartCycle = heartCycle;
		}
		public String getReqTime() {
			return reqTime;
		}
		public void setReqTime(String reqTime) {
			this.reqTime = reqTime;
		}
}
