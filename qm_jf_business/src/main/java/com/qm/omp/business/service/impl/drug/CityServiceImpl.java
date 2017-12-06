package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.admin.IUserDao;
import com.qm.omp.business.dao.drug.ICityDao;
import com.qm.omp.business.dao.drug.ICustomerDao;
import com.qm.omp.business.pojo.drug.City;

@Service("cityService")
public class CityServiceImpl{

	@Autowired
	private ICityDao cityDao;
	
	@Autowired
	private ICustomerDao customerDao;
	
	@Autowired
	private IUserDao userDao;
	
	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", cityDao.getListTotal(params));
		res.put("rows", cityDao.getList(params));
		return res;
	}

	
	public void save(City bean) {
		this.cityDao.save(bean);
	}

	
	public void edit(City bean) {
		this.cityDao.edit(bean);
	}

	
	
	public void delete(City bean) {
		this.cityDao.edit(bean);
		//删除关联
		this.customerDao.deleteBeanByCityId(bean.getfId());
		this.userDao.deleteBeanByCityId(bean.getfId());
		
	}

	
	public List<City> getAllBean() {
		List<City> list = this.cityDao.getAllBean();
		for(City bean : list){
			bean.setParentId("0");
		}
		City bean = new City();
		bean.setfId(0);
		bean.setfName("全部");
		return list;
	}
	
	
	
}
