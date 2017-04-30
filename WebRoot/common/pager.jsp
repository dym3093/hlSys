<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="pagerForm" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="pageSize" value="${page.pageSize }" />
</form>
	
<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.pageSize }" pageNumShown="10" currentPage="${page.pageNum }"></div>

<div class="pages" style="float:right">
		<span>显示</span>
		<select id="pageSizeSelect" name="pageSize" onchange="dwzPageBreak({data:{numPerPage:this.value}})">
			<option value="5">5</option>
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
		</select>
		<span>条，共${page.totalCount}条</span>
	</div>
<script src="${path}/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
function copyForm(){
	var frag='<input type="hidden" name="#name#" value="#value#" />';
	var $searchForm=$("form[rel='pagerForm']"),$pagerForm=$("#pagerForm");
	$pagerForm.attr("action",$searchForm.attr("action"));
	$searchForm.find(":input").each(function(){
	var $input=$(this),name=$input.attr("name");
	if(name&&(!$input.is(":checkbox,:radio")||$input.is(":checked"))){
	if($pagerForm.find(":input[name='"+name+"']").length==0){
	var inputFrag=frag.replaceAll("#name#",name).replaceAll("#value#",$input.val());
	$pagerForm.append(inputFrag);}}});}
function _getPagerForm($parent,args){
	var form=$("#pagerForm",$parent).get(0);
	if(form){
	if(args["pageNum"])form[DWZ.pageInfo.pageNum].value=args["pageNum"];
	if(args["numPerPage"])form[DWZ.pageInfo.numPerPage].value=args["numPerPage"];
	if(args["orderField"])form[DWZ.pageInfo.orderField].value=args["orderField"];
	if(args["orderDirection"]&&form[DWZ.pageInfo.orderDirection])form[DWZ.pageInfo.orderDirection].value=args["orderDirection"];}
	return form;}
function dwzPageBreak(options){
	var form=_getPagerForm($(document),options.data);
	if(form){
		copyForm();
		form.submit();
	}
}
$(document).ready(function(){
	DWZ.frag["pagination"] = '<ul>\
							<li class="j-first">\
								<a class="first" href="javascript:;"><span>首页</span></a>\
								<span class="first"><span>首页</span></span>\
							</li>\
							<li class="j-prev">\
								<a class="previous" href="javascript:;"><span>上一页</span></a>\
								<span class="previous"><span>上一页</span></span>\
							</li>\
							#pageNumFrag#\
							<li class="j-next">\
								<a class="next" href="javascript:;"><span>下一页</span></a>\
								<span class="next"><span>下一页</span></span>\
							</li>\
							<li class="j-last">\
								<a class="last" href="javascript:;"><span>末页</span></a>\
								<span class="last"><span>末页</span></span>\
							</li>\
							<li class="jumpto"><input class="textInput" type="text" size="4" value="#currentPage#" /><input class="goto" type="button" value="确定" /></li>\
						</ul>'
	
	$("#pageSizeSelect").val(${page.pageSize });
	$("div.pagination").each(function(){
		var $this=$(this);
		$this.pagination({
		targetType:$this.attr("targetType"),
		rel:$this.attr("rel"),
		totalCount:$this.attr("totalCount"),
		numPerPage:$this.attr("numPerPage"),
		pageNumShown:$this.attr("pageNumShown"),
		currentPage:$this.attr("currentPage")});});
});

function navSort(orderField,orderDirection){
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
