package com.qm.omp.business.invocation.drug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SequenceGenerator;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.dao.drug.IDrugDao;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.pojo.drug.Order;
import com.qm.omp.business.pojo.drug.OrderDetail;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderDetailServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderServiceImpl;
import com.qm.omp.business.service.impl.drug.ZgYWYServiceImpl;
import com.qm.omp.business.util.DateTimeUtil;

/**
 * @ClassName: OrderInvocation
 * @Description: 发货单管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_order")
public class OrderInvocation implements BaseInvocation {
	private Logger logger = Logger.getLogger(OrderInvocation.class);

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private OrderDetailServiceImpl orderDetailService;

	@Autowired
	private SequenceGenerator orderSeqGen;// 订单号生成器

	@Autowired
	private DrugServiceImpl drugService;

	@Autowired
	private ZgYWYServiceImpl zgYWYService;

	@Autowired
	private IDrugDao drugDao;

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
		try {
			Map<String, Object> map = orderService.getList(fName, page, rows);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getList_ywy
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_ywy(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("userId", userInfo.getId());
		try {
			Map<String, Object> map = orderService.getList_ywy(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_ywy happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: getList_zy
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_zy(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("userId", userInfo.getId());
		try {
			Map<String, Object> map = orderService.getList_zy(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_zy happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getList_zg
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_zg(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "beginTime", "");
		String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"endTime", "");
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		Map<String, Object> params = new HashMap<String, Object>();
		Integer fZhuGuanId = userInfo.getId();
		params.put("fYwyName", fName);
		params.put("fZhuGuanId", fZhuGuanId);
		params.put("beginTime", com.qm.omp.business.util.DateTimeUtil
				.get_yyyyMMddHHmmss(beginTime));
		params.put("endTime", com.qm.omp.business.util.DateTimeUtil
				.get_yyyyMMddHHmmss(endTime));
		try {
			Map<String, Object> map = this.orderService.getList_zg(params,
					page, rows, userInfo.getfUserRoleRel());
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_ywy happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 主管提成总额
	 * 
	 * @Title: getList_zg
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getTc_Total(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "beginTime", "");
		String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"endTime", "");
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		Map<String, Object> params = new HashMap<String, Object>();
		Integer fZhuGuanId = userInfo.getId();
		params.put("fYwyName", fName);
		params.put("fZhuGuanId", fZhuGuanId);
		params.put("beginTime", com.qm.omp.business.util.DateTimeUtil
				.get_yyyyMMddHHmmss(beginTime));
		params.put("endTime", com.qm.omp.business.util.DateTimeUtil
				.get_yyyyMMddHHmmss(endTime));
		try {
			Map<String, Object> map = this.orderService.getTc_Total(params,
					userInfo.getfUserRoleRel());
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getTc_Total happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getList_policy 政策报单审批页面
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_policy(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCustomName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCustomName", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fCustomName", fCustomName);
		try {
			Map<String, Object> map = orderService.getList_policy(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_policy happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getList_cw
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_cw(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		// 默认状态1，未审核
		String fState = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fState", "");
		// 付款情况
		String fPaymentState = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPaymentState", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fCustomName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCustomName", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fCustomName", fCustomName);
		params.put("fState", fState);
		params.put("fPaymentState", fPaymentState);
		try {
			Map<String, Object> map = orderService.getList_cw(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_cw happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getList_fh
	 * @Description: (分页查询)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult getList_fh(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(),
				"rows", 50);
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fPaymentState = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPaymentState", "");
		String fExamine = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fExamine", "");
		String fCustomName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fCustomName", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fCustomName", fCustomName);
		params.put("fPaymentState", fPaymentState);
		params.put("fExamine", fExamine);
		try {
			Map<String, Object> map = orderService.getList_fh(page, rows,
					params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation getList_fh happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 添加订单
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult save(InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String orderId = this.orderSeqGen.next();
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPhone", "");
		String drug = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drug", "");
		String customer = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"customer", "");
		String fTownship = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fTownship", "");
		String fYaofang = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fYaofang", "");
		// 是否为直营：1表示直营，非1表示正常订单，直接采用药品管理里面的供货价代替 库存管理里面的价格 计算
		String isZy = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"isZy", "0");
		Integer fCustomerId = null;
		if (!"".equals(customer)) {
			JSONArray cArr = JSONArray.parseArray(customer);
			fCustomerId = cArr.getJSONObject(0).getInteger("fId");
		}
		Order bean = new Order();
		bean.setfName(fName);
		bean.setfState("0");// 保存未提交给财务状态
		bean.setfIsEditPrice("0");// 订单未被修改价格
		bean.setfTax(fTax);
		bean.setfAddress(fAddress);
		bean.setfPhone(fPhone);
		bean.setfCustomerId(fCustomerId);
		bean.setfId(orderId);
		bean.setfTownship(fTownship);
		bean.setfYaofang(fYaofang);
		if ("1".equals(isZy)) {// IsPolicy 为3时 :直营订单，为4时表示：直营政策订单
			bean.setIsPolicy("3");
		} else {			
			bean.setIsPolicy("0");
		}
		JSONArray dArr = JSONArray.parseArray(drug);// 药品

		// 判断药品库存是否够下单所用 start
		JSONObject drugObj = null;
		String drugName = null;
		int fSalesNumber22 = 0;
		int drugId;
		Drug drug1 = null;
		boolean flag = false;
		for (int i = 0; i < dArr.size(); i++) {
			drugObj = dArr.getJSONObject(i);
			drugName = drugObj.getString("fName");
			fSalesNumber22 = drugObj.getIntValue("fSalesNumber");
			drugId = drugObj.getIntValue("fId");
			drug1 = this.drugService.getBean(drugId);
			if (drug1.getfNumber() < fSalesNumber22) {// 库存不足
				flag = true;
				break;
			}
		}
		if (flag) {// 库存不足,添加失败，并返回那个药品库存不足
			result.setRetCode(ISystemConstants.SYS_FAILED);
			result.setRetObj(drugName + "的库存不足" + fSalesNumber22);
			return result;
		}
		// 判断药品库存是否够下单所用 end

		/*
		 * {field : 'fId',align:'center',checkbox : true}, {field :
		 * 'fName',title : '药品名称',width : 100,align:'center'}, {field :
		 * 'fSpecification',title : '药品规格',width : 100,align:'center'}, {field :
		 * 'fBatchNumber',title : '批号',width :100,align:'center'}, {field :
		 * 'fPrice',title : '价格',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fKaiPiaoPrice',title : '开票价',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fSalesNumber',title : '出售数量',width :100,align:'center'}
		 */
		JSONObject obj = null;
		Double fBuyingPrice = null;// 进货价
		Double fPrice = null;// 单价
		Double fGongyePrice = null;// 工业票价
		Double fKaiPiaoPrice = null;// 开票价
		Integer fSalesNumber = null;// 出售数量
		// 过票费
		Double fGuoJiFei = 0d;
		Double fGuoJiFeiTotal = 0d;
		// 返点
		Double fFanDian = 0d;
		Double fFanDianTotal = 0d;
		// 高开费
		Double fGaoKaiFei = 0d;
		Double fGaoKaiFeiTotal = 0d;
		// 通过公式计算后的价格
		Double fMoney = 0d;
		Double fMoneyTotal = 0d;

		// 总金额:销量*单价
		Double fMoney_noTax = 0d;
		Double fMoney_noTaxTotal = 0d;

		// 销量*进货价
		Double fMoney_buyingPrice = 0d;
		Double fMoney_buyingPriceTotal = 0d;

		// 小区提成
		Double fXqTc = 0d;
		Double fXqTcTotal = 0d;
		// 订单小区总提成
		Double fXqTcTotal_Money = 0d;

		// 大区提成
		Double fDqTc = 0d;
		Double fDqTcTotal = 0d;
		// 订单大区总提成
		Double fDqTcTotal_Money = 0d;

		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
													// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);
				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				
				fGongyePrice = obj.getDouble("fGongyePrice");// 工业票价
				fBuyingPrice = obj.getDouble("fBuyingPrice");// 进货价
				fSalesNumber = obj.getInteger("fSalesNumber");// 销售数量
				fXqTc = obj.getDouble("fXqTc");// 小区提成
				fDqTc = obj.getDouble("fDqTc");// 大区提成
				// 单个药品返点= 数量*单价 * 0.07
				// fFanDian = fPrice.doubleValue() * fSalesNumber.intValue() *
				// 0.07;
				fFanDian = 0d;
				// 主订单合计返点金额
				fFanDianTotal += fFanDian;
				// 单个药品合计金额
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue();
				// 主订单合计金额
				fMoneyTotal += fMoney;

				// 金额
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计金额
				fMoney_noTaxTotal += fMoney_noTax;

				// 金额
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计金额
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				// 小区提成
				fXqTcTotal = fXqTc.doubleValue() * fSalesNumber.intValue();
				// 大区提成
				fDqTcTotal = fDqTc.doubleValue() * fSalesNumber.intValue();
				// 订单总提成
				fXqTcTotal_Money += fXqTcTotal;
				fDqTcTotal_Money += fDqTcTotal;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(fSalesNumber);
									
				// 单价
				od.setfPrice(fPrice);
				od.setfGongyePrice(obj.getDouble("fGongyePrice"));
				od.setfFanDian(fFanDian);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				// 提成
				od.setfXqTc(fXqTc);
				od.setfXqTc_Money(fXqTcTotal);

				od.setfDqTc(fDqTc);
				od.setfDqTc_Money(fDqTcTotal);

				ods.add(od);
			}

		} else {// 含税
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);
				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fBuyingPrice = obj.getDouble("fBuyingPrice");
				fGongyePrice = obj.getDouble("fGongyePrice");
				fKaiPiaoPrice = obj.getDouble("fKaiPiaoPrice");
				fSalesNumber = obj.getInteger("fSalesNumber");
				fXqTc = obj.getDouble("fXqTc");// 小区提成
				fDqTc = obj.getDouble("fDqTc");// 大区提成
				// 过票费= 开票价 * 数量 * 0.03
				fGuoJiFei = fKaiPiaoPrice.doubleValue()
						* fSalesNumber.intValue() * 0.03;
				fGuoJiFeiTotal += fGuoJiFei;
				// 高开费 = （开票价-工业票价）* 数量 * 0.17
				fGaoKaiFei = (fKaiPiaoPrice.doubleValue() - fPrice
						.doubleValue()) * fSalesNumber.intValue() * 0.17;
				fGaoKaiFeiTotal += fGaoKaiFei;
				// 单个药品合计
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						+ fGuoJiFei + fGaoKaiFei;
				// 合计
				fMoneyTotal += fMoney;

				// 单个药品合计
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计
				fMoney_noTaxTotal += fMoney_noTax;

				// 单个药品合计
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				// 小区提成
				fXqTcTotal = fXqTc.doubleValue() * fSalesNumber.intValue();
				// 大区提成
				fDqTcTotal = fDqTc.doubleValue() * fSalesNumber.intValue();
				// 订单总提成
				fXqTcTotal_Money += fXqTcTotal;
				fDqTcTotal_Money += fDqTcTotal;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(obj.getInteger("fSalesNumber"));
									
				// 单价
				od.setfPrice(fPrice);
				od.setfKaiPiaoPrice(obj.getDouble("fKaiPiaoPrice"));
				od.setfGongyePrice(fGongyePrice);
				od.setfGuoJiFei(fGuoJiFei);
				od.setfGaoKaiFei(fGaoKaiFei);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				// 提成
				od.setfXqTc(fXqTc);
				od.setfXqTc_Money(fXqTcTotal);

				od.setfDqTc(fDqTc);
				od.setfDqTc_Money(fDqTcTotal);
				ods.add(od);
			}
		}

		bean.setfMoney(fMoneyTotal);
		bean.setfMoney_noTax(fMoney_noTaxTotal);// 数量*单价
		bean.setfMoney_buyingPrice(fMoney_buyingPriceTotal);
		bean.setfGuoJiFei(fGuoJiFeiTotal);
		bean.setfGaoKaiFei(fGaoKaiFeiTotal);
		bean.setfFanDian(fFanDianTotal);
		bean.setfTime(DateTimeUtil.getTodayChar14());
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfSaleUserId(userInfo.getId());
		bean.setfSaleUserName(userInfo.getfUserCode());

		// 提成
		bean.setfXqTc_Money(fXqTcTotal_Money);
		bean.setfDqTc_Money(fDqTcTotal_Money);

		try {
			orderService.save(bean);

			for (OrderDetail b : ods) {

				this.orderDetailService.save(b);
			}
		} catch (Exception ex) {
			logger.error("OrderInvocation save happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 政策报单
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult save_policy(InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String orderId = this.orderSeqGen.next();
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPhone", "");
		String drug = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drug", "");
		String customer = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"customer", "");
		String fTownship = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fTownship", "");
		String fYaofang = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fYaofang", "");
		String fPolicyIntro = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPolicyIntro", "");

		// 是否为直营：1表示直营，非1表示正常订单，直接采用药品管理里面的供货价代替 库存管理里面的价格 计算
		String isZy = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"isZy", "0");
		Integer fCustomerId = null;
		if (!"".equals(customer)) {
			JSONArray cArr = JSONArray.parseArray(customer);
			fCustomerId = cArr.getJSONObject(0).getInteger("fId");
		}
		Order bean = new Order();
		bean.setfName(fName);
		bean.setfState(0 + "");// 保存未提交给财务状态
		bean.setfIsEditPrice("0");// 订单未被修改价格
		bean.setfTax(fTax);
		bean.setfAddress(fAddress);
		bean.setfPhone(fPhone);
		bean.setfCustomerId(fCustomerId);
		bean.setfId(orderId);
		bean.setfTownship(fTownship);
		bean.setfYaofang(fYaofang);
		bean.setfPolicyIntro(fPolicyIntro);
		// IsPolicy 为3时 :直营订单，为4时表示：直营政策订单，0：普通订单，1：政策订单
		if ("1".equals(isZy)) {
			bean.setIsPolicy("4");
		} else {			
			bean.setIsPolicy("1");
		}
		JSONArray dArr = JSONArray.parseArray(drug);// 药品

		// 判断药品库存是否够下单所用 start
		JSONObject drugObj = null;
		String drugName = null;
		int fSalesNumber22 = 0;
		int drugId;
		Drug drug1 = null;
		boolean flag = false;
		for (int i = 0; i < dArr.size(); i++) {
			drugObj = dArr.getJSONObject(i);
			drugName = drugObj.getString("fName");
			fSalesNumber22 = drugObj.getIntValue("fSalesNumber");
			drugId = drugObj.getIntValue("fId");
			drug1 = this.drugService.getBean(drugId);
			if (drug1.getfNumber() < fSalesNumber22) {// 库存不足
				flag = true;
				break;
			}
		}
		if (flag) {// 库存不足,添加失败，并返回那个药品库存不足
			result.setRetCode(ISystemConstants.SYS_FAILED);
			result.setRetObj(drugName + "的库存不足" + fSalesNumber22);
			return result;
		}
		// 判断药品库存是否够下单所用 end

		/*
		 * {field : 'fId',align:'center',checkbox : true}, {field :
		 * 'fName',title : '药品名称',width : 100,align:'center'}, {field :
		 * 'fSpecification',title : '药品规格',width : 100,align:'center'}, {field :
		 * 'fBatchNumber',title : '批号',width :100,align:'center'}, {field :
		 * 'fPrice',title : '价格',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fKaiPiaoPrice',title : '开票价',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fSalesNumber',title : '出售数量',width :100,align:'center'}
		 */
		JSONObject obj = null;
		Double fBuyingPrice = null;// 进货价
		Double fPrice = null;// 单价
		Double fGongyePrice = null;// 工业票价
		Double fKaiPiaoPrice = null;// 开票价
		Integer fSalesNumber = null;// 出售数量
		// 过票费
		Double fGuoJiFei = 0d;
		Double fGuoJiFeiTotal = 0d;
		// 返点
		Double fFanDian = 0d;
		Double fFanDianTotal = 0d;
		// 高开费
		Double fGaoKaiFei = 0d;
		Double fGaoKaiFeiTotal = 0d;
		// 通过公式计算后的价格
		Double fMoney = 0d;
		Double fMoneyTotal = 0d;

		// 总金额:销量*单价
		Double fMoney_noTax = 0d;
		Double fMoney_noTaxTotal = 0d;

		// 销量*进货价
		Double fMoney_buyingPrice = 0d;
		Double fMoney_buyingPriceTotal = 0d;

		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
													// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);
				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fBuyingPrice = obj.getDouble("fBuyingPrice");// 进货价
				fSalesNumber = obj.getInteger("fSalesNumber");// 销售数量
				// 单个药品返点= 数量*单价 * 0.07
				// fFanDian = fPrice.doubleValue() * fSalesNumber.intValue() *
				// 0.07;
				fFanDian = 0d;
				// 主订单合计返点金额
				fFanDianTotal += fFanDian;
				// 单个药品合计金额
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						- fFanDian;
				// 主订单合计金额
				fMoneyTotal += fMoney;

				// 金额
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计金额
				fMoney_noTaxTotal += fMoney_noTax;

				// 金额
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计金额
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(fSalesNumber);
				od.setfPrice(fPrice);
				od.setfFanDian(fFanDian);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				ods.add(od);
			}

		} else {// 含税
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);

				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fBuyingPrice = obj.getDouble("fBuyingPrice");
				fGongyePrice = obj.getDouble("fGongyePrice");// 工业票价
				fKaiPiaoPrice = obj.getDouble("fKaiPiaoPrice");
				fSalesNumber = obj.getInteger("fSalesNumber");
				// 过票费= 开票价 * 数量 * 0.03
				fGuoJiFei = fKaiPiaoPrice.doubleValue()
						* fSalesNumber.intValue() * 0.03;
				fGuoJiFeiTotal += fGuoJiFei;
				// 高开费 = （开票价-工业票价）* 数量 * 0.17
				fGaoKaiFei = (fKaiPiaoPrice.doubleValue() - fPrice
						.doubleValue()) * fSalesNumber.intValue() * 0.17;
				fGaoKaiFeiTotal += fGaoKaiFei;
				// 单个药品合计
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						+ fGuoJiFei + fGaoKaiFei;
				// 合计
				fMoneyTotal += fMoney;

				// 单个药品合计
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计
				fMoney_noTaxTotal += fMoney_noTax;

				// 单个药品合计
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(obj.getInteger("fSalesNumber"));
				od.setfPrice(obj.getDouble("fPrice"));
				od.setfKaiPiaoPrice(obj.getDouble("fKaiPiaoPrice"));
				od.setfGongyePrice(fGongyePrice);
				od.setfGuoJiFei(fGuoJiFei);
				od.setfGaoKaiFei(fGaoKaiFei);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				ods.add(od);
			}
		}

		bean.setfMoney(fMoneyTotal);
		bean.setfMoney_noTax(fMoney_noTaxTotal);// 数量*单价
		bean.setfMoney_buyingPrice(fMoney_buyingPriceTotal);
		bean.setfGuoJiFei(fGuoJiFeiTotal);
		bean.setfGaoKaiFei(fGaoKaiFeiTotal);
		bean.setfFanDian(fFanDianTotal);
		bean.setfTime(DateTimeUtil.getTodayChar14());
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfSaleUserId(userInfo.getId());
		bean.setfSaleUserName(userInfo.getfUserCode());

		try {
			orderService.save(bean);

			for (OrderDetail b : ods) {

				this.orderDetailService.save(b);
			}
		} catch (Exception ex) {
			logger.error("OrderInvocation save happen execption.", ex);
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
	public synchronized InvocationResult editOrder(InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String orderId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPhone", "");
		String drug = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drug", "");
		String customer = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"customer", "");
		String fTownship = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fTownship", "");
		String fYaofang = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fYaofang", "");
		// 是否为直营：1表示直营，非1表示正常订单，直接采用药品管理里面的供货价代替 库存管理里面的价格 计算
		String isZy = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"isZy", "0");
		Integer fCustomerId = null;
		if (!"".equals(customer)) {
			JSONArray cArr = JSONArray.parseArray(customer);
			fCustomerId = cArr.getJSONObject(0).getInteger("fId");
		}
		Order bean = new Order();
		bean.setfName(fName);
		bean.setfState(0 + "");// 保存未提交给财务状态
		bean.setfTax(fTax);
		bean.setfAddress(fAddress);
		bean.setfPhone(fPhone);
		bean.setfCustomerId(fCustomerId);
		bean.setfId(orderId);
		bean.setfTownship(fTownship);
		bean.setfYaofang(fYaofang);
		JSONArray dArr = JSONArray.parseArray(drug);// 药品

		// 判断药品库存是否够下单所用 start
		JSONObject drugObj = null;
		String drugName = null;
		int fSalesNumber22 = 0;
		int drugId;
		Drug drug1 = null;
		boolean flag = false;
		for (int i = 0; i < dArr.size(); i++) {
			drugObj = dArr.getJSONObject(i);
			drugName = drugObj.getString("fName");
			fSalesNumber22 = drugObj.getIntValue("fSalesNumber");
			drugId = drugObj.getIntValue("fId");
			drug1 = this.drugService.getBean(drugId);
			if (drug1 == null || drug1.getfNumber() < fSalesNumber22) {// 库存不足
				flag = true;
				break;
			}
		}
		if (flag) {// 库存不足,添加失败，并返回那个药品库存不足
			result.setRetCode(ISystemConstants.SYS_FAILED);
			result.setRetObj(drugName + "的库存不足" + fSalesNumber22);
			return result;
		}
		// 判断药品库存是否够下单所用 end

		/*
		 * {field : 'fId',align:'center',checkbox : true}, {field :
		 * 'fName',title : '药品名称',width : 100,align:'center'}, {field :
		 * 'fSpecification',title : '药品规格',width : 100,align:'center'}, {field :
		 * 'fBatchNumber',title : '批号',width :100,align:'center'}, {field :
		 * 'fPrice',title : '价格',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fKaiPiaoPrice',title : '开票价',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fSalesNumber',title : '出售数量',width :100,align:'center'}
		 */
		JSONObject obj = null;
		Double fBuyingPrice = null;// 进货价
		Double fPrice = null;// 单价
		Double fGongyePrice = null;// 工业票价
		Double fKaiPiaoPrice = null;// 开票价
		Integer fSalesNumber = null;// 出售数量
		// 过票费
		Double fGuoJiFei = 0d;
		Double fGuoJiFeiTotal = 0d;
		// 返点
		Double fFanDian = 0d;
		Double fFanDianTotal = 0d;
		// 高开费
		Double fGaoKaiFei = 0d;
		Double fGaoKaiFeiTotal = 0d;
		// 通过公式计算后的价格
		Double fMoney = 0d;
		Double fMoneyTotal = 0d;

		// 总金额:销量*单价
		Double fMoney_noTax = 0d;
		Double fMoney_noTaxTotal = 0d;

		// 销量*进货价
		Double fMoney_buyingPrice = 0d;
		Double fMoney_buyingPriceTotal = 0d;

		// 小区提成
		Double fXqTc = 0d;
		Double fXqTcTotal = 0d;
		// 订单小区总提成
		Double fXqTcTotal_Money = 0d;

		// 大区提成
		Double fDqTc = 0d;
		Double fDqTcTotal = 0d;
		// 订单大区总提成
		Double fDqTcTotal_Money = 0d;

		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
													// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);
				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fGongyePrice = obj.getDouble("fGongyePrice");// 工业票价
				fBuyingPrice = obj.getDouble("fBuyingPrice");// 进货价
				fSalesNumber = obj.getInteger("fSalesNumber");// 销售数量
				fXqTc = obj.getDouble("fXqTc");// 小区提成
				fDqTc = obj.getDouble("fDqTc");// 大区提成
				// 单个药品返点= 数量*单价 * 0.07
				// fFanDian = fPrice.doubleValue() * fSalesNumber.intValue() *
				// 0.07;
				fFanDian = 0d;
				// 主订单合计返点金额
				fFanDianTotal += fFanDian;
				// 单个药品合计金额
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue();
				// 主订单合计金额
				fMoneyTotal += fMoney;

				// 金额
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计金额
				fMoney_noTaxTotal += fMoney_noTax;

				// 金额
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计金额
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				// 小区提成
				fXqTcTotal = fXqTc.doubleValue() * fSalesNumber.intValue();
				// 大区提成
				fDqTcTotal = fDqTc.doubleValue() * fSalesNumber.intValue();
				// 订单总提成
				fXqTcTotal_Money += fXqTcTotal;
				fDqTcTotal_Money += fDqTcTotal;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(fSalesNumber);
				od.setfPrice(fPrice);
				od.setfGongyePrice(obj.getDouble("fGongyePrice"));
				od.setfFanDian(fFanDian);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				// 提成
				od.setfXqTc(fXqTc);
				od.setfXqTc_Money(fXqTcTotal);

				od.setfDqTc(fDqTc);
				od.setfDqTc_Money(fDqTcTotal);
				ods.add(od);
			}

		} else {// 含税
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);
				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fGongyePrice = obj.getDouble("fGongyePrice");
				fBuyingPrice = obj.getDouble("fBuyingPrice");
				fKaiPiaoPrice = obj.getDouble("fKaiPiaoPrice");
				fSalesNumber = obj.getInteger("fSalesNumber");
				fXqTc = obj.getDouble("fXqTc");// 小区提成
				fDqTc = obj.getDouble("fDqTc");// 大区提成
				// 过票费= 开票价 * 数量 * 0.03
				fGuoJiFei = fKaiPiaoPrice.doubleValue()
						* fSalesNumber.intValue() * 0.03;
				fGuoJiFeiTotal += fGuoJiFei;
				// 高开费 = （开票价-工业票价）* 数量 * 0.17
				fGaoKaiFei = (fKaiPiaoPrice.doubleValue() - fPrice
						.doubleValue()) * fSalesNumber.intValue() * 0.17;
				fGaoKaiFeiTotal += fGaoKaiFei;
				// 单个药品合计
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						+ fGuoJiFei + fGaoKaiFei;
				// 合计
				fMoneyTotal += fMoney;

				// 单个药品合计
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计
				fMoney_noTaxTotal += fMoney_noTax;

				// 单个药品合计
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				// 小区提成
				fXqTcTotal = fXqTc.doubleValue() * fSalesNumber.intValue();
				// 大区提成
				fDqTcTotal = fDqTc.doubleValue() * fSalesNumber.intValue();
				// 订单总提成
				fXqTcTotal_Money += fXqTcTotal;
				fDqTcTotal_Money += fDqTcTotal;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(obj.getInteger("fSalesNumber"));
				od.setfPrice(fPrice);
				od.setfKaiPiaoPrice(obj.getDouble("fKaiPiaoPrice"));
				od.setfGongyePrice(fGongyePrice);
				od.setfGuoJiFei(fGuoJiFei);
				od.setfGaoKaiFei(fGaoKaiFei);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				// 提成
				od.setfXqTc(fXqTc);
				od.setfXqTc_Money(fXqTcTotal);

				od.setfDqTc(fDqTc);
				od.setfDqTc_Money(fDqTcTotal);
				ods.add(od);
			}
		}

		bean.setfMoney(fMoneyTotal);
		bean.setfMoney_noTax(fMoney_noTaxTotal);// 数量*单价
		bean.setfMoney_buyingPrice(fMoney_buyingPriceTotal);
		bean.setfGuoJiFei(fGuoJiFeiTotal);
		bean.setfGaoKaiFei(fGaoKaiFeiTotal);
		bean.setfFanDian(fFanDianTotal);
		bean.setfTime(DateTimeUtil.getTodayChar14());
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfSaleUserId(userInfo.getId());
		bean.setfSaleUserName(userInfo.getfUserCode());

		// 提成
		bean.setfXqTc_Money(fXqTcTotal_Money);
		bean.setfDqTc_Money(fDqTcTotal_Money);
		try {
			/**
			 * 清空订单详细，再重新生成，达到修改的目的
			 */
			// 退单恢复订单内所有药品的库存
			// List<OrderDetail> detailList = this.orderDetailService
			// .getListByOrderId(orderId);
			// for (OrderDetail detail : detailList) {
			// this.drugService.minusNumber(detail.getfDrugId(),
			// detail.getfNumber());
			// }
			this.orderDetailService.deleteBeanByOrderId(bean.getfId());
			for (OrderDetail b : ods) {
				// this.drugService.minusNumber(b.getfDrugId(), -b.getfNumber()
				// .intValue());// 减少库存
				this.orderDetailService.save(b);
			}
			orderService.edit(bean);
		} catch (Exception ex) {
			logger.error("OrderInvocation save happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 编辑政策报单
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult editOrder_policy(
			InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String orderId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fAddress", "");
		String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fPhone", "");
		String drug = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drug", "");
		String customer = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"customer", "");
		String fTownship = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fTownship", "");
		String fYaofang = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fYaofang", "");
		String fPolicyIntro = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPolicyIntro", "");

		// 是否为直营：1表示直营，非1表示正常订单，直接采用药品管理里面的供货价代替 库存管理里面的价格 计算
		String isZy = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"isZy", "0");
		Integer fCustomerId = null;
		if (!"".equals(customer)) {
			JSONArray cArr = JSONArray.parseArray(customer);
			fCustomerId = cArr.getJSONObject(0).getInteger("fId");
		}
		Order bean = new Order();
		bean.setfName(fName);
		bean.setfState(0 + "");// 保存未提交给财务状态
		bean.setfTax(fTax);
		bean.setfAddress(fAddress);
		bean.setfPhone(fPhone);
		bean.setfCustomerId(fCustomerId);
		bean.setfId(orderId);
		bean.setfTownship(fTownship);
		bean.setfYaofang(fYaofang);
		bean.setfPolicyIntro(fPolicyIntro);
		JSONArray dArr = JSONArray.parseArray(drug);// 药品

		// 判断药品库存是否够下单所用 start
		JSONObject drugObj = null;
		String drugName = null;
		int fSalesNumber22 = 0;
		int drugId;
		Drug drug1 = null;
		boolean flag = false;
		for (int i = 0; i < dArr.size(); i++) {
			drugObj = dArr.getJSONObject(i);
			drugName = drugObj.getString("fName");
			fSalesNumber22 = drugObj.getIntValue("fSalesNumber");
			drugId = drugObj.getIntValue("fId");
			drug1 = this.drugService.getBean(drugId);
			if (drug1 == null || drug1.getfNumber() < fSalesNumber22) {// 库存不足
				flag = true;
				break;
			}
		}
		if (flag) {// 库存不足,添加失败，并返回那个药品库存不足
			result.setRetCode(ISystemConstants.SYS_FAILED);
			result.setRetObj(drugName + "的库存不足" + fSalesNumber22);
			return result;
		}
		// 判断药品库存是否够下单所用 end

		/*
		 * {field : 'fId',align:'center',checkbox : true}, {field :
		 * 'fName',title : '药品名称',width : 100,align:'center'}, {field :
		 * 'fSpecification',title : '药品规格',width : 100,align:'center'}, {field :
		 * 'fBatchNumber',title : '批号',width :100,align:'center'}, {field :
		 * 'fPrice',title : '价格',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fKaiPiaoPrice',title : '开票价',width :100,align:'center', formatter:
		 * function(value,row,index){ return value+"元"; } }, {field :
		 * 'fSalesNumber',title : '出售数量',width :100,align:'center'}
		 */
		JSONObject obj = null;
		Double fBuyingPrice = null;// 进货价
		Double fPrice = null;// 单价
		Double fKaiPiaoPrice = null;// 开票价
		Double fGongyePrice = null;// 工业票价
		Integer fSalesNumber = null;// 出售数量
		// 过票费
		Double fGuoJiFei = 0d;
		Double fGuoJiFeiTotal = 0d;
		// 返点
		Double fFanDian = 0d;
		Double fFanDianTotal = 0d;
		// 高开费
		Double fGaoKaiFei = 0d;
		Double fGaoKaiFeiTotal = 0d;
		// 通过公式计算后的价格
		Double fMoney = 0d;
		Double fMoneyTotal = 0d;

		// 总金额:销量*单价
		Double fMoney_noTax = 0d;
		Double fMoney_noTaxTotal = 0d;

		// 销量*进货价
		Double fMoney_buyingPrice = 0d;
		Double fMoney_buyingPriceTotal = 0d;

		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
													// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);

				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fBuyingPrice = obj.getDouble("fBuyingPrice");// 进货价
				fSalesNumber = obj.getInteger("fSalesNumber");// 销售数量
				// 单个药品返点= 数量*单价 * 0.07
				// fFanDian = fPrice.doubleValue() * fSalesNumber.intValue() *
				// 0.07;
				fFanDian = 0d;
				// 主订单合计返点金额
				fFanDianTotal += fFanDian;
				// 单个药品合计金额
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						- fFanDian;
				// 主订单合计金额
				fMoneyTotal += fMoney;

				// 金额
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计金额
				fMoney_noTaxTotal += fMoney_noTax;

				// 金额
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计金额
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(fSalesNumber);
				od.setfPrice(fPrice);
				od.setfFanDian(fFanDian);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				ods.add(od);
			}

		} else {// 含税
			for (int i = 0; i < dArr.size(); i++) {
				obj = dArr.getJSONObject(i);

				if ("1".equals(isZy)) {
					fPrice = obj.getDouble("fSupplyPrice");// 供货价
				} else {					
					fPrice = obj.getDouble("fPrice");// 单价
				}
				fBuyingPrice = obj.getDouble("fBuyingPrice");
				fKaiPiaoPrice = obj.getDouble("fKaiPiaoPrice");
				fGongyePrice = obj.getDouble("fGongyePrice");
				fSalesNumber = obj.getInteger("fSalesNumber");
				// 过票费= 开票价 * 数量 * 0.03
				fGuoJiFei = fKaiPiaoPrice.doubleValue()
						* fSalesNumber.intValue() * 0.03;
				fGuoJiFeiTotal += fGuoJiFei;
				// 高开费 = （开票价-工业票价）* 数量 * 0.17
				fGaoKaiFei = (fKaiPiaoPrice.doubleValue() - fPrice
						.doubleValue()) * fSalesNumber.intValue() * 0.17;
				fGaoKaiFeiTotal += fGaoKaiFei;
				// 单个药品合计
				fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
						+ fGuoJiFei + fGaoKaiFei;
				// 合计
				fMoneyTotal += fMoney;

				// 单个药品合计
				fMoney_noTax = fPrice.doubleValue() * fSalesNumber.intValue();
				// 合计
				fMoney_noTaxTotal += fMoney_noTax;

				// 单个药品合计
				fMoney_buyingPrice = fBuyingPrice.doubleValue()
						* fSalesNumber.intValue();
				// 合计
				fMoney_buyingPriceTotal += fMoney_buyingPrice;

				od = new OrderDetail();
				od.setfOrderId(orderId);
				od.setfDrugId(obj.getInteger("fId"));
				od.setfDrugName(obj.getString("fName"));
				od.setfNumber(obj.getInteger("fSalesNumber"));
				od.setfPrice(obj.getDouble("fPrice"));
				od.setfKaiPiaoPrice(obj.getDouble("fKaiPiaoPrice"));
				od.setfGongyePrice(fGongyePrice);
				od.setfGuoJiFei(fGuoJiFei);
				od.setfGaoKaiFei(fGaoKaiFei);
				od.setfMoney(fMoney);
				od.setfMoney_noTax(fMoney_noTax);
				od.setfMoney_buyingPrice(fMoney_buyingPrice);
				ods.add(od);
			}
		}

		bean.setfMoney(fMoneyTotal);
		bean.setfMoney_noTax(fMoney_noTaxTotal);// 数量*单价
		bean.setfMoney_buyingPrice(fMoney_buyingPriceTotal);
		bean.setfGuoJiFei(fGuoJiFeiTotal);
		bean.setfGaoKaiFei(fGaoKaiFeiTotal);
		bean.setfFanDian(fFanDianTotal);
		bean.setfTime(DateTimeUtil.getTodayChar14());
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfSaleUserId(userInfo.getId());
		bean.setfSaleUserName(userInfo.getfUserCode());

		try {
			// 退单恢复订单内所有药品的库存
			// List<OrderDetail> detailList = this.orderDetailService
			// .getListByOrderId(orderId);
			// for (OrderDetail detail : detailList) {
			// this.drugService.minusNumber(detail.getfDrugId(),
			// detail.getfNumber());
			// }
			this.orderDetailService.deleteBeanByOrderId(bean.getfId());
			for (OrderDetail b : ods) {
				// this.drugService.minusNumber(b.getfDrugId(), -b.getfNumber()
				// .intValue());// 减少库存
				this.orderDetailService.save(b);
			}
			orderService.edit(bean);
		} catch (Exception ex) {
			logger.error("OrderInvocation save happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 编辑订单，有提交备注、删除、编辑等功能
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult edit(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();

		String fId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		String fState = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fState", "");
		String fExpressId = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fExpressId", "");
		String fExpressName = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fExpressName", "");
		String fShipperIntro = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fShipperIntro", "");
		String fFinanceIntro = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fFinanceIntro", "");
		String fPaymentState = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPaymentState", "");
		Integer fPaymentSource = RequestUtil.getIntegerParamter(
				context.getRequest(), "fPaymentSource");
		String fPolicyIntro2 = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fPolicyIntro2", "");
		Order bean = new Order();
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfId(fId);
		bean.setfState(fState);
		// 状态：0，业务员未提交；1，提交财务审核，2，财务审核通过，3,已发货,4，退单
		if ("2".equals(fState)) {// 财务审核通过
			bean.setfFinanceTime(DateTimeUtil.getTodayChar14());
			bean.setfFinanceUserId(userInfo.getId());
			bean.setfFinanceUserName(userInfo.getfUserCode());
			bean.setfFinanceIntro(fFinanceIntro);
			bean.setfPaymentState(fPaymentState);
			bean.setfPaymentSource(fPaymentSource);
		} else if ("3".equals(fState)) {// 已发货
			bean.setfShipperTime(DateTimeUtil.getTodayChar14());
			bean.setfShipperUserId(userInfo.getId());
			bean.setfShipperUserName(userInfo.getfUserCode());

			bean.setfShipperIntro(fShipperIntro);
			bean.setfExpressId(fExpressId);
			bean.setfExpressName(fExpressName);
		} else if ("4".equals(fState)) {// 财务退单
			bean.setfFinanceTime(DateTimeUtil.getTodayChar14());
			bean.setfFinanceUserId(userInfo.getId());
			bean.setfFinanceUserName(userInfo.getfUserCode());

			// 退单恢复订单内所有药品的库存
			List<OrderDetail> list = this.orderDetailService
					.getListByOrderId(fId);
			Drug stock;
			for (OrderDetail detail : list) {
				// 有可能是结转前的库存，恢复保持数据一致性
				// 大部分情况下都是当月库存，只有跨月才会走下面的if
				this.drugService.addNumber(detail.getfDrugId(),
						detail.getfNumber());
				/**
				 * 跨月
				 * 情况：当月底那天下单，下个月再提交的情况，这时因为库存又生成了一份，drugId变化了，
				 * 有可能结转到下个月的balanceId中，这是需要判断drugId, 如果没有值，
				 * 则取结转后的库存信息（balanceId）,恢复库存也要恢复这个结转后的库存信息
				 */
				stock = this.drugService.getBean(detail.getfDrugId());
				if (stock == null) {
					// 结转后新的库存信息，drugId是新的
					stock = this.drugService.getBeanByBalanceId(detail.getfDrugId());
					if (stock == null) {
						continue;
					}
					// 结转后的id
					detail.setfDrugId(stock.getfId());
					this.drugService.addNumberAndNumberBak(detail.getfDrugId(),
							detail.getfNumber());
				}
			}
		} else if ("5".equals(fState)) {// 业务员删除订单
			bean.setfSaleTime(DateTimeUtil.getTodayChar14());
			bean.setfSaleUserId(userInfo.getId());
			bean.setfSaleUserName(userInfo.getfUserCode());

		} else if ("11".equals(fState)) {// 提交政策报单给管理员-->管理员修改后提交给财务
			bean.setfState("1");
			bean.setfPolicyTime(DateTimeUtil.getTodayChar14());
			bean.setfPolicyUserId(userInfo.getId());
			bean.setfPolicyUserName(userInfo.getfUserCode());
			bean.setfPolicyIntro2(fPolicyIntro2);
		}
		try {
			orderService.edit(bean);
		} catch (Exception ex) {
			logger.error("OrderInvocation edit happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 提交给财务审核、提交政策报单给管理员
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult commit2Finance(
			InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();

		String fId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		String fState = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fState", "");
		String fSaleIntro = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fSaleIntro", "");
		// 发货方式，0：物流，1：自提，2：快递
		Integer fSendType = RequestUtil.getIntegerParamter(
				context.getRequest(), "fSendType");
		// 选择物流发货输入的物流公司名称
		String fLogistics = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "fLogistics", null);
		Order bean = new Order();
		UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(
				context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
		bean.setfId(fId);
		bean.setfState(fState);
		// 状态：0，业务员未提交；1，提交财务审核，2，财务审核通过，3,已发货,4，退单,10:提交政策报单给管理员
		bean.setfSaleTime(DateTimeUtil.getTodayChar14());
		bean.setfSaleUserId(userInfo.getId());
		bean.setfSaleUserName(userInfo.getfUserCode());
		bean.setfSaleIntro(fSaleIntro);
		bean.setfLogistics(fLogistics);
		bean.setfSendType(fSendType);

		try {
			/**
			 * 1、取出订单明细，判断库存是否足够 2、减库存
			 */
			List<OrderDetail> orderDetailList = this.orderDetailService
					.getListByOrderId(fId);
			// 判断药品库存是否够下单所用 start
			String drugName = null;
			int fSalesNumber = 0;
			int drugId;
			Drug stock = null;
			boolean flag = false;
			for (OrderDetail od : orderDetailList) {
				drugName = od.getfDrugName();
				fSalesNumber = od.getfNumber();
				drugId = od.getfDrugId();
				stock = this.drugService.getBean(drugId);
				/**
				 * 情况：当月底那天下单，下个月再提交的情况，这时因为库存又生成了一份，drugId变化了，
				 * 有可能结转到下个月的balanceId中，这是需要判断drugId如果没有值，
				 * 则取结转后的库存信息（balanceId）,减库存也要减这个结转后的库存信息
				 */
				if (stock == null) {
					// 结转后新的库存信息，drugId是新的
					stock = this.drugService.getBeanByBalanceId(drugId);
					// 结转后的id
					od.setfDrugId(stock.getfId());
					// 订单时间改为下个月，纳入下个月统计
					bean.setfTime(DateTimeUtil.getTodayChar14());
				}
				if (stock == null || stock.getfNumber() < fSalesNumber) {// 库存不足
					flag = true;
					break;
				}
			}
			if (flag) {// 库存不足,添加失败，并返回那个药品库存不足
				result.setRetCode(ISystemConstants.SYS_FAILED);
				result.setRetObj(drugName + "的库存不足" + fSalesNumber);
				return result;
			}
			// 判断药品库存是否够下单所用 end
			// 减库存
			for (OrderDetail od : orderDetailList) {
				this.drugDao.addNumber(od.getfDrugId(), -od.getfNumber()
						.intValue());// 减少库存
				// 更新订单详细表里的药品库存id为结转后id，这个才是真正减库存的记录，方便之后统计
				this.orderDetailService.edit(od);
			}
			// 存储提交信息
			orderService.edit(bean);
		} catch (Exception ex) {
			logger.error("OrderInvocation edit happen execption.", ex);
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
		Order bean = new Order();
		bean.setfState(1 + "");

		try {
			orderService.edit(bean);
		} catch (Exception ex) {
			logger.error("OrderInvocation delete happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * editPrice 财务修改订单价格
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult editPrice(InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fOrderId", "");
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String drugIds = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drugIds", "");// 订单详细id,根据id对应药品
		String prices = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"prices", "");
		String drugIds33 = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "drugIds33", "");// 订单详细id,根据id对应药品
		String kaipiaoPrices = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "kaipiaoPrices", "");

		Order bean = new Order();
		// bean.setfId(fOrderId);
		bean.setfTax(fTax);

		Double fBuyingPrice = null;// 进货价
		Double fPrice = null;// 单价
		Double fKaiPiaoPrice = null;// 开票价
		Double fGongyePrice = null;// 工业票价
		Integer fSalesNumber = null;// 出售数量
		// 过票费
		Double fGuoJiFei = 0d;
		Double fGuoJiFeiTotal = 0d;
		// 返点
		Double fFanDian = 0d;
		Double fFanDianTotal = 0d;
		// 高开费
		Double fGaoKaiFei = 0d;
		Double fGaoKaiFeiTotal = 0d;
		// 通过公式计算后的价格
		Double fMoney = 0d;
		Double fMoneyTotal = 0d;

		// 总金额:销量*单价
		Double fMoney_noTax = 0d;
		Double fMoney_noTaxTotal = 0d;

		// 销量*进货价
		Double fMoney_buyingPrice = 0d;
		Double fMoney_buyingPriceTotal = 0d;

		OrderDetail od = null;
		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		/**
		 * 未修改的订单详细信息
		 */
		List<Map<String, Object>> detail = this.orderDetailService
				.getListMapByOrderId(fOrderId);
		if (drugIds != null && !"".equals(drugIds)) {
			// 单价 start
			drugIds = drugIds.substring(0, drugIds.length() - 1);
			prices = prices.substring(0, prices.length() - 1);
			List<String> idList = Arrays.asList(drugIds.split(","));
			String[] arr = prices.split(",");
			// key:药品id，value:修改后的价格
			Map<String, Double> drugPriceMap = new HashMap<String, Double>();
			for (int i = 0; i < idList.size(); i++) {
				drugPriceMap.put(idList.get(i), Double.parseDouble(arr[i]));
			}
			// 单价 end

			// 开票价 end
			if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
				// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
				for (Map<String, Object> map : detail) {
					fPrice = drugPriceMap.get(MapUtils.getString(map, "F_ID"));// 单价
					fBuyingPrice = MapUtils.getDouble(map, "F_BUYING_PRICE");// 进货价
					fSalesNumber = MapUtils.getInteger(map, "F_NUMBER");// 销售数量
					
					// 单个药品返点= 数量*单价 * 0.07
					// fFanDian = fPrice.doubleValue() * fSalesNumber.intValue()
					// * 0.07;
					fFanDian = 0d;
					// 主订单合计返点金额
					fFanDianTotal += fFanDian;
					// 单个药品合计金额
					fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
							- fFanDian;
					// 主订单合计金额
					fMoneyTotal += fMoney;

					// 金额
					fMoney_noTax = fPrice.doubleValue()
							* fSalesNumber.intValue();
					// 合计金额
					fMoney_noTaxTotal += fMoney_noTax;

					// 金额
					fMoney_buyingPrice = fBuyingPrice.doubleValue()
							* fSalesNumber.intValue();
					// 合计金额
					fMoney_buyingPriceTotal += fMoney_buyingPrice;

					od = new OrderDetail();
					od.setfDrugId(MapUtils.getInteger(map, "F_DRUG_ID"));
					od.setfPrice(fPrice);
					od.setfFanDian(fFanDian);
					od.setfMoney(fMoney);
					od.setfMoney_noTax(fMoney_noTax);
					od.setfMoney_buyingPrice(fMoney_buyingPrice);
					ods.add(od);
				}

			} else {// 含税
					// 开票价 start
				drugIds33 = drugIds33.substring(0, drugIds33.length() - 1);
				kaipiaoPrices = kaipiaoPrices.substring(0,
						kaipiaoPrices.length() - 1);
				List<String> idList33 = Arrays.asList(drugIds33.split(","));
				String[] arr33 = kaipiaoPrices.split(",");
				// key:药品id，value:修改后的价格
				Map<String, Double> drugKaiPiaoPriceMap = new HashMap<String, Double>();
				for (int i = 0; i < idList33.size(); i++) {
					drugKaiPiaoPriceMap.put(idList33.get(i),
							Double.parseDouble(arr33[i]));
				}
				for (Map<String, Object> map : detail) {
					fPrice = drugPriceMap.get(MapUtils.getString(map, "F_ID"));// 单价
					fBuyingPrice = MapUtils.getDouble(map, "F_BUYING_PRICE");// 进货价
					fGongyePrice = MapUtils.getDouble(map, "F_GONGYE_PRICE");// 工业票价
					fSalesNumber = MapUtils.getInteger(map, "F_NUMBER");// 销售数量
					fKaiPiaoPrice = drugKaiPiaoPriceMap.get(MapUtils.getString(
							map, "F_ID"));// 开票价
					// 过票费= 开票价 * 数量 * 0.03
					fGuoJiFei = fKaiPiaoPrice.doubleValue()
							* fSalesNumber.intValue() * 0.03;
					fGuoJiFeiTotal += fGuoJiFei;
					// 高开费 = （开票价-工业票价）* 数量 * 0.17
					fGaoKaiFei = (fKaiPiaoPrice.doubleValue() - fPrice
							.doubleValue()) * fSalesNumber.intValue() * 0.17;
					fGaoKaiFeiTotal += fGaoKaiFei;
					// 单个药品合计
					fMoney = fPrice.doubleValue() * fSalesNumber.intValue()
							+ fGuoJiFei + fGaoKaiFei;
					// 合计
					fMoneyTotal += fMoney;

					// 单个药品合计
					fMoney_noTax = fPrice.doubleValue()
							* fSalesNumber.intValue();
					// 合计
					fMoney_noTaxTotal += fMoney_noTax;

					// 单个药品合计
					fMoney_buyingPrice = fBuyingPrice.doubleValue()
							* fSalesNumber.intValue();
					// 合计
					fMoney_buyingPriceTotal += fMoney_buyingPrice;

					od = new OrderDetail();
					od.setfDrugId(MapUtils.getInteger(map, "F_DRUG_ID"));
					od.setfPrice(fPrice);
					od.setfKaiPiaoPrice(fKaiPiaoPrice);
					od.setfGongyePrice(fGongyePrice);
					od.setfGuoJiFei(fGuoJiFei);
					od.setfGaoKaiFei(fGaoKaiFei);
					od.setfMoney(fMoney);
					od.setfMoney_noTax(fMoney_noTax);
					od.setfMoney_buyingPrice(fMoney_buyingPrice);
					ods.add(od);
				}
			}
		}

		bean.setfMoney(fMoneyTotal);
		bean.setfMoney_noTax(fMoney_noTaxTotal);// 数量*单价
		bean.setfMoney_buyingPrice(fMoney_buyingPriceTotal);
		bean.setfGuoJiFei(fGuoJiFeiTotal);
		bean.setfGaoKaiFei(fGaoKaiFeiTotal);
		bean.setfFanDian(fFanDianTotal);
		// bean.setfTime(DateTimeUtil.getTodayChar14());
		// UserInfoBean userInfo = (UserInfoBean)
		// SessionUtil.getObjectAttribute(context.getRequest(),
		// RequestConstants.ADMIN_SESSION_KEY);
		// bean.setfSaleUserId(userInfo.getId());
		// bean.setfSaleUserName(userInfo.getfUserCode());

		try {
			/*
			 * 1：保留原有订单，这份订单是给业务员看的 2：生成新订单，这份修改后的订单给财务和发货人员看
			 * 3：如果一个订单被多次修改则只在新订单中修改，不再生成修改后的新订单
			 */
			// 判断该订单是否已被修改过了
			Order order = this.orderService.getBeanById(fOrderId);
			if (order.getfSourceId() == null) {// 没有原始订单id，首次被修改，生成新订单
				String newfOrderId = this.orderSeqGen.next();
				// 复制原订单 start
				order.setfId(newfOrderId);// 新订单号
				order.setfSourceId(fOrderId);// 保留原订单号
				this.orderService.save(order);// 复制订单
				List<OrderDetail> details = this.orderDetailService
						.getListByOrderId(fOrderId);
				for (OrderDetail o : details) {
					o.setfOrderId(newfOrderId);
					this.orderDetailService.save2(o);
				}
				// 复制原订单 end

				order.setfId(fOrderId);
				order.setfIsEditPrice("1");
				this.orderService.edit(order);//
				// 修改新的订单
				bean.setfId(newfOrderId);
				bean.setfIsEditPrice("2");
				orderService.edit(bean);
				// 修改新订单详细
				for (OrderDetail b : ods) {
					b.setfOrderId(newfOrderId);
					this.orderDetailService.editByOrderIdAndDrugId(b);
				}
			} else {
				// 修改新的订单
				bean.setfId(fOrderId);
				orderService.edit(bean);
				// 修改新订单详细
				for (OrderDetail b : ods) {
					b.setfOrderId(fOrderId);
					this.orderDetailService.editByOrderIdAndDrugId(b);
				}
			}

		} catch (Exception ex) {
			logger.error("OrderInvocation editPrice happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * editPrice_policy 管理员修改政策订单价格,返点,金额，高开费，过票费
	 * 
	 * @param context
	 * @return
	 */
	public synchronized InvocationResult editPrice_policy(
			InvocationContext context) {

		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fOrderId", "");
		String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fTax", "");
		String nums = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"nums", "");// 订单详细id,
		String drugIds = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"drugIds", "");// 订单详细id,
		String prices = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"prices", "");// 单价
		String fandians = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fandians", "");// 返点
		String moneys = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"moneys", "");// 金额

		String kaipiaoPrices = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "kaipiaoPrices", "");// 开票价
		String guopiaofeis = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "guopiaofeis", "");// 过票费
		String gaokaifeis = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "gaokaifeis", "");// 高开费

		try {
			Order order = null;
			OrderDetail detail = null;
			List<OrderDetail> details = new ArrayList<OrderDetail>();

			nums = nums.substring(0, nums.length() - 1);// 数量
			List<String> numList = Arrays.asList(nums.split(","));
			drugIds = drugIds.substring(0, drugIds.length() - 1);// 药品id
			List<String> drugIdList = Arrays.asList(drugIds.split(","));
			prices = prices.substring(0, prices.length() - 1);// 单价
			List<String> priceList = Arrays.asList(prices.split(","));
			moneys = moneys.substring(0, moneys.length() - 1);// 金额
			List<String> moneyList = Arrays.asList(moneys.split(","));

			Double fPrice = 0d;// 单价
			Integer fNum = 0;// 销量

			Double fandian = 0d;// 返点
			Double fandianTotal = 0d;// 返点统计

			Double money = 0d;// 金额
			Double moneyTotal = 0d;// 金额统计

			Double fMoney_noTax = 0d;// 单价*数量
			Double fMoney_noTaxTotal = 0d;// 金额统计

			Double fGuoJiFei = 0d;// 过票费
			Double fGuoJiFeiTotal = 0d;// 过票费合计

			Double fGaoKaiFei = 0d;// 高开费
			Double fGaoKaiFeiTotal = 0d;// 高开费合计

			if ("0".equals(fTax) || "3".equals(fTax)) {// 不含税
				// 0：普通发票，1：含税(增值税)，2：含税(普通),3:专用发票
				fandians = fandians.substring(0, fandians.length() - 1);// 返点
				List<String> fandianList = Arrays.asList(fandians.split(","));
				for (int i = 0; i < drugIdList.size(); i++) {
					detail = new OrderDetail();
					detail.setfOrderId(fOrderId);
					detail.setfId(Integer.parseInt(drugIdList.get(i)));// 订单详细id
					fPrice = Double.parseDouble(priceList.get(i));
					detail.setfPrice(fPrice);

					// fandian = Double.parseDouble(fandianList.get(i));
					fandian = 0d;
					fandianTotal += fandian;
					detail.setfFanDian(fandian);

					money = Double.parseDouble(moneyList.get(i));
					moneyTotal += money;
					detail.setfMoney(money);

					fMoney_noTax = Integer.parseInt(numList.get(i)) * fPrice;
					fMoney_noTaxTotal += fMoney_noTax;
					detail.setfMoney_noTax(fMoney_noTax);

					details.add(detail);
				}
			} else {
				kaipiaoPrices = kaipiaoPrices.substring(0,
						kaipiaoPrices.length() - 1);// 开票价
				List<String> kaipiaoPriceList = Arrays.asList(kaipiaoPrices
						.split(","));
				guopiaofeis = guopiaofeis
						.substring(0, guopiaofeis.length() - 1);// 过票费
				List<String> guopiaofeiList = Arrays.asList(guopiaofeis
						.split(","));
				gaokaifeis = gaokaifeis.substring(0, gaokaifeis.length() - 1);// 高开费
				List<String> gaokaifeiList = Arrays.asList(gaokaifeis
						.split(","));
				for (int i = 0; i < drugIdList.size(); i++) {
					detail = new OrderDetail();
					detail.setfOrderId(fOrderId);
					detail.setfId(Integer.parseInt(drugIdList.get(i)));
					detail.setfPrice(Double.parseDouble(priceList.get(i)));
					detail.setfKaiPiaoPrice(Double.parseDouble(kaipiaoPriceList
							.get(i)));

					fGuoJiFei = Double.parseDouble(guopiaofeiList.get(i));
					fGuoJiFeiTotal += fGuoJiFei;
					detail.setfGuoJiFei(fGuoJiFei);

					fGaoKaiFei = Double.parseDouble(gaokaifeiList.get(i));
					fGaoKaiFeiTotal += fGaoKaiFei;
					detail.setfGaoKaiFei(fGaoKaiFei);

					money = Double.parseDouble(moneyList.get(i));
					moneyTotal += money;
					detail.setfMoney(money);

					fMoney_noTax = Integer.parseInt(numList.get(i)) * fPrice;
					fMoney_noTaxTotal += fMoney_noTax;
					detail.setfMoney_noTax(fMoney_noTax);

					details.add(detail);
				}
			}
			order = new Order();
			order.setfId(fOrderId);
			order.setfFanDian(fandianTotal);
			order.setfMoney(moneyTotal);
			order.setfGaoKaiFei(fGaoKaiFeiTotal);
			order.setfGuoJiFei(fGuoJiFeiTotal);
			order.setfMoney_noTax(fMoney_noTaxTotal);
			this.orderService.edit(order);
			for (OrderDetail bean : details) {
				this.orderDetailService.edit(bean);
			}

		} catch (Exception ex) {
			logger.error("OrderInvocation editPrice happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: salePerday
	 * @Description: (业务员每天的销售业绩)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult salePerday(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "beginTime", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fTime", beginTime.replaceAll("-", ""));
		try {
			Map<String, Object> map = orderService.salePerday(params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation salePerday happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: salesSummary
	 * @Description: (药品当前库存，销售明细)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult salesSummary(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fName = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(
				context.getRequest(), "beginTime", "");
		// ywy 业务员看到的库存， admin 看的信息
		String fType = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fType", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fName", fName);
		params.put("fType", fType);
		params.put("fTime", beginTime.replaceAll("-", ""));
		try {
			Map<String, Object> map = orderService.salesSummary(params);
			result.setRetObj(map);
		} catch (Exception ex) {
			logger.error("OrderInvocation salesSummary happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: showIntro
	 * @Description: (显示备注信息)
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult showIntro(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		int fType = RequestUtil.getIntParamter(context.getRequest(), "fType");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fId", fId);
		try {
			Order bean = orderService.showIntro(params);
			String msg = "";
			if (fType == 1) {
				msg = bean.getfSaleIntro();
			} else if (fType == 2) {
				msg = bean.getfFinanceIntro();
			} else if (fType == 3) {
				msg = bean.getfShipperIntro();
			} else if (fType == 4) {// 政策保单政策内容
				msg = bean.getfPolicyIntro();
			} else if (fType == 10) {// 政策保单审批备注
				msg = bean.getfPolicyIntro2();
			}
			result.setRetObj(msg);
		} catch (Exception ex) {
			logger.error("OrderInvocation showIntro happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

	/**
	 * 
	 * @Title: examine
	 * @Description: 财务复核
	 * @param context
	 * @return
	 * @return InvocationResult 返回类型
	 * @throws
	 */
	public InvocationResult examine(InvocationContext context) {
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fId = RequestUtil.getStrParamterAsDef(context.getRequest(),
				"fId", "");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fId", fId);
		try {
			orderService.examine(params);
		} catch (Exception ex) {
			logger.error("OrderInvocation examine happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}

}
