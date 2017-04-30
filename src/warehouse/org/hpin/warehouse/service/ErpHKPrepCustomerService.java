package org.hpin.warehouse.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipProDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.exception.MyException;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpConferenceDao;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.warehouse.dao.ErpHKPrepCustomerDao;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.hpin.warehouse.entity.vo.ErpPreCustomerQueryObj;
import org.hpin.warehouse.entity.vo.ErpPrepCustomerVO;
import org.hpin.warehouse.util.CustomerExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 弘康预导入客户Service
 * @author tangxing
 * @date 2016-12-29 15:14:45
 */
@Service(value = "org.hpin.warehouse.service.ErpHKPrepCustomerService")
@Transactional()
public class ErpHKPrepCustomerService extends BaseService {
	
	private static String TEST_INSTITUTION = "南方";

	private Logger log = Logger.getLogger(ErpHKPrepCustomerService.class);
	
	private String localPath;
	
	private String downloadContext;
	
	@Autowired
	ErpHKPrepCustomerDao dao;
	@Autowired
	private CustomerRelationshipDao customerRelationshipDao; //支公司;
	@Autowired
	private ErpEventsDao erpEventsDao; //场次dao
	@Autowired
	private ErpConferenceDao erpConferenceDao; //会场dao
	@Autowired
	private DeptDao deptDao; //总公司Dao
	@Autowired
	private ErpCustomerDao customerDao;
	
	@Autowired
	private CustomerRelationshipProDao customerRelationshipProDao;
	
	@SuppressWarnings("resource")
	public boolean savePreCustomerByExcel(File affix, String affixFileName,
			User userInfo, HashMap<String, String> params) throws Exception {
		boolean result = false;
		String companyId = params.get("branchCompanyId");
		String projectCode = params.get("projectCode");
		String bacthPre = "";
		/*
		 * 1.验证Excel表头规则;
		 * Excel表头为：客户唯一识别号、姓名、套餐;
		 */
		if (StringUtils.isEmpty(affixFileName)) {
			result = false;
			throw new MyException("文件上传失败!");
		}

		InputStream is = null;
		Workbook wb = null; 
		File path = affix.getAbsoluteFile();

		is = new FileInputStream(path);

		wb = new XSSFWorkbook(is);

		Sheet sheet = wb.getSheetAt(0);
		if(null == sheet) {
			result = false;
			throw new MyException("Excel文件错误, 请检查导入文件模板是否正确!");
		}

		/*
		 * 1. 获取导入excel中的数据;放入list中;
		 */
		List<ErpPreCustomer> precs = CustomerExcelUtils.dealExcelSheetData(sheet);

		if(precs.size() <= 0) {
			result = false;
			throw new MyException("导入的Excel中不存在数据,请确认后再导入!");
		}
		
		/*
		 * 验证同一导入excel不能有重复套餐;存在则退出;
		 * 验证弘康套餐
		 */
		StringBuilder sb = new StringBuilder("Excel中第");
		int j = 0;
		boolean flag = false;
		
		bacthPre = this.customerRelationshipDao.findBacthPreByCondition(companyId, projectCode);
		for(int i=0; i<precs.size(); i++) {
			ErpPreCustomer prec = precs.get(i);
			j++;
			//根据支公司,项目编码,远盟套餐名称验证是否存在该远盟套餐
			boolean tempFlag = this.customerRelationshipProDao.isExcitComboName(companyId, projectCode, prec.getYmCombo());
			
			if(!tempFlag) {
				flag = true;
				sb.append(j).append(", ");
				
			}
		}
		
		if(flag) {
			sb.append("行的项目套餐在远盟数据库没有对应数据,请核对后再导入!");
			result = false;
					
			throw new MyException(sb.toString());			
		}
		

		/*
		 * 2. 生成场次号;以及会议管理;
		 */
		String eventsNo = bacthPre+ DateUtil.getDateTime("yyyyMMddHHmmss", new Date());//场次号和批次号规则为HK+当日日期，如HK20161215。
		log.info("场次号自动生成为: " + eventsNo);
		String shipId = params.get("branchCompanyId");
		
		/*
		 * 3.保存到与导入设置客户信息表中;
		 */
		String shipProId = this.customerRelationshipDao.findCustomerRelationshipProId(companyId, projectCode);
		for(ErpPreCustomer prec : precs) {
			prec.setCompanyId(shipId);
			prec.setShipPorId(shipProId);
			prec.setErpApplicationId(null);
			prec.setCreateTime(new Date());
			prec.setCreateUserId(userInfo.getId());
			prec.setIsDeleted(0);
			prec.setEventsNo(eventsNo);

			this.dao.save(prec);
		}

		if(wb != null) {
			wb.close();

		}
		return result;
	}
	
