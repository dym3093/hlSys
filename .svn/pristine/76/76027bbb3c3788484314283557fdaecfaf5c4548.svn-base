/**
 * @author DengYouming
 * @since 2016-5-19 上午11:13:30
 */
package org.hpin.base.dict.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.dict.entity.ErpDict;
import org.hpin.base.dict.service.ErpDictService;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DengYouming
 * @since 2016-5-19 上午11:13:30
 */
@Namespace("/hpin")
@Action("erpDict")
@Results({   
    @Result(name="listErpDict",location="/WEB-INF/content/system/dict/listErpDict.jsp"),
    @Result(name="toAddErpDict",location="/WEB-INF/content/system/dict/addErpDict.jsp"),
})  
public class ErpDictAction extends BaseAction{

	@Autowired
	private ErpDictService service;
	
	/**
	 * 显示列表
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-19 上午11:37:50
	 */
	public String listErpDict(){
		List<ErpDict> list = null;
		Map<String,Object> params = new HashMap<String,Object>();
		
		try {
			list = service.listByProps(params, false);
			if(list!=null&&list.size()>0){
				page = new Page(HttpTool.getPageNum(), HttpTool.getPageSize());
				page.setResults(list);
				page.setTotalCount(Long.parseLong(""+list.size()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listErpDict";
	}
	
	/**
	 * 跳转添加页面
	 * @return String
	 * @author DengYouming
	 * @since 2016-5-19 下午12:07:14
	 */
	public String toAddPage(){
		HttpTool.setAttribute("navTabId", super.navTabId);
		return "toAddErpDict";
	}
	
	/**
	 * 
	 * 保存
	 * @author DengYouming
	 * @since 2016-5-19 上午11:38:03
	 */
	public void save(){
		JSONObject json = new JSONObject();
		//获取前台数据,封装成对象
		String jsonStr = HttpTool.getParameter("data");
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		List<ErpDict> list = JSONArray.toList(jsonArray, ErpDict.class);
		
		json.put("result", "error");
		try{
			if(list!=null&&list.size()>0){
				 //获取当前用户
				User currentUser = (User) HttpTool.getSession().getAttribute("currentUser");
				if(list.size()>1){
					ErpDict entity = null;
					List<ErpDict> tempList = new ArrayList<ErpDict>();
					for (int i = 0; i < list.size(); i++) {
						entity = list.get(i);
						entity.setCreateTime(new Date());
						entity.setCreateUser(currentUser.getUserName());
						entity.setCreateUserId(currentUser.getId());
						tempList.add(entity);
					}
					if(service.saveList(tempList)){
						json.put("result", "success");
					}
				}else if(list.size()==1){
					ErpDict entity = list.get(0);
					entity.setCreateTime(new Date());
					entity.setCreateUser(currentUser.getUserName());
					entity.setCreateUserId(currentUser.getId());
					if(service.save(entity)){
						json.put("result", "success");
					}
				}
			}
		}catch(Exception e){
			json.put("result", "error");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		renderJson(json);
	}
	
	public void toComboBox() throws Exception{
		JSONObject json = null;
		List<ErpDict> list = null;
		String type = HttpTool.getParameter(ErpDict.F_TYPEFILTER);
		
		if(StringUtils.isNotEmpty(type)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(ErpDict.F_TYPEFILTER, type);
			try {
				list = service.listByProps(params, false);
				if(list!=null&&list.size()>0){
					StringBuffer buff = new StringBuffer();
					for (ErpDict dict : list) {
						buff.append("[\""+dict.getValueName()+"\",\""+dict.getKeyName()+"\"]");
					}
					json = JSONObject.fromObject(buff.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		renderJson(json);
	}
	
}
