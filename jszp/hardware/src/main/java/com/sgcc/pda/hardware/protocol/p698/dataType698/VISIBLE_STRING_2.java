package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理VISIBLE_STRING_2类型，当字节数组不够2个补齐
 * 
 * @author zhm
 *
 */
public class VISIBLE_STRING_2 extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes = {97, 98};
		byte[] bytes = this.getUpbyte();
		return Utils.bytesToAsciiStr(bytes);
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[3];
			// String octetStr ="12";
			String octetStr = this.getInputValue();
			System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(Integer
					.parseInt("2"))), 0, bytes, 0, 1);
			String hexStr = Integer.toHexString(Integer.parseInt(octetStr));
			if (hexStr.length() == 4) {
				System.arraycopy(CommUtils.hex2Binary(hexStr), 0, bytes, 1, 2);
			} else if (hexStr.length() < 4) {
				System.arraycopy(
						CommUtils.hex2Binary(Utils.addType(hexStr, 4)), 0,
						bytes, 1, 2);
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		VISIBLE_STRING_2 vs2 = new VISIBLE_STRING_2();
		vs2.getBytes();
		System.out.println(vs2.getResStr());
	}
}
