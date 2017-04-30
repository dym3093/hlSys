package org.hpin.children.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hpin.children.dao.ErpGroupOrderInfoDao;
import org.hpin.children.entity.ErpOrderInfo;
import org.hpin.children.entity.ExportOrderData;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.children.service.ErpGroupOrderInfoService")
@Transactional()
@SuppressWarnings({"rawtypes" })
public class ErpGroupOrderInfoService extends BaseService {
	
	@Autowired
	private ErpGroupOrderInfoDao egoiDao;

	public List<Map<String, Object>> findGroupOrderInfo(Page page, Map searchMap) {
		
		return egoiDao.findGroupOrderInfo(page, searchMap);
	}
	
	public List findByPage(Page page,Map searchMap) {
		return egoiDao.findByPage(page, searchMap);
	}
	
	public List  test(Page page,Map map) {
		return egoiDao.test(page,map);
	}
	
	/**
	 * @param file
	 * @return excel读取到的数据
	 * @throws IOException
	 * @throws ParseException
	 */
	public List getCustomerImportData(File file,String userName) throws IOException, ParseException {
		List <Map<String, String>> resultList =org.hpin.common.util.ExcelUtils.importSettlementExcel(file);
		return createSettlementObj(resultList,userName);
	}
	
	private List<ErpOrderInfo> createSettlementObj(List<Map<String, String>> resultList,String userName) throws ParseException {
		List<ErpOrderInfo> list = new ArrayList<ErpOrderInfo>();
		Date date = new Date();
		for(Map<String, String> map : resultList){
			ErpOrderInfo customerJY = new ErpOrderInfo();
			customerJY.setOrderNo(map.get("0").toString());
			customerJY.setName(map.get("1").toString());
			customerJY.setIdNo(map.get("2").toString());
			customerJY.setSex(map.get("3").toString());
			customerJY.setAge(new BigDecimal(map.get("4").toString()));
			customerJY.setSetMealName(map.get("5").toString());
			customerJY.setStatus("处理中");
			customerJY.setPhone(map.get("6").toString());
			customerJY.setSampleAddress(map.get("7").toString());
			customerJY.setReportAddress(map.get("8").toString());
			customerJY.setGuardianName(map.get("9").toString());
			customerJY.setGuardianPhone(map.get("10").toString());
			customerJY.setRelationship(map.get("11").toString());
			customerJY.setFamilyHistory(map.get("12").toString());
			customerJY.setHeight(map.get("13").toString());
			customerJY.setWeight(map.get("14").toString());
			customerJY.setNote(map.get("15").toString());
			customerJY.setCreateUser(userName);
			customerJY.setCreateDate(date);
			customerJY.setIsDelete("1");
			list.add(customerJY);
		}
		return list;
	}

	/**
	 * @param list
	 * @return 将读取到的数据存放
	 */
	public int getImportData2Oracle(List<ErpOrderInfo> list) {
		int count = 0;
		for(ErpOrderInfo coi : list){
			try {
				egoiDao.save(coi);
				count++;
			} catch (Exception e) {
				count--;
				System.out.println(e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 获取儿童套餐下拉框的值
	 */
	public List<Map<String, Object>> getSelectedInput() {
		return egoiDao.getSelectedInput();
	}

	public int isSaleMan(String accountName) {
		String sql = "select count(1) from um_user where account_name='"+accountName+"' and dept_name='远盟销售部'";
		return egoiDao.getJdbcTemplate().queryForInt(sql);
	}
	
	
}
