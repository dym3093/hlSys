package org.hpin.warehouse.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipDao;
import org.hpin.base.customerrelationship.dao.CustomerRelationshipProDao;
import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.base.usermanager.dao.DeptDao;
import org.hpin.base.usermanager.dao.UserDao;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.exception.MyException;
import org.hpin.common.util.DateUtil;
import org.hpin.common.util.ExcelUtils;
import org.hpin.common.util.StaticMehtod;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpConferenceDao;
import org.hpin.events.dao.ErpEventsDao;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.hpin.warehouse.dao.ErpApplicationDao;
import org.hpin.warehouse.dao.ErpApplicationDetailDao;
import org.hpin.warehouse.dao.ErpBxCompanyPreSetDao;
import org.hpin.warehouse.dao.ErpHKPrepCustomerDao;
import org.hpin.warehouse.dao.ErpProductComboDao;
import org.hpin.warehouse.dao.ErpProductDao;
import org.hpin.warehouse.entity.ErpApplication;
import org.hpin.warehouse.entity.ErpApplicationDetail;
import org.hpin.warehouse.entity.ErpBxCompanyPreSet;
import org.hpin.warehouse.entity.ErpPreCustomer;
import org.hpin.warehouse.entity.ErpProComboProduct;
import org.hpin.warehouse.entity.ErpProduct;
import org.hpin.warehouse.entity.ErpProductCombo;
import org.hpin.warehouse.entity.vo.ApplicationDataVo;
import org.hpin.warehouse.mail.MailSenderInfo;
import org.hpin.warehouse.mail.SimpleMailSender;
import org.hpin.warehouse.util.CustomerExcelUtils;
import org.hpin.warehouse.util.ResultWarehouseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @description: 基因物品申请Service
 * create by henry.xu 2016年10月10日
 */
@Service("org.hpin.warehouse.service.ErpApplicationService")
@Transactional
public class ErpApplicationService extends BaseService {
	private static Logger log = Logger.getLogger(ErpApplicationService.class);

	@Autowired
	private ErpApplicationDao erpApplicationDao ; //基因物品申请Dao

	@Autowired
	private ErpApplicationDetailDao erpApplicationDetailDao; //基因物品申请明细dao

	@Autowired
	private ErpProductComboDao erpProductComboDao; //产品套餐dao
	
	@Autowired
	private ErpProductDao erpProductDao; //产品dao

	@Autowired
	private UserService userService; //用户查询
	@Autowired
	private ErpBxCompanyPreSetDao erpBxCompanyPreSetDao; //客户信息预设置Dao
	@Autowired
	private CustomerRelationshipDao customerRelationshipDao; //支公司;
	@Autowired
	private CustomerRelationshipProDao customerRelationshipProDao; //项目类型;
	
	@Autowired
	private ErpHKPrepCustomerDao erpHKPrepCustomerDao; //客户信息预制Dao
	@Autowired
	private ErpConferenceDao erpConferenceDao; //会场dao

	@Autowired
	private UserDao userDao ; //用户Dao
	@Autowired
	private DeptDao deptDao; //总公司Dao

	@Autowired
	private ErpEventsDao erpEventsDao; //场次dao;

	//@Value("${mail.account}")
	@Value("#{setting['mail.account']}")
	private String sendMailAccount; //发件人账户;

	//@Value("${mail.password}")
	@Value("#{setting['mail.password']}")
	private String sendMailPassword; //发件人密码;

	//@Value("${receive.receiveMailAccount}")
	@Value("#{setting['receive.receiveMailAccount']}")
	private String receiveMailAccount; //收件人账号;

	/**
	 * 弘康客户信息批量导入;
	 * create by henry.xu 2016年12月29日
	 * @param affix
	 * @param affixFileName
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public boolean saveCustomerHKByExcel(File affix, String affixFileName, 
			ErpApplication application, User userInfo, List<ErpApplicationDetail> details) throws Exception  {

		boolean result = false;
		/*
		 * 1.验证Excel表头规则;
		 * Excel表头为：客户唯一识别号、姓名、套餐;
		 */
		if (StringUtils.isEmpty(affixFileName)) {
			result = false;
			throw new MyException("文件上传失败!");
		}

		InputStream is = null;
		Workbook wb = null; 
		File path = affix.getAbsoluteFile();

		is = new FileInputStream(path);

		wb = new XSSFWorkbook(is);

		Sheet sheet = wb.getSheetAt(0);
		if(null == sheet) {
			result = false;
			throw new MyException("Excel文件错误, 请检查导入文件模板是否正确!");
		}

		/*
		 * 1. 获取导入excel中的数据;放入list中;
		 */
		List<ErpPreCustomer> precs = CustomerExcelUtils.dealExcelSheetData(sheet);

		if(precs.size() <= 0) {
			result = false;
			throw new MyException("导入的Excel中不存在数据,请确认后再导入!");
		}
		
		/*
		 * 2. 生成场次号;以及会议管理;
		 */
		//存在异常; 但是由于业务需要,不会出现不存在的情况.如果出现了,说明业务出问题了
		String companyId = application.getBannyCompanyId();
		String projectCode = application.getProjectCode();
		
		String bacthPre = this.customerRelationshipDao.findBacthPreByCondition(companyId, projectCode);
		String eventsNo = bacthPre + DateUtil.getDateTime("yyyyMMddHHmmss", new Date());//场次号和批次号规则为HK+当日日期，如HK20161215。
		
		log.info("场次号自动生成为: " + eventsNo);
		
		/*
		 * 验证同一导入excel不能有重复套餐;存在则退出;
		 * 验证弘康套餐
		 */
		StringBuilder sb = new StringBuilder("Excel中第");
		int j = 0;
		boolean flag = false;
		
		for(int i=0; i<precs.size(); i++) {
			ErpPreCustomer prec = precs.get(i);
			j++;
			//根据支公司,项目编码,远盟套餐名称验证是否存在该远盟套餐
			boolean tempFlag = this.customerRelationshipProDao.isExcitComboName(companyId, projectCode, prec.getYmCombo());
			
			if(!tempFlag) {
				flag = true;
				sb.append(j).append(", ");
				
			}
		}
		
		if(flag) {
			sb.append("行的项目套餐在远盟数据库没有对应数据,请核对后再导入!");
			result = false;
					
			throw new MyException(sb.toString());			
		}

