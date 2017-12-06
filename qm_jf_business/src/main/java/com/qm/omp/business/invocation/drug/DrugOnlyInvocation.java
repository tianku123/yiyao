package com.qm.omp.business.invocation.drug;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.pojo.drug.DrugOnly;
import com.qm.omp.business.service.impl.drug.DrugOnlyServiceImpl;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;

/**
 * @ClassName: DrugOnlyInvocation
 * @Description: 药品管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_drugOnly")
public class DrugOnlyInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(DrugOnlyInvocation.class);
	
	@Autowired
	private DrugOnlyServiceImpl drugOnlyService;
	
	@Autowired
	private DrugServiceImpl drugService;

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
        String isHasTc = RequestUtil.getStrParamterAsDef(context.getRequest(), "isHasTc", "please");
        String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
        String fDrugPrinterName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugPrinterName", "");
        String fDrugIntroName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugIntroName", "");
        Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fName", fName);
    	params.put("fDrugPrinterName", fDrugPrinterName);
    	params.put("fDrugIntroName", fDrugIntroName);
    	params.put("isHasTc", isHasTc);
        try
        {
        	Map<String, Object> map = drugOnlyService.getList(page, rows, params);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("DrugOnlyInvocation getList happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    /**
     * 
     * @Title: getList_DrugOnlyIntro
     * @Description: (产品介绍)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList_DrugOnlyIntro(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fDrugPrinterName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugPrinterName", "");
    	String fDrugIntroName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugIntroName", "");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fName", fName);
    	params.put("fDrugPrinterName", fDrugPrinterName);
    	params.put("fDrugIntroName", fDrugIntroName);
    	UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
    	params.put("userId", userInfo.getId());
    	try
    	{
    		Map<String, Object> map = drugOnlyService.getList_DrugOnlyIntro(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugOnlyInvocation getList_DrugOnlyIntro happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 
     * @Title: getSelectedList
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getSelectedList(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fId", fId);
    	try
    	{
    		Map<String, Object> map = drugOnlyService.getSelectedList(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugOnlyInvocation getSelectedList happen execption.", ex);
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
    	
    	String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(), "fAddress", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fSpecification = RequestUtil.getStrParamterAsDef(context.getRequest(), "fSpecification", "");
    	
    	String fSupplyPrice = RequestUtil.getStrParamterAsDef(context.getRequest(), "fSupplyPrice", "");
    	String fRetailPrice = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRetailPrice", "");
    	String fIntro = RequestUtil.getStrParamterAsDef(context.getRequest(), "fIntro", "");
    	String fImage = RequestUtil.getStrParamterAsDef(context.getRequest(), "fImage", "");
    	int fDrugIntroId = RequestUtil.getIntParamter(context.getRequest(), "fDrugIntroId");
    	int fDrugPrinterId = RequestUtil.getIntParamter(context.getRequest(), "fDrugPrinterId");
    	int fDepartmentId = RequestUtil.getIntParamter(context.getRequest(), "fDepartmentId");
    	String fDqTc = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDqTc", "");
    	String fXqTc = RequestUtil.getStrParamterAsDef(context.getRequest(), "fXqTc", "");
    
    	DrugOnly bean = new DrugOnly();
    	bean.setfAddress(fAddress);
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	bean.setfSpecification(fSpecification);
    	bean.setfTime(DateTimeUtil.getTodayChar14());
    	bean.setfSupplyPrice(Double.parseDouble(fSupplyPrice));
    	bean.setfRetailPrice(Double.parseDouble(fRetailPrice));
    	bean.setfIntro(fIntro);
    	bean.setfImg(fImage);
    	bean.setfDrugIntroId(fDrugIntroId);
    	bean.setfDrugPrinterId(fDrugPrinterId);
    	// 主管提成
    	bean.setfXqTc(Double.parseDouble(fXqTc));
    	// 大区提成
    	bean.setfDqTc(Double.parseDouble(fDqTc));
    	// 部门
    	bean.setfDepartmentId(fDepartmentId);
    	try
    	{
    		drugOnlyService.save(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugOnlyInvocation save happen execption.", ex);
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
    	String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(), "fAddress", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fSpecification = RequestUtil.getStrParamterAsDef(context.getRequest(), "fSpecification", "");
    	
    	String fSupplyPrice = RequestUtil.getStrParamterAsDef(context.getRequest(), "fSupplyPrice", "");
    	String fRetailPrice = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRetailPrice", "");
    	String fIntro = RequestUtil.getStrParamterAsDef(context.getRequest(), "fIntro", "");
    	String fImage = RequestUtil.getStrParamterAsDef(context.getRequest(), "fImage", "");
    	int fDrugIntroId = RequestUtil.getIntParamter(context.getRequest(), "fDrugIntroId");
    	int fDrugPrinterId = RequestUtil.getIntParamter(context.getRequest(), "fDrugPrinterId");
    	int fDepartmentId = RequestUtil.getIntParamter(context.getRequest(), "fDepartmentId");
    	String fDqTc = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDqTc", "");
    	String fXqTc = RequestUtil.getStrParamterAsDef(context.getRequest(), "fXqTc", "");
    
    	DrugOnly bean = new DrugOnly();
    	bean.setfAddress(fAddress);
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	//bean.setfTime(DateTimeUtil.getTodayChar14());
    	bean.setfSpecification(fSpecification);
    	bean.setfId(fId);
    	bean.setfSupplyPrice(Double.parseDouble(fSupplyPrice));
    	bean.setfRetailPrice(Double.parseDouble(fRetailPrice));
    	bean.setfIntro(fIntro);
    	bean.setfImg(fImage);
    	bean.setfDrugIntroId(fDrugIntroId);
    	bean.setfDrugPrinterId(fDrugPrinterId);
    	// 主管提成
    	bean.setfXqTc(Double.parseDouble(fXqTc));
    	// 大区提成
    	bean.setfDqTc(Double.parseDouble(fDqTc));
    	// 部门
    	bean.setfDepartmentId(fDepartmentId);
    	try
    	{
    		drugOnlyService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugOnlyInvocation edit happen execption.", ex);
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
    	DrugOnly bean = new DrugOnly();
    	bean.setfState(1+"");
    	bean.setfId(fId);
    	try
    	{
    		drugOnlyService.edit(bean);
    		/**
    		 * 删除对应的药品权限关联表数据
    		 */
    		drugService.deleteUserDrugByDrugOnlyId(fId);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DrugOnlyInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
   
}
