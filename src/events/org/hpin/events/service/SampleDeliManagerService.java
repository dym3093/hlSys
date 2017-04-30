package org.hpin.events.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.exception.MyException;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PreciseCompute;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ProjectRelationshipDao;
import org.hpin.events.dao.SampleDeliManagerDao;
import org.hpin.events.entity.ProjectRelationship;
import org.hpin.events.entity.SampleDeliManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value="org.hpin.events.service.SampleDeliManagerService")
public class SampleDeliManagerService extends BaseService  {
	
	@Autowired
	private ProjectRelationshipDao projectRelationshipDao; //项目关联;dao
	
	@Autowired
	private SampleDeliManagerDao sampleDeliManagerDao; //样本快递费用dao
	
	/**
	 * 根据样本快递费Id查询对应数据;
	 * @param sampleDeliId 样本快递费ID
	 * @return mapObj ["sampleDeli"样本快递费, "proRels"项目关联]
	 */
	public Map<String, Object> findBySampleDeliId(String sampleDeliId) {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		//查找主数据;
		SampleDeliManager sampleDeli = (SampleDeliManager) sampleDeliManagerDao.findById(SampleDeliManager.class, sampleDeliId);
		mapObj.put("sampleDeli", sampleDeli);
		//查询从数据;
		List<ProjectRelationship> proRels = projectRelationshipDao.findAllBySampleDeliId(sampleDeliId);
		mapObj.put("proRels", proRels);
		
		return mapObj;
	}
	
	/**
	 * 查询分页;
	 */
	@SuppressWarnings("rawtypes")
	public void findSampleDelisByPage(Page page, Map<String, String> paramMap) {
		
		List<Object> lists = new ArrayList<Object>();
		//参数;
		/**/
		StringBuilder jdbcSql = new StringBuilder("select "+
		"sam.ID, "+
		"sam.DELI_NUM deliNum, "+
		"sam.EXPRESS_COMPANY_ID expressCompanyId, "+
		"excom.COMPANY_NAME expressCompanyName, " +
		"sam.SAMPLE_TYPE sampleType, "+
		"sam.RECEIVE_SEND_DATE receliveSendDate, "+
		"sam.ISBILL isBill, "+
		"sam.WEIGHT weight, "+
		"sam.COST_PRICE costPrice "+
		"from ERP_SAMPLE_DELI_MANAGER sam "+
		"left join erp_express_company excom on excom.id = sam.EXPRESS_COMPANY_ID " + 
		"where isdeleted=0 ");
		
		if(paramMap.get("deliNum")!= null && StringUtils.isNotEmpty(paramMap.get("deliNum"))) {
			jdbcSql.append(" and sam.DELI_NUM=? ");
			lists.add(paramMap.get("deliNum").trim());
		}
		
		if(paramMap.get("expressCompanyId")!= null && StringUtils.isNotEmpty(paramMap.get("expressCompanyId"))) {
			jdbcSql.append(" and sam.EXPRESS_COMPANY_ID=? ");
			lists.add(paramMap.get("expressCompanyId"));
		}
		if(paramMap.get("sampleType")!= null && StringUtils.isNotEmpty(paramMap.get("sampleType"))) {
			jdbcSql.append(" and sam.SAMPLE_TYPE=? ");
			lists.add(paramMap.get("sampleType"));
		}
		if(paramMap.get("startDate")!= null && StringUtils.isNotEmpty(paramMap.get("startDate"))) {
			jdbcSql.append(" and sam.RECEIVE_SEND_DATE >= to_date('"+paramMap.get("startDate")+"', 'yyyy-mm-dd') ");
		}
		if(paramMap.get("endDate")!= null && StringUtils.isNotEmpty(paramMap.get("endDate"))) {
			jdbcSql.append(" and sam.RECEIVE_SEND_DATE < to_date('"+paramMap.get("endDate")+"', 'yyyy-mm-dd')+1 ");
		}
		jdbcSql.append(" ORDER BY sam.RECEIVE_SEND_DATE DESC");
		//总数查找
		Long count = sampleDeliManagerDao.findJdbcCount(jdbcSql, lists);
		page.setTotalCount(count);
		
		//分页数据查询;
		lists.add(page.getPageNumEndCount());
		lists.add(page.getPageNumStartCount());
		RowMapper<SampleDeliManager> rowMapper = new BeanPropertyRowMapper<SampleDeliManager>(SampleDeliManager.class);
		List<?> sampleDelis = sampleDeliManagerDao.findJdbcList(jdbcSql, lists, rowMapper);
		page.setResults(sampleDelis);
		
	}
	
