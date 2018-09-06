package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理DOUBLE_LONG类型，当字节数组不够4个补齐(保留2位小数)
 * 
 * @author zhm
 * 
 */
public class DOUBLE_LONG_2 extends IPharesItem698 {
	@Override
	public String getResStr() {
		// byte[] bytes = {67, 35, 45, 51};
		byte[] bytes = this.getUpbyte();
		String hexStr = CommUtils.bytesToHexString(bytes);
		double tempVal = Double.valueOf(Integer.parseInt(hexStr, 16)) / 100;
		if (tempVal == 0)
			return "0.00";
		else
			return String.valueOf(tempVal).substring(
					String.valueOf(tempVal).indexOf(".") + 1,
					String.valueOf(tempVal).length()).length() == 1 ? String
					.valueOf(tempVal) : Utils.roundByScale(tempVal, 2);

	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[4];
			// String dl2Str = "11263788.03".replace(".", "");
			String dl2Str = this.getInputValue().replace(".", "");
			String hexStr = Integer.toHexString(Integer.parseInt(dl2Str));
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
		DOUBLE_LONG_2 dl2 = new DOUBLE_LONG_2();
		dl2.getBytes();

		System.out.println(dl2.getResStr());
	}
}
