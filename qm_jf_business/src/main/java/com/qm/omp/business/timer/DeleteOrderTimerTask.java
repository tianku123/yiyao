package com.qm.omp.business.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

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
		    			this.drugService.minusNumber(detail.getfDrugId(), detail.getfNumber());
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
		List<Order> list = this.orderService.getList_State0(1);//查询所有业务员未提交的订单，超过十分钟未提交则删除订单，恢复库存
		Order bean = new Order();
		for(Order m : list){
			String time = m.getfTime();
			String orderId = m.getfId();
			try {
				if(time!=null && now - format.parse(time).getTime()> 24 * 60 * 60 * 1000){//时差大于30分钟则删除订单恢复库存
					bean = new Order();
					bean.setfId(orderId);
					bean.setfState("8");//业务员提交的订单，超过12小时未审核的则删除订单，恢复库存
					orderService.edit(bean);
					//退单恢复订单内所有药品的库存
					List<OrderDetail> detailList = this.orderDetailService.getListByOrderId(orderId);
					for(OrderDetail detail : detailList){
						this.drugService.minusNumber(detail.getfDrugId(), detail.getfNumber());
					}
				}
			} catch (ParseException e) {
			}
		}
	}
	

}
