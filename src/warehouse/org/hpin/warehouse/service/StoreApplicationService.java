package org.hpin.warehouse.service;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.base.usermanager.entity.User;
import org.hpin.base.usermanager.service.DeptService;
import org.hpin.base.usermanager.service.UserService;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.BeanUtils;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StaticMehtod;
import org.hpin.warehouse.dao.StoreApplicationDAO;
import org.hpin.warehouse.dao.StoreApplicationDetailDao;
import org.hpin.warehouse.entity.StoreApplication;
import org.hpin.warehouse.entity.StoreApplicationDetail;
import org.hpin.warehouse.mail.MailSenderInfo;
import org.hpin.warehouse.mail.SimpleMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value="org.hpin.warehouse.service.StoreApplicationService")
@Transactional
public class StoreApplicationService extends BaseService {
	@Autowired
	private DeptService deptService;
	@Autowired
	private CustomerRelationShipService shipService;
	@Autowired
	private UserService userService;	
	@Autowired
	private CustomerRelationShipService customerService;
	
	@Autowired
	private StoreApplicationDAO dao = null;

	@Autowired
	private StoreApplicationDetailDao detailDao;

	public void update(StoreApplication storeApplication){
		StoreApplication app = (StoreApplication)dao.findById(StoreApplication.class, storeApplication.getId());
		BeanUtils.copyProperties(app, storeApplication);
		dao.update(app);
	}
	
	public List getEmsList(String id){
		return dao.getEmsList(id);
	}

	/**
	 * 保存申请表及其明细信息
	 * @param obj StoreApplication
	 * @param detailList List
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-14 下午6:44:42
	 */
	public boolean saveStoreApplicationInfo(User currentUser, StoreApplication storeApplication, List<StoreApplicationDetail> details) throws Exception{
		boolean flag_save = false;
		boolean flag_sendMail = false;
		if(storeApplication!=null&&CollectionUtils.isNotEmpty(details)){
			
			storeApplication.setCreateDeptId(currentUser.getDeptId());
			storeApplication.setCreateUserId(currentUser.getAccountName());
			storeApplication.setCreateUserName(currentUser.getUserName());

			storeApplication.setCreateTime(new Date());	
			//批次号
			storeApplication.setBatNo(StaticMehtod.getCurrentDateTime("yyyyMMddHHmmss"));
			
			String idRelated = storeApplication.getId();
			
			List<StoreApplicationDetail> tempDetails = new ArrayList<StoreApplicationDetail>();
			if(StringUtils.isNotEmpty(idRelated)){
				for (StoreApplicationDetail entity : details){
					entity.setIdRelated(idRelated);
					entity.setStatus(0);
					tempDetails.add(entity);
				}
			}
			//处理要保存的数据
			List<Map<String, String>> list = this.dealData4Excel(currentUser, storeApplication, tempDetails);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentTime = sdf.format(Calendar.getInstance().getTime());
			//创建excel
			File attachment = this.createExcelFile(currentTime+"_"+storeApplication.getRemark3(), list);
			List<File> attachmentList = new ArrayList<File>();
			attachmentList.add(attachment);
			storeApplication.setAttachmentPath(attachment.getAbsolutePath());
			int num = 0;
			num = detailDao.saveList(details);
			
			/*
			 * 获取前台的省和城市,拼接到详细地址中;(暂时没有另外设置字段来处理该数据;)
			 * create by henry.xu	20160905
			 */
			String province = HttpTool.getParameter("provinceName", "");
			String city = HttpTool.getParameter("cityName", "");
			String addr = storeApplication.getAddress();
			StringBuilder stb = new StringBuilder(province);
			stb.append("省")
			.append(city)
			.append("市")
			.append(addr);
			
			storeApplication.setAddress(stb.toString());
			
			dao.save(storeApplication);
			if(num==details.size()){
				flag_save = true;
			}
			flag_sendMail = this.sendMail(currentUser, storeApplication, tempDetails, attachmentList);
		}
		return flag_save&&flag_sendMail;
	}
	
