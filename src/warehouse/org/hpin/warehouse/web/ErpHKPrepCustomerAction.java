package org.hpin.warehouse.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.hpin.warehouse.entity.vo.ErpPreCustomerQueryObj;
import org.hpin.warehouse.entity.vo.ErpPrepCustomerVO;
import org.hpin.warehouse.service.ErpHKPrepCustomerService;
import org.hpin.webservice.websExt.impl.GeneServiceImplPA;
import org.hpin.webservice.websExt.impl.GeneServiceImplPAServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 弘康预导入客户Action
 * @author tangxing
 * @date 2016-12-29下午3:06:22
 */

@Namespace("/warehouse")
@Action("prepCustomer")
@Results({
	@Result(name = "listPrepCustomer", location = "/WEB-INF/warehouse/HKPrepCustomer/listHKPrepCustomer.jsp"),
	@Result(name = "addOrUpdatePrepCustomer", location = "/WEB-INF/warehouse/HKPrepCustomer/addOrUpdatePrepCustomer.jsp"),
	@Result(name = "updatePrepCustomer", location = "/WEB-INF/warehouse/HKPrepCustomer/updatePrepCustomer.jsp"),
	@Result(name = "preCustomerListEdit", location = "/WEB-INF/warehouse/YAPrepCustomer/preCustomerListEdit.jsp"),
	@Result(name = "addPACustomer", location = "/WEB-INF/warehouse/HKPrepCustomer/addPACustomer.jsp"),
	@Result(name = "updateErrorCustomer", location = "/WEB-INF/warehouse/HKPrepCustomer/updateErrorCustomer.jsp"),
})
public class ErpHKPrepCustomerAction extends BaseAction {

	private static final long serialVersionUID = -8144032850348793887L;

	private Logger log = Logger.getLogger(ErpHKPrepCustomerAction.class);
	
	ErpPreCustomer prepCustomer;
	
	/*@Autowired
	ErpHKPrepCustomerService service;*/
	
	private ErpHKPrepCustomerService service = (ErpHKPrepCustomerService) SpringTool.getBean("erpHKPrepCustomerService");
	@Value("${report.upload.pa.address}")
	private String paAddress ;//测试环境
	@Autowired
	ErpCustomerService customerService;
	
	@Autowired
	ErpEventsService eventsService;
	
	@Autowired
	CustomerRelationShipService customerRelationShipService;
	
	ErpPreCustomerQueryObj preCustomerQueryObj;
	
	private String affixFileName; //文件上传名称;
	private File affix; //文件
    private String affixContentType; //文件类型;
	
    /**
     * 客户信息清单excel文件上传;
     * create by henry.xu 2017年1月18日
     */
    public void preExcelImport() {
    	JSONObject json = new JSONObject();
		String message = "";
		String statusCode = "";
		try {
			log.info("上传的excel文件名: " + affixFileName);
			if(StringUtils.isNotEmpty(affixFileName)) {
				//获取后缀名;
				String type = affixFileName.substring(affixFileName.lastIndexOf(".")+1, affixFileName.length());
				
				if("xlsx".equals(type) || "xls".equals(type)) {
					this.service.savePreCustomerByExcel(affix, affixFileName, getUserInfo(), params);
					message = "Excel导入成功!";
					log.info("客户信息保存后消息: " + message);
					statusCode = "200";
					json.accumulate("callbackType", "closeCurrent");
				} else {
					statusCode = "300";
					message = "导入的文件格式不正确, 请下载模板导入!";
				}
				
				
			}
			
		} catch (Exception ex) {
			log.info("saveCustomer Exception"+ex);
			statusCode = "300";
			message = ex.getMessage();
		}finally{
			json.put("navTabId", navTabId);
			json.put("message", message);
			json.put("statusCode", statusCode);
			renderJson(json);
		}
    }
    
