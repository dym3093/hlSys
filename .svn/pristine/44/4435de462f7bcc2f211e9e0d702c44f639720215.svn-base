package org.hpin.warehouse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ResultWarehouseUtils {
	/**
	 * 场次号生成
	 * <p>Description: </p>
	 * @author herny.xu
	 * @date 2017年2月20日
	 */
	public static String getEventsNo(String num, Date date, String batchPre) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("HHmmss");
		String lastNumber = num.substring(num.length() - 3);
		String newNumber = String.valueOf(Integer.parseInt(lastNumber) + 1);
		if (newNumber.length() == 1) {
			newNumber = "00" + newNumber;
		}
		if (newNumber.length() == 2) {
			newNumber = "0" + newNumber;
		}
		
		//modified by henry.xu 20170324 默认为HL
		if(StringUtils.isEmpty(batchPre)) {
			batchPre = "HL";
		}
		
		String temp = batchPre + sf.format(date) + sf2.format(new Date()) + newNumber;
		return temp;
	}

}
