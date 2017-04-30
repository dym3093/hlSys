package org.hpin.settlementManagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpReceiptInfo;
import org.springframework.stereotype.Repository;


@Repository()
public class ErpReceiptInfoDao extends BaseDao {

	/**
	 * 根据银行流水号获取ReceiptInfo
	 * @param bankStatement
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-29下午5:35:58
	 */
	public List<ErpReceiptInfo> getReceiptInfoByBankStatement(String bankStatement){
		String hql = "from ErpReceiptInfo where isDeleted=0 and BankStatement = ?";
		return this.getHibernateTemplate().find(hql,new Object[]{bankStatement});
	}
	
	/**
	 * 
	 * @param bankStatement
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-31下午2:20:13
	 */
	public boolean judgeBankStatementRepeat(String bankStatement){
		boolean flag = true;
		
		String sql = "select count(1) from erp_receiptinfo rec where ISDELETED = 0 and rec.BANK_STATEMENT=?";
		Integer count = this.getJdbcTemplate().queryForInt(sql, new Object[]{bankStatement});
		if(count!=null&&count==1){	//判断银行流水号是否重复
			flag = false;
		}
		return flag;
	}
	
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from ErpReceiptInfo where 1=1 and isDeleted=0 ");
        searchMap.put("order_createDate", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
	}
	
}
