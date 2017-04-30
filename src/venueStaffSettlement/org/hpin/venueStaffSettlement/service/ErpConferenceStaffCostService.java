package org.hpin.venueStaffSettlement.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PreciseCompute;
import org.hpin.events.dao.ErpConferenceDao;
import org.hpin.events.entity.ErpConference;
import org.hpin.venueStaffSettlement.dao.ErpConferenceCostDetailDao;
import org.hpin.venueStaffSettlement.dao.ErpConferenceStaffCostDao;
import org.hpin.venueStaffSettlement.dao.ErpNonConferenceCostDetailExcDao;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetail;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetailExc;
import org.hpin.venueStaffSettlement.entity.ErpConferenceStaffCost;
import org.hpin.venueStaffSettlement.util.FeesTypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "org.hpin.venueStaffSettlement.service.ErpConferenceStaffCostService")
@Transactional()
public class ErpConferenceStaffCostService extends BaseService {
	
	@Autowired
	private ErpConferenceStaffCostDao dao ;
	@Autowired
	private ErpConferenceCostDetailDao detailDao;
	@Autowired
	private ErpConferenceDao conferenceDao;
	@Autowired
	private ErpNonConferenceCostDetailExcDao excDao;
	
	private Logger logger = Logger.getLogger(ErpConferenceStaffCostService.class);

	/**
	 * @param conferenceId 会议ID
	 * @param name 名字
	 * @param belong 费用类型0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
	 * @return
	 */
//	public List<ErpConferenceStaffCost> getConferenceCostInfo(String conferenceId, String name) {
//		String sql ="from ErpConferenceStaffCost where conference_id=? and staff=?";
//		return dao.getHibernateTemplate().find(sql,conferenceId,name);
//	}

	/**
	 * @param conferenceId
	 * @param staff
	 * @return 是否存在该用户
	 */
	public int getPersonCount(String conferenceId, String staff) {
		String sql ="select count(1) from erp_conference_staff_cost where conference_id=? and staff=?";
		return dao.getJdbcTemplate().queryForInt(sql, conferenceId,staff);
	}

	/**
	 * @param conferenceId 会议id
	 * @param name 姓名
	 * @return 获取小计的总和
	 */
	public BigDecimal getAmoutSum(String costId) {
		String sql = "select nvl(travel_cost,0)+nvl(instructor_cost,0)+nvl(city_traffic_cost,0)+nvl(province_traffic_cost,0)+nvl(hotel_cost,0)+nvl(sample_cost,0)+nvl(other_cost,0) amout"
				+ " from erp_conference_staff_cost ecsc where ecsc.id=? ";
		return (BigDecimal)dao.getJdbcTemplate().queryForMap(sql,costId).get("AMOUT");
	}

	/**
	 * @param file
	 * @param accountName
	 * @return 获取上传的数据
	 * @throws Exception 
	 */
	public List<ErpConferenceStaffCost> getConferenceStaffCost(File file,String accountName) throws Exception {
		List <Map<String, String>> resultList =org.hpin.common.util.ExcelUtils.readXlsxExcel(file);
		return createSettlementObj(resultList,accountName);
	}

