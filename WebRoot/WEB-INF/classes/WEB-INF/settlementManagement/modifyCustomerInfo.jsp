<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	
});

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}
</script>
<div class="tip"><span>更正客户信息</span></div>
<div class="pageHeader" style="background:white">
	<form class="pageForm required-validate" id="modifyCustomer" action="${path}/settlementManagement/erpSettlementTaskJY!modifyCustomerInfo.action" method="post">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>条码：</label> 
					<input type="text" name="code" value="${erpSettlementCustomerJY.code}" readonly="readonly"/>
					<input type="hidden" name="setCustomerId" value="${erpSettlementCustomerJY.id}"/>
				</td>
				<td>
					<label>状态：</label> 
					<input type="text" name="status" value="${erpSettlementCustomerJY.status}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>性别：</label> 
					<input type="text" name="sex" value="${erpSettlementCustomerJY.sex}" readonly="readonly"/>
				</td>
				<td>
					<label>年龄：</label> 
					<input type="text" name="age" value="${erpSettlementCustomerJY.age}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>联系方式：</label> 
					<input type="text" name="contact" value="${erpSettlementCustomerJY.contact}" readonly="readonly" />
				</td>
				<td>
					<label>支公司：</label> 
					<input type="text" name="contact" value="" readonly="readonly"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>检查项目：</label> 
					<input type="text" name="items" value="${erpSettlementCustomerJY.items}" readonly="readonly"/>
				</td>
				<td>
					<label>送检单位：</label> 
					<input type="text" name="censorship_company" value="${erpSettlementCustomerJY.censorship_company}" readonly="readonly"/>
				</td>
			</tr>
			
			<tr>
				
				<td>
					<label>样本类型：</label> 
					<input type="text" name="sample_type" value="${erpSettlementCustomerJY.sample_type}" readonly="readonly" />
				</td>
				<td>
					<label>送检医生：</label> 
					<input type="text" name="censorship_doctor" value="${erpSettlementCustomerJY.censorship_doctor}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>提交者：</label> 
					<input type="text" name="submitter" value="${erpSettlementCustomerJY.submitter}" readonly="readonly" />
				</td>
				<td>
					<label>送检日期：</label> 
					<input type="text" name="censorship_time" value="${erpSettlementCustomerJY.censorship_time}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>接收日期：</label> 
					<input type="text" name="receive_time" value="${erpSettlementCustomerJY.receive_time}" readonly="readonly"/>
				</td>
				<td>
					<label>导入状态：</label> 
					<input type="text" name="import_status" value="${erpSettlementCustomerJY.import_status}" readonly="readonly" />
				</td>
				
			</tr>
			<tr>
				
				<td><label>套餐价格：</label> 
					<input type="text" name="setmeal_price" value="${erpSettlementCustomerJY.setmeal_price}" readonly="readonly"/>
				</td>
				<td>
					<label>姓名：</label> 
					<input type="text" name="name" value="${erpSettlementCustomerJY.name}" class="required"/>
				</td>
			</tr>
			<tr>
				<td><label>套餐名字：</label> 
					<input type="text" name="setmealName" value="${erpSettlementCustomerJY.setmeal_name}" class="required"/></td>
				<td></td>
         		<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="validateCallback('#modifyCustomer', navTabAjaxDone),isClose()">修改</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
