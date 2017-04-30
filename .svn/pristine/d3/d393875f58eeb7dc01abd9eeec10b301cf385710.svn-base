package org.hpin.events.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.web.BaseAction;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.service.ErpCustomerUpdateService;
import org.hpin.settlementManagement.web.ErpComboPriceAction;
import org.springframework.beans.factory.annotation.Autowired;


@Namespace("/events")
@Action("erpCustomerUpdate")
@Results({
	@Result(name="importUpdateCustomer",location="/WEB-INF/events/erpCustomer/importUpdateCustomer.jsp"),
})
public class ErpCustomerUpdateAction extends BaseAction {

	private File affixExcel;
	
	private Logger log = Logger.getLogger(ErpCustomerUpdateAction.class);
	
	@Autowired
	ErpCustomerUpdateService service;
	
	public String updateCustomer(){
		
		return "importUpdateCustomer";
	}
	
	/**
	 * 读取Excel文件
	 * 
	 * @author tangxing
	 * @date 2016-7-22下午3:18:39
	 */
	public void uploadCustomerInfo(){
		JSONObject json = new JSONObject();
		File file = new File("Z:\\ftp\\transact");
		try {
			print(file);
			json.put("statusCode", 200);
			json.put("message", "修改成功");
		} catch (Exception e) {
			json.put("statusCode", 300);
			json.put("message", "修改失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取pdf文件
	 * 
	 * @author tangxing
	 * @date 2016-7-22下午3:18:39
	 */
	public void uploadCustomerPdf(){
		JSONObject json = new JSONObject();
		boolean flag = false;
		/*String[] strTime = new String[]
		{"2016-01-01,2016-01-31","2016-02-01,2016-02-29","2016-03-01,2016-03-31","2016-04-01,2016-04-30"
				,"2016-05-01,2016-05-31","2016-06-01,2016-06-30"};*/
		String[] strTime = new String[]
				{"2016-06-01,2016-06-30"};
		
		for (int i = 0; i < strTime.length; i++) {
			log.info("createTime --- "+strTime[i]);
			if(!StringUtils.isEmpty(strTime[i])){
				String[] str = strTime[i].split(",");
				try {
					Date sDate = service.formatString(str[0]);
					Date eDate = service.formatString(str[1]);
					service.readPdfCustomer(sDate, eDate);
					flag = true;
				} catch (Exception e) {
					flag = false;
					json.put("statusCode", 300);
					json.put("message", "修改失败");
					log.error("uploadCustomerPdf --- ",e);
				}
			}
		}
		
		if(flag){
			json.put("statusCode", 200);
			json.put("message", "修改成功");
		}
	}

	
	public void print(File file) throws Exception{// 递归调用
	/*	
	//加"\\"是为了与文件名字中的日期区分开
	String[] sArr = new String[]
	{"20160101\\","20160102\\","20160103\\","20160104\\","20160107\\","20160108\\","20160109\\","20160111\\","20160112\\","20160119\\","20160121\\","20160130\\","20160131\\"
	+"20160203\\","20160206\\","20160208\\","20160215\\","20160219\\","20160223\\","20160224\\","20160226\\","20160229\\"
	+"20160303\\","20160304\\","20160305\\","20160306\\","20160307\\","20160308\\","20160313\\","20160318\\","20160319\\","20160320\\","20160321\\","20160323\\","20160330\\","20160331\\","20160332\\"
	+"20160401\\","20160402\\","20160414\\","20160415\\","20160416\\","20160423\\","20160427\\","20160428\\"
	+"20160507\\","20160513\\","20160519\\","20160520\\","20160523\\","20160524\\","20160526\\","20160530\\","20160531\\"
	+"20160601\\","20160605\\","20160608\\","20160621\\","20160623\\"
	+"20160703\\","20160704\\","20160712\\","20160718\\"
	+"7.14.远盟报告1366个","7.05","@Recycle"};
	*/
	
	//加"\\"是为了与文件名字中的日期区分开
	String[] sArr = new String[]
	{"20160103\\","20160121\\","20160131\\"
	,"20160203\\","20160229\\"
	,"20160303\\","20160332\\"
	,"20160401\\","20160428\\"
	,"20160507\\","20160531\\"
	,"20160601\\","20160623\\"
	,"20160703\\","20160718\\","20160719\\"
	,"7.14.远盟报告1366个"};
	
		
		//String[] sArr = new String[]{"20160711ss\\","20160712ss\\"};
		
		if(file!=null){// 判断对象是否为空
			if(file.isDirectory()){// 如果是目录
			File f[] = file.listFiles() ;// 列出全部的文件
				if(f!=null){// 判断此目录能否列出
					for(int i=0;i<f.length;i++){
						print(f[i]) ;// 因为给的路径有可能是目录，所以，继续判断
						String fStr = f[i].toString();
						for(int j=0; j<sArr.length;++j){
							if(fStr.indexOf(".xlsx")!=-1||fStr.indexOf(".xls")!=-1){		//不包含pdf的文件夹，包含.xlsx和.xls 可以进去
								if(fStr.indexOf(sArr[j])!=-1){		//是包含数组里的元素
									if(fStr.indexOf(".xlsx")!=-1){
										log.info("saveCustomer"+f[i]);
										try {
											service.saveCustomerXlsx(f[i]);
										} catch (Exception e) {
											log.error("saveCustomer error ",e);
										}
									}else if(f[i].toString().indexOf(".xls")!=-1){
										log.info("saveCustomerTwo"+f[i]);
										try {
											service.saveCustomerXls(f[i]);
										} catch (Exception e) {
											log.error("saveCustomerTwo error ",e);
										}
									}
									break;
								}
							}
						}
					}
				}
			}else{
				//不是目录，直接是指向一个文件时
				System.out.println("---"+file) ;
				
				/*String s = file.toString();
				if(s.indexOf("pdf")==-1){			//不包含pdf的文件夹才进去
					if(s.indexOf(".xlsx")!=-1){
						log.info("saveCustomer"+file);
						try {
							service.saveCustomerXlsx(file);
						} catch (Exception e) {
							log.error("saveCustomer error ",e);
						}
					}else if(s.indexOf(".xls")!=-1){
						log.info("saveCustomerTwo"+file);
						try {
							service.saveCustomerXls(file);
						} catch (Exception e) {
							log.error("saveCustomerTwo error ",e);
						}
					}
				}*/
			}
		}
	}
	
	/**
	 * 读取指定的Excel文件
	 * 
	 * @author tangxing
	 * @date 2016-7-26下午2:11:44
	 */
	public void readExcelPath(){
		JSONObject json = new JSONObject();
		String path = "D:/Excel/检测日期二批.xlsx";
		File file = new File(path);
		
		try {
			service.saveCustomerXlsx(file);
			/*json.put("statusCode", 200);
			json.put("message", "修改成功");*/
		} catch (Exception e) {
			/*json.put("statusCode", 300);
			json.put("message", "修改失败");*/
			log.error("uploadCustomerPdf --- ",e);
			e.printStackTrace();
		}
	}
	
	public File getAffixExcel() {
		return affixExcel;
	}

	public void setAffixExcel(File affixExcel) {
		this.affixExcel = affixExcel;
	}
}
