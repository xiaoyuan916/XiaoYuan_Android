package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 线路同步帧，车载机广播.
 * <p>
 * Title: LineSyncFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-14
 */
public class LineSyncFrame extends BaseFrame {

	private static final long serialVersionUID = -1821443039934473342L;

	/**
	 * 包标识，同一个包重发时应保持一致
	 */
	private int flag;

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
	 * 首站信息长度
	 */
	private int firstStationLength;
	/**
	 * 首站信息
	 */
	private String firstStationName;
	/**
	 * 末站信息长度
	 */
	private int endStationLength;
	/**
	 * 末站信息
	 */
	private String endStationName;
	/**
	 * 首末班时间长度
	 */
	private int timeLength;
	/**
	 * 首末班时间
	 */
	private String time;

	public static LineSyncFrame processMessgae(byte[] message) {
		try {
			int offset = 7;
			LineSyncFrame frame = new LineSyncFrame();
			frame = BaseFrame.processBaseInfo(frame, message);

			frame.flag = Integer.parseInt(Utils.byteToHex(message[offset]), 16);
			offset++;
			frame.lienNameVersion = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;

			frame.lineNameLength = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;
			byte[] _info = new byte[frame.lineNameLength];
			System.arraycopy(message, offset, _info, 0, frame.lineNameLength);
			frame.lineName = new String(_info, "GB2312");
			offset += frame.lineNameLength;

			frame.firstStationLength = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;
			_info = new byte[frame.firstStationLength];
			System.arraycopy(message, offset, _info, 0,
					frame.firstStationLength);
			frame.firstStationName = new String(_info, "GB2312");
			offset += frame.firstStationLength;

			frame.endStationLength = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;
			_info = new byte[frame.endStationLength];
			System.arraycopy(message, offset, _info, 0, frame.endStationLength);
			frame.endStationName = new String(_info, "GB2312");
			offset += frame.endStationLength;

			frame.timeLength = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;
			_info = new byte[frame.timeLength];
			System.arraycopy(message, offset, _info, 0, frame.timeLength);
			frame.time = new String(_info, "GB2312");
			offset += frame.timeLength;

			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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

	public int getFirstStationLength() {
		return firstStationLength;
	}

	public void setFirstStationLength(int firstStationLength) {
		this.firstStationLength = firstStationLength;
	}

	public String getFirstStationName() {
		return firstStationName;
	}

	public void setFirstStationName(String firstStationName) {
		this.firstStationName = firstStationName;
	}

	public int getEndStationLength() {
		return endStationLength;
	}

	public void setEndStationLength(int endStationLength) {
		this.endStationLength = endStationLength;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public int getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(int timeLength) {
		this.timeLength = timeLength;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endStationLength;
		result = prime * result
				+ ((endStationName == null) ? 0 : endStationName.hashCode());
		result = prime * result + firstStationLength;
		result = prime
				* result
				+ ((firstStationName == null) ? 0 : firstStationName.hashCode());
		result = prime * result + flag;
		result = prime * result + lienNameVersion;
		result = prime * result
				+ ((lineName == null) ? 0 : lineName.hashCode());
		result = prime * result + lineNameLength;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + timeLength;
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
		LineSyncFrame other = (LineSyncFrame) obj;
		if (endStationLength != other.endStationLength)
			return false;
		if (endStationName == null) {
			if (other.endStationName != null)
				return false;
		} else if (!endStationName.equals(other.endStationName))
			return false;
		if (firstStationLength != other.firstStationLength)
			return false;
		if (firstStationName == null) {
			if (other.firstStationName != null)
				return false;
		} else if (!firstStationName.equals(other.firstStationName))
			return false;
		if (flag != other.flag)
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
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (timeLength != other.timeLength)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LineSyncFrame [flag=" + flag + ", lienNameVersion="
				+ lienNameVersion + ", lineNameLength=" + lineNameLength
				+ ", lineName=" + lineName + ", firstStationLength="
				+ firstStationLength + ", firstStationName=" + firstStationName
				+ ", endStationLength=" + endStationLength
				+ ", endStationName=" + endStationName + ", timeLength="
				+ timeLength + ", time=" + time + "]";
	}

}
