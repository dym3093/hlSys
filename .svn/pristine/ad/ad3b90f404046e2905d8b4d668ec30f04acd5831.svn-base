package org.hpin.genepack.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.genepack.entity.Genepack;
import org.springframework.stereotype.Repository;
@Repository()
public class GenepackDao  extends BaseDao {
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
//		StringBuffer query = new StringBuffer(" from Genepack where isDeleted=0  and isEnable = 1");//没有isEnable
		StringBuffer query = new StringBuffer(" from Genepack where 1=1 ");
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
	public String isNotRepeatNo(String batchno){
		String repeat="";
		String hql = "from Genepack where batchno=?";
		List list = this.getHibernateTemplate().find(hql,batchno);
		if(list.size()>0){
			repeat="基因包号已存在";
		}
		return repeat;
	}
	
	/**
	 * 是否存在已有的基因包
	 * @param compannyName 支公司名称 eventDate 基因包日期
	 * @return
	 */
	public String isNotRepeatBycompanyIdAndDate(String compannyName,String ownedCompany,Date eventDate,String address){
		String repeat="";
		String hql = "from Genepack where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 AND address=?";
//		String hql = "from Genepack where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 ";
		System.out.println("校验基因包："+hql);
		List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate,address});
//		List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate});
		if(list.size()>0){
			repeat="该时间已经存在基因包";
		}
		return repeat;
	}
	
	/**
	 * 查询当天最大基因包号
	 * @param date
	 * @return
	 */
	public String maxNo(String date){
		String batchno="";
		String sql = "select max(batchno) as batchno from Genepack where to_char(updates,'yyyy-mm-dd')='"+date+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		Map map = (Map) list.get(0);
		String maxNo = (String) map.get("EVENTS_NO");
		if(StrUtils.isNotNullOrBlank(maxNo)){
			batchno=maxNo.toString();
		}
		return batchno;
	}
	public String del(String id){
		String message="";
		this.delete(Genepack.class, id);
		
		return message;
	}
	public void modify(Genepack genepack){
		this.update(genepack);
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Genepack get(String id){
		return (Genepack)this.findById(Genepack.class, id);
	}
	/**
	 * 根据批次号查询
	 * @param eventsNo
	 * @return
	 */
	public Genepack queryEventNO(String batchno){
		String hql = "from Genepack where batchno=?";
		List list = this.getHibernateTemplate().find(hql,batchno);
		Genepack events = (Genepack) list.get(0);
		return events;
	}

}
