<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>


<<script type="text/javascript">
	$("input", navTab.getCurrentPanel()).attr("readonly", "readonly");
	$("select",navTab.getCurrentPanel()).attr("disabled", "disabled");
</script>

<div class="pageContent">
	<form class="pageForm -validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/sampleDlei!saveOrUpdate.action" method="post">
    	<input type="hidden" name="navTabId" id = "navTabId" value="${navTabId }" />					  
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
					    	<input type="text" id="deliNum" name="sampleDleiManager.deliNum" value="${sampleDleiManager.deliNum }" class=" textInput"/>
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
					    	<input type="text" id="weight" name="sampleDleiManager.weight" value="${sampleDleiManager.weight }"  class=" textInput"/>
					    </td>
					   
					</tr>
					<tr>
						<td><label>类别：</label></td>
					    <td>
					    	<select name="sampleDleiManager.sampleType" style="width: 194px; margin-left: 5px;">
					    		<option value="TYPE_01" ${sampleDleiManager.sampleType=="TYPE_01" ? "selected":"" }>寄样</option>
					    		<option value="TYPE_02" ${sampleDleiManager.sampleType=="TYPE_02" ? "selected":"" }>收样</option>
					    	</select>
					    </td>
					    <td><label>收发件日期：</label></td>
					    <td>
					    	<input type="text" id="receliveSendDateBak" name="sampleDleiManager.receliveSendDateBak" value="<fmt:formatDate value="${sampleDleiManager.receliveSendDate }" pattern="yyyy-MM-dd"/>"  class="date "/>
					    </td>
					    <td><label>发票有无：</label></td>
					    <td><input type="radio" name="sampleDleiManager.isBill" value="0000" ${sampleDleiManager.isBill == '0' ? "checked":"checked"}/>有&emsp;
					    	<input type="radio" name="sampleDleiManager.isBill" value="0001" ${sampleDleiManager.isBill == '1' ? "checked":""}/>无</td>
					</tr>
					<tr>
						<td><label>费用(元)：</label></td>
					    <td><input type="text" id="costPriceBak" name="sampleDleiManager.costPriceBak" value="${sampleDleiManager.costPrice }"  class="textInput  "/></td>
					</tr>
				</tbody>
			</table>
			<div class="tip"><span>项目关联</span></div>
			<table class="list nowrap" style="clear: both;" width="100%">
				<thead>
					<tr>
						<th type="text" name="proRels[#index#].batchNum" filedStyle="width: 100px" fieldClass="">批次号</th>
						<th type="lookup" filedStyle="width: 120px" callback="pro_callback" fieldClass="" lookupgroup="proRels[#index#].ship" bringBackFlag="true" bringBackId="id" bringBackName="branchCommanyName" lookupPk="id" lookupurl="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" >支公司</th>
						<th type="lookup" filedStyle="width: 120px" fieldClass=" onblurClass" lookupgroup="proRels[#index#].shipPro" bringBackFlag="true" bringBackId="id" bringBackName="projectCode" lookupPk="id" >项目编码</th>
						<th type="text" name="proRels[#index#].shipPro.projectName" filedStyle="width: 100px;" fieldAttrs="{'readonly':'readonly'}" fieldAttrs="{'readonly':'readonly'}"  fieldClass="" >项目名称</th>
						<th type="text" name="proRels[#index#].shipPro.projectOwner" filedStyle="width: 80px;" fieldAttrs="{'readonly':'readonly'}" fieldAttrs="{'readonly':'readonly'}"  fieldClass="">项目负责人</th>
						<th type="text" name="proRels[#index#].proJointPerson" filedStyle="width: 90px" fieldClass="">远盟对接人</th>
						<th type="text" name="proRels[#index#].personNum" filedStyle="width: 80px" fieldClass="">人数</th>
						<th type="text" name="proRels[#index#].price" filedStyle="width: 100px"  fieldAttrs="{'readonly':'readonly'}" fieldClass="">单价</th>
					</tr>
				</thead>
			<tbody id="proReals_tboby_id">
				<c:forEach items="${proRels }" var="pro" varStatus="stat">
					<tr>
						<td>
							<input name="proRels[${stat.index }].batchNum" class=" textInput" style="width: 100px;" type="text" size="12" value="${proRels[stat.index].batchNum }">
						</td>
						<td>
							<input name="proRels[${stat.index }].ship.id" type="hidden" bringbackname="proRels[${stat.index }].ship.id" value="${proRels[stat.index].customerRelationShipId }">
							<input class=" textInput readonly" type="text" size="12" readonly="readonly" lookuppk="id" bringbackname="proRels[${stat.index }].ship.branchCommanyName" value="${proRels[stat.index].customerRelationShipName }">
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.id" type="hidden" bringbackname="proRels[${stat.index }].shipPro.id" value="${proRels[stat.index].customerRelationShipProId}">
							<input class=" onblurClass textInput readonly" type="text" size="12" readonly="readonly" lookuppk="id" bringbackname="proRels[${stat.index }].shipPro.projectCode" value="${proRels[stat.index].projectCode}">
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.projectName" class=" textInput readonly" style="width: 100px;" type="text" readonly="readonly" value="${proRels[stat.index].projectName }" bringbackname="proRels[${stat.index }].shipPro.projectName">
						</td>
						<td>
							<input name="proRels[${stat.index }].shipPro.projectOwner" class=" textInput readonly" style="width: 80px;" type="text" readonly="readonly" value="${proRels[stat.index].projectOwner}" bringbackname="proRels[${stat.index }].shipPro.projectOwner">
						</td>
						<td>
							<input name="proRels[${stat.index }].proJointPerson" class=" textInput" style="width: 90px;" type="text" value="${proRels[stat.index].linkName }">
						</td>
						<td>
							<input name="proRels[${stat.index }].personNum" class=" textInput" style="width: 80px;" type="text" value="${proRels[stat.index].personNum}">
						</td>
						<td>
							<input name="proRels[${stat.index }].price" class=" textInput readonly" style="width: 100px;" type="text" readonly="readonly" value="${proRels[stat.index].price }">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		
</form>
</div>

<script type="text/javascript">
/*
 * 回调函数;
 */
function pro_callback(obj) {
	var vname = $(obj).attr("name");
	
	if(vname.indexOf("ship.id")>-1) {
		var shipId = $("#proReals_tboby_id").find("input[name='"+vname+"']").val();
		console.log(shipId);
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
	//清空值;
	$("#proReals_tboby_id").find("input[name='"+proRel+".shipPro.id']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectCode']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectName']").val("");
	$("#proReals_tboby_id").find("input[bringbackname='"+proRel+".shipPro.projectOwner']").val("");
}
</script>