package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//透明方案
public class TransScheme {
	
	  //方案内容集
	  private List<TransScheme> schemeCont = new ArrayList<TransScheme>();
	   /** 
	    * 方案编号
	    *
	    * @generated
	    */
	   private String schemeId;
	   
	   
	   /** 
	    * 通讯地址
	    *
	    * @generated
	    */
	   private String mailAdd;
	   
	   /** 
	    * 开始前脚本id
	    *
	    * @generated
	    */
	   private String beginBefourId;
	   
	   /** 
	    * 完成后脚本id
	    *
	    * @generated
	    */
	   private String finishAfterId;
	   
	   /** 
	    * 上报透明方案结果并等待后续报文
	    *
	    * @generated
	    */
	   private String reportFrameWate;
	   
	   /** 
	    * 等待后续报文超时时间
	    *
	    * @generated
	    */
	   private String waitOvertime;
	   
	   /** 
	    * 结果比对标识
	    *
	    * @generated
	    */
	   private String resAlignMark;
	   
	   /** 
	    * 特征字节
	    *
	    * @generated
	    */
	   private String resAlignParam;
	   
	   /** 
	    * 截取开始
	    *
	    * @generated
	    */
	   private String subBegin;
	   
	   /** 
	    * 截取长度
	    *
	    * @generated
	    */
	   private String subLength;
	   
	   /** 
	    * 方案报文集  <报文序号, 报文内容>
	    *
	    * @generated
	    */
	   private Map<String,String> scheneFrameColle = new HashMap<String,String>();
	   
	   /** 
	    * 存储深度
	    *
	    * @generated
	    */
	   private String strogDeep;

	public List<TransScheme> getSchemeCont() {
		return schemeCont;
	}

	public void setSchemeCont(List<TransScheme> schemeCont) {
		this.schemeCont = schemeCont;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getMailAdd() {
		return mailAdd;
	}

	public void setMailAdd(String mailAdd) {
		this.mailAdd = mailAdd;
	}

	public String getBeginBefourId() {
		return beginBefourId;
	}

	public void setBeginBefourId(String beginBefourId) {
		this.beginBefourId = beginBefourId;
	}

	public String getFinishAfterId() {
		return finishAfterId;
	}

	public void setFinishAfterId(String finishAfterId) {
		this.finishAfterId = finishAfterId;
	}

	public String getReportFrameWate() {
		return reportFrameWate;
	}

	public void setReportFrameWate(String reportFrameWate) {
		this.reportFrameWate = reportFrameWate;
	}

	public String getWaitOvertime() {
		return waitOvertime;
	}

	public void setWaitOvertime(String waitOvertime) {
		this.waitOvertime = waitOvertime;
	}

	public String getResAlignMark() {
		return resAlignMark;
	}

	public void setResAlignMark(String resAlignMark) {
		this.resAlignMark = resAlignMark;
	}

	public String getResAlignParam() {
		return resAlignParam;
	}

	public void setResAlignParam(String resAlignParam) {
		this.resAlignParam = resAlignParam;
	}

	public String getSubBegin() {
		return subBegin;
	}

	public void setSubBegin(String subBegin) {
		this.subBegin = subBegin;
	}

	public String getSubLength() {
		return subLength;
	}

	public void setSubLength(String subLength) {
		this.subLength = subLength;
	}

	public Map<String, String> getScheneFrameColle() {
		return scheneFrameColle;
	}

	public void setScheneFrameColle(Map<String, String> scheneFrameColle) {
		this.scheneFrameColle = scheneFrameColle;
	}

	public String getStrogDeep() {
		return strogDeep;
	}

	public void setStrogDeep(String strogDeep) {
		this.strogDeep = strogDeep;
	}  
	   
       
}