	/**
	 * 发送邮件
	 * @param currentUser
	 * @param storeApplication
	 * @param details
	 * @param attachmentList
	 * @return boolean
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-19 上午11:48:04
	 */
	private boolean sendMail(User currentUser, StoreApplication storeApplication, List<StoreApplicationDetail> details, List<File> attachmentList) throws Exception{
		boolean flag = false;
		
		/***************************发送邮件*******************************************/
		Logger log = Logger.getLogger("Method sendMail");
		
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
		
		String currentMail= "gene@healthlink.cn";
		String currentMailPwd= "Yue123.com";
		
		mailInfo.setUserName(currentMail);	
		mailInfo.setPassword(currentMailPwd);
		mailInfo.setFromAddress(currentMail);
		log.info("发件人邮箱： "+mailInfo.getFromAddress());
		
		//发件人:
		String username=currentUser.getUserName();
		//项目负责人：             
		String remark1=storeApplication.getRemark1();
		//项目归属：
		String remark2=storeApplication.getRemark2();
		//联系电话
		String receiveTel=storeApplication.getReceiveTel();
		//需求说明
		String requires=storeApplication.getRequires();
		//处理意见
		String remark=storeApplication.getRemark();
		//项目编码
		String remark3=storeApplication.getRemark3();
		// 邮件内容
		StringBuffer buffer = new StringBuffer();
		
		//收件人
		String toEmail;
		String subject;//标题
		String mes;
		if(remark!=null&&remark.length()>0){//处理
			User u=userService.getUserByCreateName(currentUser.getUserName());
			toEmail=u.getEmail();
			subject="基因项目"+remark2+"处理邮件";// 邮件标题
			buffer.append("申请编码："+storeApplication.getBatNo()+"\n");
			buffer.append("项目编码："+remark3+"\n");
			buffer.append("项目归属："+remark2+"\n");
			buffer.append("联系电话："+receiveTel+"\n");
			buffer.append("需求说明："+requires+"\n");
			buffer.append("处理意见："+remark+"\n");
			mes="已处理，请查收";
			buffer.append("发件人："+username+"\n");
			buffer.append("发件人邮箱："+toEmail+"\n");
		}else{//申请
			//获取业务员邮箱
			String createUserName="远盟管理";
			User u=userService.getUserByCreateName(createUserName);
			toEmail=u.getEmail();
			subject="基因项目"+remark2+"申请邮件";// 邮件标题
			buffer.append("申请编码："+storeApplication.getBatNo()+"\n");
			buffer.append("项目编码："+remark3+"\n");
			buffer.append("项目负责人："+remark1+"\n");
			buffer.append("项目归属："+remark2+"\n");
			buffer.append("联系电话："+receiveTel+"\n");
			buffer.append("需求说明："+requires+"\n");
			mes="已申请，请查收";
			buffer.append("发件人："+username+"\n");
			buffer.append("发件人邮箱："+toEmail+"\n");
		}
		buffer.append("链接网址：http://gene.healthlink.cn\n");
		mailInfo.setSubject(subject);// 邮件标题
		// 收件人邮箱
		// 收件人邮箱 固定为  xuejunyang@healthlink.cn
		toEmail = "xuejunyang@healthlink.cn";

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
		flag = SimpleMailSender.sendHtmlMail(mailInfo);
		//发送附件
		//SimpleMailSender.sendAttchement(mailInfo);
		return flag;
	}
	
	/**
	 * 转换成可以做成附件表的数据
	 * @param user 用户
	 * @param storeApplication 主表
	 * @param details 明细表
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-7-18 下午3:43:46
	 */
	private List<Map<String,String>> dealData4Excel(User user, StoreApplication storeApplication, List<StoreApplicationDetail> details) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date useDate = storeApplication.getUseDate()==null? Calendar.getInstance().getTime(): storeApplication.getUseDate(); 
		//日期
		String expectDate = sdf.format(useDate);
		List<Map<String,String>> records = new ArrayList<Map<String,String>>();
		Map<String,String> data = null;
		StoreApplicationDetail entity = null;
		for (int i = 0; i < details.size(); i++) {
			entity = details.get(i);
			data = new HashMap<String, String>();
			data.clear();
			//日期
			data.put(StoreApplication.F_USEDATE, expectDate);
			//数量
			data.put(StoreApplicationDetail.F_APPLYNUM, ""+entity.getApplyNum());
			//品名
			data.put(StoreApplicationDetail.F_PRDOUCENAME, entity.getPrdouceName());
			//版本
			data.put("版本", "通用");
			//申请人
			data.put("申请人", user.getUserName());
			//项目编码
			data.put(StoreApplication.F_REMARK3, storeApplication.getRemark3());
			//项目归属
			data.put(StoreApplication.F_REMARK2, storeApplication.getRemark2());
			//项目负责人
			data.put(StoreApplication.F_REMARK1, storeApplication.getRemark1());
			//总公司名称
			data.put(StoreApplication.F_OWNEDCOMPANY, storeApplication.getOwnedCompany());
			//支公司名称
			data.put(StoreApplication.F_BANNYCOMPANYNAME, storeApplication.getBannyCompanyName());
			//收件人
			data.put(StoreApplication.F_RECEIVENAME, storeApplication.getReceiveName());
			//电话
			data.put(StoreApplication.F_RECEIVETEL, storeApplication.getReceiveTel());
			//地址
			data.put(StoreApplication.F_ADDRESS, storeApplication.getAddress());
			//库房
			data.put("库房", "");
			
			//添加到记录
			records.add(data);
		}
		
