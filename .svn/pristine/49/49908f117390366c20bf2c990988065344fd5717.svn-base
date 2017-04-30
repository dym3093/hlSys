<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/warehouse!listStoreWarehouseAll.action'){this.action = '${path }/warehouse/warehouse!listStoreWarehouseAll.action' ;} ;return navTabSearch(this);" 
	action="${ path }/warehouse/warehouse!listStoreWarehouseAll.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
		<input type="hidden" name="navTabId" value="${storeWarehouse.id}"/>
			<tr>
				<td>
				<label>仓库名称：</label>
					<input type="text" name="filter_and_nameLIKE_S"  value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<label>联系人：</label><input type="text" name="filter_and_director_LIKE_S" value="${filter_and_director_LIKE_S}"/>			
				</td>
				<td>
					<label>联系电话：</label><input type="text" name="filter_and_tel_LIKE_S" value="${filter_and_tel_LIKE_S}"/>
				</td>
			</tr>
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
	 <%--<web:security tag="customerRelationShipAdmin"> --%>
		<ul class="toolBar">
			<li><a class="add" href="${path }/warehouse/warehouse!addWarehouse.action" target="navTab"><span>添加</span></a></li> 
			 <%-- <li><a class="edit" href="${path}/warehouse/warehouse!modifyWarehouse.action?id={sid_user}" target="navTab"><span>修改</span></a></li> --%>
			 <li><a class="delete" href="${path }/warehouse/warehouse!deleteWarehouse.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> 
			 <web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findByPage" 
						actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
						informationTableId="exportExcelTable"
						fileName="StoreWarehouse">
			</web:exportExcelTag> 
		</ul>
	<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap="nowrap" export = "true" columnEnName = "name" columnChName = "仓库名称" >仓库名称</th>
				<th nowrap="nowrap" export = "true" columnEnName = "province" columnChName = "省份" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省份</th>
				<th nowrap="nowrap" export = "true" columnEnName = "city" columnChName = "城市"  id2NameBeanId = "org.hpin.base.region.dao.RegionDao">城市</th>
				<th nowrap="nowrap" export = "true" columnEnName = "director" columnChName = "联系人" >联系人</th>
				<th nowrap="nowrap" export = "true" columnEnName = "tel" columnChName = "联系电话" >联系电话</th> 
				<th nowrap="nowrap" export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
				<th nowrap="nowrap" export = "true" columnEnName = "" columnChName = "" >操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeWarehouse" varStatus="status">
			<tr target="sid_user" rel="${storeWarehouse.id}">
				<td align="center"><input type="checkbox" name="ids" value="${storeWarehouse.id}">${status.count}</td>
				<td align="center" nowrap="nowrap">
					<a href="${path}/warehouse/warehouse!browStoreWarehouse.action?id=${storeWarehouse.id}" 
				    width="1100" height="480" style="color:#0000FF" target="navTab" title="${storeWarehouse.name}">
					<c:set var="str" value="${storeWarehouse.name}" /> 
					<c:choose> 
						<c:when test="${fn:length(str) > 18}"> 
							<c:out value="${fn:substring(str, 0, 18)}......" /> 
						</c:when> 
						<c:otherwise> 
							<c:out value="${str}" /> 
						</c:otherwise> 
					</c:choose>
					</a>
				</td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${storeWarehouse.province}"/></td>
				<td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${storeWarehouse.city}"/></td>
				<td align="center" nowrap="nowrap">${storeWarehouse.director}</td>
				<td align="center" nowrap="nowrap">${storeWarehouse.tel}</td> 
				<td align="center" nowrap="nowrap">${fn:substring(storeWarehouse.createTime,0,19)}</td>
				<td align="center"  nowrap="nowrap">
						<div class="panelBar">
							<ul class="toolBar">
								<li><a class="icon" href="${path}/warehouse/warehouse!browStoreWarehouse.action?id=${storeWarehouse.id}&navTabId=${storeWarehouse.id}" target="navTab" ><span>类别维护</span></a></li>
								<li><a class="edit" href="${path}/warehouse/warehouse!browStoreWarehouseAll.action?id=${storeWarehouse.id}" target="navTab"><span>查看库存</span></a></li>
								<%-- <li><a class="add" href="${path}/warehouse/warehouse!toPushGoods.action?id=${storeWarehouse.id}" target="navTab" rel="add"><span>入库</span></a></li>
								<li><a class="delete" href="${path}/events/erpEvents!delOneEvents.action?id=${storeWarehouse.id}"  target="navTab" rel="delete"><span>出库</span></a></li> --%>
							</ul>
						</div>
				</td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
