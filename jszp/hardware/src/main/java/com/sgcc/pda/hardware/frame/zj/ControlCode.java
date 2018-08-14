package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月21日 下午4:18:59
 * 
 * @Description:
 * 
 */

public enum ControlCode {
	ZHONG_JI((byte) 0x0, "中继"), READ_NOW_DATA((byte) 0x1, "读当前数据"), READ_TASK_DATA(
			(byte) 0x2, "读任务数据"), READ_PROGRAM_LOG((byte) 0x4, "读编程日志"), WRITE_DATA_NOW(
			(byte) 0x7, "实时写对象参数"), WRITE_DATA((byte) 0x8, "写对象参数"), EXCEPTION_WARN(
			(byte) 0x9, "异常告警"), WARN_CONFIRM((byte) 0xA, "告警确认"), USER_SET(
			(byte) 0xF, "用户自定义数据");

	private byte code;
	private String name;

	private ControlCode(byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(byte code) {
		for (ControlCode c : ControlCode.values()) {
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

	@Override
	public String toString() {
		return "功能:" + this.name;
	}

}