		/*
		 * 根据业务需求做出相应调整;导入的数据为一批次物料;
		 * 物料申请.modified by henry.xu 20170117
		 * 4. 生成物料申请单中数据;
		 * 导入数据对应的 是收件人的信息： 收件人姓名，电话，地址
		 * 被检人的姓名，电话，样本盒接收地址对应;
		 */
		ErpApplication appc = new ErpApplication();
		appc.setBannyCompanyId(companyId);
		appc.setBannyCompanyName(application.getBannyCompanyName());
		appc.setHopeDate(application.getHopeDate());
		appc.setOwnerCompanyId(application.getOwnerCompanyId());
		appc.setOwnerCompanyName(application.getOwnerCompanyName());
		appc.setProjectCode(projectCode);
		appc.setProjectName(application.getProjectName());
		appc.setProjectOwner(application.getProjectOwner());
		appc.setProviceName(application.getProviceName());
		appc.setRemark(application.getRemark());
		appc.setRequirement(application.getRequirement());
		appc.setLinkTel(application.getLinkTel());
		appc.setLinkName(application.getLinkName());
		
		//由于线下操作,所以导致具体的收件人,收件人电话,地址未知默认为空;
		appc.setReceiveName(null);
		appc.setReceiveTel(null);
		appc.setAddress(null);
		String currentTime = StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss");
		
		appc.setCreateUserId(userInfo.getId()); //为当前填写申请单的用户（create_user_id 对应的用户名）
		appc.setCreateTime(new Date());
		appc.setStatus("0"); //默认为未发货状态
		appc.setIsDeleted(0); //默认转态为有效0
		appc.setApplicationNo(currentTime); //设置申请号,规则为时间搓
		log.info("物料申请号: " + currentTime);
		erpApplicationDao.saveErpApplication(appc);

		/*
		 * 3.保存到与导入设置客户信息表中;
		 */
		String shipProId = this.customerRelationshipDao.findCustomerRelationshipProId(companyId, projectCode);
		for(ErpPreCustomer prec : precs) {
			prec.setCompanyId(appc.getBannyCompanyId());
			prec.setShipPorId(shipProId);
			prec.setErpApplicationId(appc.getId());
			prec.setCreateTime(new Date());
			prec.setCreateUserId(userInfo.getId());
			prec.setIsDeleted(0);
			prec.setEventsNo(eventsNo);
			prec.setBatchPre(bacthPre);

			this.erpHKPrepCustomerDao.save(prec);
		}
		
		/*
		 * 处理套餐对应的产品信息;
		 * 根据前台返回数据进行重新组装数据;
		 */
		List<ErpApplicationDetail> newDetails = this.dealNewDetailsList(details);
		//获取保存后ID
		int len = newDetails.size();
		if(newDetails != null && len > 0) {
			for(int i=0; i<len; i++) {
				ErpApplicationDetail detail = newDetails.get(i);
				//当该对象为空时, 跳过继续;
				if(detail == null) {
					continue;
				}

				detail.setApplicationId(appc.getId());
				detail.setCreateTime(new Date());
				detail.setCreateUserId(userInfo.getId());
				detail.setStatus(null); //默认为未发货状态                   状态：0已发货  1部分发货
				detail.setSendCount(0);//已发货为0
				detail.setIsDeleted(0);//默认转态为有效0

				this.erpApplicationDetailDao.saveErpApplicationDetail(detail);
			}
		}

		if(wb != null) {
			wb.close();

		}
		
		result = true;

