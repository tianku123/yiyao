package com.qm.omp.business.dao.drug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qm.omp.business.pojo.drug.Order;

@Repository("orderDao")
public interface IOrderDao {

	/**
	 * 分页查询
	 * @param fName
	 * @return
	 */
	Integer getListTotal(Map<String, Object> params);
	List<Order> getList(Map<String, Object> params);
	
	
	void save(Order bean);
	
	void edit(Order bean);
	
	/**
	 * 业务员
	 * @param params
	 * @return
	 */
	Integer getListTotal_ywy(Map<String, Object> params);
	List<Order> getList_ywy(Map<String, Object> params);
	
	
	/**
	 * 主管
	 * @param params
	 * @return
	 */
	Integer getListTotal_zg(Map<String, Object> params);
	List<Order> getList_zg(Map<String, Object> params);
	
	
	/**
	 * 政策报单审批
	 * @param params
	 * @return
	 */
	Integer getListTotal_policy(Map<String, Object> params);
	List<Order> getList_policy(Map<String, Object> params);
	
	/**
	 * 财务
	 * @param params
	 * @return
	 */
	Integer getListTotal_cw(Map<String, Object> params);
	List<Order> getList_cw(Map<String, Object> params);
	
	/**
	 * 发货
	 * @param params
	 * @return
	 */
	Integer getListTotal_fh(Map<String, Object> params);
	List<Order> getList_fh(Map<String, Object> params);
	
	/**
	 * 业务员未提交的订单
	 * @return
	 */
	List<Order> getList_State0(@Param(value="fState")Integer fState);
	
	/**
	 * 所有可以绩效的有效订单
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> salePerday(Map<String, Object> params);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> salesSummary(Map<String, Object> params);
	
	Order showIntro(Map<String, Object> params);
	
	Order getBeanById(String fOrderId);
	
	/**
	 * 财务复核
	 * @param params
	 */
	void examine(Map<String, Object> params);
	
	/**
	 * 销售明细
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getOrderDetail(Map<String, Object> params);
	
	
}
