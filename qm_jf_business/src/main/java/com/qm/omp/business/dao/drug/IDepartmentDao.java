package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Department;

@Repository("departmentDao")
public interface IDepartmentDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<Department> getList(Map<String, Object> params);
	
	
	void save(Department bean);
	
	void edit(Department bean);
	
	List<Department> getAllBean();
	
	/**
	 * 勾选
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> getCheckedDepartmentList(@Param(value="userId")String userId);
	
	
	List<Map<String, Object>> getDepartmentNameByUserId(@Param(value="userId")int userId);

}
