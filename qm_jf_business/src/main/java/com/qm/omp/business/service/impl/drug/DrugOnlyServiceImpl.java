package com.qm.omp.business.service.impl.drug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDrugOnlyDao;
import com.qm.omp.business.pojo.drug.Department;
import com.qm.omp.business.pojo.drug.DrugOnly;

@Service("drugOnlyService")
public class DrugOnlyServiceImpl{

	@Autowired
	private IDrugOnlyDao drugOnlyDao;

	@Autowired
	private DepartmentServiceImpl departmentService;

	
	public Map<String, Object> getList(int page, int rows, Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", drugOnlyDao.getListTotal(params));
		res.put("rows", drugOnlyDao.getList(params));
		return res;
	}
	
	
	
	
	public Map<String, Object> getList_DrugOnlyIntro(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		List<DrugOnly> list = drugOnlyDao.getList_DrugOnlyIntro(params);
		ListIterator<DrugOnly> ite = list.listIterator();
		DrugOnly bean;
		DrugOnly bean22;
		Integer fNumber = 0;
		Map<Integer, DrugOnly> map = new LinkedHashMap<Integer, DrugOnly>();
		while(ite.hasNext()){
			bean = ite.next();
			if(map.containsKey(bean.getfId())){
				bean22 = (DrugOnly) map.get(bean.getfId());
				fNumber = bean22.getfNumber();
				fNumber += bean.getfNumber();
				bean22.setfNumber(fNumber);
				ite.remove();
				map.put(bean.getfId(), bean22);
			}else{
				map.put(bean.getfId(), bean);
			}
		}
		List<DrugOnly> list2 = new ArrayList<DrugOnly>();
		for(Entry<Integer, DrugOnly> en : map.entrySet()){
			list2.add(en.getValue());
		}
		res.put("total", list2.size());
		res.put("rows", list2);
		return res;
	}



	
	public Map<String, Object> getSelectedList(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", drugOnlyDao.getSelectedListTotal(params));
		res.put("rows", drugOnlyDao.getSelectedList(params));
		return res;
	}



	
	public void save(DrugOnly bean) {
		this.drugOnlyDao.save(bean);
	}

	
	public void edit(DrugOnly bean) {
		this.drugOnlyDao.edit(bean);
	}

	public List<DrugOnly> getDrugOnlyList(Integer userId) {
		
		/**
		 * 获取部门
		 */
		List<Department> deps = this.departmentService.getAllDeparment();
		List<DrugOnly> list =  this.drugOnlyDao.getDrugOnlyList();
		List<Map<String, Integer>> checkedList =  this.drugOnlyDao.getCheckedDrugOnlyList(userId);
		for(DrugOnly bean : list){//勾选有权限的URL
			bean.setParentId("0");
			for(Map<String, Integer> chk : checkedList){
				if(bean.getfId().intValue() == MapUtils.getIntValue(chk, "F_DRUG_ID")){
					bean.setChecked(true);
				}
			}
		}
		/**
		 * 根据部门分类
		 * 有部门药品
		 */
		List<DrugOnly> trees = new ArrayList<DrugOnly>();
		DrugOnly bean;
		for (Department de : deps) {
			bean = new DrugOnly();
			bean.setChecked(false);
			bean.setfId(-de.getfId());
			bean.setfName(de.getfName());
			bean.setParentId("0");
			trees.add(bean);
			for (DrugOnly drug : list) {
				if (de.getfId().equals(drug.getfDepartmentId())) {
					drug.setParentId((-de.getfId()) + "");
					trees.add(drug);
				}
			}
		}
		/**
		 * 不在部门里的药品
		 */
		for (DrugOnly drug : list) {
			if (!this.contains(trees, drug.getfId())) {
				trees.add(drug);
			}
		}
		bean = new DrugOnly();
		bean.setfName("全部");
		bean.setfId(0);
		trees.add(bean);
		return trees;
	}
	
	private boolean contains(List<DrugOnly> list, Integer id) {
		boolean flag = false;
		for (DrugOnly drug : list) {
			if (drug.getfId().equals(id)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	
}
