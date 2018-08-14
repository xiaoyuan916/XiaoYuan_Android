package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年9月2日 上午11:00:39
 * 
 * @Description:
 * 
 */

public enum MeasurePointProperty {

	BIAO_485((byte) 1, "485表"), SIMULATE((byte) 2, "模拟量"), PULSE((byte) 3,
			"脉冲量"), CALCULATE((byte) 4, "计算值"), ALTERNATING((byte) 5, "交流采样");

	private byte code;
	private String name;

	private MeasurePointProperty(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		for (MeasurePointProperty c : MeasurePointProperty.values()) {
			if (c.getCode() == code) {
				return c.name;
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

}
