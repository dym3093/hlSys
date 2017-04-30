package org.hpin.reportdetail.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.dao.ErpPrintTaskContentDao;
import org.hpin.reportdetail.dao.ErpRepeatPrintTaskDao;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.hpin.reportdetail.entity.ErpRepeatPrintTask;
import org.hpin.reportdetail.entity.vo.ErpRepeatPrintEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Carly
 * @since 2016年9月19日17:24:10
 */
@Service(value = "org.hpin.reportdetail.service.ErpRepeatPrintTaskService")
@Transactional()
@SuppressWarnings({"rawtypes","unchecked"})
public class ErpRepeatPrintTaskService extends BaseService {

	@Autowired
	ErpRepeatPrintTaskDao dao;
	@Autowired
	ErpPrintTaskContentDao taskContentDao;
	
	Logger logger = Logger.getLogger(ErpRepeatPrintTaskService.class);
	
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}

	/**
	 * @param searchMap
	 * @return 通过code获取需要重复打印的套餐
	 */
	public List<ErpPrintTaskContent> getPdfContentInfoByCode(Page page, Map searchMap) {
		return dao.getPdfContentInfoByCode(page, searchMap);
	}
	
	public void getPdfContentInfoByCode(Page page, String codes) {
		dao.getPdfContentInfoByCode(page, codes);
	}

	/**
	 * @param ids
	 * @return 手动添加重复打印任务
	 */
	public boolean addRepeatPrintTask(String ids) throws Exception{
		boolean flag = false;
		User user = (User) HttpTool.getSession().getAttribute("currentUser");
		List<ErpPrintTaskContent> pdfContentList = dao.getPdfContentInfo(ids);
		List<ErpRepeatPrintTask> repeatlist = new ArrayList<ErpRepeatPrintTask>();		//重复打印表
		List<ErpPrintTaskContent> contentlist = new ArrayList<ErpPrintTaskContent>();	//内容表
		Date date = new Date();
		String batchNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		for(ErpPrintTaskContent content:pdfContentList){
			String repeatTaskId = UUID.randomUUID().toString().replace("-", "");	//生成id
			repeatlist.add(getRepeatPrintTask(content,date,user,"0",batchNo,repeatTaskId));
			contentlist.add(getPrintTaskContent(content, date, user,batchNo,repeatTaskId));
		}
		try {
			dao.getHibernateTemplate().saveOrUpdateAll(repeatlist);
			taskContentDao.getHibernateTemplate().saveOrUpdateAll(contentlist);
			flag=true;
		} catch (Exception e) {
			logger.error("ErpRepeatPrintTaskService addRepeatPrintTask", e);
		}
		return flag;
	}

	/**
	 * @param file
	 * @return 获取上传的数据
	 */
	public String getRepeatPrintTaskData(File file) throws Exception{
		List <Map<String, String>> resultList =org.hpin.common.util.ExcelUtils.importSettlementExcel(file);
		StringBuilder codes = new StringBuilder();
		for(Map<String, String> map : resultList){
			if(!map.get("0").equals("")){
				codes.append("'"+map.get("0")+"',");
			}
		}
		String code = codes.substring(0,codes.length()-1);
		return code;
	}

	/**
	 * @param list
	 * @return 根据导入的code保存
	 */
	public int[] insertImportData2Oracle(List<Map<String, String>> list) {
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		Date date = new Date();
		int [] arr = new int[3];
		int noCodeCount = 0;	//从pdfcontent表中没有查询到该数据的计数
		int codeCount = 0;		//找到的计数
		int moreCount = 0;		//多个条码
		List<ErpRepeatPrintTask> repeatlist = new ArrayList<ErpRepeatPrintTask>();		//重复打印表
		List<ErpPrintTaskContent> contentlist = new ArrayList<ErpPrintTaskContent>();	//内容表
		String batchNo = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
		for(Map<String, String> map : list){
			List<ErpPrintTaskContent> pdfContentList = dao.getPdfContentInfoByCode(map.get("0"));
			if(pdfContentList.size()==0){
				noCodeCount++;
			}else if (pdfContentList.size()==1) {
				ErpPrintTaskContent content = pdfContentList.get(0);
				String repeatTaskId = UUID.randomUUID().toString().replace("-", "");	//生成id
				repeatlist.add(getRepeatPrintTask(content, date, user,"1",batchNo,repeatTaskId));
				contentlist.add(getPrintTaskContent(content, date, user,batchNo,repeatTaskId));
				codeCount++;
			}else{
				moreCount++;
			}
		}
		try {
			dao.getHibernateTemplate().saveOrUpdateAll(repeatlist);		
			taskContentDao.getHibernateTemplate().saveOrUpdateAll(contentlist);
			arr[0] = codeCount;
			arr[1] = noCodeCount;
			arr[2] = moreCount;
		} catch (Exception e) {
			logger.error("ErpRepeatPrintTaskService insertImportData2Oracle",e);
		}
		return arr;
		
	}
	
