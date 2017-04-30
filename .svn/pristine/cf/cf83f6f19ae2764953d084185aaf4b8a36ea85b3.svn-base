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
@Action("erpGenepack")
@Results({
		@Result(name = "toListGenepack", location = "/WEB-INF/genepack/erpGenepack/listGenepack.jsp"),
		@Result(name = "toGenepack", location = "/WEB-INF/genepack/erpGenepack/genepack.jsp"),
		@Result(name = "toAddGenepack", location = "/WEB-INF/genepack/erpGenepack/addGenepack.jsp"), })
public class GenepackAction extends BaseAction {
	GenepackService service = (GenepackService) SpringTool
			.getBean(GenepackService.class);
	GenepackDetailService detailService = (GenepackDetailService) SpringTool
			.getBean(GenepackDetailService.class);

	private Genepack genepack;
	private GenepackDetail genepackDetail;

	public Page findByPage(Page page, Map searchMap) {
		User currentUser = (User) HttpTool.getSession().getAttribute(
				"currentUser");
		String userName = currentUser.getUserName();
		if (!userName.equals("管理员")) {
			searchMap.put("filter_and_createUserName_EQ_S",	currentUser.getUserName());
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List<Genepack> genepackList = service.findByPage(page, searchMap);
		page.setResults(genepackList);
		return page;
	}

	/**
	 * 信息列表查询
	 */
	public String toListGenepack() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
		findByPage(page, searchMap);
		return "toListGenepack";
	}

	/**
	 * 信息查询
	 */
	public String toAddGenepack() throws Exception {
		Date now1 = new Date();
		SimpleDateFormat sd= new SimpleDateFormat(DateUtils.DATE_FORMAT);
		String now = sd.format(now1);
		HttpTool.setAttribute("now", now);
	
		return "toAddGenepack";
	}

	/**
	 * 增加基因批次
	 */
	public String addGenepack() {
		JSONObject json = new JSONObject();
		User currentUser = (User) HttpTool.getSession().getAttribute(
				"currentUser");

		try {
			if(StrUtils.isNotNullOrBlank(genepack.getId())){//已存在更新
				genepack.setUpdatetime(new Date());
				service.update(genepack);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "更新成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				//增加
				genepack.setCreateusername(currentUser.getUserName());
				genepack.setIsdeleted("0");
				genepack.setIsDeleted(0);
				genepack.setIsQuery("0");
				genepack.setCreatetime(new Date());
				service.save(genepack);
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

	/**
	 * 删除基因批次
	 * @description 
	 * @return
	 *
	 * @return String
	 * @throws
	 *
	 */
	public String delGenepack() {
		JSONObject json = new JSONObject();
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		String[] id = ids.replaceAll(" ", "").split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				genepack = (Genepack) service.findById(id[i]);
				genepack.setIsdeleted("1");
				genepack.setIsQuery("1");
				genepack.setUpdatetime(new Date());
				genepack.setUpdateusername(currentUser.getUserName());
				String delBatchno = genepack.getBatchno();
				genepackDetail = (GenepackDetail) detailService.findById(delBatchno);
				if(genepackDetail!=null){
					genepackDetail.setIsDeleted(1);
					genepackDetail.setUpdateTime(new Date());
					detailService.update(genepackDetail);
				}
				service.update(genepack);
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
	public String toModifyGenepack(){
		genepack=(Genepack)service.findById(id);
		return "toAddGenepack";
	}
	/**
	 * 生成场次号
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	// public String createNo() throws ParseException, IOException{
	// Date date = HttpTool.getDateParameter("data");
	// SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	// if(StrUtils.isNotNullOrBlank(date)){
	// String eventDate = sf.format(date);
	// String eventsNo = eventsService.maxNo(eventDate);
	// String data = GuNoUtil.getNo(eventsNo,date);
	// HttpServletResponse response = ServletActionContext.getResponse();
	// response.setCharacterEncoding("UTF-8");
	// response.getWriter().write(data);
	// }
	// return null;
	// }
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
