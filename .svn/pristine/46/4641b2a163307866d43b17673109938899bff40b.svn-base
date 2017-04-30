<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/warehouse!listStoreType.action'){this.action = '${path }/warehouse/warehouse!listStoreType.action' ;} ;return navTabSearch(this);" 
	action="${ path }/warehouse/warehouse!listStoreType.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
		<input type="hidden" name="navTabId" value="${storeWarehouse.id}"/>
			<tr>
				<td><label>仓库：</label></td>
				<td><input type="text" name="filter_and_deleteUserName_LIKE_S" value="${filter_and_deleteUserName_LIKE_S}"/></td>
				<td><label>名称：</label></td>
				<td><input type="text" name="filter_and_name_LIKE_S"  value="${filter_and_name_LIKE_S}"/></td>
				<td><label>类型：</label></td>
				<td><select id="remark1"  name="filter_and_remark1_EQ_S"  rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107">
						<option value="${filter_and_remark1_EQ_S}"></option>
					</select>
				</td>
			<%--</tr>
			 <tr> 
				<td align="left">
		  		<label>省 份：</label>
          		<select id="province" name="filter_and_province_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  			<option value="${filter_and_province_EQ_S}" ></option>
		  		</select>
	       	 	</td> 
	       	 	<td align="left">
	       	 	     <label>城 市：</label>
			         <select id="city1" name="filter_and_city_EQ_S" rel="iselect">
			         	<option value="${filter_and_city_EQ_S}"></option>
			        </select>
	       	 	</td> 
				--%>
				<td>
				    <div class="subBar block_btn">
						<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			 <web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findTypesByPage" 
						actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
						informationTableId="exportExcelTable"
						fileName="StoreType">
			</web:exportExcelTag> 
		</ul>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="99%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th width="5%;">序号</th>
				<th nowrap="nowrap" width="10%;" export = "true" columnEnName = "deleteUserName" columnChName = "仓库" >仓库</th>
				<th nowrap="nowrap" width="10%;" export = "true" columnEnName = "remark1" columnChName = "类别" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">类别</th>
				<th nowrap="nowrap" width="15%;" export = "true" columnEnName = "name" columnChName = "名称" >名称</th>
				<th nowrap="nowrap" width="10%;" export = "true" columnEnName = "remark2" columnChName = "省份" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省份</th>
				<th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "remark3" columnChName = "城市"  id2NameBeanId = "org.hpin.base.region.dao.RegionDao">城市</th>
				<th nowrap="nowrap" width="20%;"  export = "true" columnEnName = "standards" columnChName = "规格" >规格</th>
				<th nowrap="nowrap" width="20%;"  export = "true" columnEnName = "descripe" columnChName = "描述" >描述</th>
				<th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "createUserName" columnChName = "创建人" >创建人</th>
				<!-- <th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "tel" columnChName = "联系电话" >联系电话</th>  -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeType" varStatus="status">
			<tr target="sid_user" rel="${storeType.id}">
				<td align="center">${status.count}</td>
				<td align="center" nowrap="nowrap">
					<%-- <a href="${path}/warehouse/warehouse!browstoreType.action?id=${storeType.id}" 
				    width="1100" height="480" style="color:#0000FF" target="navTab" title="${storeType.deleteUserName}"></a> --%>
				    ${storeType.deleteUserName}
					<%-- <c:set var="str" value="${storeType.remark}" /> 
					<c:choose> 
						<c:when test="${fn:length(str) > 18}"> 
							<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
							<c:out value="${str}" /> 
						</c:otherwise> 
					</c:choose> --%>
					
				</td>
				<td align="center" nowrap="nowrap">
					<hpin:id2nameDB id='${storeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/>
				</td>
				<td align="center" nowrap="nowrap">
					<%-- <a href="${path}/warehouse/warehouse!browstoreType.action?id=${storeType.id}" 
				    width="1100" height="480" style="color:#0000FF" target="navTab" title="${storeType.name}"></a> --%>
					<c:set var="str1" value="${storeType.name}" /> 
					<c:choose> 
						<c:when test="${fn:length(str1) > 18}"> 
							<c:out value="${fn:substring(str1, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
							<c:out value="${str1}" /> 
						</c:otherwise> 
					</c:choose>
				</td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${storeType.remark2}"/></td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${storeType.remark3}"/></td>
				<td align="center" nowrap="nowrap">${storeType.standards}</td>
				<td align="center" nowrap="nowrap">${storeType.descripe}</td> 
				<td align="center" nowrap="nowrap">${storeType.createUserName}</td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
