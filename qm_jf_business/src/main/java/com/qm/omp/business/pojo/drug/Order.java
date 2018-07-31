package com.qm.omp.business.pojo.drug;

import java.io.Serializable;

import com.qm.common.util.RequestUtil;
/**
 * 主订单
 * @author Administrator
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fId;
	//财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为6的状态，并保存原始订单的id值到该字段
	private String fSourceId;
	//客户id	F_CUSTOMER_ID
	private Integer fCustomerId;
	//客户名称
	private String fCustomerName;
	//收货人
	private String fName;
	//收货地址
	private String fAddress;
	//购货单位
	private String fUnit;
	//收货电话
	private String fPhone;
	//业务员下单时间
	private String fSaleTime;
	//业务员备注
	private String fSaleIntro;
	//业务员id
	private Integer fSaleUserId;
	//业务员名称
	private String fSaleUserName;
	//政策报单审核时间
	private String fPolicyTime;
	//政策报单审核备注
	private String fPolicyIntro2;
	//政策报单审核人id
	private Integer fPolicyUserId;
	//政策报单审核人名称
	private String fPolicyUserName;
	//财务审批时间	F_FINANCE_TIME
	private String fFinanceTime;
	//财务备注	
	private String fFinanceIntro;
	//财务人员id
	private Integer fFinanceUserId;
	//财务人员name
	private String fFinanceUserName;
	//发货时间	F_SHIPPER_TIME
	private String fShipperTime;
	//发货备注	
	private String fShipperIntro;
	//发货人员id
	private Integer fShipperUserId;
	//发货人员name
	private String fShipperUserName;
	//快递单号
	private String fExpressId;
	//快递公司名称
	private String fExpressName;
	/*
	 * 状态：	0，未提交；
			1，提交到财务，
			2，财务审批发货，
			3.已发货，
			4，退单,
			5:业务员删除订单，

			8,财务24小时内未提交被定时器删除（未及时付款）
			9,业务员未提交被定时器删除,
	 */
	private String fState;
	/*
	 * 		0:未被修改
			1:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为1的状态，并保存原始订单的id值到该字段
			2:修改价格后的订单状态；
	 */
	private String fIsEditPrice;
	//是否付款：0，借款，1：已付款
	private String fPaymentState;
	//财务复核：0，未复核，1：复核
	private String fExamine;
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
	//根据公式计算后总金额
	private Double fMoney;
	//是否含税，0：不含税，1：含税(增值税)，2：含税(普通)
	private String fTax;
	//创建时间
	private String fTime;
	//乡镇
	private String fTownship;
	//药房
	private String fYaofang;
	//0:非政策订单，1：政策报单
	private String isPolicy;
	//政策报单内容
	private String fPolicyIntro;
	
	//
	private String parentId;

	// 小区提成金额
	private Double fXqTc_Money;
	// 大区提成金额
	private Double fDqTc_Money;
	// 小区销售金额
	private Double fXq_Money;
	// 大区销售金额
	private Double fDq_Money;
	// 付款方式
	private Integer fPaymentSource;
	// 付款时间
	private String fPaymentTime;
	// 发货方式，0：物流，1：自提，2：快递
	private Integer fSendType;
	// 选择物流发货输入的物流公司名称
	private String fLogistics;
	
	
	public Integer getfPaymentSource() {
		return fPaymentSource;
	}
	public void setfPaymentSource(Integer fPaymentSource) {
		this.fPaymentSource = fPaymentSource;
	}
	public String getfPaymentTime() {
		return fPaymentTime;
	}
	public void setfPaymentTime(String fPaymentTime) {
		this.fPaymentTime = fPaymentTime;
	}
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getfSourceId() {
		return fSourceId;
	}
	public void setfSourceId(String fSourceId) {
		this.fSourceId = fSourceId;
	}
	public Integer getfCustomerId() {
		return fCustomerId;
	}
	public void setfCustomerId(Integer fCustomerId) {
		this.fCustomerId = fCustomerId;
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
	public String getfPhone() {
		return fPhone;
	}
	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}
	public String getfSaleTime() {
		return fSaleTime;
	}
	public void setfSaleTime(String fSaleTime) {
		this.fSaleTime = fSaleTime;
	}
	public Integer getfSaleUserId() {
		return fSaleUserId;
	}
	public void setfSaleUserId(Integer fSaleUserId) {
		this.fSaleUserId = fSaleUserId;
	}
	public String getfSaleUserName() {
		return fSaleUserName;
	}
	public void setfSaleUserName(String fSaleUserName) {
		this.fSaleUserName = fSaleUserName;
	}
	public String getfFinanceTime() {
		return fFinanceTime;
	}
	public void setfFinanceTime(String fFinanceTime) {
		this.fFinanceTime = fFinanceTime;
	}
	public Integer getfFinanceUserId() {
		return fFinanceUserId;
	}
	public void setfFinanceUserId(Integer fFinanceUserId) {
		this.fFinanceUserId = fFinanceUserId;
	}
	public String getfFinanceUserName() {
		return fFinanceUserName;
	}
	public void setfFinanceUserName(String fFinanceUserName) {
		this.fFinanceUserName = fFinanceUserName;
	}
	public String getfShipperTime() {
		return fShipperTime;
	}
	public void setfShipperTime(String fShipperTime) {
		this.fShipperTime = fShipperTime;
	}
	public Integer getfShipperUserId() {
		return fShipperUserId;
	}
	public void setfShipperUserId(Integer fShipperUserId) {
		this.fShipperUserId = fShipperUserId;
	}
	public String getfShipperUserName() {
		return fShipperUserName;
	}
	public void setfShipperUserName(String fShipperUserName) {
		this.fShipperUserName = fShipperUserName;
	}
	public String getfExpressId() {
		return fExpressId;
	}
	public void setfExpressId(String fExpressId) {
		this.fExpressId = fExpressId;
	}
	public String getfExpressName() {
		return fExpressName;
	}
	public void setfExpressName(String fExpressName) {
		this.fExpressName = fExpressName;
	}
	/**
	 * 状态：	0，未提交；
			1，提交到财务，
			2，财务审批发货，
			3.已发货，
			4，退单,
			5:业务员删除订单，
			6:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为6的状态，并保存原始订单的id值到该字段
			7:修改价格后的订单状态；
			8,财务24小时内未提交被定时器删除（未及时付款）
			9,业务员未提交被定时器删除,
	 */
	public String getfState() {
		return fState;
	}
	/**
	 * 状态：	0，未提交；
			1，提交到财务，
			2，财务审批发货，
			3.已发货，
			4，退单,
			5:业务员删除订单，
			6:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为6的状态，并保存原始订单的id值到该字段
			7:修改价格后的订单状态；
			8,财务24小时内未提交被定时器删除（未及时付款）
			9,业务员未提交被定时器删除,
	 */
	public void setfState(String fState) {
		this.fState = fState;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Double getfMoney() {
		return fMoney;
	}
	public void setfMoney(Double fMoney) {
		this.fMoney = fMoney;
	}
	public String getfTax() {
		return fTax;
	}
	public void setfTax(String fTax) {
		this.fTax = fTax;
	}
	public String getfCustomerName() {
		return fCustomerName;
	}
	public void setfCustomerName(String fCustomerName) {
		this.fCustomerName = fCustomerName;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
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
	public Double getfMoney_noTax() {
		return fMoney_noTax;
	}
	public void setfMoney_noTax(Double fMoney_noTax) {
		this.fMoney_noTax = fMoney_noTax;
	}
	public String getfSaleIntro() {
		return fSaleIntro;
	}
	public void setfSaleIntro(String fSaleIntro) {
		this.fSaleIntro = fSaleIntro;
	}
	public String getfFinanceIntro() {
		return fFinanceIntro;
	}
	public void setfFinanceIntro(String fFinanceIntro) {
		this.fFinanceIntro = fFinanceIntro;
	}
	public String getfShipperIntro() {
		return fShipperIntro;
	}
	public void setfShipperIntro(String fShipperIntro) {
		this.fShipperIntro = fShipperIntro;
	}
	public String getfPaymentState() {
		return fPaymentState;
	}
	public void setfPaymentState(String fPaymentState) {
		this.fPaymentState = fPaymentState;
	}
	public Double getfMoney_buyingPrice() {
		return fMoney_buyingPrice;
	}
	public void setfMoney_buyingPrice(Double fMoney_buyingPrice) {
		this.fMoney_buyingPrice = fMoney_buyingPrice;
	}
	public String getfTownship() {
		return fTownship;
	}
	public void setfTownship(String fTownship) {
		this.fTownship = fTownship;
	}
	public String getfYaofang() {
		return fYaofang;
	}
	public void setfYaofang(String fYaofang) {
		this.fYaofang = fYaofang;
	}
	/**
			0:未被修改
			1:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为1的状态，并保存原始订单的id值到该字段
			2:修改价格后的订单状态；
	 */
	public String getfIsEditPrice() {
		return fIsEditPrice;
	}
	/**
			0:未被修改
			1:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为1的状态，并保存原始订单的id值到该字段
			2:修改价格后的订单状态；
	 */
	public void setfIsEditPrice(String fIsEditPrice) {
		this.fIsEditPrice = fIsEditPrice;
	}
	public String getIsPolicy() {
		return isPolicy;
	}
	public void setIsPolicy(String isPolicy) {
		this.isPolicy = isPolicy;
	}
	/**
	 * 0:非政策订单，1：政策报单
	 * @return
	 */
	public String getfPolicyIntro() {
		return fPolicyIntro;
	}
	/**
	 * 0:非政策订单，1：政策报单
	 * @param fPolicyIntro 0:非政策订单，1：政策报单
	 */
	public void setfPolicyIntro(String fPolicyIntro) {
		this.fPolicyIntro = fPolicyIntro;
	}
	public String getfPolicyTime() {
		return fPolicyTime;
	}
	public void setfPolicyTime(String fPolicyTime) {
		this.fPolicyTime = fPolicyTime;
	}
	public String getfPolicyIntro2() {
		return fPolicyIntro2;
	}
	public void setfPolicyIntro2(String fPolicyIntro2) {
		this.fPolicyIntro2 = fPolicyIntro2;
	}
	public Integer getfPolicyUserId() {
		return fPolicyUserId;
	}
	public void setfPolicyUserId(Integer fPolicyUserId) {
		this.fPolicyUserId = fPolicyUserId;
	}
	public String getfPolicyUserName() {
		return fPolicyUserName;
	}
	public void setfPolicyUserName(String fPolicyUserName) {
		this.fPolicyUserName = fPolicyUserName;
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
	public Double getfXq_Money() {
		return fXq_Money;
	}
	public void setfXq_Money(Double fXq_Money) {
		this.fXq_Money = fXq_Money;
	}
	public Double getfDq_Money() {
		return fDq_Money;
	}
	public void setfDq_Money(Double fDq_Money) {
		this.fDq_Money = fDq_Money;
	}
	public String getfUnit() {
		return fUnit;
	}
	public void setfUnit(String fUnit) {
		this.fUnit = fUnit;
	}
	public String getfExamine() {
		return fExamine;
	}
	public void setfExamine(String fExamine) {
		this.fExamine = fExamine;
	}
	public Integer getfSendType() {
		return fSendType;
	}
	public void setfSendType(Integer fSendType) {
		this.fSendType = fSendType;
	}
	public String getfLogistics() {
		return fLogistics;
	}
	public void setfLogistics(String fLogistics) {
		this.fLogistics = fLogistics;
	}
	
	
}
