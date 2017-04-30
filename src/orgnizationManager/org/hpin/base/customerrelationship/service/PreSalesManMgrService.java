package org.hpin.base.customerrelationship.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.dao.PreSalesManMgrDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.PreSalesManMgr;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ybc
 * @since 2017/02/08
 */
@Service(value = "org.hpin.base.customerrelationship.service.PreSalesManMgrService")
@Transactional()
public class PreSalesManMgrService extends BaseService{
	
	@Autowired
	PreSalesManMgrDao preSalesManMgrDao;
	
	@Autowired
	CustomerRelationshipDao customerRelationshipDao;

	public Map<String,Integer> addSalesmanInfo(File salesManExc,User user) throws Exception {
		String filePath = HttpTool.getParameter("filePath");
		List<PreSalesManMgr> list = new ArrayList<PreSalesManMgr>();
		if(filePath.indexOf(".xlsx")!=-1){
			List<Map<String, String>> result = ExcelUtils.xlsxExcel(salesManExc);
			list = createSalesmanInfo(result,user);
		}else if(filePath.indexOf(".xls")!=-1){
			List<Map<String, String>> result = ExcelUtils.xlsExcel(salesManExc);
			list = createSalesmanInfo(result,user);
		}
		return saveSalesmanInfo(list);
	}
	
	public List<PreSalesManMgr> addSalesmanInfo(List<Map<String,String>> data,User user) throws Exception {
		List<PreSalesManMgr> list = new ArrayList<PreSalesManMgr>();
		list = createSalesmanInfo(data,user);
		return list;
	}
	
	//将Excel中数据提取出来
	public List<Map<String, String>> getExcelData(File salesManExc) throws Exception{
		return ExcelUtils.xlsxExcel(salesManExc);
	}
	
	public List<PreSalesManMgr> addSalesmanInfoBX(List<Map<String, String>> data,User user){
		List<PreSalesManMgr> list = new ArrayList<PreSalesManMgr>();
		list = createSalesmanInfoBX(data,user);
		return list;
	}
	
	public List<PreSalesManMgr> createSalesmanInfo(List<Map<String, String>> list,User user){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		Set<PreSalesManMgr> salesManSet = new HashSet<PreSalesManMgr>();
		List<PreSalesManMgr> salesManlist = new ArrayList<PreSalesManMgr>();
		for(Map<String, String> map : list){
			PreSalesManMgr salesman = new PreSalesManMgr();
			// 第一列是营销员工号,第二列是营销员姓名,第三列营销员电话,第四列营销员所在支公司,第五列营销员所在市公司,第六列营销员所在总公司,第七列远盟支公司名称,第八列远盟总公司名称,第九列渠道
			salesman.setEmployeeNo(map.get("0"));
			salesman.setSalesman(map.get("1"));
			salesman.setEmployeePhone(map.get("2"));
			salesman.setEmployeeCompany(map.get("3"));
			salesman.setEmployeeCityCompany(map.get("4"));
			salesman.setEmployeeHeadOffice(map.get("5"));
			salesman.setYmCompany(map.get("6"));
			salesman.setYmOwncompany(map.get("7"));
			//salesman.setMark(map.get("8"));//2017-4-1 焦姐要求取消渠道号
			salesman.setCreateUserId(currentUser.getId());
			salesman.setCreateTime(new Date());
			salesManSet.add(salesman);
		}
		salesManlist.addAll(salesManSet);
		return salesManlist;
	}
	
	public List<PreSalesManMgr> createSalesmanInfoBX(List<Map<String, String>> list,User user){
		User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
		Set<PreSalesManMgr> salesManSet = new HashSet<PreSalesManMgr>();
		List<PreSalesManMgr> salesManlist = new ArrayList<PreSalesManMgr>();
		for(Map<String, String> map : list){
			PreSalesManMgr salesman = new PreSalesManMgr();
			// 第一列是营销员工号,第二列是营销员姓名,第三列营销员电话,第四列渠道
			salesman.setEmployeeNo(map.get("0"));
			salesman.setSalesman(map.get("1"));
			salesman.setEmployeePhone(map.get("2"));
			//salesman.setMark(map.get("3"));//2017-4-1 焦姐要求取消渠道号
			salesman.setYmCompanyId(user.getJobNumber());
			salesman.setYmOwncompanyId(user.getDeptId());
			salesman.setCreateUserId(currentUser.getId());
			salesman.setCreateTime(new Date());
			salesManSet.add(salesman);
		}
		salesManlist.addAll(salesManSet);
		return salesManlist;
	}
	
