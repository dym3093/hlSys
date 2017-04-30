package org.hpin.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

/**
 * 精确的计算BigDecimal数据类型;
 * @author Henry 20160826
 *
 */
public class PreciseCompute {
	  
	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 
	*/  
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		b1 = b1==null?new BigDecimal(0):b1;
		b2 = b2==null?new BigDecimal(0):b2;
	   return b1.add(b2);  
	}  
	  
	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/  
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {  
		b1 = b1==null?new BigDecimal(0):b1;
		b2 = b2==null?new BigDecimal(0):b2;
	   return b1.subtract(b2);  
	}  
	  
	/** 
	* 提供精确的乘法运算。 
	* @param v1 被乘数 
	* @param v2 乘数 
	* @return 两个参数的积 
	*/  
	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {  
		//执行乘法后取小数点保留2为;
		if(b1 == null || b2 == null) {
			return new BigDecimal(0.00);
		}
	    return div(b1.multiply(b2), new BigDecimal(1), 2);  
	}  
	  
	/** 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/  
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {  
	    if (scale < 0) {  
	    	throw new IllegalArgumentException("The scale must be a positive integer or zero");  
	    }
	    
	    if(b1 == null) {
	    	return new BigDecimal(0.00);
	    }
	   
	   return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);  
	}  
	  
	/** 
	* 提供精确的小数位四舍五入处理。 
	* @param v 需要四舍五入的数字 
	* @param scale 小数点后保留几位 
	* @return 四舍五入后的结果 
	*/  
	public static BigDecimal round(BigDecimal b, int scale) {  
	   if (scale < 0) {  
	    throw new IllegalArgumentException(  
	      "The scale must be a positive integer or zero");  
	   }  
	   BigDecimal ne = new BigDecimal("1");  
	   return b.divide(ne, scale, BigDecimal.ROUND_HALF_UP);  
	}  
	
	public static boolean isNumber(String value) {  
        return isInteger(value) || isDouble(value);  
    } 
	
	public static boolean isDouble(String value) {  
        try {  
            Double.parseDouble(value);  
            if (value.contains("."))  
                return true;  
            return false;  
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }  
	public static boolean isInteger(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }
	
	/**
	 * 将字符串转换为BigDecimal;四舍五入
	 * @param obj
	 * @return
	 */
	public static BigDecimal formatComma2BigDecimal(String obj, int scale) {  
        String val = String.valueOf(obj);  
        if (val == null)  
            return new BigDecimal("0.00");  
  
        val = val.replaceAll(",", "");  
        if (!isNumber(val))  
            return new BigDecimal("0.00");  
  
        BigDecimal decimal = new BigDecimal(val);  
        decimal = decimal.setScale(scale, RoundingMode.HALF_UP);  
  
        return decimal;  
  
    }
	
	/**
	 * 将字符串转换为BigDecimal;该方法不会四舍五入;
	 * @param obj
	 * @return
	 */
	public static BigDecimal formatComma2BigDecimalNo(String obj, int scale) {  
        String val = String.valueOf(obj);  
        if (val == null)  
            return new BigDecimal("0.00");  
  
        val = val.replaceAll(",", "");  
        if (!isNumber(val))  
            return new BigDecimal("0.00");  
        
        /*
         * 排除四舍五入处理,不够自动补0;
         */
        int num = obj.lastIndexOf(".");
        if(num > -1) {
        	
        	String toval = val.substring(num+1, val.length());
        	//当要处理的数据不够时自动补0
        	if(toval.length() <= scale) {
        		int len = scale - toval.length();
        		int count =0; 
        		while(count < len) {
        			val += "0";
        			count ++;
        		}
        		
        	} else {
        		val = val.substring(0, num + scale+1);
        	}
        	
        } else {
        	int len = scale;
    		int count =0; 
    		val += ".";
    		while(count < len) {
    			val += "0";
    			count ++;
    		}
        }
        
        BigDecimal decimal = new BigDecimal(val);  
  
        return decimal;  
  
    }
	
	public static BigDecimal string2BigDecimal(String obj) {  
        String val = String.valueOf(obj);  
        if (StringUtils.isEmpty(obj))  
            return new BigDecimal("0.00");  
  
        val = val.replaceAll(",", "");  
        if (!isNumber(val))  
            return new BigDecimal("0.00"); 
        
        return new BigDecimal(val);
	}
}
