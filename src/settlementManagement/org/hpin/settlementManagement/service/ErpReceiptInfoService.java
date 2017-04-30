package org.hpin.settlementManagement.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipProDao;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.dao.ErpReceiptInfoDao;
import org.hpin.settlementManagement.entity.ErpClaimInfo;
import org.hpin.settlementManagement.entity.ErpReceiptInfo;
import org.hpin.settlementManagement.entity.VO.ErpReceiptInfoVO;
import org.hpin.settlementManagement.entity.VO.ReceiptInfoQueryObj;
import org.hpin.settlementManagement.exception.BankStatementNullException;
import org.hpin.settlementManagement.exception.BankStatementRepeatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.settlementManagement.service.ErpReceiptInfoService")
@Transactional()
public class ErpReceiptInfoService extends BaseService {
	private Logger log = Logger.getLogger(ErpReceiptInfoService.class);
	
	@Autowired
	private ErpReceiptInfoDao dao;
	
	@Autowired
	private CustomerRelationshipProDao relationshipProDao;
	
	public void findReceiptInfoAll(Page<ErpReceiptInfoVO> page,ReceiptInfoQueryObj queryObject) throws Exception{
		List<Object> params = new ArrayList<Object>(); //参数;
		String sql = 
				" select receipt.owncash_bankaccountname_posno as ownCashBankAccountNamePOSNo, "+
				" receipt.owncash_bankaccounts          as ownCashBankAccounts, "+
				" receipt.payment_date                  as paymentDate, "+
				" receipt.actual_bankaccout_arrival     as actualBankAccountArrival, "+
				" receipt.payment_cost                  as paymentCost, "+
				" receipt.pos_fee                       as POSFee, "+
				" receipt.actual_bankaccout_cost        as actualBankAccountCost, "+
				" receipt.PAYERCOST_PAYERNAME           as payerCostPayerName, "+
				" receipt.payercost_bankaccout          as payerCostBankAccount, "+
				" receipt.BANK_SUMMARY                  as bankSummary, "+
				" receipt.PAYER_BANKACCOUNT_NAME        as payerBankAccountName, "+
				" pro.project_name                      as projectName, "+
				" pro.project_code                      as projectCode, "+
				" pro.project_owner                     as projectOwner, "+
				" claim.claim_cost                      as claimCost "+
				" from ERP_CLAIMINFO                claim "+
				" left join erp_receiptinfo         receipt on claim.receipt_id = receipt.id "+
				" left join HL_CUSTOMER_RELATIONSHIP_PRO pro on claim.project_id = pro.id "+
				" where claim.isdeleted = 0 ";
		StringBuilder jdbcSql = new StringBuilder(sql);
		if(queryObject!=null){
			jdbcSql.append(dealSqlParam(queryObject));
		}
		
		//查询count;
		page.setTotalCount(dao.findJdbcCount(jdbcSql, params));
		jdbcSql.append(" order by claim.receipt_id desc");
		//查询list;
		params.add(page.getPageNumEndCount());
		params.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpReceiptInfoVO> rowMapper = new BeanPropertyRowMapper<ErpReceiptInfoVO>(ErpReceiptInfoVO.class);
		page.setResults(dao.findJdbcList(jdbcSql, params, rowMapper));
//		List<ErpReceiptInfoVO> list = (List<ErpReceiptInfoVO>) dao.findJdbcListLocalView(jdbcSql.toString(), params, rowMapper);
		
//		page.setResults(list);
	}
	
