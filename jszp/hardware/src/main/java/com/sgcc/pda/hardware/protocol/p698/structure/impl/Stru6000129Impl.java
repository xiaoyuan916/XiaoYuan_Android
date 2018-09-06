package com.sgcc.pda.hardware.protocol.p698.structure.impl;


import com.sgcc.pda.hardware.protocol.p698.bean698.AcquSchemeConfUnit;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import java.util.Map;

//操作 采集档案配置表  方法129
public class Stru6000129Impl  extends IStructure {
	@Override
	public  byte[]   getBytes(){
		//List<Object>   list  = this.obj;
		int  pos=0;
		byte  []  temp = new byte[1024];
		byte  []  value; 
		DataItem698 di  =  new DataItem698();
			AcquSchemeConfUnit ascu= (AcquSchemeConfUnit)this.obj.get(0);
			//temp[pos++] = 0x04;  //采集档案配置单元  包含元素个数
			//配置序号  long-unsigned，
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getConfCode());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//基本信息  Basic_object，  structure
			temp[pos++] = StruUtils.getDataTypeByte("structure");
			temp[pos++] = CommUtils.hex2Binary(Integer.toHexString(10))[0];;
			//通信地址  TSA，
			temp[pos++] = StruUtils.getDataTypeByte("TSA");
			di.setDataType("TSA");
			di.setInputParam(ascu.getMailAdd());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//波特率    enum
			/*{
			300bps（0），  600bps（1），    1200bps（2），
			2400bps（3）， 4800bps（4），   7200bps（5），
			9600bps（6）， 19200bps（7），  38400bps（8），
			57600bps（9），115200bps（10），自适应（255）
			}，*/
			temp[pos++] = StruUtils.getDataTypeByte("enum");
			di.setDataType("enum");
			di.setInputParam(ascu.getBaudRate());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			/*规约类型  enum
			{
			  未知 (0)，
			DL/T645-1997（1），
			DL/T645-2007（2），
			DL/T698.45（3），
			CJ/T 188-2004（4）
			}，*/
			temp[pos++] = StruUtils.getDataTypeByte("enum");
			di.setDataType("enum");
			di.setInputParam(ascu.getProtocolType());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//端口      OAD，
			temp[pos++] = StruUtils.getDataTypeByte("OAD");
			byte  []  port= CommUtils.hex2Binary(ascu.getPort());
			System.arraycopy(port, 0, temp, pos, port.length);
			pos +=port.length;
			//通信密码  octet-string，
			temp[pos++] = StruUtils.getDataTypeByte("octet-string");
			di.setDataType("octet-string");
			di.setInputParam(ascu.getMailPass());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//费率个数  unsigned，
			temp[pos++] = StruUtils.getDataTypeByte("unsigned");
			di.setDataType("unsigned");
			di.setInputParam(ascu.getRateNum());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//用户类型  unsigned，
			temp[pos++] = StruUtils.getDataTypeByte("unsigned");
			di.setDataType("unsigned");
			di.setInputParam(ascu.getUserType());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			/*接线方式  enum
			{
			   未知（0），
			   单相（1），
			   三相三线（2），
			   三相四线（3）
			}，*/
			temp[pos++] = StruUtils.getDataTypeByte("enum");
			di.setDataType("enum");
			di.setInputParam(ascu.getConnMode());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//额定电压    long-unsigned(换算-1，单位V),
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getRatedVoltage());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			//额定电流    long-unsigned(换算-1，单位A)
			temp[pos++] = StruUtils.getDataTypeByte("long-unsigned");
			di.setDataType("long-unsigned");
			di.setInputParam(ascu.getRatedCurrent());
			value = StruUtils.assValue(di);
			System.arraycopy(value, 0, temp, pos, value.length);
			pos +=value.length;
			
		return   StruUtils.getByteData(temp, pos);
	}

	@Override
	public String parseStructure() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	 

}