	public Map<String,Integer> saveSalesmanInfo(List<PreSalesManMgr> list){
		List<PreSalesManMgr> listAble = new ArrayList<PreSalesManMgr>();
		Map<String,Integer> resMap = new HashMap<String,Integer>();
		Integer validData = 0,amlData = 0,repeatData = 0;
		if(list.size()>0){
			for(PreSalesManMgr salesMan : list){
				//20170413_焦姐要求做过滤
				if(preSalesManMgrDao.hasObjByNoAndName(salesMan)){
					repeatData++;
					continue;
				}
				CustomerRelationShip relationShip = customerRelationshipDao.findCustomerRelationShipByName(salesMan.getYmCompany(),salesMan.getYmOwncompany());
				if(null!=relationShip){
					salesMan.setYmCompanyId(relationShip.getId());
					salesMan.setYmOwncompanyId(relationShip.getOwnedCompany());
					salesMan.setIsDeleted(0);
					validData++;
				}else{
					salesMan.setIsDeleted(2);
					amlData++;
				}
				listAble.add(salesMan);
			}
			if(0<listAble.size()){
				preSalesManMgrDao.getHibernateTemplate().saveOrUpdateAll(listAble);
			}
		}
		resMap.put("validData", validData);
		resMap.put("amlData", amlData);
		resMap.put("repeatData",repeatData);
		return resMap;
	}
	
	public Integer saveSalesmanInfoBX(List<PreSalesManMgr> list){
		List<PreSalesManMgr> listAble = new ArrayList<PreSalesManMgr>();
		for(PreSalesManMgr salesman : list){
			if(!preSalesManMgrDao.hasObjByNoAndName(salesman)){
				listAble.add(salesman);
			}
		}
		if(listAble.size()>0){
			preSalesManMgrDao.getHibernateTemplate().saveOrUpdateAll(listAble);
		}
		return listAble.size();
	}

	/**
	 * 处理支公司为ID转为NAME
	 * @param page
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dealSalesManInfo(Page page) throws Exception {
		List<PreSalesManMgr> list = page.getResults();
		for(PreSalesManMgr salesMan : list){
			CustomerRelationShip relationShip = customerRelationshipDao.findShipByCompanyId(salesMan.getYmCompanyId());
			if(null!=relationShip){
				salesMan.setYmCompany(relationShip.getBranchCommany());
				salesMan.setYmOwncompany(relationShip.getCustomerNameSimple());
			}
		}
		page.setResults(list);
	}

	/**
	 * @param i 0查支公司,1查所属公司
	 * @param companyName 公司名称
	 * @return
	 */
	public String getCompanyList(int i, String companyName) {
		List<CustomerRelationShip> companyList = preSalesManMgrDao.getCompanyList(i,companyName);
		StringBuilder result = new StringBuilder();
		for (CustomerRelationShip ship : companyList) {
			switch (i) {
			case 1:
				result.append("'").append(ship.getOwnedCompany()).append("',");
				break;
			default:
				result.append("'").append(ship.getId()).append("',");
				break;
			}
		}
		
		switch (companyList.size()) {
		case 0:
			return "";
		default:
			return result.toString().substring(0, result.length()-1);
		}
	}
	
	//单独添加信息
	public boolean addSalesManByOne(PreSalesManMgr salesMan) throws Exception{
		if(!preSalesManMgrDao.hasObjByNoAndName(salesMan)){
			preSalesManMgrDao.getHibernateTemplate().save(salesMan);
			return true;
		}
		return false;
	}

	/**
	 * 条件查询
	 * @param page
	 * @param searchMap
	 * @return
	 * @author Damian
	 * @since 2017-04-12
	 */
	public Page listPages(Page page, Map searchMap) throws Exception{
		return preSalesManMgrDao.listPages(page, searchMap);
	}

}
