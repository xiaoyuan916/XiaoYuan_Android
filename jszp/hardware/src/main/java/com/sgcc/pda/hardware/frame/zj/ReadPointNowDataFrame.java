package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月25日 下午3:21:45
 * 
 * @Description: 读取测量点当前数据
 * 
 */

public class ReadPointNowDataFrame extends BaseFrame {

	@Override
	protected byte[] setData() {
		byte[] data = new byte[20];
		for (int i = 0; i < data.length; i++) {
			if (i == 0) {
				data[i] = (byte) 0x0f;
			} else {
				data[i] = (byte) 0x0;
			}
		}
		data[8] = (byte) 0x00;
		data[9] = (byte) 0x89;
		data[10] = (byte) 0x01;
		data[11] = (byte) 0x89;
		data[12] = (byte) 0x02;
		data[13] = (byte) 0x89;
		data[14] = (byte) 0x03;
		data[15] = (byte) 0x89;
		data[16] = (byte) 0x04;
		data[17] = (byte) 0x89;
		data[18] = (byte) 0x05;
		data[19] = (byte) 0x89;
		return data;
	}

	@Override
	protected byte setControl() {
		return ControlCode.READ_NOW_DATA.getCode();
	}

	@Override
	protected String decodeData(byte[] data) {
		StringBuilder sb = new StringBuilder();

		byte[] value = new byte[] { data[11], data[12], data[13], data[14],
				data[15], data[16], data[17], data[18] };
		String valueStr = Util.BytesToHexL(value, 0, value.length);
		sb.append("测量点:" + valueStr + "\n");

		int count = calculateMeasurePointCount(value);

		sb.append("测量点个数:" + count + "\n");

		for (int i = 0; i < count; i++) {
			// 测量点的状态 8900
			if (data[21 + i] == 1) {
				sb.append("测量点" + (i + 1) + "状态:有效" + "\n");
			} else {
				sb.append("测量点" + (i + 1) + "状态:无效" + "\n");
				continue;
			}

			// 测量点性质 8901
			String name = MeasurePointProperty
					.getName(data[21 + count + 2 + i]);
			sb.append("测量点" + (i + 1) + "性质:" + name + "\n");

			// 仅对于485量
			if (data[21 + count + 2 + i] == MeasurePointProperty.BIAO_485
					.getCode()) {
				// 测量点地址 8902
				byte a = data[21 + 2 * (count + 2) + i * 6 + 5];
				byte b = data[21 + 2 * (count + 2) + i * 6 + 4];
				byte c = data[21 + 2 * (count + 2) + i * 6 + 3];
				byte d = data[21 + 2 * (count + 2) + i * 6 + 2];
				byte e = data[21 + 2 * (count + 2) + i * 6 + 1];
				byte f = data[21 + 2 * (count + 2) + i * 6 + 0];

				String str = Util.BytesToHexL(new byte[] { a, b, c, d, e, f },
						0, 6);
				sb.append("测量点" + (i + 1) + "地址:" + str + "\n");

				// 测量点通讯规约 8903
				byte g = data[21 + 2 * (count + 2) + 2 + count * 6 + i];

				if (g == 10) {
					sb.append("测量点" + (i + 1) + "通讯规约:部颁规约" + "\n");
				} else if (g == 20) {
					sb.append("测量点" + (i + 1) + "通讯规约:浙江规约" + "\n");
				}

				// 测量点端口号 8904
				byte h = data[21 + 3 * (count + 2) + 2 + count * 6 + i];
				sb.append("测量点" + (i + 1) + "端口号:" + h + "\n");
			}

			// 测量点的波特率 8905
			byte k = data[21 + 4 * (count + 2) + 2 + count * 6 + i];
			sb.append("测量点" + (i + 1) + "波特率:" + (k * 300) + "\n");

		}

		return sb.toString();
	}

	/**
	 * 计算测量点个数
	 * 
	 * @param data
	 * @return
	 */
	private int calculateMeasurePointCount(byte[] data) {
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			String binaryString = Util.byteToBit(data[i]);
			String replaceAll = binaryString.replaceAll("0", "");
			count += replaceAll.length();
		}
		return count;
	}

}
