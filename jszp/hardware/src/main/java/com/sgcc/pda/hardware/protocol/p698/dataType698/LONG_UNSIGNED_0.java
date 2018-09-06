package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理LONG_UNSIGNED_0类型，当字节数组不够2个补齐
 * 
 * @author zhm
 * 
 */
public class LONG_UNSIGNED_0 extends IPharesItem698 {
	
	@Override
	public String getResStr() {
//		byte [] bytes ={22, 10};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] bytes = new byte[2];
//			String lu0 = "5642";
			String lu0 = this.getInputValue().replace(".", "");
			String hexStr = Integer.toHexString(Integer.parseInt(lu0));
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
	public static void main(String[] args) {
		LONG_UNSIGNED_0 lu0 = new LONG_UNSIGNED_0();
		lu0.getBytes();
		System.out.println(lu0.getResStr());
	}
}
