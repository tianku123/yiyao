package com.qm.omp.business.service.iface.system;

import java.util.List;

import com.qm.omp.business.pojo.system.RoleInfoBean;

public interface IRoleService {

	/**
	 * 查询角色详情
	 * @param roleCode
	 * @return
	 */
	public RoleInfoBean queryRoleDetail(String roleCode);

	/**
	 * 获取角色数据
	 * @param roleType 
	 * @return
	 */
	public List<RoleInfoBean> getRoleList(String roleType);
	
	
	/**
	 * 新增角色
	 * @param roleInfoBean
	 */
	public void addRole(RoleInfoBean roleInfoBean);

	/**
	 * 修改角色名
	 * @param roleInfoBean
	 */
	public void editRole(RoleInfoBean roleInfoBean);

	/**
	 * 删除角色，删除角色对应的url映射信息
	 * @param fRoleCode
	 */
	public void deleteRole(String fRoleCode);




}
