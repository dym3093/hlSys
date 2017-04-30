package org.hpin.reportdetail.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hpin.common.core.orm.BaseService;
import org.hpin.reportdetail.dao.ErpReadPDFMatchInfoDao;
import org.hpin.reportdetail.entity.ErpReadPDFMatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpReadPDFMatchInfoService")
@Transactional()
public class ErpReadPDFMatchInfoService extends BaseService {

	private static Logger log = Logger.getLogger(ErpReadPDFMatchInfoService.class);
	
	@Autowired
	private ErpReadPDFMatchInfoDao dao;
	
	private static ErpReportItemsCheck checker = null;
	
	public void readPdfPageText(ErpReadPDFMatchInfo pdfMatchInfo){
		Date date = new Date();
		long startTime = date.getTime();
		PDDocument document = null; 
		
		pdfMatchInfo.setIsReadPdf("1");
		try {
			File loadFile = new File(pdfMatchInfo.getPdfFilePath());
			document = PDDocument.load(loadFile);
			int pageNum = document.getNumberOfPages();
			pdfMatchInfo.setPdfPages(pageNum);
			Boolean isWrong = false;
			int beforeHashCode = pdfMatchInfo.hashCode();
			PDFTextStripper stripper = new PDFTextStripper();
			for(int i=1;i<=pageNum;i++){
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				String pdfStr = stripper.getText(document);
				ErpReportStandardCheckConcrete checkConcrete = new ErpReportStandardCheckConcrete();
				
				checker = new ErpReportSexItemCheck(pdfMatchInfo,pdfStr.replaceAll("(\r\n|\r|\n|\n\r|\t|\\s)",""));
				checker.setChecker(checkConcrete);
				checker.check();
				int afterHashCode = pdfMatchInfo.hashCode();
				if(beforeHashCode!=afterHashCode){
					pdfMatchInfo.setErrorPages(String.valueOf(i));
					isWrong = true;
					break;
				}
			}
			if(isWrong){
				dao.renameFilePdfContent(pdfMatchInfo,1);
				dao.renameFilePdfCustomer(pdfMatchInfo,1);
			}else{
				dao.renameFilePdfContent(pdfMatchInfo,2);
				dao.renameFilePdfCustomer(pdfMatchInfo,2);
			}
			dao.update(pdfMatchInfo);
			Date date2 = new Date();
			long endTime = date2.getTime();
			log.info(pdfMatchInfo.getCode()+",读取PDF并匹配用时--"+(endTime-startTime));
		} catch (FileNotFoundException e) {  
			log.error("未找到文件--"+e);  
		} catch (IOException e) {  
			log.error("读取错误--"+e);  
		} finally {  
			if (document != null) {  
				try {  
					document.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  
	
	}
	
	/**
	 * 保存ErpReadPDFMatchInfo，并返回ErpReadPDFMatchInfo集合
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-3-8下午5:18:25
	 */
	public List<ErpReadPDFMatchInfo> saveErpReadPDFMatchInfo() throws Exception{
		/*ErpReadPDFMatchInfo pdfMatchInfo = null;
		List<Map<String, Object>> results = dao.getCustomerAndFilePath();
		if(results!=null){
			for (Map<String, Object> customerMap : results) {
				pdfMatchInfo = new ErpReadPDFMatchInfo();
				pdfMatchInfo.setCode((String)customerMap.get("code"));
				pdfMatchInfo.setName((String)customerMap.get("name"));
				pdfMatchInfo.setAge((String)customerMap.get("age"));
				pdfMatchInfo.setSex((String)customerMap.get("sex"));
				pdfMatchInfo.setPdfFilePath((String)customerMap.get("filePath"));
				pdfMatchInfo.setCreateTime(new Date());
				pdfMatchInfo.setIsReadPdf("0");
				dao.save(pdfMatchInfo);
			}
		}*/
		return dao.listErpReadPDFMatchInfo();
	}
}
