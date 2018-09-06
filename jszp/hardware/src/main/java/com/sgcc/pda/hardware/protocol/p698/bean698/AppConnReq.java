package com.sgcc.pda.hardware.protocol.p698.bean698;

//建立应用连接请求数据类型
public class AppConnReq {

	//应用层协议版本号
	private String version;
	//协议一致性
	private String protocal;
	//功能一致性
	private String function;
	//发送整最大尺寸
	private String sendMaxSize;
	//接收最大尺寸
	private String reviceMaxSize;
	//接收最大窗口尺寸
	private String reviceMaxWinSize;
	//最大可处理APDU尺寸
	private String maxApduSize;
	//连接超时时间
	private String linkOverTime;
	//认证请求对象
	//0:公共连接  ---null     1:一般密码   2：对称加密[密文1，客户机签名]   3：数字签名[密文2， 客户机签名2]
	private String connMechInfo;
	//认证附件信息-----如果没有附加信息 ---- 使用该字段  表示应用连接请求认证响应信息
	private String isInfo;
	//应用连接请求认证响应信息对象
	private ConnRespInfo connRespInfo;
	//服务器厂商版本信息 
	private FactoryVersion factoryVarsion;

	/**
	 * 无参构造方法
	 */
	public AppConnReq() {

	}
	
	/**
	 * 带参构造方法
	 * @param version
	 * @param sendMaxSize
	 * @param reviceMaxSize
	 * @param reviceMaxWinSize
	 * @param maxApduSize
	 * @param linkOverTime 期望的应用连接超时时间,建议设置一天24小时（即86400秒）
	 * @param connMechInfo
	 */
	public AppConnReq(String version, String sendMaxSize, String reviceMaxSize,
                      String reviceMaxWinSize, String maxApduSize, String linkOverTime,
                      String connMechInfo) {
		this.version = version;
		this.sendMaxSize = sendMaxSize;
		this.reviceMaxSize = reviceMaxSize;
		this.reviceMaxWinSize = reviceMaxWinSize;
		this.maxApduSize = maxApduSize;
		this.linkOverTime = linkOverTime;
		this.connMechInfo = connMechInfo;

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSendMaxSize() {
		return sendMaxSize;
	}

	public void setSendMaxSize(String sendMaxSize) {
		this.sendMaxSize = sendMaxSize;
	}

	public String getReviceMaxSize() {
		return reviceMaxSize;
	}

	public void setReviceMaxSize(String reviceMaxSize) {
		this.reviceMaxSize = reviceMaxSize;
	}

	public String getReviceMaxWinSize() {
		return reviceMaxWinSize;
	}

	public void setReviceMaxWinSize(String reviceMaxWinSize) {
		this.reviceMaxWinSize = reviceMaxWinSize;
	}

	public String getMaxApduSize() {
		return maxApduSize;
	}

	public void setMaxApduSize(String maxApduSize) {
		this.maxApduSize = maxApduSize;
	}

	public String getLinkOverTime() {
		return linkOverTime;
	}

	public void setLinkOverTime(String linkOverTime) {
		this.linkOverTime = linkOverTime;
	}

	public String getConnMechInfo() {
		return connMechInfo;
	}

	public void setConnMechInfo(String connMechInfo) {
		this.connMechInfo = connMechInfo;
	}

	public ConnRespInfo getConnRespInfo() {
		return connRespInfo;
	}

	public void setConnRespInfo(ConnRespInfo connRespInfo) {
		this.connRespInfo = connRespInfo;
	}

	public FactoryVersion getFactoryVarsion() {
		return factoryVarsion;
	}

	public void setFactoryVarsion(FactoryVersion factoryVarsion) {
		this.factoryVarsion = factoryVarsion;
	}

	public String getIsInfo() {
		return isInfo;
	}

	public void setIsInfo(String isInfo) {
		this.isInfo = isInfo;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

}
