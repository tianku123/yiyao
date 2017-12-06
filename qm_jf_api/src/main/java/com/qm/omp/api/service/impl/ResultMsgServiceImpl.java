package com.qm.omp.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.dao.ResultMsgMapper;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.api.service.IResultMsgService;
import com.qm.omp.api.web.BaseWebProperties;

/**
 * @ClassName: ResultMsgServiceImpl
 * @Description: 
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
@Service("resultMsgService")
public class ResultMsgServiceImpl implements IResultMsgService {
    protected static final Logger logger = LoggerFactory.getLogger(ResultMsgServiceImpl.class);

    @Autowired
    private ResultMsgMapper resultMsgMapper;

    public void formatResultMsg(InvocationResult result) {
        String resultMsg = "";
        try {
            if (result != null) {
                if (StringUtils.isBlank(result.getB())) {
                    resultMsg = "";
                } else {
                    resultMsg = queryResultMsgInfo(result.getB(), result.getE());
                    if (StringUtils.isBlank(resultMsg)) {
                        if(ISystemConstants.SYS_SUCCESS.equals(result.getA()))
                        {
                            resultMsg = "操作成功！";
                        }
                        else
                        {
                            resultMsg = "系统忙，请稍候再试！";
                        }
                    }
                }
            }
        } catch (Exception ex) {
            //resultMsg = "系统忙，请稍候再试！";
            logger.error("ResultMsgServiceImpl formatResultMsg happen execption.", ex);
        }

        result.setResMsg(resultMsg);
    }

    private String queryResultMsgInfo(String systemCode, String logicCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subSysNum", BaseWebProperties.SYS_NUM);
        map.put("systemCode", systemCode);
        map.put("logicCode", logicCode);
        String resultMsg = resultMsgMapper.queryResultMsgInfo4Mem(map);
        return resultMsg;
    }
}
