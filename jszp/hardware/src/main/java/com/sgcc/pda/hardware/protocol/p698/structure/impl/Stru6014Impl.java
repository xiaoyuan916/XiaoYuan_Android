package com.sgcc.pda.hardware.protocol.p698.structure.impl;

import com.sgcc.pda.hardware.protocol.p698.bean698.AcquScheme;
import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.TimeInterval;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

import java.util.List;
import java.util.Map;


//批量设置  、操作方法添加或更新一组普通采集方案
public class Stru6014Impl   extends IStructure {
	@Override
	public  byte[]   getBytes(){
		List<Object> list  = this.obj;
		StruUtils su =new  StruUtils();
		int  pos =0;  //游离下标
		byte  []  temp = new  byte[1024];
		byte  []  value; 
		DataItem698 di =  new DataItem698();
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];//设置、操作数组元素个数
		for(Object obj : list){
			AcquScheme as = (AcquScheme)obj;
			temp[pos++]=0x02;  //设置结构体数据类型
			temp[pos++]=0x06;  //结构体 成员数量
			//方案编号
			di.setDataType("unsigned");
			di.setInputParam(as.getSchemeCode());
			value  = StruUtils.assValue(di);
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0]; //组装数据类型
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//存储深度
			di.setDataType("long-unsigned");
			di.setInputParam(as.getStorageDep());
			value  = StruUtils.assValue(di);
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];//组装数据类型
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//采集方式  ---------数据类型 结构体 
			temp[pos++]=0x02; //结构体
			temp[pos++]=0x02; //采集方式 结构体数据个数
			//采集 方式
				di.setDataType("unsigned");
				di.setInputParam(as.getAcquType());
				//采集 方式的数据类型
				temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];
				//采集 方式 的数值
				temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(as.getAcquType())))[0];
				  //采集当前数据                      //按冻结时标采集
				if("0".equals(as.getAcquType())||"2".equals(as.getAcquType())){
					//采集内容
					temp[pos++] =0x00;  
				}else if("1".equals(as.getAcquType())){ //采集上第N次
					di.setDataType("unsigned");
					di.setInputParam(as.getAcquCont());
					//采集内容的数据类型
					temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];
					//采集内容值
					temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(as.getAcquCont())))[0];
				}else{// 按时标间隔采集
					TimeInterval ti = as.getTi();
					di.setDataType("TI");
					//组装TI数据类型
					temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];
					//组装时间间隔数据类型
					temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("enum"))))[0];
					di.setDataType("enum");
					di.setInputParam(ti.getTimeUnit());
					value = StruUtils.assValue(di);  //组装时间间隔单位
					System.arraycopy(value, 0, temp, pos, value.length);
					pos +=value.length;
					//组装时间间隔值
					di.setDataType("long-unsigned");
					di.setInputParam(ti.getIntervalValue());
					temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(di.getDataType()))))[0];   //组装数据类型
					value = StruUtils.assValue(di);   //组装间隔值
					System.arraycopy(value, 0, temp, pos, value.length);
					pos +=value.length;
				}
			//记录列选择   array  CSD
				//组装array数据类型  ？？？
				di.setDataType("array");  //设置数组数据类型
			    value = StruUtils.getObjDataType(di);
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				temp[pos++]= CommUtils.hex2Binary(Integer.toHexString(as.getRrb().getRecordRcse().size()))[0];  //数组元素个数
				value  =su.optRcsd(as.getRrb());
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
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
			//存储时标选择 
			    di.setDataType("enum");
			    di.setInputParam(as.getStorageTimeScale());
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
