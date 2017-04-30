<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">
//页面加载选中单选框
$(document).ready(function(){
	var wereSex = $("#wereSex").val();
	if(wereSex=="男"){
		$("input[name='wereSex']", navTab.getCurrentPanel()).get(0).checked = true;
	}else if(wereSex=="女"){
		$("input[name='wereSex']", navTab.getCurrentPanel()).get(1).checked = true;
	}else{//为空，默认选中第一个
		$("input[name='wereSex']", navTab.getCurrentPanel()).get(0).checked = true;
	}
	
});

//提交选择单选
$("input[name='wereSex']", navTab.getCurrentPanel()).change(function(){
	var sex = $("input[name='wereSex']:checked", navTab.getCurrentPanel()).val();
	$("#wereSex").val(sex);
});

function colseWindow(){
	navTab.closeCurrentTab();
}

</script>
<html>
<head>
	
</head>
<body>
<!-- <div class="tip"><span>付款</span></div> -->
<div class="tip"><span>预导入客户信息</span></div>
<div class="pageHeader" style="margin-left: 180px;">
	 <%-- <c:if test="${prepCustomer.code!=''}">		<!-- 有条码的，创建场次批次等信息 --> --%>
	 	<form class="pageForm required-validate" 
		action="${path}/warehouse/prepCustomer!addPreCustomerEventsAndBtachNo.action"  onsubmit="return validateCallback(this, navTabAjaxDone);"
		 class="pageForm" method="post" rel="pagerForm" id="pagerFindForm">
	 <%-- </c:if> --%>
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>被检人条形码：</label> 
					<input type="hidden" name="prepCustomer.id" value="${prepCustomer.id}"/>
					<input type="hidden" name="prepCustomer.eventsNo" value="${prepCustomer.eventsNo}"/>
					<input type="hidden" name="navTabId" value="${navTabId}"/>
					<c:if test="${empty prepCustomer.code}">
						<input type="text" name="prepCustomer.code" value="${prepCustomer.code}" class="required"/>
					</c:if>
					<c:if test="${not empty prepCustomer.code}"> <!-- 预导入客户：已有code，不可修改 -->
						<input type="text" name="prepCustomer.code" value="${prepCustomer.code}" readonly="readonly" class="required"/>
					</c:if>
				</td>  
				<td>
					<label>被检人姓名：</label> 
					<input type="text" name="prepCustomer.wereName"  readonly="readonly" value="${prepCustomer.wereName}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>被检人性别：</label> 
					<input type="hidden" id="wereSex" name="prepCustomer.wereSex" value="${prepCustomer.wereSex}">
					<input type="radio" name="wereSex" value="男">男</input>
					<input type="radio" name="wereSex" value="女" >女</input>
				</td>
				<td>
					<label>被检人证件号码：</label> 
					<input type="text" name="prepCustomer.wereIdcard" readonly="readonly" value="${prepCustomer.wereIdcard}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>被检人身高：</label> 
					<input type="text" name="prepCustomer.wereHeight" value="${prepCustomer.wereHeight}"/>
				</td>
				<td><label>被检人电话：</label> 
					<input type="text" name="prepCustomer.werePhone" readonly="readonly" value="${prepCustomer.werePhone}" />
				</td>
			</tr>
			<tr>
				<td>
					<label>被检人体重：</label> 
					<input type="text" name="prepCustomer.wereWeight" value="${prepCustomer.wereWeight}" />
				</td>
				<td>
					<label>保险公司套餐名称：</label> 
					<input type="text" name="prepCustomer.checkCobmo" readonly="readonly" value="${prepCustomer.checkCobmo}" />
				</td>
			</tr>
			<tr>
				<td>
					<label>被检人年龄：</label> 
					<input type="text" name="prepCustomer.wereAge" value="${prepCustomer.wereAge}" />
				</td>
				<td>
					<label>样本盒寄送地址：</label> 
					<input type="text" name="prepCustomer.checkboxEmilAddr" value="${prepCustomer.checkboxEmilAddr}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>既往疾病史：</label> 
					<input type="text" name="prepCustomer.customerHistory" value="${prepCustomer.customerHistory}" />
				</td>
				<td>
					<label>报告接收人姓名：</label> 
					<input type="text" name="prepCustomer.reportReceiveName" value="${prepCustomer.reportReceiveName}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>家族疾病史：</label> 
					<input type="text" name="prepCustomer.familyHistory" value="${prepCustomer.familyHistory}" />
				</td>
				<td>
					<label>报告接收人电话：</label> 
					<input type="text" name="prepCustomer.phone" value="${prepCustomer.phone}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>采样日期：</label> 
					<input type="text" name="simpleingDate"  value="${simpleingDate }" readonly="readonly"/>
				</td>
				<td>
					<label>报告寄送地址：</label> 
					<input type="text" name="prepCustomer.reportSendAddr" value="${prepCustomer.reportSendAddr}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>备注：</label> 
					<input type="text" name="prepCustomer.remark" value="${prepCustomer.remark}" />
				</td>
			</tr>
			<tr>
				<td>
					<div class="buttonActive" style="margin-left: 300px;"><div class="buttonContent"><button type="submit" id="formSumbitButton" >保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent" id="formSumbitOnce" ><button type="button" onClick="colseWindow()">取消</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>

</body>
</html>