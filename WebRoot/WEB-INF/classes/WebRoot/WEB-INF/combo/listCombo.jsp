<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function changeCombo() {
	var ids = '';
	var count = 0;
	var state = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		state = $(this).attr("state");
	});
	if(count == 0) {
		alert('请选择你要修改的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行修改！');
		return ;		
	}
	else {
		/* if(state=='1010101'){ */
		 	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("listcombo", "../combo/comboAction!modifyComboAll.action?id="+ids+"&navTabId="+navTabId, { title:"修改", fresh:false, data:{} });
	/* }else{
		alert("只有未派发的资产才能修改！");
		return;
	} */
	}
}
function clearInput(){
	$(':input','#pagerFindForm',navTab.getCurrentPanel())  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('selected');  
}
</script>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/combo/comboAction!listCombo.action'){this.action = '${path }/combo/comboAction!listCombo.action' ;} ;return navTabSearch(this);" action="${ path }/combo/comboAction!listCombo.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>套餐名称：</label>
					<input type="text" name="filter_and_comboName_LIKE_S" value="${filter_and_comboName_LIKE_S }"/>
				</td>
				<td>
					<label>产品名称：</label>
					<input type="text" name="filter_and_productName_LIKE_S" value="${filter_and_productName_LIKE_S }"/>			
				</td>
				<td>
					<label>项目类别:</label>
					<select id="projectTypes" name="filter_and_projectTypes_EQ_S" rel="iselect">
						<option value="">请选择</option>
						<option value="PCT_001" ${filter_and_projectTypes_EQ_S eq "PCT_001" ? "selected" :""}>健康管理-基因</option>
						<option value="PCT_002" ${filter_and_projectTypes_EQ_S eq "PCT_002" ? "selected" :""}>健康管理-癌筛</option>
						<option value="PCT_003" ${filter_and_projectTypes_EQ_S eq "PCT_003" ? "selected" :""}>分子检测</option>
						<option value="PCT_004" ${filter_and_projectTypes_EQ_S eq "PCT_004" ? "selected" :""}>健康管理-无创-生物电</option>
						<option value="PCT_005" ${filter_and_projectTypes_EQ_S eq "PCT_005" ? "selected" :""}>健康管理-无创-微磁</option>
						<option value="PCT_006" ${filter_and_projectTypes_EQ_S eq "PCT_006" ? "selected" :""}>其他</option>
					</select>
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
	<%-- <web:security tag="comboAdmin"> --%>
		<ul class="toolBar">
			<li><a class="add" href="${path }/combo/comboAction!addCombo.action" target="navTab"><span>添加</span></a></li>
			<%-- <li><a class="delete" href="${path }/combo/comboAction!deleteCombo.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> --%>
			<li><a class="edit" onclick="changeCombo()" style="cursor:pointer;"><span>修改</span></a></li>
			<web:exportExcelTag 
					pagerFormId="pagerFindForm" 
					pagerMethodName="findDealByPage" 
					actionAllUrl="org.ymjy.combo.web.ComboAction" 
					informationTableId="exportExcelTable"
					fileName="combo"></web:exportExcelTag>
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="125%" layoutH="90" id="exportExcelTable">
		<thead>
			<tr>
				<th nowrap="nowrap" width="40px;">序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "comboName" columnChName = "套餐名称" >
					<a href="javascript:navSort('order_comboName','desc');">套餐名称</a>
				</th>
				<th nowrap="nowrap" export = "true" columnEnName = "productName" columnChName = "产品名称" >
					<a href="javascript:navSort('order_productName','desc');">产品名称</a>
				</th>
				<th nowrap="nowrap" export ="true" columnEnName="validityDay" columnChName="时效(天)" >时效(天)</th>
				<th nowrap="nowrap" export = "true" columnEnName = "projectTypes" columnChName = "项目类别" >项目类别</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "printType" columnChName = "打印方" >打印方</th> -->
				<th nowrap="nowrap" export = "true" columnEnName = "comboContent" columnChName = "内容" >内容</th>
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th nowrap="nowrap">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="combo" varStatus="status">
			<tr target="sid_user" rel="${ combo.id }">
				<td align="center"><input type="checkbox" name="ids" value="${ combo.id }">${ status.count }</td>
				<td align="center" nowrap="nowrap" width="150px;">
					<a href="${ path }/combo/comboAction!browCombo.action?id=${ combo.id }"  style="color:#0000FF" target="dialog" title="${combo.comboName }">${combo.comboName }</a>
				</td>
				
				<td align="left" nowrap="nowrap" width="300px" >
					<c:if test="${fn:length(combo.productName) > 30}">
						${fn:substring(combo.productName , 0 , 30 ) }<span title="${combo.productName }">...</span>
					</c:if>
					<c:if test="${fn:length(combo.productName) <= 30}">${combo.productName }</c:if>
				</td>
				
				<td align="center" width="80px">${combo.validityDay }</td>
				
				<td align="left" nowrap="nowrap" width="120px;">
					<c:if test="${combo.projectTypes=='PCT_001'}">健康管理-基因</c:if>
					<c:if test="${combo.projectTypes=='PCT_002'}">健康管理-癌筛</c:if>
					<c:if test="${combo.projectTypes=='PCT_003'}">分子检测</c:if>
					<c:if test="${combo.projectTypes=='PCT_004'}">健康管理-无创-生物电</c:if>
					<c:if test="${combo.projectTypes=='PCT_005'}">健康管理-无创-微磁</c:if>
					<c:if test="${combo.projectTypes=='PCT_006'}">其他</c:if>
				</td>
				<%-- 
				<td align="left" nowrap="nowrap" width="120px;">
					<c:if test="${combo.printType=='0'}">远盟打印</c:if>
					<c:if test="${combo.printType=='1'}">基因公司打印</c:if>
					<c:if test="${combo.printType=='2'}">其他机构打印</c:if>
				</td> --%>
				
				<td align="left" nowrap="nowrap" >
					<c:if test="${fn:length(combo.comboContent) > 50}">
						${fn:substring(combo.comboContent , 0 , 50 ) }<span title="${combo.comboContent }">...</span>
					</c:if>
					<c:if test="${fn:length(combo.comboContent) <= 50}">
						${combo.comboContent }
					</c:if>
				</td>
				
				<td align="center" nowrap="nowrap" width="100px;">
					<fmt:formatDate value="${combo.createTime }" pattern="yyyy-MM-dd"/>
				</td>
				
			    <td align="center" nowrap="nowrap" width="100px;">
				<%-- <web:security tag="comboAdmin"> --%>
				  <c:if test="${combo.isDelete eq '0'}">
					<a href="${path }/combo/comboAction!modifyComboAll.action?id=${ combo.id }" target="navTab"><span>修改</span></a>
				  </c:if>
				<%--  </web:security> --%>
				 </td> 
		</c:forEach>
		</tbody>
	</table>
</div>
