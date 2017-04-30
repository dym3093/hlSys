package org.hpin.base.accessories.tags;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.hpin.base.accessories.entity.TawCommonsAccessories;
import org.hpin.base.accessories.exception.AccessoriesException;
import org.hpin.base.accessories.service.TawCommonsAccessoriesManagerCOSService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.util.StaticMehtod;

/**
 * <p>
 * Title:下载标签
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Sep 11, 2008 8:00:06 PM
 * </p>
 * 
 * @author sherry
 * @version 3.5.1
 * 
 */
public class DownloadTag extends BodyTagSupport {

	/**
	 * log4j
	 */
	private final Logger logger = Logger.getLogger(DownloadTag.class);

	/**
	 * 用于下载的文件id
	 */
	private String ids;

	/**
	 * 应用id
	 */
	private String appId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4459571241103637956L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		List list = null;
		String html = "";
		try {
			TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService=(TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
			list =tawCommonsAccessoriesManagerCOSService.getAllFileById(ids);

		} catch (AccessoriesException e) {
			logger.error(e.getMessage());
		}
		if (list != null) {

			for (Iterator it = list.iterator(); it.hasNext();) {
				TawCommonsAccessories tawCommonsAccessories = (TawCommonsAccessories) it
						.next();
				tawCommonsAccessories.getId();
				// /eoms/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=8aa081281c548400011c548beb100007
				html += "<a href ='"
						+ request.getContextPath()
						+ "/accessories/tawCommonsAccessoriesConfigs.do?method=download&id="
						+ tawCommonsAccessories.getId() + "'/>"
						+ tawCommonsAccessories.getAccessoriesCnName()
						+ "</a><br>";
			}
		}
		if (html != null && !"".equals(html)) {
			html = StaticMehtod.removeLastStr(html, "<br>");
		}
		try {
			pageContext.getOut().write(html);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return super.doStartTag();
	}

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

}
