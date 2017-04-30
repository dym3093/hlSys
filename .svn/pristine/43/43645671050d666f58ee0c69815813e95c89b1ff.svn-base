package org.hpin.events.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.region.entity.JYRegion;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.entity.Dept;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.JYCombo;
import org.hpin.settlementManagement.dao.ErpEventsComboPriceDao;
import org.hpin.settlementManagement.entity.ErpEventsComboPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.ymjy.combo.entity.Combo;

@Service(value = "org.hpin.events.service.ErpEventsService")
@Transactional()
public class ErpEventsService extends BaseService {
	@Autowired
	private ErpEventsDao dao;
	@Autowired
	private ErpCustomerDao cDao;
	
	/** 场次套餐价格表Dao */
	@Autowired
	private ErpEventsComboPriceDao erpEventsComboPriceDao;
	
	@Autowired
	private ErpCustomerDao erpCustomerDao;
	
	@Autowired
	private DeptDao deptDao; //add by DengYouming 2016-08-22
	
	/**
	 * 根据场次号获取人数
	 * @param eventsNo
	 * @return
	 */
	public int getNowHeadcountByEventsNo(String eventsNo){
		return cDao.getNowHeadcountByEventsNo(eventsNo);
	}
	/**
	 * 根据场次号获取PDF报告人数
	 * @param eventsNo
	 * @return
	 */
	public int getCustomerPDFNums(String eventsNo){
		return cDao.getCustomerPDFNums(eventsNo);
	}
	/**
	 * 根据场次号获取没有PDF报告人数
	 * @param eventsNo
	 * @return
	 */
	public int getCustomerNOPDFNums(String eventsNo){
		return cDao.getCustomerNOPDFNums(eventsNo);
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
	public List findByPage4(Page page, Map searchMap) {
		return dao.findByPage4(page, searchMap);
	}
	/**
	 * 保存
	 * @param erpEvents
	 */
	public void save(ErpEvents erpEvents){
		dao.save(erpEvents);
	}
	
	public String isNotRepeatNo(String eventsNo){
		return dao.isNotRepeatNo(eventsNo);
	}
	public String isNotRepeatNoByDateAndBannyCompany(String compannyName,String ownedCompany,Date eventDate, String address){
		return dao.isNotRepeatBycompanyIdAndDate(compannyName,ownedCompany,eventDate,address);
	}
	public String maxNo(String date){
		return dao.maxNo(date);
	}

	public void del(String id){
		dao.del(id);
	}
	public ErpEvents get(String id){
		return dao.get(id);
	}
	public void modify(ErpEvents events) throws Exception {
		
		ErpEvents oldErpEvents=get(events.getId());
		//拷贝属性 复制属性
//		ReflectionUtils.copyPropertiesForHasValue(oldErpEvents, events);
		
		oldErpEvents.setEventDate(events.getEventDate());
		oldErpEvents.setAddress(events.getAddress());
		oldErpEvents.setHeadcount(events.getHeadcount());
		oldErpEvents.setBatchNo(events.getBatchNo());
		oldErpEvents.setYmSalesman(events.getYmSalesman());
		oldErpEvents.setLevel2(events.getLevel2());
		oldErpEvents.setComboId(events.getComboId());
		oldErpEvents.setComboName(events.getComboName());
		oldErpEvents.setUpdateTime(events.getUpdateTime());
		oldErpEvents.setUpdateUserName(events.getUpdateUserName());
		oldErpEvents.setBranchCompanyId(events.getBranchCompanyId());
		oldErpEvents.setOwnedCompanyId(events.getOwnedCompanyId());
		oldErpEvents.setCustomerRelationShipProId(events.getCustomerRelationShipProId());
		oldErpEvents.setEventsNo(events.getEventsNo());
		oldErpEvents.setProvice(events.getProvice());
		oldErpEvents.setCity(events.getCity());
		oldErpEvents.setOwnedCompanyId(events.getOwnedCompanyId());
		oldErpEvents.setBranchCompanyId(events.getBranchCompanyId());
		oldErpEvents.setCustomerRelationShipProId(events.getCustomerRelationShipProId());
		oldErpEvents.setComboName(events.getComboName());
		oldErpEvents.setNurseNumber(events.getNurseNumber());
		dao.saveOrUpdate(oldErpEvents);
	}
	
	/*删除或修改*/
	public void delete(List<ErpEvents> list) {
		for(ErpEvents events:list){
			String eventNo = events.getEventsNo();
			String sql = " delete from ERP_CUSTOMER  c where c.EVENTS_NO ='"+eventNo+"'";
			String delExceptionSql = "delete from ERP_CUSTOMER_EXCEPTION e where e.EVENTS_NO ='"+eventNo+"'";
			String [] arrSql = new String []{sql,delExceptionSql};
			cDao.getJdbcTemplate().batchUpdate(arrSql);
			dao.update(events);
		}
	}
	
	public ErpEvents queryEventNO(String eventsNo){
		return dao.queryEventNO(eventsNo);
	}
	
	public ErpEvents queryByEventNO(String eventsNo){
		return dao.queryByEventNO(eventsNo);
	}
	
	/**
	 * 更新
	 * @param 
	 */
	public void updateInfo(ErpEvents events){
		ErpEvents _erpEvents = (ErpEvents) dao.findById(ErpEvents.class, events.getId());
		BeanUtils.copyProperties(_erpEvents, events);
		_erpEvents.setUpdateTime(new Date());
		dao.update(_erpEvents);
	}
	public Integer getCustomerNums(String eventsNo){
		Integer count=dao.getCustomerNums(eventsNo);
		return count;
	}
	public List getAllInputNumsList(String userName){
		List list=dao.getAllInputNumsList(userName);
		return list;
	}
	
	/**
	 * 根据场次号获取对应的统计数据;
	 * @param eventsNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getNumsMapByEventsNo(String eventsNo) {
		String sql = "select "
				+ "events_no,count(*) as total , "
				+ "count(CASE WHEN pdffilepath IS NOT NULL THEN 1 END) AS pdftotal, "
				+ "count(CASE WHEN pdffilepath IS  NULL THEN 1 END) AS nopdftotal "
				+ "from ERP_CUSTOMER "
				+ "where "
				+ "is_deleted=0 "
				+ "and events_no=? "
				+ "group by events_no";
		List<?> list = this.dao.getJdbcTemplate().queryForList(sql, eventsNo);
		return  list!= null && list.size()> 0 ? (Map<String, Object>)list.get(0): null;
	}
	
	/**
	 * 列出所有未添加到结算任务的场次
	 * @return List<ErpEvents>
	 * @author DengYouming
	 * @since 2016-5-2 上午12:11:03
	 */
	public List<ErpEvents> listEventsNotInSettle(Page page, Map searchMap){
		List<ErpEvents> list = null;
		list = dao.findByPage(page, searchMap);
		return list;
	}
	
	/**
	 * 根据条件查询相关场次
	 * @param params 传入的条件
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-18 上午11:32:03
	 */
	public List<ErpEvents> listEventsByProps(Map<String,String> params) throws Exception{
		List<ErpEvents> list = null;
		if(params!=null&&params.keySet().size()>0){
			list = dao.listEventsByProps(params);
		}
		return list;
	}
	
	/**
	 * 获取场次中的所有套餐名称
	 * @param params 参数
	 * @return List 结果集
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-13 上午11:26:19
	 */
	public List<String> listEventsComboPrice(Map<String,Object> params) throws Exception{
		List<String> comboList = null;
		//传入场次号查找
		List<ErpCustomer> erpCustomers = erpCustomerDao.listCustomerCanAddSettle(params);
		if(!CollectionUtils.isEmpty(erpCustomers)){
			comboList = new ArrayList<String>();
			for (ErpCustomer erpCustomer : erpCustomers) {
				String comboName = erpCustomer.getSetmealName();
				if(!comboList.contains(comboName)){
					comboList.add(comboName);
				}
			}
		}
		return comboList;
	}
	
	/**
	 * 获取场次套餐价格
	 * @param params 参数
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-13 下午5:42:03
	 */
	public List<ErpEventsComboPrice> findEventsComboPrice(Map<String,Object> params) throws Exception{
		List<ErpEventsComboPrice> list = null;
		if(!CollectionUtils.isEmpty(params)){
			list = erpEventsComboPriceDao.findEventsComboPriceByProps(params);
		}
		//如果没有设置场次套餐价格
		if(CollectionUtils.isEmpty(list)){
			//根据场次号查询场次中所有套餐			
			String eventsNo = (String) params.get(ErpEventsComboPrice.F_EVENTSNO);
			if(StringUtils.isNotEmpty(eventsNo)){
				List<String> comboList = erpCustomerDao.listComboNameByEventsNo(eventsNo);
				if(!CollectionUtils.isEmpty(comboList)){
					ErpEventsComboPrice eventsComboPrice = null;
					list = new ArrayList<ErpEventsComboPrice>();
					for (String comboName : comboList) {
						//获取套餐名称
						eventsComboPrice = new ErpEventsComboPrice();
						eventsComboPrice.setCombo(comboName);
						eventsComboPrice.setEventsNo(eventsNo);
						list.add(eventsComboPrice);
					}
				}else{
					//仍未找到场次套餐，则直接从场次信息中查找
					List<ErpEvents> eventsList = dao.findByProperty(ErpEvents.class, ErpEvents.F_EVENTSNO, eventsNo, ErpEvents.F_CREATETIME, true);
					if(!CollectionUtils.isEmpty(eventsList)){
						ErpEvents resErpEvents = eventsList.get(0);
						ErpEventsComboPrice eventsComboPrice = new ErpEventsComboPrice();
						eventsComboPrice.setCombo(resErpEvents.getComboName());
						eventsComboPrice.setEventsNo(resErpEvents.getEventsNo());
						list.add(eventsComboPrice);
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * @return 金域套餐
	 */
	public List<JYCombo> getJYCombo() {
		String hql="from JYCombo where is_Deleted=0 order by combo_name ";
		List<JYCombo> combo = dao.getHibernateTemplate().find(hql);
		return combo;
	}
	
	/**
	 * @param combo
	 * @return 套餐的code
	 */
	public String getComboCode(String combo) {
		StringBuilder comboArray = new StringBuilder();
		String sql = "select code from jy_cancer_combo_info where comboid=(select id from jy_combo where combo_name='"+combo+"')";
		List<Map<String, Object>> codelList = dao.getJdbcTemplate().queryForList(sql);
		for (Map<String,Object> map :codelList) {
			comboArray.append(map.get("CODE")+",");
		}
		if (comboArray.toString().endsWith(",")) {
			comboArray = comboArray.delete(comboArray.length() - 1, comboArray.length());
		}
		return comboArray.toString();
	}
	
	public List<JYRegion> getCityName(String id) {
		String sql ="from JYRegion where cityid='"+id+"'";
		return dao.getHibernateTemplate().find(sql);
	}
	
	/**
	 * @param comboName
	 * @return 金域套餐
	 */
	public JYCombo getJyCombo(String comboName) {
		String sql = "from JYCombo where combo = ? and is_deleted = '0'";
		return (JYCombo)dao.getHibernateTemplate().find(sql,comboName).get(0);
	}
	
	/**
	 * @param parameter
	 * @return 获取级别的名称
	 */
	public String getLevelName(String parameter) {
		String sql = "select dictname from sys_dicttype where dictid='"+parameter+"' and parentdictid='10103'";
		return dao.getJdbcTemplate().queryForList(sql).get(0).get("DICTNAME").toString();
	}
	
//	public String getCityId(String province) {
//		String sql ="select code from jy_city_info where cityid='"+province+"'";
//		return String.valueOf(dao.getJdbcTemplate().queryForInt(sql));
//	}
	
	public ErpEvents getEventsById(String eventsId){
		return dao.getEventsById(eventsId);
	}
	
	public Dept findDeptByName(String deptName){
		return deptDao.findByDeptName(deptName);
	}
	/**
	 * @param eventsNo
	 * @return 通过场次号获取hl_customer_relationship_pro的id
	 */
	public ErpEvents getCompanyPro(String eventsNo) {
		String eventSql = "from ErpEvents where events_no=?";
		return (ErpEvents) dao.getHibernateTemplate().find(eventSql,eventsNo).get(0);
	}
	
	/**
	 * @return 获取支公司癌筛套餐
	 */
	public List<Combo> getCancerCombo() {
		String sql = "from Combo where is_delete=0 and project_types ='PCT_002'";
		return dao.getHibernateTemplate().find(sql);
	}

	/**
	 * 可以根据
	 * 1）info: 会员姓名 , infoType: name
	 * 2）info: 会员条码, infoType: code
	 * 3）info: 批次号,  infoType: batchNo
	 * 4）info: 场次号,  infoType: eventsNo
	 * 5）info: 团单号 ,  infoType: groupOrderNo
	 * 等信息，查找到相关场次
	 * @param info 传入的信息
	 * @param infoType 信息类型
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-10-25 下午5:03:39
	 */
	public List<ErpEvents> listEventsByInfo(String info, String infoType)throws Exception{
		List<ErpEvents> list = null;
		if(StringUtils.isNotEmpty(info)&&StringUtils.isNotEmpty(infoType)){
			list = dao.listEventsByInfo(info,infoType);
		}
		return list;
	}


}
