package com.qm.omp.business.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.pojo.drug.Order;
import com.qm.omp.business.pojo.drug.OrderDetail;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderDetailServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderServiceImpl;

/**
 * 心跳，判断终端是否正常连接，每两分钟执行一次
 * @author Administrator
 *
 */
public class DeleteOrderTimerTask extends TimerTask {
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private OrderDetailServiceImpl orderDetailService;
	
	@Autowired
	private DrugServiceImpl drugService;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public void run() {
		System.out.println("DeleteOrderTimerTask====run");
//		ywy10Minu();
		cw12Minu();
	}
	
	/**
	 * 业务员未提交的订单，超过十分钟未提交则删除订单，恢复库存
	 */
	private void ywy10Minu() {
		long now = new Date().getTime();
		List<Order> list = this.orderService.getList_State0(0);//查询所有业务员未提交的订单，超过十分钟未提交则删除订单，恢复库存
		Order bean = new Order();
		for(Order m : list){
			String time = m.getfTime();
			String orderId = m.getfId();
			try {
				if(time!=null && now - format.parse(time).getTime()>1800000){//时差大于30分钟则删除订单恢复库存
					bean = new Order();
					bean.setfId(orderId);
					bean.setfState("9");//业务员未提交被删除
					orderService.edit(bean);
					//退单恢复订单内所有药品的库存
		    		List<OrderDetail> detailList = this.orderDetailService.getListByOrderId(orderId);
		    		for(OrderDetail detail : detailList){
		    			this.drugService.addNumber(detail.getfDrugId(), detail.getfNumber());
		    		}
				}
			} catch (ParseException e) {
			}
		}
	}
	
	/**
	 * 业务员提交的订单，超过24小时未审核的则删除订单，恢复库存
	 */
	private void cw12Minu() {
		long now = new Date().getTime();
		List<Order> list = this.orderService.getList_State0(1);//提交财务未审批的订单
		List<Order> policyList = this.orderService.getList_State0(10);//提交政策报单未审批的订单
		list.addAll(policyList);
		Order bean = new Order();
		Drug stock;
		for(Order m : list){
			String time = m.getfSaleTime();// 以提交给财务审批的时间为准
			String orderId = m.getfId();
			try {
				if(time!=null && now - format.parse(time).getTime()> 24 * 60 * 60 * 1000){//超时则删除订单恢复库存
					bean = new Order();
					bean.setfId(orderId);
					bean.setfState("8");//超时退单状态
					orderService.edit(bean);
					//退单恢复订单内所有药品的库存
					List<OrderDetail> detailList = this.orderDetailService.getListByOrderId(orderId);
					for(OrderDetail detail : detailList){
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
				}
			} catch (ParseException e) {
			}
		}
	}
	

}
