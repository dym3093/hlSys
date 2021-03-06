package org.hpin.statistics.briefness.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List ;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService ;
import org.hpin.common.widget.pagination.Page ;
import org.hpin.events.dao.ErpCustomerBakDao;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.settlementManagement.dao.ErpCustomerSettleBXDao;
import org.hpin.settlementManagement.dao.ErpSettlementCustomerJYDao;
import org.hpin.settlementManagement.dao.ErpSettlementTaskBXDao;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.hpin.settlementManagement.entity.ErpCustomerSettleBX;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.service.ErpSettlementTaskJYService;
import org.hpin.statistics.briefness.dao.QueryDAO ;
import org.hpin.statistics.briefness.entity.GeneReportSummary;
import org.hpin.statistics.briefness.entity.GeneReportSummaryFinance;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreProduceDetail;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;
import org.springframework.transaction.annotation.Transactional ;


/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-8-26 下午11:34:32</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Service(value = "org.hpin.statistics.briefness.service.QueryService")
@Transactional()
public class QueryService extends BaseService {
	
	private Logger log = Logger.getLogger(QueryService.class);

	@Autowired()
	private QueryDAO queryDao = null ;
	
	@Autowired
	ErpCustomerDao customerDao;
	
	@Autowired
	ErpEventsDao eventsDao;
	
	@Autowired
	CustomerRelationshipDao customerRelationshipDao;
	
	@Autowired
	ErpSettlementCustomerJYDao settlementCustomerJYDao;
	
	@Autowired
	ErpCustomerSettleBXDao customerSettleBXDao;
	
	private String localPath;
	
	private String downloadContext;
	
