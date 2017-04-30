package org.hpin.venueStaffSettlement.web;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.ProjectType;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.service.ErpConferenceService;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetail;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetailExc;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostException;
import org.hpin.venueStaffSettlement.entity.ErpConferenceStaffCost;
import org.hpin.venueStaffSettlement.entity.vo.ConferenceQuery;
import org.hpin.venueStaffSettlement.service.ConferenceCostManageService;
import org.hpin.venueStaffSettlement.service.ErpConferenceCostDetailExcService;
import org.hpin.venueStaffSettlement.service.ErpConferenceCostDetailService;
import org.hpin.venueStaffSettlement.service.ErpConferenceCostExceptionService;
import org.hpin.venueStaffSettlement.service.ErpConferenceStaffCostService;
import org.springframework.util.CollectionUtils;

/**
 * 会议费用结算Action
 * @author tangxing
 * @date 2016-5-17下午5:06:04
 */

@Namespace("/venueStaffSett")
@Action("conferCostMan")
@Results({
    @Result(name="listConferCostMan",location="/WEB-INF/venueStaffSettlement/conferenceCostManage.jsp"),
    @Result(name="toAddConferenceStaffCost",location="/WEB-INF/venueStaffSettlement/addConferenceCostMan.jsp"),
    @Result(name="listPersonDetail",location="/WEB-INF/venueStaffSettlement/conferencePersonDetail.jsp"),
    @Result(name="addConferenceCostInfo",location="/WEB-INF/venueStaffSettlement/addConferenceCostInfo.jsp"),
    @Result(name="getConferenceCostInfo",location="/WEB-INF/venueStaffSettlement/getConferenceCostInfo.jsp"),
    @Result(name="showCostDetail",location="/WEB-INF/venueStaffSettlement/showCostDetail.jsp"),
    @Result(name="getCostDetail",location="/WEB-INF/venueStaffSettlement/getCostDetail.jsp"),
    @Result(name="showUploadFile",location="/WEB-INF/venueStaffSettlement/showUploadFile.jsp"),
    @Result(name="listConferenceCostException",location="/WEB-INF/venueStaffSettlement/listConferenceCostException.jsp"),
    @Result(name="listConferenceCostDetailExc",location="/WEB-INF/venueStaffSettlement/listConferenceCostDetailExc.jsp"),
})
public class ConferenceCostManageAction extends BaseAction {
	private Logger logger = Logger.getLogger(ConferenceCostManageAction.class);
	
	ConferenceCostManageService conferenceCostManService = (ConferenceCostManageService)SpringContextHolder.getBean("conferenceExcelFile");
	ErpConferenceService conferenceService = (ErpConferenceService)SpringTool.getBean(ErpConferenceService.class);
	ErpConferenceStaffCostService costService = (ErpConferenceStaffCostService)SpringTool.getBean(ErpConferenceStaffCostService.class);
	ErpConferenceCostDetailService costDetailService = (ErpConferenceCostDetailService)SpringTool.getBean(ErpConferenceCostDetailService.class);
	ErpConferenceCostExceptionService costExceptionService = (ErpConferenceCostExceptionService)SpringTool.getBean(ErpConferenceCostExceptionService.class);
	CustomerRelationShipService customerRelationShipService=(CustomerRelationShipService)SpringTool.getBean(CustomerRelationShipService.class);
	ErpConferenceCostDetailExcService excService = (ErpConferenceCostDetailExcService)SpringTool.getBean(ErpConferenceCostDetailExcService.class);
	
	private List<ErpConferenceStaffCost> entityList;	
	private ConferenceQuery confQuery; //查询条件; create by henry.xu 20160928
	private File file;
	private String fileFileName;
	private String fileContentType;

	/**
	 * 显示会议信息列表
	 * @return
	 * @author tangxing
	 * @date 2016-5-17下午5:07:35
	 */
	@SuppressWarnings("rawtypes")
	public String listConferCostMan(){
		
		List<ProjectType> proTypes = customerRelationShipService.findProjectTypes();
		HttpTool.setAttribute("proTypes", proTypes);
		
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        if(confQuery!=null){//是否存在费用
        	HttpTool.setAttribute("isExistCost", confQuery.getIsExistCost());
        }
		conferenceCostManService.findConferenceCostsByCondition(page, confQuery);
		
		return "listConferCostMan";
	}
	
