package org.hpin.events.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.hpin.base.customerrelationship.entity.CustomerRelationShip;
import org.hpin.base.customerrelationship.entity.ErpRelationShipCombo;
import org.hpin.base.region.service.RegionService;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.dao.ErpQRCodeDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.events.entity.ErpQRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.events.service.ErpQRCodeService")
@Transactional()
public class ErpQRCodeService extends BaseService {
	private Logger log = Logger.getLogger(ErpQRCodeService.class);
	@Autowired
	private ErpQRCodeDao dao;
	
	@Autowired
	ErpCustomerDao customerDao;
	
	@Autowired
	RegionService regionService;
	
	@Value(value="${erpQRcode.file.download.local.path}")
	private String localPath;
	@Value(value="${erpQRcode.file.download.context}")
	private String downloadContext;
	/**
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * @param localPath the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * @return the downloadContext
	 */
	public String getDownloadContext() {
		return downloadContext;
	}

	/**
	 * @param downloadContext the downloadContext to set
	 */
	public void setDownloadContext(String downloadContext) {
		this.downloadContext = downloadContext;
	}

	/**
	 * 分页获取用户
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPageQRCOde(Page page, Map searchMap){
		return dao.findByPageQRCOde(page, searchMap);
	}
	
	/**
	 * 保存场次时，保存ErpQRCode
	 * @param erpEvents
	 * @throws Exception
	 * @author tangxing
	 * @date 2016-8-18下午3:17:05
	 */
	public void saveErpQRCode(ErpEvents erpEvents) {
		ErpEvents erpEvents2 = new ErpEvents();
		List<ErpEvents> erpEvents2s = dao.getEventsByNo(erpEvents.getEventsNo());	//查找ID
		
		if(erpEvents2s!=null&&erpEvents2s.size()>0){
			erpEvents2 = erpEvents2s.get(0);
		}
		
		ErpQRCode erpQRCode = new ErpQRCode();
		erpQRCode.setEventsNo(erpEvents.getEventsNo());
		erpQRCode.setEventsDate(erpEvents.getEventDate());
		
		erpQRCode.setProvinceId(erpEvents.getProvice());
		String provinceName = regionService.id2Name(erpEvents.getProvice(),null);	//省份转换
		erpQRCode.setProvinceName(provinceName);
		
		erpQRCode.setCityId(erpEvents.getCity());
		String cityName = regionService.id2Name(erpEvents.getCity(),null);			//城市转换
		erpQRCode.setCityName(cityName);
		
		erpQRCode.setEventsId(erpEvents2.getId());
		erpQRCode.setBatchNo(erpEvents.getBatchNo());
		erpQRCode.setBanchCompanyId(erpEvents.getBranchCompanyId());
		erpQRCode.setBanchCompanyName(erpEvents.getBranchCompany());
		erpQRCode.setOwnedCompanyId(erpEvents.getOwnedCompanyId());
		erpQRCode.setOwnedCompanyName(erpEvents.getOwnedCompany());
		erpQRCode.setCombo(erpEvents.getComboName());
		erpQRCode.setLevel(erpEvents.getLevel2());
		erpQRCode.setCreateUserName(erpEvents.getCreateUserName());
		erpQRCode.setCreateTime(new Date());
		erpQRCode.setIsDelete("0");
		erpQRCode.setQRCodeStatus("0");
		if(erpEvents.getHeadcount()!=null){
			erpQRCode.setExpectNum(String.valueOf(erpEvents.getHeadcount()));
		}else{
			erpQRCode.setExpectNum("0");
		};
		dao.saveErpQRCode(erpQRCode);
	};
	
	/**
     * 根据条形码查询客户
     */
	public List<ErpCustomer>  getCustomerByCode(String code)throws Exception{
		return customerDao.getCustomerByCode(code);
	}
	
	/**
	 * 修改或逻辑删除ErpQRCode
	 * @param entity
	 * @author tangxing
	 * @date 2016-8-18下午4:59:24
	 */
	public void updateOrdelQRCodeStatus(ErpQRCode entity)throws Exception{
		customerDao.update(entity);
	}
	
	/**
	 * 根据ID获取ErpQRCode对象
	 * @param QRCodeId
	 * @return
	 * @author tangxing
	 * @date 2016-8-18下午5:04:21
	 */
	public ErpQRCode getErpQRCodeById(String QRCodeId)throws Exception{
		return dao.getErpQRCodeById(QRCodeId);
	}
	
