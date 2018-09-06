package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.ArrayList;
import java.util.List;

//采集规则
public class AcquRule {
	
	  //数据列选择描述符  CSD，
	 private   RecordRcsdBean   rrb;
	/** 
	    * 2007主用ID
	    *
	    * @generated
	    */
	  private List<String> master2007 = new ArrayList<String>();
	   
	   /** 
	    * 2007备用ID
	    *
	    * @generated
	    */
	   private List<String> spare2007  = new ArrayList<String>();
	   
	   /** 
	    * 1997主用ID
	    *
	    * @generated
	    */
	   private List<String> master1997= new ArrayList<String>();
	   
	   /** 
	    * 1997备用ID
	    *
	    * @generated
	    */
	   private List<String> spare1997= new ArrayList<String>();
	   
	   /** 
	    * FRAME
	    *
	    * @generated
	    */
	   private String frame;

	public RecordRcsdBean getRrb() {
		return rrb;
	}

	public void setRrb(RecordRcsdBean rrb) {
		this.rrb = rrb;
	}

	public List<String> getMaster2007() {
		return master2007;
	}

	public void setMaster2007(List<String> master2007) {
		this.master2007 = master2007;
	}

	public List<String> getSpare2007() {
		return spare2007;
	}

	public void setSpare2007(List<String> spare2007) {
		this.spare2007 = spare2007;
	}

	public List<String> getMaster1997() {
		return master1997;
	}

	public void setMaster1997(List<String> master1997) {
		this.master1997 = master1997;
	}

	public List<String> getSpare1997() {
		return spare1997;
	}

	public void setSpare1997(List<String> spare1997) {
		this.spare1997 = spare1997;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}
	   
       
}
