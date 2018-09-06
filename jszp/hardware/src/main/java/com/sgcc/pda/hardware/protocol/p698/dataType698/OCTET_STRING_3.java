package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理OCTET_STRING_3类型，当字节数组不够3个补齐
 * 
 * @author zhm
 *
 */
public class OCTET_STRING_3 extends IPharesItem698 {

	@Override
	public String getResStr() {
//		 byte[] bytes = { 0, 4, -48};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[4];
//			 String octetStr ="1232";
			String octetStr = this.getInputValue().replace(".", "");
			System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt("3"))), 0, bytes, 0, 1);
			String hexStr = Integer.toHexString(Integer.parseInt(octetStr));
			if (hexStr.length() == 6) {
				System.arraycopy(CommUtils.hex2Binary(hexStr), 0, bytes, 1, 3);
			} else if (hexStr.length() < 6) {
				System.arraycopy(CommUtils.hex2Binary(Utils.addType(hexStr, 6)), 0,bytes, 1, 3);
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
	
	public static void main(String[] args) {
		OCTET_STRING_3 os3 = new OCTET_STRING_3();
		os3.getBytes();
		System.out.println(os3.getResStr());
	}

}
