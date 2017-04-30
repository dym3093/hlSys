package org.hpin.venueStaffSettlement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.venueStaffSettlement.dao.ConferenceCostManageDao;
import org.hpin.venueStaffSettlement.entity.ErpConferenceCostDetail;
import org.hpin.venueStaffSettlement.entity.ErpConferenceStaffCost;
import org.hpin.venueStaffSettlement.entity.vo.ConferenceCostExport;
import org.hpin.venueStaffSettlement.entity.vo.ConferenceCostVo;
import org.hpin.venueStaffSettlement.entity.vo.ConferenceQuery;
import org.hpin.venueStaffSettlement.util.CreateNewExcel;
import org.hpin.venueStaffSettlement.util.FeesTypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service(value = "org.hpin.venueStaffSettlement.service.ConferenceCostManageService")
@Transactional()
public class ConferenceCostManageService extends BaseService {
	@Autowired
	ConferenceCostManageDao dao;
	Logger logger = Logger.getLogger(ConferenceCostManageService.class);
	
	private String conferenceExcel;
	/**
	 * 查询会议结算费用;
	 * create by henry.xu 20160928;
	 * @param page 分页;
	 * @param confQuery 查询条件;
	 * @changer chenqi 2016年11月23日18:04:56
	 */
	@SuppressWarnings("rawtypes")
	public void findConferenceCostsByCondition(Page page, ConferenceQuery confQuery) {
		//参数集合;
		List<Object> params = new ArrayList<Object>();
		//sql
		StringBuilder sql = new StringBuilder();
		sql.append("select confer.ID id, confer.CONFERENCE_NO conferenceNo, confer.CONFERENCE_DATE conferenceDate, ")
			.append("confer.ADDRESS address, confer.PRODUCE_COST produceCost, shipPro_type.TYPENAME projectTypeName, ")
			.append("ud.dept_name ownedCompany, hcr.BRANCH_COMMANY branchCompany,confer.SETT_NUMBERS settNumbers, hl.region_name provice, ")
			.append("hl2.region_name city, sd.dictname conferenceType from ERP_CONFERENCE confer ")
			.append("LEFT JOIN (select shipPro.id, shipPro.PROJECT_CODE, shipPro.PROJECT_OWNER, shipPro.PROJECT_TYPE, protype.project_type_name typeName ")
			.append("from HL_CUSTOMER_RELATIONSHIP_PRO shipPro inner join t_project_type protype on protype.id = shipPro.PROJECT_TYPE) ")
			.append("shipPro_type ON shipPro_type.id = confer.CUSTOMERRELATIONSHIPPRO_ID , hl_region hl, hl_region hl2, hl_customer_relationship hcr, ")
			.append("um_dept ud, sys_dicttype sd  where confer.IS_DELETED in(0,2) and hl.id = confer.province and hl2.id = confer.city ")
			.append("and hcr.id = confer.branch_company_id and ud.id = confer.owned_company_id and sd.dictid = confer.conference_type ");
			
		StringBuilder jdbcSql = new StringBuilder(sql);
		//参数处理
		dealParams(confQuery, jdbcSql);
		jdbcSql.append(" ORDER BY confer.CONFERENCE_DATE DESC ");
		page.setTotalCount(dao.findJdbcCount(jdbcSql, params));
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ConferenceCostVo> rowMapper = new BeanPropertyRowMapper<ConferenceCostVo>(ConferenceCostVo.class);
		page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
		
	}

