package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月25日 下午3:21:45
 * 
 * @Description: 读取终端当前数据
 * 
 */

public class ReadTerminalNowDataFrame extends BaseFrame {

	private MasterStationCommunicationAddress mAddressZero;// 主站地址
	private MasterStationCommunicationAddress mAddressOne;// 备份
	private MasterStationCommunicationAddress mAddressTwo;// 备份

	private String mSmsCenterNum;
	private String mGateWay;
	private String mApn;

	@Override
	protected byte[] setData() {
		byte[] data = new byte[20];
		for (int i = 0; i < data.length; i++) {
			if (i == 0) {
				data[i] = (byte) 0x1;
			} else {
				data[i] = (byte) 0x0;
			}

		}
		data[8] = (byte) 0x10;
		data[9] = (byte) 0x80;
		data[10] = (byte) 0x11;
		data[11] = (byte) 0x80;
		data[12] = (byte) 0x12;
		data[13] = (byte) 0x80;
		data[14] = (byte) 0x13;
		data[15] = (byte) 0x80;
		data[16] = (byte) 0x14;
		data[17] = (byte) 0x80;
		data[18] = (byte) 0x15;
		data[19] = (byte) 0x80;
		return data;
	}

	@Override
	protected byte setControl() {
		return ControlCode.READ_NOW_DATA.getCode();
	}

	@Override
	protected String decodeData(byte[] data) {
		StringBuilder sb = new StringBuilder();
		int start = 21;
		int offset = 11;
		// 主站通讯地址 8010
		byte[] com = new byte[] { data[start + 8], data[start + 7],
				data[start + 6], data[start + 5], data[start + 4],
				data[start + 3], data[start + 2], data[start + 1],
				data[start + 0] };
		// 备用主站通讯地址1 8011
		byte[] com1 = new byte[] { data[start + 8 + offset],
				data[start + 7 + offset], data[start + 6 + offset],
				data[start + 5 + offset], data[start + 4 + offset],
				data[start + 3 + offset], data[start + 2 + offset],
				data[start + 1 + offset], data[start + 0 + offset] };
		// 备用主站通讯地址2 8012
		byte[] com2 = new byte[] { data[start + 8 + 2 * offset],
				data[start + 7 + 2 * offset], data[start + 6 + 2 * offset],
				data[start + 5 + 2 * offset], data[start + 4 + 2 * offset],
				data[start + 3 + 2 * offset], data[start + 2 + 2 * offset],
				data[start + 1 + 2 * offset], data[start + 0 + 2 * offset] };

		mAddressZero = new MasterStationCommunicationAddress();
		decodeCom(com, sb, 0, mAddressZero);
		mAddressOne = new MasterStationCommunicationAddress();
		decodeCom(com1, sb, 1, mAddressOne);
		mAddressTwo = new MasterStationCommunicationAddress();
		decodeCom(com2, sb, 2, mAddressTwo);

		// 短信中心号码 8013
		byte[] sms = new byte[] { data[start + 7 + 3 * offset],
				data[start + 6 + 3 * offset], data[start + 5 + 3 * offset],
				data[start + 4 + 3 * offset], data[start + 3 + 3 * offset],
				data[start + 2 + 3 * offset], data[start + 1 + 3 * offset],
				data[start + 0 + 3 * offset] };
		decodeSms(sms, sb);

		// 默认网关地址或者代理服务器地址和端口 8014
		byte[] gateWay = new byte[] { data[start + 7 + 4 * offset - 1],
				data[start + 6 + 4 * offset - 1],
				data[start + 5 + 4 * offset - 1],
				data[start + 4 + 4 * offset - 1],
				data[start + 3 + 4 * offset - 1],
				data[start + 2 + 4 * offset - 1],
				data[start + 1 + 4 * offset - 1],
				data[start + 0 + 4 * offset - 1] };
		decodeGateWay(gateWay, sb);

		// APN 8015
		byte[] apn = new byte[] { data[start + 15 + 5 * offset - 2],
				data[start + 14 + 5 * offset - 2],
				data[start + 13 + 5 * offset - 2],
				data[start + 12 + 5 * offset - 2],
				data[start + 11 + 5 * offset - 2],
				data[start + 10 + 5 * offset - 2],
				data[start + 9 + 5 * offset - 2],
				data[start + 8 + 5 * offset - 2],
				data[start + 7 + 5 * offset - 2],
				data[start + 6 + 5 * offset - 2],
				data[start + 5 + 5 * offset - 2],
				data[start + 4 + 5 * offset - 2],
				data[start + 3 + 5 * offset - 2],
				data[start + 2 + 5 * offset - 2],
				data[start + 1 + 5 * offset - 2],
				data[start + 0 + 5 * offset - 2] };
		decodeApn(apn, sb);

		return sb.toString();
	}

