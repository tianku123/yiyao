package com.qm.omp.business.invocation.drug;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.drug.DrugIntro;
import com.qm.omp.business.service.impl.drug.DrugIntroServiceImpl;

/**
 * @ClassName: DrugIntroInvocation
 * @Description: 药品介绍分类
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_drugIntro")
public class DrugIntroInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(DrugIntroInvocation.class);
	
	@Autowired
	private DrugIntroServiceImpl drugIntroService;
	

    /**
     * 
     * @Title: getList
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
        int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
        String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
        try
        {
        	Map<String, Object> map = drugIntroService.getList(fName, page, rows);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("DrugIntroInvocation getList happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    /**
     * 添加bean
     * @param context
     * @return
     */
    public InvocationResult save(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	DrugIntro bean = new DrugIntro();
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	bean.setfTime(DateTimeUtil.getTodayChar14());
    	try
    	{
    		drugIntroService.save(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugIntroInvocation save happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 编辑
     * @param context
     * @return
     */
    public InvocationResult edit(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	
    	int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	DrugIntro bean = new DrugIntro();
    	bean.setfId(fId);
    	bean.setfName(fName);
    	try
    	{
    		drugIntroService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugIntroInvocation edit happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 删除（软删除）
     * @param context
     * @return
     */
    public InvocationResult delete(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	
    	int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
    	DrugIntro bean = new DrugIntro();
    	bean.setfState(1+"");
    	bean.setfId(fId);
    	try
    	{
    		drugIntroService.delete(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugIntroInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
   
}
