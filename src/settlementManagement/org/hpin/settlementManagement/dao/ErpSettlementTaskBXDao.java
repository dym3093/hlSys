/**
 * dym
 * 2016-4-26 下午2:36:30
 */
package org.hpin.settlementManagement.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.util.Constants;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpCustomerSettleBX;
import org.hpin.settlementManagement.entity.ErpSettlementTaskBX;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 保险公司结算任务DAO
 * @author DengYouming
 * @since 2016-5-1 下午4:25:33
 */
@Repository()
public class ErpSettlementTaskBXDao extends BaseDao{

	private static final String SQL_FIND_EVENTS_HEAD_QUERY = " SELECT * FROM (\n";

	private static final String SQL_FIND_EVENTS_HEAD_COUNT = " SELECT COUNT(1) FROM (\n";

	private static final String SQL_FIND_EVENTS_BODY = " \tSELECT\n" +
			"    t.ID AS id,\n" +
			"    t.BATCHNO AS batchNO,\n" +
			"    t.EVENTS_NO AS eventsNo,\n" +
			"    t.EVENT_DATE AS eventDate,\n" +
			"    t.PROVINCE AS provice,\n" +
			"    t.CITY AS city,\n" +
			"    t.BRANCH_COMPANY_ID AS branchCompanyId,\n" +
			"    t.OWNED_COMPANY_ID AS ownedCompanyId,\n" +
			"    t.COMBO_NAME AS comboName,\n" +
			"    t.LEVEL2 AS level2,\n" +
			"    t.HEADCOUNT AS headcount,\n" +
			"    ( SELECT COUNT( 1 ) FROM ERP_CUSTOMER a WHERE a.IS_DELETED = 0 AND a.EVENTS_NO = t.EVENTS_NO) AS nowHeadcount,\n" +
			"    ( SELECT COUNT( 1 ) FROM ERP_CUSTOMER b WHERE b.IS_DELETED = 0 AND b.EVENTS_NO = t.EVENTS_NO AND LENGTH(b.PDFFILEPATH)> 0) AS pdfcount,\n" +
			"    ( SELECT COUNT( 1 ) FROM ERP_CUSTOMER c WHERE c.IS_DELETED = 0 AND c.EVENTS_NO = t.EVENTS_NO AND c.PDFFILEPATH IS NULL) AS nopdfcount,\n" +
			"    t.YM_SALESMAN AS ymSalesman,\n" +
			"    t.STATUS_BX AS statusBX,\n" +
			"    t.CREATE_TIME AS createTime,\n" +
			"    rownum rn\n" +
			" FROM \n" +
			"    ( SELECT e.* FROM ERP_EVENTS e WHERE 1 = 1 AND e.IS_DELETED = 0 AND e.CUSTOMERRELATIONSHIPPRO_ID \n" +
			"        IN ( SELECT p.ID FROM HL_CUSTOMER_RELATIONSHIP_PRO p WHERE 1 = 1 AND p.IS_DELETED = 0" +
//			"		 AND p.IS_SEAL = 0" +
			"		 AND p.PROJECT_CODE = ? \n";
//			"		 AND ( p.PROJECT_OWNER = ? OR p.LINK_NAME = ? ) )\n" +
//			"    ) t WHERE 1 = 1  ";

	//销售人员筛选条件
	private static final String SQL_FIND_EVENTS_BODY_SALEMAN = "	AND ( p.PROJECT_OWNER = ? OR p.LINK_NAME = ? ) )\n" + " ) t WHERE 1 = 1  ";

	//管理员不添加筛选条件
	private static final String SQL_FIND_EVENTS_BODY_ADMIN = " ) ) t WHERE 1 = 1  ";

	private static final String SQL_FIND_EVENTS_TAIL_QUERY = " AND  rownum <= ? ) WHERE rn >= ? ORDER BY createTime DESC ";

	private static final String SQL_FIND_EVENTS_TAIL_COUNT = " ) ";

