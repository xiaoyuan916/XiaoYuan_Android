package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 线路站点列表下载帧，多帧,车载机推送
 * <p>
 * Title: StationsResponse
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-14
 */
public class LineInfoPushFrame extends BaseFrame implements
        Comparable<LineInfoPushFrame> {

	private static final long serialVersionUID = 8259563007733912717L;

	private int id;

	/**
	 * 站点名称版本
	 */
	private int lienNameVersion;

	/**
	 * 线路名称长度
	 */
	private int lineNameLength;

	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 上先行，0：上行 1：下行
	 */
	private int direction;
	/**
	 * 站点总数
	 */
	private int stationCount;

	/**
	 * 站点编号
	 */
	private int stationNo;
	/**
	 * 站点名称长度
	 */
	private int stationNameLength;
	/**
	 * 站点名称
	 */
	private String stationName;

	public static LineInfoPushFrame processMessgae(byte[] message) {
		try {
			LineInfoPushFrame frame = new LineInfoPushFrame();
			frame = BaseFrame.processBaseInfo(frame, message);

			frame.lienNameVersion = Integer.parseInt(
					Utils.byteToHex(message[7]), 16);
			frame.lineNameLength = Integer.parseInt(
					Utils.byteToHex(message[8]), 16);

			byte[] _info = new byte[frame.lineNameLength];
			System.arraycopy(message, 9, _info, 0, frame.lineNameLength);
			frame.lineName = new String(_info, "GB2312");

			frame.direction = Integer.parseInt(
					Utils.byteToHex(message[frame.lineNameLength + 9]), 16);
			frame.stationCount = Integer.parseInt(
					Utils.byteToHex(message[frame.lineNameLength + 10]), 16);
			frame.stationNo = Integer.parseInt(
					Utils.byteToHex(message[frame.lineNameLength + 11]), 16);
			frame.stationNameLength = Integer.parseInt(
					Utils.byteToHex(message[frame.lineNameLength + 12]), 16);

			_info = new byte[frame.stationNameLength];
			System.arraycopy(message, frame.lineNameLength + 13, _info, 0,
					frame.stationNameLength);
			frame.stationName = new String(_info, "GB2312");

			return frame;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLienNameVersion() {
		return lienNameVersion;
	}

	public void setLienNameVersion(int lienNameVersion) {
		this.lienNameVersion = lienNameVersion;
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

	/**
	 * 上下行，0：上行 1：下行
	 * 
	 * @return
	 */
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getStationCount() {
		return stationCount;
	}

	public void setStationCount(int stationCount) {
		this.stationCount = stationCount;
	}

	public int getStationNo() {
		return stationNo;
	}

	public void setStationNo(int stationNo) {
		this.stationNo = stationNo;
	}

	public int getStationNameLength() {
		return stationNameLength;
	}

	public void setStationNameLength(int stationNameLength) {
		this.stationNameLength = stationNameLength;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direction;
		result = prime * result + lienNameVersion;
		result = prime * result
				+ ((lineName == null) ? 0 : lineName.hashCode());
		result = prime * result + lineNameLength;
		result = prime * result + stationCount;
		result = prime * result
				+ ((stationName == null) ? 0 : stationName.hashCode());
		result = prime * result + stationNameLength;
		result = prime * result + stationNo;
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
		LineInfoPushFrame other = (LineInfoPushFrame) obj;
		if (direction != other.direction)
			return false;
		if (lienNameVersion != other.lienNameVersion)
			return false;
		if (lineName == null) {
			if (other.lineName != null)
				return false;
		} else if (!lineName.equals(other.lineName))
			return false;
		if (lineNameLength != other.lineNameLength)
			return false;
		if (stationCount != other.stationCount)
			return false;
		if (stationName == null) {
			if (other.stationName != null)
				return false;
		} else if (!stationName.equals(other.stationName))
			return false;
		if (stationNameLength != other.stationNameLength)
			return false;
		if (stationNo != other.stationNo)
			return false;
		return true;
	}

	@Override
	public int compareTo(LineInfoPushFrame another) {

		return this.getStationNo() - another.getStationNo();
	}

	@Override
	public String toString() {
		return "LineInfoPushFrame [id=" + id + ", lienNameVersion="
				+ lienNameVersion + ", lineNameLength=" + lineNameLength
				+ ", lineName=" + lineName + ", direction=" + direction
				+ ", stationCount=" + stationCount + ", stationNo=" + stationNo
				+ ", stationNameLength=" + stationNameLength + ", stationName="
				+ stationName + "]";
	}

}
