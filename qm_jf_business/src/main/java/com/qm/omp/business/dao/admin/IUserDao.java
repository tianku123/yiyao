package com.qm.omp.business.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.admin.UserInfoBean;

/**
 * @ClassName: IUserDao
 * @Description: 商户信息
 * @author rh
 * @date 2014-12-2 11:56:11
 */
@Repository("userDao")
public interface IUserDao
{

    /**
     * 
     * @Title: selectByPrimaryKey
     * @Description: (根据主键查询)
     * @param id
     * @return
     * @return MerchantUserInfoBean 返回类型
     * @throws
     */
    UserInfoBean selectByPrimaryKey(@Param(value = "id")String id);

	
	/**
	 * 获取用户条数
	 * @return
	 */
	public int queryUserRows(Map<String, Object> param);
	
	/**
	 * 获取用户列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryUserList(Map<String, Object> param);
	
	/**
	 * 根据用户编号查询用户
	 * @param param
	 * @return
	 */
	public UserInfoBean queryUserById(Map<String, Object> param);
	
	
	/**
	 * 根据用户编号查询用户
	 * @param param
	 * @return
	 */
	public void addUser(UserInfoBean userInfoBean);

	/**
	 * 编辑用户
	 * @param userInfoBean
	 */
	void editUser(UserInfoBean userInfoBean);

	/**
	 * 删除用户
	 * @param param
	 */
	void deleteUser(Map<String, Object> param);

	/**
	 * 修改密码
	 * @param id
	 * @param comfiPasswd
	 */
	void modifyUserPasswd(@Param(value = "id")Integer id, 
			@Param(value = "fUserPwd")String comfiPasswd);

	/**
	 * 重置用户密码为：123456
	 * @param fId
	 * @param pwd
	 */
	void modifyPassword(@Param(value = "fId")int fId, 
			@Param(value = "pwd")String pwd);


	List<UserInfoBean> getUserList();


	void deleteBeanByCityId(@Param(value = "fId")Integer fId);

	/**
	 * 主管用户
	 * @return
	 */
	List<UserInfoBean> getZhuGuanUser();

	/**
	 * 业务员
	 * @param param
	 * @return
	 */
	int queryYWYListTotal(Map<String, Object> param);
	List<Map<String, Object>> queryYWYList(Map<String, Object> param);

	/**
	 * 根据主管用户id，确定是哪种主管
	 * @param fZhuGuanId
	 * @return
	 */
	String queryRoleCodeById(@Param(value = "fId")String fZhuGuanId);

	/**
	 * 业务员所属部门
	 * @param id
	 * @param parseInt
	 */
	void saveYwy_Department(@Param(value = "fId")Integer id, @Param(value = "departmentId")int parseInt);
	
	/**
	 * 删除业务员所属部门
	 * @param id
	 */
	void deleteYwy_Department(@Param(value = "fId")Integer id);
}
