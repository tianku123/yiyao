package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.DrugIntro;

@Repository("drugIntroDao")
public interface IDrugIntroDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<DrugIntro> getList(Map<String, Object> params);
	
	
	void save(DrugIntro bean);
	
	void edit(DrugIntro bean);
	
	List<DrugIntro> getAllBean();
	
	List<DrugIntro> exportAllBean();

}
