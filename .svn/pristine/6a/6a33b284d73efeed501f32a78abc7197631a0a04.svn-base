<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	textarea{
		resize:none;
	}
</style>
<script type="text/javascript" language="javascript">
	
	function insertBarCodeInfo(){
		var id = $.trim($("#id1").val());
		var code = $.trim($(":text:last").val());
		if(code==""){
			alertMsg.info("请输入条形码");
			return false;
		}
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id,"code":code},
			url: "${path}/children/erpOrderInfo!insertBarCodeInfo.action",
			success: function(data){
 				if(eval("("+data+")").count==0){
 					alertMsg.error("条形码更新失败");
 				}else{
 					alertMsg.info("条形码更新成功");
 				}
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function closeTab(){
		navTab.closeCurrentTab();
	}
	
</script>

<div class="tip"><span>条码录入</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpOrderInfo!insertBarCodeInfo.action'){this.action = '${path}/children/erpOrderInfo!insertBarCodeInfo.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpOrderInfo!insertBarCodeInfo.action" method="post"	>
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" id="id1" name="filter_and_id_LIKE_S" value="${id}" readonly="readonly"/>
				</td>
				<td>
					<label>订单号：</label> 
					<input type="text" name="filter_and_orderNo_LIKE_S"  value="${orderNo}"  readonly="readonly"/>
				</td>
				<td>
					<label>订单日期：</label>
					<input type="text" name="filter_and_createDate_LIKE_T"  value="${fn:substring(createDate,0,10)}"  readonly="readonly"/>
				</td>
				<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>姓名：</label>
					<input type="text" name="filter_and_name_LIKE_S"  value="${name}"  readonly="readonly"/>
				</td>
				<td>
					<label>性别：</label>
					<input type="text" name="filter_and_sex_LIKE_S"  value="${sex}"  readonly="readonly"/>
				</td>
				<td>
					<label>年龄：</label>
					<input type="text" name="filter_and_age_LIKE_S"  value="${age}"  readonly="readonly"/>
				</td>
				<td> </td>
			</tr>
			
			<tr>
				<td><label>检测项目：</label> 
					<input type="text" name="filter_and_setMealName_LIKE_S"  value="${setMealName}"  readonly="readonly"/>
				</td>
				<td><label>监护人姓名：</label> 
					<input type="text" name="filter_and_guardianName_LIKE_S"  value="${guardianName}"  readonly="readonly"/>
				</td>
				<td>
					<label>监护人手机：</label>
					<input type="text" name="filter_and_guardianPhone_LIKE_S"  value="${guardianPhone}"  readonly="readonly"/>
				</td>
         		<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>关系：</label>
					<input type="text" name="filter_and_relationship_LIKE_S"  value="${relationship}"  readonly="readonly"/>
				</td>
				<td>
					<label>订单状态：</label>
					<input type="text" name="filter_and_status_LIKE_S"  value="${status}"  readonly="readonly"/>
				</td>
				<td>
					<label>地址：</label>
					<input type="text" name="filter_and_reportAddress_LIKE_S"  value="${reportAddress}"  readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td colspan="2">
					<label>备注：</label>
					<textarea style="width: 495px;" readonly="readonly">${note}</textarea>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td> </td>
				<td> </td>
				<td> 
					<label>条形码：</label>
					<input type="text" name="filter_and_code_LIKE_S" value="${code}" required="required"/>
				</td>
				<td> </td>
			</tr>	
			
			<tr>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> 
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="insertBarCodeInfo()">保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeTab()">返回</button></div></div>
				</td>
			</tr>	
			
		</table>
	</div>
	</form>
</div>