package org.ymjy.combo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymjy.combo.dao.ComboDao;
import org.ymjy.combo.entity.Combo;
@Service(value = "org.ymjy.combo.service.ComboService")
@Transactional()
public class ComboService extends BaseService{
		@Autowired
		private ComboDao dao;
		@Autowired
		private ErpEventsDao erpEventsDao;
		
		/**
		 * 根据条件查询对应的套餐名称
		 * @param page
		 * @param params [comboName: 套餐名称, comboType: 套餐类型]
		 */
		@SuppressWarnings("rawtypes")
		public void findCombosPageByConditions(Page page,
				HashMap<String, String> params) {
			//objs
			List<Object> objs = new ArrayList<Object>();
			//sql
			StringBuilder jdbcSql = new StringBuilder("select  "
				+ "id, "
				+ "combo_name comboName, "
				+ "PROJECT_TYPES projectTypes "
				+ "from  "
				+ "HL_JY_COMBO "
				+ "where IS_DELETE = '0' ");
			//param
			if(params != null) {
				String comboName = params.get("comboName");
				String comboType = params.get("comboType");
				if(StringUtils.isNotEmpty(comboName)) {
					jdbcSql.append(" and combo_name like '%").append(comboName.trim()).append("%' ");
				}
				
				if(StringUtils.isNotEmpty(comboType)) {
					jdbcSql.append(" and PROJECT_TYPES = '").append(comboType).append("' ");
				}
				
				
			}
			
			//排序
			jdbcSql.append(" order by create_time desc ");
			
			//count
			long count = this.dao.findJdbcCount(jdbcSql, objs);
			page.setTotalCount(count);
			
			//list
			objs.add(page.getPageNumEndCount());
			objs.add(page.getPageNumStartCount());
			BeanPropertyRowMapper<Combo> rowMapper = new BeanPropertyRowMapper<Combo>(Combo.class);
			List<?> results = this.dao.findJdbcList(jdbcSql, objs, rowMapper);
			page.setResults(results);
			
		}
		
		public List<Combo> findByIds(String ids){
			
		StringBuffer hql = new StringBuffer();
		hql.append("from Combo c ");
		if(StrUtils.isNotNullOrBlank(ids)){
			hql.append(" where c.id in('"+ids.replaceAll(",", "\',\'")+"')");
		}
		List<Combo> list = dao.findByHql(hql);
		return list;
		}
		
		public Combo findByComboName(String comboName){
			Combo combo = null;
			String hql =" from Combo c where c.comboName = ?";
			List list = dao.getHibernateTemplate().find(hql,comboName);
			if(list!=null&&list.size()>0){
				combo = (Combo) list.get(0);
			}
			return combo;
		}
		
		/**
		 * 根据hl_customer_relationship表中公司的对应套餐信息(有或者没有逗号分割均可)，查找套餐表中的套餐数据
		 * @param comboName
		 * @return List
		 * @author DengYouming
		 * @since 2016-5-5 下午12:17:11
		 */
		public List<Combo> findListByComboName(String comboName) throws Exception{
			List<Combo> list = null;
			if(comboName!=null&&comboName.length()>0){
				list = dao.findListByComboName(comboName);				
			}
			return list;
		}
		
		/**
		 * 通过套餐名称模糊查询对应的数据集合;
		 * @param comboName 套餐名称
		 * @return 套餐集合
		 * @throws Exception
		 * @author henry.xu
		 * @since 20160816
		 */
		public List<Combo> findListByLikeComboName(String comboName, String projectTypes) throws Exception {
			if(StringUtils.isEmpty(comboName)){
				comboName = "";
			}
			//项目类型  add by YoumingDeng 2016-09-26 start 
			if(StringUtils.isEmpty(projectTypes)){
				projectTypes = "";
			}
			//项目类型  add by YoumingDeng 2016-09-26 end 
			
			return dao.findListByLikeComboName(comboName,projectTypes);
			
		}
		
		/**
		 * 根据ID获取对象
		 * 
		 * @param id
		 *            对象ID
		 * @return 对象
		 */
		public Combo findByComboId(String id) {
			return dao.findByComboId(id);
		}

	/**
	 * 根据场次号获取场次,场次中查找支公司对应的项目编码Id;从而查询对应套餐;
	 * @param eventsno
	 * @return
	 */
	public String findComboByEventNo(String eventsno) {
		//根据eventsNo查询
		ErpEvents events=erpEventsDao.queryEventNO(eventsno);
		if(events != null) {
			//根据events对象项目id获取套餐集合;
			String customerRelationShipId = events.getCustomerRelationShipProId();
			return findComboByShipProId(customerRelationShipId);
			
		}
		
		return null;
	}
		
	/**
	 * 根据项目Id获取对应的套餐;
	 * @param id
	 * @return
	 */
	public String findComboByShipProId(String id) {
		StringBuilder toJson = new StringBuilder("[");
		if(StringUtils.isNotEmpty(id)) {
			
			String sql = "select " +
				"combo.id, "+
				"combo.combo_name comboName "+
				"from ERP_RELATIONSHIPPRO_COMBO shipCombo "+
				"inner join HL_JY_COMBO combo on combo.id = shipCombo.COMBO_ID  "+
				"where shipCombo.CUSTOMER_RELATIONSHIP_PRO_ID = '"+id+"'";
			BeanPropertyRowMapper<Combo> rowMapper = new BeanPropertyRowMapper<Combo>(Combo.class);
			List<Combo> combos = this.dao.getJdbcTemplate().query(sql, rowMapper);
			
			if(combos != null && combos.size() > 0) {
				for (Combo combo : combos) {
					toJson.append("{\"text\":\"" + combo.getComboName() + "\",\"id\":\"" + combo.getComboName() + "\",\"leaf\":" + false+"},");
				}
			}
		}
		if (toJson.toString().endsWith(",")) {
			toJson = toJson.delete(toJson.length() - 1, toJson.length());
		}
		toJson.append("]");
		
		return toJson.toString();
		
	}

	/**
	 * 保存项目套餐中间表数据;
	 * create by henry.xu 2016年12月26日
	 * @param id
	 * @param isUsed
	 * @param comboShowName
	 * @param isCreatePdf
	 * @param printType 
	 * @return
	 */
	public boolean saveRelationCombo(String id, String isUsed,
			String comboShowName, String isCreatePdf, Integer printType) {
		String updateSql = "update ERP_RELATIONSHIPPRO_COMBO set IS_USED = ?, COMBO_SHOW_NAME = ?,IS_CREATE_PDF = ?,PRINT_TYPE = ? where id = ?";
		
		int rowNum = this.dao.getJdbcTemplate().update(updateSql, isUsed,comboShowName,isCreatePdf,printType,id);
		return rowNum > 0 ? true : false;
	}
}
