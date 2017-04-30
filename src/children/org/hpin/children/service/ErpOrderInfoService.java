package org.hpin.children.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hpin.children.dao.ErpOrderInfoDao;
import org.hpin.children.entity.ErpCustomerMailInfo;
import org.hpin.children.entity.ErpOrderInfo;
import org.hpin.children.entity.ErpOrderMailNo;
import org.hpin.children.entity.ExportOrderData;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.web.ErpEventsAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.logging.resources.logging;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Service(value = "org.hpin.children.service.ErpOrderInfoService")
@Transactional()
public class ErpOrderInfoService extends BaseService{
	
	private String rootPath;
	private Logger logger = Logger.getLogger(ErpOrderInfoService.class);
	
	@Autowired
	private ErpOrderInfoDao eoiDao;
	
	/**
	 * 点击主页面的订单号然后通过订单获取个人订单
	 * @param orderNo
	 * @param page
	 */
	public List findByPage(Page page,Map searchMap) {
		return eoiDao.findByPage(page, searchMap);
	}

	/**
	 * 获取儿童套餐下拉框的值
	 */
	public List<Map<String, Object>> getSelectedInput() {
		return eoiDao.getSelectedInput();
	}

	public int updateStatus(List<ErpOrderMailNo> jsonList) {
		int count1=0;
		int count2=0;
//		StringBuilder sql1=new StringBuilder();
		StringBuilder sql2=new StringBuilder();
		String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		List<ErpCustomerMailInfo> mailList = new ArrayList<ErpCustomerMailInfo>();
		for(ErpOrderMailNo en:jsonList){
			ErpCustomerMailInfo eMailInfo = new ErpCustomerMailInfo();
			eMailInfo.setId(eoiDao.getMailId(en.getId(),en.getExpressType()));		//返回id号
			eMailInfo.setOrderNo(en.getOrderNo());
			eMailInfo.setExpressType(en.getExpressType());
			eMailInfo.setCreateUser(en.getCreateUser());
			eMailInfo.setCreateTime(new java.util.Date());
			eMailInfo.setUpdateUser(en.getUpdateUser());
			eMailInfo.setMailNo(en.getMailNo());
			eMailInfo.setMailCompany(en.getMailCompany());
			eMailInfo.setOrderId(en.getId());
			eMailInfo.setIsDelete("0");
			mailList.add(eMailInfo);
		}
		for(ErpCustomerMailInfo entity : mailList){
			if (eoiDao.getMailInfoSize(entity.getOrderId(),entity.getExpressType())>=1) {
				try {
					eoiDao.update(entity);
					sql2.append("update erp_order_info set status='"+jsonList.get(0).getStatus()+"',"
							+ "sampledate=to_date('"+date+"','yyyy-mm-dd hh24:mi:ss') where id='"+entity.getOrderId()+"'");
					count1=eoiDao.updateOrderInfo(sql2);
					sql2.delete(0,sql2.length());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					eoiDao.save(entity);
					sql2.append("update erp_order_info set status='"+jsonList.get(0).getStatus()+"',"
							+ "sampledate=to_date('"+date+"','yyyy-mm-dd hh24:mi:ss') where id='"+entity.getOrderId()+"'");
					count2=eoiDao.updateOrderInfo(sql2);
					sql2.delete(0,sql2.length());
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		
		return count1+count2;
	}

	public List<Map<String, Object>> getAllComboInfo() {
		return eoiDao.getAllComboInfo();
	}

	/**
	 * @return 条形码，快递编号，快递号
	 */
	public List<Map<String, Object>> getCodeList() {
		return eoiDao.getCodeList();
	}

	/**
	 * @param data(id数组)
	 * @return
	 */
	public int deleteOrderInfo(String data) {
		int count= 0;
		try {
			for(String id: data.split(",")){
				String sql ="update erp_order_info set isdelete ='0' where id='"+id+"'";
				eoiDao.deleteOrderInfo(sql);
				count ++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 更新订单信息
	 * @param map
	 * @param accountName
	 * @return
	 */
	public int updateOrderInfo(Map<String, Object> map1, Map<String, Object> map2,String accountName) {
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		try {
			StringBuilder sql1 = new StringBuilder();
			StringBuilder sql2 = new StringBuilder();
			StringBuilder sql3 = new StringBuilder();
			sql1.append("update erp_order_info set ");
			sql2.append("update erp_customer_mail_info set ");
			sql3.append("update erp_customer_mail_info set ");
			for(String key :map1.keySet()){
				if (key.equals("id")) {
					continue;
				}
				if (!key.equals("age")) {
					sql1.append(key+"='"+map1.get(key)+"', ");
				}else{
					sql1.append(key+"='"+new BigDecimal(map1.get(key).toString())+"', ");
				}
			}//to_date('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
			for(String key:map2.keySet()){
				if (key.equals("id")) {
					continue;
				}
				if (key.equals("sampleCode")) {
					sql2.append("mailno='"+map2.get(key)+"', updateUser='"+accountName+"',updateTime=to_date('"+date+
							"','yyyy-mm-dd hh24:mi:ss') where orderid='"+map2.get("id")+"' and express_type='1'");
					count2 = eoiDao.updateOrderInfo(sql2);
				}else {
					sql3.append("mailno='"+map2.get(key)+"', updateUser='"+accountName+"',updateTime=to_date('"+date+
							"','yyyy-mm-dd hh24:mi:ss') where orderid='"+map2.get("id")+"' and express_type='3'");
					count3 = eoiDao.updateOrderInfo(sql3);
				}
			}
			sql1.append(" updateUser='"+accountName+"',updateTime=to_date('"+date+"','yyyy-mm-dd hh24:mi:ss') where id='"+map1.get("id")+"'");
			count1 = eoiDao.updateOrderInfo(sql1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count1+count2+count3;
	}

	/**
	 * @param data
	 * @return 通过id获取该订单信息
	 */
	public List<Map<String, Object>> getOrderInfoById1(String data) {
		String sql = "select id,orderno,createdate,name,sex,age,setmealname,guardianname,guardianphone,relationship,reportaddress,note,idNo,weight,height,familyhistory,code from erp_order_info where id='"+data+"'";
		return eoiDao.getOrderInfoById(sql);
	}
	public List<Map<String, Object>> getOrderInfoById2(String id) {
		String sql = "select mailno,express_type from erp_customer_mail_info where orderid='"+id+"' AND express_type IN ('1','3')order by express_type";
		return eoiDao.getOrderInfoById(sql);
	}

	/**
	 * @param page
	 * @param data 查询条件
	 * @return 客户采样盒信息
	 * @throws ParseException 
	 */
	public List<ErpOrderMailNo> getCustomerSampleBox(Page page, String data) throws ParseException {
		List<ErpOrderMailNo> customerSampleList =new ArrayList<ErpOrderMailNo>();
		List<Map<String, Object>> list1 =new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list2 =new ArrayList<Map<String,Object>>();
		StringBuilder sql1 =new StringBuilder();
		StringBuilder sql2 =new StringBuilder();
		for (String id : data.split(",")) {
			ErpOrderMailNo em = new ErpOrderMailNo();
			sql1.append("select id,orderNo,createdate,name,setmealname,guardianname,guardianphone,status,reportaddress,note from erp_order_info where id='"+id+"'");
			sql2.append("select mailno from erp_customer_mail_info where orderid='"+id+"'");
			list1=eoiDao.getCustomerSampleBox(sql1.toString());
			list2=eoiDao.getCustomerSampleBox(sql2.toString());
			for (Map<String, Object> map:list1) {
				em.setId(map.get("id").toString());
				em.setOrderNo(map.get("orderNo").toString());
				em.setCreateDate(java.sql.Date.valueOf(map.get("createdate").toString().substring(0, 10)));
				em.setName(map.get("name").toString());
				em.setSetMealName(map.get("setmealname").toString());
				em.setGuardianName(map.get("guardianname").toString());
				em.setGuardianPhone(map.get("guardianphone").toString());
				em.setStatus(map.get("status").toString());
				em.setReportAddress(map.get("reportaddress").toString());
				em.setNote((String)map.get("note"));
			}
			for (Map<String, Object> map:list2) {
				em.setMailNo((String)map.get("mailno"));
			}
			sql1.delete(0, sql1.length());
			sql2.delete(0, sql2.length());
			customerSampleList.add(em);
		}
		page.setTotalCount((long)customerSampleList.size());
		return customerSampleList;
	}

	/**
	 * @param id
	 * @return 要修改的信息
	 */
	public List<Map<String, Object>> getCustomerOrderInfo(String id) {
		String sql = "select id,orderNo,createdate,name,sex,age,setmealname,guardianname,guardianphone,relationship,status,reportaddress,note,code from erp_order_info where id='"+id+"'";
		return eoiDao.getCustomerOrderInfo(sql);
	}
	
	/**
	 * @param id 
	 * @param code 条形码
	 * @return
	 */
	public int updateCustomerCode(String id, String code) {
		String sql = "update erp_order_info set code ='"+code+"' where id ='"+id+"'";
		return eoiDao.updateCustomerCode(sql);
	}
	
	public String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	/**
	 * @param page
	 * @param paramsMap
	 * @return 用于导出数据
	 */
	public List<ExportOrderData> getCustomerMailInfo(Page page,Map paramsMap) {
		String ids = paramsMap.get("filter_and_ids_LIKES_S").toString();
		List<ExportOrderData> customerSampleList =new ArrayList<ExportOrderData>();
		List<Map<String, Object>> list1 =new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list2 =new ArrayList<Map<String,Object>>();
		StringBuilder sql1 =new StringBuilder();
		StringBuilder sql2 =new StringBuilder();
		for (String id : ids.split(",")) {
			ExportOrderData em = new ExportOrderData();
			sql1.append("select id,orderNo,createdate,name,setmealname,guardianphone,status,reportaddress,note from erp_order_info where id='"+id+"'");
			sql2.append("select mailno from erp_customer_mail_info where orderid='"+id+"'");
			list1=eoiDao.getCustomerSampleBox(sql1.toString());
			list2=eoiDao.getCustomerSampleBox(sql2.toString());
			for (Map<String, Object> map:list1) {
				em.setId(map.get("ID").toString());
				em.setOrderNo(map.get("ORDERNO").toString());
				em.setOrderDate(map.get("CREATEDATE").toString().substring(0, 10));
				em.setName(map.get("NAME").toString());
				em.setSetMealName(map.get("SETMEALNAME").toString());
				em.setGuardianPhone(map.get("GUARDIANPHONE").toString());
				em.setStatus(map.get("STATUS").toString());
				em.setReportAddress(map.get("REPORTADDRESS").toString());
				em.setNote((String)map.get("NOTE"));
			}
			for (Map<String, Object> map:list2) {
				em.setMailNo((String)map.get("MAILNO"));
			}
			sql1.delete(0, sql1.length());
			sql2.delete(0, sql2.length());
			customerSampleList.add(em);
		}
		page.setTotalCount((long)customerSampleList.size());
		return customerSampleList;
	}

	/**
	 * @param id
	 * @return 需要打印的信息
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public String getPrintOrderInfo(String id,String name,HttpServletRequest request) throws Exception{
			StringBuffer url = request.getRequestURL();
			String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).toString();
			String fileName = name+"的儿童基因报告.pdf";
			String outFilePath = rootPath+fileName;
			PdfStamper stamp = null;
			FileOutputStream out = null;
			PdfReader reader = new PdfReader(request.getRealPath("")+"\\pdfmode\\childrenModel\\01\\001.pdf");	//C:\\Users\\Carly\\Downloads\\
			File file = new File(rootPath);
			file.mkdirs();
			ErpOrderInfo entity = (ErpOrderInfo)eoiDao.findById(ErpOrderInfo.class, id.trim());
		
			out = new FileOutputStream(outFilePath);
			stamp = new PdfStamper(reader, out);
			AcroFields form = stamp.getAcroFields();
			form.setField("orderNo",entity.getOrderNo());
			form.setField("name",entity.getName());
			if (entity.getSex().equals("男")) {
				form.setField("sex1","√");
			}else {
				form.setField("sex2","√");
			}
			form.setField("height",entity.getHeight());
			form.setField("weight",entity.getWeight());
			form.setField("age",String.valueOf(entity.getAge()));
			logger.info(entity.getPhone().length());
			for(int i=0;i<entity.getPhone().length();i++){
				form.setField("iphone"+i,String.valueOf(entity.getPhone().charAt(i)));
			}
			for(int i=0;i<entity.getIdNo().length();i++){
				form.setField("idNo"+i,String.valueOf(entity.getIdNo().charAt(i)));
			}
			form.setField("guardianName",entity.getGuardianName());
			form.setField("guardianPhone",entity.getGuardianPhone());
			form.setField("relationship",entity.getRelationship());
			form.setField("setMealName",entity.getSetMealName());
			form.setField("sampleAddress",entity.getSampleAddress());
			form.setField("reportAddress",entity.getReportAddress());
			form.setField("note",entity.getNote()==null?"":entity.getNote());
			stamp.setFormFlattening(true);
			String sql ="update erp_order_info set reportStatus='"+fileName+"' where id='"+id.trim()+"'";
			eoiDao.updateReportStatus(sql);
			stamp.close();
			reader.close();
			out.flush();
			out.close();
		return contextUrl+outFilePath.substring(2,outFilePath.length());
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
}
