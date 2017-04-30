<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<script type="text/javascript">

</script>

<div class="tip"><span>匹配成功查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/report/reportFileTask!query.action'){this.action = '${path}/report/reportFileTask!query.action' ;} ;return navTabSearch(this);" action="${path}/report/reportFileTask!query.action" method="post">
		<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_batchno_LIKE_S" value="${filter_and_batchno_LIKE_S}"/>
				</td>
				<td></td>
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
					<th>报告文件名称</th>
					<th>报告姓名</th>
					<th>报告条码</th>
					<th>系统姓名</th>
					<th>系统条码</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="pdfcusbean" varStatus="status">
					<tr>
						<td align="center">${status.count}
                            <input type="hidden" name="pdfid" value="${pdfcusbean.pdfid}">
                            <input type="hidden" name="cusid" value="${pdfcusbean.cusid}">
                        </td>
						<td align="center">${pdfcusbean.pdffilename}</td>
                        <td align="center">${pdfcusbean.pdfusername}</td>
                        <td align="center">${pdfcusbean.pdfcode}</td>
                        <td align="center">${pdfcusbean.cusname}</td>
                        <td align="center">${pdfcusbean.cuscode}</td>
						<td align="center">${fn:substring(pdfcusbean.createdate,0,17)}00</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
