package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理LONG_UNSIGNED_2类型，当字节数组不够2个补齐(保留2位小数)
 * 
 * @author zhm
 * 
 */
public class LONG_UNSIGNED_2 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte [] bytes ={22, 10};
		byte[] bytes = this.getUpbyte();
		long tempVal = Long.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16)) / 100;
		if (tempVal == 0)
			return "0.00";
		else
			return String.valueOf(tempVal).substring(
					String.valueOf(tempVal).indexOf(".") + 1,
					String.valueOf(tempVal).length()).length() == 2 ? String
					.valueOf(tempVal) : Utils.roundByScale(tempVal, 2);
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[2];
			// String lu2 = "5642";
			String lu2 = this.getInputValue().replace(".", "");
			String hexStr = Integer.toHexString(Integer.parseInt(lu2));
			if (hexStr.length() == 4) {
				bytes = CommUtils.hex2Binary(hexStr);
			} else if (hexStr.length() < 4) {
				bytes = CommUtils.hex2Binary(Utils.addType(hexStr, 4));
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
}
