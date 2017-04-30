package org.hpin.reportdetail.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.entity.PrintBatchInfoBean;
import org.hpin.reportdetail.service.ErpPrintBatchService;
import org.hpin.reportdetail.service.ErpReportdetailPDFContentService;
import org.hpin.reportdetail.util.BuidReportExcel;
import org.hpin.reportdetail.util.DealWithFileUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ErpReportWillPrintJob {

	private Logger log = Logger.getLogger(ErpReportWillPrintJob.class);
	
	@Autowired
	private ErpReportdetailPDFContentService pdfService;
	
	@Autowired
	private ErpPrintBatchService batchService;
	
	private String localPath;
	
	private BuidReportExcel buildExcel = new BuidReportExcel();
	
	@Autowired
	CustomerRelationShipService relService;
	
	public void execute(){
		long start = System.currentTimeMillis();
		
		log.info("ErpReportWillPrintJob execute start");
		
		List<ErpReportdetailPDFContent> willPrintPdfs = pdfService.getWillPrintPdf();
		
		List<ErpPrintTaskContent> printPdfs = pdfService.getPrintPDF();
		//部门不为空集合
		List<PrintBatchInfoBean> batchPrint = pdfService.getBatchPrint();
		//部门为空集合
		List<PrintBatchInfoBean> batchPrint2 = pdfService.getBatchPrint2();
		//包含部门为空和不为空的集合
		List<PrintBatchInfoBean> batchPrintAll = new ArrayList<PrintBatchInfoBean>();
		
		batchPrintAll.addAll(batchPrint);
		batchPrintAll.addAll(batchPrint2);
		List<ErpPrintBatch> batchBeanList = new ArrayList<ErpPrintBatch>();
		log.info("ErpReportWillPrintJob execute pdfContent size : "+willPrintPdfs.size());
		log.info("ErpReportWillPrintJob execute ErpPrintTaskContent size : "+printPdfs.size());
		log.info("ErpReportWillPrintJob execute batchPrintAll size : "+batchPrintAll.size());
		for(PrintBatchInfoBean batchInfo : batchPrintAll){
			List<ErpPrintTaskContent> taskContentList = new ArrayList<ErpPrintTaskContent>();
			try{
				int num = 0;
				int excelRows = 0;
				String printbthNo = createBatchNo();
				ErpPrintBatch batchBean = new ErpPrintBatch();
				//回写ErpReportdetailPDFContent表中批次管理的批次号
				for(ErpReportdetailPDFContent content : willPrintPdfs){
					if(batchPrint.contains(batchInfo)){
						//部门不为空，则判断部门是否相等
						if(batchInfo.getBranch_company().equals(content.getBranch_company())
								&&batchInfo.getDept().equals(content.getDept())
								&&batchInfo.getSetmeal_name().equals(content.getSetmeal_name())){
							content.setPrintbthno(printbthNo);//批次管理的批次号
							content.setPs("2");//标记为已走打印流程
						}
					}
					if(batchPrint2.contains(batchInfo)){
						//部门为空，则判断项目编码是否相等
						if(batchInfo.getBranch_company().equals(content.getBranch_company())
								&& (batchInfo.getProjectCode()==null?"":batchInfo.getProjectCode()).equals(content.getProjectCode()==null?"":content.getProjectCode())
								&& batchInfo.getSetmeal_name().equals(content.getSetmeal_name())
								&& StringUtils.isEmpty(content.getDept())){
							content.setPrintbthno(printbthNo);//批次管理的批次号
							content.setPs("2");//标记为已走打印流程
						}
					}
				}
				//将ErpPrintTaskContent表中的批次管理批次号赋值
				for(ErpPrintTaskContent content : printPdfs){
					//部门不是空的集合
					if(batchPrint.contains(batchInfo)){
						//判断部门是否相等
						if(batchInfo.getBranch_company().equals(content.getBranchCompanyId())
								&&batchInfo.getDept().equals(content.getDept())
								&&batchInfo.getSetmeal_name().equals(content.getCombo())
								&&batchInfo.getReportType().equals(content.getReportType())){
							num +=1;
							if(!content.getType().equals("3")){
								excelRows++;
							}
							content.setPrintBatchNo(printbthNo);//批次管理的批次号
							content.setPs("1");
							taskContentList.add(content);
							if(null != content.getSaleman()){
								batchBean.setYmContacts(content.getSaleman());
							}
							if(null==batchBean.getProvince()&&null!=content.getProvince()){
								batchBean.setProvince(content.getProvince());
								batchBean.setCity(content.getCity());
							}
							batchBean.setReportType(content.getReportType());
						}
					}
					//部门为空的集合
					if(batchPrint2.contains(batchInfo)){
						//判断项目编码是否相等且content表中的部门为空
						if(null==content.getDept()
								&&(batchInfo.getProjectCode()==null?"":batchInfo.getProjectCode()).equals(content.getProjectCode()==null?"":content.getProjectCode())
								&&batchInfo.getBranch_company().equals(content.getBranchCompanyId())
								&&batchInfo.getSetmeal_name().equals(content.getCombo())
								&&batchInfo.getReportType().equals(content.getReportType())){
							num +=1;
							if(!content.getType().equals("3")){
								excelRows++;
							}
							content.setPrintBatchNo(printbthNo);//批次管理的批次号
							content.setPs("1");
							taskContentList.add(content);
							if(null != content.getSaleman()){
								batchBean.setYmContacts(content.getSaleman());
							}
							if(null==batchBean.getProvince()&&null!=content.getProvince()){
								batchBean.setProvince(content.getProvince());
								batchBean.setCity(content.getCity());
							}
							batchBean.setReportType(content.getReportType());
						}
					}
				}
				
				batchBean.setPrintBatchNo(printbthNo);
				batchBean.setCount(num);
				batchBean.setBranchCompany(batchInfo.getBranch_company());			//实际是支公司id
				batchBean.setBranchCompanyId(batchInfo.getBranch_company_name());	//实际是支公司name
				batchBean.setCreateTime(new Date());
				batchBean.setCombo(batchInfo.getSetmeal_name());
				batchBean.setOwnedCompany(batchInfo.getOwnedcompany());				//所属公司名称
				batchBean.setOwnedCompanyId(batchInfo.getOwnedcompanyid());			//所属公司ID
				batchBean.setIsDelete("0");
				batchBean.setIsPrintTask("0");
				batchBean.setDept(batchInfo.getDept()==null||batchInfo.getDept()==""?"无":batchInfo.getDept());//当等于空时，设置部门为无，方便在搜索，需要在页面做处理
				batchBean.setProjectCode(batchInfo.getProjectCode());
				//备份PDF到打印批次相关目录
				String printFilePathName = createFilePathName(batchInfo)+"_"+excelRows;
//				String batchFilePath = createBatchFilePath(taskContentList,printFilePathName);
				String batchFilePath = createBatchFilePathNext(taskContentList,printFilePathName);
				batchBean.setBatchFilePath(batchFilePath);
				batchBeanList.add(batchBean);
				log.info("ybc info batchBeanList size :"+batchBeanList.size()+";branch_company:"+batchInfo.getBranch_company());
			}catch(Exception e){
				log.error("ybc error", e);
				continue;
			}
		}
		try {
			pdfService.updateContentPrintInfo(willPrintPdfs);
			pdfService.updateTaskContentInfo(printPdfs);
			for(ErpPrintBatch batch:batchBeanList){
				batchService.save(batch);
				log.info("保存的批次信息："+batch);
			}
		} catch (Exception e) {
			log.error("保存批次信息时出错",e);
		}
		log.info("ErpReportWillPrintJob execute end , USE Time : "+(System.currentTimeMillis()-start));
	}
	
	//生成目录
	private String createFilePathName(PrintBatchInfoBean batchInfo) {
		String companyName = batchInfo.getBranch_company();
		List<CustomerRelationShip> relationShip = relService.findByCustomer(companyName);
		if(relationShip.size()!=0){
			for(CustomerRelationShip ship : relationShip){
				if(StringUtils.isNotEmpty(ship.getBranchCommany())){
					companyName = ship.getBranchCommany();
					break;
				}
			}
		}
		return companyName+"_"+batchInfo.getSetmeal_name();
	}


	private String createBatchNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date())+createRandomNo();
	}
	
	//生成三位数的随机数
	private String createRandomNo(){
		Random r = new Random();
		int n = r.nextInt(1000);
		int k = 3 - String.valueOf(n).length();
		String v = "";
		for (; k > 0; k--) {
			v = v + "0";
		}
		return v + String.valueOf(n);
	}
	
	//生成PDF拷贝目录
	private String createBatchFilePath(List<ErpPrintTaskContent> pdfContentList,String key) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timePath = sdf.format(new Date());
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
		String batchFilePath = rootPath.toString()+timePath+"_"+key+File.separator;
		String curFilePath = rootPath.toString()+timePath+"_"+key+File.separator+timePath+"_"+key+File.separator;
		File filePath = new File(curFilePath);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		List<String> fileNameList = new ArrayList<String>();
		for(ErpPrintTaskContent pdfContent : pdfContentList){
			File currFile = new File(pdfContent.getFilePath());
			String fileName = currFile.getName();
			if(fileNameList.contains(fileName)){
				Random r=new Random();
				String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(new Date())+r.nextInt(1000)+ "." +suffix;
			}else{
				fileNameList.add(fileName);
			}
			log.info("curFilePath:"+curFilePath);
			DealWithFileUtil.getInstance().copyReportFile(pdfContent.getFilePath(),curFilePath,fileName);
		}
		//去掉重复数据
		List<ErpPrintTaskContent> toBuildExlContents = buildExlContents(pdfContentList);
		//构建Excel文件
		List<List<String>> rowList = DealWithFileUtil.getInstance().buildExcelRowByPdf(toBuildExlContents);
		String excleName = key+".xls";
		buildExcel.createPrintBthXls(curFilePath.toString(),excleName,rowList);
		return batchFilePath;
	}
	
	/**
	 * 生成打印目录，根据createBatchFilePath方法做的修改
	 * @param pdfContentList
	 * @param key
	 * @return String
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-10-20 上午11:56:20
	 */
	private String createBatchFilePathNext(List<ErpPrintTaskContent> pdfContentList,String key) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timePath = sdf.format(new Date());
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
		String batchFilePath = rootPath.toString()+timePath+"_"+key+File.separator;
		String curFilePath = rootPath.toString()+timePath+"_"+key+File.separator+timePath+"_"+key+File.separator;
		File filePath = new File(curFilePath);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		List<String> fileNameList = new ArrayList<String>();
		//复制文件的最终保存目录
		String copyFilePath = null;
		String excelPath = null;
		for(ErpPrintTaskContent pdfContent : pdfContentList){
			File currFile = new File(pdfContent.getFilePath());
			//是否图片
			
			if(isImage(currFile)){
				copyFilePath = curFilePath + "img"+File.separator ;
			}else{
				copyFilePath = curFilePath + "pdf"+File.separator ;
				excelPath = copyFilePath;
				File file = new File(excelPath);
				if(!file.exists()){
					file.mkdirs();
				}
			}
			
			String fileName = currFile.getName();
			if(fileNameList.contains(fileName)){
				Random r=new Random();
				String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(new Date())+r.nextInt(1000)+ "." +suffix;
			}else{
				fileNameList.add(fileName);
			}
			DealWithFileUtil.getInstance().copyReportFile(pdfContent.getFilePath(),copyFilePath,fileName);
		}
		
		if(excelPath!=null){
			//去掉重复数据
			List<ErpPrintTaskContent> toBuildExlContents = buildExlContents(pdfContentList);
			//构建Excel文件
			List<List<String>> rowList = DealWithFileUtil.getInstance().buildExcelRowByPdf(toBuildExlContents);
			String excleName = key+".xls";
			buildExcel.createPrintBthXls(excelPath,excleName,rowList);
		}
		return batchFilePath;
	}
	
	private boolean isImage(File file){  
        boolean flag = false;   
        try {  
//            ImageInputStream is = ImageIO.createImageInputStream(file);  
            if(null != file){  
            	String fileName = file.getName();
            	log.info("文件名："+fileName);
            	if (!fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equals("pdf")) {
            		flag = true;  
				}
            }  
        } catch (Exception e){  
            log.error("判断是否是image出错："+e);
        }  
        return flag;  
    }  
	
	//去掉重复数据
	private List<ErpPrintTaskContent> buildExlContents(List<ErpPrintTaskContent> pdfContentList) {
		List<ErpPrintTaskContent> toBuildExlContents = new ArrayList<ErpPrintTaskContent>();
		List<String> codesList = new ArrayList<String>();
		for(ErpPrintTaskContent content : pdfContentList){
			if(!content.getType().equals("3") && !codesList.contains(content.getCode())){//修复在去重时，excel少客户信息的问题
				codesList.add(content.getCode());
				toBuildExlContents.add(content);
			}
		}
		return toBuildExlContents;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	
}
