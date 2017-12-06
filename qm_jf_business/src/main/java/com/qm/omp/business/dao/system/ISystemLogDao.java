package com.qm.omp.business.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("systemLogDao")
public interface ISystemLogDao {

	/**
	 * 保存日志
	 * @param log
	 */
	void saveLog(Map<String, Object> log);

	/**
	 * 日志条数
	 * @param fUserCode
	 * @param fUserName
	 * @return
	 */
	int getLogListTotal(@Param(value="fUserCode")String fUserCode, 
			@Param(value="fUserName")String fUserName,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime
			);

	/**
	 * 查询系统日志
	 * @param fUserCode
	 * @param fUserName
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> queryLogList(
			@Param(value="fUserCode")String fUserCode, 
			@Param(value="fUserName")String fUserName, 
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="page")int page, 
			@Param(value="rows")int rows);

	/**
	 * 删除日志
	 * @param fUserCode
	 * @param fUserName
	 * @param beginTime
	 * @param endTime
	 */
	void deleteLog(@Param(value="fUserCode")String fUserCode, 
			@Param(value="fUserName")String fUserName, 
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime);

}
