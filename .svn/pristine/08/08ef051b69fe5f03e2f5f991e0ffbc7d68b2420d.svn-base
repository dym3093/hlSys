package org.hpin.events.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpQRCode;
import org.hpin.events.entity.vo.ErpQRCodeVo;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpQRCodeService;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;

import com.itextpdf.text.log.SysoCounter;

import net.sf.json.JSONObject;
/**
 * @author machuan
 *
 */
@Namespace("/events")
@Action("erpSaleQRCode")
@Results({
	@Result(name="listSaleQRCode",location="/WEB-INF/events/erpEvents/listSaleQRCode.jsp"),
})
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ErpSaleQRCodeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ErpSaleQRCodeAction.class);
	ErpQRCodeService erpQRCodeService = (ErpQRCodeService) SpringTool.getBean(ErpQRCodeService.class);
	ErpEventsService erpEventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	ErpCustomerService erpCustomerService = (ErpCustomerService) SpringTool.getBean(ErpCustomerService.class);
	YmGeneReportServiceImplServiceLocator geneReportServiceImplServiceLocator = new YmGeneReportServiceImplServiceLocator();
	
	
	
	
	
	/**
	 * @return
	 * @author machuan
	 * @date  2016年10月14日
	 */
	
	public String listSaleQRCode(){
		Map searchMap = super.buildSearch();
		String aaaa = HttpTool.getParameter("aaaa", "");
		String bbbb = HttpTool.getParameter("bbbb", "");
		String eventsDateStr = (String) searchMap.get("filter_and_eventsDate_EQ_T");
		if(StringUtils.isNotBlank(eventsDateStr)){
			searchMap.remove("filter_and_eventsDate_EQ_T");
			searchMap.put("filter_and_eventsDate_GEST_T", eventsDateStr);
			searchMap.put("filter_and_eventsDate_LTET_T", eventsDateStr);
		}else{
			//进入方法就获取当前日期 当作查询条件
			Date dt=new Date();
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			searchMap.put("filter_and_eventsDate_GEST_T", df.format(dt));
			searchMap.put("filter_and_eventsDate_LTET_T", df.format(dt));
		}
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpQRCode> QRCodeList=erpQRCodeService.findByPageQRCOde(page, searchMap);
			List<ErpQRCodeVo> QRCodeVoList = new ArrayList<ErpQRCodeVo>();
			for(ErpQRCode erpQRCode : QRCodeList){
				ErpQRCodeVo erpQRCodeVo = new ErpQRCodeVo();
				erpQRCodeVo.setId(erpQRCode.getId());
				erpQRCodeVo.setEventsNo(erpQRCode.getEventsNo());
				erpQRCodeVo.setEventsDate(erpQRCode.getEventsDate());
				erpQRCodeVo.setProvinceName(erpQRCode.getProvinceName());
				erpQRCodeVo.setCityName(erpQRCode.getCityName());
				erpQRCodeVo.setBanchCompanyId(erpQRCode.getBanchCompanyId());
				erpQRCodeVo.setOwnedCompanyId(erpQRCode.getOwnedCompanyId());
				erpQRCodeVo.setCombo(erpQRCode.getCombo());
				erpQRCodeVo.setLevel(erpQRCode.getLevel());
				erpQRCodeVo.setExpectNum(erpQRCode.getExpectNum());
				erpQRCodeVo.setBatchNo(erpQRCode.getBatchNo());
				erpQRCodeVo.setQRCodeStatus(erpQRCode.getQRCodeStatus());
				erpQRCodeVo.setKeyword(erpQRCode.getKeyword());
				erpQRCodeVo.setExpiryDate(erpQRCode.getExpiryDate());
				erpQRCodeVo.setQRCodeLocalPath(erpQRCode.getQRCodeLocalPath());
				erpQRCodeVo.setAlreadyInputNum(String.valueOf(erpCustomerService.getErpCustomerNumByEventsNo(erpQRCode.getEventsNo())));
				QRCodeVoList.add(erpQRCodeVo);
			}
			page.setResults(QRCodeVoList) ;
		} catch (Exception e) {
			log.error("listSaleQRCode:", e);
		}
//			getErpCustomerNumByEventsNo
		HttpTool.setAttribute("aaaa", aaaa);
		HttpTool.setAttribute("bbbb", bbbb);
		log.info("listSaleQRCode: success");
		return "listSaleQRCode";
	}
	
	/**
	 * 导出场次信息的excel表
	 * @author machuan
	 * @throws Exception 
	 * @date  2016年10月14日
	 */
	public void createExSeFilePath(){
		JSONObject json = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String QRCodeId = HttpTool.getParameter("id");
		ErpQRCode erpQRCode;
		String excelPath = "";
		try {
			erpQRCode = erpQRCodeService.getErpQRCodeById(QRCodeId);
			if("2".equals(erpQRCode.getQRCodeStatus())){
				json.put("error", "请先将二维码失效才可导出数据！");
				renderJson(json);
				return;
			}
			List<ErpCustomer> erpCustomers = erpCustomerService.getErpCustomerByEventsNo(erpQRCode.getEventsNo());
			ErpEvents erpEvents = erpEventsService.getCompanyPro(erpQRCode.getEventsNo());
			excelPath= erpQRCodeService.createQRCodeExcelPath(request, erpQRCode,erpCustomers,erpEvents);
		} catch (Exception e) {
			log.error("createExSeFilePath:", e);
		}
		log.info("Excle path--"+excelPath);
		json.put("path",excelPath);
		
		renderJson(json);
	}
  
    
}
