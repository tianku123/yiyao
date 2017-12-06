package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IRepaymentDao;
import com.qm.omp.business.pojo.drug.Repayment;

@Service
public class RepaymentService {
	
	@Autowired
	private IRepaymentDao repaymentDao;

	public Map<String, Object> getList(Map<String, Object> params, int page,
			int rows) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", repaymentDao.getListTotal(params));
		res.put("rows", repaymentDao.getList(params));
		return res;
	}

	public void save(Repayment bean) {
		this.repaymentDao.save(bean);
	}

	public void edit(Repayment bean) {
		this.repaymentDao.edit(bean);
	}

}
