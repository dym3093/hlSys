package org.hpin.statistics.briefness.util;

import java.util.List ;

import org.hpin.statistics.briefness.dao.QueryDAO ;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;
import org.springframework.transaction.annotation.Transactional ;


/**
 * <p>@desc : Report报表数据库工具类</p>
 * <p>@see : </p>
 *
 * <p>@author : 胡五音</p>
 * <p>@createDate : 2013-9-8 下午5:20:32</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p> 
 */
@Service(value = "org.hpin.statistics.briefness.util.ReportDbExecuteUtils")
@Transactional(rollbackFor = Exception.class)
public class ReportDbExecuteUtils {

	@Autowired()
	private QueryDAO queryDao = null ;
	
	public void executeProcedure(String procedureName , List paramsList){
		queryDao.getJdbcTemplate().execute(getExecuteStr(procedureName , paramsList)) ;
	}
	
	private String getExecuteStr(String procedureName , List paramsList){
		StringBuffer sb = new StringBuffer() ;
		sb.append("call ").append(procedureName).append("(") ;
		for(Object obj : paramsList){
			sb.append("'").append(obj == null ? "" : (String)obj).append("' ,") ;
		}
		String result = sb.toString().substring(0 , sb.toString().length() - 2) ;
		return result + ")" ;
	}
	
}

