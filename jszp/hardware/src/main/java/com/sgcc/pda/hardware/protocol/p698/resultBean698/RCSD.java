package com.sgcc.pda.hardware.protocol.p698.resultBean698;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZXS
 *	RCSD数据类型，是CSD的集合
 */
public class RCSD {
	/**
	 * RCSD单元的总长度
	 */
	private Integer totalLength;
	
	/**
	 * CSD序列
	 */
	private List<CSD> csds=new ArrayList<CSD>();

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public List<CSD> getCsds() {
		return csds;
	}

	public void setCsds(List<CSD> csds) {
		this.csds = csds;
	}
	
	/**
	 * @param num
	 * 通过读取数据字节中CSD的个数，计算总长度
	 */
	public void setTotalLengthByByte(int num) {
		this.totalLength = 1+num;
	}
	
	/**
	 * @param bytes
	 * 解析所占的字节数组，给各属性赋值
	 */
	public void valuAtion(byte[] bytes){
		int num=bytes[0];
		for(int i = 0;i<num;i++){
			CSD csd=new CSD();
			csd.valuAtion(bytes[1+i]);
			csds.add(csd);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		if(csds.size()==0){
			sb.append("0");
		}else{
			for(int i=0;i<csds.size();i++){
				if(i==csds.size()-1){
					sb.append(csds.get(i).toString());
				}else{
					sb.append(csds.get(i).toString()+" ");
				}
				
			}
		}
		return sb.toString();
	}
	
	
	
}
