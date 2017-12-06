package com.qm.omp.business.service.impl.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qm.omp.business.dao.system.ISystemLogDao;
import com.qm.omp.business.service.iface.system.ISystemLogService;
import com.qm.omp.business.util.DateTimeUtil;
import com.qm.omp.business.web.aop.LogAnnotation;
@Transactional
@Service("systemLogService")
public class SystemLogServiceImpl implements ISystemLogService {

	@Autowired
	private ISystemLogDao systemLogDao;
	
	@Override
	public void saveLog(Map<String, Object> log) {
		systemLogDao.saveLog(log);
	}
	
	
	@LogAnnotation(description = "查询后台系统操作日志", menuName = "系统管理--系统日志", method = "查询后台系统操作日志", params = { "登录名", "用户名", "开始时间", "结束时间", "当前页", "每页行数" })
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> queryLogList(String fUserCode, String fUserName,String beginTime,String endTime,
			int page, int rows) {
		beginTime = DateTimeUtil.get_yyyyMMddHHmmss(beginTime);
		endTime = DateTimeUtil.get_yyyyMMddHHmmss(endTime);
		page = (page-1)*rows;
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", this.systemLogDao.getLogListTotal(fUserCode,fUserName,beginTime,endTime));
		res.put("rows", this.systemLogDao.queryLogList(fUserCode,fUserName,beginTime,endTime,page,rows));
		return res;
	}

	/**
	 * 删除日志
	 */
	
	@LogAnnotation(description = "删除后台系统操作日志", menuName = "系统管理--系统日志", method = "删除后台系统操作日志", params = { "登录名", "用户名", "开始时间", "结束时间" })
	@Override
	public void deleteLog(String fUserCode, String fUserName,
			String beginTime, String endTime) {
		if(beginTime!=null && "".equals("")){
			beginTime = DateTimeUtil.get_yyyyMMddHHmmss(beginTime);
			endTime = DateTimeUtil.get_yyyyMMddHHmmss(endTime);
		}
		this.systemLogDao.deleteLog(fUserCode, fUserName, beginTime, endTime);
	}

}
