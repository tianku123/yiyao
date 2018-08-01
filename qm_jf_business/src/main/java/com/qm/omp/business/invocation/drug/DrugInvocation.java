package com.qm.omp.business.invocation.drug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;
import com.qm.omp.business.util.DateTimeUtil;

/**
 * @ClassName: DrugInvocation
 * @Description: 库存管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_drug")
public class DrugInvocation implements BaseInvocation {
	private Logger logger = Logger.getLogger(DrugInvocation.class);

	@Autowired
	private DrugServiceImpl drugService;

	/**
	 * 
	 * @Title: getList
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCityName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCityName", "");
		// String beginTime =
		// RequestUtil.getStrParamterAsDef(context.getRequest(), "beginTime",
		// "");
		// String endTime =
		// RequestUtil.getStrParamterAsDef(context.getRequest(), "endTime", "");
		// beginTime = DateTimeUtil.get_yyyyMMddHHmmss(beginTime);
		// endTime = DateTimeUtil.get_yyyyMMddHHmmss(endTime);
		Map<String, Object> params = new HashMap<String, Object>();
		String time = DateTimeUtil.getTodayChar6();
		params.put("time", time);
		params.put("fName", fName);
		params.put("fCityName", fCityName);
		try {
			Map<String, Object> map = drugService.getList(page, rows, params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("DrugInvocation getList happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: queryStock
	 * @Description: (入库查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult queryStock(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCityName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCityName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "beginTime", "");
		String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"endTime", "");

		beginTime = DateTimeUtil.get_yyyyMMddHHmmss(beginTime);
		endTime = DateTimeUtil.get_yyyyMMddHHmmss(endTime);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		params.put("fName", fName);
		params.put("fCityName", fCityName);
		try {
			Map<String, Object> map = drugService
					.queryStock(page, rows, params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("DrugInvocation queryStock happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getListByUser
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getListByUser(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCityName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCityName", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fCityName", fCityName);
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		params.put("userId", userInfo.getId());
		try {
			Map<String, Object> map = drugService.getListByUser(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("DrugInvocation getListByUser happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getListByUser_ExceptSelected
	 * @Description: (下单环节选择时，除去已选择的药品)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getListByUser_ExceptSelected(
			InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		int tax = RequestUtil.getIntParamterAsDef(context.getRequest(), "tax",
				-1);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCityName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCityName", "");
		String ids = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"ids", "");// 已选择的药品id
		String nums = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"nums", "");// 已选择的药品销售情况
		String fCompanyIds = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCompanyIds", "");// 客户所在公司
		String fDrugPrinterIds = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fDrugPrinterIds", "");// 客户经营分类
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fCityName", fCityName);
		/**
		 * 限制公司和经营分类 start
		 */
		params.put("fCompanyIds", fCompanyIds);
		params.put("fDrugPrinterIds", fDrugPrinterIds);
		/**
		 * 限制公司和经营分类 end
		 */
		if (null != ids && !"".equals(ids)) {
			String[] idArr = ids.split(",");
			String[] numArr = nums.split(",");

			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for (int i = 0; i < idArr.length; i++) {
				map.put(Integer.parseInt(idArr[i]), Integer.parseInt(numArr[i]));
			}
			params.put("selected", map);
		}
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		params.put("userId", userInfo.getId());
		try {
			Map<String, Object> map = drugService.getListByUser_ExceptSelected(
					page, rows, params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error(
					"DrugInvocation getListByUser_ExceptSelected happen execption.",
					ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 添加bean
	 * 
	 * @param context
	 * @return
	 */
	public InvocationResult save(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();

		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fSpecification = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fSpecification", "");
		String fBatchNumber = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fBatchNumber", "");
		String fExpiryDate = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fExpiryDate", "");
		int fNumber = RequestUtil.getIntParamter(context.getRequest(),
				"fNumber");
		String fPrice = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPrice", "");
		String fBuyingPrice = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fBuyingPrice", "");
		String fGongyePrice = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fGongyePrice", "");
		int fWareHouseId = RequestUtil.getIntParamter(context.getRequest(),
				"fWareHouseId");
		int fDrugOnlyId = RequestUtil.getIntParamter(context.getRequest(),
				"fDrugOnlyId");
		int fCompanyId = RequestUtil.getIntParamter(context.getRequest(),
				"fCompanyId");

		Drug bean = new Drug();
		bean.setfAddress(fAddress);
		bean.setfName(fName);
		bean.setfState(0 + "");
		bean.setfTime(DateTimeUtil.getTodayChar14());
		bean.setfSpecification(fSpecification);
		bean.setfBatchNumber(fBatchNumber);
		bean.setfExpiryDate(fExpiryDate);
		bean.setfNumber(fNumber);
		bean.setfNumberBak(fNumber);
		bean.setfPrice(Double.parseDouble(fPrice));
		bean.setfWareHouseId(fWareHouseId);
		bean.setfDrugOnlyId(fDrugOnlyId);
		bean.setfBuyingPrice(Double.parseDouble(fBuyingPrice));
		bean.setfGongyePrice(Double.parseDouble(fGongyePrice));
		bean.setfCompanyId(fCompanyId);
		try {
			drugService.save(bean);
		} catch (Exception ex) {
			logger.error("DrugInvocation save happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 编辑
	 * 
	 * @param context
	 * @return
	 */
	public InvocationResult edit(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();

		int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId",
				0);
		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fSpecification = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fSpecification", "");
		String fBatchNumber = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fBatchNumber", "");
		String fExpiryDate = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fExpiryDate", "");
		int fNumber = RequestUtil.getIntParamter(context.getRequest(),
				"fNumber");
		String fPrice = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPrice", "");
		String fBuyingPrice = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fBuyingPrice", "");
		String fGongyePrice = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fGongyePrice", "");
		int fWareHouseId = RequestUtil.getIntParamter(context.getRequest(),
				"fWareHouseId");
		int fDrugOnlyId = RequestUtil.getIntParamter(context.getRequest(),
				"fDrugOnlyId");
		int fCompanyId = RequestUtil.getIntParamter(context.getRequest(),
				"fCompanyId");

		Drug bean = new Drug();
		bean.setfAddress(fAddress);
		bean.setfName(fName);
		bean.setfState(0 + "");
		bean.setfTime(DateTimeUtil.getTodayChar14());
		bean.setfSpecification(fSpecification);
		bean.setfBatchNumber(fBatchNumber);
		bean.setfExpiryDate(fExpiryDate);
		bean.setfNumber(fNumber);
		bean.setfPrice(Double.parseDouble(fPrice));
		bean.setfBuyingPrice(Double.parseDouble(fBuyingPrice));
		bean.setfGongyePrice(Double.parseDouble(fGongyePrice));
		bean.setfWareHouseId(fWareHouseId);
		bean.setfId(fId);
		bean.setfDrugOnlyId(fDrugOnlyId);
		bean.setfCompanyId(fCompanyId);
		try {
			drugService.edit(bean);
		} catch (Exception ex) {
			logger.error("DrugInvocation edit happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 删除（软删除）
	 * 
	 * @param context
	 * @return
	 */
	public InvocationResult delete(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();

		int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId",
				0);
		Drug bean = new Drug();
		bean.setfState(1 + "");
		bean.setfId(fId);
		try {
			drugService.editState(bean);
		} catch (Exception ex) {
			logger.error("DrugInvocation delete happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: userDrug 业务员和drugOnly表的关联关系保存
	 * @Description: (药品权限)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult userDrug(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int userId = RequestUtil.getIntParamter(context.getRequest(), "userId");
		String drugIds = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drugIds", "");

		try {
			this.drugService.userDrug(userId, drugIds);// 先删除该用户权限药品再添加
		} catch (Exception ex) {
			logger.error("DrugInvocation userDrug happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: userDrugOnly_setPrice
	 * @Description: (业务员设置进货价和零售价)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult userDrugOnly_setPrice(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int userId = RequestUtil.getIntParamter(context.getRequest(), "userId");// 业务员id
		int drugOnlyId = RequestUtil.getIntParamter(context.getRequest(),
				"drugOnlyId");// 药品id
		Double fSupplyPrice = Double
				.parseDouble(RequestUtil.getStrParamterAsDef(
						context.getRequest(), "fSupplyPrice", "0"));// 供货价
		Double fRetailPrice = Double
				.parseDouble(RequestUtil.getStrParamterAsDef(
						context.getRequest(), "fRetailPrice", "0"));// 零售价

		try {
			this.drugService.userDrugOnly_setPrice(userId, drugOnlyId,
					fSupplyPrice, fRetailPrice);// 先删除再添加
		} catch (Exception ex) {
			logger.error(
					"DrugInvocation userDrugOnly_setPrice happen execption.",
					ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: userDrugOnly_showPrice
	 * @Description: (显示业务员设置进货价和零售价)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult userDrugOnly_showPrice(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int userId = RequestUtil.getIntParamter(context.getRequest(), "userId");// 业务员id
		int drugOnlyId = RequestUtil.getIntParamter(context.getRequest(),
				"drugOnlyId");// 药品id

		try {
			Map<String, Object> map = this.drugService.userDrugOnly_showPrice(
					userId, drugOnlyId);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error(
					"DrugInvocation userDrugOnly_showPrice happen execption.",
					ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: checkExpireDate
	 * @Description: (同种药品选择效期最近的卖，强制性限制 )
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult checkExpireDate(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int drugId = RequestUtil.getIntParamter(context.getRequest(), "fId");
		int fDrugOnlyId = RequestUtil.getIntParamter(context.getRequest(),
				"fDrugOnlyId");
		int tax = RequestUtil.getIntParamterAsDef(context.getRequest(), "tax",
				-1);
		String ids = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"ids", "");// 已选择的药品id
		String nums = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"nums", "");// 已选择的药品销售情况
		String fCompanyIds = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCompanyIds", null);// 客户所在公司
		String fDrugPrinterIds = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fDrugPrinterIds", null);// 客户经营分类
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("time", DateTimeUtil.getTodayChar6());
		if (tax != 0) {
			params.put("fCompanyIds", fCompanyIds);
			params.put("fDrugPrinterIds", fDrugPrinterIds);
		}
		System.out.println("ids:" + ids);
		System.out.println("nums:" + nums);
		Map<Integer, Integer> map = null;
		if (null != ids && !"".equals(ids)) {
			map = new HashMap<Integer, Integer>();
			String[] idArr = ids.split(",");
			String[] numArr = nums.split(",");

			for (int i = 0; i < idArr.length; i++) {
				map.put(Integer.parseInt(idArr[i]), Integer.parseInt(numArr[i]));
			}
		}
		try {

			// 判断最近效期的药品是否销售完了，也许效期有多个，已经选择多个，要判断已经选择的多个是否已经销售完了,这里采用的方式是：隐藏已经销售完的效期，只对比未销售完的最近效期
			List<Drug> data = drugService.getListByUser_ExceptSelected(params);// 比较已经选择的药品库存是否已被销完
			List<Integer> idList = null;// 需要排除在外的药品id
			ListIterator<Drug> drugL = data.listIterator();
			Drug bean = null;
			if (map != null) {
				idList = new ArrayList<Integer>();// 需要排除在外的药品id
				for (Entry<Integer, Integer> en : map.entrySet()) {
					while (drugL.hasNext()) {// 如果和已选择的药品id相同并且库存和销量一致则删除该药（隐藏药品）,这里收集需要排除掉的药品id
						bean = drugL.next();
						if (bean.getfId().intValue() == en.getKey().intValue()
								&& bean.getfNumber().intValue() == en
										.getValue().intValue()) {
							idList.add(bean.getfId());
							continue;
						}
					}
				}
			}

			System.out.println("idList:" + idList);
			params.put("fDrugOnlyId", fDrugOnlyId);
			params.put("idList", idList);
			List<Drug> list = this.drugService.getBeanByDrugOnlyId(params);// 根据药品id获取此药品多个库存信息
			System.out.println("list:" + list.size());
			if (drugId == list.get(0).getfId().intValue()) {// 如果选择的效期是未选择的最近效期，也就是排序后的数据的第一个则可以添加，不然则报不可以
				// 判断未被选择销售完最近效期的药是否已被选择，并且正好选择完库存 start
				int drugIdSel;// 已选择的药品库存id
				int fSalesNumber;// 已选择的药品销售数量
				if (map != null) {
					for (Entry<Integer, Integer> en : map.entrySet()) {
						drugIdSel = en.getKey();
						fSalesNumber = en.getValue();
						if (drugId == drugIdSel
								&& fSalesNumber == list.get(0).getfNumber()) {// 该药品已被选择,并且销售数量和库存一致（表明最近效期的药品已经卖完了，可以点其他效期的）
							result.setRetObj("success");
							break;
						}
					}

				}
				// 判断最近效期的药是否已被选择，并且正好选择完库存 end

			} else {
				// 如果未被选择销售完最近的效期的药品已经选择完成了则可以继续选择该药品其他效期的
				result.setRetCode(ISystemConstants.SYS_FAILED);
				result.setRetObj("fail");
			}
		} catch (Exception ex) {
			logger.error("DrugInvocation checkExpireDate happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

}
