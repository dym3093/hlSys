package org.hpin.security.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SystemConstant;

/**
 * @author : 胡五音
 * 
 * 
 */
public class SecurityAdFilterBak implements Filter {

	protected FilterConfig filterConfig = null;

	private String notCheckLoginUrl = null;
	
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 获取当前访问URL
		String currentUrl = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		if (notCheckLoginUrl.indexOf(currentUrl) >= 0) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		//登录
		if (null == request.getSession().getAttribute("currentUser")) {
			
		}else{
			filterChain.doFilter(servletRequest, servletResponse) ;
		}
		
		currentUrl = currentUrl.substring(1);
		if (currentUrl.lastIndexOf("?") > 0) {
			currentUrl = currentUrl.substring(0, currentUrl.lastIndexOf("?"));
		}
		User user = (User) request.getSession().getAttribute("currentUser");
		// 对需要安全控制的URL进行验证
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		notCheckLoginUrl = SystemConstant
				.getSystemConstant("not_check_url_list");
	}

	public void destroy() {

	}

}