	/**
	 * @author Carly 
	 * @since 2016年8月25日17:50:24
	 * @return 会议费用结算的人员费用录入
	 */
	public String addConferenceCostInfo(){
		try {
			String id = HttpTool.getParameter("conferenceId");
			ErpConference conference = (ErpConference)conferenceService.findById(id);
			//根据项目编码ID查询对应的项目列表信息; modified by henry.xu 20160928
			if(StringUtils.isNotEmpty(conference.getCustomerRelationShipProId())) {
	        	HttpTool.setAttribute("shipPro", customerRelationShipService.findCustRelShipProById(conference.getCustomerRelationShipProId()));
	        }
			
			List<ErpConferenceStaffCost> costList = conferenceCostManService.getConferenceCost(id);
			HttpTool.setAttribute("conference", conference);
			System.out.println(conference.getConferenceType());
			HttpTool.setAttribute("costList", costList);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction addConferenceCostInfo",e);
		}
		return "addConferenceCostInfo";
	}
	
	/**
	 * @return 显示上传文件的弹窗
	 */
	public String showUploadFile(){
		HttpTool.setAttribute("uploadType", HttpTool.getParameter("uploadType"));
		return "showUploadFile";
	}
	
	/**
	 *  上传文件
	 */
	public void updloadFile(){
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		Date date = new Date();
		int count = 0;	//是否有该场次
		int count2 = 0;//是否有该用户
		int count3 = 0;//导入成功的数据
		try{
			List<ErpConferenceStaffCost> list = costService.getConferenceStaffCost(file,user.getAccountName());	
			for(ErpConferenceStaffCost cost:list){
				List<ErpConference> conferenceList = costService.isRepeatEvent(cost.getConferenceNo());
				if(conferenceList.size()==0){
					costExceptionService.save(getCostException(cost,"0",date,user));
					count ++;
				}else{
					List<ErpConferenceStaffCost> staffCostList = costService.getConferenceCost(cost.getConferenceNo(),cost.getStaff());
					if(staffCostList.size()==0){
						ErpConference conference = conferenceList.get(0);
						cost.setConferenceId(conference.getId());
						costService.save(cost);
						conference.setSettNumbers(conference.getSettNumbers()+1);
						conference.setProduceCost(conference.getProduceCost()+cost.getAmount().doubleValue());
						conference.setUpdateTime(date);
						conference.setUpdateUserName(user.getAccountName());
						conferenceService.update(conference);
						count3++;
					}else{
						costExceptionService.save(getCostException(cost,"1",date,user));
						count2++;
					}
				}
			}
			
		}catch(Exception e){
			logger.error("ConferenceCostManageAction updloadFile",e);
		}finally{
			file.delete();
			json.put("count",count);//是否有该场次
			json.put("count2", count2);//是否有该用户
			json.put("count3", count3);//导入成功的数据
			renderJson(json);
		}
	}
	
	/**
	 * @param cost
	 * @param descripe //异常描述 0：没有该场次信息，1：已有该用户
	 * @param date
	 * @return
	 */
	private ErpConferenceCostException getCostException(ErpConferenceStaffCost cost,String descripe,Date date,User user){
		
		ErpConferenceCostException costException = new ErpConferenceCostException();
		costException.setConferenceNo(cost.getConferenceNo());
		costException.setName(cost.getStaff());
		costException.setTravelCost(cost.getTravelCost());
		costException.setInstructorCost(cost.getInstructorCost());
		costException.setCityTrafficCost(cost.getCityTrafficCost());
		costException.setProvinceTrafficCost(cost.getProvinceTrafficCost());
		costException.setHotelCost(cost.getHotelCost());
		costException.setSampleCost(cost.getSampleCost());
		costException.setOtherCost(cost.getOtherCost());
		costException.setAmount(cost.getAmount());
		costException.setCreateTime(date);
		costException.setCreateUser(user.getAccountName());
		costException.setDescripe(descripe);//异常描述 0：没有该场次信息，1：已有该用户
		return costException;
	}
	
	/**
	 * @return 导入的异常信息
	 */
	public String listExportExceptionInfo(){
		try {
			Map<String,Object> map = buildSearch();
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpConferenceCostException> list = costExceptionService.findByPage(page, map);
			page.setResults(list);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction listExportExceptionInfo(导入会议费用异常信息)",e);
		}
		return "listConferenceCostException";
	}
	
