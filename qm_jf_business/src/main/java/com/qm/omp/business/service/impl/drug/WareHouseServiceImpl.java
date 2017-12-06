package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDrugDao;
import com.qm.omp.business.dao.drug.IWareHouseDao;
import com.qm.omp.business.pojo.drug.WareHouse;

@Service("wareHouseService")
public class WareHouseServiceImpl {

	@Autowired
	private IWareHouseDao wareHouseDao;
	
	@Autowired
	private IDrugDao drugDao;

	
	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", wareHouseDao.getListTotal(params));
		res.put("rows", wareHouseDao.getList(params));
		return res;
	}

	
	public void save(WareHouse bean) {
		this.wareHouseDao.save(bean);
	}

	
	public void edit(WareHouse bean) {
		this.wareHouseDao.edit(bean);
	}
	
	

	
	public void delete(WareHouse bean) {
		this.wareHouseDao.edit(bean);
		//删除关联
		this.drugDao.deleteBeanByWareHouseId(bean.getfId());
	}

	
	public List<WareHouse> getAllBean() {
		List<WareHouse> list = this.wareHouseDao.getAllBean();
		for(WareHouse bean : list){
			bean.setParentId("0");
		}
		WareHouse bean = new WareHouse();
		bean.setfId(0);
		bean.setfName("全部");
		return list;
	}
	
	
	
}
