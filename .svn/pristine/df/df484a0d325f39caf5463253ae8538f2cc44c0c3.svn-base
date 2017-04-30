/**
 * 
 */
package org.hpin.settlementManagement.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ErpPrintComboCost;
import org.hpin.settlementManagement.service.ErpPrintComboCostService;
import org.hpin.warehouse.entity.StoreWarehouse;
import org.hpin.warehouse.service.StoreWarehouseService;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


/**
 * 	@author chenqi
 *	报告打印费用结算
 */
@Namespace("/settlementManagement")
@Action("erpPrintComboCost")
@Results({
	@Result(name="listPrintComboCost",location="/WEB-INF/settlementManagement/printcost/listPrintComboCost.jsp"),
	@Result(name="showEditComboCost",location="/WEB-INF/settlementManagement/printcost/showEditComboCost.jsp"),
	@Result(name="showAddComboCost",location="/WEB-INF/settlementManagement/printcost/showAddComboCost.jsp"),

})  
@SuppressWarnings({ "rawtypes"})
public class ErpPrintComboCostAction extends BaseAction{
	
	private static final long serialVersionUID = -6018322640855658720L;
	/** 打印公司套餐费用 */
	private ErpPrintComboCostService service = (ErpPrintComboCostService)SpringTool.getBean(ErpPrintComboCostService.class);
	/** 打印公司 */
	private StoreWarehouseService storeService = (StoreWarehouseService)SpringTool.getBean(StoreWarehouseService.class);
	/** 打印套餐 */
	private ComboService comboService = (ComboService)SpringTool.getBean(ComboService.class);
	
	private Logger log = Logger.getLogger(ErpPrintComboCostAction.class);
    
    /**
     * 批次信息查询
     */
    public String listPrintComboCost(){
    	try {
    		Map searchMap = buildSearch();
    		page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
    		List <ErpPrintComboCost> comboCostList = service.findByPage(page, searchMap);
    		page.setResults(comboCostList);
		} catch (Exception e) {
			log.error("ErpPrintComboCostAction listPrintComboCost", e);
		}
        return "listPrintComboCost";
    }
    
    /**
     * 	打印公司下拉框
     */
    public void setPrintCompanySelect(){
    	setSelectdInput("company", service.getPrintCompany());
    }
    
    /**
     * 	打印公司下拉框
     */
    public void setComboSelect(){
    	setSelectdInput("combo", service.getCombos());
    }
    
    
    public void setSelectdInput(String key,List list) {
		JSONObject json = new JSONObject();
		json.put(key, list);
		renderJson(json);
	}
    
    /**
     * @return 显示新增套餐页面
     */
    public String showAddComboCost(){
    	
    	return "showAddComboCost";
    }
    
    /**
     * @return 新增套餐费用
     */
    public void addComoboCost(){
    	JSONObject json = new JSONObject();
    	try {
    		User user = (User)HttpTool.getSession().getAttribute("currentUser");
    		String printCompanyId = HttpTool.getParameter("companyId");
    		String comboId = HttpTool.getParameter("comboId");
    		BigDecimal price = new BigDecimal(HttpTool.getParameter("price"));
    		int count = service.getPrintComboCount(printCompanyId,comboId);//是否有该公司的套餐信息
    		if(count==0){
    			Combo combo = (Combo) comboService.findById(comboId);
    			StoreWarehouse warehouse = (StoreWarehouse)storeService.findById(printCompanyId);
    			ErpPrintComboCost comboCost = new ErpPrintComboCost();
    			comboCost.setPrintCompanyId(printCompanyId);
    			comboCost.setPrintCompany(warehouse.getName());
    			comboCost.setComboId(comboId);
    			comboCost.setComboName(combo.getComboName());
    			comboCost.setProductName(combo.getProductName());
    			comboCost.setComboDetail(combo.getComboContent());
    			comboCost.setCreateUser(user.getAccountName());
    			comboCost.setCreateTime(new Date());
    			comboCost.setIsDeleted(0);
    			comboCost.setPrice(price);
    			service.save(comboCost);
    			json.put("count", 1);
    		}else {
				json.put("count", 2);
			}
		} catch (Exception e) {
			json.put("count", 0);
			log.error("ErpPrintComboCostAction addComoboCost(保存新增套餐费用)", e);
		}
    	renderJson(json);
    }
    
    /**
     * @return 显示套餐修改页面
     */
    public String showEditComboCost(){
    	try {
    		HttpTool.setAttribute("id", HttpTool.getParameter("id"));
    		HttpTool.setAttribute("company", java.net.URLDecoder.decode(HttpTool.getParameter("company"),"UTF-8"));
    		HttpTool.setAttribute("combo", java.net.URLDecoder.decode(HttpTool.getParameter("combo"),"UTF-8"));
    		HttpTool.setAttribute("price", HttpTool.getParameter("price"));
		} catch (Exception e) {
			log.error("ErpPrintComboCostAction showEditComboCost(显示套餐修改页面)",e);
		}
    	return "showEditComboCost";
    }
    
    /**
     * 	修改套餐费用 
     */
    public void editComoboCost(){
    	String id = HttpTool.getParameter("id");
    	BigDecimal price = new BigDecimal(HttpTool.getParameter("price"));
    	JSONObject json = new JSONObject();
    	User user = (User)HttpTool.getSession().getAttribute("currentUser");
    	try {
    		ErpPrintComboCost comboCost = (ErpPrintComboCost) service.findById(id);
    		comboCost.setUpdateTime(new Date());
    		comboCost.setUpdateUser(user.getAccountName());
    		comboCost.setPrice(price);
    		service.update(comboCost);
    		json.put("count", 1);
		} catch (Exception e) {
			json.put("count", 0);
			log.error("ErpPrintComboCostAction editComoboCost(修改打印公司套餐费用)", e);
		}
    	renderJson(json);
    }
    
    /**
     * 	删除套餐费用
     */
    public void deleteComoboCost(){
    	String id = HttpTool.getParameter("id");
    	int count = 0;
    	JSONObject json = new JSONObject();
    	try {
    		count = service.updateIsDeleted(id);
			json.put("count", count);
		} catch (Exception e) {
			json.put("count", count);
			log.error("ErpPrintComboCostAction deleteComoboCost(删除打印公司套餐费用)", e);
		}
    	renderJson(json);
    }

    
}
