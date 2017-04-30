<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

$("#clearText",$.pdialog.getCurrent()).click(function(){
	$("#pagerFindForm",$.pdialog.getCurrent()).find("input,textarea").each(function(){
		if($(this).attr("name")!="configId"){
			$(this).val("");
		}
	});
	$("#pagerFindForm",$.pdialog.getCurrent()).find("select").each(function(){
		$(this).val("");
	});
});

var saveScheduleInfo = function(){
	
	var infoType = $("#infoType").val();
	
	var scheduleInfo = $("#scheduleInfo").val();
	
	var scheduleTime = $("#scheduleTime").val();
	
	var keyWord = $("#keyWord").val();
	
	if(infoType==""||infoType==null){
		alertMsg.error("请选择执行时间!");
		return ;
	} 
	
	if(scheduleTime==""||scheduleTime==null){
		alertMsg.error("请选择执行时间!");
		return ;
	} 
	
	if(scheduleInfo==""||scheduleInfo==null){
		alertMsg.error("请填写信息!");
		return ;
	}
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/erpCustomer!saveScheduleInfo.action",
		data: {'infoType':infoType, 'scheduleTime':scheduleTime, 'keyWord':keyWord, 'scheduleInfo': scheduleInfo},
		success: function(data){
			var resp = eval("("+data+")");
			if(resp.result=="success"){
				alert(resp.msg);
			}else{
				alert(data.msg);
			}	
		},
		error :function(data){
			var resp = eval("("+data+")");
			alert(resp.msg);
		}
	});	
};


</script>
<div class="pageHeader">
	<input name="navTabId" type="hidden" value="${navTabId }" />
	<form id="pagerFindForm" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label style="height:60px; width:90px" for="infoType">信息类型：</label>
					<select id="infoType" name="infoType" style="height:20px;margin:5px;width:140px;" >
						<option value="batchNo">批次号</option>
						<option value=groupOrderNo>团单号</option>
						<option value="eventsNo">场次号</option>
						<option value="customerName">会员姓名</option>
						<option value="phone">电话号码</option>
					</select>
				</td>
				<td>
					<label style="height:60px; width:90px" for="scheduleTime">执行时间：</label>
				    <input type="text" id="scheduleTime" name="scheduleTime" class="date" dateFmt="HH:mm:ss" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3">
					<label style="height:60px; width:90px" for="keyWord">关键字：</label>
				    <input type="text" id="keyWord" name="keyWord" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<p class="nowrap"><label style="height:120px;width:90px" for="scheduleInfo">请输入信息：</label>
						<textarea rows="3" style="height:300;width:100%px;" id="scheduleInfo" name="scheduleInfo" ></textarea>
				</p>
				</td>
				<td><span style="color:red;">注：多条信息请以逗号隔开</span></td>
			</tr>			
			<tr>
				<td colspan="4"> <div class="subBar">
           			 <ul>
			       		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveScheduleInfo()">提交任务</button></div></div></li>
			      		<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">清除</button></div></div></li>
			      </ul>
			   </div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
