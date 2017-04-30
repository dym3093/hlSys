package org.hpin.reportdetail.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.reportdetail.entity.ErpExpressInfoTemp;
import org.hpin.reportdetail.service.ErpExpressInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 快递信息入库入口
 * @author tangxing
 * @date 2016-8-23下午4:04:15
 */

public class ExpressInfoEntrance {
	private Logger log = Logger.getLogger(ExpressInfoEntrance.class);
	
	@Autowired
	ErpExpressInfoService erpExpressInfoService;
	
	public ExpressInfoEntrance(){
		
	}
	
	
	/**
	 * 保存
	 * return "success":成功
	 * return "notFound":未找到快递公司code
	 * return "fail":	保存失败
	 * @param expressNo
	 * @param expressCompany
	 * @return
	 * @author tangxing
	 * @date 2016-8-24下午6:26:14
	 */
	public String saveExpressInfoTemp(String expressNo,String expressCompany){
		String flag = "";
		String str = "";
		try {
			str = save(expressNo,expressCompany);
			if(str.equals("success")){
				flag = str;
			}else if(str.equals("notFound")){
				flag = str;
			}
		} catch (Exception e) {
			flag = "fail";
			log.error("ExpressInfoEntrance save--"+e);
		}
		return flag;
	}
	
	private String save(String expressNo,String expressCompany) throws Exception{
		String flag = "success";
		Map<String, Object> pamResult = new HashMap<String, Object>();
		ErpExpressInfoTemp erpExpressInfoTemp = new ErpExpressInfoTemp();
		String expressCompanyTable = "";		//数据库中的快递公司
		
		erpExpressInfoTemp.setExpressNo(expressNo);
		
		pamResult = erpExpressInfoService.getCompanyPYByName(expressCompany);
		if(pamResult!=null&&pamResult.size()>0){
			log.info("pamResult--"+pamResult.toString());
			expressCompanyTable = (String) pamResult.get("COMPANY_CODE");	//对应的快递编号
		}else{		//没根据中文找到快递公司code，直接退出方法
			flag = "notFound";
			return flag;
		}
		
		erpExpressInfoTemp.setExpressCompany(expressCompany);			//快递公司名字
		erpExpressInfoTemp.setExpressCompanyCode(expressCompanyTable);	//快递公司code
		erpExpressInfoTemp.setStatus("0");
		erpExpressInfoTemp.setAccessCount(0);
		erpExpressInfoTemp.setCreateTime(new Date());
		erpExpressInfoService.saveExpressInfoTemp(erpExpressInfoTemp);
		return flag;
	}
	
	public List<ErpCustomer> getCustomerByName(String name){
		return erpExpressInfoService.getCustomerByName(name);
	}

}
