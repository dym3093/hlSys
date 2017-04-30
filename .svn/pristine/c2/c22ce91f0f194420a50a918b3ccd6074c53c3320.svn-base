package org.hpin.base.accessories.bo;

import org.hpin.base.accessories.entity.HpinAccessInfBZ;
import org.hpin.base.accessories.service.TawCommonsAccessoriesManagerCOSService;
import org.hpin.common.core.SpringTool;

/**
 * 附件Bo
 * <p>@desc : </p>
 * <p>@see : </p>
 *
 * <p>@author : sky</p>
 * <p>@createDate : Aug 29, 2012 6:06:53 PM</p>
 * <p>@version : v1.0 </p>
 * <p>All Rights Reserved By Acewill Infomation Technology(Beijing) Co.,Ltd</p>
 */
public class TawCommonsAccessoriesBO {

	
	private TawCommonsAccessoriesBO() {

	}
	private static TawCommonsAccessoriesBO instance = null;
	public static TawCommonsAccessoriesBO getInstance() {
		if (instance == null) {
			instance = init();
		}
		return instance;
	}

	private static TawCommonsAccessoriesBO init() {
		
		instance = new TawCommonsAccessoriesBO();

		return instance;
	}

	/**
	 * 根据附件文件名获取附件路径url
	 * @param accesspriesFileNames
	 * @return
	 */
	public String getAccessUrlBy(String accesspriesFileNames){
		
		TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
		
		String url = "";
		
		url = tawCommonsAccessoriesManagerCOSService.getFileNameAndUrl(accesspriesFileNames);
		
		return url;
	
	}
	
	
	
	/**
	 * 根据附件
	 * @param accesspriesFileNames
	 * @return
	 */
    public HpinAccessInfBZ getHpinAccessInfBZ(String accesspriesFileNames){
    	HpinAccessInfBZ accessinfbz = new HpinAccessInfBZ();
		if( accesspriesFileNames !=null && !accesspriesFileNames.equals("")){
			TawCommonsAccessoriesManagerCOSService tawCommonsAccessoriesManagerCOSService = (TawCommonsAccessoriesManagerCOSService)SpringTool.getBean(TawCommonsAccessoriesManagerCOSService.class);
			accessinfbz.setPhototype(0);
//			accessinfbz.setListphotopath(tawCommonsAccessoriesManagerCOSService.getListAccessUrl(accesspriesFileNames));
			String[] str = accesspriesFileNames.split("\\.");
			accessinfbz.setPhotono("");
			accessinfbz.setStrphotopath(tawCommonsAccessoriesManagerCOSService.getFileNameAndUrl(accesspriesFileNames));
//			String appcode = (tawCommonsAccessoriesManagerCOSService.getListAccess(accesspriesFileNames) == null || 
//					tawCommonsAccessoriesManagerCOSService.getListAccess(accesspriesFileNames).size() == 0 ) ? "kmmanager" : 
//					tawCommonsAccessoriesManagerCOSService.getListAccess(accesspriesFileNames).get(0).getAppCode();
			//accessinfbz.setPhotoalagn(tawCommonsAccessoriesManagerCOSService.getTawCommonsAccessoriesConfig(appcode).getAppName());
			accessinfbz.setPhotoalagn("images") ;
		}
		return accessinfbz;
	}
	
}
