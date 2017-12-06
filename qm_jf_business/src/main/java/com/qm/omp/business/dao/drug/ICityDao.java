package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.City;

@Repository("cityDao")
public interface ICityDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<City> getList(Map<String, Object> params);
	
	
	void save(City bean);
	
	void edit(City bean);
	
	List<City> getAllBean();
	
	List<City> exportAllBean();

}
