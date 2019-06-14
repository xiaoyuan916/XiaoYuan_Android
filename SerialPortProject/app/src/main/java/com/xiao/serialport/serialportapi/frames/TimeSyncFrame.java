package com.xiao.serialport.serialportapi.frames;

import android.util.Log;

import com.xiao.serialport.serialportapi.DigitalTrans;
import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间同步广播。 <br>
 * （一般用处不大，因为节站屏的时间只要设置成准确的北京时间就行了。）
 * <p>
 * Title: TimeSync
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author ShenYang
 * @date 2015-8-14
 */
public class TimeSyncFrame extends BaseFrame {

	private static final long serialVersionUID = -5972775328867172087L;

	/**
	 * 同一包重发时，包标识不变。<br>
	 * 根据这个 字段判断是不是重发的包，重发的包标示不会变化。<br>
	 * 不同类型的数据帧包的包标识不同。<br>
	 */
	private int flag;

	/**
	 * 车载机发过来的校对时间。<br>
	 * （需要节站屏和这时间一致，不过一般用处不大，因为节站屏的时间只要设置成准确的北京时间就行了。）
	 */
	private Date time;

	public static TimeSyncFrame processMessgae(byte[] message) {
		TimeSyncFrame frame = new TimeSyncFrame();
		frame = BaseFrame.processBaseInfo(frame, message);
		int offset = 7;

		frame.flag = Integer.parseInt(Utils.byteToHex(message[offset]), 16);
		offset++;

		byte[] _info = new byte[6];
		System.arraycopy(message, offset, _info, 0, 6);
		StringBuffer sb = new StringBuffer();
		int i = -1;
		String ss = "";
		sb.append("20");
		for (byte b : _info) {
			i = (int) b;
			ss = i < 10 ? "0" + i : String.valueOf(i);
			sb.append(ss);
		}
		// System.out.println(sb.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			String data = "20321212121212";
			Date time2 = sdf.parse(data);
			Date time1 = sdf.parse(sb.toString());
			if (time1.getTime() < time2.getTime()) {
				frame.time = sdf.parse(sb.toString());
			} else {
				Log.d("TimeSyncFrame", "校时越界: ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return frame;
	}

	public static void main(String[] args) {
		String s = "0F0805101A1E";
		byte[] bs = DigitalTrans.hex2byte(s);
		StringBuffer sb = new StringBuffer();
		int i = -1;
		String ss = "";
		for (byte b : bs) {
			i = (int) b;
			ss = i < 10 ? "0" + i : String.valueOf(i);
			sb.append(ss);
		}
		System.out.println(sb.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		try {
			Date d = sdf.parse(sb.toString());
			System.out.println(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
