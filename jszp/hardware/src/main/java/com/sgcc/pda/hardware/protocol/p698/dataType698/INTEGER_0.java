package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * 处理INTEGER类型
 * 
 * @author zhm
 * 
 */
public class INTEGER_0 extends IPharesItem698 {

	@Override
	public String getResStr() {
	 // byte[] bytes ={2};
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils.bytesToHexString(bytes), 16));
	}

	@Override
	public byte[] getBytes() {
		try {
		 // String intStr = "2";
			String intStr = this.getInputValue();
			return CommUtils.hex2Binary(Utils.judgeLen(Integer.toHexString(Integer.parseInt(intStr)), 2));
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		INTEGER_0 i = new INTEGER_0();
		i.getBytes();
		System.out.println(i.getResStr());
	}
}
