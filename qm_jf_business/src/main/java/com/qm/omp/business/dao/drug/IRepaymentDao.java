package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Repayment;

@Repository("repaymentDao")
public interface IRepaymentDao {

	Integer getListTotal(Map<String, Object> params);

	List<Repayment> getList(Map<String, Object> params);

	void save(Repayment bean);

	void edit(Repayment bean);


}
