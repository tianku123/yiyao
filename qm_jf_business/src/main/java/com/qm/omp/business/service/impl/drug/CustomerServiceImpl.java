package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.common.util.DateTimeUtil;
import com.qm.omp.business.dao.drug.ICustomerDao;
import com.qm.omp.business.pojo.drug.Customer;

@Service("customerService")
public class CustomerServiceImpl {

	@Autowired
	private ICustomerDao customerDao;

	
	public Map<String, Object> getList(String fName, int page, int rows, String fCityName) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		params.put("fCityName", fCityName);
		params.put("nowTime", DateTimeUtil.getTodayChar8());
		res.put("total", customerDao.getListTotal(params));
		res.put("rows", customerDao.getList(params));
		return res;
	}

	
	public Map<String, Object> getListById(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("nowTime", DateTimeUtil.getTodayChar8());
		res.put("total", 1);
		res.put("rows", customerDao.getListById(params));
		return res;
	}

	
	public void save(Customer bean, List<String> drugPrinterIds, List<String> companyIds){
		//经营分类
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<drugPrinterIds.size(); i++){
			if("0".equals(drugPrinterIds.get(i))){
				continue;
			}
			if(i == drugPrinterIds.size()-1){
				sb.append(drugPrinterIds.get(i));
			}else{
				sb.append(drugPrinterIds.get(i)).append(",");
			}
		}
		bean.setfDrugPrinterIds(sb.toString());
		//公司分类
		sb = new StringBuilder();
		for(int i=0; i<companyIds.size(); i++){
			if("0".equals(companyIds.get(i))){
				continue;
			}
			if(i == companyIds.size()-1){
				sb.append(companyIds.get(i));
			}else{
				sb.append(companyIds.get(i)).append(",");
			}
		}
		bean.setfCompanyIds(sb.toString());
		this.customerDao.save(bean);
		//经营分类
		for(String drugPrinterId : drugPrinterIds){
			if("0".equals(drugPrinterId)){
				continue;
			}
			this.customerDao.saveCustomer_DrugPrinter(bean.getfId(), Integer.parseInt(drugPrinterId));
		}
		//公司分类
		for(String companyId : companyIds){
			if("0".equals(companyId)){
				continue;
			}
			this.customerDao.saveCustomer_Company(bean.getfId(), Integer.parseInt(companyId));
		}
	}

	
	public void edit(Customer bean) {
		this.customerDao.edit(bean);
	}
	
	
	public void edit(Customer bean, List<String> drugPrinterIds, List<String> companyIds) {
		//经营分类
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<drugPrinterIds.size(); i++){
			if("0".equals(drugPrinterIds.get(i))){
				continue;
			}
			if(i == drugPrinterIds.size()-1){
				sb.append(drugPrinterIds.get(i));
			}else{
				sb.append(drugPrinterIds.get(i)).append(",");
			}
		}
		bean.setfDrugPrinterIds(sb.toString());
		//公司分类
		sb = new StringBuilder();
		for(int i=0; i<companyIds.size(); i++){
			if("0".equals(companyIds.get(i))){
				continue;
			}
			if(i == companyIds.size()-1){
				sb.append(companyIds.get(i));
			}else{
				sb.append(companyIds.get(i)).append(",");
			}
		}
		bean.setfCompanyIds(sb.toString());
		this.customerDao.edit(bean);
		
		this.customerDao.deleteCustomer_DrugPrinter(bean.getfId());
		for(String drugPrinterId : drugPrinterIds){
			if("0".equals(drugPrinterId)){
				continue;
			}
			this.customerDao.saveCustomer_DrugPrinter(bean.getfId(), Integer.parseInt(drugPrinterId));
		}
		
		//公司分类
		this.customerDao.deleteCustomer_Company(bean.getfId());
		for(String companyId : companyIds){
			if("0".equals(companyId)){
				continue;
			}
			this.customerDao.saveCustomer_Company(bean.getfId(), Integer.parseInt(companyId));
		}
	}

	
	public Map<String, Object> getList_youXiao(int page,
			int rows, Map<String, Object> params ) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("nowTime", DateTimeUtil.getTodayChar8());
		res.put("total", customerDao.getListTotal_youXiao(params));
		res.put("rows", customerDao.getList_youXiao(params));
		return res;
	}
	
	
	
}
