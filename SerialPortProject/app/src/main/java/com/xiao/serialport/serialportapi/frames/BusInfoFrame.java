package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 
 * @version
 * @author NY
 * @Description 车辆信息包。<br>
 *              主要用于获取 车辆裱花、机器编号（车载机编号）。
 * @CreatTime 2016年3月15日14:21:01
 */
public class BusInfoFrame extends BaseFrame {

	private static final long serialVersionUID = 8809897922521052226L;

	/**
	 * 包标识。<br>
	 * 同一包重发是，包标识不变。<br>
	 * 然并卵。
	 */
	private int frameFlag;

	/**
	 * 车辆编号。<br>
	 * 车辆编号、车载机号 都是十进制转成十六进制后传过来的。 可以用long类型来保存这两个编号。
	 * 用int也可以，但是int的话，这两个编号如果要占4个字节用int就不行了。 为了扩展性更好、保险点还是用long类型好些。
	 */
	private long busNo;

	/**
	 * 机器编号（车载机编号）。
	 */
	private long machineNo;

	public static BusInfoFrame processMessgae(byte[] message) {
		int offset = 7;
		BusInfoFrame frame = new BusInfoFrame();
		frame = BaseFrame.processBaseInfo(frame, message);

		frame.frameFlag = byteToInt(message[offset]);

		offset++;// 8
		// 车辆编号字段占3个字节。
		byte[] _info = new byte[3];
		System.arraycopy(message, offset, _info, 0, 3);
		frame.busNo = Utils.bytes2Long(_info);

		// 机器编号字段占3个字节。
		offset += 3;// 11
		_info = new byte[3];
		System.arraycopy(message, offset, _info, 0, 3);
		frame.machineNo = Utils.bytes2Long(_info);

		return frame;
	}

	/**
	 * 获取包标识。
	 * 
	 * @return
	 */
	public int getFrameFlag() {
		return frameFlag;
	}

	public void setFrameFlag(int frameFlag) {
		this.frameFlag = frameFlag;
	}

	public long getBusNo() {
		return busNo;
	}

	public void setBusNo(long busNo) {
		this.busNo = busNo;
	}

	public long getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(long machineNo) {
		this.machineNo = machineNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (busNo ^ (busNo >>> 32));
		result = prime * result + frameFlag;
		result = prime * result + (int) (machineNo ^ (machineNo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusInfoFrame other = (BusInfoFrame) obj;
		if (busNo != other.busNo)
			return false;
		if (frameFlag != other.frameFlag)
			return false;
		if (machineNo != other.machineNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusInfoFrame [frameFlag=" + frameFlag + ", busNo=" + busNo
				+ ", machineNo=" + machineNo + "]";
	}

}
