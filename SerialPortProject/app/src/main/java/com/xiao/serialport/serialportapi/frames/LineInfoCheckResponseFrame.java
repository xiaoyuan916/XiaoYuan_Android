package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.DigitalTrans;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

import java.io.UnsupportedEncodingException;

/**
 * 节站屏线路信息应答帧，节站屏接收到查询包以后发送
 * <p>
 * Title: LineInfoCheckResponseFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-19
 */
public class LineInfoCheckResponseFrame extends BaseFrame {
	private static final long serialVersionUID = -5168904541845964L;
	/**
	 * 包标识
	 */
	private int frameFlag;
	/**
	 * 自检状态 0：设备正常
	 */
	private int selfStatus;
	/**
	 * 线路名称长度
	 */
	private int lineNameLength;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 线路名称版本
	 */
	private int lienNameVersion;

	public LineInfoCheckResponseFrame() {
	}

	public int getFrameFlag() {
		return frameFlag;
	}

	/**
	 * 设置 包标识。
	 * 
	 * @param frameFlag
	 */
	public void setFrameFlag(int frameFlag) {
		this.frameFlag = frameFlag;
	}

	public int getSelfStatus() {
		return selfStatus;
	}

	/**
	 * 设置自检状态。<br>
	 * 自检状态 0：设备正常
	 * 
	 * @param selfStatus
	 */
	public void setSelfStatus(int selfStatus) {
		this.selfStatus = selfStatus;
	}

	public int getLineNameLength() {
		return lineNameLength;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getLienNameVersion() {
		return lienNameVersion;
	}

	public void setLienNameVersion(int lienNameVersion) {
		this.lienNameVersion = lienNameVersion;
	}

	public String toHEXString() {
		byte[] _lineName = null;
		try {
			_lineName = lineName.getBytes("GB2312");
			lineNameLength = _lineName.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		StringBuffer sb = new StringBuffer(FRAMEHEADER);
		sb.append(address.getAddressValue());
		sb.append(order.getOrderValue());
		// 长度
		sb.append(Utils.intTohex(4 + 1 + 1 + 1 + 1 + 2 + 1 + lineNameLength + 1
				+ 2));
		sb.append(Utils.intTohex(frameFlag));
		String ss = Utils.intTohex(selfStatus);
		if (ss.length() < 4) {
			String pre = "";
			for (int i = 0; i < (4 - ss.length()); i++) {
				pre += "0";
			}
			ss = pre + ss;
		}
		sb.append(ss);
		sb.append(Utils.intTohex(lineNameLength));
		sb.append(DigitalTrans.byte2hex(_lineName));
		sb.append(Utils.intTohex(lienNameVersion));
		// crc校验
		byte[] bs = DigitalTrans.hex2byte((sb.toString()));
		sb.append(Utils.crcTable(bs));
		return sb.toString().toUpperCase();
	}

	@Override
	public String toString() {
		return "LineInfoCheckResponseFrame [frameFlag=" + frameFlag
				+ ", selfStatus=" + selfStatus + ", lineNameLength="
				+ lineNameLength + ", lineName=" + lineName
				+ ", lienNameVersion=" + lienNameVersion + "]";
	}

}
