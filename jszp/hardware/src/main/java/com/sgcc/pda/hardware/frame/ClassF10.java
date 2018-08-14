package com.sgcc.pda.hardware.frame;


import com.sgcc.pda.hardware.util.ToastManager;

public class ClassF10 {

	private byte[] position = new byte[2];// 序号
	private byte[] measurePoint = new byte[2];// 测量点
	private byte[] speedAndPort = new byte[1];// 通信速率&&端口
	private byte[] procole = new byte[1];// 通讯协议
	private byte[] address = new byte[6];// 通讯地址
	private byte[] password = new byte[6];// 通讯密码
	private byte[] flnum = new byte[1];// 费率个数（1-48）
	private byte[] zhenshuAndXiaoshuNum = new byte[1];// 整数位个数（4-7） //
	private byte[] caijiqiAddress = new byte[6];// 采集器地址（1-9999999999）
	private byte[] bigAndSmallNum = new byte[1];// 大类个数（0-15）
	private int mPostion = 0;

	public ClassF10 byteToClass(byte[] data) {
		if (data == null || data.length != 27) {
			ToastManager.getInstance().displayToast("数据个数错误");
		}
		mPostion = 0;
		try {
			System.arraycopy(data, mPostion, position, 0, position.length);
			mPostion = mPostion + position.length;
			System.arraycopy(data, mPostion, measurePoint, 0,
					measurePoint.length);
			mPostion = mPostion + measurePoint.length;
			System.arraycopy(data, mPostion, speedAndPort, 0,
					speedAndPort.length);
			mPostion = mPostion + speedAndPort.length;
			System.arraycopy(data, mPostion, procole, 0, procole.length);
			mPostion = mPostion + procole.length;
			System.arraycopy(data, mPostion, address, 0, address.length);
			mPostion = mPostion + address.length;
			System.arraycopy(data, mPostion, password, 0, password.length);
			mPostion = mPostion + password.length;
			System.arraycopy(data, mPostion, flnum, 0, flnum.length);
			mPostion = mPostion + flnum.length;
			System.arraycopy(data, mPostion, zhenshuAndXiaoshuNum, 0,
					zhenshuAndXiaoshuNum.length);
			mPostion = mPostion + zhenshuAndXiaoshuNum.length;
			System.arraycopy(data, mPostion, caijiqiAddress, 0,
					caijiqiAddress.length);
			mPostion = mPostion + caijiqiAddress.length;
			System.arraycopy(data, mPostion, bigAndSmallNum, 0,
					bigAndSmallNum.length);
			mPostion = mPostion + bigAndSmallNum.length;
			mPostion = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	public byte[] classToByte() {
		byte[] data = new byte[27];
		mPostion = 0;
		try {
			System.arraycopy(position, 0, data, mPostion, position.length);
			mPostion = mPostion + position.length;
			System.arraycopy(measurePoint, 0, data, mPostion,
					measurePoint.length);
			mPostion = mPostion + measurePoint.length;
			System.arraycopy(speedAndPort, 0, data, mPostion,
					speedAndPort.length);
			mPostion = mPostion + speedAndPort.length;
			System.arraycopy(procole, 0, data, mPostion, procole.length);
			mPostion = mPostion + procole.length;
			System.arraycopy(address, 0, data, mPostion, address.length);
			mPostion = mPostion + address.length;
			System.arraycopy(password, 0, data, mPostion, password.length);
			mPostion = mPostion + password.length;
			System.arraycopy(flnum, 0, data, mPostion, flnum.length);
			mPostion = mPostion + flnum.length;
			System.arraycopy(zhenshuAndXiaoshuNum, 0, data, mPostion,
					zhenshuAndXiaoshuNum.length);
			mPostion = mPostion + zhenshuAndXiaoshuNum.length;
			System.arraycopy(caijiqiAddress, 0, data, mPostion,
					caijiqiAddress.length);
			mPostion = mPostion + caijiqiAddress.length;
			System.arraycopy(bigAndSmallNum, 0, data, mPostion,
					bigAndSmallNum.length);
			mPostion = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public byte[] getPosition() {
		return position;
	}

	public void setPosition(byte[] position) {
		this.position = position;
	}

	public byte[] getMeasurePoint() {
		return measurePoint;
	}

	public void setMeasurePoint(byte[] measurePoint) {
		this.measurePoint = measurePoint;
	}

	public byte[] getSpeedAndPort() {
		return speedAndPort;
	}

	public void setSpeedAndPort(byte[] speedAndPort) {
		this.speedAndPort = speedAndPort;
	}

	public byte[] getProcole() {
		return procole;
	}

	public void setProcole(byte[] procole) {
		this.procole = procole;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public byte[] getFlnum() {
		return flnum;
	}

	public void setFlnum(byte[] flnum) {
		this.flnum = flnum;
	}

	public byte[] getZhenshuAndXiaoshuNum() {
		return zhenshuAndXiaoshuNum;
	}

	public void setZhenshuAndXiaoshuNum(byte[] zhenshuAndXiaoshuNum) {
		this.zhenshuAndXiaoshuNum = zhenshuAndXiaoshuNum;
	}

	public byte[] getCaijiqiAddress() {
		return caijiqiAddress;
	}

	public void setCaijiqiAddress(byte[] caijiqiAddress) {
		this.caijiqiAddress = caijiqiAddress;
	}

	public byte[] getBigAndSmallNum() {
		return bigAndSmallNum;
	}

	public void setBigAndSmallNum(byte[] bigAndSmallNum) {
		this.bigAndSmallNum = bigAndSmallNum;
	}

	// //////////String

	public String getPositionString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(position);
	}

	public void setPositionString(String position) {
		this.position = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(position);
	}

	public String getMeasurePointString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(measurePoint);
	}

	public void setMeasurePointString(String measurePoint) {
		this.measurePoint = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(measurePoint);
	}

	public String getPortString() {
		return "" + (int) (speedAndPort[0] & 0x1f);
	}

	public String getSpeedString() {
		return getSpeed((int) ((speedAndPort[0] & 0xe0) >> 5));
	}

	public void setSpeedAndPortString(String speedAndPort) {
		this.speedAndPort = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(speedAndPort);
	}

	public String getProcoleString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(procole);
	}

	public void setProcoleString(String procole) {
		this.procole = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(procole);
	}

	public String getAddressString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(address);
	}

	public void setAddressString(String address) {
		this.address = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(address);
	}

	public String getPasswordString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(password);
	}

