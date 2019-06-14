package com.xiao.serialport.serialportapi.bean;

/**
 * 命令枚举
 * <p>
 * Title: Order
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-6
 */
public enum Order {

	/**
	 * 站点信息广播，车载机广播
	 */
	STATIONINFO("11"),

	/**
	 * 信息推送，车载机广播
	 */
	INFODOWNLOAD("12"),

	/**
	 * 信息推送应答，屏发送
	 */
	INFODOWNLOADRESPONSE("12"),

	/**
	 * 线路站点列表下载帧，车载机发送，N个站点发送N帧
	 */
	LINESTATIONSDOWNLOAD("17"),

	/**
	 * 屏线路信息查询帧,车载机发送
	 */
	LINEINFOCHECK("19"),

	/**
	 * 屏线路信息查询应答帧，屏回复
	 */
	LINEINFOCHECKRESPONSE("19"),

	/**
	 * 屏线路同步，车载机广播
	 */
	LINESYNC("20"),

	/**
	 * 校时包（时间同步），车载机广播
	 */
	TIMESYNC("21"),

	/**
	 * 车辆信息包。<br>
	 * 车载机广播。
	 * 
	 * @author NY
	 */
	BUSINFO("22"),
	/**
	 * 司机信息包
	 */
	DRIVERINFO("24");

	private String value;

	private Order(String value) {
		this.value = value;
	}

	/**
	 * 获取order的值，十六进制。
	 * 
	 * @author ShenYang
	 * @date 2015-8-7
	 * @return
	 */
	public String getOrderValue() {
		return this.value;
	}

	/**
	 * 获取order的值，十进制
	 * 
	 * @author ShenYang
	 * @date 2015-8-7
	 * @return
	 */
	public int getIntValue() {
		return Integer.valueOf(this.value, 16);
	}

	/**
	 * 获取order的值字符串形式，十进制
	 * 
	 * @author ShenYang
	 * @date 2015-8-7
	 * @return
	 */
	public String getIntValueString() {
		return String.valueOf(getIntValue());
	}

	public static Order getOrder(String value) {
		for (Order o : Order.values()) {
			if (o.getOrderValue().equalsIgnoreCase(value)) {
				return o;
			}
		}
		return null;
	}

}
