package com.sgcc.pda.hardware.protocol.p698.dataType698;



;import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.DateUtil;

/**
 * 将上行报文解析为2016-11-25 10:15:25:451 eg:{7, -32, 11, 25, 5, 10, 15, 25, 1, -61}
 * --> 2016-11-25 10:15:25:451
 * 
 * 并且将输入的日期字符串组成字节数组 eg:20161125051015250451 --> {7, -32, 11, 25, 5, 10, 15, 25,
 * 1, -61}
 * 
 * @author zhm
 * 
 */
public class DATE_TIME extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte [] bytes = {7, -32, 11, 25, 5, 10, 15, 25, 1, -61};
		byte[] bytes = this.getUpbyte();
		String dateStr = CommUtils.bytesToHexString(bytes);
		String yyyy = String.valueOf(Integer.parseInt(dateStr.substring(0, 4),16));
		String MM = String.valueOf(Integer.parseInt(dateStr.substring(4, 6), 16));
		String dd = String.valueOf(Integer.parseInt(dateStr.substring(6, 8), 16));
		String ww = DateUtil.getWeek(Integer.parseInt(dateStr.substring(8, 10),16));
		String hh = String.valueOf(Integer.parseInt(dateStr.substring(10, 12),16));
		String mm = String.valueOf(Integer.parseInt(dateStr.substring(12, 14),16));
		String ss = String.valueOf(Integer.parseInt(dateStr.substring(14, 16),16));
		String ms = String.valueOf(Integer.parseInt(dateStr.substring(16), 16));
		return yyyy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss
				+ ":" + ms;

	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] dateByte = new byte[10];
			// String dateStr="20161125051015250451";
			String dateStr;
			dateStr= this.getInputValue().replace("-", "").replace(":", "").replace(" ", "").trim();
			byte[] dateByteTemp = CommUtils.hex2Binary(Integer
					.toHexString(Integer.parseInt(dateStr.substring(0, 4))));
			System.arraycopy(dateByteTemp, 0, dateByte, 0, 2);
			for (int i = 0; i < dateStr.substring(4, 16).length() / 2; i++) {
				dateByteTemp = CommUtils.hex2Binary(Integer.toHexString(Integer
						.parseInt(dateStr.substring(4 + i * 2, 6 + i * 2))));
				System.arraycopy(dateByteTemp, 0, dateByte, 2 + i, 1);
			}
			dateByteTemp = CommUtils.hex2Binary(Integer.toHexString(Integer
					.parseInt(dateStr.substring(16))));
			if(dateByteTemp.length==1){
				  byte  []  temp  =new  byte[2];
				  temp[0]=0;
				  temp[1]=dateByteTemp[0];
				  dateByteTemp = temp;
			}
			System.arraycopy(dateByteTemp, 0, dateByte, 8, 2);
			return dateByte;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		DATE_TIME dt = new DATE_TIME();
		dt.getBytes();
		String dd = dt.getResStr();
		System.out.println(dd);

	}

}
