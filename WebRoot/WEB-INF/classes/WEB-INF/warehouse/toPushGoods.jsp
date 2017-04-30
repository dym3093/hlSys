<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>
<script type="text/javascript" language="javascript">
function edit(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		var wareId=$("#wareId").val();
		navTab.openTab("ModifyType", "../warehouse/warehouse!toModifyTypeById.action?id="+ids+"&wareId="+wareId+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}

</script>
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${stroeWarehouse.name}仓库类别信息详情</h1>
	 <input type="hidden" name="navTabId" value="${navTabId}"/>
	<div class="divider"></div>
	 <div class="tip"><span>仓库信息</span></div>
		<p>
			<label>仓库名称：</label>
			<input type="hidden" name ="stroeWarehouse.id" id="wareId" value="${stroeWarehouse.id}"/>
			<span>${stroeWarehouse.name}</span>
		</p>
		<p>
			<label>省份：</label>
			<span><hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/></span>
		</p>
		<p>
	    	<label>城市：</label>
	    	<span><hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/></span>
	    </p>
	    <p>
	    	<label>联系人：</label>
	    	<span>${stroeWarehouse.director}</span>
	    </p>
	    <p>
	    	<label>联系电话：</label>
	    	<span>${stroeWarehouse.tel}</span>
	    </p>
	    <p>
	    	<label>详细地址：</label>
	    	<span>${stroeWarehouse.address}</span>
	    </p>
<div class="tip"><span>仓库类别信息清单</span></div>
	<form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/warehouse/warehouse!pushGoods.action'){this.action = '${path}/warehouse/warehouse!pushGoods.action' ;} ;return navTabSearch(this);" action="${path}/warehouse/warehouse!pushGoods.action" method="post">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "remark1" columnChName = "大类" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">大类</th>
				<th  export = "true" columnEnName = "name" columnChName = "名称" >名称</th>
				<th  export = "true" columnEnName = "standards" columnChName = "规格" >规格</th>
				<th  export = "true" columnEnName = "descripe" columnChName = "描述" >描述</th>
				<th  export = "true" columnEnName = "counts" columnChName = "库存数量" >库存数量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="storeType" varStatus="status">
					<tr target="sid_user" rel="${storeType.id }">
					<input type="hidden" name ="storeDetail.warehouseId" value="${storeType.remark}"/>
					<input type="hidden" name ="storeDetail.typeBigCode" value="${storeType.remark1}" />
					<input type="hidden" name ="storeDetail.typeSmallCode" value="${storeType.id}" />
						<td align="center">${status.count}</td>
						<td align="center"><hpin:id2nameDB id='${storeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
						<td align="center"><input type="text" name="storeDetail.typeSmallName" readonly="readonly" value="${storeType.name}"/></td>
						<td align="center">	${storeType.standards}</td>
						<td align="center">	${storeType.descripe}</td>
						<td align="center">
						<c:choose>	
							<c:when test="${ storeAll.count gt 0}">
								${storeAll.count}
							</c:when>
							<c:otherwise>
								0
						    </c:otherwise>
					    </c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
		<div class="subBar" style="float:right; ">
			<!-- 	 <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">下一条</button>
            </div> 
          </div>
         </li> -->
         	<li>
          <div class="button">
            <div class="buttonContent">
              <button type=submit>确定</button>
            </div>
          </div>
        </li>
      </div>
		</form>
	</div> 
</div>
