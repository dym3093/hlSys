package org.hpin.settlementManagement.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymjy.combo.entity.Combo;
import org.hpin.settlementManagement.dao.ErpComboPriceDao;
import org.hpin.settlementManagement.entity.ComboHistoryPrice;
import org.hpin.settlementManagement.entity.ErpComboPrice;
@Service(value = "org.hpin.settlementManagement.service.ErpComboPriceService")
@Transactional()
public class ErpComboPriceService extends BaseService {

	@Autowired
	private ErpComboPriceDao dao;
	
	
    /**
     * 分页获取对象
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
    	return dao.findByPage(page, searchMap);
    }
    
    /**
     * 获取所有套餐价格
     * @return
     * @author tangxing
     * @date 2016-5-25下午5:52:14
     */
    public List<ErpComboPrice> listComboPrice(){
    	return dao.listComboPrice();
    }
    
    /**
     * 删除数据
     * @param comboPrice
     */
    public void delComboPrice(ErpComboPrice comboPrice){
    	dao.delete(comboPrice);
    }
    
    public List listCombo(){
    	return dao.listCombo();
    }
    
	public List<Combo> getComboSelectedInput() {
		return dao.getComboSelectedInput();
	}
    
    /**
     * 增加
     * @param comboPrice
     */
    public void addComboPrice(ErpComboPrice comboPrice) throws Exception{
    	/*List<ErpComboPrice> list = dao.listErpComboPrice();
    	boolean flag = false;
    	if(list.size()>0){
    		for(ErpComboPrice allComboPrice:list){
        		
        		//如果这个支公司and套餐 跟以前的重复
        		if(comboPrice.getComboId().equals(allComboPrice.getComboId())
        				&&comboPrice.getGeneCompany().equals(allComboPrice.getGeneCompany())){
        			flag = false;
        			break;
        		}else{
        			flag = true;
        		}
        	}
    	}else{			//集合为0，肯定不会有重复
    		flag=true;	
    	}
    	if(flag){
    		dao.save(comboPrice);	
    	}else{
    		;
    	}
    	
    	return flag;*/
    	dao.save(comboPrice);
    }
    
    /**
     * 修改
     * @param comboPrice
     */
    public void updateComboPrice(ErpComboPrice comboPrice){
    	dao.update(comboPrice);
    }
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ErpComboPrice get(String id){
		return dao.get(id);
	}
    
    /**
     * 创建一条历史价格记录
     * @param comboHistoryPrice
     */
	public void saveHistoryPrice(ComboHistoryPrice comboHistoryPrice) {
		dao.saveHistoryPrice(comboHistoryPrice);
	}
	
	/**
	 * 根据基因公司Id筛选套餐价格费用
	 * @param geCompanyId
	 * @return
	 * @author tangxing
	 * @date 2016-6-14下午3:48:37
	 */
	public List<ErpComboPrice> listComboPriceByGeCompanyId(String geCompanyId){
		return dao.listComboPriceByGeCompanyId(geCompanyId);
	}
	
	/**
	 * 判断是否重复
	 * @param comboId
	 * @param geCompanyId
	 * @return
	 * @author tangxing
	 * @date 2016-6-16上午10:29:26
	 */
	public boolean judgeRepeatList(String comboId,String geCompanyId){
		boolean isRepeat = false;
		 List<ErpComboPrice> comboPrices = dao.judgeRepeatList(comboId,geCompanyId);
		 if(comboPrices.size()>0){		//重复
			 isRepeat = false;
		 }else{
			 isRepeat = true;
		 }
		 return isRepeat;
	}

	/**
	 * @param comboId
	 * @return 根据ID从hl_jy_combo里获取套餐信息
	 */
	public Combo getComboInfo(String id) {
		return dao.getHibernateTemplate().get(Combo.class, id);
	}

}