	public StringBuilder dealSqlParam(ReceiptInfoQueryObj queryObject){
		StringBuilder jdbcSql = new StringBuilder();
		if(StringUtils.isNotEmpty(queryObject.getPaymentAccount())){//批次号
			jdbcSql.append(" and receipt.payment_Account = '").append(queryObject.getPaymentAccount().trim()).append("'");
			
		} 
		if(StringUtils.isNotEmpty(queryObject.getReceivablesAccount())){//姓名
			jdbcSql.append(" and receipt.receivables_Account = '").append(queryObject.getReceivablesAccount().trim()).append("'");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getPaymentCost())){			//条形码
			jdbcSql.append(" and receipt.payment_cost = ").append(queryObject.getPaymentCost()).append("");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getPaymentDateStart())){//导入开始时间
			jdbcSql.append(" and receipt.payment_date >= ").append("to_date('").append(queryObject.getPaymentDateStart()).append("','yyyy-MM-dd')");
			
		}
		if(StringUtils.isNotEmpty(queryObject.getPaymentDateEnd())){//导入结束时间
			jdbcSql.append(" and receipt.payment_date < ").append("to_date('").append(queryObject.getPaymentDateEnd()).append("','yyyy-MM-dd')+1");
			
		}
		return jdbcSql;
	}
	
	/**
	 * 根据银行流水号获取ReceiptInfo
	 * @param bankStatement
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-29下午5:37:53
	 */
	public ErpReceiptInfo getReceiptInfoByBankStatement(String bankStatement){
		List<ErpReceiptInfo> receiptInfos = dao.getReceiptInfoByBankStatement(bankStatement);
		ErpReceiptInfo receiptInfo = null;
		if(receiptInfos!=null&&receiptInfos.size()>0){
			//receiptInfo = new ErpReceiptInfo();
			receiptInfo = receiptInfos.get(0);
		}
		
		return receiptInfo;
	}
	
	/**
	 * 读取Xlsx格式的Excel
	 * @param file
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-3-29下午4:25:01
	 */
	public List<Map<String, String>> readExcelXlsx(File file) throws Exception{
		List<Map<String, String>> result = ExcelUtils.xlsxExcel(file);
		return result;
	}
	
	/**
	 * 读取Xls格式的Excel
	 * @param file
	 * @return
	 * @throws Exception
	 * @author LeslieTong
	 * @date 2017-3-29下午4:27:18
	 */
	public List<Map<String, String>> readExcelXls(File file) throws Exception{
		List<Map<String, String>> result = ExcelUtils.xlsExcel(file);
		return result;
	}
	
	/**
	 * 入库Excel读取ClaimInfo类
	 * @param excelResult
	 * @return
	 * @throws Exception
	 * @author LeslieTong
	 * @date 2017-3-29下午4:35:14
	 */
	public Map<String, String>  saveClaimInfoObject(List<Map<String, String>> excelResult, User currentUser) throws Exception{
		HashMap<String, String> mapStr = new HashMap<String, String>();
		ErpClaimInfo claimInfo = null;
		
		if(excelResult!=null&&excelResult.size()>0){
			for(Map<String, String> map : excelResult){
				claimInfo = new ErpClaimInfo();
				
				String bankStatement = map.get("0");	//银行流水号
				String projectCode = map.get("1");	//项目编号
				String subscriptionAmount = map.get("2");		//认购金额
				
				/* **** 认购金额为空 **** */
				if(StringUtils.isNotEmpty(subscriptionAmount)){		//认购金额有空
					
					/* **** 银行流水号为空 **** */
					if(StringUtils.isNotEmpty(bankStatement)){		//如果有流水号为空的，直接退出
						ErpReceiptInfo erpReceiptInfo = this.getReceiptInfoByBankStatement(bankStatement);
						if(erpReceiptInfo==null){	//银行流水号和的，提示失败
							mapStr.put("statusCode", "300");
							mapStr.put("message", "认领数据存在无对应流水号！");
							throw new BankStatementNullException(bankStatement+",认领数据存在无对应流水号！");
						}else{
							claimInfo.setReceiptId(erpReceiptInfo.getId());
						}
					}else{
						throw new BankStatementNullException("数据有银行流水号为空！");
					}
					
					//设置
					if(StringUtils.isNotEmpty(projectCode)){
						String projectId = relationshipProDao.getRelationShipProIdByCode(projectCode);
						claimInfo.setProjectId(projectId);
						
					}else{
						log.info(bankStatement+" , "+map.get("2")+"的projectId为空！");
					}
					
					//认领金额
					claimInfo.setClaimCost(StringUtils.isNotEmpty(map.get("2")) ? new BigDecimal(map.get("2")) : BigDecimal.ZERO);
				}else{
					throw new BankStatementNullException("【"+bankStatement+"】有认购金额为空！");
				}
				
				claimInfo.setCreateDate(new Date());
				claimInfo.setCreateUserName(currentUser.getUserName());
				claimInfo.setIsDeleted(0);
				
				dao.save(claimInfo);
			}
			
			mapStr.put("statusCode", "200");
			mapStr.put("message", "导入成功！");
		}else{
			mapStr.put("statusCode", "300");
			mapStr.put("message", "数据为空！");
		}
		
		return mapStr;
	}
	
	/**
	 * 入库Excel读取ReceiptInfo类
	 * @param excelResult
	 * @return
	 * @throws Exception
	 * @author LeslieTong
	 * @date 2017-3-29下午4:34:44
	 */
	public Map<String, String> saveReceiptInfoObject(List<Map<String, String>> excelResult, User currentUser,String filePath) throws Exception{
		HashMap<String, String> mapStr = new HashMap<String, String>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		ErpReceiptInfo receiptInfo = null;
		Set<String> set = null;
		
		/* **** 判断Excel是否有内容  **** */
		if(excelResult!=null&&excelResult.size()>0){
			set = new TreeSet<String>();
			
			boolean judgeBankAccoutIsNullflag = this.judgeBankAccoutIsNull(excelResult);
			
			/* **** 判断是否存在 只有【银行流水号】为空的数据 **** */
			if(judgeBankAccoutIsNullflag){		//true表示不存在 只有【银行流水号】为空的数据
				
				this.removeNull(excelResult);	//删除value为空的Map
				
				for(Map<String, String> map : excelResult){		//（Excel当次）排重银行流水号
					String bankStatement = map.get("4");		//银行流水
					if(!set.contains(bankStatement)){
						set.add(bankStatement);
					}
				}
				
				/* **** 当次Excel数据【银行流水号】排重 **** */
				if(excelResult.size()==set.size()){				//如果set集合的条数和读取Excel内容的条数一样，证明当次无重复的银行流水号
					
					for(Map<String, String> map : excelResult){
						String bankStatement = map.get("4");		//银行流水
						
						boolean judgeBankStatementflag = dao.judgeBankStatementRepeat(bankStatement);
						
						/* ****判断【银行流水号】是否跟数据库中有重复 **** */
						if(judgeBankStatementflag){	//true表示没有重复
							receiptInfo = new ErpReceiptInfo();
							
							receiptInfo.setOwnCompanyName(map.get("0"));					//我方公司名称
							receiptInfo.setPaymentTYpe(map.get("1"));						//收付款类别
							receiptInfo.setOwnCashBankAccountNamePOSNo(map.get("2"));		//我方现金/银行账户名称/POS机号
							receiptInfo.setOwnCashBankAccounts(map.get("3"));				//我方现金/银行账户账号
							
							String ownCashBankAccount = map.get("3");
							if(ownCashBankAccount.indexOf("/")!=-1){						//收款方银行账户
								String[] arrStr = ownCashBankAccount.split("/");
								receiptInfo.setReceivablesAccount(arrStr[1]);
							}
							
							receiptInfo.setBankStatement(bankStatement);					//银行流水
							
							if(map.get("5")!=null&&StringUtils.isNotEmpty(map.get("5"))){
								receiptInfo.setPaymentDate(sdf.parse(map.get("5")));		//收付款日期
							}else{
								receiptInfo.setPaymentDate(null);							//收付款日期
							}
							
							if(map.get("6")!=null&&StringUtils.isNotEmpty(map.get("6"))){
								receiptInfo.setActualBankAccountArrival(sdf.parse(map.get("6")));//实际银行到账日期
							}else{
								receiptInfo.setActualBankAccountArrival(null);				//实际银行到账日期
							}
							
							receiptInfo.setPaymentCost( StringUtils.isNotEmpty(map.get("7")) ? new BigDecimal(map.get("7")) : BigDecimal.ZERO );//收付款金额
							receiptInfo.setPOSFee(StringUtils.isNotEmpty(map.get("8")) ? new BigDecimal(map.get("8")) : BigDecimal.ZERO );//pos机刷卡手续费
							receiptInfo.setActualBankAccountCost(StringUtils.isNotEmpty(map.get("9")) ? new BigDecimal(map.get("9")) : BigDecimal.ZERO );//实际银行到账金额
							receiptInfo.setPayerCostPayerName(map.get("10"));				//付款方现金/付款方名称
							receiptInfo.setPayerCostBankAccount(map.get("11"));				//付款方现金/银行账户账号
							
							String payerCostBankAccount = map.get("11");
							if(payerCostBankAccount.indexOf("/")!=-1){						//付款方银行账户
								String[] arrStr = payerCostBankAccount.split("/");
								receiptInfo.setPaymentAccount(arrStr[1]);
							}
							
							receiptInfo.setPayerBankAccountName(map.get("12"));				//付款方银行账户名称
							receiptInfo.setBankSummary(map.get("13"));						//银行摘要
							receiptInfo.setIsDeleted(0);
							receiptInfo.setCreateDate(now);
							receiptInfo.setCreateUserName(currentUser.getUserName());
							
							dao.save(receiptInfo);
						}else{
							log.info(filePath+",银行流水号：【"+bankStatement+"】重复！");
							throw new BankStatementRepeatException("银行流水号：【"+bankStatement+"】重复！");
						}
					}
					
					mapStr.put("statusCode", "200");
					mapStr.put("message", "导入成功！");
				}else{
					log.info(filePath+",Excel内容存在，重复银行流水号！");
					//throw new BankStatementRepeatException("Excel内容存在重复银行流水号！");
					mapStr.put("statusCode", "300");
					mapStr.put("message", "Excel内容存在，重复银行流水号！");
				}
			}else{
				log.info(filePath+",Excel内容存在，银行流水号为空的数据！");
				//throw new BankStatementRepeatException("Excel内容存在重复银行流水号！");
				mapStr.put("statusCode", "300");
				mapStr.put("message", "Excel内容存在，银行流水号为空的数据！");
			}
			
		}else{
			mapStr.put("statusCode", "300");
			mapStr.put("message", "数据为空！");
		}
		
		return mapStr;
	}
	
	/**
	 * 删除list集合中，value为空的Map集合
	 * @param hashMaps
	 * @author LeslieTong
	 * @date 2017-3-31下午6:35:02
	 */
	private void removeNull(List<Map<String, String>> hashMaps){
		Iterator<Map<String, String>> it = hashMaps.iterator();
		while(it.hasNext()){
			Map<String, String> x = it.next();
			String str = x.get("0");
		    if(str.equals("")){
		        it.remove();
		    }
		}
	}
	
	/**
	 * 判断是否存在其他信息都有，但银行流水号为空
	 * @param hashMaps
	 * @return
	 * @author LeslieTong
	 * @date 2017-3-31下午6:38:54
	 */
	private boolean judgeBankAccoutIsNull(List<Map<String, String>> hashMaps){
		boolean flag = true;
		for(Map<String, String> map : hashMaps){
			String ownCashBankAccountNamePOSNo = map.get("2");
			String bankAccount = map.get("4");
			
			//如果"我方现金/银行账户名称/POS机号"有数据，但是银行流水号是空的，直接提示Excel导入失败
			if(StringUtils.isNotEmpty(ownCashBankAccountNamePOSNo)&&StringUtils.isEmpty(bankAccount)){
				flag = false;
				break;
			}
		}
		
		return flag;
	}
	
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	
}
