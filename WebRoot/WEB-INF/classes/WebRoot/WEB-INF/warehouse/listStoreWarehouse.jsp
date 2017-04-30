<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/warehouse!listStoreWarehouse.action'){this.action = '${path }/warehouse/warehouse!listStoreWarehouse.action' ;} ;return navTabSearch(this);" 
	action="${ path }/warehouse/warehouse!listStoreWarehouse.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
		<input type="hidden" name="navTabId" value="${storeWarehouse.id}"/>
			<tr>
				<td><label>仓库名称：</label></td>
				<td><input type="text" name="params.name"  value="${params.name}"/></td>
				<td><label>联系人：</label></td>
				<td><input type="text" name="params.director" value="${params.director}"/></td>
				<td><label>联系电话：</label></td>
				<td><input type="text" name="params.tel" value="${params.tel}"/></td>
			</tr>
			<tr> 
				<td><label>省 份：</label></td>
				<td><select id="province"  name="params.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city1" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  			<option value="${params.province}" ></option>
		  		    </select>
	       	 	</td> 
	       	 	<td><label>城 市：</label></td>
				<td>
			         <select id="city1" name="params.city" rel="iselect">
			         	<option value="${params.city}"></option>
			        </select>
	       	 	</td>
				<td style="padding-left:40px;">
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
	<%-- <web:security tag="customerRelationShipAdmin"> --%>
		<ul class="toolBar">
			<li><a class="add" href="${path }/warehouse/warehouse!addWarehouse.action" target="navTab"><span>添加</span></a></li> 
			<li><a name="editWarehouse" class="edit" href="javascript:void(0);" ><span>修改</span></a></li> 
			<%--<li><a class="delete" href="${path }/warehouse/warehouse!deleteWarehouse.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li> --%>
			<li><a class="icon" href="javascript:void(0);" onclick="inWarehouse();"><span>入库</span></a></li> 
			<web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findByPage" 
						actionAllUrl="org.hpin.warehouse.web.StoreWarehouseAction" 
						informationTableId="exportExcelTable"
						fileName="StoreWarehouse">
			</web:exportExcelTag> 
		</ul>
	 <%--</web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th width="5%;">序号</th>
				<th nowrap="nowrap" width="15%;" export = "true" columnEnName = "name" columnChName = "仓库名称" >仓库名称</th>
				<th nowrap="nowrap" width="10%;" export = "true" columnEnName = "province" columnChName = "省份" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省份</th>
				<th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "city" columnChName = "城市"  id2NameBeanId = "org.hpin.base.region.dao.RegionDao">城市</th>
				<th nowrap="nowrap" width="20%;"  export = "true" columnEnName = "remark1" columnChName = "服务范围" >服务范围</th>
				<th nowrap="nowrap" width="20%;"  export = "true" columnEnName = "remark2" columnChName = "描述" >描述</th>
				<th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "director" columnChName = "联系人" >联系人</th>
				<th nowrap="nowrap" width="10%;"  export = "true" columnEnName = "tel" columnChName = "联系电话" >联系电话</th> 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeWarehouse" varStatus="status">
			<tr target="sid_user" rel="${storeWarehouse.id}">
				<td align="center"><input type="checkbox" name="ids" value="${storeWarehouse.id}">${status.count}</td>
				<td align="center" nowrap="nowrap">
				<%-- ${path}/warehouse/warehouse!browStoreAll.action?id=${storeWarehouse.id} --%>
					<a href="${path}/warehouse/warehouse!viewDetailed.action?id=${storeWarehouse.id}" 
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
				<td align="center" nowrap="nowrap">${storeWarehouse.remark1}</td> 
				<td align="center" nowrap="nowrap">${storeWarehouse.remark2}</td>
				<td align="center" nowrap="nowrap">${storeWarehouse.director}</td>
				<td align="center" nowrap="nowrap">${storeWarehouse.tel}</td> 
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
	function inWarehouse() {
		
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
			
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要入库的仓库数据！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条信息进行入库！");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("inWarehouse", "${path }/warehouse/storegeIn!edit.action?warehouseId="+ids+"&navTabId="+navTabId, { title:"产品入库", fresh:false, data:{} });
	}
	
$(function() {
	$("a[name='editWarehouse']", navTab.getCurrentPanel()).on("click", function() {
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
			
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要修改的数据！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条信息进行修改！");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("inWarehouse", "${path}/warehouse/warehouse!toModifyStoreWarehouseById.action?id="+ids+"&navTabId="+navTabId, { title:"仓库修改", fresh:false, data:{} });
	});
});
</script>
