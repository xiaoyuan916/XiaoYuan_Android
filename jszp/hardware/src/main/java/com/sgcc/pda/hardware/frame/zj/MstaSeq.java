package com.sgcc.pda.hardware.frame.zj;

/**
 * @author WangJinKe
 * 
 * @version: 1.0
 * 
 * @CreateTime 2014年8月20日 上午10:18:22
 * 
 * @Description: 主站地址与命令序号
 * 
 */

public class MstaSeq {
	private byte ms1;
	private byte ms2;

	private byte[] value;

	/**
	 * 数组长度必须为2
	 * 
	 * @param value
	 */
	public MstaSeq(byte[] value) {
		super();
		if (value.length == 2) {
			this.value = value;
		} else {
			throw new RuntimeException("数组长度必须为2");
		}

	}

	/**
	 * 
	 * @param iseq
	 *            取值0-7
	 * @param fseq
	 *            取值01H～07FH
	 * @return
	 */
	public byte[] encode(byte iseq, byte fseq) {
		ms1 = 0;
		ms2 = 0;
		setMsta();
		setIseq(iseq);
		setFseq(fseq);
		value[0] = ms1;
		value[1] = ms2;
		return value;
	}

	public String decode() {
		String str = Util.BytesToHexL(value, 0, value.length);
		return "主站地址与命令序号:" + str;
	}

	/**
	 * 设置主站地址(D5-D0)
	 */
	private void setMsta() {
		ms1 = (byte) (ms1 | (byte) 0x37);
	}

	/**
	 * 设置帧内序号(D15-D13)
	 * 
	 * @param iseq
	 *            取值0-7
	 */
	private void setIseq(byte iseq) {
		if (iseq >= 0 && iseq <= 7) {
			ms2 = (byte) (ms2 | iseq);
		} else {
			throw new RuntimeException("iseq取值0-7");
		}
	}

	/**
	 * 设置帧序号(D12-D6)
	 * 
	 * @param fseq
	 *            取值01H～07FH
	 */
	private void setFseq(byte fseq) {

		if (fseq >= 0x1 && fseq <= 0x7f) {
			ms1 = (byte) (ms1 | (fseq & 3) << 6);
			ms2 = (byte) (ms2 | (fseq >> 2));
		} else {
			throw new RuntimeException("fseq取值01H～07FH");
		}

	}

}
