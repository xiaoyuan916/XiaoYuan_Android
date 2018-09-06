package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理OCTET_STRING_2类型，当字节数组不够2个补齐
 * 
 * @author zhm
 *
 */
public class OCTET_STRING_2 extends IPharesItem698 {

	@Override
	public String getResStr() {
//	    byte[] bytes = {4, -48};
		byte[] bytes = this.getUpbyte();
		double tempVal = Integer.parseInt(CommUtils.bytesToHexString(bytes), 16);
		if (tempVal == 0)
			return "0.0";
		else
			return String.valueOf(tempVal).substring(
					String.valueOf(tempVal).indexOf(".") + 1,
					String.valueOf(tempVal).length()).length() == 3 ? String
					.valueOf(tempVal).concat("0") : String.valueOf(tempVal);
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[3];
//			String octetStr ="1232";
			String octetStr = this.getInputValue().replace(".", "");
			System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt("2"))), 0, bytes, 0, 1);
			String hexStr = Integer.toHexString(Integer.parseInt(octetStr));
			if (hexStr.length() == 4) {
				System.arraycopy(CommUtils.hex2Binary(hexStr), 0, bytes, 1, 2);
			} else if (hexStr.length() < 4) {
				System.arraycopy(CommUtils.hex2Binary(Utils.addType(hexStr, 4)), 0, bytes, 1, 2);
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		OCTET_STRING_2 o = new OCTET_STRING_2();
		o.getBytes();
		System.out.println(o.getResStr());
	}
}
