package com.qm.omp.business.service.impl.drug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDrugDao;
import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.util.DateTimeUtil;

@Service("drugService")
public class DrugServiceImpl {

	@Autowired
	private IDrugDao drugDao;

	public Map<String, Object> getList(int page, int rows,
			Map<String, Object> params) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", drugDao.getListTotal(params));
		res.put("rows", drugDao.getList(params));
		return res;
	}

	public Map<String, Object> queryStock(int page, int rows,
			Map<String, Object> params) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", drugDao.queryStockTotal(params));
		res.put("rows", drugDao.queryStock(params));
		return res;
	}

	public Map<String, Object> getListByUser(int page, int rows,
			Map<String, Object> params) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", drugDao.getListByUserTotal(params));
		res.put("rows", drugDao.getListByUser(params));
		return res;
	}

	public Map<String, Object> getListByUser_ExceptSelected(int page, int rows,
			Map<String, Object> params) {
		page = (page - 1) * rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		String time = DateTimeUtil.getTodayChar6();
		params.put("time", time);

		List<Integer> idList = null;// 需要排除在外的药品id
		List<Drug> data = drugDao.getListByUser_ExceptSelected(params);// 比较已经选择的药品库存是否已被销完
		if (params.containsKey("selected")) {
			idList = new ArrayList<Integer>();// 需要排除在外的药品id
			Map<Integer, Integer> sel = (Map<Integer, Integer>) params
					.get("selected");
			ListIterator<Drug> drugL = null;
			Drug bean = null;
			for (Entry<Integer, Integer> en : sel.entrySet()) {
				drugL = data.listIterator();
				System.out.println("getListByUser_ExceptSelected==idList:"
						+ en.getKey().intValue());
				while (drugL.hasNext()) {// 如果和已选择的药品id相同并且库存和销量一致则删除该药（隐藏药品）,这里收集需要排除掉的药品id
					bean = drugL.next();
					if (bean.getfId().intValue() == en.getKey().intValue()
							&& bean.getfNumber().intValue() == en.getValue()
									.intValue()) {
						idList.add(bean.getfId());
						continue;
					}
				}
			}
			System.out
					.println("getListByUser_ExceptSelected==idList:" + idList);
			params.put("idList", idList);
		}
		res.put("total", drugDao.getListByUser_ExceptSelectedIdsTotal(params));
		res.put("rows", drugDao.getListByUser_ExceptSelectedIds(params));
		return res;
	}

	public List<Drug> getListByUser_ExceptSelected(Map<String, Object> params) {
		return drugDao.getListByUser_ExceptSelected(params);
	}

	public void save(Drug bean) {
		this.drugDao.save(bean);
	}

	public void edit(Drug bean) {
		// 库存修改了，则备份库存也要改，库存加减多少备份库存也加减多少
		Drug res = this.drugDao.getBean(bean.getfId());
		int num = res.getfNumber().intValue() - bean.getfNumber().intValue();// 原库存减去修改后的库存，50
																				// 100
		int numBak = res.getfNumberBak().intValue() - num;
		bean.setfNumberBak(numBak);
		this.drugDao.edit(bean);
	}

	public void editState(Drug source) {
		this.drugDao.edit(source);
	}

	@Deprecated
	public List<Drug> getDrugList(Integer userId) {
		List<Drug> list = this.drugDao.getDrugList();
		List<Map<String, Integer>> checkedList = this.drugDao
				.getCheckedDrugList(userId);
		for (Drug bean : list) {// 勾选有权限的URL
			bean.setParentId("0");
			for (Map<String, Integer> chk : checkedList) {
				if (bean.getfId().intValue() == MapUtils.getIntValue(chk,
						"F_DRUG_ID")) {
					bean.setChecked(true);
				}
			}
		}
		Drug bean = new Drug();
		bean.setfName("全部");
		bean.setfId(0);
		list.add(bean);
		return list;
	}

	public void userDrug(int userId, String drugIds) {
		this.drugDao.deleteUserDrug(userId);
		String[] arr = drugIds.split(",");
		int drugId;
		for (String str : arr) {
			drugId = Integer.parseInt(str);
			if (drugId > 0) {

				this.drugDao.saveUserDrug(userId, drugId);
			} else {
				System.out.println(drugId);
			}
		}
	}

	public void deleteUserDrugByDrugOnlyId(int id) {
		this.drugDao.deleteUserDrugByDrugOnlyId(id);
	}

	public void userDrugOnly_setPrice(int userId, int drugOnlyId,
			Double fSupplyPrice, Double fRetailPrice) {

		this.drugDao.deleteUserDrugOnly_setPrice(userId, drugOnlyId);

		this.drugDao.userDrugOnly_setPrice(userId, drugOnlyId, fSupplyPrice,
				fRetailPrice);

	}

	public Map<String, Object> userDrugOnly_showPrice(int userId, int drugOnlyId) {

		return this.drugDao.userDrugOnly_showPrice(userId, drugOnlyId);
	}

	public Drug getBean(int drugId) {
		return this.drugDao.getBean(drugId);
	}

	public Drug getBeanByBalanceId(int drugId) {
		return this.drugDao.getBeanByBalanceId(drugId);
	}

	public List<Drug> getBeanByDrugOnlyId(Map<String, Object> params) {
		return this.drugDao.getBeanByDrugOnlyId(params);
	}

	public void addNumber(Integer fDrugId, Integer fNumber) {
		this.drugDao.addNumber(fDrugId, fNumber);
	}
	
	public void addNumberAndNumberBak(Integer fDrugId, Integer fNumber) {
		this.drugDao.addNumberAndNumberBak(fDrugId, fNumber);
	}

	public List<Drug> getListByTime(String yyyyMM) {
		return this.drugDao.getListByTime(yyyyMM);
	}

}
