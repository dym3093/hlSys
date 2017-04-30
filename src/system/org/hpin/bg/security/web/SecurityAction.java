package org.hpin.bg.security.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.priv.service.AssignService;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.bg.system.util.Iuser;
import org.hpin.bg.system.util.LogTool;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PasswordMd5;
import org.hpin.common.util.StrUtils;

/**
 * 安全Action
 * 
 * @author thinkpad
 * @data 2009-9-4
 */
@Namespace("/security")
@Action("security")
@Results({
		@Result(name = "index", location = "/WEB-INF/index.jsp"),
		@Result(name = "index_cs", location = "/WEB-INF/index_cs.jsp"),
		@Result(name = "login", location = "/login.jsp"),
		@Result(name = "login_user", location = "/login_user.jsp"),
		@Result(name = "indexForCustomerService", location = "/WEB-INF/indexForCustomerService.jsp"),
		@Result(name = "dlzyPortal", location = "/WEB-INF/portal/dlzyPortal.jsp"),
		@Result(name = "dlzlPortal", location = "/WEB-INF/portal/dlzlPortal.jsp"),
		@Result(name = "htzyPortal", location = "/WEB-INF/portal/htzyPortal.jsp"),
		@Result(name = "ddyPortal", location = "/WEB-INF/portal/ddyPortal.jsp"),
		//@Result(name = "commonPortal", location = "/WEB-INF/portal/commonPortal.jsp"),
		@Result(name = "commonPortal", location = "/WEB-INF/pages/butlerTable/butleDetail.jsp"),
		@Result(name = "showUpdatePassword", location = "/WEB-INF/showUpdatePassword.jsp"),
		@Result(name = "left", location = "/left.jsp"),
		@Result(name = "menus", location = "/menus.jsp") })
public class SecurityAction extends BaseAction {

	private UserService userService = (UserService) SpringTool
			.getBean(UserService.class);
	
	private ResourceService resourceService = (ResourceService)SpringTool.getBean(ResourceService.class) ;

	private AssignService assignService = (AssignService)SpringTool.getBean(AssignService.class) ;
	/**
	 * 登录
	 */
	public String login() throws Exception {
		HttpSession session = ServletActionContext.getRequest()
				.getSession(true);
		
		String username  = HttpTool.getParameter("j_username", "").trim();
		String password  = HttpTool.getParameter("j_password", "").trim();
		String extension = HttpTool.getParameter("j_extension", "").trim();
		String returnValue = "login";
		if (StrUtils.isNotNullOrBlank(extension)) returnValue = "login_user";
		String verifyCode = HttpTool.getParameter("verifyCode" , "").trim().toUpperCase() ;
		if(!verifyCode.equals( session.getAttribute("RANDOMVALIDATECODEKEY"))){
			HttpTool.setAttribute("error" , "验证码输入错误！") ;
			return "login" ;
		}

		// 检验用户名和密码是否为空
		if (StrUtils.isNullOrBlank(username)
				|| StrUtils.isNullOrBlank(password)) {
			return returnValue;
		}
		// 根据用户加载用户
		User user = userService.loadUserByUsername(username); 
		
		//System.out.println(user.getDeptId());
		// 登录失败
		if (null == user) {
			HttpTool.setAttribute("error", "不存在此用户!");
			return returnValue;
		}
		if (!PasswordMd5.authenticatePassword(user.getPassword(), password)) {
			HttpTool.setAttribute("error", "密码错误!");
			return returnValue;
		}
		if (null != user.getIsEnable() && user.getIsEnable() == 0) {
			HttpTool.setAttribute("error", "该账户已经停用，请联系管理员!");
			return returnValue;
		}
		/*if(StrUtils.isNotNullOrBlank(extension)){
			user.setExtension(extension);
		}else{
			user.setExtension("");
		}*/
		// 组装用户

		user = userService.loadSecurityUser(user);
		
		user.setLoginLogId(LogTool.saveLoginLog(ServletActionContext.getRequest(), user));
		System.out.println("用户数据准备前");
		session.setAttribute("currentUser", user);
		System.out.println("用户数据准备后");
		
		
		session.setAttribute("style","default");
		List<Resource> list = resourceService.findBaseResource() ;
		StringBuffer sb = new StringBuffer() ;
		for(Resource r : list){
			sb.append(r.getId()).append(",") ;
		}
		HttpTool.setAttribute("resourceStr" , sb.substring(0 , sb.length() - 1)) ;
		
//		if(StrUtils.isNullOrBlank(extension)) {
			return jump("security!index.action", null);
//		}
//		else {
//			return "indexForCustomerService";
//		}
	}
	
