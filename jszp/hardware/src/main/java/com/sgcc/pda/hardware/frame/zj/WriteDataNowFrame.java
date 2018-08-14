package com.sgcc.pda.hardware.frame.zj;

import java.nio.ByteBuffer;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年9月11日 下午1:55:25
 * 
 * @Description:
 * 
 */

public class WriteDataNowFrame extends BaseFrame {

	public final int CommunicationZero = 8010;
	public final int CommunicationOne = 8011;
	public final int CommunicationTwo = 8012;

	private byte[] addressZero;
	private byte[] addressOne;
	private byte[] addressTwo;

	private byte[] sms;

	private byte[] gateWay;

	private byte[] apn;

	private byte[] psw;

	@Override
	protected byte setControl() {
		return ControlCode.WRITE_DATA.getCode();
	}

	// 6811531210770068814F00 0100000000000000 1080 8A 20 08 FD 89 0A AA AA 02
	// 1180 00FF00FF00FF00FF00 1280 FF00FF00FF00FF00FF
	// 1380 00FF00FF00FF00FF 1480 FF0000FF00FFAAAA
	// 1580 6A 7A 2E 6C 64 6A 7A 000000000000000000 AF16

	@Override
	protected byte[] setData() {
		boolean b1 = false;
		boolean b2 = false;
		boolean b3 = false;
		boolean b4 = false;
		boolean b5 = false;
		boolean b6 = false;
		int len = 5;
		if (addressZero != null && addressZero.length > 0) {
			len += addressZero.length;
			b1 = true;
		}
		if (addressOne != null && addressOne.length > 0) {
			len += addressOne.length;
			b2 = true;
		}
		if (addressTwo != null && addressTwo.length > 0) {
			len += addressTwo.length;
			b3 = true;
		}

		if (sms != null && sms.length > 0) {
			len += sms.length;
			b4 = true;
		}

		if (gateWay != null && gateWay.length > 0) {
			len += gateWay.length;
			b5 = true;
		}
		if (apn != null && apn.length > 0) {
			len += apn.length;
			b6 = true;
		}

		byte[] data = new byte[len];

		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.put((byte) 0x0);
		buf.put((byte) 0x11);
		// 密码
		buf.put(psw);
		if (b1) {
			buf.put(addressZero);
		}
		if (b2) {
			buf.put(addressOne);
		}
		if (b3) {
			buf.put(addressTwo);
		}
		if (b4) {
			buf.put(sms);
		}
		if (b5) {
			buf.put(gateWay);
		}
		if (b6) {
			buf.put(apn);
		}

		return data;
	}

	public void setRtua(String value) {
		rtua.setRtuaStr(value);
	}

	/**
	 * 设置密码
	 * 
	 * @param value
	 */
	public boolean setPassword(String value) {
		if (value == null || value.length() != 6) {
			return false;
		}
		psw = new byte[3];
		StringBuilder sb = new StringBuilder();
		sb.append(value.substring(4)).append(value.substring(2, 4))
				.append(value.substring(0, 2));
		Util.HexsToBytes(psw, 0, sb.toString());
		return true;
	}

	/**
	 * 设置apn
	 * 
	 * @param value
	 */
	public boolean setApn(String value) {
		if (value == null) {
			return false;
		}
		apn = new byte[18];
		for (int i = 0; i < apn.length; i++) {
			apn[0] = 0x00;
		}
		apn[0] = (byte) 0x15;
		apn[1] = (byte) 0x80;

		char[] charArray = value.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			apn[i + 2] = (byte) charArray[charArray.length - i - 1];
		}

