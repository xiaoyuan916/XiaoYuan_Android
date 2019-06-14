package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.DigitalTrans;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;
import com.xiao.serialport.serialportapi.bean.Order;

/**
 * 
 * @version
 * @author NY
 * @Description 信息下载应答帧。
 * @CreatTime 2015年9月20日15:04:54
 */
public class InfoPushResponseFrame extends BaseFrame {
	private static final long serialVersionUID = -4368943121269669170L;

	/**
	 * 应答标识
	 */
	private String responseFlag;

	public InfoPushResponseFrame() {
		this.order = Order.INFODOWNLOADRESPONSE;
	};

	public InfoPushResponseFrame(String responseFlag) {
		this.responseFlag = responseFlag;
	}

	public InfoPushResponseFrame(byte[] responseFlag) {
		this.responseFlag = DigitalTrans.byte2hex(responseFlag);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(FRAMEHEADER);
		sb.append(address.getAddressValue());
		sb.append(order.getOrderValue());
		// 长度
		sb.append(Utils.intTohex(length));
		sb.append(responseFlag);
		// crc校验
		byte[] bs = DigitalTrans.hex2byte((sb.toString()));
		sb.append(Utils.crcTable(bs));
		return sb.toString().toUpperCase();
	}

	public String getResponseFlag() {
		return responseFlag;
	}

	public void setResponseFlag(String responseFlag) {
		this.responseFlag = responseFlag;
	}

	/**
	 * 设置应答标识。
	 * 
	 * @param responseFlag
	 */
	public void setResponseFlag(byte[] responseFlag) {
		this.responseFlag = DigitalTrans.byte2hex(responseFlag);
	}

}
