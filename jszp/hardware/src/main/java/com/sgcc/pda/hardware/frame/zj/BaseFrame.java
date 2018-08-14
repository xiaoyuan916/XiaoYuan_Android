package com.sgcc.pda.hardware.frame.zj;

import java.nio.ByteBuffer;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月20日 上午10:28:19
 * 
 * @Description:
 * 
 */

public abstract class BaseFrame {

	protected Rtua rtua;
	protected MstaSeq mstaSeq;
	protected Control control;

	/**
	 * 是否有效帧
	 */
	private boolean isFrameOk;
	/**
	 * 是否异常帧
	 */
	private boolean isFrameUnusual;
	/**
	 * 异常原因
	 */
	private String unusualReason;

	public BaseFrame() {
		super();
		rtua = new Rtua(new byte[4]);
		mstaSeq = new MstaSeq(new byte[2]);
	}

	public byte[] encode() {

		byte[] data = setData();
		byte con = setControl();

		int len = 5 + 13 + data.length;
		byte[] result = new byte[len];
		ByteBuffer buffer = ByteBuffer.wrap(result);

		buffer.put((byte) 0xFE);
		buffer.put((byte) 0xFE);
		buffer.put((byte) 0xFE);
		buffer.put((byte) 0xFE);
		buffer.put((byte) 0xFE);

		buffer.put((byte) 0x68);

		buffer.put(rtua.encode());

		buffer.put(mstaSeq.encode((byte) 0, (byte) 1));

		buffer.put((byte) 0x68);

		buffer.put(con);

		buffer.put(getDataLength(data));

		buffer.put(data);

		byte cs = Util.calculateCS(result, 5, len - 5 - 2);
		buffer.put(cs);

		buffer.put((byte) 0x16);
		return result;
	}

	/**
	 * 设置控制码
	 * 
	 * @return
	 */
	protected abstract byte setControl();

	/**
	 * 设置数据域
	 * 
	 * @return
	 */
	protected abstract byte[] setData();

	private byte[] getDataLength(byte[] data) {
		byte[] length = new byte[2];
		length[0] = (byte) (data.length % 0x100);
		length[1] = (byte) (data.length / 0x100);
		return length;
	}

	public Rtua getRtua() {
		return rtua;
	}

	public MstaSeq getMstaSeq() {
		return mstaSeq;
	}

	public String decode(byte[] data) {
		if (data == null || data.length < 13) {
			isFrameOk = false;
			return "无效帧";
		}

		int offset = 0;
		byte[] dst = null;
		for (int i = 0; i < data.length; i++) {
			if (data[i] == (byte) 0x68) {
				offset = i;
				break;
			}
		}

		if (offset == 0) {
			dst = data;
		} else {
			dst = new byte[data.length - offset];
			for (int i = offset; i < data.length; i++) {
				dst[i - offset] = data[i];
			}
		}

		// 计算数据域长度
		int l = (dst[10] & 0xff) * 256 + (dst[9] & 0xff);

		if (dst.length < l + 13) {
			isFrameOk = false;
			return "无效帧";
		}

		// 检查是否帧结尾
		if ((byte) 0x16 != dst[l + 12]) {
			isFrameOk = false;
			return "无效帧";
		}

		// 检查校验码是否一致
		byte cs = Util.calculateCS(dst, 0, l + 11);
		if (cs != dst[l + 11]) {
			isFrameOk = false;
			return "无效帧";
		}

		StringBuilder sb = new StringBuilder();

		// 判断帧状态
		Control controlD = new Control(dst[8]);
		int flag = controlD.getFlag();
		if (flag == 1) {
			sb.append("异常帧\n");
			isFrameUnusual = true;
			String name = ErrorCode.getName(dst[11]);
			if (name != null) {
				sb.append("异常原因:" + name + "\n");
				unusualReason = name;
			}
			return sb.toString();
		}

		isFrameOk = true;
		isFrameUnusual = false;

		// 解析终端逻辑地址
		Rtua rtuaD = new Rtua(new byte[] { dst[1], dst[2], dst[4], dst[3] });
		String rtuaStr = rtuaD.decode();
		sb.append(rtuaStr + "\n");

		// 主站地址与命令序列
		MstaSeq mstaSeqD = new MstaSeq(new byte[] { dst[5], dst[6] });
		String mstaSeqStr = mstaSeqD.decode();
		sb.append(mstaSeqStr + "\n");

		String cStr = controlD.decode();
		sb.append(cStr + "\n");

		String decodeData = decodeData(dst);
		if (decodeData != null) {
			sb.append(decodeData + "\n");
		}

		return sb.toString();
	}

	/**
	 * 解析数据域
	 * 
	 * @return
	 */
	protected abstract String decodeData(byte[] data);

	public boolean isFrameOk() {
		return isFrameOk;
	}

	public boolean isFrameUnusual() {
		return isFrameUnusual;
	}

	public String getUnusualReason() {
		return unusualReason;
	}

}
