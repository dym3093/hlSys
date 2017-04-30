package org.hpin.statistics.briefness.util;

import java.lang.reflect.Field ;


/**
 * <p>@desc : 报表导出，反射工具类</p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-9-7 下午9:52:23</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
public class ReportReflectionUtils {

	public static String getValue(String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Field field = SqlConstant.class.getField(name) ;
		return field.get(null).toString() ;
	}
	
}