	/**
	 * 预设值用户导入界面跳转; 易安;
	 * create by henry.xu 2017年1月18日
	 * @return
	 */
	public String preCustomerListEdit() {
		return "preCustomerListEdit";
	}
	
	
	public String listPrepCustomerForHibernate(){
		Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
		List<ErpPreCustomer> list = null;
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			list = service.getListPrepCustomer(page, searchMap);
		} catch (Exception e) {
			log.error("ErpHKPrepCustomerAction listPrepCustomer",e);
		}
		page.setResults(list); 
		
		return "listPrepCustomer";
	}
	
	public String listPrepCustomer(){
		try {
			page = new Page<ErpPrepCustomerVO>(HttpTool.getPageNum(),HttpTool.getPageSize());
			service.findPrepCustomerAll(page,preCustomerQueryObj);
		}catch (Exception e) {
			log.error("ErpHKPrepCustomerAction listPrepCustomer Exception--"+e);
		}	
		
		return "listPrepCustomer";
	}
	
	/**
	 * 录入客户及修改信息默认页面
	 * 
	 * @author tangxing
	 * @date 2016-12-30上午11:30:37
	 */
	public String toAddOrUpdatePrepCustomer(){
		String ymComboName = "";
		String id = HttpTool.getParameter("id");
		String navTabId = HttpTool.getParameter("navTabId");
		ErpPreCustomer prepCustomer = service.getErpPreCustomerById(id);
		/*if(prepCustomer!=null){		//add by LeslieTong. 到中间表ERP_YMCOMBONAME_TESTCOMBONAME取对应远盟套餐
			String comboName = prepCustomer.getCheckCobmo().trim();	//弘康检测机构套餐
			ymComboName = service.getYMComboNameByTestComboName(comboName);
			if(StringUtils.isNotEmpty(ymComboName)){
				prepCustomer.setCheckCobmo(ymComboName);
			}
		}*/
		HttpTool.setAttribute("prepCustomer",prepCustomer);
		HttpTool.setAttribute("simpleingDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		HttpTool.setAttribute("navTabId", navTabId);
		return "addOrUpdatePrepCustomer";
	}
	
	/**
	 * 变更PrepCustomer默认页面
	 * @return
	 * @author tangxing
	 * @date 2017-1-5下午2:41:51
	 */
	public String toUpdatePrepCustomer(){
		String id = HttpTool.getParameter("id");
		String navTabId = HttpTool.getParameter("navTabId");
		if(StringUtils.isNotEmpty(id)){
			ErpPreCustomer prepCustomer = service.getErpPreCustomerById(id);
			if(StringUtils.isNotEmpty(prepCustomer.getCode())){		//未有条形码，就取当前时间
				String eventsNo = prepCustomer.getEventsNo();
				String idCard = prepCustomer.getWereIdcard();
				String name = prepCustomer.getWereName();
				ErpCustomer customerTemp = customerService.getCustomerByIdnoAndName(idCard,name);
				HttpTool.setAttribute("simpleingDate", customerTemp.getSamplingDate()!=null?new SimpleDateFormat("yyyy-MM-dd").format(customerTemp.getSamplingDate()):"");
				HttpTool.setAttribute("erpCustomer",customerTemp);
			}
			HttpTool.setAttribute("prepCustomer",prepCustomer);
		}
		HttpTool.setAttribute("navTabId", navTabId);
		return "updatePrepCustomer";
	}
	
	/**
	 * 录入客户及修改信息
	 * 
	 * @author tangxing
	 * @date 2016-12-30上午11:30:37
	 */
	public void addOrUpdatePrepCustomer(){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String navTabId = HttpTool.getParameter("navTabId");
		String simpleingDate = HttpTool.getParameter("simpleingDate");
		String userName=currentUser.getUserName();
		String userId = currentUser.getId();
		JSONObject json = new JSONObject();
		//ErpEvents erpEvents = new ErpEvents();
		
		if(prepCustomer!=null){
			String code = prepCustomer.getCode();
			List<ErpPreCustomer> preCustomers = service.getPreCustomerByCode(code);
			if(preCustomers!=null&&preCustomers.size()>0){		//根据code能查到ErpPreCustomer（重复）
				json.accumulate("statusCode", 300);
				json.accumulate("message", "保存失败,条形码重复！");
			}else{
				try {
					String customerId = "";
					String eventsNo = prepCustomer.getEventsNo();
					String idCard = prepCustomer.getWereIdcard();
					String name = prepCustomer.getWereName();
					ErpCustomer customerTemp = customerService.getCustomerByIdnoAndName(idCard,name);
					//erpEvents = eventsService.queryEventNO(eventsNo);
					if(customerTemp==null){		//erp_customer表中没有还没有数据
						ErpCustomer customer = new ErpCustomer();
						customer.setCode(prepCustomer.getCode());
						customer.setName(prepCustomer.getWereName());
						customer.setIdno(prepCustomer.getWereIdcard());
						customer.setPhone(prepCustomer.getWerePhone());
						customer.setSex(prepCustomer.getWereSex());
						customer.setAge(prepCustomer.getWereAge());
						customer.setWeight(prepCustomer.getWereWeight());
						customer.setHeight(prepCustomer.getWereHeight());
						customer.setSamplingDate(StringUtils.isNotEmpty(simpleingDate)?new SimpleDateFormat("yyyy-MM-dd").parse(simpleingDate):null);
						//customer.setEventsNo(prepCustomer.getEventsNo());
						customer.setNote(prepCustomer.getRemark());
						customer.setSetmealName(prepCustomer.getCheckCobmo());
						customer.setCustomerHistory(prepCustomer.getCustomerHistory());	//既往病史
						customer.setFamilyHistory(prepCustomer.getFamilyHistory());	//家族病史

						customer.setStatusYm(150);		//样本已获取状态
						customer.setCreateTime(new Date());
						customer.setCreateUserName(userName);
						customer.setCreateUserId(userId);
						customer.setIsDeleted(0);
						customer.setStatus("0");
						
						ErpEvents erpEvents = eventsService.queryEventNO(eventsNo);
						/* ****customer场次相关**** */
						if(erpEvents!=null){
							customer.setBranchCompany(erpEvents.getBranchCompany());
							customer.setBranchCompanyId(erpEvents.getBranchCompanyId());
							customer.setOwnedCompany(erpEvents.getOwnedCompany());
							customer.setOwnedCompanyId(erpEvents.getOwnedCompanyId());
							CustomerRelationShip customerRelationShip = customerRelationShipService.getCustomerRelationShipById(erpEvents.getBranchCompanyId());
							if(customerRelationShip!=null){
								customer.setCity(customerRelationShip.getCity());
								customer.setProvice(customerRelationShip.getProvince());
							}
							
							CustomerRelationShipPro customerRelationShipPro = customerRelationShipService.getCustomerRelationShipProById(erpEvents.getCustomerRelationShipProId());
							customer.setSalesMan(customerRelationShipPro.getProjectOwner());		//项目负责人
						}
						
						customerId = customerService.saveCustomer(customer);
						log.info("saveCustomer customerId -- "+customerId);
						
						/* ****预导入表相关信息**** */
						ErpPreCustomer preCustomerTemp = service.getErpPreCustomerById(prepCustomer.getId());
						if(preCustomerTemp!=null){
							if(StringUtils.isNotEmpty(customerId)){
								preCustomerTemp.setErpCustomerId(customerId);
							}
							
							preCustomerTemp.setCheckboxEmilAddr(prepCustomer.getCheckboxEmilAddr());
							preCustomerTemp.setReportSendAddr(prepCustomer.getReportSendAddr());
							preCustomerTemp.setReportReceiveName(prepCustomer.getReportReceiveName());
							preCustomerTemp.setPhone(prepCustomer.getPhone());
							preCustomerTemp.setUpdateTime(new Date());
							preCustomerTemp.setUpdateUserId(userId);
							
							/* ****ErpCustomer相关的信息修改**** */
							preCustomerTemp.setCode(prepCustomer.getCode());
							preCustomerTemp.setWereSex(prepCustomer.getWereSex());
							preCustomerTemp.setWereAge(prepCustomer.getWereAge());
							preCustomerTemp.setWereHeight(prepCustomer.getWereHeight());
							preCustomerTemp.setWereWeight(prepCustomer.getWereWeight());
							preCustomerTemp.setCustomerHistory(prepCustomer.getCustomerHistory());
							preCustomerTemp.setFamilyHistory(prepCustomer.getFamilyHistory());
							preCustomerTemp.setRemark(prepCustomer.getRemark());
							
							service.update(preCustomerTemp);
						}
						
					}
					json.accumulate("statusCode", 200);
					json.accumulate("message", "保存成功！");
					json.accumulate("navTabId", navTabId);
					json.accumulate("callbackType", "closeCurrent");
				} catch (Exception e) {
					json.accumulate("statusCode", 300);
					json.accumulate("message", "保存失败！");
					log.error("ErpHKPrepCustomer addOrUpdatePrepCustomer -- "+e);
				}
			}
			
		}else{
			json.accumulate("statusCode", 300);
			json.accumulate("message", "保存失败！");
		}
		renderJson(json);
	}
	
	/**
	 * 设置预导入的场次号、批次号，并创建场次
	 * @author tangxing
	 * @date 2017-2-15下午5:11:20
	 */
	public void addPreCustomerEventsAndBtachNo(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String navTabId = HttpTool.getParameter("navTabId");
		String simpleingDate = HttpTool.getParameter("simpleingDate");
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		hashMap.put("currentUser", currentUser);
		hashMap.put("navTabId", navTabId);
		hashMap.put("simpleingDate", simpleingDate);
		hashMap.put("prepCustomer", prepCustomer);
		
		try {
			json = service.saveErpCustomerAndEvents(hashMap);
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "保存失败！");
			log.error("保存cutomer、events，返回json异常--"+e);
		}
		
		renderJson(json);
	}
	
	/**
	 * 变更prepCustomer
	 * 
	 * @author tangxing
	 * @date 2017-1-5下午3:47:27
	 */
	public void updatePrepCustomer(){
		JSONObject json = new JSONObject();
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String userName=currentUser.getUserName();
		String userId = currentUser.getId();
		String navTabId = HttpTool.getParameter("navTabId");
		
		/* ****表单提交内容**** */
		String id = HttpTool.getParameter("id");
		String checkboxEmilAddr = HttpTool.getParameter("checkboxEmilAddr");
		String reportSendAddr = HttpTool.getParameter("reportSendAddr");
		String phone = HttpTool.getParameter("phone");
		String reportReceiveName = HttpTool.getParameter("reportReceiveName");
		
		try {
			ErpPreCustomer preCustomerTemp = service.getErpPreCustomerById(id);
			if(preCustomerTemp!=null){
				preCustomerTemp.setCheckboxEmilAddr(checkboxEmilAddr);
				preCustomerTemp.setReportSendAddr(reportSendAddr);
				preCustomerTemp.setReportReceiveName(reportReceiveName);
				preCustomerTemp.setPhone(phone);
				preCustomerTemp.setUpdateTime(new Date());
				preCustomerTemp.setUpdateUserId(userId);
				
				service.update(preCustomerTemp);
				
				json.accumulate("statusCode", 200);
				json.accumulate("message", "修改成功！");
				json.accumulate("navTabId", navTabId);
				json.accumulate("callbackType", "closeCurrent");
			}else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", "修改失败！");
			}
		} catch (Exception e) {
			json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败！");
			log.error("ErpHKPrepCustomer updatePrepCustomer -- "+e);
		}
		
		HttpTool.setAttribute("navTabId", navTabId);
		
		renderJson(json);
	}
	
	/**
	 * 删除prepCustomer
	 * 
	 * @author tangxing
	 * @date 2016-12-30上午11:31:23
	 */
	public void deletePrepCustomer(){
		JSONObject json = new JSONObject();
		String id = HttpTool.getParameter("id");
		String status = "";
		String message = "";
		if(StringUtils.isNotEmpty(id)){
			ErpPreCustomer preCcustomer = service.getErpPreCustomerById(id);
			if(preCcustomer!=null){
				try {
					preCcustomer.setIsDeleted(1);
					service.update(preCcustomer);
					status = "200";
					message = "删除成功！";
				} catch (Exception e) {
					status = "300";
					message = "删除失败！";
					log.error("ErpHKPrepCustomer deletePrepCustomer -- "+e);
				}
			}else{
				status = "300";
				message = "删除失败！";
			}
		}else{
			status = "300";
			message = "删除失败！";
		}
		json.put("status", status);
		json.put("message", message);
		renderJson(json);
	}
	
	
	/**
	 * 导出excel文件
	 * 
	 * @author tangxing
	 * @date 2017-1-2下午7:21:37
	 */
	public void exportExcelPrepCustomer(){
		JSONObject json = new JSONObject();
		JSONObject jsonObject = JSONObject.fromObject(HttpTool.getParameter("data"));	//查询条件
		log.info("exportExcelPrepCustomer where -- "+jsonObject.toString());
		String excelPath = "";
		try {
			excelPath = service.exportExcelPrepCustomer(jsonObject);
			json.put("status", "200");
			json.put("message", excelPath);
		}catch (Exception e) {
			json.put("status", "300");
			json.put("message", "导出失败！");
			log.error("ErpPrepCustomerAction exportExcelPrepCustomer--"+e);
		}
		renderJson(json);
	}
	
	/**
	 * 导出excel文件（文件流）
	 * 
	 * @author tangxing
	 * @date 2017-1-11上午11:12:10
	 */
	public void exportExcelForStrem(){
		HttpServletResponse response = ServletActionContext.getResponse();
		String pBatchNo = HttpTool.getParameter("pBatchNo");
		String wereName = HttpTool.getParameter("wereName");
		String code = HttpTool.getParameter("code");
		String wereIdcard = HttpTool.getParameter("wereIdcard");
		String werePhone = HttpTool.getParameter("werePhone");
		String applicationNo = HttpTool.getParameter("applicationNo");
		String checkCombo = HttpTool.getParameter("checkCombo");
		String startTime = HttpTool.getParameter("startTime");
		String endTime = HttpTool.getParameter("endTime");
		String statusYm = HttpTool.getParameter("statusYm");
		String branchCompany = HttpTool.getParameter("branchCompany");
		
		HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("pBatchNo", pBatchNo);
		queryMap.put("wereName", wereName);
		queryMap.put("code", code);
		queryMap.put("wereIdcard", wereIdcard);
		queryMap.put("werePhone", werePhone);
		queryMap.put("applicationNo", applicationNo);
		queryMap.put("checkCombo", checkCombo);
		queryMap.put("startTime", startTime);
		queryMap.put("endTime", endTime);
		queryMap.put("statusYm", statusYm);
		queryMap.put("branchCompany", branchCompany);
		
		try {
			
			HashMap<String, String> excelPathMap = service.exportExcelPrepCustomerForStrem(queryMap);
			if(excelPathMap!=null){
				String currentPath = excelPathMap.get("curFilePath");
				String excelName = excelPathMap.get("excelName");
				response.setContentType("application/msexcel;");                
	            response.setHeader("Content-Disposition", new String(("attachment;filename="+excelName).getBytes("GB2312"), "UTF-8"));
				FileInputStream in = new FileInputStream(new File(currentPath+excelName));  
	            byte b[] = new byte[1024];  
	            int i = 0;  
	            ServletOutputStream out = response.getOutputStream();  
	            while((i=in.read(b))!=  -1){  
	                out.write(b, 0, i);  
	            }  
	            out.flush();  
	            out.close();  
	            in.close(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	/**
	 * @since 2017年2月15日15:42:21
	 * @author Carly
	 * @return 获取平安客户信息
	 */
	public String addPACustomer(){
		HttpTool.setAttribute("navTabId", HttpTool.getParameter("navTabId"));
		return "addPACustomer";
	}
	
	/**
	 * @since 2017年2月15日15:43:13
	 * @author Carly
	 * @return 检测失败客户
	 */
	public String updateErrorCustomer(){
		HttpTool.setAttribute("navTabId", HttpTool.getParameter("navTabId"));
		return "updateErrorCustomer";
	}
	/**
	 * @since 2017年2月15日15:42:21
	 * @author Carly
	 * @return 获取平安客户信息
	 */
	public void savePACustomer(){
		String code = HttpTool.getParameter("code");
		String navTabId = HttpTool.getParameter("navTabId");
		String info = "";
		
		JSONObject json = new JSONObject();
		
		try {
			GeneServiceImplPAServiceLocator push = new GeneServiceImplPAServiceLocator();
			push.setGeneServiceImplPAPortEndpointAddress(paAddress);
			GeneServiceImplPA paService = push.getGeneServiceImplPAPort();
			info = paService.getOrder("1",code);
			org.json.JSONObject object = new org.json.JSONObject(info);
			log.info("savePACustomer返回的客户信息:"+info);
			Boolean result = object.getBoolean("success");
			String msg = object.getString("msg");
			json.put("navTabId", navTabId);
			json.put("result", result);
			json.put("msg", msg);
		} catch (Exception e) {
			log.error("savePACustomer", e);
			e.printStackTrace();
		}
		renderJson(json);
	}
	
	/**
	 * @since 2017年2月15日15:43:13
	 * @author Carly
	 * @return 检测失败客户
	 */
	public void saveErrorCustomer(){
		String code = HttpTool.getParameter("code");
		String navTabId = HttpTool.getParameter("navTabId");
		String reason = HttpTool.getParameter("reason");
		JSONObject json = new JSONObject();
		String info = "";
		Boolean result = false;
		String msg = "";
		try {
			List<ErpPreCustomer> performNoList = service.getErpPreCustomerByCode(code);
			GeneServiceImplPAServiceLocator push = new GeneServiceImplPAServiceLocator();
			push.setGeneServiceImplPAPortEndpointAddress(paAddress);
			GeneServiceImplPA paService = push.getGeneServiceImplPAPort();
			int count = 0;
			switch (performNoList.size()) {
			case 0:
				break;

			default:
				count = 1;
				ErpPreCustomer preCustomer = performNoList.get(0);
				info = paService.detectFailed(preCustomer.getPerformNo(), reason);
				org.json.JSONObject object = new org.json.JSONObject(info);
				log.info("savePACustomer返回的客户信息:"+info);
				result = object.getBoolean("success");
				msg = object.getString("msg");
				if (result) {
					service.updateCustomerState(preCustomer.getErpCustomerId());
				}
				break;
			}
			
			json.put("navTabId", navTabId);
			json.put("result", result);
			json.put("msg", msg);
			json.put("count", count);
		} catch (Exception e) {
			log.error("saveErrorCustomer", e);
			e.printStackTrace();
		}
		renderJson(json);
	}
	
	

	public ErpPreCustomer getPrepCustomer() {
		return prepCustomer;
	}

	public void setPrepCustomer(ErpPreCustomer prepCustomer) {
		this.prepCustomer = prepCustomer;
	}

	public ErpPreCustomerQueryObj getPreCustomerQueryObj() {
		return preCustomerQueryObj;
	}

	public void setPreCustomerQueryObj(ErpPreCustomerQueryObj preCustomerQueryObj) {
		this.preCustomerQueryObj = preCustomerQueryObj;
	}

	public String getAffixFileName() {
		return affixFileName;
	}

	public void setAffixFileName(String affixFileName) {
		this.affixFileName = affixFileName;
	}

	public File getAffix() {
		return affix;
	}
	public void setAffix(File affix) {
		this.affix = affix;
	}
	public String getAffixContentType() {
		return affixContentType;
	}
	public void setAffixContentType(String affixContentType) {
		this.affixContentType = affixContentType;
	}
	
}
