package org.hpin.warehouse.service;

import java.util.List;

import org.hpin.common.core.orm.BaseService;
import org.hpin.warehouse.dao.StoreProduceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value="org.hpin.warehouse.service.StoreProduceService")
@Transactional()
public class StoreProduceService extends BaseService {
@Autowired
private StoreProduceDAO dao = null;
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
/**
 * 
 * @param storeId 小类ID
 * @param type 出入库
 * @return
 */
public List findByStoreIdAndType(String storeId,String type){
	StringBuffer sb = new StringBuffer();
	sb.append(" from StoreProduce p where p.isDeleted=0 and p.type= ? and p.remark1= ? order by p.createTime desc");
	return dao.findByHql(sb, new Object[]{Integer.parseInt(type),storeId});
}
}
