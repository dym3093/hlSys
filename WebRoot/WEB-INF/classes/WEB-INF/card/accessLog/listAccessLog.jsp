<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/accessLog/accessLogAction!listAccessLog.action'){this.action = '${path }/accessLog/accessLogAction!listAccessLog.action' ;} ;return navTabSearch(this);" 
	action="${path }/accessLog/accessLogAction!listAccessLog.action"  method="post" rel="pagerForm" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>
							登陆ip：
						</label>
						<input type="text" name="filter_and_ip_LIKE_S"
							value="${filter_and_ip_LIKE_S }" />
					</td>
					<td>
				<label>登陆时间：</label>
				<input type="text" name="filter_and_createTime_GEST_T"  id="d787453" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d543668\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_createTime_LEET_T" id="d543668" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d787453\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
						<div class="buttonActive" style="margin-left: 10px">
							<div class="buttonContent">
								<button type="submit">
									查找
								</button>
							</div>
						</div>
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
				<web:exportExcelTag pagerFormId="pagerFindForm"
					pagerMethodName="findByPage"
					actionAllUrl="org.hpin.accessLog.web.AccessLogAction"
					informationTableId="exportExcelTable" 
					fileName="AccessLog"></web:exportExcelTag>
			</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="150" id="exportExcelTable">
		<thead>
			<tr>
			<th width="40px"><nobr>序号</nobr></th>
			<th export = "true" columnEnName = "ip" columnChName = "登陆IP">登陆IP</th>
			<th export = "true" columnEnName = "url" columnChName = "URL">URL</th>
			<th export = "true" columnEnName = "createTime" columnChName = "登陆时间">登陆时间</th>
			<th export = "true" columnEnName = "remark" columnChName = "创建时间">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ page.results }" var="accessLog"
				varStatus="status">
				<tr target="sid_user" rel="${accessLog.id }">
					<td width="40px" align="center">
						${ status.count }
					</td>
					<td align="center" nowrap="nowrap">
					${accessLog.ip}
					</td>
					<td align="center" nowrap="nowrap" showTitle="true">${accessLog.url}</td>
					<td align="center" nowrap="nowrap">${fn:substring( accessLog.createTime,0,19 )}</td>
					<td align="center" nowrap="nowrap">${accessLog.remark}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
