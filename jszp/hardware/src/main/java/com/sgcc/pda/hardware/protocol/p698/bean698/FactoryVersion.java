package com.sgcc.pda.hardware.protocol.p698.bean698;
//服务器厂商版本信息 
public class FactoryVersion {
	
	 //厂商代码       visible-string(SIZE (4))，
	 private String factCode;
	 //软件版本号     visible-string(SIZE (4))，
	 private String softVersion;
	 //软件版本日期   visible-string(SIZE (6))，
	 private String versionDate;
	 //硬件版本号     visible-string(SIZE (4))，
	 private String hardVersion;
	 //硬件版本日期   visible-string(SIZE (6))，
	 private String hardDate;
	 //厂家扩展信息   visible-string(SIZE (8))
	 private String factExtendInfo;
	 
	public String getFactCode() {
		return factCode;
	}
	public void setFactCode(String factCode) {
		this.factCode = factCode;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}
	public String getHardVersion() {
		return hardVersion;
	}
	public void setHardVersion(String hardVersion) {
		this.hardVersion = hardVersion;
	}
	public String getHardDate() {
		return hardDate;
	}
	public void setHardDate(String hardDate) {
		this.hardDate = hardDate;
	}
	public String getFactExtendInfo() {
		return factExtendInfo;
	}
	public void setFactExtendInfo(String factExtendInfo) {
		this.factExtendInfo = factExtendInfo;
	}
	 
}
