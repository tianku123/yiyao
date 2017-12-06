package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.admin.ZgYWYBean;
import com.qm.omp.business.pojo.drug.City;

@Repository("zgYwyDao")
public interface IZgYWYDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<Map<String, Object>> getList(Map<String, Object> params);
	
	
	void save(ZgYWYBean bean);

	void delete(@Param(value="fId")int fId);
	
	/**
	 * 检查该主管下是否添加该业务员
	 * @param fDepartmentId 
	 * @param getfZhuGuanId
	 * @param getfYWYId
	 * @return
	 */
	ZgYWYBean getBeanByZhuguanIdAndYWYId(@Param(value="fZhuGuanId")Integer fZhuGuanId,
			@Param(value="fYWYId")Integer fYWYId, @Param(value="fDepartmentId")Integer fDepartmentId);
	
	/**
	 * 获取该主管下所有业务员
	 * @param fZhuGuanId
	 * @return
	 */
	List<ZgYWYBean> getYWYListByZhuGuanId(@Param(value="fZhuGuanId")Integer fZhuGuanId);
	
	
	List<ZgYWYBean> getBeansByZhuguanIdAndYWYId(@Param(value="fZhuGuanId")Integer fZhuguanId,
			@Param(value="fYWYId")Integer fYwyId);
	
	/**
	 * 根据业务员名称模糊查询
	 * 根据主管id
	 * @param params
	 * @return
	 */
	List<ZgYWYBean> getYWYListByZhuGuanIdAndYwyName(Map<String, Object> params);

}
