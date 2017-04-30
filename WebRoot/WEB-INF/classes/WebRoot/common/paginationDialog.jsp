<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<form id="pagerForm" method="post" action="#">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" id = "pageSize" name="pageSize" value="${page.pageSize }" />
</form>
<script type="text/javascript">
	function changePageSize(value){
		document.getElementById('pageSize').value = value;
		dwzPageBreak({targetType:'dialog' , numPerPage: value});
	}
</script>
<div class="panelBar" >
	<div class="pagination" targettype="dialog" totalCount="${page.totalCount}" numPerPage="${page.pageSize }"  currentPage="${page.pageNum }"></div>
	<div class="pages" style="float:right">
		<span>显示</span>
		<select id="pageSizeSelect" name="pageSizeSelect" onchange="changePageSize(this.value)">
			<option value="10" <c:if test="${page.pageSize == 10 }">selected</c:if> >10</option>
			<option value="20" <c:if test="${page.pageSize == 20 }">selected</c:if>>20</option>
			<option value="50" <c:if test="${page.pageSize == 50 }">selected</c:if>>50</option>
			<option value="100" <c:if test="${page.pageSize == 100 }">selected</c:if>>100</option>
		</select>
		<span>条，共${page.totalCount}条</span>
	</div>
	
</div>

<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#pagerForm",$.pdialog.getCurrent()).attr("action",$("form[rel='pagerForm']").attr("action"));
});

</script>
