package org.hpin.reportdetail.service;

import java.util.List;
import java.util.Map;

import org.hpin.common.core.orm.BaseService;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.dao.ErpExpressInfoDao;
import org.hpin.reportdetail.entity.ErpExpressInfo;
import org.hpin.reportdetail.entity.ErpExpressInfoTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpExpressInfoService")
@Transactional()
public class ErpExpressInfoService extends BaseService {

	@Autowired
	ErpExpressInfoDao expressInfoDao;
	
	public void saveExpressInfoTemp(ErpExpressInfoTemp erpExpressInfoTemp) throws Exception{
		expressInfoDao.save(erpExpressInfoTemp);
	}
	
	/**
	 * 根据快递公司中午名字，获取快递公司对应的拼音
	 * @param companyName
	 * @author tangxing
	 * @date 2016-8-24下午12:18:35
	 */
	public Map<String,Object> getCompanyPYByName(String companyName){
		return expressInfoDao.getExpressPY(companyName);
	}
	
	/**
	 * 获取所有未签收的ErpExpressInfoTemp
	 * @return
	 * @author tangxing
	 * @date 2016-8-24下午3:19:11
	 */
	public List<ErpExpressInfoTemp> getExpressInfoTempList(){
		return expressInfoDao.getExpressInfoTempList();
	}
	
	public List<ErpExpressInfoTemp> getExpressInfoTempByNoCompany(String expressNo,String expressCompanyCode){
		return expressInfoDao.getExpressInfoTempByNoCompany(expressNo, expressCompanyCode);
	}
	
	public List<ErpExpressInfo> getExpressInfoByNoCompany(String expressNo,String expressCompanyCode){
		return expressInfoDao.getExpressInfoByNoCompany(expressNo, expressCompanyCode);
	}
	
	public void updateExpressInfo(ErpExpressInfo expressInfo) throws Exception{
		expressInfoDao.update(expressInfo);
	}
	
	public void updateExpressInfoTemp(ErpExpressInfoTemp expressInfoTemp) throws Exception{
		expressInfoDao.update(expressInfoTemp);
	}
	
	public List<ErpCustomer> getCustomerByName(String name){
		return expressInfoDao.getCustomerByName(name);
	}
}
