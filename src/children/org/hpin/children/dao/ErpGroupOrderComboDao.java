package org.hpin.children.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.STTabJcImpl;
import org.springframework.stereotype.Repository;

@Repository()
public class ErpGroupOrderComboDao extends BaseDao{

	public List findByPage(Page page, Map<String,Object> searchMap) {
		
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT a.id as companyId, a.branch_commany as company,a.customer_name_simple as customerNameSimple,a.salesman as salesman,"
    			+ "A.province as province,a.citey as city,a.create_time as createtime,length(a.combo)-length(replace(a.combo,','))+1 AS combocount "
    			+ "FROM hl_customer_relationship a where a.combo like '%儿童%' ");
		if (!searchMap.isEmpty()) {
			for(String key:searchMap.keySet()){
				if (isEndLT(key)) {
					sql.append(" and to_char(a."+getSearchMap(key)+",'yyyy-mm-dd')>='"+searchMap.get(key)+"' ");
				}else if (isEndRT(key)) {
					sql.append(" and to_char(a."+getSearchMap(key)+",'yyyy-mm-dd')<='"+searchMap.get(key)+"' ");
				}else {
					if (key.contains("AND")) {
						sql.append( " and a."+getSearchMap(key)+" and='"+searchMap.get(key)+"' ");
					}else if(key.contains("LIKE")){
						sql.append( " and a."+getSearchMap(key)+" like '%"+searchMap.get(key)+"%' ");
					}
				}
			}
			
		}
		return getPageList(page, sql.toString());
	}

	/**
	 * @param company
	 * @param totalCompany
	 * @return 根据公司和总公司得到所有套餐
	 */
	public List<Map<String, Object>> getComboList(String company,String totalCompany) {
		String sql="select a.combo from hl_customer_relationship a where 1=1 and a.branch_commany='"+company+"' and a.customer_name_simple='"+totalCompany+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * @param company
	 * @param totalCompany
	 * @return 获取套餐价格
	 */
	public List<Map<String, Object>> getComboPriceList(String company) {
		String sql="select a.combo,a.GUIDANCE_PRICE,a.SETTLEMENT_PRICE,a.COMBO_NICKNAME,a.PAYED_PRICE from erp_company_combo_price a where 1=1 and a.company='"+company+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public String getCompanyId(String company) {
		String sql = "SELECT id FROM hl_customer_relationship WHERE branch_commany='"+company+"'";
		String result=this.getJdbcTemplate().queryForList(sql).get(0).get("id").toString();
		return result;
	}
	
	public String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	/**
	 * @param company
	 * @param combo
	 * @return 获取儿童套餐信息
	 */
	public List<Map<String, Object>> getChildrenCombo(String company, String combo) {
		String sql = "SELECT a.settlement_price,A.guidance_price,A.payed_price,a.combo_nickname FROM erp_company_combo_price a where a.company='"+company+"' and a.combo='"+combo+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * @param company
	 * @param combo
	 * @return 是否有该套餐
	 */
	public int isExistsCombo(String company, String combo) {
		String sql = "SELECT count(1) FROM erp_company_combo_price  where company='"+company+"' and combo='"+combo+"'";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * 更新儿童套餐信息
	 * @param jsonList
	 * @return
	 */
	public int updateChildrenCombo(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	public int insertChildrenCombo(String sql, Object [] params, int[]types) {
		
		return super.getJdbcTemplate().update(sql, params, types);

	}
}
