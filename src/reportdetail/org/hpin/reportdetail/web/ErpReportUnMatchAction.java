package org.hpin.reportdetail.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.reportdetail.entity.ErpReportUnMatch;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.job.ErpReportWillPrintJob;
import org.hpin.reportdetail.service.ErpReportFileTaskService;
import org.hpin.reportdetail.service.ErpReportUnMatchService;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;

@Namespace("/report")
@Action("unMatch")
@Results({
	@Result(name="listUnMatch",location="/WEB-INF/reportdetail/reportFileUnMatch.jsp"),
	@Result(name="modifyInfo",location="/WEB-INF/reportdetail/modifyInfo.jsp"),
	@Result(name="errorInfo",location="/WEB-INF/reportdetail/reportFileError.jsp"),
	@Result(name="showWillPrintAgain",location="/WEB-INF/reportdetail/showWillPrintAgain.jsp"),
	@Result(name="showSynchronizeInfo",location="/WEB-INF/reportdetail/showSynchronizeInfo.jsp"),
})

/**
 * 未匹配报告Action
 * @author ybc
 */
public class ErpReportUnMatchAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private File pdffile;

	private Logger log = Logger.getLogger(ErpReportUnMatchAction.class);
	
	ErpReportUnMatchService unMatchSerice = (ErpReportUnMatchService) SpringTool.getBean(ErpReportUnMatchService.class);
	ErpReportdetailPDFContentService contentService = (ErpReportdetailPDFContentService) SpringTool.getBean(ErpReportdetailPDFContentService.class);
	ErpCustomerService customerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	ErpReportFileTaskService taskService = (ErpReportFileTaskService) SpringTool.getBean(ErpReportFileTaskService.class);
	//查询
	public String query(){
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			paramsMap.put("order_createdate", "desc");
			paramsMap.put("filter_and_modifystate_EQ_S","1");	//更改状态,0:customer多条1:与customer数据不匹配;2:已更改;3:人工处理;默认是1
			page = findByPage(page, paramsMap);
		} catch (ParseException e) {
			log.error("ErpReportUnMatchAction query", e);
		}
		return "listUnMatch";
	}
	
	//查询PDF错误信息
	public String queryPdfError(){
		try{
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			Map paramsMap = buildSearch();
			paramsMap.put("filter_and_modifystate_EQ_S","3");
			page = findByPage(page, paramsMap);
		}catch(ParseException e){
			log.error("ErpReportUnMatchAction queryPdfError", e);
		}
		return "errorInfo";
	}
	
	//查询会员信息
	public String queryModifyInfo(){
		String unmatchid = HttpTool.getParameter("id");
		ErpReportUnMatch unmatchbean = (ErpReportUnMatch) unMatchSerice.findById(unmatchid);
		HttpTool.setAttribute("unmatchbean", unmatchbean);
		return "modifyInfo";
	}
	
	//更新会员信息
	public void updateInfo(){
		JSONObject json = new JSONObject();
		String unmatchid = HttpTool.getParameter("id");
		String name = HttpTool.getParameter("modifyname");
		String sex = HttpTool.getParameter("modifysex");
		String modifyid = HttpTool.getParameter("tomodifyid");
		unMatchSerice.updateCustomerInfo(name,sex,modifyid);
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		unMatchSerice.updateUnMatchInfo(unmatchid,currentUser);
		json.put("result","success");
		renderJson(json);
	}
	
	//更新PDF信息
	public void updatePdfInfo(){
		String modifyid = HttpTool.getParameter("tomodifyid");
		String unmatchid = HttpTool.getParameter("id");
		String name = HttpTool.getParameter("modifyname");
		String sex = HttpTool.getParameter("modifysex");
		String age = HttpTool.getParameter("modifyage");
		JSONObject json = new JSONObject();
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		try{
			//不用处理PDF文件
			if(null!=this.getPdffile()){
				File file = this.getPdffile();
				String oldPdfPath = contentService.getPdfPathById(modifyid);
				inputStream = new FileInputStream(file);
				outputStream = new FileOutputStream(oldPdfPath);
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buf)) != -1) {
					outputStream.write(buf, 0, length);
				}
				outputStream.flush();
				unMatchSerice.updatePdfContent(name,sex,age,modifyid);
				unMatchSerice.updateUnMatchInfo(unmatchid,currentUser);
				json.put("result","success");
			}else{
				unMatchSerice.updatePdfContent(name,sex,age,modifyid);
				unMatchSerice.updateUnMatchInfo(unmatchid,currentUser);
				json.put("result","success");
			}
			
		}catch(Exception e){
			log.error("ErpReportUnMatchAction updatePdfInfo error", e);
			json.put("result","fail");
		}finally{
			try{
				if(inputStream!=null){
					inputStream.close();
				}
				if(outputStream!=null){
					outputStream.close();
				}
			}catch(IOException e){
				log.error("ErpReportUnMatchAction updatePdfInfo IOException", e);
			}
			renderJson(json);
		}
	}
	
	//PDF报告错误
	public void dealPdfError(){
		String unmatchid = HttpTool.getParameter("id");
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		unMatchSerice.updateUnMatchPdfError(unmatchid,currentUser);
		json.put("result","success");
		renderJson(json);
	}
	
	//通过批次处理未匹配成功的数据
	public void dealUnMatchByBatchno(){
		JSONObject json = new JSONObject();
		String batchno = HttpTool.getParameter("batchno");
		List<ErpReportdetailPDFContent> contentMatch3 = contentService.queryContentsByUnMatch3(batchno);
		List<ErpReportdetailPDFContent> contentMatch4 = contentService.queryContentsByUnMatch4(batchno);
		unMatchSerice.dealUnMatch3InMatchInfo(contentMatch3);
		unMatchSerice.dealUnMatch4InMatchInfo(contentMatch4);
		json.put("result","success");
		renderJson(json);
	}
	
	//同步PDF内容至系统会员信息
	public void syncPdfContent2Cus(){
		JSONObject json = new JSONObject();
		String cusid = HttpTool.getParameter("cusid");
		String unmatchid = HttpTool.getParameter("id");
		String pdfid = HttpTool.getParameter("pdfid");
		ErpReportdetailPDFContent content = (ErpReportdetailPDFContent) contentService.findById(pdfid);
		//List<ErpReportdetailPDFContent> content = contentService.queryContentsByCode(code);
		try {
			String name = content.getUsername();
			String sex = content.getSex();
			unMatchSerice.updateCustomerInfo(name,sex,cusid);	//更新客户表的姓名,性别,年龄
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			unMatchSerice.updateUnMatchInfo(unmatchid,currentUser);	//更新状态为已更改
			//重新匹配
			List<ErpReportdetailPDFContent> toMatchPdf = new ArrayList<ErpReportdetailPDFContent>();
			toMatchPdf.add(content);
			contentService.dealUnMachPdf(toMatchPdf);
			json.put("result","success");
			
		} catch (Exception e) {
			json.put("result","fail");
			log.error("同步会员信息失败---",e);
		}
			
		renderJson(json);
		
	}
	
	//同步系统会员信息至PDF内容
	public void syncCus2PdfContent(){
		JSONObject json = new JSONObject();
		String cusid = HttpTool.getParameter("cusid");
		String unmatchid = HttpTool.getParameter("id");
		String pdfid = HttpTool.getParameter("pdfid");
		ErpCustomer customer = (ErpCustomer)customerService.findById(cusid);
		//List<ErpCustomer> customer = customerService.getCustomerByCode(code);
		try {
			String name = customer.getName();
			String sex = customer.getSex();
			String age = customer.getAge();
			unMatchSerice.updatePdfContent(name,sex,age,pdfid);
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			unMatchSerice.updateUnMatchInfo(unmatchid,currentUser);
			//重新匹配
			ErpReportdetailPDFContent content = (ErpReportdetailPDFContent) contentService.findById(pdfid);
			List<ErpReportdetailPDFContent> toMatchPdf = new ArrayList<ErpReportdetailPDFContent>();
			toMatchPdf.add(content);
			contentService.dealUnMachPdf(toMatchPdf);
			json.put("result","success");
		} catch (Exception e) {
			json.put("result","fail");
			log.error("同步报告信息失败---",e);
		}
		renderJson(json);
		
	}
	
	public String toShowWillPrintAgain(){
		return "showWillPrintAgain";
	}
	
	//手动更改异常数据之后重新生成打印批次1、先更新完数据
	public void toWillPrintBatchAgain(){
		JSONObject json = new JSONObject();
		String batchno = HttpTool.getParameter("batchno");
		List<ErpReportdetailPDFContent> toMatchPdf = contentService.queryWillMathPdfByBthno(batchno);
		try {
			switch (toMatchPdf.size()) {
			case 0:
				json.put("result",0);
				break;

			default:
				contentService.dealUnMachPdf(toMatchPdf);
				json.put("result","success");
				break;
			}
		} catch (Exception e) {
			json.put("result","fail");
			log.error("更新批次任务失败---", e);
		}
		renderJson(json);
	}
	
	/*
	 * 异常数据与未匹配数据重新生成打印批次1、更新数据
	 * 若为异常数据，需要查询已更改过的数据进行重新生成
	 * 若为未匹配数据，则需要重新比对
	 */
	public void issueContentInfo2WillPrint(){
		JSONObject json = new JSONObject();
		//1:全部;2:异常数据;3:未匹配数据;4:会员信息多条
		String state = HttpTool.getParameter("state");
		List<ErpReportdetailPDFContent> pdfUnMatchList = new ArrayList<ErpReportdetailPDFContent>();
		List<ErpReportdetailPDFContent> pdfAbnormalList = new ArrayList<ErpReportdetailPDFContent>();
		List<ErpReportdetailPDFContent> pdfMoreCusList = new ArrayList<ErpReportdetailPDFContent>();
		try {
			if(state.equals("1")){
				pdfUnMatchList = contentService.queryContentUnMatch();
				pdfAbnormalList = contentService.queryContentAbnormal();
				pdfMoreCusList = contentService.queryContentMoreCustomer();
			}
			if(state.equals("2")){
				pdfAbnormalList = contentService.queryContentAbnormal();
			}
			if(state.equals("3")){
				pdfUnMatchList = contentService.queryContentUnMatch();
			}
			if(state.equals("4")){
				pdfMoreCusList = contentService.queryContentMoreCustomer();
			}
			//处理未匹配的数据和会员信息多条和处理异常数据
			if(0<pdfUnMatchList.size()||0<pdfMoreCusList.size()||0<pdfAbnormalList.size()){
				List<ErpReportdetailPDFContent> toMatchPdf = new ArrayList<ErpReportdetailPDFContent>();
				toMatchPdf.addAll(pdfUnMatchList);
				toMatchPdf.addAll(pdfMoreCusList);
				toMatchPdf.addAll(pdfAbnormalList);
				contentService.dealUnMachPdf(toMatchPdf);//重新匹配
			}
			json.put("result","success");
		} catch (Exception e) {
			json.put("result", "fail");
			
		}
		renderJson(json);
	}
	
	//手动更改异常数据之后重新生成打印批次2、生成打印批次
	public void createWillPrintBatchAgain(){
		JSONObject json = new JSONObject();
		ErpReportWillPrintJob willPrint = (ErpReportWillPrintJob) SpringTool.getBean("batchPrintJobBean");
		willPrint.execute();
		json.put("result","success");
		renderJson(json);
	}
	
	//取消打印
	public void cancelPrint(){
		JSONObject json = new JSONObject();
		String pdfid = HttpTool.getParameter("pdfid");
		String batchNo = HttpTool.getParameter("batchNo");
		String unMatchId = HttpTool.getParameter("unMatchId");
		try {
			contentService.cancelPrint(pdfid, batchNo, unMatchId);
			json.put("result", 1);
			
		} catch (Exception e) {
			json.put("result", 0);
			log.error("取消打印失败---", e);
			
		} finally {
			renderJson(json);
		}
		
	}
	
	/**
	 * @since 2017年3月1日18:23:22
	 * @author Carly
	 * @return 显示同步信息
	 */
	public String showSynchronizeInfo() {
		String unMatchId = HttpTool.getParameter("unMatchId");
		ErpReportUnMatch unMatch = (ErpReportUnMatch) unMatchSerice.findById(unMatchId);
		HttpTool.setAttribute("unMatch", unMatch);
		HttpTool.setAttribute("unMatchId", unMatchId);
		HttpTool.setAttribute("cusId", HttpTool.getParameter("cusId"));
		HttpTool.setAttribute("pdfId", HttpTool.getParameter("pdfId"));
		HttpTool.setAttribute("synType", HttpTool.getParameter("synType"));
		
		return "showSynchronizeInfo";
	}
	
	
	/**
	 * @since 2017年3月2日11:26:16
	 * @author Carly
	 * @return 更新pdfcontent表的信息
	 */
	public void updatePdfContentInfo() {
		String unMatchId = HttpTool.getParameter("unMatchId");
//		String cusId = HttpTool.getParameter("cusId");
		String pdfId = HttpTool.getParameter("pdfId");
		String pdfCode = HttpTool.getParameter("pdfCode");
		String pdfUserName = HttpTool.getParameter("pdfUserName");
		String pdfUserSex = HttpTool.getParameter("pdfUserSex");
		String pdfCombo = HttpTool.getParameter("pdfCombo");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		try {
			unMatchSerice.updatePdfContent(pdfId,pdfCombo,pdfCode,pdfUserName,pdfUserSex);
			unMatchSerice.updateUnMatchPdfError(unMatchId, currentUser,pdfCombo,pdfCode,pdfUserName,pdfUserSex);
			ErpReportdetailPDFContent content = (ErpReportdetailPDFContent) contentService.findById(pdfId);
			List<ErpReportdetailPDFContent> toMatchPdf = new ArrayList<ErpReportdetailPDFContent>();
			toMatchPdf.add(content);
			contentService.dealUnMachPdf(toMatchPdf);
			json.put("result","success");
		} catch (Exception e) {
			log.error("updatePdfContentInfo(同步会员信息出错)", e);
		}
		renderJson(json);
	}
	
	public Page findByPage(Page page , Map paramsMap){
		unMatchSerice.findByPage(page, paramsMap);
		return page ;
	}

	public File getPdffile() {
		return pdffile;
	}

	public void setPdffile(File pdffile) {
		this.pdffile = pdffile;
	}
	
}
