<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" language="javascript">
 function downexcel(fileName){
	window.location = "${path }/healthMsg/downLoadExcel.action?filename="+fileName;
  }
</script>  
  
<div class="pageContent">
	<form method="post" action="${path }/healthMsg/healthInfoAction!doUploadFile.action" class="pageForm required-validate" enctype="multipart/form-data"  onsubmit="return iframeCallback(this, navTabAjaxDone);"  >
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<input type="hidden" name="infoid" value = "${infoid}" />
		<div class="pageFormContent" layoutH="56">
			
		<div class="tip"><span>模板下载：&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" onclick="downexcel('imp.xlsx')">EXCEL模板</a></span></div>	
		
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: red">注意：导入数据会把该病症旧数据删除!</span>
				
		<div class="tip"><span>模板导入：</span></div>
		<p>
		  <label>附件：</label>
		  <input type="file" class="required valid" id="fileobj" name="fileobj">
		</p>
    <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">上传导入</button></div></div></li>
			</ul>
		</div>
			</div>
	</form>
</div>
