package com.qm.omp.business.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qm.omp.business.dao.admin.IUserDao;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.system.IUserService;
import com.qm.omp.business.service.impl.drug.DepartmentServiceImpl;
import com.qm.omp.business.web.aop.LogAnnotation;
/**
 * 用户资料管理service
 * @author rh
 * @DATE 2014-12-2 12:00:36
 *
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private DepartmentServiceImpl departmentService;

	/**
	 * 查询用户列表，datagrid渲染
	 */
	@LogAnnotation(description = "用户查询", menuName = "系统管理--用户管理", method = "用户查询", params = { "查询用户的多种参数","当前页","每页行数" })
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> queryUserList(Map<String, Object> param,
			int page, int pageSize) {
		 // TODO Auto-generated method stub
        Map<String, Object> retMap = new HashMap<String, Object>();
        param.put("start", (page - 1) * pageSize);
        param.put("pageSize", pageSize);
        int total = userDao.queryUserRows(param);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        if (total != 0)
        {
            rows = userDao.queryUserList(param);
            /**
             * 显示部门
             * 根据用户id拼接部门
             */
            int id;
            String department;
            for (Map<String, Object> map : rows) {
				id = MapUtils.getIntValue(map, "F_ID");
				department = this.departmentService.getDepartmentNameByUserId(id);
				map.put("F_DEPARTMENT_NAME", department);
			}
        }
        retMap.put("rows", rows);
        retMap.put("total", total);
        return retMap;
	}
	
	/**
	 * 业务员
	 */
	@Override
	public Map<String, Object> queryYWYList(Map<String, Object> param,
			int page, int pageSize) {
		Map<String, Object> retMap = new HashMap<String, Object>();
        param.put("start", (page - 1) * pageSize);
        param.put("pageSize", pageSize);
        int total = userDao.queryYWYListTotal(param);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        if (total != 0)
        {
            rows = userDao.queryYWYList(param);
        }
        retMap.put("rows", rows);
        retMap.put("total", total);
        return retMap;
	}



	/**
	 * 根据用户编号查询用户
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public UserInfoBean queryUserById(Map<String, Object> param) {
		return userDao.queryUserById(param);
	}

	/**
	 * 添加用户
	 */
//	@LogAnnotation(description = "添加用户", menuName = "系统管理--用户管理", method = "添加用户", params = { "用户信息" })
	@Override
	public void addUser(UserInfoBean userInfoBean, List<String> fDepartmentIdLists) {
		userDao.addUser(userInfoBean);
		if (fDepartmentIdLists != null && fDepartmentIdLists.size() > 0) {
			//部门
			for(String id : fDepartmentIdLists){
				if("0".equals(id) || "".equals(id)){
					continue;
				}
				this.userDao.saveYwy_Department(userInfoBean.getId(), Integer.parseInt(id));
			}
		}
	}

	/**
	 * 编辑用户
	 */
//	@LogAnnotation(description = "编辑用户", menuName = "系统管理--用户管理", method = "编辑用户", params = { "用户信息" })
	@Override
	public void editUser(UserInfoBean userInfoBean, List<String> fDepartmentIdLists) {
		userDao.editUser(userInfoBean);
		this.userDao.deleteYwy_Department(userInfoBean.getId());
		//部门
		for(String id : fDepartmentIdLists){
			if("0".equals(id)){
				continue;
			}
			this.userDao.saveYwy_Department(userInfoBean.getId(), Integer.parseInt(id));
		}
	}

	/**
	 * 删除用户
	 */
	@LogAnnotation(description = "删除用户", menuName = "系统管理--用户管理", method = "删除用户", params = { "用户id" })
	@Override
	public void deleteUser(Map<String, Object> param) {
		userDao.deleteUser(param);
	}

	/**
	 * 修改密码
	 */
	@Override
	@LogAnnotation(description = "修改密码", menuName = "首页--修改密码", method = "修改密码", params = { "用户id","修改后密码" })
	public void modifyUserPasswd(Integer id, String comfiPasswd) {
		this.userDao.modifyUserPasswd(id,comfiPasswd);
	}

	/**
	 * 重置用户密码为：123456
	 */
	@LogAnnotation(description = "重置用户密码", menuName = "系统管理--用户管理--重置用户密码", method = "重置用户密码", params = { "用户id","初始密码" })
	@Override
	public void modifyPassword(int fId, String pwd) {
		this.userDao.modifyPassword(fId,pwd);
	}

	@Override
	public List<UserInfoBean> getUserList() {
		List<UserInfoBean> list = this.userDao.getUserList();
		for(UserInfoBean role : list){
			role.setParentId("0");
		}
		return list;
	}

	/**
	 * 主管用户
	 */
	@Override
	public List<UserInfoBean> getZhuGuanUser() {
		return this.userDao.getZhuGuanUser();
	}

	/**
	 * 根据主管用户id，确定是哪种主管
	 */
	@Override
	public String queryRoleCodeById(String fZhuGuanId) {
		return this.userDao.queryRoleCodeById(fZhuGuanId);
	}

	
}
