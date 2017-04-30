package org.hpin.base.resource.service;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hpin.base.resource.dao.ParameterConfigDao;
import org.hpin.base.resource.entity.ParameterConfig;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.XmlDom4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>@desc : 系统参数配置Service</p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 30, 2012 12:25:10 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
@Service(value = "org.hpin.base.resource.service.ParameterConfigService")
@Transactional()
public class ParameterConfigService extends BaseService {

	@Autowired
	private ParameterConfigDao parameterConfigDao = null;

	/**
	 * 系统参数配置
	 */
	public void initSysConfig() {
		parameterConfigDao.deleteAllData();
		Document document = XmlDom4j.loadFile("initData/sys_config.xml");
		Element rootElement = XmlDom4j.getRootElement(document);
		List<Element> childList = rootElement.selectNodes("config");
		String name = null;
		String value = null;
		String description = null;
		for (int i = 0; i < childList.size(); i++) {
			Element element = childList.get(i);
			ParameterConfig parameterConfig = new ParameterConfig();
			name = element.attributeValue("name");
			value = element.attributeValue("value");
			if (null != element.attributeValue("url")) {
				description = element.attributeValue("description");
				parameterConfig.setDescription(description);
			}
			parameterConfig.setName(name);
			parameterConfig.setValue(value);
			parameterConfigDao.saveForJdbc(parameterConfig);
		}
	}
}
