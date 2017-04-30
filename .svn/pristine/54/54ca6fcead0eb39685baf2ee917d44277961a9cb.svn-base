package org.hpin.base.usermanager.web;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.Group;
import org.hpin.base.usermanager.service.GroupService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;

/**
 * 用户Action
 * 
 * @author thinkpad
 * @data 2009-8-9
 */
@Namespace("/um")
@Results( {
		@Result(name = "listGroup", location = "/WEB-INF/content/userManager/group/listGroup.jsp"),
		@Result(name = "addGroup", location = "/WEB-INF/content/userManager/group/addGroup.jsp"),
		@Result(name = "modifyGroup", location = "/WEB-INF/content/userManager/group/modifyGroup.jsp") })
public class GroupAction extends BaseAction {
	private GroupService groupService = (GroupService) SpringTool
			.getBean(GroupService.class);

	private Group group = null;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * 显示用户列表
	 */
	public String listGroup() throws Exception {
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		groupService.findByPage(page, searchMap);
		return "listGroup";
	}

	/**
	 * 添加用户
	 */
	public String addGroup() throws Exception {
		return "addGroup";
	}

	/**
	 * 保存用户
	 */
	public String saveGroup() throws Exception {
		groupService.save(group);
		return jump("group!listGroup.action");
	}

	/**
	 * 显示修改用户页面
	 */
	public String modifyGroup() throws Exception {
		group = (Group) groupService.findById(String.valueOf(id));
		return "modifyGroup";
	}

	/**
	 * 修改用户
	 */
	public String updateGroup() throws Exception {
		groupService.update(group);
		return jump("group!listGroup.action");
	}

	/**
	 * 删除用户
	 */
	public String deleteGroup() throws Exception {
		groupService.deleteIds(ids);
		return jump("group!listGroup.action");
	}
}
