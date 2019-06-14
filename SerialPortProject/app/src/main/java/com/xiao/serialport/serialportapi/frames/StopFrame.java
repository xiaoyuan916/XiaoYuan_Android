package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.bean.BaseFrame;

import java.util.Arrays;

/**
 * 报站信息帧，车载机广播
 * <p>
 * Title: LineInfoFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-6
 */
public class StopFrame extends BaseFrame {
	private static final long serialVersionUID = 3590013989222514725L;

	/**
	 * 上先行，0：上行 1：下行
	 */
	private int direction;
	/**
	 * 进出站类型，0：进站 1：出站
	 */
	private int stationFlag;
	/**
	 * 预留
	 */
	private byte[] yuLiu;
	/**
	 * 当前站点编号
	 */
	private int currStation;
	/**
	 * 线路名称长度
	 */
	private int lineNameLength;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 开门标记，1：左侧开门 2：右侧开门<br>
	 * 2015年9月10日15:19:05 更改成了 1，左侧开门； 0右侧开门。
	 */
	private int doorFlag;
	/**
	 * 附件信息长度
	 */
	private int infoLength;
	/**
	 * 附加信息，GB2312
	 */
	private String info;

	public StopFrame() {
	}

	public static StopFrame processMessgae(byte[] message) {
		int offset = 7;
		StopFrame frame = new StopFrame();
		// 基本信息处理
		frame = BaseFrame.processBaseInfo(frame, message);
		// 方向
		frame.direction = byteToInt(message[offset]);
		offset++;
		// 进站类型
		frame.stationFlag = byteToInt(message[offset]);
		offset++;
		// 预留
		frame.yuLiu = new byte[5];
		System.arraycopy(message, offset, frame.yuLiu, 0, frame.yuLiu.length);
		offset += 5;
		// 当前站点编号
		frame.currStation = byteToInt(message[offset]);
		offset++;
		// 汉字线路号长度
		frame.lineNameLength = byteToInt(message[offset]);
		offset++;
		// 汉字线路号
		frame.lineName = bytesToGB2312(message, offset, frame.lineNameLength);
		offset += frame.lineNameLength;
		// 开门标记
		frame.doorFlag = byteToInt(message[offset]);
		offset++;
		// 附加信息长度
		frame.infoLength = byteToInt(message[offset]);
		offset++;
		// 附加信息
		frame.info = bytesToGB2312(message, offset, frame.infoLength);
		offset += frame.infoLength;
		return frame;
	}

	/**
	 * @return 上先行，0：上行 1：下行。
	 */
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return 进出站类型，0：进站 1：出站
	 */
	public int getStationFlag() {
		return stationFlag;
	}

	public void setStationFlag(int stationFlag) {
		this.stationFlag = stationFlag;
	}

	public byte[] getYuLiu() {
		return yuLiu;
	}

	public void setYuLiu(byte[] yuLiu) {
		this.yuLiu = yuLiu;
	}
	
	/**
	 * @return 当前站号。
	 */
	public int getCurrStation() {
		return currStation;
	}

	public void setCurrStation(int currStation) {
		this.currStation = currStation;
	}

	/**
	 * 获取 开门标记，1：左侧开门 2：右侧开门<br>
	 * 2015年9月10日15:19:05 更改成了 1，左侧开门； 0右侧开门。
	 * 
	 * @return
	 */
	public int getDoorFlag() {
		return doorFlag;
	}

	public void setDoorFlag(int doorFlag) {
		this.doorFlag = doorFlag;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getInfoLength() {
		return infoLength;
	}

	public void setInfoLength(int infoLength) {
		this.infoLength = infoLength;
	}

	public int getLineNameLength() {
		return lineNameLength;
	}

	public void setLineNameLength(int lineNameLength) {
		this.lineNameLength = lineNameLength;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currStation;
		result = prime * result + direction;
		result = prime * result + doorFlag;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + infoLength;
		result = prime * result
				+ ((lineName == null) ? 0 : lineName.hashCode());
		result = prime * result + lineNameLength;
		result = prime * result + stationFlag;
		result = prime * result + Arrays.hashCode(yuLiu);
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
		StopFrame other = (StopFrame) obj;
		if (currStation != other.currStation)
			return false;
		if (direction != other.direction)
			return false;
		if (doorFlag != other.doorFlag)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (infoLength != other.infoLength)
			return false;
		if (lineName == null) {
			if (other.lineName != null)
				return false;
		} else if (!lineName.equals(other.lineName))
			return false;
		if (lineNameLength != other.lineNameLength)
			return false;
		if (stationFlag != other.stationFlag)
			return false;
		if (!Arrays.equals(yuLiu, other.yuLiu))
			return false;
		return true;
	}

}
