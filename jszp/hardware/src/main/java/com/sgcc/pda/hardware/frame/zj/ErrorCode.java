package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年9月16日 上午10:33:56
 * 
 * @Description:
 * 
 */

public enum ErrorCode {
	H00((byte) 0x00, "正确,无错误"), H01((byte) 0x01, "中继命令没有返回"), H02((byte) 0x02,
			"设置内容非法"), H03((byte) 0x03, "密码权限不足"), H04((byte) 0x04, "无此项数据"), H05(
			(byte) 0x05, "命令时间失效"), H11((byte) 0x11, "目标地址不存在"), H12(
			(byte) 0x12, "发送失败"), H13((byte) 0x13, "短消息帧太长");

	private byte code;
	private String name;

	private ErrorCode(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		for (ErrorCode c : ErrorCode.values()) {
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