		return true;
	}

	/**
	 * 设置网关
	 * 
	 * @param value
	 * @return
	 */
	public boolean setGateWay(String value) {
		gateWay = new byte[10];
		try {
			gateWay[0] = (byte) 0x14;
			gateWay[1] = (byte) 0x80;
			gateWay[9] = (byte) 0xaa;
			gateWay[8] = (byte) 0xaa;
			String[] split = value.split(":");
			String[] ip = split[0].split("\\.");
			gateWay[7] = (byte) Integer.parseInt(ip[0]);
			gateWay[6] = (byte) Integer.parseInt(ip[1]);
			gateWay[5] = (byte) Integer.parseInt(ip[2]);
			gateWay[4] = (byte) Integer.parseInt(ip[3]);

			int p = Integer.parseInt(split[1]);
			byte l = (byte) (p & 0xff);
			byte h = (byte) ((p - (l & 0xff)) >> 8);

			gateWay[3] = h;
			gateWay[2] = l;
			return true;
		} catch (Exception e) {
			gateWay = null;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 设置短信中心号码
	 * 
	 * @param value
	 * @return
	 */
	public boolean setSmsCenterNum(String value) {
		try {
			sms = new byte[10];
			sms[0] = (byte) 0x13;
			sms[1] = (byte) 0x80;
			sms[9] = (byte) 0xaa;
			sms[8] = (byte) 0xaa;
			sms[7] = (byte) Integer.parseInt(value.substring(0, 1));
			sms[6] = (byte) Integer.parseInt(value.substring(1, 3));
			sms[5] = (byte) Integer.parseInt(value.substring(3, 5));
			sms[4] = (byte) Integer.parseInt(value.substring(5, 7));
			sms[3] = (byte) Integer.parseInt(value.substring(7, 9));
			sms[2] = (byte) Integer.parseInt(value.substring(9));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			sms = null;
			return false;
		}
	}

	/**
	 * 设置主站通讯地址
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean setCommunicationAddressZero(CommunicationType type,
			String value) {
		addressZero = new byte[11];
		boolean b = setCommunicationAddress(type, value,
				CommunicationZero, addressZero);
		if (!b) {
			addressZero = null;
		}
		return b;
	}

	/**
	 * 设置备用主站通讯地址1
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean setCommunicationAddressOne(CommunicationType type,
			String value) {
		addressOne = new byte[11];
		boolean b = setCommunicationAddress(type, value,
				CommunicationOne, addressOne);
		if (!b) {
			addressOne = null;
		}
		return b;
	}

	/**
	 * 设置备用主站通讯地址2
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public boolean setCommunicationAddressTwo(CommunicationType type,
			String value) {
		addressTwo = new byte[11];
		boolean b = setCommunicationAddress(type, value,
				CommunicationTwo, addressTwo);
		if (!b) {
			addressTwo = null;
		}
		return b;
	}

	/**
	 * 
	 * @param type
	 * @param value
	 * @param c
	 * @return
	 */
	private boolean setCommunicationAddress(CommunicationType type,
			String value, int c, byte[] address) {
		switch (c) {
		case CommunicationZero:
			address[0] = (byte) 0x10;
			address[1] = (byte) 0x80;
			break;
		case CommunicationOne:
			address[0] = (byte) 0x11;
			address[1] = (byte) 0x80;
			break;
		case CommunicationTwo:
			address[0] = (byte) 0x12;
			address[1] = (byte) 0x80;
			break;

		default:
			break;
		}

		address[10] = type.getCode();

		switch (type) {
		case SMS:
		case DTMF:
		case CSD:
			try {
				address[9] = (byte) 0xaa;
				address[8] = (byte) 0xaa;
				address[7] = (byte) Integer.parseInt(value.substring(0, 1));
				address[6] = (byte) Integer.parseInt(value.substring(1, 3));
				address[5] = (byte) Integer.parseInt(value.substring(3, 5));
				address[4] = (byte) Integer.parseInt(value.substring(5, 7));
				address[3] = (byte) Integer.parseInt(value.substring(7, 9));
				address[2] = (byte) Integer.parseInt(value.substring(9));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		case GPRS:
		case ETHERNET:
			try {
				address[9] = (byte) 0xaa;
				address[8] = (byte) 0xaa;
				String[] split = value.split(":");
				String[] ip = split[0].split("\\.");
				address[7] = (byte) Integer.parseInt(ip[0]);
				address[6] = (byte) Integer.parseInt(ip[1]);
				address[5] = (byte) Integer.parseInt(ip[2]);
				address[4] = (byte) Integer.parseInt(ip[3]);

				int p = Integer.parseInt(split[1]);
				byte l = (byte) (p & 0xff);
				byte h = (byte) ((p - (l & 0xff)) >> 8);

				address[3] = h;
				address[2] = l;
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		case INFRA:
		case RS232:
		case RADIO:
			// address[9] = (byte) 0xaa;
			// address[8] = (byte) 0xaa;
			// address[7] = (byte) Integer.parseInt(value.substring(0, 1));
			// address[6] = (byte) Integer.parseInt(value.substring(1, 3));
			// address[5] = (byte) Integer.parseInt(value.substring(3, 5));
			// address[4] = (byte) Integer.parseInt(value.substring(5, 7));
			// address[3] = (byte) Integer.parseInt(value.substring(7, 9));
			// address[2] = (byte) Integer.parseInt(value.substring(9));
			break;

		default:
			return false;
		}
		return false;
	}

	@Override
	protected String decodeData(byte[] data) {
		StringBuilder sb = new StringBuilder();
		byte[] value = new byte[data.length - 2 - 12];
		for (int i = 12; i < data.length - 2; i++) {
			value[i - 12] = data[i];
		}

		for (int i = 0; i < value.length / 3; i++) {
			String s = Util.BytesToHexL(new byte[] { value[3 * i + 1] },
					value[3 * i + 0], 2);
			String name = ErrorCode.getName(value[3 * i + 2]);
			sb.append("数据项:" + s + "设置结果:" + name);
		}

		return sb.toString();
	}

}
