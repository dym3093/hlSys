.<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="pageContent">
		<div class="pageFormContent" layoutH="40" style="height: 330px;">
			<p>
				<label>套餐名称：</label>
				<input name="comboName" type="text" size="30" value="${combo.comboName }" readonly="readonly" class="textInput readonly">
			</p>
			<p>
				<label>项目类型：</label> <input name="comboName" type="text" size="30"
				value="<c:if test="${combo.projectTypes=='PCT_001'}">健康管理-基因</c:if><c:if test="${combo.projectTypes=='PCT_002'}">健康管理-癌筛</c:if><c:if test="${combo.projectTypes=='PCT_003'}">分子检测</c:if><c:if test="${combo.projectTypes=='PCT_004'}">健康管理-无创-生物电</c:if><c:if test="${combo.projectTypes=='PCT_005'}">健康管理-无创-微磁</c:if><c:if test="${combo.projectTypes=='PCT_006'}">其他</c:if>"
				readonly="readonly" class="textInput readonly">
		</p>
			<p>
				<label>1+X是否生成报告：</label>
				<select name="isCreatePdf" style="margin-top: 6px; margin-left: 4px;">
					<option value="0" ${combo.isCreatePdf == '0' ? "selected": "" }>否</option>
					<option value="1" ${combo.isCreatePdf == '1' ? "selected": "" }>是</option>
				</select>
			</p>
			<p>
				<label>打印套餐：</label>
				<select name="printType" style="margin-top: 6px; margin-left: 4px;">
					<option value="0" ${combo.printType == 0 ? "selected": "" }>否</option>
					<option value="1" ${combo.printType == 1 || combo.printType == null? "selected": "" }>是</option>
				</select>
			</p>
			<p>
				<label>套餐显示名称：</label>
				<input name="comboShowName" type="text" size="100" value="${combo.comboShowName }" class="textInput">
			</p>
			<p>
				<label>是否前端显示：</label>
				<select name="isUsed" style="margin-top: 6px; margin-left: 4px;">
					<option value="0" ${combo.isUsed == '0' ? "selected": "" }>否</option>
					<option value="1" ${combo.isUsed == '1' ? "selected": "" }>是</option>
				</select>
			</p>
	  
   	 	</div> 
	    <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="buttonSave">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</div>
<script type="text/javascript">
$(function() {
	$("#buttonSave", $.pdialog.getCurrent()).on("click", function() {
		var isUsed = $("select[name='isUsed']",$.pdialog.getCurrent()).val();
		var isCreatePdf = $("select[name='isCreatePdf']",$.pdialog.getCurrent()).val();
		var printType = $("select[name='printType']",$.pdialog.getCurrent()).val();
		var comboShowName = $("input[name='comboShowName']",$.pdialog.getCurrent()).val();
		var id = "${combo.id }";
		
		$.ajax({
			type: "post",
			cache : false,
			async : false,
			data:{"id":id, "comboShowName": comboShowName, "isCreatePdf":isCreatePdf, "isUsed":isUsed,"printType":printType},
			url: "${path }/resource/customerRelationShip!saveRelationCombo.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.result) {
					alertMsg.correct("您的数据提交成功！");
					navTab.reloadFlag("${navTabId}");
					$.pdialog.closeCurrent();
					
				} else {
					alertMsg.error("您提交的数据有误，请检查后重新提交！");
				}
			},
			error :function(){
				alertMsg.warn("服务发生异常，请稍后再试！");
				return;
			}
		});
		
		
	});
});
</script>