	/**
	 * 查询list
	 * @param page
	 * @param queryObject
	 * @author tangxing
	 * @date 2017-1-3下午1:50:35
	 */
	public void findPrepCustomerAll(Page<ErpPrepCustomerVO> page,ErpPreCustomerQueryObj queryObject) throws Exception{
		List<Object> params = new ArrayList<Object>(); //参数;
		String sql = 
				"select preCus.id as id, "+
				" preCus.batch_no as batchNo, "+
					      " preCus.Code as code,  "+
					      "  preCus.Were_Name as wereName, "+
					      "  preCus.Were_Sex as wereSex, "+
					      "  preCus.Were_Age as wereAge, "+
					      "  preCus.Were_Idcard as wereIdcard, "+
					      "  preCus.Were_Phone as werePhone, "+
					      "  preCus.Check_Cobmo as checkCobmo, "+
					      "  (select relPro.Project_Owner "+
					      "  from hl_customer_relationship_pro relPro "+
					      "  where relPro.id = " +
					      "    (select events.CUSTOMERRELATIONSHIPPRO_ID from erp_events events " +
					      "      where events.events_no=preCus.Events_No)) as salesman, "+
					      "  (select ship.BRANCH_COMMANY from hl_customer_relationship ship" +
					      "      where ship.id=preCus.company_id) as branchCompany, "+
					      "  preCus.status_ym as statusYm, " +
					      "  preCus.create_time as createTime, " +
					      "  preCus.file_path as filePath, " +
					      "  preCus.upload_report_time as uploadReportTime, " +
					      "  preCus.Remark as remark " +
					      "  from ERP_PRE_CUSTOMER preCus left join erp_application appl on appl.id=preCus.ERP_APPLICATION_ID "+
					      "  where preCus.is_Deleted = 0 ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		if(queryObject!=null){
			jdbcSql.append(dealSqlParam(queryObject));
		}
		
