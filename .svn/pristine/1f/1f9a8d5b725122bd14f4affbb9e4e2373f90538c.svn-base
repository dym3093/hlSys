<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="pageSize" value="${page.pageSize }" />
</form>
<div class="panelBar" >
	<div class="pages">
		<span>显示</span>
		<select id="pageSizeSelect" name="pageSize" onchange="navTabPageBreak({numPerPage:this.value})">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
		</select>
		<span>条，共${page.totalCount}条</span>
	</div>
	
	<div class="pagination" rel="jbsxBox2" totalCount="${page.totalCount}" numPerPage="${page.pageSize }" pageNumShown="10" currentPage="${page.pageNum }"></div>
</div>

<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#pagerForm").attr("action",$("form[rel='pagerForm']").attr("action"));
	$("#pageSizeSelect").val(${page.pageSize });
});
</script>
