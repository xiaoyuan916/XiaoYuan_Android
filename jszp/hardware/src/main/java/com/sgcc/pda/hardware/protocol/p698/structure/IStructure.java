package com.sgcc.pda.hardware.protocol.p698.structure;


import com.sgcc.pda.hardware.protocol.p698.utils.IGetByte;

import java.util.List;

//  处理 structure 数据类型 的 抽象类
public abstract class IStructure  implements IGetByte {
	
	public List<Object> obj;
	 //组装  structure  数据类型数据
	public    byte []    getBytes(){
		throw new RuntimeException("IPharesItem698数据格式组装类：[" + this.getClass().getName() + "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
	}
	
	//解析   structure  数据类型数据
	public  abstract String parseStructure();

	public List<Object> getObj() {
		return obj;
	}

	public void setObj(List<Object> obj) {
		this.obj = obj;
	}
	

}
