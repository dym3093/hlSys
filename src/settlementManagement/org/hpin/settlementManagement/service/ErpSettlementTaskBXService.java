/**
 * dym
 * 2016-4-26 下午2:45:48
 */
package org.hpin.settlementManagement.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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
import org.hpin.base.dict.util.Constants;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.PreciseCompute;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.service.ErpEventsService;
import org.hpin.reportdetail.dao.ErpReportdetailPDFContentDao;
import org.hpin.settlementManagement.dao.*;
import org.hpin.settlementManagement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 保险公司结算service
 * @author DengYouming
 * @since 2016-5-1 下午4:27:44
 */
@Service(value = "org.hpin.settlementManagement.service.ErpSettlementTaskBXService")
@Transactional()
public class ErpSettlementTaskBXService extends BaseService{
	private Logger log = Logger.getLogger(ErpSettlementTaskBXService.class);
	
	@Autowired
	private ErpSettlementTaskBXDao dao;
	
	@Autowired
	ErpCustomerDao erpCustomerDao;

	@Autowired
	ErpReportdetailPDFContentDao contentDao;
	
	@Autowired
	ErpEventsDao erpEventsDao;
	
	@Autowired
	ErpEventsService erpEventsService;
	
	@Autowired
	ErpEventsComboPriceDao erpEventsComboPriceDao;
	
	@Autowired
	ErpCustomerSettleBXDao erpCustomerSettleBXDao;
	
	@Autowired
	ErpCompanyComboPriceDao erpCompanyComboPriceDao;
	
	@Autowired
	ErpBankDao erpBankDao ; //银行dao
	
	@Autowired
	ErpProceedsDao erpProceedsDao; //收款dao

	@Autowired
    ErpSettlementIncomeBXDao incomeBXDao; //其他收入的DAO

	/**
	 * 保存场地场次号和套餐对应的数据;修改人员套餐;
	 */
	public void saveOrUpdateEventsTask() {
		
		//参数处理
		String id = HttpTool.getParameter("id");
		User currentUser=(User)HttpTool.getSession().getAttribute("currentUser");
		
		//实体对象
		ErpEventsComboPrice comboPr = new ErpEventsComboPrice();
		if(StringUtils.isEmpty(id)) {//保存
			comboPr.setCreateDeptId(currentUser.getDeptId());
			comboPr.setCreateTime(new Date());
			comboPr.setCreateUserId(currentUser.getId());
			comboPr.setCreateUser(currentUser.getUserName());
			dealErpEventsTask(comboPr);
			erpEventsComboPriceDao.save(comboPr);
		} else { //修改
			comboPr.setId(id);
			comboPr = erpEventsComboPriceDao.getHibernateTemplate().get(ErpEventsComboPrice.class, id);
			if(comboPr != null) {
				dealErpEventsTask(comboPr);
				comboPr.setUpdateTime(new Date());
				comboPr.setUpdateUser(currentUser.getUserName());
				comboPr.setUpdateUserId(currentUser.getId());
				erpEventsComboPriceDao.update(comboPr);
			}
		}
		
		//处理其他业务;修改其他数据;
		try {
			List<Object> objs = new ArrayList<Object>();
			String discount = HttpTool.getParameter("discount", "0.00");	
			String comboPrice = HttpTool.getParameter("comboPrice", "0.00"); //价格
			BigDecimal disc = PreciseCompute.div(PreciseCompute.formatComma2BigDecimal(discount, 2), new BigDecimal(100), 4);
			BigDecimal acutlPrice = PreciseCompute.mul(PreciseCompute.formatComma2BigDecimal(comboPrice, 2), disc);
			objs.add(acutlPrice); //实际价格;
			objs.add(comboPr.getRemark());
			objs.add(comboPr.getDiscount());
			objs.add(currentUser.getId());
			objs.add(currentUser.getUserName());
			objs.add(new Date());
			String settlementTaskId = HttpTool.getParameter("settlementTaskId");
			objs.add(settlementTaskId);
			objs.add(comboPr.getEventsNo());
			objs.add(comboPr.getCombo());
			erpCustomerSettleBXDao.updateCustomerTaskPrice(objs.toArray());

			//更新结算任务主表信息 add by Damian 2017-03-16 begin
			this.addEventsCustomersToSettle(id, null, currentUser.getUserName(), currentUser.getId());
			//更新结算任务主表信息 add by Damian 2017-03-16 end
		}catch (Exception e) {
			log.error("保存场地场次号和套餐对应的数据错误!ErpSettlementTaskBXService.saveOrUpdateEventsTask", e);
		}
		
	}

	private void dealErpEventsTask(ErpEventsComboPrice comboPr) {
		String comboId = HttpTool.getParameter("comboId");
		String combo = HttpTool.getParameter("combo");
		String comboPrice = HttpTool.getParameter("comboPrice"); //价格
		String eventsNo = HttpTool.getParameter("eventsNo");
		String remark = HttpTool.getParameter("remark");
		String discount = HttpTool.getParameter("discount");	
		comboPr.setRemark(remark);
		comboPr.setCombo(combo);
		comboPr.setComboId(comboId);
		comboPr.setComboPrice(PreciseCompute.formatComma2BigDecimal(comboPrice, 2));
		comboPr.setDiscount(PreciseCompute.formatComma2BigDecimal(discount, 2));
		comboPr.setEventsNo(eventsNo);
	}
	
