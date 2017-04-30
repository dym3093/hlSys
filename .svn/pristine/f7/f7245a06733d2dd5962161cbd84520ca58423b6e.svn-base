<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

String id = (String)request.getAttribute("id");
%>

<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/jquery/json2.js" type="text/javascript"></script>

<script type="text/javascript">

$(document).ready(function(){
	
var id = "${id}";

//初始化日期控件
if($("input[name='settlementTime']").val()==""){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDay();
	var now = ""+year+"-"+month+"-"+day;
	$("input[name='settlementTime']").val(now);	
}

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
	
	$("#importCustomer").css("display", "none");			//隐藏导入div
});

//保存结算任务
function saveSettlementTaskJY(){
	$("#saveSettleTaskButton").attr("disabled",true);	//设置保存按钮不能点击
	setTimeout("buttonDisabled()",3000);	 			//3秒后可点击
	var taskNo = $("input[name='taskNo']").val();
	if(taskNo.length<1){
		alertMsg.info("请填写任务编号!");
		return;
	}
	var geCompanyId = $("select[name='geCompanyId'] option:selected").val();
	var geCompany = $("select[name='geCompanyId'] option:selected").text();
	var settlementTime = $("input[name='settlementTime']").val();
	if(geCompanyId.length<1){
		alertMsg.info("请填写基因!");
		return;
	}

	//var data = $("#addSMForm").serializeJson();
	//var id = $("#id").val();
	

	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/settlementManagement/erpSettlementTaskJY!saveSettlementTaskJY.action",
		//data: {'data': JSON.stringify(data),'id':id},
		data: {'taskNo':taskNo,'geCompany':geCompany,'geCompanyId':geCompanyId,'settlementTime':settlementTime},
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
}

//设置按钮可点击
function buttonDisabled(){
	$("#saveSettleTaskButton").attr("disabled",false);
}

/* //保存结算任务
function saveSettlementTaskJY(){
	var taskNo = $("input[name='taskNo']").val();
	if(taskNo.length<1){
		alertMsg.info("请填写任务编号!");
		return;
	}
	var geCompany = $("select[name='geCompanyId'] option:selected").val();
	if(geCompany.length<1){
		alertMsg.info("请填写基因!");
		return;
	}

	var data = $("#addSMForm").serializeJson();
	
	var id = $("#id").val();
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/settlementManagement/erpSettlementTaskJY!saveSettlementTaskJY.action",
		data: {'data': JSON.stringify(data),'id':id},
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
		dataType: 'json',
		data: {'id':id},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("成功！");
				navTab.closeCurrentTab();
				//$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else if(data.result=="error"){
				alertMsg.error("失败！");
				return navTabSearch(this);
			}	
		},
		error: function(resp){
			alertMsg.error('服务发生异常，请稍后再');
		}
	}); 
	
}

//打开导入页面
function showImportDialog(settlementTaskId,settleJYNo){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	/* var geneName = $("input[name='geneCompanyName']").val();
	var geneId = $("input[name='geneCompanyId']").val(); */
	//打开导入窗口
	$.pdialog.open("${path}/settlementManagement/erpSettlementTaskJY!toImportCustomerPage.action?id="+settlementTaskId+"&navTabId="+navTabId, "importCustomer", "导入基因公司数据",
		{width:600,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );
	/*
	navTab.openTab("importCustomer", "${path}/settlementManagement/erpSettlementTaskJY!toImportCustomerPage.action", {title:"导入会员信息", fresh:true, data:{id:settlementTaskId}});
	*/
}

//下拉选中的基因公司name
function selectGeneCompany(){
	var geneCompany = $("select[name='geCompanyId'] option:selected").text();
	$("#selectGeneCompanyName").val(geneCompany);
}

/* //将选中的基因公司名字，id放入隐藏input
$("select[name='geCompanyId']").change(function(){
	
	var name = $("select[name='geCompanyId'] option:selected").val();
	var id = $("select[name='geCompanyId'] option:selected").text();
	
	$("input[name='geneCompanyName']").val(name);
	$("input[name='geneCompanyId']").val(id);
}); */