		return records;
	}
	
	/**
	 * 根据传入的表头和内容生成对应的Excel文件
	 * @param fileName String
	 * @param heads List
	 * @param contents List
	 * @return File
	 * @author DengYouming
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @since 2016-7-18 上午11:44:29
	 */
	private File createExcelFile(String fileName, List<Map<String,String>> records) throws FileNotFoundException, IOException{
		File file = null;

		String tempPath = this.findPublishPath() +"templateExcel/库存申请模板.xls";
//		String tempPath = "D:/库存申请模板.xls";
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(tempPath)));
		file = new File(this.findPublishPath()+"mailAttachment/"+fileName+".xls");
//		file = new File("d:/mailAttachment/"+fileName+".xls");
		if(!file.exists()){
			file.createNewFile();
		}
		
		HSSFWorkbook wb = new HSSFWorkbook(bis);
		
		HSSFSheet sheet = wb.getSheetAt(0);
		Map<String,String> data = null;
		HSSFRow row = null;
		
		/*row = sheet.createRow(0);
		row.createCell(0).setCellValue("序号");
		row.createCell(1).setCellValue("日期");
		row.createCell(2).setCellValue("数量");
		row.createCell(3).setCellValue("品名");
		row.createCell(4).setCellValue("版本");
		
		row.createCell(5).setCellValue("申请人");
		row.createCell(6).setCellValue("项目编码");
		row.createCell(7).setCellValue("项目归属");
		row.createCell(8).setCellValue("项目负责人");
		row.createCell(9).setCellValue("总公司名称");
		
		row.createCell(10).setCellValue("支公司名称");
		row.createCell(11).setCellValue("收件人");
		row.createCell(12).setCellValue("电话");
		row.createCell(13).setCellValue("地址");
		row.createCell(14).setCellValue("库房");*/
		
		for (int i = 0; i < records.size(); i++) {
			row = sheet.createRow(i+1);
			data = records.get(i);
			for (int j = 0; j < data.keySet().size(); j++) {
				HSSFCell cell = row.createCell(j);
				switch(j){
					case 0: 
							cell.setCellValue(i+1);  //序号
						break;
					case 1: 
							cell.setCellValue(data.get(StoreApplication.F_USEDATE));
						break;
					case 2:
							cell.setCellValue(data.get(StoreApplicationDetail.F_APPLYNUM)); 
						break;
					case 3: 
							cell.setCellValue(data.get(StoreApplicationDetail.F_PRDOUCENAME)); 
						break;
					case 4:
							cell.setCellValue(data.get("版本"));
						break;
					case 5: 
							cell.setCellValue(data.get("申请人")); 
						break;
					case 6:
							cell.setCellValue(data.get(StoreApplication.F_REMARK3));
						break;
					case 7: 
							cell.setCellValue(data.get(StoreApplication.F_REMARK2));
						break;
					case 8:
							cell.setCellValue(data.get(StoreApplication.F_REMARK1));
						break;
					case 9: 
							cell.setCellValue(data.get(StoreApplication.F_OWNEDCOMPANY));
						break;
					case 10:
							cell.setCellValue(data.get(StoreApplication.F_BANNYCOMPANYNAME));
						break;
				
					case 11: 
							cell.setCellValue(data.get(StoreApplication.F_RECEIVENAME)); 
						break;
					case 12:
							cell.setCellValue(data.get(StoreApplication.F_RECEIVETEL));
						break;
					case 13: 
							cell.setCellValue(data.get(StoreApplication.F_ADDRESS)); 
						break;
					case 14:
							cell.setCellValue(data.get("库房"));
						break;
				}
			}
		}
		FileOutputStream wbFos = new FileOutputStream(file);
		wb.write(wbFos);
		wbFos.flush();
		bis.close();
		wbFos.close();
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

	/**
	 * @param parentDictid 
	 * @return 品类类别
	 */
	public List<SysDictType> getThingsType(String parentDictid) {
		String sql = "from SysDictType where parentDictid=?";
		return dao.getHibernateTemplate().find(sql, parentDictid);
	}
	
	/**
	 * @return 获取公司信息
	 */
	public List<CustomerRelationShip> getCompanyInfo() {
		String sql ="from CustomerRelationShip where is_deleted=0";
		return dao.getHibernateTemplate().find(sql);
	}
	
	/**
	 * 手动发送邮件
	 * @param mail
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-8-12 下午6:38:17
	 */
	public String sendMailByHand(String email, String fileName) throws Exception{
		boolean flag = false;
		Logger log = Logger.getLogger("sendMailByHand");
		
		String msg = null;
		File file = null;
		if(fileName.contains("xls")||fileName.contains("xlsx")){
			file = new File(this.findPublishPath()+"mailAttachment/"+fileName);
		}else{
			file = new File(this.findPublishPath()+"mailAttachment/"+fileName+".xls");
		}
		if(file!=null){
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(bis);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(1);
			
			//申请人
//			String applicant = row.getCell(5).getStringCellValue();
			String applicant = "admin";
			User uer = userService.getUserByCreateName(applicant);
			
			//项目编码
			String projNo = row.getCell(6).getStringCellValue();
			//项目归属
			String projOwned = row.getCell(7).getStringCellValue();
			//项目负责人
			String projPeople = row.getCell(8).getStringCellValue();
			
			//查找单据
			Map<String,Object> params = new HashMap<String, Object>();
			params.put(StoreApplication.F_REMARK1, projPeople);
			params.put(StoreApplication.F_REMARK2, projOwned);
			params.put(StoreApplication.F_REMARK3, projNo);
			List<StoreApplication> list = dao.listByProps(params);
			StoreApplication store = null;
			if(!list.isEmpty()){
				store = list.get(0);
			}
			//处理信息
			String mes = null;
			StringBuffer buffer = new StringBuffer();
			if(store!=null){
				buffer.append("申请编码："+store.getBatNo()+"\n");
				buffer.append("项目编码："+projNo+"\n");
				buffer.append("项目归属："+store.getRemark2()+"\n");
				buffer.append("联系电话："+store.getReceiveTel()+"\n");
				buffer.append("需求说明："+store.getRequires()+"\n");
				buffer.append("处理意见："+store.getRemark()+"\n");
				buffer.append("发件人："+uer.getUserName()+"\n");
				buffer.append("发件人邮箱："+uer.getEmail()+"\n");
			
				if(store.getRemark()!=null&&store.getRemark().length()>0){
					mes = "已处理，请查收";
				}else{
					mes = "已申请，请查收";
				}
				buffer.append(mes);
				
				//邮件类
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost("smtp.exmail.qq.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true); 
				
				//附件
				String[] attachFileNames = mailInfo.getAttachFileNames();
	
				StringBuilder filePaths = new StringBuilder("");
				if(attachFileNames!=null&&attachFileNames.length>0){
					for (int i = 0; i < mailInfo.getAttachFileNames().length; i++) {
						filePaths.append(mailInfo.getAttachFileNames()[i]);
					}
				}
				
				log.info("附件路径： "+filePaths.toString());
				
//				String currentMail= "gene@healthlink.cn";
//				String currentMailPwd= "Yue123.com";
				
				String currentMail= "youmingdeng@healthlink.cn";
				String currentMailPwd= "d8552Y8402m**";
				
				mailInfo.setUserName(currentMail);	
				mailInfo.setPassword(currentMailPwd);
				mailInfo.setFromAddress(currentMail);
				
				log.info("发件人邮箱： "+mailInfo.getFromAddress());
				
				// 邮件标题
				String subject="基因项目"+store.getRemark2()+"处理邮件";
				buffer.append("链接网址：http://gene.healthlink.cn\n");
				mailInfo.setSubject(subject);// 邮件标题
				// 收件人邮箱
				// 收件人邮箱 固定为  xuejunyang@healthlink.cn
				
				String toEmail = null;
				if(email!=null&&email.length()>0){
					toEmail = email;
				}else{
					toEmail = "xuejunyang@healthlink.cn";
				}
	
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
				flag = SimpleMailSender.sendHtmlMail(mailInfo);
				//发送附件
				if(flag){
					msg = "发送成功!";
				}else{
					msg = "发送失败！请稍后再试!";
				}
			}else{
				msg = "网络异常！请稍后再试!";
				throw new NullPointerException("StoreApplication空对象!");
			}
		}else{
			msg = "【"+fileName+"】文件不存在！";
		}
		return msg;
	}
	
}
