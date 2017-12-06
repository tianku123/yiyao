package com.qm.omp.api.dao;

import java.util.Map;

public interface ResultMsgMapper
{
    /**
     * @Title: queryResultMsgInfo
     * @Description: 查询结果提示信息
     * @param map
     * @return String 返回类型
     * @throws
     */
    public String queryResultMsgInfo4Mem(Map<String, Object> map);
}
