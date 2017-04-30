package org.hpin.bg.system.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;

/**
 * 用户接口
 * 
 * @author thinkpad
 * 
 */
public class Iuser {
	
	protected static final Logger logger = Logger.getLogger(Iuser.class) ;
	
	/**
	 * 获取用户
	 * 
	 * @return
	 */
	public static User getCurentUser() {
		User user = null ;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			user = (User) session.getAttribute("currentUser");
		}catch(Exception e){
			System.out.println(e.getMessage()) ;
			logger.error("Session中没有取出当前用户！") ;
		}
		return user;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static String getCurentUserId() {
		return getCurentUser().getId();
	}

	public static List findAllUser() throws Exception {
		UserService userService = (UserService) SpringTool
				.getBean(UserService.class);
		List list = userService.findAll();
		return list;
	}

}