	/**
	 * 求总和;
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年4月7日
	 */
	public Long queryCountSum(String configId, String time) {
		String sql = "";
		if("stat170".equals(configId)) {
			sql = "select sum(count(1)) from erp_customer_temp_wuchuang temp "+
					"inner join HL_CUSTOMER_RELATIONSHIP_PRO pro on pro.CUSTOMER_RELATIONSHIP_ID = temp.BRANCH_COMPANY_ID " +
					"left join T_PROJECT_TYPE ptype on ptype.id = pro.PROJECT_TYPE " +
					"where ptype.PROJECT_TYPE in ('PCT_004', 'PCT_005') ";
			if(time != null && !"".equals(time)) {
				sql += "and temp.create_time <= to_date('"+time+" 23:59:59', 'yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "group by  to_char(temp.create_time, 'yyyy-MM-dd'), pro.PROJECT_CODE, pro.PROJECT_NAME, pro.PROJECT_OWNER ";
			sql += "having pro.PROJECT_NAME is not null";
		}
		
		if("stat171".equals(configId)) {
			sql = "select sum(count(1)) from erp_customer_receive rec " +
			"inner join erp_customer cus on cus.code = rec.servicecode and cus.IS_DELETED = 0 " +
			"inner join HL_CUSTOMER_RELATIONSHIP_PRO pro on pro.CUSTOMER_RELATIONSHIP_ID = rec.BRANCHCOMPANYID " +
			"left join T_PROJECT_TYPE ptype on ptype.id = pro.PROJECT_TYPE " +
			"where 1=1 and rec.RETURNFLAG = 'Yes' and ISMATCH = 1 and ptype.PROJECT_TYPE in ('PCT_004', 'PCT_005') " ;
			
			if(time != null && !"".equals(time)) {
				sql += "and rec.EXAMDATE <= ('"+time+" 23:59:59') ";
			}
			sql += "group by rec.EXAMDATE, pro.PROJECT_CODE, pro.PROJECT_NAME, pro.PROJECT_OWNER ";
			sql += "having pro.PROJECT_NAME is not null ";
		}
		
		return this.queryDao.getJdbcTemplate().queryForLong(sql);
	}
	
	
	public List findBySqlPage(Page page , String sql , Object[] values){
		return queryDao.findBySql(page , sql , values) ;
	}
	
	public List queryAllConfigSql(String sql) throws Exception{
		return queryDao.queryAllConfigSql(sql) ;
	}
	
	public List findByPage(Page page, Map searchMap){
		return queryDao.findByPage(page, searchMap);
	}
	
	public List findByPageFinance(Page page, Map searchMap){
		return queryDao.findByPageFinance(page, searchMap);
	}
	
	/**
	 * 
	 * @param proById
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午4:23:41
	 */
	public String getCustomerRelationShipProById(String cusRelShipId){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		listMap = queryDao.getCustomerRelationShipProById(cusRelShipId);
		String str="";
		if(listMap!=null&&listMap.size()>0){
			String proCode = (String) listMap.get(0).get("project_code");
			String proName = (String) listMap.get(0).get("project_name");
			String proOwner = (String) listMap.get(0).get("project_owner");
			str = proCode+","+proName+","+proOwner;
			return str;
		}else{
			return null;
		}
	}
	
	public String getCRShipProById(String cusRelShipId){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		listMap = queryDao.getCRShipProById(cusRelShipId);
		if(listMap!=null&&listMap.size()>0){
			String proCode = (String) listMap.get(0).get("project_code");
			return proCode;
		}else{
			return null;
		}
	}
	
	/**
	 * 时间段查找场次
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午2:12:11
	 */
	public List<ErpEvents> getErpEventsByDate(Date startTime,Date endTime){
		return eventsDao.getErpEventsByDate(startTime,endTime);
	}
	
	/**
	 * 根据场次号查找客户
	 * @param eventsNo
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午4:28:15
	 */
	public List<ErpCustomer> getErpCustomerByEventsNo(String eventsNo){
		return customerDao.getErpCustomerByEventsNo(eventsNo);
	}
	
	public List<ErpCustomer> getErpCustomerByEventsNoAndStatus(String eventsNo){
		return customerDao.getErpCustomerByEventsNoAndStatus(eventsNo);
	}
	
	/**
	 * 根据code查找结算customer
	 * @param code
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午4:40:46
	 */
	public List<ErpSettlementCustomerJY> getErpSettlementCustomerJYByCode(String code){
		return settlementCustomerJYDao.getErpSettlementCustomerJYByCode(code);
	}
	
	/**
	 * 获取所有基因公司结算套餐价格
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午3:45:32
	 */
	public List<ErpComboPrice> getComboPriceByComboName(String comboName,String geneCompany){
		return queryDao.getComboPriceByComboName(comboName,geneCompany);
	}
	
	
	/**
	 * 获取所有保险公司结算套餐价格
	 * @return
	 * @author tangxing
	 * @date 2016-9-21下午3:45:32
	 */
	public List<ErpCompanyComboPrice> getCompanyComboPriceByComboName(String comboName,String branchCompany){
		return queryDao.getCompanyComboPriceByComboName(comboName,branchCompany);
	}
	
	/**
     * 根据code查找已结算的ErpCustomerSettleBX
     * @param code
     * @return
     * @author tangxing
     * @date 2016-9-1下午5:00:04
     */
	public List<ErpCustomerSettleBX> getErpCustomerSettleBXByCode(String code){
		return customerSettleBXDao.getErpCustomerSettleBXByCode(code);
	}
	
	/**
	 * 根据项目编码查找StoreProduce
	 * @param itemCode
	 * @return
	 * @author tangxing
	 * @date 2016年9月5日11:28:16
	 */
	public List<StoreProduce> getStoreProduceByItemCode(String itemCode,Date startTime,Date endTime){
		return queryDao.getStoreProduceByItemCode(itemCode,startTime,endTime);
	}
	
	public List<StoreProduceDetail> getStoreProduceDetailByProduceId(String produceId){
		return queryDao.getStoreProduceDetailByProduceId(produceId);
	}
	
	/**
	 * 根据Id获取GeneReportSummary
	 * @param id
	 * @return
	 * @author tangxing
	 * @date 2016-9-5下午3:26:01
	 */
	public List<GeneReportSummary> getGeneReportSummaryById(String id){
		return queryDao.getGeneReportSummaryById(id);
	}
	
	public List<GeneReportSummaryFinance> getGeneReportSummaryFinanceById(String id){
		return queryDao.getGeneReportSummaryFinanceById(id);
	}
	
	/**
	 * 根据用户ID获取User
	 * @param userName
	 * @return
	 * @author tangxing
	 * @date 2016年12月21日14:34:35
	 */
	public User getUserByUserId(String UserId){
		return queryDao.getUserByUserId(UserId);
	}
	
	
	/* ************导出Excel************ */
    /**
     * 生成excel及路径
     * @param customerJYs
     * @param settleExceTaskNo
     * @return
     * @author tangxing
     * @date 2016-6-14下午6:46:11
     */
    public String createExSeFilePath(HttpServletRequest request,List<GeneReportSummary> summaries){
    	StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
    	
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
//		String batchFilePath = rootPath.toString()+timePath+"_"+settleExceTaskNo+File.separator;
		String curFilePath = localPath+timePath+"_geneReportSummary"+File.separator;//存放位置
		String str = timePath+"_geneReportSummary"+File.separator;
    	
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdf(summaries);
		String excleName = "geneReportSummary"+".xls";			//文件名
		createExSettleXls(curFilePath.toString(),excleName,rowList);
		
		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return downloadurl;
	}
    
    public String createExSeFilePathTwo(HttpServletRequest request,List<GeneReportSummaryFinance> summaries){
    	StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
    	
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
//		String batchFilePath = rootPath.toString()+timePath+"_"+settleExceTaskNo+File.separator;
		String curFilePath = localPath+timePath+"_geneReportSummaryFinance"+File.separator;//存放位置
		String str = timePath+"_geneReportSummaryFinance"+File.separator;
    	
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdfTwo(summaries);
		String excleName = "geneReportSummaryFinance"+".xls";			//文件名
		createExSettleXlsTwo(curFilePath.toString(),excleName,rowList);
		
		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return downloadurl;
	}
    
    /**
     * 生成Excel每行的内容（异常结算任务客户信息）
     * @param customerJYs
     * @return
     * @author tangxing
     * @date 2016-6-14下午6:20:51
     */
  	//序号、条码、姓名、性别、年龄、身份证号、电话、省、市、支公司、所属公司、套餐、采样日期、营销员、批次号、检测机构
  	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRowByPdf(List<GeneReportSummary> summaries){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		for(int i=0;i<summaries.size();i++){
  			GeneReportSummary content = summaries.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(sdf.format(content.getStartTime()));//条码
  			list.add(content.getItemCode());//姓名
  			list.add(content.getItemHead());//性别
  			list.add(content.getItemName());//年龄
  			list.add(content.getSumNum()==null?"":String.valueOf(content.getSumNum()));//联系方式
  			list.add(content.getSettlementAmountBX()==null?"":String.valueOf(content.getSettlementAmountBX()));//支公司
  			list.add(content.getSettlementAmountJY()==null?"":String.valueOf(content.getSettlementAmountJY()));//检查项目
  			list.add(content.getMoveMatterAmount()==null?"":String.valueOf(content.getMoveMatterAmount()));//样本类型
  			result.add(list);
  		}
  		return result;
  	}
  	
  	//序号、条码、姓名、性别、年龄、身份证号、电话、省、市、支公司、所属公司、套餐、采样日期、营销员、批次号、检测机构
  	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRowByPdfTwo(List<GeneReportSummaryFinance> summaries){
  		List<List<String>> result = new ArrayList<List<String>>();
  		for(int i=0;i<summaries.size();i++){
  			GeneReportSummaryFinance content = summaries.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getMonth());//条码
  			list.add(content.getItemCode());//姓名
  			list.add(content.getItemHead());//性别
  			list.add(content.getItemName());//年龄
  			list.add(content.getHaveReportNum()==null?"":String.valueOf(content.getHaveReportNum()));//联系方式
  			list.add(content.getSettlementAmountBX()==null?"":String.valueOf(content.getSettlementAmountBX()));//支公司
  			list.add(content.getSettlementAmountJY()==null?"":String.valueOf(content.getSettlementAmountJY()));//检查项目
  			result.add(list);
  		}
  		return result;
  	}
  	
  	/**
  	 * 生成异常结算任务客户信息EXCEL
  	 * @param path
  	 * @param filename
  	 * @param rowList
  	 * @author tangxing
  	 * @date 2016-6-14下午6:21:23
  	 */
  	public void createExSettleXls(String path ,String filename, List<List<String>> rowList) {
		
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("开始时间");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("项目编码");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("项目负责人");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("项目名称");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("会员人数");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("保险公司结算金额");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("基因公司结算金额");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("物料领用金额");
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
  	 * 生成EXCEL
  	 * @param path
  	 * @param filename
  	 * @param rowList
  	 * @author tangxing
  	 * @date 2016-6-14下午6:21:23
  	 */
  	public void createExSettleXlsTwo(String path ,String filename, List<List<String>> rowList) {
		
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("月份");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("项目编码");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("项目负责人");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("项目名称");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("已出报告人数");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("保险公司结算金额");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("基因公司结算金额");
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
	
}

