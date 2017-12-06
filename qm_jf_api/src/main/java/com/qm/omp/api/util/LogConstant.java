package com.qm.omp.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * aop中获取httpservletrequest和HttpServletResponse
 * @author rh
 *
 */
public class LogConstant {

	private static ThreadLocal<HttpServletRequest> reqLocal = new ThreadLocal<HttpServletRequest>();
	
	private static ThreadLocal<HttpServletResponse> resLocal = new ThreadLocal<HttpServletResponse>();
	
	public static HttpServletRequest getRequest(){
		return reqLocal.get();
	}
	public static void setRequest(HttpServletRequest request){
		reqLocal.set(request);
	}
	public static HttpServletResponse getResponse(){
		return resLocal.get();
	}
	public static void setResponse(HttpServletResponse response){
		resLocal.set(response);
	}
	

}
