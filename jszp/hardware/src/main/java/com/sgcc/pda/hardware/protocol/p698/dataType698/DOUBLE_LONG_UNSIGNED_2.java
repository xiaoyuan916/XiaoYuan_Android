package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 
 * 处理DOUBLE_LONG类型，当字节数组不够4个补齐(保留2位小数)
 * 
 * @author zhm
 * 
 */

public class DOUBLE_LONG_UNSIGNED_2 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes = {0, 0, 13, -117};
		byte[] bytes = this.getUpbyte();
		String hexStr = CommUtils.bytesToHexString(bytes);
		double tempVal = Double.valueOf(Integer.parseInt(hexStr, 16)) / 100;
		if (tempVal == 0)
			return "0.00";
		else
			return String.valueOf(tempVal).substring(
					String.valueOf(tempVal).indexOf(".") + 1,
					String.valueOf(tempVal).length()).length() == 1 ? String
					.valueOf(tempVal) : Utils.roundByScale(tempVal, 3);
	}

	@Override
	public byte[] getBytes() {
		try {
//			String dlu2Str = "3467".replace(".", "");
			String dlu2Str = this.getInputValue().replace(".", "");
			byte[] bytes = new byte[4];
			String hexStr = Integer.toHexString(Integer.parseInt(dlu2Str));
			if (hexStr.length() == 8) {
				bytes = CommUtils.hex2Binary(hexStr);
			} else if (hexStr.length() < 8) {
				bytes = CommUtils.hex2Binary(Utils.addType(hexStr, 8));
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
	
	public static void main(String[] args) {
		DOUBLE_LONG_UNSIGNED_2 dlu2 = new DOUBLE_LONG_UNSIGNED_2();
		dlu2.getBytes();
		System.out.println(dlu2.getResStr());
	}
}
