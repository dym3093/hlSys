package org.hpin.warehouse.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.ErpSupplier;
import org.hpin.warehouse.service.ErpSupplierService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 供应商action
 * @author tangxing
 * @date 2017-2-7上午10:27:29
 */

@Namespace("/warehouse")
@Action("supplier")
@Results({
		@Result(name = "listSupplier", location = "/WEB-INF/warehouse/supplier/listSupplier.jsp"),
		@Result(name = "toAddSupplier", location = "/WEB-INF/warehouse/supplier/addSupplier.jsp"),
		@Result(name = "toUpdateSupplier", location = "/WEB-INF/warehouse/supplier/updateSupplier.jsp"),
		@Result(name = "querySupplier", location = "/WEB-INF/warehouse/supplier/querySupplier.jsp"),
		
})
public class ErpSupplierAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ErpSupplierAction.class);
	
	@Autowired
	private ErpSupplierService service;
	
	private ErpSupplier supplier;

	/**
	 * 获取supplier集合
	 * @return
	 * @author tangxing
	 * @date 2017-2-7上午11:19:30
	 */
	public String listSupplier(){
		
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("ErpSupplierAction listSupplier "+e);
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List list = service.findByPage(page, searchMap);
		page.setResults(list);
		return "listSupplier";
	}
	
	/**
	 * 逻辑删除
	 * 
	 * @author tangxing
	 * @date 2017-2-7上午11:39:00
	 */
	public void deleteSupplier(){
		JSONObject json = new JSONObject();
		String supplierId = HttpTool.getParameter("id");
		ErpSupplier erpSupplier = service.getSupplierById(supplierId);
		if(erpSupplier!=null){
			erpSupplier.setIsDeleted(1);	//逻辑删除
			service.update(erpSupplier);
			
			json.accumulate("status", 200);
			json.accumulate("message", "删除成功");
		}else{
			json.accumulate("statusCode", "300");
			json.accumulate("message", "删除失败！");
		}
		renderJson(json);
	}
	
	/**
	 * 添加跳转默认页面
	 * @return
	 * @author tangxing
	 * @date 2017-2-7上午11:53:09
	 */
	public String toAddSupplier(){
		
		return "toAddSupplier";
	}
	
	/**
	 * 添加supplier
	 * 
	 * @author tangxing
	 * @date 2017-2-7下午2:07:04
	 */
	public void addSupplier(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		if(supplier!=null){
			log.info("save supplier supplierName -- "+supplier.getSupplierName());
			supplier.setCreateUserId(currentUser.getId());
			supplier.setCreateTime(new Date());
			supplier.setIsDeleted(0);
			
			service.save(supplier);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "添加成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "closeCurrent");
		}else{
			json.accumulate("statusCode", "300");
			json.accumulate("message", "添加失败！");
		}
		
		renderJson(json);
	}
	
	/**
	 * 修改默认页面
	 * @return
	 * @author tangxing
	 * @date 2017-2-7下午2:27:36
	 */
	public String toUpdateSupplier(){
		String supplierId = HttpTool.getParameter("id");
		ErpSupplier erpSupplier = service.getSupplierById(supplierId);
		if(erpSupplier!=null){
			HttpTool.setAttribute("supplier", erpSupplier);
		}
		return "toUpdateSupplier";
	}
	
	/**
	 * 修改supplier
	 * 
	 * @author tangxing
	 * @date 2017-2-7下午4:01:37
	 */
	public void updateSupplier(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String navTabId = HttpTool.getParameter("navTabId");
		JSONObject json = new JSONObject();
		if(supplier!=null){
			supplier.setUpdateUserId(currentUser.getId());
			supplier.setUpdateTime(new Date());
			
			service.update(supplier);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "refreshCurrent");
		}else{
			json.accumulate("statusCode", "300");
			json.accumulate("message", "修改失败！");
		}
		renderJson(json);
	}

	/**
	 * 仓库信息模块，入库新增页面,供应商字段的查找带回查询
	 * @return String
     * @auther Damian
	 * @since 2017-02-07
	 */
	public String querySupplier(){
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List<ErpSupplier> supplierList = null;
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			supplierList = service.findByPage(page, searchMap);
		} catch (ParseException e) {
		    log.info(e);
		}
		page.setResults(supplierList);
		return "querySupplier";
	}
	
	/**
	 * 导出supplier（文件流）
	 * 
	 * @author tangxing
	 * @date 2017-2-7下午6:20:01
	 */
	public void exportExcelSupplier(){
		HttpServletResponse response = ServletActionContext.getResponse();
		String province = HttpTool.getParameter("province");
		String city = HttpTool.getParameter("city");
		String supplierName = HttpTool.getParameter("supplierName");
		String linkName = HttpTool.getParameter("linkName");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
		String strName = sdf.format(new Date());
		String excelName = "supplier"+strName+".xls";			//文件名
		
		StringBuilder builder = new StringBuilder("from ErpSupplier where 1=1 and isDeleted=0 ");
		if(StringUtils.isNotEmpty(province)){
			builder.append(" and provice = "+province);
		}
		if(StringUtils.isNotEmpty(city)){
			builder.append(" and city = "+city);
		}
		if(StringUtils.isNotEmpty(supplierName)){
			builder.append(" and supplierName like '%"+supplierName+"%'");
		}
		if(StringUtils.isNotEmpty(linkName)){
			builder.append(" and linkName like '%"+linkName+"%'");
		}
		
		List<ErpSupplier> erpSuppliers = service.exportExcelList(builder.toString());
		List<List<String>> rowList = service.buildExcelRow(erpSuppliers);
		service.createSupplierXls(excelName, rowList, response);
	}

	public String query(){
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("ErpSupplierAction listSupplier "+e);
		}
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List list = service.findByPage(page, searchMap);
		page.setResults(list);
		
		return "querySupplier";
	}

	public ErpSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(ErpSupplier supplier) {
		this.supplier = supplier;
	}
}