	public void getVerify(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片  
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容  
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expire", 0); 
        RandomValidateCode randomValidateCode = new RandomValidateCode(); 
        try { 
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法  
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	/**
	 * 显示首页页面
	 */
	public String index() throws Exception {
		
		return "index";
	}
	
	/**
	 * 显示首页页面
	 */
	public String indexForCustomerService() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		session.setAttribute("currentUser", session.getAttribute("currentUser"));
		session.setAttribute("style","default");
		List<Resource> list = resourceService.findBaseResource();
		StringBuffer sb = new StringBuffer();
		for(Resource r : list){
			sb.append(r.getId()).append(",");
		}
		HttpTool.setAttribute("resourceStr" , sb.substring(0 , sb.length() - 1)) ;
		return "index";
	}
	
	/**
	 * 显示旧首页页面
	 */
	public String index_old() throws Exception {
		HttpSession session = ServletActionContext.getRequest()
				.getSession(true);
		session.setAttribute("currentUser", session.getAttribute("currentUser"));
		session.setAttribute("style","old");
		return "index_old";
	}

	public String indexOfSpecial(HttpSession session) throws Exception {
		User u = (User) session.getAttribute("currentUser");
		return "index";
	}

	/**
	 * 显示portal页面
	 */
	public String portal() throws Exception {
		return "commonPortal";
	}

	/**
	 * 注销
	 */
	public String logout() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		//登出时记录登出日志
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser"); 
		String jobNumber = currentUser.getJobNumber();
//		LogTool.saveLogoutLog(ServletActionContext.getRequest(), currentUser);
		session.invalidate();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
		PrintWriter out = response.getWriter();
//		if(StrUtils.isNotNullOrBlank(jobNumber)) {
//			out.print("<script>top.window.location.href='"
//					+ ServletActionContext.getRequest().getContextPath()
//					+ "/login_user.jsp'</script>");
//		}
//		else {
			out.print("<script>top.window.location.href='"
				+ ServletActionContext.getRequest().getContextPath()
				+ "/security/security!login.action'</script>");
		//}
		return null;
	}