	private List<ErpConferenceStaffCost> createSettlementObj(List<Map<String, String>> resultList, String accountName)throws Exception {
		List<ErpConferenceStaffCost> list = new ArrayList<ErpConferenceStaffCost>();
		Date date = new Date();
		for(Map<String, String> map : resultList){
			ErpConferenceStaffCost staffCost = new ErpConferenceStaffCost();
			BigDecimal traveldeDecimal = PreciseCompute.string2BigDecimal(map.get("2"));
			BigDecimal instructorDecimal = PreciseCompute.string2BigDecimal(map.get("3"));
			BigDecimal cityTrafficDecimal = PreciseCompute.string2BigDecimal(map.get("4"));
			BigDecimal provinceTraffic = PreciseCompute.string2BigDecimal(map.get("5"));
			BigDecimal hoteldeDecimal = PreciseCompute.string2BigDecimal(map.get("6"));
			BigDecimal sampleDecimal = PreciseCompute.string2BigDecimal(map.get("7"));
			BigDecimal otherDecimal = PreciseCompute.string2BigDecimal(map.get("8"));
			BigDecimal amountDecimal = traveldeDecimal.add(instructorDecimal).add(cityTrafficDecimal).add(provinceTraffic).add(
					hoteldeDecimal).add(sampleDecimal).add(otherDecimal);
			staffCost.setConferenceNo(map.get("0").trim().replace(" ", ""));//场次号
			staffCost.setStaff(map.get("1").trim().replace(" ", ""));//姓名
			staffCost.setTravelCost(traveldeDecimal);//出差补助
			staffCost.setInstructorCost(instructorDecimal);//讲师费
			staffCost.setCityTrafficCost(cityTrafficDecimal);//市内交通费
			staffCost.setProvinceTrafficCost(provinceTraffic);//往来交通费
			staffCost.setHotelCost(hoteldeDecimal);//住宿费
			staffCost.setSampleCost(sampleDecimal);//采样费
			staffCost.setOtherCost(otherDecimal);//其他
			staffCost.setAmount(amountDecimal);//小计
			staffCost.setCreateTime(date);
			staffCost.setCreateUser(accountName);
			staffCost.setIsDeleted(0);
			list.add(staffCost);
		}
		return list;
	}

	/**
	 * @param conferenceNo
	 * @return 是否有该场次
	 */
	public List<ErpConference> isRepeatEvent(String conferenceNo) {
		String sql = "from ErpConference where conference_no=?";
		List <ErpConference> list = dao.getHibernateTemplate().find(sql,conferenceNo);
		return list;
	}

	/**
	 * @param conferenceNo
	 * @param staff
	 * @return 该场次是否有该用户
	 */
	public List<ErpConferenceStaffCost> getConferenceCost(String conferenceNo,String staff) {
		String sql = "from ErpConferenceStaffCost where conference_no=? and staff=?";
		List<ErpConferenceStaffCost> list = dao.getHibernateTemplate().find(sql,conferenceNo,staff);
		return list;
	}

	
	public int [] saveUploadDetailCost(File file ) throws Exception{
		
		List<ErpConferenceCostDetail> list = getConferenceCostDetail(file);
		int [] countArr = new int[3];
		for(ErpConferenceCostDetail costDetail:list){
			List<ErpConference> conferenceList = isRepeatEvent(costDetail.getConferenceNo());
			switch (conferenceList.size()) {
			case 0:
				countArr[0] ++;	//没有会议号
				excDao.getHibernateTemplate().save(getConferenceCostDetailException(costDetail, "没有对应的会议号"));
				break;

			default:
				if(FeesTypeList.getFeesList().contains(costDetail.getCostName())){//如果包含这些费用
					List<ErpConferenceStaffCost> staffCostList = getConferenceCost(costDetail.getConferenceNo(),costDetail.getName());
					List<ErpConference> conferenceConstList =  dao.getConferenceListByConferenceNo(costDetail.getConferenceNo());
					ErpConference conference = conferenceConstList.get(0);
					costDetail.setConferenceId(conference.getId());	//会议id
					
					switch (staffCostList.size()) {
					case 0:
						saveNewCostDetail(costDetail, conference);
						countArr[1] ++;	//保存成功的费用
						break;
						
					default:
						ErpConferenceStaffCost staffCost = staffCostList.get(0);
						saveOldCostDetail(costDetail, conference, staffCost);
						countArr[1] ++;	//保存成功的费用
						break;
					}
					
				} else {
					countArr[2] ++;	//没有该费用名称
					excDao.getHibernateTemplate().save(getConferenceCostDetailException(costDetail, "没有对应的费用名称"));
				}
				break;
			}
			
		}
		return countArr;
	}
	
