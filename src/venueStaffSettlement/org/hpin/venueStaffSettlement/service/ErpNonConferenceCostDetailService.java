package org.hpin.venueStaffSettlement.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.venueStaffSettlement.dao.ErpNonConferenceCostDao;
import org.hpin.venueStaffSettlement.dao.ErpNonConferenceCostDetailDao;
import org.hpin.venueStaffSettlement.dao.ErpNonConferenceDao;
import org.hpin.venueStaffSettlement.entity.ErpNonConference;
import org.hpin.venueStaffSettlement.entity.ErpNonConferenceCost;
import org.hpin.venueStaffSettlement.entity.ErpNonConferenceCostDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Carly
 * @since 2017年1月23日14:52:45
 * 非会场费用管理
 */
@Service(value = "org.hpin.venueStaffSettlement.service.ErpNonConferenceCostDetailService")
@Transactional()
public class ErpNonConferenceCostDetailService extends BaseService {
	@Autowired
	ErpNonConferenceDao dao;
	@Autowired
	ErpNonConferenceCostDao costDao;
	@Autowired
	ErpNonConferenceCostDetailDao costDetailDao;
	Logger logger = Logger.getLogger(ErpNonConferenceCostDetailService.class);
	
	
	/**
	 * @param dataJson
	 * @param costId
	 * @param belong
	 * @param travelDecimal
	 * @since 2017年1月23日14:53:04
	 * @author Carly
	 * @param costDecimal2 
	 * @return 保存明细
	 */
	public String saveConferenceCostDetailInfo(String dataJson,String projectInfo, String nonConferenceId,
			String costId,String name,String belong, BigDecimal travelDecimal,BigDecimal amount, BigDecimal costDecimal2) throws Exception{
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		Date date = new Date();
		BigDecimal totalDetail = new BigDecimal(0);
		JSONArray jsonArray = JSONArray.fromObject(dataJson);
		JSONArray projectArray = JSONArray.fromObject(projectInfo); 
//		JSONArray costArray = JSONArray.fromObject(costInfo);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		
		for(int i=0;i<jsonArray.size();i++){//保存详细信息
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String days = jsonObject.getString("days");
			String flight = jsonObject.getString("flight");
			String descripe = jsonObject.getString("descripe");
			BigDecimal costDecimal = new BigDecimal(jsonObject.getString("cost"));			//输入的明细的费用
			String costDetailId = jsonObject.getString("costDetailId");
			totalDetail = totalDetail.add(costDecimal);
			if (costDetailId.equals("0")) {	//新增费用明细
				ErpNonConferenceCostDetail costDetail = new ErpNonConferenceCostDetail();
				costDetail.setId(UUID.randomUUID().toString().replace("-", ""));
				costDetail.setCostId(uuid);
				if(!costId.equals("0")){
					costDetail.setCostId(costId);
				}
				costDetail.setDays(days);
				costDetail.setFlight(flight);
				costDetail.setDescripe(descripe);
				costDetail.setCost(costDecimal);
				costDetail.setBelong(Integer.valueOf(belong));
				costDetail.setCreateUser(user.getUserName());
				costDetail.setCreateTime(date);
				this.save(costDetail);
				
			}else {	//更新
				ErpNonConferenceCostDetail costDetail = (ErpNonConferenceCostDetail) costDetailDao.findById(ErpNonConferenceCostDetail.class,costDetailId);
				costDetail.setCostId(costId);
				costDetail.setDays(days);
				costDetail.setFlight(flight);
				costDetail.setDescripe(descripe);
				costDetail.setCost(costDecimal);
				costDetail.setUpdateTime(date);
				costDetail.setCreateUser(user.getUserName());
				this.update(costDetail);	//更新费用明细
			}
		}
		
		if(costId.equals("0")){//如果人员是新增的
			ErpNonConferenceCost nonConferenceCost = getNonConferenceCost(name, uuid, belong, nonConferenceId,travelDecimal, totalDetail);
			costDao.save(nonConferenceCost);
		}else{
			ErpNonConferenceCost staffCost = (ErpNonConferenceCost)costDao.findById(ErpNonConferenceCost.class,costId);
			staffCost.setName(name);
			staffCost.setTravelCost(travelDecimal);	//差旅费
			if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
				staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getCityCost()).add(totalDetail));
				staffCost.setCityCost(totalDetail);
				
			}else if(belong.equals("1")){//往返交通费
				staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getProvinceCost()).add(totalDetail));
				staffCost.setProvinceCost(totalDetail);
				
			}else if(belong.equals("2")){//住宿费
				staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getHotelCost()).add(totalDetail));
				staffCost.setHotelCost(totalDetail);
				
			}else if(belong.equals("3")){//劳务费
				staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getLaborCost()).add(totalDetail));
				staffCost.setLaborCost(totalDetail);
				
			}else if(belong.equals("4")){//其他费用
				staffCost.setAmount(staffCost.getAmount().subtract(staffCost.getOtherCost()).add(totalDetail));
				staffCost.setOtherCost(totalDetail);
			}
			costDao.update(staffCost);
			
		}
		
		if(StringUtils.isEmpty(nonConferenceId)){
			JSONObject json = projectArray.getJSONObject(0);
			String projectCode = json.getString("projectCode");
			String projectName = json.getString("projectName");
			String projectUser = json.getString("projectUser");
			String projectType = json.getString("projectType");
			String OASerial = json.getString("OASerial");
			Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("startTime"));
			Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("endTime"));
			String month = json.getString("month");
			String note = json.getString("note");
			StringBuilder monthDate = new StringBuilder(month);
			monthDate.insert(4, "-").insert(7, "-01");//默认为月份日期为第一天
			Date monthTime = new SimpleDateFormat("yyyy-MM-dd").parse(monthDate.toString()); 
