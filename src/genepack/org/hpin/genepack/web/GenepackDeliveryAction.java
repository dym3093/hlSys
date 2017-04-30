package org.hpin.genepack.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.region.dao.RegionDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.genepack.entity.Genepack;
import org.hpin.genepack.entity.GenepackDelivery;
import org.hpin.genepack.service.GenepackDeliveryService;
import org.hpin.genepack.service.GenepackService;

@Namespace("/genepack")
@Action("erpGenepackDelivery")
@Results({
		@Result(name = "toListGenepackDelivery", location = "/WEB-INF/genepack/erpGenepack/listGenepackDelivery.jsp"),
		@Result(name = "toGenepackDelivery", location = "/WEB-INF/genepack/erpGenepack/genepackDelivery.jsp"),
		@Result(name = "toAddGenepackDelivery", location = "/WEB-INF/genepack/erpGenepack/addGenepackDelivery.jsp"), 
		@Result(name = "lookUpGenepack", location = "/WEB-INF/genepack/erpGenepack/lookUpGenepack333.jsp"), 
		
})
public class GenepackDeliveryAction extends BaseAction {
	GenepackDeliveryService service = (GenepackDeliveryService) SpringTool.getBean(GenepackDeliveryService.class);
	GenepackService genepackService = (GenepackService) SpringTool.getBean(GenepackService.class);
	private GenepackDelivery genepackDelivery;
	private Genepack genepack;
	private String deliverybatchno;
	
	/*
	 * 发货分页列表
	 */
	public Page findByPage(Page page, Map searchMap) {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		String userName = currentUser.getUserName();
		if (!userName.equals("管理员")) {
			searchMap.put("filter_and_createUserName_EQ_S",	currentUser.getUserName());
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List<GenepackDelivery> genepackList = service.findByPage(page, searchMap);
		page.setResults(genepackList);
		return page;
	}

	/**
	 * 信息列表查询
	 */
	public String toListGenepackDelivery() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toListGenepackDelivery";
	}
	
	/*
	 * 发货批次号分页列表
	 */
	public Page findByPageDelivery(Page page, Map searchMap) {
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List<Genepack> genepackList = genepackService.findByPage(page, searchMap);
		page.setResults(genepackList);
		return page;
	}
	/**
	 * 根据ID查询
	 */
	public String toGenepackDelivery() throws Exception {
		
		if(id!=null|id!=""){
		    String[] ids = id.replaceAll(" ", "").split(",");
	        for (int i = 0; i < ids.length; i++) {
	            genepackDelivery=service.getGenepackDelivery(ids[0]);
	        }
	        Map searchMap = super.buildSearch();
	        searchMap.put("filter_and_deliverybatchno_EQ_S", genepackDelivery.getDeliverybatchno());
	        page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
	        findByPageDelivery(page, searchMap);
		}
	
//		Date now1 = new Date();
//		SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
//		String now = sd.format(now1);
//		HttpTool.setAttribute("now", now);
		
		return "toGenepackDelivery";
	}
	/**
	 * 增加
	 */
	public String toAddGenepackDelivery() throws Exception {
		Date now1 = new Date();
		SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
		String now = sd.format(now1);
		HttpTool.setAttribute("now", now);
	
		return "toAddGenepackDelivery";
	}
	/**
	 * 修改
	 */
	public String toModifyGenepackDelivery(){
		genepackDelivery=(GenepackDelivery)service.findById(id);
		return "toAddGenepackDelivery";
	}

	/**
	 * 增加或更新批次
	 */
	public String addGenepackDelivery() {
		JSONObject json = new JSONObject();
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");

		try {
			if(StrUtils.isNotNullOrBlank(genepackDelivery.getId())){//已存在更新
				genepackDelivery.setUpdateTime(new Date());
				service.update(genepackDelivery);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "更新成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				//增加
				genepackDelivery.setId(null);
				genepackDelivery.setIsDeleted(0);
				genepackDelivery.setCreateTime(new Date());
				service.save(genepackDelivery);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "增加成功");
				json.accumulate("navTabId", super.navTabId);
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

	public String delGenepackDelivery() {
		JSONObject json = new JSONObject();
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				genepackDelivery = (GenepackDelivery) service.findById(id[i]);
				genepackDelivery.setIsDeleted(1);
				genepackDelivery.setUpdateTime(new Date());
				genepackDelivery.setUpdateUserId(currentUser.getUserName());
				service.update(genepackDelivery);
				
				String delDeliverybatchno = genepackDelivery.getDeliverybatchno();
				List<Genepack> genepackList = genepackService.findListByDeliverybatchno(delDeliverybatchno);
				if(genepackList!=null){
					for (Genepack delGenepack : genepackList) {
						delGenepack.setDowndates(null);
						delGenepack.setDeliverybatchno(null);
						genepackService.update(delGenepack);
					}
				}
			}
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
			// json.accumulate("navTabId", super.navTabId);
			// json.accumulate("callbackType", "closeCurrent");
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
			e.printStackTrace();
		}
		renderJson(json);
		return null;
	}
	/**
	 * 更改货物下架状态
	 * @return
	 */
	public String updateGenepack(){
		JSONObject json = new JSONObject();
		User currentUser = (User) HttpTool.getSession().getAttribute(
				"currentUser");
		try {
			if(StrUtils.isNotNullOrBlank(genepack.getId())){//已存在更新
				genepack.setUpdatetime(new Date());
				genepack.setUpdateusername(currentUser.getUserName());
				genepack.setIsQuery("1");
				genepackService.update(genepack);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "更新成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "refreshCurrent");
			}else{
				/*//增加
				genepack.setCreateusername(currentUser.getUserName());
				genepack.setIsdeleted("0");
				genepack.setIsDeleted(0);
				genepack.setCreatetime(new Date());
				service.save(genepack);*/
				json.accumulate("statusCode", 300);
				json.accumulate("message", "增加成功");
				json.accumulate("navTabId", super.navTabId);
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
	/**
	 * 查找带回
	 * @return
	 */
	public String toLookUpGenepack(){
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String state = "0";
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();			
		    paramsMap.put("filter_and_batchno_NEQ_S", null);
		    paramsMap.put("filter_and_isQuery_EQ_S", state);
		    findByPageDelivery(page, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "lookUpGenepack";
	}
	public GenepackDelivery getGenepackDelivery() {
		return genepackDelivery;
	}

	public void setGenepackDelivery(GenepackDelivery genepackDelivery) {
		this.genepackDelivery = genepackDelivery;
	}

	public Genepack getGenepack() {
		return genepack;
	}

	public void setGenepack(Genepack genepack) {
		this.genepack = genepack;
	}

    /**
     * @return the deliverybatchno
     */
    public String getDeliverybatchno() {
        return deliverybatchno;
    }

    /**
     * @param deliverybatchno the deliverybatchno to set
     */
    public void setDeliverybatchno(String deliverybatchno) {
        this.deliverybatchno = deliverybatchno;
    }
}
