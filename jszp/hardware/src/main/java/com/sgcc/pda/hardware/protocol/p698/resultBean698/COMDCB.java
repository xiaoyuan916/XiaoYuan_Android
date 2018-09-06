package com.sgcc.pda.hardware.protocol.p698.resultBean698;


/**
 * @author ZXS
 *	COMDCB类型的数据
 */
public class COMDCB {
	/**
	 * 总长度,默认为5个字节
	 */
	private Integer totalLength=5;
	/**
	 * 波特率
	 */
	private Integer baudrate;
	/**
	 * 校验位 {无校验（0），奇校验（1），偶校验（2）}
	 */
	private Integer checkBit;
	/**
	 * 数据位 {5（5），6（6），7（7），8（8）}，
	 */
	private Integer dataBit;
	/**
	 * 停止位 {1（1），2（2）}，
	 */
	private Integer stopBit;
	/**
	 * 流控 {无(0)，硬件(1)，软件(2)}
	 */
	private Integer fluidControl;
	
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	public Integer getBaudrate() {
		return baudrate;
	}
	public void setBaudrate(Integer baudrate) {
		this.baudrate = baudrate;
	}
	public Integer getCheckBit() {
		return checkBit;
	}
	public void setCheckBit(Integer checkBit) {
		this.checkBit = checkBit;
	}
	public Integer getDataBit() {
		return dataBit;
	}
	public void setDataBit(Integer dataBit) {
		this.dataBit = dataBit;
	}
	public Integer getStopBit() {
		return stopBit;
	}
	public void setStopBit(Integer stopBit) {
		this.stopBit = stopBit;
	}
	public Integer getFluidControl() {
		return fluidControl;
	}
	public void setFluidControl(Integer fluidControl) {
		this.fluidControl = fluidControl;
	}
	/**
	 * @param bytes
	 * 通过字节数组，为其属性赋值
	 */
	public void valuAtion(byte[] bytes){
		this.setBaudrate(new Integer(bytes[0]));
		this.setCheckBit(new Integer(bytes[0]));
		this.setDataBit(new Integer(bytes[0]));
		this.setStopBit(new Integer(bytes[0]));
		this.setFluidControl(new Integer(bytes[0]));
	}
	@Override
	public String toString() {
		return ""+this.getBaudrate()+this.getCheckBit()+this.getDataBit()+this.getStopBit()+this.getFluidControl();
	}
	
	
}
