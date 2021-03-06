package org.hpin.base.dict.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.dict.dao.SysDictTypeDao;
import org.hpin.base.dict.entity.DictMailBase;
import org.hpin.base.dict.entity.DictMailSysdict;
import org.hpin.base.dict.service.DictMailBaseService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @用户邮箱
 * @author wuhao
 *
 */
@Namespace("/hpin")
@Action("dictMail")
@Results({
	
	@Result(name ="listDictMail", location="/WEB-INF/content/system/dictMail/listDictMail.jsp"),
	@Result(name = "addDictMail", location = "/WEB-INF/content/system/dictMail/addDictMail.jsp") 
})
public class DictMailBaseAction extends BaseAction{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6447329410403661708L;

	private static final Logger log = Logger.getLogger(DictMailBase.class);

	private DictMailBaseService dictMailBaseService =  (DictMailBaseService) SpringTool.getBean(DictMailBaseService.class);
	
	@Autowired
	private SysDictTypeDao dictTypeDao;
	
	private DictMailBase dictMailBase;
	
	private List<DictMailSysdict> dictMailSysdicts;
	/**
	 * 根据条件查询用户邮箱字典
	 * @return
	 */
	public String listDictMail(){
		try {
		if (params == null) {
			params =new HashMap<String, String>();
		}
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
			this.dictMailBaseService.findDictMailByPage(page, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("用户邮件信息查询错误",e);;
		}
		
		return "listDictMail";		
	}
	
	/**
	 * @进入添加用户邮箱页面
	 */
	public String addDictMail() {
		ArrayList list = new ArrayList();
		String parentdictid="10120";
		list = dictTypeDao.getDictSonsByDictid(parentdictid);		
		HttpTool.setAttribute("list",list);
		if(StringUtils.isNotEmpty(id)) {
			dictMailBase = (DictMailBase) this.dictMailBaseService.findById(id);
			//查询中间表
			dictMailSysdicts = this.dictMailBaseService.findDictMailSysdictsByMailId(id);
		}
		return "addDictMail";
	}
	
	
	/**
	 * 保存与修改;
	 */
	public void saveOrUpdate() {
		JSONObject json = new JSONObject();
		String statusCode = "200";
		String callbackType= "closeCurrent";
		String message = "操作成功!";
		try {
			if(dictMailBase != null) {
				User userInfo = getUserInfo();
				if(StringUtils.isEmpty(dictMailBase.getId())) { 
					this.dictMailBaseService.saveProCombo(dictMailBase, userInfo, dictMailSysdicts);
				} else {
					this.dictMailBaseService.updateProCombo(dictMailBase, userInfo, dictMailSysdicts);
				}
			}
			
		} catch (Exception e) {
			statusCode = "300";
			message = e.getMessage();
			log.error(message, e);
		}
		json.put("statusCode", statusCode);
		json.put("navTabId", navTabId);
		json.put("callbackType", callbackType);
		json.put("message", message);
		renderJson(json);
		
		
	}
	
	/**
	 * 判断数据库中是否存在同样的邮箱
	 */
	public void exitsObject() {
		JSONObject json = new JSONObject();
		String message = "操作成功!";
		//验证的同时判断是否可以删除,如果被使用则不可以删除;
		String mailAddress = HttpTool.getParameter("mailAddress", "");
		boolean result = this.dictMailBaseService.exitsObject(id, mailAddress);
		
		json.put("result", result);
		json.put("message", message);
		json.put("navTabId", navTabId);
		renderJson(json);
	}
	
	/**
	 * 删除
	 */
	public void deleteDictMail() {
		JSONObject json = new JSONObject();
		String message = "操作成功!";
		//验证的同时判断是否可以删除,如果被使用则不可以删除;
		boolean result = this.dictMailBaseService.deleteValid(ids, getUserInfo());
		if(!result) {
			message = "存在邮箱正在启用中,不能删除!";
		}
		json.put("result", result);
		json.put("message", message);
		json.put("navTabId", navTabId);
		renderJson(json);
	}
	
	

	public DictMailBase getDictMailBase() {
		return dictMailBase;
	}

	public void setDictMailBase(DictMailBase dictMailBase) {
		this.dictMailBase = dictMailBase;
	}

	public List<DictMailSysdict> getDictMailSysdicts() {
		return dictMailSysdicts;
	}

	public void setDictMailSysdicts(List<DictMailSysdict> dictMailSysdicts) {
		this.dictMailSysdicts = dictMailSysdicts;
	}
	
	
}
