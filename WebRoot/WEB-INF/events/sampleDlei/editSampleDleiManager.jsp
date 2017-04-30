<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
.batchNum {}
</style>
<div class="pageContent">
	<form id="sampleDeli_edit_from_id" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/sampleDlei!saveOrUpdate.action" method="post">
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    <input type="hidden" name="sampleDleiManager.id" id="sampleDleiManagerId" value="${sampleDleiManager.id }"/>
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">场次信息管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>样本快递费用录入</span></div>
			<div class="divider"></div>
			<table >
				<tbody>
					<tr>
					    <td><label>快递单号：</label></td>
					    <td>
					    	<input type="text" id="deliNum" <c:if test="${sampleDleiManager.id != null && sampleDleiManager.id != ''}">readonly="readonly"</c:if> name="sampleDleiManager.deliNum" value="${sampleDleiManager.deliNum }" class="required textInput"/>
					    </td>
					    <td><label>快递公司：</label></td>
					    <td>
					    	<select type="text" name="sampleDleiManager.expressCompanyId" id="expressCompanyId" style="width: 194px; margin-left: 4px;">
						    	<c:forEach items="${exprComps }" var="comp">
									<option value="${comp.id }" ${sampleDleiManager.expressCompanyId == comp.id ? "selected":"" }>${comp.companyName }</option>
								</c:forEach>
					    	</select>
					    </td>
					    <td><label>重量(Kg)：</label></td>
					    <td>
					    	<input type="text" id="weight" name="sampleDleiManager.weight" value="${sampleDleiManager.weight }"  class="required number textInput"/>
					    </td>
					   
					</tr>
					<tr>
						<td><label>类别：</label></td>
					    <td>
					    	<select name="sampleDleiManager.sampleType" style="width: 194px; margin-left: 5px;">
					    		<option value="TYPE_01">寄样</option>
					    		<option value="TYPE_02">收样</option>
					    	</select>
					    </td>
					    <td><label>收发件日期：</label></td>
					    <td>
					    	<input readonly="readonly" type="text" id="receliveSendDateBak" name="sampleDleiManager.receliveSendDateBak" value="<fmt:formatDate value="${sampleDleiManager.receliveSendDate }" pattern="yyyy-MM-dd"/>"  class="date required"/>
					    	<a class="inputDateButton" href="javascript:;">选择</a>
					    </td>
					    <td><label>发票有无：</label></td>
					    <td><input type="radio" name="sampleDleiManager.isBill" value="0" ${sampleDleiManager.isBill == '0' ? "checked":"checked"}/>有&emsp;
					    	<input type="radio" name="sampleDleiManager.isBill" value="1" ${sampleDleiManager.isBill == '1' ? "checked":""}/>无</td>
					</tr>
					<tr>
						<td><label>费用(元)：</label></td>
					    <td><input type="text" id="costPriceBak" name="sampleDleiManager.costPriceBak" value="${sampleDleiManager.costPrice }"  class="textInput required number"/></td>
					</tr>
				</tbody>
			</table>
			<div class="tip"><span>项目关联</span></div>
			<table class="list nowrap itemDetail" addButton="新增关联项目" style="clear: both;" width="100%">
				<thead>
					<tr>
						<th type="text" name="proRels[#index#].batchNum" filedStyle="width: 100px" fieldClass="required batchNum">批次号</th>
						<th type="lookup" filedStyle="width: 120px" callback="pro_callback" fieldClass="required" lookupgroup="proRels[#index#].ship" bringBackFlag="true" bringBackId="id" bringBackName="branchCommanyName" lookupPk="id" lookupurl="${ path }/events/sampleDlei!findBranchComplany.action" >支公司</th>
						<th type="lookup" filedStyle="width: 120px" fieldClass="required onblurClass" lookupgroup="proRels[#index#].shipPro" bringBackFlag="true" bringBackId="id" bringBackName="projectCode" lookupPk="id" lookupurl="${ path }/resource/customerRelationShip!customerRelshipPro.action">项目编码</th>
						<th type="text" name="proRels[#index#].shipPro.projectName" filedStyle="width: 100px;" fieldAttrs="{'readonly':'readonly'}" fieldAttrs="{'readonly':'readonly'}"  fieldClass="required" >项目名称</th>
						<th type="text" name="proRels[#index#].shipPro.projectOwner" filedStyle="width: 80px;" fieldAttrs="{'readonly':'readonly'}" fieldAttrs="{'readonly':'readonly'}"  fieldClass="required">项目负责人</th>
						<th type="text" name="proRels[#index#].shipPro.linkName" fieldAttrs="{'readonly' : 'readonly'}" filedStyle="width: 90px" fieldClass="required">远盟对接人</th>
						<th type="text" name="proRels[#index#].personNum" filedStyle="width: 80px" fieldClass="required number">人数</th>
						<th type="text" name="proRels[#index#].priceBak" filedStyle="width: 100px"  fieldAttrs="{'readonly':'readonly'}">单价</th>
						<th type="del" width="30px">操作</th>
					</tr>
				</thead>
			<tbody id="proReals_tboby_id">
				<c:forEach items="${proRels }" var="pro" varStatus="stat">
					<tr>
						<td>
							<input name="proRels[${stat.index }].batchNum" class="required textInput" style="width: 100px;" type="text" size="12" value="${proRels[stat.index].batchNum }">
						</td>
						<td>
							<input name="proRels[${stat.index }].ship.id" type="hidden" bringbackname="proRels[${stat.index }].ship.id" value="${proRels[stat.index].customerRelationShipId }">
							<input class="required textInput readonly" type="text" size="12" readonly="readonly" lookuppk="id" bringbackname="proRels[${stat.index }].ship.branchCommanyName" value="${proRels[stat.index].customerRelationShipName }">
							<a title="查找带回" class="btnLook" href="${ path }/events/sampleDlei!findBranchComplany.action" lookuppk="id" lookupgroup="proRels[0].ship" callback="pro_callback">查找带回</a>
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.id" type="hidden" bringbackname="proRels[${stat.index }].shipPro.id" value="${proRels[stat.index].customerRelationShipProId}">
							<input class="required onblurClass textInput readonly" type="text" size="12" readonly="readonly" lookuppk="id" bringbackname="proRels[${stat.index }].shipPro.projectCode" value="${proRels[stat.index].projectCode}">
							<a title="查找带回" class="btnLook" href="${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId=${proRels[stat.index].customerRelationShipId }" lookuppk="id" lookupgroup="proRels[${stat.index }].shipPro" callback="">查找带回</a>
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.projectName" class="required textInput readonly" style="width: 100px;" type="text" readonly="readonly" value="${proRels[stat.index].projectName }" bringbackname="proRels[${stat.index }].shipPro.projectName">
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.projectOwner" class="required textInput readonly" style="width: 80px;" type="text" readonly="readonly" value="${proRels[stat.index].projectOwner}" bringbackname="proRels[${stat.index }].shipPro.projectOwner">
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.linkName" class="required textInput" style="width: 90px;" readonly="readonly" type="text" value="${proRels[stat.index].linkName }">
						</td>
						<td>
							<input name="proRels[${stat.index }].personNum" class="required textInput" style="width: 80px;" type="text" value="${proRels[stat.index].personNum}">
						</td>
						<td>
							<input name="proRels[${stat.index }].price" class="required textInput readonly" style="width: 100px;" type="text" readonly="readonly" value="${proRels[stat.index].price }">
						</td>
						<td>
							<a class="btnDel " href="javascript:void(0)">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
