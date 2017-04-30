<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">

function colseWindow(){
	navTab.closeCurrentTab();
}

</script>
<html>
<head>
	
</head>
<body>
<!-- <div class="tip"><span>付款</span></div> -->
<div class="tip"><span>添加供货商</span></div>
<div class="pageHeader" style="margin-left: 180px;">
	<form class="pageForm required-validate" 
	action="${path}/warehouse/supplier!updateSupplier.action"  onsubmit="return validateCallback(this, navTabAjaxDone);"
	class="pageForm" method="post" id="pagerFindForm">
	
	<%-- <form id="pagerFindForm" onsubmit="if(this.action != '${path}/warehouse/supplier!addSupplier.action')
	{this.action = '${path}/warehouse/supplier!addSupplier.action' ;} ;return navTabSearch(this);" 
	action="${path}/warehouse/supplier!addSupplier.action" method="post"  rel="pagerForm" id="pagerFindForm"> --%>
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>供货商名称：</label> 
					<input type="text" name="supplier.supplierName" value="${supplier.supplierName}" class="required"/>
					
					<input type="hidden" name="supplier.id" value="${supplier.id}"/>
					<input type="hidden" name="supplier.createUserId" value="${supplier.createUserId}"/>
					<input type="hidden" name="supplier.createTime" value="${supplier.createTime}"/>
					<input type="hidden" name="supplier.isDeleted" value="${supplier.isDeleted}"/>
					<input type="hidden" name="navTabId" value="${navTabId}"/>
				</td>  
				<td>
					<label>联系人：</label> 
					<input type="text" name="supplier.linkMan" value="${supplier.linkMan} " class="required"/>
				</td>
			</tr>
			<tr>
	       	 	<td><label>省 份：</label>
					<select id="province"  name="supplier.provice" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  			<option value="${supplier.provice}" ></option>
		  		    </select>
	       	 	</td> 
	       	 	<td><label>城 市：</label>
			         <select id="city1" name="supplier.city" rel="iselect">
			         	<option value="${supplier.city}"></option>
			        </select>
	       	 	</td>
	       	 </tr>	
			<tr>
				<td>
					<label>手机号：</label> 
					<input type="text" name="supplier.linkPhone" value="${supplier.linkPhone}" class="required"/>
				</td>
				<td><label>详细地址：</label> 
					<input type="text" name="supplier.address" value="${supplier.address}" />
				</td>
			</tr>
			<tr>
				<td>
					<label>服务范围：</label> 
					<input type="text" name="supplier.serviceArea" value="${supplier.serviceArea}" />
				</td>
				<td>
					<label>描述：</label> 
					<input type="text" name="supplier.description" value="${supplier.description}" />
				</td>
			</tr>
			<tr>
				<td>
					<div class="buttonActive" style="margin-left: 300px;"><div class="buttonContent"><button type="submit" id="formSumbitButton" >保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent" id="formSumbitOnce" ><button type="button" onClick="colseWindow()">取消</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>

</body>
</html>