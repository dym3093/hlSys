package org.hpin.events.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipProDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.exception.MyException;
import org.hpin.common.util.*;
import org.hpin.common.util.DateUtil;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.*;
import org.hpin.events.entity.*;
import org.hpin.events.entity.vo.EventCustomers;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.reportdetail.entity.vo.ErpReportCustomerVo;
import org.hpin.reportdetail.util.ZipFileUtil;
import org.hpin.settlementManagement.dao.ErpEventsComboPriceDao;
import org.hpin.webservice.service.YmGeneReportServiceImpl;
import org.hpin.webservice.service.YmGeneReportServiceImplServiceLocator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.ymjy.combo.dao.ComboDao;
import org.ymjy.combo.entity.Combo;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import java.io.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = "org.hpin.events.service.ErpCustomerService")
@Transactional()
public class ErpCustomerService extends BaseService {
	private Logger log = Logger.getLogger(ErpCustomerService.class);
	
	@Autowired
	ErpCustomerDao dao;
	@Autowired
	private ErpCustomerBakDao bakDao;
	@Autowired
	ComboDao comboDao;
	@Autowired
	ErpEventsDao eventsDao;
	@Autowired
	ErpEventsComboPriceDao erpEventsComboPriceDao;
	@Autowired
	private ErpScheduleJobDao erpScheduleJobDao;
	@Autowired
	private CustomerRelationshipProDao customerRelationshipProDao;
	@Autowired
	private CustomerRelationshipDao customerRelationshipDao; //支公司dao
	@Autowired
	private ErpBarCodeDetailDao erpBarCodeDetailDao; //条码明细Dao
	@Autowired
	private ErpQRCodeDao erpQRCodeDao ; //无创二维码dao;

	private String localPath;

	private String downloadContext;

//	private static final String ADDRESS = "http://172.16.212.171:8199/websGene/geneReport?wsdl";

	private static final String ADDRESS = "http://gene.healthlink.cn:8088/websGene/geneReport?wsdl";

	//根据ID更改会员表的报告状态
	public static final String UPDATE_CUST_BY_ID = " update ERP_CUSTOMER set status_ym = ? , c.update_time = sysdate where id = ? ";

	//根据打印任务ID更改会员表状态
	public static final String UPDATE_CUST_BY_PRINT_TASK_ID = " update erp_customer c set c.status_ym = ?, c.update_time = sysdate  where c.code in " +
													   "(select r.code from erp_print_task_content r where r.printtaskno in " +
			                                           " (select p.printtaskno from erp_printtask p where p.isdelete = 0 and p.id = ? )) " +
			                                           " and c.is_deleted = 0 ";
	//根据报告快递信息修改会员表状态
	public static final String UPDATE_CUST_BY_EXPRESS_INFO = " update erp_customer c set c.status_ym = ? , c.update_time = sysdate where c.is_deleted = 0 events_no = ? and code = ? and name = ? ";

	public void updateSampleDatebyEventsNo(ErpEvents events) {
		dao.updateSampleDatebyEventsNo(events);
	}

	/**
	 * 保存
	 *
	 * @param erpCustomer
	 */
	public void save(ErpCustomer erpCustomer) {
		dao.save(erpCustomer);
	}

	/**
	 * 查找比对失败任务
	 * @param page
	 * @param searchMap
	 * @return
	 * @author tangxing
	 * @date 2016-6-21上午11:42:40
	 */
	public List<ErpCustomerImpFailBtach> listFailBtach(Page page,Map searchMap){
		return dao.listFailBtach(page, searchMap);
	}

	public String isNotRepeatcode(String code) throws Exception {
		return dao.isNotRepeatcode(code);
	}

	/**
	 * 根据场次号查询客户
	 */
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	/**
	 * 根据场次号查询没报告客户
	 */
	public List findByPageNoReport(Page page, Map searchMap) {
		return dao.findByPageNoReport(page, searchMap);
	}

	/* 非物理删除 */
	public void delete(List<ErpCustomer> list) {
		for (ErpCustomer customer : list) {
			dao.update(customer);
		}
	}

	/**
	 * 根据比对批次失败批次Id查找customer
	 * @param FailBtachId
	 * @author tangxing
	 * @date 2016-6-21下午5:27:29
	 */
	public List<ErpCustomer> getCustomerByFailBtachId(String FailBtachId){
		return dao.getCustomerByFailBtachId(FailBtachId);
	}

	public ErpCustomerImpFailBtach getfailBtachByid(String id){
		return dao.getFailBtachById(id);
	}

	/**
	 * 更新
	 *
	 * @param customer
	 */
	public void updateInfo(ErpCustomer customer) {
		Date now = new Date();
		customer.setUpdateTime(now);
		ErpCustomer _erpCustomer = (ErpCustomer) dao.findById(ErpCustomer.class, customer.getId());
		//2016-11-25  基因系统中当修改完善基因客户信息时满足如下条件 保存入另外一张表 定时更新CRM系统的信息 create by sirius.ma
		boolean flag = checkIsUpdated(customer,_erpCustomer);
		if(flag){
			//将数据保存到sync表中
			ErpCustomerSync customerSync = new ErpCustomerSync();
			customerSync.setCustomerId(customer.getId());
			customerSync.setName(customer.getName());
			customerSync.setSex(customer.getSex());
			customerSync.setPhone(customer.getPhone());
			customerSync.setDocumentType(customer.getDocumentType());
			customerSync.setIdno(customer.getIdno());
			customerSync.setProvice(_erpCustomer.getProvice());
			customerSync.setCity(_erpCustomer.getCity());
			customerSync.setCode(customer.getCode());
			//销售为远盟销售员
			customerSync.setSalesMan(customer.getYmSalesman());
			//公司为支公司名称  从查出来的数据里面取
			customerSync.setBranchCompany(_erpCustomer.getBranchCompany());
			//默认设置状态为0
			customerSync.setStatus("0");
			customerSync.setCreateTime(_erpCustomer.getCreateTime());
			customerSync.setUpdateTime(customer.getUpdateTime());
			//从数据库判断这个ID是否有重复数据
			ErpCustomerSync _customerSync = dao.getCustomerSyncByCId(customer.getId());
			if(_customerSync!=null){
				BeanUtils.copyProperties(_customerSync, customerSync);
				dao.update(_customerSync);
			}else{
				dao.save(customerSync);
			}
		}
		BeanUtils.copyProperties(_erpCustomer, customer);
//		_erpCustomer.setUpdateTime(now);
		dao.update(_erpCustomer);
	}

	/**
	 * @return
	 * @author machuan
	 * @param _erpCustomer
	 * @param customer
	 * @date  2016年11月25日
	 */
	private boolean checkIsUpdated(ErpCustomer customer, ErpCustomer _erpCustomer) {
		//条件1.修改时间和录入时间不是同一天；
		Calendar cc = Calendar.getInstance();
		Calendar cu = Calendar.getInstance();
		cc.setTime(_erpCustomer.getCreateTime());
		cu.setTime(customer.getUpdateTime());
		cc.set(Calendar.HOUR_OF_DAY, 0);
		cc.set(Calendar.MINUTE,0);
		cc.set(Calendar.SECOND,0);
		cu.set(Calendar.HOUR_OF_DAY, 0);
		cu.set(Calendar.MINUTE,0);
		cu.set(Calendar.SECOND,0);
		cu.add(Calendar.DATE, -2);
		boolean flag1 = cu.getTimeInMillis()>=cc.getTimeInMillis();
		//条件2.当修改后的条形码，姓名，电话，证件号都非空情况下和原数据比较，只要有一个不相同为true。
		boolean flag2 = false;
		if(StringUtils.isNotBlank(customer.getCode())&&StringUtils.isNotBlank(customer.getName())&&StringUtils.isNotBlank(customer.getPhone())&&StringUtils.isNotBlank(customer.getIdno())){
			flag2 = !(customer.getCode().equals(_erpCustomer.getCode())&&
			customer.getName().equals(_erpCustomer.getName())&&
			customer.getPhone().equals(_erpCustomer.getPhone())&&
			customer.getIdno().equals(_erpCustomer.getIdno()));

		}
		return flag1&&flag2;
	}

	/**
     * 根据条形码查询客户
     */
	public List<ErpCustomer>  getCustomerByCode(String code){
		return dao.getCustomerByCode(code);
	}

	/**
     * 根据条形码查询客户
     */
	public List<ErpCustomerException>  getCustomerExceptionByCode(String code){
		return dao.getCustomerExceptionByCode(code);
	}

