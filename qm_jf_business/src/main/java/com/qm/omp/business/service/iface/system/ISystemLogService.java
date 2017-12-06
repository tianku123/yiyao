package com.qm.omp.business.service.iface.system;

import java.util.Map;

/**
 * 日志管理类
 * @author Administrator
 *
 */
public interface ISystemLogService {

	/**
	 * 保存日志
	 * @param log
	 */
	void saveLog(Map<String, Object> log);

	/**
	 * 查询系统日志
	 * @param fUserCode
	 * @param fUserName
	 * @param page
	 * @param rows
	 * @return
	 */
	Map<String, Object> queryLogList(String fUserCode, String fUserName,String beginTime,String endTime,
			int page, int rows);

	/**
	 * 删除日志
	 * @param fUserCode
	 * @param fUserName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	void deleteLog(String fUserCode, String fUserName,
			String beginTime, String endTime);

}