    /**
     * 有效时间
     * @return
     * @author tangxing
     * @date 2016-5-16下午3:28:30
     */
    public String expectTime(Date eventsTime,int hour) throws Exception{
    	String exTime = "";
    	long currentTime = 0L;
    	Date date = null;
    	if(eventsTime!=null){
    		currentTime =eventsTime.getTime() + hour * 60 * 60 * 1000;
    		date = new Date(currentTime); 
    	}
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
         try{ 
             exTime = sdf.format(date);
             System.out.println("有效时间：" + exTime); 
         } 
         catch (Exception e){ 
        	 log.error("expectTime",e);
         } 
    	return exTime;
    }
    
    /**
     * 查看二维码图片
     * @return
     * @author tangxing
     * @date 2016-8-18下午5:36:41
     */
    public String showQRCodeImg(String QRCodeId) throws Exception{
    	ErpQRCode erpQRCode = dao.getErpQRCodeById(QRCodeId);
		String path = erpQRCode.getQRCodePath();
		return path;
    }
    
    /**
	 * 根据支公司ID获取支公司对应项目，项目下的所有套餐ID
	 * @param bcId
	 * @return
	 * @author tangxing
	 * @date 2016-11-17下午3:17:53
	 */
    public List<String> getComboIdByProjectId(String projectId){
    	List<Map<String, Object>> listMap = dao.getComboIdByProjectId(projectId);
    	List<String> lists = new ArrayList<String>();
    	if(listMap!=null){
    		log.info("getComboIdByBranchCompanyId all comboId info -- "+listMap.toString());
    		for (Map<String, Object> map : listMap) {
				String str = (String) map.get("comboId");
				lists.add(str);
			}
    	}
    	return lists;
    }
	
	 
    /**
     * 根据comboId查comboname
     * @param name
     * @return
     * @author tangxing
     * @date 2016-11-17下午3:52:06
     */
    public String getComboNameById(String comboId){
    	log.info("getComboNameById--"+comboId);
    	Map<String, Object> mapInfo = dao.getComboNameById(comboId);
    	String name="";
    	if(mapInfo!=null&&mapInfo.size()>0){
    		log.info("getComboIdByName map toString--"+mapInfo.toString());
    		name = (String) mapInfo.get("comboName");
    	}
    	return name;
    }
    
    /**
     * 根据支公司ID查找对应的套餐
     * @param branchCompanyId
     * @return
     * @author tangxing
     * @date 2016-8-19下午4:54:15
     */
    public String[] getComboByBranchCompanyId(String branchCompanyId){
    	List<CustomerRelationShip> crsList = dao.getComboByBranchCompanyId(branchCompanyId);
    	log.info("crsList size--"+crsList.size());
    	String combo = "";
    	String[] comboStr = null;
    	if(crsList!=null){
    		combo = crsList.get(0).getCombo();
    	}
    	
    	if(StringUtils.isNotEmpty(combo)){ //修复支公司只有一个套餐情形下的bug  modify by YoumingDeng 2016-09-22 
			if(combo.indexOf(",")!=-1){	//包含 ，
				comboStr = combo.replaceAll(" ", "").split(",");
			}else{ //只有一个套餐
				comboStr = new String[1];
				comboStr[0] = combo;
			}
    	}
    	return comboStr;
    }
    
    /**
     * 根据comboname查comboId
     * @param name
     * @return
     * @author tangxing
     * @date 2016-8-19下午5:16:13
     */
    public String getComboIdByName(String name){
    	log.info("getComboIdByName--"+name);
    	Map<String, Object> mapInfo = dao.getComboIdByName(name);
    	String id="";
    	if(mapInfo.size()>0){
    		log.info("getComboIdByName map toString--"+mapInfo.toString());
    		id = (String) mapInfo.get("id");
    	}
    	return id;
    }
    
