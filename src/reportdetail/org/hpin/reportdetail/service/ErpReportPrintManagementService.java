package org.hpin.reportdetail.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.dao.ErpReportPrintManagementDao;
import org.hpin.reportdetail.entity.ErpReportdetailManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.reportdetail.service.ErpReportPrintManagementService")
@Transactional()
public class ErpReportPrintManagementService extends BaseService {
	@Autowired
	private ErpReportPrintManagementDao dao;
	Logger logger = Logger.getLogger(ErpReportPrintManagementService.class);
	
	public void findByPages(Page<ErpReportdetailManagement> page, Map<String,Object> searchMap) {
		dao.findByPages(page, searchMap);
	}
	
	public List<ErpReportdetailManagement> getReportPrintData(File file,String fileName, String accountName) throws ParseException {
		List<Map<String, String>> resultList = null;
		try {
			resultList = org.hpin.common.util.ExcelUtils.xlsExcel(file);
		} catch (Exception e) {
			logger.error("ErpReportPrintManagementService getReportPrintData",e);
			e.printStackTrace();
		}
		return createSettlementObj(resultList,fileName,accountName);
	}

	private List<ErpReportdetailManagement> createSettlementObj(List<Map<String, String>> resultList,String fileName,String userName) throws ParseException {
		List<ErpReportdetailManagement> list = new ArrayList<ErpReportdetailManagement>();
		Date date = new Date();
		String batchNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);//批次号
		for(Map<String, String> map : resultList){
			
			ErpReportdetailManagement management = new ErpReportdetailManagement();
			management.setName(map.get("0").trim());
			management.setCode(map.get("1").trim());
			management.setGender(map.get("2").trim());
			management.setExcelName(fileName);
			management.setCreateUser(userName);
			management.setCreateTime(date);
			management.setIsDelete("0");
			management.setBatchNo(batchNo);
			management.setState("0");
			list.add(management);
		}
		return list;
	}

	/**
	 * @param excelName 文件名
	 * 确认该打印为不打印任务（state）为1
	 */
	public int updateState1(String batchNo) {
		String sql = "update erp_reportdetail_management set state='1' where batchNo=?";
		return dao.getJdbcTemplate().update(sql, batchNo);
	}
	
	/**
	 * @param excelName 文件名
	 * 取消该打印批次状态（state）为2
	 */
	public int updateState2(String batchNo) {
		String sql = "update erp_reportdetail_management set state='2' where batchNo=?";
		return dao.getJdbcTemplate().update(sql, batchNo);
	}

	/**
	 * @param name
	 * @param code
	 * @param gender
	 * @return 通过姓名,条形码,性别查询是否有重复数据
	 */
	public int isRepeatData(String name, String code, String gender) {
		String sql = "select count(1) from erp_reportdetail_management where name=? and code=? and gender=?";
		return dao.getJdbcTemplate().queryForInt(sql,name,code,gender);
	}
	
}
