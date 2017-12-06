package com.qm.omp.api.invocation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: InvocationContext
 * @Description: 请求处理上下文
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class InvocationContext {
    private HttpServletRequest request;

    private HttpServletResponse response;

    private Map<String, String> reqParam;

    public InvocationContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map<String, String> getReqParam() {
        return reqParam;
    }

    public void setReqParam(Map<String, String> reqParam) {
        this.reqParam = reqParam;
    }
}
