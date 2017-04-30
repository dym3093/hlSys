package org.hpin.events.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.base.dict.util.Constants;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.Tools;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpCustomerException;
import org.hpin.events.entity.ErpCustomerImpFailBtach;
import org.hpin.events.entity.ErpCustomerSync;
import org.hpin.events.entity.ErpEvents;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


@Repository()
public class ErpCustomerDao extends BaseDao {
	/**
	 * 根据场次号修改客户采样日期
	 * @param events
	 */
	public void updateSampleDatebyEventsNo(ErpEvents events) {
		String eventsNo=events.getEventsNo();
		Date eventsDate=events.getEventDate();
		String sql="update erp_customer set SAMPLING_DATE=? where EVENTS_NO=?";
		this.getJdbcTemplate().update(sql, new Object[]{eventsDate,eventsNo});
		
	}
	
	/**
	 * 查找比对失败任务
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-6-21上午11:42:40
	 */
	public List<ErpCustomerImpFailBtach> listFailBtach(Page page, Map searchMap){
		StringBuffer query = new StringBuffer("from ErpCustomerImpFailBtach where 1=1");
	    searchMap.put("order_createTime", "desc");
	    List valueList = new ArrayList();
	    OrmConverter.assemblyQuery(query, searchMap, valueList);
	    return super.findByHql(page, query, valueList);
	}
	
	   /**
     * 根据会议号修改客户采样日期
     * @param conference
     */
    public void updateSampleDatebyConferenceNo(ErpConference conference) {
        String conferneceNo=conference.getConferenceNo();
        Date conferneceDate=conference.getConferenceDate();
        String sql="update erp_customer set SAMPLING_DATE=? where CONFERENCE_NO=?";
        this.getJdbcTemplate().update(sql, new Object[]{conferneceDate,conferneceNo});
        
    }

