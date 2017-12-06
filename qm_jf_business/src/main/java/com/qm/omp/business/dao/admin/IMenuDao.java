package com.qm.omp.business.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qm.omp.business.pojo.admin.MenuInfoBean;

/**
 * @ClassName: IMenuDao
 * @Description:
 * @author rh
 * @date 2014-12-3 13:46:37
 */
public interface IMenuDao
{

    /**
     * @Title: queryAllFunListByUserCode
     * @Description: 根据用户code获取用户的权限
     * @param userCode
     * @return
     * @return List<FunInfoBean> 返回类型
     * @throws
     */

    List<MenuInfoBean> queryAllFunListByUserCode(String userCode);

    /**
     * 获取URL树，用户权限管理模块
     */
	List<MenuInfoBean> getMenuUrlList();
	
	/**
     * 根据角色获取该角色所有拥有的url
     */
	List<MenuInfoBean> getMenuUrlListByRoleCode(@Param(value="roleCode")String roleCode);
	
	/**
	 * 获取URL树，终端用户权限管理模块
	 */
	List<Map<String, Object>> getMenuUrlList_Terminal();
	
	/**
	 * 根据角色获取该角色所有拥有的url
	 */
	List<Map<String, Object>> getMenuUrlList_TerminalByRoleCode(@Param(value="roleCode")String roleCode);

	/**
	 * 更改角色对应的URL
	 * @param roleCode
	 * @param _urlId
	 */
	void modifyFuncQX(@Param(value="roleCode")String roleCode, 
			@Param(value="urlId")String urlId);

	void modifyFuncQX_Terminal(@Param(value="roleCode")String roleCode, 
			@Param(value="urlId")String urlId);
	
}
