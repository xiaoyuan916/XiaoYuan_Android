package com.sgcc.pda.hardware.protocol.p698.bean698;


import com.sgcc.pda.hardware.protocol.p698.utils.IGetByte;

public abstract class IPharesItem698   implements IGetByte {
	
	
	//输入参数值
	private String inputValue;
	
	//上行报文数据
	private   byte []   upbyte;
	//解析获取到的数据
	public abstract String getResStr();
	
	public Object obj;
	
	public byte[] getBytes(){
		throw new RuntimeException("IPharesItem698数据格式组装类：[" + this.getClass().getName() + "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
	//	return null;
	}



	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}


	public byte[] getUpbyte() {
		return upbyte;
	}

	public void setUpbyte(byte[] upbyte) {
		this.upbyte = upbyte;
	}


	public Object getObj() {
		return obj;
	}


	public void setObj(Object obj) {
		this.obj = obj;
	}

	
	
}
