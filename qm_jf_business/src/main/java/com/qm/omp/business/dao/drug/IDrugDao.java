package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Drug;

@Repository("drugDao")
public interface IDrugDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<Drug> getList(Map<String, Object> params);
	
	Integer queryStockTotal(Map<String, Object> params);
	List<Drug> queryStock(Map<String, Object> params);
	
	
	void save(Drug bean);
	
	void edit(Drug bean);
	
	List<Drug> getDrugList();
	
	/**
	 * 已选择的药品
	 * @param userId
	 * @return
	 */
	List<Map<String, Integer>> getCheckedDrugList(@Param(value="userId")Integer userId);
	
	
	void deleteUserDrugByDrugOnlyId(@Param(value="id")int id);
	
	void deleteUserDrug(@Param(value="userId")int userId);
	void saveUserDrug(@Param(value="userId")int userId, @Param(value="drugId")int drugId);
	
	/**
	 * 用户有权限的药品
	 * @param params
	 * @return
	 */
	Integer getListByUserTotal(Map<String, Object> params);
	List<Drug> getListByUser(Map<String, Object> params);
	
	Drug getBean(@Param(value="fDrugId")Integer fDrugId);
	
	void minusNumber(@Param(value="fDrugId")Integer fDrugId, @Param(value="fNumber")Integer fNumber);
	
	void deleteBeanByWareHouseId(Integer fId);
	
	List<Drug> getBeanByDrugOnlyId(Map<String, Object> params);

	void deleteUserDrugOnly_setPrice(@Param(value="userId")int userId, 
			@Param(value="drugOnlyId")int drugOnlyId);
	void userDrugOnly_setPrice(@Param(value="userId")int userId, 
			@Param(value="drugOnlyId")int drugOnlyId, 
			@Param(value="fSupplyPrice")Double fSupplyPrice,
			@Param(value="fRetailPrice")Double fRetailPrice);
	
	Map<String, Object> userDrugOnly_showPrice(@Param(value="userId")int userId, 
			@Param(value="drugOnlyId")int drugOnlyId);
	
	/**
	 * 比较已经选择的药品库存是否已被销完
	 * @param params
	 * @return
	 */
	List<Drug> getListByUser_ExceptSelected(Map<String, Object> params);

	/**
	 * 排除已经选择的销售空的药品
	 * @param params
	 * @return
	 */
	Integer getListByUser_ExceptSelectedIdsTotal(Map<String, Object> params);
	List<Drug> getListByUser_ExceptSelectedIds(Map<String, Object> params);

	List<Drug> getListByTime(@Param(value="yyyyMM")String yyyyMM);
	
	List<Drug> exportAllBean();

}
