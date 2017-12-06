package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDepartmentDao;
import com.qm.omp.business.pojo.drug.Department;

@Service("departmentService")
public class DepartmentServiceImpl {

	@Autowired
	private IDepartmentDao departmentDao;

	
	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", departmentDao.getListTotal(params));
		res.put("rows", departmentDao.getList(params));
		return res;
	}

	
	public void save(Department bean) {
		this.departmentDao.save(bean);
	}

	
	public void edit(Department bean) {
		this.departmentDao.edit(bean);
	}

	
	public void delete(Department bean) {
		this.departmentDao.edit(bean);
		// 删除关联
		// this.customerDao.deleteBeanByDepartmentId(bean.getfId());
		// this.userDao.deleteBeanByDepartmentId(bean.getfId());

	}

	
	public List<Department> getAllBean() {
		List<Department> list = this.departmentDao.getAllBean();
		for (Department bean : list) {
			bean.setParentId("0");
		}
		Department bean = new Department();
		bean.setfId(0);
		bean.setfName("全部");
		list.add(bean);
		return list;
	}

	
	public List<Department> getAllBean(String userId) {
		List<Department> list = this.departmentDao.getAllBean();
		List<Map<String, Integer>> checkedList =  this.departmentDao.getCheckedDepartmentList(userId);
		for(Department bean : list){//勾选有权限的URL
			bean.setParentId("0");
			for(Map<String, Integer> chk : checkedList){
				if(bean.getfId().intValue() == MapUtils.getIntValue(chk, "F_DEPARTMENT_ID")){
					bean.setChecked(true);
				}
			}
		}
		Department bean = new Department();
		bean.setfName("全部");
		bean.setfId(0);
		list.add(bean);
		return list;
	}

	
	public String getDepartmentNameByUserId(int userId) {
		List<Map<String, Object>> list = this.departmentDao.getDepartmentNameByUserId(userId);
		StringBuilder name = new StringBuilder();
		for (Map<String, Object> map : list) {
			name.append(MapUtils.getString(map, "F_NAME")).append(",");
		}
		if (name.length() == 0) {
			return "";
		}
		return name.toString().substring(0, name.length() -1);
	}

	
	public List<Map<String, Object>> getDepartmentByUserId(int userId) {
		return this.departmentDao.getDepartmentNameByUserId(userId);
	}

	
	public List<Department> getAllDeparment() {
		return this.departmentDao.getAllBean();
	}
	
	

}
