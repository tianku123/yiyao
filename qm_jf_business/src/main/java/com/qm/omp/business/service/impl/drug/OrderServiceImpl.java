package com.qm.omp.business.service.impl.drug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IOrderDao;
import com.qm.omp.business.dao.drug.IOrderDetailDao;
import com.qm.omp.business.dao.drug.IZgYWYDao;
import com.qm.omp.business.pojo.admin.ZgYWYBean;
import com.qm.omp.business.pojo.drug.Order;
import com.qm.omp.business.util.DateTimeUtil;
import com.qm.omp.business.util.NumberFormatUtil;
import com.qm.omp.business.util.PoiUtil;

@Service("orderService")
public class OrderServiceImpl {

	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IOrderDetailDao orderDetailDao;
	
	@Autowired
	private ZgYWYServiceImpl zgYWYService;
	
	@Autowired
	private IZgYWYDao zgYWYDao;

	public Map<String, Object> getList(String fName, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fName", fName);
		res.put("total", orderDao.getListTotal(params));
		res.put("rows", orderDao.getList(params));
		return res;
	}

	/**
	 * 当月库存，剩余库存，销售情况
	 * @param params
	 * @return
	 */
	public Map<String, Object> salesSummary(Map<String, Object> params) {
		String fType = MapUtils.getString(params, "fType");
		Map<String, Object> datas = new HashMap<String, Object>();
		List<Map<String, Object>> data = this.orderDao.salesSummary(params);
		/*
		 * 库存状态 
		 * 0：正常入库状态或当月入库当月销售完的状态；
		 * 1：添加错误删除状态；
		 * 2：正常入库当月未销售完则通过定时任务在月底，
		 * 		自动复制改库存信息（此库存信息状态置为2）生成一个新的库存信息（此库存信息置为3，F_NUMBER_BAK，F_NUMBER字段值为上月结余数，F_BALANCE_ID的值为被复制记录的id）
		 * 		到下个月的日期成为下个月库存信息里面的上月结余库存正常销售。
		 * 3：上月结余库存，拥有上月的剩余库存数和一切其他信息（参考2的解释）
		 */
		int fState;
		//上月库存   状态为3的库存信息为上月库存，统计F_NUMBER_BAK字段
		int lastMonthStock_in = 0;
		//本月入库  状态为0的就是本月入库，也是统计F_NUMBER_BAK字段，状态为2的是本月剩余库存也要算到本月入库中
		int thisMonthStock_in = 0;
		//本月库存（剩余库存）  状态为0和3的库存信息的F_NUMBER字段值累加
		int thisMonthStock_surplus = 0;
		//本月销售  上月库存+本月入库-剩余库存
		int salesNumber = 0;
		
		//进货价	由于一个单品对应多个库存，进货价和销售价不一定一样所以暂时无法显示进货价或销售价
		//销售价
		
		//销售金额	本月销售*销售价   此处存在误差，因为销售价是可以根据订单修改的，应该从订单中获取具体的销售金额
//		Double salesMoney = 0d;
		//进货金额	本月入库*进价的累积
		Double costMoney = 0d;
		//上月结余库存进货金额	上月库存*进价的累积
		Double lastMonthMoney = 0d;
		//利润	(销售价-进货价)*数量
//		Double profit = 0d;
		
		//业务员销售额统计   key:药品id， value:Map<String, Double>-->key:库存或金额，value:库存或金额
		Map<String, Map<String, Object>> res = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> sgl = null;
		//药品id
		String drugOnlyId = null;
		String fName = null;//药品名称
		String fSpecification = null;//药品规格
		String fAddress = null;//药品产地
		Double fBuyingPrice;//进货价
		
		for(Map<String, Object> map : data){
			drugOnlyId = MapUtils.getString(map, "F_ID");
			fName = MapUtils.getString(map, "F_NAME");
			fSpecification = MapUtils.getString(map, "F_SPECIFICATION");
			fAddress = MapUtils.getString(map, "F_ADDRESS");
			fState = MapUtils.getIntValue(map, "F_STATE");
			fBuyingPrice = MapUtils.getDouble(map, "F_BUYING_PRICE");
			//初始化0
			//上月库存   状态为3的库存信息为上月库存，统计F_NUMBER_BAK字段
			lastMonthStock_in = 0;
			//本月入库  状态为0的就是本月入库，也是统计F_NUMBER_BAK字段，状态为2的是本月剩余库存也要算到本月入库中
			thisMonthStock_in = 0;
			//本月库存（剩余库存）  状态为0和3的库存信息的F_NUMBER字段值累加
			thisMonthStock_surplus = 0;
			//本月销售  上月库存+本月入库-剩余库存
			salesNumber = 0;
			//进货金额	本月入库*进价的累积
			costMoney = 0d;
			//上月结余库存进货金额	上月库存*进价的累积
			lastMonthMoney = 0d;
			
			if(fState == 3){//上月结余
				lastMonthStock_in = MapUtils.getIntValue(map, "F_NUMBER_BAK");
				thisMonthStock_surplus = MapUtils.getIntValue(map, "F_NUMBER");//本月剩余库存
				salesNumber = lastMonthStock_in - thisMonthStock_surplus;
				
				lastMonthMoney = fBuyingPrice * lastMonthStock_in;
				lastMonthMoney = NumberFormatUtil.doubleFormat1(lastMonthMoney);
			}else if(fState == 0 || fState == 2){//本月入库
				thisMonthStock_in = MapUtils.getIntValue(map, "F_NUMBER_BAK");
				thisMonthStock_surplus = MapUtils.getIntValue(map, "F_NUMBER");//本月剩余库存
				salesNumber = thisMonthStock_in - thisMonthStock_surplus;
				
				costMoney = fBuyingPrice * thisMonthStock_in;
				costMoney = NumberFormatUtil.doubleFormat1(costMoney);
			}
			if(res.containsKey(drugOnlyId)){//根据单一药品区分计算
				sgl = res.get(drugOnlyId);
				
				sgl.put("lastMonthStock_in", lastMonthStock_in + MapUtils.getIntValue(sgl, "lastMonthStock_in"));
				sgl.put("thisMonthStock_in", thisMonthStock_in + MapUtils.getIntValue(sgl, "thisMonthStock_in"));
				sgl.put("thisMonthStock_surplus", thisMonthStock_surplus + MapUtils.getIntValue(sgl, "thisMonthStock_surplus"));
				sgl.put("salesNumber", salesNumber + MapUtils.getIntValue(sgl, "salesNumber"));
				
				sgl.put("costMoney", costMoney + MapUtils.getDoubleValue(sgl, "costMoney"));
				sgl.put("lastMonthMoney", lastMonthMoney + MapUtils.getDoubleValue(sgl, "lastMonthMoney"));
				
			}else{
				sgl = new HashMap<String, Object>();
				sgl.put("fId", drugOnlyId);
				sgl.put("fName", fName);
				sgl.put("fSpecification", fSpecification);
				sgl.put("fAddress", fAddress);
				
				sgl.put("lastMonthStock_in", lastMonthStock_in);
				sgl.put("thisMonthStock_in", thisMonthStock_in);
				sgl.put("thisMonthStock_surplus", thisMonthStock_surplus);
				sgl.put("salesNumber", salesNumber);
				sgl.put("costMoney", costMoney);
				sgl.put("lastMonthMoney", lastMonthMoney);
				
				res.put(drugOnlyId, sgl);
			}
		}
		// 只有admin权限才需要算这些
		if ("admin".equals(fType)) {
			/**
			 * 计算后的具体销量
			 * 每日销量
			 */
			List<Map<String, Object>> xlList = this.orderDao.getOrderDetail(params);
			int xlTotal;
			int xl;
			String yyyyMM = MapUtils.getString(params, "fTime");
			String fTime;
			for(Map<String, Object> map : xlList) {
				xlTotal = 0;
				drugOnlyId = MapUtils.getString(map, "F_DRUG_ONLY_ID");
				fTime = MapUtils.getString(map, "F_TIME");
				if(res.containsKey(drugOnlyId)) {
					// 计算总销售 start
					xl = MapUtils.getIntValue(map, "F_NUMBER");
					sgl = res.get(drugOnlyId);
					if(sgl.containsKey("xlTotal")){
						xlTotal = MapUtils.getIntValue(sgl, "xlTotal");					
					}
					xlTotal += xl;
					sgl.put("xlTotal", xlTotal);
					// 计算总销售 end
					// 每日销售 start
					drugSalePerday(sgl, xl, fTime);
					// 每日销售 end
					res.put(drugOnlyId, sgl);
				}
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(Entry<String, Map<String, Object>> en : res.entrySet()){
			list.add(en.getValue());
		}
		datas.put("rows", list);
		datas.put("total", list.size());
		return datas;
	}

	/**
	 * 每天某个药品的销售额度
	 * @param sgl
	 * @param xl
	 * @param yyyyMM 
	 * @param day 
	 * @param fTime 当前订单时间
	 */
	private void drugSalePerday(Map<String, Object> sgl, int xl, String fTime) {
		String day = fTime.substring(6, 8);
		// 只有当天的订单才累加
		String key = "day"+ day;
		int xlDayTotal = 0;
		if(sgl.containsKey(key)){
			xlDayTotal = MapUtils.getIntValue(sgl, key);					
		}
		xlDayTotal += xl;
		sgl.put(key, xlDayTotal);
	}



	
	public Map<String, Object> salePerday(
			Map<String, Object> params) {
		Map<String, Object> datas = new HashMap<String, Object>();
		List<Map<String, Object>> data = this.orderDao.salePerday(params);
		String fTime = MapUtils.getString(params, "fTime");
		int days = DateTimeUtil.getMonthLastDay(fTime);
		//业务员销售额统计   key:业务员id， value:Map<String, Double>-->key:日期，value:销售额
		Map<String, Map<String, Object>> res = new LinkedHashMap<String, Map<String, Object>>();
		//一个业务员一个月每天的销售额-->key:日期，value:销售额
		Map<String, Object> salePerMonth_data = new LinkedHashMap<String, Object>();
		Map<String, Object> salePerMonth = null;
		//初始化
		String keyTime = null;
		for(int i=1; i<=days; i++){
			if(i < 10){
				keyTime =  "0" + i;
			}else{
				keyTime =  i + "";
			}
			
			salePerMonth_data.put(keyTime, 0);
		}
		
		String fUserCode = null;//每个用户
		String yyyyMMdd = null;//每天
		Double fMoney = 0d;//计算后的销售额
		for(Map<String, Object> map : data){
			fUserCode = MapUtils.getString(map, "F_USER_CODE");
			if(MapUtils.getString(map, "F_TIME") == null){//该业务员这段时间没有订单
				yyyyMMdd = "55";
			}else{
				yyyyMMdd = MapUtils.getString(map, "F_TIME").substring(6, 8);
			}
			fMoney = MapUtils.getDouble(map, "F_MONEY");
			if(res.containsKey(fUserCode)){//如果包含则取出对应的value，进行计算
				salePerMonth = res.get(fUserCode);//一个业务员一个月每天的销售额
				
				salePerMonth.put(yyyyMMdd, MapUtils.getDouble(salePerMonth, yyyyMMdd) + fMoney);
				res.put(fUserCode, salePerMonth);
			}else{
				salePerMonth = getSalePerMonth_data(days);
				salePerMonth.put(yyyyMMdd, 0 + fMoney);
				salePerMonth.put("fUserName", fUserCode);
				res.put(fUserCode, salePerMonth);//放入有初始化数据的map
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(Entry<String, Map<String, Object>> en : res.entrySet()){
			list.add(en.getValue());
		}
		datas.put("rows", list);
		datas.put("total", list.size());
		return datas;
	}

	private Map<String, Object> getSalePerMonth_data(int days){
		//一个业务员一个月每天的销售额-->key:日期，value:销售额
		Map<String, Object> salePerMonth_data = new LinkedHashMap<String, Object>();
		//初始化
		String keyTime = null;
		for(int i=1; i<=days; i++){
			if(i < 10){
				keyTime =  "0" + i;
			}else{
				keyTime =  i + "";
			}
			
			salePerMonth_data.put(keyTime, 0);
		}
		return salePerMonth_data;
	}

	
	public void save(Order bean) {
		this.orderDao.save(bean);
	}

	
	public void edit(Order bean) {
		this.orderDao.edit(bean);
	}

	
	public Map<String, Object> getList_ywy(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDao.getListTotal_ywy(params));
		res.put("rows", orderDao.getList_ywy(params));
		return res;
	}
	
	public Map<String, Object> getList_zy(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDao.getListTotal_zy(params));
		res.put("rows", orderDao.getList_zy(params));
		return res;
	}
	
	
	public Map<String, Object> getList_policy(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDao.getListTotal_policy(params));
		res.put("rows", orderDao.getList_policy(params));
		return res;
	}

	
	public Map<String, Object> getList_cw(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDao.getListTotal_cw(params));
		res.put("rows", orderDao.getList_cw(params));
		return res;
	}

	
	public Map<String, Object> getList_fh(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDao.getListTotal_fh(params));
		res.put("rows", orderDao.getList_fh(params));
		return res;
	}

	
	public List<Order> getList_State0(Integer fState) {
		return orderDao.getList_State0(fState);
	}

	
	public Order showIntro(Map<String, Object> params) {
		return this.orderDao.showIntro(params);
	}



	
	public Order getBeanById(String fOrderId) {
		return this.orderDao.getBeanById(fOrderId);
	}



	
	public Map<String, Object> getList_zg(Map<String, Object> params, int page,
			int rows, String role) {
		page = (page-1)*rows;
		params.put("page", page);
		params.put("rows", rows);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", 0);
		res.put("rows", new ArrayList<Order>());
		Integer total;
		List<Order> list;
		/**
		 * 根据主管id和业务员名称模糊查询获取主管下业务员订单
		 */
		List<ZgYWYBean> ywys = this.zgYWYDao.getYWYListByZhuGuanIdAndYwyName(params);
		if (ywys != null && ywys.size() > 0) {
			
			/**
			 * 主管所拥有的业务员的所属部门
			 * key:	业务员	+部门
			 * value:	null
			 */
			Set<String> departmentYwy = new HashSet<String>();
			// 业务员id去重
			Set<String> ywySet = new HashSet<String>();
			StringBuilder userId = new StringBuilder();
			for (int i = 0; i < ywys.size(); i++) {
				departmentYwy.add("" + ywys.get(i).getfYWYId() + ywys.get(i).getfDepartmentId());
				ywySet.add(ywys.get(i).getfYWYId()+"");
			}
			for (String str : ywySet) {
				// 获取业务员	start
				userId.append(str).append(",");
				// 获取业务员	end
			}
			params.put("userId", userId.toString().substring(0, userId.length() -1));
			
			total = orderDao.getListTotal_zg(params);
			if (total > 0) {
				/**
				 * 所有主管有权限的订单，需要对订单提成、金额进行重新计算
				 */
				list = orderDao.getList_zg(params);
				
				/**
				 * 获取所有订单详细
				 */
				List<String> orderIds = new ArrayList<String>();
				for (Order order : list) {
					orderIds.add(order.getfId());
				}
				List<Map<String, Object>> details = this.orderDetailDao.getListByOrderIds(orderIds);
				/**
				 * key:订单id
				 * value： Map 计算的值
				 */
				Map<String, Map<String, Double>> calcMap = new HashMap<String, Map<String,Double>>();
				Map<String, Double> calc = new HashMap<String, Double>();
				// 小区提成
				Double xqtc = 0d;
				// 大区提成
				Double dqtc = 0d;
				// 小区销售额
				Double xqMoney = 0d;
				// 大区销售额
				Double dqMoney = 0d;
				/**
				 * 订单根据权限过滤
				 */
				String key;
				String orderId;
				for (Map<String, Object> mm : details) {
					xqtc = 0d;
					dqtc = 0d;
					orderId = MapUtils.getString(mm, "F_ORDER_ID");
					// 业务员	+ 部门
					key = MapUtils.getString(mm, "F_SALE_USER_ID") + MapUtils.getString(mm, "F_DEPARTMENT_ID");
					/**
					 * 主管 	一对多	业务员
					 * 业务员	一对多	部门
					 * 根据key过滤，此主管是否具有该业务员的此部门权限
					 */
					if (departmentYwy.contains(key)) {
						if (calcMap.containsKey(orderId)) {
							calc = calcMap.get(orderId);
						} else {
							calc = new HashMap<String, Double>();
						}
						if ("xiaoquzhuguan".equals(role)) {
							// 小区提成
							if (calc.containsKey("F_XQ_TC_MONEY")) {
								xqtc = calc.get("F_XQ_TC_MONEY") + MapUtils.getDouble(mm, "F_XQ_TC_MONEY");
								calc.put("F_XQ_TC_MONEY", xqtc);
								xqMoney = calc.get("F_XQ_MONEY") + MapUtils.getDouble(mm, "F_MONEY_NOTAX");
								calc.put("F_XQ_MONEY", xqMoney);
							} else {
								calc.put("F_XQ_TC_MONEY", MapUtils.getDouble(mm, "F_XQ_TC_MONEY"));
								calc.put("F_XQ_MONEY", MapUtils.getDouble(mm, "F_MONEY_NOTAX"));
							}
						}
						if ("daquzhuguan".equals(role)) {
							// 大区提成
							if (calc.containsKey("F_DQ_TC_MONEY")) {
								dqtc = calc.get("F_DQ_TC_MONEY") + MapUtils.getDouble(mm, "F_DQ_TC_MONEY");
								calc.put("F_DQ_TC_MONEY", dqtc);
								dqMoney = calc.get("F_DQ_MONEY") + MapUtils.getDouble(mm, "F_MONEY_NOTAX");
								calc.put("F_DQ_MONEY", dqMoney);
							} else {
								calc.put("F_DQ_TC_MONEY", MapUtils.getDouble(mm, "F_DQ_TC_MONEY"));
								calc.put("F_DQ_MONEY", MapUtils.getDouble(mm, "F_MONEY_NOTAX"));
							}
						}
						calcMap.put(orderId, calc);
					}
				}
				
				List<Order> calcList = new ArrayList<Order>();
				for (Order order : list) {
					if (calcMap.containsKey(order.getfId())) {
						calc = calcMap.get(order.getfId());
						order.setfXqTc_Money(NumberFormatUtil.doubleFormat1(MapUtils.getDouble(calc, "F_XQ_TC_MONEY")));
						order.setfDqTc_Money(NumberFormatUtil.doubleFormat1(MapUtils.getDouble(calc, "F_DQ_TC_MONEY")));

						order.setfXq_Money(NumberFormatUtil.doubleFormat1(MapUtils.getDouble(calc, "F_XQ_MONEY")));
						order.setfDq_Money(NumberFormatUtil.doubleFormat1(MapUtils.getDouble(calc, "F_DQ_MONEY")));
					}
					calcList.add(order);
				}
				res.put("total", total);
				res.put("rows", calcList);
				return res;
			}
		}
		return res;
	}



	
	public Map<String, Object> getTc_Total(Map<String, Object> params,
			String role) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", 0);
		Integer total;
		List<Order> list;
		/**
		 * 根据主管id和业务员名称模糊查询获取主管下业务员订单
		 */
		List<ZgYWYBean> ywys = this.zgYWYDao.getYWYListByZhuGuanIdAndYwyName(params);
		if (ywys != null && ywys.size() > 0) {
			
			/**
			 * 主管所拥有的业务员的所属部门
			 * key:	业务员	+部门
			 * value:	null
			 */
			Set<String> departmentYwy = new HashSet<String>();
			// 业务员id去重
			Set<String> ywySet = new HashSet<String>();
			StringBuilder userId = new StringBuilder();
			for (int i = 0; i < ywys.size(); i++) {
				departmentYwy.add("" + ywys.get(i).getfYWYId() + ywys.get(i).getfDepartmentId());
				ywySet.add(ywys.get(i).getfYWYId()+"");
			}
			for (String str : ywySet) {
				// 获取业务员	start
				userId.append(str).append(",");
				// 获取业务员	end
			}
			params.put("userId", userId.toString().substring(0, userId.length() -1));
			
			total = orderDao.getListTotal_zg(params);
			if (total > 0) {
				/**
				 * 所有主管有权限的订单，需要对订单提成、金额进行重新计算
				 */
				list = orderDao.getList_zg(params);
				
				/**
				 * 获取所有订单详细
				 */
				List<String> orderIds = new ArrayList<String>();
				for (Order order : list) {
					orderIds.add(order.getfId());
				}
				List<Map<String, Object>> details = this.orderDetailDao.getListByOrderIds(orderIds);
				
				/**
				 * 订单根据权限过滤
				 */
				String key;
				String orderId;
				// 总提成
				Double totalTc = 0d;
				// 总销售额
				Double totalMoney = 0d;
				for (Map<String, Object> mm : details) {
					orderId = MapUtils.getString(mm, "F_ORDER_ID");
					// 业务员	+ 部门
					key = MapUtils.getString(mm, "F_SALE_USER_ID") + MapUtils.getString(mm, "F_DEPARTMENT_ID");
					/**
					 * 主管 	一对多	业务员
					 * 业务员	一对多	部门
					 * 根据key过滤，此主管是否具有该业务员的此部门权限
					 */
					if (departmentYwy.contains(key)) {
						if ("xiaoquzhuguan".equals(role)) {
							// 小区提成
							totalTc += MapUtils.getDouble(mm, "F_XQ_TC_MONEY");
							// 总销售额
							totalMoney += MapUtils.getDouble(mm, "F_MONEY");
						}
						if ("daquzhuguan".equals(role)) {
							// 大区提成
							totalTc += MapUtils.getDouble(mm, "F_DQ_TC_MONEY");
							// 总销售额
							totalMoney += MapUtils.getDouble(mm, "F_MONEY");
						}
					}
				}
				
				res.put("total", NumberFormatUtil.doubleFormat1(totalTc));
				res.put("totalMoney", NumberFormatUtil.doubleFormat1(totalMoney));
				return res;
			}
		}
		return res;
	}

	/**
	 * 财务复核
	 * @param params
	 */
	public void examine(Map<String, Object> params) {
		this.orderDao.examine(params);
	}

	/**
	 * 导出每日库存及每日销售
	 * @param params
	 * @return
	 */
	public HSSFWorkbook exportSalesSummary(Map<String, Object> params) {
		Map<String, Object> data = this.salesSummary(params);
		if (data != null && data.containsKey("rows")) {
			List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
			if (rows != null && !rows.isEmpty()){
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("订单及订单详情");
				sheet.setDefaultColumnWidth(15);
				sheet.setColumnWidth(5, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
				HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
				HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
				String[] headers = {"药品名称", "规格", "产地", "上月剩余库存", "本月入库", "本月销售"
						,"本月剩余库存", "本月进货金额", "上月结余进货金额", "本月销售合计(计算后结果)"
				};
				HSSFRow row = sheet.createRow(0);
				HSSFCell cell = null;
				for(int i=0;i<headers.length;i++){
					cell = row.createCell(i);
					cell.setCellValue(headers[i]);
					cell.setCellStyle(titleStyle);
				}
				int k = 1;
				for(int i = headers.length; i<headers.length + 31; i++) {
					cell = row.createCell(i);
					cell.setCellValue(k++ + "号");
					cell.setCellStyle(titleStyle);
				}
				Map<String, Object> map;
				int cellNum;
		 		for(int i = 0; i < rows.size(); i++) {
		 			map = rows.get(i);
		 			row = sheet.createRow(i + 1);
		 			cellNum = 0;
		 			// 药品名称
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getString(map, "fName"));
		 			cell.setCellStyle(cellStyle);
		 			// 规格
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getString(map, "fSpecification"));
		 			cell.setCellStyle(cellStyle);
		 			// 产地
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getString(map, "fAddress"));
		 			cell.setCellStyle(cellStyle);
		 			// 上月剩余库存
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getInteger(map, "lastMonthStock_in"));
		 			cell.setCellStyle(cellStyle);
		 			// 本月入库
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getInteger(map, "thisMonthStock_in"));
		 			cell.setCellStyle(cellStyle);
		 			// 本月销售
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getInteger(map, "salesNumber"));
		 			cell.setCellStyle(cellStyle);
		 			// 本月剩余库存
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getInteger(map, "thisMonthStock_surplus"));
		 			cell.setCellStyle(cellStyle);
		 			// 本月进货金额
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getDoubleValue(map, "costMoney"));
		 			cell.setCellStyle(cellStyle);
		 			// 上月结余进货金额
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getDoubleValue(map, "lastMonthMoney"));
		 			cell.setCellStyle(cellStyle);
		 			// 本月销售合计(计算后结果)
		 			cell = row.createCell(cellNum++);
		 			cell.setCellValue(MapUtils.getDoubleValue(map, "xlTotal"));
		 			cell.setCellStyle(cellStyle);
		 			// 每日销售
		 			String key;
		 			for(int day=1; day<=31; day++) {
		 				cell = row.createCell(cellNum++);
		 				cell.setCellStyle(cellStyle);
		 				if (day < 10) {
		 					key = "day0" + day;
		 				} else {
		 					key = "day" + day;
		 				}
			 			cell.setCellValue(MapUtils.getInteger(map, key, 0));
					}
		 		}
		 		return wb;
			}
		}
		return null;
	}
	
	/**
	 * 导出每日库存及每日销售
	 * @param params
	 * @return
	 */
	public HSSFWorkbook exportSalesSummary_ywy(Map<String, Object> params) {
		Map<String, Object> data = this.salesSummary(params);
		if (data != null && data.containsKey("rows")) {
			List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
			if (rows != null && !rows.isEmpty()){
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("订单及订单详情");
				sheet.setDefaultColumnWidth(15);
//				sheet.setColumnWidth(5, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
				HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
				HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
				String[] headers = {"药品名称", "规格", "产地", "本月剩余库存"
				};
				HSSFRow row = sheet.createRow(0);
				HSSFCell cell = null;
				for(int i=0;i<headers.length;i++){
					cell = row.createCell(i);
					cell.setCellValue(headers[i]);
					cell.setCellStyle(titleStyle);
				}
				Map<String, Object> map;
				int cellNum;
				for(int i = 0; i < rows.size(); i++) {
					map = rows.get(i);
					row = sheet.createRow(i + 1);
					cellNum = 0;
					// 药品名称
					cell = row.createCell(cellNum++);
					cell.setCellValue(MapUtils.getString(map, "fName"));
					cell.setCellStyle(cellStyle);
					// 规格
					cell = row.createCell(cellNum++);
					cell.setCellValue(MapUtils.getString(map, "fSpecification"));
					cell.setCellStyle(cellStyle);
					// 产地
					cell = row.createCell(cellNum++);
					cell.setCellValue(MapUtils.getString(map, "fAddress"));
					cell.setCellStyle(cellStyle);
					
					// 本月剩余库存
					cell = row.createCell(cellNum++);
					cell.setCellValue(MapUtils.getInteger(map, "thisMonthStock_surplus"));
					cell.setCellStyle(cellStyle);
					
				}
				return wb;
			}
		}
		return null;
	}

	
}
