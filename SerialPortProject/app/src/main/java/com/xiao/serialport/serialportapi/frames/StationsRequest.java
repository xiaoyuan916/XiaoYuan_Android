package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.DigitalTrans;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.Address;
import com.xiao.serialport.serialportapi.bean.BaseFrame;
import com.xiao.serialport.serialportapi.bean.Order;

/**
 * 线路站点列表下载请求帧
 * <p>Title: StationsRequest</p>
 * <p>Description: </p>
 * @author	ShenYang
 * @date	2015-8-13
 */
@Deprecated
public class StationsRequest extends BaseFrame {
	private static final long serialVersionUID = -2636808174388079437L;

	/**
	 * @param address	当前屏的地址
	 */
	public StationsRequest(Address address){
		this.address = address;
		this.order = Order.getOrder("17");
		this.length = 9;
		this.crc16 = null;
	}
	
	/**
	 * @param address	当前屏的地址
	 */
	public StationsRequest(String address){
		this.address = Address.getAddress(address);
		this.order = Order.getOrder("17");
		this.length = 9;
		this.crc16 = null;
	}
	
	/**
	 * 返回请求的16进制字符串
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(FRAMEHEADER);
		sb.append(address.getAddressValue());
		sb.append(order.getOrderValue());
		sb.append(Utils.intTohex(length));
		byte[] bs = DigitalTrans.hex2byte((sb.toString()));
		/*String str = "哈哈测试";
		try {
			sb.append(DigitalTrans.byte2hex(str.getBytes("GB2312")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		sb.append(Utils.crcTable(bs));
		return sb.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		StationsRequest re = new StationsRequest("17");
		System.out.println(re.toString());
	}
}