	@SuppressWarnings("rawtypes") 
	public void findEvntsComboBySettleIdPage(Page page, Map<String, Object> search) {
		String settleId = (String) search.get("settlementTaskId");
		
		if(StringUtils.isNotEmpty(settleId)) {
			List<Object> objs = new ArrayList<Object>();
			
			//参数;
			objs.add(-1);//status
			objs.add(settleId); //结算任务ID
			
			StringBuilder commonSql = new StringBuilder("select "); //主要查询语句;
			
			//要查询的字段;
			commonSql.append("t_bx.events_no eventNo, ");
			commonSql.append("t_bx.combo combo, ");
			commonSql.append("t_bx.combo_id comboId, ");
			commonSql.append("(select count(1) from erp_customer_settle_bx where settle_id = '"+settleId+"' and status!=-1 and combo=t_bx.combo and events_no =  t_bx.events_no ) userNum, ");
			commonSql.append("price.id id, ");
			commonSql.append("price.REMARK remark, ");
			commonSql.append("price.DISCOUNT discount, ");
			commonSql.append("t_bx.COMBO_PRICE comboPrice ");
			
			commonSql.append("from ( select ");
			commonSql.append("settleBx.events_no, ");
			commonSql.append("settleBx.combo_id, ");
			commonSql.append("settleBx.COMBO_PRICE, ");
			commonSql.append("settleBx.combo ");
			commonSql.append("from erp_customer_settle_bx settleBx where ");
			commonSql.append("rowid = (select max(rowid) from erp_customer_settle_bx setbx where ");
			
			commonSql.append("settleBx.events_no = setbx.events_no ");
			commonSql.append("and settleBx.combo=setbx.combo ");
			commonSql.append("and setbx.status != ? ");
			commonSql.append("and setbx.settle_id = ?)) t_bx ");
			
			commonSql.append("left join erp_events_combo_price price ");
			commonSql.append("on price.events_no = t_bx.events_no ");
			commonSql.append("and t_bx.combo = price.COMBO");
			
			//查询count;
			Long countLong = dao.findJdbcCount(commonSql, objs);
			page.setTotalCount(countLong);
			
			//分页参数; 放在最后;注意顺序;
			objs.add(page.getPageNumEndCount());
			objs.add(page.getPageNumStartCount());
			//集合查询
			@SuppressWarnings("unchecked")
			List<ErpEventsComboPrice> lists = (List<ErpEventsComboPrice>) dao.findJdbcList(commonSql, objs, new ErpEventsComboPriceRowMapper());
			
			page.setResults(lists);
		}
	}
	
	/**
	 * 处理参数;
	 * @author Henry
	 *
	 */
	class ErpEventsComboPriceRowMapper implements RowMapper<ErpEventsComboPrice> {  
		  
        @Override  
        public ErpEventsComboPrice mapRow(ResultSet rs, int rowNum) throws SQLException {  
        	ErpEventsComboPrice etComPrice = new ErpEventsComboPrice();
        	etComPrice.setId(rs.getString("id"));
        	etComPrice.setCombo(rs.getString("combo"));
        	etComPrice.setComboId(rs.getString("comboId"));
        	etComPrice.setComboPrice(PreciseCompute.formatComma2BigDecimal(rs.getString("comboPrice"), 2));
        	etComPrice.setEventsNo(rs.getString("eventNo"));
        	etComPrice.setRemark(rs.getString("remark"));
        	etComPrice.setDiscount(PreciseCompute.formatComma2BigDecimal(rs.getString("discount"),2));
        	etComPrice.setUserNum(rs.getString("userNum"));
        	
            return etComPrice;  
        }
          
    }  
	
	/**
	 * 通过id查询会员附表信息;
	 * create by henry.xu 20160830
	 * @param id
	 * @return
	 */
	public ErpCustomerSettleBX findErpCusSettleById(String id) {
		return (ErpCustomerSettleBX) erpCustomerSettleBXDao.findById(ErpCustomerSettleBX.class, id);
	}
	

	/**
	 * 根据Id修改对应的会员信息数据;
	 * create by henry.xu 20160830
	 */
	public void updateCustomerTask() {
		
		//参数处理;
		
		List<Object> objs = new ArrayList<Object>();
		//当前用户
		User currentUser=(User)HttpTool.getSession().getAttribute("currentUser");
		String comboPriceActual = HttpTool.getParameter("comboPriceActual"); //实际价格;
		BigDecimal cpa = new BigDecimal(0.00);
		if(StringUtils.isNotEmpty(comboPriceActual)) {
			cpa = PreciseCompute.formatComma2BigDecimal(comboPriceActual, 2);
		}
		objs.add(cpa);
		objs.add(HttpTool.getParameter("remark"));
		objs.add(currentUser.getId());
		objs.add(currentUser.getUserName());
		objs.add(new Date());
		objs.add(HttpTool.getParameter("id"));
		
		//执行修改;
		erpCustomerSettleBXDao.updateCustomerTask(objs.toArray());
		
	}
	
	/**
	 * 根据参数修改计算任务转态;
	 * create by henry.xu 20160829
	 * @param params (status结算状态0未收款1代收款2已收款, )
	 * @throws Exception
	 */
	public void updateSettlementTaskStatus(Map<String, Object> params) throws Exception {
		dao.updateSettlementTaskStatus(params);
	}
	
	/**
	 * 根据id查找数据;
	 * create by henry.xu 20160829
	 * @param id
	 * @return
	 */
	public ErpProceeds findErpProceedsById(String id) {
		return erpProceedsDao.findById(id);
	}
	
