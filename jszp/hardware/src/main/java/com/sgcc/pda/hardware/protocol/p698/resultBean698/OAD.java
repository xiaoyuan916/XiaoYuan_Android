package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * @author ZXS
 * 定义此类的目的，是在解析报文时，把OAD类型的数据以对象的方式存储
 *
 */
public class OAD {
	
	/**
	 * OAD单元的总长度，默认为4个字节
	 */
	private Integer totalLength=4;
	
	/**
	 * 以16位的字符串表示结果
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
	@Override
	public String toString() {
		return value;
	}
	/**
	 * @param bytes
	 * 解析上行报文的OAD字节，为OAD赋值
	 */
	public void valuAtion(byte[] bytes){
		this.setValue(CommUtils.byteToHexString(bytes));
	}
	
	/*public static void main(String[] args) {
		//20 00 02 00
		String v= "20000200";
		byte[] b = CommUtils.hex2Binary(v);
		OAD oad=new OAD();
		oad.valuAtion(b);
		System.out.println(oad);
		
	}*/
}
