package org.hpin.reportdetail.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.FileUtil;
import org.hpin.common.util.PropsUtils;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.reportdetail.dao.ErpPrintTaskDao;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpPrintTask;
import org.hpin.reportdetail.entity.ErpPrintTaskBean;
import org.hpin.reportdetail.util.BuidReportExcel;
import org.hpin.reportdetail.util.DealWithFileUtil;
import org.hpin.reportdetail.util.ZipFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* author: dengqin 
* createDate: 2016-4-14 下午5:38:19
*/


@Service(value = "org.hpin.reportdetail.service.ErpPrintTaskService")
@Transactional()
public class ErpPrintTaskService extends BaseService{
	
	private Logger log = Logger.getLogger(ErpPrintTaskService.class);
	
	private String localPath;
	
	private String downloadContext;

    private BuidReportExcel buildExcel = new BuidReportExcel();
    
    private ZipFileUtil zipUtil = new ZipFileUtil();
	
    @Autowired
    ErpPrintTaskDao dao;

	// add by Dayton 2016-12-16 start
	@Autowired
	private ErpCustomerService customerService;

	@Value("#{setting['mail.host.printTask']}")
	private String mailHost;

	@Value("#{setting['mail.account']}")
	private String sendAccount;

	@Value("#{setting['mail.password']}")
	private String sendPassword;

	@Value("#{setting['mail.account.printTask']}") //接收人的邮箱
	private String receiverAccount;

	@Value("#{setting['mail.nickName.printTask']}")
	private String nickName; //邮件中的昵称

	@Value("#{setting['mail.subject.printTask']}")
	private String subject; //邮件主题
	// add by Dayton 2016-12-16 end

	@Value("#{setting['mail.content.printTask']}")
	private String mailContent; //邮件内容  add by Damian 2017-03-14

    /**
     * 分页获取用户
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        return dao.findByPage(page, searchMap);
    }
    
    public List getAllInputNumsList(String userName){
        List list=dao.getAllInputNumsList(userName);
        return list;
    }
    
    /**
     * 保存
     * @param printTask
     */
    public void save(ErpPrintTask printTask){
    	System.out.println("-----打印任务的保存-----");
        dao.save(printTask);
    }
    
//    /*删除或修改*/
//    public void delete(List<ErpConference> list) {
//        for(ErpConference conference:list){
//            String conferenceNo = conference.getConferenceNo();
//            String sql = " delete from ERP_CUSTOMER as c where c.CONFERENCE_NO ='"+conferenceNo+"';";
////            cDao.getJdbcTemplate().update(sql);
//            dao.update(conference);
//        }
//    }
    
    /**
     * 修改
     * @description 
     * @param printTask
     * @return void
     * @throws
     *
     */
    public void modify(ErpPrintTask printTask){
        
        ErpPrintTask oldErpPrintTask=get(printTask.getId());
        //拷贝属性 复制属性
        ReflectionUtils.copyPropertiesForHasValue(oldErpPrintTask, printTask);
        dao.saveOrUpdate(oldErpPrintTask);
    }
    
    /**
     * 根据ID获取批次
     * @description 
     * @param id
     * @return
     *
     * @return ErpPrintBatch
     * @throws
     *
     */
    public ErpPrintTask get(String id){
        return dao.get(id);
    }
    
