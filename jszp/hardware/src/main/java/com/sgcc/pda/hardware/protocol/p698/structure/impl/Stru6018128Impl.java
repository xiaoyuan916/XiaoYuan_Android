package com.sgcc.pda.hardware.protocol.p698.structure.impl;

import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.TransScheme;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import java.util.Map;


//操作透明方案集 方法128 添加一组报文
public class Stru6018128Impl  extends IStructure {

	@Override
	public  byte[]   getBytes(){
		TransScheme ts= (TransScheme)this.obj.get(0);
		int  pos=0;
		byte  []  temp = new byte[1024];
		byte  []  value; 
		DataItem698 di = new DataItem698();
			//方案编号
			temp[pos++]= StruUtils.getDataTypeByte("unsigned");
			di.setDataType("unsigned");
			di.setInputParam(ts.getSchemeId());
			value  =  StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//通信地址
			temp[pos++]=StruUtils.getDataTypeByte("TSA");   
			di.setDataType("TSA");
			di.setInputParam(ts.getMailAdd());
			value  =  StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			TransScheme  trs=ts.getSchemeCont().get(0);
				//方案控制标志
				temp[pos++]=StruUtils.getDataTypeByte("structure"); 
				temp[pos++]=0x04;  //方案控制标志 包含元素个数
				//上报透明方案结果并等待后续报文
				temp[pos++]=StruUtils.getDataTypeByte("bool"); 
				di.setDataType("bool");
				di.setInputParam(trs.getReportFrameWate());
				value  =  StruUtils.assValue(di);
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				//等待后续报文超时时间（秒）
				temp[pos++]=StruUtils.getDataTypeByte("long-unsigned"); 
				di.setDataType("long-unsigned");
				di.setInputParam(trs.getWaitOvertime());
				value  =  StruUtils.assValue(di);
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				//结果比对标识
				temp[pos++]=StruUtils.getDataTypeByte("enum"); 
				di.setDataType("enum");
				di.setInputParam(trs.getResAlignMark());
				value  =  StruUtils.assValue(di);
				System.arraycopy(value, 0, temp, pos, value.length);
				pos +=value.length;
				//结果比对参数
				temp[pos++]=StruUtils.getDataTypeByte("structure"); 
				temp[pos++]=0x03;  //结果比对参数 包含元素个数
				//特征字节      unsigned，
				 temp[pos++]=StruUtils.getDataTypeByte("unsigned"); 
				 di.setDataType("unsigned");
				 di.setInputParam(trs.getResAlignParam());
				 value  =  StruUtils.assValue(di);
				 System.arraycopy(value, 0, temp, pos, value.length);
				 pos +=value.length;
				//截取开始      long-unsigned，
				 temp[pos++]=StruUtils.getDataTypeByte("long-unsigned"); 
				 di.setDataType("long-unsigned");
				 di.setInputParam(trs.getSubBegin());
				 value  =  StruUtils.assValue(di);
				 System.arraycopy(value, 0, temp, pos, value.length);
				 pos +=value.length;
				//截取长度      long-unsigned
				 temp[pos++]=StruUtils.getDataTypeByte("long-unsigned"); 
				 di.setDataType("long-unsigned");
				 di.setInputParam(trs.getSubLength());
				 value  =  StruUtils.assValue(di);
				 System.arraycopy(value, 0, temp, pos, value.length);
				 pos +=value.length;
				 //方案报文集
				 temp[pos++]=StruUtils.getDataTypeByte("array"); 
				 temp[pos++]= CommUtils.hex2Binary(Integer.toHexString(trs.getScheneFrameColle().size()))[0];
				 Map<String,  String> map  = trs.getScheneFrameColle();
				 for(Map.Entry<String, String>  entry : map.entrySet()){
					 temp[pos++]=StruUtils.getDataTypeByte("structure"); 
					 temp[pos++]=0x02;  //方案报文 包含元素个数
					// 报文序号  unsigned，
					 temp[pos++]=StruUtils.getDataTypeByte("unsigned"); 
					 di.setDataType("unsigned");
					 di.setInputParam(entry.getKey());
					 value  =  StruUtils.assValue(di);
					 System.arraycopy(value, 0, temp, pos, value.length);
					 pos +=value.length;
					//报文内容  octet-string
					 temp[pos++]=StruUtils.getDataTypeByte("octet-string"); 
					 di.setDataType("octet-string");
					 di.setInputParam(entry.getValue());
					 value  =  StruUtils.assValue(di);
					 System.arraycopy(value, 0, temp, pos, value.length);
					 pos +=value.length;
				 }
			
		 return   StruUtils.getByteData(temp, pos);
	}
	@Override
	public String parseStructure() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	   

}
