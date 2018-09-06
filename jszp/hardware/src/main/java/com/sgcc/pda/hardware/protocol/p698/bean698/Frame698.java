package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.List;

//  698报文对象
public class Frame698 {
    //对象分类        ---------05:读取     06：设置     07：操作     08：上报   09： 代理
	private  byte  objclassifi;

	public byte getControlCode() {
		return controlCode;
	}

	public void setControlCode(byte controlCode) {
		this.controlCode = controlCode;
	}

	// 控制码   在Make698Frame中  assFrameHead（）  固定值为  0x43
	// bit7	  			bit6	    bit5        bit4      bit3    bit2 bit1 bit0
	//传输方向位DIR	启动标志位PRM	分帧标志位	保留	 扰码标志位SC	功能码
	private  byte  controlCode = 0b01000011;  // 43   01(传输方向,启动标志位,此处01 为客户机发出的请求) 0(不分帧) 0(保留) 0 (绕码 不加33H ) 011 (功能码3 数据交互)
	//地址
	private String meterComId;
	//地址类型
	private String meterComIdType;
	//逻辑地址
	private String logiAdd;
	//长度
	private int frameLen;
	//对象标识
	private String objIdent;
	//操作对象个数    一个或多个    ,  如果是响应安全传输报文，该字段表示应用数据单元  0 明文   1 密文   2 异常错误
	private  byte   optCount;   
	//链路用户数据
	private byte[] linkUserData;
	//下行Bean继承与IPharesItem698
	private   IPharesItem698   downvalue;
	//报文内容  ----组装的完整下行报文
	private byte[] frameCont;
	//操作的698数据项对象
	private List<DataItem698> dataItem698;
	//输入参数
	private String inputparam;
	
	//执行操作标识   ----------执行设置后读取 或操作后读取 或执行什么代理操作 或读取记录型属性  时用到
	private String setOrOpt;
	//整个代理请求超时时间
    private String allTimeOut;   //执行代理操作时应用
    
    //代理服务器地址  TSA          ------用于组装报文
    private List<String> tsa;
    
    //执行代理操作时，所要执行操作的对象
    private List<List<DataItem698>> proxy698;
    
    
    //某一个代理服务器的超时时间
    private List<String> oneTimeOut;   //执行代理操作时应用
    
    //读取记录型对象属性----记录选择描述符
    private List<RecordRsdBean> rsd;
    //记录列选择描述符
    private List<RecordRcsdBean> rcsd;
    
    //代理透明转发命令请求时 应用  ---------------
    //串口控制块
    private   ComdcbDataTypeBean   cdtb;
    //接收等待报文超时时间（秒）
    private String waitMessOutOfTime;
    //接收等待字节超时时间(毫秒）
    private String waitByteOutOfTiem;
    //透明转发命令
    private String transForwardCommd;
    //---------------------------------------
    
    //安全传输时（明文或密文） 应用 ----------
    private Boolean isSecurity = false;
    //安全传输对象
    private   Security   securityObj;
    
    //预连接对象
    private   LinkBean   linkBean;
    
    //建立应用连接请求对象
    private    AppConnReq    appConnReq;
    
    /**
     * 无参构造方法
     */
    public Frame698() {

	}
    
    
    /**
     * 带参的构造方法
     * 
     * @param objclassifi 		 对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId		 电能通讯地址
     */
    public Frame698(byte objclassifi, String meterComId) {
    	this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = "00";
		this.logiAdd = "00";
	}
    
    /**
     * 带参的构造方法
     * 
     * @param objclassifi 		 对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId		 电能通讯地址
     * @param dataItemList  	 操作对象类
     */
    public Frame698(byte objclassifi, String meterComId, List<DataItem698> dataItemList) {
    	this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = "00";
		this.logiAdd = "00";
		this.dataItem698 = dataItemList;
	}

    /**
     * 带参的构造方法
     * 
     * @param objclassifi    对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId	 电能通讯地址
     */
	public Frame698(byte objclassifi, String meterComId, Security security) {
		this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = "00";
		this.logiAdd = "00 ";
		this.securityObj = security;
	}
    
    /**
     * 带参的构造方法
     * 
     * @param objclassifi    对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId	 电能通讯地址
     * @param meterComIdType 地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
     * @param logiAdd		 逻辑地址
     */
	public Frame698(byte objclassifi, String meterComId, String meterComIdType, String logiAdd, Security security) {
		this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = meterComIdType;
		this.logiAdd = logiAdd;
		this.securityObj = security;
	}
	
    /**
     * 带参的构造方法
     * 
     * @param objclassifi    对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId	 电能通讯地址
     * @param meterComIdType 地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
     * @param logiAdd		 逻辑地址
     * @param dataItemList   操作对象类
     */
	public Frame698(byte objclassifi, String meterComId, String meterComIdType,
                    String logiAdd, List<DataItem698> dataItemList) {
		this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = meterComIdType;
		this.logiAdd = logiAdd;
		this.dataItem698 = dataItemList;
	}
	
	
	
	/**
     * 带参的构造方法
     * 
     * @param objclassifi    对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId	 电能通讯地址
     */
	public Frame698(byte objclassifi, String meterComId, AppConnReq appConnReq) {
		this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = "00";
		this.logiAdd = "00";
		this.appConnReq = appConnReq;
	}
	
