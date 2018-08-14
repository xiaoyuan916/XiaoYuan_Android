package com.sgcc.pda.hardware.frame.zj;


import com.sgcc.pda.hardware.frame.ClassAmmter07And97Decode;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645MeterPacket;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

import java.nio.ByteBuffer;


/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年9月3日 下午2:08:52
 * 
 * @Description:
 * 
 */

public class ReadZhongJiFrame extends BaseFrame {

	private boolean trueFor07Ammter = true;

	@Override
	protected byte setControl() {
		return ControlCode.ZHONG_JI.getCode();
	}

	@Override
	protected byte[] setData() {
		Gb645MeterPacket meterpack = new Gb645MeterPacket("aaaaaaaa");// 11790478
		meterpack.getControlCode().setValue((byte) 0x11);
		meterpack.getData().putLongWord(0x04000102);
		byte[] dt = new byte[meterpack.getValue().length + 4];
		System.arraycopy(new byte[]{(byte) 0xFE, (byte) 0xFE, (byte) 0xFE,
				(byte) 0xFE}, 0, dt, 0, 4);
		System.arraycopy(meterpack.getValue(), 0, dt, 4,
				meterpack.getValue().length);

		byte[] data = new byte[7 + dt.length];
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.put((byte) 0x1);
		buf.put((byte) 0x5);
		buf.put((byte) 0x0);
		buf.put((byte) 0x0);
		buf.put((byte) 0x0);
		buf.put((byte) 0x0);
		buf.put((byte) 0x0);
		buf.put(dt);
		return data;

	}

	public ReadZhongJiFrame setTrueFor07Ammter(boolean trueFor07Ammter) {
		this.trueFor07Ammter = trueFor07Ammter;
		return this;
	}

	@Override
	protected String decodeData(byte[] data) {
		byte[] bs = new byte[23];
		for (int i = 12; i < 35; i++) {
			bs[i - 12] = data[i];
		}

		StringBuilder sb = new StringBuilder();

		if (trueFor07Ammter) {
			try {
				ClassAmmter07And97Decode decode = new ClassAmmter07And97Decode(
						bs);
				byte[] data1 = decode.getDataBuffer();
				data1 = setDataBytes(data1, 0, data1.length);
				String time = (BcdUtils.bcdToInt(data1[2])) + "时"
						+ (BcdUtils.bcdToInt(data1[1])) + "分"
						+ (BcdUtils.bcdToInt(data1[0])) + "秒";
				sb.append("报文时间：" + BcdUtils.binArrayToString(data1) + "\n");
				sb.append("date:" + time + "\n");
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("表计时钟召测失败");
			}
		} else {
			try {
				ClassAmmter07And97Decode decode = new ClassAmmter07And97Decode(
						bs);
				byte[] data1 = decode.getDataBuffer();
				data1 = setDataBytes(data1, 0, data1.length);
				sb.append("报文时间：" + BcdUtils.binArrayToString(data1) + "\n");
				String time = (BcdUtils.bcdToInt(data1[data1.length - 1]))
						+ "时" + (BcdUtils.bcdToInt(data1[data1.length - 2]))
						+ "分" + (BcdUtils.bcdToInt(data1[data1.length - 3]))
						+ "秒";
				sb.append("date:" + time + "\n");
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("表计时钟召测失败");
			}
		}

		return sb.toString();
}

	private byte[] setDataBytes(byte[] msg, int beginIndex, int len) { // 将接收到的序列转换为去掉33
		byte[] dataBytes = new byte[len];
		for (int i = 0; i < len; i++) {
			// DATABYTES[I] = (BYTE) (MSG[BEGININDEX + I] + 0X0100 - 0X0033);
			dataBytes[i] = (byte) (msg[beginIndex + i] - 0x33);
		}
		return dataBytes;
	}

}
