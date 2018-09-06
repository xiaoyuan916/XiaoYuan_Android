package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * @author ZXS
 *	时间间隔数据单元
 */
public class TI {
	
	/**
	 * 占据的总长度,默认为3个字节
	 */
	private Integer totalLength=3;
	/**
	 * 时间值：类型为long-unsigned,两个字节
	 */
	private Integer value;
	/**
	 * 时间单位:一个字节
	 */
	private Integer units;
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	
	public Integer getUnits() {
		return units;
	}
	public void setUnits(Integer units) {
		this.units = units;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	/**
	 * @param bytes
	 * 解析字节，为各属性赋值
	 */
	public void valuAtion(byte[] bytes){
		
		int choice=bytes[0];
		this.setUnits(choice);
		byte[] valueBytes=new byte[2];
		System.arraycopy(bytes, 1, valueBytes, 0, valueBytes.length);
		String hexString = CommUtils.byteToHexString(valueBytes);
		this.setValue(Integer.valueOf(hexString, 16));
	}
	
	@Override
	public String toString() {
		return ""+units+" "+value;
	}
}
