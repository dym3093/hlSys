package org.hpin.base.accessories.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.accessories.entity.TawCommonsAccessories;
import org.hpin.base.accessories.service.TawCommonsAccessoriesConfigService;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;

@Namespace("/hpin")
@Action("acessesories")
@Results( {
	

}
)
public final class TawCommonsAccessoriesAction extends BaseAction {
	
	TawCommonsAccessoriesConfigService tawCommonsAccessoriesConfigService = (TawCommonsAccessoriesConfigService) SpringTool.getBean(TawCommonsAccessoriesConfigService.class);
	
	private TawCommonsAccessories tawCommonsAccessories;
	public TawCommonsAccessories getTawCommonsAccessories() {
		return tawCommonsAccessories;
	}
	public void setTawCommonsAccessories(TawCommonsAccessories tawCommonsAccessories) {
		this.tawCommonsAccessories = tawCommonsAccessories;
	}
	public String cancel()
			throws Exception {
		return "search";
	}
    /**
     * 文件查询，未使用此方法，预留
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author sherry
     */
	public String search()throws Exception {
	/*	
		ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) getBean("ItawCommonsAccessoriesManager");
		request.setAttribute(Constants.TAWCOMMONSACCESSORIES_LIST, mgr
				.getTawCommonsAccessoriess());
		return mapping.findForward("list");*/
		return "";
	}
	/**
     * 默认执行方法
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author panlong
     */
	public String unspecified()
			throws Exception {
		
		return "search";
	}
}
