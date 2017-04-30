<%@ page language="java" contentType="text/html; charset=UTF-8; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.hpin.common.util.StrUtils"%>
<script type="text/javascript" language="javascript">
 function downexcel(fileName){
	window.location = "${path}/resource/downLoadExcel.action?filename="+fileName;
  }
 </script> 
  <div class="pageFormContent" id="import">
	<div>
	  <!-- <div class="tip"><span><b>客户信息导入</b></span></div> -->
	   <div class="tip"><span><b>模板下载</b>&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" onclick="downexcel('imp.xlsx')">EXCEL模板</a></span></div>
	  <form method="post" action="${path }/events/erpCustomer!saveCustomerAll.action" class="pageForm required-validate" onsubmit="return iframeCallback(this,returnOnclickAjaxDone);" enctype="multipart/form-data">
		<input name="navTabId" type="hidden" value="${ navTabId }"/>
		<%-- <input name="customer.eventsNo" type="hidden" value="${events.eventsNo}"/> --%>
		<%-- <div class="tip"><span><b>客户信息导入</b>&nbsp;&nbsp;场次：${events.eventsNo}</span></div> --%>
		<p>
		  <label>附件：</label>
		  <input type="file" class="required valid" id="affix" name="affix">
		</p>
		<div></div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="sc">上传</button></div></div></li>
			</ul>
		</div>
	  </form>
	</div>
  </div>
  