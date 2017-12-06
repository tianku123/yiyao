package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IZgYWYDao;
import com.qm.omp.business.pojo.admin.ZgYWYBean;

@Service("zgYwyService")
public class ZgYWYServiceImpl {

	@Autowired
	private IZgYWYDao zgYWYDao;
	
	public Map<String, Object> getList(Map<String, Object> params, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", zgYWYDao.getListTotal(params));
		res.put("rows", zgYWYDao.getList(params));
		return res;
	}

	
	public void save(ZgYWYBean bean) {
		ZgYWYBean gg = this.zgYWYDao.getBeanByZhuguanIdAndYWYId(bean.getfZhuGuanId(), bean.getfYWYId(), bean.getfDepartmentId());
		if (gg == null) {
			this.zgYWYDao.save(bean);
		}
	}

	
	public void delete(int fId) {
		this.zgYWYDao.delete(fId);
	}

	
	public List<ZgYWYBean> getYWYListByZhuGuanId(Integer fZhuGuanId) {
		return this.zgYWYDao.getYWYListByZhuGuanId(fZhuGuanId);
	}
	

}
