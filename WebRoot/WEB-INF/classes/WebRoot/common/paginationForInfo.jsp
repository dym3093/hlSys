<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<form id="pagerForm" method="post" action="${path }/resource/customer!lookUpCustomer.action">
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
	<div class="pages">
		<span>显示</span>
		<select id="pageSizeSelect" name="pageSizeSelect" onchange="changePageSize(this.value)">
			<option value="1" <c:if test="${page.pageSize == 1 }">selected</c:if> >1</option>
			<option value="2" <c:if test="${page.pageSize == 2 }">selected</c:if>>2</option>
		</select>
		<span>条，共${page.totalCount}条</span>
	</div>
	
	<div class="pagination" targettype="dialog" totalCount="${page.totalCount}" numPerPage="${page.pageSize }"  currentPage="${page.pageNum }"></div>
</div>

<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#pagerForm").attr("action",$("form[rel='pagerForm']").attr("action"));
});

</script>
