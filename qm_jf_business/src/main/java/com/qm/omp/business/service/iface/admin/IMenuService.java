package com.qm.omp.business.service.iface.admin;

import java.util.List;
import java.util.Map;

import com.qm.omp.business.pojo.admin.MenuInfoBean;

/**
 * @ClassName: IFuncQXService
 * @Description: 权限处理
 * @author rh
 * @date 2014-11-28 10:24:22
 */
public interface IMenuService
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
     * @return
     */
	List<MenuInfoBean> getMenuUrlList(String roleCode);
	
	/**
	 * 获取URL树，终端用户权限管理模块
	 * @return
	 */
	List<Map<String, Object>> getMenuUrlList_Terminal(String roleCode);

	/**
	 * 更改角色对应的URL
	 * @param roleCode
	 * @param _urlId
	 */
	void modifyFuncQX(String roleCode, String _urlId);
	
	/**
	 * 更改终端角色对应的URL
	 * @param roleCode
	 * @param _urlId
	 */
	void modifyFuncQX_Terminal(String roleCode, String _urlId);

   

}
