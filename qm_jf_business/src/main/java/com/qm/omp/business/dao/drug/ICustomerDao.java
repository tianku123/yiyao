package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Customer;

@Repository("customerDao")
public interface ICustomerDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<Customer> getList(Map<String, Object> params);
	
	
	void save(Customer bean);
	
	void edit(Customer bean);
	
	/**
	 * 有效的客户
	 * @param params
	 * @return
	 */
	Integer getListTotal_youXiao(Map<String, Object> params);
	List<Customer> getList_youXiao(Map<String, Object> params);
	
	
	void deleteBeanByCityId(Integer fId);
	
	
	void saveCustomer_DrugPrinter(@Param(value="customerId")Integer fId, @Param(value="drugPrinterId")int drugPrinterId);
	void deleteCustomer_DrugPrinter(@Param(value="customerId")Integer fId);
	
	
	void deleteCustomer_DrugPrinterByDrugPrinterId(@Param(value="drugPrinterId")Integer fId);
	
	void saveCustomer_Company(@Param(value="customerId")Integer fId, @Param(value="companyId")int companyId);
	void deleteCustomer_Company(@Param(value="customerId")Integer fId);
	
	List<Customer> getListById(Map<String, Object> params);

}
