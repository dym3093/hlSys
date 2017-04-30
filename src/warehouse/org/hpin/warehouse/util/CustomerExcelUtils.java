package org.hpin.warehouse.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hpin.common.exception.MyException;
import org.hpin.common.util.ExcelUtils;
import org.hpin.warehouse.entity.ErpPreCustomer;

/**
 * 处理对应批量导入的excel处理数据工具类
 * 此类避免了所有业务在service层;
 * @description: 
 * create by henry.xu 2016年12月29日
 */
public class CustomerExcelUtils {
	
	public static List<ErpPreCustomer> dealExcelSheetData(Sheet sheet) throws Exception {
		List<ErpPreCustomer> precs = new ArrayList<ErpPreCustomer>();
		
		// 循环行Row
		ErpPreCustomer preCustomer = null;
		Row row = null;
		
		for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(null != row) {
				//1> 获取行中列对应的值;
				
				String numId = ExcelUtils.getValue(row.getCell(0));
				if(StringUtils.isEmpty(numId)) {
					continue;
				}
				String numPolicy = ExcelUtils.getValue(row.getCell(1)); //保单号
			    String applicantName = ExcelUtils.getValue(row.getCell(2)); //被保人姓名;
			    String applicantSex = ExcelUtils.getValue(row.getCell(3)); //被保人性别;
			    String applicantAge = ExcelUtils.getValue(row.getCell(4)); //被保人年龄
			    String applicantIdcard = ExcelUtils.getValue(row.getCell(5)); //被保人省份中;
			    String applicantPhone = ExcelUtils.getValue(row.getCell(6)); //被保人联系电话;
			    String wereName = ExcelUtils.getValue(row.getCell(7)); //被检测人姓名
			    String reportReceiveName = wereName; //报告接收人姓名 =被检测人姓名
			    String wereSex = ExcelUtils.getValue(row.getCell(8)); //被检测人性别
			    String wereAge = ExcelUtils.getValue(row.getCell(9)); //被检测人年龄;
			    
			    String wereIdcard = ExcelUtils.getValue(row.getCell(10)); //被检测人身份证
			    String werePhone = ExcelUtils.getValue(row.getCell(11)); //被检测人电话 ;
			    String checkCobmo = ExcelUtils.getValue(row.getCell(12)); //检测项目;
			    String ymCombo = ExcelUtils.getValue(row.getCell(13)); //远盟标准检测套餐名称;
			    
			    Cell cell = row.getCell(14);
			    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			    String dateStr = ExcelUtils.getValue(cell);
			    
			    
			    Date orderCreateDate = null;
			    if(StringUtils.isNotEmpty(dateStr)) {
			    	Calendar c = new GregorianCalendar(1900,0,-1);  
					c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+Integer.parseInt(dateStr));
			    	orderCreateDate = c.getTime();//订单生成日期;
			    }
			    
			    String checkboxEmilAddr = ExcelUtils.getValue(row.getCell(15)); //检测盒邮寄地址
			    String reportSendAddr = ExcelUtils.getValue(row.getCell(16)); //报告邮寄地址;
			    String emsNumber = ExcelUtils.getValue(row.getCell(17)); //快递单号;
			    String remark = ExcelUtils.getValue(row.getCell(18)); //备注;
				String address = reportSendAddr;//地址
				String phone = werePhone;//电话  
				
				/*
				 * 验证被检测人基本信息不能为空;如果空抛出异常;
				 */
				if(StringUtils.isEmpty(wereName) || StringUtils.isEmpty(wereIdcard) 
						|| StringUtils.isEmpty(wereAge) || StringUtils.isEmpty(checkCobmo) 
						|| StringUtils.isEmpty(werePhone) || StringUtils.isEmpty(wereSex)) {
					throw new MyException("请检查第"+rowNum+"行的被检测人数据,并确保正确!");
				} 
				
				wereName = wereName.trim();
				wereIdcard = wereIdcard.trim();
				werePhone = werePhone.trim();
				
				int lenAge = wereAge.indexOf(".");
				if(lenAge > 0) {
					wereAge = wereAge.substring(0, lenAge);
				}
				 
				/*
				 * 判断每条数据不能与之前数据重复,如果重复并提示;
				 */
				if(precs.size()>0) {
					for (int j=0; j<precs.size(); j++) {
						//当同一个数据导入中,被检测人的姓名和身份证相等说明是重复数据;需要返回并让其修改;
						if(wereName.equals(precs.get(j).getWereName()) && wereIdcard.equals(precs.get(j).getWereIdcard())) {
							throw new MyException("请检查第"+(j+4)+"行和第"+(rowNum+1)+"行存在重复被检测人信息!");
						}
					}
				}
				
			    /*
				 * 5.保存excel中数据;
				 */
				preCustomer = new ErpPreCustomer();
				preCustomer.setYmCombo(ymCombo);
				preCustomer.setAddress(address);
				preCustomer.setApplicantAge(applicantAge);
				preCustomer.setApplicantIdcard(applicantIdcard);
				preCustomer.setApplicantName(applicantName);
				preCustomer.setApplicantPhone(applicantPhone);
				preCustomer.setApplicantSex(applicantSex);
				preCustomer.setCheckboxEmilAddr(checkboxEmilAddr);
				preCustomer.setCheckCobmo(checkCobmo);
				preCustomer.setNumPolicy(numPolicy);
				preCustomer.setOrderCreateDate(orderCreateDate);
				preCustomer.setPhone(phone);
				preCustomer.setRemark(remark);
				preCustomer.setReportReceiveName(reportReceiveName);
				preCustomer.setReportSendAddr(reportSendAddr);
				preCustomer.setWereAge(wereAge);
				preCustomer.setWereIdcard(wereIdcard);
				preCustomer.setWereName(wereName);
				preCustomer.setWerePhone(werePhone);
				preCustomer.setWereSex(wereSex);
				preCustomer.setEmsNumber(emsNumber);
				
				precs.add(preCustomer);
			}
		}
		
		return precs ;
	}
	
}