	/**
	 * 保存实体对象;
	 * @return
	 */
	public void save(SampleDeliManager sampleDeli, List<ProjectRelationship> proRels) throws Exception {
		//设置基本数据;
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		if(currentUser == null) {
			throw new MyException("error: 当前用户为空值!");
		}
		sampleDeli.setCreateUserId(currentUser.getId());
		sampleDeli.setDeptId(currentUser.getDeptId());
		sampleDeli.setCreateDeptId(currentUser.getDeptId());
		sampleDeli.setCreateTime(new Date());
		sampleDeli.setIsDeleted(0); //默认为0标示有效,1无效;
		
		sampleDeliManagerDao.saveSampleDeli(sampleDeli);
		//获取Id
		String sampleDeliId = sampleDeli.getId();
		
		saveProRels(proRels, currentUser, sampleDeliId, sampleDeli.getCostPrice());
	}

	public void update(SampleDeliManager sampleDeli, List<ProjectRelationship> proRels) throws Exception {
		//设置基本数据;
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		if(currentUser == null) {
			throw new MyException("error: 当前用户为空值!");
		}
		
		//数据库中值;
		SampleDeliManager sampleDeliOld = (SampleDeliManager) sampleDeliManagerDao.findById(SampleDeliManager.class, sampleDeli.getId());
		//将参数sampleDeli数据赋值给old然后在update;
		sampleDeliOld.setUpdateTime(new Date());
		sampleDeliOld.setUpdateUserId(currentUser.getId());
		sampleDeliOld.setCostPrice(sampleDeli.getCostPrice());
		sampleDeliOld.setDeliNum(sampleDeli.getDeliNum());
		sampleDeliOld.setWeight(sampleDeli.getWeight());
		sampleDeliOld.setSampleType(sampleDeli.getSampleType());
		sampleDeliOld.setReceliveSendDate(sampleDeli.getReceliveSendDate());
		sampleDeliOld.setExpressCompanyId(sampleDeli.getExpressCompanyId());
		sampleDeliOld.setIsBill(sampleDeli.getIsBill());
		
		sampleDeliManagerDao.update(sampleDeliOld);
		
		//先删除数据;
		projectRelationshipDao.deleteBySampleDeliId(sampleDeli.getId());
		
		//重新保存;
		saveProRels(proRels, currentUser, sampleDeli.getId(), sampleDeli.getCostPrice());
		
	}
	
	/**
	 * 查找快递公司数据;
	 * @return
	 */
	public List<Map<String, Object>> findErpExprossComnpanys() {
		StringBuilder jdbcSql = new StringBuilder("select ID id, COMPANY_NAME companyName, COMPANY_CODE companyCode from erp_express_company");
		return sampleDeliManagerDao.findJdbcListAll(jdbcSql);
	}
	
	/**
	 * 从字典表中获取快递公司信息
	 */
	public List<Map<String,Object>> findErpExprossComnpanysByDict(){
		StringBuilder jdbcSql = new StringBuilder("select ID id, dictname companyName, dictcode companyCode from SYS_DICTTYPE where parentdictid=(select max(dictid) from SYS_DICTTYPE where dictname='快递公司')");
		return sampleDeliManagerDao.findJdbcListAll(jdbcSql);
	}
	
