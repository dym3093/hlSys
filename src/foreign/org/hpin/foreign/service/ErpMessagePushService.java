package org.hpin.foreign.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.XmlUtils;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.foreign.dao.ErpMessagePushDao;
import org.hpin.foreign.entity.ErpMessagePush;
import org.hpin.reportdetail.dao.ErpReportExpressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 消息推送表Service
 * Created by Damian on 17-1-1.
 */
@Service("org.hpin.foreign.service.ErpMessagePushService")
@Transactional
public class ErpMessagePushService extends BaseService {

    private static Logger log = Logger.getLogger("ErpMessagePushService");

    @Autowired
    private ErpCustomerDao customerDao;

    @Autowired
    private ErpReportExpressDao reportExpressDao;//报告寄送

    //星宁基因获取符合条件的客户信息
    private static String FIND_EXPRESS_INFO;

    static {
        try {
            FIND_EXPRESS_INFO = XmlUtils.getSingleTxt("sql","/sql_list/sql[@id='FIND_EXPRESS_INFO']");
            log.info("FIND_EXPRESS_INFO: "+ FIND_EXPRESS_INFO);
        } catch (FileNotFoundException e) {
            log.info(e);
        } catch (DocumentException e) {
            log.info(e);
        }
    }

    @Autowired
    private ErpMessagePushDao dao;

    /**
     * 根据条件是否严格查找
     * @param params 条件集合
     * @param isExact true:equal查找，false:like查找
     * @return List
     */
    public List<ErpMessagePush> listByProps(Map<String,String> params, boolean isExact) throws Exception {
        return dao.listByProps(params,isExact);
    }


    /**
     * 根据条件严格查找
     * @param params 条件集合
     * @return List
     */
    public List<ErpMessagePush> listByProps(Map<String,String> params) throws Exception{
        return dao.listByProps(params);
    }

    /**
     * 把太平微磁相关的信息保存到消息推送表中
     * @param projCode 项目编号
     * @param mark 渠道标识
     * @return int 保存的数量
     * @throws Exception
     * @author Damian
     * @since 2017-02-17
     */
    public int savePushInfoTPWC(String projCode, String mark) throws Exception {
        int count = 0;
        List<Map<String,Object>> list = dao.findPushInfoTPWC(projCode, mark);
        if (!CollectionUtils.isEmpty(list)){
            List<ErpMessagePush> msgList = this.infoTPWCToMsg(list);
            if (!CollectionUtils.isEmpty(msgList)){
                count = this.saveList(msgList);
            }
        }
        return count;
    }

    /**
     * 转换成消息List
     * @param list Map
     * @return List
     * @author Damian
     * @since 2017-02-17
     */
    private List<ErpMessagePush> infoTPWCToMsg(List<Map<String,Object>> list){
        List<ErpMessagePush> msgList = null;
        if (!CollectionUtils.isEmpty(list)){
            msgList = new ArrayList<ErpMessagePush>();
            for (Map<String,Object> item:list){
                ErpMessagePush obj = this.mapToEntityTPWC(item);
                if (obj!=null){
                    msgList.add(obj);
                }
            }
        }
        return msgList;
    }

    /**
     * 根据Map组装对象
     * @param map
     * @return ErpMessagePush
     * @author Damian
     * @since 2017-02-17
     */
    private ErpMessagePush mapToEntityTPWC(Map<String,Object> map) {
        ErpMessagePush obj = null;
        if (!CollectionUtils.isEmpty(map)){
            obj = new ErpMessagePush();

            obj.setCode((String) map.get("code"));
            obj.setName((String) map.get("name"));
            obj.setPhone((String) map.get("phone"));
            obj.setPushName("太平无创");
            obj.setKeyWord("TPWC");

            obj.setMessage(mapToMsgTPWC(map));
            obj.setStatusYm(300);
            obj.setStatus(0); //未发送
            obj.setCounter(0);
            obj.setCreateUserName("ymjy");//创建人为web项目名称
            obj.setCreateUserId("-1");

            obj.setCreateTime(Calendar.getInstance().getTime());
        }
        return obj;
    }

    /**
     * 根据Map组装XML格式
     * @param map
     * @return XML格式的字符串
     * @author Damian
     * @since 2017-02-17
     */
    private String mapToMsgTPWC(Map<String,Object> map){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<message>"
                + "<barCode>"+map.get("code")+"</barCode>"
                + "<name>"+map.get("name")+"</name>"
                + "<phone>"+map.get("phone")+"</phone>"
                + "<status>300</status>"
                + "<expressNo></expressNo>"
                + "<salesManPhone>"+map.get("employee_phone")+"</salesManPhone>"
                + "<salesChannel>"+map.get("mark")+"</salesChannel>"
                + "</message>";
    }

    /**
     * 批量保存
     * @param list
     * @return 保存的数量
     * @throws Exception
     * @author Damian
     * @since 2017-02-17
     */
    public int saveList(List<ErpMessagePush> list) throws Exception{
        return dao.saveList(list);
    }

    /**
     * 更新推送消息的状态
     * @param msg 消息
     * @param status 状态直
     * @return boolean true:更新成功 ; false:更新失败
     * @author Damian
     * @since 2017-04-06 15:07
     */
    public boolean updateStatus(ErpMessagePush msg, int status){
        boolean flag = false;
        if (msg!=null) {
            msg.setUpdateTime(Calendar.getInstance().getTime());
            msg.setUpdateUserName("websGene");
            msg.setStatus(status);
            dao.update(msg);
            flag = true;
        }
        return flag;
    }

