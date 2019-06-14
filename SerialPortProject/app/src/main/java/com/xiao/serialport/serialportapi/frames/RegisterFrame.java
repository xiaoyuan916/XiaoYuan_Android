package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.DigitalTrans;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

import java.io.UnsupportedEncodingException;

/**
 * 屏注册信息帧
 * <p>Title: RegisterFrame</p>
 * <p>Description: </p>
 * @author	ShenYang
 * @date	2015-8-13
 */
@Deprecated
public class RegisterFrame extends BaseFrame {
	private static final long serialVersionUID = 6678070260527716334L;

	/**
	 * 包标识，同一个包重发时应保持一致
	 */
	private int flag;
	/**
	 * 自检状态，0：正常
	 */
	private int selfStatus;
	/**
	 * 线路名称长度，没有时设为0
	 */
	private int lineNameLength;
	/**
	 * 线路名称，没有时设为null
	 */
	private String lineName;
	/**
	 * 站点名称版本，没有时设为0
	 */
	private int lienNameVersion;

	public RegisterFrame(){}
	
	/**
	 * @param flag				包标识，重发时保持一致
	 * @param selfStatus		自检状态。0：正常
	 * @param lineName			线路名称。没有传null
	 * @param lienNameVersion	线路名称版本。没有传0
	 */
	public RegisterFrame(int flag, int selfStatus, String lineName, int lienNameVersion){
		this.flag = flag;
		this.selfStatus = selfStatus;
		if(lineName == null || lineName == ""){
			this.lineNameLength = 0;
		} else {
			this.lineNameLength = lineName.length();
		}
		this.lineName = lineName;
		this.lienNameVersion = lienNameVersion;
		this.length = 14 + lineNameLength;
	}
	
	/**
	 * 返回十六进制字符串
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(FRAMEHEADER);
		sb.append(address.getAddressValue());
		sb.append(order.getOrderValue());
		//长度
		sb.append(Utils.intTohex(length));
		sb.append(Utils.intTohex(flag));
		sb.append(Utils.intTohex(selfStatus));
		sb.append(Utils.intTohex(lineNameLength));
		try {
			sb.append(DigitalTrans.byte2hex(lineName.getBytes("GB2312")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append(Utils.intTohex(lienNameVersion));
		//crc校验
		byte[] bs = DigitalTrans.hex2byte((sb.toString()));
		sb.append(Utils.crcTable(bs));
		return sb.toString().toUpperCase();
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getSelfStatus() {
		return selfStatus;
	}

	public void setSelfStatus(int selfStatus) {
		this.selfStatus = selfStatus;
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

	public int getLienNameVersion() {
		return lienNameVersion;
	}

	public void setLienNameVersion(int lienNameVersion) {
		this.lienNameVersion = lienNameVersion;
	}
	
}
