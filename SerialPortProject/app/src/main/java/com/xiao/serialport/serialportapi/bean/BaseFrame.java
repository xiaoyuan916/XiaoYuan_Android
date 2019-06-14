package com.xiao.serialport.serialportapi.bean;


import com.xiao.serialport.serialportapi.Utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 数据帧基类，所有数据帧对象都应继承此类
 * <p>
 * Title: BaseFrame
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-6
 */
public class BaseFrame implements Serializable {

	private static final long serialVersionUID = -6119845862045581462L;

	/**
	 * 数据帧包头，“$$$$”的16进制
	 */
	protected static final String FRAMEHEADER = "24242424";

	/**
	 * 发送者地址
	 */
	protected Address address;

	/**
	 * 命令
	 */
	protected Order order;

	/**
	 * 数据帧长度
	 */
	protected int length;

	/**
	 * crc16校验
	 */
	protected byte[] crc16;

	public static BaseFrame processMessgae(byte[] message) {
		BaseFrame frame = new BaseFrame();
		return processBaseInfo(frame, message);
	}

	protected static <T extends BaseFrame> T processBaseInfo(T frame,
			byte[] message) {
		// 解析地址
		String add = Utils.byteToHex(message[4]);
		frame.address = Address.getAddress(add);
		// 解析命令
		String ord = Utils.byteToHex(message[5]);
		frame.order = Order.getOrder(ord);
		// 解析长度
		frame.length = byteToInt(message[6]);

		// 获取 当前信息下载帧 的crc16校验字节。
		frame.crc16 = Arrays.copyOfRange(message, message.length - 2,
				message.length);

		return frame;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getCrc16() {
		return crc16;
	}

	public void setCrc16(byte[] crc16) {
		this.crc16 = crc16;
	}

	/*************** Utils ********************/

	/**
	 * 字节转十进制数字
	 * 
	 * @author ShenYang
	 * @date 2015-8-19
	 * @param b
	 * @return
	 */
	protected static int byteToInt(byte b) {
		return Integer.parseInt(Utils.byteToHex(b), 16);
	}

	/**
	 * byte数组转gb2312字符串，支持转指定位置和长度
	 * 
	 * @author ShenYang
	 * @date 2015-8-19
	 * @param bytes
	 *            byte数组
	 * @param offset
	 *            数据在byte数组中下标起始位置
	 * @param length
	 *            数据长度
	 * @return
	 */
	protected static String bytesToGB2312(byte[] bytes, int offset, int length) {
		byte[] _name = new byte[length];
		System.arraycopy(bytes, offset, _name, 0, length);
		try {
			return new String(_name, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
