<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/resource/preSalesmanMgr!listSalesManInfoBX.action'){this.action = '${path }/resource/preSalesmanMgr!listSalesManInfoBX.action' ;} ;return navTabSearch(this);" action="${ path }/resource/preSalesmanMgr!listSalesManInfoBX.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
				<label>营销员工号：</label>
					<input type="text" name="filter_and_employeeNo_EQ_S" value="${filter_and_employeeNo_EQ_S}"/>
				</td>
				<td>
					<label>营销员姓名：</label><input type="text" name="filter_and_salesman_EQ_S" value="${filter_and_salesman_EQ_S}"/>
				</td>
				<td>
					<%-- <label>渠道：</label><input type="text" name="filter_and_mark_EQ_S" value="${filter_and_mark_EQ_S}"/> --%>
				</td>
				<td style="float:right;margin:2px">
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
		<ul class="toolBar">
			<li><a class="icon" href="http://192.168.1.50:8099/ymtemplet/salesManInfoTmp.xlsx"><span>下载模板</span></a></li>
			<li><a class="add" href="${path}/resource/preSalesmanMgr!importSalesmanInfoBX.action" target="dialog" width="600" hight="100"><span>导入表格</span></a></li>
			<li><a class="edit" href="javascript:void(0);" onclick="addSalesmanInfoByOne()"><span>增加信息</span></a></li>
			<%-- <web:exportExcelTag
						pagerFormId="pagerFindForm" 
						pagerMethodName="findPageByConditions" 
						actionAllUrl="org.hpin.base.customerrelationship.web.CustomerRelationShipAction" 
						informationTableId="exportExcelTable"
						fileName="customerRelationShip"></web:exportExcelTag>  --%>
		</ul>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="100" id="exportExcelTable">
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeeNo" columnChName = "营销员工号" >营销员工号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "salesman" columnChName = "营销员姓名" >营销员姓名</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeePhone" columnChName = "营销员电话" >营销员电话</th>
				<th nowrap="nowrap" export = "true" columnEnName = "ymCompany" columnChName = "远盟支公司名称" >远盟支公司名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "ymOwncompany" columnChName = "远盟总公司名称" >远盟总公司名称</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "mark" columnChName = "渠道" >渠道</th> -->
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="salesManVo" varStatus="status">
			<tr target="sid_user" rel="${ salesManVo.id }">
				<td align="center"><%-- <input type="checkbox" name="ids" value="${ salesManVo.id }"> --%>${ status.count }</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeeNo}</td>
				<td align="center" nowrap="nowrap">${salesManVo.salesman}</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeePhone}</td>
				<td align="center" nowrap="nowrap">${salesManVo.ymCompany }</td>
				<td align="center" nowrap="nowrap">${salesManVo.ymOwncompany }</td>
				<%-- <td align="center" nowrap="nowrap">${salesManVo.mark }</td> --%>
				<td align="center" nowrap="nowrap"><fmt:formatDate value='${salesManVo.createTime }' pattern='yyyy-MM-dd'/></td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript" language="javascript">
function addSalesmanInfoByOne(){
	var rootNavTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/resource/preSalesmanMgr!showAddSalesmanInfoByOne.action?rootNavTabId="+rootNavTabId, "addSalesmanInfoByOne", "增加信息",
			{width:800,height:250,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );	
}
</script>