	/**
	 * 保存项目关联;
	 * @param proRels
	 * @param currentUser
	 * @param sampleDeliId
	 * @param costPrice
	 */
	private void saveProRels(List<ProjectRelationship> proRels,
			User currentUser, String sampleDeliId, BigDecimal costPrice) {
		
		BigDecimal avg = new BigDecimal(0.0); //平均值;
		BigDecimal sumPerson = new BigDecimal(0);
		
		if(proRels != null && proRels.size()> 0) {
			
			for (ProjectRelationship proRel : proRels) {
				if(proRel != null) {
					//所以明细数据人数相加;
					sumPerson = PreciseCompute.add(sumPerson, PreciseCompute.formatComma2BigDecimal(proRel.getPersonNum(), 2));
				}
			}
			
			//当人数总和大于0时,进行计算;
			if(sumPerson.compareTo(new BigDecimal(0)) > 0) {
				avg = PreciseCompute.div(costPrice, sumPerson, 2);
			}
			
			for (ProjectRelationship proRel : proRels) {
				if(proRel != null) {
					//根据平均值在剩行人数; 
					proRel.setPrice(PreciseCompute.mul(avg, PreciseCompute.formatComma2BigDecimal(proRel.getPersonNum(), 2)));
					
					proRel.setSampleDeliManagerId(sampleDeliId);
					proRel.setCreateUserId(currentUser.getId());
					proRel.setDeptId(currentUser.getDeptId());
					proRel.setCreateTime(new Date(0));
					proRel.setIsDeleted(0);
					proRel.setCreateDeptId(currentUser.getDeptId());
					proRel.setCustomerRelationShipId(proRel.getShip().getId());
					proRel.setCustomerRelationShipProId(proRel.getShipPro().getId());
					projectRelationshipDao.save(proRel);
				}
			}
		}
	}

	/**
	 * 删除数据;
	 * @param ids
	 */
	public void deleteSampleDleiManagerIds(String ids) throws Exception {
		if(StringUtils.isNotEmpty(ids)) {
			User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
			if(ids.indexOf(",") > -1) {
				String arr[] = ids.split(",");
				for (String id : arr) {
					sampleDeliManagerDao.deleteSampleDleiManagerIds(id.trim(), currentUser);
					projectRelationshipDao.deletePro(id.trim(), currentUser);
				}
			} else {
				sampleDeliManagerDao.deleteSampleDleiManagerIds(ids.trim(), currentUser);
				projectRelationshipDao.deletePro(ids.trim(), currentUser);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void findBranchComplany(Page page, Map<String, Object> params) {
		String bitchNum = (String) params.get("bitchNum");
		
		String branchCommany = (String) params.get("branchCommany");
		String province = (String) params.get("province");
		String city = (String) params.get("city");
		
		List<Object> lists = new ArrayList<Object>();
		
		StringBuilder jdbcSql = new StringBuilder("select " + 
		"ship.ID id, " +  
		"ship.BRANCH_COMMANY branchCommany, " + 
		"ship.SALESMAN salesman, " + 
		"ship.PROVINCE province,  " + 
		"ship.CITEY city, " + 
		"ship.CUSTOMER_NAME_SIMPLE customerNameSimple " + 
		"from " +
		"HL_CUSTOMER_RELATIONSHIP ship ");
		
		if(StringUtils.isNotEmpty(bitchNum)) {
			
			//去场次查询哈子;如果没有则不加入该数据;
			StringBuilder eventsSql = new StringBuilder("select id from erp_events where BATCHNO like '%"+bitchNum.trim()+"%' ");
			Long couNum = sampleDeliManagerDao.findJdbcCount(eventsSql, new ArrayList<Object>());
			if(couNum > 0) {
				jdbcSql.append("inner join erp_events event on ship.BRANCH_COMMANY = event.BRANCH_COMPANY where 1=1 ");
				jdbcSql.append(" and  event.BATCHNO like '%").append(bitchNum.trim()).append("%' ");				
			}
		}
		
		if(jdbcSql.indexOf("where") == -1){
			jdbcSql.append(" where 1=1 ");
		}
		if(StringUtils.isNotEmpty(branchCommany)) {
			jdbcSql.append(" and  ship.BRANCH_COMMANY like '%").append(branchCommany).append("%' "); 
		}
		
		if(StringUtils.isNotEmpty(province)) {
			jdbcSql.append(" and  ship.PROVINCE='").append(province).append("' ");
		}
		
		if(StringUtils.isNotEmpty(city)) {
			jdbcSql.append(" and  ship.CITEY='").append(city).append("' ");
		}
		
		//count查询
		page.setTotalCount(sampleDeliManagerDao.findJdbcCount(jdbcSql, lists));
		
		//list查询
		lists.add(page.getPageNumEndCount());
		lists.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<CustomerRelationShip> rowMapper = new BeanPropertyRowMapper<CustomerRelationShip>(CustomerRelationShip.class);
		List<?> customerships = sampleDeliManagerDao.findJdbcList(jdbcSql, lists, rowMapper);
		
		page.setResults(customerships);
	}
}
