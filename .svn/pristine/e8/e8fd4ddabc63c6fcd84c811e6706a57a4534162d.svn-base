package org.hpin.settlementManagement.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.settlementManagement.entity.ComboHistoryPrice;
import org.hpin.settlementManagement.entity.ErpComboPrice;
import org.hpin.settlementManagement.service.ErpComboPriceService;
import org.ymjy.combo.entity.Combo;
import org.ymjy.combo.service.ComboService;


@Namespace("/combo")
@Action("erpComboPrice")
@Results({
    @Result(name="toAddErpComboPrice",location="/WEB-INF/settlementManagement/addErpComboPrice.jsp"),
    @Result(name="toModifyComboPrice",location="/WEB-INF/settlementManagement/modifyComboPrice.jsp"),
    @Result(name="listErpComboPrice",location="/WEB-INF/settlementManagement/comboPrice.jsp"),
})  
public class ErpComboPriceAction extends BaseAction {
	private Logger log = Logger.getLogger(ErpComboPriceAction.class);
	ErpComboPriceService comboPriceService = (ErpComboPriceService) SpringTool.getBean(ErpComboPriceService.class);
	ComboService comboService  = (ComboService) SpringTool.getBean(ComboService.class);
	ErpComboPrice erpComboPrice;
	ComboHistoryPrice comboHistoryPrice;
	
	
	
    /**
     * 批次信息查询
     */
    public String listErpComboPrice() throws Exception{
        String selectGeCompanyId = HttpTool.getParameter("selectGeCompanyId");
        String selectComboId = HttpTool.getParameter("selectComboId");
        Map searchMap = super.buildSearch(); //获取查询参数(查询参数以filter开始)
        page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
        List<ErpComboPrice> comboPriceList=comboPriceService.findByPage(page, searchMap);
        page.setResults(comboPriceList) ;
        
        HttpTool.setAttribute("selectGeCompanyId", selectGeCompanyId);
        HttpTool.setAttribute("selectComboId", selectComboId);
        return "listErpComboPrice";
    }
    
