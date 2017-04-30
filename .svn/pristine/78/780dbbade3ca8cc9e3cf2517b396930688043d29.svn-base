/**
 * 
 */
package org.hpin.events.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.vo.CustomerReadQuery;
import org.hpin.events.service.ErpCustomerReadService;

import net.sf.json.JSONObject;

/**
 * @author machuan
 * @date 2016年11月2日
 */
@Namespace("/events")
@Action("erpCustomerRead")
@Results({
	@Result(name="listReportInterpretation",location="/WEB-INF/events/erpCustomerRead/listReportInterpretation.jsp"),
	@Result(name="findDetail",location="/WEB-INF/events/erpCustomerRead/findDetail.jsp"),
})
public class ErpCustomerReadAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerRelationShipService relService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	
	private ErpCustomerReadService customerReadService=(ErpCustomerReadService)SpringTool.getBean(ErpCustomerReadService.class);
	
	private Logger logger = Logger.getLogger(ErpCustomerReadAction.class);
	
	private CustomerReadQuery readQuery;
	
	private static final String BASEPATH = "<img src=\"";
	/**
	 * 创建场次账户对应客户信息列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String listReportInterpretation() {
		//渲染项目类别下拉框
		try {
			List<ProjectType> proTypes = relService.findProjectTypes();
			HttpTool.setAttribute("proTypes", proTypes);
			
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			customerReadService.findListReportInterpretation(page, readQuery);
		} catch (Exception e) {
			logger.error("listReportInterpretation : " +e);
		}
		logger.info("listReportInterpretation --- success。");
		return "listReportInterpretation";
	}
	
	/**
	 * 获取体检报告的pdf地址 
	 * @return 返回预览地址
	 * @author machuan
	 * @date  2016年11月3日
	 */
	public void findPhyReport() throws Exception{
		JSONObject json = new JSONObject();
		String code = HttpTool.getParameter("code");
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = //"http:/192.168.1.50:8066";//暂时写死   测试环境  
				//TODO 上线之后的地址
				url.delete(url.length()-request.getRequestURI().length(), url.length()).toString();
		String reportPath;
		reportPath = customerReadService.findPhyReport(code);
		if(reportPath!=null&&reportPath.length()>0){
			reportPath = reportPath.substring(2, reportPath.length());
			json.put("phyReport", contextUrl+reportPath);
		}
		renderJson(json);
	}
	
	/**
	 * 
	 * 获取原始报告单的图片地址  并生成预览样式
	 * @return 新打开的页面
	 * @throws Exception
	 * @author machuan
	 * @date  2016年11月4日
	 */
	public String findDetail() throws Exception{
		String code = HttpTool.getParameter("code");
		List<String> details= customerReadService.findDetail(code);
		StringBuilder sb = new StringBuilder();
		for(String str : details){
			sb.append(BASEPATH);
			sb.append(str);
			sb.append("\" /> ");
		}
		HttpTool.setAttribute("details", sb.toString());
		return "findDetail";
	}
	
	/**
	 * @return the readQuery
	 */
	public CustomerReadQuery getReadQuery() {
		return readQuery;
	}
	/**
	 * @param readQuery the readQuery to set
	 */
	public void setReadQuery(CustomerReadQuery readQuery) {
		this.readQuery = readQuery;
	}
	
}
