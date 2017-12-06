package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.WareHouse;

@Repository("wareHouseDao")
public interface IWareHouseDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<WareHouse> getList(Map<String, Object> params);
	
	
	void save(WareHouse bean);
	
	void edit(WareHouse bean);
	
	List<WareHouse> getAllBean();
	
	List<WareHouse> exportAllBean();

}
