 package org.hpin.security.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.bg.system.util.LogTool;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ad接口，AD接口说明，AD原理，访问系统页面A，通过web.xml的配置Ad认证配置的收保护资源。
 * 如果是收保护资源，AD获取A页面的路径及用户名和密码，如果验证通过，request.getUserPrincipal()能够获取用户凭证，
 * request.getUserPrincipal().getName()
 * 能够获取用户登录帐号，只要验证通过，request.getUserPrincipal
 * ()就不为空，负责request.getUserPrincipal()为空。 验证通过之后，系统返回A页面，否则返回登录页面。
 * 
 * @author thinkpad
 * 
 */
public class SecurityAdFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAdFilter.class) ;

	protected FilterConfig filterConfig = null;

	private String notCheckLoginUrl = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		// 获取当前访问URL
		String currentUrl = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		if (notCheckLoginUrl.indexOf(currentUrl) >= 0) {
			if(currentUrl.indexOf("j_security_check") >= 0){
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
		
		System.out.println("***********当前请求的URL为：***********" + currentUrl) ;
		
		if(currentUrl.indexOf("/checkPaymentVoucher/checkPaymentVoucher") >= 0){
			filterChain.doFilter(servletRequest, servletResponse) ;
			return ;
		}
		
		// 退出操作(此代码段中增加有特权帐号)
		if (null == request.getUserPrincipal()
				&& null != request.getSession().getAttribute("currentUser") ) {
			if(null == request.getSession().getAttribute("specialUserName")){
				request.getSession().invalidate();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				PrintWriter out = response.getWriter();
				out.print("<script>top.window.location.href='"
						+ request.getContextPath() + "/login.jsp'</script>");
			}
		}
		// 登录操作，获取用户并且将用户存放到session中
		if (null != request.getUserPrincipal()
				&& null == request.getSession().getAttribute("currentUser")) {
			LOGGER.info("&&&&&&AD返回的userName是【" + request.getUserPrincipal().getName() + "】" ) ;
			UserService userService = (UserService) SpringTool
					.getBean(UserService.class);
			User user = userService.loadUserByUsername(request
					.getUserPrincipal().getName());
			LOGGER.info("通过AD返回值，找到系统的用户为【" + user + "】") ;
			if (null == user) {
				user = userService.findByHlUser(request.getUserPrincipal()
						.getName());
			}
			if (null == user) {
				request.getSession().invalidate();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				PrintWriter out = response.getWriter();
				out.print("<script>top.window.location.href='"
						+ request.getContextPath() + "/login.jsp'</script>");
				out.close();
				return;
			}
			user = userService.loadSecurityUser(user);
			user.setLoginLogId(LogTool.saveLoginLog(request, user));
			request.getSession().setAttribute("currentUser", user);
		}
		// 没有登录的用户跳转到登陆页面
		if (null == request.getSession().getAttribute("currentUser")) {
			PrintWriter out = response.getWriter();
			out.println("<script>top.location.href = '"
					+ request.getContextPath() + "/login.jsp" + "'</script>");
			out.close();
			return;
		}
		if (currentUrl.indexOf("login.jsp") >= 0) {
			PrintWriter out = response.getWriter();
			out.print("<script>top.window.location.href='"
					+ request.getContextPath()
					+ "/security/security!index.action'</script>");
			out.close();
			return;
		}
		// 过滤掉当前访问URL后面参数
		currentUrl = currentUrl.substring(1);
		if (currentUrl.lastIndexOf("?") > 0) {
			currentUrl = currentUrl.substring(0, currentUrl.lastIndexOf("?"));
		}
		User user = (User) request.getSession().getAttribute("currentUser");
		// 对需要安全控制的URL进行验证
		if(user.getResources().indexOf(currentUrl) < 0 && SystemConstant.sys_resources.indexOf(currentUrl) > 0){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out
					.println("<script>alert('您没有权限访问，请联系系统管理员!');top.location.href = '"
							+ request.getContextPath()
							+ "/login.jsp"
							+ "'</script>");
			out.close();
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		//notCheckLoginUrl = SystemConstant.getSystemConstant("not_check_url_list");
	}
}