	@SuppressWarnings("unchecked")
	public String saveCustomere(File file, String afileName, String eventsNo)
			throws ParseException {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		List<ErpEvents> list = eventsDao.findByProperty(ErpEvents.class,"eventsNo", eventsNo, null, null);
		ErpEvents events_temp=list.get(0);
		String provice = events_temp.getProvice();
		String city = events_temp.getCity();
		String bc = events_temp.getBranchCompany();
		String oc = events_temp.getOwnedCompany();//所属公司名称
		//根据批次号判定检测机构  add By YoumingDeng 2016-09-26 start
		String batchNo = events_temp.getBatchNo();
		String testInstitution = "南方";
		if(batchNo.startsWith("JY")){
			testInstitution = "金域";
		}else if(batchNo.startsWith("JY")){
			testInstitution = "吉思朗";
		}
		// add By YoumingDeng 2016-09-26 end
		String branchCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getBranchCompanyId())){
			branchCompanyId = events_temp.getBranchCompanyId();
		}
		String ownedCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getOwnedCompanyId())){
			ownedCompanyId=events_temp.getOwnedCompanyId();}
		String ymSalesman=events_temp.getYmSalesman();
		String fileName = "";
		//导入成功的数量
		int successCount = 0;
		//异常数据的数量
		int repeatCount = 0;
		//导入失败的数量
		int failedCount = 0;
		if (afileName != null) {
			fileName = file.getAbsolutePath();
			long a = System.currentTimeMillis();
			InputStream is = null;
			XSSFWorkbook xssfWorkbook = null;
			try {
				is = new FileInputStream(fileName);
				xssfWorkbook = new XSSFWorkbook(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 循环工作表Sheet
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Date importDate = new Date();
			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				ErpCustomer customer = new ErpCustomer();
				ErpCustomerException customerException = new ErpCustomerException();
				//该行如果为空就跳到下一行
				if (xssfRow == null||(StringUtils.isBlank(getValue(xssfRow.getCell(1)))&&(getValue(xssfRow.getCell(0))).length()<=4)) {
					//因为导入的EXCEL可能有异常情况  所以判定条形码少于4位和姓名为空也为空数据  直接跳过
					continue;
				}
				//2016-11-30 新增校验规则
				//第一步 检查 错误数据：“条形码”、“姓名”、“性别”任意一项为空
				String code = getValue(xssfRow.getCell(0));
				String name = getValue(xssfRow.getCell(1));
				String sex = getValue(xssfRow.getCell(5));
				String idNo = getValue(xssfRow.getCell(3));
				String phone = getValue(xssfRow.getCell(2));
				boolean errorFlag = false;
				errorFlag = checkError(code,name,sex);
				//如果有一项为空，计入导入失败
				if(errorFlag){
					failedCount++;
					continue;
				}
				//第二步 都不为空 验证重复性 首先验证条形码是否重复  需要验证正常数据和异常数据两张表
				//2016-12-14需求变动，只需要验证正常数据
				boolean flag = false;
				//正常数据表
				List<ErpCustomer> listCustomer = getCustomerByCode(code);
				//条形码重复 则进行身份证比对
				for(ErpCustomer _erpCustomer : listCustomer){
					//身份证都不为空并且相等  则表示为同一条数据
					if(StringUtils.isNotBlank(idNo)&&_erpCustomer.getIdno().equals(idNo)){
						flag = true;
						break;
					}
					//身份证有一条为空或者两边都为空，手机号不为空并且相同也视为同一条数据
					if((StringUtils.isBlank(idNo)||StringUtils.isBlank(_erpCustomer.getIdno()))&&
							StringUtils.isNotBlank(_erpCustomer.getPhone())&&_erpCustomer.getPhone().equals(phone)){
						flag = true;
						break;
					}
				}
				//判定为重复数据，计入导入失败
				if(flag){
					failedCount++;
					continue;
				}
				/*****************************南方标准*********************************/
				//给customer赋值
				customer = setValueToCustomer(customer,xssfRow,provice,city,bc,oc,batchNo,
						testInstitution,branchCompanyId,ownedCompanyId,ymSalesman,importDate,eventsNo,currentUser,list);
				//客户信息进行校验。包括客户的性别，年龄，套餐名。
				//20161121--新增样本类型校验
				//20161130--新增套餐费用验证
				//20170117--新增身份证与性别验证
				System.out.println("customer---"+customer);
				List<Object> checkSexAgeComboList = checkSexAgeCombo(idNo,customer.getSex(),customer.getAge(),customer.getSetmealName(),events_temp.getCustomerRelationShipProId(),events_temp.getBranchCompanyId());
				//验证样本类型是否正确
				boolean saTypeFlag = false;
				String saTypeMsg = "样本类型不匹配";
				String sampleType = "";
				if(StringUtils.isNotBlank(customer.getSampleType())){
					Map<String,String> sampleTypeMap = new HashMap<String, String>();
					sampleTypeMap.put("口腔粘膜上皮细胞", "1010601");
					sampleTypeMap.put("口腔黏膜上皮细胞", "1010601");
					sampleTypeMap.put("唾液", "1010603");
					sampleTypeMap.put("血液", "1010604");
					sampleType = sampleTypeMap.get(customer.getSampleType());
					if(StringUtils.isNotBlank(sampleType)){
						customer.setSampleType(sampleType);
						saTypeFlag = true;
					}
				}
				//2016-12-14 新增需求 数据导入成功删除异常表中的重复数据  导入失败也删除异常表重复数据
				List<ErpCustomerException> exList = findAllRepeatInfo(customer);
				if(exList!=null&&!exList.isEmpty()){
					for(ErpCustomerException exception : exList){
						dao.delete(exception);
					}
				}
				if ((Boolean) checkSexAgeComboList.get(0)&&saTypeFlag) {
					successCount++;
					dao.save(customer);
				} else {
					repeatCount++;
					customerException.setEventsNo(eventsNo.trim()); // 场次号
					customerException.setCode(getValue(xssfRow.getCell(0))); // 条形码
					customerException.setName(getValue(xssfRow.getCell(1))); // 姓名
					customerException.setPhone(getValue(xssfRow.getCell(2))); // 电话
					customerException.setIdno(getValue(xssfRow.getCell(3))); // 身份证号
					customerException.setSetmealName(getValue(xssfRow.getCell(4))); // 套餐名
					customerException.setSex(getValue(xssfRow.getCell(5))); // 性别
					customerException.setAge(getValue(xssfRow.getCell(6))); // 年龄
					customerException.setSampleType(sampleType); // 样本类型
					customerException.setFamilyHistory(getValue(xssfRow.getCell(8))); // 家族疾病史
					customerException.setDepartment(getValue(xssfRow.getCell(9)));//部门
					customerException.setSalesMan(getValue(xssfRow.getCell(10))); // 保险营销员
					customerException.setSalesManNo(getValue(xssfRow.getCell(11)));//保险营销员工号
					customerException.setNote(getValue(xssfRow.getCell(12)));//备注
					String result = "";
					for(int i = 1;i<checkSexAgeComboList.size();i++){
						result+=checkSexAgeComboList.get(i)+"，";
					}
					if(!saTypeFlag){
						result+=saTypeMsg+"，";
					}
					customerException.setResult(result.substring(0,result.length()-1)+"。");//失败原因
					dao.save(customerException);
				}
			}
			System.out.println("耗时 :" + (System.currentTimeMillis() - a)/ 1000f + " 秒 ");
		}//上传文件处理结束
		String success = "成功导入：" + successCount + "条! 未导入数据：" + failedCount + "条! 异常数据有：" + repeatCount + "条！";
		return success;
	}

	private List<ErpCustomerException> findAllRepeatInfo(ErpCustomer customer){
		//异常数据表
		List<ErpCustomerException> listCustomerExcption = getCustomerExceptionByCode(customer.getCode());
		List<ErpCustomerException> list = new ArrayList<ErpCustomerException>();
		//条形码重复 则进行身份证比对
		for(ErpCustomerException _erpCustomerException : listCustomerExcption){
			//身份证都不为空并且相等  则表示为同一条数据
			if(StringUtils.isNotBlank(customer.getIdno())&&_erpCustomerException.getIdno().equals(customer.getIdno())){
				list.add(_erpCustomerException);
				break;
			}
			//身份证有一条为空或者两边都为空，手机号不为空并且相同也视为同一条数据
			if((StringUtils.isBlank(customer.getIdno())||StringUtils.isBlank(_erpCustomerException.getIdno()))&&
					StringUtils.isNotBlank(_erpCustomerException.getPhone())&&_erpCustomerException.getPhone().equals(customer.getPhone())){
				list.add(_erpCustomerException);
				break;
			}
		}
		return list;
	}


	/**
	 * 验证条形码，姓名，性别是否有一项为空
	 * @param code
	 * @param name
	 * @param sex
	 * @return
	 * @author machuan
	 * @date  2016年11月30日
	 */
	private boolean checkError(String code, String name, String sex) {
		return !(StringUtils.isNotBlank(code)&&StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(sex));
	}

	/**
	 * @param customer
	 * @param xssfRow
	 * @return
	 * @author machuan
	 * @param list
	 * @param currentUser
	 * @param eventsNo
	 * @param importDate
	 * @param ymSalesman
	 * @param ownedCompanyId
	 * @param branchCompanyId
	 * @param testInstitution
	 * @param batchNo
	 * @param oc
	 * @param bc
	 * @param city
	 * @param provice
	 * @param xssfRow
	 * @param customer
	 * @param ymSalesman
	 * @param ownedCompanyId
	 * @param branchCompanyId
	 * @param testInstitution
	 * @param batchNo
	 * @param oc
	 * @param bc
	 * @param city
	 * @param provice
	 * @param list
	 * @param currentUser
	 * @param eventsNo
	 * @param importDate
	 * @date  2016年11月17日
	 */
	private ErpCustomer setValueToCustomer(ErpCustomer customer, XSSFRow xssfRow, String provice,
			String city, String bc, String oc, String batchNo, String testInstitution,
			String branchCompanyId, String ownedCompanyId, String ymSalesman, Date importDate,
			String eventsNo, User currentUser, List<ErpEvents> list) {
		customer.setProvice(provice);
		customer.setCity(city);
		customer.setEventsNo(eventsNo); // 场次号
		customer.setBranchCompanyId(branchCompanyId);//支公司ID
		customer.setBranchCompany(bc);// 支公司
		customer.setSamplingDate(list.get(0).getEventDate());
		customer.setIsDeleted(0);
		customer.setOwnedCompany(oc); // 所属公司
		customer.setOwnedCompanyId(ownedCompanyId);//所属公司ID
		customer.setCreateTime(importDate); // 创建时间
		customer.setCreateUserName(currentUser.getUserName());
		customer.setYmSalesman(ymSalesman);//远盟营销员
		customer.setCode(getValue(xssfRow.getCell(0))); // 条形码
		customer.setName(getValue(xssfRow.getCell(1))); // 姓名
		customer.setPhone(getValue(xssfRow.getCell(2))); // 电话
		customer.setIdno(getValue(xssfRow.getCell(3))); // 身份证号
		// 套餐名截掉前后空格，并且把英文括号换成中文括号  add by YoumingDeng 2016-09-19 start
		String combo = getValue(xssfRow.getCell(4)).trim(); //套餐名
		if(combo.contains("(")){
			combo = combo.replace("(", "（");
		}
		if(combo.contains(")")){
			combo = combo.replace(")", "）");
		}
		// 套餐名截掉前后空格，并且把英文括号换成中文括号  add by YoumingDeng 2016-09-19 end
		customer.setSetmealName(combo); // 套餐名
		customer.setSex(getValue(xssfRow.getCell(5))); // 性别
		customer.setAge(getValue(xssfRow.getCell(6))); // 年龄
		customer.setSampleType(getValue(xssfRow.getCell(7))); // 样本类型
		customer.setFamilyHistory(getValue(xssfRow.getCell(8))); // 家族疾病史
		customer.setDepartment(getValue(xssfRow.getCell(9)));//部门 	 add by YoumingDeng 2016-09-22
		customer.setSalesMan(getValue(xssfRow.getCell(10))); // 保险营销员
		//20170419 新增字段 营销员工号
		customer.setSalesManNo(getValue(xssfRow.getCell(11))); // 保险营销员工号
		customer.setNote(getValue(xssfRow.getCell(12)));//备注
		customer.setTestInstitution(testInstitution);//检测机构
		customer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错， add by DengYouming 2016-07-13
		customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));//会员报告状态， 150：样本已获取 add by YoumingDeng 2016-12-14
		//对于年龄为空，身份证号不为空且长度为15位或18位的客户信息自动计算年龄
		if((customer.getAge()==null||customer.getAge().equals(""))&&customer.getIdno()!=null){
			if(customer.getIdno().length()==15||customer.getIdno().length()==18){
				String tempage=getStrAge(customer.getIdno());
				customer.setAge(tempage);

			}
		}
		return customer;
	}

	/**
	 * 验证客户的性别，年龄，套餐名，套餐费用，套餐名和套餐费用 需要到套餐表里面去查
	 * @param sex
	 * @param age
	 * @param setmealName
	 * @return list中 第一个元素为验证正确或者失败 ，后面为备注信息
	 * @author machuan
	 * @param conpanyId  支公司ID
	 * @date  2016年11月17日
	 */
	private List<Object> checkSexAgeCombo(String idNo,String sex, String age, String setmealName, String customerRelationShipId, String conpanyId) {
		List<Object> list = new ArrayList<Object>();
		boolean sexFlag = false;
		String sexMessage = "性别错误";
		boolean ageFlag = false;
		String ageMessage = "年龄格式错误";
		boolean comboFlag = false;
		String comboMessage = "套餐名不匹配";
		boolean comboPriceFlag = false;
		String comboPriceMessage = "价格未维护";

		try{
		//验证性别是否正确
			if(("男").equals(sex)||("女").equals(sex)){
				//20170117新增身份证与性别比对
				String idSex = "";
				//15位身份证
				if(StringUtils.isNotBlank(idNo)&&idNo.length()==15){
					String sexNum = idNo.substring(14);
					Integer i = Integer.valueOf(sexNum);
					if(i%2==0){
						idSex = "女";
					}else{
						idSex = "男";
					}
				}
				//18位身份证
				if(StringUtils.isNotBlank(idNo)&&idNo.length()==18){
					String sexNum = idNo.substring(16,17);
					Integer i = Integer.valueOf(sexNum);
					if(i%2==0){
						idSex = "女";
					}else{
						idSex = "男";
					}
				}
				if(StringUtils.isNotBlank(idSex)){
					sexFlag = idSex.equals(sex);
				}else{
					sexFlag = true;
				}
			}
		}catch (Exception e) {
		}
		try{
			//验证年龄是否正确
			int ageNum = Integer.valueOf(age);
			if(ageNum<=120&&ageNum>=0){
				ageFlag = true;
			}
		}catch(Exception e){
			//如果是空字符串会转换错误，不用管,默认是false
		}
		//验证套餐名是否正确
		if(StringUtils.isNotBlank(setmealName)&&StringUtils.isNotBlank(customerRelationShipId)){
			List<Combo> combos = findByCustomer(customerRelationShipId);
			for(Combo combo : combos){
				if((setmealName.trim()).equals(combo.getComboName().trim())){
					comboFlag = true;
					break;
				}
			}
		}
		//验证套餐费用是否非空--20170309需求 判断项目ID 不需要项目编码
		/*String projectCode = "";
		CustomerRelationShipPro customerRelationShipPro = customerRelationshipProDao.findCustRelShipProById(CustomerRelationShipPro.class,customerRelationShipId);
		if(customerRelationShipPro!=null){
			projectCode = customerRelationShipPro.getProjectCode();
		}*/
		if(StringUtils.isNotBlank(setmealName)&&StringUtils.isNotBlank(conpanyId)&&StringUtils.isNotBlank(customerRelationShipId)){
			List<Map<String,Object>> priceList = findComboPrice(setmealName,conpanyId,customerRelationShipId);
			for(Map<String,Object> map : priceList){
				try{
					float price = ((BigDecimal)map.get("COMBO_PRICE")).floatValue();
					if(price>=0){
						comboPriceFlag = true;
					}
				}catch(Exception e){
				}
			}
		}

		list.add(sexFlag&&ageFlag&&comboFlag&&comboPriceFlag);
		if(!sexFlag){
			list.add(sexMessage);
		}
		if(!ageFlag){
			list.add(ageMessage);
		}
		if(!comboFlag){
			list.add(comboMessage);
		}
		if(!comboPriceFlag){
			list.add(comboPriceMessage);
		}
		return list;
	}

	/**
	 * 导入客户自动比对对应的场次
	 * @param file
	 * @param afileName
	 * @return
	 * @throws ParseException
	 * @author tangxing
	 * @date 2016-6-20上午11:39:46
	 */
	public String saveAllCustomere(File file, String afileName)
			throws ParseException,Exception {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");

		/*List<ErpEvents> list = eventsDao.findByProperty(ErpEvents.class,"eventsNo", eventsNo, null, null);
		ErpEvents events_temp=list.get(0);
		String provice = events_temp.getProvice();
		String city = events_temp.getCity();
		String bc = events_temp.getBranchCompany();
		String oc = events_temp.getOwnedCompany();//所属公司名称
		String branchCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getBranchCompanyId())){
			branchCompanyId = events_temp.getBranchCompanyId();
		}
		String ownedCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getOwnedCompanyId())){
			ownedCompanyId=events_temp.getOwnedCompanyId();}
		String ymSalesman=events_temp.getYmSalesman();*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ErpCustomerImpFailBtach customerImpFailBtach = null;
		String fileName = "";
		int countAll = 0;
		String str = "重复条形码信息：\\n条形码         姓    名        身份 证号          检测项目\\n";
		String saveName = System.currentTimeMillis()+ afileName.substring(afileName.lastIndexOf("."));
		String globleEventsNo="";
		if (afileName != null) {
			fileName = file.getAbsolutePath();
			long a = System.currentTimeMillis();
			InputStream is = null;
			XSSFWorkbook xssfWorkbook = null;
			try {
				is = new FileInputStream(fileName);
				xssfWorkbook = new XSSFWorkbook(is);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 循环工作表Sheet
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Date importDate = new Date();
			// 循环行Row

			String combo = null;

			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				ErpCustomer customer = new ErpCustomer();
				ErpCustomerBak customerBak = new ErpCustomerBak();
				if (xssfRow == null) {
					continue;
				}
				//20160302
				String events_no;
				String code;
				String name;
				String sex;
				String age;
				String phone;
				String idno;
				String branch_company;
				String setmeal_name;
				String sample_type;
				String sales_man;
				String sampling_date;
//				String document_type;
				int is_deleted=0;
//				String update_user_name;
//				String update_time;
				String create_time;
				String create_user_name;
				String family_history;
				String owned_company;
//				String provice;
//				String city;
				String note;
				String pdffilepath;

				//20160302
				if (rowNum == 0) {

				} else {
					/*****************************南方标准*********************************/
					/*customer.setProvice(provice);
					customer.setCity(city);
					customer.setEventsNo(eventsNo); // 场次号
					customer.setBranchCompanyId(branchCompanyId);//支公司ID
					customer.setBranchCompany(bc);// 支公司
					customer.setSamplingDate(list.get(0).getEventDate());
					customer.setIsDeleted(0);
					customer.setOwnedCompany(oc); // 所属公司
					customer.setOwnedCompanyId(ownedCompanyId);//所属公司ID
					customer.setCreateTime(importDate); // 创建时间
					customer.setCreateUserName(currentUser.getUserName());
					customer.setYmSalesman(ymSalesman);//远盟营销员
*/
					customer.setCode(getValue(xssfRow.getCell(0))); // 条形码
					customer.setName(getValue(xssfRow.getCell(1))); // 姓名
					customer.setPhone(getValue(xssfRow.getCell(2))); // 电话
					customer.setIdno(getValue(xssfRow.getCell(3))); // 身份证号
					// 套餐名截掉前后空格，并且把英文括号换成中文括号  add by YoumingDeng 2016-09-22 start
					combo = getValue(xssfRow.getCell(4)).trim(); //套餐名
					if(combo.contains("(")){
						combo = combo.replace("(", "（");
					}
					if(combo.contains(")")){
						combo = combo.replace(")", "）");
					}
					// 套餐名截掉前后空格，并且把英文括号换成中文括号  add by YoumingDeng 2016-09-22 end
					customer.setSetmealName(combo); // 套餐名
					customer.setSex(getValue(xssfRow.getCell(5))); // 性别
					customer.setAge(getValue(xssfRow.getCell(6))); // 年龄
					customer.setSampleType(getValue(xssfRow.getCell(7))); // 样本类型
					customer.setFamilyHistory(getValue(xssfRow.getCell(8))); // 家族疾病史
					customer.setDepartment(getValue(xssfRow.getCell(9)));//部门 	 add by YoumingDeng 2016-09-22
					customer.setSalesMan(getValue(xssfRow.getCell(10))); // 保险营销员
					customer.setNote(getValue(xssfRow.getCell(11)));//备注
					customer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错， add by DengYouming 2016-07-04
					customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));//会员报告状态， 150：数据审核中 add by YoumingDeng 2016-12-14