	private void dealParams(ConferenceQuery confQuery, StringBuilder jdbcSql) {
		if(confQuery != null) {
			//支公司名称;
			if(StringUtils.isNotEmpty(confQuery.getBranchCompany())) {
				jdbcSql.append(" AND confer.BRANCH_COMPANY = '").append(confQuery.getBranchCompany()).append("' ");
			}
			//城市;
			if(StringUtils.isNotEmpty(confQuery.getCity())) {
				jdbcSql.append(" AND confer.CITY = '").append(confQuery.getCity()).append("' ");
			}
			//省份
			if(StringUtils.isNotEmpty(confQuery.getProvince())) {
				jdbcSql.append(" AND confer.PROVINCE = '").append(confQuery.getProvince()).append("' ");
			}
			//会场号
			if(StringUtils.isNotEmpty(confQuery.getConferenceNo())) {
				jdbcSql.append(" AND confer.CONFERENCE_NO like '%").append(confQuery.getConferenceNo().trim()).append("%' ");
			}
			//会议类型
			if(StringUtils.isNotEmpty(confQuery.getConferenceType())) {
				jdbcSql.append(" AND confer.CONFERENCE_TYPE = '").append(confQuery.getConferenceType()).append("' ");
			}
			//项目编码
			if(StringUtils.isNotEmpty(confQuery.getProjectCode())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_CODE like '%").append(confQuery.getProjectCode().trim()).append("%' ");
			}
			//项目负责人
			if(StringUtils.isNotEmpty(confQuery.getProjectOwner())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_OWNER like '%").append(confQuery.getProjectOwner().trim()).append("%' ");
			}
			//项目类型;
			if(StringUtils.isNotEmpty(confQuery.getProjectType())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_TYPE = '").append(confQuery.getProjectType()).append("' ");
			}
			//会议时间
			if(StringUtils.isNotEmpty(confQuery.getStartDate())) {
				jdbcSql.append(" AND confer.CONFERENCE_DATE >= to_date('").append(confQuery.getStartDate()).append("', 'yyyy-MM-dd') ");
			}
			if(StringUtils.isNotEmpty(confQuery.getEndDate())) {
				jdbcSql.append(" AND confer.CONFERENCE_DATE < to_date('").append(confQuery.getEndDate()).append("', 'yyyy-MM-dd')+1 ");
			}
			//所属公司;(总公司)
			if(StringUtils.isNotEmpty(confQuery.getOwnedCompany())) {
				jdbcSql.append(" AND confer.OWNED_COMPANY like '%").append(confQuery.getOwnedCompany().trim()).append("%' ");
			}
			//创建人;
//			if(StringUtils.isNotEmpty(confQuery.getCreateUserName())) {
//				jdbcSql.append(" AND confer.CREATE_USER_NAME ='").append(confQuery.getCreateUserName()).append("' ");
//			}
			if(StringUtils.isNotEmpty(confQuery.getIsExistCost())) {//是否产生费用，PRODUCE_COST>0产生了费用，等于0时没产生费用
				if(confQuery.getIsExistCost().equals("0")){
					jdbcSql.append(" AND confer.PRODUCE_COST =0 ");
				}else if(confQuery.getIsExistCost().equals("1")){
					jdbcSql.append(" AND confer.PRODUCE_COST <> 0 ");
				}
			}
		}
	}
	
	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	
	/**
	 * 分页获取用户(会议结算)
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage2(Page page, Map searchMap) {
		return dao.findByPage2(page, searchMap);
	}
	
	/**
	 * 分页获取用户(会议人员费用)
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage3(Page page, Map searchMap,String conferenceId) {
		return dao.findByPage3(page, searchMap,conferenceId);
	}
	
	/**
	 * 验证是否存在
	 * @param params
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-5-19下午1:49:47
	 */
	public ErpConferenceStaffCost checkExist(Map<String, Object> params) throws Exception{
		ErpConferenceStaffCost entity = null;
		List<ErpConferenceStaffCost> list = null;
    	if(!CollectionUtils.isEmpty(params)){
    		list = dao.checkExist(params);
    	}
    	if(!CollectionUtils.isEmpty(list)){
    		entity = list.get(0);
    	}
    	return entity;
	}
	
	/**
	 * 批量保存ErpConferenceStaffCost
	 * @param entityList
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-5-19下午1:51:44
	 */
	public boolean saveList(List<ErpConferenceStaffCost> entityList) throws Exception{
		boolean flag = false;
		if(entityList!=null&&entityList.size()>0){
			flag = dao.saveList(entityList);
		}
		return flag;
	}

	/**
	 * @param id 会议号对应的id
	 * @return 人员费用录入信息（不包括明细）
	 */
	public List<ErpConferenceStaffCost> getConferenceCost(String id) throws Exception{
		String sql = "from ErpConferenceStaffCost where conference_id=?";
		return dao.getHibernateTemplate().find(sql,id);
	}
	
