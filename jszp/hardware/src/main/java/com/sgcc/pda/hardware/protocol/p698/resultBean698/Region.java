package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.Parser698;
import com.sgcc.pda.hardware.protocol.p698.ParserUpdata;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;

public class Region {
	
	public int valuAtion(int pos, byte[] bytes, StringBuffer sb, DataItem698 dataItem) {
		//第一个字节，ENUMERATED类型
		sb.append("|"+bytes[pos++]);
		//循环解析两个Data
		for(int i=0;i<2;i++){
			String type  = new Parser698().userCodeType(bytes[pos++]);
			if("structure".equals(type)||"array".equals(type)){
				int data=bytes[pos++];
				pos= ParserUpdata.handlerInner(bytes,data,pos,sb,dataItem);
			}else{
				pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,type, dataItem);
			}
		}
		return pos;
	}
}
