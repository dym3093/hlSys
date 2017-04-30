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
import org.hpin.genepack.entity.GenepackDetail;
import org.hpin.genepack.service.GenepackService;
import org.hpin.genepack.service.GenepackDetailService;

@Namespace("/genepack")
@Action("erpGenepackDetail")
@Results({
	@Result(name="toListGenepack",location="/WEB-INF/genepack/erpGenepack/listGenepack.jsp"),
	@Result(name="toGenepackDetail",location="/WEB-INF/genepack/erpGenepack/genepack.jsp"),
	@Result(name="toAddGenepackDetail",location="/WEB-INF/genepack/erpGenepack/addGenepackDetail.jsp"),
})										
public class GenepackDetailAction extends BaseAction {
	GenepackService service = (GenepackService) SpringTool.getBean(GenepackService.class);
	GenepackDetailService detailService = (GenepackDetailService) SpringTool.getBean(GenepackDetailService.class);
	
	private Genepack genepack;
	private GenepackDetail genepackDetail;
	 
	public Page findByPage(Page page , Map searchMap){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String userName=currentUser.getUserName();
		if(!userName.equals("管理员")){
			searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List<Genepack> genepackList=service.findByPage(page, searchMap);
		System.out.println(genepackList.size());
		page.setResults(genepackList) ;
		return page ;
	}
	/**
	 * 信息查询
	 */
	public String toGenepackDetail() throws Exception{
		genepack=service.getGenepack(id);
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_batchno_EQ_S", genepack.getBatchno());
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		detailService.findByPage(page, searchMap);
		return "toGenepackDetail";
	}
	/**
	 * 信息查询
	 */
	public String toAddGenepackDetail() throws Exception{
		String tempid=HttpTool.getParameter("id");
		if(tempid!=null){
			id=tempid;
		}
		genepackDetail=detailService.getGenepackDetail(id);
		return "toAddGenepackDetail";
	}
	/**
	 * 增加基因物品检测包
	 */
	public String addGenepackDetail(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String isBtn=HttpTool.getParameter("isBtn");
//		genepackDetail
		try {
			if(StrUtils.isNotNullOrBlank(genepackDetail.getId())){//已存在更新
				detailService.update(genepackDetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "更新成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{//增加
				genepackDetail.setId(null);
				genepackDetail.setIsDeleted(0);
				detailService.save(genepackDetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "增加成功");
				json.accumulate("navTabId", super.navTabId);
//				json.accumulate("callbackType", "closeCurrent");
				json.accumulate("callbackType", "refreshCurrent");
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
	 * 删除基因物品检测包
	 */
	public String delGenepackDetail(){

		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
//		String id=HttpTool.getParameter("id");
		try {
			for (int i = 0; i < id.length; i++) {
				genepackDetail=(GenepackDetail)detailService.findById(id[i]);
				genepackDetail.setUpdateTime(new Date());
				genepackDetail.setIsDeleted(1);
				genepackDetail.setUpdateUserId(currentUser.getAccountName());
				detailService.update(genepackDetail);
			}
				json.accumulate("statusCode", 200);
				json.accumulate("message", "删除成功");
			} catch (Exception e) {
				json.accumulate("statusCode", 300);
				json.accumulate("message", "删除失败");
				e.printStackTrace();
			}
		renderJson(json);
		return null;
	}
	
	/**
	 * 清除基因物品的快递批次号信息
	 * @description 
	 * @return
	 *
	 * @return String
	 * @throws
	 *
	 */
	public String clearGenepackDetail(){
	  
         JSONObject json = new JSONObject();
         User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
         String[] id = ids.replaceAll(" ", "").split(",");
//       String id=HttpTool.getParameter("id");
         try {
             for (int i = 0; i < id.length; i++) {
                 genepack=(Genepack)service.findById(id[i]);
                 if(genepack.getDeliverybatchno()!=null){
                     genepack.setDeliverybatchno(null);//将基因物品的发货批次号设为null
                 }
                 genepack.setIsQuery("0");
                 service.update(genepack);
             }
             json.accumulate("statusCode", 200);
             json.accumulate("message", "删除成功");
         } catch (Exception e) {
             json.accumulate("statusCode", 300);
             json.accumulate("message", "删除失败");
             e.printStackTrace();
         }
    	   renderJson(json);
    	   return null;
	}

	public Genepack getGenepack() {
		return genepack;
	}
	public void setGenepack(Genepack genepack) {
		this.genepack = genepack;
	}
	public GenepackDetail getGenepackDetail() {
		return genepackDetail;
	}
	public void setGenepackDetail(GenepackDetail genepackDetail) {
		this.genepackDetail = genepackDetail;
	}
	
}
