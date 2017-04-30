/**
 * 
 */
package org.hpin.reportdetail.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.DateUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.reportdetail.dao.ErpReportExpressDao;
import org.hpin.reportdetail.entity.ErpBaseEmpInfo;
import org.hpin.reportdetail.entity.ErpReportExpress;
import org.hpin.reportdetail.entity.ErpReportExpressHis;
import org.hpin.reportdetail.entity.ErpReportExpressTemp;
import org.hpin.reportdetail.entity.vo.ErpReportCustomerVo;
import org.hpin.reportdetail.entity.vo.ErpReportDetailVo;
import org.hpin.reportdetail.entity.vo.ErpReportExpressBaseInfo;
import org.hpin.reportdetail.entity.vo.ErpReportExpressQuery;
import org.hpin.reportdetail.entity.vo.ErpReportExpressVo;
import org.hpin.reportdetail.entity.vo.ErpReportInfoVo;
import org.hpin.reportdetail.entity.vo.ErpReportMailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


/**
 * @author machuan
 * @date 2016年12月12日
 */
@Service(value = "org.hpin.reportdetail.service.ErpReportExpressService")
@Transactional()
@SuppressWarnings("rawtypes")
public class ErpReportExpressService extends BaseService{
	private Logger log = Logger.getLogger(ErpReportExpressService.class);
    @Autowired
    ErpReportExpressDao dao;
    @Value(value="${erpReportExpress.file.download.local.path}")
	private String localPath;
    @Value(value="${erpReportExpress.file.download.context}")
	private String downloadContext;
    @Value("#{setting['mail.account']}")
	private String sendMailAccount; //发件人账户;
	@Value("#{setting['mail.password']}")
	private String sendMailPassword; //发件人密码;
	@Value("#{setting['receive.receiveMailAccount']}")
	private String receiveMailAccount; //收件人账号;

	@Autowired
	private ErpCustomerService customerService; // add by YoumingDeng 2016-12-16

	/**
	 * @param page
	 * @param searchQuery
	 * @author machuan
	 * @date  2016年12月12日
	 */
	public void listReportDelivery(Page page, ErpReportExpressQuery searchQuery) throws Exception{
		//参数集合;
		List<Object> params = new ArrayList<Object>();
		String content = "select * from (select e.id,e.events_no eventsNo,e.event_date eventsDate,e.batchno batchNo,h.id commanyId,h.branch_commany branchCommany,"
				+"(select count(*) from erp_customer where events_no=e.events_no and is_deleted='0') as alreadyCount ,"
				+"(select count(*) from erp_report_express where events_no=e.events_no and express_delete='0') as haveReportCount "
				+"from erp_events e "
				+"left join hl_customer_relationship h on e.branch_company_id=h.id "
				+"where e.is_deleted='0' and h.is_deleted='0') where alreadyCount>0 ";
		StringBuilder sql = new StringBuilder(content);
		//参数处理
		dealParams(searchQuery, sql);
		sql.append(" ORDER BY eventsDate DESC ");
		//count
		page.setTotalCount(dao.findJdbcCount(sql, params));
		//list
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportExpressVo> rowMapper = new BeanPropertyRowMapper<ErpReportExpressVo>(ErpReportExpressVo.class);
		page.setResults(dao.findJdbcList(sql, params, rowMapper));
	}

	/**
	 * @param searchQuery
	 * @param contentb
	 * @author machuan
	 * @throws Exception 
	 * @date  2016年12月13日
	 */
	private void dealParams(ErpReportExpressQuery searchQuery, StringBuilder contentb) throws Exception {
		if(searchQuery!=null){
			if(StringUtils.isNotBlank(searchQuery.getBatchNo())){
				if("1".equals(searchQuery.getQueryType())){
					contentb.append(" AND batchNo = '").append(searchQuery.getBatchNo().trim()).append("' ");
				}else{
					contentb.append(" AND batchNo like '%").append(searchQuery.getBatchNo().trim()).append("%' ");
				}
			}
			if(StringUtils.isNotBlank(searchQuery.getBranchCommany())){
				contentb.append(" AND commanyId = '").append(searchQuery.getBranchCommany().trim()).append("' ");
			}
			if(StringUtils.isNotBlank(searchQuery.getStartTime())){
				contentb.append(" AND eventsDate >= to_date('").append(searchQuery.getStartTime().trim()).append("','yyyy-MM-dd') ");
			}
			if(StringUtils.isNotBlank(searchQuery.getEndTime())){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date endTime = df.parse(searchQuery.getEndTime().trim());
				Calendar c = Calendar.getInstance();
				c.setTime(endTime);
				c.add(Calendar.DATE, 1);
				String endTimeStr = df.format(c.getTime());
				contentb.append(" AND eventsDate < to_date('").append(endTimeStr).append("','yyyy-MM-dd') ");
			}
		}
	}
	
	/**
	 * @param page
	 * @param searchQuery
	 * @author machuan
	 * @date  2016年12月12日
	 */
	public void listReportDeliveryByCode(Page page, String customerCode) throws Exception{
		//参数集合;
		List<Object> params = new ArrayList<Object>();
		String content = "select * from (select e.id,e.events_no eventsNo,e.event_date eventsDate,e.batchno batchNo,h.id commanyId,h.branch_commany branchCommany,"
				+"(select count(*) from erp_customer where events_no=e.events_no and is_deleted='0') as alreadyCount ,"
				+"(select count(*) from erp_report_express where events_no=e.events_no and express_delete='0') as haveReportCount "
				+"from erp_events e "
				+"left join hl_customer_relationship h on e.branch_company_id=h.id "
				+"where e.is_deleted='0' and h.is_deleted='0' ";
		StringBuilder sql = new StringBuilder(content);
		//参数处理
		if(StringUtils.isNotBlank(customerCode)){
			sql.append(" and e.events_no = (select c.events_no from erp_customer c where c.code='");
			sql.append(customerCode);
			sql.append("')");
		}
		sql.append( ") where alreadyCount>0 ");
		sql.append(" ORDER BY eventsDate DESC ");
		//count
		page.setTotalCount(dao.findJdbcCount(sql, params));
		//list
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportExpressVo> rowMapper = new BeanPropertyRowMapper<ErpReportExpressVo>(ErpReportExpressVo.class);
		page.setResults(dao.findJdbcList(sql, params, rowMapper));
	}

