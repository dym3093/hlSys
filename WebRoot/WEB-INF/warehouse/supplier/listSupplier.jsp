<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

$(document).ready(function(){
	
});

//删除
function deleteSupplier(supplierId){
	if(supplierId=="undefined"||supplierId==""){
		alertMsg.error("删除失败！");
	}else{
		alertMsg.confirm("确认删除?",{okCall:function(){
			$.ajax({
				type : "post",
				cache : false,
				dataType : "json",
				url : "${path}/warehouse/supplier!deleteSupplier.action",
				data : {'id':supplierId},
				success : function(data){
					if(data.status=="200"){
						alertMsg.correct(data.message);
						navTab.refreshCurrentTab();
						//$.pdialog.closeCurrent();
						return navTabSearch(this); 
					}else if(data.status=="300"){
						alertMsg.error(data.message);
					}
				},
				error : function(data){
					alert("服务发生异常，请稍后再试！");
				}
			});
		}});
		
		
	}
	
}


//修改
function updateSupplier(){
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alertMsg.warn("请选择你要修改的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn('只能选择一条信息进行修改！');
		return;
	} else {
		var idArr = ids.split(",");//数据结构为：erpPrepCustomerId,erpCustomerId
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		alert(navTabId);
		navTab.openTab("updateSupplier",
				"${path}/warehouse/supplier!toUpdateSupplier.action?id=" + idArr[0]
						+ "&navTabId=" + navTabId, {
					title : "修改供货商信息",
					fresh : false,
					data : {}
				});
		
	}
}

function updateSupplier(){
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
			});
	if (count == 0) {
		alertMsg.warn("请选择你要修改的信息！");
		return;
	} else if (count > 1) {
		alertMsg.warn('只能选择一条信息进行修改！');
		return;
	} else {
		var idArr = ids.split(",");//数据结构为：erpPrepCustomerId,erpCustomerId
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("updateSupplier",
				"${path}/warehouse/supplier!toUpdateSupplier.action?id=" + idArr[0]
						+ "&navTabId=" + navTabId, {
					title : "修改供货商信息",
					fresh : false,
					data : {}
				});
		
	}
}

function addSupplier(){
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
			function(i, n) {
				ids = n.value;
				count += count + 1;
				status = $(this).parent().next().text();
			});
		var idArr = ids.split(",");//数据结构为：erpPrepCustomerId,erpCustomerId
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("updateSupplier",
				"${path}/warehouse/supplier!toAddSupplier.action?navTabId=" + navTabId, {
					title : "增加供货商信息",
					fresh : false,
					data : {}
				});
		
}

function exportExcelSupplier(){
	var supplierName = $("#pSupplierName").val();
	var linkName = $("#pLinkName").val();
	var province = $("select[name='filter_and_provice_EQ_S'][option:selected]",navTab.getCurrentPanel()).val();
	var city1 = $("select[name='filter_and_city_EQ_S'][option:selected]",navTab.getCurrentPanel()).val();
	if(province=="undefined"){
		province="";
	}
	if(city1=="undefined"){
		city1="";
	}
	
	var queryString = "province=" + province+"&city="+city1+"&supplierName="+supplierName+"&linkName="+linkName;
	window.open("${path}/warehouse/supplier!exportExcelSupplier.action?"+queryString);
}

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/warehouse/supplier!listSupplier.action')
	{this.action = '${path}/warehouse/supplier!listSupplier.action' ;} ;return navTabSearch(this);" 
	action="${path}/warehouse/supplier!listSupplier.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>供货商名称：</label> 
					<input type="text" id="pSupplierName" name="filter_and_supplierName_LIKE_S" value="${filter_and_supplierName_LIKE_S}"/>
				</td>  
				<td>
					<label>联系人姓名：</label> 
					<input type="text" id="pLinkName" name="filter_and_linkName_LIKE_S"  value="${filter_and_linkName_LIKE_S}"/>
				</td>
			</tr>
			<tr>
				<td><label>省 份：</label>
				<select id="province"  name="filter_and_provice_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  			<option value="${filter_and_provice_EQ_S}" ></option>
		  		    </select>
	       	 	</td> 
	       	 	<td><label>城 市：</label>
			         <select id="city1" name="filter_and_city_EQ_S" rel="iselect">
			         	<option value="${filter_and_city_EQ_S}"></option>
			        </select>
	       	 	</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td>
					<div style="margin-left:60px;" class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
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
			<%-- <li><a class="add"	href="${path}/warehouse/supplier!toAddSupplier.action" target="navTab"
					rel="addEvents"><span>添加</span></a></li> --%>
			<li><a class="add" onclick="addSupplier()" href="javascript:void(0);"><span>添加</span></a></li>
			<li><a class="edit" onclick="updateSupplier()" href="javascript:void(0);"><span>修改</span></a></li>
			<c:if test="${ not empty page.results}">
				<li><a class="icon" onclick="exportExcelSupplier()" href="javascript:void(0);"><span>导出Excel</span></a></li>
			</c:if>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th> -->
				<th>序号</th>
                <!--<th  export = "true" columnEnName = "id" columnChName = "id" >id</th>-->
				<th  export = "true" columnEnName = "supplierName" columnChName = "供货商名称" >供货商名称</th>
				<th  export = "true" columnEnName = "provice" columnChName = "省份" >省份</th>
				<th  export = "true" columnEnName = "city" columnChName = "城市">城市</th>
				<th  export = "true" columnEnName = "serviceArea" columnChName = "服务范围">服务范围</th>
				<th  export = "true" columnEnName = "description" columnChName = "描述" >描述</th>
				<th  export = "true" columnEnName = "linkMan" columnChName = "联系人" >联系人</th>
				<th  export = "true" columnEnName = "linkPhone" columnChName = "联系电话" >联系电话</th>
				<th  export = "true" columnEnName = "" columnChName = "操作" width="50">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }" >
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.id}">
						${status.count}
					</td> 
					<td align="center">${entity.supplierName}</td>
					<td align="center">	
						<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.provice }" />
					</td>
					<td align="center">
						<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.city }" />
					</td>
					<td align="center">	${entity.serviceArea}</td>
					<td align="center">	${entity.description}</td>
					<td align="center">	${entity.linkMan}</td>
					<td align="center">	${entity.linkPhone}</td>
					<td align="center">	<div class="panelBar"><ul class="toolBar"><li><a class="delete" onclick="deleteSupplier('${entity.id}')" ><span>删除</span></a></li></ul></div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 



