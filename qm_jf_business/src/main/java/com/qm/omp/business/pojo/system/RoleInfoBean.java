package com.qm.omp.business.pojo.system;
/**
 * @description 角色bean
 * @author rh
 * @date  2014-11-28 10:29:41
 */
public class RoleInfoBean {

	private String roleCode;
	private String roleName;
	private String roleDate;
	private int roleType;//0:后台角色    1：终端角色 终端角色只有一个
	
	private String parentId;//表中没有这个字段，用于显示树状结构的
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDate() {
		return roleDate;
	}
	public void setRoleDate(String roleDate) {
		this.roleDate = roleDate;
	}
	@Override
	public String toString() {
		return "RoleInfoBean [roleCode=" + roleCode + ", roleName=" + roleName
				+ ", roleDate=" + roleDate + ", roleType=" + roleType
				+ ", parentId=" + parentId + "]";
	}
	
	
}
