package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 *  处理OCTET_STRING_4类型，当字节数组不够4个补齐
 * 
 * @author zhm
 *
 */
public class OCTET_STRING_4 extends IPharesItem698 {

	@Override
	public String getResStr() {
//		 byte[] bytes = { 0, 0, 4, -48};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}
	
	@Override
	public byte[] getBytes(){
		try {
			byte[] bytes = new byte[5];
//			String octetStr ="1232";
			String octetStr = this.getInputValue().replace(".", "");
			System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt("4"))), 0, bytes, 0, 1);
			String hexStr = Integer.toHexString(Integer.parseInt(octetStr));
			if (hexStr.length() == 8) {
				System.arraycopy(CommUtils.hex2Binary(hexStr), 0, bytes, 1, 4);
			} else if (hexStr.length() < 8) {
				System.arraycopy(CommUtils.hex2Binary(Utils.addType(hexStr, 8)), 0,bytes, 1, 4);
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
	
	public static void main(String[] args) {
		OCTET_STRING_4 os4 = new OCTET_STRING_4();
		os4.getBytes();
		System.out.println(os4.getResStr());
	}

}
