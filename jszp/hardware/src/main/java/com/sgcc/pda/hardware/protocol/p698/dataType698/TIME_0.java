package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理TIME_0类型
 * 
 * @author zhm
 *
 */
public class TIME_0 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes = { 5, 13, 12 };
		byte[] bytes = this.getUpbyte();
		String timeStr = "";
		for (int i = 0; i < bytes.length; i++) {
			String tempStr = String.valueOf(Integer.parseInt(CommUtils
					.bytesToHexString(new byte[] { bytes[i] }), 16));
			if (i != bytes.length - 1) {
				timeStr += Utils.addType(tempStr, 2) + ":";
			} else {
				timeStr += Utils.addType(tempStr, 2);
			}
		}
		return timeStr;
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[3];
//			String timeStr = "051312";
			String timeStr = this.getInputValue();
			for (int i = 0; i < timeStr.length() / 2; i++) {
				System.arraycopy(CommUtils.hex2Binary(Integer
						.toHexString(Integer.parseInt(timeStr.substring(i * 2,
								2 + i * 2)))), 0, bytes, i, 1);
			}
			
			return bytes;
			
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		TIME_0 T = new TIME_0();
		// T.getBytes();
		System.out.println(T.getResStr());

	}
}