    /**
     *
     * @return String
     */
    public String savePushMsg(Map<String,String> params){
        String result;
        int count = 0;
        //星宁基因
        String hqlXN = " from ErpCustomer where isDeleted = 0 and branchCompanyId = 'ff8080815b133915015b136e40880958' and branchCompany = '星宁之家' " +
                     " and statusYm in (110, 150, 200, 300, 600) ";
        List<ErpCustomer> customerListXN = customerDao.getHibernateTemplate().find(hqlXN);
        if (!CollectionUtils.isEmpty(customerListXN)){
            log.info("星宁基因客户信息数量: "+customerListXN.size());
            for (ErpCustomer obj: customerListXN){
                count += this.savePushMsgXN(obj);
            }
        }else{
            log.info("本次没有要保存到消息推送表的星宁基因客户信息！！！");
        }
        log.info("本次保存到消息推送表的客户信息数量： "+count);
        result = ""+count;
        return result;
    }

    /**
     *
     * @param obj
     * @return int 保存的数量
     * @author Damian
     * @since 2017-04-17
     */
    private int savePushMsgXN(ErpCustomer obj){
        int i = 0;
        if (obj!=null){
            String pushMsg = this.entityToMsgXN(obj);
            log.info("生成的推送消息： "+pushMsg);
            if (StringUtils.isNotEmpty(pushMsg)) {
                StringBuilder checkMsgBld = new StringBuilder(" SELECT count(1) FROM ERP_MESSAGE_PUSH WHERE 1=1 AND STATUS <> -1 ");
                if (StringUtils.isNotEmpty(obj.getCode())){
                    checkMsgBld.append(" AND CODE = '"+obj.getCode()+"' ");
                }
                if (StringUtils.isNotEmpty(obj.getName())){
                    checkMsgBld.append(" AND NAME = '"+obj.getName()+"'");
                }
                if (StringUtils.isNotEmpty(obj.getEventsNo())){
                    checkMsgBld.append(" AND EVENTS_NO = '"+obj.getEventsNo()+"'");
                }
                checkMsgBld.append(" AND STATUS_YM = " + obj.getStatusYm() );
                checkMsgBld.append(" AND dbms_lob.substr(MESSAGE) = '"+pushMsg+"'");

                //check
                log.info("sqlCheckMsg： "+ checkMsgBld.toString());
                int num = dao.getJdbcTemplate().queryForInt(checkMsgBld.toString());
                if (num==0){
                    ErpMessagePush messagePush = this.customerToEntityXN(obj, pushMsg);
                    if (messagePush!=null){
                        dao.save(messagePush);
                        i = 1;
                    }
                }else{
                    log.info("ERP_MESSAGE_PUSH表中已经存在该数据： [name="+obj.getName()+", code="+obj.getCode()
                                        +", eventsNo="+obj.getEventsNo()+", statusYm="+obj.getStatusYm()+"]");
                }
            }
        }
        return i;
    }

    /**
     * 转换成推送消息
     * @param obj ErpCustomer
     * @return String
     * @author Damian
     * @since 2017-04-17
     */
    private String entityToMsgXN(ErpCustomer obj){
        int statusYm = obj.getStatusYm();
        String expressNo = "";
        if (statusYm==600){
            Object[] queryArr = new Object[3];
            queryArr[0] = obj.getCode();
            queryArr[1] = obj.getName();
            queryArr[2] = obj.getEventsNo();
            List<Map<String,Object>> expressList = reportExpressDao.getJdbcTemplate().queryForList(FIND_EXPRESS_INFO, queryArr);
            if (CollectionUtils.isEmpty(expressList)){
                Map<String,Object> expressMap = expressList.get(0);
                expressNo = (String) expressMap.get("expressNo");
            }
        }
        String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<reqStatus>"
                    + "<barCode>"+obj.getCode()+"</barCode>"
                    + "<name>"+obj.getName()+"</name>"
                    + "<phone>"+obj.getPhone()+"</phone>"
                    + "<status>"+statusYm+"</status>"
                    + "<expressNo>"+expressNo+"</expressNo>"
                + "</reqStatus>";
        log.info("entityToMsgXN msg: " + msg);
        return msg;
    }

    /**
     * 星宁基因对象
     * @param customer
     * @param pushMsg
     * @return ErpMessagePush
     * @author Damian
     * @since 2017-04-17
     */
    private ErpMessagePush customerToEntityXN(ErpCustomer customer, String pushMsg){
        ErpMessagePush obj = null;
        if (customer!=null&&StringUtils.isNotEmpty(pushMsg)){
            obj = new ErpMessagePush();

            obj.setCode(customer.getCode());
            obj.setName(customer.getName());
            obj.setEventsNo(customer.getEventsNo());
            obj.setStatusYm(customer.getStatusYm());
            obj.setMessage(pushMsg);

            obj.setPhone(customer.getPhone());
            obj.setPushName("星宁基因");
            obj.setKeyWord("XN");
            obj.setStatus(0); //未发送
            obj.setCounter(0);

            obj.setCreateUserName("ymjy");//创建人为web项目名称
            obj.setCreateUserId("-1");//不存在的ID
            obj.setCreateTime(Calendar.getInstance().getTime());
        }
        return obj;
    }
}
