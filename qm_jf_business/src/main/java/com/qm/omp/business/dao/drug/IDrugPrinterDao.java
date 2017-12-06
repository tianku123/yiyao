package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.DrugPrinter;

@Repository("drugPrinterDao")
public interface IDrugPrinterDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<DrugPrinter> getList(Map<String, Object> params);
	
	
	void save(DrugPrinter bean);
	
	void edit(DrugPrinter bean);
	
	List<DrugPrinter> getAllBean();
	List<Map<String, Integer>> getCheckedDrugPrinterList(Integer customerId);
	
	List<DrugPrinter> exportAllBean();

}
