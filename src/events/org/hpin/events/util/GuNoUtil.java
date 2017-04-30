package org.hpin.events.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuNoUtil {
	public static String getNo(String num,Date date) throws ParseException{
		SimpleDateFormat sf=new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sf2=new SimpleDateFormat("HHmmss");
		String lastNumber=num.substring(num.length()-3);
		String newNumber=String.valueOf(Integer.parseInt(lastNumber)+1);
		if(newNumber.length()==1){
			newNumber="00"+newNumber;
		}
		if(newNumber.length()==2){
			newNumber="0"+newNumber;
		}
		String temp="HL"+sf.format(date)+sf2.format(new Date())+newNumber; 
		System.out.println(temp);
		return temp;
	}
	
		/**
	 * 会议号生成
	 * @description 
	 * @param num
	 * @param date
	 * @return
	 * @throws ParseException
	 *
	 * @return String
	 * @throws
	 *
	 */
	   public static String getNoTwo(String num,Date date) throws ParseException{
	        SimpleDateFormat sf=new SimpleDateFormat("yyMMdd");
	        SimpleDateFormat sf2=new SimpleDateFormat("HHss");
	        String lastNumber=num.substring(num.length()-3);
	        String newNumber=String.valueOf(Integer.parseInt(lastNumber)+1);
	        if(newNumber.length()==1){
	            newNumber="00"+newNumber;
	        }
	        if(newNumber.length()==2){
	            newNumber="0"+newNumber;
	        }
	        String temp="HY"+sf.format(date)+sf2.format(new Date())+newNumber; 
	        System.out.println(temp);
	        return temp;
	    }
}