</form>
</div>

<script type="text/javascript">
/**
 * 重置;
 */
function resetDo() {
	$("#sampleDeli_edit_from_id :input").each(function() {
		$(this).val("");
	}); 
	$("#sampleDeli_edit_from_id :select").each(function() {
		$(this).val("");
	});
}
/**
 * 鼠标移开事件;
 */
$(".batchNum").live("blur", function() {
	var val = $(this).val(); //this value
	
	var thname = $(this).attr("name");
	
	var ahref = $(this).parent().next().find(".btnLook").attr("href");
	var newhref = "";
	if(ahref.indexOf("?") > 0) {
		newhref = ahref.substring(0, ahref.indexOf("?")) + "?bitchNum="+val;
	} else {
		newhref = ahref +  "?bitchNum="+val;
	}
	 
	$(this).parent().next().find(".btnLook").attr("href", newhref);
});


/*
 * 回调函数;
 */
function pro_callback(obj) {
	var vname = $(obj).attr("name");
	
	if(vname.indexOf("ship.id")>-1) {
		var shipId = $("#proReals_tboby_id").find("input[name='"+vname+"']").val();
		var proRel =  vname.substring(0, vname.indexOf("]")+1);
		var procodeName = proRel + ".shipPro.projectCode";
		$("#proReals_tboby_id").find("input[bringbackname='"+procodeName+"']").next().attr("href", "${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+shipId);
		clearPro($(obj), proRel);//清空数据;
	}
	
	return ;
}

function clearPro($th, proRel) {
	//修改那么属性;
	//$("#proReals_tboby_id").find("input[name='"+proRel+"shipPro.projectName']").attr("bringbackname", proRel+"shipPro.projectName");
	//$("#proReals_tboby_id").find("input[name='"+proRel+"shipPro.projectOwner']").attr("bringbackname", proRel+"shipPro.projectOwner");
	$("#proReals_tboby_id").find("input[name='"+proRel+".shipPro.projectName']").attr("bringbackname", proRel+".shipPro.projectName");
	$("#proReals_tboby_id").find("input[name='"+proRel+".shipPro.projectOwner']").attr("bringbackname", proRel+".shipPro.projectOwner");
	$("#proReals_tboby_id").find("input[name='"+proRel+".shipPro.linkName']").attr("bringbackname", proRel+".shipPro.linkName");
	//清空值;
	$("#proReals_tboby_id").find("input[name='"+proRel+".shipPro.id']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectCode']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectName']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectOwner']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.linkName']").val("");
}
</script>