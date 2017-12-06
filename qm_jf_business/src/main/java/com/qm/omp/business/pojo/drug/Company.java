package com.qm.omp.business.pojo.drug;

import java.io.Serializable;

public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//名称:合肥汇丰医药有限公司      安徽阜阳新特药业有限责任公司
	private String fName;
	//创建时间
	private String fTime;
	//状态：0，正常；1，删除
	private String fState;
	//
	private String parentId;
	//
	private boolean checked;
	
	
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getfState() {
		return fState;
	}
	public void setfState(String fState) {
		this.fState = fState;
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
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