    /**
     * 是否为最大批次号
     * @description 
     * @param date
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String maxNo(String date){
        return dao.maxNo(date);
    }
    
    /**
     * 判断是否重复
     * @description 
     * @param printTaskNo
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String isNotRepeatNo(String printTaskNo){
        return dao.isNotRepeatNo(printTaskNo);
    }
    
    /**
	 * 将打印的批次生成同一个压缩包，提供下载路径
	 * @return 下载地址
	 * @throws Exception 
	 */
//	public String createDownLoadFile(HttpServletRequest request,List<Map<String,List<ErpReportdetailPDFContent>>> pdfContents,String taskName) throws Exception{
//		StringBuffer url = request.getRequestURL();
//		//含域名上下文的URL
//		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String timePath = sdf.format(new Date());
//		StringBuilder rootPath = new StringBuilder(localPath + File.separator + timePath + File.separator + taskName + File.separator);
//		int pdfNum = 0;
//		//拷贝PDF文件到目录下
//		for(Map<String, List<ErpReportdetailPDFContent>> content : pdfContents){
//			for (Entry<String, List<ErpReportdetailPDFContent>> mapContent : content.entrySet()) {
//				   String key = mapContent.getKey();
//				   List<ErpReportdetailPDFContent> pdfContentList = mapContent.getValue();
//				   pdfNum += pdfContentList.size();
//				   String curFilePath = rootPath.toString()+File.separator+key+File.separator;
//				   File filePath = new File(curFilePath);
//					if(!filePath.exists()){
//						filePath.mkdirs();
//					}
//				   for(ErpReportdetailPDFContent pdfContent : pdfContentList){
//					   DealWithFileUtil.getInstance().copyReportFile(pdfContent.getFilepath(),curFilePath,null);
//				   }
//				   //构建Excel文件
//				   List<List<String>> rowList = DealWithFileUtil.getInstance().buildExcelRowByPdf(pdfContentList);
//				   String excleName = key+".xls";
//				   buildExcel.writeXls(curFilePath.toString(),excleName,rowList);
//			}
//		}
//		
//		String zipName = timePath+"_"+pdfNum +".zip";
//		String zipPathAndName = localPath +zipName;
//		zipUtil.fileToZip(zipPathAndName,rootPath.toString());
//		//删除拷贝的文件
//		DealWithFileUtil.getInstance().delFileByPath(rootPath.toString());
//		
//		String downloadurl = contextUrl + zipName;
//		
//		log.info("taskName is "+taskName+": print task download url --- "+downloadurl);
//		return downloadurl;
//	}

	/**
	 * 将打印的批次包生成在同一个压缩包，提供下载路径
	 * @return 下载地址
	 * @throws Exception 
	 */
	public String createDownLoadFileByPrintBth(HttpServletRequest request,List<ErpPrintBatch> printBatch,String printTaskNo) throws Exception{
		StringBuffer url = request.getRequestURL();
		//含域名上下文的URL
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
//		String timePath = sdf.format(new Date());
		//StringBuilder rootPath = new StringBuilder(localPath + File.separator + timePath + File.separator + taskName + File.separator);
		List<String> pdfPathList = getFileListByBth(printBatch);
//		String pdfNum = getToPrintNum(printBatch);
//		String zipName = timePath+"_"+pdfNum +".zip";//以前的解压文件名字规则(时间+数量)
		String zipName = printTaskNo + ".zip";		 //任务编号
		String zipPathAndName = localPath +zipName;
		zipUtil.zipFolder(zipPathAndName,pdfPathList);
		//zipUtil.fileToZip(zipPathAndName,rootPath.toString());
		//删除拷贝的文件
		//DealWithFileUtil.getInstance().delFileByPath(rootPath.toString());
		String downloadurl = contextUrl + zipName;
		log.info("printTaskNo is "+printTaskNo+": print task download url --- "+downloadurl);
		return downloadurl;
	}
	
	//生成PDF地址集合
	private List<String> getFileListByBth(List<ErpPrintBatch> printBath){
		List<String> list = new ArrayList<String>();
		for(ErpPrintBatch batch : printBath){
			if(null!=batch.getBatchFilePath()){
				list.add(batch.getBatchFilePath());
			}
		}
		log.info("getFileListByBth list size --- "+list.size());
		return list;
	}
	//获取总共需要打印PDF数量
	private String getToPrintNum(List<ErpPrintBatch> printBath){
		int num = 0;
		for(ErpPrintBatch batch : printBath){
			if(null!=batch.getCount()){
				num +=batch.getCount();
			}
		}
		return String.valueOf(num);
	}
	
