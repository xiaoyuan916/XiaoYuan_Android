package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月21日 上午10:17:10
 * 
 * @Description: 城市编码
 * 
 */

public enum CityCode {
	HANG_ZHOU((byte) 0x91, "杭州"), NING_BO((byte) 0x92, "宁波"), WEN_ZHOU(
			(byte) 0x93, "温州"), SHAO_XING((byte) 0x94, "绍兴"), HU_ZHOU(
			(byte) 0x95, "湖州"), JIA_XING((byte) 0x96, "嘉兴"), JIN_HUA(
			(byte) 0x97, "金华"), QU_ZHOU((byte) 0x98, "衢州"), TAI_ZHOU(
			(byte) 0x99, "台州"), LI_SHUI((byte) 0x9A, "丽水"), ZHOU_SHAN(
			(byte) 0x9B, "舟山");

	private byte code;
	private String name;

	private CityCode(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		for (CityCode city : CityCode.values()) {
			if (city.getCode() == code) {
				return city.name;
			}
		}
		return null;
	}

	public byte getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "城市:" + this.name;
	}

}
