package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.ArrayList;
import java.util.List;


//任务配置单元
public class TaskConfUnit {
	  /** 
	    * 任务ID
	    *
	    * @generated
	    */
	   private String taskId;
	   
	   //执行频率
	   private    TimeInterval    actionrate;
	   
	   /** 
	    * 方案类型
	    *
	    * @generated
	    */
	   private String schemeType;
	   
	   /** 
	    * 方案编号
	    *
	    * @generated
	    */
	   private String schemeCode;
	   
	   /** 
	    * 开始时间
	    *
	    * @generated
	    */
	   private String beginTime;
	   
	   /** 
	    * 结束时间
	    *
	    * @generated
	    */
	   private String endTime;
	   
	   //延时
	   private   TimeInterval   delay;
	   
	   /** 
	    * 执行优先级
	    *
	    * @generated
	    */
	   private String actionPro;
	   
	   /** 
	    * 状态
	    *
	    * @generated
	    */
	   private String state;
	   
	   /** 
	    * 任务开始前脚本id
	    *
	    * @generated
	    */
	   private String taskBeginBeforeScriptid;
	   
	   /** 
	    * 任务开始后脚本id
	    *
	    * @generated
	    */
	   private String taskBeginAfterScriptid;
	   
	   /** 
	    * 任务运行时段类型
	    *
	    * @generated
	    */
	   private String taskRunType;
	   //时段
	   private List<String> timeBlock  =  new ArrayList<String>();
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public TimeInterval getActionrate() {
		return actionrate;
	}
	public void setActionrate(TimeInterval actionrate) {
		this.actionrate = actionrate;
	}
	public String getSchemeType() {
		return schemeType;
	}
	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public TimeInterval getDelay() {
		return delay;
	}
	public void setDelay(TimeInterval delay) {
		this.delay = delay;
	}
	public String getActionPro() {
		return actionPro;
	}
	public void setActionPro(String actionPro) {
		this.actionPro = actionPro;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTaskBeginBeforeScriptid() {
		return taskBeginBeforeScriptid;
	}
	public void setTaskBeginBeforeScriptid(String taskBeginBeforeScriptid) {
		this.taskBeginBeforeScriptid = taskBeginBeforeScriptid;
	}
	public String getTaskBeginAfterScriptid() {
		return taskBeginAfterScriptid;
	}
	public void setTaskBeginAfterScriptid(String taskBeginAfterScriptid) {
		this.taskBeginAfterScriptid = taskBeginAfterScriptid;
	}
	public String getTaskRunType() {
		return taskRunType;
	}
	public void setTaskRunType(String taskRunType) {
		this.taskRunType = taskRunType;
	}
	public List<String> getTimeBlock() {
		return timeBlock;
	}
	public void setTimeBlock(List<String> timeBlock) {
		this.timeBlock = timeBlock;
	}
	   
	   

}
