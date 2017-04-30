package org.hpin.settlementManagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.dict.util.Constants;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.dao.ErpCompanyComboPriceDao;
import org.hpin.settlementManagement.entity.ErpCompanyComboPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ymjy.combo.dao.ComboDao;
import org.ymjy.combo.entity.Combo;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
@Service(value = "org.hpin.settlementManagement.service.ErpCompanyComboPriceService")
@Transactional()
public class ErpCompanyComboPriceService extends BaseService {

	@Autowired
	private ErpCompanyComboPriceDao dao;
	
	@Autowired
	private ComboDao comboDao;
	
	@Autowired
	private CustomerRelationshipDao customerRelationshipDao;
	
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
     * 删除数据
     * @param comboPrice
     */
    public void delComboPrice(ErpCompanyComboPrice comboPrice){
    	dao.delete(comboPrice);
    }
    
    public List listCombo(){
    	return dao.listCombo();
    }
    
    /**
     * 查找所有未删除的数据
     * @return List
     * @author DengYouming
     * @since 2016-5-6 上午11:43:29
     */
    public List listErpCompanyComboPrice(int pageSize,int pageNum){
    	User user=(User)HttpTool.getSession().getAttribute("currentUser");
    	return dao.listErpCompanyComboPrice(pageNum,pageSize,user.getUserName());
    }
    
    public List listErpCompanyComboPrice(int pageSize,int pageNum,Map<String, Object> map) throws Exception{
    	User user=(User)HttpTool.getSession().getAttribute("currentUser");
    	//tx 2016-11-23  去掉admin才能看所有的权限
    	/*if (user.getAccountName().equals("admin")) {
			return dao.listErpCompanyComboPrice(pageSize,pageNum,map);
		}else {
			
			 * 通过支公司去查询对应项目编码,项目负责人(1对多);
			 * create by henry.xu 20160929;
			 
			return dao.listErpCompanyComboPrice(pageSize,pageNum,map,user.getUserName());
		}*/
    	return dao.listErpCompanyComboPrice(pageSize,pageNum,map);
    }
    
    public long getTotalPageSize(Map<String, Object> map) throws Exception {
    	User user=(User)HttpTool.getSession().getAttribute("currentUser");
    	//tx 2016-11-23  去掉admin才能看所有的权限
    	/*if (user.getAccountName().equals("admin")) {
			return dao.getTotalPageSize(map);
		}else {
			return dao.getTotalPageSize(map,user.getUserName());
		}*/
    	return dao.getTotalPageSize(map);
	}
    
    /**
     * 保存ErpCompanyComboPrice对象
     * @param entity
     * @return
     * @author DengYouming
     * @throws Exception 
     * @since 2016-5-5 下午6:05:34
     */
    public boolean addErpCompanyComboPrice(ErpCompanyComboPrice entity) throws Exception{
    	boolean flag = false;
    	//获取当前用户
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		//因前端select标签的问题，comboName的值为comboId，暂时的解决方法是：根据comboId从数据库中查询对应的comboName. add by DengYouming 2016-05-06
		String comboName = comboDao.findComboNameById(entity.getComboId());
		if(comboName!=null&&comboName.length()>0){
			entity.setCombo(comboName);
		}
		//无ID为新增操作
		if(null==entity.getId()||"".equals(entity.getId())){
	    	entity.setCreateTime(new Date());
	    	entity.setCreateUser(currentUser.getUserName());
	    	entity.setCreateUserId(currentUser.getId());
	    	entity.setStatus(Constants.STATUS_NEW_STR);
	    	flag = dao.addErpCompanyComboPrice(entity);
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(currentUser.getUserName());
			entity.setUpdateUserId(currentUser.getId());
			flag = dao.addErpCompanyComboPrice(entity);
		}
    	return flag;
    }
    
