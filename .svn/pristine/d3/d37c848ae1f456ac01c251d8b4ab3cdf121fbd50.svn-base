package org.hpin.security.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.bg.system.util.LogTool;
import org.hpin.common.core.SpringTool;
import org.hpin.common.util.PasswordMd5;

public class SpecialUserLogin extends HttpServlet {

	private UserService userService = (UserService)SpringTool.getBean(UserService.class) ;
	
	public SpecialUserLogin() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response) ;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		HttpSession session = request.getSession() ;
		System.out.println("*******************************进入特权帐号登录action*************************************") ;
		String username = request.getParameter("j_username").trim();
		String password = request.getParameter("j_password").trim();
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
			if("AMSadmin".equals(username) || "AMSadmin" == username){
				User _user = userService.loadUserByUsername("AMSadmin") ;
				User user = userService.loadUserByUsername("admin") ;
				if (PasswordMd5.authenticatePassword(_user.getPassword(), password)) {
					user = userService.loadSecurityUser(user);
					user.setLoginLogId(LogTool.saveLoginLog(request, user));
					session.setAttribute("currentUser", user);
					session.setAttribute("specialUserName", "AMSadmin") ;
					
				}else{
					RequestDispatcher dispatch = request.getRequestDispatcher("/login.jsp") ;
					dispatch.forward(request, response) ;
				}
			}else{
				RequestDispatcher dispatch = request.getRequestDispatcher("/login.jsp") ;
				dispatch.forward(request, response) ;
			}
		}else{
			RequestDispatcher dispatch = request.getRequestDispatcher("/login.jsp") ;
			dispatch.forward(request, response) ;
		}
	}
	 

	
	public void init() throws ServletException {
		
	}

}