	//获取上传条码EXCEL文件中的数据
//	public List<String> getCodeByExcel(File exlFile) {
//		Workbook workbook = null;
//		List<String> codeList = new ArrayList<String>();
//		try{
//			InputStream in = new FileInputStream(exlFile);
//			workbook = Workbook.getWorkbook(in);
//			Sheet[] sheetNum = workbook.getSheets();
//			System.out.println("上传的sheet的个数："+sheetNum.length);
//			Sheet sheet = workbook.getSheet(0);
//			Cell cell = null;
//			int rowCount = sheet.getRows(); //行数
//			for (int i = 0; i < rowCount; i++) {
//				cell = sheet.getCell(0, i);
//				codeList.add(cell.getContents());
//			}
//			workbook.close();
//
//		}catch(Exception e){
//			log.error("ErpReportdetailService getCodeByExcel ",e);
//			return codeList;
//		}
//		return codeList;
//	}
	
	/**
	 * 
	 * @param codes 条码
	 * @param exact 是否精确查询
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-27 下午5:34:16
	 */
	public List<ErpPrintTask> listPrintTaskByCodes(String codes, boolean exact) throws Exception{
		List<ErpPrintTask> list = null;
		if(StringUtils.isNotEmpty(codes)){
			list = dao.listPrintTaskNoByCodes(codes, exact);
		}
		return list;
	}
	
