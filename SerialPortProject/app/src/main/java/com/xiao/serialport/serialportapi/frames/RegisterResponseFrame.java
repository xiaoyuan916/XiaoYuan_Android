package com.xiao.serialport.serialportapi.frames;

import com.xiao.serialport.serialportapi.Utils;
import com.xiao.serialport.serialportapi.bean.BaseFrame;

/**
 * 屏注册应答帧
 * <p>Title: RegisterResponseFrame</p>
 * <p>Description: </p>
 * @author	ShenYang
 * @date	2015-8-14
 */
@Deprecated
public class RegisterResponseFrame extends BaseFrame {

	private static final long serialVersionUID = 3721815612393029009L;

	/**
	 * 包标识,与注册帧信息中相同
	 */
	private int flag;
	
	public static RegisterResponseFrame processMessgae(byte[] message){
		RegisterResponseFrame frame = new RegisterResponseFrame();
		frame = BaseFrame.processBaseInfo(frame, message);
		
		frame.flag = Integer.parseInt(Utils.byteToHex(message[7]), 16);
		
		return frame;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