	/**
	 * @param file
	 * @return 获取详细费用
	 */
	private List<ErpConferenceCostDetail> getConferenceCostDetail(File file) throws Exception{
		//抬头: 场次号	姓名		类别		天数		航班/车次(其他类别可不填写)	描述	金额
		List <Map<String, String>> resultList =org.hpin.common.util.ExcelUtils.readXlsxExcel(file);
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		List<ErpConferenceCostDetail> list = new ArrayList<ErpConferenceCostDetail>();
		Date date = new Date();
		for(Map<String, String> map : resultList){
			ErpConferenceCostDetail costDetail = new ErpConferenceCostDetail();
			String belong = map.get("2").replaceAll(" ", "");
			costDetail.setCostName(belong);
			if (FeesTypeList.getFeesList().contains(belong)) {
				costDetail.setBelong(FeesTypeList.getFeesTypeMap().get(belong));
				costDetail.setConferenceNo(map.get("0").replaceAll(" ", ""));
				costDetail.setName(map.get("1").replaceAll(" ", ""));
				//0:市内交通费;1:往返交通费;2:住宿费;3:采样费;4:其他费用
				costDetail.setDays(map.get("3"));
				costDetail.setFlight(map.get("4"));
				costDetail.setDescripe(map.get("5"));
				costDetail.setCost(PreciseCompute.string2BigDecimal(map.get("6").replaceAll(" ", "'")));
				costDetail.setCreateTime(date);
				costDetail.setCreateUser(user.getAccountName());
				costDetail.setCostName(belong);
			}
			
			list.add(costDetail);
		}
		return list;
	}
	
	/**
	 * @since 2017年2月21日18:29:55
	 * @author Carly
	 * @param costDetail
	 * @param conference
	 * @return 保存没有人员的费用
	 * @throws Exception
	 */
	private boolean saveNewCostDetail(ErpConferenceCostDetail costDetail,ErpConference conference) throws Exception{
		
		boolean flag = false;
		ErpConferenceStaffCost staffCost = new ErpConferenceStaffCost();
		BigDecimal zeroDecimal = new BigDecimal(0);
		staffCost.setConferenceId(conference.getId());
		staffCost.setConferenceNo(conference.getConferenceNo());
		staffCost.setInstructorCost(zeroDecimal);
		staffCost.setTravelCost(zeroDecimal);
		staffCost.setStaff(costDetail.getName());
		
		if ("0".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(costDetail.getCost());
			staffCost.setProvinceTrafficCost(zeroDecimal);
			staffCost.setHotelCost(zeroDecimal);
			staffCost.setSampleCost(zeroDecimal);
			staffCost.setOtherCost(zeroDecimal);
			staffCost.setAmount(costDetail.getCost());
			
		} else if ("1".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(zeroDecimal);
			staffCost.setProvinceTrafficCost(costDetail.getCost());
			staffCost.setHotelCost(zeroDecimal);
			staffCost.setSampleCost(zeroDecimal);
			staffCost.setOtherCost(zeroDecimal);
			staffCost.setAmount(costDetail.getCost());
			
		} else if ("2".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(zeroDecimal);
			staffCost.setProvinceTrafficCost(zeroDecimal);
			staffCost.setHotelCost(costDetail.getCost());
			staffCost.setSampleCost(zeroDecimal);
			staffCost.setOtherCost(zeroDecimal);
			staffCost.setAmount(costDetail.getCost());
			
		} else if ("3".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(zeroDecimal);
			staffCost.setProvinceTrafficCost(zeroDecimal);
			staffCost.setHotelCost(zeroDecimal);
			staffCost.setSampleCost(costDetail.getCost());
			staffCost.setOtherCost(zeroDecimal);
			staffCost.setAmount(costDetail.getCost());
			
		} else if ("4".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(zeroDecimal);
			staffCost.setProvinceTrafficCost(zeroDecimal);
			staffCost.setHotelCost(zeroDecimal);
			staffCost.setSampleCost(zeroDecimal);
			staffCost.setOtherCost(costDetail.getCost());
			staffCost.setAmount(costDetail.getCost());
			
		}
		
		staffCost.setCreateUser(costDetail.getCreateUser());
		staffCost.setCreateTime(costDetail.getCreateTime());
		
		conference.setSettNumbers(conference.getSettNumbers()+1);
		conference.setProduceCost(conference.getProduceCost()+costDetail.getCost().doubleValue());
		
		detailDao.getHibernateTemplate().save(costDetail);
		dao.getHibernateTemplate().save(staffCost);
		conferenceDao.getHibernateTemplate().update(conference);
		
		flag = true;
		
		return flag;
	}
	
