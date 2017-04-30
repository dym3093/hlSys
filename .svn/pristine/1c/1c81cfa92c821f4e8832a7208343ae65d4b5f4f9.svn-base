package org.hpin.genepack.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.genepack.entity.GenepackDelivery;
import org.springframework.stereotype.Repository;
@Repository()
public class GenepackDeliveryDao  extends BaseDao {
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
//		StringBuffer query = new StringBuffer(" from Genepack where isDeleted=0  and isEnable = 1");//没有isEnable
		StringBuffer query = new StringBuffer(" from GenepackDelivery where 1=1 ");
		//searchMap.put(" eventsNo", "desc");
//		searchMap.put("order_updates", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 是否存在已有的基因包
	 * @param eventsNo
	 * @return
	 */
	public String isNotRepeatNo(String deliverybatchno){
		String repeat="";
		String hql = "from GenepackDelivery where deliverybatchno=?";
		List list = this.getHibernateTemplate().find(hql,deliverybatchno);
		if(list.size()>0){
			repeat="基因包号已存在";
		}
		return repeat;
	}
	
	public String del(String id){
		String message="";
		this.delete(GenepackDelivery.class, id);
		
		return message;
	}
	public void modify(GenepackDelivery genepackDelivery){
		this.update(genepackDelivery);
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public GenepackDelivery get(String id){
		return (GenepackDelivery)this.findById(GenepackDelivery.class, id);
	}
	/**
	 * 根据批次号查询
	 * @param eventsNo
	 * @return
	 */
	public GenepackDelivery queryEventNO(String deliverybatchno){
		String hql = "from GenepackDelivery where deliverybatchno=?";
		List list = this.getHibernateTemplate().find(hql,deliverybatchno);
		GenepackDelivery genepackDelivery = (GenepackDelivery) list.get(0);
		return genepackDelivery;
	}

}
