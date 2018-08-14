package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月20日 上午10:04:27
 * 
 * @Description: 终端逻辑地址
 * 
 */

public class Rtua {
	private byte[] value;
	private String rtuaStr;

	/**
	 * 数组长度必须为4
	 * 
	 * @param value
	 */
	public Rtua(byte[] value) {
		super();
		if (value.length == 4) {
			this.value = value;
		} else {
			throw new RuntimeException("数组长度必须为4");
		}
	}

	public void setRtuaStr(String rtuaStr) {
		this.rtuaStr = rtuaStr;
	}

	public byte[] encode() {
		if (rtuaStr == null || rtuaStr.length() != 8) {
			value[0] = (byte) 0xff;
			value[1] = (byte) 0xff;
			value[2] = (byte) 0xff;
			value[3] = (byte) 0xff;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(rtuaStr.substring(0, 2)).append(rtuaStr.substring(2, 4))
					.append(rtuaStr.substring(6))
					.append(rtuaStr.substring(4, 6));
			Util.HexsToBytes(value, 0, sb.toString());
		}

		return value;
	}

	private byte getCityCode() {
		return value[0];
	}

	public void setCityCode(byte cityCode) {
		value[0] = cityCode;
	}

	public String decode() {
		String str = Util.BytesToHexL(value, 0, value.length);
		return "终端逻辑地址:" + str;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		byte a1 = getCityCode();
		String cityName = CityCode.getName(a1);
		sb.append("城市:" + cityName + "\n");

		return sb.toString();
	}

}
