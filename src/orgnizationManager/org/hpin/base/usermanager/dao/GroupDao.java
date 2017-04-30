package org.hpin.base.usermanager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * 组Dao
 * 
 * @author thinkpad
 */
@Repository()
public class GroupDao extends BaseDao {

	/**
	 * 删除组
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		this
				.getHibernateTemplate()
				.bulkUpdate(
						" update  User set groupId=null,groupName=null where groupId=?",
						id);
		this.getHibernateTemplate().bulkUpdate(" delete from Group where id=?",
				id);
	}

	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		StringBuffer query = new StringBuffer(" from Group");
		List valueList = new ArrayList();
		return super.findByHql(page, query, valueList);
	}
}
