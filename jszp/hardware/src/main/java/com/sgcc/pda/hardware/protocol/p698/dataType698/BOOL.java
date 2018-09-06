package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class BOOL extends IPharesItem698 {

	@Override
	public String getResStr() {
		return CommUtils.bytesToHexString(this.getUpbyte());
	}
	
	public byte[] getBytes(){
		return CommUtils.hex2Binary(this.getInputValue());
	}
	

}
