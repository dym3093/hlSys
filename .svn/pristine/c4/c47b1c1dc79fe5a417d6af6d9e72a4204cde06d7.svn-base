/**
 * dym
 * 2016-4-26 下午2:45:48
 */
package org.hpin.settlementManagement.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringContextHolder;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpReportdetailPDFContentDao;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.reportdetail.job.ErpReportWillPrintJob;
import org.hpin.reportdetail.util.DealWithFileUtil;
import org.hpin.settlementManagement.dao.ErpComboPriceDao;
import org.hpin.settlementManagement.dao.ErpCompanyComboPriceDao;
import org.hpin.settlementManagement.dao.ErpSettlementCustomerJYDao;
import org.hpin.settlementManagement.dao.ErpSettlementTaskJYDao;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.hpin.settlementManagement.entity.ErpJYSettleTaskDetail;
import org.hpin.settlementManagement.entity.ErpPrintComSettleCustomer;
import org.hpin.settlementManagement.entity.ErpSettleExcetaskJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerJY;
import org.hpin.settlementManagement.entity.ErpSettlementCustomerTemp;
import org.hpin.settlementManagement.entity.ErpSettlementTaskJY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymjy.combo.dao.ComboDao;

/**
 * @author dym
 * 2016-4-26 下午2:45:48
 */
@Service(value = "org.hpin.settlementManagement.service.ErpSettlementTaskJYService")
@Transactional()
public class ErpSettlementTaskJYService extends BaseService{
	
	private Logger log = Logger.getLogger(ErpSettlementTaskJYService.class);
	
	private String localPath;
	
	private String downloadContext;
	
	@Autowired
	private ErpSettlementTaskJYDao settlementTaskDao;
	@Autowired
	ErpCustomerDao erpCustomerDao;
	@Autowired
	ErpEventsDao eventsDao;
	@Autowired
	ErpReportdetailPDFContentDao contentDao;
	@Autowired
	ErpSettlementCustomerJYDao erpSettlementCustomerJYDao;
	@Autowired
	ErpComboPriceDao erpComboPriceDao;
	@Autowired
	ComboDao comboDao;
	@Autowired
	ErpCompanyComboPriceDao comboPriceDao;
	
	private Integer excelSize = 0;
	
	public List<ErpSettlementTaskJY> getSettleTaskAll(){
		return settlementTaskDao.getSettleTaskAll();
	}
	
	public List listSettleJyTask(Page page,Map searchMap){
		return settlementTaskDao.listSettleJyTask(page, searchMap);
	}
	
	public List listSettleJyTaskTwo(Page page,Map searchMap){
		return settlementTaskDao.listSettleJyTaskTwo(page, searchMap);
	}
	
	public List listSettleJyTaskTwoAll(Page page,Map searchMap){
		return settlementTaskDao.listSettleJyTaskTwoAll(page, searchMap);
	}
	
	public List listSettleExceTask(Page page,Map searchMap){
		return settlementTaskDao.listSettleExceTask(page, searchMap);
	}
	
	public List getExceSettleTaskBySettleTaskNo(String taskNo,Page page,Map searchMap){
		return settlementTaskDao.getExceSettleTaskBySettleTaskNo(taskNo,page,searchMap);
	}
	
	public List<ErpSettlementCustomerJY> getSettleCusByExceId(String exceTaskId,String status,Page page,Map searchMap){
		return settlementTaskDao.getSettleCusByExceId(exceTaskId,status,page,searchMap);
	}
	
	public List<ErpSettlementCustomerJY> getSettleCusByExceIdAnd(String exceTaskId,String pdfStatus,Page page,Map searchMap){
		return settlementTaskDao.getSettleCusByExceIdAnd(exceTaskId,pdfStatus,page,searchMap);
	}
	
	public List<ErpSettlementCustomerJY> getExceSettleCusAll(String exceTaskId,Page page,Map paramsMap){
		return settlementTaskDao.getExceSettleCusAll(exceTaskId,page,paramsMap);
	}
	
	/**
	 * 查找0、6状态的结算customer
	 * @return
	 * @author tangxing
	 * @date 2016-11-10上午11:08:13
	 */
	public List<ErpSettlementCustomerJY> getSettlementCustomerByStatus(String settlementId,String status){
		return settlementTaskDao.getSettlementCustomerByStatus(settlementId,status);
	}
	
	public List<ErpSettlementCustomerJY> getSettlementCustomerByStatus(String settlementId){
		return settlementTaskDao.getSettlementCustomerByStatus(settlementId);
	}
	
	/**
	 * 统计Excel总数
	 * @param settlementId
	 * @return
	 */
	public int getExcleNum(String settlementId){
		return settlementTaskDao.getExcleNum(settlementId);
	}
	
	/**
	 * 统计异常数量
	 * @param settlementId
	 * @return
	 */
	public int getExceptionNum(String settlementId){
		return settlementTaskDao.getExceptionNum(settlementId);
	}
	
	/**
	 * 统计支公司数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 * @date 2016-5-16上午10:24:45
	 */
	public int getBranchCompanyNum(String settlementId){
		return settlementTaskDao.getBranchCompanyNum(settlementId);
	}
	
	/**
	 * 获取当前结算批次的所有套餐数量
	 * @param settlementId
	 * @return
	 * @author tangxing
	 */
	public int getComboNumBySettleId(String settlementId){
		List<ErpSettlementCustomerJY> list = settlementTaskDao.getCustomer(settlementId);
		List<String> strings = new ArrayList<String>();
		int size=0;
		
		Set<String> set = new LinkedHashSet<String>();
		
		if(list.size()>0){
			for(int i=0;i<list.size(); ++i){
				strings.add(list.get(i).getSetmeal_id());
			}
			set.addAll(strings);
			size = set.size();
			return size;
		}else{
			return 0;
		}
	}
	
	public ErpSettleExcetaskJY getExceSettleById(String exceTaskId){
		return settlementTaskDao.getExceSettleById(exceTaskId);
	}
	
	/**
	 * 根据ID获取读取客户类
	 * @param id
	 * @return
	 */
	public ErpSettlementCustomerJY get(String id){
		return settlementTaskDao.get(id);
	}
	
	/**
	 * 根据ID获取读取客户类
	 * @param id
	 * @return
	 */
	public List getCustomer(String id){
		return settlementTaskDao.getCustomer(id);
	}
	
