package org.hpin.physical.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Hlookup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.base.customerrelationship.entity.vo.CustomerRelationShipVO;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.physical.dao.PhyReportDao;
import org.hpin.physical.entity.PhyCompleteReport;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.entity.PhyReportItem;
import org.hpin.physical.entity.PhyReportTemp;
import org.hpin.physical.vo.PhyReportInfoVO;
import org.hpin.physical.vo.PhyReportQueryObject;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * matchPhyReportTemp()方法做的PhyReport，PhyReportItem对象处理为：1、只要生成对象失败，就不会执行pdf的生成；2、失败一个，全部回滚
 */

@Service(value = "org.hpin.physical.service.PhyReportService")
@Transactional()
public class PhyReportService extends BaseService {

	@Autowired
	PhyReportDao dao;
	
	@Autowired
	ReportInfoService infoService;
	
	private Integer excelSize;
	
    
    @Value("#{setting['mail.account']}")
	private String sendMailAccount; //发件人账户;
	@Value("#{setting['mail.password']}")
	private String sendMailPassword; //发件人密码;
	@Value("#{setting['receive.receiveMailAccount']}")
	private String receiveMailAccount; //收件人账号;
	
	private Logger log = Logger.getLogger(PhyReportService.class);
	
	public ErpCustomer getErpCustomerByCode(String code){
		List<ErpCustomer> customers = dao.getErpCustomerByCode(code);
		if(customers!=null&&customers.size()>0){
			return customers.get(0);
		}
		return null;
	}
	
	/**
	 * 根据completeReportId获取XreportPrintContent
	 * @param id
	 * @return
	 * @author tangxing
	 * @date 2016-10-27下午9:55:17
	 */
	/*public XreportPrintContent getPdfContentById(String id){
		List<XreportPrintContent> contents = dao.getPdfContentById(id);
		XreportPrintContent contentTemp = null;
		if(contents!=null&&contents.size()>0){
			contentTemp = contents.get(0);
		}
		return contentTemp;
	}
	
	public XreportPdfInfo getPdfInfoById(String id){
		return dao.getPdfInfoById(id);
	}*/
	
	/**
	 * 根据ID获取PhyReport
	 * @param reportId
	 * @return
	 * @author tangxing
	 * @date 2016-10-26下午4:59:38
	 */
	public PhyReport getPhyReportById(String reportId){
		return dao.getId(reportId);
	}
	
	/**
	 * 获取所有PhyCompleteReport
	 * @return
	 * @author tangxing
	 * @date 2016-10-27下午9:39:36
	 */
	public List<PhyCompleteReport> getAllcompleteReport(){
		return dao.getAllcompleteReport();
	}
	
	
	/**
	 * 根据ID获取XreportPdfInfo
	 * @param reportId
	 * @return
	 * @author tangxing
	 * @date 2016年10月26日17:50:35
	 */
	/*public XreportPdfInfo getPdfInfoId(String id){
		return dao.getPdfInfoById(id);
	}*/
	
	/**
	 * 获取所有为拆分固定页的PDF
	 * @return
	 * @author tangxing
	 * @date 2016-10-26下午4:38:52
	 */
	public List<PhyCompleteReport> getPhyCompleteReportByStatus(){
		return dao.getPhyCompleteReportByStatus();
	}
	
	public PhyCompleteReport getPhyCompleteReport(String code,String name){
		List<PhyCompleteReport> reports = dao.getPhyCompleteReport(code,name);
		if(reports!=null&&reports.size()>0){
			return reports.get(0);
		}
		return null;
	}
	
	/**
	 * 根据会员的code和name获取对应的申友提供的PDF信息
	 * @param code
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016年10月21日15:16:36
	 */
	/*public XreportPdfInfo getReportPdfInfo(String code,String name){
		List<XreportPdfInfo> infos = dao.getReportPdfInfo(code,name);
		if(infos!=null&&infos.size()>0){
			return infos.get(0);
		}
		return null;
	}*/
	
	
	/**
	 * 获取所有PhyReport(已有12页报告的)
	 * @return
	 * @author tangxing
	 * @date 2016年10月19日16:17:34
	 */
	public List<PhyReport> getAllPhyReport(){
		return dao.getAllPhyReport();
	}
	