	/**
	 * 根据模块ID显示菜单树
	 */
	public String treeResource() throws Exception {
		String parentId = HttpTool.getParameter("parentId");
		// 遍历用户可以访问的所有菜单
		List<Resource> menus = Iuser.getCurentUser().getResourceList();
		StringBuffer json = new StringBuffer("[");
		for (int i = 0; i < menus.size(); i++) {
			if (null != menus.get(i).getParentId()
					&& parentId.equals(menus.get(i).getParentId().toString())) {
				json.append("{\"text\":" + "\"" + menus.get(i).getName() + "\""
						+ "," + "\"id\":" + "\"" + menus.get(i).getId() + "\""
						+ ",\"leaf\":"
						+ (menus.get(i).getIsLeaf() == 1 ? true : false));
				if (menus.get(i).getIsLeaf() == 0) {
				} else {
					json.append(",\"url\":\"" + menus.get(i).getUrl() + "\"");
				}
				json.append(",\"iconCls\":\"" + menus.get(i).getIconCls()
						+ "\"");
				json.append("},");
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}
		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}

	/**
	 * 显示修改密码页面
	 */
	public String showUpdatePassword() throws Exception {
		return "showUpdatePassword";
	}

	/**
	 * 修改密码页面
	 */
	public String updatePassword_old() throws Exception {
		String newPassword1 = HttpTool.getParameter("newPassword1", "").trim();
		String newPassword2 = HttpTool.getParameter("newPassword2", "").trim();
		if (!newPassword1.equals(newPassword2)) {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('密码输入不一致!');</script>");
			out.flush();
			out.close();
		}
		//Long userId = Iuser.getCurentUserId();
		String userId = String.valueOf(Iuser.getCurentUserId());
		User user = (User) userService.findById(userId);
		newPassword1 = PasswordMd5.createPassword(newPassword1);
		user.setPassword(newPassword1);
		userService.update(user);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.invalidate();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
		PrintWriter out = response.getWriter();
		out.print("<script>alert('修改密码成功，请重新登录!');top.window.location.href='"+ ServletActionContext.getRequest().getContextPath()+ "/login.jsp';</script>");
		out.flush();
		out.close();
		return null;
	}
	
	public String updatePassword() throws Exception {
		String newPassword1 = HttpTool.getParameter("newPassword1", "").trim();
		String newPassword2 = HttpTool.getParameter("newPassword2", "").trim();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		try {
			if (!newPassword1.equals(newPassword2)) {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('密码输入不一致!');</script>");
				out.flush();
				out.close();
			}
			else {
				User user = (User) userService.findById(currentUser.getId());
				newPassword1 = PasswordMd5.createPassword(newPassword1);
				user.setPassword(newPassword1);
				userService.update(user);
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.invalidate();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				PrintWriter out = response.getWriter();
				out.print("<script>alert('修改密码成功，请重新登录!');top.window.location.href='"+ ServletActionContext.getRequest().getContextPath()+ "/login.jsp';</script>");
				out.flush();
	    		out.close();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 根据模块ID显示菜单树
	 */
	public String getResources() throws Exception {
		String parentIds = HttpTool.getParameter("parentIds");
		String[] arrIds = parentIds.split(",");
		
		
		Set<String> setIds = new HashSet<String>();
		CollectionUtils.addAll(setIds, arrIds);
		List<Resource> parentMunus = new ArrayList<Resource>();
		
		Map<Long,List<Resource>> subMenus = new HashMap<Long,List<Resource>>();
		for(String parentId: arrIds){
			subMenus.put(Long.valueOf(parentId), new ArrayList<Resource>());
		}

		// 遍历用户可以访问的所有菜单
		List<Resource> menus = Iuser.getCurentUser().getResourceList();
		StringBuffer json = new StringBuffer("[");
		for (int i = 0; i < menus.size(); i++) {
			Resource menu = menus.get(i);
			if(setIds.contains(menu.getId().toString())){
				parentMunus.add(menu);
			}
			if (null != menu.getParentId()
					&& setIds.contains(menu.getParentId().toString())) {
				List<Resource> tmp = subMenus.get(menu.getParentId());
				tmp.add(menu);
			}
		}
		
		ServletActionContext.getRequest().setAttribute("subMenus", subMenus);
		ServletActionContext.getRequest().setAttribute("parentMunus", parentMunus);
		return "left";
	}
	
	/**
	 * 根据模块ID显示菜单树
	 */
	public String getNextResources() throws Exception {
		String parentId = HttpTool.getParameter("parentId");
		
		// 遍历用户可以访问的所有菜单
		List<Resource> menus = Iuser.getCurentUser().getResourceList();
		
		List<Resource> subMenus = new ArrayList<Resource>();
		for (int i = 0; i < menus.size(); i++) {
			Resource menu = menus.get(i);
			//menu = (Resource)resourceService.findById(menu.getId()) ;
			if(menu.getId().toString().equals(parentId)){
				ServletActionContext.getRequest().setAttribute("parentMenu", menu);
			}
			if (menu.getParentId()!=null && menu.getParentId().toString().equals(parentId)) {
				subMenus.add(menu);
			}
		}
		
		ServletActionContext.getRequest().setAttribute("subMenus", subMenus);
		ServletActionContext.getRequest().setAttribute("userUrls" , Iuser.getCurentUser().getResources()) ;
		return "menus";
	}
}
