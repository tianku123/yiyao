package com.qm.omp.business.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qm.omp.business.dao.system.IRoleDao;
import com.qm.omp.business.pojo.system.RoleInfoBean;
import com.qm.omp.business.service.iface.system.IRoleService;
import com.qm.omp.business.web.aop.LogAnnotation;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	/**
	 * 查询角色详情
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public RoleInfoBean queryRoleDetail(String roleCode) {
		return roleDao.queryRoleDetail(roleCode);
	}

	/**
	 * 获取角色数据  
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<RoleInfoBean> getRoleList(String roleType) {
		List<String> par = new ArrayList<String>();
		String[] _arr = roleType.split(",");
		for(String a : _arr){
			par.add(a);
		}
		List<RoleInfoBean> list = roleDao.getRoleList(par);
		for(RoleInfoBean role : list){
			role.setParentId("999999");
		}
		return list;
	}
	

	/**
	 * 新增角色
	 */
	@LogAnnotation(description = "新增角色", menuName = "系统管理--角色管理", method = "新增角色", params = { "新增角色信息" })
	@Override
	public void addRole(RoleInfoBean roleInfoBean) {
		roleDao.addRole(roleInfoBean);
	}

	/**
	 * 修改角色名
	 */
	@LogAnnotation(description = "修改角色", menuName = "系统管理--角色管理", method = "修改角色", params = { "修改角色信息" })
	@Override
	public void editRole(RoleInfoBean roleInfoBean) {
		roleDao.editRole(roleInfoBean);
	}

	/**
	 * 删除角色 ,同时删除角色对应的url映射信息
	 */
	@LogAnnotation(description = "删除角色", menuName = "系统管理--角色管理", method = "删除角色", params = { "角色id" })
	@Override
	public void deleteRole(String fRoleCode) {
		roleDao.deleteRole(fRoleCode);//删除角色
		roleDao.deleteRole_Fun_URL(fRoleCode);//删除角色对应的url映射信息
		roleDao.updateRole_User(fRoleCode);//更改角色对应的用户信息，用户信息的角色项清空
	}

	




	

}
