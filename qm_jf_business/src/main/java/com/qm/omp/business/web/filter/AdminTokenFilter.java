package com.qm.omp.business.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.business.pojo.admin.UserInfoBean;

/**
 * 登陆过滤器
 * @author rh
 * @date 2014-12-14 15:56:43
 */
public class AdminTokenFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//不过滤的URL
		String[] noFilter = {"login.jsp","login_code.jsp"};
		
		//请求的URL
		String uri = request.getRequestURI();
		//是否过滤
		boolean doFilter = true;
		for(String s : noFilter){
			if(uri.indexOf(s)!=-1){
				doFilter = false;
				break;
			}
		}
		if(doFilter){
			UserInfoBean user = (UserInfoBean) SessionUtil.getObjectAttribute(request, RequestConstants.ADMIN_SESSION_KEY);
			if(null == user){
				request.setCharacterEncoding("UTF-8");  
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");  
				PrintWriter out = response.getWriter();  
				String loginPage = request.getContextPath()+"/login.jsp";  
				StringBuilder builder = new StringBuilder();  
				builder.append("<script type=\"text/javascript\">");  
				builder.append("alert('网页过期，请重新登录！');");  
				builder.append("window.top.location.href='");  
				builder.append(loginPage);  
				builder.append("';");  
				builder.append("</script>");  
				out.print(builder.toString());  
			}else{
				filterChain.doFilter(request, response);
			}
		}else{
			filterChain.doFilter(request, response);
		}
	}

}
