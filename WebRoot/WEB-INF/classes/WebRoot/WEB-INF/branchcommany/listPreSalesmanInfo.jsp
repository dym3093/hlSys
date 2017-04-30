<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/resource/preSalesmanMgr!listSalesManInfo.action'){this.action = '${path }/resource/preSalesmanMgr!listSalesManInfo.action' ;} ;return navTabSearch(this);" action="${ path }/resource/preSalesmanMgr!listSalesManInfo.action" method="post" rel = "pagerForm" id="pagerFindForm">
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
			</tr>
			<tr>
				<%--<td><label>远盟支公司名称：</label>--%>
					<%--<input type="text" name="filter_and_ymCompanyId_IN_S"  value="${filter_and_ymCompanyId_IN_S}"/>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<label>远盟总公司名称：</label>--%>
					<%--<input type="text" name="filter_and_ymOwncompanyId_IN_S" value="${filter_and_ymOwncompanyId_IN_S}"/>--%>
				<%--</td>--%>
				<td><label>远盟支公司名称：</label>
					<input type="hidden" id="id" name="filter_and_ymCompany_IN_S" bringbackname="customer.branchCommanyName" value="${filter_and_ymCompany_IN_S}" />
					<input type="text" id="branchCompany" name="aaaa" bringbackname="customer.branchCommanyName" value="${aaaa}" readonly="readonly" />
					<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" lookupGroup="customer"
					   target="dialog" width="800" height="480">查找带回</a>
				</td>
				<td><label>远盟总公司名称：</label>
					<input type="hidden" id="ownedCompanyId" name="filter_and_ymOwncompany_IN_S" bringbackname="dept.customerNameSimple" value="${filter_and_ymOwncompany_IN_S}" />
					<input type="text" id="ownedCompany" name="bbbb" bringbackname="dept.customerNameSimple" value="${bbbb}" readonly="readonly" />
					<a class="btnLook" href="${ path }/resource/customerRelationShip!lookDept.action" lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a>
				</td>
			</tr>
            <tr>
				<td>
					<label>省：</label>
					<select id="province" name="filter_and_provice_EQ_S"
							rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
							ref="city"
							refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
						<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td>
					<label>市：</label>
					<select id="city" name="filter_and_city_EQ_S" rel="iselect">
						<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				<td style="float:right">
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
	 <web:security tag="customerRelationShipAdmin">
		<ul class="toolBar">
			<li><a class="icon" href="http://192.168.1.50:8099/ymtemplet/salesManInfoTmp.xlsx"><span>下载模板</span></a></li>
			<li><a class="add" href="${path}/resource/preSalesmanMgr!importSalesmanInfo.action" target="dialog" width="600" hight="100"><span>添加</span></a></li>
			<li><a class="edit" href="javascript:void(0);" onclick="showAmlPreSalesMan();"><span>异常数据</span></a></li>
			<%-- <web:exportExcelTag
						pagerFormId="pagerFindForm" 
						pagerMethodName="findPageByConditions" 
						actionAllUrl="org.hpin.base.customerrelationship.web.CustomerRelationShipAction" 
						informationTableId="exportExcelTable"
						fileName="customerRelationShip"></web:exportExcelTag>  --%>
		</ul>
	</web:security> 
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="100" id="exportExcelTable">
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeeNo" columnChName = "营销员工号" >营销员工号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "salesman" columnChName = "营销员姓名" >营销员姓名</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeePhone" columnChName = "营销员电话" >营销员电话</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeeCompany" columnChName = "营销员所在支公司" >营销员所在支公司</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeeCityCompany" columnChName = "营销员所在市公司" >营销员所在市公司</th>
				<th nowrap="nowrap" export = "true" columnEnName = "employeeHeadOffice" columnChName = "营销员所在总公司" >营销员所在总公司</th>
				<th nowrap="nowrap" export = "true" columnEnName = "ymCompany" columnChName = "远盟支公司名称" >远盟支公司名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "ymOwncompany" columnChName = "远盟总公司名称" >远盟总公司名称</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "mark" columnChName = "渠道" >渠道</th> -->
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="salesManVo" varStatus="status">
			<tr target="sid_user" rel="${ salesManVo.id }">
				<td align="center"><input type="checkbox" name="ids" value="${ salesManVo.id }">${ status.count }</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeeNo}</td>
				<td align="center" nowrap="nowrap">${salesManVo.salesman}</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeePhone}</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeeCompany }</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeeCityCompany }</td>
				<td align="center" nowrap="nowrap">${salesManVo.employeeHeadOffice }</td>
				<td align="center" nowrap="nowrap">${salesManVo.ymCompany }</td>
				<td align="center" nowrap="nowrap">${salesManVo.ymOwncompany }</td>
				<%-- <td align="center" nowrap="nowrap">${salesManVo.mark }</td> --%>
				<td align="center" nowrap="nowrap"><fmt:formatDate value='${salesManVo.createTime }' pattern='yyyy-MM-dd'/></td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
function showAmlPreSalesMan(){
	//打开导入窗口
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("showAmlPreSalesMan", "${path}/resource/preSalesmanMgr!showAmlPreSalesMan.action?navTabId="+navTabId, { title:"异常数据", fresh:false, data:{} });

};
</script>