		return result;
	}
	
	public Map<String, Object> findApplicationById(String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//查询主体对象
		resultMap.put("application", findObjectById(id));

		//查询明细对象集合;
		resultMap.put("details", this.erpApplicationDetailDao.findObjectsByApplicationId(id));

		return resultMap;
	}

	/**
	 * 浏览时候使用该方法,该方法返回的是具体明细;
	 * @param id
	 * @return
	 */
	public Map<String, Object> findApplicationToDetailById(String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//查询主体对象
		resultMap.put("application", findObjectById(id));

		//查询明细对象集合; 处理过后, 所以数据有点不好理解;
		resultMap.put("details", this.erpApplicationDetailDao.findObjectsByApplicationId(id));

		return resultMap;
	}

	/**
	 * 根据Id获取对象;
	 * @param id
	 * @return
	 */
	public ErpApplication findObjectById(String id) {
		return this.erpApplicationDao.findObjectById(id);
	}

	/**
	 * 修改数据; 具体方式注解见saveApplication
	 * @param application
	 * @param userInfo
	 * @param details
	 * @throws MyException 
	 */
	public boolean updateApplication(ErpApplication application, User userInfo,
			List<ErpApplicationDetail> details) throws Exception {
		boolean flag = false;

		if(details == null || details.size() <= 0) {
			throw new MyException("没有添加产品明细,请添加后再点击保存!");
		}

		/*
		 * 主体;
		 */
		ErpApplication applicationOld = findObjectById(application.getId());
		applicationOld.setUpdateTime(new Date());
		applicationOld.setUpdateUserId(userInfo.getId());
		applicationOld.setAddress(application.getAddress());
		applicationOld.setBannyCompanyId(application.getBannyCompanyId());
		applicationOld.setHopeDate(application.getHopeDate());
		applicationOld.setLinkName(application.getLinkName());
		applicationOld.setLinkTel(application.getLinkTel());
		applicationOld.setProjectCode(application.getProjectCode());
		applicationOld.setProjectName(application.getProjectName());
		applicationOld.setProjectOwner(application.getProjectOwner());
		applicationOld.setReceiveName(application.getReceiveName());
		applicationOld.setReceiveTel(application.getReceiveTel());
		applicationOld.setRequirement(application.getRequirement());
		applicationOld.setStatus("0"); //修改原有状态为0,待发货;
		//邮件内容使用;
		application.setApplicationNo(applicationOld.getApplicationNo());

		/*
		 * 处理套餐对应的产品信息;
		 * 根据前台返回数据进行重新组装数据;
		 */
		List<ErpApplicationDetail> newDetails = dealNewDetailsList(details);

		/*
		 * 此处由于有修改,所以重新邮件要发送的内容附件处理;
		 * 邮件要发送的内容附件处理;
		 */
		List<ApplicationDataVo> dataVos = dealDataForExcelData(userInfo, application, newDetails);
		//创建excel
		String currentTime = StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss");
		File attachment = createExcelFile(currentTime + "_" + application.getProjectCode(), dataVos);

		//设置基因申请路径赋值;
		if(attachment != null) {
			application.setAttachmentPath(attachment.getAbsolutePath());			
		}
		this.erpApplicationDao.updateErpApplication(applicationOld);

		/*
		 * 明细处理修改
		 */
		//先删除原有老数据;此处由于明细数据没有处理,所以物理删除;没有使用逻辑删除;
		this.erpApplicationDetailDao.deleteApplicationDetail(application.getId());
		//获取保存后ID
		String sourceId = application.getId();
		int len = newDetails.size();
		if(newDetails != null && len > 0) {
			for(int i=0; i<len; i++) {
				ErpApplicationDetail detail = newDetails.get(i);
				//当该对象为空时, 跳过继续;
				if(detail == null) {
					continue;
				}

				detail.setApplicationId(sourceId);
				detail.setCreateTime(new Date());
				detail.setCreateUserId(userInfo.getId());
				detail.setStatus(null); //默认为未发货状态                   状态：0已发货  1部分发货
				detail.setSendCount(0);//已发货为0
				detail.setIsDeleted(0);//默认转态为有效0

				erpApplicationDetailDao.saveErpApplicationDetail(detail);
			}
		}

		/*
		 * 发送邮件;
		 */
		//提交的时候，同时发送邮件。
		List<File> attachmentList = new ArrayList<File>();
		attachmentList.add(attachment);
		flag = true; //sendMail(userInfo, application, newDetails, attachmentList); modified by henry.xu 20161219 暂时屏蔽掉;
		return flag;

	}

	/**
	 * 保存基因物品申请实体;
	 * 1.插入申请单主表信息时，status（状态）字段默认为：0（待发货）
	 * 2.【寄送地址】的赋值规则：【省份】+【城市】+【寄送地址】
	 * 3.为当前填写申请单的用户（create_user_id 对应的用户名）
	 * 4. 提交的时候，同时发送邮件。
	 * @param application
	 */
	public boolean saveApplication(ErpApplication application, User userInfo, 
			List<ErpApplicationDetail> details) throws Exception {
		boolean flag = false;

		if(details == null || details.size() <= 0) {
			throw new MyException("没有添加产品明细,请添加后再点击保存!");
		}

		String currentTime = StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss");
		/*
		 * 主体
		 */
		application.setCreateUserId(userInfo.getId()); //为当前填写申请单的用户（create_user_id 对应的用户名）
		application.setCreateTime(new Date());
		application.setStatus("0"); //默认为未发货状态
		application.setIsDeleted(0); //默认转态为有效0
		application.setApplicationNo(currentTime); //设置申请号,规则为时间搓

		//新增的时候【寄送地址】的赋值规则：【省份】+【城市】+【寄送地址】
		String address = application.getProviceName() + "省" + application.getCityName() + "市" + application.getAddress();
		application.setAddress(address);

		/*
		 * 邮件要发送的内容附件处理;
		 */
		List<ErpApplicationDetail> newDetails = dealNewDetailsList(details);
		List<ApplicationDataVo> dataVos = dealDataForExcelData(userInfo, application, newDetails);
		//创建excel
		File attachment = createExcelFile(currentTime + "_" + application.getProjectCode(), dataVos);

		//设置基因申请路径赋值;
		if(attachment != null) {
			application.setAttachmentPath(attachment.getAbsolutePath());			
		}

		erpApplicationDao.saveErpApplication(application);

		/*
		 * 明细;
		 */
		//获取保存后ID
		String sourceId = application.getId();
		int len = newDetails.size();
		if(newDetails != null && len > 0) {
			for(int i=0; i<len; i++) {
				ErpApplicationDetail detail = newDetails.get(i);
				//当该对象为空时, 跳过继续;
				if(detail == null) {
					continue;
				}
				
				detail.setApplicationId(sourceId);
				detail.setCreateTime(new Date());
				detail.setCreateUserId(userInfo.getId());
				detail.setStatus(null); //默认为未发货状态      状态：0已发货  1部分发货
				detail.setSendCount(0);//已发货为0
				detail.setIsDeleted(0);//默认转态为有效0
				erpApplicationDetailDao.saveErpApplicationDetail(detail);
			}
		}

		/*
		 * 发送邮件;
		 */
		//提交的时候，同时发送邮件。
		List<File> attachmentList = new ArrayList<File>();
		attachmentList.add(attachment);
		flag = true; //sendMail(userInfo, application, newDetails, attachmentList); modified by henry.xu 20161219 暂时屏蔽掉;
		return flag;
	}
	
	private List<ErpApplicationDetail> dealNewDetailsList(List<ErpApplicationDetail> details) {
		/*
		 * 处理套餐对应的产品信息;
		 * 根据前台返回数据进行重新组装数据;
		 */
		List<ErpApplicationDetail> newDetails = new ArrayList<ErpApplicationDetail>(); //组装后数据存放以及使用;
		ErpApplicationDetail newDetail = null;
		for(int i=0; i<details.size(); i++) {
			if(details.get(i) == null) {
				continue;
			}
			
			//获取产品套餐id
			String productId = details.get(i).getProductId();
			String proComboId = details.get(i).getProComboId();
			ErpProduct product = this.erpProductDao.findById(productId);
			
			//获取产品套餐名称;
			ErpProductCombo combo = (ErpProductCombo) this.erpProductComboDao.findById(ErpProductCombo.class, proComboId);
			
			//重新组装;
			newDetail = new ErpApplicationDetail();
			newDetail.setApplicationCount(details.get(i).getApplicationCount()); //每个产品对应的申请数量是相同的;
			newDetail.setProComboId(proComboId);
			newDetail.setProductId(productId);
			newDetail.setProductName(product.getProductName());
			newDetail.setProductTypeName(product.getProductTypeName());
			newDetail.setStandard(product.getStandard());
			newDetail.setProductComboName(combo.getProductComboName());
			newDetails.add(newDetail);
					
		}
		
		return newDetails; 
	}

	/**
	 * 登录用户只能看到自己提交的申请单。
	 * @param page 分页, userInfo 登陆人对象, 
	 * params查询参数[ ownerCompanyName:总公司名称, bannyCompanyName:支公司名称,
	 * status:状态, projectCode:项目编码, projectName:项目名称
	 * ,projectOwner:项目负责人, startDate:申请开始日期, endDate:申请截止日期 , receiveName:收件人]
	 */
	@SuppressWarnings("rawtypes")
	public void findApplicationsByPage(Page page, Map<String, String> params) {
		//list查询参数;
		List<Object> objs = new ArrayList<Object>();

		//sql
		//查询参数处理
		StringBuilder jdbcsql = this.erpApplicationDao.dealParamsReturnSql(params);

		//根据时间排序,降序;
		jdbcsql.append(" order by apc.create_time desc, apc.id  ");


		//count
		long count = this.erpApplicationDao.findJdbcCount(jdbcsql, objs);
		page.setTotalCount(count);

		//list
		objs.add(page.getPageNumEndCount());
		objs.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpApplication> rowMapper = new BeanPropertyRowMapper<ErpApplication>(ErpApplication.class);
		List<?> list = this.erpApplicationDao.findJdbcList(jdbcsql, objs, rowMapper);
		page.setResults(list);

	}

	/*私有方法*/

	/**
	 * 转换成要使用的excel数据;
	 * @param userInfo
	 * @param application
	 * @param details
	 * @return
	 */
	private List<ApplicationDataVo> dealDataForExcelData(User userInfo, ErpApplication application, 
			List<ErpApplicationDetail> details) {
		List<ApplicationDataVo> datalists = new ArrayList<ApplicationDataVo>();
		ApplicationDataVo data = null;
		if(details != null && details.size() > 0) {
			for(ErpApplicationDetail detal : details) {

				if(detal == null) {
					continue;
				}

				data = new ApplicationDataVo();
				data.setProductComboName(detal.getProductComboName());
				data.setHopeDate(application.getHopeDate());
				data.setApplicationCount(detal.getApplicationCount());
				data.setProductName(detal.getProductName());
				data.setVersion("通用");
				data.setApplyForUserName(userInfo.getUserName());
				data.setProjectCode(application.getProjectCode());
				data.setProjectName(application.getProjectName());
				data.setProjectOwner(application.getProjectOwner());
				data.setOwnerCompanyName(application.getOwnerCompanyName());
				data.setBannyCompanyName(application.getBannyCompanyName());
				data.setReceiveName(application.getReceiveName());
				data.setReceiveTel(application.getReceiveTel());
				data.setAddress(application.getAddress());
				data.setStoreName(""); //默认无;

				datalists.add(data);
			}
		}
		return datalists;
	}

	@SuppressWarnings("resource")
	private File createExcelFile(String fileName, List<ApplicationDataVo> records) throws Exception {
		FileOutputStream wbFos = null;
		HSSFWorkbook wb = null;
		File file = null;

		try {
			String path = File.separator+this.findPublishPath();
			log.info("获取项目部署地址为: " + path);
			file = new File(path+"mailAttachment"+File.separator+fileName+".xls");
			//文件不存在则创建;
			if(!file.exists()){
				file.createNewFile();
			}

			wb = new HSSFWorkbook();

			HSSFSheet sheet = wb.createSheet();
			ApplicationDataVo data = null;
			HSSFRow row = null;

			row = sheet.createRow(0);
			row.createCell(0).setCellValue("序号");
			row.createCell(1).setCellValue("日期");
			row.createCell(2).setCellValue("数量");
			row.createCell(3).setCellValue("产品套餐名称");
			row.createCell(4).setCellValue("品名");
			row.createCell(5).setCellValue("版本");

			row.createCell(6).setCellValue("申请人");
			row.createCell(7).setCellValue("项目编码");
			row.createCell(8).setCellValue("项目归属");
			row.createCell(9).setCellValue("项目负责人");
			row.createCell(10).setCellValue("总公司名称");

			row.createCell(11).setCellValue("支公司名称");
			row.createCell(12).setCellValue("收件人");
			row.createCell(13).setCellValue("电话");
			row.createCell(14).setCellValue("地址");
			row.createCell(15).setCellValue("库房");

			if(records != null && records.size() > 0) {
				for (int i = 0; i < records.size(); i++) {
					row = sheet.createRow(i+1);
					data = records.get(i);

					HSSFCell cell = row.createCell(0);
					cell.setCellValue(i+1);  //序号

					cell = row.createCell(1);
					cell.setCellValue(DateUtil.convertDateToString(data.getHopeDate()));  //日期

					cell = row.createCell(2);
					cell.setCellValue(data.getApplicationCount());  //数量
					cell = row.createCell(3);
					cell.setCellValue(data.getProductComboName());  //产品套餐名称
					cell = row.createCell(4);
					cell.setCellValue(data.getProductName());  //品名
					cell = row.createCell(5);
					cell.setCellValue(data.getVersion());  //版本
					cell = row.createCell(6);
					cell.setCellValue(data.getApplyForUserName());  //申请人
					cell = row.createCell(7);
					cell.setCellValue(data.getProjectCode());  //项目编码
					cell = row.createCell(8);
					cell.setCellValue(data.getProjectName());  //项目归属
					cell = row.createCell(9);
					cell.setCellValue(data.getProjectOwner());  //项目负责人
					cell = row.createCell(10);
					cell.setCellValue(data.getOwnerCompanyName());  //总公司名称
					cell = row.createCell(11);
					cell.setCellValue(data.getBannyCompanyName());  //支公司名称
					cell = row.createCell(12);
					cell.setCellValue(data.getReceiveName());  //收件人
					cell = row.createCell(13);
					cell.setCellValue(data.getReceiveTel());  //电话
					cell = row.createCell(14);
					cell.setCellValue(data.getAddress());  //地址
					cell = row.createCell(15);
					cell.setCellValue(data.getStoreName());  //库房

				}
			}

			wbFos = new FileOutputStream(file);
			wb.write(wbFos);
			wbFos.flush();
		} catch (Exception e) {
			throw new MyException("基因物品提交申请生成execl主体执行错误!", e);
		} finally {
			if(wb != null) {
				try {
					//wb.close();
				}catch (Exception e) {
					throw new MyException("基因物品提交申请生成execl关闭wb流错误!", e);
				}
			}

			if(wbFos != null) {
				try {
					wbFos.close();
				}catch (Exception e) {
					throw new MyException("基因物品提交申请生成execl关闭wbFos流错误!", e);
				}
			}

		}
		return file;

	}

	/**
	 * 获取项目实际部署路径
	 * @return String
	 * @author DengYouming
	 * @since 2016-7-18 下午2:58:29
	 */
	public String findPublishPath(){
		String path = this.getClass().getClassLoader().getResource("").getPath();  
		int end = path.length() - "WEB-INF/classes/".length();  
		path = path.substring(1, end);  
		return path;  
	}

	public boolean sendMail(User userInfo, ErpApplication application, 
			List<ErpApplicationDetail> details, List<File> attachmentList) {
		boolean flag = false;

		/*
		 * 此处单独处理日志.适用于便于查看邮件发送错误;
		 */
		Logger log = Logger.getLogger("Method sendMail");

		log.info("***************************邮件发送开始***************************");
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.exmail.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true); 
		//附件
		mailInfo.setAttaches(attachmentList);
		String[] attachFileNames = mailInfo.getAttachFileNames();
		StringBuilder filePaths = new StringBuilder("");
		if(attachFileNames!=null&&attachFileNames.length>0){
			for (int i = 0; i < mailInfo.getAttachFileNames().length; i++) {
				filePaths.append(mailInfo.getAttachFileNames()[i]);
			}
		}
		log.info("附件路径： "+filePaths.toString());
		mailInfo.setUserName(sendMailAccount);	
		mailInfo.setPassword(sendMailPassword);
		mailInfo.setFromAddress(sendMailAccount);
		log.info("发件人邮箱： " + sendMailAccount);

		String username = userInfo.getUserName();//发件人:
		String projectOwner = application.getProjectOwner();//项目负责人：             
		String projectName = application.getProjectName();//项目归属：
		String projectCode = application.getProjectCode();//项目编码
		String receiveTel = application.getReceiveTel();//联系电话
		String requirement = application.getRequirement();//需求说明
		String remark = application.getRemark(); //处理意见; 该字段为null没有值;
		StringBuffer buffer = new StringBuffer();// 邮件内容

		/**/
		//收件人
		String toEmail;
		String subject;//标题
		String mes;
		if(remark!=null&&remark.length()>0){//处理
			User user = userService.getUserByCreateName(username);
			toEmail=user.getEmail();
			subject="基因项目" + projectName + "处理邮件";// 邮件标题
			buffer.append("申请编码：" + application.getApplicationNo() + "\n");
			buffer.append("项目编码："+projectCode+"\n");
			buffer.append("项目名称："+projectName+"\n");
			buffer.append("联系电话："+receiveTel+"\n");
			buffer.append("需求说明："+requirement+"\n");
			buffer.append("处理意见："+remark+"\n");
			mes="已处理，请查收";
			buffer.append("发件人："+username+"\n");
			buffer.append("发件人邮箱："+toEmail+"\n");
		} else {//申请
			//获取业务员邮箱
			User user = userService.getUserByCreateName("远盟管理");
			toEmail=user.getEmail();
			subject="基因项目"+projectName+"申请邮件";// 邮件标题
			buffer.append("申请编码：" + application.getApplicationNo() + "\n");
			buffer.append("项目编码："+projectCode+"\n");
			buffer.append("项目负责人："+projectOwner+"\n");
			buffer.append("项目名称："+projectName+"\n");
			buffer.append("联系电话："+receiveTel+"\n");
			buffer.append("需求说明："+requirement+"\n");
			mes="已申请，请查收";
			buffer.append("发件人："+username+"\n");
			buffer.append("发件人邮箱："+toEmail+"\n");
		}
		buffer.append("链接网址：http://gene.healthlink.cn\n");
		mailInfo.setSubject(subject);// 邮件标题
		// 收件人邮箱
		// 收件人邮箱 固定为  xuejunyang@healthlink.cn
		toEmail = receiveMailAccount;

		mailInfo.setToAddress(toEmail);

		log.info("收件人邮箱： "+mailInfo.getToAddress());

		buffer.append(mes);
		mailInfo.setContent(buffer.toString());
		log.info("发件内容： "+mailInfo.getContent());

		// 发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		// 发送文体格式
		flag = sms.sendTextMail(mailInfo);
		// 发送html格式
		//flag = SimpleMailSender.sendHtmlMail(mailInfo);
		log.info("***************************邮件发送结束***************************");
		return flag;
	}



	public String findStatusById(String id) {
		return this.erpApplicationDao.findStatusById(id);
	}

	/**
	 * 通过数据Id进行逻辑删除;
	 * @param id
	 */
	public void deleteApplicationById(String id) throws Exception {
		//修改is_deleted状态为1;
		String sql = "update ERP_APPLICATION set IS_DELETED = '1' where id='"+id+"'";
		this.erpApplicationDao.getJdbcTemplate().update(sql);
	}

	public void updateStatus(String id, String backstatus) throws Exception {
		//修改is_deleted状态为1;
		String sql = "update ERP_APPLICATION set STATUS = '4', DEAL_USER_ID=null where id='"+id+"'";
		this.erpApplicationDao.getJdbcTemplate().update(sql);

	}

	public boolean findDealUserIdById(String ids) {
		String sql = "select deal_user_id dealUserId from erp_application where id='"+ids+"'";
		Map<String, Object> map = this.erpApplicationDao.getJdbcTemplate().queryForMap(sql);
		if(map != null) {
			String dealUserId = (String)map.get("dealUserId");
			if(StringUtils.isNotEmpty(dealUserId)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据id修改处理人id为null, status状态为0待发货
	 * @param id
	 */
	public void updateStatusAndDealUserId(String id) {
		String sql = "update erp_application set status='0', deal_user_id = null where id='"+id+"'";
		this.erpApplicationDao.getJdbcTemplate().update(sql);

	}

	public boolean findDealRepealCount(String id, String dealUserId) {
		String sql = "select count(1) from erp_application where id='"+id+"' and deal_user_id='"+dealUserId+"'";
		int count = this.erpApplicationDao.getJdbcTemplate().queryForInt(sql);
		if(count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据选中套餐id和已选择套餐id进行产品id判断;
	 * 当存在相等的时候, 返回false 标示不能继续执行;
	 * 可能该方法有些复杂,可能后面需要优化;
	 * @param ids
	 * @param areadyIds
	 */
	public boolean validProductEqual(String ids, String areadyIds) {
		//处理Id
		String[] idsArr = ids.split(",");
		String[] areadyIdsArr = areadyIds.split(",");

		StringBuilder inIds = new StringBuilder("");
		StringBuilder inAreadyIds = new StringBuilder("");
		//拼接要查询的sql;
		if(idsArr != null && idsArr.length > 0) {
			for(int i=0; i<idsArr.length; i++) {
				if(StringUtils.isNotEmpty(idsArr[i])) {
					if(i == 0) {
						inIds.append("'").append(idsArr[i]).append("'");
					} else {
						inIds.append(",'").append(idsArr[i]).append("'");
					}
				}
			}

		}

		if(areadyIdsArr != null && areadyIdsArr.length > 0) {
			for(int i=0; i<areadyIdsArr.length; i++) {
				if(StringUtils.isNotEmpty(areadyIdsArr[i])) {
					if(i == 0) {
						inAreadyIds.append("'").append(areadyIdsArr[i]).append("'");
					} else {
						inAreadyIds.append(",'").append(areadyIdsArr[i]).append("'");
					}

				}
			}

		}
		//查询出所有数据;
		List<ErpProComboProduct> idsList = null;
		if(StringUtils.isNotEmpty(inIds.toString())) {
			idsList = this.erpProductComboDao.findErpProComboProductByIds(inIds.toString());

		}

		List<ErpProComboProduct> areadyIdsList = null;
		if(StringUtils.isNotEmpty(inAreadyIds.toString())) {
			areadyIdsList = this.erpProductComboDao.findErpProComboProductByIds(inAreadyIds.toString());

		}

		//首先自己集合中不能存在相等的;
		if(idsList != null && idsList.size() > 0) {
			for(int i=0; i< idsList.size(); i++) {
				for(int j=i+1; j< idsList.size(); j++) {
					//如果相等,则跳出说有步骤,返回false;
					if(idsList.get(i).getProductId().equals(idsList.get(j).getProductId())) {
						return false;
					}
				}
			}
		}

		//其次与已存在的集合中也不能邮箱等的;
		if(areadyIdsList != null && areadyIdsList.size()> 0) {

			for(int i=0; i< areadyIdsList.size(); i++) {
				for(int j=0; j< idsList.size(); j++) {
					//如果相等,则跳出说有步骤,返回false;
					if(areadyIdsList.get(i).getProductId().equals(idsList.get(j).getProductId())) {
						return false;
					}
				}
			}

		}
		return true;
	}
	/**
	 * 阳光保险支公司保存操作;
	 * 1）此时主表中的状态为4（待销售处理）
	 * 2）CREATE_USER_ID字段取值规则为：通过当前登录系统的用户找到用户信息表中的【联系方式】字段（后续对于保险公司的账号，此字段将配置为对应的远盟的对接人登录系统的账号）。
	 * create by henry.xu 2016年12月14日
	 * @param application
	 * @param userInfo
	 * @param details
	 * @return
	 * @throws Exception
	 */

	public boolean saveYgbxApplication(ErpApplication application, User userInfo,
			List<ErpApplicationDetail> details) throws Exception {

		boolean flag = false;

		if(details == null || details.size() <= 0) {
			throw new MyException("没有添加产品明细,请添加后再点击保存!");
		}

		String currentTime = StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss");
		/*主体*/
		//CREATE_USER_ID字段取值规则为：通过当前登录系统的用户找到用户信息表中的【联系方式】字段（后续对于保险公司的账号，此字段将配置为对应的远盟的对接人登录系统的账号）。
		application.setCreateUserId(dealCreateUserId(userInfo.getId())); //为当前填写申请单的用户（create_user_id 对应的用户名）
		application.setCreateTime(new Date());
		application.setStatus("5"); //默认为待销售处理
		application.setIsDeleted(0); //默认转态为有效0
		application.setIsMark("1"); //1标示该保存为保险公司;
		application.setApplicationNo(currentTime); //设置申请号,规则为时间搓

		//新增的时候【寄送地址】的赋值规则：【省份】+【城市】+【寄送地址】
		String address = application.getProviceName() + "省" + application.getCityName() + "市" + application.getAddress();
		application.setAddress(address);

		/*
		 * 处理套餐对应的产品信息;
		 * 根据前台返回数据进行重新组装数据;
		 */
		List<ErpApplicationDetail> newDetails = new ArrayList<ErpApplicationDetail>(); //组装后数据存放以及使用;
		ErpApplicationDetail newDetail = null;
		for(int i=0; i<details.size(); i++) {
			if(details.get(i) == null) {
				continue;
			}
			//获取产品套餐id
			String proComboId = details.get(i).getProComboId();
			//查询对应的产品;
			StringBuilder jdbcsql = this.erpProductComboDao.dealErpProComboProductSql(proComboId);
			BeanPropertyRowMapper<ErpProduct> rowMapper = new BeanPropertyRowMapper<ErpProduct>(ErpProduct.class);

			List<ErpProduct> lists = this.erpProductComboDao.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);

			//获取产品套餐名称;
			ErpProductCombo combo = (ErpProductCombo) this.erpProductComboDao.findById(ErpProductCombo.class, proComboId);

			//重新组装;
			if(lists != null && lists.size() > 0) {
				for(int j = 0; j<lists.size(); j++) {
					newDetail = new ErpApplicationDetail();
					newDetail.setApplicationCount(details.get(i).getApplicationCount()); //每个产品对应的申请数量是相同的;
					newDetail.setProComboId(proComboId);
					newDetail.setProductId(lists.get(j).getId());
					newDetail.setProductName(lists.get(j).getProductName());
					newDetail.setProductTypeName(lists.get(j).getProductTypeName());
					newDetail.setStandard(lists.get(j).getStandard());
					newDetail.setProductComboName(combo.getProductComboName());
					newDetails.add(newDetail);
				}
			}

		}

		/*
		 * 邮件要发送的内容附件处理;
		 */
		List<ApplicationDataVo> dataVos = dealDataForExcelData(userInfo, application, newDetails);
		//创建excel
		String fileName = currentTime + (null != application.getProjectCode()? "_" + application.getProjectCode() : "");
		File attachment = createExcelFile(fileName, dataVos);

		//设置基因申请路径赋值;
		if(attachment != null) {
			application.setAttachmentPath(attachment.getAbsolutePath());			
		}

		erpApplicationDao.saveErpApplication(application);

		/*
		 * 明细;
		 */
		//获取保存后ID
		String sourceId = application.getId();
		int len = newDetails.size();
		if(newDetails != null && len > 0) {
			for(int i=0; i<len; i++) {
				ErpApplicationDetail detail = newDetails.get(i);
				//当该对象为空时, 跳过继续;
				if(detail == null) {
					continue;
				}

				detail.setApplicationId(sourceId);
				detail.setCreateTime(new Date());
				detail.setCreateUserId(userInfo.getId());
				detail.setStatus(null); //默认为未发货状态      状态：0已发货  1部分发货
				detail.setSendCount(0);//已发货为0
				detail.setIsDeleted(0);//默认转态为有效0

				erpApplicationDetailDao.saveErpApplicationDetail(detail);
			}
		}

		/*
		 * 发送邮件;
		 */
		//提交的时候，同时发送邮件。
		List<File> attachmentList = new ArrayList<File>();
		attachmentList.add(attachment);
		flag = sendYgbxMail(userInfo, application, attachmentList);
		return flag;
	}

	/**
	 * 根据登陆用户id查询出远盟对接人id并返回;
	 * create by henry.xu 2016年12月14日
	 * @param userId
	 * @return
	 * @throws MyException
	 */
	private String dealCreateUserId(String userId) throws MyException {
		User user = this.userDao.findUserById(userId);
		String userName = user.getYmSaleMan(); //获取当前登陆人对应的远盟对接人姓名;

		//再根据远盟对接人反查数据;
		User userYm = this.userDao.getUserByUserName(userName);

		if(null == userYm) {
			throw new MyException("远盟对接人没有对应数据,请更改后在保存!");
		}
		return userYm.getId();
	}

	/**
	 * 根据登陆用户id查询出远盟对接人id并返回;
	 * create by henry.xu 2016年12月14日
	 * @param userId
	 * @return
	 * @throws MyException
	 */
	private List<String> dealCreateUserEmail(String userId) throws MyException {
		User user = this.userDao.findUserById(userId);
		String emailStr = user.getEmail();
		if(StringUtils.isEmpty(emailStr)) {
			throw new MyException("没有填写邮件地址!");
		}
		String emailsStr[] = emailStr.split(",");
		List<String> listEmailStr = new ArrayList<String> ();
		if(null != emailsStr) {
			for(int i=0; i< emailsStr.length; i++) {
				listEmailStr.add(emailsStr[i]);
			}
		}

		return listEmailStr;
	}

	/**
	 * 阳光保险公司使用
	 * create by henry.xu 2016年12月14日
	 * @param userInfo
	 * @param application
	 * @param attachmentList
	 * @return
	 */
	private boolean sendYgbxMail(User userInfo, ErpApplication application, List<File> attachmentList) {
		boolean flag = false;

		/*
		 * 此处单独处理日志.适用于便于查看邮件发送错误;
		 */
		Logger log = Logger.getLogger("Method sendMail");

		log.info("***************************邮件发送开始***************************");
		try {
			List<String> mailsAddr =  dealCreateUserEmail(userInfo.getId());
			/*
			 * 1.创建邮件实体对象;并设置值;
			 */
			MailEntity mail = new MailEntity();
			mail.setAttachMents(attachmentList);
			mail.setHost("smtp.exmail.qq.com"); // 设置邮件服务器,qq
			mail.setSender(this.sendMailAccount);
			mail.setPassword(this.sendMailPassword);
			mail.setReceiver(mailsAddr); //多人邮件地址;
			mail.setUsername(this.sendMailAccount);

			//附件
			StringBuilder filePaths = new StringBuilder();
			if(attachmentList != null&& attachmentList.size()>0){
				int leg = attachmentList.size();
				for (int i = 0; i < leg; i++) {
					filePaths.append(attachmentList.get(i).getName()).append("||");
				}
			}
			log.info("附件路径： "+filePaths.toString());
			log.info("发件人邮箱： " + sendMailAccount);

			String receiveTel = application.getReceiveTel();//联系电话
			String requirement = null != application.getRequirement() ? application.getRequirement() : "";//需求说明
			//String remark = null != application.getRemark() ? application.getRemark() : ""; //处理意见; 该字段为null没有值;

			StringBuilder buffer = new StringBuilder();// 邮件内容

			//收件人
			String subject;//标题
			subject="基因项目处理邮件";// 邮件标题
			buffer.append("申请编码：" + application.getApplicationNo() + "\n");
			buffer.append("联系电话："+receiveTel+"\n");
			buffer.append("需求说明："+requirement+"\n");
			//buffer.append("处理意见："+remark+"\n");
			buffer.append("链接网址：http://gene.healthlink.cn\n");
			mail.setSubject(subject);// 邮件标题

			buffer.append("已处理，请查收");
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
	 * 阳光保险修改数据; 
	 * create by henry.xu 2016年12月15日
	 * @param application
	 * @param userInfo
	 * @param details
	 * @return
	 * @throws Exception
	 */
	public boolean updateYgbxApplication(ErpApplication application,
			User userInfo, List<ErpApplicationDetail> details) throws Exception {
		boolean flag = false;

		if(details == null || details.size() <= 0) {
			throw new MyException("没有添加产品明细,请添加后再点击保存!");
		}

		/*
		 * 主体;
		 */
		ErpApplication applicationOld = findObjectById(application.getId());
		applicationOld.setUpdateTime(new Date());
		applicationOld.setUpdateUserId(userInfo.getId());
		applicationOld.setAddress(application.getAddress());
		applicationOld.setBannyCompanyId(application.getBannyCompanyId());
		applicationOld.setHopeDate(application.getHopeDate());
		applicationOld.setLinkName(application.getLinkName());
		applicationOld.setLinkTel(application.getLinkTel());
		applicationOld.setProjectCode(application.getProjectCode());
		applicationOld.setProjectName(application.getProjectName());
		applicationOld.setProjectOwner(application.getProjectOwner());
		applicationOld.setReceiveName(application.getReceiveName());
		applicationOld.setReceiveTel(application.getReceiveTel());
		applicationOld.setRequirement(application.getRequirement());
		applicationOld.setStatus("5"); //修改原有状态为0,待发货;
		applicationOld.setIsMark("1");
		//邮件内容使用;
		application.setApplicationNo(applicationOld.getApplicationNo());

		/*
		 * 处理套餐对应的产品信息;
		 * 根据前台返回数据进行重新组装数据;
		 */
		List<ErpApplicationDetail> newDetails = new ArrayList<ErpApplicationDetail>(); //组装后数据存放以及使用;
		ErpApplicationDetail newDetail = null;
		for(int i=0; i<details.size(); i++) {
			if(details.get(i) == null) {
				continue;
			}
			//获取产品套餐id
			String proComboId = details.get(i).getProComboId();
			//查询对应的产品;
			StringBuilder jdbcsql = this.erpProductComboDao.dealErpProComboProductSql(proComboId);
			BeanPropertyRowMapper<ErpProduct> rowMapper = new BeanPropertyRowMapper<ErpProduct>(ErpProduct.class);

			List<ErpProduct> lists = this.erpProductComboDao.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);
			//获取产品套餐名称;
			ErpProductCombo combo = (ErpProductCombo) this.erpProductComboDao.findById(ErpProductCombo.class, proComboId);

			//重新组装;
			if(lists != null && lists.size() > 0) {
				for(int j = 0; j<lists.size(); j++) {
					newDetail = new ErpApplicationDetail();
					newDetail.setApplicationCount(details.get(i).getApplicationCount()); //每个产品对应的申请数量是相同的;
					newDetail.setProComboId(proComboId);
					newDetail.setProductId(lists.get(j).getId());
					newDetail.setProductName(lists.get(j).getProductName());
					newDetail.setProductTypeName(lists.get(j).getProductTypeName());
					newDetail.setStandard(lists.get(j).getStandard());
					newDetail.setProductComboName(combo.getProductComboName());
					newDetails.add(newDetail);
				}
			}

		}

		/*
		 * 此处由于有修改,所以重新邮件要发送的内容附件处理;
		 * 邮件要发送的内容附件处理;
		 */
		List<ApplicationDataVo> dataVos = dealDataForExcelData(userInfo, application, newDetails);
		//创建excel
		String currentTime = StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss");
		String fileName = currentTime + (null != application.getProjectCode()? "_" + application.getProjectCode() : "");
		File attachment = createExcelFile(fileName, dataVos);

		//设置基因申请路径赋值;
		if(attachment != null) {
			application.setAttachmentPath(attachment.getAbsolutePath());			
		}
		this.erpApplicationDao.updateErpApplication(applicationOld);

		/*
		 * 明细处理修改
		 */
		//先删除原有老数据;此处由于明细数据没有处理,所以物理删除;没有使用逻辑删除;
		this.erpApplicationDetailDao.deleteApplicationDetail(application.getId());
		//获取保存后ID
		String sourceId = application.getId();
		int len = newDetails.size();
		if(newDetails != null && len > 0) {
			for(int i=0; i<len; i++) {
				ErpApplicationDetail detail = newDetails.get(i);
				//当该对象为空时, 跳过继续;
				if(detail == null) {
					continue;
				}

				detail.setApplicationId(sourceId);
				detail.setCreateTime(new Date());
				detail.setCreateUserId(userInfo.getId());
				detail.setStatus(null); //默认为未发货状态                   状态：0已发货  1部分发货
				detail.setSendCount(0);//已发货为0
				detail.setIsDeleted(0);//默认转态为有效0

				erpApplicationDetailDao.saveErpApplicationDetail(detail);
			}
		}

		/*
		 * 发送邮件;
		 */
		//提交的时候，同时发送邮件。
		List<File> attachmentList = new ArrayList<File>();
		attachmentList.add(attachment);
		flag = sendYgbxMail(userInfo, application, attachmentList);
		return flag;
	}

	/**
	 * 原先创建场次和会议管理,取消同时只生成场次号作为保留依据;同时保存到erp_pre_customer表;
	 * modified by henry.xu 
	 * @param affix
	 * @param affixFileName
	 * @param applicationNo
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public boolean saveCustomerByExcel(File affix, String affixFileName, String applicationNo, User userInfo) throws Exception  {

		boolean result = false;
		/*
		 * 1.验证Excel表头规则;
		 * Excel表头为：客户唯一识别号、姓名、套餐;
		 */
		if (StringUtils.isEmpty(affixFileName)) {
			throw new MyException("文件上传失败!");
		}

		InputStream is = null;
		Workbook wb = null; 
		String eventsNo = "";
		File path = affix.getAbsoluteFile();

		is = new FileInputStream(path);

		wb = new XSSFWorkbook(is);

		Sheet sheet = wb.getSheetAt(0);
		if(null == sheet) {
			throw new MyException("Excel文件错误, 请下载导入文件模板在进行导入!");
		}
		Row row = sheet.getRow(0);
		String customerSkuNum = ExcelUtils.getValue(row.getCell(0));
		String customerName = ExcelUtils.getValue(row.getCell(1));
		String comboName = ExcelUtils.getValue(row.getCell(2));

		/*验证导入的excel表头是否正确;*/
		if("客户唯一识别号".equals(customerSkuNum) && "姓名".equals(customerName) && "套餐".equals(comboName)) {

			/*
			 * 3.支公司ID，通过当前登录用户找到用户信息中的支公司ID（um_user 表中的JOB_NUMBER）
			 */
			User user = (User) userDao.findById(User.class, userInfo.getId());
			String customerRelationShipId = user.getJobNumber(); //支公司id;



			//根据申请单号查询对应的申请数据;
			ErpApplication application = this.erpApplicationDao.findByApplicationNo(applicationNo);
			
			//根据支公司id以及项目编码查询对应的shipProid; user.getProjectCode()在用户信息设置;
			CustomerRelationShipPro shipPro = this.customerRelationshipProDao.findByCompanyIdAndProjectCode(application.getBannyCompanyId(), user.getProjectCode()) ;
			/*
			 * 4.创建场次号;
			 */
			String batchPre = shipPro.getBatchPre();
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String eventDate = sf.format(date);
			String eventsMaxNo = this.erpEventsDao.maxNo(eventDate);
			eventsNo = ResultWarehouseUtils.getEventsNo(eventsMaxNo, date, batchPre);
			log.info("创建场次号Events createNo : " + eventsNo);
			
			//重复遍历,避免导入的时候存在重复的数据;
			String customerSkuNum_bak = "";
			String customerName_bak = "";//姓名
			String comboName_bak = "";
			for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
				row = sheet.getRow(rowNum);
				if(null != row) {

					customerSkuNum = ExcelUtils.getValue(row.getCell(0));//客户唯一识别号
					customerName = ExcelUtils.getValue(row.getCell(1));//姓名
					comboName = ExcelUtils.getValue(row.getCell(2));//套餐
					
					if(StringUtils.isEmpty(customerSkuNum) || StringUtils.isEmpty(customerName) || StringUtils.isEmpty(comboName)) {
						throw new MyException("Excel文件中存在为空的数据, 请核对后再做提交!");
					}
					
					for (int n = rowNum+1; n <= sheet.getLastRowNum(); n++) {
						row = sheet.getRow(n);
						if(null != row) {
							customerSkuNum_bak = ExcelUtils.getValue(row.getCell(0));//客户唯一识别号
							customerName_bak = ExcelUtils.getValue(row.getCell(1));//姓名
							comboName_bak = ExcelUtils.getValue(row.getCell(2));//套餐
							//当三个条件相等标示有相同的数据.
							if(customerSkuNum.equals(customerSkuNum_bak) && customerName.equals(customerName_bak) && comboName.equals(comboName_bak)) {
								throw new MyException("Excel文件中存在重复的数据, 请核对后再做提交!");
							}
						}
					}
				}
			}
			
			// 循环行Row
			ErpBxCompanyPreSet customerPre = null;
			//ErpPreCustomer erpPreCustomer = null;
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				row = sheet.getRow(rowNum);
				if(null != row) {

					customerSkuNum = ExcelUtils.getValue(row.getCell(0));//客户唯一识别号
					customerName = ExcelUtils.getValue(row.getCell(1));//姓名
					comboName = ExcelUtils.getValue(row.getCell(2));//套餐

					//验证客户 唯一识别号,姓名, 套餐名称 在该申请单号中是否重复; 如果重复则跳过保存;
					boolean flag = this.erpBxCompanyPreSetDao.findCustomerSkuNumIsExits(applicationNo, customerSkuNum, comboName, customerName);
					if(flag) {
						continue;
					}
					/*
					 * 5.保存excel中数据;
					 */
					customerPre = new ErpBxCompanyPreSet();
					customerPre.setApplicationNo(applicationNo);
					customerPre.setComboName(comboName);
					customerPre.setComboShowName(null);
					customerPre.setCreateTime(new Date());
					customerPre.setCreateUserId(userInfo.getId());
					customerPre.setEventsNo(eventsNo);
					customerPre.setCustomerName(customerName);
					customerPre.setCustomerRelationshipId(customerRelationShipId);
					customerPre.setCustomerSkuNum(customerSkuNum);
					customerPre.setIsUseable("0");

					this.erpBxCompanyPreSetDao.saveoBxCustomer(customerPre);
					
				}

			}

			result = true;
		} else {
			result = false;
			throw new MyException("Excel文件表头不匹配, 请下载导入文件模板在进行导入!");
		}

		if(wb != null) {
			wb.close();
		}

		return result;
	}

	public String ajaxProductComboDetail(String proComboId) {

		String result = null;
		if(StringUtils.isEmpty(proComboId)) {
			return result;
		}

		StringBuilder jdbcsql = this.erpProductComboDao.dealErpProComboProductSql(proComboId);
		//查询对应的产品;
		BeanPropertyRowMapper<ErpProduct> rowMapper = new BeanPropertyRowMapper<ErpProduct>(ErpProduct.class);
		List<ErpProduct> lists = this.erpProductComboDao.getJdbcTemplate().query(jdbcsql.toString(), rowMapper);

		JSONArray jsonArray = JSONArray.fromObject(lists);
		result = jsonArray.toString();

		return result;
	}

}
