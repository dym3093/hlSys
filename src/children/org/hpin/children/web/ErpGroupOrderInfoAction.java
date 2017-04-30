package org.hpin.children.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.children.entity.ErpEtOrderInfoBean;
import org.hpin.children.entity.ErpOrderInfo;
import org.hpin.children.entity.ExportOrderData;
import org.hpin.children.service.ErpGroupOrderInfoService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;

@Namespace("/children")
@Action("erpGroupOrderInfo")
@Results({
	@Result(name="erpGroupOrderList",location="/WEB-INF/children/groupOrder/erpGroupOrderList.jsp"),
	@Result(name="salemanGroupOrderList",location="/WEB-INF/children/groupOrder/salemanGroupOrderList.jsp"),
	@Result(name="erpBasicInfo",location="/WEB-INF/children/groupOrder/erpBasicCustomerList.jsp"),
    @Result(name="uploadFile",location="/WEB-INF/children/groupOrder/erpGroupOrderInfoUpload.jsp")
//    @Result(name="listErpComboPrice",location="/WEB-INF/children/comboPrice.jsp"),
}) 
@SuppressWarnings({"rawtypes"})
public class ErpGroupOrderInfoAction extends BaseAction{
	
	private File file;
	private String fileFileName;
	private String fileContentType;


	ErpGroupOrderInfoService groupOrderInfoService = (ErpGroupOrderInfoService)SpringTool.getBean(ErpGroupOrderInfoService.class);
	
	/**
	 * @return 团购订单信息表
	 * @throws Exception
	 */
	public String getErpGroupOrderList(){
		try {
			Map searchMap = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<Map<String, Object>> groupOrderInfoList = groupOrderInfoService.findGroupOrderInfo(page, searchMap);
			page.setResults(groupOrderInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "erpGroupOrderList";
	}
	
	public String saleManGroupOrderList(){//销售人员查看订单信息
		try {
			Map searchMap = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<Map<String, Object>> groupOrderInfoList = groupOrderInfoService.findGroupOrderInfo(page, searchMap);
			page.setResults(groupOrderInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "salemanGroupOrderList";
	}
	
	/**
	 * 是否是销售人员
	 */
	public void isSaleMan(){
		JSONObject json = new JSONObject();
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		int count = groupOrderInfoService.isSaleMan(user.getAccountName());
		json.put("count", count);
		renderJson(json);
	}
	
	/**
	 * @return 客户基本信息
	 * @throws Exception
	 */
	public String getCustomerBasicInfo(){
		try {
			Map searchMap = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			searchMap.put("filter_and_isDelete_LIKE_S", "1");
			page.setResults(groupOrderInfoService.findByPage(page, searchMap));
			if(searchMap.get("filter_and_status_LIKE_S")!=null){
				HttpTool.setAttribute("filter_and_status_LIKE_S", searchMap.get("filter_and_status_LIKE_S"));
			}
			if(searchMap.get("filter_and_status_LIKE_S")!=null){
				HttpTool.setAttribute("filter_and_status_LIKE_S", searchMap.get("filter_and_status_LIKE_S"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "erpBasicInfo";
	}
	
	public void getSelectInput(){
		JSONObject json = new JSONObject();
		json.put("combo", groupOrderInfoService.getSelectedInput());
		renderJson(json);
	}
	
	/**
	 * @return 转到导入页面
	 * @throws Exception
	 */
	public String showUploadPage(){
		HttpTool.setAttribute("orderNo", "团购订单【"+HttpTool.getParameter("orderNo").trim()+"】信息录入");
		HttpTool.setAttribute("id", "批次号："+HttpTool.getParameter("id" ).trim());
		return "uploadFile";
	}
	
	public String uploadFile(){
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		int count = 0;
		try{
			List<ErpOrderInfo> list = groupOrderInfoService.getCustomerImportData(file,user.getAccountName());	
			count = groupOrderInfoService.getImportData2Oracle(list);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			file.delete();
			json.put("count", count);
			renderJson(json);
		}
		return null;
	}
	

	//导出儿童基因订单excel
	public Page exportEtOrderByExl(Page page , Map paramsMap){
		List<ErpEtOrderInfoBean> list = new ArrayList<ErpEtOrderInfoBean>();
		List<Map<String, Object>> result = groupOrderInfoService.findGroupOrderInfo(page, paramsMap);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		for(Map<String, Object> map : result){
			ErpEtOrderInfoBean bean = new ErpEtOrderInfoBean();
			bean.setId(map.get("ID")!=null?map.get("ID").toString():"");
			bean.setOrderno(map.get("ORDERNO")!=null?map.get("ORDERNO").toString():"");
			bean.setOrderdate(map.get("ORDERDATE")!=null?dateFormat.format(map.get("ORDERDATE")):"");
			bean.setName(map.get("NAME")!=null?map.get("NAME").toString():"");
			bean.setPhone(map.get("PHONE")!=null?map.get("PHONE").toString():"");
			bean.setType(map.get("TYPE")!=null?(("2").equals(map.get("TYPE").toString())?"团购":"个人"):"");
			bean.setExam_num(map.get("EXAM_NUM")==null?"":map.get("EXAM_NUM").toString());
			bean.setPrice(map.get("PRICE")!=null?map.get("PRICE").toString():"");
			list.add(bean);
		}
		page.setResults(list);
		return page;
	}
	
	//导出客户基本信息
	public Page exportBasicCustomerInfo(Page page , Map paramsMap){
		paramsMap.put("filter_and_isDelete_LIKE_S", "1");
		page.setResults(groupOrderInfoService.findByPage(page, paramsMap));
		if(paramsMap.get("filter_and_status_LIKE_S")!=null){
			HttpTool.setAttribute("filter_and_status_LIKE_S", paramsMap.get("filter_and_status_LIKE_S"));
		}
		return page;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
}