	/**
	 * @return 点击会议号，结算人数，产生费用进行费用查看
	 */
	public String getConferenceCostInfo(){
		try {
			String id = HttpTool.getParameter("conferenceId");
			ErpConference conference = (ErpConference)conferenceService.findById(id);
			
			//根据项目编码ID查询对应的项目列表信息; modified by henry.xu 20160928
			if(StringUtils.isNotEmpty(conference.getCustomerRelationShipProId())) {
	        	HttpTool.setAttribute("shipPro", customerRelationShipService.findCustRelShipProById(conference.getCustomerRelationShipProId()));
	        }
			
			List<ErpConferenceStaffCost> costList = conferenceCostManService.getConferenceCost(id);
			HttpTool.setAttribute("conference", conference);
			HttpTool.setAttribute("costList", costList);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction addConferenceCostInfo",e);
		}
		return "getConferenceCostInfo";
	}
	
	/**
	 *  删除行数据
	 */
	public void removeRow(){
		JSONObject json = new JSONObject();
		String costId = HttpTool.getParameter("costId");
		String name = HttpTool.getParameter("name");
		String conferenceId = HttpTool.getParameter("conferenceId");
		String price = HttpTool.getParameter("price");
		try {
			conferenceService.removeRow(costId,name,conferenceId,price);
			json.put("count", 1);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction removeRow",e);
			json.put("count", 0);
		}
		renderJson(json);
	}
	
