package com.qm.omp.business.invocation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.qm.omp.api.invocation.InvocationContext;

@Component("INVO_demo")
public class ActionDemo {
	
	/**
	 * http://localhost:9099/qm_jf_business/actionDispatcher.do?reqUrl=demo&reqMethod=test&reqType=action
	 * @param context
	 */
    public void test(InvocationContext context)
    {
    	HttpServletRequest request = context.getRequest();
    	HttpServletResponse response = context.getResponse();
    	request.setAttribute("test", "nihao");
    	try {
//			response.sendRedirect("/qm_jf_business/login.jsp");
    		request.getRequestDispatcher("/test.jsp").forward(request, response);
		} catch (Exception e) {
		}
    }

}
