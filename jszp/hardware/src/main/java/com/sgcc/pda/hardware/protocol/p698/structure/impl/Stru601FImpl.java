package com.sgcc.pda.hardware.protocol.p698.structure.impl;


import com.sgcc.pda.hardware.protocol.p698.bean698.AcquRule;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

//设置 采集规则  一个
public class Stru601FImpl extends IStructure {

	@Override
	public  byte[]   getBytes(){
		StruUtils su =new StruUtils();
		AcquRule ar= (AcquRule)this.obj.get(0);
		int  pos=0;
		byte  []  temp = new byte[1024];
		byte  []  value; 
		temp[pos++] = 0x02;  //结构体包含元素个数  --------OAD、对象的数据类型 已组装
		//组装数据列选择描述符  CSD，
		value  =su.optRcsd(ar.getRrb());	
		System.arraycopy(value, 0, temp, pos, value.length);
		pos +=value.length;
		//规则描述  structrue   3个 元素
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("structure"))))[0];
		temp[pos++] = 0x03;
		// AcqCmd_2007    structrue 2个 元素
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("structure"))))[0];
		temp[pos++] = 0x02;
        //   2007  ===主用ID
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("array"))))[0];
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(ar.getMaster2007().size()))[0];   //元素个数
		DataItem698 di;
		byte  []  v;
		for(String id : ar.getMaster2007()){
			  temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("octet-string"))))[0]; //数据类型
			  di = new DataItem698();
			  di.setDataType("octet-string");
			  di.setInputParam(id);
			  v = StruUtils.assValue(di);
			  System.arraycopy(v, 0, temp, pos, v.length);
			  pos +=v.length;
		}
	    // 2007  ===备用ID
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("array"))))[0];
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(ar.getSpare2007().size()))[0];   //元素个数
		for(String id : ar.getSpare2007()){
			  temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("octet-string"))))[0]; //数据类型
			  di = new DataItem698();
			  di.setDataType("octet-string");
			  di.setInputParam(id);
			  v = StruUtils.assValue(di);
			  System.arraycopy(v, 0, temp, pos, v.length);
			  pos +=v.length;
		}
		//AcqCmd_1997 
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("structure"))))[0];
		temp[pos++] = 0x02;
		 //   1997  ===主用ID
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("array"))))[0];
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(ar.getMaster1997().size()))[0];   //元素个数
		for(String id : ar.getMaster1997()){
			  temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("octet-string"))))[0]; //数据类型
			  di = new DataItem698();
			  di.setDataType("octet-string");
			  di.setInputParam(id);
			  v = StruUtils.assValue(di);
			  System.arraycopy(v, 0, temp, pos, v.length);
			  pos +=v.length;
		}
	    // 1997  ===备用ID
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("array"))))[0];
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(ar.getSpare1997().size()))[0];   //元素个数
		for(String id : ar.getSpare1997()){
			  temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("octet-string"))))[0]; //数据类型
			  di = new DataItem698();
			  di.setDataType("octet-string");
			  di.setInputParam(id);
			  v = StruUtils.assValue(di);
			  System.arraycopy(v, 0, temp, pos, v.length);
			  pos +=v.length;
		}
		
	   //	AcqCmd_Trans
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("structure"))))[0];
		temp[pos++] = 0x01;
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("octet-string"))))[0];
		di = new DataItem698();
		di.setDataType("octet-string");
		di.setInputParam(ar.getFrame());
		v = StruUtils.assValue(di);
		System.arraycopy(v, 0, temp, pos, v.length);
		pos +=v.length;
		return   StruUtils.getByteData(temp, pos);
	}
	
	
	@Override
	public String parseStructure() {
		// TODO Auto-generated method stub
		return null;
	}

}