	/**
	 * 根据条件获取报告
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午5:58:23
	 */
	public List<PhyReport> getPhyReport(Page page,Map searchMap){
		return dao.findByPage(page, searchMap);
	}
	
	/**
	 * 获取所有
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-6-29下午4:00:04
	 */
	public List<PhyReport> listPhyReport(Page page,Map searchMap){
		return dao.findByPage(page, searchMap);
	}
	
	/* *****************导入Excel****************** */
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-6-23下午9:08:30
	 */
	public TreeSet<String> saveReport(File file,String time) throws Exception {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		TreeSet<String> codeSets = new TreeSet<String>();
		
		List<Map<String, String>> result = ExcelUtils.importSettlementExcel(file);
		
		excelSize = result.size();
		
		if(excelSize<=10000){
			//Set<PhyReportTemp> phyReportTemps = createPhyReportsObj(result,currentUser,time);	//读取出来的List
			Set<PhyReportTemp> phyReportTemps = createPhyReportsObj(result);	//读取出来的List
			codeSets = matchPhyReportTemp(phyReportTemps);
			
			//保存比对好的结果
//			this.savePhyReports(phyReports);
		}
		
		return codeSets;
	}

	/**
	 * 将EXCEL中的数据组装对象
	 * @param result
	 * @param user
	 * @return
	 * @throws ParseException
	 * @author tangxing
	 * @date 2016-6-23下午9:12:43
	 */
	public Set<PhyReportTemp> createPhyReportsObj(List<Map<String, String>> result) throws ParseException,Exception {
		List<PhyReportTemp> list = new ArrayList<PhyReportTemp>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Set<PhyReportTemp> sets = new HashSet<PhyReportTemp>();
		//SimpleDateFormat sdfTwo=new SimpleDateFormat("yyyyMMddHHmmss");
		/*String s1 = time.replace("-", "");
		String s2 = s1.replace(":", "").replace(" ", "");
		String batchNo = s2;*/
		
		Date now = new Date();
		for(Map<String, String> map : result){
			PhyReportTemp reportTemp = new PhyReportTemp();
			// 第一列是姓名,第二列是性别,第三列是年龄,第四列是身份证号,第五列是条码,第六列是报告日期,第七列套餐编码,第八列套餐名称,
			// 第九列疾病编号,第十列疾病名称,第十一列风险等级，十二列风险等级数字
			if(map.get("4")==null||StringUtils.isEmpty(map.get("4"))){
				continue;
			}
			reportTemp.setUserName(map.get("0"));
			reportTemp.setUserSex(map.get("1"));
			reportTemp.setUserAge(map.get("2"));
			reportTemp.setUserIdno(map.get("3"));
			reportTemp.setGeneCode(map.get("4"));
			log.info("createPhyReportsObj map value get(5)--"+map.get("5"));
			if(map.get("5")!=null&&map.get("5").length()>0&&map.get("5")!="null"){
				reportTemp.setReportDate(sdf.parse(map.get("5")));
			}
			reportTemp.setComboCode(map.get("6"));
			reportTemp.setComboName(map.get("7"));
			//customerJY.setFamily_history(map.get("6"));
			reportTemp.setGeneDiseaseCode(map.get("8"));
			reportTemp.setGeneDiseaseName(map.get("9"));
			reportTemp.setGeneRiskRemark(map.get("10"));
			if(map.get("11")!=null&&map.get("11").length()>0&&map.get("11")!="null"){
				reportTemp.setGeneRiskGrade(Integer.valueOf(map.get("11")));
			}
			//reportTemp.setReportBatch(batchNo);		//批次
			
			list.add(reportTemp);
		}
		sets.addAll(list);	//排重
		log.info("PhyReportTemp List size----"+list.size());
		return sets;
	}
	
