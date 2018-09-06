package com.sgcc.pda.hardware.protocol.p698.structure.impl;

import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import java.util.Map;


//操作 采集档案配置表  方法130   相当于对添加类型的进行处理
public class Stru6000130Impl  extends IStructure {
	@Override
	public  byte[]   getBytes(){
		int  pos=0;
		byte  []  temp = new byte[1024];
		byte  []  value; 
		DataItem698 di  =  new DataItem698();
			AcquSchemeConfUnit ascu= (AcquSchemeConfUnit)this.obj.get(0);
			//配置序号  long-unsigned，
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getConfCode());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//扩展信息  Extended_object，  structure
			temp[pos++] = StruUtils.getDataTypeByte("structure");
			temp[pos++] = 0x04;
			//采集器地址  TSA，
			temp[pos++] = StruUtils.getDataTypeByte("TSA");
			di.setDataType("TSA");
			di.setInputParam(ascu.getColleAdd());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//资产号      octet-string，
			temp[pos++] = StruUtils.getDataTypeByte("octet-string");
			di.setDataType("octet-string");
			di.setInputParam(ascu.getAssetNum());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//PT          long-unsigned，
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getPt());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//CT          long-unsigned
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getCt());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//附属信息  Annex_object   structure
			//oad
			//data
			temp[pos++] = StruUtils.getDataTypeByte("array");
			if(0==ascu.getAnnex().size()){
				temp[pos++] = 0x0;
			}else{
				temp[pos++] =  CommUtils.hex2Binary(Integer.toHexString(ascu.getAnnex().size()))[0];
				byte  []  oad;
				for(Map<DataItem698, String> map : ascu.getAnnex()){
					temp[pos++] = StruUtils.getDataTypeByte("structure");
					temp[pos++] = 0x02; 
					for(Map.Entry<DataItem698, String>   entry:  map.entrySet()){
						DataItem698   di698 =(DataItem698)entry.getKey();
						temp[pos++] = StruUtils.getDataTypeByte("OAD");
						IPharesItem698 phares = Constant698.getiPharesItem698("OAD");
						phares.setObj(di698);
						oad = phares.getBytes();
						System.arraycopy(oad, 0, temp, pos, 4);
						pos+=4; //---------------OAD
						di.setDataType(di698.getDataType());
						di.setInputParam(entry.getValue());
						temp[pos++] = StruUtils.getDataTypeByte(di698.getDataType());
						value = StruUtils.assValue(di);
						System.arraycopy(value, 0, temp, pos, value.length);
						pos +=value.length;
					}
					
				}
			}
		
		return   StruUtils.getByteData(temp, pos);
	}

	@Override
	public String parseStructure() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	 

}
