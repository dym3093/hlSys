<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

String id = (String)request.getAttribute("id");
%>

<script src="${path}/jquery/json2.js" type="text/javascript"></script>

<script type="text/javascript">

$(document).ready(function(){/*必须放在预加载中*/
    py_ready();
});

$(document).ready(function(){
	
var id = "${id}";

$.fn.serializeJson=function(){  
		var serializeObj={};  
		var array=this.serializeArray();  
		$(array).each(function(){  
			if(serializeObj[this.name]){  
				if($.isArray(serializeObj[this.name])){  
					serializeObj[this.name].push(this.value);  
				}else{  
					serializeObj[this.name]=[serializeObj[this.name],this.value];  
				}  
			}else{  
				serializeObj[this.name]=this.value;   
			}  
		});  
		return serializeObj;  
	}; 
});

//保存结算任务
/* function saveSettlementTaskJY(){
	var taskNo = $("input[name='taskNo']").val();
	if(taskNo.length<1){
		alertMsg.info("请填写任务编号!");
		return;
	}
	var taskName = $("input[name='taskName']").val();
	if(taskName.length<1){
		alertMsg.info("请填写任务名称!");
		return;
	}
	var geCompany = $("input[name='geCompany']").val();
	if(geCompany.length<1){
		alertMsg.info("请填写基因!");
		return;
	}

	var data = $("#addSMForm").serializeJson();
	
//	alert(JSON.stringify(data));
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/settlementManagement/erpPrintCompanySettleTask!addPrintCompanySettleTask.action",
		data: {'data': JSON.stringify(data)},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("保存成功！");
				navTab.closeCurrentTab();
				//$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alertMsg.error("保存失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
} */

//取消保存，删除之前导入的数据
function cancelSettlementTaskJY(id){
	//ajax改为同步	
	$.ajax({
		url: '../settlementManagement/erpSettlementTaskJY!deleteErpSettlementCustomerJYBatch.action',
		method: 'post',
		async: false,
		cache: false,
		data: {'id':id},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("成功！");
				navTab.closeCurrentTab();
				//$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alertMsg.error("失败！");
			}	
		},
		error: function(resp){
			alertMsg.error('服务发生异常，请稍后再');
		}
	}); 
	
}

//打开导入页面
function showImportDialog(settlementTaskId){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	//打开导入窗口
	$.pdialog.open("${path}/settlementManagement/erpPrintCompanySettleTask!toImportCustomerPage.action?id="+settlementTaskId+"&navTabId="+navTabId, "importCustomer", "导入打印公司数据",
		{width:600,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );
	/*
	navTab.openTab("importCustomer", "${path}/settlementManagement/erpSettlementTaskJY!toImportCustomerPage.action", {title:"导入会员信息", fresh:true, data:{id:settlementTaskId}});
	*/
}



</script>

<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->
<div class="pageHeader" style="background:white">
	<form id = "addPCSTForm" class="pageForm required-validate" action="${path}/settlementManagement/erpPrintCompanySettleTask!addPrintCompanySettleTask.action" method="post">
	<div class="searchBar">
			<input type="hidden" id="id" name="id" value="${id}"/>
			<input type="hidden" name="navTabId" value="${navTabId}"/>
			<input type="hidden" name="status" value="${status}"/>
			<input type="hidden" name="version" value="${version}"/>
			<!--<input type="hidden" name="createTime" value="${createTime}"/>-->
			<input type="hidden" name="createUserId" value="${createUserId}"/>
			<input type="hidden" name="createUser" value="${createUser}"/>
			<!--<input type="hidden" name="updateTime" value="${updateTime}"/>-->
			<input type="hidden" name="updateUserId" value="${updateUserId}"/>
			<input type="hidden" name="updateUser" value="${updateUser}"/>
			
		<table class="pageFormContent">
			<tr>				
				<td><label>任务编号：</label> 
					<input type="text" name="taskNo" value="${taskNo}" class="required"/>
				</td>
				<td><label>任务名称：</label> 
					<input type="text" name="taskName" value="${taskName}" class="required">
				</td>				
			</tr>
			<tr>
				<td>
					<label>打印公司：</label> 
					<input type="text" name="printCompany" value="${printCompany}" class="required"/>
					<input type="hidden" name="printCompanyId" value="${printCompanyId}"/>
				</td>
				<td align="center">
					<label>结算时间：</label> 
					<input type="text" name="settlementTime" dateFmt="yyyy-MM-dd" value="${settlementTime}" class="date" readonly="readonly"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td></td>
         		
			</tr>
			<tr>
				<td>
					<label>套餐数量：</label> 
					<input type="text" name="setMealNum" value="${setMealNum}" readonly="readonly"/>
				</td>
				<td>
					<label>总人数：</label> 
					<input type="text" name="totalPersonNum" value="${totalPersonNum}" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>可结算数量：</label> 
					<input type="text" name="successNum" value="${successNum}" readonly="readonly"/>
				</td>
				<td>
					<label>异常数量：</label> 
					<input type="text" name="abnormalNum" value="${abnormalNum}" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			<%-- <tr>
				<td>
					<label>单据总额(元)：</label> 
					<input type="text" name="totalAmount" value="${totalAmount}" readonly="readonly"/>
				</td>
				<td>
					<label>路径：</label> 
					<input type="text" name="excelPath" value="${excelPath}" readonly="readonly"/>
				</td>
			</tr> --%>
			<tr>
				<td></td><td></td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="showImportDialog('${id}')">导入会员信息</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="validateCallback('#addPCSTForm', navTabAjaxDone)">保存</button></div></div>
				</td>
			</tr>
		</table>
		
	</div>
	</form>
</div>

