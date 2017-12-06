package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.OrderDetail;

@Repository("orderDetailDao")
public interface IOrderDetailDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<OrderDetail> getList(Map<String, Object> params);
	
	
	void save(OrderDetail bean);
	
	List<OrderDetail> getListByOrderId(@Param(value="fId")String fId);
	
	
	List<Map<String, Object>> printer(@Param(value="fOrderId")String fOrderId);
	
	List<Map<String, Object>> getListMapByOrderId(@Param(value="fOrderId")String fOrderId);
	
	void edit(OrderDetail b);
	
	void deleteBeanByOrderId(@Param(value="fOrderId")String fOrderId);
	
	
	Integer getListTotal_EditOrder(Map<String, Object> params);
	List<OrderDetail> getList_EditOrder(Map<String, Object> params);
	
	void editByOrderIdAndDrugId(OrderDetail b);

	/**
	 * 批量获取详细订单
	 * @param orderIds
	 * @return
	 */
	List<Map<String, Object>> getListByOrderIds(@Param(value="orderIds")List<String> orderIds);
	
	
	Integer getListTotal_zg(Map<String, Object> params);
	List<OrderDetail> getList_zg(Map<String, Object> params);
	
	/**
	 * 订单详情
	 * @param params
	 * @return
	 */
	Integer getOrderDetailListTotal(Map<String, Object> params);
	List<Map<String, Object>> getOrderDetailList(Map<String, Object> params);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getTotalMoney(Map<String, Object> params);
	
}
