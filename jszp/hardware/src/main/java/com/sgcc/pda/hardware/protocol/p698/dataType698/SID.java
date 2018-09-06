package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class SID extends IPharesItem698 {

	@Override
	public String getResStr() {
		byte[] bytes = this.getUpbyte();
		return String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(bytes), 16));
	}
	
	@Override
	public byte[] getBytes(){
		try {
			byte[] byteLen = CommUtils.hex2Binary( this.getInputValue().replace(".", ""));
			//组装 SID标识
			byte[] sidIndex= new byte[5];
			sidIndex[0] = CommUtils.hex2Binary(Integer.toHexString(6))[0];
			System.arraycopy(byteLen, 0, sidIndex, 1, 4);
			
			//组装附加数据
			byte[] sidAtt = new byte[byteLen.length-4+2];
			sidAtt[0] = CommUtils.hex2Binary(Integer.toHexString(9))[0];
			sidAtt[1] = CommUtils.hex2Binary(Integer.toHexString(byteLen.length-4))[0];
			System.arraycopy(byteLen, 4, sidAtt, 2, byteLen.length-4);
			
			byte[] bytes = new byte[sidAtt.length+sidIndex.length];
			System.arraycopy(sidIndex, 0, bytes, 0, 5);
			System.arraycopy(sidAtt, 0, bytes, 5, sidAtt.length);
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
}
