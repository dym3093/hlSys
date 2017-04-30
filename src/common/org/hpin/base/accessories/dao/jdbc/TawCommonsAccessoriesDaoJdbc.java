package org.hpin.base.accessories.dao.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hpin.base.accessories.entity.TawCommonsApplication;
import org.hpin.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * <p>@desc : 附件管理Dao，JDBC的访问方式</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 29, 2012 6:12:42 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */

@Repository
public class TawCommonsAccessoriesDaoJdbc extends BaseDao {
  public static  final String TBL_SYSTEM_APPLICATION = " Taw_Application";	
  
  /**
   * 获取系统所有的附件应用模块
   * @return
   */
  public  List getTawSystemApplications() {
	 ArrayList _objReturnList = new ArrayList();
	 String _strSQL = "select APP_ID,APP_NAME from "
	             + TBL_SYSTEM_APPLICATION;

	 List _objList = getJdbcTemplate().queryForList(_strSQL);
	 Iterator _objIterator = _objList.iterator();
	 while (_objIterator.hasNext()) {
	    	 TawCommonsApplication _objApp = new TawCommonsApplication();
	         Map _objMap = (Map) _objIterator.next();
	         String _strAppId = _objMap.get("APP_ID").toString().trim();
	         int _iAppId = Integer.parseInt(_strAppId);
	         _objApp.setAppId(_iAppId);
	         _objApp.setAppName(_objMap.get("APP_NAME").toString().trim());

	         _objReturnList.add(_objApp);
	     }

	     return _objReturnList;
	 }
	 /**
	  * 根据应用模块ID号查询模块名称
	  * @param applicationId 应用模块ID
	  * @return
	  * @author sherry
	  */
	 public String getApplicatioNameById(int applicationId){

	     String applicationName="";
	     String _strSQL = "select APP_NAME from "
	             + TBL_SYSTEM_APPLICATION+" where app_id="+applicationId;

	     List _objList = getJdbcTemplate().queryForList(_strSQL);
	     Iterator _objIterator = _objList.iterator();
	     while (_objIterator.hasNext()) {
	        Map _objMap = (Map) _objIterator.next();
	        applicationName=_objMap.get("APP_NAME").toString().trim();
	     }
	     return applicationName;
	 }

}
