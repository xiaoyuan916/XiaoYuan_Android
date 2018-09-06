package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理DOUBLE_LONG类型，当字节数组不够4个补齐
 * 
 * @author zhm
 * 
 */
public class DOUBLE_LONG_0 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes = {0, 0, 13, -117};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}

	@Override
	public byte[] getBytes() {
		try {
			// String dlStr = "3467.0";
			String dlStr = this.getInputValue();
			byte[] bytes = new byte[4];
			String hexStr = "";
			if (dlStr.indexOf(".") != -1)
				hexStr = Integer.toHexString(Integer.parseInt(dlStr.substring(
						0, dlStr.indexOf("."))));
			else
				hexStr = Integer.toHexString(Integer.parseInt(dlStr));

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
		DOUBLE_LONG_0 dl0 = new DOUBLE_LONG_0();
		dl0.getBytes();
		String b = dl0.getResStr();
		System.out.println(b);
	}
}
