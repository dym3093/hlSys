package org.hpin.bg.listener;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.hpin.base.resource.entity.Resource;
import org.hpin.base.resource.service.ResourceService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.SystemConstant;
import org.hpin.common.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统启动Servlet
 * 
 * @author thinkpad
 * @Apr 15, 2009
 */
public class SystemServlet extends HttpServlet { 

	protected final Logger logger = LoggerFactory.getLogger("系统初始化");

	// 系统class路径
	public static String syspath = null;
	
	public static String waspath=null;

	/**
	 * 对系统进行初始化
	 */
	public void init() throws ServletException {
		SystemServlet.class.getClassLoader().getResourceAsStream( "" );
		
		logger.info("系统初始化开始");
		
		SpringTool.initInstance(this.getServletContext());
		
		logger.info("初始化基本数据 =============================");

		logger.info("初始化日志配置文件 ==========================");
		setStaticDataConstant();
		logger.info("系统初始化结束");
	}

	/**
	 * 设置静态基本数据
	 */
	private void setStaticDataConstant() {
		// 将系统资源URL连接成字符串,验证安全用
		List<Resource> list = ((ResourceService) SpringTool
				.getBean(ResourceService.class)).findAllEnable();
		String resources = "";
		for (int i = 0; i < list.size(); i++) {
			if (StrUtils.isNotNullOrBlank(list.get(i).getUrl())) {
				resources = resources + list.get(i).getUrl() + ",";
			}
		}
		SystemConstant.sys_resources = resources;
	}

}