	/**
	 * 根据场次号获取PDF报告人数
	 * 
	 * @param eventsNo
	 * @return
	 */
	public Integer getCustomerPDFNums(String eventsNo) {
		String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0 AND pdffilepath IS NOT NULL  and EVENTS_NO=?";
		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{eventsNo});
		return count;
	}
	public Integer getCustomerNOPDFNums(String eventsNo) {
		String queryString = "select count(*) from ERP_CUSTOMER where  IS_DELETED=0 AND pdffilepath IS NULL  and EVENTS_NO=?";
		Integer count=this.getJdbcTemplate().queryForInt(queryString, new Object[]{eventsNo});
		return count;
	}
	/**
	 * 根据场次号获取人数
	 * 
	 * @param eventsNo
	 * @return
	 */
	public int getNowHeadcountByEventsNo(String eventsNo) {
		String hql = "from ErpCustomer where isDeleted=0 and  eventsNo=?";
		List list = this.getHibernateTemplate().find(hql, eventsNo);
		return list.size();
	}
	
	/**
     * 根据会议号获取人数
     * 
     * @param conferenceNo
     * @return
     */
    public int getNowHeadcountByConferenceNo(String conferenceNo) {
        String hql = "from ErpCustomer where isDeleted=0 and  conferenceNo=?";
        List list = this.getHibernateTemplate().find(hql, conferenceNo);
        return list.size();
    }

	/**
	 * 验证条形码是否被使用
	 * 
	 * @param code
	 * @return
	 */
	public String isNotRepeatcode(String code) throws Exception{
		String message = "";
		String hql = "from ErpCustomer where code=? and IS_DELETED=0";
		List list = this.getHibernateTemplate().find(hql, code);
		if(!CollectionUtils.isEmpty(list)){
			if (list.size() > 0) {
				message =code+"重复";
			}
		}
		return message;
	}
	/**
	 * 根据条形码查找用户信息
	 * 
	 * @param code
	 * @return
	 */
	public List<ErpCustomer> getCustomerByCode(String code) {
		String message = "";
		String hql = "from ErpCustomer where code=? and IS_DELETED=0";
		List<ErpCustomer> list = this.getHibernateTemplate().find(hql, code);
		return list;
	}
	
	public List findByPage(Page page, Map searchMap) {
		// StringBuffer query = new
		// StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");
		
		String reportState = (String) searchMap.get("reportState");
		StringBuffer query = new StringBuffer("from ErpCustomer where 1=1 ");
		
		//reportState 0有报告; 1没有报告; 添加报告状态查询条件;
		//modified by henry.xu 20160817;
		if("0".equals(reportState)) {
			query.append("and pdffilepath is not null and LENGTH(pdffilepath) > 53");
		} else if("1".equals(reportState)) {
			query.append("and (pdffilepath is null or LENGTH(pdffilepath) < 53)");
		}
		
		searchMap.put(" code", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}
	public List findByPageNoReport(Page page, Map searchMap) {
		// StringBuffer query = new
		// StringBuffer(" from ErpEvents where isDeleted=0  and isEnable = 1");
		StringBuffer query = new StringBuffer(" from ErpCustomer where 1=1 and pdffilepath is null ");
		searchMap.put(" code", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 导入前查询是否条形码重复
	 * 
	 * @param eventsNo
	 * @return
	 */
	public int queryEventNO(String eventsNo) {
		int no = 0;
		String hql = "from ErpEvents where eventsNo=?";
		List list = this.getHibernateTemplate().find(hql, eventsNo);
		if (list.size() > 0) {
			no = 1;
		}
		return no;
	}
	
	public List<ErpCustomer> getCustomerByCodes(String batchno,String branchCompany){
		String hql = "from ErpCustomer where IS_DELETED=0 and code in ( select code from ErpReportdetailPDFContent where batchno = ?) ";
		List<ErpCustomer> list = null;
		if(null!=branchCompany){
			hql = hql + "and BRANCH_COMPANY = ?";
			list = this.getHibernateTemplate().find(hql,batchno,branchCompany);
		}else{
			list = this.getHibernateTemplate().find(hql,batchno);
		}
		return list;
	}
	
	/**
	 * 查找客户信息比对状态为'1'的
	 * @return
	 * @author tangxing
	 * @date 2016-6-21上午11:20:12
	 */
	public List<ErpCustomer> getCustomerByComparStatus(){
		String hql = "from ErpCustomer where failBtachId is null and comparStatus='1'";
		List<ErpCustomer> list  = this.getHibernateTemplate().find(hql);
		return list;
	}
	
	public void updatePdfPath(String newpath, String id) {
		String custHql = "update ERP_CUSTOMER set pdffilepath=? where id = ?";
		this.getJdbcTemplate().update(custHql, new Object[]{newpath,id});
	}
	
	/**
	 * 列出可结算的会员
	 * @param params
	 * @return List
	 * @author DengYouming
	 * @since 2016-5-7 下午5:46:40
	 */
	public List<ErpCustomer> listCustomerCanAddSettle(Map<String,Object> params){
		List<ErpCustomer> list = null;
		
		if(!CollectionUtils.isEmpty(params)){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Criteria criteria = session.createCriteria(ErpCustomer.class);
			//未删除
			criteria.add(Restrictions.eq(ErpCustomer.F_ISDELETED, Constants.STATUS_NEW_INT));
			//状态为0
//			criteria.add(Restrictions.eq(ErpCustomer.F_STATUS, "0"));
			//TODO 有pdf文件路径？？？
			//criteria.add(Restrictions.isNotEmpty(ErpCustomer.F_PDFFILEPATH));
			//根据ID查找
			String[] idArr = null ;
			if(params.get(ErpCustomer.F_ID)!=null){
				if(params.get(ErpCustomer.F_ID) instanceof String[]){
					idArr = (String[]) params.get(ErpCustomer.F_ID);
				}else{
					String ids = (String)params.get(ErpCustomer.F_ID);
					if(ids.indexOf(Constants.PUNCTUATION_COMMA)!=Constants.STATUS_DELETE_INT){
						int n = ids.split(Constants.PUNCTUATION_COMMA).length;
						idArr = new String[n] ;
						idArr = ids.split(Constants.PUNCTUATION_COMMA);
					}else{
						idArr = new String[1] ;
						idArr[0] = ids;
					}
				}
				criteria.add(Restrictions.in(ErpCustomer.F_ID, idArr));
			}
			//状态
			if(params.get(ErpCustomer.F_STATUS)!=null){
				criteria.add(Restrictions.eq(ErpCustomer.F_STATUS, (String) params.get(ErpCustomer.F_STATUS)));
			}
			//根据场次号查找
			if(params.get(ErpCustomer.F_EVENTSNO)!=null){
				criteria.add(Restrictions.eq(ErpCustomer.F_EVENTSNO, (String) params.get(ErpCustomer.F_EVENTSNO)));
			}
			//根据条码查找
			if(params.get(ErpCustomer.F_CODE)!=null){
				criteria.add(Restrictions.eq(ErpCustomer.F_CODE, (String) params.get(ErpCustomer.F_CODE)));
			}		
			//根据姓名精确查找
			if(params.get(ErpCustomer.F_NAME)!=null){
				criteria.add(Restrictions.eq(ErpCustomer.F_NAME, (String) params.get(ErpCustomer.F_NAME)));
			}	
			list = criteria.list();
		}
		return list;
	}
	
	/**
	 * 根据参数查询ErpCustomer列表
	 * @param params 查询参数
	 * @param isExact 是否精确查询 true:是， false:否
	 * @return List
	 * @author DengYouming
	 * @since 2016-8-25 上午11:16:37
	 */
	public List<ErpCustomer> listByProps(Map<String,String> params, boolean isExact){
		List<ErpCustomer> list = null;
		
		if(!CollectionUtils.isEmpty(params)){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Criteria criteria = session.createCriteria(ErpCustomer.class);
			//未删除
			criteria.add(Restrictions.eq(ErpCustomer.F_ISDELETED, Constants.STATUS_NEW_INT));
			for (String key : params.keySet()) {
				String value = params.get(key);
				if(StringUtils.isNotEmpty(value)){
					if(isExact){
						criteria.add(Restrictions.eq(key, value));
					}else{
						criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
					}
				}
			}
			list = criteria.list();
		}
		return list;
	}
	
	/**
	 * 根据参数精确查询ErpCustomer集合
	 * @param params 参数
	 * @return List
	 * @author DengYouming
	 * @since 2016-8-25 上午11:26:04
	 */
	public List<ErpCustomer> listByProps(Map<String,String> params){
		List<ErpCustomer> list = null;
		if(!CollectionUtils.isEmpty(params)){
			list = this.listByProps(params, true);
		}
		return list;
	}
	
	/**
	 * 根据场次号查找场次号中所有套餐
	 * @param eventsNo
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-13 下午1:54:12
	 */
	public List<String> listComboNameByEventsNo(String eventsNo) throws Exception{
		List<String> list = null;
		if(eventsNo!=null&&eventsNo.length()>0){
			String sql = "select distinct(t.setmeal_name) combo from erp_customer t where t.events_no=? group by t.setmeal_name";
			//查找
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query =  session.createSQLQuery(sql);
			query.setParameter(0, eventsNo);
			list = query.list();
			session.getTransaction().commit();
		}
		return list;
	}
	
	/**
	 * 
	 * @param list
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-25 下午7:49:24
	 */
	public void saveOrUpdateList(List<ErpCustomer> list) throws Exception{
		if(list!=null&&list.size()>0){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate(list.get(i));
				if(i!=0&&i%100==0){
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
		}
	}
	
	/**
	 * 保存比对失败批次
	 * @param btach
	 * @author tangxing
	 * @date 2016-6-21下午5:23:49
	 */
	public void saveFailBtach(ErpCustomerImpFailBtach btach){
		this.getHibernateTemplate().save(btach);
	}
	
	/**
	 * 根据比对批次失败批次Id查找customer
	 * @param FailBtachId
	 * @return
	 * @author tangxing
	 * @date 2016-6-21下午5:25:34
	 */
	public List<ErpCustomer> getCustomerByFailBtachId(String FailBtachId){
		String hql = "from ErpCustomer where failBtachId = ?";
		List<ErpCustomer> list  = this.getHibernateTemplate().find(hql,new Object[]{FailBtachId});
		return list;
	}
	
	/**
     * 根据ID查找读取客户类
     * @param id
     * @return
     */
    public ErpCustomerImpFailBtach getFailBtachById(String id){
        return (ErpCustomerImpFailBtach)this.findById(ErpCustomerImpFailBtach.class, id);
    }
    
    public List<ErpCustomer> getCustomerByCreateTime(Date stratTime,Date endTime){
    	String hql = "from ErpCustomer where createTime>=? and createTime<=? and checkTime is null";
		List<ErpCustomer> list  = this.getHibernateTemplate().find(hql,new Object[]{stratTime,endTime});
    	return list;
    }
    
    public Integer updateStatus(String id, String status){
    	Integer num = 0;
    	boolean flag = false;
    	String sql = " update erp_customer set status = ? where 1=1 and id = ? ";
	
    	Connection conn = null;
    	PreparedStatement ps = null;
    	try {

			conn = this.getJdbcTemplate().getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, id);
			System.out.println("sql: "+sql);
			flag = ps.execute();
			if(flag){
				num = 1;
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return num;
    }
    
    /**
     * 查找该场次下所有的客户
     * @param eventsNo
     * @return
     * @author tangxing
     * @date 2016-9-21下午4:44:39
     */
    public List<ErpCustomer> getErpCustomerByEventsNo(String eventsNo){
    	String hql = "from ErpCustomer where eventsNo=?";
		List<ErpCustomer> list  = this.getHibernateTemplate().find(hql,new Object[]{eventsNo});
    	return list;
    }
    
    /**
     * 查找该场次下的已出报告会议
     * @param eventsNo
     * @return
     * @author tangxing
     * @date 2016-9-21下午4:43:46
     */
    public List<ErpCustomer> getErpCustomerByEventsNoAndStatus(String eventsNo){
    	String hql = "from ErpCustomer where eventsNo=? and pdffilepath is not null";
		List<ErpCustomer> list  = this.getHibernateTemplate().find(hql,new Object[]{eventsNo});
    	return list;
    }

	/**
	 * 查询客户信息表，姓名和条形码必须有，对比“场次号”、“客户姓名”、“条形码”，假如已存在则直接退出，避免重复录入
	 * @param eventsNo
	 * @param name
	 * @param code
	 * @return 查询的结果为0即为不重复  返回false  否则true
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public boolean verifyRepeat(String eventsNo, String name, String code) {
		boolean flag = true;
		if(StringUtils.isNotBlank(name)&&code.length()>=4){
			String hql = "from ErpCustomer where eventsNo=? and name=? and code=? and IS_DELETED=0";
			flag = this.getHibernateTemplate().find(hql,new Object[]{eventsNo,name,code}).size()>0;
		}
		return flag;
	}

	/**
	 * 查询是否有匹配的套餐名 
	 * @param setmealName
	 * @return 有结果就返回true  否则false
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public boolean checkComboName(String setmealName) {
		String hql = "from Combo where combo_name=? and is_delete=0" ;
		return this.getHibernateTemplate().find(hql,new Object[]{setmealName}).size()>0;
	}

	/**
	 * 根据场次号  获取 异常数据的数量
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public int getExceptCount(String eventsNo) {
		String hql = "from ErpCustomerException where eventsNo=? ";
		return this.getHibernateTemplate().find(hql,new Object[]{eventsNo}).size();
	}

	/**
	 * 根据场次号 获取 分页异常数据
	 * @param page
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public List getListCustomerException(Page page, String eventsNo) {

		StringBuffer query = new StringBuffer("from ErpCustomerException where 1=1 ");
		Map<String, String> searchMap  = new HashMap<String, String>();
		searchMap.put(" filter_and_eventsNo_EQ_S", eventsNo);
		searchMap.put(" code", "desc");
		List valueList = new ArrayList();
		OrmConverter.assemblyQuery(query, searchMap, valueList);
		return super.findByHql(page, query, valueList);
	}

	/**
	 * 根据id获取异常客户信息
	 * @param id
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public ErpCustomerException findExceptionById(String id) {
		String hql = "from ErpCustomerException where id=?";
		return (ErpCustomerException) this.getHibernateTemplate().find(hql,new Object[]{id}).get(0);
	}

	/**
	 * 到异常表查询是否有重复导入的数据  判断的字段为场次号，客户姓名，条形码  重复则删除
	 * @param eventsNo
	 * @param name
	 * @param code
	 * @author machuan
	 * @date  2016年11月23日
	 */
	public void verifyRepeatException(String eventsNo, String name, String code) {
		String hql = "from ErpCustomerException where eventsNo=? and name=? and code=?";
		List<ErpCustomerException> list = this.getHibernateTemplate().find(hql,new Object[]{eventsNo,name,code});
		if(list.size()>0){
			for(ErpCustomerException customerException : list){
				delete(customerException);
			}
		}
	}

	/**
	 * 根据id判断sync表中是否有重复数据
	 * @param id
	 * @return
	 * @author machuan
	 * @date  2016年11月28日
	 */
	public ErpCustomerSync getCustomerSyncByCId(String id) {
		String hql = "from ErpCustomerSync where customerId=? and status=0";
		List<ErpCustomerSync> list = this.getHibernateTemplate().find(hql,new Object[]{id});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @param code
	 * @return
	 * @author machuan
	 * @date  2016年11月30日
	 */
	public List<ErpCustomerException> getCustomerExceptionByCode(String code) {
		String hql = "from ErpCustomerException where code=?";
		List<ErpCustomerException> list = this.getHibernateTemplate().find(hql, code);
		return list;
	}

	/**
	 * 根据场次号获取客户信息
	 * @author ybc
	 * @since 2016/12/20
	 */
	@SuppressWarnings("unchecked")
	public List<ErpCustomer> findCustomerByEventNo(String eveNo) {
		String hql = "from ErpCustomer where eventsNo=? and IS_DELETED=0 ";
		List<ErpCustomer> list = this.getHibernateTemplate().find(hql, eveNo);
		return list;
	}

	/**
	 * @description 根据ID修改会员的报告状态
	 * @param statusYm 状态
	 * @param id 会员ID
	 * @author YoumingDeng
	 * @since: 2016/12/14 14:55
	 */
	public void updateStatusYmById(Integer statusYm, String id){
		String sql = " update erp_customer set status_ym = ? where id = ? ";
		this.getJdbcTemplate().update(sql, new Object[]{statusYm, id});
	}

	/**
	 * @description 根据传入的参数修改会员报告状态
	 * @author YoumingDeng
	 * @since: 2016/12/14 17:07
	 */
	public void updateStatusYm(String sql, Object[] paramArr) {
		this.getJdbcTemplate().update(sql, paramArr);
	}

	/**
	 * @description 根据会员条码修改其报告状态
	 * @author YoumingDeng
	 * @since: 2016/12/16 2:32
	 */
	public void updateStatusYmByCode(Integer statusYm, String code, String name, String eventsNo){
		String sql = " update erp_customer set status_ym = ? where is_deleted = 0 and code = ? and NAME = ? and events_no = ? ";
		this.getJdbcTemplate().update(sql, new Object[]{statusYm, code, name, eventsNo});
	}
	
	
	/**
	 * （弘康）根据code和eventsNo查询用户
	 * @param idno
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016年12月30日15:50:58
	 */
	public List<ErpCustomer> getCustomerByIdnoAndName(String idno,String name){
		String hql = "from ErpCustomer where is_deleted = 0 and idno=? and name=?";
		return this.getHibernateTemplate().find(hql, new Object[]{idno,name});
	}
	
	/**
	 * （弘康）入库customer
	 * @param customer
	 * @author tangxing
	 * @date 2016-12-30下午4:00:47
	 */
	public String saveCustomer (ErpCustomer customer){
		return (String) this.getHibernateTemplate().save(customer);
	}

	/**
	 * 根据条件查询
	 * @param params 传入的Map键值对
	 * @param isExact 是否精确查找（只对字符串类型有效，ID为in查找，不用like）
	 * @return List ErpCustomer对象集
	 * @author DengYouming
	 * @since 2016-8-18 上午11:57:59
	 */
	public List<ErpCustomer> listCustomerByProps(Map<String,String> params, boolean isExact){
		List<ErpCustomer> list = null;
		Session session;
		Criteria criteria;
		if(!CollectionUtils.isEmpty(params)){
			session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			criteria = session.createCriteria(ErpCustomer.class);

			for (String key : params.keySet()) {
				String value = params.get(key);
				if(isExact){
					if(value.indexOf(",")!=-1){
						String[] valArr = value.split(",");
						criteria.add(Restrictions.in(key, valArr));
					}else{
						if(key.equalsIgnoreCase(ErpCustomer.F_ISDELETED)
								|| key.equals(ErpCustomer.F_STATUS)
								|| key.equals(ErpCustomer.F_STATUSYM)){
							criteria.add(Restrictions.eq(key, Integer.valueOf(value)));
						}
//						else if (key.equals(ErpCustomer.F_SAMPLINGDATE)
//								|| key.equals(ErpCustomer.F_CREATETIME)
//								|| key.equals(ErpCustomer.F_UPDATETIME)
//								|| key.equals("checkTime")) {
//							criteria.add(Restrictions.eq(key, Tools.getDateFromStr(value, Tools.DATE_FORMAT_DEFAULT));
//						}
						else {
							criteria.add(Restrictions.eq(key, value));
						}
					}
				}else{
					criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
				}
			}
//			for (String key : params.keySet()) {
//				String value = params.get(key);
//				if(key.equals(ErpCustomer.F_ID)){
//					String[] idArr;
//					if(value.indexOf(",")!=-1){
//						idArr = value.split(",");
//					}else{
//						idArr = new String[1] ;
//						idArr[0] = value;
//					}
//					criteria.add(Restrictions.in(ErpCustomer.F_ID, idArr));
//				}else if(key.equalsIgnoreCase(ErpCustomer.F_ISDELETED)){
//					criteria.add(Restrictions.eq(key, Integer.valueOf(value)));
//				}else{
//					if(isExact){
//						if(value.indexOf(",")!=-1){
//							String[] valArr = value.split(",");
//							criteria.add(Restrictions.in(key, valArr));
//						}else{
//							criteria.add(Restrictions.eq(key, value));
//						}
//					}else{
//						criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
//					}
//				}
//			}

			list = criteria.list();
		}
		return list;
	}


	/**
	 * @description
	 * @param params 传入的参数
	 * @return List
	 * @author YoumingDeng
	 * @since: 2016/12/6 14:51
	 */
	public List<ErpCustomer> listCustomerByProps(Map<String,String> params) throws Exception{
		List<ErpCustomer> list = null;
		if(!CollectionUtils.isEmpty(params)){
			list = this.listCustomerByProps(params, true);
		}
		return list;
	}

	/**
	 * @description 根据会员条码修改其报告状态
	 * @author YoumingDeng
	 * @since: 2016/12/16 2:32
	 */
	public void updateStatusYmByCode(Integer statusYm, String code){
		String sql = " update erp_customer set status_ym = ? where is_deleted = 0 and code = ? ";
		this.getJdbcTemplate().update(sql, new Object[]{statusYm, code});
	}

	/** 
	 * @author Carly
	 * @param deletedStatus 
	 * @since 2017年4月5日19:02:14
	 * @return 更新样本状态
	 */
	public boolean updateDeletedStatus(String customerId, String note, String deletedStatus) throws Exception{
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		String sql = " update erp_customer set note = ?, update_user_name = ?, update_time = ?, is_deleted = ? where id=? ";
		int count = this.getJdbcTemplate().update(sql, note, user.getUserName(), new Date(), deletedStatus, customerId);
		return count == 0 ? false : true;
	}

}
