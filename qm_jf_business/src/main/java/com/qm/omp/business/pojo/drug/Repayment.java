package com.qm.omp.business.pojo.drug;

import java.io.Serializable;
/**
 * 还债记录
 * @author Administrator
 *
 */
public class Repayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//订单id
	private String fOrderId;
	//还款金额
	private Double fMoney;
	// 还款方式来源
	private Integer fSource;
	// 备注
	private String fIntro;
	// 还款时间
	private String fTime;
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
	public Double getfMoney() {
		return fMoney;
	}
	public void setfMoney(Double fMoney) {
		this.fMoney = fMoney;
	}
	public Integer getfSource() {
		return fSource;
	}
	public void setfSource(Integer fSource) {
		this.fSource = fSource;
	}
	public String getfIntro() {
		return fIntro;
	}
	public void setfIntro(String fIntro) {
		this.fIntro = fIntro;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
	}
	
	
}