	/**
	 * 根据异常任务ID查找客户信息
	 * @param exSettTaskId
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午6:51:55
	 */
	public List<ErpSettlementCustomerJY> getSettleCustomerByExSettTaskId(String exSettTaskId){
		return settlementTaskDao.getSettleCustomerByExSettTaskId(exSettTaskId);
	}
	
	
	/**
	 * 根据ID获取读取客户类
	 * @param id
	 * @return
	 */
	public List getCustomer(Map searchMap, Page page,String id){
		return settlementTaskDao.getCustomer(searchMap,page,id);
	}
	
	/**
	 * 根据ID获取读取客户类
	 * @param id
	 * @return
	 */
	public ErpSettlementTaskJY getSettlementTask(String id){
		return settlementTaskDao.getSettlementTask(id);
	}
	
	public List<ErpJYSettleTaskDetail> getJYSettleTaskDetailBysettleId(String settleTaskId,Page page, Map searchMap){
		return settlementTaskDao.getJYSettleTaskDetailBysettleId(settleTaskId,page,searchMap);
	}
	
	public List<ErpJYSettleTaskDetail> getJYSettleTaskDetailBysettleId(String settleTaskId){
		return settlementTaskDao.getJYSettleTaskDetailBysettleId(settleTaskId);
	}
	/**
	 * 根据ID获取ErpJYSettleTaskDetail
	 * @param settleTaskDetailId
	 * @return
	 * @author tangxing
	 * @date 2016-9-18下午12:12:07
	 */
	public ErpJYSettleTaskDetail getJYSettleTaskDetailByDetailId(String settleTaskDetailId){
		return settlementTaskDao.getJYSettleTaskDetailByDetailId(settleTaskDetailId);
	}
	
	/**
	 * 根据ID获取异常结算任务
	 * @param id
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午6:53:38
	 */
	public ErpSettleExcetaskJY getExceSettlementTask(String id){
		return settlementTaskDao.getExceSettlementTask(id);
	}
	
	/**
	 * 根据结算任务ID,获取读取客户类
	 * @param settleTaskId
	 * @return
	 * @author tangxing
	 * @date 2016-6-13下午6:18:56
	 */
	public List<ErpSettlementCustomerJY> getCustomerBySettleTaskId(String settleTaskId){
		return settlementTaskDao.getCustomerBySettleTaskId(settleTaskId);
	}
	
	/**
	 * 根据不同状态查找出读取的客户信息
	 */
	public List showCustomerByStatus(String settlementId,String status){
		return settlementTaskDao.showCustomerByStatus(settlementId, status);
	}
	
	/**
	 * 修改价格
	 * @author tangxing
	 */
	public void updateCustomerPrice(ErpSettlementCustomerJY customerJY){
		settlementTaskDao.update(customerJY);
	}
	
