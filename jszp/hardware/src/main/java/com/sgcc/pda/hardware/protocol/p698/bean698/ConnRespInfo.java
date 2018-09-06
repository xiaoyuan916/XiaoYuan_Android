package com.sgcc.pda.hardware.protocol.p698.bean698;
//应用连接请求的认证响应信息
public class ConnRespInfo {
	
	//认证结果
	private String connResult;
	
	//服务器随机数
	private String rn;
	
	//服务器签名信息
	private String signInfo;

	public String getConnResult() {
		return connResult;
	}

	public void setConnResult(String connResult) {
		this.connResult = connResult;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	
	
	

}
