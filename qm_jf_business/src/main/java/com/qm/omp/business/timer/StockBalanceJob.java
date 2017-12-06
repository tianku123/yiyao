package com.qm.omp.business.timer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;
import com.qm.omp.business.util.DateTimeUtil;
/**
 * 库存每月结余，流转到下月的定时任务
 * @author Administrator
 *
 */
public class StockBalanceJob {
	
	@Autowired
	private DrugServiceImpl drugService;
	
	private Logger logger = Logger.getLogger(StockBalanceJob.class);
	
	public void stock()
	{
		System.out.println("==stockBalancejob=====>run==");
		/*
		 * 库存状态 
		 * 0：正常入库状态或当月入库当月销售完的状态；
		 * 1：添加错误删除状态；
		 * 2：正常入库当月未销售完则通过定时任务在月底，
		 * 		自动复制改库存信息（此库存信息状态置为2）生成一个新的库存信息（此库存信息置为3，F_NUMBER_BAK，F_NUMBER字段值为上月结余数，F_BALANCE_ID的值为被复制记录的id）
		 * 		到下个月的日期成为下个月库存信息里面的上月结余库存正常销售。
		 * 3：上月结余库存，拥有上月的剩余库存数和一切其他信息（参考2的解释）
		 */
		//此定时器任务就是2和3状态的转换
		String yyyyMM = DateTimeUtil.getLastMonth("yyyyMM");//上个月信息
		List<Drug> list = this.drugService.getListByTime(yyyyMM);
		if(list != null && list.size()>0){
			
			//状态变为2的上月库存id们
			List<Integer> idList = new ArrayList<Integer>();
			Drug source = new Drug();
			source.setfState("2");
			for(Drug bean : list){
				idList.add(bean.getfId());
				bean.setfBalanceId(bean.getfId());
				bean.setfNumberBak(bean.getfNumber());
				bean.setfState("3");
				bean.setfTime(DateTimeUtil.getTodayChar14());
				// 库存为零了就不结转到下个月
				this.drugService.save(bean);
				
				source.setfId(bean.getfId());
				this.drugService.editState(source);
				System.out.println(bean.getfId() + ":" + bean.getfName());
			}
		}
		
	}
	
	public static void main(String args[])
	{
		
	}
}
