package com.qm.omp.business.pojo.admin;

import java.io.Serializable;


/**
 * @ClassName: ZgYyyBean
 * @Description: 主管与业务员关联表
 * @author rh
 * @date 2014-2-17 下午4:36:46
 */
public class ZgYWYBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fId;
    // 主管id
    private Integer       fZhuGuanId;
    // 业务员id
    private Integer       fYWYId;
    // 部门id
    private Integer fDepartmentId;
    private String fDepartmentName;
    //
    private String fTime;

	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public Integer getfZhuGuanId() {
		return fZhuGuanId;
	}
	public void setfZhuGuanId(Integer fZhuGuanId) {
		this.fZhuGuanId = fZhuGuanId;
	}
	public Integer getfYWYId() {
		return fYWYId;
	}
	public void setfYWYId(Integer fYWYId) {
		this.fYWYId = fYWYId;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
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