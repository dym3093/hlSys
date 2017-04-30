package org.hpin.reportdetail.web;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;

import org.hpin.reportdetail.entity.ErpReportdetail;
import org.hpin.reportdetail.entity.ErpReportdetailBatch;
import org.hpin.reportdetail.entity.ErpReportdetailPDF;

import org.hpin.reportdetail.service.ErpReportdetailService;
import org.hpin.reportdetail.service.ErpReportdetailBatchService;
import org.hpin.reportdetail.service.ErpReportdetailPDFService;

@Namespace("/reportdetail")
@Action("erpReportdetailBatch")
@Results({
	@Result(name="listReportdetailBatch",location="/WEB-INF/reportdetail/listReportdetailBatch.jsp"),
	@Result(name="toReportdetailBatch",location="/WEB-INF/reportdetail/reportdetailBatch.jsp"),
	@Result(name="addReportdetailBatch",location="/WEB-INF/reportdetail/addReportdetailBatch.jsp"),
	@Result(name="listReportdetailPDF",location="/WEB-INF/reportdetail/reportdetailPDF.jsp"),
})
public class ErpReportdetailBatchAction extends BaseAction {
	ErpReportdetailBatchService service = (ErpReportdetailBatchService) SpringTool.getBean(ErpReportdetailBatchService.class);
	ErpReportdetailService reportdetailService = (ErpReportdetailService) SpringTool.getBean(ErpReportdetailService.class);
	ErpReportdetailPDFService erpReportdetailPDFService = (ErpReportdetailPDFService) SpringTool.getBean(ErpReportdetailPDFService.class);
	
	private ErpReportdetail reportdetail;
	private ErpReportdetailBatch reportdetailBatch;
	private ErpReportdetailPDF reportdetailPDF;
	public Page findByPage(Page page , Map paramsMap){
		service.findByPage(page, paramsMap);
		return page ;
	}
	public Page findByPage1(Page page , Map paramsMap){
		reportdetailService.findByPage(page, paramsMap);
		return page ;
	}
	public Page findByPagePDF(Page page , Map paramsMap){
		erpReportdetailPDFService.findByPage(page, paramsMap);
		return page ;
	}
	/**
	 * 查询PDF信息
	 */
	public String listReportdetailPDF() throws Exception{
		reportdetailBatch=service.get(id);
		String batchno=reportdetailBatch.getBatchno();
		String ismatch=HttpTool.getParameter("ismatchpdf");
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_batchno_EQ_S", batchno);
		if(reportdetailPDF==null){
			reportdetailPDF=new ErpReportdetailPDF();
		}
		if(ismatch!=null&&!ismatch.equals("")){
			if(ismatch.equals("1")){
				System.out.println("reportdetailPDF"+1);
				reportdetailPDF.setIsmatch("1");
				searchMap.put("filter_and_ismatch_EQ_S", "1");
			}else if(ismatch.equals("0")){
				reportdetailPDF.setIsmatch("0");
				searchMap.put("filter_and_ismatch_EQ_S", "0");
			}else{
				reportdetailPDF.setIsmatch("2");
			}
		}
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPagePDF(page, searchMap);
		
		return "listReportdetailPDF";
	}
	/**
	 * 查询
	 * @return
	 */
	public String listReportdetailBatch(){
		try {
			page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			findByPage(page, paramsMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "listReportdetailBatch";
	}
	/**
	 * 根据批次号显示批次信息及相关信息
	 * @return
	 */
	public String toReportdetailBatch() throws Exception{
		reportdetailBatch=service.get(id);
		String batchno=reportdetailBatch.getBatchno();
		String ismatch=HttpTool.getParameter("ismatch");
		
		Map searchMap = super.buildSearch();
		searchMap.put("filter_and_batchno_EQ_S", batchno);
		
		if(ismatch!=null&&!ismatch.equals("")){
			if(ismatch.equals("1")){
				reportdetailBatch.setIsmatch(1);
				searchMap.put("filter_and_ismatch_EQ_S", 1);
			}else if(ismatch.equals("0")){
				reportdetailBatch.setIsmatch(0);
				searchMap.put("filter_and_ismatch_EQ_S", 0);
			}else{
				reportdetailBatch.setIsmatch(2);
			}
		}
//		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		page = new Page(HttpTool.getPageNum() , HttpTool.getPageSize());
		findByPage1(page, searchMap);
//		reportdetailService.findByPage(page, searchMap);
		return "toReportdetailBatch";
	}
	
	public String addReportdetail(){
		return "addReportdetail";
	}
	/**
	 * 保存或更新
	 * @return
	 */			  
	public String saveOrUpdateReportdetail(){
		JSONObject json = new JSONObject();
		try {
			if(StrUtils.isNotNullOrBlank(reportdetail.getId())){
//				service.updateInfo(reportdetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				reportdetail.setId(null);
//				service.save(reportdetail);
				json.accumulate("statusCode", 200);
				json.accumulate("message", "操作成功");
				json.accumulate("navTabId", super.navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "操作失败");
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	public String modifyReportdetail(){
		reportdetail = (ErpReportdetail) service.findById(id);
		return "addReportdetail";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deletereportdetail(){
		JSONObject json = new JSONObject();
		String[] id = ids.replaceAll(" ", "").split(",");
		List<ErpReportdetail> list = new LinkedList<ErpReportdetail>();
		try {
			for (int i = 0; i < id.length; i++) {
				reportdetail = (ErpReportdetail) service.findById(id[i]);
				list.add(reportdetail);
			}
//			service.deleteInfo(list);
			json.accumulate("statusCode", 200);
			json.accumulate("message", "删除成功");
			json.accumulate("navTabId", super.navTabId);
			json.accumulate("callbackType", "refreshCurrent");
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("statusCode", 300);
			json.accumulate("message", "删除失败");
		}
		renderJson(json);
		return null;
	}
	public ErpReportdetail getReportdetail() {
		return reportdetail;
	}
	public void setReportdetail(ErpReportdetail reportdetail) {
		this.reportdetail = reportdetail;
	}
	public ErpReportdetailBatch getReportdetailBatch() {
		return reportdetailBatch;
	}
	public void setReportdetailBatch(ErpReportdetailBatch reportdetailBatch) {
		this.reportdetailBatch = reportdetailBatch;
	}
	public ErpReportdetailPDF getReportdetailPDF() {
		return reportdetailPDF;
	}
	public void setReportdetailPDF(ErpReportdetailPDF reportdetailPDF) {
		this.reportdetailPDF = reportdetailPDF;
	}
	
	
}
