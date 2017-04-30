package org.hpin.statistics.briefness.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.OrmConverter;
import org.hpin.common.util.DateUtils;


/**
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-9-7 上午11:04:29</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
public class GenerateParamsUtils {
	
	private final static String PRE_TEMP_REPORT_TABLE = "TMP_" ;

	public static String generateParamsSql(Map<String , String> paramsMap , String viewName , List valueList) throws Exception{
		StringBuffer sqlBuffer = new StringBuffer() ;
		String sqlAll = ReportReflectionUtils.getValue(viewName) ;
		String _startSql = sqlAll.substring(0 , sqlAll.toLowerCase().indexOf("group by")) ;
		String _groupSql = sqlAll.substring(sqlAll.toLowerCase().indexOf("group by")) ;
		sqlBuffer.append(_startSql) ;
		OrmConverter.assemblyQuery(sqlBuffer , paramsMap , valueList) ;
		String sql = sqlBuffer.toString() ;
		sql = sql.replaceAll("#" , "_") ;
		sql = sql.substring(0 , sql.indexOf("order ")) ;
		sql = sql + _groupSql ;
		return sql ;
	}
	
	/**
	 * 转换参数值list为数组
	 * @param valueList	参数值数组
	 * @param flag	是否存储过程，如果为存储过程，则date类型需要传递字符串，否则传递sqlDate即可
	 * @return
	 */
	public static Object[] convertList2Array(List valueList , boolean flag){
		Object[] values = new Object[valueList.size()] ;
    	for(int i = 0 ; i < valueList.size() ; i ++){
    		if("Date".equals(valueList.get(i).getClass().getSimpleName())){
    			if(!flag){
    				values[i] = new java.sql.Date(((Date)valueList.get(i)).getTime()) ;
    			}else{
    				values[i] = DateUtils.DateToStr((Date)valueList.get(i) , "yyyy-MM-dd") ;
    			}
    		}else{
    			values[i] = valueList.get(i) ;
    		}
    	}
    	return values ;
	}
	
	public static String generateTempTableName(User user , String businessSeq){
		StringBuffer buffer = new StringBuffer(PRE_TEMP_REPORT_TABLE) ;
		buffer.append(user.getAccountName()).append("_") ;
		buffer.append(businessSeq);
		return buffer.toString() ;
	}
	
}

