/**
 * 
 */
package org.hpin.reportdetail.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.base.region.entity.Region;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpPrintBatchDao;
import org.hpin.reportdetail.entity.ErpPrintBatch;
import org.hpin.reportdetail.entity.ErpReportdetailPDFContent;
import org.hpin.settlementManagement.entity.ErpPrintComboCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: dengqin 
* createDate: 2016-4-14 下午3:41:04
*/
/**
 * @author dengqin
 *
 */

@Service(value = "org.hpin.reportdetail.service.ErpPrintBatchService")
@Transactional()
public class ErpPrintBatchService extends BaseService{

    @Autowired
    ErpPrintBatchDao dao;
    
    
    
    /**
     * 分页获取用户
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        return dao.findByPage(page, searchMap);
    }
    
    /**
     * 分页获取用户
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPageReport(Page page, Map searchMap) {
        return dao.findByPageReport(page, searchMap);
    }
    
    /**
     * 不分页的ErpPrintBatch集合
     * @return
     */
    public List listErpPrintBatchs(){
    	
    	List list = dao.listErpPrintBatchs();
    	
    	return list;
    }
    
    public List getAllInputNumsList(String userName){
        List list=dao.getAllInputNumsList(userName);
        return list;
    }
    
    /**
     * 保存
     * @param conference
     */
    public void save(ErpPrintBatch printBatch){
        dao.save(printBatch);
    }
    
//    /*删除或修改*/
//    public void delete(List<ErpConference> list) {
//        for(ErpConference conference:list){
//            String conferenceNo = conference.getConferenceNo();
//            String sql = " delete from ERP_CUSTOMER as c where c.CONFERENCE_NO ='"+conferenceNo+"';";
////            cDao.getJdbcTemplate().update(sql);
//            dao.update(conference);
//        }
//    }
    
    /**
     * 修改
     * @description 
     * @param printBatch
     *
     * @return void
     * @throws
     *
     */
    public void modify(ErpPrintBatch printBatch){
        
        ErpPrintBatch oldErpPrintBatch=get(printBatch.getId());
        oldErpPrintBatch.setUpdateTime(new Date());
        //拷贝属性 复制属性
        ReflectionUtils.copyPropertiesForHasValue(oldErpPrintBatch, printBatch);
        dao.saveOrUpdate(oldErpPrintBatch);
    }
    
    /**
     * 获取所有打印批次集合
     * @return
     * @author tangxing
     * @date 2016-5-10下午2:59:38
     */
    public List<ErpPrintBatch> getProvinceCity(){
    	return dao.getProvinceCity();
    }
    
    /**
     * 获取已经打印批次集合
     * @return
     * @author tangxing
     * @date 2016-5-31下午5:10:27
     */
    public List<ErpPrintBatch> getProvinceCityTwo(){
    	return dao.getProvinceCityTwo();
    }
    
    
    /**
     * 根据ID获取批次
     * @description 
     * @param id
     * @return
     *
     * @return ErpPrintBatch
     * @throws
     *
     */
    public ErpPrintBatch get(String id){
        return dao.get(id);
    }
    
    /**
     * 是否为最大批次号
     * @description 
     * @param date
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String maxNo(String date){
        return dao.maxNo(date);
    }
    
    /**
     * 判断是否重复
     * @description 
     * @param compannyName
     * @param ownedCompany
     * @param conferneceDate
     * @param address
     * @return
     *
     * @return String
     * @throws
     *
     */
    public String isNotRepeatNoByEvents(String printBatchNo,String events){
        return dao.isNotRepeatNoByEvents(printBatchNo,events);
    }
    
    public void save(List<ErpPrintBatch> erpPrintBatchs){
        dao.save(erpPrintBatchs);
    }
    
	/**
	 * 根据父节点，找到子行政区划
	 * @return
	 */
	public List<Region> findRegionByParentId(String parentId) {
		return dao.findRegionByParentId(parentId);
	}
	
	/**
	 * 显示未在打印任务中的打印批次
	 * @author dym
	 * @return
	 */
	public List listErpPrintBatchsNotInPrintTask(){
		return dao.listErpPrintBatchsNotInPrintTask();
	}
	
	/**
	 * 查询已处理批次（后期待改进）
	 * @param params
	 * @return
	 */
	public List<ErpPrintBatch> listAlreadyErpPrintBatchs(){
		return dao.listAlreadyErpPrintBatchs();
	}
	
	/**
	 * 已处理打印批次
	 * @return
	 * @author tangxing
	 * @date 2016-5-31下午4:01:14
	 */
	public List<ErpPrintBatch> findByPageIsPrint(Page page, Map searchMap){
		return dao.findByPageIsPrint(page,searchMap);
	}
	