	/**
     * 带参的构造方法
     * 
     * @param objclassifi    对象分类    05:读取     06：设置     07：操作     08：上报   09： 代理
     * @param meterComId	 电能通讯地址
     * @param meterComIdType 地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
     * @param logiAdd		 逻辑地址
     */
	public Frame698(byte objclassifi, String meterComId, String meterComIdType,
                    String logiAdd, AppConnReq appConnReq) {
		this.objclassifi = objclassifi;
		this.meterComId = meterComId;
		this.meterComIdType = meterComIdType;
		this.logiAdd = logiAdd;
		this.appConnReq = appConnReq;
	}
    
	public String getMeterComId() {
		return meterComId;
	}

	public void setMeterComId(String meterComId) {
		this.meterComId = meterComId;
	}

	
	public String getMeterComIdType() {
		return meterComIdType;
	}

	public void setMeterComIdType(String meterComIdType) {
		this.meterComIdType = meterComIdType;
	}

	public String getLogiAdd() {
		return logiAdd;
	}

	public void setLogiAdd(String logiAdd) {
		this.logiAdd = logiAdd;
	}

	public int getFrameLen() {
		return frameLen;
	}

	public void setFrameLen(int frameLen) {
		this.frameLen = frameLen;
	}

	public String getObjIdent() {
		return objIdent;
	}

	public void setObjIdent(String objIdent) {
		this.objIdent = objIdent;
	}

	public byte[] getLinkUserData() {
		return linkUserData;
	}

	public void setLinkUserData(byte[] linkUserData) {
		this.linkUserData = linkUserData;
	}

	public byte[] getFrameCont() {
		return frameCont;
	}

	public void setFrameCont(byte[] frameCont) {
		this.frameCont = frameCont;
	}

	public byte getObjclassifi() {
		return objclassifi;
	}

	public void setObjclassifi(byte objclassifi) {
		this.objclassifi = objclassifi;
	}

	public List<DataItem698> getDataItem698() {
		return dataItem698;
	}

	public void setDataItem698(List<DataItem698> dataItem698) {
		this.dataItem698 = dataItem698;
	}

	public String getInputparam() {
		return inputparam;
	}

	public void setInputparam(String inputparam) {
		this.inputparam = inputparam;
	}

	public IPharesItem698 getDownvalue() {
		return downvalue;
	}

	public void setDownvalue(IPharesItem698 downvalue) {
		this.downvalue = downvalue;
	}

	public byte getOptCount() {
		return optCount;
	}

	public void setOptCount(byte optCount) {
		this.optCount = optCount;
	}

	public String getSetOrOpt() {
		return setOrOpt;
	}

	public void setSetOrOpt(String setOrOpt) {
		this.setOrOpt = setOrOpt;
	}

	public String getAllTimeOut() {
		return allTimeOut;
	}

	public void setAllTimeOut(String allTimeOut) {
		this.allTimeOut = allTimeOut;
	}

	public List<String> getTsa() {
		return tsa;
	}

	public void setTsa(List<String> tsa) {
		this.tsa = tsa;
	}

	public List<List<DataItem698>> getProxy698() {
		return proxy698;
	}

	public void setProxy698(List<List<DataItem698>> proxy698) {
		this.proxy698 = proxy698;
	}

	public List<String> getOneTimeOut() {
		return oneTimeOut;
	}

	public void setOneTimeOut(List<String> oneTimeOut) {
		this.oneTimeOut = oneTimeOut;
	}

	public List<RecordRsdBean> getRsd() {
		return rsd;
	}

	public void setRsd(List<RecordRsdBean> rsd) {
		this.rsd = rsd;
	}

	public List<RecordRcsdBean> getRcsd() {
		return rcsd;
	}

	public void setRcsd(List<RecordRcsdBean> rcsd) {
		this.rcsd = rcsd;
	}
	
	public ComdcbDataTypeBean getCdtb() {
		return cdtb;
	}

	public void setCdtb(ComdcbDataTypeBean cdtb) {
		this.cdtb = cdtb;
	}

	public String getWaitMessOutOfTime() {
		return waitMessOutOfTime;
	}

	public void setWaitMessOutOfTime(String waitMessOutOfTime) {
		this.waitMessOutOfTime = waitMessOutOfTime;
	}

	public String getWaitByteOutOfTiem() {
		return waitByteOutOfTiem;
	}

	public void setWaitByteOutOfTiem(String waitByteOutOfTiem) {
		this.waitByteOutOfTiem = waitByteOutOfTiem;
	}

	public String getTransForwardCommd() {
		return transForwardCommd;
	}

	public void setTransForwardCommd(String transForwardCommd) {
		this.transForwardCommd = transForwardCommd;
	}

	public Boolean getIsSecurity() {
		return isSecurity;
	}

	public void setIsSecurity(Boolean isSecurity) {
		this.isSecurity = isSecurity;
	}

	public Security getSecurityObj() {
		return securityObj;
	}

	public void setSecurityObj(Security securityObj) {
		this.securityObj = securityObj;
	}

	public LinkBean getLinkBean() {
		return linkBean;
	}

	public void setLinkBean(LinkBean linkBean) {
		this.linkBean = linkBean;
	}

	public AppConnReq getAppConnReq() {
		return appConnReq;
	}

	public void setAppConnReq(AppConnReq appConnReq) {
		this.appConnReq = appConnReq;
	}

    
}
