package org.hpin.warehouse.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.warehouse.dao.StoreWarehouseAllDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value="org.hpin.warehouse.service.StoreWarehouseAllService")
@Transactional()
public class StoreWarehouseAllService extends BaseService {
@Autowired
private StoreWarehouseAllDAO dao = null;
public boolean isRepeat(String warehouseId,String typename,String bigCode){
	String sql = "select count(*) from STORE_TYPE t where t.REMARK ='"+warehouseId+"" +
			"' and t.REMARK1='"+bigCode+"' and t.NAME='"+typename+"' AND T.IS_DELETED=0";
	return dao.getJdbcTemplate().queryForInt(sql)>0?false:true;
}
public List findByTypeId(String id){
	StringBuffer sb = new StringBuffer();
	sb.append(" from StoreWarehouseAll where typeSmallCode = ? and isDeleted=0");
	return dao.findByHql(sb, new Object[]{id});
}
public List findByTypeIdAndStoreId(String storeId,String smallTypeId){
	StringBuffer sb = new StringBuffer();
	sb.append(" from StoreWarehouseAll y where y.warehouseId = ? and y.typeSmallCode = ? and y.isDeleted=0");
	return dao.findByHql(sb, new Object[]{storeId,smallTypeId});
}
}