	/**
	 * @since 2017年2月21日18:29:55
	 * @author Carly
	 * @param costDetail
	 * @param conference
	 * @return 保存已经录入的人员费用
	 * @throws Exception
	 */
	private boolean saveOldCostDetail(ErpConferenceCostDetail costDetail,ErpConference conference,ErpConferenceStaffCost staffCost) throws Exception{
		
		boolean flag = false;
		
		if ("0".equals(costDetail.getBelong())) {
			staffCost.setCityTrafficCost(PreciseCompute.add(staffCost.getCityTrafficCost(),costDetail.getCost()));
			staffCost.setAmount(PreciseCompute.add(staffCost.getAmount(),costDetail.getCost()));
			
		} else if ("1".equals(costDetail.getBelong())) {
			staffCost.setProvinceTrafficCost(PreciseCompute.add(staffCost.getProvinceTrafficCost(),costDetail.getCost()));
			staffCost.setAmount(PreciseCompute.add(staffCost.getAmount(),costDetail.getCost()));
			
		} else if ("2".equals(costDetail.getBelong())) {
			staffCost.setHotelCost(PreciseCompute.add(staffCost.getHotelCost(),costDetail.getCost()));
			staffCost.setAmount(PreciseCompute.add(staffCost.getAmount(),costDetail.getCost()));
			
		} else if ("3".equals(costDetail.getBelong())) {
			staffCost.setSampleCost(PreciseCompute.add(staffCost.getSampleCost(),costDetail.getCost()));
			staffCost.setAmount(PreciseCompute.add(staffCost.getAmount(),costDetail.getCost()));
			
		} else if ("4".equals(costDetail.getBelong())) {
			staffCost.setOtherCost(PreciseCompute.add(staffCost.getOtherCost(),costDetail.getCost()));
			staffCost.setAmount(PreciseCompute.add(staffCost.getAmount(),costDetail.getCost()));
			
		}
		
		staffCost.setUpdateUser(costDetail.getCreateUser());
		staffCost.setUpdateTime(costDetail.getCreateTime());
		
		conference.setProduceCost(conference.getProduceCost()+costDetail.getCost().doubleValue());
		
		detailDao.getHibernateTemplate().save(costDetail);		//保存详细费用
		dao.getHibernateTemplate().update(staffCost);			//更新费用
		conferenceDao.getHibernateTemplate().update(conference);//更新总费用
		
		flag = true;
		
		return flag;
	}
	
	private ErpConferenceCostDetailExc getConferenceCostDetailException(ErpConferenceCostDetail costDetail,String descripe) {
		ErpConferenceCostDetailExc detailExc2 = new ErpConferenceCostDetailExc();
		detailExc2.setConferenceNo(costDetail.getConferenceNo());
		detailExc2.setName(costDetail.getName());
		detailExc2.setDays(costDetail.getDays());
		detailExc2.setFlight(costDetail.getFlight());
		detailExc2.setCost(costDetail.getCost());
		detailExc2.setDescribe(costDetail.getDescripe());
		detailExc2.setCostName(costDetail.getCostName());
		detailExc2.setExceptionDescribe(descripe);
		detailExc2.setCreateUser(costDetail.getCreateUser());
		detailExc2.setCreateTime(costDetail.getCreateTime());
		return detailExc2;
	}
	
}
