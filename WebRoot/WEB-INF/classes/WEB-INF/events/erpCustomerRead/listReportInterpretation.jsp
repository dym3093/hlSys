<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function(){
		//体检报告--新窗口打开PDF
		$("#phyReport").click(function (){
			var code = $(this).attr("name");
			$.ajax({	
				type: "post",
				cache :false,
				data:{"code":code},
				url: "../events/erpCustomerRead!findPhyReport.action",
				success: function(data){
					if(data!=null && data!=""){
						eval("data="+data);
						window.open(data.phyReport);  
					}else{
						alert("该文件未找到，请稍后再试！");
						return;
					}
				},
				error :function(){
					alert("该文件未找到，请稍后再试！");
					return;
				}
			});
		})
		
	})

</script>
<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomerRead!listReportInterpretation.action'){this.action = '${path}/events/erpCustomerRead!listReportInterpretation.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomerRead!listReportInterpretation.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent" >
			<tr>
				<td>
					<label>姓名：</label> 
					<input type="text" name="readQuery.name" value="${readQuery.name}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="readQuery.code" value="${readQuery.code}"/>
				</td>
				<td>
					<label>套餐名：</label> 
					<input type="text" name="readQuery.setmealName" value="${readQuery.setmealName}"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>身份证号：</label> 
					<input type="text" name="readQuery.idno" value="${readQuery.idno}"/>
				</td>
				<td>
					<label>手机号：</label> 
					<input type="text" name="readQuery.phone" value="${readQuery.phone}"/>
				</td>
				<td>
					<label>项目类别：</label> 
					<select id="projectTypes" name="readQuery.projectTypes" style="width: 170px;" rel="iselect">
						<option value="">请选择</option>
						<c:forEach items="${proTypes }" var="item">
							<option value="${item.projectType }" ${item.projectType == readQuery.projectTypes ? "selected":"" }>${item.projectTypeName }</option>
						</c:forEach>
					</select>
				</td>
				
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
			
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="210" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "phone" columnChName = "手机号" >手机号</th>
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<th  export = "true" columnEnName = "projectTypeName" columnChName = "项目类别" >项目类别</th>
				<th  export = "true" columnEnName = "pdfContent" columnChName = "报告查看" >报告查看</th>
				<th  export = "true" columnEnName = "detail" columnChName = "原始报告单" >原始报告单</th>
				<th  export = "true" columnEnName = "phyReport" columnChName = "体检报告" >体检报告</th>
				<th  export = "true" columnEnName = "note" columnChName = "note" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomerRead" varStatus="status">
					<tr target="sid_user" rel="${erpCustomerRead.id }">
						<td align="center">
							${ status.count }
						</td>
						<td align="center">	${erpCustomerRead.code}</td>
						<td align="center">	${erpCustomerRead.name}</td>
						<td align="center">	${erpCustomerRead.sex}</td>
						<td align="center">	${erpCustomerRead.age}</td>
						<td align="center">	${erpCustomerRead.idno}</td>
						<td align="center">	${erpCustomerRead.phone}</td>
						<td align="center">${erpCustomerRead.setmealName}</td>
						<td align="center">${erpCustomerRead.projectTypeName}</td>
						<td align="center">
							<c:if test="${fn:length(erpCustomerRead.pdffilepath)>1}">
								<web:security tag="customerReport">
									<a href="${erpCustomerRead.pdffilepath}" target="blank">报告查看</a>
								</web:security>	
							</c:if>
						</td>
						<td align="center">
							<c:if test="${fn:length(erpCustomerRead.detail)>1}">
								<web:security tag="customerReport">
									<a href="${path}/events/erpCustomerRead!findDetail.action?code=${erpCustomerRead.code }" id="detail" target="navTab" rel="viewReport">原始报告单</a>
								</web:security>	
							</c:if>
						</td>
						<td align="center">
							<c:if test="${fn:length(erpCustomerRead.phyReport)>1}">
								<web:security tag="customerReport">
									<a href="javascript:void(0);" id="phyReport" name="${erpCustomerRead.code }">体检报告</a>
								</web:security>
							</c:if>
						</td>
						<td align="center">	${erpCustomerRead.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 