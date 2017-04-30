package org.hpin.events.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.util.Constants;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpComboMap;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 远盟套餐与外部套餐对应关系表DAO
 * Created by admin on 2016/11/9.
 */
@Repository
public class ErpComboMapDao extends BaseDao {

    public List<ErpComboMap> listByProps(Map<String,String> params, boolean isExact) throws Exception{
        List<ErpComboMap> list = null;

        if (!CollectionUtils.isEmpty(params)) {
            Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            Criteria criteria = session.createCriteria(ErpComboMap.class);
            //未删除
            criteria.add(Restrictions.eq(ErpComboMap.F_STATUS, Constants.STATUS_NEW_INT));
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (StringUtils.isNotEmpty(value)) {
                    if (isExact) {
                        criteria.add(Restrictions.eq(key, value));
                    } else {
                        criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
                    }
                }
            }
            list = criteria.list();
        }
        return list;
    }

    public List<ErpComboMap> listByProps(Map<String,String> params) throws Exception{
        return this.listByProps(params, true);
    }

}

