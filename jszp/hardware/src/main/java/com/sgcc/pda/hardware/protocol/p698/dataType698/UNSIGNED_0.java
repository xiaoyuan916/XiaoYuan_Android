package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理UNSIGNED_0类型
 * 
 * @author zhm
 *
 */
public class UNSIGNED_0 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes={2};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(
				CommUtils.byteToHexString(bytes), 16));
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[1];
			// String usgStr = "2";
			String usgStr = this.getInputValue();
			bytes = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(usgStr)));
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		UNSIGNED_0 u = new UNSIGNED_0();
		u.getBytes();
		System.out.println(u.getResStr());
	}

}
