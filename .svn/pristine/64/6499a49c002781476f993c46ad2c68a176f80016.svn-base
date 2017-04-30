package org.hpin.warehouse.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.widget.pagination.Page;
import org.hpin.warehouse.dao.ErpSupplierDao;
import org.hpin.warehouse.entity.ErpSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 供货商service
 * @author tangxing
 * @date 2017-2-7上午10:54:48
 */

@Service("org.hpin.warehouse.service.ErpSupplierService")
@Transactional
public class ErpSupplierService extends BaseService{

	@Autowired
	private ErpSupplierDao dao;
	
	private static final Logger log = Logger.getLogger(ErpSupplierService.class);
	
	public List findByPage(Page page, Map searchMap) {
		return dao.findByPage(page, searchMap);
	}
	
	/**
	 * 根据ID获取ErpSupplier
	 * @param supplierId
	 * @return
	 * @author tangxing
	 * @date 2017-2-7下午2:18:13
	 */
	public ErpSupplier getSupplierById(String supplierId){
		return dao.getSupplierById(supplierId);
	}
	
	/**
	 * 根据条件导出excel
	 * @param hql
	 * @return
	 * @author tangxing
	 * @date 2017年2月7日17:37:22
	 */
	public List<ErpSupplier> exportExcelList(String hql){
		return dao.exportExcelList(hql);
	}
	
	/**
	 * 格式化excel数据
	 * @param preCustomer
	 * @return
	 * @author tangxing
	 * @date 2017-1-2下午8:27:06
	 */
	@SuppressWarnings("unchecked")
  	public List<List<String>> buildExcelRow(List<ErpSupplier> erpSupplier){
  		List<List<String>> result = new ArrayList<List<String>>();
  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<erpSupplier.size();i++){
  			ErpSupplier content = erpSupplier.get(i);
  			List<String> list = new ArrayList<String>();
//  			ErpCustomer customer = cusService.findCusById(content.getCustomerid());//20160519增加更多的会员信息
  			list.add(String.valueOf(i+1));//序号
  			list.add(content.getSupplierName());//供货商名称
  			list.add(StringUtils.isNotEmpty(content.getProvice())?this.getRegionById(content.getProvice()):"");	//省份
  			list.add(StringUtils.isNotEmpty(content.getCity())?this.getRegionById(content.getCity()):"");	//城市
  			list.add(content.getServiceArea());//服务范围
  			list.add(content.getDescription());//描述
  			list.add(content.getLinkMan());//联系人
  			list.add(content.getLinkPhone());//联系电话
  			
  			result.add(list);
  		}
  		return result;
  	}
	
	/**
	 * 生成excel文件
	 * @param filename
	 * @param rowList
	 * @param response
	 * @author tangxing
	 * @date 2017-2-6下午1:27:45
	 */
	public void createSupplierXls(String filename, List<List<String>> rowList,HttpServletResponse response) {
		try {
			/*File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}*/
			// 整个excel
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			// 工作表1
			HSSFSheet hssfSheet1=hssfWorkbook.createSheet("sheet1");
			
			HSSFRow row0 = hssfSheet1.createRow(0);
			
			Font font = createFonts(hssfWorkbook, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
			CellStyle cellStyle = hssfWorkbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
			cellStyle.setFont(font);
			/* ***sheet1*** */
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			Cell cell1 = row0.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("供货商名称");
			Cell cell2 = row0.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("省份");
			Cell cell3 = row0.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("城市");
			Cell cell4 = row0.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("服务范围");
			Cell cell5 = row0.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("描述");
			Cell cell6 = row0.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("联系人");
			Cell cell7 = row0.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("联系电话");
			for(int rowNum=0;rowNum<rowList.size();rowNum++){
				HSSFRow row1 = hssfSheet1.createRow(rowNum+1);
				List<String> row=rowList.get(rowNum);
				for (int colI=0;colI<row.size();colI++) {
					String str=row.get(colI);
					createCell(hssfWorkbook, row1, colI, str, cellStyle);
				}
				
			}
			response.reset();
			response.setContentType("application/msexcel;");                
			response.setHeader("Content-Type","application/vnd.ms-excel");
            response.setHeader("Content-Disposition", new String(("attachment;filename="+filename).getBytes("GB2312"), "UTF-8"));
			OutputStream os = response.getOutputStream();
			hssfWorkbook.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			log.error("BuidReportExcel writeXls", e);
		} catch (IOException e) {
			log.error("BuidReportExcel writeXls", e);
		}
	}
	
	/**
	 * 根据regionId获取省份城市
	 * @param regionId
	 * @return
	 * @author tangxing
	 * @date 2017-2-6下午1:31:12
	 */
	public String getRegionById(String regionId){
		List<Map<String, Object>> maps = dao.getRegionById(regionId);
		String regionName = "";
		if(maps!=null&&maps.size()>0){
			regionName = (String) maps.get(0).get("regionName");	//获取省份城市
		}
		return regionName;
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
	
}
