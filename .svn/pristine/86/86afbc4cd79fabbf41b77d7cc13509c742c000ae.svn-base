package org.hpin.events.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpComboMap;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpQRCode;
import org.hpin.events.service.ErpComboMapService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.events.service.ErpQRCodeService;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;
@Namespace("/events")
@Action("erpQRCode")
@Results({
	@Result(name="listQRCode",location="/WEB-INF/events/erpEvents/listQRCode.jsp"),
	@Result(name="listWuChangEventQRCode",location="/WEB-INF/events/erpEvents/listWuChangEventQRCode.jsp"),
	@Result(name="createQRCode",location="/WEB-INF/events/erpEvents/createQRCode.jsp"),
})
public class ErpQRCodeAction extends BaseAction {
	private Logger log = Logger.getLogger(ErpQRCodeAction.class);
	ErpQRCodeService erpQRCodeService = (ErpQRCodeService) SpringTool.getBean(ErpQRCodeService.class);
	ErpEventsService erpEventsService = (ErpEventsService) SpringTool.getBean(ErpEventsService.class);
	YmGeneReportServiceImplServiceLocator geneReportServiceImplServiceLocator = new YmGeneReportServiceImplServiceLocator();
	@Autowired
	ComboService comboService; // add by YoumingDeng 2016-11-02

	ErpComboMapService erpComboMapService = (ErpComboMapService) SpringTool.getBean(ErpComboMapService.class);// add by YoumingDeng 2016-11-09
	
