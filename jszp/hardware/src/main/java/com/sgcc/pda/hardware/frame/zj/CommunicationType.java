package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年9月11日 下午3:37:53
 * 
 * @Description:
 * 
 */

public enum CommunicationType {

	INVALID((byte) 0xff, "无效"), SMS((byte) 0x01, "短信"), GPRS((byte) 0x02,
			"GPRS"), DTMF((byte) 0x03, "DTMF"), ETHERNET((byte) 0x04,
			"ETHERNET"), INFRA((byte) 0x05, "红外"), RS232((byte) 0x06, "RS232"), CSD(
			(byte) 0x07, "CSD"), RADIO((byte) 0x08, "RADIO");

	private byte code;
	private String name;

	private CommunicationType(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		for (CommunicationType c : CommunicationType.values()) {
			if (c.getCode() == code) {
				return c.name;
			}
		}
		return null;
	}

	public static CommunicationType getCommunicationType(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		for (CommunicationType c : CommunicationType.values()) {
			if (c.getName() == name) {
				return c;
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
