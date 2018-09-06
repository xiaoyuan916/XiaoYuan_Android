package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * @author ZXS
 *OMD类型数据
 */
public class OMD {
	/**
	 * OMD单元的总长度，默认为4个字节
	 */
	private Integer totalLength=4;
	
	/**
	 * 16位串表示值
	 */
	private String value;
	
	
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @param bytes
	 * 解析上行报文的OMD字节，为OMD赋值
	 */
	public void valuAtion(byte[] bytes){
		this.setValue(CommUtils.byteToHexString(bytes));
	}
	@Override
	public String toString() {
		return this.getValue();
	}
	/*public static void main(String[] args) {
		//20 00 02 00
		String v= "20000200";
		byte[] b = CommUtils.hex2Binary(v);
		OMD omd=new OMD();
		omd.valuAtion(b);
		System.out.println(omd);
		
	}*/
}