	private static final String SQL_FIND_CUSTOMER_HEAD_QUERY = " SELECT * FROM ( ";

	private static final String SQL_FIND_CUSTOMER_HEAD_COUNT = " SELECT COUNT(1) FROM ( ";

	//销售人员
	private static final String SQL_FIND_CUSTOMER_BODY_SALEMAN = " SELECT \n" +
			"    t.ID AS id,  \n" +
			"    (SELECT BATCHNO FROM ERP_EVENTS WHERE IS_DELETED = 0 AND EVENTS_NO = t.EVENTS_NO) AS batchNo, \n" +
			"    t.EVENTS_NO AS eventsNo, \n" +
			"    t.CODE AS code, \n" +
			"    t.NAME AS name, \n" +
			"    t.SEX AS sex, \n" +
			"    t.AGE AS age, \n" +
			"    t.IDNO AS idno, \n" +
			"    t.PHONE AS phone, \n" +
			"    t.BRANCH_COMPANY_ID AS branchCompanyId, \n" +
			"    t.SETMEAL_NAME AS setmealName, \n" +
			"    t.YM_SALESMAN AS ymSalesman, \n" +
			"    t.SAMPLING_DATE AS samplingDate, \n" +
			"    t.NOTE AS note, \n" +
			"    t.STATUS AS status, \n" +
			"    t.PDFFILEPATH AS pdffile, \n" +
			"    t.CREATE_TIME AS createTime, \n" +
			"    rownum rn \n" +
			"	FROM \n" +
			"    ( SELECT m.* FROM ERP_CUSTOMER m WHERE 1 = 1 \n" +
			"            AND m.EVENTS_NO\n" +
			"            IN ( SELECT EVENTS_NO FROM ERP_EVENTS WHERE IS_DELETED = 0 AND CUSTOMERRELATIONSHIPPRO_ID IN(" +
			"				 SELECT p.ID FROM HL_CUSTOMER_RELATIONSHIP_PRO p WHERE 1 = 1 AND p.IS_DELETED = 0 "+
//			"			 AND p.IS_SEAL = 0 "
			"			 AND p.PROJECT_CODE = ? \n" +
			"			 AND ( p.PROJECT_OWNER = ? OR p.LINK_NAME = ? ) ) ) \n" +
			"            AND m.IS_DELETED = 0  \n" +
			"			 AND m.status = '0' \n" +
			"            AND LENGTH(m.PDFFILEPATH) > 0 ) t \n" +
			" WHERE 1=1 ";

	//管理员
	private static final String SQL_FIND_CUSTOMER_BODY_ADMIN = " SELECT \n" +
			"    t.ID AS id,  \n" +
			"    (SELECT BATCHNO FROM ERP_EVENTS WHERE IS_DELETED = 0 AND EVENTS_NO = t.EVENTS_NO) AS batchNo, \n" +
			"    t.EVENTS_NO AS eventsNo, \n" +
			"    t.CODE AS code, \n" +
			"    t.NAME AS name, \n" +
			"    t.SEX AS sex, \n" +
			"    t.AGE AS age, \n" +
			"    t.IDNO AS idno, \n" +
			"    t.PHONE AS phone, \n" +
			"    t.BRANCH_COMPANY_ID AS branchCompanyId, \n" +
			"    t.SETMEAL_NAME AS setmealName, \n" +
			"    t.YM_SALESMAN AS ymSalesman, \n" +
			"    t.SAMPLING_DATE AS samplingDate, \n" +
			"    t.NOTE AS note, \n" +
			"    t.STATUS AS status, \n" +
			"    t.PDFFILEPATH AS pdffile, \n" +
			"    t.CREATE_TIME AS createTime, \n" +
			"    rownum rn \n" +
			"	FROM \n" +
			"    ( SELECT m.* FROM ERP_CUSTOMER m WHERE 1 = 1 \n" +
			"            AND m.EVENTS_NO\n" +
			"            IN ( SELECT EVENTS_NO FROM ERP_EVENTS WHERE IS_DELETED = 0 AND CUSTOMERRELATIONSHIPPRO_ID IN(" +
			"				 SELECT p.ID FROM HL_CUSTOMER_RELATIONSHIP_PRO p WHERE 1 = 1 AND p.IS_DELETED = 0 "+
//			"			 AND p.IS_SEAL = 0 "
			"			 AND p.PROJECT_CODE = ? ) ) \n" +
			"            AND m.IS_DELETED = 0  \n" +
			"			 AND m.status = '0' \n" +
			"            AND LENGTH(m.PDFFILEPATH) > 0 ) t \n" +
			" WHERE 1=1 ";