	public String getDownloadPath(ConferenceQuery query,HttpServletRequest request) throws Exception{
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).toString();
		String fileName = "会场费用.xls";
		String outFilePath = conferenceExcel+fileName;
		List<ConferenceCostExport> list = getConferenceVOList(query);
		List<List<String>> result = new ArrayList<List<String>>();
		int count = 1;
		for(ConferenceCostExport costExport:list){
			List<ErpConferenceStaffCost> staffCostList = getConferenceCost(costExport.getId());
			for(ErpConferenceStaffCost staffCost:staffCostList){
				List<String> entityList = new ArrayList<String>();
				entityList.add(String.valueOf(count));
				entityList.add(costExport.getConferenceNo());
				entityList.add(costExport.getConferenceDate().substring(0,costExport.getConferenceDate().length()-2));//2016-12-24 09:00:00.0
				if(costExport.getCustomerRelationshipProId()!=null){
					CustomerRelationShipPro shipPro = findCustRelShipProById(costExport.getCustomerRelationshipProId());
					entityList.add(shipPro.getProjectCode());
					entityList.add(shipPro.getProjectOwner());
					entityList.add(shipPro.getProjectName());
				}else {
					entityList.add(costExport.getProjectCode());
					entityList.add(costExport.getProjectOwner());
					entityList.add(costExport.getProjectName());
				}

				entityList.add(staffCost.getStaff());
				entityList.add(staffCost.getTravelCost().toString());
				entityList.add(staffCost.getInstructorCost().toString());
				entityList.add(staffCost.getCityTrafficCost().toString());
				entityList.add(staffCost.getProvinceTrafficCost().toString());
				entityList.add(staffCost.getHotelCost().toString());
				entityList.add(staffCost.getSampleCost().toString());
				entityList.add(staffCost.getOtherCost().toString());
				entityList.add(staffCost.getAmount().toString());
				count++;
				result.add(entityList);
			}
		}
		CreateNewExcel.createNewXls(conferenceExcel,fileName,result);
//		return contextUrl+outFilePath.substring(2,outFilePath.length()); //window下
//		logger.info("截取之前的路径："+outFilePath);
//		logger.info("outfilepath截取后的路径："+outFilePath.substring(11,outFilePath.length()));
		return contextUrl+outFilePath.substring(12,outFilePath.length()); //linux下
	}
	
	public CustomerRelationShipPro findCustRelShipProById(String shipProId) {
		return dao.getHibernateTemplate().get(CustomerRelationShipPro.class, shipProId);
	}
	/**
	 * @param page
	 * @param confQuery
	 * @return 用于会场费用导出的查询
	 * @2016年12月7日17:58:45
	 * @author Carly
	 */
	private List<ConferenceCostExport> getConferenceVOList(ConferenceQuery confQuery){
		String sql = "select " +
				"confer.ID id, " +
				"confer.CONFERENCE_NO conferenceNo, " +
				"confer.CONFERENCE_DATE conferenceDate, " +
				"confer.PROVINCE provice, " +
				"confer.CITY city, " +
				"confer.PRO_USER projectOwner, " +
				"confer.PRO_CODE projectCode, " +
				"confer.PRO_BELONG projectName, " +
				"confer.PRODUCE_COST produceCost, " +
				"confer.BRANCH_COMPANY_ID branchCompanyId, " +
				"confer.OWNED_COMPANY_ID ownedCompanyId, " +
				"confer.CUSTOMERRELATIONSHIPPRO_ID customerRelationshipProId " +
				"from ERP_CONFERENCE confer " +
					"LEFT JOIN (select shipPro.id, shipPro.PROJECT_CODE, shipPro.PROJECT_OWNER, shipPro.PROJECT_TYPE, protype.project_type_name typeName from HL_CUSTOMER_RELATIONSHIP_PRO shipPro " +
					"inner join t_project_type protype on protype.id = shipPro.PROJECT_TYPE) shipPro_type ON shipPro_type.id = confer.CUSTOMERRELATIONSHIPPRO_ID " +
					"where confer.IS_DELETED in(0,2) ";

		StringBuilder jdbcSql = new StringBuilder(sql);
		//参数处理
		dealParams(confQuery, jdbcSql);
		jdbcSql.append(" ORDER BY confer.CONFERENCE_DATE DESC ");
//		BeanPropertyRowMapper<ConferenceCostExport> rowMapper = new BeanPropertyRowMapper<ConferenceCostExport>(ConferenceCostExport.class);
//		dao.getJdbcTemplate().query(jdbcSql.toString(),new BeanPropertyRowMapper(ConferenceCostExport.class))
//		return (List<ConferenceCostExport>) dao.findJdbcList(jdbcSql, params, rowMapper);
		return dao.getJdbcTemplate().query(jdbcSql.toString(),new BeanPropertyRowMapper(ConferenceCostExport.class));
	}

	public String exportConferencCostDetail(ConferenceQuery query,HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).toString();
		String fileName = "单项导出费用.xls";
		String outFilePath = conferenceExcel+fileName;
		List<List<String>> result = new ArrayList<List<String>>();
		int count = 1;
		List<ErpConferenceCostDetail> costDetailList = exportCostDetail(query);
		for(ErpConferenceCostDetail costDetail:costDetailList){
			List<String> entityList = new ArrayList<String>();
			entityList.add(String.valueOf(count));
			entityList.add(costDetail.getConferenceNo());
			entityList.add(costDetail.getName());
			entityList.add(FeesTypeList.getBelongName().get(costDetail.getBelong()));
			entityList.add(costDetail.getDays());
			entityList.add(costDetail.getFlight());
			entityList.add(costDetail.getDescripe());
			entityList.add(costDetail.getCost().toString());
			count++;
			result.add(entityList);
		}
		CreateNewExcel.createConferencCostDetailXls(conferenceExcel,fileName,result);
		logger.info("截取之前的路径："+outFilePath);
		logger.info("outfilepath截取后的路径："+outFilePath.substring(2,outFilePath.length()));
