package org.hpin.settlementManagement.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymjy.combo.entity.Combo;
import org.hpin.settlementManagement.dao.ErpComboPriceDao;
import org.hpin.settlementManagement.dao.ErpPrintComboCostDao;
import org.hpin.settlementManagement.entity.ComboHistoryPrice;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpPrintComboCost;
@Service(value = "org.hpin.settlementManagement.service.ErpPrintComboCostService")
@Transactional()
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ErpPrintComboCostService extends BaseService {

	@Autowired
	private ErpPrintComboCostDao dao;
	
    /**
     * 分页获取对象
     * @param page
     * @param searchMap
     * @return
     */
    public List<ErpPrintComboCost> findByPage(Page page, Map searchMap){
    	return dao.findByPage(page, searchMap);
    }

	/**
	 * @param id
	 * @return 更改是否删除的状态
	 */
	public int updateIsDeleted(String id) throws Exception{
		String sql = "update erp_print_combo_cost set isdeleted=1 where id ='"+id+"'";
		return dao.getJdbcTemplate().update(sql);
	}

	/**
	 * @return 打印公司
	 */
	public List getPrintCompany() {
		String sql = "from StoreWarehouse where is_deleted=0";
		return dao.getHibernateTemplate().find(sql);
	}

	/**
	 * @return	打印套餐
	 */
	public List getCombos() {
		String sql = "from Combo where is_delete=0 and print_type='0' order by combo_name";
		return dao.getHibernateTemplate().find(sql);
	}

	/**
	 * @param printCompanyId 打印公司id
	 * @param comboId 打印套餐id
	 * @return 是否有该公司的套餐信息
	 */
	public int getPrintComboCount(String printCompanyId, String comboId) {
		String sql = "select count(1) from erp_print_combo_cost where printcompanyid=? and comboid=? and isdeleted=0";
		return dao.getJdbcTemplate().queryForInt(sql, printCompanyId,comboId);
	}
  
}
