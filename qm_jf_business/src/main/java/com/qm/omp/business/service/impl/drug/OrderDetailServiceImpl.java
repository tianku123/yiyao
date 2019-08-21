package com.qm.omp.business.service.impl.drug;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.IDrugDao;
import com.qm.omp.business.dao.drug.IOrderDetailDao;
import com.qm.omp.business.dao.drug.IZgYWYDao;
import com.qm.omp.business.pojo.admin.ZgYWYBean;
import com.qm.omp.business.pojo.drug.OrderDetail;
import com.qm.omp.business.util.DateTimeUtil;
import com.qm.omp.business.util.PoiUtil;

@Service("orderDetailService")
public class OrderDetailServiceImpl {

	@Autowired
	private IOrderDetailDao orderDetailDao;
	
	@Autowired
	private IDrugDao drugDao;
	
	@Autowired
	private IZgYWYDao zgYWYDao;

	
	public Map<String, Object> getList(String fOrderId, int page, int rows) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fOrderId", fOrderId);
		res.put("total", orderDetailDao.getListTotal(params));
		res.put("rows", orderDetailDao.getList(params));
		return res;
	}
	
	

	
	public Map<String, Object> getList_EditOrder(String fOrderId, int page,
			int rows, String isZy) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		params.put("fOrderId", fOrderId);
		res.put("total", orderDetailDao.getListTotal_EditOrder(params));
		// 直营的情况下，更改一下key，fPrice 改成 fSupplyPrice
		List<OrderDetail> list = orderDetailDao.getList_EditOrder(params);
		if (list != null && !list.isEmpty()) {
			for (OrderDetail o : list) {
				o.setfSupplyPrice(o.getfPrice());
			}
		}
		res.put("rows", list);
		return res;
	}



	
	public void save(OrderDetail bean) {
//		this.drugDao.minusNumber(bean.getfDrugId(), -bean.getfNumber().intValue());//减少库存
		
		this.orderDetailDao.save(bean);
	}
	
	
	public void save2(OrderDetail bean) {
		
		this.orderDetailDao.save(bean);
	}

	
	public List<OrderDetail> getListByOrderId(String fId) {
		
		return this.orderDetailDao.getListByOrderId(fId);
	}

	
	public List<Map<String, Object>> printer(String fOrderId) {
		return this.orderDetailDao.printer(fOrderId);
	}

	
	public List<Map<String, Object>> getListMapByOrderId(String fOrderId) {
		return this.orderDetailDao.getListMapByOrderId(fOrderId);
	}

	
	public void edit(OrderDetail b) {
		this.orderDetailDao.edit(b);
	}

	
	public void deleteBeanByOrderId(String fOrderId) {
		this.orderDetailDao.deleteBeanByOrderId(fOrderId);
	}

	
	public void editByOrderIdAndDrugId(OrderDetail b) {
		this.orderDetailDao.editByOrderIdAndDrugId(b);
	}


	
	public Map<String, Object> getList_zg(Map<String, Object> params, int page,
			int rows, String role) {
		page = (page-1)*rows;
		params.put("page", page);
		params.put("rows", rows);
		int fYwyId = MapUtils.getIntValue(params, "fYwyId");
		int fZhuguanId = MapUtils.getIntValue(params, "userId");
		List<ZgYWYBean> departments = this.zgYWYDao.getBeansByZhuguanIdAndYWYId(fZhuguanId, fYwyId);
		// 主管所拥有该业务员部门
		Set<Integer> set = new HashSet<Integer>();
		for(ZgYWYBean bean : departments){
			set.add(bean.getfDepartmentId());
		}
		
		Integer total = orderDetailDao.getListTotal_zg(params);
		List<OrderDetail> list = orderDetailDao.getList_zg(params);
		ListIterator<OrderDetail> ite = list.listIterator();
		while (ite.hasNext()) {
			if (!set.contains(ite.next().getfDepartmentId())) {
				ite.remove();
			}
		}
		for (OrderDetail bean : list) {
			if ("xiaoquzhuguan".equals(role)) {
				bean.setfDqTc_Money(null);
			} else if ("daquzhuguan".equals(role)) {
				bean.setfXqTc_Money(null);
			}
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", total);
		res.put("rows", list);
		return res;
	}



	/**
	 * 订单详情
	 * @param page
	 * @param rows
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOrderDetailList(int page, int rows,
			Map<String, Object> params) {
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		params.put("page", page);
		params.put("rows", rows);
		res.put("total", orderDetailDao.getOrderDetailListTotal(params));
		res.put("rows", orderDetailDao.getOrderDetailList(params));
		return res;
	}

	/**
	 * 导出订单及订单详情
	 * @param params 
	 * @return
	 */
	public HSSFWorkbook exportOrderDetail(Map<String, Object> params) {
		List<Map<String, Object>> data = orderDetailDao.getOrderDetailList(params);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("订单及订单详情");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(5, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"订单号","是否含税","政策报单","付款情况","订单状态","复核"
				,"客户名称","收货人","购货单位","收货地址","收货电话","业务员"
				,"下单时间", "财务", "财务审批时间", "发货员", "发货时间", "快递公司"
				,"快递单号", "过票费", "返点", "高开费", "成本金额", "金额"
				,"计算后金额", "小区主管提成", "大区主管提成"
				,"药品名称", "销售数量", "过票费", "返点", "高开费", "计算后金额"
				,"成本金额", "小区提成", "大区提成"
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
 		for(int i = 0; i < data.size(); i++) {
 			map = data.get(i);
 			row = sheet.createRow(i + 1);
 			cellNum = 0;
 			// 订单号
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_ID"));
 			cell.setCellStyle(cellStyle);
 			// 是否含税
 			cell = row.createCell(cellNum++);
 			cell.setCellStyle(cellStyle);
 			int flag = MapUtils.getIntValue(map, "F_TAX");
 			String flagStr = "";
 			if (flag == 0) {
 				flagStr = "工业票";
 			}else if (flag == 1) {
 				flagStr = "含税(增值税)";
 			}else if (flag == 2) {
 				flagStr = "含税(普通)";
 			}
 			cell.setCellValue(flagStr);
 			// 政策报单
 			flag = MapUtils.getIntValue(map, "F_ISPOLICY");
 			flagStr = "";
 			cell = row.createCell(cellNum++);
 			cell.setCellStyle(cellStyle);
 			if (flag == 0) {
 				flagStr = "否";
 			} else {
 				flagStr = "是"; 				
 			}
 			cell.setCellValue(flagStr);
 			// 付款情况
 			cell = row.createCell(cellNum++);
 			cell.setCellStyle(cellStyle);
 			flag = MapUtils.getIntValue(map, "F_PAYMENT_STATE");
 			flagStr = "";
 			if (flag == 0) {
 				flagStr = "借款";
 			} else if (flag == 1) {
 				flagStr = "已付款";
 			} else if (flag == 2) {
 				flagStr = "已还款";
 			}
 			cell.setCellValue(flagStr);
 			// 订单状态
 			cell = row.createCell(cellNum++);
 			cell.setCellStyle(cellStyle);
 			flag = MapUtils.getIntValue(map, "F_STATE");
 			flagStr = "";
 			if (flag == 0) {
 				flagStr = "业务员未提交";
 			} else if (flag == 1) {
 				flagStr = "未审核";
 			} else if (flag == 2) {
 				flagStr = "审核通过";
 			} else if (flag == 3) {
 				flagStr = "已发货";
 			}
 			cell.setCellValue(flagStr);
 			// 复核
 			cell = row.createCell(cellNum++);
 			cell.setCellStyle(cellStyle);
 			flag = MapUtils.getIntValue(map, "F_EXAMINE");
 			flagStr = "";
 			if (flag == 0) {
 				flagStr = "未复核";
 			} else if (flag == 1) {
 				flagStr = "已复核";
 			}
 			cell.setCellValue(flagStr);
 			// 客户名称
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_CUSTOMER_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 收货人
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 购货单位
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_UNIT"));
 			cell.setCellStyle(cellStyle);
 			// 收货地址
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_ADDRESS"));
 			cell.setCellStyle(cellStyle);
 			// 收货电话
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_PHONE"));
 			cell.setCellStyle(cellStyle);
 			// 业务员
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_SALE_USER_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 下单时间
 			cell = row.createCell(cellNum++);
 			
 			cell.setCellValue(DateTimeUtil.formatDateStrToOtherStr6ByDateFormat(MapUtils.getString(map, "F_SALE_TIME")));
 			cell.setCellStyle(cellStyle);
 			// 财务
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_FINANCE_USER_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 财务审批时间
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(DateTimeUtil.formatDateStrToOtherStr6ByDateFormat(MapUtils.getString(map, "F_FINANCE_TIME")));
 			cell.setCellStyle(cellStyle);
 			// 发货员
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_SHIPPER_USER_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 发货时间
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(DateTimeUtil.formatDateStrToOtherStr6ByDateFormat(MapUtils.getString(map, "F_SHIPPER_TIME")));
 			cell.setCellStyle(cellStyle);
 			// 快递公司
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_EXPRESS_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 快递单号
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_EXPRESS_ID"));
 			cell.setCellStyle(cellStyle);
 			// 过票费
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_GUOJIFEI"));
 			cell.setCellStyle(cellStyle);
 			// 返点
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_FANDIAN"));
 			cell.setCellStyle(cellStyle);
 			// 高开费
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_GAOKAIFEI"));
 			cell.setCellStyle(cellStyle);
 			// 成本金额
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_MONEY_BUYINGPRICE"));
 			cell.setCellStyle(cellStyle);
 			// 金额
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_MONEY_NOTAX"));
 			cell.setCellStyle(cellStyle);
 			// 计算后金额
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_MONEY"));
 			cell.setCellStyle(cellStyle);
 			// 小区主管提成
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_XQ_TC_MONEY"));
 			cell.setCellStyle(cellStyle);
 			// 大区主管提成
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_DQ_TC_MONEY"));
 			cell.setCellStyle(cellStyle);
 			
 		//=============================================== 以下是订单详情部分
 			// 药品名称
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getString(map, "F_DRUG_NAME"));
 			cell.setCellStyle(cellStyle);
 			// 销售数量
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getInteger(map, "F_NUMBER"));
 			cell.setCellStyle(cellStyle);
 			// 过票费
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_GUOJIFEI_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 返点
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_FANDIAN_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 高开费
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_GAOKAIFEI_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 计算后金额
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_MONEY_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 成本金额
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_MONEY_BUYINGPRICE_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 小区提成
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_XQ_TC_MONEY_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			// 大区提成
 			cell = row.createCell(cellNum++);
 			cell.setCellValue(MapUtils.getDoubleValue(map, "F_DQ_TC_MONEY_DETAIL"));
 			cell.setCellStyle(cellStyle);
 			
 		}
 		
 		/**
 		 * 合并单元格
 		 * var mergeField = "F_ID,F_TAX,F_ISPOLICY,F_PAYMENT_STATE,F_STATE,F_CUSTOMER_NAME,F_NAME,F_UNIT,F_ADDRESS,F_PHONE,"+
					"F_SALE_USER_NAME,F_SALE_TIME,F_FINANCE_USER_NAME,F_FINANCE_TIME"
					+",F_SHIPPER_USER_NAME,F_SHIPPER_TIME,F_EXPRESS_NAME,F_EXPRESS_ID,F_EXAMINE," 
					+"F_GUOJIFEI,F_FANDIAN,F_GAOKAIFEI,F_MONEY_BUYINGPRICE,F_MONEY_NOTAX,F_MONEY,F_XQ_TC_MONEY,F_DQ_TC_MONEY"
 		 */
 		String[] mergeField = {"F_ID","F_TAX","F_ISPOLICY","F_PAYMENT_STATE","F_STATE","F_EXAMINE"
				,"F_CUSTOMER_NAME","F_NAME","F_UNIT","F_ADDRESS","F_PHONE","F_SALE_USER_NAME"
				,"F_SALE_TIME", "F_FINANCE_USER_NAME", "F_FINANCE_TIME", "F_SHIPPER_USER_NAME", "F_SHIPPER_TIME", "F_EXPRESS_NAME"
				,"F_EXPRESS_ID", "F_GUOJIFEI", "F_FANDIAN", "F_GAOKAIFEI", "F_MONEY_BUYINGPRICE", "F_MONEY_NOTAX"
				,"F_MONEY", "F_XQ_TC_MONEY", "F_DQ_TC_MONEY"
		};
 		mergeCell(data, "F_ID", mergeField, sheet);
		return wb;
	}
	
	private void mergeCell(List<Map<String, Object>> data, String field, String[] mergeField, HSSFSheet sheet) {
		Map<String, Object> map;
		int span = 1;
		String PerValue = "";
		// 下一个值
		String CurValue = "";
		for(int i = 0; i < data.size(); i++) {
 			map = data.get(i);
 			if (i == data.size()) {
 				 CurValue = "";
			 }
			 else {
				 CurValue = MapUtils.getString(map, field);
			 }
			 if (i == 0){
				 PerValue = CurValue;
				 continue;
			 }
			 if (PerValue.equals(CurValue)) {
				 span += 1;
			 }
			 else {
				 int index = i - span;
				 int length = mergeField.length - 1;
				 System.out.println(index + "====" + i + "====" + span);
				 for (int k = length; k >= 0; k--) {
					 sheet.addMergedRegion(new CellRangeAddress(index + 1, i, k, k));
				 }
				 span = 1;
				 PerValue = CurValue;
			 }
 		}
	}

	/**
	 * 合计金额
	 * @param page
	 * @param rows
	 * @param params
	 * @return
	 */
	public Map<String, Object> getTotalMoney(int page, int rows,
			Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", 1);
		res.put("rows", orderDetailDao.getTotalMoney(params));
		return res;
	}
}
