<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
<script type="text/javascript" language="javascript">

function submitForm() {
	$(".pageForm", navTab.getCurrentPanel()).submit();

}

$(document).ready(function(){
	$("input[name='eDetail_ids']", navTab.getCurrentPanel()).bind("change",function (){
		var reportIds = $("#reportIds").val();
		if($(this).is(':checked')){
			if(reportIds.indexOf($(this).val())<0){
				if(reportIds!=""){
					reportIds+=","+$(this).val();
				}else{
					reportIds = $(this).val();
				}
			}
		}else{
			if(reportIds.indexOf(","+$(this).val())>-1){
				reportIds = reportIds.replace((","+$(this).val()),"");
			}else if(reportIds.indexOf($(this).val())>-1){
				reportIds = reportIds.replace($(this).val(),"");
			}
		}
		$("input[name='reportIds']").val(reportIds);
	})
	//切换查询条件渲染页面之后 将已选择的展示出来
	rederCheckbox();
	
});
function rederCheckbox(){
	var reportIds = '${reportIds }' ;//$("#reportIds").val();
	var arrIds = reportIds.split(",");
	for(var i in arrIds){
		$("input[name='eDetail_ids'][value='"+arrIds[i]+"']").attr("checked","checked");
	}
}
function cancelExpress(reportId){
	if (confirm("确认撤销?")) {
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/reportDelivery!cancelExpress.action",
			data:{"reportId":reportId},
			success: function(data){
				eval("data="+data);
				if(data.status == '200'){
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else if(data.status == '300'){
					alertMsg.error(data.message)
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
}
function changeExpress(reportId){
	$.pdialog.open("${path}/reportdetail/reportDelivery!toChangeExpress.action?reportId="+reportId, 
		"changeExpress", "快递信息修改",{width:750,height:320,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}
function showHis(reportId){
	$.pdialog.open("${path}/reportdetail/reportDelivery!toShowHis.action?reportId="+reportId, 
		"showHis", "查询变更历史",{width:720,height:520,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}
function selectAll(allBox){
	var checkBoxs = $("input[name='eDetail_ids']");
	for(var i=0; i < checkBoxs.length;i++){
		if($(allBox).is(':checked')){
			$(checkBoxs[i]).attr("checked","checked");
		}else{
			$(checkBoxs[i]).attr("checked",false);
		}
		$(checkBoxs[i]).trigger("change");
	}
	
}
//显示选中
function showChooseExpress(){
	var reportIds = $("#reportIds").val();
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/reportDelivery!showChooseExpress.action",
		data:{"reportIds":reportIds},
		success: function(data){
			eval("data="+data);
			if(data.status == '200'){
				var showMsg = data.data;
				var str = "已选中：\n"
				str+="寄送日期，批次号，快递费用，快递重量，报告数 \n";
				for(var i=0; i < showMsg.length;i++){
					var date = new Date(showMsg[i].expressDate.time);
					str+=date.format('yyyy-MM-dd hh:mm:ss')+"，";
					str+=showMsg[i].batchNo+"，";
					str+=showMsg[i].totalCost+"，";
					str+=showMsg[i].expressWeight+"，";
					str+=showMsg[i].alreadyCount+"\n";
				}
				alert(str);
			}else if(data.status == '300'){
				alertMsg.error(data.message)
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}

function toSendEmail(){
	var reportIds = $("#reportIds").val();
	if(reportIds==""||reportIds==undefined){
		alertMsg.error("请至少选择一条数据");
		return;
	}
	$.pdialog.open("${path}/reportdetail/reportDelivery!findBaseEmpInfo.action?reportIds="+reportIds, 
		"expressEntry", "发送邮件",{width:750,height:360,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
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

<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/reportdetail/reportDelivery!expressDetail.action'){this.action = '${path}/reportdetail/reportDelivery!expressDetail.action' ;} ;return navTabSearch(this);"
			action="${path}/reportdetail/reportDelivery!expressDetail.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td><label>起始日期：</label> <input type="text" name="searchQuery.startTime" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${searchQuery.startTime}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="searchQuery.endTime" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${searchQuery.endTime}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
				</tr>
				<tr>
					<input type="hidden" id="reportIds" name="reportIds" value="${reportIds }"/>
					<td style="white-space:nowrap"><label>批次号：</label> 
						<input type="text" id="queryTypeId" name="searchQuery.batchNo" value="${searchQuery.batchNo}" />
					</td>
					<td>
					</td>
					<td>
						<input type="radio" name="searchQuery.queryType" value="1" checked="checked" />精准查询
						<input type="radio" name="searchQuery.queryType" value="2" />模糊查询 
					</td>
					<td>
						<div class="subBar">
							<ul style="float: left">
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
								<li><a id="clearText" href="javascript:;" class="button"><span>重置</span></a></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
	</form>
	</div>
</div>
<div class="pageContent">
	<c:if test="${currentUser.userType!='查询用户'}">
		<div class="formBar" >
		 <ul>
		       <li>
		          <div class="buttonActive">
		            <div class="buttonContent">
		              <button onclick="toSendEmail()">发送邮件</button>
		            </div>
		          </div>
		        </li>
		         
		      </ul>
		<p></p>
		</div>
	</c:if>
	<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				 <li>
		          	 <a class="icon" href="javascript:;"
						onclick="showChooseExpress()"><span>显示选中</span></a></li>
		          </li>
			</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="50" nowrap="true">全选<input type="checkbox" class="checkboxCtrl" onclick="selectAll(this)"/></th>
				<th export="true" columnEnName="expressDate" columnChName="寄送日期">寄送日期</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="totalCost" columnChName="快递费用">快递费用</th>
				<th export="true" columnEnName="expressWeight" columnChName="快递重量">快递重量</th>
				<th export="true" columnEnName="alreadyCount" columnChName="报告数">报告数</th>
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpReport" varStatus="status">
				<tr target="sid_user" rel="${erpReport.reportId }">
					<td align="center">
						<input type="checkbox" name="eDetail_ids" value="${erpReport.reportId }">
						${ status.count }
					</td>
					<td align="center">${fn:substring(erpReport.expressDate,0,19)}</td>
					<td align="center">${erpReport.batchNo}</td>

					<td align="center">${erpReport.totalCost}</td>
					<td align="center">${erpReport.expressWeight}</td>
					<td align="center">${erpReport.alreadyCount}</td>
					
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">
							<div class="panelBar" style="width:250px;">
								<ul class="toolBar" >
									<li><div class="buttonActive"><div class="buttonContent"><button id="changeBn" type="button" onclick="changeExpress('${erpReport.reportId }')">变更</button></div></div></li>
									<li></li>
									<li><div class="buttonActive"><div class="buttonContent"><button id="cancelBn" type="button" onclick="cancelExpress('${erpReport.reportId }')">撤销</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button id="showHisBn" type="button" onclick="showHis('${erpReport.reportId }')">查询变更历史</button></div></div></li>
                        		</ul>
							</div>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