//			ProjectType type = dao.getProjectInfo(projectType);

			ErpNonConference nonConference = new ErpNonConference();
			nonConference.setId(uuid);
			nonConference.setProjectCode(projectCode);
			nonConference.setProjectName(projectName);
			nonConference.setProjectUser(projectUser);
			nonConference.setProjectType(projectType);
			nonConference.setStartTime(startTime);
			nonConference.setEndTime(endTime);
			nonConference.setMonth(month);
			nonConference.setMonthTime(monthTime);
			nonConference.setFees(totalDetail.add(travelDecimal));
			nonConference.setNote(note);
			nonConference.setOASerial(OASerial);
			nonConference.setCreateTime(date);
			nonConference.setCreateUser(user.getUserName());
			nonConference.setIsDeleted(0);
			dao.save(nonConference);
			
		}else {
			
			BigDecimal oldDecimal = costDao.getSumAmount(nonConferenceId);
			if(costId.equals("0")){
				costDao.udpateFees(nonConferenceId,oldDecimal.add(totalDetail).add(travelDecimal));
				
			}else {
				ErpNonConference nonConference = (ErpNonConference) dao.findById(ErpNonConference.class, nonConferenceId);
				JSONObject json = projectArray.getJSONObject(0);
				String projectCode = json.getString("projectCode");
				String projectName = json.getString("projectName");
				String projectUser = json.getString("projectUser");
				String projectType = json.getString("projectType");
				String OASerial = json.getString("OASerial");
				Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("startTime"));
				Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("endTime"));
				String month = json.getString("month");
				String note = json.getString("note");
				StringBuilder monthDate = new StringBuilder(month);
				monthDate.insert(4, "-").insert(7, "-01");//默认为月份日期为第一天
				Date monthTime = new SimpleDateFormat("yyyy-MM-dd").parse(monthDate.toString()); 

				nonConference.setProjectCode(projectCode);
				nonConference.setProjectName(projectName);
				nonConference.setProjectUser(projectUser);
				nonConference.setProjectType(projectType);
				nonConference.setStartTime(startTime);
				nonConference.setEndTime(endTime);
				nonConference.setMonth(month);
				nonConference.setMonthTime(monthTime);
				nonConference.setFees(totalDetail.add(travelDecimal));
				nonConference.setNote(note);
				nonConference.setOASerial(OASerial);
				nonConference.setCreateTime(date);
				nonConference.setCreateUser(user.getUserName());
				nonConference.setIsDeleted(0);
				nonConference.setFees(oldDecimal);
				BigDecimal decimal = amount.subtract(travelDecimal);
				if (decimal.compareTo(totalDetail) == -1) {
					nonConference.setFees(oldDecimal.add(totalDetail).subtract(costDecimal2));
				}
				dao.update(nonConference);
	//			costDao.udpateFees(nonConferenceId,oldDecimal.add(totalDetail).subtract(costDecimal2));
			}
		}
		
		if(StringUtils.isEmpty(nonConferenceId)){
			return uuid;
		}else {
			return nonConferenceId;
		}
	}
	
	private ErpNonConferenceCost getNonConferenceCost(String name,String uuid,String belong,String nonConferenceId,BigDecimal travelDecimal,BigDecimal totalDetail){
		ErpNonConferenceCost nonConferenceCost= new ErpNonConferenceCost();
		BigDecimal zeroDecimal = new BigDecimal(0);
		nonConferenceCost.setName(name);
		nonConferenceCost.setTravelCost(travelDecimal);
		nonConferenceCost.setId(uuid);
		nonConferenceCost.setAmount(travelDecimal.add(totalDetail));
		nonConferenceCost.setNonConferenceId(nonConferenceId);
		if(StringUtils.isEmpty(nonConferenceId)){
			nonConferenceCost.setNonConferenceId(uuid);
		}
		if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
			nonConferenceCost.setCityCost(totalDetail);
			nonConferenceCost.setProvinceCost(zeroDecimal);//防止在更新其他费用取空的问题
			nonConferenceCost.setHotelCost(zeroDecimal);
			nonConferenceCost.setLaborCost(zeroDecimal);
			nonConferenceCost.setOtherCost(zeroDecimal);
		}else if(belong.equals("1")){//往返交通费
			nonConferenceCost.setProvinceCost(totalDetail);
			nonConferenceCost.setCityCost(zeroDecimal);
			nonConferenceCost.setHotelCost(zeroDecimal);
			nonConferenceCost.setLaborCost(zeroDecimal);
			nonConferenceCost.setOtherCost(zeroDecimal);
		}else if(belong.equals("2")){//住宿费
			nonConferenceCost.setHotelCost(totalDetail);
			nonConferenceCost.setCityCost(zeroDecimal);
			nonConferenceCost.setProvinceCost(zeroDecimal);
			nonConferenceCost.setLaborCost(zeroDecimal);
			nonConferenceCost.setOtherCost(zeroDecimal);
		}else if(belong.equals("3")){//采样费
			nonConferenceCost.setLaborCost(totalDetail);
			nonConferenceCost.setCityCost(zeroDecimal);
			nonConferenceCost.setProvinceCost(zeroDecimal);
			nonConferenceCost.setHotelCost(zeroDecimal);
			nonConferenceCost.setOtherCost(zeroDecimal);
		}else if(belong.equals("4")){//其他费用
			nonConferenceCost.setOtherCost(totalDetail);
			nonConferenceCost.setCityCost(zeroDecimal);
			nonConferenceCost.setProvinceCost(zeroDecimal);
			nonConferenceCost.setHotelCost(zeroDecimal);
			nonConferenceCost.setLaborCost(zeroDecimal);
		}
		return nonConferenceCost;
	}

	public List<ErpNonConferenceCostDetail> getCostDetailList(String costId,String belong) {
		return costDetailDao.getCostDetailList(costId,belong);
	}

	/**
	 * @param costDetailId
	 * @param belong
	 * @param costId
	 * @param nonConferenceId
	 * @since 2017年1月24日11:42:57
	 */
	public void removeCostDetail(String costDetailId, String belong,String costId, String nonConferenceId) throws Exception{
		ErpNonConferenceCost nonConferenceCost = (ErpNonConferenceCost) costDao.findById(ErpNonConferenceCost.class, costId);
		ErpNonConferenceCostDetail nonConferenceCostDetail = (ErpNonConferenceCostDetail)costDetailDao.findById(ErpNonConferenceCostDetail.class, costDetailId);
		ErpNonConference nonConference = (ErpNonConference)dao.findById(ErpNonConference.class, nonConferenceId);
		
		Date date = new Date();
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		
		if(belong.equals("0")){//费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
			nonConferenceCost.setCityCost(nonConferenceCost.getCityCost().subtract(nonConferenceCostDetail.getCost()));
		}else if(belong.equals("1")){//往返交通费
			nonConferenceCost.setProvinceCost(nonConferenceCost.getProvinceCost().subtract(nonConferenceCostDetail.getCost()));
		}else if(belong.equals("2")){//住宿费
			nonConferenceCost.setHotelCost(nonConferenceCost.getHotelCost().subtract(nonConferenceCostDetail.getCost()));
		}else if(belong.equals("3")){//采样费
			nonConferenceCost.setLaborCost(nonConferenceCost.getLaborCost().subtract(nonConferenceCostDetail.getCost()));
		}else if(belong.equals("4")){//其他费用
			nonConferenceCost.setOtherCost(nonConferenceCost.getOtherCost().subtract(nonConferenceCostDetail.getCost()));
		}
		nonConferenceCost.setAmount(nonConferenceCost.getAmount().subtract(nonConferenceCostDetail.getCost()));
		
		nonConference.setFees(nonConference.getFees().subtract(nonConferenceCostDetail.getCost()));
		nonConference.setUpdateTime(date);
		nonConference.setUpdateUser(user.getUserName());
		
		costDetailDao.delete(ErpNonConferenceCostDetail.class, costDetailId);
		costDao.update(nonConferenceCost);
		dao.update(nonConference);
		
	}
	
	
}
