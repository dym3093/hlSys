package org.hpin.foreign.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.util.Tools;
import org.hpin.foreign.entity.ErpReportResultsJY;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报告详情里(ErpReportOrgJy)的results字段(金域报告)DAO
 * @author Damian
 * @since 2017-02-23
 */
@Repository
public class ErpReportResultsJYDao extends BaseDao {

    /**
     * 根据条件查询
     * @param params 传入的Map键值对
     * @param isExact 是否精确查找（只对字符串类型有效，ID为in查找，不用like）
     * @return List
     * @author Damian
     * @since 2017-02-23
     */
    public List<ErpReportResultsJY> listByProps(Map<String,String> params, boolean isExact) throws Exception {
        List<ErpReportResultsJY> list = null;
        Session session;
        Criteria criteria;
        if(!CollectionUtils.isEmpty(params)){
            session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(ErpReportResultsJY.class);

            for (String key : params.keySet()) {
                String value = params.get(key);
                if(isExact){
                    if(value.indexOf(",")!=-1){
                        String[] valArr = value.split(",");
                        //字段为Integer类型
                        if(key.equals(ErpReportResultsJY.F_ISDELETED)
                                || key.equals(ErpReportResultsJY.F_STATUS)){
                            Integer[] numArr = new Integer[valArr.length];
                            for (int i=0; i<valArr.length; i++) {
                                numArr[i] = Integer.valueOf(valArr[i]);
                            }
                            criteria.add(Restrictions.in(key, numArr));
                        } else if (key.equals(ErpReportResultsJY.F_RECORDTIME)
                                    || key.equals(ErpReportResultsJY.F_CHECKTIME)
                                    || key.equals(ErpReportResultsJY.F_AUTHORIZETIME)
                                    || key.equals(ErpReportResultsJY.F_CREATETIME)
                                    || key.equals(ErpReportResultsJY.F_UPDATETIME)){
                            Date[] dateArr = new Date[valArr.length];
                            for (int i=0; i<valArr.length; i++) {
                                dateArr[i] = Tools.parseDateAuto(valArr[i]);
                            }
                            criteria.add(Restrictions.in(key, dateArr));
                        } else {
                            criteria.add(Restrictions.in(key, valArr));
                        }
                    }else{
                        if(key.equalsIgnoreCase(ErpReportResultsJY.F_ISDELETED)
                                || key.equals(ErpReportResultsJY.F_STATUS)){
                            criteria.add(Restrictions.eq(key, Integer.valueOf(value)));
                        } else if (key.equals(ErpReportResultsJY.F_RECORDTIME)
                            || key.equals(ErpReportResultsJY.F_CHECKTIME)
                            || key.equals(ErpReportResultsJY.F_AUTHORIZETIME)
                            || key.equals(ErpReportResultsJY.F_CREATETIME)
                            || key.equals(ErpReportResultsJY.F_UPDATETIME)) {
                            criteria.add(Restrictions.eq(key, Tools.parseDateAuto(value)));
                        }
                        else {
                            criteria.add(Restrictions.eq(key, value));
                        }
                    }
                }else{
                    criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
                }
            }
            list = criteria.list();
        }
        return list;
    }

    /**
     * 根据条件查询
     * @param params 传入的Map键值对
     * @return List
     * @author Damian
     * @since 2017-02-23
     */
    public List<ErpReportResultsJY> listByProps(Map<String,String> params) throws Exception {
        return this.listByProps(params, true);
    }
}
