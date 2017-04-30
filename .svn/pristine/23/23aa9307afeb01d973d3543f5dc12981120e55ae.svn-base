package org.hpin.settlementManagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ComboHistoryPrice;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.springframework.stereotype.Repository;
@Repository()
public class ErpComboPriceDao extends BaseDao {

    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        StringBuffer query = new StringBuffer(" from ErpComboPrice where 1=1 and isdelete='0' ");
        List valueList = new ArrayList();
        searchMap.put("order_createTime","desc");
        OrmConverter.assemblyQuery(query, searchMap, valueList);
        return super.findByHql(page, query, valueList);
    }
    
    /**
     * 获取所有的套餐价格
     * @return
     * @author tangxing
     * @date 2016-5-25下午5:49:52
     */
    public List<ErpComboPrice> listComboPrice(){
    	String query = " from ErpComboPrice where 1=1";
    	return getHibernateTemplate().find(query);
    }
    
    
    /**
     * 根据ID获取套餐价
     * @param id
     * @return ErpComboPrice
     */
    public ErpComboPrice get(String id){
        return (ErpComboPrice)this.findById(ErpComboPrice.class, id);
    }
    
    /**
     * 删除数据
     * @param comboPrice
     */
    public void delComboPrice(ErpComboPrice comboPrice){
    	super.delete(comboPrice);
    }
    
    /**
     * 增加
     * @param comboPrice
     */
    public void addComboPrice(ErpComboPrice comboPrice){
    	super.save(comboPrice);
    }
    
    /**
     * 修改
     * @param comboPrice
     */
    public void updateComboPrice(ErpComboPrice comboPrice){
    	super.update(comboPrice);
    }
    
    /**
     * 查找套餐价格集合
     * @return
     */
    public List listErpComboPrice(){
        List list=null;
        String queryString="from ErpComboPrice where isDelete=?";
        list = getHibernateTemplate().find(queryString, new Object[]{"0"});
        return list;
    }
    
    /**
     * 获取基因公司
     * @return
     */
    public List listCombo(){
        List list=null;
        String queryString="from ErpComboPrice where isDelete=0 order by COMBONAME";
        list = getHibernateTemplate().find(queryString);
        return list;
    }
    
    /**
     * @return
     * 获取套餐
     */
    public List getComboSelectedInput(){
    	List list=null;
    	String queryString="from Combo where is_delete=0 order by combo_name";
    	list = getHibernateTemplate().find(queryString);
    	return list;
    }


    /**
     * 创建一条历史价格记录
     * @param comboHistoryPrice
     */
	public void saveHistoryPrice(ComboHistoryPrice comboHistoryPrice) {
		super.save(comboHistoryPrice);
	}
	
    /**
     * 查找套餐价格集合
     * @return
     */
    public List listComboPriceByGeCompanyId(String geCompanyId){
        List list=null;
        String queryString="from ErpComboPrice where geneCompanyId=? and isDelete=0";
        list = getHibernateTemplate().find(queryString, new Object[]{geCompanyId});
        return list;
    }
    
    public List<ErpComboPrice> judgeRepeatList(String comboId,String geCompanyId){
    	 List list=null;
         String queryString="from ErpComboPrice where geneCompanyId=? and comboId=? and isDelete=0";
         list = getHibernateTemplate().find(queryString, new Object[]{geCompanyId,comboId});
         return list;
    }
}
