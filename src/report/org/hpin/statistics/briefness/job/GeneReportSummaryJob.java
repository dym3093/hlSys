package org.hpin.statistics.briefness.job;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.util.HttpTool;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpCustomerService;
import org.hpin.events.service.ErpEventsService;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.hpin.settlementManagement.entity.ErpCustomerSettleBX;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.service.ErpSettlementTaskBXService;
import org.hpin.settlementManagement.service.ErpSettlementTaskJYService;
import org.hpin.statistics.briefness.entity.GeneReportSummary;
import org.hpin.statistics.briefness.service.QueryService;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreProduceDetail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务部门基因汇总报表
 * @author tangxing
 * @date 2016-9-21下午4:33:18
 */

public class GeneReportSummaryJob {

	@Autowired
	QueryService queryService;
	
	ErpSettlementTaskBXService settlementTaskBXService;
	
	private Logger log = Logger.getLogger(GeneReportSummaryJob.class);
	
	public synchronized  void execute() {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, String>> listMap = new ArrayList<Map<String,String>>();					//保存项目相关
		List<Map<String, List<ErpEvents>>> mapList = new ArrayList<Map<String,List<ErpEvents>>>();
		List<CustomerRelationShip> customerRelationShips = null;
		List<ErpEvents> erpEvents = null;							//保存该月份所有的场次
		Set<String> setStr = new TreeSet<String>();
		//CustomerRelationShip customerRelationShip = null;
		GeneReportSummary geneReportSummary = null;
		//boolean flag = false;
		
		/*Calendar a=Calendar.getInstance();
		a.add(Calendar.DAY_OF_MONTH, -50);	//前一天
		Date startTime = a.getTime();	//开始时间
		Date endTime = new Date();		//结束时间
		
*/		
		Calendar a=Calendar.getInstance();
		String dateStr = "2016-01-01 00:00:00";
		String endStr = "2016-08-31 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			a.setTime(sdf.parse(dateStr));
			//System.out.println(sdf.parse(endStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		boolean flag = true;
		int i = 1;
		
		try {
			while(flag){
				Date startTime = a.getTime();
				a.add(Calendar.DAY_OF_MONTH, +i);
				Date endTime = a.getTime();
				if(startTime.equals(sdf.parse(endStr))){	//满足2016-08-31 00:00:00 退出
					break;
				}
				log.info("startTime--"+startTime+",endTime--"+endTime);
				
				try {
					erpEvents = new ArrayList<ErpEvents>();
					erpEvents = queryService.getErpEventsByDate(startTime,endTime);
				} catch (Exception e) {
					log.error("execute query getErpEventsByDate--"+e);
				}
				
				if(erpEvents!=null&&erpEvents.size()>0){
					customerRelationShips = new ArrayList<CustomerRelationShip>();
					for (ErpEvents event : erpEvents) {
						String cusRelShipId = event.getCustomerRelationShipProId();
						log.info("for event cusRelShipId--"+cusRelShipId+","+event.getEventsNo());
						String str = queryService.getCustomerRelationShipProById(cusRelShipId);//项目编码，名称，负责人的组装
						if(str!=null){
							if(!setStr.contains(str)){
								setStr.add(str);
							}
						}
					}
				}
				
				try {
					//把CustomerRelationShip List格式化为 map List
					//listMap = this.formatCustomerRelationShipList(customerRelationShips);
					
					//组装为：项目相关信息，ErpEvents List
					mapList = this.formatListAndSet(erpEvents,setStr);
				} catch (Exception e) {
					log.error("execute format info--"+e);
				}
				
				log.info("format project info and eventsList size--"+mapList.size());
				
				try {
					if(mapList!=null&&mapList.size()>0){
						for (Map<String, List<ErpEvents>> result : mapList) {
							geneReportSummary = new GeneReportSummary();
							for (Map.Entry<String, List<ErpEvents>> entry : result.entrySet()) {
								String str = entry.getKey();				//组合的项目相关信息
								List<ErpEvents> events = entry.getValue();	//对应的场次集合
								
								geneReportSummary = getAmountAndNum(events);//获取结算金额报告数量等信息
								
								log.info("settlement amount info--"+geneReportSummary.toString());
								
								if(str!=null&&!StringUtils.isEmpty(str)){
									log.info("execute item info str--"+str);
									String sArr[] = str.split(",");
									geneReportSummary.setMoveMatterAmount(this.getMaterielAmount(sArr[0],startTime,endTime));	//物料总金额(根据项目编码)
									geneReportSummary.setItemCode(sArr[0]);
									geneReportSummary.setItemName(sArr[1]);
									geneReportSummary.setItemHead(sArr[2]);
								}
								geneReportSummary.setCreateDate(new Date());
								//geneReportSummary.setCreateUserName(userName);
								//geneReportSummary.setMonth(startTime.length()>0?startTime.substring(0,7).replace("-", ""):"");
								geneReportSummary.setStartTime(startTime);
								log.info("save geneReportSummary info--"+geneReportSummary.toString());
								
								queryService.save(geneReportSummary);
							}
						}
					}
				} catch (Exception e) {
					log.error("execute count amount--"+e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 格式化CustomerRelationShip List为Map
	 * @param customerRelationShips
	 * @author tangxing
	 * @date 2016-9-1下午2:48:37
	 */
	private List<Map<String,String>> formatCustomerRelationShipList(List<CustomerRelationShip> customerRelationShips) throws Exception{
		Map<String, String> maps = null;
		List<Map<String,String>> listMap = null;
		Set<String> sets = new TreeSet<String>();
		if(customerRelationShips!=null&&customerRelationShips.size()>0){
			listMap = new ArrayList<Map<String,String>>();
			for (CustomerRelationShip customerRelationShipTemp : customerRelationShips) {
				String proCode = customerRelationShipTemp.getProCode();
				
				if(proCode==null&&StringUtils.isEmpty(proCode)){	//为null不执行
					
				}else{
					if(!sets.contains(proCode)){	//根据项目code排重
						sets.add(proCode);
						maps = new HashMap<String, String>();
						maps.put("proCode", customerRelationShipTemp.getProCode());
						maps.put("proBelong", customerRelationShipTemp.getProBelong());
						maps.put("proUser", customerRelationShipTemp.getProUser());
						maps.put("branchCompany", customerRelationShipTemp.getBranchCommany());
						listMap.add(maps);
					}
				}
			}
		}
		log.info("formatCustomerRelationShipList listMap--"+listMap.toString());
		return listMap;
	}
	
	/**
	 * 组装  项目对应场次List
	 * @param erpEvents
	 * @param listMap
	 * @return
	 * @author tangxing
	 * @date 2016-9-5上午11:11:10
	 */
	private List<Map<String, List<ErpEvents>>> formatListAndSet(List<ErpEvents> erpEvents,Set<String> setStr) throws Exception{
		//入库GeneReportSummary
		
		List<Map<String, List<ErpEvents>>> resultMapList = new ArrayList<Map<String,List<ErpEvents>>>();
		Map<String, List<ErpEvents>> mapList = null;
		List<ErpEvents> erpEventsResult = null;
		if(setStr!=null){
			for (String string : setStr) {
				log.info("formatListAndSet item info set --"+string);
				mapList = new HashMap<String, List<ErpEvents>>();
				erpEventsResult = new ArrayList<ErpEvents>();
				String[] arrStr = string.split(",");
				String cusRelaShipCode = arrStr[0];		//项目code
				for (ErpEvents events : erpEvents) {
					String cusRelShipId = events.getCustomerRelationShipProId();
					String proCode = queryService.getCRShipProById(cusRelShipId); 	//查找项目Code
					if(cusRelShipId!=null&&proCode!=null){
						if(cusRelaShipCode.equals(proCode)){	//如果场次的cusRelaShipId跟cusRelaShip的Id相等
							erpEventsResult.add(events);
						}
					}
				}
				if(erpEventsResult!=null&&erpEventsResult.size()>0){
					mapList.put(string, erpEventsResult);
					
					resultMapList.add(mapList);
				}
			}
		}
		log.info("formatListAndSet resultMapList size --"+resultMapList.size());
		return resultMapList;
	}
	
	/**
	 * 获取结算金额数量等信息
	 * @param erpEvents
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午3:25:02
	 */
	private GeneReportSummary getAmountAndNum(List<ErpEvents> erpEvents) throws Exception{
		GeneReportSummary geneReportSummary = new GeneReportSummary();
		List<ErpCustomer> customers = new ArrayList<ErpCustomer>();
		BigDecimal bigDecimalJY = BigDecimal.ZERO;
		BigDecimal bigDecimalBX = BigDecimal.ZERO;
		int sumNum = 0;
		if(erpEvents!=null&&erpEvents.size()>0){
			for (ErpEvents event : erpEvents) {
				String eventsNo = event.getEventsNo();
				customers = queryService.getErpCustomerByEventsNo(eventsNo);//该场次所有的customer
				sumNum = sumNum+customers.size();				//该项目编码所有的客户数量
				log.info("getAmountAndNum customer size -- "+customers.size());
				bigDecimalJY = bigDecimalJY.add(getSettleAmountJY(customers));
				bigDecimalBX = bigDecimalBX.add(getSettleAmountBX(customers));
			}
		}
		
		geneReportSummary.setSumNum(sumNum);
		geneReportSummary.setSettlementAmountJY(bigDecimalJY);
		geneReportSummary.setSettlementAmountBX(bigDecimalBX);
		
		return geneReportSummary;
	}
	
	/**
	 * 基因公司结算金额计算
	 * @param customers
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午5:07:21
	 */
	private BigDecimal getSettleAmountJY(List<ErpCustomer> customers) throws Exception{
		BigDecimal bigDecimal = BigDecimal.ZERO;
		log.info("getSettleAmountJY customers size--"+customers.size());
		
		for (ErpCustomer erpCustomer : customers) {
			String setmealName = erpCustomer.getSetmealName();
			String test = erpCustomer.getTestInstitution();		//检测机构
			List<ErpComboPrice> comboPrices = queryService.getComboPriceByComboName(setmealName,test);
			/*log.info("getSettleAmountJY comboName--"+comboName);
			List<ErpSettlementCustomerJY> customerJY = queryService.getErpSettlementCustomerJYByCode(code);
			if(customerJY!=null&&customerJY.size()>0){
				log.info("getSettleAmountJY ErpSettlementCustomerJY name--"+customerJY.get(0).getName());
				if(customerJY.get(0).getSetmeal_price()!=null){
					bigDecimal = bigDecimal.add(customerJY.get(0).getSetmeal_price());	//价格
				}
			}*/
			
			if(comboPrices!=null&&comboPrices.size()>0){
					String comboName = comboPrices.get(0).getComboName();
					if(setmealName.equals(comboName)){
						log.info("getSettleAmountJY comboName"+comboName+","+comboPrices.get(0).getPrice());
						bigDecimal = bigDecimal.add(comboPrices.get(0).getPrice());	//价格
					}
			}
		}
		
		log.info("getSettleAmountJY bigDecimal--"+bigDecimal);
		return bigDecimal;
	}
	
	/**
	 * 保险公司结算金额计算
	 * @param customers
	 * @return
	 * @author tangxing
	 * @date 2016-9-1下午5:07:21
	 */
	private BigDecimal getSettleAmountBX(List<ErpCustomer> customers) throws Exception{
		BigDecimal bigDecimal = BigDecimal.ZERO;
		if(customers!=null){
			for (ErpCustomer erpCustomer : customers) {
				String setmealName = erpCustomer.getSetmealName();
				String branchCompany = erpCustomer.getBranchCompany();
				String code = erpCustomer.getCode();
				String status = erpCustomer.getStatus();
				log.info("getSettleAmountBX code--"+code);
				List<ErpCompanyComboPrice> companyComboPrices = queryService.getCompanyComboPriceByComboName(setmealName,branchCompany);
				if(companyComboPrices!=null&&companyComboPrices.size()>0){
					String companyComboName = companyComboPrices.get(0).getCombo();
					String company = companyComboPrices.get(0).getCompany();
					if(setmealName.equals(companyComboName)&&branchCompany.equals(company)){
						log.info("getSettleAmountBX comboName & branchCompany"+companyComboName+","+companyComboPrices.get(0).getComboPrice()+","+company);
						bigDecimal = bigDecimal.add(companyComboPrices.get(0).getComboPrice());	//价格
					}
				}
				
				/*if(status.equals("0")){	//状态为未结算，就取套餐价格表
					for (ErpCompanyComboPrice companyComboPrice : companyComboPrices) {
						String companyComboName = companyComboPrice.getCombo();
						if(setmealName.equals(companyComboName)){
							bigDecimal = bigDecimal.add(companyComboPrice.getComboPrice());	//价格
						}
					}
				}else{
					List<ErpCustomerSettleBX> customerSettleBXs = queryService.getErpCustomerSettleBXByCode(code);
					if(customerSettleBXs!=null&&customerSettleBXs.size()>0){
						log.info("getSettleAmountBX ErpCustomerSettleBX name--"+customerSettleBXs.get(0).getName());
						if(customerSettleBXs.get(0).getComboPrice()!=null){
							bigDecimal = bigDecimal.add(customerSettleBXs.get(0).getComboPrice());	//价格
						}
					}
				}*/
			}	
		}
		log.info("getSettleAmountBX bigDecimal--"+bigDecimal);
		return bigDecimal;
	}
	
	/**
	 * 根据项目编码查找物料金额（StoreProduceDetail）
	 * @param itemCode
	 * @return
	 * @author tangxing
	 * @date 2016-9-5上午11:10:25
	 */
	public BigDecimal getMaterielAmount(String itemCode,Date startTime,Date endTime) throws Exception{
		log.info("getMaterielAmount itemCode--"+itemCode);
		BigDecimal bigDecimal = BigDecimal.ZERO;
		List<StoreProduce> storeProduces = queryService.getStoreProduceByItemCode(itemCode,startTime,endTime);
		log.info("getMaterielAmount storeProduces size--"+storeProduces.size());
		if(storeProduces!=null){
			for (StoreProduce storeProduce : storeProduces) {
				log.info("getMaterielAmount storeProduce id--"+ storeProduce.getId());
				List<StoreProduceDetail> details = queryService.getStoreProduceDetailByProduceId(storeProduce.getId());	//获取StoreProduceDetail
				if(details!=null){	
					for (StoreProduceDetail storeProduceDetail : details) {
						if(storeProduceDetail.getTotalPrice()!=null){
							log.info("storeProduceDetail totalPrice--"+storeProduceDetail.getTotalPrice());
							bigDecimal = bigDecimal.add(BigDecimal.valueOf(storeProduceDetail.getTotalPrice()));
						}
					}
				}
			}
		}
		log.info("getMaterielAmount bigDecimal--"+bigDecimal);
		return bigDecimal;
	}
	
}
