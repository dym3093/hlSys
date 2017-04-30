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

<script type="text/javascript" language="javascript">

	function submitForm() {
		$(".pageForm", navTab.getCurrentPanel()).submit();
	
	}
	function dj(id,projectTypeCode,projectCode,proId){
		$.ajax({
			type: "post",
			cache :false,
			data:{"crid":id,"projectCode":projectCode,"projectTypeCode":projectTypeCode},
			url: "${path}/resource/customerRelationShip!checkShowCombo.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					$.pdialog.open("../resource/customerRelationShip!printKeyWord.action?crid="+id+"&projectTypeCode="+projectTypeCode+"&projectCode="+projectCode+"&proId="+proId, 
						"printKeyWord", "生成支公司二维码",{width:500,height:100,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
				}else if(data.status=='300'){
					alertMsg.error(data.message);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}

</script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path }/resource/customerRelationShip!listCompanyQRCode.action'){this.action = '${path }/resource/customerRelationShip!listCompanyQRCode.action' ;} ;return navTabSearch(this);" 
			action="${ path }/resource/customerRelationShip!listCompanyQRCode.action" 
			method="post" rel = "pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				
				<tr>
					<td style="margin-top:-10px">
						<label>支公司名称：</label> 
						<input type="text" name="branchCommany" value="${branchCommany}" />
					</td>
					<td style="margin-top:-10px">
						<label>总公司名称：</label> 
						<input type="text" name="customerNameSimple" value="${customerNameSimple}" />
					</td>
					<td style="margin-top:-10px">
						<label>项目名称：</label> 
						<select id="projectType" name="projectType" rel="iselect">
						<option value="">---请选择---</option>
							<c:forEach items="${projectTypeList}" var="project">
								<option value='${project.projectType}' <c:if test="${project.projectType == projectType}">selected</c:if>>${project.projectType}</option>
							</c:forEach>
	                	</select>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
				
			</table>
			
	</form>
	</div>
	
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="35">序号</th>
				<th export="true" columnEnName="branchCommany" columnChName="支公司名称">支公司名称</th>
				<th export="true" columnEnName="customerNameSimple" columnChName="总公司名称">总公司名称</th>
				<th export="true" columnEnName="projectTypeName" columnChName="项目类别">项目类别</th>
				<th export="true" columnEnName="projectName" columnChName="项目名称">项目名称</th>
				<th export="true" columnEnName="projectOwner" columnChName="项目负责人">项目负责人</th>
				<th export="true" columnEnName="projectCode" columnChName="项目编码">项目编码</th>
				
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="companyQRCode" varStatus="status">
				<tr target="sid_user" rel="${companyQRCode.id }">
					<td align="center">
						${ status.count }
					</td>
					<td align="center">${companyQRCode.branchCommany}</td>
					<td align="center">${companyQRCode.customerNameSimple}</td>
					<td align="center">${companyQRCode.projectTypeName}</td>
					<td align="center">${companyQRCode.projectName}</td>
					<td align="center">${companyQRCode.projectOwner}</td>
					<td align="center">${companyQRCode.projectCode}</td>
					
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">
							<div class="panelBar" style="width:220px;">
								<ul class="toolBar" >
									<c:if test="${companyQRCode.qrCodeUrl==null||companyQRCode.qrCodeUrl==''}">
										<li><a class="add" href="javascript:void(0)" onclick="dj('${companyQRCode.id }','${companyQRCode.projectTypeCode}','${companyQRCode.projectCode}','${companyQRCode.proId}')"
											 rel="add" title="生成"><span>生成支公司二维码</span></a></li>
									</c:if>
									<c:if test="${companyQRCode.qrCodeUrl !=null&&companyQRCode.qrCodeUrl!=''}">
										<input id="viewPath" type="hidden" value="${companyQRCode.qrCodeUrl}"/>
										<li><a class="add" target="_blank" href="${companyQRCode.qrCodeUrl}?r=1472179530843" ><span>查看支公司二维码</span></a></li>
									</c:if>
                        		</ul>
							</div>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
