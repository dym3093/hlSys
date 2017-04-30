package org.hpin.physical.job;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.physical.service.PhyReportService;
import org.hpin.reportdetail.entity.ErpBaseEmpInfo;
import org.hpin.reportdetail.entity.vo.ErpReportMailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 1+X风险数据提交跟踪 定时任务
 * @author tangxing
 * @date 2017-2-22下午4:41:26
 */
public class PhyReportPdfTrack {

	private Logger log = Logger.getLogger(PhyReportPdfTrack.class);
	
	@Autowired
	private PhyReportService reportService;
	
    @Value(value="${phyReportPdfTrack.file.download.context}")
	private String downloadContext;	//文件夹名字
	
    @Value(value="${phyReportPdfTrack.file.local.path}")
	private String localPath;		//文件存放路径

	
	public synchronized  void execute() {
		log.info("==== PhyReportPdfTrack start ====");
		TreeSet<String> treeSet = new TreeSet<String>();				//用于 支公司id，套餐名字 排重
		List<HashMap<String, String>> branchCompanyLists = null;	//（支公司id，套餐名字）集合
		HashMap<String, String> branchIdComboIdmap = null;				//存放（支公司id，套餐名字）
		
		List<ErpRelationShipCombo> relationShipCombos = reportService.getErpRelationShipComboByCreatePdf();
		
		
		if(relationShipCombos!=null){
			branchCompanyLists = new ArrayList<HashMap<String,String>>();
			
			for (ErpRelationShipCombo erpRelationShipCombo : relationShipCombos) {
				branchIdComboIdmap = new HashMap<String, String>();
				
				String shipProId = erpRelationShipCombo.getCustomerRelationShipProId();		//项目ID
				String comboId = erpRelationShipCombo.getComboId();							//套餐ID
				branchIdComboIdmap = reportService.getBranchIdComboName(shipProId,comboId);
				log.info("PhyReportPdfTrack 每次的支公司ID,套餐名 Map -- "+branchIdComboIdmap.toString());
				if(branchIdComboIdmap!=null){
					String branchIdCombo = branchIdComboIdmap.get("branchCompanyId")+branchIdComboIdmap.get("comboName");
					if(!treeSet.contains(branchIdCombo)){
						branchCompanyLists.add(branchIdComboIdmap);
					}
				}
			}
			List<String> customerCodes = new ArrayList<String>();
			//遍历能生成1+X报告对应 支公司ID，套餐名 集合
			for (HashMap<String, String> branchCompanyMap : branchCompanyLists) {
				String branchCompanyId = branchCompanyMap.get("branchCompanyId");
				String comboName = branchCompanyMap.get("comboName");
				
				List<String> codes = reportService.getCustomerByBranchIdAndComboName(branchCompanyId,comboName);
				customerCodes.addAll(codes);
			}
			
			//List<String> phyReportCodes =  reportService.getPhyReportByReportPath();
			//log.info("操作集合前数量-- customerCode size="+customerCodes.size()+",phyReportCode size="+phyReportCodes.size());
			
			//customerCodes.removeAll(phyReportCodes);		//erp_customer code 减去 phy_report code
			//log.info("操作集合后数量-- customerCode size="+customerCodes.size());
			
			try {
				List<Map<String, Object>> customerInfos = new ArrayList<Map<String,Object>>();
				//当前时间的毫秒值
				Date nowDate = new Date();
				long nowTime= nowDate.getTime();
				
				for (String customerCode : customerCodes) {
					boolean flag = reportService.getReportByCodeJDBC(customerCode);				//判断在phy_report表是否存在
					if(flag==false){	//不存在继续判断到现在时间 是否已经超时48小时
						String createTime =reportService.getPdfContentMaxTime(customerCode);	//拿到code的报告获取时间
						
						if(StringUtils.isNotEmpty(createTime)){
							long hour = this.getHour(nowTime,createTime);
							if(hour>48){	//大于两天
								Map<String, Object> results = reportService.getExcelCustomerInfo(customerCode);
								if(results!=null){
									customerInfos.add(results);
								}
							}
						}
					}
				}
				
				Map<String, String> excelFileMap = reportService.createExSeFilePath(customerInfos, localPath, downloadContext);
				reportService.sendEmail(excelFileMap);
			} catch (Exception e) {
				log.error("PhyReportPdfTrack 获取毫秒值计算时间差、生成excel、发送邮件 失败--"+e);
				e.printStackTrace();
			}
			
		}else{
			log.info("===没有可生成1+X的套餐===");
		}
		
		
		log.info("==== PhyReportPdfTrack end! ====");
	}
	

	
	/**
	 * 获取到当前时间的小时差
	 * @return
	 * @author tangxing
	 * @throws ParseException 
	 * @date 2017-2-23下午12:00:24
	 */
	private long getHour(long nowTime,String time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long createMilles = sdf.parse(time).getTime();
		long timeDifference = nowTime-createMilles;
		
		long hour = (timeDifference/1000/60/60);
		
		return hour;
	}

	public String getDownloadContext() {
		return downloadContext;
	}

	public void setDownloadContext(String downloadContext) {
		this.downloadContext = downloadContext;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	
}