	/**
	 * 	保存人员费用录入页面的费用信息
	 */
	public void  saveConferenceCostInfo(){
		JSONArray jsonArray = JSONArray.fromObject(HttpTool.getParameter("dataJson"));
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String conferenceId = HttpTool.getParameter("conferenceId");
		JSONObject jsonObject = new JSONObject();
		BigDecimal zeroDecimal = new BigDecimal(0);
		try {
			
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				String staff = json.getString("staff");
				String travelCost = json.getString("travelCost");
				String instructorCost = json.getString("instructorCost");
				String costId = json.getString("costId");
				BigDecimal travelBigDecimal = new BigDecimal(travelCost);
				BigDecimal instructorBigDecimal = new BigDecimal(instructorCost);
				ErpConference conference = (ErpConference) conferenceService.findById(conferenceId);
				if (costId.equals("0")) {//新增
					ErpConferenceStaffCost cost = new ErpConferenceStaffCost();
					Date date = new Date();
					cost.setConferenceId(conferenceId);
					cost.setConferenceNo(conference.getConferenceNo());
					cost.setStaff(staff);
					cost.setTravelCost(travelBigDecimal);
					cost.setInstructorCost(instructorBigDecimal);
					cost.setProvinceTrafficCost(zeroDecimal);
					cost.setCityTrafficCost(zeroDecimal);
					cost.setHotelCost(zeroDecimal);
					cost.setSampleCost(zeroDecimal);
					cost.setOtherCost(zeroDecimal);
					cost.setCreateTime(date);
					cost.setCreateUser(user.getAccountName());
					cost.setAmount(instructorBigDecimal.add(travelBigDecimal));
					costService.save(cost);
					
					conference.setProduceCost(conference.getProduceCost()+cost.getAmount().doubleValue());
					conference.setCreateTime(date);
					conference.setCreateUserName(user.getAccountName());
					conference.setSettNumbers(conference.getSettNumbers()+1);
					conferenceService.update(conference);
				}else {//更新
					ErpConferenceStaffCost cost = (ErpConferenceStaffCost) costService.findById(costId);
					List<ErpConferenceCostDetail> costDetailList = costDetailService.getConferenceDetailInfo(conferenceId, cost.getStaff());
					Date date = new Date();
					BigDecimal amoutdDecimal = costService.getAmoutSum(costId);
					cost.setStaff(staff);
					cost.setAmount(amoutdDecimal.subtract(cost.getTravelCost()).subtract(cost.getInstructorCost()).add(travelBigDecimal).add(instructorBigDecimal));
					cost.setTravelCost(travelBigDecimal);
					cost.setInstructorCost(instructorBigDecimal);
					cost.setUpdateTime(date);
					cost.setUpdateUser(user.getAccountName());
					costService.update(cost);

					if(costDetailList.size()>=1){//更新该用户的详细费用对应的姓名
						for(ErpConferenceCostDetail costDetail:costDetailList){
							costDetail.setName(staff);
							costDetail.setUpdateTime(date);
							costDetail.setUpdateUser(user.getAccountName());
							costDetailService.update(costDetail);
						}
					}

					conference.setUpdateTime(date);
					conference.setProduceCost(conferenceService.getProduceCostSum(conferenceId).doubleValue());
					conference.setUpdateUserName(user.getAccountName());
					costService.update(cost);
				}
			}
			jsonObject.put("count", 1);
		} catch (Exception e) {
			jsonObject.put("count", 0);
			logger.error("ConferenceCostManageAction saveConferenceCostInfo",e);
		}
		renderJson(jsonObject);
	}
	
	/**
	 * @return 费用明细弹框
	 */
	public String showCostDetail(){
		try {
			String conferenceId = HttpTool.getParameter("conferenceId");
			String conferenceNo = HttpTool.getParameter("conferenceNo");
			String name = java.net.URLDecoder.decode(HttpTool.getParameter("name"),"UTF-8");
			String belong = HttpTool.getParameter("belong");
			String travelCost = HttpTool.getParameter("travelCost");
			String instructorCost = HttpTool.getParameter("instructorCost");
			String costId = HttpTool.getParameter("costId");
			List<ErpConferenceCostDetail> costDetaiList = costDetailService.getConferenceDetailInfo(conferenceId,name,belong);
			HttpTool.setAttribute("conferenceId", conferenceId);
			HttpTool.setAttribute("conferenceNo", conferenceNo);
			HttpTool.setAttribute("name", name);
			HttpTool.setAttribute("belong", belong);
			HttpTool.setAttribute("costDetail", costDetaiList);
			HttpTool.setAttribute("travelCost", travelCost);
			HttpTool.setAttribute("instructorCost", instructorCost);
			HttpTool.setAttribute("costId", costId);	
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction showCostDetail",e);
		}
		return "showCostDetail";
	}
	
	public String getCostDetail(){
		try {
			String conferenceId = HttpTool.getParameter("conferenceId");
			String conferenceNo = HttpTool.getParameter("conferenceNo");
			String name = java.net.URLDecoder.decode(HttpTool.getParameter("name"),"UTF-8");
			String belong = HttpTool.getParameter("belong");
			String travelCost = HttpTool.getParameter("travelCost");
			String instructorCost = HttpTool.getParameter("instructorCost");
			String costId = HttpTool.getParameter("costId");
			List<ErpConferenceCostDetail> costDetaiList = costDetailService.getConferenceDetailInfo(conferenceId,name,belong);
			HttpTool.setAttribute("conferenceId", conferenceId);
			HttpTool.setAttribute("conferenceNo", conferenceNo);
			HttpTool.setAttribute("name", name);
			HttpTool.setAttribute("belong", belong);
			HttpTool.setAttribute("costDetail", costDetaiList);
			HttpTool.setAttribute("travelCost", travelCost);
			HttpTool.setAttribute("instructorCost", instructorCost);
			HttpTool.setAttribute("costId", costId);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction getCostDetail",e);
		}
		return "getCostDetail";
	}
	
	public void removeDetailInfo(){//移除详细费用信息
		String costDetailId = HttpTool.getParameter("costDetailId");
		String conferenceId = HttpTool.getParameter("conferenceId");
		String price = HttpTool.getParameter("price");
		String belong = HttpTool.getParameter("belong");
		String costId = HttpTool.getParameter("costId");
		
		BigDecimal priceBigDecimal = new BigDecimal(price);
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		try {
			
			costDetailService.deleteIds(costDetailId);
			ErpConferenceStaffCost staffCost = (ErpConferenceStaffCost)costService.findById(costId);
			if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
				staffCost.setCityTrafficCost(staffCost.getCityTrafficCost().subtract(priceBigDecimal));
			}else if(belong.equals("1")){//往返交通费
				staffCost.setProvinceTrafficCost(staffCost.getProvinceTrafficCost().subtract(priceBigDecimal));
			}else if(belong.equals("2")){//住宿费
				staffCost.setHotelCost(staffCost.getHotelCost().subtract(priceBigDecimal));
			}else if(belong.equals("3")){//采样费
				staffCost.setSampleCost(staffCost.getSampleCost().subtract(priceBigDecimal));
			}else if(belong.equals("4")){//其他费用
				staffCost.setOtherCost(staffCost.getOtherCost().subtract(priceBigDecimal));
			}
			staffCost.setAmount(staffCost.getAmount().subtract(priceBigDecimal));
			staffCost.setUpdateTime(new Date());
			staffCost.setUpdateUser(user.getAccountName());
			costService.update(staffCost);
			
			ErpConference conference = (ErpConference) conferenceService.findById(conferenceId);
			conference.setProduceCost(conference.getProduceCost()-Double.valueOf(price));
			conference.setUpdateTime(new Date());
			conference.setUpdateUserName(user.getAccountName());
			conferenceService.update(conference);
			json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			logger.error("ConferenceCostManageAction removeDetailInfo",e);
		}
		renderJson(json);
	}
	
	/**
	 *  添加费用明细
	 */
	public void saveConferenceCostDetail(){
		String name = HttpTool.getParameter("name");
		String conferenceNo = HttpTool.getParameter("conferenceNo");
		String conferenceId = HttpTool.getParameter("conferenceId");
		String belong = HttpTool.getParameter("belong");	//	费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
		String travelCost = HttpTool.getParameter("travelCost");	
		String instructorCost = HttpTool.getParameter("instructorCost");	
		String costId = HttpTool.getParameter("costId");	
		String dataJson = HttpTool.getParameter("dataJson");	//表格数据
		
		BigDecimal travelDecimal = new BigDecimal(travelCost);//差旅费
		BigDecimal instructorDecimal = new BigDecimal(instructorCost);//讲师费
		
		JSONArray jsonArray = JSONArray.fromObject(dataJson);
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		JSONObject json = new JSONObject();
		try {
			Date date = new Date();
			BigDecimal costDetailDecimal = new BigDecimal(0);
			for(int i=0;i<jsonArray.size();i++){//保存详细信息
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String days = jsonObject.getString("days");
				String flight = jsonObject.getString("flight");
				String descripe = jsonObject.getString("descripe");
				Double cost = jsonObject.getDouble("cost");			//输入的明细的费用
				String costDetailId = jsonObject.getString("costDetailId");
				BigDecimal costDecimal = new BigDecimal(cost);
				costDetailDecimal = costDetailDecimal.add(costDecimal);
				if (costDetailId.equals("0")) {	//新增费用明细
					ErpConferenceCostDetail costDetail = new ErpConferenceCostDetail();
					costDetail.setName(name);
					costDetail.setConferenceId(conferenceId);
					costDetail.setConferenceNo(conferenceNo);
					costDetail.setDays(days);
					costDetail.setFlight(flight);
					costDetail.setDescripe(descripe);
					costDetail.setCost(costDecimal);
					costDetail.setBelong(belong);
					costDetail.setCreateUser(user.getAccountName());
					costDetail.setCreateTime(new Date());
					costDetailService.save(costDetail);
					
				}else {	//更新
					ErpConferenceCostDetail costDetail = (ErpConferenceCostDetail) costDetailService.findById(jsonObject.getString("costDetailId"));
					costDetail.setDays(days);
					costDetail.setFlight(flight);
					costDetail.setDescripe(descripe);
					costDetail.setCost(costDecimal);
					costDetail.setUpdateTime(new Date());
					costDetail.setCreateUser(user.getAccountName());
					costDetailService.update(costDetail);	//更新费用明细
				}
			}
			
			ErpConference conference = (ErpConference) conferenceService.findById(conferenceId);
			
			if(costId.equals("0")){//如果人员是新增的
				ErpConferenceStaffCost conferenceStaffCost= new ErpConferenceStaffCost();
				BigDecimal zeroDecimal = new BigDecimal(0);
				conferenceStaffCost.setStaff(name);
				conferenceStaffCost.setTravelCost(travelDecimal);
				conferenceStaffCost.setInstructorCost(instructorDecimal);
				conferenceStaffCost.setConferenceId(conferenceId);
				conferenceStaffCost.setConferenceNo(conferenceNo);
				if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
					conferenceStaffCost.setCityTrafficCost(costDetailDecimal);
					conferenceStaffCost.setProvinceTrafficCost(zeroDecimal);//防止在更新其他费用取空的问题
					conferenceStaffCost.setHotelCost(zeroDecimal);
					conferenceStaffCost.setSampleCost(zeroDecimal);
					conferenceStaffCost.setOtherCost(zeroDecimal);
				}else if(belong.equals("1")){//往返交通费
					conferenceStaffCost.setProvinceTrafficCost(costDetailDecimal);
					conferenceStaffCost.setCityTrafficCost(zeroDecimal);
					conferenceStaffCost.setHotelCost(zeroDecimal);
					conferenceStaffCost.setSampleCost(zeroDecimal);
					conferenceStaffCost.setOtherCost(zeroDecimal);
				}else if(belong.equals("2")){//住宿费
					conferenceStaffCost.setHotelCost(costDetailDecimal);
					conferenceStaffCost.setCityTrafficCost(zeroDecimal);
					conferenceStaffCost.setProvinceTrafficCost(zeroDecimal);
					conferenceStaffCost.setSampleCost(zeroDecimal);
					conferenceStaffCost.setOtherCost(zeroDecimal);
				}else if(belong.equals("3")){//采样费
					conferenceStaffCost.setSampleCost(costDetailDecimal);
					conferenceStaffCost.setCityTrafficCost(zeroDecimal);
					conferenceStaffCost.setProvinceTrafficCost(zeroDecimal);
					conferenceStaffCost.setHotelCost(zeroDecimal);
					conferenceStaffCost.setOtherCost(zeroDecimal);
				}else if(belong.equals("4")){//其他费用
					conferenceStaffCost.setOtherCost(costDetailDecimal);
					conferenceStaffCost.setCityTrafficCost(zeroDecimal);
					conferenceStaffCost.setProvinceTrafficCost(zeroDecimal);
					conferenceStaffCost.setHotelCost(zeroDecimal);
					conferenceStaffCost.setSampleCost(zeroDecimal);
				}
				conferenceStaffCost.setCreateUser(user.getAccountName());
				conferenceStaffCost.setCreateTime(date);
				conference.setSettNumbers(conference.getSettNumbers()+1);
				costService.save(conferenceStaffCost);
				BigDecimal amoutDecimal = costService.getAmoutSum(conferenceStaffCost.getId());
//				ErpConferenceStaffCost staffCost = (ErpConferenceStaffCost)costService.getConferenceCostInfo(conferenceId, name).get(0);
				ErpConferenceStaffCost staffCost = (ErpConferenceStaffCost)costService.findById(conferenceStaffCost.getId());
				staffCost.setAmount(amoutDecimal);
				costService.update(staffCost);
			}else{
//				ErpConferenceStaffCost staffCost = (ErpConferenceStaffCost)costService.getConferenceCostInfo(conferenceId, name).get(0);
				ErpConferenceStaffCost staffCost = (ErpConferenceStaffCost)costService.findById(costId);
				staffCost.setStaff(name);
				staffCost.setTravelCost(travelDecimal);	//差旅费
				staffCost.setInstructorCost(instructorDecimal);//讲师费
				if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
					staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getCityTrafficCost()).add(costDetailDecimal));
					staffCost.setCityTrafficCost(costDetailDecimal);
				}else if(belong.equals("1")){//往返交通费
					staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getProvinceTrafficCost()).add(costDetailDecimal));
					staffCost.setProvinceTrafficCost(costDetailDecimal);
				}else if(belong.equals("2")){//住宿费
					staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getHotelCost()).add(costDetailDecimal));
					staffCost.setHotelCost(costDetailDecimal);
				}else if(belong.equals("3")){//采样费
					staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getSampleCost()).add(costDetailDecimal));
					staffCost.setSampleCost(costDetailDecimal);
				}else if(belong.equals("4")){//其他费用
					staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getOtherCost()).add(costDetailDecimal));
					staffCost.setOtherCost(costDetailDecimal);
				}
				costService.update(staffCost);
			}
			
			Double produceCost = conferenceService.getProduceCostSum(conferenceId).doubleValue();
			conference.setProduceCost(produceCost);
			conference.setUpdateTime(new Date());
			conference.setUpdateUserName(user.getAccountName());
			conferenceService.update(conference);
			json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			logger.error("ConferenceCostManageAction saveConferenceCostDetail",e);
		}
		
		renderJson(json);
	}

	
	/**
	 * 添加页面
	 * @return
	 * @author tangxing
	 * @date 2016-5-19上午10:14:45
	 */
	public String toAddConferenceStaffCost(){
		//会议ID
		String conferenceId = HttpTool.getParameter("id");
		//会议号
		String conferenceNo =  HttpTool.getParameter("conferenceNo");
		
		ErpConference obj = null;
		
		Map searchMap = super.buildSearch();
		
		String id= "id";
		String conferenceNo2="conferenceNo";
		
		if(StringUtils.isNotEmpty(conferenceId)&&StringUtils.isNotEmpty(conferenceNo)){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put(id, conferenceId);
			params.put(conferenceNo2, conferenceNo);
			searchMap.put("filter_and_id_LIKE_S", conferenceId);
			searchMap.put("filter_and_conferenceNo_LIKE_S", conferenceNo);
			List<ErpConference> conferenceList = null;
			try {
				conferenceList = conferenceService.listConferenceByProps(params);
				if(!CollectionUtils.isEmpty(conferenceList)){
					obj = conferenceList.get(0);
				}
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
				conferenceCostManService.findByPage2(page, searchMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HttpTool.setAttribute("obj", obj);
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toAddConferenceStaffCost";
	}
	
	/**
	 * 保存场次人员费用
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午1:40:31
	 */
	public String saveConferenceStaffCost(){
		boolean flag = false;
		JSONObject json = new JSONObject();
		json.put("result", "error");
		json.put("statusCode", 300);
		json.put("message","保存失败！");
		json.put("navTabId", super.navTabId);

		//会议ID
		String conferenceId = HttpTool.getParameter("id");
		//会议号
		String conferenceNo =  HttpTool.getParameter("conferenceNo");
		
		ErpConference conference = conferenceService.get(conferenceId);

		if(StringUtils.isNotEmpty(conferenceId)&&StringUtils.isNotEmpty(conferenceNo)&&!CollectionUtils.isEmpty(entityList)){
			try {
				 //获取当前用户
				User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		    	Map<String,Object> params = new HashMap<String, Object>();
		    	ErpConferenceStaffCost entity = null;
		    	Double produceDou = null;
		    	Integer settNumbers = null;
    			if(conference!=null){
    				produceDou = conference.getProduceCost()==null ? new Double("0"):conference.getProduceCost();	//原有的总费用
    				settNumbers = conference.getSettNumbers()==null ? new Integer(0):conference.getSettNumbers();	//原有的人数
		    			
					for (int i = 0; i < entityList.size(); i++) {
						entity = entityList.get(i);
						params.clear();
						//会议ID
						params.put("id", conferenceId);
						//会议号
						params.put("conferenceNo", conferenceNo);
				    	//员工名
						params.put("staff", entity.getStaff());
						//员工职能
						params.put("position", entity.getPosition());
						//验证是否存在
						ErpConferenceStaffCost existObj = conferenceCostManService.checkExist(params);
						
						entity.setConferenceId(conferenceId);
						entity.setConferenceNo(conferenceNo);
						if(existObj!=null){
							entity.setId(existObj.getId());
							entity.setCreateTime(existObj.getCreateTime());
							entity.setCreateUser(existObj.getCreateUser());
							entity.setCreateUserId(existObj.getCreateUserId());
							entity.setUpdateUserId(currentUser.getId());
							entity.setUpdateUser(currentUser.getUserName());
							entity.setUpdateTime(new Date());
							//先减去之前的总额, 再加上现在的总额
							produceDou = produceDou- Double.parseDouble(""+existObj.getAmount()) + Double.parseDouble(""+entity.getAmount());
						}else{
							entity.setCreateUserId(currentUser.getId());
							entity.setCreateUser(currentUser.getUserName());
							entity.setCreateTime(new Date());
							settNumbers ++;
							produceDou += Double.parseDouble(""+entity.getAmount());
						}
						
					}
					flag = conferenceCostManService.saveList(entityList);
		
					conference.setSettNumbers(settNumbers);
					conference.setProduceCost(produceDou);
					conference.setUpdateUserId(currentUser.getId());
					conference.setUpdateUserName(currentUser.getUserName());
					conference.setUpdateTime(new Date());
					conferenceService.update(conference);
					
					if(flag){
						json.put("result", "success");
						json.put("statusCode", 200);
						json.put("message","保存成功！");
						json.put("navTabId", super.navTabId);
						json.put("callbackType","closeCurrent");
					}
    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderJson(json);
		return null;
	}
	
	/**
	 * 查询当前会议的人员费用列表
	 * @return
	 * @author tangxing
	 * @date 2016-5-19下午5:33:09
	 */
	public String getPersonByConferenceId(){
		String conferenceId = HttpTool.getParameter("id");
		Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
//        searchMap.put("filter_and_isDeleted_EQ_I", 0);//默认查询条件
        try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        List<ErpConferenceStaffCost> conferenceStaffList=conferenceCostManService.findByPage3(page, searchMap,conferenceId);
        page.setResults(conferenceStaffList);
        
        HttpTool.setAttribute("conferenceId", conferenceId);
		
		return "listPersonDetail";
	}
	
	 public void export2Excel(){
		 JSONObject jsonObject = new JSONObject();
		 try {
			 String jsonData = HttpTool.getParameter("jsonData");
			 HttpServletRequest request = ServletActionContext.getRequest();
			 JSONArray jsonArray = JSONArray.fromObject(jsonData);
			 ConferenceQuery query = new ConferenceQuery();
			 query.setConferenceNo(jsonArray.getJSONObject(0).getString("confQuery.conferenceNo"));
			 query.setConferenceType(jsonArray.getJSONObject(1).getString("confQuery.conferenceType"));
			 query.setBranchCompany(java.net.URLDecoder.decode(jsonArray.getJSONObject(2).getString("confQuery.branchCompany"),"UTF-8"));
			 query.setProjectCode(jsonArray.getJSONObject(3).getString("confQuery.projectCode"));
			 query.setProjectOwner(java.net.URLDecoder.decode(jsonArray.getJSONObject(4).getString("confQuery.projectOwner"),"UTF-8"));
			 query.setProvince(jsonArray.getJSONObject(5).getString("confQuery.province"));
			 query.setStartDate(jsonArray.getJSONObject(6).getString("confQuery.startDate"));
			 query.setEndDate(jsonArray.getJSONObject(7).getString("confQuery.endDate"));
			 query.setCity(jsonArray.getJSONObject(8).getString("confQuery.city"));
			 query.setProjectType(jsonArray.getJSONObject(9).getString("confQuery.projectType"));
			 query.setIsExistCost(jsonArray.getJSONObject(10).getString("confQuery.isExistCost"));
			 String filePath = conferenceCostManService.getDownloadPath(query, request);
			 jsonObject.put("path",filePath);
		 } catch (Exception e) {
			 logger.error("导出失败"+e);
			 jsonObject.put("",0);
		 }
		 renderJson(jsonObject);
//		 return page;
	}
	
	/**
	 * @since 2017年2月21日12:29:52
	 * @author Carly
	 * 详细费用导入
	 */
	public void uploadDetailCost(){
		
		JSONObject json = new JSONObject();
		try {
			int[] countArr = costService.saveUploadDetailCost(file);	
			json.put("count0", countArr[0]);		//没有会议号
			json.put("count1", countArr[1]);	//保存成功的
			json.put("count2", countArr[2]);	//没有该费用名称
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入失败uploadDetailCost---", e);
		}
		renderJson(json);
	}
	
	/**
	 * @since 2017年2月22日15:14:23
	 * @author Carly
	 * @return 单项异常数据查看
	 */
	public String listConferenceCostDetailExc() {
		try {
			Map<String,Object> map = buildSearch();
			page = new Page<ErpConferenceCostDetailExc>(HttpTool.getPageNum(),HttpTool.getPageSize());
			List<ErpConferenceCostDetailExc> list = excService.findByPage(page, map);
			page.setResults(list);
		} catch (Exception e) {
			logger.error("ConferenceCostManageAction listConferenceCostDetailExc(单项异常查看)",e);
		}
		return "listConferenceCostDetailExc";
	}
	 
	/**
	 * @since 2017年2月22日15:14:37
	 * @author Carly
	 * @return 单项费用导出
	 */
	public void exportCostDetail () {
		JSONObject jsonObject = new JSONObject();
		try {
			String jsonData = HttpTool.getParameter("jsonData");
			HttpServletRequest request = ServletActionContext.getRequest();
			JSONArray jsonArray = JSONArray.fromObject(jsonData);
			ConferenceQuery query = new ConferenceQuery();
			query.setConferenceNo(jsonArray.getJSONObject(0).getString("confQuery.conferenceNo"));
			query.setConferenceType(jsonArray.getJSONObject(1).getString("confQuery.conferenceType"));
			query.setBranchCompany(java.net.URLDecoder.decode(jsonArray.getJSONObject(2).getString("confQuery.branchCompany"),"UTF-8"));
			query.setProjectCode(jsonArray.getJSONObject(3).getString("confQuery.projectCode"));
			query.setProjectOwner(java.net.URLDecoder.decode(jsonArray.getJSONObject(4).getString("confQuery.projectOwner"),"UTF-8"));
			query.setProvince(jsonArray.getJSONObject(5).getString("confQuery.province"));
			query.setStartDate(jsonArray.getJSONObject(6).getString("confQuery.startDate"));
			query.setEndDate(jsonArray.getJSONObject(7).getString("confQuery.endDate"));
			query.setCity(jsonArray.getJSONObject(8).getString("confQuery.city"));
			query.setProjectType(jsonArray.getJSONObject(9).getString("confQuery.projectType"));
			query.setIsExistCost(jsonArray.getJSONObject(10).getString("confQuery.isExistCost"));
			String path = conferenceCostManService.exportConferencCostDetail(query,request);
			jsonObject.put("path",path);
			
		} catch (Exception e){
			logger.error("exportCostDetail---", e);
		}
		renderJson(jsonObject);
	}

	public List<ErpConferenceStaffCost> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ErpConferenceStaffCost> entityList) {
		this.entityList = entityList;
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
	public ConferenceQuery getConfQuery() {
		return confQuery;
	}

	public void setConfQuery(ConferenceQuery confQuery) {
		this.confQuery = confQuery;
	}
}
