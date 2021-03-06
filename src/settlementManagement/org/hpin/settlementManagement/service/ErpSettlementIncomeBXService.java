package org.hpin.settlementManagement.service;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.XmlUtils;
import org.hpin.settlementManagement.dao.ErpSettlementIncomeBXDao;
import org.hpin.settlementManagement.entity.ErpSettlementIncomeBX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 保险公司结算模块，结算任务中的其他收入表Service
 * @since 17-3-9
 * @author damian
 */
@Transactional
@Service
public class ErpSettlementIncomeBXService extends BaseService{

    @Autowired
    private ErpSettlementIncomeBXDao dao;

    private static String SQL_DEL_IMCOME_BY_ID;

    static {
        try {
            SQL_DEL_IMCOME_BY_ID = XmlUtils.getSingleTxt("sql.xml", "/sql_list/sql[@id='DEL_INCOME_BY_ID']");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量保存
     * @param list
     * @return Integer 保存的数量
     * @author Damian
     * @since 2017-03-10
     */
    public Integer saveOrUpdateList(List<ErpSettlementIncomeBX> list, User user, String taskId) throws Exception{
        Integer count = 0;
        if (!CollectionUtils.isEmpty(list)){
            count = dao.saveOrUpdateList(list, user, taskId);
        }
        return count;
    }

    /**
     * 根据ID或者TASK_ID逻辑删除(is_deleted = 1)
     * @param id id或者taskId之一
     * @param user 操作人信息(可以为NULL)
     * @return Integer 删除数量
     * @throws FileNotFoundException
     * @throws DocumentException
     * @author Damian
     * @since 2017-03-10
     */
    public Integer delIncomeById(String id, User user) throws FileNotFoundException, DocumentException {
        Integer count = 0;
        if (StringUtils.isNotEmpty(id)){
            String[] arr;
            if (id.indexOf(",")>-1){
                arr = id.split(",");
            } else {
                arr = new String[1];
                arr[0] = id;
            }
            String userId;
            String userName;
            if (user==null){
                userId = "1";
                userName = "gene system";
            } else {
                userId = user.getId();
                userName = user.getUserName();
            }
            for (int i = 0; i < arr.length; i++) {
                int num = dao.getJdbcTemplate().update(SQL_DEL_IMCOME_BY_ID, new String[]{userId, userName, arr[i], arr[i]} );
                if (num>0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 根据结算任务ID查找其他收入表的信息
     * @param taskId 结算任务ID，可以传入以逗号分隔的字符串
     * @return List
     * @throws FileNotFoundException
     * @throws DocumentException
     * @author Damian
     * @since 2017-03-10/
     */
    public List<ErpSettlementIncomeBX> findByTaskId(String taskId) throws FileNotFoundException, DocumentException {
        List<ErpSettlementIncomeBX> list = null;
        if (StringUtils.isNotEmpty(taskId)){
            String hql = " from ErpSettlementIncomeBX where isDeleted <> 1 and taskId = ?";
            list = dao.getHibernateTemplate().find(hql, taskId);
        }
        return list;
    }
}