function uploadFile(){
	var settlementTaskId = $("input[name='settlementTaskId']").val();
	var id = $("input[name='id']").val();
	$.ajaxFileUpload({
		url:'${path}/settlementManagement/erpSettlementTaskJY!uploadJYCheckInfo.action',
		type:'post',
		data:{"settlementTaskId":settlementTaskId},
		secureuri: false,
		fileElementId: 'affix',
		dataType: 'json',
		success:function(data,status){
			if(data.statusCode=="200"){
				alert(data.message);
				/* $.pdialog.closeCurrent();
				$("input[name='setMealNum']").val(data.setMealNum);
				$("input[name='totalPersonNum']").val(data.totalPersonNum);
				$("input[name='successNum']").val(data.successNum);
				$("input[name='abnormalNum']").val(data.abnormalNum); */
				$("#importCustomer").css("display", "none");
				
				
			}else if(data.statusCode=="300"){
				alert(data.message);
			}
			
		},
		error:function (data,status,e){
			alert("服务发生异常，请稍后再试！");
			py_hide();
			clearInterval(iCount); //当有返回值时就关闭循环执行方法;
		}
	});
 }
 
	$(function() {

		$("#importButton").click(function() {
			$("#importCustomer").css("display", "block");
		});
		
		/* $("#sc").click(function(){
			$("#importCustomer").css("display", "none");
		}); */
	});
	
</script>
<div class="pageHeader" style="background:white">
	<form id = "addSMForm" >
	<div class="searchBar">
			<input type="hidden" id="id" name="id" value="${id}"/>
			<input type="hidden" name="status" value="${status}"/>
			<input type="hidden" name="version" value="${version}"/>
			<!--<input type="hidden" name="createTime" value="${createTime}"/>-->
			<input type="hidden" name="createUserId" value="${createUserId}"/>
			<input type="hidden" name="createUser" value="${createUser}"/>
			<!--<input type="hidden" name="updateTime" value="${updateTime}"/>-->
			<input type="hidden" name="updateUserId" value="${updateUserId}"/>
			<input type="hidden" name="updateUser" value="${updateUser}"/>
			
			<!-- <input type="hidden" name="geneCompanyName" value=""/>
			<input type="hidden" name="geneCompanyId" value=""/> -->
			
		<table class="pageFormContent">
			<tr>				
				<td><label>任务编号：</label> 
					<input type="text" name="taskNo" value="${settleJYNo}" class="required" readonly="readonly"/>
				</td>
				<%-- <td><label>任务名称：</label> 
					<input type="text" name="taskName" value="${taskName}" class="required">
				</td>				
			</tr>
			<tr> --%>
				<td>
					<label>基因公司：</label> 
				<%-- 	<input type="text" name="geCompany" value="${geCompany}" class="required"/>
					<input type="hidden" name="geCompanyId" value="${geCompanyId}"/> --%>
					<select id="geCompany" name="geCompanyId" rel="iselect" onclick="selectGeneCompany()">
						<option value=''>---请选择---</option>
						<option value='402881b25373b2cd0153a223c8f00000'>南方基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00001'>北方基因公司</option>
						<option value='43C1E79788BDA311E050A8C0F1016F1D'>知康基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00002'>吉思朗基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00003'>金域基因公司</option>
						<option value='402881b25373b2cd0153a223c8f00004'>艾迪康基因公司</option>
		 			</select>
		 			<input id="selectGeneCompanyName" type="hidden" name="geCompany" value=""/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<label>创建结算时间：</label> 
					<input type="text" name="settlementTime" dateFmt="yyyy-MM-dd HH:mm:ss" value="${createSettleTime}"  class="date" readonly="readonly"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>
					<!--<div class="buttonActive"><div class="buttonContent"><%-- <button type="button" onclick="showImportDialog('${id}','${settleJYNo}')">导入会员信息</button> --%>
					 <button type="button" id="importButton">导入会员信息</button></div></div> -->
					<!-- <div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveSettlementTaskJY()">保存</button></div></div> -->
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="saveSettleTaskButton" onclick="saveSettlementTaskJY()">保存</button></div></div>
			</tr>
		</table>
		
	</div>
	</form>
</div>
<%-- <div class="pageFormContent" id="importCustomer" style="margin:10px auto auto 20px"> 
    <form method="post" action="${path}/settlementManagement/erpSettlementTaskJY!uploadJYCheckInfo.action" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data">
      <!-- 打印任务ID -->
      <input name="settlementTaskId" type="hidden" value="${id}"/>
      <input name="navTabId" type="hidden" value="${navTabId}"/>
      <!--<input name="customer.eventsNo" type="hidden" value="${events.eventsNo}"/>
		<div class="tip"><span><b>客户信息导入</b>&nbsp;&nbsp;场次：${events.eventsNo}</span></div>-->
      <div>
        <span style="float:left;line-height:25px;">基因核对数据表：</span>
          <input style="float:left" type="file" class="required valid" id="affix" name="affix">
            <div class="buttonActive" style="margin-left:10px;">
              <div class="buttonContent">
                <button type="button" id="sc" onclick="uploadFile()">上传</button>
              </div>
            </div>
          <span style="line-height:30px;padding-left:10px;color:red">备注：请导入小于10000条数据的Excel</span>
      </div>
    </form>
  </div> --%>
