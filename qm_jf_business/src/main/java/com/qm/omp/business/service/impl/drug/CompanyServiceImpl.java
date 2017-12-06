package com.qm.omp.business.service.impl.drug;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.ICompanyDao;
import com.qm.omp.business.pojo.drug.Company;

@Service("companyService")
public class CompanyServiceImpl {

	@Autowired
	private ICompanyDao companyDao;
	
	
	public List<Company> getAllBean() {
		List<Company> list = this.companyDao.getAllBean();
		for(Company bean : list){
			bean.setParentId("0");
		}
		Company bean = new Company();
		bean.setfId(0);
		bean.setfName("全部");
		list.add(bean);
		return list;
	}
	
	
	public List<Company> getAllBean2() {
		List<Company> list = this.companyDao.getAllBean();
		for(Company bean : list){
			bean.setParentId("0");
		}
		Company bean = new Company();
		bean.setfId(0);
		bean.setfName("全部");
		return list;
	}

	
	public List<Company> getAllBean(Integer customerId) {
		List<Company> list = this.companyDao.getAllBean();
		List<Map<String, Integer>> checkedList =  this.companyDao.getCheckedCompanyList(customerId);
		for(Company bean : list){//勾选有权限的URL
			bean.setParentId("0");
			for(Map<String, Integer> chk : checkedList){
				if(bean.getfId().intValue() == MapUtils.getIntValue(chk, "F_COMPANY_ID")){
					bean.setChecked(true);
				}
			}
		}
		Company bean = new Company();
		bean.setfName("全部");
		bean.setfId(0);
		list.add(bean);
		return list;
	}

	
}
