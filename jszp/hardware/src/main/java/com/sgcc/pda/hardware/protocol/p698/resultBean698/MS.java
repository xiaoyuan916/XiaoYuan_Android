package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZXS MS数据单元
 * 
 */
public class MS {

	/**
	 * MS单元的总长度
	 */
	private Integer totalLength;

	/**
	 * MS单元的选择项
	 */
	private Integer choice;

	/**
	 * 内部的数据集合
	 */
	private List<Object> dataUnits = new ArrayList<Object>();

	/**
	 * 内部集合数据单元个数，默认为0
	 */
	private int num = 0;

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Integer getChoice() {
		return choice;
	}

	public void setChoice(Integer choice) {
		this.choice = choice;
	}

	public List<Object> getDataUnits() {
		return dataUnits;
	}

	public void setDataUnits(List<Object> dataUnits) {
		this.dataUnits = dataUnits;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @param pos
	 * @param bytes
	 */
	public void valuAtion(int pos, byte[] bytes) {
		int length = 0;
		choice=new Integer(bytes[pos]);
		switch (choice) {
		case 0: // 无电能表,总长度为1个字节;
			this.setTotalLength(1);
			break;
		case 1: // 全部用户地址,总长度为1个字节
			this.setTotalLength(1);
			break;
		case 2: // 一组用户类型,单元数据类型为:unsigned 8位 一个字节
			length = 2;// 总长度初始化为2,一个字节为choice所占字节,一个字节为数据单元个数;
			num = bytes[pos + 1];
			for (int i = 0; i < num; i++) {
				int value = bytes[pos + length + i];
				dataUnits.add(value);
			}
			this.setTotalLength(length + 1 * num);
			break;
		case 3: // 一组用户地址 TSA
			length = 2;// 总长度初始化为2;
			num = bytes[pos + 1];
			for (int i = 0; i < num; i++) {
				TSA tsa = new TSA();
				tsa.setTotalLength(new Integer(bytes[pos+length])+1);//带定义长度的那个字节，为总长度
				byte[] tsaBytes=new byte[tsa.getTotalLength()];
				System.arraycopy(bytes, pos+length, tsaBytes, 0, tsaBytes.length);
				tsa.valuAtion(tsaBytes);
				dataUnits.add(tsa);
				length += tsa.getTotalLength();
			}
			this.setTotalLength(length);
			break;
		case 4: // 一组配置序号 long-unsigned 64位 8个字节
			length = 2;
			num = bytes[pos + 1];
			for (int i = 0; i < num; i++) {
				byte[] valueBytes = new byte[8];
				System.arraycopy(bytes, pos + length, valueBytes, 0,
						valueBytes.length);
				String hexString = CommUtils.byteToHexString(valueBytes);
				dataUnits.add(hexString);
				length += 8;
			}
			this.setTotalLength(length);
			break;
		case 5: // 一组用户类型区间 Region类型的unsigned数据区间
			length = 2;
			num = bytes[pos + 1];
			int unitLength = 3;// 数据单元长度：一个区间符号，两个unsigned数值，
			for (int i = 0; i < num; i++) {
				String valueStr = null;// 以字符串格式描述数据
				int innerChoice = bytes[pos + length];//Region类型选择的符号位
				int beforeValue = bytes[pos + length + 1];
				int afterValue = bytes[pos + length + 2];
				if (innerChoice == 0) {// 前闭后开 （0）
					valueStr = "[" + beforeValue + "," + afterValue+ ")";
				} else if (innerChoice == 1) {// 前开后闭（1）
					valueStr = "(" + beforeValue + "," + afterValue+ "]";
				} else if (innerChoice == 2) {// 前闭后闭（2）
					valueStr = "[" + beforeValue + "," + afterValue+ "]";
				} else if (innerChoice == 3) {// 前开后开（3）
					valueStr = "(" + beforeValue + "," + afterValue+ ")";
				}
				dataUnits.add(valueStr);
				length += unitLength;
			}
			this.setTotalLength(length);
			break;
		case 6: // 一组用户地址区间 Region类型的TSA数据区间
			length = 2;
			num = bytes[pos + 1];
			for (int i = 0; i < num; i++) {
				String valueStr = null;// 以字符串格式描述数据
				int innerChoice = bytes[pos + length];//Region类型选择的符号位
				//解析数据区间的首字节
				TSA beforeTsa = new TSA();
				beforeTsa.setTotalLength(new Integer(bytes[pos+length+1])+1);
				byte[] tsaBytes1 = new byte[beforeTsa.getTotalLength()];// 服务器地址
				System.arraycopy(bytes, pos + length + 1, tsaBytes1, 0,
						tsaBytes1.length);
				beforeTsa.valuAtion(tsaBytes1);
				length += beforeTsa.getTotalLength();
				//解析数据区间的末字节
				TSA afterTsa = new TSA();
				afterTsa.setTotalLength(new Integer(bytes[pos + length+1])+1);
				byte[] tsaBytes2 = new byte[afterTsa.getTotalLength()];// 服务器地址
				System.arraycopy(bytes, pos + length + 1, tsaBytes2, 0,
						tsaBytes2.length);
				afterTsa.valuAtion(tsaBytes2);
				length += afterTsa.getTotalLength();
				if (innerChoice == 0) {// 前闭后开 （0）
					valueStr = "[" + beforeTsa.toString() + "," + afterTsa.toString()+ ")";
				} else if (innerChoice == 1) {// 前开后闭（1）
					valueStr = "(" + beforeTsa.toString() + "," + afterTsa.toString()+ "]";
				} else if (innerChoice == 2) {// 前闭后闭（2）
					valueStr = "[" + beforeTsa.toString() + "," + afterTsa.toString()+ "]";
				} else if (innerChoice == 3) {// 前开后开（3）
					valueStr = "(" + beforeTsa.toString() + "," + afterTsa.toString()+ ")";
				}
				dataUnits.add(valueStr);
				length += 1;
			}
			this.setTotalLength(length);
			break;
		case 7: // 一组配置序号区间 Region类型的long-unsigned数据区间
			length = 2;
			num = bytes[pos + 1];
			int unitLength1 = 17;// 数据单元长度：一个区间符号，两个long-unsigned数值，
			for (int i = 0; i < num; i++) {
				String valueStr = null;// 以字符串格式描述数据
				int innerChoice = bytes[pos + length];//Region类型选择的符号位
				//解析第一个数据
				byte[] beforeBytes=new byte[8];
				System.arraycopy(bytes, pos + length+1, beforeBytes, 0,
						beforeBytes.length);
				String beforeHexString = CommUtils.byteToHexString(beforeBytes);
				//解析第二个数据
				byte[] afterBytes=new byte[8];
				System.arraycopy(bytes, pos + length+1+8, afterBytes, 0,
						afterBytes.length);
				String aterHexString = CommUtils.byteToHexString(afterBytes);
				//根据符号位拼接结果字符串
				if (innerChoice == 0) {// 前闭后开 （0）
					valueStr = "[" + beforeHexString + "," + aterHexString+ ")";
				} else if (innerChoice == 1) {// 前开后闭（1）
					valueStr = "(" + beforeHexString + "," + aterHexString+ "]";
				} else if (innerChoice == 2) {// 前闭后闭（2）
					valueStr = "[" + beforeHexString + "," + aterHexString+ "]";
				} else if (innerChoice == 3) {// 前开后开（3）
					valueStr = "(" + beforeHexString + "," + aterHexString+ ")";
				}
				dataUnits.add(valueStr);
				length += unitLength1;
			}
			this.setTotalLength(length);
			break;
		}
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		switch(choice){
			case 0:
				sb.append("0");
				break;
			case 1:
				sb.append("1");
				break;
			case 2:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
			case 3:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
			case 4:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
			case 5:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
			case 6:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
			case 7:
				for(int i=0;i<dataUnits.size();i++){
					sb.append(dataUnits.get(i).toString()+" ");
				}
				break;
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {
		//String v= "070201040000000122000004000000088881220104000000888801220400000088880122";
		String v="0604000604000000012206040000000122000604000000012206040000000122000604000000012206040000000122000604000000012206040000000122";
		System.out.println(v.length());
		MS ms = new MS();
		byte[] b = CommUtils.hex2Binary(v);
		ms.valuAtion(0, b);
		System.out.println(ms.toString());
		System.out.println(ms.getTotalLength());
	}
}
