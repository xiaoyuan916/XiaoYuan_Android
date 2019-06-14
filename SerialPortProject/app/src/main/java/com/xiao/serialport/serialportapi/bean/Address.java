package com.xiao.serialport.serialportapi.bean;

/**
 * 地址枚举
 * <p>
 * Title: Address
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-6
 */
public enum Address {
	/**
	 * 广播地址,仅仅只是节站屏的广播地址。
	 */
	BROADCAST("16"),
	/**
	 * 站点信息广播地址，会向所有屏(不仅仅是节站屏)发布广播
	 */
	STATIONBROADCAST("7F"),
	/**
	 * 屏1地址
	 */
	SCREEN1("17"),
	/**
	 * 屏2地址
	 */
	SCREEN2("18"),
	/**
	 * 屏3地址
	 */
	SCREEN3("19"),
	/**
	 * 屏4地址<br>
	 * NY 应该是 1A 。 <br>
	 * SY 的是 20 。
	 */
	SCREEN4("1A");

	private String value;

	private Address(String value) {
		this.value = value;
	}

	public String getAddressValue() {
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

	/**
	 * 根据地址值获取地址对象
	 * 
	 * @author ShenYang
	 * @date 2015-8-6
	 * @param value
	 * @return
	 */
	public static Address getAddress(String value) {
		for (Address add : Address.values()) {
			if (add.getAddressValue().equalsIgnoreCase(value)) {
				return add;
			}
		}
		return null;
	}
}