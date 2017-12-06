package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Company;

@Repository("companyDao")
public interface ICompanyDao {

	List<Company> getAllBean();

	List<Map<String, Integer>> getCheckedCompanyList(Integer customerId);

}
