package com.qm.omp.business.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.system.RoleInfoBean;
@Repository("roleDao")
public interface IRoleDao {

	/**
	 * 查询角色详情
	 * @param roleCode
	 * @return
	 */
	public RoleInfoBean queryRoleDetail(@Param(value="roleCode")String roleCode);

	/**
	 * 获取角色数据
	 * @param par 
	 * @return
	 */
	public List<RoleInfoBean> getRoleList(@Param(value="list")List<String> par);
	


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
	 * 删除角色
	 * @param fRoleCode
	 */
	public void deleteRole(@Param(value="roleCode")String fRoleCode);

	/**
	 * 删除角色对应的url映射信息
	 * @param fRoleCode
	 */
	public void deleteRole_Fun_URL(@Param(value="roleCode")String fRoleCode);

	/**
	 * 更改角色对应的用户信息，用户信息的角色项清空
	 * @param fRoleCode
	 */
	public void updateRole_User(@Param(value="roleCode")String fRoleCode);

	public void deleteRole_Fun_URL_Terminal(@Param(value="roleCode")String roleCode);

	
}
