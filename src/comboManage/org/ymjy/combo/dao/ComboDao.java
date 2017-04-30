package org.ymjy.combo.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.base.dict.exceptions.DictDAOException;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.util.StrUtils;
import org.springframework.stereotype.Repository;
import org.ymjy.combo.entity.Combo;
@Repository(value="org.ymjy.combo.dao.ComboDao")
public class ComboDao extends BaseDao implements ID2NameDAO{
	
	
	/**
	 * 根据套餐名获取套餐
	 * @return
	 * @author tangxing
	 * @date 2016-11-15下午4:57:12
	 */
	public List<Combo> getComboByName(String comboName,String comboId){
		String sql = "from Combo where comboName=? and id = ? and isDelete=0 ";
		return this.getHibernateTemplate().find(sql,comboName,comboId);
	}

	/**
	 * 导入前查询id
	 * @param comboName
	 * @return
	 */
	public String queryId(String comboName){
		String comboId="";
		StringBuffer hql = new StringBuffer(" from Combo where comboName=?");
		List list = this.getHibernateTemplate().find(hql.toString(), comboName);
		if(list.size()>0){
			Combo combo=(Combo) list.get(0);
			comboId = combo.getId();
		}
		return comboId;
	}
	public Combo findByElementsId(String comboId){
		String query = " from Combo where id = ?";
		List<Combo> list = super.getHibernateTemplate().find(query , comboId);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String id2Name(String id) throws DictDAOException {
		String comboName = "";
		if(StrUtils.isNotNullOrBlank(id)){
			String queryString = " from Combo where id=?";
			List<Combo> list = this.getHibernateTemplate().find(queryString,id);
			if( list != null && list.size() >0){
				Combo combo = list.get(0);
				comboName = combo.getComboName();
			}
		}
		System.out.println(comboName);
		return comboName;
	}
	@Override
	public String id2Field(String id, String beanId, String field) throws DictDAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据hl_customer_relationship表中公司的对应套餐信息(有或者没有逗号分割均可)，查找套餐表中的套餐数据
	 * @param comboName
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-5 下午12:29:19
	 */
	public List<Combo> findListByComboName(String comboName) throws Exception{
		List<Combo> list = null;
		if(StringUtils.isNotEmpty(comboName)){
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Transaction tx = session.getTransaction();
			tx.begin();
			Criteria crit = session.createCriteria(Combo.class);
			//有逗号分隔
			if(comboName.indexOf(",")!=-1){
				crit.add(Restrictions.in("comboName", comboName.split(",")));
			}else{
				crit.add(Restrictions.in("comboName", new String[]{comboName}));
			}
			crit.addOrder(Order.desc("comboName"));
			list = crit.list();
			tx.commit();
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
	@SuppressWarnings("unchecked")
	public List<Combo> findListByLikeComboName(String comboName, String projectTypes) throws Exception{
		StringBuffer hql = new StringBuffer("from Combo where comboName like '%"+comboName.trim()+"%' and projectTypes like '%"+projectTypes.trim()+"%' and isDelete = 0 order by createTime desc");
		return findByHql(hql);
	}
	
	public Combo findByComboId(String id) {
		StringBuffer hql = new StringBuffer("from Combo where id = ? ");
		@SuppressWarnings("unchecked")
		List<Combo> list = findByHql(hql, id);
		return list !=null && list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据套餐ID查找套餐名
	 * @param comboId
	 * @return String
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-6 下午2:14:52
	 */
	public String findComboNameById(String comboId) throws Exception{
		String comboName = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Criteria crit = session.createCriteria(Combo.class);
		List<Combo> list = crit.add(Restrictions.eq("id", comboId)).list();
		if(list!=null&&list.size()>0){
			comboName = list.get(0).getComboName();
		}
		tx.commit();
		return comboName;
	}

}