//		return contextUrl+outFilePath.substring(2,outFilePath.length()); //window下
		return contextUrl+outFilePath.substring(12,outFilePath.length()); //linux下
	}
	
	/**
	 * @param conferenceNo 会议号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 单项会议费用导出
	 */
	public List<ErpConferenceCostDetail> exportCostDetail(ConferenceQuery query) {
		String sql = "select detail.* from erp_conference_cost_detail detail where 1=1 ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		dealParams2(query, jdbcSql);
		List<ErpConferenceCostDetail> list = dao.getJdbcTemplate().query(jdbcSql.toString(),new BeanPropertyRowMapper(ErpConferenceCostDetail.class));
		return list;
		
	}
	
	private void dealParams2(ConferenceQuery confQuery, StringBuilder jdbcSql) {
		if(confQuery != null) {
			String sql = "and detail.conferenceid in("
					+ "select confer.ID id " +
					"from ERP_CONFERENCE confer " +
						"LEFT JOIN (select shipPro.id, shipPro.PROJECT_CODE, shipPro.PROJECT_OWNER, shipPro.PROJECT_TYPE, protype.project_type_name typeName from HL_CUSTOMER_RELATIONSHIP_PRO shipPro " +
						"inner join t_project_type protype on protype.id = shipPro.PROJECT_TYPE) shipPro_type ON shipPro_type.id = confer.CUSTOMERRELATIONSHIPPRO_ID " +
						"where confer.IS_DELETED in(0,2)";
			jdbcSql.append(sql);
			//支公司名称;
			if(StringUtils.isNotEmpty(confQuery.getBranchCompany())) {
				jdbcSql.append(" AND confer.BRANCH_COMPANY = '").append(confQuery.getBranchCompany()).append("' ");
			}
			//城市;
			if(StringUtils.isNotEmpty(confQuery.getCity())) {
				jdbcSql.append(" AND confer.CITY = '").append(confQuery.getCity()).append("' ");
			}
			//省份
			if(StringUtils.isNotEmpty(confQuery.getProvince())) {
				jdbcSql.append(" AND confer.PROVINCE = '").append(confQuery.getProvince()).append("' ");
			}
			//会场号
			if(StringUtils.isNotEmpty(confQuery.getConferenceNo())) {
				jdbcSql.append(" AND confer.CONFERENCE_NO like '%").append(confQuery.getConferenceNo().trim()).append("%' ");
			}
			//会议类型
			if(StringUtils.isNotEmpty(confQuery.getConferenceType())) {
				jdbcSql.append(" AND confer.CONFERENCE_TYPE = '").append(confQuery.getConferenceType()).append("' ");
			}
			//项目编码
			if(StringUtils.isNotEmpty(confQuery.getProjectCode())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_CODE like '%").append(confQuery.getProjectCode().trim()).append("%' ");
			}
			//项目负责人
			if(StringUtils.isNotEmpty(confQuery.getProjectOwner())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_OWNER like '%").append(confQuery.getProjectOwner().trim()).append("%' ");
			}
			//项目类型;
			if(StringUtils.isNotEmpty(confQuery.getProjectType())) {
				jdbcSql.append(" AND shipPro_type.PROJECT_TYPE = '").append(confQuery.getProjectType()).append("' ");
			}
			//会议时间
			if(StringUtils.isNotEmpty(confQuery.getStartDate())) {
				jdbcSql.append(" AND confer.CONFERENCE_DATE >= to_date('").append(confQuery.getStartDate()).append("', 'yyyy-MM-dd') ");
			}
			if(StringUtils.isNotEmpty(confQuery.getEndDate())) {
				jdbcSql.append(" AND confer.CONFERENCE_DATE < to_date('").append(confQuery.getEndDate()).append("', 'yyyy-MM-dd')+1 ");
			}
			//所属公司;(总公司)
			if(StringUtils.isNotEmpty(confQuery.getOwnedCompany())) {
				jdbcSql.append(" AND confer.OWNED_COMPANY like '%").append(confQuery.getOwnedCompany().trim()).append("%' ");
			}
			if(StringUtils.isNotEmpty(confQuery.getIsExistCost())) {//是否产生费用，PRODUCE_COST>0产生了费用，等于0时没产生费用
				if(confQuery.getIsExistCost().equals("0")){
					jdbcSql.append(" AND confer.PRODUCE_COST =0 ");
				}else if(confQuery.getIsExistCost().equals("1")){
					jdbcSql.append(" AND confer.PRODUCE_COST <> 0 ");
				}
			}
			jdbcSql.append(")");
		}
	}
	
	
	public String getConferenceExcel() {
		return conferenceExcel;
	}

	public void setConferenceExcel(String conferenceExcel) {
		this.conferenceExcel = conferenceExcel;
	}
	
}
