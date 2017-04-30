package org.hpin.warehouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.StoreProduceDetailDAO;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreDeliveryDetail;
import org.hpin.warehouse.entity.StoreProduce;
import org.hpin.warehouse.entity.StoreType;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.hpin.warehouse.entity.StoreWarehouseAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value="org.hpin.warehouse.service.StoreProduceDetailService")
@Transactional()
public class StoreProduceDetailService extends BaseService {
@Autowired
private StoreProduceDetailDAO dao = null;
public boolean isRepeat(String warehouseId,String typename,String bigCode){
	String sql = "select count(*) from STORE_TYPE t where t.REMARK ='"+warehouseId+"" +
			"' and t.REMARK1='"+bigCode+"' and t.NAME='"+typename+"' AND T.IS_DELETED=0";
	return dao.getJdbcTemplate().queryForInt(sql)>0?false:true;
}
public List findByStoreId(String storeId){
	StringBuffer sb = new StringBuffer();
	sb.append(" from StoreType y where y.remark = ? and y.isDeleted=0");
	return dao.findByHql(sb, new Object[]{storeId});
}
public List findByTypeIdAndStoreId(String typeid, String storeId) {
	// TODO Auto-generated method stub
	StringBuffer sb = new StringBuffer();
	sb.append(" from StoreProduceDetail d where d.isDeleted=0 and d.typeSmallCode= ? and d.warehouseId=? order by d.createTime desc");
	return dao.findByHql(sb, new Object[]{typeid,storeId});
}

	/**
	 * 查询商品大类的信息及数量
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-12 下午8:18:20
	 */
	public List<Map<String,Object>> listProduceDetailGroup(String produceName, long rowStart, long rowEnd) throws Exception{
		StringBuffer buff = new StringBuffer();
		if(rowStart!=0&&rowEnd!=0){
			buff.append("select * from (");
			buff.append(" select rownum rn, t.* from ( ");
		}
		buff.append(" select * from STORE_TYPE where 1=1 ");
		if(StringUtils.isNotEmpty(produceName)){
			buff.append(" and name like '%"+ produceName +"%' ");
		}
		if(rowStart!=0&&rowEnd!=0){
			buff.append(" ) t where rownum<="+rowEnd);
			buff.append(")  where rn >"+rowStart);
		}
		/*
		String sql = " select * from ( select t.type_big_code, t.type_small_code, t.type_small_name, sum(t.count) total_num from STORE_PRODUCE_DETAIL t group by t.type_big_code, t.type_small_code, t.type_small_name ) n where 1=1 ";
		buff.append(sql);
		if(StringUtils.isNotEmpty(produceName)){
			buff.append(" and n.type_small_name like '%"+ produceName +"%' ");
		}*/
		System.out.println("sql: "+buff.toString());
		return dao.getJdbcTemplate().queryForList(buff.toString());
	}
	
	/* 
	 * 	物品品类信息
	 */
	public List findByPage(Page page,Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	
	/**
	 * @param id
	 * @return 申请信息
	 */
	public List<StoreApplicationDetail> getApplyDetails(String id) {
		return dao.getApplyDetails(id);
	}
	
	/**
	 * @return 库存信息
	 */
	public List<StoreDeliveryDetail> getDeliveryDetails(Page page,String batNo) {
		String sql1 = "from StoreProduce where is_deleted=0 and remark3=?";
		List<StoreProduce> produceList = dao.getHibernateTemplate().find(sql1,batNo);
		List<StoreDeliveryDetail> list = new ArrayList<StoreDeliveryDetail>();
		for(StoreProduce produce:produceList){
			if (Integer.valueOf(produce.getRemark())==0) {	//发货数量
				continue;
			}
			List<StoreType> typeList = dao.getStoreTypeInfo(produce.getRemark1());
			for(StoreType type:typeList){
				StoreDeliveryDetail detail = new StoreDeliveryDetail();
				detail.setWareHouse(produce.getWarehouseName());	//仓库名称
				detail.setType(dao.getTypeName(type.getRemark1())); //品类
				detail.setName(type.getName());						//名称
				detail.setSpecifications(type.getStandards()); 		//规格
				detail.setStock(type.getNum());						//发货数量
				detail.setExpressCompany(produce.getEmsName()); 	//快递公司
				detail.setExpressNum(produce.getEmsNo());	//快递单号
				detail.setCreateTime(produce.getCreateTime());
				list.add(detail);
			}
			
		}
		return getPageList(page,list);
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

}