	/**
	 * @param arrId
	 * @return
	 * @author machuan
	 * @date  2016年12月14日
	 */
	public List<ErpEvents> findEventsByIds(String[] arrId) {
		List<ErpEvents> list = new ArrayList<ErpEvents>();
		for(String id : arrId){
			ErpEvents erpEvents = (ErpEvents) dao.findById(ErpEvents.class, id);
			list.add(erpEvents);
		}
		return list;
	}

	/**
	 * 分页获取客户信息清单
	 * @param page
	 * @param arrId
	 * @param dept
	 * @param reportType
	 * @author machuan
	 * @param comboName 
	 * @date  2016年12月14日
	 */
	public void findBatchDealInfo(Page page, String[] arrId, String dept,
			String reportType, String comboName) throws Exception {
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append("select c.id,c.code,c.name,c.setmeal_name comboName,c.status_ym reportType,c.note,c.department dept,h.branch_commany as brachCommany,");
		//2016-12-27 重复寄送的数据只显示一条 edit by machuan
		sb.append("(select  r.express_no  from  erp_report_express r"
                          +" where c.events_no = r.events_no"
                          +" and c.code = r.code"
                          +" and c.name = r.name"
                          +" and r.Express_Delete = '0'and rownum=1 ) as expressNo,"
                          +" (select  r.express_date "
                		  +" from  erp_report_express r"
                		  +" where c.events_no = r.events_no"
                		  +" and c.code = r.code"
                		  +" and c.name = r.name"
                		  +" and r.Express_Delete = '0'and rownum=1 ) as expressDate");
		sb.append(" from erp_events e ");
		sb.append(" left join erp_customer c on e.events_no=c.events_no");
		sb.append(" left join hl_customer_relationship h on e.branch_company_id=h.id");
		sb.append(" where e.id in ");
		sb.append(ids);
		sb.append("and e.is_deleted='0' and c.is_deleted='0' and h.is_deleted='0'");
		sb.append(") t where 1=1 ");
		if(StringUtils.isNotBlank(dept)){
			sb.append(" and t.dept='");
			sb.append(dept.trim());
			sb.append("'");
		}
		if(StringUtils.isNotBlank(reportType)){
				sb.append(" and t.reportType='");
				sb.append(reportType.trim());
				sb.append("'");
		}
		if(StringUtils.isNotBlank(comboName)){
			sb.append(" and t.comboName='");
			sb.append(comboName.trim());
			sb.append("'");
		}
		sb.append(" ORDER BY t.code DESC ");
		//count
		List<Object> params = new ArrayList<Object>();
		page.setTotalCount(dao.findJdbcCount(sb, params));
		//list
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportInfoVo> rowMapper = new BeanPropertyRowMapper<ErpReportInfoVo>(ErpReportInfoVo.class);
		page.setResults(dao.findJdbcList(sb, params, rowMapper));
	}

