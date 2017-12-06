package com.qm.omp.business.pojo.drug;

import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//经营者
	private String fName;
	//购货地址
	private String fAddress;
	//状态：0，正常；1，删除
	private String fState;
	//创建时间
	private String fTime;
	//购货单位
	private String fUnit;
	//联系电话
	private String fPhone;
	//委托书开始时间
	private String fBeginTime;
	//委托书结束时间
	private String fEndTime;
	//备注
	private String fRemark;
	//客户所属区域id
	private Integer fCityId;
	//客户所属区域名称
	private String fCityName;
	//有效期状态 0:过期，1：正在进行中，2：即将开始
	private String fExpiryDate;
	//生日
	private String fBirthday;
	//经营分类ids
	private String fDrugPrinterIds;
	//公司ids
	private String fCompanyIds;
	
	
	public String getfCityName() {
		return fCityName;
	}
	public void setfCityName(String fCityName) {
		this.fCityName = fCityName;
	}
	public Integer getfCityId() {
		return fCityId;
	}
	public void setfCityId(Integer fCityId) {
		this.fCityId = fCityId;
	}
	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getfAddress() {
		return fAddress;
	}
	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}
	public String getfState() {
		return fState;
	}
	public void setfState(String fState) {
		this.fState = fState;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
	}
	public String getfUnit() {
		return fUnit;
	}
	public void setfUnit(String fUnit) {
		this.fUnit = fUnit;
	}
	public String getfPhone() {
		return fPhone;
	}
	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}
	public String getfBeginTime() {
		return fBeginTime;
	}
	public void setfBeginTime(String fBeginTime) {
		this.fBeginTime = fBeginTime;
	}
	public String getfEndTime() {
		return fEndTime;
	}
	public void setfEndTime(String fEndTime) {
		this.fEndTime = fEndTime;
	}
	public String getfRemark() {
		return fRemark;
	}
	public void setfRemark(String fRemark) {
		this.fRemark = fRemark;
	}
	public String getfExpiryDate() {
		return fExpiryDate;
	}
	/**
	 * 0:过期，1：正在进行中，2：即将开始
	 * @param fExpiryDate
	 */
	public void setfExpiryDate(String fExpiryDate) {
		this.fExpiryDate = fExpiryDate;
	}
	public String getfBirthday() {
		return fBirthday;
	}
	public void setfBirthday(String fBirthday) {
		this.fBirthday = fBirthday;
	}
	public String getfDrugPrinterIds() {
		return fDrugPrinterIds;
	}
	public void setfDrugPrinterIds(String fDrugPrinterIds) {
		this.fDrugPrinterIds = fDrugPrinterIds;
	}
	public String getfCompanyIds() {
		return fCompanyIds;
	}
	public void setfCompanyIds(String fCompanyIds) {
		this.fCompanyIds = fCompanyIds;
	}
	
}
