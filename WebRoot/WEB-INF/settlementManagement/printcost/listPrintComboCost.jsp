<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">

//变更用于复选框
function addComoboCost() {
	$.pdialog.open("../settlementManagement/erpPrintComboCost!showAddComboCost.action","showAddComboCost", "新增套餐费用",
			{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:500,height:300});
}

function showEditComboCost(){
	var checkbox = $("input:checkbox[name='comboCostNo']:checked");
	if($(checkbox).length!=1){
		alertMsg.error("请选择要修改的套餐费用");
		return;
	}
	var id = $.trim($(checkbox).val());
	var company = encodeURI($(checkbox).parent().nextAll().eq(1).text());
	var combo = encodeURI($(checkbox).parent().nextAll().eq(2).text());
	var price = $(checkbox).parent().nextAll().eq(4).text();
	$.pdialog.open("../settlementManagement/erpPrintComboCost!showEditComboCost.action?id="+id+"&company="+company+"&combo="+combo+"&price="+price,"showEditComboCost", "修改套餐费用",
		{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:700,height:300});
}

function deleteComboCost(){
	var checkbox = $("input:checkbox[name='comboCostNo']:checked");
	if($(checkbox).length!=1){
		alertMsg.error("请选择要删除的套餐费用");
		return;
	}
	var id = $.trim($(checkbox).val());
	$.ajax({
		type: "post",
		cache: false,
		data:{"id":id},
		url: "../settlementManagement/erpPrintComboCost!deleteComoboCost.action",
		success: function(data){
			if(eval("("+data+")").count==1){
				alertMsg.correct("删除成功");
				return navTabSearch(this);
			}
		},
		error:function(){
			alertMsg.error('服务发生异常，请稍后再试');
			return;
		},
	});
}
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpPrintComboCost!listPrintComboCost.action'){this.action = '${path}/settlementManagement/erpPrintComboCost!listPrintComboCost.action' ;} ;return navTabSearch(this);" action="${path}/settlementManagement/erpPrintComboCost!listPrintComboCost.action" method="post" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>打印公司：</label> 
						<input type="text" name="filter_and_printCompany_LIKE_S" value="${filter_and_printCompany_LIKE_S}"/>
					</td>
					<td>
						<label>套餐名称：</label> 
						<input type="text" name="filter_and_comboName_LIKE_S" value="${filter_and_comboName_LIKE_S}"/>
					</td>
					<td>
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
		<ul class="toolBar">
 			<li><a class="add" onclick="addComoboCost()" style="cursor:pointer;"><span>新增套餐费用</span></a></li>
 			<li><a class="edit" onclick="showEditComboCost()" style="cursor:pointer;"><span>修改套餐费用</span></a></li>
 			<li><a class="icon" onclick="deleteComboCost()" style="cursor:pointer;"><span>删除套餐费用</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="50" nowrap="nowrap">序号</th>
				<th width="100" export="true" columnEnName="printCompany" columnChName="打印公司" >打印公司</th>
				<th width="100" export="true" columnEnName="comboName" columnChName="套餐名称" >套餐名称</th>
				<th width="200" export="true" columnEnName="comboDetail" columnChName="套餐描述" >产品名称</th>
				<th width="100" export="true" columnEnName="price" columnChName="价格" >价格</th>
				<th width="100" export="true" columnEnName="createUser" columnChName="创建人" >创建人</th>
				<th width="100" export="true" columnEnName="createTime" columnChName="创建时间" >创建时间</th>
				<th width="100" export="true" columnEnName="updateTime" columnChName="更改时间" >更改时间</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr>
					<td width="50" align="center" nowrap="nowrap"><input type="checkbox" name="comboCostNo" value="${entity.id}">${status.count}</td>
					<td width="100" align="center" nowrap="nowrap" hidden="hidden">${entity.id}</td>
					<td width="100" align="center" nowrap="nowrap">${entity.printCompany}</td>
					<td width="100" align="center" nowrap="nowrap">${entity.comboName}</td>
					<td width="200" align="center" nowrap="nowrap">${entity.productName}</td>
					<td width="100" align="center" nowrap="nowrap">${entity.price}</td>
					<td width="100" align="center" nowrap="nowrap">${entity.createUser}</td>
					<td width="100" align="center" nowrap="nowrap">${fn:substring(entity.createTime,0,19)}</td>
					<td width="100" align="center" nowrap="nowrap">${fn:substring(entity.updateTime,0,19)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 