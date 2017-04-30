package org.hpin.base.usermanager.web;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.BigRole;
import org.hpin.base.usermanager.entity.BigRoleAndRole;
import org.hpin.base.usermanager.entity.UserBigRole;
import org.hpin.base.usermanager.service.BigRoleAndRoleService;
import org.hpin.base.usermanager.service.BigRoleService;
import org.hpin.base.usermanager.service.UserBigRoleService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.exception.SystemException;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;

/**
 * <p>@desc : 角色Action</p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-11-18 上午11:36:02</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Namespace("/um")
@Action("bigRole")
@Results({
	@Result(name = "listBigRole" , location = "/WEB-INF/content/userManager/role/listBigRole.jsp") , 
	@Result(name = "addBigRole" , location = "/WEB-INF/content/userManager/role/addBigRole.jsp") ,
	@Result(name = "modifyBigRole" , location = "/WEB-INF/content/userManager/role/modifyBigRole.jsp") 
})
public class BigRoleAction extends BaseAction {

	private BigRoleService bigRoleService = (BigRoleService)SpringTool.getBean(BigRoleService.class) ;
	
	private BigRoleAndRoleService bigRoleAndRoleService = (BigRoleAndRoleService)SpringTool.getBean(BigRoleAndRoleService.class) ;
	
	private UserBigRoleService userBigRoleService = (UserBigRoleService)SpringTool.getBean(UserBigRoleService.class) ;
	
	private BigRole bigRole = null ;
	
	public BigRole getBigRole() {
		return bigRole;
	}

	public void setBigRole(BigRole bigRole) {
		this.bigRole = bigRole;
	}

	/**
	 * 打开角色管理主页面
	 * @return
	 * @throws SystemException
	 */
	public String listBigRole() throws SystemException , Exception{
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize()) ;
		Map searchMap = buildSearch() ;
		bigRoleService.findByPage(page , searchMap) ;
		return "listBigRole" ;
	}
	
	/**
	 * 打开新增角色页面
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String addBigRole() throws SystemException , Exception{
		return "addBigRole" ;
	}
	
	/**
	 * 保存角色
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String saveBigRole() throws SystemException , Exception{
		String moduleIds = HttpTool.getParameter("moduleIds") ;
		String userIds = HttpTool.getParameter("userIds") ;
		bigRoleService.saveBigRoleInfo(bigRole, moduleIds, userIds) ;
		return jump("bigRole!listBigRole.action") ;
	}
	
	/**
	 * 打开修改角色页面
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String modifyBigRole() throws SystemException , Exception{
		bigRole = (BigRole)bigRoleService.findById(id) ;
		List<UserBigRole> userBigRoles = userBigRoleService.getUserBigRoleListByBigRoleId(id) ;
		List<BigRoleAndRole> brrList = bigRoleAndRoleService.getBigRoleAndRoleListByBigRoleId(id) ;
		HttpTool.setAttribute("userBigRoles", userBigRoles) ;
		HttpTool.setAttribute("brrList", brrList) ;
		StringBuffer userIdBuffer = new StringBuffer() ;
		StringBuffer moduleIdBuffer = new StringBuffer() ;
		for(UserBigRole userBigRole : userBigRoles){
			userIdBuffer.append(userBigRole.getUserId()).append(",") ;
		}
		for(BigRoleAndRole brr : brrList){
			moduleIdBuffer.append(brr.getRoleId()).append(",") ;
		}
		HttpTool.setAttribute("userIds", userIdBuffer.length() > 1 ? userIdBuffer.substring(0 , userIdBuffer.length() - 1) : "") ;
		HttpTool.setAttribute("moduleIds", moduleIdBuffer.length() > 1 ? moduleIdBuffer.substring(0 , moduleIdBuffer.length() - 1) : "") ;
		return "modifyBigRole" ;
	}
	
	/**
	 * 更新角色
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String updateBigRole() throws SystemException , Exception{
		BigRole oldRole = (BigRole)bigRoleService.findById(bigRole.getId()) ;
		ReflectionUtils.copyPropertiesForHasValue(oldRole , bigRole) ;
		String moduleIds = HttpTool.getParameter("moduleIds") ;
		String userIds = HttpTool.getParameter("userIds") ;
		bigRoleService.updateBigRoleInfo(oldRole, moduleIds, userIds) ;
		return jump("bigRole!listBigRole.action") ;
	}
	
	/**
	 * 删除角色信息
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public String deleteBigRole() throws SystemException , Exception{
		bigRoleService.deleteBigRoleByIds(ids) ;
		return jump("bigRole!listBigRole.action") ;
	}
	
}

