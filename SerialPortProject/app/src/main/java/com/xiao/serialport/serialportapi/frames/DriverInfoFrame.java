package com.xiao.serialport.serialportapi.frames;


import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 司机信息包
 * <p>
 * Title: DriverInfoFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Lcy
 * @date 2018-5-17
 */
public class DriverInfoFrame extends BaseFrame {

	/**
	 * 编号类型，1、物理卡号 2、员工编号（株洲专用） 3、逻辑号
	 */
	private int sort;
	/**
	 * 卡号
	 */
	private String strCardNum;
	/**
	 * 司机工号
	 */
	private String strDriverNum;
	/**
	 * 司机姓名
	 */
	private String strDriverName;

	/**
	 * 1、签到 2、签退
	 */
	private int carSort;

	public DriverInfoFrame() {
	}

	public static DriverInfoFrame processMessgae(byte[] message) {
		int offset = 7;
		DriverInfoFrame frame = new DriverInfoFrame();
		// 基本信息处理
		frame = BaseFrame.processBaseInfo(frame, message);
		// 编号类型，1、物理卡号 2、员工编号（株洲专用） 3、逻辑号
		frame.sort = byteToInt(message[offset]);
		offset += 1;
		// 1、签到 2、签退
		frame.carSort = byteToInt(message[offset]);
		offset += 1;
		// 卡号
		byte[] bCardNum = new byte[8];
		System.arraycopy(message, offset, bCardNum, 0, 8);
		frame.strCardNum = Utils.bytesToHexFun(bCardNum) + "";
		offset += 8;
		// 司机工号
		byte[] bDriverNum = new byte[3];
		System.arraycopy(message, offset, bDriverNum, 0, 3);
		frame.strDriverNum = Utils.bytes2Long(bDriverNum) + "";

		offset += 3;
		// 司机姓名
		frame.strDriverName = bytesToGB2312(message, offset, 32);
		return frame;
	}

	/**
	 * 编号类型，1、物理卡号 2、员工编号（株洲专用） 3、逻辑号
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * 编号类型，1、物理卡号 2、员工编号（株洲专用） 3、逻辑号
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getStrCardNum() {
		return strCardNum;
	}

	public void setStrCardNum(String strCardNum) {
		this.strCardNum = strCardNum;
	}

	public String getStrDriverNum() {
		return strDriverNum;
	}

	public void setStrDriverNum(String strDriverNum) {
		this.strDriverNum = strDriverNum;
	}

	public String getStrDriverName() {
		return strDriverName;
	}

	public void setStrDriverName(String strDriverName) {
		this.strDriverName = strDriverName;
	}

	/**
	 * 1、签到 2、签退
	 */
	public int getCarSort() {
		return carSort;
	}

	/**
	 * 1、签到 2、签退
	 */
	public void setCarSort(int carSort) {
		this.carSort = carSort;
	}

}
