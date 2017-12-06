package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.DrugOnly;

@Repository("drugOnlyDao")
public interface IDrugOnlyDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<DrugOnly> getList(Map<String, Object> params);
	
	/**
	 * 产品展示
	 * @param params
	 * @return
	 */
	Integer getList_DrugOnlyIntroTotal(Map<String, Object> params);
	List<DrugOnly> getList_DrugOnlyIntro(Map<String, Object> params);
	
	void save(DrugOnly bean);
	
	void edit(DrugOnly bean);
	
	List<DrugOnly> getDrugOnlyList();
	/**
	 * 根据用户勾选药品
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> getCheckedDrugOnlyList(Integer userId);
	
	Integer getSelectedListTotal(Map<String, Object> params);
	List<DrugOnly> getSelectedList(Map<String, Object> params);
	
	
	void deleteBeanByDrugIntroId(Integer fId);
	void deleteBeanByDrugPrinterId(Integer fId);
	
	List<DrugOnly> exportAllBean();
	
	
	

}
