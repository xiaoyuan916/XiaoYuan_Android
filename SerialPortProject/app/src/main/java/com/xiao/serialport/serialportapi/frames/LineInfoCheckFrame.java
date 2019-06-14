package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 节站屏线路信息查询帧，车载机广播
 * <p>
 * Title: LineInfoCheckFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-19
 */
public class LineInfoCheckFrame extends BaseFrame {
	private static final long serialVersionUID = 4072362705164494130L;

	/**
	 * 包标识。
	 */
	private int frameFlag;

	/**
	 * 线路名称长度。
	 */
	private int lineNameLength;
	/**
	 * 线路名称。
	 */
	private String lineName;

	public static LineInfoCheckFrame processMessgae(byte[] message) {
		try {
			int offset = 7;
			LineInfoCheckFrame frame = new LineInfoCheckFrame();
			frame = BaseFrame.processBaseInfo(frame, message);

			frame.frameFlag = byteToInt(message[offset]);
			offset++;// 8
			frame.lineNameLength = Integer.parseInt(
					Utils.byteToHex(message[offset]), 16);
			offset++;// 9
			byte[] _info = new byte[frame.lineNameLength];
			System.arraycopy(message, offset, _info, 0, frame.lineNameLength);
			frame.lineName = new String(_info, "GB2312");

			return frame;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取包标识。
	 * 
	 * @return
	 */
	public int getFrameFlag() {
		return frameFlag;
	}

	public void setFrameFlag(int frameFlag) {
		this.frameFlag = frameFlag;
	}

	/**
	 * 获取线路名称长度。
	 * 
	 * @return
	 */
	public int getLineNameLength() {
		return lineNameLength;
	}

	public void setLineNameLength(int lineNameLength) {
		this.lineNameLength = lineNameLength;
	}

	/**
	 * 获取线路名称。
	 * 
	 * @return
	 */
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
		result = prime * result + frameFlag;
		result = prime * result
				+ ((lineName == null) ? 0 : lineName.hashCode());
		result = prime * result + lineNameLength;
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
		LineInfoCheckFrame other = (LineInfoCheckFrame) obj;
		if (frameFlag != other.frameFlag)
			return false;
		if (lineName == null) {
			if (other.lineName != null)
				return false;
		} else if (!lineName.equals(other.lineName))
			return false;
		if (lineNameLength != other.lineNameLength)
			return false;
		return true;
	}

}