    /**
     * 增加初始化页面
     * @return
     */
    public String toAddErpComboPrice(){
    	
    	return "toAddErpComboPrice";
    }
    /**
     * 增加一个价格套餐
     */
    public void addErpComboPrice(){
    	boolean isRepeat=false;
    	BigDecimal priceBD = null;
    	JSONObject json = new JSONObject();
    	ErpComboPrice comboPrice = null;
    	User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
    	String comboId = HttpTool.getParameter("comboName");
    	String geneCompany = HttpTool.getParameter("geCompanyNameSave");
    	String geneCompanyId = HttpTool.getParameter("geneCompany");
    	String comboName = HttpTool.getParameter("selectComboName");
    	String navTabId = HttpTool.getParameter("navTabId");
    	String price = HttpTool.getParameter("price");
    	priceBD =  new BigDecimal(price);
    	isRepeat = comboPriceService.judgeRepeatList(comboId, geneCompanyId);
    	if(isRepeat){
    		Combo combo = (Combo) comboService.findById(comboId);
    		if(combo != null && StringUtils.isNotEmpty(geneCompanyId)){
    			comboPrice = new ErpComboPrice();
    			comboPrice.setComboId(comboId);
    			comboPrice.setComboName(comboName);
    			comboPrice.setProductName(combo.getProductName());
    			comboPrice.setComboContent(combo.getComboContent());
    			comboPrice.setGeneCompany(geneCompany);
    			comboPrice.setGeneCompanyId(geneCompanyId);
    			comboPrice.setPrice(priceBD);
    			comboPrice.setCreateTime(new Date());
    			comboPrice.setIsDelete("0");
    			comboPrice.setCreateUsername(currentUser.getUserName());
    			try {
    				comboPriceService.addComboPrice(comboPrice);
    				json.accumulate("statusCode", 200);
    				json.accumulate("message", "添加成功！");
    				json.accumulate("navTabId", navTabId);
    				json.accumulate("callbackType", "refreshCurrent");
    			} catch (Exception e) {
    				log.error("addErpComboPrice",e);
    				json.accumulate("statusCode", 300);
    				json.accumulate("message", "添加失败！");
    				e.printStackTrace();
    			}
    		}else{
        		json.accumulate("statusCode", 300);
    			json.accumulate("message", "数据不完整,请重新选择！");
        	}
        	
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "重复添加,添加失败！");
    	}
    	
    	
    	renderJson(json);
    }
 
    /**
     * 删除请求
     */
    public void delErpComboPrice(){
    	JSONObject json = new JSONObject();
    	String ids = HttpTool.getParameter("ids");
    	System.out.println("ids------------"+ids);
    	String[] id = ids.replaceAll(" ", "").split(",");
    	int length = id.length;
    	
    	if(length>0){
    		try {
    			for(int i=0; i<length; ++i){
    	    		erpComboPrice = comboPriceService.get(id[i]);
    	    		erpComboPrice.setIsDelete("1");
    	    		comboPriceService.updateComboPrice(erpComboPrice);
    	    	}
    			json.accumulate("statusCode", 200);
    			json.accumulate("message", "下架成功");
    			json.accumulate("navTabId", navTabId);
    			json.accumulate("callbackType", "refreshCurrent");
			} catch (Exception e) {
				log.error("delErpComboPrice",e);
				json.accumulate("statusCode", 300);
				json.accumulate("message", "下架失败");
			}
    	}else{
    		
    	}
    	
    	renderJson(json);
    }
    
    /**
     * 默认初始化页面
     * @return
     */
    public String toModifyErpComboPrice(){
    	ErpComboPrice comboPrice = new ErpComboPrice();
    	String id = HttpTool.getParameter("id");
    	comboPrice = comboPriceService.get(id);
    	
    	HttpTool.setAttribute("price", comboPrice.getPrice());
    	HttpTool.setAttribute("comboPriceId", comboPrice.getId());
	
    	return "toModifyComboPrice";
    }
    /**
     * 修改套餐价格
     */
    public void modifyErpComboPrice(){
    	boolean flag=false;
    	JSONObject json = new JSONObject();
    	ComboHistoryPrice comboHistoryPrice = new ComboHistoryPrice();
    	BigDecimal hisDB = null;
    	BigDecimal currDB = null;
    	
    	String historyPrice = HttpTool.getParameter("historyPrice");//原来的价格
    	String currentPrice = HttpTool.getParameter("currentPrice");//修改后的价格
    	String comboPriceId = HttpTool.getParameter("comboPriceId");//带过来的ID
    	User currentUser = (User)HttpTool.getSession().getAttribute("currentUser");
    	erpComboPrice = comboPriceService.get(comboPriceId);
    	
    	try {
    		hisDB=new BigDecimal(historyPrice);
            currDB = new BigDecimal(currentPrice);
		} catch (Exception e) {
			log.error("modifyErpComboPrice for BigDecimal",e);
			e.printStackTrace();
		}
        
    	
    	if(hisDB!=null&&currDB!=null){
    		comboHistoryPrice.setHistoryPrice(hisDB);
    		comboHistoryPrice.setIsDelete("0");
    		comboHistoryPrice.setCreateTime(new Date());
    		comboHistoryPrice.setCreateUsername(currentUser.getUserName());
    		comboHistoryPrice.setComboId(erpComboPrice.getComboId());
    		comboHistoryPrice.setBranchCompanyId(erpComboPrice.getGeneCompanyId());
    		comboHistoryPrice.setBranchCompany(erpComboPrice.getGeneCompany());
    		
    		erpComboPrice.setPrice(currDB);
    		erpComboPrice.setUpdateUsername(currentUser.getUserName());
    		erpComboPrice.setUpdateTime(new Date());
    		try {
    			comboPriceService.updateComboPrice(erpComboPrice);
    			comboPriceService.saveHistoryPrice(comboHistoryPrice);
    			flag = true;
			} catch (Exception e) {
				log.error("updateComboPrice and saveHistoryPrice",e);
				flag = false;
				e.printStackTrace();
			}
    	}else{//为空时
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败,请检查输入数据是否！");
    	}
    	
    	if(flag){
    		json.accumulate("statusCode", 200);
			json.accumulate("message", "修改成功");
			json.accumulate("navTabId", navTabId);
			json.accumulate("callbackType", "refreshCurrent");
    	}else{
    		json.accumulate("statusCode", 300);
			json.accumulate("message", "修改失败");
    	}
    	renderJson(json);
    }
    
    public void listCombo(){//设置基因公司下拉框
    	JSONObject jsonObject = new JSONObject();
    	List<ErpComboPrice> comboList = comboPriceService.listCombo();
    	jsonObject.put("combo", comboList);
    	renderJson(jsonObject);
    }
    
    public void setComboSelectedInput(){//设置套餐下拉框
    	JSONObject jsonObject = new JSONObject();
    	List<Combo> comboList = comboPriceService.getComboSelectedInput();
    	jsonObject.put("combos", comboList);
    	renderJson(jsonObject);
    }
    
	public ErpComboPrice getErpComboPrice() {
		return erpComboPrice;
	}

	public void setErpComboPrice(ErpComboPrice erpComboPrice) {
		this.erpComboPrice = erpComboPrice;
	}

	public ComboHistoryPrice getComboHistoryPrice() {
		return comboHistoryPrice;
	}

	public void setComboHistoryPrice(ComboHistoryPrice comboHistoryPrice) {
		this.comboHistoryPrice = comboHistoryPrice;
	}
}
