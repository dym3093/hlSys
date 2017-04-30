<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){/*必须放在预加载中*/
    py_ready();
});
function importMoreCodes(){
	var file = $("#affix").val();
	if(file==''){
		alert("请上传文件，再导入！");
		return;
	}
	$("input[name='filter_and_code_IN_S']").val("");
	py_show();
	$.ajaxFileUpload({
		url:'${path}/report/reportFileTask!importCodesByExcel.action',
        type:'post',
       	secureuri: false,
        fileElementId: 'affix',
        dataType: 'json',
		success:function(data,status){
			if(data.result=="success"){
				alert("导入成功！");
				$("input[name='filter_and_code_IN_S']").val(data.codes);
				$("#queryCodes").val(data.codes);
				py_hide();
			}else{
				alert("导入失败！");
				py_hide();
			}
		},
		error:function (data,status,e){
        	alert("服务发生异常，请稍后再试！");
			py_hide();
        }	
	});	
}

function toDownload(){
	var codes = $("#queryCodes").val();
	py_show();
	if(codes==''){
		alert("请先查询需下载的条形码！");
		return ;
	}
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/report/reportFileTask!downReportByCodes.action",
		data: {'codes': codes},
		success: function(data){
			if(data.result=="success"){
				if(data.downloadurl!=""){
					window.location.href=data.downloadurl;
					py_hide();
				}else{
					alertMsg.error("生成下载包失败，请稍后再试！");
					py_hide();
				}
				
			}else{
				alertMsg.error("下载失败！");
				py_hide();
			}	
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			py_hide();
		}
	});		
}
</script>
<!--蒙版 start-->
<div class="py_theMb" ><img class="py_loadpic"src="${path}/images/circle.jpg" alt="加载中……"/></div>
<!--蒙版 end-->
<div class="tip"><span>报告批量查询下载</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action!='${path}/report/reportFileTask!queryReportFile2Down.action?type=1'){this.action='${path}/report/reportFileTask!queryReportFile2Down.action?type=1';};return navTabSearch(this);" action="${path}/report/reportFileTask!queryReportFile2Down.action?type=1" method="post" rel="pagerForm">
		<div class="searchBar" style="height:40px;">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_IN_S" value="${codes}" readonly="readonly" />
                    <input type="hidden" id="queryCodes" value="${codes}"/>
				</td>
                <td>
					<input type="file" id="affix" name="affix"/>
                    <input type="button" onclick="importMoreCodes()" value="导入条码">
				</td>
                <td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit" >查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
                </td>
			</tr>
		</table>
	</div>
	</form>
</div>
<c:if test="${not empty surplus}">
	<div class="tip"><span>未查到的条码</span></div>
    <div style="height:20px;margin-top:10px;">
   		<c:forEach items="${surplus}" var="code" varStatus="status">
   			<span style="color:#F00;margin:0 4px">${code}</span>
    	</c:forEach>
   	</div>
</c:if>
<div class="pageContent">
	<div class="panelBar">
    	<ul class="toolBar">
			<web:exportExcelTag pagerFormId="pagerFindForm"
								pagerMethodName="exportCustomerByExl"
								actionAllUrl="org.hpin.reportdetail.web.ErpReportFileTaskAction"
								informationTableId="exportExcelTable"
								fileName="ErpReportFileTaskAction">
			</web:exportExcelTag>
            <li>
				<a class="add" href="javascript:void(0)" onclick="toDownload()"><span>下载报告</span></a>
			</li>
        </ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<div layouth="170" style="overflow:auto;height:102px;">
		<table class="list" width="100%" layoutH="170" id="exportExcelTable">
			<thead>
				<tr>
					<th nowrap="nowrap" width="40">序号</th>
					<th nowrap="nowrap" export="true" columnEnName="name" columnChName ="姓名">姓名</th>
					<th nowrap="nowrap" export="true" columnEnName="sex" columnChName ="性别">性别</th>
					<th nowrap="nowrap" export="true" columnEnName="age" columnChName ="年龄">年龄</th>
					<th nowrap="nowrap" export="true" columnEnName="code" columnChName ="条码">条码</th>
					<th nowrap="nowrap" export="true" columnEnName="matchstateView" columnChName ="状态">状态</th>
					<th nowrap="nowrap" export="true" columnEnName="createTime" columnChName ="创建日期">创建日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}"  var="erpCustomer" varStatus="status">
					<tr>
						<td nowrap="nowrap" align="center">${status.count}</td>
						<td nowrap="nowrap" align="center">${erpCustomer.name}</td>
						<td nowrap="nowrap" align="center">${erpCustomer.sex}</td>
						<td nowrap="nowrap" align="center">${erpCustomer.age}</td>
						<td nowrap="nowrap" align="center">${erpCustomer.code}</td>
						<td nowrap="nowrap" align="center">${erpCustomer.matchstateView}</td>
						<td nowrap="nowrap" align="center">${fn:substring(erpCustomer.createTime,0,17)}00</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
