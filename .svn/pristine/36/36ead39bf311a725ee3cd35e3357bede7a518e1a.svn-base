package org.hpin.base.resource.web;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.resource.entity.ParameterConfig;
import org.hpin.base.resource.service.ParameterConfigService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;

/**
 * <p>@desc : 系统参数配置Action</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 30, 2012 1:32:22 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
@Namespace("/system")
@Action("parameterConfig")
@Results( {
		@Result(name = "listParameterConfig", location = "/WEB-INF/content/system/resource/listParameterConfig.jsp"),
		@Result(name = "modifyParameterConfig", location = "/WEB-INF/content/system/resource/modifyParameterConfig.jsp") })
public class ParameterConfigAction extends BaseAction {

	private ParameterConfigService parameterConfigService = (ParameterConfigService) SpringTool
			.getBean(ParameterConfigService.class);

	private ParameterConfig parameterConfig = null;

	public ParameterConfig getParameterConfig() {
		return parameterConfig;
	}

	public void setParameterConfig(ParameterConfig parameterConfig) {
		this.parameterConfig = parameterConfig;
	}

	public String listParameterConfig() throws Exception {
		page = new Page(HttpTool.getPageNum());
		Map searchMap = super.buildSearch();
		parameterConfigService.findByPage(page, searchMap);
		return "listParameterConfig";
	}

	/**
	 * 修改参数设置
	 */
	public String modifyParameterConfig() throws Exception {
		parameterConfig = (ParameterConfig) parameterConfigService.findById(String.valueOf(id));
		return "modifyParameterConfig";
	}

	public String updateParameterConfig() throws Exception {
		parameterConfigService.update(parameterConfig);
		return jump("parameterConfig!listParameterConfig.action");
	}
}