	private static final String SQL_FIND_CUSTOMER_TAIL_QUERY = " AND rownum <= ? )  WHERE rn >= ? ORDER BY createTime DESC ";

	private static final String SQL_FIND_CUSTOMER_TAIL_COUNT = " ) ";

	public Session getCurrentSession() throws Exception{
		Session session = null;
		if(session!=null){
			getHibernateTemplate().getSessionFactory().getCurrentSession();
		}
		return session;
	}

	/**
	 * 根据参数修改计算任务转态;
	 * create by henry.xu
	 * @param params (status结算状态0未收款1代收款2已收款, )
	 * @throws Exception
	 */
	public void updateSettlementTaskStatus(Map<String, Object> params) throws Exception {
		//修改语句;
		StringBuilder stbUpdate = new StringBuilder("UPDATE ERP_SETTLEMENT_TASK_BX set status=? WHERE ID=?");
		//参数
		Object[] obj = new Object[2];
		if(params != null) {
			obj[0] = params.get("status");
			obj[1] = params.get("settlementId");
		}
		
		this.getJdbcTemplate().update(stbUpdate.toString(), obj);

	}

	/**
	 * 分页查询
	 * @param page
	 * @param searchMap
	 * @return
	 * @author DengYouming
	 * @since 2016-4-26 下午7:29:36
	 */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpSettlementTaskBX where 1=1 ");
        searchMap.put("create_time", "desc");
        List valueList = new ArrayList();
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /**
     * 会计页面的查询
     * @param page
     * @param searchMap
     * @return
     * @author DengYouming
     * @throws SQLException 
     * @since 2016-9-5 下午4:26:07
     */
    public List findByPageKj(Page page, Map searchMap) throws SQLException {
    	StringBuffer sqlBuff = new StringBuffer("select r.*, s.bank_name, s.bank_num from (select id, task_no, task_name, create_time, create_user, pro_user, company_num, set_meal_num, total_amount, actual_total_amount, payment_type, status from erp_settlement_task_bx t where 1=1 ");
	    
        Object[] values = new Object[searchMap.keySet().size()];
        
        Iterator iter = searchMap.keySet().iterator();
        
        int i = 0;
        String key;
        while(iter.hasNext()){
        	key = (String) iter.next();
        	if("filter_and_taskNo_LIKE_S".equals(key)){
        		sqlBuff.append(" and t.TASK_NO like '%'||?||'%'");
        	}else if("filter_and_projectNo_LIKE_S".equals(key)){
        		sqlBuff.append(" and t.PROJECT_NO like '%'||?||'%'");
        	}else if("filter_and_createUser_LIKE_S".equals(key)){
        		sqlBuff.append(" and t.CREATE_USER like '%'||?||'%'");
        	}else if("filter_and_proUser_LIKE_S".equals(key)){
        		sqlBuff.append(" and t.PRO_USER like '%'||?||'%'");
        	}else if("filter_and_createTime_GEST_T".equals(key)){
        		sqlBuff.append(" and  t.CREATE_TIME > to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
        	}else if("filter_and_createTime_LEET_T".equals(key)){
        		sqlBuff.append(" and  t.CREATE_TIME < to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
        	}
        	values[i] = searchMap.get(key);
        	i++;
        }

        sqlBuff.append(" and t.STATUS in (?) order by t.create_time desc ) r ");
	    sqlBuff.append(" left join (select p.settlement_id, p.bank_num, b.bank_name ");
	    sqlBuff.append(" from ERP_PROCEEDS p, ERP_BANK b where p.proc_bank_id = b.id) s  on r.id = s.settlement_id ");

	    Connection conn;
	    PreparedStatement ps;
	    ResultSet rs = null;
	    if(sqlBuff!=null){
		    conn = this.getJdbcTemplate().getDataSource().getConnection();
		    ps = conn.prepareCall(sqlBuff.toString());
		    rs = ps.executeQuery();
	    }
    	return super.findBySql(page, sqlBuff.toString(), values);
    }

    /**
     * 保存ErpSettlementTaskBX对象
     * @param obj
     * @author DengYouming
     * @since 2016-4-27 上午5:46:38
     */
    public void saveSettlementTask(ErpSettlementTaskBX obj){
        this.getHibernateTemplate().saveOrUpdate(obj);

    }
    
    /**
     * 根据条件查询ErpSettlementTaskBX
     * @param params
     * @return List
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-9 下午4:37:12
     */
    public List<ErpSettlementTaskBX> listErpSettlementTaskBX(Map<String,Object> params) throws Exception{
    	List<ErpSettlementTaskBX> list;
    	Session session;
    	Criteria criteria = null;
    	if(params!=null){
	    	session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	    	criteria = session.createCriteria(ErpSettlementTaskBX.class);
	    	if(StringUtils.isNotEmpty((String)params.get(ErpSettlementTaskBX.F_ID))){
	    		String id = (String)params.get(ErpSettlementTaskBX.F_ID);
	    		if(id!=null&&id.length()>0){
		    		if(id.indexOf(",")!=-1){
		    			String[] idArr = id.split(Constants.PUNCTUATION_COMMA);
		    			criteria.add(Restrictions.in(ErpSettlementTaskBX.F_ID, idArr));
		    		}else{
		    			criteria.add(Restrictions.eq(ErpSettlementTaskBX.F_ID, id));
		    		}
	    		}
	    	}
	    	
	    	String creater = (String)params.get(ErpSettlementTaskBX.F_CREATEUSER);
	    	if(StringUtils.isNotEmpty(creater)){
	    		criteria.add(Restrictions.eq(ErpSettlementTaskBX.F_CREATEUSER, creater));
	    	}
	    	
	    	if(StringUtils.isNotEmpty((String)params.get(ErpSettlementTaskBX.F_STATUS))){
	    		String status = (String)params.get(ErpSettlementTaskBX.F_STATUS);
	    		if(status!=null&&status.length()>0){
		    		if(status.indexOf(",")!=-1){
		    			String[] statusArr = status.split(Constants.PUNCTUATION_COMMA);
		    			criteria.add(Restrictions.in(ErpSettlementTaskBX.F_STATUS, statusArr));
		    		}else{
		    			criteria.add(Restrictions.eq(ErpSettlementTaskBX.F_STATUS, status));
		    		}
	    		}
	    	}
	    	
	    	String taskNo = (String)params.get(ErpSettlementTaskBX.F_TASKNO);
	    	if(StringUtils.isNotEmpty(taskNo)){
	    		criteria.add(Restrictions.like(ErpSettlementTaskBX.F_TASKNO, taskNo, MatchMode.ANYWHERE));
	    	}
	       	String taskName = (String)params.get(ErpSettlementTaskBX.F_TASKNAME);
	    	if(StringUtils.isNotEmpty(taskName)){
	    		criteria.add(Restrictions.ilike(ErpSettlementTaskBX.F_TASKNAME, taskName, MatchMode.ANYWHERE));
	    	}
	       	String projectNo = (String)params.get(ErpSettlementTaskBX.F_PROJECTNO);
	    	if(StringUtils.isNotEmpty(projectNo)){
	    		criteria.add(Restrictions.like(ErpSettlementTaskBX.F_PROJECTNO, projectNo, MatchMode.ANYWHERE));
	    	}
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    			
	    	String createTimeGe = (String)params.get("filter_and_createTime_GEST_T");
	     	if(StringUtils.isNotEmpty(createTimeGe)){
	    		criteria.add(Restrictions.ge(ErpSettlementTaskBX.F_CREATETIME, sdf.parse(createTimeGe+" 00:00:00")));
	    	}
	    	String createTimeLe = (String)params.get("filter_and_createTime_LEET_T");
	     	if(StringUtils.isNotEmpty(createTimeLe)){
	    		criteria.add(Restrictions.le(ErpSettlementTaskBX.F_CREATETIME, sdf.parse(createTimeLe+" 23:59:59")));
	    	}
    	}
    	list = criteria.list();
    	return list;
    }
    
    /**
     * 批量保存ErpSettlementTaskBX
     * @param list
     * @author DengYouming
     * @since 2016-5-9 下午6:23:39
     */
    public void saveSettlementTaskList(List<ErpSettlementTaskBX> list) throws Exception{
    	if(list!=null&&list.size()>0){
	    	for (int i=0; i<list.size(); i++) {
				this.getHibernateTemplate().saveOrUpdate(list.get(i));
			}
    	}
    }
    
    /**
     * 取消按钮
     * @param settleId
     * @return Integer 
     * @throws Exception
     * @author DengYouming
     * @since 2016-8-11 下午5:39:58
     */
    public Integer cancelTask(String settleId, String userName) throws Exception{
    	Integer num = 0;
    	if(StringUtils.isNotEmpty(settleId)){
    		Connection conn = this.getJdbcTemplate().getDataSource().getConnection();
    		String sql = "{call ERP_COMM_PKG.cancelSettleTask(?,?,?)}";
    		CallableStatement cs = conn.prepareCall(sql);
    		cs.setString(1, settleId);
    		cs.setString(2, userName);
    		cs.registerOutParameter(3, Types.INTEGER);
    		cs.execute();
    		num = cs.getInt(3);
    		conn.commit();
    		if(cs!=null){
    			cs.close();
    		}
    		if(conn!=null){
    			conn.close();
    		}
    	}
    	return num;
    }
    
    /**
     * 处理结算相关业务逻辑
     * @param settleId 结算任务ID
     * @param info 场次号或场次ID或会员ID（均可用逗号拼接）
     * @param userName 当前操作人姓名
     * @param userId 当前操作人ID
     * @return Map 结果集
     * @throws Exception
     * @author DengYouming
     * @since 2016-8-30 上午10:10:30
     */
    public synchronized Map<String,String> dealSettleBX(String settleId, String info, String userName, String userId) throws Exception{
    	Map<String,String> result = null;
    	if(StringUtils.isNotEmpty(settleId)&&StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(userId)){
			Connection conn = this.getJdbcTemplate().getDataSource().getConnection();
			String sql = "{call ERP_COMM_PKG.dealSettleBX(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, settleId);
			cs.setString(2, info);
			cs.setString(3, userName);
			cs.setString(4, userId);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.registerOutParameter(6, Types.DECIMAL);
			cs.registerOutParameter(7, Types.INTEGER);
			cs.registerOutParameter(8, Types.INTEGER);
			cs.registerOutParameter(9, Types.CHAR);
			cs.registerOutParameter(10, Types.DECIMAL);
			cs.registerOutParameter(11, Types.CHAR);
			cs.registerOutParameter(12, Types.CHAR);
			cs.registerOutParameter(13, Types.CHAR);
			cs.registerOutParameter(14, Types.CHAR);
			cs.registerOutParameter(15, Types.CHAR);
			cs.registerOutParameter(16, Types.DECIMAL);

			cs.execute();
			result = new HashMap<String, String>();
			result.put(ErpSettlementTaskBX.F_TOTALPERSONNUM, "" + cs.getInt(5));
			result.put(ErpSettlementTaskBX.F_TOTALAMOUNT, "" + cs.getBigDecimal(6));
			result.put(ErpSettlementTaskBX.F_SETMEALNUM, "" + cs.getInt(7));
			result.put(ErpSettlementTaskBX.F_COMPANYNUM, "" + cs.getInt(8));
			result.put(ErpSettlementTaskBX.F_REMARK, cs.getString(9));
			result.put(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT, cs.getString(10));
			result.put(ErpSettlementTaskBX.F_PROUSER, cs.getString(11));
			result.put(ErpSettlementTaskBX.F_BRANCHCOMPANY, cs.getString(12));
			result.put(ErpSettlementTaskBX.F_OWNEDCOMPANY, cs.getString(13));
			result.put(ErpSettlementTaskBX.F_BRANCHCOMPANYID, cs.getString(14));
			result.put(ErpSettlementTaskBX.F_OWNEDCOMPANYID, cs.getString(15));
			result.put(ErpSettlementTaskBX.F_TOTALINCOME, "" + cs.getBigDecimal(16));

			if(cs!=null){
				cs.close();
			}
			if (conn!=null){
				conn.close();
			}
    	}
    	return result;
    }
    
    /**
     * 根据结算ID，获取结算客户信息
     * @param settBXId
     * @return
     * @author tangxing
     * @date 2017-2-4下午12:05:40
     */
    public List<ErpCustomerSettleBX> getCustomerSettleBXBySettleId(String settBXId){
    	String hql = "from ErpCustomerSettleBX where status <> '-1' and settleId=?";
    	return this.getHibernateTemplate().find(hql,new Object[]{settBXId});
    }
    
    /**
     * 根据regionId获取省份城市
     * @param regionId
     * @return
     * @author tangxing
     * @date 2017-2-6下午1:30:24
     */
    public List<Map<String, Object>> getRegionById(String regionId){
    	String sql = "select hr.region_name as regionName from hl_region hr where hr.id = ? ";
    	return this.getJdbcTemplate().queryForList(sql, new Object[]{regionId});
    }

	/**
	 * @desc  添加项目编号为条件查找场次信息
	 * @author Damian
	 * @since 17-3-15 下午9:38
	 */
	public Page findRelateEvents(Page page, Map searchMap, String projectNo) throws Exception{
		List list;
		if (StringUtils.isNotEmpty(projectNo)){
			Object[] values;
			int i = 0;
			//查询
			StringBuilder sqlBld = new StringBuilder(SQL_FIND_EVENTS_HEAD_QUERY);
			//查询数量
			StringBuilder sqlBldCount = new StringBuilder(SQL_FIND_EVENTS_HEAD_COUNT);
			sqlBld.append(SQL_FIND_EVENTS_BODY);
			sqlBldCount.append(SQL_FIND_EVENTS_BODY);
			//项目负责人或者远盟对接人
            if (searchMap.containsKey("filter_and_ymSalesman_EQ_S")) {
				values = new Object[searchMap.keySet().size()+4];
				System.out.println("values length: "+values.length);
				//先放入项目编号
				values[i] = projectNo;

				String ymSalesMan = (String) searchMap.get("filter_and_ymSalesman_EQ_S");
				values[++i] = ymSalesMan;
				values[++i] = ymSalesMan;
				sqlBld.append(SQL_FIND_EVENTS_BODY_SALEMAN);
				sqlBldCount.append(SQL_FIND_EVENTS_BODY_SALEMAN);
				//移除
				searchMap.remove("filter_and_ymSalesman_EQ_S");
			} else {
				values = new Object[searchMap.keySet().size()+3];
				System.out.println("values length: "+values.length);
				//先放入项目编号
				values[i] = projectNo;
				sqlBld.append(SQL_FIND_EVENTS_BODY_ADMIN);
				sqlBldCount.append(SQL_FIND_EVENTS_BODY_ADMIN);
			}

			if (!CollectionUtils.isEmpty(searchMap)) {
				Iterator iter = searchMap.keySet().iterator();
				String key;
				while (iter.hasNext()) {
					key = (String) iter.next();
					if ("filter_and_eventsNo_LIKE_S".equals(key)) {
						sqlBld.append(" and t.events_no like '%'||?||'%' ");
						sqlBldCount.append(" and t.events_no like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_branchCompanyId_EQ_S".equals(key)) {
						sqlBld.append(" and t.BRANCH_COMPANY_ID = ? ");
						sqlBldCount.append(" and t.BRANCH_COMPANY_ID = ? ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_ownedCompanyId_EQ_S".equals(key)) {
						sqlBld.append(" and t.OWNED_COMPANY_ID = ? ");
						sqlBldCount.append(" and t.OWNED_COMPANY_ID = ? ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_eventDate_GEST_T".equals(key)) {
						sqlBld.append(" and t.EVENT_DATE > to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
						sqlBldCount.append(" and t.EVENT_DATE > to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_eventDate_LEET_T".equals(key)) {
						sqlBld.append(" and  t.EVENT_DATE < to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
						sqlBldCount.append(" and  t.EVENT_DATE < to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_batchno_EQ_S".equals(key)) {
						sqlBld.append(" and t.BATCHNO like '%'||?||'%'");
						sqlBldCount.append(" and t.BATCHNO like '%'||?||'%'");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_province_EQ_S".equals(key)) {
						sqlBld.append(" and t.PROVINCE = ? ");
						sqlBldCount.append(" and t.PROVINCE = ? ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_city_EQ_S".equals(key)) {
						sqlBld.append(" and t.CITY = ? ");
						sqlBldCount.append(" and t.CITY = ? ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_level2_LIKE_S".equals(key)) {
						sqlBld.append(" and t.LEVEL2 = ? ");
						sqlBldCount.append(" and t.LEVEL2 = ? ");
						values[++i] = searchMap.get(key);
					}
//					else if ("filter_and_ymSalesman_EQ_S".equals(key)) {
//						sqlBld.append(" and t.YM_SALESMAN = ? ");
//						sqlBldCount.append(" and t.YM_SALESMAN = ? ");
//					}
				}
			}
			sqlBldCount.append(SQL_FIND_EVENTS_TAIL_COUNT);
			Object[] countArr = Arrays.copyOfRange(values, 0, values.length-2);
			System.out.println("find events sqlBldCount SQL: "+sqlBldCount.toString());
			System.out.println("find events sqlBldCount countArr: "+ Arrays.toString(countArr));
			int total = this.getJdbcTemplate().queryForInt(sqlBldCount.toString(), countArr);
			page.setTotalCount((long) total);
			sqlBld.append(SQL_FIND_EVENTS_TAIL_QUERY);
			values[++i] = page.getPageNumEndCount();
			values[++i] = page.getPageNumStartCount();
			System.out.println("find events sqlBld SQL: "+sqlBldCount.toString());
			System.out.println("find events sqlBld values: "+ Arrays.toString(values));
			list = this.getJdbcTemplate().queryForList(sqlBld.toString(), values);
			page.setResults(list);
		}
		return page;
	}

	/**
	 * @desc  以项目编号为基准条件查询客户信息
	 * @param page
	 * @param searchMap
	 * @param projectNo
	 * @author Damian
	 * @since 17-3-16 上午10:43
	 */
	public Page findRelateCustomer(Page page, Map searchMap, String projectNo) throws Exception{
		List list;
		if (StringUtils.isNotEmpty(projectNo)){
			StringBuilder sqlBld = new StringBuilder(SQL_FIND_CUSTOMER_HEAD_QUERY);
			StringBuilder sqlBldCount = new StringBuilder(SQL_FIND_CUSTOMER_HEAD_COUNT);
			Object[] values;
			int i = 0;
			//项目负责人或者远盟对接人 (普通人员排序)
			if (searchMap.containsKey("filter_and_ymSalesman_EQ_S")) {
				values = new Object[searchMap.keySet().size()+4];
				//先放入项目编号
				values[i] = projectNo;
				System.out.println("values length: "+values.length);
				String ymSalesMan = (String) searchMap.get("filter_and_ymSalesman_EQ_S");
				values[++i] = ymSalesMan;
				values[++i] = ymSalesMan;
				sqlBld.append(SQL_FIND_CUSTOMER_BODY_SALEMAN);
				sqlBldCount.append(SQL_FIND_CUSTOMER_BODY_SALEMAN);
				//移除
				searchMap.remove("filter_and_ymSalesman_EQ_S");
			} else{
				values = new Object[searchMap.keySet().size()+3];
				System.out.println("values length: "+values.length);
				//先放入项目编号
				values[i] = projectNo;
			    //管理员操作，去掉查询条件
				sqlBld.append(SQL_FIND_CUSTOMER_BODY_ADMIN);
				sqlBldCount.append(SQL_FIND_CUSTOMER_BODY_ADMIN);
			}

			if (!CollectionUtils.isEmpty(searchMap)) {
				Iterator iter = searchMap.keySet().iterator();
				String key;
				while (iter.hasNext()) {
					key = (String) iter.next();
					if ("filter_and_eventsNo_LIKE_S".equals(key)) {
						sqlBld.append(" and t.events_no like '%'||?||'%' ");
						sqlBldCount.append(" and t.events_no like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_name_LIKE_S".equals(key)) {
						sqlBld.append(" and t.name like '%'||?||'%' ");
						sqlBldCount.append(" and t.name like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_code_LIKE_S".equals(key)) {
						sqlBld.append(" and t.code like '%'||?||'%' ");
						sqlBldCount.append(" and t.code like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_phone_LIKE_S".equals(key)) {
						sqlBld.append(" and t.phone like '%'||?||'%' ");
						sqlBldCount.append(" and t.phone like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_setmealName_LIKE_S".equals(key)) {
						sqlBld.append(" and t.SETMEAL_NAME like '%'||?||'%' ");
						sqlBldCount.append(" and t.SETMEAL_NAME like '%'||?||'%' ");
						values[++i] = searchMap.get(key);
					}
					else if ("filter_and_samplingDate_GEST_T".equals(key)) {
						sqlBld.append(" and t.SAMPLING_DATE > to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
						sqlBldCount.append(" and t.SAMPLING_DATE > to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_samplingDate_LEET_T".equals(key)) {
						sqlBld.append(" and t.SAMPLING_DATE < to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
						sqlBldCount.append(" and t.SAMPLING_DATE < to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
						values[++i] = searchMap.get(key);
					} else if ("filter_and_idno_LIKE_S".equals(key)) {
						sqlBld.append(" and t.idno like '%'||?||'%'");
						sqlBldCount.append(" and t.idno like '%'||?||'%'");
						values[++i] = searchMap.get(key);
					}
//					else if ("filter_and_ymSalesman_EQ_S".equals(key)) {
//						sqlBld.append(" and t.YM_SALESMAN = ? ");
//						sqlBldCount.append(" and t.YM_SALESMAN = ? ");
//					}
				}
			}

			sqlBldCount.append(SQL_FIND_CUSTOMER_TAIL_COUNT);
			Object[] countArr = Arrays.copyOfRange(values, 0, values.length-2);
			System.out.println("find customer sqlBldCount SQL: "+sqlBldCount.toString());
			System.out.println("find customer sqlBldCount countArr: "+ Arrays.toString(countArr));
			int total = this.getJdbcTemplate().queryForInt(sqlBldCount.toString(), countArr);
			page.setTotalCount((long) total);

			sqlBld.append(SQL_FIND_CUSTOMER_TAIL_QUERY);
			values[++i] = page.getPageNumEndCount();
			values[++i] = page.getPageNumStartCount();
			System.out.println("find customer sqlBld SQL: "+sqlBldCount.toString());
			System.out.println("find customer sqlBld values: "+ Arrays.toString(values));
			list = this.getJdbcTemplate().queryForList(sqlBld.toString(), values);
			page.setResults(list);
		}
		return page;
	}
}
