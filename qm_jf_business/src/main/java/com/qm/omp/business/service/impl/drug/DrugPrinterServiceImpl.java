package com.qm.omp.business.service.impl.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.ICustomerDao;
import com.qm.omp.business.dao.drug.IDrugOnlyDao;
import com.qm.omp.business.dao.drug.IDrugPrinterDao;
import com.qm.omp.business.pojo.drug.DrugPrinter;

@Service("drugPrinterService")
public class DrugPrinterServiceImpl {

	@Autowired
	private IDrugPrinterDao drugPrinterDao;

	@Autowired
	private IDrugOnlyDao drugOnlyDao;
	
	@Autowired
	private ICustomerDao customerDao;
	
	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", drugPrinterDao.getListTotal(params));
		res.put("rows", drugPrinterDao.getList(params));
		return res;
	}

	
	public void save(DrugPrinter bean) {
		this.drugPrinterDao.save(bean);
	}

	
	public void edit(DrugPrinter bean) {
		this.drugPrinterDao.edit(bean);
	}
	

	
	public void delete(DrugPrinter bean) {
		this.drugPrinterDao.edit(bean);
		//删除关联
		this.drugOnlyDao.deleteBeanByDrugPrinterId(bean.getfId());
		this.customerDao.deleteCustomer_DrugPrinterByDrugPrinterId(bean.getfId());
	}

	
	public List<DrugPrinter> getAllBean() {
		List<DrugPrinter> list = this.drugPrinterDao.getAllBean();
		for(DrugPrinter bean : list){
			bean.setParentId("0");
		}
		DrugPrinter bean = new DrugPrinter();
		bean.setfId(0);
		bean.setfName("全部");
		return list;
	}
	
	
	
	public List<DrugPrinter> getAllBean2() {
		List<DrugPrinter> list = this.drugPrinterDao.getAllBean();
		for(DrugPrinter bean : list){
			bean.setParentId("0");
		}
		DrugPrinter bean = new DrugPrinter();
		bean.setfId(0);
		bean.setfName("全部");
		list.add(bean);
		return list;
	}

	
	public List<DrugPrinter> getAllBean(Integer customerId) {
		List<DrugPrinter> list = this.drugPrinterDao.getAllBean();
		List<Map<String, Integer>> checkedList =  this.drugPrinterDao.getCheckedDrugPrinterList(customerId);
		for(DrugPrinter bean : list){//勾选有权限的URL
			bean.setParentId("0");
			for(Map<String, Integer> chk : checkedList){
				if(bean.getfId().intValue() == MapUtils.getIntValue(chk, "F_DRUG_PRINTER_ID")){
					bean.setChecked(true);
				}
			}
		}
		DrugPrinter bean = new DrugPrinter();
		bean.setfName("全部");
		bean.setfId(0);
		list.add(bean);
		return list;
	}
	
	
	
}