	private void decodeApn(byte[] apn, StringBuilder sb) {
		sb.append("APN:");
		String str = "";
		for (int i = 0; i < apn.length; i++) {
			if (apn[i] == (byte) 0x0 || apn[i] == (byte) 0xff) {
				continue;
			}

			// sb.append((char) apn[i]);
			str += (char) apn[i];
		}
		sb.append(str + "\n");
		mApn = str;

	}

	private void decodeGateWay(byte[] gateWay, StringBuilder sb) {
		if (gateWay[0] == (byte) 0xff) {
			sb.append("默认网关地址或者代理服务器地址和端口无效!\n");
			return;
		}
		int p = (gateWay[6] << 8) + (gateWay[7] & 0xff);
		String ip = (gateWay[2] & 0xff) + "." + (gateWay[3] & 0xff) + "."
				+ (gateWay[4] & 0xff) + "." + (gateWay[5] & 0xff) + ":" + p;
		sb.append("默认网关地址或者代理服务器地址和端口:" + ip + "\n");

		mGateWay = ip;
	}

	private void decodeSms(byte[] sms, StringBuilder sb) {
		String num = "";
		boolean flag = true;
		for (int i = 0; i < sms.length; i++) {
			if (sms[0] == (byte) 0xff) {
				sb.append("短信中心号码:无效!\n");
				return;
			}
			if (sms[i] == (byte) 0xaa && flag) {
				continue;
			}
			flag = false;
			if (sms[i] == (byte) 0xa1) {
				sms[i] = (byte) 0x01;
			}
			String s = Util.ByteToHex(sms[i]);
			num += s;
		}
		sb.append("短信中心号码:" + num + "\n");
		mSmsCenterNum = num;
	}

	private void decodeCom(byte[] com, StringBuilder sb, int c,
			MasterStationCommunicationAddress address) {
		switch (c) {
		case 0:
			sb.append("主站通讯地址,");
			break;
		case 1:
			sb.append("备用主站通讯地址1,");
			break;
		case 2:
			sb.append("备用主站通讯地址2,");
			break;

		default:
			break;
		}
		switch (com[0]) {
		case (byte) 0x01:
		case (byte) 0x03:
		case (byte) 0x07:
			String name = CommunicationType.getName(com[0]);
			sb.append("通信通道类型:" + name + "\n");
			String num = Util.BytesToHexL(new byte[] { com[3], com[4], com[5],
					com[6], com[7], com[8] }, 0, 6);
			sb.append("主站号码:" + num + "\n");
			address.setType(name);
			address.setValue(num);
			break;
		case (byte) 0x02:
		case (byte) 0x04:
			name = CommunicationType.getName(com[0]);
			sb.append("通信通道类型:" + name + "\n");
			int p = (com[7] << 8) + (com[8] & 0xff);
			String ip = "" + (com[3] & 0xff) + "." + (com[4] & 0xff) + "."
					+ (com[5] & 0xff) + "." + (com[6] & 0xff) + ":" + p;

			sb.append("IP地址:" + ip + "\n");
			address.setType(name);
			address.setValue(ip);
			break;

		case (byte) 0x05:
		case (byte) 0x06:
		case (byte) 0x08:
			name = CommunicationType.getName(com[0]);
			sb.append("通信通道类型:" + name + "\n");
			String str = "" + (com[1] & 0xff) + (com[2] & 0xff)
					+ (com[3] & 0xff) + (com[4] & 0xff) + (com[5] & 0xff)
					+ (com[6] & 0xff) + (com[7] & 0xff) + (com[8] & 0xff);
			sb.append("通信地址地址:" + str + "\n");
			address.setType(name);
			address.setValue(str);
			break;
		default:
			sb.append("通信通道类型:无效" + "\n");
			break;
		}
	}

	public MasterStationCommunicationAddress getmAddressZero() {
		return mAddressZero;
	}

	public MasterStationCommunicationAddress getmAddressOne() {
		return mAddressOne;
	}

	public MasterStationCommunicationAddress getmAddressTwo() {
		return mAddressTwo;
	}

	public String getmSmsCenterNum() {
		return mSmsCenterNum;
	}

	public String getmGateWay() {
		return mGateWay;
	}

	public String getmApn() {
		return mApn;
	}

}
