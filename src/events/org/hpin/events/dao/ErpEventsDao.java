package org.hpin.events.dao;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.dao.ID2NameDAO;
import org.hpin.base.dict.util.Constants;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.StrUtils;
import org.hpin.common.util.Tools;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpEvents;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository(value="erpEventsDao")
public class ErpEventsDao extends BaseDao implements ID2NameDAO{
	
	/**
	 * 查询并处理二维码场次对应的批次号算法;
	 * create by henry.xu 2016年12月9日
	 * @return
	 */
	public String findEventsBatchNo(String project) {
		String batchNo = "";
		
		String sql = "select max(to_number(substr(batchNo, 3, length(batchNo)))) batchNo from erp_events where BATCHNO like '"+project+"%' ";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql);
		if(null != map) {
			batchNo = map.get("batchNo") != null ? map.get("batchNo").toString() : "" ;
		}
		
		return batchNo;
	}
	
	/**
	 * 时间段查找场次
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午2:11:47
	 */
	public List<ErpEvents> getErpEventsByDate(Date startTime,Date endTime){
		String query = "from ErpEvents where createTime>=? and createTime<?";
		return this.getHibernateTemplate().find(query, new Object[]{startTime,endTime});
	}
	
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	
	private Logger log = Logger.getLogger(ErpEventsDao.class);
	
	public List findByPage4(Page page, Map searchMap) {
//		String sql = " from ErpEvents where 1=1  ";
//		List<User> list = this.getHibernateTemplate().find(sql,new Object[]{u.getUserName(),u.getExtension(),u.getDeptName()});
//		List<ErpEvents> list = this.getHibernateTemplate().find(sql,null);
		
		
//		StringBuffer query = new StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");//没有isEnable
		StringBuffer query = new StringBuffer(" from ErpEvents where 1=1 ");
		//searchMap.put(" eventsNo", "desc");
		searchMap.put("order_eventDate", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		log.info("ErpEventsDao findByPage4 valueList size : " + valueList.size()+"=======");
//		return super.findByHql(page, query, valueList);
		return valueList;
	}
	/**
	 * 分页获取对象
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
//		StringBuffer query = new StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");//没有isEnable
		StringBuffer query = new StringBuffer(" from ErpEvents where 1=1 ");
		//searchMap.put(" eventsNo", "desc");
		searchMap.put("order_eventDate", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 是否存在已有的场次
	 * @param eventsNo
	 * @return
	 */
	public String isNotRepeatNo(String eventsNo){
		String repeat="";
		String hql = "from ErpEvents where eventsNo=?";
		List list = this.getHibernateTemplate().find(hql,eventsNo);
		if(list.size()>0){
			repeat="场次号已存在";
		}
		return repeat;
	}
	
	/**
	 * 是否存在已有的场次
	 * @param compannyName 支公司名称 eventDate 场次日期
	 * @return
	 */
	public String isNotRepeatBycompanyIdAndDate(String compannyName,String ownedCompany,Date eventDate,String address){
		String repeat="";
		String hql = "from ErpEvents where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 AND address=?";
//		String hql = "from ErpEvents where branch_company =? and owned_company = ? and event_date = ? and is_deleted=0 ";
		log.info("ErpEventsDao isNotRepeatBycompanyIdAndDate 校验场次 ; compannyName : " + compannyName +"\t ownedCompany :"+ ownedCompany +"\t eventDate :"+ eventDate +"\t address :" + address);
		List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate,address});