	/**
	 * 修改客户信息状态
	 * @author tangxing
	 */
	public void updateCustomerPrice(List<ErpSettlementCustomerJY> customerJYList){
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : customerJYList) {
			settlementTaskDao.update(erpSettlementCustomerJY);
		}
	}
	
	/**
	 * 修改结算任务
	 * @author tangxing
	 */
	public void updateSetTask(ErpSettlementTaskJY erpSettlementTaskJY) {
		settlementTaskDao.update(erpSettlementTaskJY);
	}
	
	/**
	 * 确认结算任务
	 * @param params
	 * @return boolean
	 * @author DengYouming
	 * @since 2016-4-27 上午8:41:47
	 */
	public boolean confirmErpSettlementTaskJY(Map<String, Object> params) {
		
		return false;
	}


	/**
	 * 逻辑删除，批量删除结算任务
	 * @param params
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-27 上午8:49:38
	 */
	public void delErpSettlementTaskJYBatch(Map<String, Object> params) throws Exception {
		String ids = (String)params.get(ErpSettlementTaskJY.F_ID);
		//获取当前用户
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		ErpSettlementTaskJY entity = null;
		if(ids.indexOf(",")!=-1){
			String[] idArr = ids.split(",");
			if(idArr.length>0){				
				String id = null;				
				for (int i = 0; i < idArr.length; i++) {
					id = idArr[i];
					//获取人员信息核对表
					List<ErpSettlementCustomerJY> erpSettlementCustomerJYList =
							erpSettlementCustomerJYDao.findByProperty(ErpSettlementCustomerJY.class, "settlementTask_id", id, "create_time", true);
					for (ErpSettlementCustomerJY erpSettlementCustomerJY : erpSettlementCustomerJYList) {
						//设置结算任务ID为null
						erpSettlementCustomerJY.setSettlementTask_id(null);					
						//修改人，修改人ID
						erpSettlementCustomerJY.setUpdate_user_name(currentUser.getUserName());
						erpSettlementCustomerJY.setUpdate_user_id(currentUser.getId());
						//修改时间
						erpSettlementCustomerJY.setUpdate_time(new Date());
						//结算中的状态
						erpSettlementCustomerJY.setStatus("0");
						//更新
						erpSettlementCustomerJYDao.update(erpSettlementCustomerJY);
					}
					entity = (ErpSettlementTaskJY) settlementTaskDao.findById(ErpSettlementTaskJY.class, id);
					//设置删除状态
					entity.setStatus("-1");
					entity.setUpdateTime(new Date());
					//更新状态					
					settlementTaskDao.update(entity);
				}
			}
		}else{
			entity = (ErpSettlementTaskJY) settlementTaskDao.findById(ErpSettlementTaskJY.class, ids);
			//获取人员信息核对表
			List<ErpSettlementCustomerJY> erpSettlementCustomerJYList =
					erpSettlementCustomerJYDao.findByProperty(ErpSettlementCustomerJY.class, "settlementTask_id", ids, "create_time", true);
			for (ErpSettlementCustomerJY erpSettlementCustomerJY : erpSettlementCustomerJYList) {
				//设置结算任务ID为空
				erpSettlementCustomerJY.setSettlementTask_id(null);					
				//修改人，修改人ID
				erpSettlementCustomerJY.setUpdate_user_name(currentUser.getUserName());
				erpSettlementCustomerJY.setUpdate_user_id(currentUser.getId());
				//修改时间
				erpSettlementCustomerJY.setUpdate_time(new Date());
				//结算中的状态
				erpSettlementCustomerJY.setStatus("0");
				//更新
				erpSettlementCustomerJYDao.update(erpSettlementCustomerJY);
			}
			//修改人及ID
			entity.setUpdateUser(currentUser.getUserName());
			entity.setUpdateUserId(currentUser.getId());
			entity.setUpdateTime(new Date());
			//设置删除状态
			entity.setStatus("-1");
			//更新状态
			settlementTaskDao.update(entity);
		}
	}

	public void saveErpSettlementTaskJY(ErpSettlementTaskJY erpSettlementTaskJY) throws Exception {
		//获取当前用户
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		
		//设置操作人信息
		erpSettlementTaskJY.setCreateUser(currentUser.getUserName());
		erpSettlementTaskJY.setCreateUserId(currentUser.getId());
		erpSettlementTaskJY.setCreateTime(new Date());		//创建时间
		erpSettlementTaskJY.setStatus("0");					//新建
		erpSettlementTaskJY.setIsDeleted(0);
		
		settlementTaskDao.save(erpSettlementTaskJY);
	}
	
	
	/**
	 * 保存结算任务
	 * @param params
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-27 上午8:51:20
	 */
	public boolean saveErpSettlementTaskJY(Map<String, Object> params,Integer count) throws Exception {
		String ids = (String)params.get("settleTaskId");
		BigDecimal bigDecimal = BigDecimal.ZERO;
		BigDecimal bigDecimalTwo = BigDecimal.ZERO;
		boolean flag = false;
		Integer size = 0;
		//获取结算任务
		ErpSettlementTaskJY entity = (ErpSettlementTaskJY) params.get(ErpSettlementTaskJY.F_ERPSETTLEMENTTASKJY);
		//获取当前用户
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		//人员信息核对表相关记录ID
//		String ids = (String) params.get("detail");
		
		if(entity!=null){
			
			//注意：人员核算表记录保存已在导入EXCEL表时处理，该部分实际上不执行，只做保留  mark DengYouming 2016-05-04
			if(ids!=null&&ids.length()>0){
				//结算的会员ID
				if(ids.indexOf(",")!=-1){
					String[] idArr = ids.split(",");
					if(idArr.length>0){				
						String id = null;				
						for (int i = 0; i < idArr.length; i++) {
							id = idArr[i];
							//获取人员信息核对表
							List<ErpSettlementCustomerJY> settlementCustomerJYs =
									erpSettlementCustomerJYDao.findByProperty(ErpSettlementCustomerJY.class, "settlementTask_id", id, "create_time", true);
							List<ErpSettlementCustomerJY> erpSettlementCustomerJYList = erpSettlementCustomerJYDao.getSettleCustomerBySettIdStatus(id);
							size = settlementCustomerJYs.size();								//该id所有的客户信息数量
							for (ErpSettlementCustomerJY erpSettlementCustomerJY : erpSettlementCustomerJYList) {
								String comboName = erpSettlementCustomerJY.getSetmeal_name();	//套餐名字
								
								//设置结算任务ID
								erpSettlementCustomerJY.setSettlementTask_id(entity.getId());
								//根据套餐名称查找套餐价格
								List<ErpComboPrice> comboPrices = erpComboPriceDao.listComboPriceByGeCompanyId(entity.getGeCompanyId());
								
								for (ErpComboPrice erpComboPrice : comboPrices) {
									if(comboName.contains(erpComboPrice.getComboName())){
										//关联套餐相关
										erpSettlementCustomerJY.setSetmeal_id(erpComboPrice.getComboId());
										erpSettlementCustomerJY.setSetmeal_name(erpComboPrice.getComboName());
										erpSettlementCustomerJY.setSetmeal_price(erpComboPrice.getPrice());
										break;
									}else{
										erpSettlementCustomerJY.setSetmeal_price(bigDecimalTwo);
									}
								}
								//创建人，ID及时间
								erpSettlementCustomerJY.setCreate_user_name(currentUser.getUserName());
								erpSettlementCustomerJY.setCreate_user_id(currentUser.getId());
								erpSettlementCustomerJY.setCreate_time(new Date());
								//初始化状态
//								erpSettlementCustomerJY.setStatus("0");
								//更新
								erpSettlementCustomerJYDao.update(erpSettlementCustomerJY);
							}
						}
					}
				}else{
					//单个会员信息的改变逻辑
					//获取人员信息核对表
					List<ErpSettlementCustomerJY> settlementCustomerJYs=
							erpSettlementCustomerJYDao.findByProperty(ErpSettlementCustomerJY.class, "settlementTask_id", ids, "create_time", true);
					List<ErpSettlementCustomerJY> erpSettlementCustomerJYList = erpSettlementCustomerJYDao.getSettleCustomerBySettIdStatus(ids);	//获取非异常任务的数据
					size = settlementCustomerJYs.size();			//该ID所有的客户信息
					for (ErpSettlementCustomerJY erpSettlementCustomerJY : erpSettlementCustomerJYList) {
						String comboName = erpSettlementCustomerJY.getSetmeal_name();	//套餐名字
						
						//设置结算任务ID
						erpSettlementCustomerJY.setSettlementTask_id(entity.getId());
						//根据套餐名称查找套餐价格
						List<ErpComboPrice> comboPrices = erpComboPriceDao.listComboPriceByGeCompanyId(entity.getGeCompanyId());
						
						for (ErpComboPrice erpComboPrice : comboPrices) {
							if(comboName.contains(erpComboPrice.getComboName())){
								//关联套餐相关
								erpSettlementCustomerJY.setSetmeal_id(erpComboPrice.getComboId());
								erpSettlementCustomerJY.setSetmeal_name(erpComboPrice.getComboName());
								erpSettlementCustomerJY.setSetmeal_price(erpComboPrice.getPrice());
								break;
							}else{
								erpSettlementCustomerJY.setSetmeal_price(bigDecimalTwo);
							}
						}
						
						//创建人，ID及时间
						erpSettlementCustomerJY.setCreate_user_name(currentUser.getUserName());
						erpSettlementCustomerJY.setCreate_user_id(currentUser.getId());
						erpSettlementCustomerJY.setCreate_time(new Date());
						
						bigDecimal = bigDecimal.add(erpSettlementCustomerJY.getSetmeal_price());
						//结算中的状态
//						erpSettlementCustomerJY.setStatus("2");
						//更新
						erpSettlementCustomerJYDao.update(erpSettlementCustomerJY);
					}
				}
			}
			
			//获取结算任务相关人员信息核对表
			List<ErpSettlementCustomerJY> erpSettlementCustomerJYList =
					erpSettlementCustomerJYDao.findByProperty(ErpSettlementCustomerJY.class, "settlementTask_id", entity.getId(), "create_time", true);
			if(erpSettlementCustomerJYList!=null&&erpSettlementCustomerJYList.size()>0){
				//结算总人数
				entity.setTotalPersonNum(erpSettlementCustomerJYList.size());				
			}
			//该批次套餐总额
			entity.setTotalAmount(bigDecimal);
			
			//导入的总数
			entity.setTotalPersonNum(count);	
			
			//支公司数量
			Integer branchCompanyNum = erpSettlementCustomerJYDao.branchCompanyNum(entity.getId());
			entity.setBranchCompanyNum(branchCompanyNum);
			
			//获取结算任务中套餐数量
			Integer setMealNum = erpSettlementCustomerJYDao.countSetMealNum(entity.getId());
			entity.setSetMealNum(setMealNum);
			
			//异常数量
			Integer abnormalNum = erpSettlementCustomerJYDao.abnormalNum(entity.getId());
			entity.setAbnormalNum(abnormalNum);
			
			//匹配成功总数
			Integer successNum = count-abnormalNum;
			entity.setSuccessNum(successNum);
			
			//设置操作人信息
			entity.setCreateUser(currentUser.getUserName());
			entity.setCreateUserId(currentUser.getId());
			entity.setCreateTime(new Date());
			//初始化状态
			entity.setStatus("2");
			if(size!=0){		//客户信息集合为0，不创建结算任务
				//保存结算业务
				settlementTaskDao.saveSettlementTask(entity);
				flag = true;
			}else{
				flag = false;
			}
		}
		
		return flag;
	}

	/**
	 * 根据条件查询对于结算任务列表
	 * @param page
	 * @param searchMap
	 * @return List<ErpSettlementTaskJY> 
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-27 上午8:52:25
	 */
	/*public List<ErpSettlementTaskJY> findByPage(Page page,
			Map searchMap) throws Exception {
		List<ErpSettlementTaskJY> list = settlementTaskDao.findByPage(page, searchMap);
		return list;
	}*/
	
	/**
	 * 列出所有数据
	 * @return List<ErpSettlementTaskJY>
	 * @author DengYouming
	 * @since 2016-4-27 下午2:21:26
	 */
	public List<ErpSettlementTaskJY> listAll(){
		List<ErpSettlementTaskJY> list = settlementTaskDao.listAll();
		return list;
	}
	
	/**
	 * 保存会员数据（xlsx格式的Excel）
	 * @param file
	 * @param settlementTaskId
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-8-11上午11:18:16
	 */
	public Integer saveSettlementCustomerXlsx(File file,String settlementTaskId) throws Exception {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		long stratTime = new Date().getTime();
		List<Map<String, String>> result = ExcelUtils.xlsxExcel(file);
		createSettlementObj(result,currentUser,settlementTaskId);
		long endTime = new Date().getTime();
		
		
		
		System.out.println("耗时----"+(endTime-stratTime));
		/*
		List<ErpSettlementCustomerJY> matchSettlements = new ArrayList<ErpSettlementCustomerJY>();
		excelSize = result.size();
		
		if(excelSize<=10000){
			List<ErpSettlementCustomerJY> settlements = createSettlementObj(result,currentUser,settlementTaskId);
			
			matchSettlements = matchSettlementsAndCustomer(settlements);
			
			if(matchSettlements.size()>0){	//区分异常，非异常的任务
				for (ErpSettlementCustomerJY erpSettlementCustomerJY : matchSettlements) {
					String status = erpSettlementCustomerJY.getStatus();
					if(status.equals("0")||status.equals("1")||status.equals("2")){
						settCusList.add(erpSettlementCustomerJY);
					}else if(status.equals("3")||status.equals("4")||status.equals("5")){
						settCusException.add(erpSettlementCustomerJY);
					}
				}
			}
			
			//保存比对好的结果
			this.saveErpSettlementCustomerJYBatch(matchSettlements);
		}*/
		
		
		return result.size();
	}
	
	/**
	 * 保存会员数据（xls格式的Excel）
	 * @param file
	 * @param settlementTaskId
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-8-11上午11:18:46
	 */
	public Integer saveSettlementCustomerXls(File file,String settlementTaskId) throws Exception {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		List<Map<String, String>> result = ExcelUtils.xlsExcel(file);
		createSettlementObj(result,currentUser,settlementTaskId);
		return result.size();
	}
	
	
	public Integer excelSize(){
		Integer num = excelSize;
		return num;
	}
	

	/*private List<ErpSettlementCustomerJY> matchSettlementsAndCustomer(List<ErpSettlementCustomerJY> settlements) {
		if(settlements.size()>0){ 
			for(ErpSettlementCustomerJY settlement : settlements){
				if(("").equals(settlement.getCode())||("").equals(settlement.getSetmeal_name())){
					settlement.setStatus("5");//条码为空，异常数据
					continue;
				}
				List<ErpReportdetailPDFContent>  curPdfContents = contentDao.queryUnSettlementContent(settlement.getCode());
				if(null==curPdfContents||0==curPdfContents.size()){
					settlement.setStatus("4");
				}else{
					for(ErpReportdetailPDFContent content : curPdfContents){
						if(content.getUsername()==null||content.getSetmeal_name()==null){
							settlement.setStatus("4");
							continue;
						}
						if(content.getSettlement_status()!=null){
							if((content.getSettlement_status()).equals("1")){
								settlement.setStatus("3");
								settlement.setPdfcontent_id(content.getId());
								continue;
							}
						}
						if((content.getUsername()).equals(settlement.getName())&&(settlement.getSetmeal_name().indexOf(content.getSetmeal_name())!=-1)){
							settlement.setStatus("2");//可结算
							settlement.setPdfcontent_id(content.getId());
							//匹配系统会员信息获取套餐价格
							if(null!=content.getCustomerid()||!("").equals(content.getCustomerid())){
								ErpCustomer customer = (ErpCustomer)erpCustomerDao.findById(ErpCustomer.class,content.getCustomerid());
								if(customer!=null){
									String comboid = comboDao.queryId(customer.getSetmealName());
									ErpCompanyComboPrice comboPrice = comboPriceDao.findById(comboid);
									if(null!=comboPrice){
										settlement.setSetmeal_id(comboid);
										settlement.setSetmeal_price(comboPrice.getComboPrice());
									}
								}
								
							}
							
							break;
						}
						if((content.getUsername()).equals(settlement.getName())&&(settlement.getSetmeal_name().indexOf(content.getSetmeal_name())==-1)){
							settlement.setStatus("1");//信息有误
							settlement.setPdfcontent_id(content.getId());
							continue;
						}
						if(!(content.getUsername()).equals(settlement.getName())){
							settlement.setStatus("1");//信息有误
							settlement.setPdfcontent_id(content.getId());
							continue;
						}
					}
				}
			}
			
		}
		
		return settlements;
	}*/
	
	/**
	 * 比对导入的Excel客户信息（从ErpCustomer里面去拿）
	 * @param settlements
	 * @return
	 * @author tangxing
	 * @date 2016-6-13下午5:42:52
	 */
	private List<ErpSettlementCustomerJY> matchSettlementsAndCustomer(List<ErpSettlementCustomerJY> settlements) {
		if(settlements.size()>0){ 
			for(ErpSettlementCustomerJY settlement : settlements){
				String code = settlement.getCode();
				if(StringUtils.isEmpty(code)){
					settlement.setStatus("5");						//条码为空
					continue;
				}
				
				//根据当前客户的条形码到pdf客户表去找
				
				List<ErpCustomer> customers = erpCustomerDao.getCustomerByCode(settlement.getCode());
				/*List<ErpReportdetailPDFContent>  curPdfContents = contentDao.findSettlementContent(settlement.getCode());*/
				if(null==customers||0==customers.size()){	
					settlement.setStatus("4");						//信息不存在
				}else{
					//找到条码对应的信息后，遍历比对
					for(ErpCustomer customer : customers){
						String pdfId = customer.getId();
						if(StringUtils.isEmpty(customer.getPdffilepath())){	//根据customer是否有pdf路径，判断报告状态
							settlement.setPdf_Status("1");
						}else{
							settlement.setPdf_Status("0");
						}
						
						String name = customer.getName();
						String age = customer.getAge();
						if(name==null){
							name = "";
						}
						if(age==null){
							age = "";
						}
						
						//条码,姓名，性别 有一个不一致，为信息有误
						if(!name.equals(settlement.getName())
								||!age.equals(settlement.getAge())){
							settlement.setStatus("3");
							settlement.setPdfcontent_id(pdfId);
							continue;
						}
						
						//条码,姓名，性别一致。匹配结算状态
						if(name.equals(settlement.getName())
								&&customer.getCode().equals(settlement.getCode())
								&&age.equals(settlement.getAge())){
							if(StringUtils.isEmpty(customer.getSettlement_status())||customer.getSettlement_status().equals("0")){//可结算
								settlement.setStatus("0");	
								settlement.setPdfcontent_id(pdfId);
							}else if(customer.getSettlement_status().equals("1")){	//待结算
								settlement.setStatus("1");
								settlement.setPdfcontent_id(pdfId);
							}else if(customer.getSettlement_status().equals("2")){	//已结算
								settlement.setStatus("2");
								settlement.setPdfcontent_id(pdfId);
							}
						}
					}
				}
			}
		}
		
		return settlements;
	}

	//将EXCEL中的数据组装对象
	private void createSettlementObj(List<Map<String, String>> result,User user,String settlementTaskId) throws ParseException {
		int sumSize = result.size();//总条数
		int dbNum = 1700;
		int i=0;
		int a = sumSize%dbNum;		//余数
		int b = sumSize/dbNum;		//被800整除的次数
		List<ErpSettlementCustomerTemp> list = new ArrayList<ErpSettlementCustomerTemp>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		for(Map<String, String> map : result){
			ErpSettlementCustomerTemp customerTemp = new ErpSettlementCustomerTemp();
			// 第一行是条码,第二行是姓名,第三行性别,第四行年龄,第五行联系方式,第六行支公司,
			// 第七行检测项目,第八行样本类型,第九行送检医生,第十行送检单位,
			// 第十一行提交者,第十二行送检日期,第十三行收样日期,第十四行状态,第十五行报告
			
			//连续四个为空，跳过
			if(StringUtils.isEmpty(map.get("0"))&&StringUtils.isEmpty(map.get("1"))&&StringUtils.isEmpty(map.get("2"))&&StringUtils.isEmpty(map.get("3"))){
				break;
			}
			
			customerTemp.setCode(map.get("0"));
			customerTemp.setName(map.get("1"));
			customerTemp.setSex(map.get("2"));
			customerTemp.setAge(map.get("3"));
			customerTemp.setContact(map.get("4"));
			customerTemp.setBranch_company(map.get("5"));
			//customerTemp.setFamily_history(map.get("6"));
			customerTemp.setItems(map.get("6"));
			//customerTemp.setSetmeal_name(map.get("6"));
			customerTemp.setSample_type(map.get("7"));
			customerTemp.setCensorship_doctor(map.get("8"));
			customerTemp.setCensorship_company(map.get("9"));
			customerTemp.setSubmitter(map.get("10"));
			if(map.get("11")!=null&&StringUtils.isNotEmpty(map.get("11"))){
				customerTemp.setCensorship_time(sdf.parse(map.get("11")));
			}else{
				customerTemp.setCensorship_time(null);
			}
			if(map.get("12")!=null&&StringUtils.isNotEmpty(map.get("12"))){
				customerTemp.setReceive_time(sdf.parse(map.get("12")));
			}else{
				customerTemp.setReceive_time(null);
			}
			/*customerTemp.setCensorship_time(map.get("11")==null?null:sdf.parse(map.get("11")));
			customerTemp.setReceive_time(map.get("12")==null?null:sdf.parse(map.get("12")));*/
			customerTemp.setImport_status(map.get("13"));
			customerTemp.setReport_name(map.get("14"));
			customerTemp.setCreate_user_id(user.getId());
			customerTemp.setCreate_time(now);
			customerTemp.setCreate_user_name(user.getUserName());
			customerTemp.setStatus("0");
			customerTemp.setSettlementTask_id(settlementTaskId);
			list.add(customerTemp);
			
			int size = list.size();
			if(a==0){
				if(size==dbNum){
					erpSettlementCustomerJYDao.saveSettCustomerList(list);
					list.clear();
				}
			}else{
				if(size==dbNum){
					erpSettlementCustomerJYDao.saveSettCustomerList(list);
					list.clear();
					++i;
				}
				if(i==b&&size==a){
					log.info("createSettlementObj surplus List size----"+list.size());
					erpSettlementCustomerJYDao.saveSettCustomerList(list);
				}
			}
		}
	}

	/**
	 * 批量保存ErpSettlementCustomerJY
	 * @param list
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午2:25:32
	 */
	public boolean saveErpSettlementCustomerJYBatch(List<ErpSettlementCustomerJY> list) throws Exception{
		boolean flag = false;
		//批量保存
		erpSettlementCustomerJYDao.saveEntityBatch(list);
		flag = true;
		return flag;
	}
	
	/**
	 * 根据保险公司结算任务ID删除ErpSettlementCustomerJY表的记录
	 * @param settle_id
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-3 下午11:19:58
	 */
	public boolean deleteErpSettlementCustomerJYBatchBySettleId(String settle_id) throws Exception{
		boolean flag = false;
		//批量删除
		erpSettlementCustomerJYDao.deleteErpSettlementCustomerJYBatchBySettleId(settle_id);
		flag = true;
		return flag;
	}
	