    public String getViewPath(HttpServletRequest request,String fileAddr){
    	StringBuffer url = request.getRequestURL();
		//含域名上下文的URL
		StringBuffer contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length());
		log.info("contextUrl--"+contextUrl);
    	//log.info("path处理前"+fileAddr);
    	String subAddr = fileAddr.substring(fileAddr.indexOf(File.separator));
    	//log.info("path处理前"+subAddr);
		System.out.println("subAddr: "+subAddr);
		//预览地址
		String viewPath = contextUrl+"/gene/"+subAddr.substring(1, subAddr.length());
		//log.info("二维码图片映射的预览地址： "+viewPath);
		
		return viewPath;
    }
    
    
    public List<ErpQRCode> getErpQRCodeByKeyword(String keyword){
    	List<ErpQRCode> list = dao.getErpQRCodeByKeyword(keyword);
    	return list;
    }
    
    /* ************导出Excel************ */
    /**
	 * @param request
	 * @param erpQRCode
	 * @param erpCustomers
	 * @return
	 * @author machuan
     * @param erpEvents 
	 * @date  2016年10月14日
	 */
	public String createQRCodeExcelPath(HttpServletRequest request, ErpQRCode erpQRCode,
			List<ErpCustomer> erpCustomers, ErpEvents erpEvents) {
		StringBuffer url = request.getRequestURL();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timePath = sdf.format(new Date());
		
    	
		String contextUrl = url.delete(url.length()-request.getRequestURI().length(), url.length()).append(downloadContext).toString();
		String curFilePath = localPath+timePath+"_"+erpQRCode.getId()+File.separator;//存放位置
		String str = timePath+"_"+erpQRCode.getId()+File.separator;
    	
		
		//构建Excel文件
		List<List<String>> rowList = buildExcelRowByPdf(erpCustomers);
		String excleName = erpQRCode.getEventsNo()+erpQRCode.getCombo()+".xls";			//文件名
		createExSettleXls(curFilePath.toString(),excleName,rowList,erpQRCode,erpEvents);
		
		String downloadurl = contextUrl+str+ excleName;		//下载路径
		return downloadurl;
	}
	
   
  	/**
  	 *  生成Excel每行的内容（二维码管理--销售）
  	 * @param erpCustomers
  	 * @return
  	 * @author machuan
  	 * @date  2016年10月14日
  	 */
    //序号、姓名、性别、套餐、条码、电话、年龄、采样日期、营销员信息
  	public List<List<String>> buildExcelRowByPdf(List<ErpCustomer> erpCustomers){
  		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
  		List<List<String>> result = new ArrayList<List<String>>();
  		for(int i=0;i<erpCustomers.size();i++){
  			ErpCustomer content = erpCustomers.get(i);
  			List<String> list = new ArrayList<String>();
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getName());//姓名
  			list.add(content.getSex());//性别
  			list.add(content.getSetmealName());//套餐
  			list.add(content.getCode());//条码
  			list.add(content.getPhone());//电话
  			list.add(content.getAge());//年龄
  			list.add(df.format(content.getSamplingDate()));//采样日期
  			list.add(content.getSalesMan());//营销员信息
  			result.add(list);
  		}
  		return result;
  	}
  	
  	/**
  	 * 生成二维码管理（销售）EXCEL
  	 * @param path
  	 * @param filename
  	 * @param rowList
  	 * @author tangxing
  	 * @param erpEvents 
  	 * @date 2016-6-14下午6:21:23
  	 */
  	public void createExSettleXls(String path ,String filename, List<List<String>> rowList,ErpQRCode erpQRCode, ErpEvents erpEvents) {
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat df1 = new SimpleDateFormat("hh");
		try {
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("test");
			hssfSheet1.setColumnWidth(0, 20*256);
			hssfSheet1.setColumnWidth(1, 20*256);
			hssfSheet1.setColumnWidth(2, 20*256);
			hssfSheet1.setColumnWidth(3, 20*256);
			hssfSheet1.setColumnWidth(4, 20*256);
			hssfSheet1.setColumnWidth(5, 20*256);
			hssfSheet1.setColumnWidth(6, 20*256);
			hssfSheet1.setColumnWidth(7, 20*256);
			hssfSheet1.setColumnWidth(8, 20*256);
			
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 260);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setWrapText(true);
			cellStyle.setFont(font);
			
			//构建Excel
			HSSFRow row0 = hssfSheet1.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("日期：");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			if(erpQRCode.getEventsDate()!=null){
				cell1.setCellValue(df.format(erpQRCode.getEventsDate()));
			}
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("时间：");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			if(erpQRCode.getEventsDate()!=null){
				int hour = Integer.parseInt(df1.format(erpQRCode.getEventsDate()));
				cell4.setCellValue(hour>18?"晚场":(hour>12?"下午场":"上午场"));
			}
			//2016-11-07 新插入的第二行 
			HSSFRow rownew1 = hssfSheet1.createRow(1);
			Cell cellnew10 = rownew1.createCell(0);
			cellnew10.setCellStyle(cellStyle);
			cellnew10.setCellValue("场次号：");
			Cell cellnew11 = rownew1.createCell(1);
			cellnew11.setCellStyle(cellStyle);
			cellnew11.setCellValue(erpEvents.getEventsNo()==null?"":erpEvents.getEventsNo());
			
			//第三行
			HSSFRow row2 = hssfSheet1.createRow(2);
			Cell cell20 = row2.createCell(0);
			cell20.setCellStyle(cellStyle);
			cell20.setCellValue("活动地点：");
			Cell cell21 = row2.createCell(1);
			cell21.setCellStyle(cellStyle);
			cell21.setCellValue(erpEvents.getAddress()==null?"":erpEvents.getAddress());
			//第四行
			HSSFRow row3 = hssfSheet1.createRow(3);
			Cell cell30 = row3.createCell(0);
			cell30.setCellStyle(cellStyle);
			cell30.setCellValue("举办单位：");
			Cell cell31 = row3.createCell(1);
			cell31.setCellStyle(cellStyle);
			cell31.setCellValue(erpEvents.getOwnedCompany()+erpEvents.getBranchCompany()+"");
			Cell cell32 = row3.createCell(2);
			cell32.setCellStyle(cellStyle);
			cell32.setCellValue("总公司：");
			Cell cell33 = row3.createCell(3);
			cell33.setCellStyle(cellStyle);
			cell33.setCellValue(erpEvents.getOwnedCompany()==null?"":erpEvents.getOwnedCompany());
			//第五行
			HSSFRow row4 = hssfSheet1.createRow(4);
			Cell cell40 = row4.createCell(0);
			cell40.setCellStyle(cellStyle);
			cell40.setCellValue("检测人数：");
			Cell cell41 = row4.createCell(1);
			cell41.setCellStyle(cellStyle);
			cell41.setCellValue(rowList.size());
			Cell cell42 = row4.createCell(2);
			cell42.setCellStyle(cellStyle);
			Cell cell43 = row4.createCell(3);
			cell43.setCellStyle(cellStyle);
			cell43.setCellValue("护士人数：");
			//第六行
			HSSFRow row5 = hssfSheet1.createRow(5);
			Cell cell50 = row5.createCell(0);
			cell50.setCellStyle(cellStyle);
			cell50.setCellValue("清单核对时间：");
			//第七行
			HSSFRow row6 = hssfSheet1.createRow(6);
			Cell cell60 = row6.createCell(0);
			cell60.setCellStyle(cellStyle);
			//第八行
			HSSFRow row7 = hssfSheet1.createRow(7);
			Cell cell70 = row7.createCell(0);
			cell70.setCellStyle(cellStyle);
			cell70.setCellValue("检测项目：");	
			//第九行
			HSSFRow row8 = hssfSheet1.createRow(8);
			Cell cell80 = row8.createCell(0);
			cell80.setCellStyle(cellStyle);
			cell80.setCellValue("标本人数：");	
			//第10行
			HSSFRow row9 = hssfSheet1.createRow(9);
			Cell cell90 = row9.createCell(0);
			cell90.setCellStyle(cellStyle);
			cell90.setCellValue("标本数量：");	
			//第11行
			HSSFRow row10 = hssfSheet1.createRow(10);
			Cell cell100 = row10.createCell(0);
			cell100.setCellStyle(cellStyle);
			Cell cell101 = row10.createCell(1);
			cell101.setCellStyle(cellStyle);
			cell101.setCellValue("红：");
			Cell cell103 = row10.createCell(3);
			cell103.setCellStyle(cellStyle);
			cell103.setCellValue("绿：");
			Cell cell105 = row10.createCell(5);
			cell105.setCellStyle(cellStyle);
			cell105.setCellValue("紫：");
			//第13行
			HSSFRow row12 = hssfSheet1.createRow(12);
			Cell cell120 = row12.createCell(0);
			cell120.setCellStyle(cellStyle);
			cell120.setCellValue("序号");
			Cell cell121 = row12.createCell(1);
			cell121.setCellStyle(cellStyle);
			cell121.setCellValue("姓名");
			Cell cell122 = row12.createCell(2);
			cell122.setCellStyle(cellStyle);
			cell122.setCellValue("性别");
			Cell cell123 = row12.createCell(3);
			cell123.setCellStyle(cellStyle);
			cell123.setCellValue("套餐");
			Cell cell124 = row12.createCell(4);
			cell124.setCellStyle(cellStyle);
			cell124.setCellValue("条码");
			Cell cell125 = row12.createCell(5);
			cell125.setCellStyle(cellStyle);
			cell125.setCellValue("电话");
			Cell cell126 = row12.createCell(6);
			cell126.setCellStyle(cellStyle);
			cell126.setCellValue("年龄");
			Cell cell127 = row12.createCell(7);
			cell127.setCellStyle(cellStyle);
			cell127.setCellValue("采样日期");
			Cell cell128 = row12.createCell(8);
			cell128.setCellStyle(cellStyle);
			cell128.setCellValue("营销员信息");
			int rowLength = rowList.size();
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row13 = hssfSheet1.createRow(rowNum+13);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row13, colI, str, cellStyle);
				}
			}
			//末尾--第一行签字
			HSSFRow rowQZ1 = hssfSheet1.createRow(13+rowLength);
			Cell cellQZ10 = rowQZ1.createCell(0);
			cellQZ10.setCellStyle(cellStyle);
			cellQZ10.setCellValue("远盟签字：");
			
			Cell cellQZ13 = rowQZ1.createCell(3);
			cellQZ13.setCellStyle(cellStyle);
			cellQZ13.setCellValue("保险公司签字：");
			//末尾--第二行签字
			HSSFRow rowQZ2 = hssfSheet1.createRow(14+rowLength);
			Cell cellQZ20 = rowQZ2.createCell(0);
			cellQZ20.setCellStyle(cellStyle);
			cellQZ20.setCellValue("护士签字：");
			
			Cell cellQZ23 = rowQZ2.createCell(3);
			cellQZ23.setCellStyle(cellStyle);
			cellQZ23.setCellValue("物流签字：");
			
			
			
			OutputStream os=new FileOutputStream(new File(path,filename));
			hssfWorkbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
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
		cell.setCellValue(value==null?"":value);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 通过查询条件,查找对应的数据; 
	 * create by henry.xu 2016年12月1日
	 * @param page
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void findWuChangEventQRCode(Page page, HashMap<String, String> params) {
		
		if(null == params || params.isEmpty()) {
			params = new HashMap<String, String>();
		}
		
		params.put("projectType", "PCT");
		
		//参数
		List<Object> lists = new ArrayList<Object>();
		//sql
		StringBuilder jdbcSql = this.dao.dealQRCodeSqlByParams(params);
		
		//排序; 根据时间降序;
		jdbcSql.append(" order by qrc.create_time desc ");
		
		//count
		long count = this.dao.findJdbcCount(jdbcSql, lists);
		page.setTotalCount(count);
		
		//list
		lists.add(page.getPageNumEndCount());
		lists.add(page.getPageNumStartCount());
		BeanPropertyRowMapper<ErpQRCode> rowMapper = new BeanPropertyRowMapper<ErpQRCode>(ErpQRCode.class);
		List<?> results = this.dao.findJdbcList(jdbcSql, lists, rowMapper);
		page.setResults(results);
		
		
	}


	/**
	 * 根据项目ID和套餐ID查找ErpRelationShipCombo
	 * @param pro_id 项目ID
	 * @param comboId 套餐ID
	 * @return List
	 * @throws Exception
	 * @author Damian
	 * @since 2016-12-26 14:44
	 */
	public List<ErpRelationShipCombo> listRelationShipCombo(String pro_id, String comboId) throws Exception{
		List<ErpRelationShipCombo> list = null;
		if(StringUtils.isNotEmpty(pro_id)&&StringUtils.isNotEmpty(comboId)){
			list = dao.listRelationShipCombo(pro_id,comboId);
		}
		return list;
    }

}



