	/**
	 * 入库并生成PDF
	 * @param reports
	 * @return
	 * @author tangxing
	 * @date 2016-6-23下午9:12:30
	 */
	public TreeSet<String> matchPhyReportTemp(Set<PhyReportTemp> reports) throws Exception{
		Date date = new Date();
		//List<String> saveSuccessRepostItem = new ArrayList<String>();
		//List<String> saveSuccessReport = new ArrayList<String>();
		TreeSet<String> set = new TreeSet<String>();
		List<PhyReport> phyReports = new ArrayList<PhyReport>();
		List<PhyReportItem> reportItems = new ArrayList<PhyReportItem>();
		PhyReport report = null;
		PhyReportItem reportItem = null;
		Map<String,String> idMap = new HashMap<String,String>();		//存放当前条形码对应的ReportID
		if(reports.size()>0){
			for (PhyReportTemp phyReportTemp : reports) {
				String geneCode = phyReportTemp.getGeneCode();
				if(!set.contains(geneCode)){
					report = new PhyReport();
					String reportId = UUID.randomUUID().toString().replace("-", "");
					idMap.put(geneCode, reportId);
					
					
					/* ****个人检查报告**** */
					report.setId(reportId);
					report.setUserName(phyReportTemp.getUserName());
					report.setUserSex(phyReportTemp.getUserSex());
					report.setUserAge(phyReportTemp.getUserAge());
					report.setUserIdno(phyReportTemp.getUserIdno());
					report.setGeneCode(phyReportTemp.getGeneCode());
					report.setReportDate(phyReportTemp.getReportDate());
					report.setComboName(phyReportTemp.getComboName());
					report.setComboCode(phyReportTemp.getComboCode());
					report.setReportBatch(phyReportTemp.getReportBatch());
					report.setImportDate(date);
					
					phyReports.add(report);
					set.add(geneCode);
				}
				
				/*if(phyReportTemp.getGeneRiskGrade()==null){
					report.setReportFlag("1");					//标记为异常
				}else{
					report.setReportFlag("0");
				}*/
				
				/* ****检查报告项目**** */
				reportItem = new PhyReportItem();
				String ymDisCode = "";
				String ymDisName = "";
				List<Map<String, Object>> maps = dao.getGeneYmDiseaseByDisCode(phyReportTemp.getGeneDiseaseCode());
				if(maps.size()>0){
					ymDisCode = (String) maps.get(0).get("YM_DISEASE_CODE");
					ymDisName = (String) maps.get(0).get("YM_DISEASE_NAME");
				}
				reportItem.setReportId(idMap.get(geneCode));
				reportItem.setGeneDiseaseName(phyReportTemp.getGeneDiseaseName());
				reportItem.setGeneDiseaseCode(phyReportTemp.getGeneDiseaseCode());
				reportItem.setGeneCode(phyReportTemp.getGeneCode());//冗余的基因条码字段
				reportItem.setGeneRiskGrade(phyReportTemp.getGeneRiskGrade());
				reportItem.setGeneRiskRemark(phyReportTemp.getGeneRiskRemark());
				reportItem.setYmDiseaseCode(ymDisCode);
				reportItem.setYmDiseaseName(ymDisName);
				
				reportItems.add(reportItem);
			}
			
			//removeRepeatReport(set);
		}
		
		try {
			if(phyReports.size()>0){
				for (PhyReport phyReport: phyReports) {
					dao.saveReport(phyReport);
				}
			}
			if(reportItems.size()>0){
				for (PhyReportItem phyReportItem : reportItems) {
					dao.saveReportItem(phyReportItem);
				}
			}
		} catch (Exception e) {
			for (PhyReport phyReport: phyReports) {
				dao.delete(phyReport);
			}
			for (PhyReportItem phyReportItem : reportItems) {
				dao.delete(phyReportItem);
			}
			e.printStackTrace();
			log.error("matchPhyReportTemp save ",e);
		}
	
		return set;
	}
	
	/**
	 * 统计导入的信息
	 * @return
	 * @author tangxing
	 * @date 2016-6-28上午11:15:08
	 */
	public Integer excelSize(){
		Integer num = excelSize;
		return num;
	}
	
	/**
	 * 根据geneCode获取age
	 * @param geneCode
	 * @return
	 * @author tangxing
	 * @date 2016-7-1上午11:51:39
	 */
	public String getReportAge(String geneCode){
		List<Map<String, Object>> listMap = dao.getOwnedCompAndAgeByGeneCode(geneCode);
		String age = "";
		if(listMap.size()>0){
			for (Map<String, Object> map : listMap) {
				age = (String) map.get("AGE");
			}
		}
		return age;
	}
	
	public List<PhyReport> getPhyReportByCode(String geneCode){
		return dao.getPhyReportByCodeAndIsSuccess(geneCode);	
	}
	
