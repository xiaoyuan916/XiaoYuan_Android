package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.ArrayList;
import java.util.List;

//698数据项对象
public class DataItem698 {

	//对象标识
	private String objIdent;
	//接口类
	private String interClass;
	//对象名称
	private String objName;
	//对象成员
	private String objMember;
	//成员编号【属性编号或方法编号】
	private String memberCode;
	//成员类型   0  属性    1  方法
	private String memberType;
	//数据类型
	private String dataType;
	//换算
	private String conversion;
	//单位
	private String unit;
	//读写标识
	private String readWriteIndet;
	//是否需要参数
	private String isneedParam;
	//显示顺序
	private String order;
	//是否生效
	private String whetherEffct;
	
	//对象特征
	private String objFeatures;
	//元素索引  或  操作模式   对应oad  该属性为元素索引  对于 omd  该属性为操作模式
	private String elementIndex;
	//输入数据参数 或执行操作时，方法参数
	private String inputParam;
	
	//上行报文返回的数据结果
	private String upValue;
	
	//执行698协议操作方法时，  返回的数据
	private String optMethodBack;
	
	//执行操作标识   ----------执行设置后读取 或操作后读取  时用到                                      ---无数据类型
	//执行操作标识   ----------执行设置后读取 或操作后读取 、执行操作方法不需要参数（只有对象方法描述符【OMD】） 时用到
	private String setOrOpt;
	
	//读取延时  -------执行设置后读取、操作后读取、 代理时 用到
	private String delayTime;
	
	//DataItem698类     //当执行设置后读取属性值  或 执行操作方法后读取对象属性时应用 (  设置、操作、代理有应用 )
	private  DataItem698   dataItem698;

	//tsa  代理请求的服务器地址   --------用于解析报文
	private String tsa;
    //组装或解析 复制 对象
    private List<Object> obj= new ArrayList<Object>();
    // 解析报文时应用  对象属性描述符
    private String oad;
    // 读取记录型对象时，表示该对象是记录型对象的关联对象  解析报文时应用  默认非记录型对象关联对象
    private   boolean   isrelation = false;
    // 解析报文  记录型对象关联对象 是否是第一个关联对象
    private   boolean    isfrist = false;
    /**
     * 无参构造函数
     */
    public DataItem698() {

	}
    
    /**
     * 带参构造函数
     * @param objIdent
     * @param memberCode
     */
	public DataItem698(String objIdent, String memberCode) {
		this.objIdent = objIdent;
		this.memberCode = memberCode;
	}
	
	public DataItem698(String objIdent, String memberCode, String dataType, String inputParam) {
		this.objIdent = objIdent;
		this.memberCode = memberCode;
		this.dataType = dataType;
		this.inputParam = inputParam;
	}
    
	public String getObjIdent() {
		return objIdent;
	}

	public void setObjIdent(String objIdent) {
		this.objIdent = objIdent;
	}

	public String getInterClass() {
		return interClass;
	}

	public void setInterClass(String interClass) {
		this.interClass = interClass;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}


	public String getObjMember() {
		return objMember;
	}

	public void setObjMember(String objMember) {
		this.objMember = objMember;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReadWriteIndet() {
		return readWriteIndet;
	}

	public void setReadWriteIndet(String readWriteIndet) {
		this.readWriteIndet = readWriteIndet;
	}

	public String getIsneedParam() {
		return isneedParam;
	}

	public void setIsneedParam(String isneedParam) {
		this.isneedParam = isneedParam;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getWhetherEffct() {
		return whetherEffct;
	}

	public void setWhetherEffct(String whetherEffct) {
		this.whetherEffct = whetherEffct;
	}

	public String getInputParam() {
		return inputParam;
	}

	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}

	public String getUpValue() {
		return upValue;
	}

	public void setUpValue(String upValue) {
		this.upValue = upValue;
	}

	public String getObjFeatures() {
		return objFeatures;
	}

	public void setObjFeatures(String objFeatures) {
		this.objFeatures = objFeatures;
	}

	public String getElementIndex() {
		return elementIndex;
	}

	public void setElementIndex(String elementIndex) {
		this.elementIndex = elementIndex;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getOptMethodBack() {
		return optMethodBack;
	}

	public void setOptMethodBack(String optMethodBack) {
		this.optMethodBack = optMethodBack;
	}

	public String getSetOrOpt() {
		return setOrOpt;
	}

	public void setSetOrOpt(String setOrOpt) {
		this.setOrOpt = setOrOpt;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public DataItem698 getDataItem698() {
		return dataItem698;
	}

	public void setDataItem698(DataItem698 dataItem698) {
		this.dataItem698 = dataItem698;
	}

	public String getTsa() {
		return tsa;
	}

	public void setTsa(String tsa) {
		this.tsa = tsa;
	}

	public List<Object> getObj() {
		return obj;
	}

	public void setObj(List<Object> obj) {
		this.obj = obj;
	}

	public String getOad() {
		return oad;
	}

	public void setOad(String oad) {
		this.oad = oad;
	}

	public boolean isIsrelation() {
		return isrelation;
	}

	public void setIsrelation(boolean isrelation) {
		this.isrelation = isrelation;
	}

	public boolean isIsfrist() {
		return isfrist;
	}

	public void setIsfrist(boolean isfrist) {
		this.isfrist = isfrist;
	}

	
	
}
