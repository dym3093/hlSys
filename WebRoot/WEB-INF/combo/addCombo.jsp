<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>
<script type="text/javascript">
function submit_product() {
	   var comboId         = $("#comboId").val();
	   if (comboId == '') {
	   }
	   $("#comboBean").attr("action","../combo/comboAction!saveCombo.action");
	   $("#comboBean").submit();
	}
</script>

<form method="post" action="${path }/combo/comboAction!saveCombo.action" 
	class="pageForm required-validate" id="comboBean" 
	onsubmit="return validateCallback(this, navTabAjaxDone);">
	
	<div class="pageFormContent" style="overflow: hidden;" layoutH="87">
		<div class="tip"><span>套餐信息</span></div>

			<p class="nowrap">
				<label>套餐名称：</label> 
				<input type="text" name="combo.comboName" class="required valid" maxlength="600" value="${combo.comboName }"/>
				<input type = "hidden" name="combo.id" id="comboId" value = "${id}" />
			</p>
			
			<p class="nowrap">
				<label>项目类别：</label> 
				<select id="projectTypes" name="combo.projectTypes" class="required" rel="iselect" >
					<option value="">请选择</option>
					<option value="PCT_001">健康管理-基因</option>
					<option value="PCT_002">健康管理-癌筛</option>
					<option value="PCT_003">分子检测</option>
					<option value="PCT_004">健康管理-无创-生物电</option>
					<option value="PCT_005">健康管理-无创-微磁</option>
					<option value="PCT_006">其他</option>
				</select>
			</p>
			
			<p class="nowrap">
				<label>打印方：</label> 
				<select id="printType" name="combo.printType" class="required" rel="iselect" >
					<option value="0">远盟打印</option>
					<option value="1">基因公司打印</option>
					<option value="2">其他机构打印</option>
				</select>
			</p>
			
			<p class="nowrap">
				<label>时效（天）：</label> 
				<input type="text" name="combo.validityDay" class="required digits valid" maxlength="20" value="${combo.validityDay }"/>
			</p>
			
			<p class="nowrap"><label style="height: 60px;">产品名称：</label>
				<textarea cols="35" ows="2" style="width:570px;"  class="required" name="combo.productName">${combo.productName}</textarea>
			</p>
			<p class="nowrap"><label style="height: 60px;">套餐内容：</label>
				<textarea cols="35" ows="2" style="width:570px;"  class="required" name="combo.comboContent">${combo.comboContent}</textarea>
			</p>
		<div class="formBar">
			<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submit_product()">发布</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
			</ul>
		</div>
	</div>
	<input type = "hidden" name="currentTabId" value = "1" />

</form>