	/**
	 * 根据结算任务id查找收款;
	 * 一个结算id对应一个收款数据;
	 * create by henry.xu 20160829
	 * @param settleId
	 * @return
	 */
	public ErpProceeds findErpProceedsBySettleId(String settleId) {
		return erpProceedsDao.findBySettlementId(settleId);
	}
	
	
	/**
	 * 保存收款数据;
	 * create by henry.xu 20160829
	 */
	public void saveOrUpdateProceeds() throws Exception{
		ErpProceeds proceeds;
		//当前用户
		User currentUser=(User)HttpTool.getSession().getAttribute("currentUser");
		
		//获取收款数据Id; 如果存在则修改;
		String id = HttpTool.getParameter("id");
		if(StringUtils.isEmpty(id)) {
			proceeds = new ErpProceeds();
			//数据对象处理;
			
			proceeds.setCreateTime(new Date());
			proceeds.setCreateUser(currentUser.getId());
			proceeds.setIsDelete("0");
			proceeds.setProcMethod(HttpTool.getParameter("procMethod"));
			
			proceeds.setProLeader(HttpTool.getParameter("proLeader"));
			String settlemenetId = HttpTool.getParameter("settlementId");
			proceeds.setSettlementId(settlemenetId);
			
			//数据对象处理;
			dealErpProccedsEntity(proceeds);
			
			//保存,只有保存的时候才会执行修改结算任务的状态;
			erpProceedsDao.save(proceeds);
			
			//保存后, 修改结算任务的付款状态为已付款; 0未收款1代收款2已收款
			//更新子表状态 add by Damian 2017-03-12 start
			Page page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			Map searchMap = new HashMap();
			searchMap.put("filter_and_settleId_EQ_S", settlemenetId);
			this.findErpCustomerSettleBXByPage(page, searchMap);
			List<ErpCustomerSettleBX> erpCustomerSettleBXs = page.getResults();
			if(erpCustomerSettleBXs!=null&&erpCustomerSettleBXs.size()>0){
				for (int j = 0; j < erpCustomerSettleBXs.size(); j++) {
				    //更新为已收款状态
					erpCustomerSettleBXs.get(j).setStatus("2");
				}
				//更新状态
				erpCustomerSettleBXDao.saveOrUpdateList(erpCustomerSettleBXs);
			}
			//更新子表状态 add by Damian 2017-03-12 end

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "2");
			params.put("settlementId", settlemenetId);
			updateSettlementTaskStatus(params);
		} else {
			proceeds = findErpProceedsById(id); //根据id查找数据;
			proceeds.setModifiedTime(new Date());
			proceeds.setModifiedUser(currentUser.getId());
			
			//数据对象处理;
			dealErpProccedsEntity(proceeds);
			
			//修改
			erpProceedsDao.update(proceeds);
		}
	}

	/**
	 * 处理实体对象;
	 * create by henry.xu 20160829
	 * @param proceeds
	 * @throws ParseException
	 */
	private void dealErpProccedsEntity(ErpProceeds proceeds)
			throws ParseException {
		//字符数组拼接处理;
		StringBuilder bankn = new StringBuilder("");
		String[] bankNums = HttpTool.getParameterValues("bankNum");
		int leg = bankNums.length;
		if(bankNums != null && leg > 0) {
			for (int i=0; i< leg; i++) {
				if(StringUtils.isEmpty(bankNums[i])) {
					continue;
				}
				if("".equals(bankn.toString())){
					bankn.append(bankNums[i]);					
				} else {
					bankn.append(",");
					bankn.append(bankNums[i]);
				}
			}
		}
		proceeds.setBankNum(bankn.toString()); //银行流水号
		
		String financialCost = HttpTool.getParameter("financialCost");
		proceeds.setFinancialCost(PreciseCompute.formatComma2BigDecimal(financialCost, 2)); //财务金额
		String netAmount = HttpTool.getParameter("netAmount");
		proceeds.setNetAmount(PreciseCompute.formatComma2BigDecimal(netAmount, 2)); //入账金额
		proceeds.setPostExplain(HttpTool.getParameter("postExplain")); //入账说明
		proceeds.setProcBankId(HttpTool.getParameter("procBankId"));//收款银行ID
		String procTime = HttpTool.getParameter("procTime"); //入账时间
		proceeds.setProcTime(DateUtil.convertStringToDate("yyyy-MM-dd", procTime));
		proceeds.setBranchId(HttpTool.getParameter("branchId"));
		proceeds.setOwnedCompany(HttpTool.getParameter("ownedCompany"));
	}
	
	/**
	 * 查询所有没有删除的银行信心集合;
	 * create by henry.xu 20160826
	 * @return
	 */
	public List<ErpBank> findAllErpBanks() {
		return erpBankDao.findAllErpBanks();
	}
	
	/**
	 * 保险公司结算任务提交收款
	 * @param params
	 * @return boolean
	 * @author DengYouming
	 * @since 2016-5-1 下午6:46:31
	 */
	public boolean confirmErpSettlementTask(Map<String, Object> params) throws Exception{
		boolean flag = false;
		//从页面传过来的状态值
		String status = (String) params.get(ErpCustomerSettleBX.F_STATUS);
		params.remove(ErpCustomerSettleBX.F_STATUS);
		//查询结算任务
		List<ErpSettlementTaskBX> list = dao.listErpSettlementTaskBX(params);
		List<ErpSettlementTaskBX> tempList;
		
		if(list!=null&&list.size()>0){
			tempList = new ArrayList<ErpSettlementTaskBX>();
			ErpSettlementTaskBX entity;
			List<ErpCustomerSettleBX> erpCustomerSettleBXs;
			for (int i = 0; i < list.size(); i++) {
				entity = list.get(i);
				Page page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
				Map searchMap = new HashMap();
				searchMap.put("filter_and_settleId_EQ_S", entity.getId());
				this.findErpCustomerSettleBXByPage(page, searchMap);
				erpCustomerSettleBXs = page.getResults();
				if(erpCustomerSettleBXs!=null&&erpCustomerSettleBXs.size()>0){
					for (int j = 0; j < erpCustomerSettleBXs.size(); j++) {
						erpCustomerSettleBXs.get(j).setStatus(status);
					}
					//更新状态
					erpCustomerSettleBXDao.saveOrUpdateList(erpCustomerSettleBXs);
				}
				entity.setStatus(status);
				tempList.add(entity);
			}
			dao.saveSettlementTaskList(tempList);
			flag = true;
		}
		return flag;
	}

	/**
	 * 保存保险公司结算任务
	 * @param params
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-1 下午5:50:22
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveErpSettlementTask(Map<String, Object> params) throws Exception {
		//场次的ID
		String ids = (String)params.get("ids");
		//操作类型
		String type = (String)params.get("type");
		//获取结算任务
		ErpSettlementTaskBX bx = (ErpSettlementTaskBX) params.get(ErpSettlementTaskBX.F_ERPSETTLEMENTTASKBX);
		 //获取当前用户
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");

		//重新计算入库
        bx = this.updateTaskInfo(bx, bx.getId(), null, currentUser);
		//查询是否已存在该结算
		ErpSettlementTaskBX prevObj = (ErpSettlementTaskBX) dao.findById(ErpSettlementTaskBX.class, bx.getId());
		//变更收款
		if("toChangeAmount".equals(type)){
			//应收款
			prevObj.setActualTotalAmount(bx.getActualTotalAmount());
			prevObj.setRemark(bx.getRemark());
			
		}else{
			if(prevObj!=null){
			    prevObj.setInvoice(bx.getInvoice());
				prevObj.setTaskName(bx.getTaskName());
				prevObj.setProjectNo(bx.getProjectNo());
				prevObj.setPaymentType(bx.getPaymentType());
			    prevObj.setRemark(bx.getRemark());

				prevObj.setUpdateTime(new Date());
				prevObj.setUpdateUser(currentUser.getUserName());
				prevObj.setUpdateUserId(currentUser.getId());


			}else{
			    prevObj = bx;
				//设置基础信息
				prevObj.setCreateTime(new Date());
				prevObj.setCreateUser(currentUser.getUserName());
				prevObj.setCreateUserId(currentUser.getId());
				prevObj.setSalesManYM(currentUser.getUserName());
				prevObj.setSalesManIdYM(currentUser.getId());
			}
//			if(prevObj!=null){
//				bx.setUpdateTime(new Date());
//				bx.setUpdateUser(currentUser.getUserName());
//				bx.setUpdateUserId(currentUser.getId());
//
//				bx.setCreateTime(prevObj.getCreateTime());
//				bx.setCreateUser(prevObj.getCreateUser());
//				bx.setCreateUserId(prevObj.getCreateUserId());
//				bx.setSalesManYM(prevObj.getSalesManYM());
//				bx.setSalesManIdYM(prevObj.getSalesManIdYM());
//			}else{
//				//设置基础信息
//				bx.setCreateTime(new Date());
//				bx.setCreateUser(currentUser.getUserName());
//				bx.setCreateUserId(currentUser.getId());
//				bx.setSalesManYM(currentUser.getUserName());
//				bx.setSalesManIdYM(currentUser.getId());
//			}
		}

		/**
		List<ErpEvents> eventsList = new ArrayList<ErpEvents>();		
		ErpEvents erpEvents;
		
		//根据ID获取场次
		if(StringUtils.isNotEmpty(ids)){
			//多个场次ID以逗号分割
			if(ids.indexOf(",")!=-1){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					erpEvents = (ErpEvents) erpEventsDao.findById(ErpEvents.class, idArr[i]);
					if(null!=erpEvents){
						//根据场次获取其下的会员信息
						//TODO 未判别是否匹配基因报告
						List<ErpCustomer> erpCustomerList = 
								(List<ErpCustomer>) erpCustomerDao.findByProperty(ErpCustomer.class, "EVENTS_NO", erpEvents.getEventsNo(), null, null);
						for (ErpCustomer entity : erpCustomerList) {
							//修改会员记录状态,设置为已结算状态
							entity.setStatus("2");
							//更新数据库
							erpCustomerDao.saveOrUpdate(entity);
						}
						erpEventsDao.saveOrUpdate(erpEvents);
						eventsList.add(erpEvents);
					}
				}
			}else{
				//单个场次的结算 
				erpEvents = (ErpEvents) erpEventsDao.findById(ErpEvents.class, ids);
				if(null!=erpEvents){
					//根据场次获取其下的会员信息
					//TODO 未判别是否匹配基因报告
					List<ErpCustomer> erpCustomerList = 
							(List<ErpCustomer>) erpCustomerDao.findByProperty(ErpCustomer.class, "EVENTS_NO", erpEvents.getEventsNo(), null, null);
					for (ErpCustomer entity : erpCustomerList) {
						//修改会员记录状态,设置为已结算状态
						entity.setStatus("2");
						//更新数据库
						erpCustomerDao.saveOrUpdate(entity);
					}
					//更新场次信息
					erpEventsDao.saveOrUpdate(erpEvents);
					eventsList.add(erpEvents);
				}
			}
		}
		*/
		//结算任务的状态,无状态时默认设置为0
		if(StringUtils.isEmpty(bx.getStatus())){
			bx.setStatus("0");
		}
		//去掉空格
		//bx.setRemark(bx.getRemark().trim());
		//变更收款的保存
		dao.saveSettlementTask(prevObj);
