<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/combo/erpComboPrice!setComboSelectedInput.action",
		success: function(data){
			$("#comboName").empty();
			var s= eval("("+data+")");
			var option="";
			var combos = s.combos;
			for(var i=0;i<combos.length;i++){		
				option += "<option value='"+combos[i].id+"'>"+combos[i].comboName+"</option>";
			}
			//将节点插入到下拉列表中
			$("select[name='comboName']").append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
});

//下拉选中的套餐name
function select(){
	var coName = $("select[name='comboName'] option:selected").text();
	$("#selectComboName").val(coName);
}
//下拉选中的基因公司name
function selectGeneCompany(){
	var geneCompany = $("select[name='geneCompany'] option:selected").text();
	$("#selectGeneCompanyName").val(geneCompany);
}


//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}

$("select[name='geneCompany']").change(function(){
	var geCompanyNameSave = $("select[name='geneCompany'] option:selected").text();
	$("input[name='geCompanyNameSave']").val(geCompanyNameSave);
});

</script>
<div class="tip"><span>添加价格套餐</span></div>
<div class="pageHeader" style="background:white">
		<form class="pageForm required-validate" id="addCombo" action="${path}/combo/erpComboPrice!addErpComboPrice.action" method="post">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>套餐名：</label> 
					<select id="comboName" name="comboName"  rel="iselect" onclick="select()" class="required">
						<option value=''>---请选择---</option>
		 			</select>
				</td>
				<td>
					<label>基因公司：</label> 
					<input type="hidden" name="geCompanyNameSave" value="" />
					<select id="geneCompany" name="geneCompany"  rel="iselect" onclick="selectGeneCompany()" class="required">
						<option value=''>---请选择---</option>
						<option value='402881b25373b2cd0153a223c8f00000'>南方基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00001'>北方基因公司</option>
						<option value='43C1E79788BDA311E050A8C0F1016F1D'>知康基因公司</option>
						<option value='7B9A6B56BA5E45E893D5C829B402E3FA'>金域基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00002'>吉思朗基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00003'>艾迪康基因公司</option>
		 			</select>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
					<label>价格：</label> 
					<input onkeyup="value=value.replace(/[^\d\.]/g,'')" type="text" name="price"value="" class="required"/>
					<input id="selectComboName" type="hidden" name="selectComboName" value=""/>
					<input id="selectGeneCompanyName" type="hidden" name="geneCompanyName" value=""/>
				</td>
				<td>
			      	<div style="margin-left:40px;" class="buttonActive"><div class="buttonContent"><button type="button" onclick="validateCallback('#addCombo', navTabAjaxDone),isClose()">添加</button></div></div>
				</td>
			</tr>
		</table>		
	</form>
</div>
