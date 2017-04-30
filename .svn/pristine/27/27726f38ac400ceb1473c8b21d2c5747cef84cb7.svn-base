package org.hpin.base.customerrelationship.web;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.customerrelationship.entity.GeneTestInstitution;
import org.hpin.base.customerrelationship.service.CustomerRelationShipService;
import org.hpin.base.customerrelationship.service.GeneTestInstitutionService;
import org.hpin.base.dict.entity.SysDictType;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;

/**
 * 增加修改ErpCustomer页面【检测结构】下拉选项Action
 * @author LeslieTong
 * @date 2017-4-12下午5:02:49
 */

@Namespace("/resource")
@Action("geneTestInstitution")
@Results( { 
	})
public class GeneTestInstitutionAction extends BaseAction {
	
	private static final Logger log = Logger.getLogger(CustomerRelationShipAction.class);
	
	GeneTestInstitutionService service = (GeneTestInstitutionService)SpringTool.getBean(GeneTestInstitutionService.class);

	/**
	 * 获取所有检测机构
	 * @return
	 * @author LeslieTong
	 * @date 2017-4-12下午5:12:23
	 */
	public String findGeneTestInstitution(){
			log.info("get GeneTestInstitutionAction findGeneTestInstitution()");
			StringBuffer json = new StringBuffer("[") ;
			//加载叶子节点下的button
			List<GeneTestInstitution> resultList = service.findGeneTestInstitution()  ;
			if(resultList!=null&&resultList.size() > 0){
				
				for (GeneTestInstitution geneTestInstitution : resultList) {
					json.append("{\"text\":\"" + geneTestInstitution.getGeneTestInstitutionName() + "\",\"id\":\"" + geneTestInstitution.getGeneTestInstitutionName()+ "\",\"leaf\":" + true) ;
					json.append("},") ;
				}
			}
			if (json.toString().endsWith(",")) {
				json = json.delete(json.length() - 1, json.length());
			}

			json.append("]");
			this.jsonString = json.toString();
		
		return "json";
	}
	
}