	/**
	 * @author dym
	 * @param params
	 * @return
	 */
	public List<ErpPrintBatch> listUnErpPrintBatchs(Map<String,Object> params){
		return dao.listUnErpPrintBatchs(params);
	}
	
	/**
	 * 根据任务ID查询其所有批次
	 * @param params
	 * @return
	 */
	public List<ErpPrintBatch> listErpPrintBatchByTaskId(String printTaskId){
		return dao.listErpPrintBatchByTaskId(printTaskId);
	}
	
	/**
	 * 把打印批次移除打印任务
	 * @param ids
	 * @return
	 */
	public String removePrintBatchFromPrintTask(String ids){
		String flag = "false";
		List<ErpPrintBatch> list = new ArrayList<ErpPrintBatch>();
		//包含逗号为数组
		if(ids.indexOf(",")>-1){
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				ErpPrintBatch printBatch = dao.get(idArray[i]);
				printBatch.setPrintTaskId("");
				printBatch.setIsPrintTask("0");
				list.add(printBatch);
			}
		}else{
			//不包含逗号为单个
			ErpPrintBatch printBatch = dao.get(ids);
			printBatch.setPrintTaskId("");
			printBatch.setIsPrintTask("0");
			list.add(printBatch);
		}
		if(list.size()>0){
			dao.save(list);
			flag = "true";
		}
		return flag;
	}
	
	/**
	 * 生成批次对应的子文件夹名
	 * @param id
	 * @return
	 */
	public String getCompanyComboByBatchId(String id){
		ErpPrintBatch printBatch = new ErpPrintBatch();
		printBatch = dao.get(id);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String crruTime = dateFormat.format(date);
		String branchCompany= printBatch.getBranchCompany();
		String combo= printBatch.getCombo();
		
		String group = crruTime+branchCompany+combo;
		
		return group;
	}
    
	/**
	 * 分页查询未打印批次
	 * @param searchMap
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-13 下午2:42:01
	 */
	public List<ErpPrintBatch> queryUnPrintBatchByPage(Page page,Map searchMap) throws Exception{
		List<ErpPrintBatch> list = null;
		if(!CollectionUtils.isEmpty(searchMap)){
			list = dao.findByPage(page, searchMap);
		}
		return list;
	}


	/**
	 * @param printCompanyId 打印公司ID
	 * @param setmealName 套餐名称
	 * @return 套餐价格
	 */
	public List<ErpPrintComboCost> getComboPrice(String printCompanyId,String setmealName) {
		String sql = "from ErpPrintComboCost where printcompanyid=? and comboname=? and isdeleted='0'";
		return  dao.getHibernateTemplate().find(sql,printCompanyId,setmealName);
	}

	/**
	 * @param printBatchNos
	 * @return 通过批次号获取customerID
	 */
	public List<Map<String, Object>> getCustomerId(String printBatchNos) {
		StringBuilder sql = new StringBuilder();
		StringBuilder helps = new StringBuilder();
		for (int i = 0; i < printBatchNos.split(",").length; i++) {
			helps.append("?,");
		}
		String help = helps.substring(0, helps.length()-1);
		sql.append("select customerid from erp_print_task_content where printbatchno in("+help+")");
		return dao.getJdbcTemplate().queryForList(sql.toString(),printBatchNos.split(","));
	}

	/**
	 * @return 获取下拉框的数据
	 */
	public List<ErpPrintBatch> getSelectedInputData() {
		String sql = "from ErpPrintBatch where isprinttask='0' and isdelete='0'";
		return dao.getHibernateTemplate().find(sql);
	}

	/**
	 * @param ownedCompanyId
	 * @return 根据总公司id获取支公司
	 */
	public List<ErpPrintBatch> getBranchCompanySelectedInputData(String ownedCompany) {
		String sql = "from ErpPrintBatch where ownedcompany=? and isprinttask='0' and isdelete='0'";
		return dao.getHibernateTemplate().find(sql,ownedCompany);
	}

	/**
	 * @param printTaskNo
	 * @return 根据任务号在打印批次中获取信息
	 */
	public List<ErpPrintBatch> getPrintBatchList(String printTaskNo) {
		return dao.getPrintBatchList(printTaskNo);
	}

	/**
	 * @param city
	 * @return 根据城市获取所属公司
	 */
	public List<ErpPrintBatch> getOwnedCompanySelectedInputData(String city) {
		String sql = "from ErpPrintBatch where city=? and isprinttask='0' and isdelete='0'";
		return dao.getHibernateTemplate().find(sql,city);
	}
	
}
