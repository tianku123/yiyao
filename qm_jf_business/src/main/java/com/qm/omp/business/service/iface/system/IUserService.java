package com.qm.omp.business.service.iface.system;

import java.util.List;
import java.util.Map;

import com.qm.omp.business.pojo.admin.UserInfoBean;

public interface IUserService {

	/**
	 * 获取用户数据
	 * @return
	 */
	public Map<String, Object> queryUserList(Map<String, Object> param,int pageNum,int pageSize);
	
	/**
	 * 获取业务员
	 * @return
	 */
	public Map<String, Object> queryYWYList(Map<String, Object> param,int pageNum,int pageSize);

	/**
	 * 根据用户编号查询用户
	 * @param param
	 * @return
	 */
	public UserInfoBean queryUserById(Map<String, Object> param);

	/**
	 * 新增用户
	 * @param userInfoBean
	 * @param fDepartmentIdLists 
	 */
	public void addUser(UserInfoBean userInfoBean, List<String> fDepartmentIdLists);

	/**
	 * 编辑用户
	 * @param userInfoBean
	 * @param fDepartmentIdLists 
	 */
	public void editUser(UserInfoBean userInfoBean, List<String> fDepartmentIdLists);

	/**
	 * 删除用户
	 * @param param
	 */
	public void deleteUser(Map<String, Object> param);

	/**
	 * 修改密码
	 * @param id
	 * @param comfiPasswd
	 */
	public void modifyUserPasswd(Integer id, String comfiPasswd);

	/**
	 * 重置用户密码为：123456
	 * @param fId
	 * @param pwd
	 */
	public void modifyPassword(int fId, String pwd);

	
	public List<UserInfoBean> getUserList();

	/**
	 * 主管用户 
	 * @return
	 */
	public List<UserInfoBean> getZhuGuanUser();

	/**
	 * 根据主管用户id，确定是哪种主管
	 * @param fZhuGuanId
	 * @return
	 */
	public String queryRoleCodeById(String fZhuGuanId);

}
