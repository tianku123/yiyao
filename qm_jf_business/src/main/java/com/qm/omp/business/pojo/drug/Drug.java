package com.qm.omp.business.pojo.drug;

import java.io.Serializable;
/**
 * 药品
 * @author rh 
 *
 */
public class Drug implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//库存自动流转到下个月，记录上个月库存id，知道从哪流转的
	private Integer fBalanceId;
	//药品id
	private Integer fDrugOnlyId;
	//药品名称
	private String fName;
	//药品规格 F_SPECIFICATION
	private String fSpecification;
	//产地
	private String fAddress;
	/*
	 * 库存状态 
	 * 0：正常入库状态或当月入库当月销售完的状态；
	 * 1：添加错误删除状态；
	 * 2：正常入库当月未销售完则通过定时任务在月底，
	 * 		自动复制改库存信息（此库存信息状态置为2）生成一个新的库存信息（此库存信息置为3，F_NUMBER_BAK，F_NUMBER字段值为上月结余数，F_BALANCE_ID的值为被复制记录的id）
	 * 		到下个月的日期成为下个月库存信息里面的上月结余库存正常销售。
	 * 3：上月结余库存，拥有上月的剩余库存数和一切其他信息（参考2的解释）
	 */
	private String fState;
	//创建时间或结转时间
	private String fTime;
	//库存自动流转到下个月，记录上个月库存时间，知道从哪流转的
	private String fBalanceTime;
	//批号 F_BATCH_NUMBER
	private String fBatchNumber;
	//效期 F_EXPIRY_DATE
	private String fExpiryDate;
	//数量	F_NUMBER
	private Integer fNumber;
	//备份数量	供统计用
	private Integer fNumberBak;
	//药品图片	F_IMG
	private String fImg;
	//药品介绍 	F_INTRO
	private String fIntro;
	//药品价格	F_PRICE
	private Double fPrice;
	//工业票价	F_GONGYE_PRICE
	private Double fGongyePrice;
	//进货价格	
	private Double fBuyingPrice;
	//供货价	
	private Double fSupplyPrice;
	//零售价	
	private Double fRetailPrice;
	//仓库id  F_WAREHOUSE_ID
	private Integer fWareHouseId;
	//仓库名称
	private String fWareHouseName;
	//所属药品公司
	private Integer fCompanyId;
	//药品公司名称
	private String fCompanyName;
	//部门名称
	private String fDepartmentName;
	// 小区提成
	private Double fXqTc;
	// 大区提成
	private Double fDqTc;
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
	/**
	 * 库存状态 
	 * 0：正常入库状态或当月入库当月销售完的状态；
	 * 1：添加错误删除状态；
	 * 2：正常入库当月未销售完则通过定时任务在月底，
	 * 		自动复制改库存信息（此库存信息状态置为2）生成一个新的库存信息（此库存信息置为3，F_NUMBER_BAK，F_NUMBER字段值为上月结余数，F_BALANCE_ID的值为被复制记录的id）
	 * 		到下个月的日期成为下个月库存信息里面的上月结余库存正常销售。
	 * 3：上月结余库存，拥有上月的剩余库存数和一切其他信息（参考2的解释）
	 */
	public String getfState() {
		return fState;
	}
	/**
	 * 库存状态 
	 * 0：正常入库状态或当月入库当月销售完的状态；
	 * 1：添加错误删除状态；
	 * 2：正常入库当月未销售完则通过定时任务在月底，
	 * 		自动复制改库存信息（此库存信息状态置为2）生成一个新的库存信息（此库存信息置为3，F_NUMBER_BAK，F_NUMBER字段值为上月结余数，F_BALANCE_ID的值为被复制记录的id）
	 * 		到下个月的日期成为下个月库存信息里面的上月结余库存正常销售。
	 * 3：上月结余库存，拥有上月的剩余库存数和一切其他信息（参考2的解释）
	 */
	public void setfState(String fState) {
		this.fState = fState;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
	}
	public String getfBatchNumber() {
		return fBatchNumber;
	}
	public void setfBatchNumber(String fBatchNumber) {
		this.fBatchNumber = fBatchNumber;
	}
	public String getfExpiryDate() {
		return fExpiryDate;
	}
	public void setfExpiryDate(String fExpiryDate) {
		this.fExpiryDate = fExpiryDate;
	}
	
	public Integer getfNumber() {
		return fNumber;
	}
	public void setfNumber(Integer fNumber) {
		this.fNumber = fNumber;
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
	public Double getfPrice() {
		return fPrice;
	}
	public void setfPrice(Double fPrice) {
		this.fPrice = fPrice;
	}
	public Integer getfWareHouseId() {
		return fWareHouseId;
	}
	public void setfWareHouseId(Integer fWareHouseId) {
		this.fWareHouseId = fWareHouseId;
	}
	public String getfWareHouseName() {
		return fWareHouseName;
	}
	public void setfWareHouseName(String fWareHouseName) {
		this.fWareHouseName = fWareHouseName;
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
	public Integer getfDrugOnlyId() {
		return fDrugOnlyId;
	}
	public void setfDrugOnlyId(Integer fDrugOnlyId) {
		this.fDrugOnlyId = fDrugOnlyId;
	}
	public Double getfBuyingPrice() {
		return fBuyingPrice;
	}
	public void setfBuyingPrice(Double fBuyingPrice) {
		this.fBuyingPrice = fBuyingPrice;
	}
	public Integer getfNumberBak() {
		return fNumberBak;
	}
	public void setfNumberBak(Integer fNumberBak) {
		this.fNumberBak = fNumberBak;
	}
	public Integer getfBalanceId() {
		return fBalanceId;
	}
	public void setfBalanceId(Integer fBalanceId) {
		this.fBalanceId = fBalanceId;
	}
	public Integer getfCompanyId() {
		return fCompanyId;
	}
	public void setfCompanyId(Integer fCompanyId) {
		this.fCompanyId = fCompanyId;
	}
	public String getfCompanyName() {
		return fCompanyName;
	}
	public void setfCompanyName(String fCompanyName) {
		this.fCompanyName = fCompanyName;
	}
	public Double getfGongyePrice() {
		return fGongyePrice;
	}
	public void setfGongyePrice(Double fGongyePrice) {
		this.fGongyePrice = fGongyePrice;
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
	public String getfBalanceTime() {
		return fBalanceTime;
	}
	public void setfBalanceTime(String fBalanceTime) {
		this.fBalanceTime = fBalanceTime;
	}
	public String getfDepartmentName() {
		return fDepartmentName;
	}
	public void setfDepartmentName(String fDepartmentName) {
		this.fDepartmentName = fDepartmentName;
	}
	
}
