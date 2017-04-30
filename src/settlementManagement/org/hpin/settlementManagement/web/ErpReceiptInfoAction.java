package org.hpin.settlementManagement.web;

import java.io.File;
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
import org.hpin.settlementManagement.service.ErpReceiptInfoService;

import org.hpin.settlementManagement.entity.VO.ErpReceiptInfoVO;
import org.hpin.settlementManagement.entity.VO.ReceiptInfoQueryObj;
import org.hpin.settlementManagement.exception.BankStatementNullException;
import org.hpin.settlementManagement.exception.BankStatementRepeatException;
import org.springframework.beans.factory.annotation.Autowired;


@Namespace("/settlementManagement")
@Action("erpReceiptInfo")
@Results({
	@Result(name="listReceiptAndClaimInfo",location="/WEB-INF/settlementManagement/receiptInfo/listReceiptAndClaimInfo.jsp"),
	@Result(name="toImportReceiptInfoExcel",location="/WEB-INF/settlementManagement/receiptInfo/importReceiptInfoExcel.jsp"),
	@Result(name="toImportClaimInfoExcel",location="/WEB-INF/settlementManagement/receiptInfo/importClaimInfoExcel.jsp"),
	@Result(name="showReceiptInfo",location="/WEB-INF/settlementManagement/receiptInfo/showReceiptInfo.jsp"),
})  
public class ErpReceiptInfoAction extends BaseAction {
	private Logger log = Logger.getLogger(ErpReceiptInfoAction.class);
	
	@Autowired
	private ErpReceiptInfoService service ;
	
	private ReceiptInfoQueryObj queryObj;
	
	private File affix;
	
	/**
	 * 展示
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-29下午3:51:19
	 */
	public String listReceiptAndClaimInfo(){
		try {
			page = new Page<ErpReceiptInfoVO>(HttpTool.getPageNum(),HttpTool.getPageSize());
			
			service.findReceiptInfoAll(page, queryObj);
			
		} catch (Exception e) {
			log.error("ErpReceiptInfoAction listReceiptAndClaimInfo Exception--"+e);
		}
		
		return "listReceiptAndClaimInfo";
	}
	
	/**
	 * 展示所有的回款数据
	 * @return
	 * @author LeslieTong
	 * @date 2017-4-1上午11:44:49
	 */
	public String showReceiptInfo(){
		Map searchMap = super.buildSearch();
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			log.error("ErpReceiptInfoAction showReceiptInfo -- "+e);
		}
		
		service.findByPage(page,searchMap);
		
		return "showReceiptInfo";
	}
	
	/**
	 * 导入回款数据表默认页面
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-29下午3:44:55
	 */
	public String toImportReceiptInfoExcel(){
		HttpTool.setAttribute("navTabId", super.navTabId);
		
		return "toImportReceiptInfoExcel";
	}
	
	/**
	 * 导入认领明细数据表默认页面
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-29下午3:46:23
	 */
	public String toImportClaimInfoExcel(){
		HttpTool.setAttribute("navTabId", super.navTabId);
		
		return "toImportClaimInfoExcel";
	}
	
	/**
	 * 导入回款数据
	 * 
	 * @author tangxing
	 * @date 2017-3-29下午4:04:44
	 */
	public void importReceiptInfoExcel(){
		JSONObject json = new JSONObject();
		String filePath = HttpTool.getParameter("filePath");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		
		List<Map<String, String>> excelResult = null;	//存放读取出来的Excel信息
		
		try {
			excelResult = new ArrayList<Map<String,String>>();
			
			if(null!=affix){
				if(filePath.indexOf(".xlsx")!=-1){
					excelResult = service.readExcelXlsx(affix);
				}else if(filePath.indexOf(".xls")!=-1){
					excelResult = service.readExcelXls(affix);
				}
				
				Map<String, String> messageMap = service.saveReceiptInfoObject(excelResult, currentUser,filePath);
				
				json.put("message", messageMap.get("message"));
				json.put("statusCode", messageMap.get("statusCode"));
			}else{
				json.put("statusCode", 300);
				json.put("message", "数据为空,导入失败");
				log.info("importReceiptInfoExcel "+filePath+" import xlsx file is null");
			}
			
			json.put("navTabId", super.navTabId);
			json.put("callbackType", "closeCurrent");
		} catch (BankStatementRepeatException repeatException) {	//自定义银行流水号重复异常
			json.put("statusCode", 300);
			json.put("message", repeatException.getMessage());
		} catch (Exception e) {
			log.error(filePath+" ErpReceiptInfoAction importReceiptInfoExcel--",e);
			json.put("statusCode", 300);
			json.put("message", "导入失败");
		}
		
		renderJson(json);
	}
	
	/**
	 * 导入认领明细数据
	 * 
	 * @author tangxing
	 * @date 2017-3-29下午4:05:00
	 */
	public void importClaimInfoExcel(){
		JSONObject json = new JSONObject();
		String filePath = HttpTool.getParameter("filePath");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		
		List<Map<String, String>> excelResult = null;	//存放读取出来的Excel信息
		
		try {
			excelResult = new ArrayList<Map<String,String>>();
			
			if(null!=affix){
				if(filePath.indexOf(".xlsx")!=-1){
					excelResult = service.readExcelXlsx(affix);
				}else if(filePath.indexOf(".xls")!=-1){
					excelResult = service.readExcelXls(affix);
				}
				
				Map<String, String> messageMap = service.saveClaimInfoObject(excelResult, currentUser);
				
				json.put("message", messageMap.get("message"));
				json.put("statusCode", messageMap.get("statusCode"));
			}else{
				json.put("statusCode", 300);
				json.put("message", "数据为空,导入失败");
				log.info("importClaimInfoExcel "+filePath+" import xlsx file is null");
			}
			
			json.put("navTabId", super.navTabId);
			json.put("callbackType", "closeCurrent");
		} catch (BankStatementRepeatException repeatException) {	//自定义异常
			json.put("statusCode", 300);
			json.put("message", repeatException.getMessage());
		} catch (BankStatementNullException e) {	//自定义异常
			json.put("statusCode", 300);
			json.put("message", e.getMessage());
		} catch (Exception e) {
			log.error(filePath+" ErpReceiptInfoAction importClaimInfoExcel --",e);
			json.put("statusCode", 300);
			json.put("message", "导入失败");
		}
		
		renderJson(json);
	}
	
	
	public File getAffix() {
		return affix;
	}

	public void setAffix(File affix) {
		this.affix = affix;
	}

	public ReceiptInfoQueryObj getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(ReceiptInfoQueryObj queryObj) {
		this.queryObj = queryObj;
	}
	
}