    /**
     * 根据公司ID查找其下的套餐
     * @param companyId
     * @return
     * @author DengYouming
     * @throws Exception 
     * @since 2016-5-5 下午12:10:07
     */
    public List<Combo> listComboByCompanyId(String companyId) throws Exception{
    	List<Combo> list = null;
    	//查找套餐名相关
    	String comboName = customerRelationshipDao.findComboByCompanyId(companyId);
    	if(comboName!=null&&comboName.length()>0){
    		list = comboDao.findListByComboName(comboName);
    	}
    	return list;
    }
    
    /**
     * 批量删除公司套餐价格记录(逻辑删除)
     * @return boolean
     * @throws Exception
     * @author DengYouming
     * @since 2016-5-5 下午5:11:16
     */
    public boolean deleteErpCompanyComboPriceBatch(String ids) throws Exception{
    	boolean flag = false;
    	flag = dao.deleteErpCompanyComboPriceBatch(ids);
    	return flag;
    }

	public int getErpCompanyComboPriceCount() {
		User user=(User)HttpTool.getSession().getAttribute("currentUser");
		return dao.getErpCompanyComboPriceCount(user.getUserName());
	}
	
	
	/**
	 * 根据支公司ID，获取所有的套餐
	 * @param companyId
	 * @return
	 * @author tangxing
	 * @date 2016-11-17下午6:16:21
	 */
	public List<ErpCompanyComboPrice> getCustomerShipProByCompanyId(String companyId){
		ErpCompanyComboPrice comboPrice = null;
		List<ErpCompanyComboPrice> comboPriceList = new ArrayList<ErpCompanyComboPrice>();
		//支公司对应的项目
		List<CustomerRelationShipPro> relationShipPros = dao.getCustomerShipProByProjectId(companyId);
		for (CustomerRelationShipPro customerRelationShipPro : relationShipPros) {
			String projectId = customerRelationShipPro.getId();
			String projectCode = customerRelationShipPro.getProjectCode();
			String projectName = customerRelationShipPro.getProjectName();
			String projectOwner = customerRelationShipPro.getProjectOwner();
			//String allStr = projectId+","+projectCode+","+projectName+","+projectOwner;		//项目信息（以','隔开）
			List<String> comboIdList =this.formatComboIdString(projectId);						//用于存放combo ID的List（该项目下的套餐）
			if(comboIdList!=null){
				for (String comboId : comboIdList) {
					List<ErpCompanyComboPrice> comboPrices = dao.getErpCompanyComboPriceByComboId(comboId, companyId, projectId);
					if(comboPrices!=null&&comboPrices.size()>0){								//判断是否在公司套餐价格表能找到
						comboPriceList.add(comboPrices.get(0));
					}else{
						//未找到到hl_jy_combo中去拿信息
						Combo combo = dao.getComboById(comboId);
						if(combo!=null){
							comboPrice = new ErpCompanyComboPrice();
							comboPrice.setCombo(combo.getComboName());
							comboPrice.setComboId(combo.getId());
							comboPrice.setProjectId(projectId);
							comboPrice.setProjectCode(projectCode);
							comboPrice.setProjectName(projectName);
							comboPrice.setProjectOwner(projectOwner);
							comboPriceList.add(comboPrice);//ErpCompanyComboPrice list（冗余了项目信息在ErpCompanyComboPrice类里面）
						}
					}
				}
			}
		}
		return comboPriceList;
	}
	
	/**
	 * 格式化comboIdMap
	 * @param projectId
	 * @return
	 * @author tangxing
	 * @date 2016-11-17下午5:26:49
	 */
	private List<String> formatComboIdString(String projectId){
		List<Map<String, Object>> listMap = dao.getProjectAndComboInfo(projectId);
		List<String> comboIdList = null;
		Set<String> comboIdSet= null;
		if(listMap!=null){
			comboIdList = new ArrayList<String>();
			comboIdSet = new TreeSet<String>();
			for (Map<String, Object> map : listMap) {
				String comboId = (String) map.get("comboId");
				comboIdSet.add(comboId);
			}
		}
		comboIdList.addAll(comboIdSet);
		return comboIdList;
	}
  
}
