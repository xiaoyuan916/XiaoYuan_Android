package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 *处理LONG64_1类型，当字节数组不够8个补齐(保留4位小数) 
 * 
 * @author zhm
 *
 */
public class LONG64_4 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes = { 0, 0, 0, 0, 0, 0, 0, -55 };
		byte[] bytes = this.getUpbyte();
		long tempVal = Long.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16)) / 10000;
		if (tempVal == 0)
			return "0.0000";
		else
			return String.valueOf(tempVal).substring(
					String.valueOf(tempVal).indexOf(".") + 1,
					String.valueOf(tempVal).length()).length() == 4 ? String
					.valueOf(tempVal) : Utils.roundByScale(tempVal, 4);
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[8];
			// String longStr = "201";
			String longStr = this.getInputValue().replace(".", "");
			String hexStr = Integer.toHexString(Integer.parseInt(longStr));
			if (hexStr.length() == 16) {
				bytes = CommUtils.hex2Binary(hexStr);
			} else if (hexStr.length() < 16) {
				bytes = CommUtils.hex2Binary(Utils.addType(hexStr, 16));
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		LONG64_4 l = new LONG64_4();
		System.out.println(l.getResStr());
	}
}
