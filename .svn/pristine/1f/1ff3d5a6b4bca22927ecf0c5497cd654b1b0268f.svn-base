package org.hpin.base.accessories.service;

import java.util.ArrayList;
import java.util.List;

import org.hpin.base.accessories.dao.TawCommonsAccessoriesConfigDao;
import org.hpin.base.accessories.dao.jdbc.TawCommonsAccessoriesDaoJdbc;
import org.hpin.base.accessories.entity.TawCommonsAccessoriesConfig;
import org.hpin.base.accessories.entity.TawCommonsApplication;
import org.hpin.base.accessories.exception.AccessoriesConfigException;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:附件配置管理service实现类
 * </p>
 * <p>
 * Apr 10, 2007 11:00:28 AM
 * </p>
 * 
 * @author sherry
 * @version 1.0
 * 
 */
@Service(value = "org.hpin.system.accessories.service.TawCommonsAccessoriesConfigService")
@Transactional()
public class TawCommonsAccessoriesConfigService extends BaseService
		 {
	@Autowired
	private TawCommonsAccessoriesDaoJdbc daoJdbc;
	@Autowired
	private TawCommonsAccessoriesConfigDao configDAO;

	

	/**
	 * @return Returns the configDAO.
	 */
	public TawCommonsAccessoriesConfigDao getConfigDAO() {
		return configDAO;
	}

	/**
	 * @param configDAO
	 *            The configDAO to set.
	 */
	public void setConfigDAO(TawCommonsAccessoriesConfigDao configDAO) {
		this.configDAO = configDAO;
	}

	/**
	 * 获取配置信息
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	public List getTawCommonsAccessoriesConfigs()
			throws AccessoriesConfigException {
		List configInfoList = configDAO.getTawCommonsAccessoriesConfigs();
		return configInfoList;
	}

	/**
	 * 保存配置信息（xml文件）
	 * 
	 * @author 
	 * @param configObject
	 *            配置信息
	 */
	public void saveTawCommonsAccessoriesConfig(
			TawCommonsAccessoriesConfig config)
			throws AccessoriesConfigException {
		String appName = getAppNameByAppcode(config.getAppId());
		config.setAppName(appName);
		configDAO.saveTawCommonsAccessoriesConfig(config);
	}

	/**
	 * 查询配置信息
	 * 
	 * @author
	 * @param appCode
	 *            应用模板ID
	 * 
	 */
	public TawCommonsAccessoriesConfig getTawCommonsAccessoriesConfig(
			Integer appId) throws AccessoriesConfigException {
		TawCommonsAccessoriesConfig config = configDAO
				.getTawCommonsAccessoriesConfig(appId);
		return config;
	}

	/**
	 * 查询配置信息
	 * 
	 * @author
	 * @param appCode
	 *            应用模板编码
	 * 
	 */
	public TawCommonsAccessoriesConfig getAccessoriesConfigByAppcode(
			String appCode) throws AccessoriesConfigException {
		TawCommonsAccessoriesConfig config = configDAO
				.getAccessoriesConfigByAppcode(appCode);
		return config;
	}

	/**
	 * 查询配置信息
	 * 
	 * @author
	 * @param appCode
	 *            应用模板ID
	 * 
	 */
	public TawCommonsAccessoriesConfig getTawCommonsAccessoriesConfig(String id)
			throws AccessoriesConfigException {
		TawCommonsAccessoriesConfig config = configDAO
				.getTawCommonsAccessoriesConfig(id);
		return config;
	}

	/**
	 * 删除配置信息
	 * 
	 * @author 
	 * @param appCode
	 *            应用模板ID
	 * 
	 */
	public void removeTawCommonsAccessoriesConfig(Integer appId)
			throws AccessoriesConfigException {
		configDAO.removeTawCommonsAccessoriesConfig(appId);
	}

	/**
	 * 根据应用模块ID号查询模块名称
	 * 
	 * @param appCode
	 * @return
	 * @author 
	 */
	private String getAppNameByAppcode(Integer appCode) {
		String appName = "";
		appName = daoJdbc.getApplicatioNameById(appCode.intValue());
		return appName;
	}

	/**
	 * 获取应用模块信息
	 * 
	 * @return
	 * @author 
	 */
	public List getApplicationInfo() {
		List applicationTag = new ArrayList();
		List applicationInfo = daoJdbc.getTawSystemApplications();
		for (int i = 0; i < applicationInfo.size(); i++) {
			TawCommonsApplication application = (TawCommonsApplication) applicationInfo
					.get(i);
			/*applicationTag.add(new org.apache.struts.util.LabelValueBean(
					application.getAppName(), String.valueOf(application
							.getAppId())));*/
		  
            applicationTag.add(application );
		}
		return applicationTag;
	}


}
