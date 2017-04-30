<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style type="text/css">
	.typeList{
		height: 270px;
		overflow: auto;
	}
</style>

<script type="text/javascript">

//清除查找框中的所有内容
$("#clearText",$.pdialog.getCurrent()).click(function(){
	$("#pagerFindForm",$.pdialog.getCurrent()).find("input").each(function(){
		if($(this).attr("name")!="configId"){
			$(this).val("");
		}
	});
	$("#pagerFindForm",$.pdialog.getCurrent()).find("select").each(function(){
		$(this).val("");
	});
});	

 var addProduce = function(){
	var data = [];
	var id = "";
	var name = "";
	var num = "";
	var typeBigCode = "";
	var standards = "";
	var cartStart = "";
	var cardEnd = "";
	var cardCount = "";
	
	$("input[name='ids']:checked").each(function(i){
		id = $(this).val();
		var tdArr = $(this).parent("td").siblings("td");
		name = tdArr.eq(1).text().trim();
	 	num = tdArr.eq(2).text().trim();
	 	typeBigCode = tdArr.eq(3).text().trim();
	 	standards = tdArr.eq(4).text().trim();
	 	cartStart = isEmpty(tdArr.eq(5).text().trim())?"":tdArr.eq(5).text().trim();
	 	cardEnd = isEmpty(tdArr.eq(6).text().trim())?"":tdArr.eq(6).text().trim();
		cardCount = isEmpty(tdArr.eq(7).text().trim())?"":tdArr.eq(7).text().trim();
	 	
//		console.log("cartStart: "+cartStart+" , cardEnd: "+cardEnd+" , cardCount: "+cardCount);
		
	 	var obj = {
	 		'id': id,
	 		'name':name,
	 		'num':num,
	 		'typeBigCode':typeBigCode,
	 		'standards':standards,
	 		'cardStart':cartStart,
	 		'cardEnd':cardEnd,
	 		'cardCount':cardCount
	 	};
//	 	console.log("[obj] cartStart: "+obj.cartStart+" , cardEnd: "+obj.cardEnd+" , cardCount: "+obj.cardCount);
	 	data.push(obj);
	});
	
	if(data.length<1){
		alertMsg.error("请选择数据!");
		return;
	}
	
	$.pdialog.closeCurrent();
	var parent = $("#navTabId").get(0);
	//直接调用navTab中的方法;
	addTr(data); 

 };
 
 var isEmpty = function(obj){
	return obj==""||obj==null||obj==undefined||obj.trim().length==0;  
 };
 
</script>

<html>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }" />
	<form onsubmit="if(this.action != '${path }/warehouse/storeApplication!toStoreProduceDetail.action')
				{this.action = '${path }/warehouse/storeApplication!toStoreProduceDetail.action' ;} ;return dialogSearch(this);" 
		action="${path }/warehouse/storeApplication!toStoreProduceDetail.action"  method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>物品名称：</label>
					<input type="text"  name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>	
				<td>
					<label>物品品类：</label>
					<select id="remark1" name="filter_and_remark1_LIKE_S"
							rel="iselect" loadUrl="${path}/warehouse/storeApplication!getThingsType.action?parentDictid=10107"
						<option value="${filter_and_remark1_LIKE_S}"></option>
					</select>
				</td>	
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive" style="margin-left:20px;"><div class="buttonContent"><button type="button" id="clearText" >重置</button></div></div>
				</td>
			</tr>
		
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<div class="buttonActive" >
			<div class="buttonContent" style="margin-right:20px;"><button type="button" onclick="addProduce();">确定</button></div>
		</div>
		<div class="buttonActive">
			<div class="buttonContent" ><button type="button" onclick="$.pdialog.closeCurrent();">取消</button></div>
		</div>
		<jsp:include page="/common/pagination_dialog.jsp" />
	</div>
		<div class="typeList">
		<table class="list" width="100%" height="100%">
			<thead>
				<tr>
					<th width="12%">全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
					<th width="10%;" export = "true" columnEnName = "remark1" columnChName = "物品品类" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">物品品类</th>
					<th  width="60%"><nobr>物品名称</nobr></th>
					<th><nobr>库存数量</nobr></th>
					<th style="display:none;"><nobr>大类码</nobr></th>
					<th style="display:none;"><nobr>规格</nobr></th>
					<th style="display:none;"><nobr>卡号段开始</nobr></th>
					<th style="display:none;"><nobr>卡号段截至</nobr></th>
					<th style="display:none;"><nobr>卡号数量</nobr></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ page.results }" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.id }">
					<td align="center">
						<c:if test="${currentUser.accountName!='parkson'}">
							<input type="checkbox" name="ids" value="${entity.id}">
						</c:if>
						${status.count}
					</td> 
					<td align="center"><hpin:id2nameDB id='${entity.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
					<td align="center">${entity.name}</td>
					<td align="center" >${entity.num}</td>
					<td align="center" style="display:none;">${ entity.remark1}</td>
					<td align="center" style="display:none;">${ entity.standards}</td>
					<td align="center" style="display:none;">${ entity.cardStart}</td>
					<td align="center" style="display:none;">${ entity.cardEnd}</td>
					<td align="center" style="display:none;">${ entity.cardCount}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</html>