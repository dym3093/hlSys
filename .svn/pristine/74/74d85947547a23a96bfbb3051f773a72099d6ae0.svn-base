package org.ymjy.combo.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

import sun.util.logging.resources.logging;
@Namespace("/combo")
@Action("comboAction")
@Results({
	@Result(name = "addCombo", location = "/WEB-INF/combo/addCombo.jsp"),
	@Result(name = "listCombo", location = "/WEB-INF/combo/listCombo.jsp"),
	@Result(name = "browCombo", location = "/WEB-INF/combo/browCombo.jsp"),
	@Result(name = "modifyComboAll", location = "/WEB-INF/combo/modifyComboAll.jsp"),
	@Result(name = "modifyCombo", location = "/WEB-INF/combo/modifyCombo.jsp"),
	@Result(name = "browComboAll", location = "/WEB-INF/combo/browComboAll.jsp"),
	@Result(name = "lookUpCombo", location = "/WEB-INF/combo/lookUpCombo.jsp")
})
public class ComboAction extends BaseAction{
	ComboService service = (ComboService)SpringTool.getBean(ComboService.class);
	Logger logger = Logger.getLogger(ComboAction.class);
	private Combo combo;
	
	public Combo getCombo() {
		return combo;
	}
	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	/**
	 * 添加套餐标签页面
	 */
	public String addComboAll(){
		return "addComboAll";
	}
	public String addCombo(){
		if(StrUtils.isNotNullOrBlank(id)){
			combo=(Combo) service.findById(id);
		}else{
			combo=new Combo();
		}
		return "addCombo";
	}
	/**
	 * @查看套餐页面
	 */
	public String browCombo(){
		combo = (Combo)service.findById(id);
		HttpTool.setAttribute("browState", HttpTool.getParameter("browState"));
		return "browCombo";
	}
	public Page findByPage(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDelete_EQ_I",0);
		service.findByPage(page, paramsMap);
		return page ;
	}
	
	public Page findDealByPage(Page page , Map paramsMap){
		try {
			paramsMap.put("filter_and_isDelete_EQ_I",0);
			service.findByPage(page, paramsMap);
			List<Combo> combos = page.getResults();
			for (Combo combo : combos) {
				//项目类别,PCT_001基因项目, PCT_002癌筛项目
				if("PCT_001".equals(combo.getProjectTypes())) {
					combo.setProjectTypes("基因项目");
				} else if("PCT_002".equals(combo.getProjectTypes())) {
					combo.setProjectTypes("癌筛项目");
				}
				service.clearDBLink(combo);
			}
		} catch (Exception e) {
			logger.info("套餐信息导出失败信息(findDealByPage)："+e);
		}
		
		return page ;
	}
	/**
	 * @修改套餐信息前查询
	 */
	public String modifyComboAll(){
		return "modifyComboAll";
	}
	/**
	 * @修改套餐页面
	 */
	public String modifyCombo(){
		combo = (Combo)service.findById(id);
		return "modifyCombo";
	}
	/** 保存套擦基本信息*/
	public String saveCombo(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		try {
			String currentTabId = HttpTool.getParameter("currentTabId");
			if(StrUtils.isNotNullOrBlank(combo.getId())){
				if(null!=combo){
					logger.info("comboId:"+combo.getId());
					combo.setComboName(combo.getComboName().trim());
					logger.info("套餐名称(comboName):"+combo.getComboName());
					combo.setUpdateTime(new Date());
					combo.setUpdateUser(currentUser.getId());
					service.update(combo);
					json.accumulate("statusCode", 200);
					json.accumulate("message", "操作成功");
					json.accumulate("navTabId", super.navTabId);
					json.accumulate("currentTabId", currentTabId);
					json.accumulate("callbackType", "closeCurrent");
				}
			}else{
				combo.setId(null);
				combo.setComboName(combo.getComboName().trim());
				logger.info("套餐名称(comboName):"+combo.getComboName());
				combo.setIsDelete(0);
				combo.setCreateTime(new Date());
				combo.setCreateUser(currentUser.getId());
				service.save(combo);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("currentTabId", currentTabId);
				json.accumulate("callbackType", "closeCurrent");
				}
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	public String browComboAll() throws ParseException{
		String flag = HttpTool.getParameter("flag");
		HttpTool.setAttribute("id", id);
		HttpTool.setAttribute("flag", flag);
		return "browComboAll";
	}
	public String listCombo() throws ParseException{
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		String orderField  = HttpTool.getParameter("orderField");
		String orderDirection = HttpTool.getParameter("orderDirection");
		if( paramsMap.containsKey("orderDirection")){
			paramsMap.remove("orderDirection");
		}
		if( paramsMap.containsKey("orderField")){
			paramsMap.remove("orderField");
		}
		if (StrUtils.isNotNullOrBlank(orderField)&&StrUtils.isNotNullOrBlank(orderDirection)) {
			paramsMap.put(orderField,orderDirection);
		}
		HttpTool.setAttribute("orderField", orderField);
		HttpTool.setAttribute("orderDirection", orderDirection);
		paramsMap.put("order_createTime", "desc");
		findByPage(page, paramsMap);
		return "listCombo";
	}
	/**
	 * 基因套餐查找带回
	 */
	public String lookUpCombo() throws ParseException {
		page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
		Map paramsMap = buildSearch();
		paramsMap.put("filter_and_isDelete_EQ_I", 0);
		List<Combo> comboList = service.findByPage(page, paramsMap);
		page.setResults(comboList);
		return "lookUpCombo";
	}
}
