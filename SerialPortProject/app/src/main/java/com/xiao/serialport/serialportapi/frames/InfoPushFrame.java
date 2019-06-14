package com.xiao.serialport.serialportapi.frames;


import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

import java.io.UnsupportedEncodingException;

/**
 * 车载机信息推送帧，广播
 * <p>
 * Title: InfoPushFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-14
 */
public class InfoPushFrame extends BaseFrame {
	private static final long serialVersionUID = 1668977823219680622L;

	/**
	 * 信息编号<br>
	 * 
	 * <pre>
	 * 0x00(0)：	天气预报（静态信息，需存储）
	 * 0x01-0x02(1-2):	即时存储信息（静态信息，需存储）
	 * 0x03-0x07(3-7):	保留
	 * 0x08-0x0F(8-15):	报站器触发的服务用语（静态信息，需存储）
	 * 0X7F(127)：	全部静态广告删除（信息内容为1字节ASCII空白字符）
	 * 0x80(128):	动态信息（不需存储，掉电丢失）
	 * </pre>
	 */
	private int infoNo;
	/**
	 * 信息长度
	 */
	private int infoLength;
	/**
	 * 信息内容
	 */
	private String info;

	public InfoPushFrame() {
	};

	public static InfoPushFrame processMessgae(byte[] message) {
		InfoPushFrame frame = new InfoPushFrame();
		frame = BaseFrame.processBaseInfo(frame, message);
		frame.infoNo = Integer.parseInt(Utils.byteToHex(message[7]), 16);

		frame.infoLength = Integer.parseInt(Utils.byteToHex(message[8]), 16);

		try {
			if (frame.infoNo == 0) {
				// 0x00(0)： 天气预报（静态信息，需存储）
				// 不做处理
				byte[] temp = new byte[frame.infoLength];
				System.arraycopy(message, 9, temp, 0, temp.length);
				frame.info = new String(temp, "GB2312");

			} else if (frame.infoNo == 1 || frame.infoNo == 2) {
				// 0x01-0x02(1-2): 即时存储信息（静态信息，需存储）
				// 不做处理
				byte[] temp = new byte[frame.infoLength];
				System.arraycopy(message, 9, temp, 0, temp.length);
				frame.info = new String(temp, "GB2312");

			} else if (3 <= frame.infoNo && frame.infoNo <= 7) {
				// 0x03-0x07(3-7): 保留
				// 不做处理
				byte[] temp = new byte[frame.infoLength];
				System.arraycopy(message, 9, temp, 0, temp.length);
				frame.info = new String(temp, "GB2312");
				// ---------------------------------------------------

			} else if (8 <= frame.infoNo && frame.infoNo <= 15) {
				// 0x08-0x0F(8-15): 报站器触发的服务用语（静态信息，需存储）
				// 由于静态信息内容字节数组的前1个字节有其他用途，所以先忽略掉这1个字节，只截取信息文本内容。
				byte[] temp = new byte[frame.infoLength - 1];
				System.arraycopy(message, /* 8，NY觉得应该是9 */9 + 1, temp, 0,
						temp.length);
				frame.info = new String(temp, "GB2312");

			} else if (frame.infoNo == 127) {
				// 0X7F(127)： 全部静态广告删除（信息内容为1字节ASCII空白字符）

			} else if (frame.infoNo == 128) {
				// 0x80(128): 动态信息（不需存储，掉电丢失）
				// 由于动态信息内容字节数组的前两个字节有其他用途，所以先忽略掉这两个字节，只截取信息文本内容。
				byte[] temp = new byte[frame.infoLength];
				System.arraycopy(message, 9, temp, 0, temp.length);
				frame.info = new String(temp, "GB2312");
			}

			// 获取 当前信息下载帧 的校验字节。
			// frame.crc16 = new byte[2];
			// System.arraycopy(message, 9 + frame.infoLength, frame.crc16, 0,
			// 2);
			// 或者
			// frame.crc16 = Arrays.copyOfRange(message, message.length-2,
			// message.length);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return frame;
	}

	/**
	 * 获取信息编号<br>
	 * 
	 * <pre>
	 * 0x00(0)：	天气预报（静态信息，需存储）
	 * 0x01-0x02(1-2):	即时存储信息（静态信息，需存储）
	 * 0x03-0x07(3-7):	保留
	 * 0x08-0x0F(8-15):	报站器触发的服务用语（静态信息，需存储）
	 * 0X7F(127)：	全部静态广告删除（信息内容为1字节ASCII空白字符）
	 * 0x80(128):	动态信息（不需存储，掉电丢失）
	 * </pre>
	 */
	public int getInfoNo() {
		return infoNo;
	}

	public void setInfoNo(int infoNo) {
		this.infoNo = infoNo;
	}

	public int getInfoLength() {
		return infoLength;
	}

	public void setInfoLength(int infoLength) {
		this.infoLength = infoLength;
	}

	/**
	 * 获取信息内容
	 * 
	 * @return
	 */
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + infoLength;
		result = prime * result + infoNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoPushFrame other = (InfoPushFrame) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (infoLength != other.infoLength)
			return false;
		if (infoNo != other.infoNo)
			return false;
		return true;
	}

}