	/**
	 * 根据场次id获取部门信息
	 * @param arrId
	 * @return
	 * @author machuan
	 * @date  2016年12月15日
	 */
	public List<Map<String, Object>> findDeptByIds(String[] arrId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select c.department  from erp_customer c where c.events_no in"
				+" (select e.events_no from erp_events e "
				+" where e.id in "
				+ids
				+ ") group by c.department";
//		String sql = "select l.department from hl_customer_relationship_link l "
//				+" where l.customer_relationship_id in"
//				+" (select e.branch_company_id from erp_events e where id in "
//				+ ids
//				+ " and e.is_deleted='0')";
		
		list = dao.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 根据场次Id 获取 批次号  批次日期，录入人数等信息
	 * @param eventID
	 * @return
	 * @author machuan
	 * @date  2016年12月15日
	 */
	public Map<String, Object> findEventInfoByNo(String eventID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select eee.batchno,eee.event_date eventDate,(select count(*) from erp_customer where eee.events_no=events_no and is_deleted='0') customerNum"
				+ " from erp_events eee where id = '"
				+ eventID
				+ "'";
		 map = dao.getJdbcTemplate().queryForMap(sql);
		 return map;
	}

	
	/**
	 * 录入快递信息
	 * @param request
	 * @param expressCommany
	 * @param expressNo
	 * @param expressWeight
	 * @param expressCost
	 * @param email
	 * @param eventNo
	 * @param customerID
	 * @param reportID
	 * @param userName
	 * @throws Exception
	 * @author machuan
	 * @param userName2 
	 * @return
	 * @date  2016年12月20日
	 */
	public String expressEntry(HttpServletRequest request, String expressCommany, String expressNo, String expressWeight, String expressCost,
			String eventNo, String reportCode, String reportID, String allEventsIds, String userName) throws Exception {
		//如果传过来的reportID不为空  即为变更操作 进行变更之后不执行后面代码
		/*if(StringUtils.isNotBlank(reportID)){
			changeExpress(request,reportID,expressCommany,expressNo,expressWeight,expressCost,userName);
			return reportID;
		}*/
		List<ErpReportCustomerVo> customers = new ArrayList<ErpReportCustomerVo>();
		if(StringUtils.isNotBlank(eventNo)){
			//根据场次号拿到该场次下的所有customer
			customers = findAllCustomerByEventId(eventNo);
		}
		//2017-01-04 批量处理 修改  根据
		if(StringUtils.isNotBlank(reportCode)){
			//根据customerId 拿到所有客户信息
			customers = findAllCustomerByCustomerId(reportCode);
		}
		if(StringUtils.isNotBlank(allEventsIds)){
			//根据多个场次ID 拿到所有客户信息
			customers = findAllCustomerByEventsIds(allEventsIds);
		}
		Date now = new Date();
		//快递费用均摊，四舍五入保留两位小数
		BigDecimal expressCosts = new BigDecimal(expressCost);
		DecimalFormat df = new DecimalFormat("#.00");
		BigDecimal cost = new BigDecimal(df.format(Double.valueOf(expressCost)/customers.size()));
		//每一批快递生成一个独立的批次号  当前时间毫秒数  +  随机1-4位数
		String reportId = now.getTime()+""+(int)(Math.random()*10000);
		for(ErpReportCustomerVo customerVo : customers){
			ErpReportExpress express = new ErpReportExpress();
			express.setEventsNo(customerVo.getEventsNo());
			express.setCode(customerVo.getCode());
			express.setName(customerVo.getName());
			express.setExpressNo(expressNo);
			express.setTotalCost(expressCosts);
			express.setExpressCost(cost);
			express.setExpressCommany(expressCommany);
			express.setExpressWeight(expressWeight);
			express.setExpressDate(now);
			express.setUserName(userName);
			express.setExpressDelete("0");
			express.setCreateTime(now);
			express.setBatchNo(customerVo.getBatchNo());
			express.setReportId(reportId);
			dao.save(express);
		}
		return reportId;
	}
	

	/**
	 * 处理事务让其不同步提交
	 * create by henry.xu 2017年1月11日
	 * @param reportID
	 */
	public void dealUpdateState(String reportID) {
		String sql = "select code, name, events_No eventsNo from ERP_REPORT_EXPRESS where REPORT_ID='"+reportID+"' ";
		List<Map<String, Object>> list = this.dao.getJdbcTemplate().queryForList(sql);
		for (Map<String, Object> map : list) {
			//修改会员报告状态为 statusYm=已寄送 add by YoumingDeng 2016-12-16 start
			customerService.updateStatusYmByCode(PropsUtils.getInt("status","statusYm.yjs"),
					(String)map.get("code"),(String)map.get("name"), (String)map.get("eventsNo"));
			//修改会员报告状态为 statusYm=已寄送 add by YoumingDeng 2016-12-16 end
			
		}
		
	}

	/**
	 * @param allEventsIds
	 * @return
	 * @author machuan
	 * @date  2016年12月30日
	 */
	private List<ErpReportCustomerVo> findAllCustomerByEventsIds(String allEventsIds) {
		String []arrId = allEventsIds.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select e.events_no eventsNo,e.code,e.name, "
				+" (select c.batchno from erp_events c where c.events_no=e.events_no"
				+ " and c.is_deleted='0') batchNo "
				+" from erp_customer e where e.is_deleted='0' and e.events_no in"
				+"(select s.events_no from erp_events s where s.id in "
				+ids
				+ ")";
		BeanPropertyRowMapper<ErpReportCustomerVo> rowMapper = new BeanPropertyRowMapper<ErpReportCustomerVo>(ErpReportCustomerVo.class);
		return dao.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 根据 reportID 进行变更操作
	 * @param reportID
	 * @author machuan
	 * @param userName 
	 * @param email 
	 * @param expressCost 
	 * @param expressWeight 
	 * @param expressNo 
	 * @param expressCommany 
	 * @date  2016年12月16日
	 */
	@SuppressWarnings("unchecked")
	public void changeExpress(HttpServletRequest request,String reportID, String expressCommany,
			String expressNo, String expressWeight, String expressCost, String userName,String reason) throws Exception{
		//根据 reportID 拿到 所有的 快递信息
		List<ErpReportExpress> list = new ArrayList<ErpReportExpress>();
		String hql = "from ErpReportExpress where reportId=? and expressDelete=?";
		list = dao.getHibernateTemplate().find(hql,reportID,"0");
		//快递费用均摊，四舍五入保留两位小数
		BigDecimal expressCosts = new BigDecimal(expressCost);
		DecimalFormat df = new DecimalFormat("#.00");
		BigDecimal cost = new BigDecimal(df.format(Double.valueOf(expressCost)/list.size()));
		//20170405新增需求 保存该条数据进入历史表
		ErpReportExpressHis his = new ErpReportExpressHis();
		his.setExpressNo(list.get(0).getExpressNo());
		his.setExpressCompany(list.get(0).getExpressCommany());
		his.setExpressCost(list.get(0).getTotalCost());
		his.setExpressWeight(list.get(0).getExpressWeight());
		his.setExpressDate(list.get(0).getExpressDate());
		his.setUserName(userName);
		his.setReason(reason);
		his.setReportId(reportID);
		his.setCreateUserName(userName);
		his.setCreateTime(new Date());
		dao.save(his);
		for(ErpReportExpress express : list){
			express.setExpressCommany(expressCommany);
			express.setExpressNo(expressNo);
			express.setExpressWeight(expressWeight);
			express.setTotalCost(expressCosts);
			express.setExpressCost(cost);
		}
	}

	/**
	 * 根据reportCode拿到所有customer
	 * @param reportCode
	 * @return
	 * @author machuan
	 * @date  2016年12月15日
	 */
	private List<ErpReportCustomerVo> findAllCustomerByCustomerId(String reportCode) throws Exception{
		String sql = "select e.events_no eventsNo,e.code,e.name, "
				+" (select c.batchno from erp_events c where c.events_no=e.events_no"
				+ " and c.is_deleted='0') batchNo "
				+" from erp_customer e where e.id in ("
				+"select t.customer_id from erp_report_express_temp t where t.report_code='"
				+reportCode
				+ "') and e.is_deleted='0'";
		BeanPropertyRowMapper<ErpReportCustomerVo> rowMapper = new BeanPropertyRowMapper<ErpReportCustomerVo>(ErpReportCustomerVo.class);
		return dao.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 根据场次号拿到所有的customer
	 * @param eventNo
	 * @return
	 * @author machuan
	 * @date  2016年12月15日
	 */
	private List<ErpReportCustomerVo> findAllCustomerByEventId(String eventNo) throws Exception{
		String sql = "select e.events_no eventsNo,e.code,e.name, "
				+" (select c.batchno from erp_events c where c.events_no='"
				+eventNo
				+ "' and c.is_deleted='0') batchNo "
				+" from erp_customer e where e.events_no='"
				+eventNo
				+"' and e.is_deleted='0'";
		BeanPropertyRowMapper<ErpReportCustomerVo> rowMapper = new BeanPropertyRowMapper<ErpReportCustomerVo>(ErpReportCustomerVo.class);
		return dao.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 分页获取邮寄明细页面数据
	 * @param page
	 * @param searchQuery
	 * @author machuan
	 * @throws Exception 
	 * @date  2016年12月16日
	 */
	public void expressDetail(Page page, ErpReportExpressQuery searchQuery) throws Exception {
		//参数集合;
		List<Object> params = new ArrayList<Object>();
		String content = "select * from"
				+" (select e.report_id reportId,e.express_date expressDate,e.total_cost totalCost,e.express_weight expressWeight,count(*) alreadyCount,"
				+" (select listagg( ccc.batch_no,',') within GROUP (order by batch_no) from "
				+" (select cc.batch_no,cc.report_id "
				+" from erp_report_express cc where cc.express_delete='0'"
				+" group by cc.batch_no,cc.report_id"
				+" ) ccc where ccc.report_id=e.report_id) batchNo"
				+" from erp_report_express e where e.express_delete='0'";
		StringBuilder sql = new StringBuilder(content);
		//邮寄明细页面 参数处理
		detailDealParams(searchQuery, sql);
		sql.append(" group by e.report_id,e.express_date,e.total_cost,e.express_weight) where 1=1 ");
		sql.append(" ORDER BY expressDate DESC ");
		//count
		page.setTotalCount(dao.findJdbcCount(sql, params));
		//list
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReportDetailVo> rowMapper = new BeanPropertyRowMapper<ErpReportDetailVo>(ErpReportDetailVo.class);
		page.setResults(dao.findJdbcList(sql, params, rowMapper));
	}

	/**
	 * @param searchQuery
	 * @param sql
	 * @author machuan
	 * @throws Exception 
	 * @date  2016年12月16日
	 */
	private void detailDealParams(ErpReportExpressQuery searchQuery, StringBuilder sql) throws Exception {
		if(searchQuery!=null){
			if(StringUtils.isNotBlank(searchQuery.getBatchNo())){
				if("1".equals(searchQuery.getQueryType())){
					sql.append(" AND e.batch_no = '").append(searchQuery.getBatchNo().trim()).append("' ");
				}else{
					sql.append(" AND e.batch_no like '%").append(searchQuery.getBatchNo().trim()).append("%' ");
				}
			}
			if(StringUtils.isNotBlank(searchQuery.getStartTime())){
				sql.append(" AND e.express_date >= to_date('").append(searchQuery.getStartTime().trim()).append("','yyyy-MM-dd') ");
			}
			if(StringUtils.isNotBlank(searchQuery.getEndTime())){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date endTime = df.parse(searchQuery.getEndTime().trim());
				Calendar c = Calendar.getInstance();
				c.setTime(endTime);
				c.add(Calendar.DATE, 1);
				String endTimeStr = df.format(c.getTime());
				sql.append(" AND e.express_date < to_date('").append(endTimeStr).append("','yyyy-MM-dd') ");
			}
		}
		
	}

	/**
	 * @param reportId
	 * @author machuan
	 * @date  2016年12月16日
	 */
	public void cancelExpress(String reportId) throws Exception{
		String sql = "update erp_report_express e set express_delete ='1' where e.report_id='"
				+ reportId
				+ "'";
		dao.getJdbcTemplate().update(sql);
		//edit by machuan 2016-12-28
		String querySql = " select c.events_no eventsNo,c.code,c.name from (select t.events_no,t.code,t.name,"
						 +" (select count(*) from erp_report_express e "
						 +" where e.events_no=t.events_no and e.code=t.code and e.name=t.name "
						 +" and e.express_delete='0' and e.report_id <>'"
						 +reportId
						 + "') cntNum"
						 +" from erp_report_express t where t.report_id ='"
						 +reportId
						 + "') c where c.cntNum=0";
		BeanPropertyRowMapper<ErpReportCustomerVo> rowMapper = new BeanPropertyRowMapper<ErpReportCustomerVo>(ErpReportCustomerVo.class);
		List<ErpReportCustomerVo> list = dao.getJdbcTemplate().query(querySql,rowMapper);
		//如果已经没有寄送的信息，则撤销时statusYm回滚到已打印状态
		if(!CollectionUtils.isEmpty(list)) {
		    // modify by Damian 20161228
			customerService.updateStatusYmForExpress(list, PropsUtils.getInt("status","statusYm.ydy"));
		}
	}

	/**
	 *  邮件通知功能
	 * 	对该次寄出报告的客户信息生成Excel，包含批次号，条形码，客户姓名，性别，年龄，支公司信息，部门，套餐，采样日期，保险公司营销员，快递单号。
	 * @return
	 * @author machuan
	 * @param email
	 * @param userName
	 * @date  2016年12月16日
	 */
	public void sendEmail(HttpServletRequest request,String reportIds, String baseInfoIds, String userName){
		try{
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			List<ErpReportMailVo> erpReportMailVoList = new ArrayList<ErpReportMailVo>();
			//根据批次ID 找到该批次下的所有信息
			//20170106 reportIds 需要用，分割  
			erpReportMailVoList = dao.findErpReportMailVoByReportID(reportIds);
			Map<String, Object> map = createQRCodeExcelPath(request,erpReportMailVoList);
			String path = (String) map.get("path");
			String excleName = (String) map.get("excleName");
			File file = new File(path, excleName);
			List<File> files = new ArrayList<File>();
			files.add(file);
			log.info("***************报告寄送--邮件发送开始****************");
			MailEntity mail = new MailEntity();
			//设置服务器地址
			mail.setHost("smtp.exmail.qq.com");
			//设置账号
			mail.setUsername(sendMailAccount);
			//发件人的邮箱
			mail.setSender(sendMailAccount);
			//发件人的密码
			mail.setPassword(sendMailPassword);
			//收件人的邮箱
			List<String> receiver = new ArrayList<String>();
			//收件人邮箱为前端传过来的邮箱地址
			//根据 baseInfoIds 拿到所有email
			List<ErpBaseEmpInfo> baseEmpInfos = dao.getBaseInfoByIds(baseInfoIds);
			if(baseEmpInfos!=null){
				for(ErpBaseEmpInfo empInfo : baseEmpInfos){
					receiver.add(empInfo.getEmail());
				}
			}
			//TODO 正式环境添加 收件人邮箱yalingliu@healthlink.cn
			receiver.add("yalingliu@healthlink.cn");
			log.info("报告寄送--收件人邮箱："+baseInfoIds);
			mail.setReceiver(receiver);
			//附件
			mail.setAttachMents(files);
			//主题
			mail.setSubject(df.format(new Date())+"--快递寄送明细！");
			//正文信息
			String text = dao.getTextForMail(reportIds);
			mail.setMessage(text);
			log.info("报告寄送--正文信息："+text);
			mail.setName(userName);
			MailUtil.send(mail);
			log.info("***************报告寄送--邮件发送结束****************");
		}catch(Exception e){
			log.error("sendEmail--error: ",e);
		}

	}
	
	 /* ************导出Excel************ */
    
	/**
	 * @param request
	 * @param erpReportMailVoList
	 * @param expressNo
	 * @return
	 * @author machuan
	 * @date  2016年12月16日
	 */
	public Map<String, Object> createQRCodeExcelPath(HttpServletRequest request, 
			List<ErpReportMailVo> erpReportMailVoList) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String curFilePath = localPath+timePath+"_"+erpReportMailVoList.size()+File.separator;//存放位置
		String str = timePath+"_"+erpReportMailVoList.size()+File.separator;
		//构建Excel文件
		String excleName = "快递寄送明细.xls";	//文件名
		createExSettleXls(curFilePath.toString(),excleName,erpReportMailVoList);
		String downloadurl = contextUrl+str+ excleName;		//下载路径
		map.put("downloadurl", downloadurl);
		map.put("path", curFilePath.toString());
		map.put("excleName", excleName);
		return map;
	}
   
  	
  	/**
  	 * 根据批次生成快递详情信息Excel
  	 * @param path
  	 * @param filename
  	 * @param erpReportMailVoList
  	 * @author machuan
  	 * @date  2016年12月16日
  	 */
  	public void createExSettleXls(String path ,String filename, List<ErpReportMailVo> erpReportMailVoList) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("快递信息");
//			hssfSheet1.setColumnWidth(0, 20*256);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setWrapText(true);
			cellStyle.setFont(font);
			//构建Excel
			//第一行  表头
			HSSFRow row0 = hssfSheet1.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("批次号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("条形码");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("客户姓名");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("性别");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("年龄");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("支公司信息");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("部门");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("套餐");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("采样日期");
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("保险公司营销员");
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("快递单号");
			
			for(int rowNum=0;rowNum<erpReportMailVoList.size();rowNum++){
				HSSFRow valueRow = hssfSheet1.createRow(rowNum+1);
				String value = erpReportMailVoList.get(rowNum).getBatchNo();
				createCell(valueRow, 0, value, cellStyle);
				String value1 = erpReportMailVoList.get(rowNum).getCode();
				createCell(valueRow, 1, value1, cellStyle);
				String value2 = erpReportMailVoList.get(rowNum).getName();
				createCell(valueRow, 2, value2, cellStyle);
				String value3 = erpReportMailVoList.get(rowNum).getSex();
				createCell(valueRow, 3, value3, cellStyle);
				String value4 = erpReportMailVoList.get(rowNum).getAge();
				createCell(valueRow, 4, value4, cellStyle);
				String value5 = erpReportMailVoList.get(rowNum).getBranchCompany();
				createCell(valueRow, 5, value5, cellStyle);
				String value6 = erpReportMailVoList.get(rowNum).getDept();
				createCell(valueRow, 6, value6, cellStyle);
				String value7 = erpReportMailVoList.get(rowNum).getCombo();
				createCell(valueRow, 7, value7, cellStyle);
				if(erpReportMailVoList.get(rowNum).getSamplingDate()!=null){
					String value8 = df.format(erpReportMailVoList.get(rowNum).getSamplingDate());
					createCell(valueRow, 8, value8, cellStyle);
				}
				String value9 = erpReportMailVoList.get(rowNum).getSaleMan();
				createCell(valueRow, 9, value9, cellStyle);
				String value10 = erpReportMailVoList.get(rowNum).getExpressNo();
				createCell(valueRow, 10, value10, cellStyle);
				
			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
		}
		
		
	}
	/**
	 *  设置字体格式
	 * @param wb
	 * @param bold
	 * @param fontName
	 * @param isItalic
	 * @param hight
	 * @return
	 * @author machuan
	 * @date  2016年12月16日
	 */
	public static Font createFonts(Workbook wb, short bold, String fontName,boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}
	/**
	 * 设置内容
	 * @param row
	 * @param column
	 * @param value
	 * @param cellStyle
	 * @author machuan
	 * @date  2016年12月16日
	 */
	public static void createCell(Row row, int column,String value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value==null?"":value);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * @param eventsIds
	 * @return
	 * @author machuan
	 * @date  2016年12月20日
	 */
	public boolean findBranchCompanyByEventIds(String eventsIds) throws Exception {
		String []arrId = eventsIds.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select e.branch_company_id from erp_events e where e.id in "
				+ids;
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
		Set<String> set = new HashSet<String>();
		for(Map<String, Object> map : list){
			set.add((String) map.get("branch_company_id"));
		}
		return set.size()>1;
	}

	/**
	 * @param customerIds
	 * @return
	 * @author machuan
	 * @date  2016年12月20日
	 */
	public boolean findRepeatExpressByCustomerIds(String customerIds) {
		String []arrId = customerIds.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select (select count(*) from erp_report_express r "
					+" where c.events_no=r.events_no and c.code=r.code and c.name=r.name and r.Express_Delete='0') repeatNum "
					+" from erp_customer c where c.id in"
					+ids;
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
		for(Map<String, Object> map : list){
			if(((BigDecimal)map.get("repeatNum")).intValue()>0){
				return true;
			};
		}
		return false;
	}

	/**
	 * @param eventNo
	 * @return
	 * @author machuan
	 * @date  2016年12月21日
	 */
	public boolean findRepeatExpressByeventNo(String eventNo) {
		String sql = "select count(*) from erp_report_express e where e.express_delete='0' and e.events_no='"
					+eventNo
					+"'";
		return dao.getJdbcTemplate().queryForInt(sql)>0;
	}
	
	/**
	 * @param eventsIdss
	 * @return
	 * @author machuan
	 * @date  2016年12月30日
	 */
	public boolean findRepeatExpressByeventIds(String eventsIdss) {
		String []arrId = eventsIdss.split(",");
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select count(*) from erp_report_express e where e.express_delete='0' and e.events_no in "
					+"(select s.events_no from erp_events s where s.id in "
					+ids
					+ ")";
		return dao.getJdbcTemplate().queryForInt(sql)>0;
	}

	/**
	 * 根据快递批次号查询录入的基本信息
	 * @param reportId
	 * @return
	 * @author machuan
	 * @date  2016年12月21日
	 */
	public ErpReportExpressBaseInfo findExpressBaseInfoByReportId(String reportId) throws Exception {
		String sql = "select e.express_commany expressCommany,e.express_no expressNo,e.express_weight expressWeight,e.total_cost expressCost"
					+" from erp_report_express e where e.report_id='"
					+reportId
					+"' and e.express_delete='0'";
		BeanPropertyRowMapper<ErpReportExpressBaseInfo> rowMapper = new BeanPropertyRowMapper<ErpReportExpressBaseInfo>(ErpReportExpressBaseInfo.class);
		log.info("sqlsqlsql:"+sql);
		return dao.getJdbcTemplate().query(sql, rowMapper).get(0);
	}
	
	 /* ************导出Excel 报告寄送 场次信息************ */
   
	public String exportExcelForReport(HttpServletRequest request, List<ErpReportInfoVo> list,
			String eventsId) {
		//TODO 线上环境屏蔽
//		StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
//		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String curFilePath = localPath+timePath+"_"+eventsId+File.separator;//存放位置
//		String str = timePath+"_"+eventsId+File.separator;
    	log.info("exportExcelForReport--curFilePath:"+curFilePath);
		//构建Excel文件
		String excleName = eventsId+"场次信息"+".xls";			//文件名
		createExcelForReport(curFilePath.toString(),excleName,list);
		
//		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return curFilePath+excleName;
		//测试环境
//		return downloadurl;
	}
	
  	public void createExcelForReport(String path ,String filename, List<ErpReportInfoVo> rowList) {
		try {
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			hssfSheet1.setColumnWidth(0, 15*256);
			hssfSheet1.setColumnWidth(1, 15*256);
			hssfSheet1.setColumnWidth(2, 15*256);
			hssfSheet1.setColumnWidth(3, 15*256);
			hssfSheet1.setColumnWidth(4, 15*256);
			hssfSheet1.setColumnWidth(5, 15*256);
			hssfSheet1.setColumnWidth(6, 15*256);
			hssfSheet1.setColumnWidth(7, 15*256);
			hssfSheet1.setColumnWidth(8, 15*256);
			
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 260);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setWrapText(true);
			cellStyle.setFont(font);
			
			//构建Excel
			HSSFRow row0 = hssfSheet1.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("条形码");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("姓名");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("套餐名称");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("支公司");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("部门");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("快递单号");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("寄送日期");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("客户状态");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("备注");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow rowValue = hssfSheet1.createRow(rowNum+1);
				ErpReportInfoVo infoVo = rowList.get(rowNum);
				createCell(rowValue, 0, infoVo.getCode(), cellStyle);
				createCell(rowValue, 1, infoVo.getName(), cellStyle);
				createCell(rowValue, 2, infoVo.getComboName(), cellStyle);
				createCell(rowValue, 3, infoVo.getBrachCommany(), cellStyle);
				createCell(rowValue, 4, infoVo.getDept(), cellStyle);
				createCell(rowValue, 5, infoVo.getExpressNo(), cellStyle);
				if(infoVo.getExpressDate()!=null){
					createCell(rowValue, 6, df.format(infoVo.getExpressDate()), cellStyle);
				}
				String rType = infoVo.getReportType();
				createCell(rowValue, 7,PropsUtils.getString("status","statusYm_"+rType), cellStyle);
				createCell(rowValue, 8, infoVo.getNote(), cellStyle);
			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (Exception e) {
			log.error("createExcelForReport writeXls", e);
		}
	}
	
	public List<ErpReportInfoVo> findInfoForExport(String dept, String reportType,
			String[] arrId,String comboName)throws Exception{
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append("select c.id,c.code,c.name,c.setmeal_name comboName,c.status_ym reportType,c.note,c.department dept,h.branch_commany as brachCommany,");
		//2016-12-27 重复寄送的数据只显示一条 edit by machuan
		sb.append("(select  r.express_no  from  erp_report_express r"
                          +" where c.events_no = r.events_no"
                          +" and c.code = r.code"
                          +" and c.name = r.name"
                          +" and r.Express_Delete = '0'and rownum=1 ) as expressNo,"
                          +" (select  r.express_date "
                		  +" from  erp_report_express r"
                		  +" where c.events_no = r.events_no"
                		  +" and c.code = r.code"
                		  +" and c.name = r.name"
                		  +" and r.Express_Delete = '0'and rownum=1 ) as expressDate");
		sb.append(" from erp_events e ");
		sb.append(" left join erp_customer c on e.events_no=c.events_no");
		sb.append(" left join hl_customer_relationship h on e.branch_company_id=h.id");
		sb.append(" where e.id in ");
		sb.append(ids);
		sb.append("and e.is_deleted='0' and c.is_deleted='0' and h.is_deleted='0'");
		sb.append(") t where 1=1 ");
		if(StringUtils.isNotBlank(dept)){
			sb.append(" and t.dept='");
			sb.append(dept.trim());
			sb.append("'");
		}
		if(StringUtils.isNotBlank(reportType)){
				sb.append(" and t.reportType='");
				sb.append(reportType.trim());
				sb.append("'");
		}
		if(StringUtils.isNotBlank(comboName)){
			sb.append(" and t.comboName='");
			sb.append(comboName.trim());
			sb.append("'");
		}
		sb.append(" ORDER BY t.code DESC ");
		BeanPropertyRowMapper<ErpReportInfoVo> rowMapper = new BeanPropertyRowMapper<ErpReportInfoVo>(ErpReportInfoVo.class);
		List<ErpReportInfoVo> list = dao.getJdbcTemplate().query(sb.toString(), rowMapper);
		return list;
	}

	/**
	 * 根据场次id 或者客户的所有套餐
	 * @param arrId
	 * @return
	 * @author machuan
	 * @date  2016年12月30日
	 */
	public List<Map<String, Object>> findCombosByEventIds(String[] arrId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select c.setmeal_name combo from erp_customer c where c.events_no in"
					+" (select e.events_no from erp_events e "
					+" where e.id in "
					+ids
					+ ") group by c.setmeal_name";
		
		list = dao.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 根据场次ID 保存该场次下所有的客户信息进临时表
	 * @param split
	 * @author machuan
	 * @param reportCode 
	 * @date  2017年1月4日
	 */
	public void saveExpressTemp(String[] arrId, String reportCode) {
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select c.id customerId from erp_customer c where c.is_deleted='0' and c.events_no in "
					+"(select e.events_no from erp_events e where e.id in "
					+ids
					+ ")";	
		BeanPropertyRowMapper<ErpReportExpressTemp> rowMapper = new BeanPropertyRowMapper<ErpReportExpressTemp>(ErpReportExpressTemp.class);
		List<ErpReportExpressTemp> list = dao.getJdbcTemplate().query(sql, rowMapper);
		Date now = new Date();
		for(ErpReportExpressTemp temp : list){
			temp.setReportCode(reportCode);
			temp.setCreateTime(now);
			dao.save(temp);
		}
	}

	/**
	 * 根据 reportCode 查询 所有的 customerId
	 * @param reportCode
	 * @return
	 * @author machuan
	 * @date  2017年1月4日
	 */
	public String findCustomerInfo(String reportCode) throws Exception {
		String sql = "select t.customer_id customerId from erp_report_express_temp t where t.report_code ='"
					+reportCode
					+"'";
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
		String customerIds = "";
		if(list!=null){
			for(Map<String, Object> map : list){
				customerIds+=","+map.get("customerId");
			}
		}
		if(customerIds.length()>1){
			customerIds = customerIds.substring(1);
		}
		return customerIds;
	}

	/**
	 * 
	 * @param c_reportCode
	 * @param c_customerId
	 * @param c_flag
	 * @author machuan
	 * @date  2017年1月4日
	 */
	@SuppressWarnings("unchecked")
	public void saveOrDelTemp(String c_reportCode, String c_customerId, String c_flag) {
		String hql = " from ErpReportExpressTemp where customerId=? and reportCode=?";
		List<ErpReportExpressTemp> list = dao.getHibernateTemplate().find(hql, c_customerId,c_reportCode);
		//c_flag为true 是新增
		if("true".equals(c_flag)){
			//判断表中是否存在该条数据 不存在就新增
			if(list!=null&&list.size()>0){
				return;
			}else{
				ErpReportExpressTemp temp = new ErpReportExpressTemp();
				temp.setCustomerId(c_customerId);
				temp.setReportCode(c_reportCode);
				temp.setCreateTime(new Date());
				dao.save(temp);
			}
		}else{
			//删除
			if(list!=null&&list.size()>0){
				dao.delete(list.get(0));
			}
		}
		
	}

	/**
	 * @param arrId
	 * @return
	 * @author machuan
	 * @date  2017年1月6日
	 */
	public List<ErpReportDetailVo> showChooseExpress(String[] arrId) {
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select * from"
				+" (select e.report_id reportId,e.express_date expressDate,e.total_cost totalCost,e.express_weight expressWeight,count(*) alreadyCount,"
				+" (select listagg( ccc.batch_no,',') within GROUP (order by batch_no) from "
				+" (select cc.batch_no,cc.report_id "
				+" from erp_report_express cc where cc.express_delete='0'"
				+" group by cc.batch_no,cc.report_id"
				+" ) ccc where ccc.report_id=e.report_id) batchNo"
				+" from erp_report_express e where e.express_delete='0'"
				+" group by e.report_id,e.express_date,e.total_cost,e.express_weight) where 1=1"
				+" and reportId in"
				+ids
				+" ORDER BY expressDate DESC ";
		BeanPropertyRowMapper<ErpReportDetailVo> rowMapper = new BeanPropertyRowMapper<ErpReportDetailVo>(ErpReportDetailVo.class);
		return dao.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * @param page
	 * @param name
	 * @author machuan
	 * @date  2017年1月6日
	 */
	public List<ErpBaseEmpInfo> findBaseEmpInfo(Page page,Map searchMap) {
		return dao.findBaseEmpInfo(page, searchMap);
	}

	/**
	 * @param arrId
	 * @return
	 * @author machuan
	 * @date  2017年1月6日
	 */
	public List<ErpBaseEmpInfo> showChooseEmail(String[] arrId) {
		String ids = "(";
		for(String id : arrId){
			ids+="'"+id+"',";
		}
		if(ids.length()>2){
			ids = ids.substring(0,ids.length()-1);
		}else{
			ids="(''";
		}
		ids+=")";
		String sql = "select * from erp_base_emp_info where id in"+ids;
		BeanPropertyRowMapper<ErpBaseEmpInfo> rowMapper = new BeanPropertyRowMapper<ErpBaseEmpInfo>(ErpBaseEmpInfo.class);
		return dao.getJdbcTemplate().query(sql, rowMapper);
	}
 
	/**
	 * @param affix
	 * @param affixFileName
	 * @author machuan
	 * @throws Exception 
	 * @date  2017年1月12日
	 */
	public Map<String, Object> saveReportExpress(File file, String afileName) throws Exception {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		String userName = currentUser.getUserName();
		String fileName = "";
		Set<String> set = new HashSet<String>(); 
		Map<String,Object> map = new HashMap<String, Object>();
		List<ErpReportExpress> list = new ArrayList<ErpReportExpress>();
		if (afileName != null) {
			fileName = file.getAbsolutePath();
			int count = 0;
			InputStream is = null;
			XSSFWorkbook xssfWorkbook = null;
			try {
				is = new FileInputStream(fileName);
				xssfWorkbook = new XSSFWorkbook(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 循环工作表Sheet
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Date createTime = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			//随机的报告单号
			int reportIdEnd = (int)(Math.random()*10000);
			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				//该行如果为空就跳到下一行
				if (xssfRow == null||(StringUtils.isBlank(getValue(xssfRow.getCell(5)))&&StringUtils.isBlank(getValue(xssfRow.getCell(6)))&&StringUtils.isBlank(getValue(xssfRow.getCell(2)))&&(getValue(xssfRow.getCell(1))).length()<=4)) {
					continue;
				}
				String code = getValue(xssfRow.getCell(1));
				String name = getValue(xssfRow.getCell(2));
				String batchNo = getValue(xssfRow.getCell(3));
				//20170224新增需求--批次号与客户信息做匹配判断
				if(dao.isMatchingByCustomer(batchNo,name,code)){
					map.put("flag", false);
					map.put("message", "第"+rowNum+"行，批次号："+batchNo+"，姓名："+name+"，条码："+code+"不匹配！");
					log.info("saveReportExpress--error:  "+ map.toString());
					return map;
				}
				String expressCompay = getValue(xssfRow.getCell(4));
				String expressNo = getValue(xssfRow.getCell(5));
				String totalCost = getValue(xssfRow.getCell(6));
				String expressCost = getValue(xssfRow.getCell(7));
				
				//快递费用转为数字类型
				BigDecimal totalCosts = new BigDecimal(totalCost);
				BigDecimal expressCosts = new BigDecimal(expressCost);
				
				String expressWeight = getValue(xssfRow.getCell(8));
				String expressDateStr = getValue(xssfRow.getCell(9));
				//20170224 所有项都为必填项
				if(StringUtils.isBlank(expressCompay)||StringUtils.isBlank(expressNo)||StringUtils.isBlank(totalCost)||StringUtils.isBlank(expressCost)||StringUtils.isBlank(expressWeight)||StringUtils.isBlank(expressDateStr)){
					map.put("flag", false);
					map.put("message", "第"+rowNum+"行，数据不完整！");
					log.info("saveReportExpress--error:  "+ map.toString());
					return map;
				}
				Date expressDate = df.parse(expressDateStr);
				ErpReportExpress express = new ErpReportExpress();
				express.setCode(code);
				express.setName(name);
				String eventsNo = findEventsNoByParams(name,code);
				express.setEventsNo(eventsNo);
				if(StringUtils.isNotBlank(batchNo)){
					express.setBatchNo(batchNo);
				}else{
					express.setBatchNo(findBatchNoByParams(eventsNo));
				}
				
				express.setExpressCommany(expressCompay);
				express.setExpressNo(expressNo);
				express.setTotalCost(totalCosts);
				express.setExpressCost(expressCosts);
				express.setExpressWeight(expressWeight);
				express.setExpressDate(expressDate);
				express.setCreateTime(createTime);
				express.setUserName(userName);
				express.setExpressDelete("0");
				//每一批快递生成根据快递单号+总金额+随机数
				String reportId = expressNo+totalCost+reportIdEnd;
				set.add(reportId);
				express.setReportId(reportId);
				list.add(express);
				count++;
			}
			if(list!=null&&list.size()>0){
				for(ErpReportExpress express:list){
					dao.save(express);
				}
			}
			map.put("flag", true);
			map.put("reportId", set);
			map.put("message", "导入成功"+count+"条数据！");
			log.info("saveReportExpress--count："+count);
		}
		return map;
	}
		
	/**
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2017年1月12日
	 */
	private String findBatchNoByParams(String eventsNo) {
		String batchNo = "";
		if(StringUtils.isNotBlank(eventsNo)){
			String sql = "select e.batchno from erp_events e where e.events_no='"+eventsNo+"' and e.is_deleted='0''";
			List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
			if(list!=null&&list.size()>0){
				batchNo = (String) list.get(0).get("batchno");
			}
		}
		return batchNo;
	}

	/**
	 * @param name
	 * @param code
	 * @return
	 * @author machuan
	 * @date  2017年1月12日
	 */
	private String findEventsNoByParams(String name, String code) {
		String sql = "select c.events_no eventsNo from erp_customer c where c.name='"+name+"' and c.code='"+code+"' and c.is_deleted='0'";
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
		String eventsNo = "";
		if(list!=null&&list.size()>0){
			eventsNo = (String) list.get(0).get("eventsNo");
		}
		return eventsNo;
	}

	private String getValue(Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:// boolean类型
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数字或日期类型

			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否是日期类型
				value = DateUtils.DateToStr(cell.getDateCellValue(),
						DateUtils.TIME_FORMAT); // 把Date转换成本地格式的字符串
			} else {
				short format = cell.getCellStyle().getDataFormat();
				SimpleDateFormat sdf = null;
				if (format == 14 || format == 31 || format == 57
						|| format == 58) {
					// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					sdf = new SimpleDateFormat("HH:mm:ss");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					value = String.valueOf(
							df.format(cell.getNumericCellValue())).trim();
				}
			}
			break;
		case Cell.CELL_TYPE_STRING:// 字符类型
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_BLANK:// 空白单元格
			break;
		default:
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		}
		return value;
	}

	/**
	 * 根据reportId拿到所有的变更历史信息
	 * @param reportId
	 * @return
	 * @author machuan
	 * @date  2017年4月6日
	 */
	@SuppressWarnings("unchecked")
	public List<ErpReportExpressHis> findHisByReportId(String reportId) {
		String hql = "from ErpReportExpressHis where reportId=?";
		return dao.getHibernateTemplate().find(hql, reportId);
	}


}