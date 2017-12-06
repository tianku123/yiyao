package com.qm.omp.business.pojo.drug;

import java.io.Serializable;
/**
 * 详细订单
 * @author Administrator
 *
 */
public class OrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//主订单id
	private String fOrderId;
	//药品id
	private Integer fDrugId;
	//药品名称  供订单编辑页面使用
	private String fName;
	//药品名称
	private String fDrugName;
	//药品规格 F_SPECIFICATION
	private String fSpecification;
	//产地
	private String fAddress;
	//批号 F_BATCH_NUMBER
	private String fBatchNumber;
	//效期 F_EXPIRY_DATE
	private String fExpiryDate;
	//卖出数量
	private Integer fNumber;
	//卖出数量  供订单编辑页面使用
	private Integer fSalesNumber;
	//药品单价
	private Double fPrice;
	//工业票价
	private Double fGongyePrice;
	//药品进价
	private Double fBuyingPrice;
	//开票价
	private Double fKaiPiaoPrice;
	//过季费
	private Double fGuoJiFei;
	//返点
	private Double fFanDian;
	//高开费
	private Double fGaoKaiFei;
	//总金额:销量*单价
	private Double fMoney_noTax;
	//总金额:销量*进货价
	private Double fMoney_buyingPrice;
	//合计金额
	private Double fMoney;
	// 小区提成
	private Double fXqTc;
	// 大区提成
	private Double fDqTc;
	// 小区提成金额
	private Double fXqTc_Money;
	// 大区提成金额
	private Double fDqTc_Money;
	
	private Integer fDepartmentId;
	
	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	
	public String getfOrderId() {
		return fOrderId;
	}
	public void setfOrderId(String fOrderId) {
		this.fOrderId = fOrderId;
	}
	public Integer getfDrugId() {
		return fDrugId;
	}
	public void setfDrugId(Integer fDrugId) {
		this.fDrugId = fDrugId;
	}
	public String getfDrugName() {
		return fDrugName;
	}
	public void setfDrugName(String fDrugName) {
		this.fDrugName = fDrugName;
	}
	public Integer getfNumber() {
		return fNumber;
	}
	public void setfNumber(Integer fNumber) {
		this.fNumber = fNumber;
	}
	public Double getfPrice() {
		return fPrice;
	}
	public void setfPrice(Double fPrice) {
		this.fPrice = fPrice;
	}
	public Double getfBuyingPrice() {
		return fBuyingPrice;
	}
	public void setfBuyingPrice(Double fBuyingPrice) {
		this.fBuyingPrice = fBuyingPrice;
	}
	public Double getfKaiPiaoPrice() {
		return fKaiPiaoPrice;
	}
	public void setfKaiPiaoPrice(Double fKaiPiaoPrice) {
		this.fKaiPiaoPrice = fKaiPiaoPrice;
	}
	public Double getfGuoJiFei() {
		return fGuoJiFei;
	}
	public void setfGuoJiFei(Double fGuoJiFei) {
		this.fGuoJiFei = fGuoJiFei;
	}
	public Double getfFanDian() {
		return fFanDian;
	}
	public void setfFanDian(Double fFanDian) {
		this.fFanDian = fFanDian;
	}
	public Double getfGaoKaiFei() {
		return fGaoKaiFei;
	}
	public void setfGaoKaiFei(Double fGaoKaiFei) {
		this.fGaoKaiFei = fGaoKaiFei;
	}
	public Double getfMoney() {
		return fMoney;
	}
	public void setfMoney(Double fMoney) {
		this.fMoney = fMoney;
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
	public Double getfMoney_noTax() {
		return fMoney_noTax;
	}
	public void setfMoney_noTax(Double fMoney_noTax) {
		this.fMoney_noTax = fMoney_noTax;
	}
	public Double getfMoney_buyingPrice() {
		return fMoney_buyingPrice;
	}
	public void setfMoney_buyingPrice(Double fMoney_buyingPrice) {
		this.fMoney_buyingPrice = fMoney_buyingPrice;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public Integer getfSalesNumber() {
		return fSalesNumber;
	}
	public void setfSalesNumber(Integer fSalesNumber) {
		this.fSalesNumber = fSalesNumber;
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
	public Double getfXqTc_Money() {
		return fXqTc_Money;
	}
	public void setfXqTc_Money(Double fXqTc_Money) {
		this.fXqTc_Money = fXqTc_Money;
	}
	public Double getfDqTc_Money() {
		return fDqTc_Money;
	}
	public void setfDqTc_Money(Double fDqTc_Money) {
		this.fDqTc_Money = fDqTc_Money;
	}
	public Integer getfDepartmentId() {
		return fDepartmentId;
	}
	public void setfDepartmentId(Integer fDepartmentId) {
		this.fDepartmentId = fDepartmentId;
	}
	
	
	
}
