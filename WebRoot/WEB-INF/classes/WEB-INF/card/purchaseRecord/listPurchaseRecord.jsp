<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<form
		onsubmit="if(this.action != '${path }/purchaseRecord/purchaseRecordAction!listPurchaseRecord.action'){this.action = '${path }/purchaseRecord/purchaseRecordAction!listPurchaseRecord.action' ;} ;return navTabSearch(this);"
		action="${path }/purchaseRecord/purchaseRecordAction!listPurchaseRecord.action"
		method="post" rel="pagerForm" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td><label> 用户名： </label> <input type="text"
						name="filter_and_userName_LIKE_S"
						value="${filter_and_userName_LIKE_S }" /></td>
					<td><label> 民族： </label> <input type="text"
						name="filter_and_nation_LIKE_S"
						value="${filter_and_nation_LIKE_S }" /></td>
					<td><label> 云图编号： </label> <input type="text"
						name="filter_and_ytCode_LIKE_S"
						value="${filter_and_ytCode_LIKE_S }" /></td>
				</tr>
				<tr>
					<td><label>注册时间：</label> <input type="text"
						name="filter_and_createTime_GEST_T" id="d787453"
						style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d543667\')}'})"
						class="required" readonly="readonly"
						value="${filter_and_createTime_GEST_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>至：</label> <input type="text"
						name="filter_and_createTime_LEET_T" id="d543667"
						style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d787453\')}'})"
						readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td>
						<div class="buttonActive" style="margin-left: 10px">
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
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit"
				href="${path}/purchaseRecord/purchaseRecordAction!modifyPurchaseRecord.action?id={sid_user}"
				target="navTab"><span>修改</span></a></li>
			<web:exportExcelTag pagerFormId="pagerFindForm"
				pagerMethodName="findByPage"
				actionAllUrl="org.hpin.purchaseRecord.web.PurchaseRecordAction"
				informationTableId="exportExcelTable" fileName="PurchaseRecord"></web:exportExcelTag>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="150" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40px"><nobr>序号</nobr></th>
				<th export="true" columnEnName="userName" columnChName="用户名">用户名</th>
				<th export="true" columnEnName="nation" columnChName="民族">民族</th>
				<th export="true" columnEnName="sex" columnChName="性别"
					id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">性别</th>
				<th export="true" columnEnName="tel" columnChName="电话">电话</th>
				<th export="true" columnEnName="email" columnChName="电子邮件">电子邮件</th>
				<th export="true" columnEnName="idcardNum" columnChName="身份证号码">身份证号码</th>
				<th export="true" columnEnName="ytCode" columnChName="云图编号">云图编号</th>
				<th export="true" columnEnName="createTime" columnChName="注册时间">注册时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ page.results }" var="purchaseRecord"
				varStatus="status">
				<tr target="sid_user" rel="${purchaseRecord.id }">
					<td width="40px" align="center"><input type="checkbox"
						name="ids" value="${ purchaseRecord.id }">${ status.count}</td>
					<td align="center" nowrap="nowrap"><a
						href="${ path }/purchaseRecord/purchaseRecordAction!browPurchaseRecord.action?id=${ purchaseRecord.id }"
						style="color:#0000FF" target="dialog">${purchaseRecord.userName}</a></td>
					<td align="center" nowrap="nowrap">${purchaseRecord.nation}</td>
					<td align="center"><hpin:id2nameDB id="${purchaseRecord.sex}" beanId="org.hpin.base.dict.dao.SysDictTypeDao" /></td>
					<td align="center" nowrap="nowrap">${purchaseRecord.tel}</td>
					<td align="center" nowrap="nowrap" showTitle="true">${purchaseRecord.email}</td>
					<td align="center" nowrap="nowrap">${purchaseRecord.idcardNum}</td>
					<td align="center" nowrap="nowrap" showTitle="true">${purchaseRecord.ytCode}</td>
					<td align="center" nowrap="nowrap">${ fn:substring(purchaseRecord.createTime,0,10 )}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>