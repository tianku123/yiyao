package com.qm.omp.business.web.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.system.ISystemLogService;

/**
 * 系统日志aop 
 * @author rh
 * @date 2014-12-14 16:04:42
 */
@Aspect
public class SystemLogAOP {
	
	@Autowired
	private ISystemLogService systemLogService;
	
	@SuppressWarnings("unused")
	@Pointcut("execution(* com.qm.omp.business.service..*.*(..))")
	private void anyMethod(){}//定义一个切入点
	
	@Around(value="anyMethod() && @annotation(annotation)",argNames="annotation")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp,LogAnnotation annotation) throws Throwable{  
		Object object = pjp.proceed();//执行该方法  

        //日志保存的参数
		Object[] param = pjp.getArgs();
        
        Map<String, Object> log = new HashMap<String, Object>();
        log.put("menuName", annotation.menuName());
        log.put("method", annotation.method());
        log.put("description", annotation.description());
        String params = paramsHandler(annotation.params(),param);//方法参数
        log.put("params", params);
        log.put("fTime", DateTimeUtil.getTodayChar14());
        
        HttpServletRequest req = com.qm.omp.api.util.LogConstant.getRequest();
        UserInfoBean user = (UserInfoBean) SessionUtil.getObjectAttribute(req, RequestConstants.ADMIN_SESSION_KEY);
        String ipAddr = req.getRemoteHost();
        if(user!=null){
        	log.put("fUserCode", user.getfUserCode());
        }else{
        	log.put("fUserCode", param[0].toString());//刚登陆时session中还没有用户信息，这是就需要从拦截的方法中去拿
        }
        if(ipAddr!=null){
        	log.put("fIp", ipAddr);
        }
        systemLogService.saveLog(log);
        
        return object;  
    } 
	
	/**
	 * 参数处理，转换为String类型存储到数据库
	 * @param arr
	 * @param param 
	 * @return
	 */
	private String paramsHandler(String[] params, Object[] arr){
		List<String> res= new ArrayList<String>();
		if(arr!=null && arr.length!=0){
			for(int i=0;i<arr.length;i++){
				if(arr[i]!=null){
					if("[Ljava.lang.String;".equals(arr[i].getClass().getName())){//String数组处理
						String[] strArr = (String[]) arr[i];
						res.add(params[i]+":"+ArrayUtils.toString(strArr));
						continue;
					}
					res.add(params[i]+":"+arr[i].toString());
				}
			}
		}
		 return res.toString();
	}
}