	/**
	 * 获取 createPdfStatus状态为空的PhyReport
	 * @return
	 * @author tangxing
	 * @date 2016-12-6下午12:24:19
	 */
	public List<PhyReport> getPhyReportByCreatePdfStatusNull(){
		return dao.getPhyReportByCreatePdfStatusNull();
	}
	
	/**
	 * 根据 createPdf status获取能生成报告的PhyReport
	 * @return
	 * @author tangxing
	 * @date 2016年12月6日15:02:53
	 */
	public List<PhyReport> getPhyReportByCreatePdfStatus(){
		return dao.getPhyReportByCreatePdfStatus();
	}
	
	/**
	 * 分页查询excel文件路径类
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-12-6 18:02:57
	 */
	public List findByPageExcelFile(Page page, Map searchMap) {
		return dao.findByPageExcelFile(page, searchMap);
	}
	
	/**
	 * 1+X日报显示查询方法
	 * @param page
	 * @param queryObject
	 * @author tangxing
	 * @date 2016-12-7下午4:23:34
	 */
	public void findReportAll(Page page,PhyReportQueryObject queryObject){
		String temp1 = "";
		String temp2 = "";
		String strPath = "ymdata";
		String strPathFtp = "ftp";
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), 
				url.length()).toString();
		List<Object> params = new ArrayList<Object>(); //参数;
		//去掉了'select '
		String sql = 
			"  t.pcode             as geneCode, "+
		      " t.pusername        as username, "+
		      " t.pcombo           as combo, "+
		      " t.pdept            as dept, "+
		      " t.pfilepath        as geneReportPath, "+
		      " t.preportpath      as reportPath, "+
		      " t.pbranchcompanyid as branchCompanyId, "+
		      " t.pownedcompanyid  as ownedcompanyId, "+
		      " t.pbatchno         as reportBatchNo, "+
		      " t.pisdeleted       as isdeleted, "+
		      " t.pimportDate       as importDate "+
		  "from (select cus.code                 as pcode, "+
		              " cus.name                 as pusername, "+
		              " cus.setmeal_name         as pcombo, "+
		              " cus.branch_company_id    as pbranchCompanyId, "+
		              " cus.owned_company_id     as pownedCompanyId, "+
		              " cus.department           as pdept, "+
		              " cus.is_deleted           as pisdeleted, "+
		              " event.batchno            as pbatchno, "+
		              " reportPdf.Filepath       as pfilePath, "+
		              " preport.report_path      as preportpath, "+
		              " preport.createpdf_status as pcreatepdfstatus, "+
		              " preport.import_date      as pimportDate "+
		         " from erp_customer                cus, "+
		              " erp_events                  event, "+
		              " erp_reportdetail_pdfcontent reportPdf, "+
		              " phy_report                  preport "+
		        " where cus.events_no = event.events_no "+
		          " and cus.id = reportPdf.CUSTOMERID "+
		          " and cus.code = preport.gene_code) t "+
		  " where t.pcreatepdfstatus = '1' "+
		    " and t.pisdeleted = '0' ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		if(queryObject!=null){
			if(StringUtils.isNotEmpty(queryObject.getReportBatchNo())){			//批次号
				jdbcSql.append(" and t.pbatchno = '").append(queryObject.getReportBatchNo().trim()).append("' ");
			}else if(StringUtils.isNotEmpty(queryObject.getGeneCode())){		//条形码
				jdbcSql.append(" and t.pcode = '").append(queryObject.getGeneCode().trim()).append("' ");
			}else if(StringUtils.isNotEmpty(queryObject.getUserName())){		//姓名
				jdbcSql.append(" and t.pusername = '").append(queryObject.getUserName().trim()).append("' ");
			}else if(StringUtils.isNotEmpty(queryObject.getCombo())){			//套餐名
				jdbcSql.append(" and t.pcombo = '").append(queryObject.getCombo().trim()).append("' ");
			}
		}
		
		//用于查询总数
		StringBuilder jdbcSqlTemp = new StringBuilder("select ");
		//查询count;
		page.setTotalCount(dao.findJdbcCount(jdbcSqlTemp.append(jdbcSql), params));
		
		//查询list;
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<PhyReportInfoVO> rowMapper = new BeanPropertyRowMapper<PhyReportInfoVO>(PhyReportInfoVO.class);
		//page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
		List<PhyReportInfoVO> list = (List<PhyReportInfoVO>) dao.findJdbcListLocal(jdbcSql.toString(), params, rowMapper);
		
		/* ****转换为地址**** */
		for (PhyReportInfoVO object : list) {
			String reportPath = object.getReportPath();
			String geneReportPath = object.getGeneReportPath();
			/*if(reportPath!=null&&reportPath.length()>0){
				temp1 = reportPath.substring(reportPath.indexOf(strPath)+strPath.length(), reportPath.length());
				object.setReportPath(contextUrl+temp1);
			}*/
			if(reportPath!=null&&reportPath.length()>0){
				temp1 = reportPath.substring(reportPath.indexOf(strPath)+strPath.length(), reportPath.length());
				object.setReportPath(contextUrl+temp1);
			}	
			if(geneReportPath!=null&&geneReportPath.length()>0){
				temp2 = geneReportPath.substring(geneReportPath.indexOf(strPathFtp)+strPathFtp.length(), geneReportPath.length());
				object.setGeneReportPath(contextUrl+temp2);
			}
		}
		page.setResults(list);
	}
	
	/* *****  1+X风险数据提交跟踪 方法 start ***** */
	
	/**
	 * 获取项目套餐对应表(isCreatePdf 为 '1'的)
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午4:55:23
	 */
	public List<ErpRelationShipCombo> getErpRelationShipComboByCreatePdf(){
		return dao.getErpRelationShipComboByCreatePdf(); 
	}
	
	/**
	 * 根据项目ID、套餐ID,组装数据为：支公司id，套餐名字
	 * @param shipProId
	 * @param comboId
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午5:32:54
	 */
	public HashMap<String, String> getBranchIdComboName(String shipProId,String comboId){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String branchCompanyId = dao.getBranchCompanyIdByShipProId(shipProId);
		String comboName = dao.getComboNameByComboId(comboId);
		hashMap.put("branchCompanyId", branchCompanyId);
		hashMap.put("comboName", comboName);
		return hashMap;
	}
	
	/**
	 * 根据支公司ID和套餐名获取ErpCustomer
	 * @param branchId
	 * @param comboName
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午5:42:08
	 */
	public List<String> getCustomerByBranchIdAndComboName(String branchId,String comboName){
		List<String> codeList = new ArrayList<String>();
		String code = "";
		List<Map<String, Object>> results = dao.getCustomerByBranchIdAndComboName(branchId, comboName);
		if(results!=null&&results.size()>0){
			for (Map<String, Object> map : results) {
				code = (String) map.get("code");
				codeList .add(code);
			}
		}
		return codeList;
	}
	
	/**
	 * 获取有报告的1+X数据
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午6:44:14
	 */
	public List<String> getPhyReportByReportPath(){
		List<String> codeList = new ArrayList<String>();
		String code = "";
		List<Map<String, Object>> results = dao.getPhyReportByReportPath();
		if(results!=null&&results.size()>0){
			for (Map<String, Object> map : results) {
				code = (String) map.get("code");
				codeList .add(code);
			}
		}
		return codeList;
	}
	
	/**
	 * 拿到报告的获取时间(matchstate = '2')
	 * @param code
	 * @param comboName
	 * @return
	 * @author tangxing
	 * @date 2017-2-22下午7:03:46
	 */
	public String getPdfContentTime(String code,String comboName){
		String createTime = "";
		List<Map<String, Object>> results = dao.getPdfContentTime(code);
		if(results!=null){
			if(results.size()==1){		//根据code查找条数等于1
				createTime = (String) results.get(0).get("createDate");
			}
			if(results.size()>1){		//根据code查找条数大于1
				/*for (Map<String, Object> map : results) {
					String combo = (String) map.get("comboName");
					if(comboName.equals(combo)){		//先判断是否有套餐相等的	相等直接返回createDate
						return (String) map.get("createDate");
					}else{
						if(combo.indexOf(comboName)!=-1){
							createTime = (String) map.get("createDate");
						}
					}
				}*/
				
				for (Map<String, Object> map : results) {
					String combo = (String) map.get("comboName");
					if(comboName.equals(combo)){		//先判断是否有套餐相等的	相等直接返回createDate
						createTime = (String) map.get("createDate");
					}
				}
			}
		}
		return createTime;
	}
	
	/**
	 * 获取当前code最新的报告时间
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-23上午11:37:59
	 */
	public String getPdfContentMaxTime(String code){
		return dao.getPdfContentMaxTime(code);
	}
	
	/**
	 * 判断是否存在该code的phyReport(没有找到数据返回 false)
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-23下午1:26:31
	 */
	public boolean getReportByCodeJDBC(String code) throws Exception{
		boolean flag = false;
		int count = dao.getReportByCodeJDBC(code);
		if(count>0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 获取customer的相关信息（用于生成excel）
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2017-2-23下午1:45:51
	 */
	public Map<String, Object> getExcelCustomerInfo(String code){
		Map<String, Object> maps = null;
		List<Map<String, Object>> results = dao.getExcelCustomerInfo(code);
		if(results!=null&&results.size()>0){
			maps = results.get(0);
		}
		return maps;
	}
	
	/**
	 * 创建excel
	 * @param request
	 * @param customerInfos
	 * @param localPath
	 * @param downloadContext
	 * @return
	 * @author tangxing
	 * @date 2017-2-23下午2:36:51
	 */
    public Map<String, String> createExSeFilePath(List<Map<String, Object>> customerInfos,String localPath,String downloadContext){
		//HttpServletRequest request = ServletActionContext.getRequest();
    	//StringBuffer url = request.getRequestURL();
    	Map<String, String> fileMap = new HashMap<String, String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
		String time = sdf.format(new Date());
		
		//String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String curFilePath = localPath+File.separator;//存放位置
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRow(customerInfos);
		String excelName = "phyReportCustomerInfo"+time+".xls";			//文件名
		this.createExSettleXls(curFilePath.toString(),excelName,rowList);
		
		//String downloadurl = contextUrl+ excleName;		//下载路径
		
		fileMap.put("filePath", curFilePath);
		fileMap.put("excelName", excelName);
		
		return fileMap;		//文件路径
	}
    
    private List<List<String>> buildExcelRow(List<Map<String, Object>> customerInfos){
    	List<List<String>> listAll = new ArrayList<List<String>>();
    	for(int i=0;i<customerInfos.size();i++){
    		List<String> list = new ArrayList<String>();
    		Map<String, Object> map = customerInfos.get(i);
  			list.add(String.valueOf(i+1));//序号
  			list.add((String)map.get("batchNo"));
  			list.add((String)map.get("eventsNo"));
  			list.add((String)map.get("code"));
  			list.add((String)map.get("name"));
  			listAll.add(list);
    	}
    	return listAll;
    }
    
	/**
	 *  邮件通知功能
	 * @return
	 * @author tx
	 * @param email
	 * @param userName
	 * @date  2017年2月23日
	 */
	public void sendEmail( Map<String, String> excelFileMap){
		try{
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			String path = excelFileMap.get("filePath");
			String excleName = excelFileMap.get("excelName");
			File file = new File(path, excleName);
			List<File> files = new ArrayList<File>();
			files.add(file);
			log.info("***************超时1+X风险数据code--邮件发送开始****************");
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
			
			//TODO 正式环境添加 收件人邮箱 christywang@healthlink.cn
			receiver.add("christywang@healthlink.cn");
			receiver.add("mingyang@healthlink.cn");
			receiver.add("xingtang@healthlink.cn");
			
			mail.setReceiver(receiver);
			//附件
			mail.setAttachMents(files);
			//主题
			mail.setSubject(df.format(new Date())+"--超时1+X风险数据code！");
			
			//内容
			mail.setMessage(df.format(new Date())+",超时1+X风险数据code！");
			
			MailUtil.send(mail);
			log.info("***************超时1+X风险数据code--邮件发送结束****************");
		}catch(Exception e){
			log.error("sendEmail--error: ",e);
		}

	}
    
    
    private void createExSettleXls(String path ,String filename, List<List<String>> rowList) {
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("未收到1+X风险数据");
			
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
			cell2.setCellValue("场次号");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("条形码");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("姓名");
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
	
	/* *****  1+X风险数据提交跟踪 方法 end ***** */
	
	
}
