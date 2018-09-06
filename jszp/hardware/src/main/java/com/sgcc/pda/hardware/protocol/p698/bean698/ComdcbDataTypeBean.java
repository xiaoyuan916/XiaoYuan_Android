package com.sgcc.pda.hardware.protocol.p698.bean698;

//定义串口控制块数据类型COMDCB
public class ComdcbDataTypeBean {
	
		/*波特率    ENUMERATED
		{
		300bps（0），   600bps（1），     1200bps（2），
		2400bps（3），  4800bps（4），    7200bps（5），
		9600bps（6），  19200bps（7），   38400bps（8），
		57600bps（9）， 115200bps（10）， 自适应（255）
		}*/
	  private String baudRate;
	  
	  /*校验位  ENUMERATED {无校验（0），奇校验（1），偶校验（2）}*/
	  private String checkBite;
	  
	  /*数据位  ENUMERATED {5（5），6（6），7（7），8（8）}，*/
	  private String dataBits;
	  
	  /* 停止位  ENUMERATED {1（1），2（2）}，*/
	  private String stopBit;
	  
	  /* 流控    ENUMERATED {无(0)，硬件(1)，软件(2)}*/
      private String flowControl;

	public String getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}

	public String getCheckBite() {
		return checkBite;
	}

	public void setCheckBite(String checkBite) {
		this.checkBite = checkBite;
	}

	public String getDataBits() {
		return dataBits;
	}

	public void setDataBits(String dataBits) {
		this.dataBits = dataBits;
	}

	public String getStopBit() {
		return stopBit;
	}

	public void setStopBit(String stopBit) {
		this.stopBit = stopBit;
	}

	public String getFlowControl() {
		return flowControl;
	}

	public void setFlowControl(String flowControl) {
		this.flowControl = flowControl;
	}
      
      
}
