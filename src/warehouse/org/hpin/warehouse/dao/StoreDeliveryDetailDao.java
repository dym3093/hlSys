/**
 * @author DengYouming
 * @since 2016-7-14 下午6:01:11
 */
package org.hpin.warehouse.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.entity.StoreApplyedCount;
import org.hpin.warehouse.entity.StoreType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 物品申请明细表Dao
 * @author DengYouming
 * @since 2016-7-14 下午6:01:11
 */
@Repository
public class StoreDeliveryDetailDao extends BaseDao {
	
	public List<StoreApplyedCount> getApplyDetails(String id,String batNo) {
		int count = 0;
		String sql1 = " from StoreApplicationDetail where 1=1 and id_related=?";	//申请的详细信息
		String sql2 = " from SysDictType where 1=1 and dictid=?";		//获取品类名称
		String sql3 = " select spd.remark1 as remark1,sum(spd.remark) as count from store_produce spd where spd.remark3='"+batNo+"' group by spd.remark1 ";	//获取已发货数量
		String sql4 = "from StoreType where is_deleted = 0 and id=?";	//获取描述
		List<StoreApplicationDetail> applyDetailsList = this.getHibernateTemplate().find(sql1,id);
		List<StoreApplyedCount> list = new ArrayList<StoreApplyedCount>();
		List<Map<String, Object>> applyedCountList = this.getJdbcTemplate().queryForList(sql3);		//已申请的数量
		for (int i=0;i<applyDetailsList.size();i++) {
			StoreApplyedCount detail = new StoreApplyedCount();
			for(Map<String, Object> key :applyedCountList){
				if(applyDetailsList.get(i).getStoreTypeId().equals(key.get("remark1"))){
					detail.setApplyedCount(Integer.valueOf(key.get("count").toString()));
					count ++;
					break;
				}
			}
			List<SysDictType> detailsList = this.getHibernateTemplate().find(sql2,applyDetailsList.get(i).getTypeBigCode());
			List<StoreType> storeTypeList = this.getHibernateTemplate().find(sql4,applyDetailsList.get(i).getStoreTypeId());
			if(count==0){
				detail.setApplyedCount(0);
			}
			count = 0;
			detail.setTypeName(detailsList.get(0).getDictName());
			detail.setPrdouceName(applyDetailsList.get(i).getPrdouceName());
			detail.setStandards(applyDetailsList.get(i).getStandards());
			if (storeTypeList.size()>=1) {
				detail.setDescripe(storeTypeList.get(0).getDescripe());
			}
			detail.setApplyNum(applyDetailsList.get(i).getApplyNum());
			list.add(detail);
		}
		return list;
	}

	public List<StoreType> getStoreType(String remark,String id) {
//		String sql = "from StoreType where is_deleted=0 and remark=? and id in(select store_type_id from store_application_detail where id_related =?)";
		String sql = "select num,standards,name,remark1,id from store_type where is_deleted=0 and remark='"+remark+"' and id in(select store_type_id from store_application_detail where id_related='"+id+"')";
		List<StoreType> list = this.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(StoreType.class));
		return list;
	}

	/**
	 * @param remark1
	 * @return 品类
	 */
	public String getTypeName(String remark1) {
		String sql ="select dictname from sys_dicttype where dictid='"+remark1+"'";
		return this.getJdbcTemplate().queryForList(sql).get(0).get("DICTNAME").toString();
	}

	/**
	 * @param remark	仓库
	 * @param remark1	品类id
	 * @return
	 */
	public Integer getCount(String remark, String remark1) {
		String sql = "select count from store_warehouse_all where warehouse_id='"+remark+"' and type_big_code='"+remark1+"'";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	/**
	 * @param id
	 * @return 获取storeType的信息
	 */
	public List<StoreType> getTypeInfo(String id) {
		String sql = "from StoreType where is_deleted=0 and id=?";
		return this.getHibernateTemplate().find(sql,id);
	}
	
}