	/**
	 * create by henry.xu 20161201
	 * 根据业务需求,此处添加项目类型为无创的单独二维码管理,其他跟原有场次管理相同
	 * 根据条件,所以要对查询重新调整;
	 */
	@SuppressWarnings("rawtypes")
	public String listWuChangEventQRCode() {
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			this.erpQRCodeService.findWuChangEventQRCode(page, params);
			
		} catch (ParseException e) {
			log.error("无创查询场次二维码管理页面错误!", e);
		}
		return "listWuChangEventQRCode";
	}
	
	/**
	 * list列表
	 * @return
	 * @author tangxing
	 * @date 2016-8-18下午2:26:29
	 */
	public String listQRCode(){
		Map searchMap = super.buildSearch();
		String aaaa = HttpTool.getParameter("aaaa", "");
		String bbbb = HttpTool.getParameter("bbbb", "");
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		String QRCodeStatusInput = HttpTool.getParameter("QRCodeStatusInput");
		
		String userName=currentUser.getUserName();
		log.info("listQRCode--"+userName);
		if(!userName.equals("管理员")&&!userName.equals("韩晓菊")&&!userName.equals("张红")){
			searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
		}
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ErpQRCode> QRCodeList=erpQRCodeService.findByPageQRCOde(page, searchMap);
		page.setResults(QRCodeList) ;
		HttpTool.setAttribute("aaaa", aaaa);
		HttpTool.setAttribute("bbbb", bbbb);	
		HttpTool.setAttribute("QRCodeStatusInput", QRCodeStatusInput);
		return "listQRCode";
	}
	
	/**
	 * 根据客户条形码查询场次信息
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-8-18下午2:26:53
	 */
    public String findQRCodedeByCode() throws Exception{
        Map searchMap = super.buildSearch();
        String allStr = searchMap.toString(); //获取当前提交的sql条件
        String[] allStrs = allStr.split("_"); //分割sql条件
        int allSize = allStrs.length;
        for(int i=0;i<allSize;++i){
            if(allSize>=4){
                if(allStrs[2].endsWith("code")){//以条形码查询
                    String[] codes=allStrs[4].split("=");
                    String[] codes2=codes[1].split("}");
                    log.info("findQRCodedeByCode--"+codes2[0]);
                  List<ErpCustomer> customers = erpQRCodeService.getCustomerByCode(codes2[0]);
                  if(customers.size()>0){
                      searchMap.clear();
                      searchMap.put("filter_and_eventsNo_LIKE_S", customers.get(0).getEventsNo());//查找到条形码对应的客户，就查场次
                  }else{
                      searchMap.clear();
                      searchMap.put("filter_and_eventsNo_LIKE_S", "fill"); //未查找到数据，就无结果
                  }
                }
            }
        }
        User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
        String userName=currentUser.getUserName();
        if(!userName.equals("管理员")){
            searchMap.put("filter_and_createUserName_EQ_S", currentUser.getUserName());
        }
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<ErpQRCode> QRCodeList=erpQRCodeService.findByPageQRCOde(page, searchMap);
        page.setResults(QRCodeList) ;
        return "listQRCode";
    }
    
    
    /**
     * 生成二维码默认页面
     * @return
     * @author tangxing
     * @date 2016-8-18下午5:51:17
     */
    public String toCreateQRCode(){
    	String QRCodeId = HttpTool.getParameter("QRCodeId");
    	
    	HttpTool.setAttribute("QRCodeId", QRCodeId);
    	return "createQRCode";
    }
    
    /**
     * 生成二维码
     * @return
     * @author tangxing
     * @date 2016-8-18下午4:33:34
     */
    public void createQRCode(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	JSONObject json = new JSONObject();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String QRCodeId = HttpTool.getParameter("QRCodeId");//二维码ID
    	String keyword = HttpTool.getParameter("keyword");	//关键字
    	String expiry = HttpTool.getParameter("expiry");	//有效期
    	String expiryDate = "";		//有效时间到
    	String xmlInfo = "";		//组装xml
    	String repData = "";			//调用接口返回值
    	String combo = "";
    	int hour = 0;
    	StringBuffer comboBuffer = new StringBuffer("<companyComboList>");	//组装套餐xml
    	List<ErpQRCode> list = erpQRCodeService.getErpQRCodeByKeyword(keyword);		//判断关键字是否重复
    	if(list==null||list.size()==0){
	    	try {
	    		ErpQRCode erpQRCode = erpQRCodeService.getErpQRCodeById(QRCodeId);
				ErpEvents erpEvents = erpEventsService.getEventsById(erpQRCode.getEventsId());
	    		if(expiry.matches("^[0-9]+$")){
	        		hour = Integer.parseInt(expiry);
	        		expiryDate = erpQRCodeService.expectTime(erpEvents.getEventDate(),hour);	//转换成时间格式
	        	};
				
				//String[] comboStr = erpQRCodeService.getComboByBranchCompanyId(erpQRCode.getBanchCompanyId());	//套餐数组
				List<String> comboIdList = erpQRCodeService.getComboIdByProjectId(erpEvents.getCustomerRelationShipProId());	//套餐数组
				String comboDisName = null;

				log.info("createQRCode comboStr -- "+comboIdList.size());
				Map<String,String> srchMap = new HashMap<String, String>();

				// add By Damian 2016-12-26 start
				List<ErpRelationShipCombo> relationShipComboList;
				ErpRelationShipCombo relationShipCombo;
				// add By Damian 2016-12-26 end

				for (String comboId : comboIdList) {
					String comboName = erpQRCodeService.getComboNameById(comboId);
					// add by Dayton 2016-11-09
					srchMap.clear();
					srchMap.put(ErpComboMap.F_BRANCHCOMPANYID, erpQRCode.getBanchCompanyId());
					srchMap.put(ErpComboMap.F_COMBOYM, comboName);

					Combo myCombo = comboService.findByComboName(comboName);//根据套餐名获取套餐信息　modify by YoumingDeng 2016-11-02
					// modify By Damian 2016-12-26 start
                    // 修正显示套餐获取的方式
					relationShipComboList = erpQRCodeService.listRelationShipCombo(erpEvents.getCustomerRelationShipProId(),comboId);
					if(!CollectionUtils.isEmpty(relationShipComboList)){
						relationShipCombo = relationShipComboList.get(0);
						comboDisName = relationShipCombo.getComboShowName();
					}
					// modify By Damian 2016-12-26 end
					if(myCombo!=null){
						combo = "<comboItem>"+
								"<comboId>"+comboId+"</comboId>"
								+"<comboName>"+comboName+"</comboName>"
								+"<comboDisName>"+comboDisName+"</comboDisName>"
								+"<comboType>"+myCombo.getProjectTypes()+"</comboType>"
							+"</comboItem>";
					comboBuffer.append(combo);
					}
				}
				comboBuffer.append("</companyComboList>");
				
				
				xmlInfo = "<?xml version=\"1.0\" encoding=\"utf-8\"?><eventQRCodeInfo>"+
				"<eventsNo>"+erpEvents.getEventsNo()+"</eventsNo>"
				+"<eventTime>"+erpEvents.getEventDate()+"</eventTime>"
				+"<ownedCompany>"+erpEvents.getOwnedCompany()+"</ownedCompany>"
				+"<branchCompany>"+erpEvents.getBranchCompany()+"</branchCompany>"
				+"<issueNo>"+erpEvents.getBatchNo()+"</issueNo>"
				+"<keyword>"+keyword+"</keyword>"
				+"<validHour>"+hour+"</validHour>"
				+"<batchNo>"+erpEvents.getBatchNo()+"</batchNo>"+
				comboBuffer.toString()+"</eventQRCodeInfo>";
				
				YmGeneReportServiceImpl impl = geneReportServiceImplServiceLocator.getYmGeneReportServiceImplPort();
				repData = impl.pushEventQRCodeInfo(xmlInfo);
				
				log.info("接口返回值--"+repData);
				if(!repData.equals("0")){
					if(erpQRCode!=null){
						/*String viewPth = erpQRCodeService.getViewPath(request,repData);*/
						String viewPth = repData.replace("/usr/ymdata/qrCode","http://img.healthlink.cn:8099/qrCode");
						log.info("viewP--"+viewPth);
						erpQRCode.setExpiryDate(sdf.parse(expiryDate));
						erpQRCode.setKeyword(keyword);
						erpQRCode.setQRCodeStatus("2");
	        			erpQRCode.setQRCodePath(repData);
	        			erpQRCode.setQRCodeLocalPath(viewPth);
	        			log.info("erpQRCode toString--"+erpQRCode.toString());
	        			erpQRCodeService.updateOrdelQRCodeStatus(erpQRCode);
	        			json.put("status", "200");
	        			json.put("message", "生成成功！");
	            	}
				}else{
					json.put("status", "300");
	    			json.put("message", "生成失败！");
				}
				
			} catch (Exception e) {
				log.error("createQRCode--"+e);
				json.put("status", "300");
				json.put("message", "生成失败！");
			}
    	}else{
    		log.info("fail keyword--"+keyword);
    		json.put("status", "300");
			json.put("message", "关键字重复！");
    	}
    	
    	//调用生成二维码接口
    	
    	
    	renderJson(json);
    }
    
    /**
     * 
     * 逻辑删除二维码
     * @author tangxing
     * @date 2016-8-18下午4:37:28
     */
    public void deleteQRCode(){
    	JSONObject json = new JSONObject();
    	String ids = HttpTool.getParameter("ids");
    	
    	log.info("deleteQRCode ids -- "+ids);
    	try {
	    	if(ids.indexOf(",")==-1){	//单条
    			ErpQRCode erpQRCode = erpQRCodeService.getErpQRCodeById(ids);
	    		if(erpQRCode!=null){
	    			erpQRCode.setIsDelete("1");
	    			erpQRCodeService.updateOrdelQRCodeStatus(erpQRCode);
	    			json.accumulate("status", "200");
	    			json.accumulate("message", "删除成功！");
	        	}
	    	}else{
	    		String[] idArr = ids.replaceAll(" ", "").split(",");	//多条;
	    		for (int i = 0; i < idArr.length; i++) {
	    			log.info("deleteQRCode id = "+idArr[i]);
	    			ErpQRCode erpQRCode = erpQRCodeService.getErpQRCodeById(idArr[i]);
	        		if(erpQRCode!=null){
	        			erpQRCode.setIsDelete("1");
	        			erpQRCodeService.updateOrdelQRCodeStatus(erpQRCode);
	        			json.accumulate("status", "200");
	        			json.accumulate("message", "删除成功！");
	            	}
				}
	    	}
    	} catch (Exception e) {
			log.error("deleteQRCode:"+e);
			json.accumulate("status", "300");
			json.accumulate("message", "删除失败！");
		}
    	renderJson(json);
    }
    
    /**
     * 手动处理二维码失效
     * 
     * @author tangxing
     * @date 2016-8-18下午4:53:48
     */
    public void invalidQRCode(){
    	JSONObject json = new JSONObject();
    	String ids = HttpTool.getParameter("ids");
    	String xmlInfo = "";
    	String status = "";//调用接口返回值
    	
    	log.info("invalidQRCode ids -- "+ids);
    	
    	if(ids.indexOf(",")==-1){	//单条
    		try {
    			ErpQRCode erpQRCode = erpQRCodeService.getErpQRCodeById(ids);
    			ErpEvents erpEvents = erpEventsService.getEventsById(erpQRCode.getEventsId());
    			
    			xmlInfo = "<?xml version=\"1.0\" encoding=\"utf-8\"?><eventQRCode><eventsNo>"+
    					erpEvents.getEventsNo()+"</eventsNo><note>Invalid</note></eventQRCode>";
    			
    			YmGeneReportServiceImpl impl = geneReportServiceImplServiceLocator.getYmGeneReportServiceImplPort();
    			status = impl.setEventQRCodeInvalid(xmlInfo);
    			
	    		if(erpQRCode!=null){
	    			erpQRCode.setQRCodeStatus("3");
	    			erpQRCodeService.updateOrdelQRCodeStatus(erpQRCode);
	    			json.put("status", "200");
	    			json.put("message", "设置失效成功！");
	        	}
    		} catch (Exception e) {
				log.error("deleteQRCode:"+e);
				json.put("status", "300");
    			json.put("message", "设置失效失败！");
			}
    	}else{
    		String[] idArr = ids.replaceAll(" ", "").split(",");	//多条;
    		try {
	    		for (int i = 0; i < idArr.length; i++) {
	    			ErpQRCode erpQRCode = erpQRCodeService.getErpQRCodeById(idArr[i]);
	    			ErpEvents erpEvents = erpEventsService.getEventsById(erpQRCode.getEventsId());
	    			xmlInfo = "<?xml version='1.0' encoding='utf-8'?><eventQRCode><eventsNo>"+
	    					erpEvents.getEventsNo()+"</eventsNo><note>Invalid</note></eventQRCode>";
	    			
	    			YmGeneReportServiceImpl impl = geneReportServiceImplServiceLocator.getYmGeneReportServiceImplPort();
	    			status = impl.setEventQRCodeInvalid(xmlInfo);
	    			
	    			if(status.equals("1")){
	    				if(erpQRCode!=null){
	            			erpQRCode.setQRCodeStatus("3");
	            			erpQRCodeService.updateOrdelQRCodeStatus(erpQRCode);
	            			json.put("status", "200");
	            			json.put("message", "设置失效成功！");
		            	}
	    			}else if(status.equals("0")){
	    				json.put("status", "300");
            			json.put("message", "设置失效失败！");
	    			}
	        		
				}
    		} catch (Exception e) {
				log.error("deleteQRCode:"+e);
				json.put("status", "300");
    			json.put("message", "设置失效失败！");
			}
    	}
    	renderJson(json);
    }
    
}
