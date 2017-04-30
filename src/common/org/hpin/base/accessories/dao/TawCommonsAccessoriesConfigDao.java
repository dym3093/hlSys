/*
 * Created on 2007-9-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.hpin.base.accessories.dao;

import java.util.List;

import org.hpin.base.accessories.entity.TawCommonsAccessoriesConfig;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.common.log.util.HpinLog;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

/**
 * @author IBM_USER
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
@Repository()
public class TawCommonsAccessoriesConfigDao extends BaseDao
                   {

	/**
	 * 获取全部附件配置信息
	 */
	public List getTawCommonsAccessoriesConfigs() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from TawCommonsAccessoriesConfig");
	}

	/* (non-Javadoc)根据id获取附件配置信息
	 * @see org.hpin.commons.accessories.dao.TawCommonsAccessoriesConfigDao#getTawCommonsAccessoriesConfig(java.lang.Integer)
	 */
	public TawCommonsAccessoriesConfig getTawCommonsAccessoriesConfig(String id) {
		// TODO Auto-generated method stub
	  TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig=null;
	  tawCommonsAccessoriesConfig = 
	  	 (TawCommonsAccessoriesConfig) getHibernateTemplate().get(TawCommonsAccessoriesConfig.class, id);
	  if (tawCommonsAccessoriesConfig == null) {
        HpinLog.warn(this,"uh oh, tawCommonsAccessoriesConfig with id '" + id + "' not found...");
        throw new ObjectRetrievalFailureException(TawCommonsAccessoriesConfig.class, id);
       }
	  return tawCommonsAccessoriesConfig;
	 }
	/* (non-Javadoc)根据应用的id获取附件配置信息
	 * @see org.hpin.commons.accessories.dao.TawCommonsAccessoriesConfigDao#getTawCommonsAccessoriesConfig(java.lang.Integer)
	 */
	public TawCommonsAccessoriesConfig getTawCommonsAccessoriesConfig(Integer appId) {
		// TODO Auto-generated method stub
	  TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig=null; 
	  List list=getHibernateTemplate().find("from TawCommonsAccessoriesConfig where appId="+appId);
	  if(list.size()>0){
	  	tawCommonsAccessoriesConfig = 
		  	 (TawCommonsAccessoriesConfig)list.get(0);
	  }
	  return tawCommonsAccessoriesConfig;
	 }

	/* (non-Javadoc)
	 * @see org.hpin.commons.accessories.dao.TawCommonsAccessoriesConfigDao#saveTawCommonsAccessoriesConfig(org.hpin.commons.accessories.model.TawCommonsAccessoriesConfig)
	 */
	public void saveTawCommonsAccessoriesConfig(TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig) {
		// TODO Auto-generated method stub
		String appId=tawCommonsAccessoriesConfig.getId();
		 
		
		if (appId==null) {			   
			getHibernateTemplate().save(tawCommonsAccessoriesConfig);
		}
		else {		
			TawCommonsAccessoriesConfig tempConfig=getTawCommonsAccessoriesConfig(appId);
			tawCommonsAccessoriesConfig.setId(tempConfig.getId());
			getHibernateTemplate().clear();
			getHibernateTemplate().update(tawCommonsAccessoriesConfig);
		}
			
	}

	/* (non-Javadoc)
	 * @see org.hpin.commons.accessories.dao.TawCommonsAccessoriesConfigDao#removeTawCommonsAccessoriesConfig(java.lang.Integer)
	 */
	public void removeTawCommonsAccessoriesConfig(Integer id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(getTawCommonsAccessoriesConfig(id));
	}

	/* (non-Javadoc)
	 * @see org.hpin.commons.accessories.dao.TawCommonsAccessoriesConfigDao#getTawCommonsAccessoriesConfig(java.lang.Integer)
	 */
	public TawCommonsAccessoriesConfig getAccessoriesConfigByAppcode(String appCode) {
		// TODO Auto-generated method stub
	  TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig=null;
	  List list=getHibernateTemplate().find("from TawCommonsAccessoriesConfig config where config.appCode='"+appCode+"'");
	  if(list.size()>0){
	  	tawCommonsAccessoriesConfig 
          = (TawCommonsAccessoriesConfig) list.get(0);
	  }
      if (tawCommonsAccessoriesConfig == null) {
	        HpinLog.warn(this,"uh oh, tawCommonsAccessoriesConfig with appCode '" + appCode + "' not found...");
	        throw new ObjectRetrievalFailureException(TawCommonsAccessoriesConfig.class, appCode);
       }
       return tawCommonsAccessoriesConfig;
	 }

	
}
