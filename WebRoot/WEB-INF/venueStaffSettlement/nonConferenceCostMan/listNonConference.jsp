<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">

	function getNavTabId(){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		return navTabId;
	}
	
	function getCheckedbox() {
		var checkbox = $("input:checkbox[name='ids']:checked",navTab.getCurrentPanel());
		return checkbox;
	}
	
	function addNonConference() {	//添加
		var listNavTabId = getNavTabId();
		navTab.openTab("addNonConference", "${path}/venueStaffSett/nonConference!addNonConference.action", 
				{ title:"添加", fresh:true, data:{"listNavTabId":listNavTabId}});
	}
	
	function updateNonConference() {	//修改会议
		var checkbox = getCheckedbox();
		var listNavTabId = getNavTabId();
		if ($(checkbox).length != 1) {
			alertMsg.warn("请选择一条要修改的会议费用");
			return;
		}
		var nonConferenceId = $(checkbox).val();
		navTab.openTab("addNonConference", "${path}/venueStaffSett/nonConference!updateNonConference.action", 
				{ title:"修改", fresh:true, data:{"nonConferenceId": nonConferenceId,"listNavTabId":listNavTabId}});
	}
	
	
	function deleteNonConference() {//删除会议
		var checkbox = getCheckedbox();
		if ($(checkbox).length != 1) {
			alertMsg.warn("请选择一条要删除的会议费用");
			return;
		}
		var nonConferenceId = $(checkbox).val();
		var form = $("#pagerFindForm",navTab.getCurrentPanel());
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"nonConferenceId":nonConferenceId},
			url: "${path}/venueStaffSett/nonConference!deleteNonConference.action",
			success: function(data){
				var result = eval("("+data+")");
				if(result.count==1){
					alertMsg.correct("删除成功");
					return navTabSearch(form);
				}else{
					alertMsg.error("删除失败");
				}
			},
			error :function(){
				alertMsg.error("服务发生异常，请稍后再试！");
				return;
			}
		});
	}

	function showNonConferenceCost(){	//添加
		
		navTab.openTab("showNonConferenceCost", "${path}/venueStaffSett/nonConference!showNonConferenceCost.action", 
				{ title:"添加",fresh:true, data:{}});
	}
	
	function exportNonConferenceCost() {	//导出非会场费用
		var projectCode = $.trim($("#projectCode" ,navTab.getCurrentPanel()).val());
		var projectUser = $.trim($("#proUser" ,navTab.getCurrentPanel()).val());
		var projectType = $.trim($("select[name = 'nonConference.projectType']" ,navTab.getCurrentPanel()).val());
		var month = $.trim($("#month" ,navTab.getCurrentPanel()).val());
		var OASerial = $.trim($("#OASerial" ,navTab.getCurrentPanel()).val());
		var jsonData = "[{\"projectCode\":\"" + projectCode + "\"," + "\"projectUser\":\"" + projectUser + "\",\"projectType\":\"" + projectType 
			+ "\",\"month\":\"" + month + "\",\"OASerial\":\"" + OASerial + "\"}]";
		if (projectCode == "" && projectUser == "" && projectType == "" && month == "" && OASerial == "") {
 			if (confirm("您没有输入任务条件来进行导出操作,确定导出吗? 这将花费较长的时间.")) {
 				openExcel(jsonData);
 			}
 			return;
		}
		openExcel(jsonData);
		
	}
	
	function openExcel(jsonData) {
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{
				"jsonData" :jsonData,
			},
			url: "${path}/venueStaffSett/nonConference!exportNonConferenceCost.action",
			success: function(data){
				window.open(eval("("+data+")").excelPath, "_blank");
		 	},
			error :function(){
				alertMsg.error("服务发生异常，请稍后再试！");
			}
		});
	}
	
	function clearInput(){
		$(':input','#pagerFindForm',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('selected');  
	}
</script>
<div id="mc" class="py_theMb">
	<div class="py_bakpic"><!--图片--></div>
</div>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/nonConference!listNonConference.action'){this.action = '${path}/venueStaffSett/nonConference!listNonConference.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/nonConference!listNonConference.action" method="post"  rel="pagerForm" >
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>项目编码：</label> 
					<input id="projectCode" type="text" name="nonConference.projectCode" value="${nonConference.projectCode}"/>
				</td>
				<td>
					<label>项目负责人：</label> 
					<input id="proUser" type="text" name="nonConference.projectUser" value="${nonConference.projectUser}"/>
				</td>
				<td>
					<label>项目类型：</label>
					<select id="projectType" name="nonConference.projectType" class="select">
						<option value="" >请选择</option>
							<c:forEach items="${proTypes }" var="item">
								<option value="${item.id}" ${item.id  == nonConference.projectType ? 'selected' : '' }>${item.projectTypeName}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>月份：</label> 
					<input id="month" type="text" name="nonConference.month" value="${nonConference.month}"/>
				</td>
				<td>
					<label>OA流水号：</label> 
					<input id="OASerial" type="text" name="nonConference.OASerial" value="${nonConference.OASerial}"/>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
				</td>
				<td></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<web:security tag="VFM_DetailImport">
				<li style="margin-left:5px;"><a class="add" onclick="addNonConference()" style="cursor: pointer;"><span>添加</span></a></li>
			</web:security>
				<li style="margin-left:5px;"><a class="add" onclick="updateNonConference()" style="cursor: pointer;"><span>修改</span></a></li>
				<li style="margin-left:5px;"><a class="edit" onclick="exportNonConferenceCost()" style="cursor: pointer;"><span>导出非会场费用</span></a></li>
			<web:security tag="VFM_FeeImport">
				<li style="margin-left:5px;"><a class="icon" onclick="deleteNonConference()" style="cursor: pointer;"><span>删除</span></a></li> 
			</web:security>
		</ul>
		<jsp:include page="/common/pagination.jsp"/>
	</div>
	<table class="list" width="100%" layoutH="180" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "projectCode" columnChName = "会议号" >项目编码</th>
				<th  export = "true" columnEnName = "projectUser" columnChName = "项目负责人" >项目负责人</th>
				<th  export = "true" columnEnName = "projectName" columnChName = "项目名称" >项目名称</th>
				<th  export = "true" columnEnName = "projectType" columnChName = "项目类型" >项目类型</th>
				<th  export = "true" columnEnName = "startTime" columnChName = "开始时间" >开始时间</th>
				<th  export = "true" columnEnName = "endTime" columnChName = "结束时间" >结束时间</th>
				<th  export = "true" columnEnName = "month" columnChName = "月份" >月份</th>
				<th  export = "true" columnEnName = "OASerial" columnChName = "OA流水号" >OA流水号</th>
				<th  export = "true" columnEnName = "fees" columnChName = "产生费用" >产生费用</th>
				<th  export = "true" columnEnName = "note" columnChName = "备注" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="nonConference" varStatus="status">
				<tr target="sid_user" rel="${nonConference.id }">
					<td align="center">
						<input type="checkbox" name="ids" value="${nonConference.id}">${status.count}
					</td>
					<td align="center">${nonConference.projectCode}</td>	
					<td align="center">${nonConference.projectUser}</td>	
					<td align="center">${nonConference.projectName}</td>	
					<td align="center">${nonConference.projectTypeName}</td>	
					<td align="center">${fn:substring(nonConference.startTime,0,10)}</td>	
					<td align="center">${fn:substring(nonConference.endTime,0,10)}</td>	
					<td align="center">${nonConference.month}</td>	
					<td align="center">${nonConference.OASerial}</td>	
					<td align="center">
						<a title="费用信息" target="navTab" width="1000" 
						 href="${path}/venueStaffSett/nonConference!showNonConferenceCost.action?nonConferenceId=${nonConference.id}">${nonConference.fees}</a>
					</td>	
					<td align="center">${nonConference.note}</td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 