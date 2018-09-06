package com.sgcc.pda.hardware.protocol.p698.structure.impl;


import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.TaskConfUnit;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import java.util.Map;
//   任务配置单元
public class Stru6013Impl  extends IStructure {
	@Override
	public  byte[]   getBytes(){
		TaskConfUnit tcu= (TaskConfUnit)this.obj.get(0);
		int  pos=0;
		byte  []  temp = new byte[1024];
		byte  []  value; 
		DataItem698 di  =  new DataItem698();
		temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(12))[0];  //结构体包含元素个数
		//任务ID    unsigned，
		  temp[pos++] =  StruUtils.getDataTypeByte("unsigned");
		  di.setDataType("unsigned");
		  di.setInputParam(tcu.getTaskId());
		  value = StruUtils.assValue(di);
		  System.arraycopy(value, 0, temp, pos, value.length);
		  pos +=value.length;
		//执行频率   TI，
		  temp[pos++] =  StruUtils.getDataTypeByte("TI");
		  di.setDataType("TI");
		  di.setInputParam(tcu.getActionrate().getTimeUnit()+"#"+tcu.getActionrate().getIntervalValue());
		  value = StruUtils.assValue(di);
		  System.arraycopy(value, 0, temp, pos, value.length);
		  pos +=value.length;
		//方案类型   enum
		/*{
		普通采集方案 （1），   事件采集方案 （2），
		透明方案     （3），   上报方案     （4），
		脚本方案     （5）
		}，*/
		 temp[pos++] =  StruUtils.getDataTypeByte("enum");
		 di.setDataType("enum");
		 di.setInputParam(tcu.getSchemeType());
		 value = StruUtils.assValue(di);
		 System.arraycopy(value, 0, temp, pos, value.length);
		 pos +=value.length;
		//方案编号    unsigned，
		 temp[pos++] =  StruUtils.getDataTypeByte("unsigned");
		 di.setDataType("unsigned");
		 di.setInputParam(tcu.getSchemeCode());
		 value = StruUtils.assValue(di);
		 System.arraycopy(value, 0, temp, pos, value.length);
		 pos +=value.length;
		//开始时间    date_time_s，
		 temp[pos++] =  StruUtils.getDataTypeByte("date_time_s");
		 di.setDataType("date_time_s");
		 di.setInputParam(tcu.getBeginTime());
		 value = StruUtils.assValue(di);
		 System.arraycopy(value, 0, temp, pos, value.length);
		 pos +=value.length;
		///结束时间    date_time_s，
		 temp[pos++] =  StruUtils.getDataTypeByte("date_time_s");
		 di.setDataType("date_time_s");
		 di.setInputParam(tcu.getEndTime());
		 value = StruUtils.assValue(di);
		 System.arraycopy(value, 0, temp, pos, value.length);
		 pos +=value.length;
		//延时        TI，
		  temp[pos++] =  StruUtils.getDataTypeByte("TI");
		  di.setDataType("TI");
		  di.setInputParam(tcu.getDelay().getTimeUnit()+"#"+tcu.getDelay().getIntervalValue());
		  value = StruUtils.assValue(di);
		  System.arraycopy(value, 0, temp, pos, value.length);
		  pos +=value.length;
		//执行优先级  enum{首要（1），必要（2），需要（3），可能（4）}，
		     temp[pos++] =  StruUtils.getDataTypeByte("enum");
			 di.setDataType("enum");
			 di.setInputParam(tcu.getActionPro());
			 value = StruUtils.assValue(di);
			 System.arraycopy(value, 0, temp, pos, value.length);
			 pos +=value.length;
		//状态        enum{正常（1），停用（2）}，
			 temp[pos++] =  StruUtils.getDataTypeByte("enum");
			 di.setDataType("enum");
			 di.setInputParam(tcu.getState());
			 value = StruUtils.assValue(di);
			 System.arraycopy(value, 0, temp, pos, value.length);
			 pos +=value.length;
		//任务开始前脚本id   long-unsigned，
			 temp[pos++] =  StruUtils.getDataTypeByte("long-unsigned");
			 di.setDataType("long-unsigned");
			 di.setInputParam(tcu.getTaskBeginBeforeScriptid());
			 value = StruUtils.assValue(di);
			 System.arraycopy(value, 0, temp, pos, value.length);
			 pos +=value.length;
		//任务完成后脚本id   long-unsigned，
			 temp[pos++] =  StruUtils.getDataTypeByte("long-unsigned");
			 di.setDataType("long-unsigned");
			 di.setInputParam(tcu.getTaskBeginAfterScriptid());
			 value = StruUtils.assValue(di);
			 System.arraycopy(value, 0, temp, pos, value.length);
			 pos +=value.length;
		//任务运行时段       structure，
			 temp[pos++] =  StruUtils.getDataTypeByte("structure");
		     temp[pos++] =  0x02;  //任务运行时段 包含元素个数
		     //类型
		     temp[pos++] =  StruUtils.getDataTypeByte("enum");
			 di.setDataType("enum");
			 di.setInputParam(tcu.getTaskRunType());
			 value = StruUtils.assValue(di);
			 System.arraycopy(value, 0, temp, pos, value.length);
			 pos +=value.length;
			// 时段表  array 时段
			 temp[pos++] =  StruUtils.getDataTypeByte("array");
			 temp[pos++] =  CommUtils.hex2Binary(Integer.toHexString(tcu.getTimeBlock().size()))[0];  //时段表元素个数
			 String[]   timeB  = new String[4];
			 for(String tb :  tcu.getTimeBlock()){
				 temp[pos++] =  StruUtils.getDataTypeByte("structure");
				 temp[pos++] =  0x04;  //时段包含元素个数
				 timeB  =  tb.split(":");
				 //起始小时  unsigned， //起始分钟  unsigned，  //结束小时  unsigned， //结束分钟  unsigned
					  for(int i=0; i<4; i++){
						     temp[pos++] =  StruUtils.getDataTypeByte("unsigned");
							 di.setDataType("unsigned");
							 di.setInputParam(timeB[i]);
							 value = StruUtils.assValue(di);
							 System.arraycopy(value, 0, temp, pos, value.length);
							 pos +=value.length;
					  }	 
			 }
		return  StruUtils.getByteData(temp, pos);
	}

	@Override
	public String parseStructure() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
	   

}
