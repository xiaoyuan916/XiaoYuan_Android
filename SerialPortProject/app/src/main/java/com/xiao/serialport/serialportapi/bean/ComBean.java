package com.xiao.serialport.serialportapi.bean;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * @author benjaminwan 串口数据
 */
public class ComBean {
	public byte[] bRec = null;
	public String sRecTime = "";
	public String sComPort = "";
	public int bSize = 0;

	@SuppressLint("SimpleDateFormat")
	public ComBean(String sPort, byte[] buffer, int size) {
		sComPort = sPort;
		bSize = size;
		bRec = new byte[size];
		for (int i = 0; i < size; i++) {
			bRec[i] = buffer[i];
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
		sRecTime = sDateFormat.format(new java.util.Date());
	}
}