	public void setPasswordString(String password) {
		this.password = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(password);
	}

	public String getFlnumString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(flnum);
	}

	public void setFlnumString(String flnum) {
		this.flnum = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(flnum);
	}

	public String getZhenshuAndXiaoshuNumString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(zhenshuAndXiaoshuNum);
	}

	public void setZhenshuAndXiaoshuNumString(String zhenshuAndXiaoshuNum) {
		this.zhenshuAndXiaoshuNum = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils
				.stringToByteArray(zhenshuAndXiaoshuNum);
	}

	public String getCaijiqiAddressString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(caijiqiAddress);
	}

	public void setCaijiqiAddressString(String caijiqiAddress) {
		this.caijiqiAddress = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(caijiqiAddress);
	}

	public String getBigAndSmallNumString() {
		return com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.binArrayToString(bigAndSmallNum);
	}

	public void setBigAndSmallNumString(String bigAndSmallNum) {
		this.bigAndSmallNum = com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils.stringToByteArray(bigAndSmallNum);
	}

	public static String getSpeed(int data) {
		switch (data) {
		case 1:
			return "600";
		case 2:
			return "1200";
		case 3:
			return "2400";
		case 4:
			return "4800";
		case 5:
			return "7200";
		case 6:
			return "9600";
		case 7:
			return "19200";
		}
		return "未知";
	}
}
