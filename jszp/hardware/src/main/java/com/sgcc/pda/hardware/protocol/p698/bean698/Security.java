package com.sgcc.pda.hardware.protocol.p698.bean698;

//安全请求类
public class Security {
	//应用数据单元方式 ------- 0： 明文、  1：密文
	private String type;
	
	//传输过程中的明文或密文数据
	private String transferData;
	//数据验证信息 -----选择方式  :  
	//数据验证码   [0]  SID_MAC；	
	//随机数  [1]  RN； 	
	//随机数+数据MAC  [2]  RN_MAC，
	//安全标识     [3]  SID
    private String secType;
	//  安全标识  SID  包括【标识、附加数据】
	private String secSid;  //标识[double-long-unsigned--4]
	
	private String AttrachData;//附加数据[octet-string]
	 //数据MAC
	private String mac; //[octet-string]
	//随机数
	private String rn; //[octet-string]

	private String taskData;
	
	/**
	 * 无参构造函数
	 */
	public Security(){}
	/**
	 * 无参构造函数
	 */
	public Security(String str){
		this.taskData=str;
	}
	/**
	 * 带参构造函数
	 * 
	 * @param type 		0： 明文、  1：密文
	 * @param secType   数据验证信息 -----选择方式 
	 * 					数据验证码	 [0]  SID_MAC；
	 * 					随机数        [1]  RN； 	
	 * 					随机数+数据MAC [2]  RN_MAC，
	 * 					安全标识     	 [3]  SID  包括【标识、附加数据】
	 * @param secSid	标识[double-long-unsigned--4]
	 * @param data		附加数据[octet-string]
	 * @param mac		数据MAC[octet-string]
	 */
	public Security(String type, String secType, String transferData, String secSid, String AttrachData, String mac, String rn){
		this.type = type;
		this.secType = secType;
		this.secSid = secSid;
		this.transferData = transferData;
		this.AttrachData = AttrachData;
		this.mac = mac;
		this.rn = rn;
	}
	
	/**
	 * 带参构造函数
	 * 
	 * @param type 		0： 明文、  1：密文
	 * @param secType   数据验证信息 -----选择方式 
	 * 					数据验证码	 [0]  SID_MAC；
	 * 					随机数        [1]  RN； 	
	 * 					随机数+数据MAC [2]  RN_MAC，
	 * 					安全标识     	 [3]  SID  包括【标识、附加数据】
	 * @param secSid	标识[double-long-unsigned--4]
	 * @param data		附加数据[octet-string]
	 * @param mac		数据MAC[octet-string]
	 * @param rn		随机数[octet-string]
	 */
	public Security(String type, String secType, String secSid, String AttrachData, String mac, String rn){
		this.type = type;
		this.secType = secType;
		this.secSid = secSid;
		this.AttrachData = AttrachData;
		this.mac = mac;
		this.rn = rn;
	}
	public String getSecSid() {
		return secSid;
	}

	public void setSecSid(String secSid) {
		this.secSid = secSid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransferData() {
		return transferData;
	}

	public void setTransferData(String transferData) {
		this.transferData = transferData;
	}

	public String getAttrachData() {
		return AttrachData;
	}

	public void setAttrachData(String attrachData) {
		AttrachData = attrachData;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
}
