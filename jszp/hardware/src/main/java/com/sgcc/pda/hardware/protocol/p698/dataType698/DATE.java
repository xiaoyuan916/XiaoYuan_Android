package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.DateUtil;

/**
 * 将上行报文解析成指定的日期格式 eg:{7, -32, 11, 24, 4} --> 2016年11月24日星期四
 * 
 * 并且将输入的日期字符串组成字节数组 eg:2016112404 --> {7, -32, 11, 24, 4}
 * 
 * @author zhm
 * 
 */
public class DATE extends IPharesItem698 {

	@Override
	public String getResStr() {
		// byte[] bytes ={7, -32, 11, 24, 4};
		byte[] bytes = this.getUpbyte();
		String yeas = String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(new byte[] { bytes[0], bytes[1] }), 16));
		String month = String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(new byte[] { bytes[2] }), 16));
		String day = String.valueOf(Integer.parseInt(CommUtils
				.bytesToHexString(new byte[] { bytes[3] }), 16));
		String week = DateUtil.getWeek(Integer.parseInt(CommUtils
				.bytesToHexString(new byte[] { bytes[4] }), 16));
		return yeas + "年" + month + "月" + day +"日"+ week;
	}

	@Override
	public byte[] getBytes() {
		try {
			byte[] dateByte = new byte[5];
			// String dateStr = "2016112404";
			String dateStr = this.getInputValue();
			byte[] dateByteTemp = CommUtils.hex2Binary(Integer
					.toHexString(Integer.parseInt(dateStr.substring(0, 4))));
			System.arraycopy(dateByteTemp, 0, dateByte, 0, 2);
			for (int i = 0; i < dateStr.substring(4).length() / 2; i++) {
				dateByteTemp = CommUtils.hex2Binary(Integer.toHexString(Integer
						.parseInt(dateStr.substring(4 + i * 2, 6 + i * 2))));
				System.arraycopy(dateByteTemp, 0, dateByte, 2 + i, 1);
			}

			return dateByte;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}

	public static void main(String[] args) {
		DATE d = new DATE();
		// d.getBytes();
		String d1 = d.getResStr();
		System.out.println(d1);

	}
}
