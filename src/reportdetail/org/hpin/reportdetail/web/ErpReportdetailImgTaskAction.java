package org.hpin.reportdetail.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetailImginfo;
import org.hpin.reportdetail.entity.ErpReportdetailImgtask;
import org.hpin.reportdetail.service.ErpReportdetailImginfoService;
import org.hpin.reportdetail.service.ErpReportdetailImgtaskService;
import org.springframework.beans.factory.annotation.Value;

/**
 * PDF转换img任务Action
 * @author LeslieTong
 * @date 2017-3-24下午2:46:57
 */

@Namespace("/reportdetail")
@Action("erpReportdetailImgTask")
@Results({
    @Result(name="listReportdetailImgtask",location="/WEB-INF/reportdetail/listReportdetailImgtask.jsp"),
    @Result(name="showPictures",location="/WEB-INF/reportdetail/showPictures.jsp"),
})
public class ErpReportdetailImgTaskAction extends BaseAction{
	@Value("${jpg.file.to.path}")
	private String jpgToPath;
	@Value("${imginfo.common.pages01}")
	private String commonPage01;	//公共页01
	@Value("${imginfo.common.pages02}")
	private String commonPage02;	//公共页02
	@Value("${imginfo.common.pages03}")
	private String commonPage03;	//公共页03
	@Value("${imginfo.common.pages04}")
	private String commonPage04;	//公共页04
	@Value("${imginfo.common.pages05}")
	private String commonPage05;	//公共页05
	private Logger log = Logger.getLogger(ErpReportdetailImgTaskAction.class);
	
	ErpReportdetailImgtaskService service = (ErpReportdetailImgtaskService)SpringTool.getBean(ErpReportdetailImgtaskService.class);
	ErpReportdetailImginfoService infoService = (ErpReportdetailImginfoService)SpringTool.getBean(ErpReportdetailImginfoService.class);
	
	public String listReportdetailImgtask(){
		Map searchMap = super.buildSearch();
		String stateInput = HttpTool.getParameter("stateInput");
		try {
			page = new Page(HttpTool.getPageNum(),HttpTool.getPageSize());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ErpReportdetailImgtask> list = service.findByPage(page, searchMap);
		
		/* 
		 * 微磁：该检测仅适用年龄12-80岁之间的人群
		 * 生物电：该检测仅适用年龄6-90岁之间的人群
		 * 
		 * 没有匹配上客户：未匹配客户
		 * 年龄不在范围内：年龄不符合
		 * 年龄在范围内：年龄符合
		 */
		for (ErpReportdetailImgtask imgtask : list) {
			String messsage = "";
			imgtask.getAgeRangeStatus();
			String code = imgtask.getCode();
			Map<String, Object> mapResult = service.getCustomerWuChuangByCode(code);
			if((mapResult!=null&&mapResult.get("code")!=null)){
				
				if(mapResult.get("age")!=null){
					int age = Integer.parseInt((String) mapResult.get("age")) ;
					if(code.indexOf("W")!=-1){	//无创生物电
						if(age>=6&&age<=90){
							messsage="年龄符合";
						}else{
							messsage="年龄不符合";
						}
					}
					if(code.indexOf("C")!=-1){	//微磁
						if(age>=12&&age<=80){
							messsage="年龄符合";
						}else{
							messsage="年龄不符合";
						}
					}
				}else{
					messsage="年龄不符合";
				}
				
			}else{
				messsage = "未匹配客户";
			}
			imgtask.setAgeRangeStatus(messsage);
		}
		page.setResults(list);
		HttpTool.setAttribute("stateInput", stateInput);
		
		return "listReportdetailImgtask";
	}
	
	public void pdf2Jpg() {
		String ids = HttpTool .getParameter("imgTaskIds");
		JSONObject json = new JSONObject();
		try {
			boolean result = service.pdf2Jpg(ids, jpgToPath, mapCommonPages());
			json.put("result", result);
			
		} catch (Exception e) {
			json.put("result", false);
			log.info("pdf2jpg", e);
			
		} finally {
			renderJson(json);
		}
	}
	
	/**
	 *  @author Carly
	 *  @since 2017年4月17日10:58:49
	 *  显示图片
	 */
	public String showPictures() {
		String taskId = HttpTool.getParameter("taskId");
		try {
			page = new Page<ErpReportdetailImginfo>(HttpTool.getPageNum(), 1);	//每页显示一张图片
			infoService.getImgPathList(page, taskId);
			HttpTool.setAttribute("taskId", taskId);
		} catch (ParseException e) {
			log.info("图片查询失败", e);
			
		}
		return "showPictures";
	}
	
	private Map<Integer, String> mapCommonPages() {
		Map<Integer, String> mapCommonPages = new HashMap<Integer, String>();
		mapCommonPages.put(1, commonPage01);
		mapCommonPages.put(2, commonPage02);
		mapCommonPages.put(3, commonPage03);
		mapCommonPages.put(4, commonPage04);
		mapCommonPages.put(5, commonPage05);
		return mapCommonPages;
	}
	
}
