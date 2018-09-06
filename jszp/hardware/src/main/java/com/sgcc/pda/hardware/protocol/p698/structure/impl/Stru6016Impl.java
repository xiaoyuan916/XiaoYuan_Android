package com.sgcc.pda.hardware.protocol.p698.structure.impl;


import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.EventConnScheme;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

import java.util.List;
import java.util.Map;

// 批量设置、操作事件采集方案
public class Stru6016Impl   extends IStructure {

	@Override
	public  byte[]   getBytes(){
		StruUtils su =new  StruUtils();
		int  pos =0;  //游离下标
		List<Object> list = this.obj;
		byte  []  temp = new  byte[1024];
		byte  []  value; 
		DataItem698 di =  new DataItem698();
		//数组元素对象个数
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
		for(Object object : list){
			EventConnScheme as = (EventConnScheme)object;
			temp[pos++]=0x02;  //设置结构体数据类型
			temp[pos++]=0x05;  //结构体 成员数量
			//方案编号
			di.setDataType("unsigned");
			di.setInputParam(as.getSchemeCode());
			value  = StruUtils.assValue(di);
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0]; //组装数据类型
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//采集的事件数据
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("array"))))[0];//组装数据类型
			List<String> data =   as.getAcqEvent();
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(data.size()))[0];//数组元素个数
			for(String d :  data){
				di.setDataType("ROAD");
				di.setInputParam(d);
				value  = StruUtils.assValue(di);
				temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];//组装数据类型
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
			}
		    //电能表集合
				di.setDataType("MS");  //设置数组数据类型
			    value = StruUtils.getObjDataType(di);
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				if(Integer.parseInt(as.getMs())<5){
					value = su.optionMs(as.getMs(), as.getMsData());    //（ms编号，   MS集合数据）
				}else{
					value = su.optionMsRegion(as.getMs(), as.getRegion());    //（ms编号，   MS区间集合数据）
				}
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				//上报标识
			    di.setDataType("bool");
			    di.setInputParam(as.getReportFlag());
			    temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];   //组装数据类型
				value = StruUtils.assValue(di);  
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				//存储深度
			    di.setDataType("long-unsigned");
			    di.setInputParam(as.getStrdeep());
			    temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];   //组装数据类型
				value = StruUtils.assValue(di);   //组装间隔值
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
		}
		
		return StruUtils.getByteData(temp, pos);
	}

	@Override
	public String parseStructure() {
		
		return null;
	}

	

}
