package org.hpin.warehouse.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.PreciseCompute;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.ErpApplicationDao;
import org.hpin.warehouse.dao.ErpApplicationDetailDao;
import org.hpin.warehouse.dao.ErpStoregeInDao;
import org.hpin.warehouse.dao.ErpStoregeOutDao;
import org.hpin.warehouse.entity.ErpApplication;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.hpin.warehouse.entity.ErpStoregeOut;
import org.hpin.warehouse.entity.ErpStoregeReback;
import org.hpin.warehouse.entity.vo.StoregeInOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ErpStoregeOutService extends BaseService{
	
	@Autowired
	private ErpApplicationDao erpApplicationDao;  //基因申请Dao
	@Autowired
	private ErpStoregeOutDao erpStoregeOutDao ; //申请出货Dao
	@Autowired
	private ErpApplicationDetailDao erpApplicationDetailDao; //申请明细Dao
	@Autowired
	private ErpStoregeInDao erpStoregeInDao; //入库Dao
	
	/**
	 * 根据查询条件查询该条件下所有数据;
	 * @param page
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void findStoregeOutsAll(Page page, HashMap<String, String> params) {
		//sql
		StringBuilder jdbcSql = this.erpStoregeOutDao.dealJdbcStoregeOutParams(params);
		
		//list
		BeanPropertyRowMapper<StoregeInOutVo> rowMapper = new BeanPropertyRowMapper<StoregeInOutVo>(StoregeInOutVo.class);
		List<?> results = this.erpStoregeOutDao.getJdbcTemplate().query(jdbcSql.toString(), rowMapper);
		page.setResults(results);
	}
	
	/**
	 * 根据出货数据Id查询退货数量sum;
	 * @param id
	 * @return
	 */
	public Integer findStoregeRebackNumByOutId(String id) {
		return this.erpStoregeOutDao.findStoregeRebackNumByOutId(id);
	}


	/**
	 * 1. 保存退库数据;
	 * 2. 根据当前选择的出库记录中的入库信息ID获取到对应的入库记录，
	 * 更新入库记录中的【可用数量】字段，更新规则为：入库表中的【可用数量】
	 * 的值+当前填写的【退库数量】的值。
	 * @param storegeReback
	 * @throws Exception 
	 */
	public void saveStoregeReback(ErpStoregeReback storegeReback) throws Exception {
		//1.保存数据;
		this.erpStoregeOutDao.saveStoregeReback(storegeReback);
		
		//2. 根据入库数据Id修改其可使用数量;
		this.erpStoregeInDao.updateUseableQuantity(storegeReback.getQuantity(), storegeReback.getStoregeInId());
		
	}
	
	/**
	 * 根据Id查询发货对象
	 * @param id
	 * @return
	 */
	public StoregeInOutVo findStoregeOutById(String id) {
		return this.erpStoregeOutDao.findStoregeOutById(id);
	}
	
	/**
	 * 查询出库数据分页
	 * @param page
	 * @param userInfo
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void findStoregeOutByPage(Page page, User userInfo,
			HashMap<String, String> params) {
		//sql
		StringBuilder jdbcSql = this.erpStoregeOutDao.dealJdbcStoregeOutParams(params);
		
		//参数;
		List<Object> objs = new ArrayList<Object>();
		
		//count
		long count = this.erpStoregeOutDao.findJdbcCount(jdbcSql, objs);
		page.setTotalCount(count);
		
		//list
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<StoregeInOutVo> rowMapper = new BeanPropertyRowMapper<StoregeInOutVo>(StoregeInOutVo.class);
		List<?> results = this.erpStoregeOutDao.findJdbcList(jdbcSql, objs, rowMapper);
		page.setResults(results);
	}
	
	/**
	 * 选择一条申请信息，点击【处理】按钮，将当前申请单的状态修改为3（处理中）
	 */
	public void dealApplicationStatus(String applicationIds, User userInfo) {
		String arrs[] = applicationIds.split(",");
		if(arrs != null && arrs.length > 0) {
			for(String applicationId : arrs) {
				if(StringUtils.isNotEmpty(applicationId)) {
					this.erpStoregeOutDao.updateApplicationStatusById(applicationId.trim(), "3", userInfo.getId());
				}
			}
		}
		
	}
	
	/**
	 * 发货信息保存;
	 * 业务人员在【入库表中相应的产品列表】中填写发货数量、快递公司、快递单号等信息后，
	 * 点击当前列表操作列中的【发货】按钮，根据当前关联的入库id，冲减入库表（ERP_STOREGE_IN）中的可用数量，冲减规则：当前入库表中的可用数量-当前发货数量；
	 * 更新入库表中的【真正可用开始卡号】字段的值：更新规则：当前【卡号结束】字段的值+1
	 * 将当前信息插入发货/出库表；
	 * 将当前申请单中此产品的发货数量重新赋值，规则为：申请单中此产品的发货数量+当前页面中填写的发货数量
	 * 判断:当前申请单中此产品的申请数量和发货数量相等，则状态为0：已发货，如果发货数量小于申请数量，则状态为1：部分发货
	 * 判断：当前申请单明细中（ERP_APPLICATION_DETAIL）数据的状态如果都是0（已发货），则更新申请单主表（ERP_APPLICATION）中的状态为：2（已发货）
	 * 当前申请单明细中（ERP_APPLICATION_DETAIL）数据的状态如果有不是0（已发货），则更新申请单主表（ERP_APPLICATION）状态为1（部分发货）
	 * @param storegeOut
	 * @param userInfo
	 * @param detailId
	 */
	public void sendSave(ErpStoregeOut storegeOut, User userInfo, String detailId) {
		int result = 0;
		/*
		 * 先保存数据;
		 */
		storegeOut.setCreateTime(new Date());
		storegeOut.setCreateUserId(userInfo.getId());
		//COST成本（单价*数量）
		storegeOut.setCost(PreciseCompute.mul(storegeOut.getPrice(), new BigDecimal(storegeOut.getQuantity())));
		
		//Amount总金额（单价*数量）+快递费
		storegeOut.setAmount(PreciseCompute.add(storegeOut.getCost(), storegeOut.getExpressMoney()));
		
		storegeOut.setIsDeleted(0);
		
		this.erpStoregeOutDao.saveStoregeOut(storegeOut);
		
		/*
		 * 处理业务
		 * 1.点击当前列表操作列中的【发货】按钮，根据当前关联的入库id，冲减入库表（ERP_STOREGE_IN）中的可用数量，冲减规则：当前入库表中的可用数量-当前发货数量；
		 * 2.更新入库表中的【真正可用开始卡号】字段的值：更新规则：当前【卡号结束】字段的值+1
		 * 3.将当前申请单中此产品的发货数量重新赋值，规则为：申请单中此产品的发货数量+当前页面中填写的发货数量
		 * 4.判断:当前申请单中此产品的申请数量和发货数量相等，则状态为0：已发货，如果发货数量小于申请数量，则状态为1：部分发货
		 */
		//1.根据当前关联的入库id，冲减入库表（ERP_STOREGE_IN）中的可用数量;
		//使用当前库存可使用数量-发货数量;
		result = this.erpStoregeOutDao.writeDownStoregeInById(storegeOut.getStoregeInId(), storegeOut.getQuantity());
		
		//2.如果是卡片的要处理真正可用开始卡号;首先判断是否有值; 只处理前台处理;
		if(result > 0) {
			String card = storegeOut.getUserCard();
			if(StringUtils.isNotEmpty(card)) {
				this.erpStoregeOutDao.updateStoregeInUserCardNum(storegeOut.getStoregeInId(), card);
			}
		}
		
		//3.将当前申请单中此产品的发货数量重新赋值，规则为：申请单中此产品的发货数量+当前页面中填写的发货数量, 
		//判断:当前申请单中此产品的申请数量和发货数量相等，则状态为0：已发货，如果发货数量小于申请数量，则状态为1：部分发货
		this.erpStoregeOutDao.updateApplicationDetailSendCount(detailId, storegeOut.getQuantity());
		
		//4当前申请单明细中（ERP_APPLICATION_DETAIL）数据的状态如果都是0（已发货），则更新申请单主表（ERP_APPLICATION）中的状态为：2（已发货）
		//当前申请单明细中（ERP_APPLICATION_DETAIL）数据的状态如果有不是0（已发货），则更新申请单主表（ERP_APPLICATION）状态为1（部分发货）
		//根据applicationId查询对应的明细;然后根据上面规则处理;
		List<ErpApplicationDetail> detailsList = this.erpApplicationDetailDao.findObjectsByApplicationId(storegeOut.getApplicationId());
		if(detailsList != null && detailsList.size() > 0) {
			String flag = "0";
			int count = 0;
			for (ErpApplicationDetail dta : detailsList) {
				if(dta.getStatus() == null) { 
					//由于在发货处理,所以必然就是要么部分发货,要么已发货完;
					continue;
				} else if(dta.getStatus()==1) {//标示部分发货;
					flag = "1"; //部分发货;
					break;
				} else if(dta.getStatus() == 0){
					count ++;
				}
			}
			//当为0的个数=集合长度,表示所有明细已发货完成;
			if(count == detailsList.size()) {
				flag = "2";
			} else if( count > 0) { //说明明细中有数据已发货完成,为部分发货;
				flag = "1";
			}
			
			this.erpStoregeOutDao.updateApplicationStatusById(storegeOut.getApplicationId(), flag, null);
		}
		
	}

	/**
	 * 查询申请单数据;
	 * @param page
	 * @param userInfo
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void findApplicationsByPage(Page page, User userInfo,
			HashMap<String, String> params) {
		
		//list查询参数;
		List<Object> objs = new ArrayList<Object>();
		if(params == null) {
			params = new HashMap<String, String>();
		}
		params.put("storegeOutStatus", "4"); //回退数据控制;
		params.put("dealUserId", userInfo.getId());
		
		//sql
		//查询参数处理
		StringBuilder jdbcsql = this.erpApplicationDao.dealParamsReturnSql(params);
		//根据时间排序,降序;
		jdbcsql.append(" order by apc.create_time desc, apc.id ");

		//count
		long count = this.erpApplicationDao.findJdbcCount(jdbcsql, objs);
		page.setTotalCount(count);
		
		//list
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpApplication> rowMapper = new BeanPropertyRowMapper<ErpApplication>(ErpApplication.class);
		List<?> list = this.erpApplicationDao.findJdbcList(jdbcsql, objs, rowMapper);
		page.setResults(list);
	}
	
	/**
	 * 根据申请id查询对应入库数据;
	 * 规则: 如果入库没有则不显示; 如果一个产品多条入库,则对应多个入库信息;
	 * @return
	 */
	public List<StoregeInOutVo> findObjectsByApplicationId(String applicationId, String currentUserId) {
		//查询该申请id对应明细数据已发货完成了的数据;
		List<String> idsList = this.erpApplicationDetailDao.findDetailIdByApplicationId(applicationId);
		StringBuilder ids = new StringBuilder();
		
		if(idsList != null && idsList.size() > 0) {
			for(int i =0; i< idsList.size(); i++) {
				if(i == 0) {
					ids.append(" '").append(idsList.get(i)).append("' ");
				} else {
					ids.append(", '").append(idsList.get(i)).append("' ");
					
				}
			}
		}
		
		return this.erpStoregeOutDao.findObjectsByApplicationId(applicationId, ids.toString(), currentUserId);
		
	}
	/**
	 * 根据申请id查询对应入库数据;查询出货数据列表;
	 * @return
	 */
	public List<StoregeInOutVo> findStoregeOutByApplicationId(String applicationId) {
		return this.erpStoregeOutDao.findStoregeOutByApplicationId(applicationId);
	}

	/**
	 * 修改;
	 * @param storegeOut
	 * @param userInfo
	 * @throws Exception
	 */
	public void updateStoregeOut(ErpStoregeOut storegeOut, User userInfo) throws Exception {
		//查询原有数据
		ErpStoregeOut old = (ErpStoregeOut) this.erpStoregeOutDao.findById(ErpStoregeOut.class, storegeOut.getId());
		//赋值
		old.setUpdateTime(new Date());
		old.setUpdateUserId(userInfo.getId());
		old.setAmount(storegeOut.getAmount());
		old.setExpressMoney(storegeOut.getExpressMoney());
		old.setExpressName(storegeOut.getExpressName());
		old.setExpressNo(storegeOut.getExpressNo());
		old.setRemark(storegeOut.getRemark());
		//update;
		this.erpStoregeOutDao.updateStoregeOut(old);
		
	}

	
}
