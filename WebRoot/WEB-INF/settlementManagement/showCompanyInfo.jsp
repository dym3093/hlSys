<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script src="${path}/jquery/json2.js" type="text/javascript"></script>  
  
<script type="text/javascript">

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
	return navTabSearch(this);	//刷新页面
}

</script>

<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
	<div class="pageHeader" style="background:white;height:98%;" align="center">
		<div class="panelBar" style="display: none;">
			<jsp:include page="/common/pagination.jsp" />
		</div>
		<table id="priceTable" class="list" style="width:100%;">
			<thead>
				<tr>
					<th><span style="margin-left: 60px;">项目编码</span></th>
			  		<th><span style="margin-left: 63px;">项目名</span></th>
			  		<th><span style="margin-left: 43px;">项目负责人</span></th>
					<th export="true" columnEnName="combo" columnChName="套餐名称" >套餐名称</th>
					<th export="true" columnEnName="comboPrice" columnChName="套餐价格">套餐价格</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr>
						<td><span>${entity.projectCode}</span></td>
			  			<td><span>${entity.projectName}</span></td>
			  			<td><span>${entity.projectOwner}</span></td>
						<td>
							<label name="">${entity.combo}</label> 
						</td>
						
						<td >
							<lable>${entity.comboPrice}</lable>
						</td>
						
					</tr>		
				</c:forEach>
			</tbody>
		</table>
</div>
