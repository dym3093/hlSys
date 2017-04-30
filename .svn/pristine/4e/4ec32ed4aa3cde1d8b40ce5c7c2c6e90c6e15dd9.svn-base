/**
 * @author DengYouming
 * @since 2016-7-15 下午12:07:16
 */
package org.hpin.warehouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.common.core.orm.BaseEntity;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.StoreDeliveryDetailDao;
import org.hpin.warehouse.entity.StoreApplication;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreApplyedCount;
import org.hpin.warehouse.entity.StoreDeliveryDetail;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreType;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.hpin.warehouse.entity.StoreWarehouseAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品发货Service
 * @author DengYouming
 * @since 2016-7-15 下午12:07:16
 */
@Service(value="org.hpin.warehouse.service.StoreDeliveryDetailService")
@Transactional
public class StoreDeliveryDetailService extends BaseService {

	@Autowired
	private StoreDeliveryDetailDao dao;
	
	/**
	 * @param id
	 * @return 申请信息
	 */
	public List<StoreApplyedCount> getApplyDetails(String id,String batNo) {
		return dao.getApplyDetails(id,batNo);
	}
	
	/**
	 * @return 查看发货后的信息
	 */
	public List<StoreDeliveryDetail> getDeliveryDetails(Page page,String batNo) {
		String sql1 = "from StoreProduce where remark3=?";
		List<StoreProduce> produceList = dao.getHibernateTemplate().find(sql1,batNo);
		List<StoreDeliveryDetail> deliveryList = new ArrayList<StoreDeliveryDetail>();
		for(StoreProduce produce:produceList){
			StoreDeliveryDetail detail = new StoreDeliveryDetail();
			List<StoreType> typeList = dao.getTypeInfo(produce.getRemark1());
			detail.setWareHouse(produce.getWarehouseName());
			detail.setName(produce.getRemark2());
			detail.setDeliveryNum(produce.getRemark());
			detail.setExpressCompany(produce.getEmsName());
			detail.setExpressNum(produce.getEmsNo());
			detail.setDeliveryDate(produce.getCreateTime());
			detail.setSpecifications(typeList.get(0).getStandards());
			detail.setType(dao.getTypeName(typeList.get(0).getRemark1()));    
			deliveryList.add(detail);
		}
		return getPageList(page,deliveryList);
	}
	
	/**
	 * @param page
	 * @param id	申请订单id(storeApplication表ID)
	 * @return 发货需要的信息
	 */
	public List<StoreDeliveryDetail> getDeliveryDetails2(Page page,String id) {
		String sql1 = "from StoreWarehouse where is_deleted=0";
		List<StoreWarehouse> wareHouseList = dao.getHibernateTemplate().find(sql1);
		List<StoreDeliveryDetail> detailList = new ArrayList<StoreDeliveryDetail>();
		for(StoreWarehouse wareHouse:wareHouseList){
			List<StoreType> typeList = dao.getStoreType(wareHouse.getId(),id);
			for(StoreType type:typeList){
				if(type.getNum()==0){
					continue;
				}
				StoreDeliveryDetail detail = new StoreDeliveryDetail();
				detail.setWareHouseId(wareHouse.getId());
				detail.setWareHouse(wareHouse.getName());			//仓库名称
				detail.setStock(type.getNum());						//当前库存
				detail.setName(type.getName());						//名称
				detail.setSpecifications(type.getStandards()); 		//规格
				detail.setType(dao.getTypeName(type.getRemark1())); //品类
				detail.setTypeCode(type.getRemark1());
				detail.setStoreTypeId(type.getId());
				detailList.add(detail);
			}
			
		}
		return detailList;
	}
	
	public List<StoreDeliveryDetail> getPageList(Page page,List<StoreDeliveryDetail> list){
		int pageNum=page.getPageNum();
		int pageSize=page.getPageSize();
		int start=(pageNum-1)*pageSize;
		int end=pageNum*pageSize;
		if(list.size()>=1){
			if (end>list.size()) {
				end=list.size();
			}
			page.setTotalCount((long)list.size());
			return list.subList(start, end);
		}else {
			return list;
		}
	}
	
	/**
	 * @param typeCode 品类ID
	 * @param id 仓库ID
	 * @return
	 */
	public int getStockNum(String storeTypeId, String id) {
		String sql = "select count from store_warehouse_all where warehouse_id ='"+id+"' and type_small_code='"+storeTypeId+"'";
		return dao.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * @param string
	 * @return 申请单号信息
	 */
	public StoreApplication getApplycation(String batchNo) {
		String sql = "from StoreApplication where bat_no=?";
		return (StoreApplication) dao.getHibernateTemplate().find(sql,batchNo).get(0);
	}

	/**
	 * @return 总公司信息
	 */
	public List<Map<String,Object>> getOwedCompany() {
		String sql = "select distinct hcr.customer_name_simple from hl_customer_relationship hcr";
		return dao.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * @param owedCompany
	 * @return 支公司信息
	 */
	public List<Map<String,Object>> getCompany(String owedCompany) {
		String sql = "select hcr.branch_commany from hl_customer_relationship hcr where hcr.customer_name_simple='"+owedCompany.trim()+"'";
		return dao.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * @param string
	 * @return 获取小类信息
	 */
	public StoreType getTypeInfo(String id) {
		String sql = "from StoreType where id=?";
		return (StoreType) dao.getHibernateTemplate().find(sql,id).get(0);
	}

	public StoreWarehouseAll getAllInfo(String wareHouseId, String storeTypeId) {
		String sql = "from StoreWarehouseAll y where y.warehouseId = ? and y.typeSmallCode = ? and y.isDeleted=0";
		return (StoreWarehouseAll) dao.getHibernateTemplate().find(sql,wareHouseId,storeTypeId).get(0);
	}

	/**
	 * @param name   名称
	 * @param batNo  批次号
	 * @return	已发货数量
	 */
	public String getApplyedCount(String name, String batNo) {
		String sql = "SELECT  spd.remark1 as remark1,sum(spd.remark) as count from store_produce spd where spd.remark2='"+name+"' AND spd.REMARK3='"+batNo+"' group by spd.remark1";
		List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
		if(list.size()>=1){
			return list.get(0).get("count").toString();
		}else {
			return "0";
		}
	}
	
}
