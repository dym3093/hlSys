<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
  <style type="text/css">
	   .suggest_out{background-color:#FFFFFF;color:black}
	   .suggest_over{background-color:#ECE9D8}
    </style> 
<script type="text/javascript">
	
	//派发
	var pullGoods = function(storeTypeId, typeBigCode){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addSettlement", "${path }/warehouse/warehouse!toPullGoodsByTypeId.action", 
				{ title:"出货", fresh:false, data:{"navTabId":navTabId, "type":"pullGoods", "id":storeTypeId, "remark1":typeBigCode} });
	};
  
</script>
 
<div class="pageHeader">
	<form class="pageForm num1_fom required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/warehouse/storeApplication!pullStoreApplication.action?id=${storeApplication.id}" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <input type = "hidden" name = "storeApplication.id" value="${storeApplication.id }" />
    <div class="tip"><span>申请信息</span></div>
    <table class="num1_tab num2_lab" layoutH="170" >
        <tr>
            <td><label>总公司名称：</label></td>
            <td><input name="storeApplication.ownedCompany" type="text" readonly="readonly" value="${ storeApplication.ownedCompany }" /></td>
            <td> <label>支公司名称：</label></td>
            <td><input name="storeApplication.bannyCompanyName" type="text" readonly="readonly" value="${ storeApplication.bannyCompanyName }" /></td>
            <td width="120px"><label>收件人：</label></td>
            <td><input name="storeApplication.receiveName" type="text" readonly="readonly" value="${ storeApplication.receiveName }" /></td>
        </tr>
        <tr>
            <td><label>项目负责人：</label></td>
            <td><input name="storeApplication.remark1" type="text" readonly="readonly" value="${ storeApplication.remark1 }" /></td>
            <td><label>项目归属：</label></td>
            <td><input name="storeApplication.remark2" type="text" readonly="readonly" value="${ storeApplication.remark2 }" /></td>
            <td><label>项目编码：</label></td>
            <td><input name="storeApplication.remark3" type="text" readonly="readonly" value="${ storeApplication.remark3 }" /></td>
        </tr>
        <tr>
            <td><label>联系电话：</label></td>
            <td><input name="storeApplication.receiveTel" type="text"  readonly="readonly" value="${ storeApplication.receiveTel }" /></td>
            <td><label>详细地址：</label></td>
            <td><input name="storeApplication.address" type="text" readonly="readonly" value="${ storeApplication.address }" /></td>
            <td><label>期望日期：</label></td>
            <td> <input type="text" name="storeApplication.useDate" readonly="readonly" style="width:180px;" onFocus="WdatePicker({minDate:'${useDate}'})"  readonly="readonly" value="${storeApplication.useDate}"/></td>
        </tr>
        <tr>
            <td><label style="height: auto">派往角色：</label></td>
            <td><input name="storeApplication.businessId" type="hidden" value="40289b6a5206079d01520619b31e0008"/>
                <input name="storeApplication.businessName" type="hidden" value="远盟本部"/>
                <span style="color:red">远盟基因业务部</span>
            </td>
            <td><label style="height:60px;line-height:60px;">需求说明：</label></td>
            <td colspan="3"><textarea cols="35" ows="2" style="width:570px;" readonly="readonly" name="storeApplication.requires" >${storeApplication.requires}</textarea></td>
        </tr>
</table>
    
  </form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<div>
		<table class="list" width="100%" layoutH="170">
			<thead>
				<tr>
					<th width="60">序号</th>
					<th width="300" columnEnName="prdouceName"  columnChName="品类名称" >品类名称</th>
					<th width="200" columnEnName="applyNum" columnChName="申请数量" >申请数量</th>
					<th width="200" columnEnName="standards" columnChName="规格" >规格</th>
					<th width="100" columnEnName="operation" columnChName="操作" >操作</th>
				</tr>					
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${entity.id}">
							</c:if>
							${status.count}
						</td> 
						<td align="center">${entity.prdouceName }</td>
						<td align="center">${entity.applyNum }</td>
						<td align="center">${entity.standards }</td>
						<td align="center">
							<c:choose>
								<c:when test="${entity.status==0 }">
									<%-- <a class="delete" id="push" href="${path }/warehouse/warehouse!toPullGoodsByTypeId.action?id=${entity.storeTypeId}" target="navTab"><span>派发</span></a> --%>
									<input type="button" value="派发" onclick="pullGoods('${entity.storeTypeId}','${entity.typeBigCode }')" />
								</c:when>
							   <c:when test="${entity.status==1 }">
									<span style="color:#00ff00">部分处理</span>
								</c:when>
							    <c:when test="${entity.status==2 }">
									<span style="color:#0000ff">已派发</span>
								</c:when>
								<c:otherwise>
									<span style="color:#ff0000">异常</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>							
			</tbody>
		</table>
	</div>
</div>
<script>
  $(".num1_tab").parent().css("height","130px")
  $(".num1_fom").css("min-height","170px")
  $(".num1_fom").css("overflow","hidden")
</script>

