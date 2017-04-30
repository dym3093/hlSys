package org.hpin.reportdetail.web;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.web.BaseAction;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.StrUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.reportdetail.entity.ErpReportdetail;
import org.hpin.reportdetail.service.ErpReportdetailService;

import cn.yuanmeng.labelprint.test.TestExcelProcess;

@Namespace("/reportdetail")
@Action("gene")
@Results({
	@Result(name="res",location="/gene.jsp"),
})
public class GeneAction extends BaseAction {
	private TestExcelProcess t=new TestExcelProcess();
	/**
	 * 处理 
	 * @return
	 */
	public String process(){
		String path=HttpTool.getParameter("path");
//		t.start(path);
		System.out.println("处理结束!");
		return "res";
	}
	
}