/*-----通过集合获取各类数量-----*/
	
	/**
	 * 条码不存在数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016年6月8日16:42:56
	 */
	public Integer getNoFindNum(List<ErpSettlementCustomerJY> result){
		Integer num = 0;
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : result) {
			if(erpSettlementCustomerJY.getStatus().equals("4")){
				num++;
			}
		}
		return num;
	}
	
	/**
	 * 信息有误数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016年6月8日16:42:56
	 */
	public Integer getErrInfoNum(List<ErpSettlementCustomerJY> result){
		Integer num = 0;
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : result) {
			if(erpSettlementCustomerJY.getStatus().equals("3")){
				num++;
			}
		}
		return num;
	}
	
	/**
	 * 条形码为空数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016年6月8日16:42:56
	 */
	public Integer getCodeNullNum(List<ErpSettlementCustomerJY> result){
		Integer num = 0;
		for (ErpSettlementCustomerJY erpSettlementCustomerJY : result) {
			if(erpSettlementCustomerJY.getStatus().equals("5")){
				num++;
			}
		}
		return num;
	}
	
	/**
	 * 套餐数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016年6月8日16:43:04
	 */
	public Integer getSetMealNum(List<ErpSettlementCustomerJY> result){
		List<String> strings = new ArrayList<String>();	//存放套餐
		Integer size=0;
		Set<String> set = new LinkedHashSet<String>();	//排重
		
		if(result.size()>0){
			for(int i=0;i<result.size(); ++i){
				if(result.get(i).getSetmeal_name()!=null){
					strings.add(result.get(i).getSetmeal_name());
				}
			}
			set.addAll(strings);
			size = set.size();
			return size;
		}else{
			return 0;
		}
	}
	
	/**
	 * 支公司数量
	 * @param result
	 * @return
	 * @author tangxing
	 * @date 2016年6月8日16:43:11
	 */
	public Integer getBranchCompanyNum(List<ErpSettlementCustomerJY> list){
		List<String> strings = new ArrayList<String>();	//存放套餐
		Integer size=0;
		Set<String> set = new LinkedHashSet<String>();	//排重
		
		if(list.size()>0){
			for(int i=0;i<list.size(); ++i){
				if(list.get(i).getBranch_company()!=null){
					strings.add(list.get(i).getBranch_company());
				}
			}
			set.addAll(strings);
			size = set.size();
			return size;
		}else{
			return 0;
		}
	}
	
    /**
     * 默认的任务号
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:23:28
     */
    public String defTaskNum(){
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	String time = dateFormat.format(date);
    	return time;
    }
    
    /**
     * 随机生成三位数
     * @return
     * @author tangxing
     * @date 2016-6-6下午4:03:54
     */
    public int ranNum(){
    	Random randomNumber = new Random();
    	int i=randomNumber.nextInt(900)+100;	//三位数随机数
    	return i;
    }
    
    /**
     * 保存异常结算任务
     * @param obj
     * @author tangxing
     * @date 2016-6-8下午3:35:01
     */
    public void saveExcepSettleTask(ErpSettleExcetaskJY obj){
    	settlementTaskDao.saveExcepSettleTask(obj);
    }
    
    /**
     * 根据ID查找基因公司结算任务
     * @param settleTaskId
     * @return
     * @author tangxing
     * @date 2016-6-8下午3:42:11
     */
    public ErpSettlementTaskJY getSettleTaskJYById(String settleTaskId){
    	return settlementTaskDao.getSettlementTask(settleTaskId);
    }
    
    public List<ErpSettlementCustomerJY> getSettleCustomerByTaskId(String id){
    	return settlementTaskDao.getCustomerByStatus(id);
    }
    
    /**
     * 根据条形码查询结算用户
     * @param code
     * @return
     * @author tangxing
     * @date 2016-6-13上午10:32:54
     */
    public List<Map<String, Object>> getSettleCustomerByCode(String code){
    	return settlementTaskDao.getSettleCustomerByCode(code);
    }
    
    /**
     * save异常客户信息的异常任务id
     * @param customerJYs
     * @author tangxing
     * @date 2016-6-13上午11:48:23
     */
    public void updateExceSettCustomer(List<ErpSettlementCustomerJY> customerJYs){
    	for (ErpSettlementCustomerJY erpSettlementCustomerJY : customerJYs) {
    		settlementTaskDao.update(erpSettlementCustomerJY);
		}
    }
    
    /**
     * id的List<Map>集合，转换为String
     * @param listMap
     * @return
     * @author tangxing
     * @date 2016-6-13下午1:33:53
     */
    public String formatMap(List<Map<String, Object>> listMap){
    	String s = "";
    	String valueStr = "";
    	if(listMap.size()!=0){
    		for (Map<String, Object> map : listMap) {
       		 Iterator it = map.entrySet().iterator();
       		  while (it.hasNext()) {
       		   Map.Entry entry = (Map.Entry) it.next();
//       		   Object key = entry.getKey();
       		   Object value = entry.getValue();
       		   s = "'"+value.toString()+"',";
       		   valueStr = valueStr + s;
       		  }
    		}
    		valueStr = valueStr.substring(0,valueStr.length()-1);
    	}
    	return valueStr;
    }
    
    public List<ErpSettlementCustomerJY> getSettleCustomerBySettIdStatus(String settleTaskId){
    	return erpSettlementCustomerJYDao.getSettleCustomerBySettIdStatus(settleTaskId);
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
    public String createExSeFilePath(HttpServletRequest request,List<ErpSettlementCustomerJY> customerJYs,String settleExceTaskNoTemp,String flag){
    	String haveYmComboStatus = "haveYmCombo";		//用于判断导出 是否增加远盟系统套餐
    	StringBuffer url = request.getRequestURL();	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
		
		String settleExceTaskNo = "";
    	
		if(flag==null&&StringUtils.isEmpty(flag)){
			settleExceTaskNo = settleExceTaskNoTemp;
		}else{
			settleExceTaskNo = settleExceTaskNoTemp+"_"+flag;
		}
		
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
//		String batchFilePath = rootPath.toString()+timePath+"_"+settleExceTaskNo+File.separator;
		String curFilePath = localPath+timePath+"_"+settleExceTaskNoTemp+File.separator;//存放位置
		String str = timePath+"_"+settleExceTaskNoTemp+File.separator;
    	
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdf(customerJYs,haveYmComboStatus);
		String excleName = settleExceTaskNo+".xls";			//文件名
		createExSettleXls(curFilePath.toString(),excleName,rowList);
		
		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return downloadurl;
	}
    
    /**
     * 导出匹配成功和套餐不匹配的数据
     * @param request
     * @param customerJYs
     * @param settleExceTaskNoTemp
     * @param flag
     * @return
     * @author tangxing
     * @date 2016-11-10上午10:55:19
     */
    public String createExSeFilePathForSuccess(HttpServletRequest request,List<ErpSettlementCustomerJY> customerJYs,List<ErpSettlementCustomerJY> customerJYsTwo,String settleExceTaskNoTemp){
    	String haveYmComboStatus = "";				//用于判断导出 是否增加远盟系统套餐（为空不增加导出远盟系统套餐）
    	StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
		
		String settleExceTaskNo = settleExceTaskNoTemp+"_"+"successAndComboMatch";
		
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		StringBuilder rootPath = new StringBuilder(localPath + timePath + File.separator);
//		String batchFilePath = rootPath.toString()+timePath+"_"+settleExceTaskNo+File.separator;
		String curFilePath = localPath+timePath+"_"+settleExceTaskNoTemp+File.separator;//存放位置
		String str = timePath+"_"+settleExceTaskNoTemp+File.separator;
    	
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdf(customerJYs,haveYmComboStatus);
		List<List<String>> rowListTwo = buildExcelRowByPdfTwo(customerJYsTwo);
		
		String excleName = settleExceTaskNo+".xls";			//文件名
		this.createExSettleXlsTwo(curFilePath.toString(),excleName,rowList,rowListTwo);
		
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
  	public List<List<String>> buildExcelRowByPdf(List<ErpSettlementCustomerJY> customerJYs,String haveYmComboStatus){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<customerJYs.size();i++){
  			ErpSettlementCustomerJY content = customerJYs.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getCode());//条码
  			list.add(content.getName());//姓名
  			list.add(content.getSex());//性别
  			list.add(content.getAge());//年龄
  			list.add(content.getContact());//联系方式
  			list.add(content.getBranch_company());//支公司
  			list.add(content.getItems());//检查项目
  			list.add(content.getSample_type());//样本类型
  			list.add(content.getCensorship_doctor());//送检医生
  			list.add(content.getCensorship_company());//送检单位
  			list.add(content.getSubmitter());//提交者
  			if(content.getCensorship_time()!=null){
  				list.add(sdf.format(content.getCensorship_time()));	//送检日期
  			}
  			if(content.getReceive_time()!=null){
  				list.add(sdf.format(content.getReceive_time()));	//接收日期
  			}
  			
  			list.add(content.getImport_status());	//导入状态
  			list.add(content.getReport_name());		//报告名称
  			String statusStr = content.getStatus();
  			String statusTemp = "";
  			if(statusStr!=null&&!StringUtils.isEmpty(statusStr)){
  				if(statusStr.equals("0")){
  					statusTemp="可结算";
  				}else if(statusStr.equals("1")){
  					statusTemp="待结算";
  				}else if(statusStr.equals("2")){
  					statusTemp="已结算";
  				}
  			}
  			list.add(statusTemp);				//结算状态
  			
  			if(StringUtils.isNotEmpty(haveYmComboStatus)){		//异常数据导出，需要加套餐名
  				list.add(content.getCustomerComboName());		//远盟系统检测项目
  			}
  			
  			/*List<CustomerRelationShip> relationShip = relService.findByCustomer(content.getBranch_company());
  			if(relationShip.size()==0){
  				list.add(content.getBranch_company());//支公司
  			}else{
  				list.add(relationShip.get(0).getBranchCommany());//支公司
  			}*/
  			
  			//list.add(cusService.findEvtBthnoById(content.getCustomerid()));//20160517增加批次号
  			result.add(list);
  		}
  		return result;
  	}
  	
  	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRowByPdfTwo(List<ErpSettlementCustomerJY> customerJYs){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<customerJYs.size();i++){
  			ErpSettlementCustomerJY content = customerJYs.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getCode());//条码
  			list.add(content.getName());//姓名
  			list.add(content.getSex());//性别
  			list.add(content.getAge());//年龄
  			list.add(content.getContact());//联系方式
  			list.add(content.getBranch_company());//支公司
  			list.add(content.getItems());//检查项目
  			list.add(content.getCustomerComboName());//远盟套餐名
  			list.add(content.getSample_type());//样本类型
  			list.add(content.getCensorship_doctor());//送检医生
  			list.add(content.getCensorship_company());//送检单位
  			list.add(content.getSubmitter());//提交者
  			if(content.getCensorship_time()!=null){
  				list.add(sdf.format(content.getCensorship_time()));	//送检日期
  			}
  			if(content.getReceive_time()!=null){
  				list.add(sdf.format(content.getReceive_time()));	//接收日期
  			}
  			
  			list.add(content.getImport_status());	//导入状态
  			list.add(content.getReport_name());		//报告名称
  			
  			/*List<CustomerRelationShip> relationShip = relService.findByCustomer(content.getBranch_company());
  			if(relationShip.size()==0){
  				list.add(content.getBranch_company());//支公司
  			}else{
  				list.add(relationShip.get(0).getBranchCommany());//支公司
  			}*/
  			
  			//list.add(cusService.findEvtBthnoById(content.getCustomerid()));//20160517增加批次号
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
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("匹配成功");
			
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
			cell1.setCellValue("条码");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("姓名");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("性别");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("年龄");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("联系方式");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("家族病史");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("检查项目");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("样本类型");
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("送检医生");
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("送检机构");
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("提交者");
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("送检日期");
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("收样日期");
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("状态");
			Cell cell15 = row0.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("报告");
			Cell cell16 = row0.createCell(16);
			cell16.setCellStyle(cellStyle);
			cell16.setCellValue("结算状态");
			Cell cell2_17 = row0.createCell(17);
			cell2_17.setCellStyle(cellStyle);
			cell2_17.setCellValue("远盟系统套餐");
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
  	 * 生成两个sheet的excel
  	 * @param path
  	 * @param filename
  	 * @param rowList
  	 * @param rowListTwo
  	 * @author tangxing
  	 * @date 2016-11-10上午10:53:29
  	 */
  	public void createExSettleXlsTwo(String path ,String filename, List<List<String>> rowList,List<List<String>> rowListTwo) {
		
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("匹配成功");
			
			// 工作表2
			HSSFSheet hssfSheet2=hssfWorkbook.createSheet("套餐不匹配");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			HSSFRow row2 = hssfSheet2.createRow(0);
			
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
			cell1.setCellValue("条码");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("姓名");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("性别");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("年龄");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("联系方式");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("家族病史");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("检查项目");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("样本类型");
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("送检医生");
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("送检机构");
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("提交者");
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("送检日期");
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("收样日期");
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("状态");
			Cell cell15 = row0.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("报告");
			Cell cell16 = row0.createCell(16);
			cell16.setCellStyle(cellStyle);
			cell16.setCellValue("结算状态");
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
			}
			/* ***sheet2*** */
			Cell cell2_0 = row2.createCell(0);
			cell2_0.setCellStyle(cellStyle);
			cell2_0.setCellValue("序号");
			Cell cell2_1 = row2.createCell(1);
			cell2_1.setCellStyle(cellStyle);
			cell2_1.setCellValue("条码");
			Cell cell2_2 = row2.createCell(2);
			cell2_2.setCellStyle(cellStyle);
			cell2_2.setCellValue("姓名");
			Cell cell2_3 = row2.createCell(3);
			cell2_3.setCellStyle(cellStyle);
			cell2_3.setCellValue("性别");
			Cell cell2_4 = row2.createCell(4);
			cell2_4.setCellStyle(cellStyle);
			cell2_4.setCellValue("年龄");
			Cell cell2_5 = row2.createCell(5);
			cell2_5.setCellStyle(cellStyle);
			cell2_5.setCellValue("联系方式");
			Cell cell2_6 = row2.createCell(6);
			cell2_6.setCellStyle(cellStyle);
			cell2_6.setCellValue("家族病史");
			Cell cell2_7 = row2.createCell(7);
			cell2_7.setCellStyle(cellStyle);
			cell2_7.setCellValue("检查项目");
			Cell cell2_8 = row2.createCell(8);
			cell2_8.setCellStyle(cellStyle);
			cell2_8.setCellValue("远盟套餐名");
			Cell cell2_9 = row2.createCell(9);
			cell2_9.setCellStyle(cellStyle);
			cell2_9.setCellValue("样本类型");
			Cell cell2_10 = row2.createCell(10);
			cell2_10.setCellStyle(cellStyle);
			cell2_10.setCellValue("送检医生");
			Cell cell2_11 = row2.createCell(11);
			cell2_11.setCellStyle(cellStyle);
			cell2_11.setCellValue("送检机构");
			Cell cell2_12 = row2.createCell(12);
			cell2_12.setCellStyle(cellStyle);
			cell2_12.setCellValue("提交者");
			Cell cell2_13 = row2.createCell(13);
			cell2_13.setCellStyle(cellStyle);
			cell2_13.setCellValue("送检日期");
			Cell cell2_14 = row2.createCell(14);
			cell2_14.setCellStyle(cellStyle);
			cell2_14.setCellValue("收样日期");
			Cell cell2_15 = row2.createCell(15);
			cell2_15.setCellStyle(cellStyle);
			cell2_15.setCellValue("状态");
			Cell cell2_16 = row2.createCell(16);
			cell2_16.setCellStyle(cellStyle);
			cell2_16.setCellValue("报告");
			Cell cell2_17 = row2.createCell(17);
			cell2_17.setCellStyle(cellStyle);
			cell2_17.setCellValue("远盟系统套餐");
			for(int rowNum=0;rowNum<rowListTwo.size();rowNum++){
				HSSFRow row1 = hssfSheet2.createRow(rowNum+1);
				List<String> row=rowListTwo.get(rowNum);
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
	
	/* ******存储过程****** */
	public void comparJYcustomer(String settlementId) throws SQLException{
		settlementTaskDao.comparJYcustomer(settlementId);
	}
	
	public boolean saveSettleTaskByCustomer(String settlementId,String currentName) throws SQLException{
		boolean flag = false;
		flag = settlementTaskDao.saveSettleTaskByCustomer(settlementId, currentName);
		return flag;
	}
	
	public boolean createExceSettleTask(String settlementId) throws SQLException{
		boolean flag = false;
		String s = "JSYC";
		String defTaskNum = defTaskNum();
		int ranNum = ranNum();
		String exceSettleTaskNo = s+defTaskNum+ranNum;					//异常结算任务号
		
		//到新增页面时生成ID
		String id = UUID.randomUUID().toString().replace("-", "");
		
		flag = settlementTaskDao.createExceSettleTask(settlementId,id,exceSettleTaskNo);
		return flag;
	}
	
	//更改状态
	public void updateStateSettleTask(String state,String id){
		settlementTaskDao.updateStateSettleTask(state, id);
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
