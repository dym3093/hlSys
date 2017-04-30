package org.hpin.base.resource.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.exception.SystemException;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.util.StaticMethod;
import org.hpin.common.widget.pagination.Page;

/**
 * 资源资源Action
 * 
 * @author thinkpad
 * @data May 8, 2009
 */
@Namespace("/system")
@Action("resource")
@Results( {
	@Result(name = "indexResource", location = "/WEB-INF/content/system/resource/indexResource.jsp"),
	@Result(name = "listResourceModule", location = "/WEB-INF/content/system/resource/listResourceModule.jsp"),
	@Result(name = "addResourceModuel", location = "/WEB-INF/content/system/resource/addResourceModuel.jsp"),
	@Result(name = "modifyResourceModuel", location = "/WEB-INF/content/system/resource/modifyResourceModuel.jsp"),
	@Result(name = "listResourceChildren", location = "/WEB-INF/content/system/resource/listResourceChildren.jsp"),
	@Result(name = "addResourceChildren", location = "/WEB-INF/content/system/resource/addResourceChildren.jsp"),
	@Result(name = "modifyResourceChildren", location = "/WEB-INF/content/system/resource/modifyResourceChildren.jsp")
	
	
})
public class ResourceAction extends BaseAction {
	
	private ResourceService  resourceService = (ResourceService) SpringTool.getBean(ResourceService.class);
	private Resource resource;
	
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	/**
	 * 模块框架页面
	 * @return
	 * @throws Exception
	 */
	public String indexResource() throws Exception{
		return "indexResource";
	}
	/**
	 * 显示添加模块页面
	 * @return
	 * @throws Exception
	 */
	public String addResourceModuel() throws Exception{
		return "addResourceModuel";
	}
	/**
	 * 显示添加子模块页面
	 * @return
	 * @throws Exception
	 */
	public String addResourceChildren() throws Exception{
		//Long parentId = StaticMethod.nullObject2Long(HttpTool.getParameter("parentId")) ;
		String parentId = HttpTool.getParameter("parentId") ;
		resource = (Resource) resourceService.findById(parentId);
		HttpTool.setAttribute("parent", resource) ;
		return "addResourceChildren";
	}
	/**
	 * 显示模块修改页面
	 * @return
	 * @throws Exception
	 */
	public String modifyResourceModuel() throws Exception{
		resource = (Resource) resourceService.findById(String.valueOf(id));
		
		return "modifyResourceModuel";
	}
	/**
	 * 显示子模块修改页面
	 * @return
	 * @throws Exception
	 */
	public String modifyResourceChildren() throws Exception{
		String parentId = HttpTool.getParameter("parentId") ;
		
		Resource parent = (Resource) resourceService.findById(String.valueOf(parentId));
		HttpTool.setAttribute("parent", parent) ;
		resource = (Resource) resourceService.findById(String.valueOf(id));
		return "modifyResourceChildren";
	}
	/**
	 * 保存子模块
	 * @return
	 * @throws Exception
	 */
	public String saveResourceChildren() throws Exception , SystemException{
		Integer isButton = StaticMethod.nullObject2Integer(HttpTool.getParameter("resource.isButton"));
		if(!isButton.equals(1)){//不是按钮
			//设置父菜单为非叶子节点
			Resource parent = (Resource)resourceService.findById(String.valueOf(resource.getParentId()));
			parent.setIsLeaf(0);
			resourceService.update(parent);
			//设置设置父菜单为非叶子节点结束
			//resource.setIsLeaf(1);
			resource.setType(3);
			resourceService.save(resource);
		}else{
			//设置父菜单为叶子节点
			Resource parent = (Resource)resourceService.findById(String.valueOf(resource.getParentId()));
			if(parent.getIsLeaf() != null && parent.getIsLeaf().intValue() != 1) throw new SystemException("非叶子节点不允许添加按钮！") ; 
			parent.setIsLeaf(1);
			resourceService.update(parent);
			//resource.setIsLeaf(1);
			resource.setType(4);
			resourceService.save(resource);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpTool.setAttribute("flag", 1);
		HttpTool.setAttribute("parentId", resource.getParentId());
		out.println("<script>alert('操作完成');parent.location.href='resource!indexResource.action';</script>");
		out.flush();
		out.close();
		return NONE ;
	}
	/**
	 * 删除模块，并附带删除模块下所有子模块信息
	 * @return
	 * @throws Exception
	 */
	public String deleteProduct() throws Exception{
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			//resourceService.updateEnable(new Long(idArray[i]));	
			resourceService.updateEnable(idArray[i]);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('操作完成');parent.location.href='resource!indexResource.action';</script>");
		out.flush();
		out.close();

		return NONE ;
	}
	/**
	 * 更新模块
	 * @return
	 * @throws Exception
	 */
	public String updateResource() throws Exception{
		Integer isButton = StaticMethod.nullObject2Integer(HttpTool.getParameter("resource.isButton"));
		if(!isButton.equals(1)){//不是按钮
			//设置父菜单为非叶子节点
			System.out.println(resource.getParentId()+"-----------------");
			Resource parent = (Resource)resourceService.findById(String.valueOf(resource.getParentId()));
			parent.setIsLeaf(0);
			resourceService.update(parent);

			Resource oldResource = (Resource)resourceService.findById(resource.getId()) ;
			ReflectionUtils.copyPropertiesForHasValue(oldResource , resource) ;
			
			//设置设置父菜单为非叶子节点结束
			//oldResource.setIsLeaf(1);
			//oldResource.setType(3) ;
			resourceService.update(oldResource);
		}else{
			//设置父菜单为非叶子节点
			Resource parent = (Resource)resourceService.findById(String.valueOf(resource.getParentId()));
			parent.setIsLeaf(1);
			resourceService.update(parent);
			
			Resource oldResource = (Resource)resourceService.findById(resource.getId()) ;
			ReflectionUtils.copyPropertiesForHasValue(oldResource , resource) ;
			
			//oldResource.setIsLeaf(1);
			oldResource.setType(4);
			resourceService.update(oldResource);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('操作完成');parent.location.href='resource!indexResource.action';</script>");
		out.flush();
		out.close();
		return NONE ;
	}
	/**
	 * 保存模块
	 * @return
	 * @throws Exception
	 */
	public String saveResource() throws Exception{
		resource.setIsLeaf(0);
		resource.setType(1);
		resourceService.save(resource);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('操作完成');parent.location.href='resource!indexResource.action';</script>");
		out.flush();
		out.close();
		return NONE ;
	}
	/**
	 * 生成模块树JSON
	 * @return
	 * @throws Exception
	 */
	public String treeResource() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		List<Resource> resourceList = resourceService.findMenuByType() ;
		Resource resource = null ;
		if(resourceList.size() > 0){
			for(int i = 0 ; i < resourceList.size() ; i ++){
				resource = resourceList.get(i) ;
				json.append("{\"text\":\"" + resource.getName() + "\",\"id\":\"resource" + resource.getId() + "\",\"leaf\":" + false) ;
				json.append("},") ;
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
	 * 生成子模块树JSON
	 * @return
	 * @throws Exception
	 */
	public String treeResourceDispose() throws Exception{
		StringBuffer json = new StringBuffer("[") ;
		String idStr = HttpTool.getParameter("parentId") ;
		String type = idStr.substring(0 , 8) ;
		String parentId = idStr.substring(8) ;
		if("children".equals(type)){
			//加载叶子节点下的button
			List<Resource> versionList =  resourceService.findEnableButtonByParentId(parentId)  ;
			Resource resource = null ;
			if(versionList.size() > 0){
				for(int i = 0 ; i < versionList.size() ; i ++){
					resource = versionList.get(i) ;
					json.append("{\"text\":\"" + resource.getName() + "\",\"id\":\"button" + resource.getId() + "\",\"leaf\":" + true) ;
					json.append("},") ;
				}
			}
			if (json.toString().endsWith(",")) {
				json = json.delete(json.length() - 1, json.length());
			}

			json.append("]");
			this.jsonString = json.toString();
		}else{
			//非叶子节点，加载树
			List<Resource> versionList = resourceService.findEnableMenuByParentId(parentId) ;
			Resource resource = null ;
			if(versionList.size() > 0){
				for(int i = 0 ; i < versionList.size() ; i ++){
					resource = versionList.get(i) ;
					//json.append("{\"text\":\"" + resource.getName() + "\",\"id\":\"children" + resource.getId() + "\",\"leaf\":" + ((resource.getIsLeaf() == 1 && StaticMethod.nullObject2int(resource.getIsButton()) == 0) ? false : true)) ;
					json.append("{\"text\":\"" + resource.getName() + "\",\"id\":" + ((Integer.valueOf(1).equals(resource.getIsLeaf())) ? "\"children" + resource.getId() : "\"resource" + resource.getId()) + "\",\"leaf\":" + (Integer.valueOf(1).equals(resource.getIsLeaf()) ? true : false)) ;
					json.append("},") ;
				}
			}
			if (json.toString().endsWith(",")) {
				json = json.delete(json.length() - 1, json.length());
			}
	
			json.append("]");
			this.jsonString = json.toString();
		}
		return "json";
	}
	/**
	 * 显示菜单列表页
	 * @return
	 * @throws Exception
	 */
	public String listResourceModule() throws Exception{
		page = new Page(HttpTool.getPageNum()) ;
		Map paramsMap = buildSearch() ;
		resourceService.findByModulePage(page, paramsMap);
		return "listResourceModule" ;
	}
	/**
	 * 显示子菜单列表页
	 * @return
	 * @throws Exception
	 */
	public String listResourceChildren() throws Exception{
		String idStr = StaticMethod.nullObject2String(HttpTool.getParameter("parentId")) ;
		page = new Page(HttpTool.getPageNum()) ;
		Map paramsMap = buildSearch() ;
		
		if(StringUtils.isNotBlank(idStr)){
			String parentId = idStr.substring(8);
			
			HttpTool.setAttribute("parentId", String.valueOf(parentId));
			paramsMap.put("filter_and_isEnable_EQ_I", 1);
			resourceService.findByparentId(page, paramsMap, String.valueOf(parentId));
		}
		
		return "listResourceChildren" ;
	}
	
	public String listResourceButtonChildren() throws Exception{
		String idStr = StaticMethod.nullObject2String(HttpTool.getParameter("parentId")) ;
		StringBuffer json = new StringBuffer("[") ;
		//Long parentId = StaticMethod.nullObject2Long(idStr.substring(8)) ;
		String parentId = idStr.substring(8) ;
		//List<Resource> versionList = resourceService.findEnableMenuByParentId(parentId) ;
		List<Resource> versionList =  resourceService.findEnableButtonByParentId(parentId)  ;
		Resource resource = null ;
		if(versionList.size() > 0){
			for(int i = 0 ; i < versionList.size() ; i ++){
				resource = versionList.get(i) ;
				json.append("{\"text\":\"" + resource.getName() + "\",\"id\":\"children" + resource.getId() + "\",\"leaf\":" + (resource.getIsLeaf()==1?true:false)) ;
				json.append("},") ;
			}
		}
		if (json.toString().endsWith(",")) {
			json = json.delete(json.length() - 1, json.length());
		}

		json.append("]");
		this.jsonString = json.toString();
		return "json";
		
		//return "listResourceChildren" ;
	}

}
