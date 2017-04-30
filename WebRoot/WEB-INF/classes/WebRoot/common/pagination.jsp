<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="pageSize" value="${page.pageSize }" />
	<input type="hidden" name="orderField" value="${orderField}"/>
    <input type="hidden" name="orderDirection" id="orderDirection" value="${orderDirection}"/>
</form>

	
	
<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.pageSize }" pageNumShown="10" currentPage="${page.pageNum }"></div>

<div class="pages" style="float:right">
		<span>显示</span>
		<select id="pageSizeSelect" name="pageSize" onchange="navTabPageBreak({numPerPage:this.value})">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
		</select>
		<span>条，共${page.totalCount}条</span>
	</div>
<script type="text/javascript" language="javascript">
$("#pageSizeSelect",navTab.getCurrentPanel()).val(${page.pageSize });
function navSort(orderField,orderDirection){
    orderDirection = $("#orderDirection",navTab.getCurrentPanel()).val();
    if (orderDirection == 'desc'){
    	orderDirection = 'asc';
    }
    else {
    	orderDirection = 'desc';
    }
	if(orderField!=''){
		$("#pagerForm input[name=orderField]",navTab.getCurrentPanel()).val(orderField);
	}
	if(orderDirection!=''){
		$("#pagerForm input[name=orderDirection]",navTab.getCurrentPanel()).val(orderDirection);
	}
	var form = $("#pagerForm",navTab.getCurrentPanel());
	navTab.reload(form.attr("action"), form.serializeArray());
	
  }
  function dialogSort(orderField,orderDirection){
    orderDirection = $("#orderDirection",navTab.getCurrentPanel()).val();
    if (orderDirection == 'desc'){
    	orderDirection = 'asc';
    }
    else {
    	orderDirection = 'desc';
    }
	if(orderField!=''){
		$("#pagerForm input[name=orderField]",navTab.getCurrentPanel()).val(orderField);
	}
	if(orderDirection!=''){
		$("#pagerForm input[name=orderDirection]",navTab.getCurrentPanel()).val(orderDirection);
	}
	var form = $("#pagerForm",navTab.getCurrentPanel());
	$.pdialog.reload(form.attr("action"), form.serializeArray());
  }

</script>
