/**
 * @Title: FuncQXServiceImple.java
 * @Package: com.qm.omp.business.service.impl.admin
 * @Description: TODO(用一句话描述该文件做什么)
 * @author:  zangjh
 * @date: 2014-2-20 下午2:42:34
 */
package com.qm.omp.business.service.impl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qm.omp.business.dao.admin.IMenuDao;
import com.qm.omp.business.dao.system.IRoleDao;
import com.qm.omp.business.pojo.admin.MenuInfoBean;
import com.qm.omp.business.service.iface.admin.IMenuService;
import com.qm.omp.business.web.aop.LogAnnotation;

/**
 * @ClassName: FuncQXServiceImpl
 * @Description: 用户权限管理
 * @author rh
 * @date 2014年12月24日17:27:55
 */
@Transactional
@Service("menuService")
public class MenuServiceImpl implements IMenuService
{

 
    /**
     * 模块dao
     */
    @Autowired
    private IMenuDao       menuDao;

	@Autowired
	private IRoleDao roleDao;
	
    /**
     * 根据用户code获取用户的权限
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public List<MenuInfoBean> queryAllFunListByUserCode(String userCode)
    {
        return menuDao.queryAllFunListByUserCode(userCode);
    }

    /**
     * 获取URL树，用户权限管理模块
    @LogAnnotation(description = "查询角色对应权限", menuName = "系统管理--权限管理", method = "查询角色对应权限", params = { "角色id" })
    该方法不经过他们封装的action，无法获取request对象，也就无法获取用户信息，所以会报错
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<MenuInfoBean> getMenuUrlList(String roleCode) {
		//所有URL
		List<MenuInfoBean> list = menuDao.getMenuUrlList();
		//根据角色获取的URL
		List<MenuInfoBean> checkedList = menuDao.getMenuUrlListByRoleCode(roleCode);
		for(MenuInfoBean bean : list){//根据角色勾选有权限的URL
			for(MenuInfoBean chk : checkedList){
				if(bean.getFuncId().equalsIgnoreCase(chk.getFuncId())){
					bean.setChecked(true);
				}
			}
		}
		return list;
	}
    
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public List<Map<String, Object>> getMenuUrlList_Terminal(String roleCode) {
    	//所有URL
    	List<Map<String, Object>> list = menuDao.getMenuUrlList_Terminal();
    	
    	//根据角色获取的URL
    	List<Map<String, Object>> checkedList = menuDao.getMenuUrlList_TerminalByRoleCode(roleCode);
    	for(Map<String, Object> bean : list){//根据角色勾选有权限的URL
    		bean.put("checked", false);
    		bean.put("PARENT_ID", "9999999");
    		for(Map<String, Object> chk : checkedList){
    			if(MapUtils.getString(bean, "F_FUNC_ID").equalsIgnoreCase(MapUtils.getString(chk, "F_FUNC_ID"))){
    				bean.put("checked", true);
    			}
    		}
    	}
    	Map<String, Object> all = new HashMap<String, Object>();
    	all.put("F_FUNC_ID", "9999999");
    	all.put("F_FUNC_NAME", "全部");
    	list.add(all);
    	return list;
    }

	/**
	 * 更改角色对应的URL,先删除该角色对应的URL映射，再建立角色、URL映射
	 */
	@LogAnnotation(description = "更改角色对应的URL地址", menuName = "系统管理--权限管理", method = "保存更改角色对应的URL地址", params = { "角色id","url对应id的字符串" })
	@Override
	public void modifyFuncQX(String roleCode, String _urlId) {
		roleDao.deleteRole_Fun_URL(roleCode);
		String[] arr = _urlId.split(",");
		for(String urlId : arr){
			menuDao.modifyFuncQX( roleCode, urlId);
		}
	}
	
	@Override
	public void modifyFuncQX_Terminal(String roleCode, String _urlId) {
		roleDao.deleteRole_Fun_URL_Terminal(roleCode);
		String[] arr = _urlId.split(",");
		for(String urlId : arr){
			menuDao.modifyFuncQX_Terminal( roleCode, urlId);
		}
	}

}
