package com.qm.omp.business.pojo.drug;

import java.io.Serializable;

public class WareHouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fId;
	//仓库名称
	private String fName;
	//仓库地址
	private String fAddress;
	//仓库状态：0，正常；1，删除
	private String fState;
	//创建时间
	private String fTime;
	//
	private String parentId;
	
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