		//查询count;
		page.setTotalCount(dao.findJdbcCount(jdbcSql, params));
		jdbcSql.append(" order by preCus.create_time desc");
		//查询list;
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpPrepCustomerVO> rowMapper = new BeanPropertyRowMapper<ErpPrepCustomerVO>(ErpPrepCustomerVO.class);
		page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
//		List<ErpPrepCustomerVO> list = (List<ErpPrepCustomerVO>) dao.findJdbcListLocalView(jdbcSql.toString(), params, rowMapper);
		
//		page.setResults(list);
	}
	
	public StringBuilder dealSqlParam(ErpPreCustomerQueryObj queryObject){
		StringBuilder jdbcSql = new StringBuilder();
		if(StringUtils.isNotEmpty(queryObject.getBatchNo())){//批次号
			jdbcSql.append(" and preCus.batch_no like '%").append(queryObject.getBatchNo().trim()).append("%'");
			
		} 
		if(StringUtils.isNotEmpty(queryObject.getWereName())){//姓名
			jdbcSql.append(" and preCus.were_Name like '%").append(queryObject.getWereName().trim()).append("%'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getCode())){			//条形码
			jdbcSql.append(" and preCus.code = '").append(queryObject.getCode().trim()).append("'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getWereIdcard())){	//身份证
			jdbcSql.append(" and preCus.were_Idcard like '%").append(queryObject.getWereIdcard().trim()).append("%'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getWerePhone())){	//电话
			jdbcSql.append(" and preCus.were_Phone like '%").append(queryObject.getWerePhone().trim()).append("%'");
			
		} else if(StringUtils.isNotEmpty(queryObject.getApplicationNo())){	//erp_application表的申请单号
			jdbcSql.append(" and appl.application_no = '").append(queryObject.getApplicationNo().trim()).append("'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getCheckCombo())){//套餐
			jdbcSql.append(" and preCus.check_cobmo like '%").append(queryObject.getCheckCombo().trim()).append("%'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getStartTime())){//导入开始时间
			jdbcSql.append(" and preCus.create_time >= ").append("to_date('").append(queryObject.getStartTime()).append("','yyyy-MM-dd')");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getEndTime())){//导入结束时间
			jdbcSql.append(" and preCus.create_time < ").append("to_date('").append(queryObject.getEndTime()).append("','yyyy-MM-dd')+1");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getStatusYm())){//状态
			jdbcSql.append(" and preCus.status_ym =").append(queryObject.getStatusYm());
			
		}
		if(StringUtils.isNotEmpty(queryObject.getBranchCompany())){//支公司
			jdbcSql.append(" and preCus.company_id =(").append("select id from hl_customer_relationship where branch_commany='")
				.append(queryObject.getBranchCompany()).append("')");
		}
		return jdbcSql;
	}
	
	public List<ErpPreCustomer> getListPrepCustomer(Page page,Map searchMap){
		return dao.getListPrepCustomer(page, searchMap);
	}
	
	public ErpPreCustomer getErpPreCustomerById(String id){
		return dao.getErpPreCustomerById(id);
	}
	
	/**
	 * 导出excel条件组装
	 * @param jsonObject
	 * @return
	 * @author tangxing
	 * @date 2017-1-2下午7:36:21
	 */
	public String exportExcelPrepCustomer(JSONObject jsonObject) throws Exception{
		String sql = 
				"select preCus.id as id, "+
				" preCus.batch_no as batchNo, "+
					      " preCus.Code as code,  "+
					      "  preCus.Were_Name as wereName, "+
					      "  preCus.Were_Sex as wereSex, "+
					      "  preCus.Were_Age as wereAge, "+
					      "  preCus.Were_Idcard as wereIdcard, "+
					      "  preCus.Were_Phone as werePhone, "+
					      "  preCus.Check_Cobmo as checkCobmo, "+
					      "  (select relPro.Project_Owner "+
					      "  from hl_customer_relationship_pro relPro "+
					      "  where relPro.id = " +
					      "    (select events.CUSTOMERRELATIONSHIPPRO_ID from erp_events events " +
					      "      where events.events_no=preCus.Events_No)) as salesman, "+
					      "  (select ship.BRANCH_COMMANY from hl_customer_relationship ship" +
					      "      where ship.id=preCus.company_id) as branchCompany, "+
					      "  preCus.status_ym as statusYm, " +
					      "  preCus.create_time as createTime, " +
					      "  preCus.file_path as filePath, " +
					      "  preCus.upload_report_time as uploadReportTime, " +
					      "  preCus.Remark as remark " +
					      "  from ERP_PRE_CUSTOMER preCus left join erp_application appl on appl.id=preCus.ERP_APPLICATION_ID "+
					      "  where preCus.is_Deleted = 0 ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		if(jsonObject!=null&&StringUtils.isNotEmpty(jsonObject.toString())){
			if(StringUtils.isNotEmpty(jsonObject.getString("pBatchNo"))){//批次号
				jdbcSql.append(" and preCus.batch_no like '%").append(jsonObject.getString("pBatchNo").trim()).append("%'");
				
			} 
			if(StringUtils.isNotEmpty(jsonObject.getString("pWereName"))){//姓名
				jdbcSql.append(" and preCus.were_Name like '%").append(jsonObject.getString("pWereName").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("pCode"))){			//条形码
				jdbcSql.append(" and preCus.code = '").append(jsonObject.getString("pCode").trim()).append("'");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("pWereIdcard"))){	//身份证
				jdbcSql.append(" and preCus.were_Idcard like '%").append(jsonObject.getString("pWereIdcard").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("pWerePhone"))){	//电话
				jdbcSql.append(" and preCus.were_Phone like '%").append(jsonObject.getString("pWerePhone").trim()).append("%'");
				
			} else if(StringUtils.isNotEmpty(jsonObject.getString("pApplicationNo"))){	//erp_application表的申请单号
				jdbcSql.append(" and appl.application_no = '").append(jsonObject.getString("pApplicationNo").trim()).append("'");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("checkCombo"))){//套餐
				jdbcSql.append(" and preCus.check_cobmo like '%").append(jsonObject.getString("checkCombo").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("startTime"))){//导入开始时间
				jdbcSql.append(" and preCus.create_time >= ").append("to_date('").append(jsonObject.getString("startTime")).append("','yyyy-MM-dd')");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("endTime"))){//导入结束时间
				jdbcSql.append(" and preCus.create_time <= ").append("to_date('").append(jsonObject.getString("endTime")).append("','yyyy-MM-dd')");
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("statusYm"))){//状态
				jdbcSql.append(" and preCus.status_ym =").append(jsonObject.getInt("statusYm"));
				
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("branchCompany"))){//支公司
				jdbcSql.append(" and preCus.company_id =(").append("select id from hl_customer_relationship where branch_commany='")
					.append(jsonObject.getString("branchCompany")).append("')");
			}
		}
		
		//查询count;
		BeanPropertyRowMapper<ErpPrepCustomerVO> rowMapper = new BeanPropertyRowMapper<ErpPrepCustomerVO>(ErpPrepCustomerVO.class);
		//page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
		List<ErpPrepCustomerVO> list = (List<ErpPrepCustomerVO>) dao.findJdbcListLocal(jdbcSql.toString(), rowMapper);
		
		String downloadurl = this.uploadPath(list);		//下载路径
		
		return downloadurl;
	}
	
	/**
	 * 获取excel下载路径
	 * @param list
	 * @return
	 * @author tangxing
	 * @date 2017-1-2下午8:26:52
	 */
	private String uploadPath(List<ErpPrepCustomerVO> list) throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String strName = sdf.format(new Date());
		String excleName = "prepCustomer"+strName+".xls";			//文件名
		String curFilePath = localPath+strName+File.separator;		//存放位置
		
		List<List<String>> rowList = this.buildExcelRowByPdf(list);
		
		this.createExSettleXls(curFilePath.toString(),excleName,rowList);
		log.info("ErpHKPrepCustomer uploadPath excel save file path -- "+curFilePath+",excleName--"+excleName);
		String downloadurl = contextUrl+File.separator+strName+File.separator+ excleName;		//下载路径
		log.info("ErpHKPrepCustomer uploadPath excel file path -- "+downloadurl);
		return downloadurl;
	}
	
	/**
	 * 生成excel文件（文件流）
	 * @param list
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-1-11上午11:12:50
	 */
	private HashMap<String,String> uploadPathForStrem(List<ErpPrepCustomerVO> list) throws Exception{
		HashMap< String, String> pathMap = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
		String strName = sdf.format(new Date());
		String excelName = "prepCustomer"+strName+".xls";			//文件名
		String curFilePath = localPath+strName+File.separator;		//存放位置
		
		List<List<String>> rowList = this.buildExcelRowByPdf(list);
		
		this.createExSettleXls(curFilePath.toString(),excelName,rowList);
		log.info("ErpHKPrepCustomer uploadPath excel save file path -- "+curFilePath+",excleName--"+excelName);
		
		pathMap.put("curFilePath",curFilePath);
		pathMap.put("excelName",excelName);
		return pathMap;
	}
	
	/**
	 * 查询导出excl数据（文件流）
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-1-11上午11:12:28
	 */
	public HashMap<String, String> exportExcelPrepCustomerForStrem(HashMap<String, String> queryMap) throws Exception{
		String sql = 
				"select preCus.id as id, "+
				" preCus.batch_no as batchNo, "+
					      " preCus.Code as code,  "+
					      "  preCus.Were_Name as wereName, "+
					      "  preCus.Were_Sex as wereSex, "+
					      "  preCus.Were_Age as wereAge, "+
					      "  preCus.Were_Idcard as wereIdcard, "+
					      "  preCus.Were_Phone as werePhone, "+
					      "  preCus.Check_Cobmo as checkCobmo, "+
					      "  (select relPro.Project_Owner "+
					      "  from hl_customer_relationship_pro relPro "+
					      "  where relPro.id = " +
					      "    (select events.CUSTOMERRELATIONSHIPPRO_ID from erp_events events " +
					      "      where events.events_no=preCus.Events_No)) as salesman, "+
					      "  (select ship.BRANCH_COMMANY from hl_customer_relationship ship" +
					      "      where ship.id=preCus.company_id) as branchCompany, "+
					      "  preCus.status_ym as statusYm, " +
					      "  preCus.create_time as createTime, " +
					      "  preCus.file_path as filePath, " +
					      "  preCus.upload_report_time as uploadReportTime, " +
					      "  preCus.Remark as remark " +
					      "  from ERP_PRE_CUSTOMER preCus left join erp_application appl on appl.id=preCus.ERP_APPLICATION_ID "+
					      "  where preCus.is_Deleted = 0 ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		if(null != queryMap){
			if(StringUtils.isNotEmpty(queryMap.get("batchNo"))){//批次号
				jdbcSql.append(" and preCus.batch_no like '%").append(queryMap.get("batchNo").trim()).append("%'");
				
			} 
			if(StringUtils.isNotEmpty(queryMap.get("wereName"))){//姓名
				jdbcSql.append(" and preCus.were_Name like '%").append(queryMap.get("wereName").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("code"))){			//条形码
				jdbcSql.append(" and preCus.code = '").append(queryMap.get("code").trim()).append("'");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("wereIdcard"))){	//身份证
				jdbcSql.append(" and preCus.were_Idcard like '%").append(queryMap.get("wereIdcard").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("werePhone"))){	//电话
				jdbcSql.append(" and preCus.were_Phone like '%").append(queryMap.get("werePhone").trim()).append("%'");
				
			} else if(StringUtils.isNotEmpty(queryMap.get("applicationNo"))){	//erp_application表的申请单号
				jdbcSql.append(" and appl.application_no = '").append(queryMap.get("applicationNo").trim()).append("'");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("checkCombo"))){//套餐
				jdbcSql.append(" and preCus.check_cobmo like '%").append(queryMap.get("checkCombo").trim()).append("%'");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("startTime"))){//导入开始时间
				jdbcSql.append(" and preCus.create_time >= ").append("to_date('").append(queryMap.get("startTime")).append("','yyyy-MM-dd')");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("endTime"))){//导入结束时间
				jdbcSql.append(" and preCus.create_time <= ").append("to_date('").append(queryMap.get("endTime")).append("','yyyy-MM-dd')");
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("statusYm"))){//状态
				jdbcSql.append(" and preCus.status_ym =").append(Integer.valueOf(queryMap.get("statusYm")));
				
			}
			if(StringUtils.isNotEmpty(queryMap.get("branchCompany"))){//支公司
				jdbcSql.append(" and preCus.company_id =(").append("select id from hl_customer_relationship where branch_commany='")
					.append(queryMap.get("branchCompany")).append("')");
			}
		}
		jdbcSql.append(" order by preCus.create_time desc");
		//查询count;
		BeanPropertyRowMapper<ErpPrepCustomerVO> rowMapper = new BeanPropertyRowMapper<ErpPrepCustomerVO>(ErpPrepCustomerVO.class);
		//page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
		List<ErpPrepCustomerVO> list = (List<ErpPrepCustomerVO>) dao.findJdbcListLocal(jdbcSql.toString(), rowMapper);
		
		HashMap<String, String> excelPath = this.uploadPathForStrem(list);		//下载路径
		
		return excelPath;
	}
	
	/**
	 * 格式化excel数据
	 * @param preCustomerVO
	 * @return
	 * @author tangxing
	 * @date 2017-1-2下午8:27:06
	 */
  	public List<List<String>> buildExcelRowByPdf(List<ErpPrepCustomerVO> preCustomerVO){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<preCustomerVO.size();i++){
  			ErpPrepCustomerVO content = preCustomerVO.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getBatchNo()==null?"":content.getBatchNo());//批次号号
  			list.add(content.getCode()==null?"":content.getCode());//条码
  			list.add(content.getWereName()==null?"":content.getWereName());//投保人姓名
  			list.add(content.getWereSex()==null?"":content.getWereSex());//性别
  			list.add(content.getWereAge()==null?"":content.getWereAge());//年龄
  			list.add(content.getWereIdcard()==null?"":content.getWereIdcard());//身份证号
  			list.add(content.getWerePhone()==null?"":content.getWerePhone());//电话
  			list.add(content.getCheckCombo()==null?"":content.getCheckCombo());//套餐
  			list.add(content.getBranchCompany()==null?"":content.getBranchCompany());
  			list.add(content.getSalesman()==null?"":content.getSalesman());	//营销员
  			switch (content.getStatusYm()==null?0:content.getStatusYm()) {
			case 100:
				list.add("已送检");
				break;
			case 200:
				list.add("已出报告");
				break;
			case 300:
				list.add("打印中");
				break;
			case 400:
				list.add("已打印");
				break;
			case 500:
				list.add("已寄送");
				break;

			default:
				list.add("审核中");
				break;
			}
  			if(null == content.getCreateTime()){
  				list.add("");
  			}else {
  				list.add(sdf.format(content.getCreateTime()));
			}
  			list.add(content.getFilePath());
  			if(null == content.getUploadReportTime()){
  				list.add("");
  			}else {
				list.add(sdf.format(content.getUploadReportTime()));
			}
  			list.add(content.getRemark()==null?"":content.getRemark());	//备注
  			result.add(list);
  		}
  		return result;
  	}
	
	public void createExSettleXls(String path ,String filename, List<List<String>> rowList) {
		
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("匹配成功");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			/* ***sheet1*** */
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("批次号");
			
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("条码");
			
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("姓名");
			
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("性别");
			
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("年龄");
			
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("身份证号");
			
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("电话");
			
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("套餐");
			
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("支公司");
			
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("营销员");
			
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("客户状态");
			
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("导入时间");
			
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("电子报告");
			
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("报告上传时间");
			
			Cell cell15 = row0.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("备注");
			
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
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
	 * 设置字体格式
	 * @param wb
	 * @param bold
	 * @param fontName
	 * @param isItalic
	 * @param hight
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午6:29:53
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
	 * @param wb
	 * @param row
	 * @param column
	 * @param value
	 * @param cellStyle
	 * @author tangxing
	 * @date 2016-6-14下午6:30:05
	 */
	public static void createCell(Workbook wb, Row row, int column,String value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getDownloadContext() {
		return downloadContext;
	}

	public void setDownloadContext(String downloadContext) {
		this.downloadContext = downloadContext;
	}
	/**
	 * @param customerId
	 * @return 根据客户id获取预存表的客户信息
	 */
	public List<ErpPreCustomer> getAdressInfo(String customerId) {
		return dao.getAdressInfo(customerId);
	}
	
	
	/**
	 * 根据检测公司套餐名，查找基因公司套餐
	 * @param TestComboName
	 * @return
	 * @author tangxing
	 * @date 2017-01-19 17:27:57
	 */
	public String getYMComboNameByTestComboName(String TestComboName){
		String ymComboname = "";
		List<Map<String, Object>> maps = dao.getYMComboNameByTestComboName(TestComboName);
		if(maps!=null&&maps.size()>0){
			ymComboname = (String)maps.get(0).get("ymComboname");
		}
		return ymComboname;
	}
	
	
	/**
	 * 获取最大批次号
	 * @return
	 * @author tangxing
	 * @date 2017-2-15下午4:24:27
	 */
	public String findBatchNo(String companyPro) {
		String batchNo = dao.findBatchNo(companyPro);
		return batchNo;
	}
	
	/**
	 * 判断是否创建场次
	 * @param companyPro
	 * @param branchCompanyId
	 * @param projectId
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-2-15下午5:40:48
	 */
	public ErpEvents createOrFindEvent(String companyPro,String branchCompanyId,String projectId) throws Exception {
		ErpEvents erpEvents = new ErpEvents();
		String eventsNo = companyPro+ DateUtil.getDateTime("yyyyMMdd", new Date());//场次号和批次号规则为TY+当日日期，如HK20161215。
		
		List<ErpEvents> eventsIsDeleted = erpEventsDao.queryByEventNoIsDeleted(eventsNo);
		//因为场次号设置了唯一约束，需要判断是否存在 在界面上逻辑删除的 场次
		if(eventsIsDeleted!=null&&eventsIsDeleted.size()>0){
			ErpEvents events = eventsIsDeleted.get(0);
			String eventsId = events.getId();
			String no = events.getEventsNo();
			events.setEventsNo(no+"_"+this.getTimeStamp());	//将已经删除的场次号改为：场次号_当前（当天00点到现在）毫秒值
			dao.update(events);
			dao.getHibernateTemplate().flush();
		}
		
		String sql = "select count(*) from erp_events e where e.events_no='"+eventsNo+"' and e.is_deleted=0";
		log.info("预导入创建场次排重条数--"+this.dao.getJdbcTemplate().queryForInt(sql));
		if(this.dao.getJdbcTemplate().queryForInt(sql)<1){
			//如果数据库中不存在该场次  则生成场次
			erpEvents = createEvent(eventsNo,branchCompanyId,projectId,companyPro);
		}else{
			erpEvents = erpEventsDao.queryByEventNO(eventsNo);
		}
		return erpEvents;
	}
	
	/**
	 * 生成场次及会场
	 * @param eventsNo
	 * @param branchCompanyId
	 * @param projectId
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-2-15下午5:41:45
	 */
	public ErpEvents createEvent(String eventsNo,String branchCompanyId,String projectId,String companyPro) throws Exception{
		ErpEvents events = new ErpEvents();
		String batchNo = "";
		log.info("createEvent场次号: " + eventsNo+",branchCompanyId : "+branchCompanyId+", projectId : "+projectId);
		CustomerRelationShip ship = new CustomerRelationShip();
		CustomerRelationShipPro shipPro = new CustomerRelationShipPro();
		ship = (CustomerRelationShip) this.dao.findById(CustomerRelationShip.class, branchCompanyId);
		shipPro =  this.dao.findShipProById(projectId);
		events.setEventsNo(eventsNo); //场次号;
		events.setEventDate(new Date()); //场次日期
		//支公司名称：支公司ID
		events.setBranchCompanyId(ship.getId());
		//场次地址：支公司名称
		events.setBranchCompany(ship.getBranchCommany());
		//总公司名称：总公司Id
		events.setOwnedCompanyId(ship.getOwnedCompany());

		events.setOwnedCompany(ship.getCustomerNameSimple());
		//总公司名称;ship中没有总公司名称
		//省份：当前支公司的省份
		events.setAddress(ship.getAddress());//地址;
		events.setProvice(ship.getProvince());
		//城市：当前支公司的城市
		events.setCity(ship.getCity());
		events.setLevel2("1010301"); //用户级别
		events.setIsDeleted(0);
		//项目ID
		events.setCustomerRelationShipProId(projectId);
		//远盟营销员：项目负责人
		events.setYmSalesman(shipPro.getProjectOwner());
		//入门检测礼：业务给出的套餐名称
		//events.setComboName();

	/*	String batchNoTemp = dao.getBatchByEventsNo(eventsNo);
		if(StringUtils.isEmpty(batchNoTemp)){			//如果为空，证明该场次还没有批次号，需要新建一个批次号
			String batchNoMAX = this.findBatchNo(companyPro);	//最大批次
			batchNo = this.dealBatchNo(batchNoMAX,companyPro);
		}else{
			batchNo = batchNoTemp;
		}*/
		String batchNoMAX = this.findBatchNo(companyPro);	//最大批次
		batchNo = this.dealBatchNo(batchNoMAX,companyPro);
		
		//批次号：
		events.setBatchNo(batchNo);
		
		events.setCreateTime(new Date());
		events.setCreateUserName(shipPro.getProjectOwner());
		events.setCreateUserId(shipPro.getCreateUserId());
		this.dao.save(events);
		// 推送到会场管理;
		ErpConference conference = new ErpConference();    //增加场次的同时增加到会议表中
		conference.setConferenceNo(events.getEventsNo());
		conference.setConferenceDate(events.getEventDate());
		conference.setProvice(events.getProvice());
		conference.setCity(events.getCity());
		conference.setBranchCompany(events.getBranchCompany());
		conference.setBranchCompanyId(events.getBranchCompanyId());
		conference.setOwnedCompany(events.getOwnedCompany());
		conference.setOwnedCompanyId(events.getOwnedCompanyId());
		conference.setAddress(events.getAddress());
		conference.setSettNumbers(0);
		conference.setProduceCost(Double.parseDouble("0"));
		conference.setConferenceType("1010902"); //默认说明会;
		conference.setProBelong(ship.getProBelong());
		conference.setProCode(ship.getProCode());
		conference.setProUser(shipPro.getProjectOwner());
		conference.setIsDeleted(0);
		conference.setIsExpress(0);
		conference.setHeadcount(events.getHeadcount());
		conference.setCreateTime(new Date());
		conference.setCreateUserName("-1");
		conference.setCustomerRelationShipProId(events.getCustomerRelationShipProId()); 

		this.dao.save(conference);
		log.info("==>会场推送成功!");
		return events;
	}
	
	/**
	 * 获取公司标示（如：宏康-->HK）
	 * @param testComboName
	 * @return
	 * @author tangxing
	 * @date 2017-2-15下午4:59:41
	 */
	public String getCorrespondingTableInfo(String testComboName){
		String companyPro = "";
		List<Map<String, Object>> results = dao.getCorrespondingTableInfo(testComboName);
		if(results!=null&&results.size()>0){
			for (Map<String, Object> map : results) {
				companyPro = (String) map.get("project");
			}
		}
		return companyPro;
	}
	
	/**
	 * 根据检测公司套餐获取远盟的套餐
	 * @param testComboName
	 * @return
	 * @author tangxing
	 * @date 2017-2-17上午11:45:10
	 */
	public String getYmComboByTestCombo(String testComboName){
		String ymComboName = "";
		List<Map<String, Object>> results = dao.getCorrespondingTableInfo(testComboName);
		if(results!=null&&results.size()>0){
			for (Map<String, Object> map : results) {
				ymComboName = (String) map.get("ymComboName");
			}
		}
		return ymComboName;
	}
	
	/**
	 * 根据场次号获取批次号
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2017年2月15日17:18:43
	 */
/*	public String getBatchByEventsNo(String eventsNo){
		String batchNo = "";
		List<ErpPreCustomer> customers = dao.getBatchByEventsNo(eventsNo);
		if(customers!=null&&customers.size()>0){
			batchNo = customers.get(0).getBatchNo();
		}
		return batchNo;
	}*/
	
	/**
	 * 根据code获取ErpPreCustomer
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-16上午10:14:11
	 */
	public List<ErpPreCustomer> getPreCustomerByCode(String code){
		return dao.getPreCustomerByCode(code);
	}
	
	/**
	 * 根据被检人姓名和被检人身份证查找ErpPreCustomer
	 * @param name
	 * @param idno
	 * @return
	 * @author tangxing
	 * @date 2017-2-16上午11:09:57
	 */
	public List<ErpPreCustomer> getPreCustomerByNameAndIdon(String name,String idno){
		return dao.getPreCustomerByNameAndIdon(name, idno);
	}
	
	/**
	 * 批次号处理
	 * @param bacthNo
	 * @param companyPro
	 * @return
	 * @author tangxing
	 * @date 2017-2-15下午4:12:17
	 */
	public String dealBatchNo(String bacthNo, String companyPro) {
		String result = "";
		if(StringUtils.isNotEmpty(bacthNo)) {
			Integer numNo = Integer.valueOf(bacthNo)+1;
			result = companyPro + numNo;
			
		} else {
			result = companyPro+"1";
		}
		return result;
	}
	
	/**
	 * 根据保险公司套餐获取ym套餐（通过ERP_RELATIONSHIPPRO_COMBO表）
	 * @param checkCombo
	 * @return
	 * @author tangxing
	 * @date 2017年2月17日15:06:53
	 */
	public String getYmComboByShipProTable(String checkCombo){
		String ymCombo = "";
		List<Map<String, Object>> maps = dao.getYmComboByShipProTable(checkCombo);
		if(maps!=null&&maps.size()>0){
			ymCombo = (String) maps.get(0).get("comboName");
		}
		return ymCombo;
	}
	
	/**
	 * save ErpCustomer、events
	 * @param maps
	 * @return
	 * @throws ParseException
	 * @author tangxing
	 * @date 2017-2-17下午5:25:07
	 */
	public JSONObject saveErpCustomerAndEvents(HashMap<String, Object> maps) throws Exception{
		JSONObject json = new JSONObject();
		
		if(maps!=null){
			ErpPreCustomer prepCustomer = (ErpPreCustomer) maps.get("prepCustomer");
			User currentUser = (User) maps.get("currentUser");
			String simpleingDate = (String)maps.get("simpleingDate");
			String navTabId = (String)maps.get("navTabId");
			
			boolean codeFlag = false;		//判断预导入信息是否有code
			boolean flag = false;			//是否生成场次成功
			String eventsNo = "";
			String batchNo = "";
			
			ErpPreCustomer preCustomer = this.getErpPreCustomerById(prepCustomer.getId());
			String userName=currentUser.getUserName();
			String userId = currentUser.getId();
			
			if(preCustomer!=null){
				String customerId = "";
				String shipProId = preCustomer.getShipPorId();			//项目ID
				String preCusCompanyId = preCustomer.getCompanyId();	//预导入客户支公司ID
				String ymComboName = "";								//入库的ym套餐
				String ymComboNameTemp = preCustomer.getYmCombo();		//ym套餐
				String inCode = prepCustomer.getCode().trim();			//jsp输入的code
				
				String code = preCustomer.getCode();					//判断是否已有code
				
				if(StringUtils.isNotEmpty(ymComboNameTemp)){			//判断ymCombo是否有数据，未有就用checkCombo去找
					ymComboName = ymComboNameTemp;
				}else{
					log.info("保险公司检测套餐（条件）--"+preCustomer.getCheckCobmo());
					ymComboName = this.getYmComboByShipProTable(preCustomer.getCheckCobmo());
					log.info("ym套餐--"+ymComboName);
				}
				
				if(StringUtils.isNotEmpty(code)){
					codeFlag = true;
				}else{
					codeFlag = false;
				}
				
				if(!codeFlag){		//false 表示没有code
					List<ErpPreCustomer> preCustomers = this.getPreCustomerByCode(inCode);
					
					if(preCustomers!=null&&preCustomers.size()>0){		//判断code是否重复
						json.accumulate("statusCode", 300);
						json.accumulate("message", "保存失败，条码重复！");
						return json;	//重复直接退出方法
					}
				}
				
				//获取公司标示（如：宏康-->HK）
				String companyPro = this.getBacthPre(preCusCompanyId,shipProId);
				try {
					ErpEvents erpEvents = this.createOrFindEvent(companyPro, preCustomer.getCompanyId(), preCustomer.getShipPorId());
					batchNo = erpEvents.getBatchNo();
					eventsNo = erpEvents.getEventsNo();
					/*String batchNoTemp = this.getBatchByEventsNo(eventsNo);	//用场次号判断当天是否已经生成批次号
					
					if(StringUtils.isEmpty(batchNoTemp)){			//如果为空，证明该场次还没有批次号，需要新建一个批次号
						String batchNoMAX = this.findBatchNo(companyPro);	//最大批次
						batchNo = this.dealBatchNo(batchNoMAX,companyPro);
					}else{
						batchNo = batchNoTemp;
					}*/
					
					/* **** 给场次设置批次号 **** */
					/*ErpEvents erpEvents = erpEventsDao.queryByEventNO(eventsNo);
					log.info("预导入创建场次'"+erpEvents.getEventsNo()+"'的ID--"+erpEvents.getId());
					erpEvents.setBatchNo(batchNo);
					erpEventsDao.update(erpEvents);*/
					flag = true;
				} catch (Exception e) {
					flag = false;
					log.info("预导入未有code的 create events fail -- "+e);
				}
				
				if(flag){
					//判断是否成功创建场次或批次号
					ErpCustomer customer = new ErpCustomer();
					customer.setCode(prepCustomer.getCode());
					customer.setName(prepCustomer.getWereName());
					customer.setIdno(prepCustomer.getWereIdcard());
					customer.setPhone(prepCustomer.getWerePhone());
					customer.setSex(prepCustomer.getWereSex());
					customer.setAge(prepCustomer.getWereAge());
					customer.setWeight(prepCustomer.getWereWeight());
					customer.setHeight(prepCustomer.getWereHeight());
					customer.setSamplingDate(StringUtils.isNotEmpty(simpleingDate)?new SimpleDateFormat("yyyy-MM-dd").parse(simpleingDate):null);
					customer.setEventsNo(eventsNo);
					customer.setNote(prepCustomer.getRemark());
					
					customer.setSetmealName(ymComboName);		//判断后的ymCombo
					
					customer.setCustomerHistory(prepCustomer.getCustomerHistory());	//既往病史
					customer.setFamilyHistory(prepCustomer.getFamilyHistory());	//家族病史

					//
					customer.setTestInstitution(TEST_INSTITUTION);
					
					// 从配置文件中获取状态值 add by Damian 2017-03-08 start
					Integer yhq = PropsUtils.getInt("status", "statusYm.yhq");
					customer.setStatusYm(yhq);		//样本已获取状态
					// 从配置文件中获取状态值 add by Damian 2017-03-08 end
					customer.setCreateTime(new Date());
					customer.setCreateUserName(userName);
					customer.setCreateUserId(userId);
					customer.setIsDeleted(0);
					customer.setStatus("0");
					
					/* **** 根据当前预导入客户获取公司省份城市等信息 **** */
					CustomerRelationShip ship = new CustomerRelationShip();
					ship = customerRelationshipDao.getCustomerRelationShipById(preCusCompanyId);		//公司信息
					String projectOwner = customerRelationshipProDao.getRelationShipProById(shipProId);	//项目负责人
					//支公司名称：支公司ID
					customer.setBranchCompanyId(ship.getId());
					//场次地址：支公司名称
					customer.setBranchCompany(ship.getBranchCommany());
					//总公司名称：总公司Id
					customer.setOwnedCompanyId(ship.getOwnedCompany());
					customer.setOwnedCompany(ship.getCustomerNameSimple());
					customer.setCity(ship.getCity());
					customer.setProvice(ship.getProvince());
					customer.setSalesMan(projectOwner);		//项目负责人
					
					customerId = customerDao.saveCustomer(customer);
					log.info("saveCustomer customerId -- "+customerId);
					
					/* ****预导入表相关信息**** */
					ErpPreCustomer preCustomerTemp = this.getErpPreCustomerById(prepCustomer.getId());
					if(preCustomerTemp!=null){
						if(StringUtils.isNotEmpty(customerId)){
							preCustomerTemp.setErpCustomerId(customerId);
						}
						
						if(codeFlag){		//true有code
							preCustomerTemp.setCode(prepCustomer.getCode());	//预防可以修改预导入客户的code的情况
						}else{				//false没有code
							preCustomerTemp.setCode(inCode);					//回写code到预导入表
						}
						
						preCustomerTemp.setEventsNo(eventsNo);	//场次号
						preCustomerTemp.setBatchNo(batchNo);	//批次号

						preCustomerTemp.setStatusYm(150);		//修改Status_YM为 150（样本已获取）
						
						preCustomerTemp.setCheckboxEmilAddr(prepCustomer.getCheckboxEmilAddr());
						preCustomerTemp.setReportSendAddr(prepCustomer.getReportSendAddr());
						preCustomerTemp.setReportReceiveName(prepCustomer.getReportReceiveName());
						preCustomerTemp.setPhone(prepCustomer.getPhone());
						preCustomerTemp.setUpdateTime(new Date());
						preCustomerTemp.setUpdateUserId(userId);
						
						preCustomerTemp.setCode(prepCustomer.getCode());	//预防可以修改预导入客户的code的情况
						/* ****ErpCustomer相关的信息修改**** */
						preCustomerTemp.setWereSex(prepCustomer.getWereSex());
						preCustomerTemp.setWereAge(prepCustomer.getWereAge());
						preCustomerTemp.setWereHeight(prepCustomer.getWereHeight());
						preCustomerTemp.setWereWeight(prepCustomer.getWereWeight());
						preCustomerTemp.setCustomerHistory(prepCustomer.getCustomerHistory());
						preCustomerTemp.setFamilyHistory(prepCustomer.getFamilyHistory());
						preCustomerTemp.setRemark(prepCustomer.getRemark());
						log.info("预导入toString--"+preCustomerTemp.toString());
						dao.update(preCustomerTemp);
					}
					json.accumulate("statusCode", 200);
					json.accumulate("message", "保存成功,批次号为：'"+batchNo+"' !");
					json.accumulate("navTabId", navTabId);
					json.accumulate("callbackType", "closeCurrent");
				
				}else{
					json.accumulate("statusCode", 300);
					json.accumulate("message", "保存失败,创建场次、批次失败！");
				}
				
			}else{
				json.accumulate("statusCode", 300);
				json.accumulate("message", "保存失败，没有获取到该客户的信息！");
			}
			
		}else{
			json.accumulate("statusCode", 300);
			json.accumulate("message", "保存失败！");
		}
		
		return json;
	}
	
	/**
	 * 根据companyId，shipProId获取 批次前缀
	 * @param companyId
	 * @param shipProId
	 * @return
	 * @author tangxing
	 * @date 2017-2-17下午4:36:38
	 */
	public String getBacthPre(String companyId,String shipProId){
		return dao.getBacthPre(companyId, shipProId);
	}
	/**
	 * @param customerId
	 * @author Carly
	 * @since 2017年2月17日12:11:33
	 * @return 通过客户id在预导入表中获取履约单号
	 */
	public List<ErpPreCustomer> getPreCustomerByCustomerId(String customerId) {
		
		return dao.getPreCustomerByCustomerId(customerId);
	}
	/**
	 * @param code
	 * @return 根据条码获取履约单信息
	 */
	public List<ErpPreCustomer> getErpPreCustomerByCode(String code) {
		return dao.getErpPreCustomerByCode(code);
	}
	
	/**
	 * 获取当前毫秒值
	 * @return
	 * @throws ParseException
	 * @author tangxing
	 * @date 2017-2-22上午11:56:46
	 */
	private String getTimeStamp() throws ParseException{
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String strTime = sdFormatter.format(new Date());
		Date dt = sdFormatter.parse(strTime);
		long time= dt.getTime();					//当天00点开始毫秒值（自1970-1-01 00:00:00.000 到当前时刻）
		long currt = System.currentTimeMillis();	//当前毫秒值（自1970-1-01 00:00:00.000 到当前时刻）
		long nowMilles = currt-time;				//当天00点开始到现在时间的毫秒值
		
		return String.valueOf(nowMilles);
	}

	/**
	 * @since 2017年2月24日17:40:48
	 * @author chenqi
	 * @param erpCustomerId
	 * @return 删除对应的erp_customer表的客户
	 */
	public int updateCustomerState(String erpCustomerId) {
		return dao.updateCustomerState(erpCustomerId);
	}
}
