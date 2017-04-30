package org.hpin.common.AjaxCheckCode.web;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.AjaxCheckCode.service.AjaxCheckCodeService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;

@Namespace("/common")
@Action("ajaxCheckCode")
@Results({
})
public class AjaxCheckCodeAction extends BaseAction {
	
	AjaxCheckCodeService ajaxCheckCodeService = (AjaxCheckCodeService)SpringTool.getBean(AjaxCheckCodeService.class);
	public String checkCode(){
		JSONObject json = new JSONObject();
		String propertyName = HttpTool.getParameter("propertyName");
		String propertyValue = HttpTool.getParameter("propertyValue");
		String beanName = HttpTool.getParameter("beanName");
		
		boolean flag = ajaxCheckCodeService.findByCode(beanName,propertyName,propertyValue);
		json.accumulate("falg", flag);
		renderJson(json);
		return null;
	}
	/**
	 * 非紧急合作机构编码唯一验证 add by sonia 20130729
	 * @return json 
	 */
	public String checkCodeByNoemer(){
		JSONObject json = new JSONObject();
		String propertyName = HttpTool.getParameter("propertyName");
		String propertyValue = HttpTool.getParameter("propertyValue");
		String propertyName2 = HttpTool.getParameter("propertyName2");
		String propertyValue2 = HttpTool.getParameter("propertyValue2");
		String beanName = HttpTool.getParameter("beanName");
		
		boolean flag = ajaxCheckCodeService.findNoemerByCode(beanName,propertyName,propertyValue,propertyName2,propertyValue2);
		json.accumulate("flag", flag);
		renderJson(json);
		return null;
	}
	/**
	 * 服务公共资源医院名称唯一验证 add by lxh 20130903
	 * @return json 
	 */
	public String checkNameByHis(){
		JSONObject json = new JSONObject();
		String propertyName = HttpTool.getParameter("propertyName");
		String propertyValue = HttpTool.getParameter("propertyValue");
		String propertyName2 = HttpTool.getParameter("propertyName2");
		String propertyValue2 = HttpTool.getParameter("propertyValue2");
		String propertyName3 = HttpTool.getParameter("propertyName3");
		String propertyValue3 = HttpTool.getParameter("propertyValue3");
		String beanName = HttpTool.getParameter("beanName");
		
		boolean flag = ajaxCheckCodeService.findHisByName(beanName,propertyName,propertyValue.trim(),propertyName2,propertyValue2,propertyName3,propertyValue3);
		json.accumulate("flag", flag);
		renderJson(json);
		return null;
	}
	
	/**
	 * 记录审核时，根据日志找出审核前发布的记录
	 * @return
	 */
	public String ajaxModifyLogItem(){
		String id = HttpTool.getParameter("id");
		JSONObject json = new JSONObject();
		try {
			List list = ajaxCheckCodeService.findModifyLogItem(id);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map)list.get(i);
					json.put(map.get("field_name"), map.get("old_value"));
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		renderJson(json);
		return null;
	}

}