//		List list = this.getHibernateTemplate().find(hql,new Object[]{compannyName,ownedCompany,eventDate});
		if(list.size()>0){
			repeat="该时间已经存在场次";
		}
		return repeat;
	}
	
	/**
	 * 根据支公司名字，场次时间获取场次
	 * @param branchCompany
	 * @param eventDate
	 * @return
	 * @author tangxing
	 * @date 2016-6-20下午2:55:20
	 */
	public List<ErpEvents> getEventsByTimeAndBranch(String branchCompany,Date eventDate) throws Exception{
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String eventsTime = sdf.format(eventDate);*/
		List<ErpEvents> list = null;
		String queryString="from ErpEvents where branchCompany =? and eventDate = ? and isDeleted=0";
		list = this.getHibernateTemplate().find(queryString, new Object[]{branchCompany,eventDate});
		return list;
	}
	
	/**
	 * 查询当天最大场次号
	 * @param date
	 * @return
	 */
	public String maxNo(String date){
		String eventsNo="HL1512142048000";
		String sql = "select max(events_no) as events_no from Erp_Events where to_char(event_Date,'yyyy-mm-dd')='"+date+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		Map map = (Map) list.get(0);
		String maxNo = (String) map.get("EVENTS_NO");
		if(StrUtils.isNotNullOrBlank(maxNo)){
			eventsNo=maxNo.toString();
		}
		return eventsNo;
	}
	public String  del(String id){
		String message="";
		this.delete(ErpEvents.class, id);
		
		return message;
	}
	public ErpEvents get(String id){
		return (ErpEvents)this.findById(ErpEvents.class, id);
	}
	public void modify(ErpEvents events){
		this.update(events);
	}
	
	/**
	 * 根据场次号查询场次信息
	 * @param eventsNo
	 * @return
	 */
	public ErpEvents queryEventNO(String eventsNo){
		String hql = "from ErpEvents where eventsNo=?";
		List list = this.getHibernateTemplate().find(hql,eventsNo);
		ErpEvents events = null;
		if(null!=list&&list.size()>0){
			events = (ErpEvents) list.get(0);
		}
		return events;
	}
	/**
	 * 根据场次号查询场次信息 create by machuan 20161122
	 * @param eventsNo
	 * @return
	 * 
	 */
	public ErpEvents queryByEventNO(String eventsNo){
		String hql = "from ErpEvents where eventsNo=? and isDeleted in (0,2)";
		List list = this.getHibernateTemplate().find(hql,eventsNo);
		ErpEvents events = null;
		if(null!=list&&list.size()>0){
			events = (ErpEvents) list.get(0);
		}
		return events;
	}
	
	/**
	 * 获取已经删除的场次
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2017-2-22上午11:26:56
	 */
	public List<ErpEvents> queryByEventNoIsDeleted(String eventsNo){
		String hql = "from ErpEvents where eventsNo=? and isDeleted = 1";
		return this.getHibernateTemplate().find(hql,eventsNo);
	}
	/**
	 * 根据场次号统计每个场次多少报告
	 * @param eventsNo
	 * @return
	 */
