package com.sgcc.pda.hardware.frame;


import com.sgcc.pda.hardware.util.ToastManager;

public class ClassAmmter07And97Decode {

	private byte[] address = new byte[6];// 通讯地址
	private byte[] controlCode = new byte[1];// 控制符
	private byte[] length = new byte[1];// 长度
	private byte[] dataBuffer;// 整数
	private byte[] cs = new byte[1];// 验证符
	private int mPostion = 0;

	public ClassAmmter07And97Decode reWind() {
		mPostion = 0;
		return this;
	}

	public ClassAmmter07And97Decode(byte[] data) {
		byteToClass(data);
	}

	public ClassAmmter07And97Decode byteToClass(byte[] data) {
		if (data == null || data[data.length - 1] != 0x16) {
			ToastManager.getInstance().displayToast("数据错误");
			return this;
		}
		int pos = -1;
		for (int n = 0; n < data.length; n++) {
			if (data[n] != 0xff && data[n] == 0x68) {
				pos = n;
				break;
			}
		}
		if (pos == -1) {
			ToastManager.getInstance().displayToast("数据错误");
			return this;
		}
		try {
			mPostion = pos + new Byte[] { 0x68 }.length;
			System.arraycopy(data, mPostion, address, 0, address.length);
			mPostion = mPostion + address.length;
			mPostion = mPostion + new Byte[] { 0x68 }.length;
			System.arraycopy(data, mPostion, controlCode, 0, controlCode.length);
			mPostion = mPostion + controlCode.length;
			System.arraycopy(data, mPostion, length, 0, length.length);
			mPostion = mPostion + length.length;
			dataBuffer = new byte[(int) length[0]];
			System.arraycopy(data, mPostion, dataBuffer, 0, dataBuffer.length);
			mPostion = mPostion + dataBuffer.length;
			System.arraycopy(data, mPostion, cs, 0, cs.length);
			mPostion = mPostion + cs.length;
			mPostion = 0;
		} catch (Exception e) {
			e.printStackTrace();
			ToastManager.getInstance().displayToast("数据格式错误");
		}

		return this;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	public byte[] getControlCode() {
		return controlCode;
	}

	public void setControlCode(byte[] controlCode) {
		this.controlCode = controlCode;
	}

	public byte[] getLength() {
		return length;
	}

	public void setLength(byte[] length) {
		this.length = length;
	}

	public byte[] getDataBuffer() {
		return dataBuffer;
	}

	public void setDataBuffer(byte[] dataBuffer) {
		this.dataBuffer = dataBuffer;
	}

	public byte[] getCs() {
		return cs;
	}

	public void setCs(byte[] cs) {
		this.cs = cs;
	}

	public int getmPostion() {
		return mPostion;
	}

	public void setmPostion(int mPostion) {
		this.mPostion = mPostion;
	}

}
