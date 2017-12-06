package com.qm.omp.business.pojo.drug;

import java.io.Serializable;
/**
 * 药品
 * @author rh 
 *
 */
public class DrugOnly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//药品名称
	private String fName;
	//药品规格 F_SPECIFICATION
	private String fSpecification;
	//产地
	private String fAddress;
	//状态：0，正常；1，删除
	private String fState;
	//创建时间
	private String fTime;
	//药品图片	F_IMG
	private String fImg;
	//药品介绍 	F_INTRO
	private String fIntro;
	//供货价	
	private Double fSupplyPrice;
	//零售价	
	private Double fRetailPrice;
	//药品介绍分类id
	private Integer fDrugIntroId;
	private String fDrugIntroName;
	//药品经营分类id
	private Integer fDrugPrinterId;
	private String fDrugPrinterName;
	//最近效期的库存，库存为零除外
	private Integer fNumber;
	//效期 F_EXPIRY_DATE
	private String fExpiryDate;
	// 小区提成
	private Double fXqTc;
	// 大区提成
	private Double fDqTc;
	// 部门
	private Integer fDepartmentId;
	// 部门
	private String fDepartmentName;
	//
	private String parentId;
	//
	private Boolean checked;
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
	public String getfSpecification() {
		return fSpecification;
	}
	public void setfSpecification(String fSpecification) {
		this.fSpecification = fSpecification;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getfImg() {
		return fImg;
	}
	public void setfImg(String fImg) {
		this.fImg = fImg;
	}
	public String getfIntro() {
		return fIntro;
	}
	public void setfIntro(String fIntro) {
		this.fIntro = fIntro;
	}
	public Double getfSupplyPrice() {
		return fSupplyPrice;
	}
	public void setfSupplyPrice(Double fSupplyPrice) {
		this.fSupplyPrice = fSupplyPrice;
	}
	public Double getfRetailPrice() {
		return fRetailPrice;
	}
	public void setfRetailPrice(Double fRetailPrice) {
		this.fRetailPrice = fRetailPrice;
	}
	public Integer getfDrugIntroId() {
		return fDrugIntroId;
	}
	public void setfDrugIntroId(Integer fDrugIntroId) {
		this.fDrugIntroId = fDrugIntroId;
	}
	public Integer getfDrugPrinterId() {
		return fDrugPrinterId;
	}
	public void setfDrugPrinterId(Integer fDrugPrinterId) {
		this.fDrugPrinterId = fDrugPrinterId;
	}
	public String getfDrugIntroName() {
		return fDrugIntroName;
	}
	public void setfDrugIntroName(String fDrugIntroName) {
		this.fDrugIntroName = fDrugIntroName;
	}
	public String getfDrugPrinterName() {
		return fDrugPrinterName;
	}
	public void setfDrugPrinterName(String fDrugPrinterName) {
		this.fDrugPrinterName = fDrugPrinterName;
	}
	public Integer getfNumber() {
		return fNumber;
	}
	public void setfNumber(Integer fNumber) {
		this.fNumber = fNumber;
	}
	public String getfExpiryDate() {
		return fExpiryDate;
	}
	public void setfExpiryDate(String fExpiryDate) {
		this.fExpiryDate = fExpiryDate;
	}
	public Double getfXqTc() {
		return fXqTc;
	}
	public void setfXqTc(Double fXqTc) {
		this.fXqTc = fXqTc;
	}
	public Double getfDqTc() {
		return fDqTc;
	}
	public void setfDqTc(Double fDqTc) {
		this.fDqTc = fDqTc;
	}
	public Integer getfDepartmentId() {
		return fDepartmentId;
	}
	public void setfDepartmentId(Integer fDepartmentId) {
		this.fDepartmentId = fDepartmentId;
	}
	public String getfDepartmentName() {
		return fDepartmentName;
	}
	public void setfDepartmentName(String fDepartmentName) {
		this.fDepartmentName = fDepartmentName;
	}
	
	
}
