package com.sgcc.pda.hardware.frame;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.ToastManager;

public class ClassF4 {
	private byte[] phoneNum = new byte[] { (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF };// 主站电话号码或手机号码
	private byte[] shortMessNum = new byte[] { (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF };// 短信中心号码

	private int mPostion = 0;

	public boolean byteToClass(byte[] data) {
		if (data == null || data.length != 16) {
			ToastManager.getInstance().displayToast("数据错误");
			return false;
		}
		mPostion = 0;
		try {
			System.arraycopy(data, mPostion, phoneNum, 0, phoneNum.length);
			mPostion = mPostion + phoneNum.length;
			System.arraycopy(data, mPostion, shortMessNum, 0,
					shortMessNum.length);
			mPostion = 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public byte[] classToByte() {
		byte[] data = new byte[16];
		mPostion = 0;
		try {
			System.arraycopy(phoneNum, 0, data, mPostion, phoneNum.length);
			mPostion = mPostion + phoneNum.length;
			System.arraycopy(shortMessNum, 0, data, mPostion,
					shortMessNum.length);
			mPostion = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public String getPhoneNumString() {
		String ip = "" + (BcdUtils.binArrayToString(phoneNum));
		return ip;
	}

	//
	public String getShortMessNumString() {
		String num = "" + (BcdUtils.binArrayToString(shortMessNum));
		return num;
	}

	public void setPhoneNumString(String phoneNumString) {
		byte[] data = BcdUtils.stringToByteArray(phoneNumString);
		System.arraycopy(data, 0, phoneNum, 0,
				data.length < phoneNum.length ? data.length : phoneNum.length);
	}

	public void setShortMessNumString(String shortNumString) {
		byte[] data = BcdUtils.stringToByteArray(shortNumString);
		System.arraycopy(data, 0, shortMessNum, 0,
				data.length < shortMessNum.length ? data.length
						: shortMessNum.length);
	}

	public byte[] getphoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(byte[] phoneNum) {
		this.phoneNum = phoneNum;
	}

	public byte[] getShortMessNum() {
		return shortMessNum;
	}

	public void setShortMessNum(byte[] shortMessNum) {
		this.shortMessNum = shortMessNum;
	}

}