//	private List<ErpRepeatPrintTask> createRepeatPrintTaskData(List <Map<String, String>> resultList){
//		User user= (User) HttpTool.getSession().getAttribute("currentUser");
//		List<ErpRepeatPrintTask> list = new ArrayList<ErpRepeatPrintTask>();
//		Date date = new Date();
//		for(Map<String, String> map : resultList){
//			ErpRepeatPrintTask repeatTask = new ErpRepeatPrintTask();
//			repeatTask.setCode(map.get("0"));		//条形码
//			repeatTask.setUserName(map.get("1"));	//用户姓名
//			repeatTask.setGender(map.get("2"));		//性别
//			repeatTask.setAge(map.get("3"));		//年龄
//			repeatTask.setCombo(map.get("4"));		//套餐
//			repeatTask.setCreateUser(user.getAccountName());
//			repeatTask.setCreateTime(date);
//			list.add(repeatTask);
//		}
//		return list;
//	}
	
	/**
	 * @param content
	 * @param date
	 * @param user
	 * @param isManuallyAdd 是否是excel导入，0不是，1是
	 * @param repeatTaskId 
	 * @return 重复打印任务表
	 */
	private ErpRepeatPrintTask getRepeatPrintTask(ErpPrintTaskContent content,Date date,
			User user,String isManuallyAdd,String batchNo, String repeatTaskId){
		ErpRepeatPrintTask task = new ErpRepeatPrintTask();
		task.setId(repeatTaskId);
		task.setCode(content.getCode());
		task.setUserName(content.getUserName());
		task.setAge(content.getAge());
		task.setGender(content.getGender());
		task.setCombo(content.getCombo());
		task.setCreateUser(user.getAccountName());
		task.setCreateTime(date);
		task.setIsDelete("0");
		task.setIsManuallyAdd(isManuallyAdd);
		task.setPdfContentId(content.getId());
		task.setBatchNo(batchNo);
		task.setIsPrint("0");
		task.setReportType(Integer.valueOf(content.getType()));
		return task;
	}
	
	/**
	 * @param content
	 * @param date
	 * @param user
	 * @param batchNo
	 * @param repeatTaskId 重复打印表的id
	 * @return
	 */
	private ErpPrintTaskContent getPrintTaskContent(ErpPrintTaskContent content,Date date,User user,String batchNo, String repeatTaskId){
		ErpPrintTaskContent taskContent = new ErpPrintTaskContent();
		String province = content.getProvince();
		String city = content.getCity();
		String branchCompanyId = content.getBranchCompanyId();
		String ownedCompanyId = content.getOwnedCompanyId();
		String combo = content.getCombo();
		String saleMan = content.getSaleman();
		String filePath = content.getFilePath();
		String customerId = content.getCustomerId();
		String code = content.getCode();
		String userName = content.getUserName();
		String gender = content.getGender();
		String age = content.getAge();
		String dept = content.getDept();
		String projectCode = content.getProjectCode();
		String type = content.getType();
		int reportType = 0;
		if(content.getType()!=null){
			reportType = Integer.valueOf(content.getType());
		}
		taskContent.setPdfContentId(repeatTaskId);
		taskContent.setProvince(province);
		taskContent.setCity(city);
		taskContent.setBranchCompanyId(branchCompanyId);
		taskContent.setOwnedCompanyId(ownedCompanyId);
		taskContent.setCombo(combo);
		taskContent.setSaleman(saleMan);
		taskContent.setFilePath(filePath);
		taskContent.setPs("0");
		taskContent.setIsManuallyAdd("1");
		taskContent.setCustomerId(customerId);
		taskContent.setCode(code);
		taskContent.setUserName(userName);
		taskContent.setGender(gender);
		taskContent.setAge(age);
		taskContent.setDept(dept);
		taskContent.setProjectCode(projectCode);
		taskContent.setType(type);
		taskContent.setReportType(reportType);
		taskContent.setCreateTime(date);
		taskContent.setCreateUser(user.getAccountName());
		taskContent.setBatchNo(batchNo);
		taskContent.setIsRepeatPrint(1);
		return taskContent;
	}

	/**
	 * @param ids 
	 * 根据勾选的添加重复打印任务
	 */
	public void saveRepeatPrintTask(String ids) throws Exception{
		User user= (User) HttpTool.getSession().getAttribute("currentUser");
		Date date = new Date();
		List<ErpPrintTaskContent> list = dao.getPdfContentInfo(ids);
		List<ErpRepeatPrintTask> repeatlist = new ArrayList<ErpRepeatPrintTask>();		//重复打印表
		List<ErpPrintTaskContent> contentlist = new ArrayList<ErpPrintTaskContent>();	//内容表
		String batchNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		for(ErpPrintTaskContent content:list){
			String repeatTaskId = UUID.randomUUID().toString().replace("-", "");	//生成id
			repeatlist.add(getRepeatPrintTask(content,date,user,"0",batchNo,repeatTaskId));
			contentlist.add(getPrintTaskContent(content, date, user,batchNo,repeatTaskId));
		}
		dao.getHibernateTemplate().saveOrUpdateAll(repeatlist);
		taskContentDao.getHibernateTemplate().saveOrUpdateAll(contentlist);
		
	}
}