//					customer.setTestInstitution(getValue(xssfRow.getCell(11)));//检测机构
					System.out.println("时间--------"+xssfRow.getCell(12));
					if(StringUtils.isEmpty(getValue(xssfRow.getCell(12)))){
						customer.setEventsTime(null);
					}else{
						customer.setEventsTime(sdf.parse(getValue(xssfRow.getCell(11))));//场次时间
					}
					customer.setTestInstitution("南方");//检测机构
					customer.setBranchCompany(getValue(xssfRow.getCell(13)));		//支公司名
					customer.setOwnedCompany(getValue(xssfRow.getCell(14)));		//所属公司名

					/***********************************************************************/

					/*customerBak.setEventsNo(eventsNo); // 场次号
					customerBak.setCode(getValue(xssfRow.getCell(0))); // 条形码
					customerBak.setName(getValue(xssfRow.getCell(1))); // 姓名
					customerBak.setPhone(getValue(xssfRow.getCell(2))); // 电话
					customerBak.setIdno(getValue(xssfRow.getCell(3))); // 身份证号
					customerBak.setSetmealName(getValue(xssfRow.getCell(4))); // 套餐名
					customerBak.setSex(getValue(xssfRow.getCell(5))); // 性别
					customerBak.setAge(getValue(xssfRow.getCell(6))); // 年龄
					customerBak.setBranchCompany(bc);// 支公司
					customerBak.setBranchCompanyId(branchCompanyId);//支公司ID
					customerBak.setSampleType(getValue(xssfRow.getCell(7))); // 样本类型
					customerBak.setSamplingDate(list.get(0).getEventDate());
					customerBak.setFamilyHistory(getValue(xssfRow.getCell(8))); // 家族疾病史
					customerBak.setSalesMan(getValue(xssfRow.getCell(9))); // 保险营销员
					customerBak.setNote(getValue(xssfRow.getCell(10)));//备注
					customerBak.setOwnedCompany(oc); // 所属公司
					customerBak.setOwnedCompanyId(ownedCompanyId);//所属公司id
					customerBak.setCreateTime(importDate); // 创建时间
					customerBak.setCreateUserName(currentUser.getUserName());
					customerBak.setIsDeleted(0);*/

					//获取匹配的场次
					List<ErpEvents> events = null;

					if(customer.getEventsTime()!=null||!(StringUtils.isEmpty(customer.getBranchCompany()))){
						events = eventsDao.getEventsByTimeAndBranch(customer.getBranchCompany(),customer.getEventsTime());
					}

					String message = dao.isNotRepeatcode(customer.getCode()); // 判断条形码是否录入

					if (customer.getCode().trim().length()>=4&&customer.getName().trim().length()>=1&&StrUtils.isNullOrBlank(message)&&(customer.getSex().equals("男")||customer.getSex().equals("女"))) {// 条形码不重复并且名字不为空
						countAll++;
						if(events!=null&&events.size()>0){
							customer.setProvice(events.get(0).getProvice());
							customer.setCity(events.get(0).getCity());
							customer.setEventsNo(events.get(0).getEventsNo()); // 场次号
							customer.setBranchCompanyId(events.get(0).getBranchCompanyId());//支公司ID
							customer.setIsDeleted(0);
							customer.setOwnedCompanyId(events.get(0).getOwnedCompanyId());//所属公司ID
							customer.setCreateTime(importDate); // 创建时间
							customer.setCreateUserName(currentUser.getUserName());
							customer.setYmSalesman(events.get(0).getYmSalesman());//远盟营销员
							customer.setComparStatus("0");					//比对场次成功
							customer.setTestInstitution("南方");//检测机构
						}else{
							customer.setIsDeleted(0);
							customer.setCreateTime(importDate); // 创建时间
							customer.setCreateUserName(currentUser.getUserName());
							customer.setComparStatus("1");					//比对场次失败
						}
						customer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错， add by DengYouming 2016-07-05
						customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));//会员报告状态， 150：样本已获取 add by YoumingDeng 2016-12-14
						customer.setTestInstitution("南方");//检测机构
						dao.save(customer);
					} else {
						// 条形码重复
						List<ErpCustomer> listCustomer = dao.getCustomerByCode(customer.getCode());

						boolean b = true;// false为重复
						for (ErpCustomer tempCustomer : listCustomer) {
							globleEventsNo=tempCustomer.getEventsNo();
							str = str + "系统信息[" + tempCustomer.getCode() + ","+ tempCustomer.getName() + ","+ tempCustomer.getIdno() +","+ tempCustomer.getSetmealName() + "]\\n";
							if(tempCustomer.getIdno()==null){
								tempCustomer.setIdno("");
							}
							if (!tempCustomer.getIdno().equals("")&&tempCustomer.getIdno().equals(customer.getIdno())) {//身份证号不空并且相等： 重复
								b = false;
							}
							if (tempCustomer.getIdno().equals("")&&tempCustomer.getIdno().equals(customer.getIdno())){//身份证号都为空
								if(tempCustomer.getName().equals(customer.getName())){//姓名相同
									b=false;
								}
							}
							if(tempCustomer.getIdno().equals("")&&!customer.getIdno().equals("")||!tempCustomer.getIdno().equals("")&&customer.getIdno().equals("")){//一个为空，一个不为空
								if(tempCustomer.getName().equals(customer.getName())){//姓名相同
									b=false;
									if(!customer.getIdno().equals("")){
										tempCustomer.setIdno(customer.getIdno());
										dao.update(tempCustomer);
									}
								}
							}
						}
						if(!(customer.getSex().equals("男")||customer.getSex().equals("女"))){//性别有误
							b=false;
						}
						if (b == true&&customer.getName().trim().length()>=1&&customer.getCode().trim().length()>=5) {// 不重复
							if(events.size()>0){
								customer.setProvice(events.get(0).getProvice());
								customer.setCity(events.get(0).getCity());
								customer.setEventsNo(events.get(0).getEventsNo()); // 场次号
								customer.setBranchCompanyId(events.get(0).getBranchCompanyId());//支公司ID
								customer.setIsDeleted(0);
								customer.setOwnedCompanyId(events.get(0).getOwnedCompanyId());//所属公司ID
								customer.setCreateTime(importDate); // 创建时间
								customer.setCreateUserName(currentUser.getUserName());
								customer.setYmSalesman(events.get(0).getYmSalesman());//远盟营销员
								customer.setComparStatus("0");					//比对场次成功
							}else{
								customer.setIsDeleted(0);
								customer.setCreateTime(importDate); // 创建时间
								customer.setCreateUserName(currentUser.getUserName());
								customer.setComparStatus("1");					//比对场次失败
							}
							customer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错， add by DengYouming 2016-07-05
							customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));//会员报告状态， 150：样本已获取 add by YoumingDeng 2016-12-14
							customer.setTestInstitution("南方");//检测机构
							dao.save(customer);
							countAll++;
							str = str + "Excel[" + customer.getCode() + ","+ customer.getName() + ","+ customer.getIdno() + ","+ customer.getSetmealName() + "]导入成功\\n";
						}else{
							str = str + "Excel[" + customer.getCode() + ","+ customer.getName() + ","+ customer.getIdno() + ","+ customer.getSetmealName() +customer.getSex()+ "]导入失败\\n";
						}