//	public Integer getCustomerPDFNums(String eventsNo) {
//		String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0  AND pdffilepath IS NOT NULL and EVENTS_NO=?";
//		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{eventsNo});
//		return count;
//	}
//	public Integer getmyPDFNums(String eventsNo) {
//		String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0  AND pdffilepath IS NULL and EVENTS_NO=?";
//		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{eventsNo});
//		return count;
//	}
	/**
	 * 根据场次号统计每个场次人数
	 * @param eventsNo
	 * @return
	 */
	public Integer getCustomerNums(String eventsNo) {
		String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0 and EVENTS_NO=?";
		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{eventsNo});
		return count;
	}
	/*
	 * 统计所有场次每个场次人数
	 */
	public List getAllInputNumsList(String userName){
		List list=null;
//		if(!userName.equals("管理员")){
//		  String queryString="select events_no,count(*) as total  from ERP_CUSTOMER where  is_deleted=0 and create_user_name=? group by events_no";//管理员增加的客户看不到
//		  list = this.getJdbcTemplate().queryForList(queryString, new Object[]{userName});	
//		}else{
//		  String queryString="select events_no,count(*) as total  from ERP_CUSTOMER where  is_deleted=0 group by events_no";
//		  list = this.getJdbcTemplate().queryForList(queryString);
//		}
		//                  select events_no,count(*) as total  from ERP_CUSTOMER where is_deleted=0 
		String queryString="select events_no,count(*) as total ,count(CASE WHEN pdffilepath IS NOT NULL THEN 1 END) AS pdftotal,count(CASE WHEN pdffilepath IS  NULL THEN 1 END) AS nopdftotal from ERP_CUSTOMER where is_deleted=0 group by events_no";
		list = this.getJdbcTemplate().queryForList(queryString);
		 return list;
	}
	
	/**
	 * 列出所有未添加到结算任务的场次
	 * @return List<ErpEvents>
	 * @author DengYouming
	 * @since 2016-5-2 上午12:16:32
	 */
	public List<ErpEvents> listEventsNotInSettle(){
		List<ErpEvents> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpEvents.class);
		//未删除，按创建日期倒序
		criteria.add(Restrictions.eq(ErpEvents.F_ISDELETED, Constants.STATUS_NEW_INT)).addOrder(Order.desc(ErpEvents.F_CREATETIME));
		list = criteria.list();
		return list;
	}
	
	
	/**
	 * 根据条件查询相关场次
	 * @param params 传入的条件
	 * @return List
	 * @author DengYouming
	 * @since 2016-5-18 上午11:33:17
	 */
	public List<ErpEvents> listEventsByProps(Map<String,String> params){
		List<ErpEvents> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ErpEvents.class);

		if(!CollectionUtils.isEmpty(params)){
			for (String key : params.keySet()) {
				String value = params.get(key);
				if(key.equalsIgnoreCase(ErpEvents.F_ID)){
					String[] idArr;
					if(value.indexOf(",")!=-1){
						idArr = value.split(",");
					}else{
						idArr = new String[1] ;
						idArr[0] = value;
					}
					criteria.add(Restrictions.in(ErpEvents.F_ID, idArr));
				}else if(key.equalsIgnoreCase(ErpEvents.F_ISDELETED)){
					criteria.add(Restrictions.eq(key, Integer.valueOf(value)));
				}else{
					criteria.add(Restrictions.eq(key, value));
				}
			}
			//未删除，按创建日期倒序
			criteria.add(Restrictions.eq(ErpEvents.F_ISDELETED, 0)).addOrder(Order.desc(ErpEvents.F_CREATETIME));
			list = criteria.list();
		}
		return list;
	}
	
	/**
	 * 根据场次号更新场次结算状态
	 * @param eventsNo
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-17 下午1:55:30
	 */
    public Integer updateEventsStatusBX(String eventsNo){
    	Integer num = 0;
    	if(StringUtils.isNotEmpty(eventsNo)){
    		Connection conn;
			try {
				conn = this.getJdbcTemplate().getDataSource().getConnection();
				String sql = "{call ERP_COMM_PKG.updateEventsStatusBX(?,?)}";
	    		CallableStatement cs = conn.prepareCall(sql);
	    		cs.setString(1, eventsNo);
	    		cs.registerOutParameter(2, Types.INTEGER);
	    		cs.execute();
	    		num = cs.getInt(2);
	    		conn.commit();
	    		if(cs!=null){
	    			cs.close();
	    		}
	    		if(conn!=null){
	    			conn.close();
	    		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return num;
    }
    
    public int countEventsCustomer(String eventsNo, Integer status){
    	int num = 0;
    	String sql = " select count(id) num from erp_customer where 1=1 and events_no = ? ";
		if(status!=null){
			sql = sql+" and status = ? ";
		}
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {

			conn = this.getJdbcTemplate().getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, eventsNo);
			if(status!=null){
				ps.setInt(2, status);
			}
			System.out.println("sql: "+sql);
			rs = ps.executeQuery();
			while(rs.next()){
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return num;
    }
    
    public ErpEvents getEventsById(String eventsId){
    	return this.getHibernateTemplate().get(ErpEvents.class, eventsId);
    }
    /**
     * 根据支公司id获取对应的总公司名称;
     * create henry.xu 20161027
     * (non-Javadoc)
     * @see org.hpin.base.customerrelationship.dao.ID2NameDAO#id2Name(java.lang.String)
     */
	@Override
	public String id2Name(String id) {
		String  sql = "select dept.dept_name deptName from HL_CUSTOMER_RELATIONSHIP ship left join um_dept dept on ship.OWNED_COMPANY = dept.id where ship.IS_DELETED = 0 and ship.id = '"+id+"'";
		Map<String, Object> result = this.getJdbcTemplate().queryForMap(sql);
		if(result != null ) {
			return (String) result.get("deptName");
		}
		return null;
	}
	@Override
	public String id2Field(String id, String beanId, String field) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询并处理二维码场次对应的阳关保险批次号算法;
	 * create by henry.xu 2016年12月9日
	 * @return
	 */
	public String findEventsYGBatchNo(String mark) {
		String batchNo = "";
		
		String sql = "select max(batchNo) batchNo from erp_events where BATCHNO like '"+mark+"%' ";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql);
		if(null != map) {
			batchNo = (String) map.get("batchNo");
		}
		
		if(StringUtils.isNotEmpty(batchNo)) {
			String numStr = batchNo.substring(2, batchNo.length());
			Integer num = Integer.valueOf(numStr)+1;
			batchNo = mark + num;
			
		} else {
			batchNo = mark+"1";
		}
		
		return batchNo;
	}

	/**
	 * 可以根据
	 * 1）info: 会员姓名 , infoType: name
	 * 2）info: 会员条码, infoType: code
	 * 3）info: 批次号,  infoType: batchNo
	 * 4）info: 场次号,  infoType: eventsNo
	 * 5）info: 团单号 ,  infoType: groupOrderNo
	 * 等信息，查找到相关场次
	 * @param info 传入的信息
	 * @param infoType 信息类型
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-10-25 下午4:37:14
	 */
	public List<ErpEvents> listEventsByInfo(String info, String infoType)throws Exception{
		List<ErpEvents> list = null;
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;

		if(StringUtils.isNotEmpty(info)&&StringUtils.isNotEmpty(infoType)){
			try{
				String procName = "{call ERP_COMM_PKG.findEvents(?,?,?)}";
				conn = this.getJdbcTemplate().getDataSource().getConnection();
				proc = conn.prepareCall(procName);
				proc.setString(1, info);
				proc.setString(2, infoType);
				proc.registerOutParameter(3, OracleTypes.CURSOR);
				proc.execute();
				rs = (ResultSet) proc.getObject(3);
				if(rs!=null){
					list = this.convertToEvents(rs);
				}
			}finally{
				if(rs!=null){
					rs.close();
				}
				if(proc!=null){
					proc.close();
				}
				if(conn!=null){
					conn.close();
				}
			}
		}
		return list;
	}

	private static List<ErpEvents> convertToEvents(ResultSet rs) throws SQLException{
		List<ErpEvents> list = null;
		ErpEvents entity = null;
		if(rs!=null){
			list = new ArrayList<ErpEvents>();
			while(rs.next()){
				entity = new ErpEvents();

				entity.setId(rs.getString("ID"));
				entity.setEventsNo(rs.getString("EVENTS_NO"));
				entity.setBatchNo(rs.getString("BATCHNO"));
				entity.setEventDate(rs.getDate("EVENT_DATE"));
				entity.setAddress(rs.getString("BRANCH_COMPANY"));

				entity.setBranchCompany(rs.getString("BRANCH_COMPANY"));
				entity.setBranchCompanyId(rs.getString("BRANCH_COMPANY_ID"));
				entity.setOwnedCompany(rs.getString("OWNED_COMPANY"));
				entity.setOwnedCompanyId(rs.getString("OWNED_COMPANY_ID"));
				entity.setLevel2(rs.getString("LEVEL2"));

				entity.setComboId(rs.getString("COMBO_ID"));
				entity.setComboName(rs.getString("COMBO_NAME"));
				entity.setHeadcount(rs.getInt("HEADCOUNT"));
				entity.setNowHeadcount(rs.getInt("NOW_HEADCOUNT"));
				entity.setIsDeleted(rs.getInt("IS_DELETED"));

				entity.setIsExpress(rs.getInt("IS_EXPRESS"));
				entity.setCreateUserName(rs.getString("CREATE_USER_NAME"));
				entity.setCreateTime(rs.getDate("CREATE_TIME"));
				entity.setUpdateUserName(rs.getString("CREATE_USER_NAME"));
				entity.setUpdateTime(rs.getDate("UPDATE_TIME"));

				entity.setEname(rs.getString("ENAME"));
				entity.setEtrackingNumber(rs.getString("ETRACKING_NUMBER"));
				entity.setEdate(rs.getDate("EDATE"));
//				entity.setHour(rs.getString("&"));
				entity.setProvice(rs.getString("PROVINCE"));

				entity.setCity(rs.getString("PROVINCE"));
				entity.setPdfcount(rs.getInt("PDFCOUNT"));
				entity.setYmSalesman(rs.getString("YM_SALESMAN"));
				entity.setNopdfcount(rs.getInt("NOPDFCOUNT"));
				entity.setSettNumbers(rs.getInt("SETT_NUMBERS"));

				entity.setProduceCost(rs.getDouble("PRODUCE_COST"));
				entity.setStatusBX(rs.getInt("STATUS_BX"));
				entity.setGroupOrderNo(rs.getString("GROUP_ORDER_NO"));
				entity.setCustomerRelationShipProId(rs.getString("CUSTOMERRELATIONSHIPPRO_ID"));
				entity.setEventsType(rs.getString("EVENTS_TYPE"));

				list.add(entity);
			}
		}
		return list;
	}

	private static List convert(ResultSet rs , Class clazz){
		//返回结果的列表集合
		List list = new ArrayList();
		try{
			//结果集的元素对象
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取结果集的元素个数
			int colCount = rsmd.getColumnCount();
    /*   for(int i = 1;i<=colCount;i++){
           System.out.println("列名："+rsmd.getColumnName(i));
           System.out.println("列类型： "+rsmd.getColumnClassName(i));
           System.out.println("#");
       } */

			//业务对象的属性数组
			Field[] fields = findPirvateFields(clazz);
			while(rs.next()){//对每一条记录进行操作
				Object obj = clazz.newInstance();//构造业务对象实体
				//将每一个字段取出进行赋值
				for(int i = 1;i<=colCount;i++){
					Object value = rs.getObject(i);
					//列名
					String colName = rsmd.getColumnName(i);
					colName = Tools.colToField(colName);
					//寻找该列对应的对象属性
					for(int j=0;j<fields.length;j++){
						Field f = fields[j];
						//如果匹配进行赋值
						if(f.getName().equalsIgnoreCase(colName)){
							boolean flag = f.isAccessible();
							f.setAccessible(true);
							//根据字段类型转换
							value = praseVal(f, value);
							f.set(obj, value);
							f.setAccessible(flag);
						}
					}
				}
				if(obj!=null){
					list.add(obj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	private static Field[] findPirvateFields(Class clazz){
		Field[] fields = new Field[]{};
		Field[] temp = clazz.getDeclaredFields();
		int num = 0;
		for (int i = 0; i < temp.length; i++) {
			String name = temp[i].getName();
			if("serialVersionUID".equalsIgnoreCase(name)||name.startsWith("F_")){
				continue;
			}
			fields = Arrays.copyOf(fields, fields.length+1);
			fields[num] = temp[i];
			num++;
		}
		return fields;
	}

	private static Object praseVal(Field f, Object val){
		Object otherVal = null;
		String typeName = f.getType().getSimpleName();
		if("String".equalsIgnoreCase(typeName)){
			val = val!=null?val:"";
			otherVal = ""+val;
		}else if("long".equalsIgnoreCase(typeName)){
			val = val!=null?val:0;
			otherVal = Long.parseLong(""+val);
		}else if("int".equalsIgnoreCase(typeName)||"integer".equalsIgnoreCase(typeName)){
			val = val!=null?val:0;
			otherVal = Integer.parseInt(""+val);
		}else if("float".equalsIgnoreCase(typeName)){
			val = val!=null?val:0;
			otherVal = Float.parseFloat(""+val);
		}else if("double".equalsIgnoreCase(typeName)){
			val = val!=null?val:0;
			otherVal = Double.parseDouble(""+val);
		}else if("bigdecimal".equalsIgnoreCase(typeName)){
			val = val!=null?val:0;
			otherVal = new BigDecimal(""+val);
		}else{
			otherVal = val;
		}
		return otherVal;
	}

}
