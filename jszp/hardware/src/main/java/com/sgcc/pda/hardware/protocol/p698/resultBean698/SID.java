package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * @author ZXS
 *	SID数据类型
 */
public class SID {
	/**
	 * 总长度
	 */
	private Integer totalLength;
	/**
	 * 标识，以16位字符串表示结果,类型为double-long-unsigned,4个字节长
	 */
	private String flag;
	/**
	 * 附加数据，以16位字符串表示结果
	 */
	private String attachData;
	
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAttachData() {
		return attachData;
	}
	public void setAttachData(String attachData) {
		this.attachData = attachData;
	}
	/**
	 * @param bytes
	 * 通过字节数组，为属性赋值
	 */
	public void valuAtion(byte[] bytes,int pos){
		byte[] flagBytes=new byte[4];//标记字节数组
		System.arraycopy(bytes, pos, flagBytes, 0, flagBytes.length);
		int length=bytes[pos+4];//附加数据字节长度
		byte[] adBytes=new byte[length];//附加数据字节数组
		System.arraycopy(bytes, pos+5, adBytes, 0, adBytes.length);
		this.setFlag(CommUtils.byteToHexString(flagBytes));
		this.setAttachData(CommUtils.byteToHexString(adBytes));
		this.setTotalLength(4+1+length);
	}
	@Override
	public String toString() {
		return this.getFlag()+" "+this.getAttachData();
	}
	
}
