package org.hpin.events.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpCustomerSyncDao;
import org.hpin.events.entity.ErpCustomerSync;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.events.service.ErpCustomerSyncService")
@Transactional()
public class ErpCustomerSyncService extends BaseService {
	private Logger log = Logger.getLogger(ErpCustomerSyncService.class);
	
	@Autowired
	ErpCustomerSyncDao dao;

//	private static final String ADDRESS = "http://172.16.212.171:8199/websGene/geneReport?wsdl";
	
	private static final String ADDRESS = "http://gene.healthlink.cn:8088/websGene/geneReport?wsdl";

	/**
	 * @return
	 * @author machuan
	 * @date  2016年11月25日
	 */
	@SuppressWarnings("unchecked")
	public List<ErpCustomerSync> findListCustomerSync() {
		String hql = "from ErpCustomerSync where status='0'";
		return dao.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 修改需要同步的客户信息
	 * @param customerSync
	 * @author machuan
	 * @date  2016年11月25日
	 */
	public void updateSyncInfo(ErpCustomerSync customerSync) {
		dao.update(customerSync);
	}
	
	public String getSyncXmlRetCode(ErpCustomerSync customerSync){
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = df.format(new Date());
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, 1);
		String endTime = df.format(c.getTime());
		String idType = "身份证";
		if(StringUtils.isNotBlank(customerSync.getDocumentType())&&"1010106".equals(customerSync.getDocumentType())){
			idType = "其他";
		}
		String xmlInfo = "<Rows>"
				+"<userName>"+customerSync.getName()+"</userName>"
				+"<sex>"+customerSync.getSex()+"</sex>"
				+"<mobile1>"+customerSync.getPhone()+"</mobile1>"
				+"<idcardType>"+idType+"</idcardType>"
				+"<idcardNum>"+customerSync.getIdno()+"</idcardNum>"
				+"<province>"+customerSync.getProvice()+"</province>"
				+"<city>"+customerSync.getCity()+"</city>"
				+"<policyNum>"+customerSync.getCode()+"</policyNum>"
				+"<salesTeam>"+customerSync.getSalesMan()+"</salesTeam>"
				+"<belongUnits>"+customerSync.getBranchCompany()+"</belongUnits>"
				+"<startTime>"+startTime+"</startTime>"
				+"<endTime>"+endTime+"</endTime>"
				+"<dataType>"+"01"+"</dataType>"
				+"</Rows>";
				
		YmGeneReportServiceImpl impl;
		String retCode = "";
		try {
			YmGeneReportServiceImplServiceLocator locator = new YmGeneReportServiceImplServiceLocator();
			locator.setYmGeneReportServiceImplPortEndpointAddress(ADDRESS);
			log.info(ADDRESS);
			impl = locator.getYmGeneReportServiceImplPort();
			retCode = impl.productMemberImmediate(xmlInfo);
			log.info("getSyncXmlRetCode==success!");
		} catch (Exception e) {
			log.error("getSyncXmlRetCode==error: "+e);
		}
		return retCode;
	}
}
