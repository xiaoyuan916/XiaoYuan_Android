package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理VISIBLE_STRING_32类型，当字节数组不够32个补齐
 * 
 * @author zhm
 *
 */
public class VISIBLE_STRING_32 extends IPharesItem698 {

	@Override
	public String getResStr() {
		byte[] bytes = this.getUpbyte();
		return Utils.bytesToAsciiStr(bytes);
	}
	
	@Override
	public byte[] getBytes(){
		try {
			byte[] bytes = new byte[33];
//			 String octetStr ="1232";
			String octetStr = this.getInputValue();
			System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt("32"))), 0, bytes, 0, 1);
			String hexStr = Integer.toHexString(Integer.parseInt(octetStr));
			if (hexStr.length() == 64) {
				System.arraycopy(CommUtils.hex2Binary(hexStr), 0, bytes, 1, 32);
			} else if (hexStr.length() < 64) {
				System.arraycopy(CommUtils.hex2Binary(Utils.addType(hexStr, 64)), 0,bytes, 1, 32);
			}
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

}