	/**
	 * 处理要下载的文件:
	 * 1.根据提供id查询对应的zip文件路径;
	 * 2.复制移动到指定文件夹;
	 * 3.zip压缩2中所有文件在下载目录下;
	 * create by henry.xu 20160812
	 * @return 下载路径;
	 * @throws Exception 
	 */
	public String dealBatchDownload(String ids, HttpServletRequest request) throws Exception {
		StringBuffer url = request.getRequestURL();
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String downloadurl = "";
		//当ids不为空时;
		if(StringUtils.isNotEmpty(ids)) {
			//创建将要拷贝到的文件夹路径;
			long millis = System.currentTimeMillis(); //获取当前时间millis
			String rootPath = new StringBuilder(File.separator +localPath+ millis + File.separator).toString();//D:/ymdownload/1480931218697\  --D:/ymdownload/\1480931265967\
			String[] idsArr = ids.split(",");
			/*
			 * 遍历处理数组idsArr;
			 */ 
			int count = 0; //记录遍历数,用于zip文件名称后面;
			for(String str : idsArr) {
				if(StringUtils.isNotEmpty(str)) {
					this.modifiedDownloadStatusById(str); //修改下载状态; modified by henry.xu 20161024
					
					ErpPrintTask erpPrintTask = (ErpPrintTask)dao.findById(ErpPrintTask.class, str); //根据ID查找打印任务数据;
					String downLoadPath  = erpPrintTask.getDownLoadPath();
					//当下载路径不为空时;
					if(StringUtils.isNotEmpty(downLoadPath)) {
						//获取到文件名称;
						String zipFileName = downLoadPath.substring(downLoadPath.lastIndexOf("/")+1, downLoadPath.length());
						String zipFilePath = localPath + File.separator + zipFileName;
						//当文件路径不存在时创建文件夹
						File filePath = new File(rootPath);
						if(!filePath.exists()){
							filePath.mkdirs();
						}
						//将该id的文件zip拷贝到指定的文件夹中; 异常抛出;
						DealWithFileUtil.getInstance().copyReportFile(zipFilePath, rootPath, null);
					}
					count ++;
				}
			}
			
			String zipNewName = millis + "_" + "批量下载" + "_" + count + ".zip";  //拼接下载文件名称;
			String zipNewPathAndName = localPath + zipNewName; //拼接文件完整路径;
			
			zipUtil.fileToZip(zipNewPathAndName, rootPath.toString());
			
			downloadurl = contextUrl + zipNewName;
			//删除拷贝的文件
			FileUtil.delDirectory(localPath + File.separator + millis + File.separator);
			log.info("批量下载路径: print task download url --- "+downloadurl);
		}
		return downloadurl;
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
	 * @param codes 条形码
	 * @param page 
	 * @param expressBySlh 是否是十里河寄出0不是，1是
	 * @return 通过条形码获取打印任务信息 
	 */
	public List<ErpPrintTaskBean> getPrintTaskInfoByCode(String codes,Page page,String expressBySlh){
		return dao.getPrintTaskInfoByCode(codes,page,expressBySlh);
	}

	/**
	 * @param printTaskId
	 * @return 通过id查询打印任务信息
	 */
	public List<ErpPrintTask> getPrintTaskById(String printTaskId) {
		return dao.getPrintTaskById(printTaskId);
	}

	/**
	 * 根据Id修改下载状态为已下载;
	 * create by henry.xu 20161024
	 * @param id
	 */
	public void modifiedDownloadStatusById(String id) throws Exception{
		this.dao.modifiedDownloadStatusById(id);
		//下载后会员状态为打印状态
		//根据打印任务ID修改其下会员的报告状态(statusYm=400，已下载) add by YoumingDeng 2016-12-15 start
		this.updateCustomerStatusYm(id, PropsUtils.getInt("status","statusYm.yxz"));
		//根据打印任务ID修改其下会员的报告状态 add by YoumingDeng 2016-12-15 end
	}

	/**
	 * @description 根据打印任务ID修改其下的会员报告状态
	 * @param printTaskId 打印任务ID
	 * @param statusYm 会员表的报告状态
	 * @author YoumingDeng
	 * @since: 2016/12/15 15:56
	 */
	public void updateCustomerStatusYm(String printTaskId, Integer statusYm) {
		customerService.updateStatusYm("ERP_PRINT_TASK", printTaskId, statusYm);
	}

	/**
	 * @description 定时任务：找出打印中状态的打印任务，根据系统时间判定是否要给业务人员发送邮件
	 * @author YoumingDeng
	 * @since: 2016/12/16 11:52
	 */
	public String tigger4SendMail(Integer timeLimit) throws IOException {
		String msg = null;
	    boolean flag;
		//1. 查找打印中状态的打印任务, erp_print_task print_state = 3,  customer状态： 300
		if(null==timeLimit||0==timeLimit){
			timeLimit = 72; //默认打印所需3天
		}
		List<ErpPrintTask> list = dao.listOvertimeTask(timeLimit);
		if(!CollectionUtils.isEmpty(list)) {
			//标准处理天数
//			Integer stdDays = timeLimit%24==0?timeLimit/24:timeLimit/24+1;
			//2. 计算超时天数
//			int num = 0;
			//当前的基准时间
			Date nowDate = Calendar.getInstance().getTime();
			//距离天数
//			Integer distanceDays;
//			//邮件内容
//			StringBuilder mailContent = new StringBuilder();
//			//TODO 预留，可能会要求以Excel附件的形式提供
//			for (ErpPrintTask task : list) {
//				num += 1;
//				mailContent.append("No." + num + " ");
//				mailContent.append(task.taskToString());
//
//				//创建打印任务时间至今的天数
//				distanceDays = this.calOverNum(nowDate, task.getCreateTime(), Calendar.DAY_OF_YEAR);
//				mailContent.append(", 已用天数(天)："+distanceDays);
//				mailContent.append(", 标准打印天数(天)："+ stdDays);
//				mailContent.append(", 超时天数(天)："+(distanceDays-stdDays));
//				mailContent.append(" <br/>");
//			}

			LinkedList<String> colNames = new LinkedList<String>();
			colNames.add("序号");
			colNames.add("任务号");
			colNames.add("任务名次");
			colNames.add("创建时间");
			colNames.add("是否十里河寄出");
			colNames.add("批次数量");
			colNames.add("打印公司");
			colNames.add("报告数量(份)");
			colNames.add("合计人数(人)");
			colNames.add("已用天数(天)");
			colNames.add("标准打印天数(天)");
			colNames.add("超时天数(天)");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String nowStr = sdf.format(nowDate);
			//生成Excel附件
            String fileName = subject+"_"+nowStr;
            //邮件保存位置
            String savePath = "/home/ymdata/printTaskOverTime";
            File excel = this.listToXlsx(fileName, savePath, colNames, list, timeLimit);
            List<File> fileList = new ArrayList<File>();
            fileList.add(excel);
			//3. 给业务人员发送邮件
			String mailSubject = subject + "("+nowStr+")";
			flag = this.sendMail(mailSubject, mailContent, fileList);
			if (flag){
				msg = fileName;
			}
		}
		return msg;
	}

	/**
	 * 制作Excel文件
	 * @param fileName 文件名
	 * @param savePath 保存目录
	 * @param colNames 列名称
	 * @param list List<ErpPrintTask>
	 * @param timeLimit 时限
	 * @return File
	 * @throws Exception
	 * @author Damian
	 * @since 2017-03-14
	 */
	private File listToXlsx(String fileName, String savePath, LinkedList<String> colNames, List<ErpPrintTask> list, Integer timeLimit) throws IOException {
		File xlsxFile;

		//标准处理天数
		Integer stdDays = timeLimit%24==0?timeLimit/24:timeLimit/24+1;
		//当前的基准时间
		Date nowDate = Calendar.getInstance().getTime();
		//距离天数
		Integer distanceDays;

		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		Sheet sh = wb.createSheet();
		sh.autoSizeColumn(1,true);

		Font headFont = wb.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setFontName("黑体");
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setFont(headFont);

		CellStyle normalStyle = wb.createCellStyle();
		normalStyle.setAlignment(CellStyle.ALIGN_CENTER);

		//行
        ErpPrintTask obj;
		for(int rownum = 0; rownum < list.size()+1; rownum++){
			Row row = sh.createRow(rownum);
			//设置表头
			if (rownum==0){
				for (int headNum=0; headNum< colNames.size(); headNum++){
					Cell headCell = row.createCell(headNum);
					headCell.setCellStyle(headStyle);
					headCell.setCellValue(colNames.get(headNum));
				}
			}
			//其他列
			else {
				obj = list.get(rownum-1);

				//序号
				Cell rowCell = row.createCell(0);
				rowCell.setCellType(Cell.CELL_TYPE_STRING);
				rowCell.setCellStyle(normalStyle);
				rowCell.setCellValue(rownum);

				//任务号
				Cell printTaskNo = row.createCell(1);
				printTaskNo.setCellType(Cell.CELL_TYPE_STRING);
				printTaskNo.setCellStyle(normalStyle);
				printTaskNo.setCellValue(obj.getPrintTaskNo());

				//任务名称
				Cell taskName = row.createCell(2);
				taskName.setCellType(Cell.CELL_TYPE_STRING);
				taskName.setCellStyle(normalStyle);
				taskName.setCellValue(obj.getTaskName());

				//创建时间
				Cell createTime = row.createCell(3);
				createTime.setCellType(Cell.CELL_TYPE_STRING);
				createTime.setCellStyle(normalStyle);
				createTime.setCellValue(obj.getCreateTime().toString());

				//是否十里河寄出
				String flagStr = "0".equals(obj.getExpressBySlh())?"否":"是";
				Cell isSlh = row.createCell(4);
				isSlh.setCellType(Cell.CELL_TYPE_STRING);
				isSlh.setCellStyle(normalStyle);
				isSlh.setCellValue(flagStr);

				//批次数量
				Cell batchNum = row.createCell(5);
				batchNum.setCellType(Cell.CELL_TYPE_STRING);
				batchNum.setCellStyle(normalStyle);
				batchNum.setCellValue(obj.getBatchNum()==null?"0":(""+obj.getBatchNum()));

				//打印公司
				Cell printCompany = row.createCell(6);
				printCompany.setCellType(Cell.CELL_TYPE_STRING);
				printCompany.setCellStyle(normalStyle);
				printCompany.setCellValue(obj.getPrintCompany());

				//报告数量
				Cell reportNum = row.createCell(7);
				reportNum.setCellType(Cell.CELL_TYPE_STRING);
				reportNum.setCellStyle(normalStyle);
				reportNum.setCellValue(obj.getReportCount()==null?"0": (""+obj.getReportCount()));

				//合计人数
				Cell count = row.createCell(8);
				count.setCellType(Cell.CELL_TYPE_STRING);
				count.setCellStyle(normalStyle);
				count.setCellValue(obj.getCount());

				//计算创建打印任务时间至今的天数
				distanceDays = this.calOverNum(nowDate, obj.getCreateTime(), Calendar.DAY_OF_YEAR);
				//已用天数
				Cell useDays = row.createCell(9);
				useDays.setCellType(Cell.CELL_TYPE_STRING);
				useDays.setCellStyle(normalStyle);
				useDays.setCellValue(distanceDays);

				//标准打印天数
				Cell stdDaysCell = row.createCell(10);
				stdDaysCell.setCellType(Cell.CELL_TYPE_STRING);
				stdDaysCell.setCellStyle(normalStyle);
				stdDaysCell.setCellValue(stdDays);

				//超时天数
				Cell divdDays = row.createCell(11);
				divdDays.setCellType(Cell.CELL_TYPE_STRING);
				divdDays.setCellStyle(normalStyle);
				divdDays.setCellValue(distanceDays-stdDays);

			}
		}

		File saveDir = new File(savePath);
		if (!saveDir.exists()||!saveDir.isDirectory()){
			saveDir.mkdirs();
		}

		//Excel2007
		String xlsxName = savePath + File.separator + fileName;
		if (!StringUtils.containsIgnoreCase(xlsxName, ".xlsx")){
			xlsxName = xlsxName + ".xlsx";
		}

		xlsxFile = new File(xlsxName);
		if (!xlsxFile.getParentFile().exists()) {
			xlsxFile.getParentFile().mkdirs();
		}
		if (!xlsxFile.exists()) {
			xlsxFile.createNewFile();
		}

		//数据写入文件
		FileOutputStream out = new FileOutputStream(xlsxFile);
		wb.write(out);
		out.close();
		wb.dispose();

		return xlsxFile;
	}
	/**
	 * @description 给指定人员发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @author YoumingDeng
	 * @since 2016/12/16 18:29
	 */
	private boolean sendMail(String subject, String content, List<File> list){
		boolean flag ;
		MailEntity mail = new MailEntity();
		mail.setHost(mailHost);
		mail.setSender(sendAccount);
		mail.setUsername(sendAccount);
		mail.setPassword(sendPassword);
		List<String> receivers = new ArrayList<String>();
		if (receiverAccount.contains(",")){
			String[] recArr = receiverAccount.split(",");
			Collections.addAll(receivers, recArr);
		} else {
			receivers.add(receiverAccount);
		}
		mail.setReceiver(receivers);
		mail.setName(nickName);
		mail.setSubject(subject);
		mail.setMessage(content);
		//添加附件
		mail.setAttachMents(list);

		flag = MailUtil.send(mail);
		return flag;
	}

	/**
	 * @description 计算两个日期间相差的时间
	 * @param destDate 目标时间
	 * @param stdDate 基准时间
	 * @param unit 计量单位，与Calendar中的一致，如计算小时差 unit = Calendar.HOUR
	 * @author YoumingDeng
	 * @since: 2016/12/16 17:28
	 */
	private Integer calOverNum(Date destDate,Date stdDate, Integer unit){
		Integer num = 0;
		if(stdDate!=null) {
			if(destDate==null){
				destDate = Calendar.getInstance().getTime();
			}
			if(0==unit||null==unit){
				unit = Calendar.SECOND;
			}
			long org = stdDate.getTime();
			long dest = destDate.getTime();
			Integer divisor = 0;
			if(Calendar.SECOND==unit){
				divisor = 1000;
			}else if (Calendar.HOUR == unit) {
				divisor = 60 * 60 * 1000;
			} else if (Calendar.DAY_OF_YEAR == unit) {
				divisor = 24 * 60 * 60 * 1000;
			}
			num = (int) ((float)(dest - org) / divisor);
		}
		return num;
	}

}
