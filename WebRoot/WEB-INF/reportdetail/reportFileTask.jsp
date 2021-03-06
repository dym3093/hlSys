<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<script type="text/javascript">

</script>

<div class="tip"><span>任务查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/report/reportFileTask!query.action'){this.action = '${path}/report/reportFileTask!query.action' ;} ;return navTabSearch(this);" action="${path}/report/reportFileTask!query.action" method="post" rel="pagerForm">
		<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>
				<td>
					<label>是否完成：</label>
					<select name="filter_and_ismatch_EQ_I" rel="iselect" id="ismatch">
						<option value="">请选择</option>
						<option value="1" <c:if test="${filter_and_ismatch_EQ_I==1 }">selected="selected"</c:if>>是</option>
						<option value="0" <c:if test="${filter_and_ismatch_EQ_I==0 }">selected="selected"</c:if>>否</option>
					</select> 
				</td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createdate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createdate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createdate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createdate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><input type="hidden" name="id" value="${reportFileTask.id }"/>
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
	<div layouth="170" style="overflow: auto; height: 102px;">
		<table class="list" width="100%" layoutH="170" id="">
			<thead>
				<tr>
					<th>序号</th>
					<th>批次号</th>
					<th>创建日期</th>
					<th>报告总数</th>
					<th>本批次重复</th>
                    <th>与其他批次重复</th>
					<th>匹配成功</th>
					<th>未匹配成功</th>
					<th>读取失败</th>
					<th>异常数量</th>
                    <th>会员信息多条</th>
                    <th>没有对应的申友套餐</th>
                    <th>套餐不匹配</th>
                    <!-- <th>没有对应的远盟套餐</th> -->
                    <th>文件名错误</th>
                    <th>暂不打印</th>
                    <th>不打印报告</th>
                    <th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="reportask" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td align="center">
							<a title="匹配详情" target="navTab" width="1000" href="${path}/report/reportFileTask!toMatchDetail.action?id=${reportask.id}">${reportask.batchno}</a>
						</td>
						<td align="center">${fn:substring(reportask.createdate,0,17)}00</td>
						<td align="center">${reportask.pdftotal}</td>
						<td align="center">${reportask.inbthrepeatnum}</td>
                        <td align="center">${reportask.otherbthrepeatnum}</td>
						<td align="center">${reportask.updatenum}</td>
						<td align="center">${reportask.unmatchnum}</td>
						<td align="center">${reportask.unrecordnum}</td>
						<td align="center">${reportask.abnormalnnum}</td>
                        <td align="center">${reportask.cusmorenum}</td>
                        <td align="center">${reportask.noSYCombo}</td>
                        <td align="center">${reportask.noCustomerCombo2SY}</td>
                        <%-- <td align="center">${reportask.noYMCombo}</td> --%>
                        <td align="center">${reportask.errorPdfName}</td>
                        <td align="center">${reportask.stopCombo}</td>
                        <td align="center">${reportask.stopReport}</td>
						<td align="center">
							<c:if test="${reportask.ismatch==0}">处理中</c:if>
							<c:if test="${reportask.ismatch==1}">已完成</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
