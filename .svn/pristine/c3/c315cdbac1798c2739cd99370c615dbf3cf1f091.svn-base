package org.hpin.physical.job;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.service.CustomerRelationshipComboService;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.physical.entity.PhyReport;
import org.hpin.physical.service.PhyReportService;
import org.hpin.physical.thread.PhysicalReportThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

/**
 * 比对PhyReport数据是否能生成报告，再生成报告
 * @author tangxing
 * @date 2016-12-6下午12:08:27
 */
public class CreatePhyReportPdfJob {

	private Logger log = Logger.getLogger(CreatePhyReportPdfJob.class);
	
	@Autowired
	private PhyReportService reportService;
	
	@Autowired
	private ErpCustomerService customerService;
	
	@Autowired
	private ComboService comboService;
	
	@Autowired
	private CustomerRelationshipComboService relationshipComboService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public synchronized  void execute() {
		TreeSet<String> codeSets  = null;
		List<String> phyReportCodeList = new ArrayList<String>();	//存放该批excel中能生成报告的PhyReport
		//获取所有createPdfStatus状态为空的PhyReport
		List<PhyReport> phyReports =  reportService.getPhyReportByCreatePdfStatusNull();
		if(phyReports!=null){
			try {
				log.info("phyReport list size -- "+phyReports.size());
				/* **** 用于判断是否需要生成报告 **** */
				for (PhyReport phyReport : phyReports) {
					String branchCompanyId = "";
					String comboName = "";		//套餐名
					String comboId = "";		//套餐ID
					String isCreatePdf = "";	//是否生成报告状态
					String code = phyReport.getGeneCode();
					List<ErpCustomer> customers = customerService.getCustomerByCode(code);
					if(customers!=null&&customers.size()>0){
						ErpCustomer customer = customers.get(0);
						branchCompanyId = customer.getBranchCompanyId();
						comboName = customer.getSetmealName();
						Combo combo = comboService.findByComboName(comboName);
						if(combo!=null){
							comboId = combo.getId();
						}
						log.info("branchCompanyId and comboId -- "+branchCompanyId+","+comboId);
						isCreatePdf = relationshipComboService.getIsCreatePdfStatus(branchCompanyId,comboId);
					}
					if(StringUtils.isEmpty(isCreatePdf)){
						phyReport.setCreatePdfStatus("0");
					}else{
						if(isCreatePdf.equals("1")){			//生成报告
							phyReport.setCreatePdfStatus("1");
							phyReportCodeList.add(code);	//可以生成的把code保存起来
						}else if(isCreatePdf.equals("0")){		//不生成报告
							phyReport.setCreatePdfStatus("0");
						}
					}
					
					reportService.update(phyReport);		//更新PhyReport
				}
				/* **** 拿到能 【生成报告】 的PhyReport集合 **** */
				
				if(phyReportCodeList!=null&&phyReportCodeList.size()>0){
					codeSets = new TreeSet<String>();
					codeSets.addAll(phyReportCodeList);
					log.info("createFileObj codeSets -- "+codeSets.toString());
					if(codeSets.size()>0){	
						//调用生成报告线程
						PhysicalReportThread thread = new PhysicalReportThread(codeSets);
						taskExecutor.submit(thread);
					}
				}
				
				/*
				//此方式需到数据库重新查询一遍数据
				List<PhyReport> phyReportList = reportService.getPhyReportByCreatePdfStatus();
				if(phyReportList!=null){
					codeSets = new TreeSet<String>();
					for (PhyReport phyReport : phyReportList) {
						String code = phyReport.getGeneCode();
						codeSets.add(code);
					}
					log.info("createFileObj codeSets -- "+codeSets.toString());
					if(codeSets.size()>0){	
						//调用生成报告线程
						PhysicalReportThread thread = new PhysicalReportThread(codeSets);
						taskExecutor.submit(thread);
					}
				}*/
				
				
				
			} catch (Exception e) {
				log.error("CreatePhyReportPdfJob execute exception -- "+e);
			}
		}
		
	}
	
}
