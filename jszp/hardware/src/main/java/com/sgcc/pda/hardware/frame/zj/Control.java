package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月20日 上午10:22:48
 * 
 * @Description: 控制码
 * 
 */

public class Control {

	private byte value;

	public Control(byte value) {
		super();
		this.value = value;
	}

	/**
	 * 
	 * @param direction
	 *            传送方向,取值0;1
	 * @param flag
	 *            异常标志,取值0;1
	 * @param function
	 *            功能码
	 * @return
	 */
	public byte encode(byte direction, byte flag, byte function) {
		value = 0;
		setDirection(direction);
		setFlag(flag);
		setFunction(function);
		return value;
	}

	private int getDirection() {
		return (value >> 7) & 0x1;
	}

	private void setDirection(byte direction) {
		if (direction == 1) {
			value = (byte) (value | 0x80);
		}

	}

	public int getFlag() {
		return (value >> 6) & 0x1;
	}

	private void setFlag(byte flag) {
		if (flag == 1) {
			value = (byte) (value | 0x40);
		}
	}

	private int getFunction() {
		return value & 0x3F;
	}

	private void setFunction(byte function) {
		value = (byte) (value | function);
	}

	public String decode() {
		StringBuilder sb = new StringBuilder();

		int direction = getDirection();
		String d = direction == 0 ? "命令帧" : "应答帧";
		sb.append("传输方向:" + d + "\n");

		int flag = getFlag();
		String f = flag == 0 ? "确认帧" : "否认帧";
		sb.append("异常标志:" + f + "\n");

		int function = getFunction();
		sb.append("功能码:" + ControlCode.getName((byte) function) + "\n");

		return sb.toString();
	}

}
