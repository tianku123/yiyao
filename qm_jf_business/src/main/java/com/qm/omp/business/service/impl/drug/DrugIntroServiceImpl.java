package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDrugIntroDao;
import com.qm.omp.business.dao.drug.IDrugOnlyDao;
import com.qm.omp.business.pojo.drug.DrugIntro;

@Service("drugIntroService")
public class DrugIntroServiceImpl {

	@Autowired
	private IDrugIntroDao drugIntroDao;
	
	@Autowired
	private IDrugOnlyDao drugOnlyDao;

	
	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", drugIntroDao.getListTotal(params));
		res.put("rows", drugIntroDao.getList(params));
		return res;
	}

	
	public void save(DrugIntro bean) {
		this.drugIntroDao.save(bean);
	}

	
	public void edit(DrugIntro bean) {
		this.drugIntroDao.edit(bean);
	}

	
	
	public void delete(DrugIntro bean) {
		this.drugIntroDao.edit(bean);
		//删除关联
		this.drugOnlyDao.deleteBeanByDrugIntroId(bean.getfId());
		
	}

	
	public List<DrugIntro> getAllBean() {
		List<DrugIntro> list = this.drugIntroDao.getAllBean();
		for(DrugIntro bean : list){
			bean.setParentId("0");
		}
		DrugIntro bean = new DrugIntro();
		bean.setfId(0);
		bean.setfName("全部");
		return list;
	}
	
	
	
}
