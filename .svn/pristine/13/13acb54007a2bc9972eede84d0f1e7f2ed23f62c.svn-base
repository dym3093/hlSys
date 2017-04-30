package org.hpin.events.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;


import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ProjectRelationship;
import org.hpin.events.entity.SampleDeliManager;
import org.hpin.events.service.SampleDeliManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Namespace("/events")
@Action("sampleDlei")
@Results({
	@Result(name="listSampleDleiManager",location="/WEB-INF/events/sampleDlei/listSampleDleiManager.jsp"),
	@Result(name="viewSampleDleiManager",location="/WEB-INF/events/sampleDlei/viewSampleDleiManager.jsp"),
	@Result(name="findBranchComplany",location="/WEB-INF/events/sampleDlei/branchCompany.jsp"),
	@Result(name="editSampleDleiManager",location="/WEB-INF/events/sampleDlei/editSampleDleiManager.jsp")
})
@Component
public class SampleDeliManagerAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SampleDeliManagerAction.class);

	@Autowired
	private SampleDeliManagerService sampleDleiManagerService; //样本快递费用service
	
	private List<ProjectRelationship> proRels;
	
	private SampleDeliManager sampleDleiManager; //样本快递费管理对象
	
	/**
	 * 根据批次号查询场次支公司;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findBranchComplany() {
		String bitchNum = HttpTool.getParameter("bitchNum", "");
		String branchCommany = HttpTool.getParameter("branchCommany", "");
		String province = HttpTool.getParameter("province", "");
		String city = HttpTool.getParameter("city", "");
		try {
			page = new Page(HttpTool.getPageNum());
		} catch (ParseException e) {
			log.error("查询错误");
		}
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("bitchNum", bitchNum);
		params.put("branchCommany", branchCommany);
		params.put("province", province);
		params.put("city", city);
		
		sampleDleiManagerService.findBranchComplany(page, params);
		
		HttpTool.setAttribute("bitchNum", bitchNum);
		HttpTool.setAttribute("branchCommany", branchCommany);
		HttpTool.setAttribute("province", province);
		HttpTool.setAttribute("city", city);
		return "findBranchComplany";
	}
	
	/**
	 * 删除数据;
	 */
	public void deleteSampleDleiManager() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String navTabId= HttpTool.getParameter("navTabId", "");
		String callbackType= "closeCurrent";
		String ids = HttpTool.getParameter("ids", "");
		
		try {
			sampleDleiManagerService.deleteSampleDleiManagerIds(ids);
		} catch (Exception e) {
			log.error("删除错误", e);
		}
		
		json.put("statusCode", statusCode);
		json.put("navTabId", navTabId);
		json.put("callbackType", callbackType);
		renderJson(json);
	}
	
	/**
	 * 浏览
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String viewSampleDleiManager() {
		String sampleDeliId = HttpTool.getParameter("sampleDeliId", "");
		Map<String,Object> mapObj = sampleDleiManagerService.findBySampleDeliId(sampleDeliId);
		
		if(mapObj != null) {
			sampleDleiManager = (SampleDeliManager) mapObj.get("sampleDeli");		
			proRels = (List<ProjectRelationship>) mapObj.get("proRels");
		}
		findErpExprossComnpanys();
		return "viewSampleDleiManager";
	}
	
	/**
	 * 保存/修改数据.根据是否粗在Id来判断;
	 */
	public void saveOrUpdate() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String navTabId= HttpTool.getParameter("navTabId", "");
		String callbackType= "closeCurrent";
		String id = sampleDleiManager.getId();
		try {
			/*
			 * 当id为空时,执行保存操作;否则修改操作;
			 */
			if(StringUtils.isEmpty(id)) {
				sampleDleiManagerService.save(sampleDleiManager, proRels);
			} else {
				sampleDleiManagerService.update(sampleDleiManager, proRels);
			}
		} catch (Exception e) {
			statusCode = "300";
			log.error("SampleDeliManagerAction.saveOrUpdate  Failure", e);
		}
		
		json.put("statusCode", statusCode);
		json.put("navTabId", navTabId);
		json.put("callbackType", callbackType);
		renderJson(json);
	}

	/**
	 * 编辑界面跳转; 新增,修改;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String editSampleDleiManager() {
		String navTabId = HttpTool.getParameter("navTabId", "");
		String sampleDeliId = HttpTool.getParameter("sampleDeliId", "");
		
		if(StringUtils.isNotEmpty(sampleDeliId)) {
			Map<String,Object> mapObj = sampleDleiManagerService.findBySampleDeliId(sampleDeliId);
			if(mapObj != null) {
				sampleDleiManager = (SampleDeliManager) mapObj.get("sampleDeli");		
				proRels = (List<ProjectRelationship>) mapObj.get("proRels");
			}
		}
		findErpExprossComnpanys();
		HttpTool.setAttribute("navTabId", navTabId);
		return "editSampleDleiManager";
	} 
	
	/**
	 * list页面跳转;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String listSampleDleiManager() {
		findErpExprossComnpanys();
		
		try {
			page = 	new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map<String, String> paramMap = new HashMap<String, String>();
			String deliNum = HttpTool.getParameter("deliNum", "");
			String expressCompanyId = HttpTool.getParameter("expressCompanyId", "");
			String sampleType = HttpTool.getParameter("sampleType", "");
			String startDate = HttpTool.getParameter("startDate", "");
			String endDate = HttpTool.getParameter("endDate", "");
			
			paramMap.put("deliNum", deliNum);
			paramMap.put("expressCompanyId", expressCompanyId);
			paramMap.put("sampleType", sampleType);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			
			sampleDleiManagerService.findSampleDelisByPage(page, paramMap);
			
			HttpTool.setAttribute("deliNum", deliNum);
			HttpTool.setAttribute("expressCompanyId", expressCompanyId);
			HttpTool.setAttribute("sampleType", sampleType);
			HttpTool.setAttribute("startDate", startDate);
			HttpTool.setAttribute("endDate", endDate);
		} catch(Exception e) {
			log.error("查询错误listSampleDleiManager!");
		}
		
		return "listSampleDleiManager";
	}
	
	
	/*私有方法*/
	/**
	 * 查找快递公司数据
	 */
	private void findErpExprossComnpanys() {
		//获取快递公司数据;
		List<Map<String, Object>> exprComps = sampleDleiManagerService.findErpExprossComnpanys();
		HttpTool.setAttribute("exprComps", exprComps);
	}
	

	/*get/set 存放区;*/
	public List<ProjectRelationship> getProRels() {
		return proRels;
	}

	public void setProRels(List<ProjectRelationship> proRels) {
		this.proRels = proRels;
	}

	public SampleDeliManager getSampleDleiManager() {
		return sampleDleiManager;
	}

	public void setSampleDleiManager(SampleDeliManager sampleDleiManager) {
		this.sampleDleiManager = sampleDleiManager;
	}
	
}