//		if("toChangeAmount".equals(type)){
//			dao.saveSettlementTask(prevObj);
//		}else{
//			dao.saveSettlementTask(bx);
//		}
	}

	/**
	 * 根据场次添加会员到结算任务
	 * @param settleId
	 * @param info 
	 * @param userName
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-30 上午10:19:24
	 */
	public Map<String,String> addEventsCustomersToSettle(String settleId, String info, String userName, String userId) throws Exception{
		Map<String,String> result = null;
		if(StringUtils.isNotEmpty(settleId)&&StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(userId)){
			result = dao.dealSettleBX(settleId, info, userName, userId);
		}
		return result;
	}

	/**
	 * 根据条件查找ErpSettlementTaskBX
	 * @param params
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-9 下午4:33:44
	 */
	public List<ErpSettlementTaskBX> listErpSettlementTaskBX(Map<String,Object> params) throws Exception{
		return dao.listErpSettlementTaskBX(params);
	}
	
	/**
	 * 批量删除保险公司结算任务,更新相关的场次记录状态和相关的会员表记录状态
	 * @param params
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-1 下午6:47:03
	 */
	public void delErpSettlementTaskBatch(Map<String, Object> params) throws Exception {
		User user = (User) HttpTool.getSession().getAttribute("currentUser");
		String userName = user.getUserName();
		String ids = (String)params.get(ErpSettlementTaskBX.F_ID);
		ErpSettlementTaskBX entity;

		if(ids.indexOf(Constants.PUNCTUATION_COMMA)!=-1){
			String[] idArr = ids.split(",");
			
			if(idArr.length>0){				
				String id;
				for (int i = 0; i < idArr.length; i++) {
					id = idArr[i];
					//存储过程执行更新 erp_customer_settle_bx, erp_customer, erp_events表状态
					this.cancelTask(id,userName);
					//逻辑删除
					entity = (ErpSettlementTaskBX) dao.findById(ErpSettlementTaskBX.class, id);
					if(entity!=null){
						entity.setStatus("-1");
						dao.update(entity);
					}
				}
			}
		}else{
			//存储过程执行更新 erp_customer_settle_bx, erp_customer, erp_events表状态
			this.cancelTask(ids,userName);
			//逻辑删除
			entity = (ErpSettlementTaskBX) dao.findById(ErpSettlementTaskBX.class, ids);
			if(entity!=null){
				entity.setStatus("-1");
				dao.update(entity);
			}
			
		}
	}

	/**
	 * 根据条件查询对于结算任务列表
	 * @param page
	 * @param searchMap
	 * @return List<ErpSettlementTaskBX> 
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-4-27 上午8:52:25
	 */
	public List<ErpSettlementTaskBX> findByPage(Page page, Map searchMap) {
		List<ErpSettlementTaskBX> list = dao.findByPage(page, searchMap);
		return list;
	}
	
	 /**
	  * 复制ErpCustomer到ErpCustomerSettleBX
	  * @param list
	  * @return List
	  * @author DengYouming
	  * @since 2016-5-8 上午11:28:35
	  */
	 private List<ErpCustomerSettleBX> converErpCustomer(List<ErpCustomer> list){
		 List<ErpCustomerSettleBX> erpCustomerSettleBXList = new ArrayList<ErpCustomerSettleBX>();
		 ErpCustomerSettleBX entity = null;
		 for (int i = 0; i < list.size(); i++) {
			ErpCustomer obj = list.get(i);
		 	entity = new ErpCustomerSettleBX();
		 	
		 	//保存Erp_customer表的ID
		 	entity.setCustomerId(obj.getId());
		 	
		 	//套餐名称
		 	entity.setCombo(obj.getSetmealName());
		 	
		 	entity.setEventsNo(obj.getEventsNo());
			entity.setCode(obj.getCode());
			entity.setName(obj.getName());
			entity.setPhone(obj.getPhone());
			entity.setDocumentType(obj.getDocumentType());
			
			entity.setIdno(obj.getIdno()); 
			entity.setSex(obj.getSex());
			entity.setAge(obj.getAge());
			entity.setCompany(obj.getBranchCompany());
			entity.setCompanyId(obj.getBranchCompanyId());
			
			entity.setOwnedCompany(obj.getOwnedCompany());
			entity.setOwnedCompanyId(obj.getOwnedCompanyId());
			entity.setSalesMan(obj.getSalesMan());
			entity.setSampleType(obj.getSampleType());
			entity.setSamplingDate(obj.getSamplingDate());
			
			entity.setFamilyHistory(obj.getFamilyHistory());
			entity.setProvice(obj.getProvice());
			entity.setCity(obj.getCity());
//			entity.setCreateTime(obj.getCreateTime());
//			entity.setCreateUserId(obj.getCreateUserId());
			
//			entity.setUpdateTime(obj.getUpdateTime());
//			entity.setUpdateUserId(obj.getUpdateUserId());
			entity.setStatus(obj.getStatus()); 
			entity.setRemark(obj.getRemark()); 
			
			//远盟对接人 add by DengYouming 2016-06-12
			entity.setSalesManYM(obj.getYmSalesman());
			//
			erpCustomerSettleBXList.add(entity);
		}
		 
		 return erpCustomerSettleBXList;
	 }
	 
	 /**
	  * 条件分页查询
	  * @param page
	  * @param filterMap
	  * @return List
	  * @throws Exception
	  * @author DengYouming
	  * @since 2016-5-10 下午3:56:35
	  */
	 public List<ErpCustomerSettleBX> findErpCustomerSettleBXByPage(Page page, Map filterMap) throws Exception{
		 return erpCustomerSettleBXDao.findByPage(page, filterMap);
	 }
	 
	 /**
	  * 根据ID查找ErpSettlementTaskBX
	  * @param id
	  * @return ErpSettlementTaskBX
	  * @throws Exception
	  * @author DengYouming
	  * @since 2016-5-10 下午4:55:59
	  */
	 public ErpSettlementTaskBX findById(String id) {
		 return (ErpSettlementTaskBX) dao.findById(ErpSettlementTaskBX.class, id);
	 }
	 
	 /**
	  * 批量移除结算任务的会员
	  * @param params 参数
	  * @return Map 结果集
	  * @throws Exception
	  * @author DengYouming
	  * @since 2016-6-14 上午10:11:16
	  */
	 public Map<String,Object> removeCustomerBatch(Map<String,Object> params) throws Exception{
		 Map<String,Object> result = null;
		 if(!CollectionUtils.isEmpty(params)){
			 String settleId = (String) params.get(ErpSettlementTaskBX.F_ID);
			 //移除的客户信息ID
			 String ids = (String) params.get("ids");
			 String[] idArr;
			 List<ErpCustomerSettleBX> customerSettleBXlist = null;
			 if(StringUtils.isNotEmpty(ids)){
				 if(ids.indexOf(",")!=-1){
					 idArr = ids.split(",");
				 }else{
					 idArr = new String[1];
					 idArr[0] = ids;
				 }
			 	customerSettleBXlist = erpCustomerSettleBXDao.listByIds(idArr);
			 }
			 
			 Set<String> eventsNoSet = new HashSet<String>(); 
			 //
			 if(!customerSettleBXlist.isEmpty()){
				 String[] idArrOther = new String[customerSettleBXlist.size()];
				//获取ERP_CUSTOMER相关的 id
				 
				 List<ErpCustomerSettleBX> tempCustomerBXList = new ArrayList<ErpCustomerSettleBX>();
				 
				 for (int i = 0; i < customerSettleBXlist.size(); i++) {
					 idArrOther[i] = customerSettleBXlist.get(i).getCustomerId();
					 customerSettleBXlist.get(i).setStatus("-1");
					 tempCustomerBXList.add(customerSettleBXlist.get(i));
					 String eNo = customerSettleBXlist.get(i).getEventsNo();
					 eventsNoSet.add(eNo);
				}
				 //更新 Erp_Customer_Settle_bx表状态
				 erpCustomerSettleBXDao.saveOrUpdateList(tempCustomerBXList);

				 Map<String,Object> props = new HashMap<String, Object>();
				 props.put(ErpCustomer.F_ID, idArrOther);
				 //根据ID查找会员信息		
				 List<ErpCustomer> customerList = erpCustomerDao.listCustomerCanAddSettle(props);
				//修改 erp_customer表相关的会员状态status
				 if(!CollectionUtils.isEmpty(customerList)){
					 for (int i = 0; i < customerList.size(); i++) {
						//更新状态
						 erpCustomerDao.updateStatus(customerList.get(i).getId(), "0");
					}
				 }
				 
				 //更新场次状态
				 Iterator<String> iter = eventsNoSet.iterator();
				 while(iter.hasNext()){
					 erpEventsDao.updateEventsStatusBX(iter.next());
				 }
			 }
			 //根据结算任务ID，重新计算此次结算的人数，应结算金额等信息
			 result = this.calculateSettle(settleId);
		 }
		 return result;
	 }
	 
	 /**
	  * 根据结算任务ID计算结算任务的总人数，应结算金额，实际结算金额（默认与应结算金额相等），套餐数量，公司数量等信息
	  * @param settleId
	  * @return Map
	  * @throws Exception
	  * @author DengYouming
	  * @since 2016-6-14 上午10:21:57
	  */
	 public Map<String,Object> calculateSettle(String settleId) throws Exception{
		 Map<String,Object> result = null;
		 if(StringUtils.isNotEmpty(settleId)){
			 result = new HashMap<String, Object>();
			 //总人数
			 Integer totalPersoNnum = 0;
			 //应结算金额
			 BigDecimal totalAmount = new BigDecimal("0");
			 //套餐数量
			 Integer comboNum = 0;
			 //公司数量
			 Integer companyNum = 0;

			 //套餐set
			 Set<String> comboSet = new HashSet<String>();
			 //套餐ID Set
			 Set<String> comboIdSet = new HashSet<String>();
	 
			 //公司相关的set用于统计支公司数量
			 //公司SET
			 Set<String> companySet = new HashSet<String>();
			 //公司IDset
			 Set<String> companyIdSet = new HashSet<String>();
			 
			 Map<String,Object> props = new HashMap<String, Object>();
			 props.put(ErpCustomerSettleBX.F_SETTLEID, settleId);
			 props.put(ErpCustomerSettleBX.F_STATUS, "-1");
			 //根据settleId查询已存储的客户信息
			 List<ErpCustomerSettleBX> prevCustomerSettleBXList = erpCustomerSettleBXDao.listByProps2(props);
			 
			 //统计之前存入的总人数，单据总额，套餐
			 if(prevCustomerSettleBXList!=null&&prevCustomerSettleBXList.size()>0){
				 //设置总人数
				 totalPersoNnum = prevCustomerSettleBXList.size();
				 for (int i = 0; i < prevCustomerSettleBXList.size(); i++) {
					 ErpCustomerSettleBX prevObj = prevCustomerSettleBXList.get(i);
					 //套餐
					 comboSet.add(prevObj.getCombo());
					 //套餐ID
					 comboIdSet.add(prevObj.getComboId());
					 //各自的套餐价格累加
					 if(null!=prevObj.getComboPrice()){
						 totalAmount = totalAmount.add(prevObj.getComboPrice());
					 }else{
						 totalAmount = totalAmount.add(new BigDecimal("0"));

					 }
					 //公司
					 companySet.add(prevObj.getCompany());
					 //公司ID
					 companyIdSet.add(prevObj.getCompanyId());
				}
			 }
			 
			 //统计套餐数量
			 comboNum = comboSet.size()>=comboIdSet.size()?comboSet.size():comboIdSet.size();
			 //统计支公司数量
			 companyNum = companySet.size()>=companyIdSet.size()?companySet.size():companyIdSet.size();
			 
			 //返回相关信息		 
			 //套餐数量
			 result.put(ErpSettlementTaskBX.F_SETMEALNUM, comboNum);
			 //结算人数
			 result.put(ErpSettlementTaskBX.F_TOTALPERSONNUM, totalPersoNnum);
			 //应结算金额
			 result.put(ErpSettlementTaskBX.F_TOTALAMOUNT, totalAmount);
			 //实际结算金额默认为应结算金额
			 result.put(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT, totalAmount);
			 //支公司数量
			 result.put(ErpSettlementTaskBX.F_COMPANYNUM, companyNum);
		 }
		 return result;
	 }

	/**
	 * 更新场次的保险公司结算状态
     * @param props
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-15 下午4:59:52
	 */
	public boolean updateEventsStatusBX(Map<String,String> props) throws Exception{
		boolean flag = false;
		List<ErpEvents> events = erpEventsDao.listEventsByProps(props);
		if(events!=null&&events.size()>0){
			Map<String,Object> params = new HashMap<String, Object>();
			int updateNum = 0;
			for (int i = 0; i < events.size(); i++) {
				params.clear();
				ErpEvents obj = events.get(i);
				String eventsNo = obj.getEventsNo();
				if(eventsNo!=null&&eventsNo.length()>0){
					updateNum += erpEventsDao.updateEventsStatusBX(eventsNo);
				}
				
			}
			System.out.println("updateNum: "+updateNum);
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 添加场次或者会员后，未保存结算任务下点击取消按钮
	 * @param settleId
	 * @return boolean 
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-11 下午5:36:52
	 */
	public boolean cancelTask(String settleId, String userName) throws Exception{
		 boolean flag = false;
		 if(StringUtils.isNotEmpty(settleId)&&StringUtils.isNotEmpty(userName)){
			 if(dao.cancelTask(settleId, userName)>0){
				 flag = true;
			 }
		 }
		 return flag;
	 }
	
	/**
	 * 根据结算ID，获取结算客户信息
	 * @param settBXId
	 * @return
	 * @author tangxing
	 * @date 2017-2-4下午1:52:49
	 */
	public List<ErpCustomerSettleBX> getCustomerSettleBXBySettleId(String settBXId){
		return dao.getCustomerSettleBXBySettleId(settBXId);
	}
	
	/**
	 * 生成excel文件（文件流）
	 * @param list
	 * @return
	 * @throws Exception
	 * @author tangxing
	 * @date 2017-1-11上午11:12:50
	 */
	public void uploadPathForStrem(List<ErpCustomerSettleBX> list,HttpServletResponse response) throws Exception{
		//HashMap< String, String> pathMap = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
		String strName = sdf.format(new Date());
		String excelName = "customerSettleBX"+strName+".xls";			//文件名
		//String curFilePath = localPath+strName+File.separator;		//存放位置
		
		List<List<String>> rowList = this.buildExcelRow(list);
		
		this.createExSettleXls(excelName,rowList,response);
		//log.info("ErpHKPrepCustomer uploadPath excel save file path -- "+curFilePath+",excleName--"+excelName);
		
		/*pathMap.put("curFilePath",curFilePath);
		pathMap.put("excelName",excelName);*/
	}
	
	/**
	 * 格式化excel数据
     * @param erpCustomerSettleBX
	 * @return
	 * @author tangxing
	 * @date 2017-1-2下午8:27:06
	 */
	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRow(List<ErpCustomerSettleBX> erpCustomerSettleBX){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<erpCustomerSettleBX.size();i++){
  			ErpCustomerSettleBX content = erpCustomerSettleBX.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getEventsNo());//场次哈号
  			list.add(content.getCode());//条码
  			list.add(content.getName());//姓名
  			list.add(content.getCombo());//套餐名
  			list.add(content.getComboPrice()!=null?String.valueOf(content.getComboPrice()):"0");//套餐价格
  			list.add(content.getComboPriceActual()!=null?String.valueOf(content.getComboPriceActual()):"0");//实际结算价格
  			list.add(content.getAge());//年龄
  			list.add(content.getCompany());//支公司名
  			list.add(content.getOwnedCompany());//所属公司
  			list.add(content.getSalesMan());//销售人员
  			if(content.getSamplingDate()!=null){
  				list.add(sdf.format(content.getSamplingDate()));	//采样日期
  			}else{
  				list.add("");
  			}
  			list.add(StringUtils.isNotEmpty(content.getProvice())?this.getRegionById(content.getProvice()):"");	//省份
  			list.add(StringUtils.isNotEmpty(content.getCity())?this.getRegionById(content.getCity()):"");	//城市
  			list.add(content.getIsMatchreport());//是否匹配报告
  			
  			result.add(list);
  		}
  		return result;
  	}
	
	/**
	 * 生成excel文件
	 * @param filename
	 * @param rowList
	 * @param response
	 * @author tangxing
	 * @date 2017-2-6下午1:27:45
	 */
	public void createExSettleXls(String filename, List<List<String>> rowList,HttpServletResponse response) {
		try {
			/*File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}*/
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("sheet1");
			
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
			cell1.setCellValue("场次号");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("条码");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("姓名");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("套餐");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("套餐价格");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("实际价格");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("年龄");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("支公司");
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("所属公司");
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("销售人员");
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("采样日期");
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("省份");
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("城市");
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("是否匹配报告");
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
			}
			response.reset();
			response.setContentType("application/msexcel;");                
			response.setHeader("Content-Type","application/vnd.ms-excel");
            response.setHeader("Content-Disposition", new String(("attachment;filename="+filename).getBytes("GB2312"), "UTF-8"));
			OutputStream os = response.getOutputStream();
			hssfWorkbook.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
		}
	}
	
	/**
	 * 根据regionId获取省份城市
	 * @param regionId
	 * @return
	 * @author tangxing
	 * @date 2017-2-6下午1:31:12
	 */
	public String getRegionById(String regionId){
		List<Map<String, Object>> maps = dao.getRegionById(regionId);
		String regionName = "";
		if(maps!=null&&maps.size()>0){
			regionName = (String) maps.get(0).get("regionName");	//获取省份城市
		}
		return regionName;
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

	/**
	 * @desc  根据条件和项目编码查询场次信息
	 * @author Damian
	 * @since 17-3-15 下午8:10
	 */
	public Page findRelateEvents(Page page, Map searchMap, String projectNo) throws Exception {
		return dao.findRelateEvents(page, searchMap, projectNo);
	}

	/**
	 * @desc 以项目编号为基准条件查询客户信息
	 * @author Damian
	 * @since 17-3-16 上午10:45
	 */
	public Page findRelateCustomer(Page page, Map searchMap, String projectNo) throws Exception {
		return dao.findRelateCustomer(page, searchMap, projectNo);
	}

	/**
	 * 更新结算统计信息
	 * @param taskBX
	 * @param resultMap
	 * @return ErpSettlementTaskBX
	 * @author Daimian
	 * @since 2017-03-10
	 */
	public ErpSettlementTaskBX updateTaskInfo(ErpSettlementTaskBX taskBX, Map<String,String> resultMap){

		taskBX.setTotalAmount(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_TOTALAMOUNT)));
		taskBX.setActualTotalAmount(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_ACTUALTOTALAMOUNT)));
		taskBX.setTotalPersonNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_TOTALPERSONNUM)));
		taskBX.setSetMealNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_SETMEALNUM)));
		taskBX.setCompanyNum(Integer.valueOf(resultMap.get(ErpSettlementTaskBX.F_COMPANYNUM)));

		taskBX.setRemark(resultMap.get(ErpSettlementTaskBX.F_REMARK));
		taskBX.setProUser(resultMap.get(ErpSettlementTaskBX.F_PROUSER));
		taskBX.setBranchCompany(resultMap.get(ErpSettlementTaskBX.F_BRANCHCOMPANY));
		taskBX.setOwnedCompany(resultMap.get(ErpSettlementTaskBX.F_OWNEDCOMPANY));
		taskBX.setBranchCompanyId(resultMap.get(ErpSettlementTaskBX.F_BRANCHCOMPANYID));

		taskBX.setOwnedCompanyId(resultMap.get(ErpSettlementTaskBX.F_OWNEDCOMPANYID));
		taskBX.setTotalIncome(new BigDecimal(resultMap.get(ErpSettlementTaskBX.F_TOTALINCOME)));

		return taskBX;
	}

	/**
	 * 更新结算统计信息
	 * @param taskBX
	 * @param settleId
	 * @param info
	 * @param user
	 * @return ErpSettlementTaskBX
	 * @author Daimian
	 * @since 2017-03-11
	 */
	public ErpSettlementTaskBX updateTaskInfo(ErpSettlementTaskBX taskBX, String settleId, String info, User user ){
		Map<String,String> resultMap;
		try {
			resultMap = this.addEventsCustomersToSettle(settleId, info, user.getUserName(), user.getId());
			if (!CollectionUtils.isEmpty(resultMap)) {
				taskBX = this.updateTaskInfo(taskBX, resultMap);
			}
		} catch (Exception e) {
			log.info(e);
		}
		return taskBX;
	}
}