//						bakDao.save(customerBak);//条形码重复信息备份
					}
				}
			}
			//查询比对状态为"1"的客户信息
			List<ErpCustomer> customers = dao.getCustomerByComparStatus();
			if(customers.size()>0){
				String s = "YC";
				String btachNo = defTaskNum();
				Integer ramNum = ranNum();
				String no = s + btachNo + ramNum;		//异常任务号
				customerImpFailBtach = new ErpCustomerImpFailBtach();
				customerImpFailBtach.setFailBtachNo(no);
				customerImpFailBtach.setCreateTime(new Date());
				customerImpFailBtach.setComparFailNum(customers.size());
				dao.saveFailBtach(customerImpFailBtach);

				for (ErpCustomer erpCustomer : customers) {
					erpCustomer.setFailBtachId(customerImpFailBtach.getId());
					erpCustomer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错， add by DengYouming 2016-07-04
					dao.update(erpCustomer);
				}
			}
			System.out.println("耗时 :" + (System.currentTimeMillis() - a)/ 1000f + " 秒 ");
		}
		String success = "成功导入" + countAll + "条!ok" + str;
		// return countAll;
		/*if(countAll==0){//获取场次号
			if(eventsNo.equals(globleEventsNo)){
				success="全部重复，现场次号："+eventsNo+"\\n系统信息所在场次号："+globleEventsNo+"\\n";
				success=success+"请确认同一场次是否重复导入！";
			}
		}*/
		return success;
	}

	private String getValue(Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:// boolean类型
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数字或日期类型

			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否是日期类型
				value = DateUtils.DateToStr(cell.getDateCellValue(),
						DateUtils.TIME_FORMAT); // 把Date转换成本地格式的字符串
			} else {
				short format = cell.getCellStyle().getDataFormat();
				SimpleDateFormat sdf = null;
				if (format == 14 || format == 31 || format == 57
						|| format == 58) {
					// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					sdf = new SimpleDateFormat("HH:mm:ss");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					value = String.valueOf(
							df.format(cell.getNumericCellValue())).trim();
				}
			}
			break;
		case Cell.CELL_TYPE_STRING:// 字符类型
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_BLANK:// 空白单元格
			break;
		default:
			value = String.valueOf(cell.getStringCellValue()).trim();
			break;
		}
		return value;
	}

	 private String getColName(String xlsTitleName){
		 xlsTitleName=xlsTitleName.trim();
		 String colName="";
		 if("条形码".equals(xlsTitleName)){
			 colName="code";
		 }else if("姓名".equals(xlsTitleName)){
			 colName="name";
		 }else if("性别".equals(xlsTitleName)){
			 colName="sex";
		 }else if("年龄".equals(xlsTitleName)){
			 colName="age";
		 }else if("电话".equals(xlsTitleName)){
			 colName="phone";
		 }else if("身份证号".equals(xlsTitleName)){
			 colName="idno";
		 }else if("支公司".equals(xlsTitleName)){
			 colName="branch_company";
		 }else if("套餐名".equals(xlsTitleName)){
			 colName="setmeal_name";
		 }else if("样本类型".equals(xlsTitleName)){
			 colName="sample_type";
		 }else if("保险营销员".equals(xlsTitleName)){
			 colName="sales_man";
		 }else if("远盟营销员".equals(xlsTitleName)){
			 colName="";
		 }else if("批次".equals(xlsTitleName)){
			 colName="entering";
		 }else if("医疗机构".equals(xlsTitleName)){
			 colName="institution";
		 }else if("送检日期".equals(xlsTitleName)){
			 colName="collectiondate";
		 }else if("收样日期".equals(xlsTitleName)){
			 colName="sampling_date";
		 }else if("样本状态".equals(xlsTitleName)){
			 colName="simplestatus";
		 }else if("页数".equals(xlsTitleName)){
			 colName="page";
		 }else if("检测机构".equals(xlsTitleName)){
			 colName="";
		 }else if("部门".equals(xlsTitleName)){
			 colName="department";
		 }
		 return colName;
	 }

	 public void updatePdfPath(String newpath,String id){
		 dao.updatePdfPath(newpath,id);
	 }

	 /**
	  * 列出可以结算的会员
	  * @return
	  * @author DengYouming
	  * @since 2016-5-7 下午5:40:19
	  */
	 public List<ErpCustomer> listCustomerCanAddSettle(Map<String,Object> params) throws Exception{
		 List<ErpCustomer> list = null;
		 list = dao.listCustomerCanAddSettle(params);
		 return list;
	 }

	//查出未找到的条码
	public List<String> queryUnFindCodes(List<ErpCustomer> customers,String codes){
		String allCodes = codes.replaceAll("'","");
		String[] strArray = allCodes.split(",");
		List<String> unfindCodes = new ArrayList<String>();
		List<String> dbCodes = new ArrayList<String>();
		for(ErpCustomer cus : customers){
			dbCodes.add(cus.getCode());
		}
		for(int i=0;i<strArray.length;i++){
			if(!dbCodes.contains(strArray[i])){
				unfindCodes.add(strArray[i]);
			}
		}
		return unfindCodes;
	}

	//通过ID获取场次中的批次号
	public String findEvtBthnoById(String cusId){
		String batchno = "";
		if(null!=cusId&&!("").equals(cusId)){
			ErpCustomer customer = (ErpCustomer) dao.findById(ErpCustomer.class, cusId);
			if(null!=customer&&null!=customer.getEventsNo()){
				ErpEvents events = eventsDao.queryEventNO(customer.getEventsNo());
				if(null!=events){
					batchno = events.getBatchNo();
				}
			}
		}
		return batchno;
	}

	//通过ID获取会员信息
	public ErpCustomer findCusById(String cusId){
		ErpCustomer customer = null;
		if(null!=cusId&&!("").equals(cusId)){
			customer = (ErpCustomer) dao.findById(ErpCustomer.class, cusId);
		}
		return customer;
	}

	//通过场次号获取批次号
	public String findEvtBthnoByEvtNo(String eventsNo){
		String batchno = "";
		if(null!=eventsNo&&!("").equals(eventsNo)){
			ErpEvents events = eventsDao.queryEventNO(eventsNo);
			if(null!=events){
				batchno = events.getBatchNo();
			}
		}
		return batchno;
	}
	private static String getStrAge(String idno) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		String s1=sdf.format(new Date());
		int curYear=Integer.parseInt(s1);

		String year=getStrYear(idno);
		int age=curYear-Integer.parseInt(year);
		return ""+age;
	}
	//由身份证号返回出生日期
	private static String getStrYear(String s) {
		int leh = s.length();
		if (leh != 18 && leh != 15) {
			return "";
		} else {
			if (leh == 18) {
				int se = Integer.valueOf(s.substring(leh - 1)) % 2;
//				String dates = s.substring(6, 10) + "-" + s.substring(10, 12)+ "-" + s.substring(12, 14);
				String dates = s.substring(6, 10) ;
				return (dates);

//				String sex = "";
//				if (leh == 0) {sex = "M";} else {sex = "F";	}
//				System.out.println(sex + "\t" + dates);
			} else {
//				String dates = "19" + s.substring(6, 8) + "-"+ s.substring(8, 10) + "-" + s.substring(10, 12);
				String dates = "19" + s.substring(6, 8);
				return (dates);
			}
		}
	}

	 /**
     * 默认的任务号
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:23:28
     */
    public String defTaskNum(){
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	String time = dateFormat.format(date);
    	return time;
    }

    /**
     * 随机生成三位数
     * @return
     * @author tangxing
     * @date 2016-6-6下午4:03:54
     */
    public int ranNum(){
    	Random randomNumber = new Random();
    	int i=randomNumber.nextInt(900)+100;	//三位数随机数
    	return i;
    }

    /* **********生成Excel及下载路径********* */

    public String createExSeFilePath(HttpServletRequest request,List<ErpCustomer> customerImpFails,String failBtachNo){
    	StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
    	String path="D:/ymFailBtach/";
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append("/ymFailBtach/").toString();
		StringBuilder rootPath = new StringBuilder(path + timePath + File.separator);
//		String batchFilePath = rootPath.toString()+timePath+"_"+settleExceTaskNo+File.separator;
		String curFilePath = path+timePath+"_"+failBtachNo+File.separator;//存放位置
		String str = timePath+"_"+failBtachNo+File.separator;


		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdf(customerImpFails);
		String excleName = failBtachNo+".xls";			//文件名
		createExSettleXls(curFilePath.toString(),excleName,rowList);

		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return downloadurl;
	}

    /**
     * 生成Excel每行的内容（异常结算任务客户信息）
     * @param customerImpFails
     * @return
     * @author tangxing
     * @date 2016-6-14下午6:20:51
     */
  	//序号、条码、姓名、性别、年龄、身份证号、电话、省、市、支公司、所属公司、套餐、采样日期、营销员、批次号、检测机构
  	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRowByPdf(List<ErpCustomer> customerImpFails){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		for(int i=0;i<customerImpFails.size();i++){
  			ErpCustomer content = customerImpFails.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getCode());//条码
  			list.add(content.getName());//姓名
  			list.add(content.getPhone());//电话
  			list.add(content.getIdno());//身份证号
  			list.add(content.getSetmealName());//套餐名
  			list.add(content.getSex());//性别
  			list.add(content.getAge());//年龄
  			list.add(content.getSampleType());//样本类型
  			list.add(content.getFamilyHistory());//家族病史
  			list.add(content.getSalesMan());//保险营销员
  			list.add(content.getNote());//备注
  			if(content.getEventsTime()!=null){
  				list.add(sdf.format(content.getEventsTime()));	//场次日期
  			}
  			list.add(content.getBranchCompany());	//支公司
  			list.add(content.getOwnedCompany());		//所属公司
  			/*List<CustomerRelationShip> relationShip = relService.findByCustomer(content.getBranch_company());
  			if(relationShip.size()==0){
  				list.add(content.getBranch_company());//支公司
  			}else{
  				list.add(relationShip.get(0).getBranchCommany());//支公司
  			}*/

  			//list.add(cusService.findEvtBthnoById(content.getCustomerid()));//20160517增加批次号
  			result.add(list);
  		}
  		return result;
  	}

  	/**
  	 * 生成异常结算任务客户信息EXCEL
  	 * @param path
  	 * @param filename
  	 * @param rowList
  	 * @author tangxing
  	 * @date 2016-6-14下午6:21:23
  	 */
  	public void createExSettleXls(String path ,String filename, List<List<String>> rowList) {

		try {
			File file = new File(path);

			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");

			HSSFRow row0 = hssfSheet1.createRow(0);
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("条码");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("姓名");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("电话");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("身份证号");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("套餐名");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("性别");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("年龄");
			Cell cell8 = row0.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("样本类型");
			Cell cell9 = row0.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("家族病史");
			Cell cell10 = row0.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("保险营销员");
			Cell cell11 = row0.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("备注");
			Cell cell12 = row0.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("场次日期");
			Cell cell13 = row0.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("支公司");
			Cell cell14 = row0.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("所属公司");
			/*Cell cell15 = row0.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("报告");*/
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}

			}
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error("createExSettleXls writeXls", e);
		} catch (IOException e) {
			log.error("createExSettleXls writeXls", e);
		}


	}

  	/**
  	 * 设置字体格式
  	 * @param wb
  	 * @param bold
  	 * @param fontName
  	 * @param isItalic
  	 * @param hight
  	 * @return
  	 * @author tangxing
  	 * @date 2016-6-14下午6:29:53
  	 */
	public static Font createFonts(Workbook wb, short bold, String fontName,boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}

	/**
	 * 设置内容
	 * @param wb
	 * @param row
	 * @param column
	 * @param value
	 * @param cellStyle
	 * @author tangxing
	 * @date 2016-6-14下午6:30:05
	 */
	public static void createCell(Workbook wb, Row row, int column,String value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 根据场次号，下载金域检测对应的会员PDF
	 * @param eventsNo 场次号
	 * @return String
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws JSONException
	 * @author DengYouming
	 * @since 2016-8-25 下午1:35:48
	 */
	public String downloadPDFByEventsNo(String eventsNo) throws RemoteException, ServiceException, JSONException{
		String msg = "0";
		if(eventsNo!=null&&eventsNo.length()>0){
			List<ErpCustomer> tempList = new ArrayList<ErpCustomer>();
			Map<String,String> params = new HashMap<String, String>();
			if(eventsNo.contains(",")){
				String[] eventsNoArr = eventsNo.split(",");
				List<ErpCustomer> simpList = null;
				for (int i = 0; i < eventsNoArr.length; i++) {
					params.clear();
					params.put(ErpCustomer.F_EVENTSNO, eventsNoArr[i]);
					simpList = dao.listByProps(params);
					if(!simpList.isEmpty()){
						tempList.addAll(simpList);
					}
				}
			}else{
				params.clear();
				params.put(ErpCustomer.F_EVENTSNO, eventsNo);
				tempList = dao.listByProps(params);
			}

			YmGeneReportServiceImplServiceLocator locator = new YmGeneReportServiceImplServiceLocator();
			locator.setYmGeneReportServiceImplPortEndpointAddress(ADDRESS);
			YmGeneReportServiceImpl ymGene =  locator.getYmGeneReportServiceImplPort();

			if (tempList!=null&&tempList.size()>0) {
				Map<String,String> qMap = new HashMap<String, String>();
				String resp = null;
				for (ErpCustomer obj : tempList) {
					qMap.clear();
					if(obj.getPhone()!=null&&obj.getPhone().length()>0){
						qMap.put(ErpCustomer.F_PHONE, obj.getPhone());
					}else{
						qMap.put("barcode", obj.getCode());//条码
					}
					resp = this.downloadPDFByCustomerInfo(qMap ,ymGene);
					if(resp!=null){
						msg = "1";
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 根据会员信息下载金域检测生成的pdf文件，并更新会员信息的预览路径
	 * @param params 查询参数
	 * @return String 保存的物理地址
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws JSONException
	 * @author DengYouming
	 * @since 2016-8-25 上午11:40:26
	 */
	public String downloadPDFByCustomerInfo(Map<String,String> params, YmGeneReportServiceImpl ymGene) throws ServiceException, RemoteException, JSONException{
		String msg = null;;
		if(params!=null&&!params.isEmpty()){
			JSONObject reqJson = new JSONObject();
			for (String key : params.keySet()) {
				String value = params.get(key);
				reqJson.put(key, value);
			}
			if(reqJson!=null&&reqJson.length()>0){
				String jsonStr = reqJson.toString();
				//返回实际的保存物理地址
				msg = ymGene.gainReport(jsonStr);
				if(msg!=null&&msg.length()>0){
					String viewPath = this.createViewPath(msg);
					List<ErpCustomer> list = dao.listByProps(params);
					if(!list.isEmpty()){
						ErpCustomer obj = list.get(0);
						obj.setPdffilepath(viewPath);
						obj.setStatusYm(PropsUtils.getInt("status","statusYm.ycj"));//会员报告状态， 300：电子报告已出 add by YoumingDeng 2016-12-14
						dao.update(obj);
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 处理定时任务
	 * @param infoType
	 * @param scheduleTime
	 * @param keyWord
	 * @param info
	 * @param user
	 * @return Map
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-9-14 上午11:30:48
	 */
	public Map<String,String> dealScheduleJob(String infoType, String scheduleTime, String keyWord, String info, User user) throws Exception{

		Map<String,String> result = null;
		if(StringUtils.isNotEmpty(infoType)&&StringUtils.isNotEmpty(scheduleTime)&&StringUtils.isNotEmpty(keyWord)&&StringUtils.isNotEmpty(info)){
			Map<String,String> params = null;

			params = new HashMap<String, String>();
			params.put(ErpScheduleJob.F_INFOTYPE, infoType);
			params.put(ErpScheduleJob.F_INFO, info);
			params.put(ErpScheduleJob.F_SCHEDULETIME, scheduleTime);
			params.put(ErpScheduleJob.F_KEYWORD, keyWord);
			params.put(ErpScheduleJob.F_CREATEUSER, user.getAccountName());
			params.put(ErpScheduleJob.F_CREATEUSERID, user.getId());

			//处理业务逻辑
			result = erpScheduleJobDao.dealScheduleJob(params);
		}
		return result;
	}

	public String[] split(String orgStr, String regex) throws Exception{
		String[] arr = null;
		if(orgStr.contains(regex)){
			arr = orgStr.split(regex);
		}else{
			arr = new String[1];
			arr[0] = orgStr;
		}
		return arr;
	}

	/**
	 * 根据保存的实际路径获取预览路径
	 * @param fileAddr 实际保存路径
	 * @return String
	 * @author DengYouming
	 * @since 2016-8-25 上午11:36:52
	 */
	public String createViewPath(String fileAddr){
		System.out.println("fileAddr: "+fileAddr);
		//截取盘符
		String subAddr = fileAddr.substring(fileAddr.indexOf(File.separator));
		System.out.println("subAddr: "+subAddr);
		//预览地址
		String viewPath = "ftp://gene:gene@geneym.healthlink.cn/gene"+subAddr;
		System.out.println("viewPath: "+viewPath);
		//预览地址
		return viewPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getDownloadContext() {
		return downloadContext;
	}

	public void setDownloadContext(String downloadContext) {
		this.downloadContext = downloadContext;
	}

	/**
	 * 根据场次号获取所有用户
	 * @param eventsId
	 * @return
	 * @author machuan
	 * @date  2016年10月14日
	 */
	public List<ErpCustomer> getErpCustomerByEventsNo(String eventsId) {
		return dao.getErpCustomerByEventsNo(eventsId);
	}

	/**
	 * 根据场次号获取已录入人数
	 * @param eventsId
	 * @return
	 * @author machuan
	 * @date  2016年11月16日
	 */
	public int getErpCustomerNumByEventsNo(String eventsId){
		return dao.getErpCustomerByEventsNo(eventsId).size();
	}

	/**
	 * 根据场次号  获取 异常数据的数量
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public int getExceptCount(String eventsNo) {
		return dao.getExceptCount(eventsNo);
	}

	/**
	 * 根据场次号 获取 分页异常数据
	 * @param page
	 * @param eventsNo
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public List getListCustomerException(Page page, String eventsNo) {
		return dao.getListCustomerException(page,eventsNo);
	}

	/**
	 * 根据id获取异常客户信息
	 * @param id
	 * @return
	 * @author machuan
	 * @date  2016年11月17日
	 */
	public ErpCustomerException findExceptionById(String id) {
		return dao.findExceptionById(id);
	}

	/**
	 * 修改异常客户信息，进行比对验证
	 * @param customerException
	 * @author machuan
	 * @date  2016年11月18日
	 */
	public String updateExceptionInfo(ErpCustomerException customerException) {
		String msg = "";
		//第一步 检查 错误数据：“条形码”、“姓名”、“性别”任意一项为空
		boolean errorFlag = false;
		errorFlag = checkError(customerException.getCode(),customerException.getName(),customerException.getSex());
		//如果有一项为空，计入导入失败
		if(errorFlag){
			msg = "条形码，姓名，性别不能为空！";
			return msg;
		}
		//第二步 都不为空 验证重复性 首先验证条形码是否重复  需要验证正常数据
		boolean flag = false;
		//正常数据表
		List<ErpCustomer> listCustomer = getCustomerByCode(customerException.getCode());
		//条形码重复 则进行身份证比对
		for(ErpCustomer _erpCustomer : listCustomer){
			//身份证都不为空并且相等  则表示为同一条数据
			if(StringUtils.isNotBlank(customerException.getIdno())&&_erpCustomer.getIdno().equals(customerException.getIdno())){
				flag = true;
				break;
			}
			//身份证有一条为空或者两边都为空，手机号不为空并且相同也视为同一条数据
			if((StringUtils.isBlank(customerException.getIdno())||StringUtils.isBlank(_erpCustomer.getIdno()))&&
					StringUtils.isNotBlank(_erpCustomer.getPhone())&&_erpCustomer.getPhone().equals(customerException.getPhone())){
				flag = true;
				break;
			}
		}
		//判定为重复数据，则删除该条数据
		if(flag){
//			dao.delete(customerException);
			dao.delete(ErpCustomerException.class, customerException.getId());
			msg = "该客户信息重复，已删除！";
			return msg;
		}
		//验证性别，年龄，套餐名,套餐费用，返回true为通过验证
		List<ErpEvents> list = eventsDao.findByProperty(ErpEvents.class,"eventsNo", customerException.getEventsNo().trim(), null, null);
		ErpEvents events_temp=list.get(0);
		List<Object> checkSexAgeComboList = checkSexAgeCombo(customerException.getIdno(),customerException.getSex(),customerException.getAge(),customerException.getSetmealName(),events_temp.getCustomerRelationShipProId(),events_temp.getBranchCompanyId());
		flag = (Boolean) checkSexAgeComboList.get(0);
		//通过验证则新增到客户表，失败则修改异常表，直接退出
		if(flag){
			//通过验证需要补全customer信息，然后保存进customer表
			ErpCustomer customer = new ErpCustomer();
			customer = transforExtion2Customer(customerException,customer);
			dao.save(customer);
			dao.delete(ErpCustomerException.class, customerException.getId());
			msg = "客户信息验证成功，已保存!";
		}else{
			String result = "";
			for(int i = 1;i<checkSexAgeComboList.size();i++){
				result+=checkSexAgeComboList.get(i)+"，";
			}
			customerException.setResult(result.substring(0,result.length()-1)+"。");//失败原因
			dao.update(customerException);
			msg = "客户信息验证失败，请重新修改!";
		}
		return msg;
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

	/**
	 * ErpCustomerException转换为ErpCustomer对象
	 * @param customerException
	 * @param customer
	 * @return
	 * @author machuan
	 * @date  2016年11月18日
	 */
	private ErpCustomer transforExtion2Customer(ErpCustomerException customerException, ErpCustomer customer) {
		User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
		List<ErpEvents> list = eventsDao.findByProperty(ErpEvents.class,"eventsNo", customerException.getEventsNo(), null, null);
		ErpEvents events_temp=list.get(0);
		String provice = events_temp.getProvice();
		String city = events_temp.getCity();
		String bc = events_temp.getBranchCompany();
		String oc = events_temp.getOwnedCompany();//所属公司名称
		//根据批次号判定检测机构  add By YoumingDeng 2016-09-26 start
		String batchNo = events_temp.getBatchNo();
		String testInstitution = "南方";
		if(batchNo.startsWith("JY")){
			testInstitution = "金域";
		}else if(batchNo.startsWith("JSL")){
			testInstitution = "吉思朗";
		}
		String branchCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getBranchCompanyId())){
			branchCompanyId = events_temp.getBranchCompanyId();
		}
		String ownedCompanyId = "";
		if(StrUtils.isNotNullOrBlank(events_temp.getOwnedCompanyId())){
			ownedCompanyId=events_temp.getOwnedCompanyId();}
		String ymSalesman=events_temp.getYmSalesman();

		customer.setProvice(provice);
		customer.setCity(city);
		customer.setEventsNo(customerException.getEventsNo()); // 场次号
		customer.setBranchCompanyId(branchCompanyId);//支公司ID
		customer.setBranchCompany(bc);// 支公司
		customer.setSamplingDate(list.get(0).getEventDate());
		customer.setIsDeleted(0);
		customer.setOwnedCompany(oc); // 所属公司
		customer.setOwnedCompanyId(ownedCompanyId);//所属公司ID
		customer.setCreateTime(new Date()); // 创建时间
		customer.setCreateUserName(currentUser.getUserName());
		customer.setYmSalesman(ymSalesman);//远盟营销员
		customer.setCode(customerException.getCode()); // 条形码
		customer.setName(customerException.getName()); // 姓名
		customer.setPhone(customerException.getPhone()); // 电话
		customer.setIdno(customerException.getIdno()); // 身份证号
		// 套餐名截掉前后空格，并且把英文括号换成中文括号  add by YoumingDeng 2016-09-19 start
		String combo =customerException.getSetmealName(); //套餐名
		if(combo.contains("(")){
			combo = combo.replace("(", "（");
		}
		if(combo.contains(")")){
			combo = combo.replace(")", "）");
		}
		// 套餐名截掉前后空格，并且把英文括号换成中文括号
		customer.setSetmealName(combo); // 套餐名
		customer.setSex(customerException.getSex()); // 性别
		customer.setAge(customerException.getAge()); // 年龄
		customer.setSampleType(customerException.getSampleType()); // 样本类型
		customer.setFamilyHistory(customerException.getFamilyHistory()); // 家族疾病史
		customer.setDepartment(customerException.getDepartment());//部门
		customer.setSalesMan(customerException.getSalesMan()); // 保险营销员
		customer.setSalesManNo(customerException.getSalesManNo());//营销员工号
		customer.setNote(customerException.getNote());//备注
		customer.setTestInstitution(testInstitution);//检测机构
		customer.setStatus("0");//状态默认为 "0"否则添加保险公司，结算时会出错
		customer.setStatusYm(PropsUtils.getInt("status","statusYm.yhq"));//会员报告状态， 150：样本已获取 add by YoumingDeng 2016-12-14
		//对于年龄为空，身份证号不为空且长度为15位或18位的客户信息自动计算年龄
		if((customer.getAge()==null||customer.getAge().equals(""))&&customer.getIdno()!=null){
			if(customer.getIdno().length()==15||customer.getIdno().length()==18){
				String tempage=getStrAge(customer.getIdno());
				customer.setAge(tempage);
			}
		}
		return customer;
	}

	/**
	 * 查询某个支公司号下面的所有套餐
	 * @param customerRelationShipId
	 * @return
	 * @author machuan
	 * @date  2016年11月18日
	 */
	public List<Combo> findByCustomer(String customerRelationShipId) {
		//根据eventsNo查询
		String sql = "select " +
			"combo.id, "+
			"combo.combo_name comboName "+
			"from ERP_RELATIONSHIPPRO_COMBO shipCombo "+
			"inner join HL_JY_COMBO combo on combo.id = shipCombo.COMBO_ID  "+
			"where shipCombo.CUSTOMER_RELATIONSHIP_PRO_ID = '"+customerRelationShipId+"'";
		BeanPropertyRowMapper<Combo> rowMapper = new BeanPropertyRowMapper<Combo>(Combo.class);
		List<Combo> combos = this.dao.getJdbcTemplate().query(sql, rowMapper);
		return combos;
	}
	/**
	 * 根据支公司号和套餐名查询套餐是否有价格
	 * @param setmealName
	 * @author machuan
	 * @param conpanyId
	 * @param projectId 项目ID
	 * @date  2016年11月18日
	 * @return
	 */
	public List<Map<String,Object>> findComboPrice(String setmealName, String conpanyId, String projectId) {
		//根据eventsNo查询
		String sql = "select combo_price from ERP_COMPANY_COMBO_PRICE "
				+ " where company_id='"+conpanyId
				+ "' and combo='"+setmealName
				+ "' and project_id ='"+projectId
				+ "'";
		List<Map<String,Object>> list = this.dao.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 根据id删除异常数据
	 * @param exceptionId
	 * @author machuan
	 * @date  2016年11月18日
	 */
	public void deleteCustomerExption(String exceptionId) throws Exception {
		dao.delete(ErpCustomerException.class, exceptionId);
	}

	/**
	 * @return
	 * @author machuan
	 * @date  2016年11月25日
	 */
	public List<ErpCustomerSync> findListCustomerSync() {
		String hql = "from ErpCustomerSync where status='0'";
		return dao.getHibernateTemplate().find(hql);
	}

	public String getSyncXmlRetCode(ErpCustomerSync customerSync){
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startTime = df.format(new Date());
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, 1);
		String endTime = df.format(c.getTime());
		String xmlInfo = "<Rows>"
				+"<userName>"+customerSync.getName()+"</userName>"
				+"<sex>"+customerSync.getSex()+"</sex>"
				+"<mobile1>"+customerSync.getPhone()+"</mobile1>"
				+"<idcardType>"+customerSync.getDocumentType()+"</idcardType>"
				+"<idcardNum>"+customerSync.getIdno()+"</idcardNum>"
				+"<province>"+customerSync.getProvice()+"</province>"
				+"<city>"+customerSync.getCity()+"</city>"
				+"<policyNum>"+customerSync.getCode()+"</policyNum>"
				+"<belongUnits>"+customerSync.getBranchCompany()+"</belongUnits>"
				+"<salesTeam>"+customerSync.getSalesMan()+"</salesTeam>"
				+"<startTime>"+startTime+"</startTime>"
				+"<endTime>"+endTime+"</endTime>"
				+"<dataType>"+"01"+"</dataType>"
				+"</Rows>";

		YmGeneReportServiceImpl impl;
		String retCode = "";
		try {
			YmGeneReportServiceImplServiceLocator locator = new YmGeneReportServiceImplServiceLocator();
			locator.setYmGeneReportServiceImplPortEndpointAddress(ADDRESS);
			impl = locator.getYmGeneReportServiceImplPort();
			retCode = impl.productMemberImmediate(xmlInfo);
			log.info("getSyncXmlRetCode==success!");
		} catch (Exception e) {
			log.error("getSyncXmlRetCode==error: "+e);
		}
		return retCode;
	}

	/**
	 * 根据场次号
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2016年12月1日
	 */
	public String findRepeatInfo(String eventsNo) {
		String sql = "select code,name,idno,phone,sex,age from erp_customer where code in"
				+ "(select code from "
				+ "(select code,count(1) as hh from erp_customer where is_deleted=0 group by code having count(1)>1 ) "
				+ " where code in"
				+ "(select code from erp_customer where events_no='"+eventsNo
				+"'))";
		List<Map<String, Object>> list = this.dao.getJdbcTemplate().queryForList(sql);
		StringBuilder sb = new StringBuilder();
		for(Map<String, Object> map : list){
			sb.append(map.get("code")+",");
			sb.append(map.get("name")+",");
			sb.append(map.get("idno")+",");
			sb.append(map.get("phone")+",");
			sb.append(map.get("sex")+",");
			sb.append(map.get("age")+"|");
		}
		String msg = sb.toString();
		if(!"".equals(msg)){
			msg = msg.substring(0, msg.length()-1);
		}
		return msg;
	}
	
	/**
	 * 点击推送按钮，调用知康的接口，将当前此条客户信息推送给知康
	 * <p>Description: </p>
	 * @author herny.xu
	 * @throws MyException 
	 * @date 2017年4月14日
	 */
	@SuppressWarnings("unchecked")
	public boolean pushSingleNumToWuChuang(String customerId) throws MyException {
		
		//>>1.首先获取ErpCustomer的数据;
		String url = "http://www.zhikangkeji.com:8888/exam/yuanmeng/confirmation.action";//正式环境地址;
		String sql = "select count(1) from erp_bar_code_detail where is_deleted = 0 and BARCODE=?";//查询该条形码是否存在;
		StringBuilder recordStr = new StringBuilder("该客户信息验证不通过: "); //记录提示字符串;
		StringBuilder query = new StringBuilder("from ErpCustomer where is_deleted=0 and id=? order by code");
		String barCode = "";
		ErpCustomer customer = null;
		
		List<ErpCustomer> customers = this.eventsDao.getHibernateTemplate().find(query.toString(), customerId);
		//>>2. 验证数据的有效性;是否满足条件;
		if(customers!= null && customers.size() > 0) {
			customer = customers.get(0);
			barCode = customer.getCode();
			//查询该条形码是否存在;
			int count = this.eventsDao.getJdbcTemplate().queryForInt(sql, barCode);
			if(count <= 0) { //表示在code明细表中没有; 并直接抛出异常;
				recordStr.append(customer.getName()).append(": "+barCode+"在条形码管理中不存在,请重新生成! \n");
				throw new MyException(recordStr.toString());
			}
			
		}
		
		//>>3. 数据推送到第三方;
		/*
		 * 这里再次遍历目的是为了处理传输数据转换为json;
		 * 最后返回;当验证都通过时进行数据传输;
		 * */
		//首先获取场次;
		ErpEvents events = this.eventsDao.queryEventNO(customer.getEventsNo());
		EventCustomers ecus = new EventCustomers();
		ecus.setStartTime(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd")+" 00:00:00");
		ecus.setEndTime(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd")+" 23:59:59");
		ecus.setDeviceSNs(null);
		ecus.setFieldInfo(events.getAddress());

		//根据支公司Id查询对应的名称,避免冗余,数据不是最新的所以重新查询;
		CustomerRelationShip relationShip = (CustomerRelationShip) this.customerRelationshipDao.findById(CustomerRelationShip.class, events.getBranchCompanyId());
		ecus.setShowsName(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd") + relationShip.getBranchCommany()); //场次开始日期（年月日）+支公司名称
		
		List<Map<String, String>> objMap = new ArrayList<Map<String,String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		String idcard = customer.getIdno();
		
		if(StringUtils.isNotEmpty(idcard)) { //避免空指针问题;
			String year = idcard.substring(6, 10);
			String month = idcard.substring(10, 12);
			String day = idcard.substring(12, 14);
			String birthday = year + "-" + month + "-" + day + " 00:00:00";
			map.put("birthday", birthday);

		}

		map.put("reportType", customer.getSetmealName()); //确认为: 套餐名称;
		map.put("serviceCode", customer.getCode());// ;
		map.put("sex", customer.getSex());
		map.put("weight", customer.getWeight());
		map.put("userName", customer.getName());
		map.put("height", customer.getHeight());
		map.put("salsman", customer.getSalesMan());
		map.put("ymUserId", "");
		map.put("institution_id", "0");
		objMap.add(map);
		ecus.setUserList(objMap);

		//对象转换为json;
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(ecus);//将java对象转换为json对象
		String str = json.toString();
		String base64Str = null;

		log.info("BASE64加密前: " + str);
		if(StringUtils.isNotEmpty(str)) {
			try {
				base64Str = Base64.encodeBase64String(str.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.info("编码转换错误!", e);
			}
		}

		log.info("BASE64加密后: "+ base64Str) ;

		Map<String, String> param = new HashMap<String, String>();
		param.put("confirmationInfo", base64Str);
		HttpClientTool tool = HttpClientTool.getInstance();
		String response = tool.httpPost(url, param);
		//执行成功后,修改数据为已使用;

		log.info("请求后返回数据: " + response);
		if(response != null && response.contains("SUCCESS")) {
			try {
				this.erpBarCodeDetailDao.updateCodeDetail(customers);
			} catch (Exception e) {
				log.info("条码修改错误!", e);
			}

			/*
			 * 新增需求
			 * 并将当前无创场次二维码设置为已推送状态，数据库表中需要增加状态字段（只增加二维码这个表即可）*/
			String sql_statusYm = "update erp_customer set STATUS_YM='200' where id=? ";
			this.dao.updateStatusYm(sql_statusYm, new Object[]{customerId});

			sendMail(events); //邮件发送;

		} else if(response != null && response.contains("FAIL")) {
			throw new MyException("该用户没有检查数据!");
		} else {
			throw new MyException("推送数据请求响应异常!");
		}
		return true;
		
	}

	/**
	 * 根据场次id查询对应的客户信息,就行对比;
	 * 规则:验证当前场次的客户条形码与条码库中的条码进行验证：验证当前条形码是否在条码库中存在，
	 * 	并且是否已经被占用，如果已经被使用，则给出相关提示信息要求业务人员调整。
	 * 	如果存在，并且没有被使用，则将条码表中【是否使用】字段设置为是
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean customerInfoSure(String id) throws Exception {
		if(StringUtils.isEmpty(id)) {
			throw new MyException("数据异常!");
		}

		//首先获取场次;
		ErpEvents events = this.eventsDao.get(id);
		//其次获取对应的客户信息;
		if(null == events) {
			throw new MyException("该场次不存在,请确认后在继续操作!");
		}
		StringBuilder query = new StringBuilder("from ErpCustomer where is_deleted=0 and EVENTS_NO='"+events.getEventsNo()+"' order by code");
		List<ErpCustomer> customers = this.eventsDao.getHibernateTemplate().find(query.toString());
		String url = "";
		if(null != customers && customers.size() > 0) {
			int noPass = 0;
			//记录提示字符串;
			StringBuilder recordStr = new StringBuilder("以下客户信息验证不通过: ");
			String sql = "select count(1) from erp_bar_code_detail where is_deleted = 0 and BARCODE=?";//查询该条形码是否存在;
			String sql2 = "select IS_USED isUsed from erp_bar_code_detail where is_deleted = 0 and BARCODE=?"; //查询该条形码是否已被使用;
			for (ErpCustomer customer : customers) {

				if(StringUtils.isEmpty(url)) {
					if("W".equals(customer.getCode().substring(0, 1))) { //知康
						url = "http://www.zhikangkeji.com:8888/exam/yuanmeng/confirmation.action";//正式环境地址;
						//String url = "http://115.28.226.155:8088/exam/yuanmeng/confirmation.action";  //测试环境地址;
					} else if("C".equals(customer.getCode().substring(0, 1))) {
						url = "http://www.jsrom.cn/index.php/Api/ConfirmUser";
					}
				}

				/*
				 * 客户信息遍历;客户条形码与条码库中的条码进行验证
				 */
				String barCode = customer.getCode();

				if(StringUtils.isEmpty(barCode)) {
					recordStr.append(customer.getName()).append(": 条形码为空; \n");
					noPass ++;
					continue; //跳过后面继续判断;
				}

				//查询该条形码是否存在;
				int count = this.eventsDao.getJdbcTemplate().queryForInt(sql, barCode);
				if(count <= 0) { //表示在code明细表中没有;
					recordStr.append(customer.getName()).append(": "+barCode+"在条形码管理中不存在,请重新生成! \n");
					noPass ++;
					continue; //跳过后面继续判断;
				}
				//查询该条形码是否已被使用;
				Map<String, Object> map = this.eventsDao.getJdbcTemplate().queryForMap(sql2, barCode);
				String isUsed = (String) map.get("isUsed");
				if("1".equals(isUsed)) { //1表示已使用;0表示未使用;
					recordStr.append(customer.getName()).append(": "+barCode+"在条形码管理中已被使用,请重新生成! \n");
					noPass ++;
					continue; //跳过后面继续判断;
				}


			}

			if(noPass > 0) {
				recordStr.append("共").append(noPass).append("个客户未通过验证.");
				throw new MyException(recordStr.toString());
			}

			/*
			 * 这里再次遍历目的是为了处理传输数据转换为json;
			 * 最后返回;当验证都通过时进行数据传输;
			 * */
			EventCustomers ecus = new EventCustomers();
			ecus.setStartTime(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd")+" 00:00:00");
			ecus.setEndTime(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd")+" 23:59:59");
			ecus.setDeviceSNs(null);
			ecus.setFieldInfo(events.getAddress());

			//根据支公司Id查询对应的名称,避免冗余,数据不是最新的所以重新查询;
			CustomerRelationShip relationShip = (CustomerRelationShip) this.customerRelationshipDao.findById(CustomerRelationShip.class, events.getBranchCompanyId());

			ecus.setShowsName(DateUtils.DateToStr(events.getEventDate(), "yyyy-MM-dd") + relationShip.getBranchCommany()); //场次开始日期（年月日）+支公司名称
			List<Map<String, String>> objMap = new ArrayList<Map<String,String>>();
			Map<String, String> map = null;
			for (ErpCustomer customer : customers) {
				map = new LinkedHashMap<String, String>();
				String idcard = customer.getIdno();
				if(StringUtils.isNotEmpty(idcard)) { //避免空指针问题;
					String year =     idcard.substring(6, 10);
					String month = idcard.substring(10, 12);
					String day = idcard.substring(12, 14);
					String birthday = year + "-" + month + "-" + day + " 00:00:00";
					map.put("birthday", birthday);

				}

				map.put("reportType", customer.getSetmealName()); //确认为: 套餐名称;
				map.put("serviceCode", customer.getCode());// ;
				map.put("sex", customer.getSex());
				map.put("weight", customer.getWeight());
				map.put("userName", customer.getName());
				map.put("height", customer.getHeight());
				map.put("salsman", customer.getSalesMan());
				map.put("ymUserId", "");
				map.put("institution_id", "0");
				objMap.add(map);
			}
			ecus.setUserList(objMap);

			//对象转换为json;
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(ecus);//将java对象转换为json对象
			String str = json.toString();
			String base64Str = null;

			log.info("BASE64加密前: " + str);
			if(StringUtils.isNotEmpty(str)) {
				base64Str = Base64.encodeBase64String(str.getBytes("UTF-8"));
			}

			log.info("BASE64加密后: "+ base64Str) ;

			Map<String, String> param = new HashMap<String, String>();
			param.put("confirmationInfo", base64Str);
			HttpClientTool tool = HttpClientTool.getInstance();
			String response = tool.httpPost(url, param);
			//执行成功后,修改数据为已使用;

			log.info("请求后返回数据: " + response);
			if(response != null && response.contains("SUCCESS")) {
				this.erpBarCodeDetailDao.updateCodeDetail(customers);

				/*
				 * 新增需求
				 * 并将当前无创场次二维码设置为已推送状态，数据库表中需要增加状态字段（只增加二维码这个表即可）*/

				this.erpQRCodeDao.updateErpQRCodePush(id, "1");

				String sql_statusYm = "update erp_customer set STATUS_YM='200' where EVENTS_NO=? ";
				this.dao.updateStatusYm(sql_statusYm, new Object[]{events.getEventsNo()});

				sendMail(events);

			} else if(response != null && response.contains("FAIL")) {
				throw new MyException("存在用户没有检查数据!");
			} else {
				throw new MyException("推送数据请求响应异常!");
			}
		} else {
			throw new MyException("该场次不存在客户信息");
		}
		return true;
	}

	/**
	 * 阳光保险公司使用
	 * create by henry.xu 2016年12月14日
     * @param events
	 * @return
	 */
	private boolean sendMail(ErpEvents events) {
		boolean flag = false;

		/*
		 * 此处单独处理日志.适用于便于查看邮件发送错误;
		 */
		Logger log = Logger.getLogger("Method sendMail");

		log.info("***************************邮件发送开始***************************");
		try {
			/*
			 * 1.创建邮件实体对象;并设置值;
			 */
			MailEntity mail = new MailEntity();
			mail.setAttachMents(null);
			
			//判断邮箱发送;
			List<String> mailsAddr =  new ArrayList<String>();
			String batchNo= events.getBatchNo();
			
			if(batchNo != null && batchNo.contains("JSLD")) {//表示吉思朗的数据;
				mail.setSender("gene@healthlink.cn");
				mail.setPassword("Yue123.com");
				
				mailsAddr.add("gene@healthlink.cn");
				mailsAddr.add("jsrom8397999@163.com");
			} else if(batchNo != null && batchNo.contains("WD")) {//表示知康的数据;
				mail.setSender("nond@healthlink.cn");
				mail.setPassword("Healthlink123");

				mailsAddr.add("zhangsheng9019@163.com");
				mailsAddr.add("gene@healthlink.cn");
				mailsAddr.add("longyu@healthlink.cn");
			}
			
			mail.setHost("smtp.exmail.qq.com"); // 设置邮件服务器,qq
			mail.setReceiver(mailsAddr); //多人邮件地址;
			mail.setUsername("gene@healthlink.cn");

			StringBuilder buffer = new StringBuilder();// 邮件内容

			//收件人
			String subject;//标题
			subject="您好！此次推送信息为：";// 邮件标题
			buffer.append("场次号("+events.getBranchCompany() + DateUtil.getDateTime("yyyyMMdd", events.getEventDate())+")、");
			buffer.append("批次号"+events.getBatchNo()+"、");
			buffer.append("客户数量"+events.getHeadcount()+"位。");
			buffer.append("请确认数据。");
			mail.setSubject(subject);// 邮件标题

			mail.setMessage(buffer.toString());
			log.info("发件内容： " + buffer.toString());

			// 发送邮件,并返回是否成功;
			flag = MailUtil.send(mail);

		} catch(Exception e) {
			flag = false;
			log.error(e.getMessage(), e);
		}
		log.info("***************************邮件发送结束***************************");
		return flag;
	}

	/**
	 * 当推送失败抛出异常后,嗲用修改状态;
	 * create by henry.xu 2016年12月23日
	 */
	public void updateErpQRCodePush(String id) {
		this.erpQRCodeDao.updateErpQRCodePush(id, "-1");
	}

	/**
	 * 根据场次号获取客户信息
	 * @author ybc
	 * @since 2016/12/20
	 */
	public List<ErpCustomer> findCustomerByEventNo(String eveNo){
		return this.dao.findCustomerByEventNo(eveNo);
	}

	/**
	 * @description 根据ID修改会员的报告状态(status_ym)
	 * @param statusYm 状态
	 * @param id 会员ID
	 * @author YoumingDeng
	 * @since: 2016/12/14 14:55
	 */
	public void updateStatusYmById(Integer statusYm, String id){
		dao.updateStatusYmById(statusYm, id);
	}

	/**
	 * @description 根据表名和其ID修改会员的报告状态
	 * @param tableName 表名：与数据库中的表名一致
	 * @param id 表对应的ID值
	 * @param status 状态值
	 * @author YoumingDeng
	 * @since: 2016/12/16 2:52
	 */
	public void updateStatusYm(String tableName, String id, Integer status){
		String sql = null;
		//默认状态为已获取状态
//		Integer statusYm = PropsUtils.getInt("status","statusYm.yhq");
		Integer statusYm = status;
		if("ERP_CUSTOMER".equalsIgnoreCase(tableName)){
			//审核中
			sql = UPDATE_CUST_BY_ID;
		}else if("ERP_PRINT_TASK".equalsIgnoreCase(tableName)){
			//打印中()&已寄送（erp_print_task.print_state = 3）
			sql = UPDATE_CUST_BY_PRINT_TASK_ID;
		}else if ("ERP_REPORT_EXPRESS".equalsIgnoreCase(tableName)){
			sql = UPDATE_CUST_BY_EXPRESS_INFO;
		}
		//参数
		Object[] paramArr = new Object[]{statusYm, id};
		//执行更新
		if(StringUtils.isNotEmpty(sql)) {
			dao.updateStatusYm(sql, paramArr);
		}
	}

	/**
	 * 根据传入值和SQL修改客户状态
	 * SQL在sql.xml配置文件中添加
	 * @param sqlId sql的ID
	 * @param paramList 值
	 * @return String
	 * @since 2017-03-07
	 * @author Damian
	 */
	public String updateStatusYmBySQL(String sqlId, LinkedList paramList) throws FileNotFoundException, DocumentException {
		String result = "0";
		if (StringUtils.isNotEmpty(sqlId)&&!CollectionUtils.isEmpty(paramList)){
			Integer len = paramList.size();
			Object[] paramArr = new Object[len];
			for (int i=0; i<paramList.size(); i++){
				paramArr[i] = paramList.get(i);
			}
			result = this.updateStatusYmBySQL(sqlId, paramArr);
		}
		return result;
    }

	/**
	 * 根据传入值和SQL修改客户状态
     * SQL在sql.xml配置文件中添加
     * @param sqlId sql的ID
	 * @param paramArr 值
	 * @return String
	 * @since 2017-03-07
     * @author Damian
	 */
	public String updateStatusYmBySQL(String sqlId, Object[] paramArr) throws FileNotFoundException, DocumentException {
		String result = "0";
		if (StringUtils.isNotEmpty(sqlId)&&paramArr!=null&&paramArr.length>0) {
			String sql = XmlUtils.getSingleTxt("sql.xml", "/sql_list/sql[@id='"+sqlId+"']");
			log.info("method updateStatusBySQL.sql : ");
			//执行更新
			if(StringUtils.isNotEmpty(sql)) {
				dao.updateStatusYm(sql, paramArr);
				result = "1";
				log.info("method updateStatusBySQL execute done! result: "+result);
			}
		}
		return result;
    }
	/**
	 * @description 根据会员条码修改其报告状态
	 * @author YoumingDeng
	 * @since: 2016/12/16 2:32
	 */
	public void updateStatusYmByCode(Integer statusYm, String code, String name, String eventsNo){
		dao.updateStatusYmByCode(statusYm, code, name, eventsNo);

	}

	/**
	 * 根据快递信息修改状态
	 * @param list
	 * @param statusYm
	 * @throws Exception
	 * @author Damian
	 * @since: 2016/12/28
	 */
	public void updateStatusYmForExpress(List<ErpReportCustomerVo> list, Integer statusYm) throws Exception{
		if(!CollectionUtils.isEmpty(list)) {
		    ErpReportCustomerVo vo ;
		    String UPDATE_CUST_BY_EXPRESS;
			for (int i=0; i<list.size(); i++) {
				vo = list.get(i);
				UPDATE_CUST_BY_EXPRESS = " update erp_customer c set c.status_ym = "+statusYm+" , c.update_time = sysdate where c.is_deleted = 0 " +
											" and events_no = '"+vo.getEventsNo()+"' and code = '"+vo.getCode()+"' and name = '"+vo.getName()+"' ";
				dao.getJdbcTemplate().update(UPDATE_CUST_BY_EXPRESS);
			}
		}
	}

	/**
	 * （弘康）根据code和eventsNo查询用户
	 * @param idno
	 * @param name
	 * @return
	 * @author tangxing
	 * @date 2016-12-30下午3:49:37
	 */
	public ErpCustomer getCustomerByIdnoAndName(String idno,String name){
		List<ErpCustomer> customers = dao.getCustomerByIdnoAndName(idno,name);
		if(customers!=null&&customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	/**
	 * （弘康）入库customer
	 * @param customer
	 * @author tangxing
	 * @date 2016年12月30日16:07:17
	 */
	public String saveCustomer (ErpCustomer customer){
		return dao.saveCustomer(customer);
	}

	/**
	 *
	 * @param timeLimit 时限
	 * @param statusYm 客户状态
	 * @return boolean
     * @throws Exception
	 * @author Damian
	 * @since: 2017-02-14
	 */
	public String findAndSendMailOvertime(Integer timeLimit, Integer statusYm) throws Exception {
		String msg = "无邮件发送";
		boolean flag;
		//1. 查找超时的客户信息
		List<Map<String, Object>> list = this.findCustOvertime(timeLimit, statusYm);

		if (!CollectionUtils.isEmpty(list)) {
			//2. 制作成Excel文件
			//设置表头
			LinkedList<String> colNames = new LinkedList<String>();
			colNames.add("序号");
			colNames.add("姓名");
			colNames.add("条码");
			colNames.add("电话");
			colNames.add("身份证号");
			colNames.add("场次号");
			colNames.add("批次号");

			Date curr = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String nowTimeStr = sdf.format(curr);
			String fileName = "超时打印任务_"+nowTimeStr ;

			String savePath = PropsUtils.getString("constant", "path_yxz");

			File xlsx = listToXlsx(fileName, savePath, colNames, list);
			if (xlsx != null) {
				List<File> attchments = new ArrayList<File>();
				attchments.add(xlsx);

				//3. 生成邮件并且发送
				//邮件内容
				StringBuilder content = new StringBuilder();
				content.append(PropsUtils.getString("mailAcount", "cust.yxz"));
                content.append(",你好!<br/>");
				content.append(PropsUtils.getString("mailAcount", "content.yxz"));
                content.append("<div style='text-align:right'>");
				content.append(nowTimeStr);
                content.append("</div><br/>");
                content.append("<div style='text-align:right'>");
				content.append(PropsUtils.getString("mailAcount", "nickName.yxz"));
                content.append("</div><br/>");

				String subject = PropsUtils.getString("mailAcount", "subject.yxz")+"_"+nowTimeStr;
				//发送邮件
				flag = this.sendMail(subject, content.toString(), attchments);
				if (flag){
					msg = "["+subject+"] 邮件已发送!!!";
				} else {
					msg = "当前日期["+nowTimeStr+"] "+msg;
				}
			}
		}
		return msg;
	}

	/**
	 * 查找超时的客户信息
	 * @param timeLimit 时限
	 * @param statusYm 客户状态
	 * @return List
	 * @throws Exception
	 * @author Damian
	 * @since: 2017-02-14
	 */
	public List<Map<String, Object>> findCustOvertime(Integer timeLimit, Integer statusYm) throws Exception{
		List<Map<String, Object>> list = null;
		if (timeLimit!=null&& statusYm!=null) {
			String sql = XmlUtils.getSingleTxt("sql.xml", "/sql_list/sql[@id='FIND_CUSTOMER_OVER_TIME']");
			list = dao.getJdbcTemplate().queryForList(sql,new Integer[]{statusYm, timeLimit});
		}
		return list;
	}

	/**
	 * 根据客户信息生成XLSX文件
	 * @param fileName 文件名
	 * @param savePath 保存路径
	 * @param colNames 列名
	 * @param list	   客户信息
	 * @return File
	 * @throws Exception
	 * @author Damian
	 * @since: 2017-02-14
	 */
	private static File listToXlsx(String fileName, String savePath, LinkedList<String> colNames, List<Map<String, Object>> list)  throws Exception{
		File xlsxFile;

		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		Sheet sh = wb.createSheet();
		sh.autoSizeColumn(1,true);

		Font headFont = wb.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setFontName("黑体");
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setFont(headFont);

        CellStyle normalStyle = wb.createCellStyle();
        normalStyle.setAlignment(CellStyle.ALIGN_CENTER);

        //行
		Map<String, Object> dataRow = null;
		for(int rownum = 0; rownum < list.size()+1; rownum++){

			Row row = sh.createRow(rownum);
			//设置表头
			if (rownum==0){
				for (int headNum=0; headNum< colNames.size(); headNum++){
					Cell headCell = row.createCell(headNum);
					headCell.setCellStyle(headStyle);
					headCell.setCellValue(colNames.get(headNum));
				}
			}
			//其他列
			else {
				dataRow = list.get(rownum-1);

				Cell rowCell = row.createCell(0);
				rowCell.setCellType(Cell.CELL_TYPE_STRING);
				rowCell.setCellStyle(normalStyle);
				rowCell.setCellValue(rownum);

				Cell name = row.createCell(1);
                name.setCellType(Cell.CELL_TYPE_STRING);
                name.setCellStyle(normalStyle);
				name.setCellValue((String) dataRow.get(ErpCustomer.F_NAME.toUpperCase()));

				Cell code = row.createCell(2);
				code.setCellType(Cell.CELL_TYPE_STRING);
                code.setCellStyle(normalStyle);
				code.setCellValue((String) dataRow.get(ErpCustomer.F_CODE.toUpperCase()));

				Cell phone = row.createCell(3);
                phone.setCellType(Cell.CELL_TYPE_STRING);
                phone.setCellStyle(normalStyle);
				phone.setCellValue((String) dataRow.get(ErpCustomer.F_PHONE.toUpperCase()));

				Cell idNo = row.createCell(4);
                idNo.setCellType(Cell.CELL_TYPE_STRING);
                idNo.setCellStyle(normalStyle);
				idNo.setCellValue((String) dataRow.get(ErpCustomer.F_IDNO.toUpperCase()));

				Cell eventsNo = row.createCell(5);
                eventsNo.setCellType(Cell.CELL_TYPE_STRING);
                eventsNo.setCellStyle(normalStyle);
				eventsNo.setCellValue((String) dataRow.get("EVENTSNO"));

				Cell batchNo = row.createCell(6);
				batchNo.setCellType(Cell.CELL_TYPE_STRING);
				batchNo.setCellStyle(normalStyle);
				batchNo.setCellValue((String) dataRow.get("BATCHNO"));
			}
		}

		File saveDir = new File(savePath);
		if (!saveDir.exists()||!saveDir.isDirectory()){
			saveDir.mkdirs();
		}

		String xlsxName = savePath + File.separator + fileName;
		if (!StringUtils.containsIgnoreCase(xlsxName, ".xlsx")){
            xlsxName = xlsxName + ".xlsx";
		}

		xlsxFile = new File(xlsxName);
		if (!xlsxFile.getParentFile().exists()) {
			xlsxFile.getParentFile().mkdirs();
		}
		if (!xlsxFile.exists()) {
			xlsxFile.createNewFile();
		}

		FileOutputStream out = new FileOutputStream(xlsxFile);
		wb.write(out);
		out.close();

		wb.dispose();
		return xlsxFile;
	}

	/**
	 * 发送邮件
	 * @param subject 主题
	 * @param content 内容
	 * @param files 附件
	 * @return boolean
	 */
	private boolean sendMail(String subject, String content, List<File> files){
		MailEntity mail = new MailEntity();

		//发件邮箱相关信息
		mail.setHost(PropsUtils.getString("mailAcount", "host.yxz"));
		mail.setSender(PropsUtils.getString("mailAcount", "mail.account"));
		mail.setName(PropsUtils.getString("mailAcount", "mail.nickName"));
		mail.setUsername(PropsUtils.getString("mailAcount", "mail.account"));
		mail.setPassword(PropsUtils.getString("mailAcount", "mail.password"));

		//邮件接收人相关信息
		List<String> receivers = new ArrayList<String>();
		String accounts = PropsUtils.getString("mailAcount", "account.to.yxz");
		if (accounts.contains(",")){
			String[] recArr = accounts.split(",");
			Collections.addAll(receivers, recArr);
		} else {
			receivers.add(accounts);
		}
		mail.setReceiver(receivers);
		if (StringUtils.isEmpty(subject)) {
			subject = PropsUtils.getString("mailAcount", "subject.yxz");
		}

		//主题
		mail.setSubject(subject);

		//内容
		if (StringUtils.isEmpty(content)) {
			content = PropsUtils.getString("mailAcount", "content.yxz");
		}
		mail.setMessage(content);

		//附件
		mail.setAttachMents(files);

		//发送邮件
		return MailUtil.send(mail);
	}

	/**
	 * 根据条件是否精确查询
	 *
	 * @param params  查询条件
	 * @param isExact true: 精确， false:模糊
	 * @return List<ErpCustomer>
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-18 下午1:29:13
	 */
	public List<ErpCustomer> listCustomerByProps(Map<String, String> params, boolean isExact) throws Exception {
		List<ErpCustomer> list = null;
		if (!params.isEmpty()) {
			list = dao.listCustomerByProps(params, isExact);
		}
		return list;
	}

	/**
	 * 根据条件精确查询
	 *
	 * @param params 查询条件
	 * @return List<ErpCustomer>
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-18 下午1:30:23
	 */
	public List<ErpCustomer> listCustomerByProps(Map<String, String> params) throws Exception {
		return this.listCustomerByProps(params, true);
	}


	/**
	 * 根据场次号查询打包客户信息的Excel文件为Zip文件
	 * @param eventsNo
	 * @return File
	 * @throws Exception
	 * @author Damian
	 * @since 2017-02-24
	 */
	public File findAndPatchByEventsNo(String eventsNo) throws Exception{
	    File file = null;
		if (StringUtils.isNotEmpty(eventsNo)){
			List<File> excelList = new ArrayList<File>();
			File excel;
			//打包压缩
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(Calendar.getInstance().getTime());
			//文件保存目录
			String saveDir = PropsUtils.getString("constant", "excel_save_wc")+File.separator+today;
			Map<String,String> params = new HashMap<String, String>();
			params.put(ErpCustomer.F_ISDELETED, "0");
			if (eventsNo.indexOf(",")>-1){
				String[] noArr = eventsNo.split(",");
				for (int i=0; i<noArr.length; i++){
					params.put(ErpCustomer.F_EVENTSNO, noArr[i]);
					excel = this.exportExcel(params, saveDir);
					if (excel!=null){
						excelList.add(excel);
					}
				}
			} else {
				params.put(ErpCustomer.F_EVENTSNO, eventsNo);
				excel = this.exportExcel(params, saveDir);
				if (excel!=null){
					excelList.add(excel);
				}
			}
			if (!CollectionUtils.isEmpty(excelList)){
				log.info("根据场次号["+eventsNo+"]查找到的客户信息有 "+excelList.size()+" 个excel文件");
				//文件名称
				String zipFileName = PropsUtils.getString("constant", "excel_name_wc")+"_"+today;
				//返回压缩文件
				file = new ZipFileUtil().compressList(excelList, saveDir, zipFileName);
			}else {
				log.info("根据场次号["+eventsNo+"]没有查找到客户信息...");
			}
		}
		log.info("zip压缩包的地址： "+file.getAbsolutePath());
		return file;
	}

	/**
	 * 根据条件查询
	 * @param params 查询条件
	 * @return File Excel文件
	 * @throws Exception
	 * @author Damian
	 * @since 2017-02-22
	 */
	public File exportExcel(Map<String,String> params, String saveDir) throws Exception {
		File file = null;
		if (!CollectionUtils.isEmpty(params)){
		    List<ErpEvents> eventsList = eventsDao.listEventsByProps(params);
			List<ErpCustomer> list = this.listCustomerByProps(params);
			if (!CollectionUtils.isEmpty(eventsList)&&
					!CollectionUtils.isEmpty(list)){
				ErpEvents events = eventsList.get(0);

				//设置表头
				LinkedList<String> colNames = new LinkedList<String>();
				colNames.add("序号");
				colNames.add("采样日期");
				colNames.add("客户检测码");
				colNames.add("姓名");
				colNames.add("性别");

				colNames.add("年龄");
				colNames.add("身份证号");
				colNames.add("电话");
				colNames.add("二维码对应支公司");
				colNames.add("设备所在公司");

				colNames.add("套餐名");
				colNames.add("营销员");
				colNames.add("营销员工号");
				colNames.add("身高");
				colNames.add("体重");

				//采样日期
				Date sampleDate = events.getEventDate();
				String dateStr = sampleDate.toString().substring(0,10);
				//查找远盟对接人
				String relationShipProId = events.getCustomerRelationShipProId();
				CustomerRelationShipPro shipPro = customerRelationshipProDao.findCustRelShipProById(CustomerRelationShipPro.class, relationShipProId);
				//远盟对接人
				String linkName = "";
				if (shipPro!=null){
					linkName = shipPro.getLinkName();
				}
				//场次实际人数
				int humanNum = list.size();
				//文件名
				String fileName = events.getBatchNo()+"_"+dateStr+"_"+events.getBranchCompany()
									+"_"+linkName+"_"+humanNum;
				//转换成 Execl 文件
				file = listToFileWC(fileName, saveDir, colNames, list);
			}
		}
		return file;
	}

	/**
	 * 根据客户信息生成XLSX文件(无创)
	 * @param fileName 文件名
	 * @param savePath 保存路径
	 * @param colNames 列名
	 * @param list	   客户信息
	 * @return File	   Excel文件
	 * @throws Exception
	 * @author Damian
	 * @since 2017-02-22
	 */
	private static File listToFileWC(String fileName, String savePath, LinkedList<String> colNames, List<ErpCustomer> list)  throws Exception{
		File excel;

		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		Sheet sh = wb.createSheet();
		sh.autoSizeColumn(1,true);

		Font headFont = wb.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setFontName("黑体");
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setFont(headFont);
		headStyle.setWrapText(false);

		CellStyle normalStyle = wb.createCellStyle();
		normalStyle.setAlignment(CellStyle.ALIGN_CENTER);

		ErpCustomer customer;
		for(int rowNum = 0; rowNum < list.size()+1; rowNum++){
			Row row = sh.createRow(rowNum);
			//设置表头
			if (rowNum==0){
				for (int headNum=0; headNum< colNames.size(); headNum++){
					Cell headCell = row.createCell(headNum);
					headCell.setCellStyle(headStyle);
					headCell.setCellValue(colNames.get(headNum));
				}
			}
			//其他列
			else {
				customer = list.get(rowNum-1);

				Cell rowCell = row.createCell(0);
				rowCell.setCellType(Cell.CELL_TYPE_STRING);
				rowCell.setCellStyle(normalStyle);
				rowCell.setCellValue(rowNum);

				Cell sampleDate = row.createCell(1);
				sampleDate.setCellType(Cell.CELL_TYPE_STRING);
				sampleDate.setCellStyle(normalStyle);
				sampleDate.setCellValue(customer.getSamipleDateStr());

				Cell code = row.createCell(2);
				code.setCellType(Cell.CELL_TYPE_STRING);
				code.setCellStyle(normalStyle);
				code.setCellValue(customer.getCode());

				Cell name = row.createCell(3);
				name.setCellType(Cell.CELL_TYPE_STRING);
				name.setCellStyle(normalStyle);
				name.setCellValue(customer.getName());

				Cell gender = row.createCell(4);
				gender.setCellType(Cell.CELL_TYPE_STRING);
				gender.setCellStyle(normalStyle);
				gender.setCellValue(customer.getSex());

				Cell age = row.createCell(5);
				age.setCellType(Cell.CELL_TYPE_STRING);
				age.setCellStyle(normalStyle);
				age.setCellValue(customer.getAge());

				Cell idNo = row.createCell(6);
				idNo.setCellType(Cell.CELL_TYPE_STRING);
				idNo.setCellStyle(normalStyle);
				idNo.setCellValue(customer.getIdno());

				Cell phone = row.createCell(7);
				phone.setCellType(Cell.CELL_TYPE_STRING);
				phone.setCellStyle(normalStyle);
				phone.setCellValue(customer.getPhone());

				//二维码对应的支公司
				Cell subCompany = row.createCell(8);
				subCompany.setCellType(Cell.CELL_TYPE_STRING);
				subCompany.setCellStyle(normalStyle);
				subCompany.setCellValue(customer.getBranchCompany());

				//设备所在支公司
				Cell deviceCompany = row.createCell(9);
				deviceCompany.setCellType(Cell.CELL_TYPE_STRING);
				deviceCompany.setCellStyle(normalStyle);
				deviceCompany.setCellValue(customer.getBranchCompany());

				Cell combo = row.createCell(10);
				combo.setCellType(Cell.CELL_TYPE_STRING);
				combo.setCellStyle(normalStyle);
				combo.setCellValue(customer.getSetmealName());

				Cell salesMan = row.createCell(11);
				salesMan.setCellType(Cell.CELL_TYPE_STRING);
				salesMan.setCellStyle(normalStyle);
				salesMan.setCellValue(customer.getSalesMan());

				Cell salesManNo = row.createCell(12);
				salesManNo.setCellType(Cell.CELL_TYPE_STRING);
				salesManNo.setCellStyle(normalStyle);
				salesManNo.setCellValue(customer.getSalesManNo());

				Cell height = row.createCell(13);
				height.setCellType(Cell.CELL_TYPE_STRING);
				height.setCellStyle(normalStyle);
				height.setCellValue(customer.getHeight());

				Cell weight = row.createCell(14);
				weight.setCellType(Cell.CELL_TYPE_STRING);
				weight.setCellStyle(normalStyle);
				weight.setCellValue(customer.getWeight());

			}
		}

		File saveDir = new File(savePath);
		if (!saveDir.exists()||!saveDir.isDirectory()){
			saveDir.mkdirs();
		}

		String xlsxName = savePath + File.separator + fileName;
		if (!StringUtils.containsIgnoreCase(xlsxName, ".xlsx")){
			xlsxName = xlsxName + ".xlsx";
		}

		excel = new File(xlsxName);
		if (!excel.exists()) {
			excel.createNewFile();
		}

		FileOutputStream out = new FileOutputStream(excel);
		wb.write(out);
		out.close();

		wb.dispose();
		return excel;
	}

	/**
	 * @param note  备注
	 * @param customerId id 
	 * @author Carly
	 * @param deletedStatus 
	 * @since 2017年4月5日18:56:31
	 * 异常报告处理
	 */
	public boolean updateDeletedStatus(String customerId, String note, String deletedStatus) throws Exception {
		return dao.updateDeletedStatus(customerId, note, deletedStatus);
		
	}

}


















