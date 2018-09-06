package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.Parser698;
import com.sgcc.pda.hardware.protocol.p698.ParserUpdata;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.phares.DATE_TIME_S;

/**
 * @author ZXS RSD数据类型
 */
public class RSD {

	public int valuAtion(int pos, byte[] bytes, StringBuffer sb, DataItem698 dataItem) {
		Integer choice = new Integer(bytes[pos++]);
		sb.append("|");
		byte[] byte1=null;
		switch (choice) {
			case 0: // 无电能表,总长度为1个字节;
				sb.append("0");
				break;
			case 1: // OAD+data类型
				OAD oad=new OAD();
				byte1 = new byte[oad.getTotalLength()];
				System.arraycopy(bytes, pos, byte1, 0, byte1.length);
				oad.valuAtion(byte1);
				sb.append(oad.toString());
				pos += oad.getTotalLength();
				String type  = new Parser698().userCodeType(bytes[pos++]);
				if("structure".equals(type)||"array".equals(type)){
					int data=bytes[pos++];
					pos= ParserUpdata.handlerInner(bytes,data,pos,sb,dataItem);
				}else{
					pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,type,dataItem);
				}
				break;
			case 2: // OAD+data+data+data类型
				OAD oad1=new OAD();
				byte1 = new byte[oad1.getTotalLength()];
				System.arraycopy(bytes, pos, byte1, 0, byte1.length);
				oad1.valuAtion(byte1);
				sb.append(oad1.toString());
				pos += oad1.getTotalLength();
				for(int i=0;i<3;i++){
					String type1  = new Parser698().userCodeType(bytes[pos++]);
					if("structure".equals(type1)||"array".equals(type1)){
						int data=bytes[pos++];
						pos=ParserUpdata.handlerInner(bytes,data,pos,sb,dataItem);
					}else{
						pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,type1,dataItem);
					}
				}
				break;
			case 3: // 多个OAD+data+data+data类型;
				int num=bytes[pos++];
				for(int k=0;k<num;k++){
					OAD oad2=new OAD();
					byte1 = new byte[oad2.getTotalLength()];
					System.arraycopy(bytes, pos, byte1, 0, byte1.length);
					oad2.valuAtion(byte1);
					sb.append(oad2.toString());
					pos += oad2.getTotalLength();
					for(int i=0;i<3;i++){
						String type1  = new Parser698().userCodeType(bytes[pos++]);
						if("structure".equals(type1)||"array".equals(type1)){
							int data=bytes[pos++];
							pos=ParserUpdata.handlerInner(bytes,data,pos,sb,dataItem);
						}else{
							pos=ParserUpdata.handlerCommUnit(bytes,pos,sb,type1,dataItem);
						}
					}
				}
				break;
			case 4: // 采集启动时间  date_time_s +  电能表集合    MS
				pos=handlerType45(bytes,pos,sb);
				break;
			case 5: // 采集存储时间  date_time_s，电能表集合    MS,方法同4;
				pos=handlerType45(bytes,pos,sb);
				break;
			case 6:
				  /*类型
				   * 采集启动时间起始值  date_time_s，
				  采集启动时间结束值  date_time_s，
				  时间间隔            TI，
				  电能表集合          MS*/
				pos=handlerType678(bytes,pos,sb);
				break;
			case 7: // 类型同6
				pos=handlerType678(bytes,pos,sb);
				break;
			case 8: // 类型同6
				pos=handlerType678(bytes,pos,sb);
				break;
			case 9: // unsigned类型,总长度为1个字节;
				sb.append(bytes[pos++]);
				break;
			case 10: //上n条记录  unsigned，电能表集合  MS
				sb.append(bytes[pos++]);
				//解析MS
				MS ms=new MS();//MS类型
				ms.valuAtion(pos, bytes);
				sb.append(" "+ms.toString());
				pos += ms.getTotalLength();
				break;
		}
		return pos;
	}
	private int handlerType45(byte[] bytes,int pos,StringBuffer sb){
		//解析date_time_s，
		byte[] byte1=new byte[7];
		System.arraycopy(bytes, pos, byte1, 0, byte1.length);
		DATE_TIME_S dts=new DATE_TIME_S();
		dts.setUpbyte(byte1);
		sb.append(dts.getResStr());
		pos+=7;
		//解析MS
		MS ms=new MS();//MS类型
		ms.valuAtion(pos, bytes);
		sb.append(" "+ms.toString());
		pos += ms.getTotalLength();
		return pos;
	}
	//case 6,7,8类型相同，抽取出来的方法
	private int handlerType678(byte[] bytes,int pos,StringBuffer sb){
		//解析第一个date_time_s，
		byte[] byte1=new byte[7];
		System.arraycopy(bytes, pos, byte1, 0, byte1.length);
		DATE_TIME_S dts=new DATE_TIME_S();
		dts.setUpbyte(byte1);
		sb.append(dts.getResStr()+" ");
		pos+=7;
		//解析第二个date_time_s，
		System.arraycopy(bytes, pos, byte1, 0, byte1.length);
		dts.setUpbyte(byte1);
		sb.append(dts.getResStr()+" ");
		pos+=7;
		//解析TI
		TI ti=new TI();
		byte1=new byte[ti.getTotalLength()];
		System.arraycopy(bytes, pos, byte1, 0, byte1.length);
		ti.valuAtion(byte1);
		if(ti.getValue()==0){
			sb.append(ti.getValue());
		}else {
			sb.append(ti.toString());
		}
		pos += ti.getTotalLength();
		//解析MS
		MS ms=new MS();//MS类型
		ms.valuAtion(pos, bytes);
		sb.append(" "+ms.toString());
		pos += ms.getTotalLength();
		return pos;
	}
}
