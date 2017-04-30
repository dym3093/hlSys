package org.hpin.genepack.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.genepack.entity.GenepackDetail;
import org.springframework.stereotype.Repository;
@Repository()
public class GenepackDetailDao  extends BaseDao {
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
//		StringBuffer query = new StringBuffer(" from Genepack where isDeleted=0  and isEnable = 1");//没有isEnable
		StringBuffer query = new StringBuffer(" from GenepackDetail where 1=1 ");
		searchMap.put("filter_and_isDeleted_EQ_I", 0);
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public GenepackDetail get(String id){
		return (GenepackDetail)this.findById(GenepackDetail.class, id);
	}
	/**
	 * 根据批次号查询
	 * @param eventsNo
	 * @return
	 */
	public List getGenepackDetailList(String batchno){
		String hql = "from GenepackDetail where batchno=?";
		List list = this.getHibernateTemplate().find(hql,batchno);
		return list;
	}
	/**
	 * 返回最大ID
	 * 
	 */
	public Integer getMaxIdcount(String batchno) {
		String queryString = "SELECT MAX(idcount) FROM erp_genepack_detail WHERE batchno=?";
		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{batchno});
		return count;
	}
}
