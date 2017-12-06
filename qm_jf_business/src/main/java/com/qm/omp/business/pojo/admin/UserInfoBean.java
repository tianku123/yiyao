package com.qm.omp.business.pojo.admin;

import java.io.Serializable;


/**
 * @ClassName: MerchantUserInfoBean
 * @Description: 商户操作员信息
 * @author jihr
 * @date 2014-2-17 下午4:36:46
 */
public class UserInfoBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    // 用户编码
    private String       fUserCode;
    // 用户角色
    private String       fUserRoleRel;
    // 所属区域
    private Integer 	fCityId;
    // 用户密码
    private String       fUserPwd;
    // 用户状态  0：正常     1：删除
    private int       fUserState;
    // 用户类型  0：后台用户    1：终端用户
    private int       fUserType;
    // 真实姓名
    private String       fUserName;
    // 手机号码
    private String       fUserTel;
    // 创建时间
    private String       fUserCreattime;
	// 部门
	private Integer fDepartmentId;
	// 部门
	private String fDepartmentName;
    
    private String fRoleName;//user表中没有这个字段
    //
    private String parentId;
    
	
	
	public String getfRoleName() {
		return fRoleName;
	}
	public void setfRoleName(String fRoleName) {
		this.fRoleName = fRoleName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getfUserCode() {
		return fUserCode;
	}
	public void setfUserCode(String fUserCode) {
		this.fUserCode = fUserCode;
	}
	public String getfUserRoleRel() {
		return fUserRoleRel;
	}
	public void setfUserRoleRel(String fUserRoleRel) {
		this.fUserRoleRel = fUserRoleRel;
	}
	public String getfUserPwd() {
		return fUserPwd;
	}
	public void setfUserPwd(String fUserPwd) {
		this.fUserPwd = fUserPwd;
	}
	
	public int getfUserState() {
		return fUserState;
	}
	public void setfUserState(int fUserState) {
		this.fUserState = fUserState;
	}
	public int getfUserType() {
		return fUserType;
	}
	public void setfUserType(int fUserType) {
		this.fUserType = fUserType;
	}
	public String getfUserName() {
		return fUserName;
	}
	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}
	public String getfUserTel() {
		return fUserTel;
	}
	public void setfUserTel(String fUserTel) {
		this.fUserTel = fUserTel;
	}
	public String getfUserCreattime() {
		return fUserCreattime;
	}
	public void setfUserCreattime(String fUserCreattime) {
		this.fUserCreattime = fUserCreattime;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getfCityId() {
		return fCityId;
	}
	public void setfCityId(Integer fCityId) {
		this.fCityId = fCityId;
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