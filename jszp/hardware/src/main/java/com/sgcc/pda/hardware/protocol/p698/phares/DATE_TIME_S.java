package com.sgcc.pda.hardware.protocol.p698.phares;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

//  数据格式   date_time_s
public class  DATE_TIME_S   extends IPharesItem698 {

	@Override
	public String getResStr() {
		byte  []   value = this.getUpbyte();
		String upValue  =  CommUtils.bytesToHexString(value);
		String yyyy = String.valueOf(Integer.parseInt(upValue.substring(0, 4), 16));
		String mm = String.valueOf(Integer.parseInt(upValue.substring(4, 6), 16));
		String dd = String.valueOf(Integer.parseInt(upValue.substring(6, 8), 16));
		String hh = String.valueOf(Integer.parseInt(upValue.substring(8, 10), 16));
		String ff = String.valueOf(Integer.parseInt(upValue.substring(10, 12), 16));
		String ss = String.valueOf(Integer.parseInt(upValue.substring(12, 14), 16));
		return yyyy+"-"+mm+"-"+dd+" "+hh+":"+ff+":"+ss;
	}

	
	@Override
	public   byte[] getBytes(){
		byte []   bytes  =new  byte[7];
		String dateStr= this.getInputValue().replace("-", "").replace(":", "").replace(" ", "").trim();
	    byte []  yyyy =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(0, 4))));
		System.arraycopy(yyyy, 0, bytes, 0, yyyy.length);
		
		byte []  mm =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(4, 6))));
		System.arraycopy(mm, 0, bytes, 2, mm.length);
		
		byte []  dd =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(6, 8))));
		System.arraycopy(dd, 0, bytes, 3, dd.length);
		
		byte []  hh =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(8, 10))));
		System.arraycopy(hh, 0, bytes, 4, hh.length);
		
		byte []  ff =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(10, 12))));
		System.arraycopy(ff, 0, bytes, 5, ff.length);
		
		byte []  ss =CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dateStr.substring(12, 14))));
		System.arraycopy(ss, 0, bytes, 6, ss.length);
		return  bytes;
	}

	
}
