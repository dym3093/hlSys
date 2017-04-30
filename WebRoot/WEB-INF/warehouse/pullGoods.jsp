<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
#table2 td {
	TEXT-ALIGN: center;
	BACKGROUND-COLOR: #f7f7f7;
	padding: 4, 4, 4, 4;
}
#table2 table {
	FONT-SIZE: 12pt;
	cellspacing: 10;
	cellpadding: 10;
	padding: 30, 30, 30, 30;
	width: 100%;
}
</style>
<script type="text/javascript">
function test(){
	var con = new Number($("#count").val());
	var start = new Number($("#start").val());
	$("#end").val(con+start-1);
}
function testPrice(){
	var con = new Number($("#price1").val());
	var start = new Number($("#price2").val());
	var all = con+start;
	$("#total").val(all.toFixed(2));
	/* $("#total").val(con+start); */
}
function hectorAjaxDone(json){
	DWZ.ajaxDone(json);
	if(json.statusCode == DWZ.statusCode.ok){
		if ("forward"== json.callbackType){
            navTab.reload(json.forwardUrl);
      }
		$.pdialog.closeCurrent();
	}
}
function findEmsName(code){
	$.ajax({
	      type: "post",
	      cache :false,
	      dataType: "json",
	      url: "../events/express!listExpressForAjax.action",
	      data: { "code":code},
	      success: function(data){ 
	      	 $.each(data,function(name,value){ 
	        	 if(value!=""){
	           		$("#emsName").val(value);
	        	 }
	        });
	      },
	      error :function(XMLHttpRequest, textStatus, errorThrown){
	      }    
	});
	
}

function submitForm(){
	/*var flag = false;
	$(".required",navTab.getCurrentPanel()).each(function(){
		if($(this).val()==""){
			$(this).focus();
			flag = true;
		}
	});
	
	if(flag){
		alert("您有必选项没有填写请确认");
		return false;
	}*/
   
		document.form1.isBtn.value="2";
		$(".pageForm",navTab.getCurrentPanel()).submit();

}
</script>
<div class="pageContent">
	<form  name="form1" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/warehouse/warehouse!pull.action" method="post">
		 <input type="hidden" value="1" name="isBtn">
		<div id="table" class="pageFormContent" layoutH="56">
		<%-- 	<div>
				<div class="tip">
					<span>仓库基本信息</span>
				</div>
				<p>
					<label>仓库名称：</label> <input type="hidden" value="${stroeWarehouse.id}" /> 
					<input type="text" value="${stroeWarehouse.name}" readonly="readonly" />
				</p>
				<p>
					<label>省 份：</label> <input type="text"
						value="<hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/>"
						readonly="readonly" />
				</p>
				<p>
					<label>城 市：</label> <input type="text"
						value="<hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/>"
						readonly="readonly">
				</p>
				<p>
					<label>联系人：</label> <input type="text"
						name="stroeWarehouse.director" maxlength="30" size="30"
						readonly="readonly" value="${stroeWarehouse.director}" />
				</p>
				<p>
					<label>联系电话：</label> <input type="text" name="stroeWarehouse.tel"
						maxlength="30" size="30" readonly="readonly"
						value="${stroeWarehouse.tel}" />
				</p>
				<p class="nowrap" style="height: 61px;">
					<label>详细地址：</label>
					<textarea name="stroeWarehouse.address" cols="4"
						readonly="readonly" rows="2">${stroeWarehouse.address}</textarea>
				</p>
			</div> --%>
			<div>
				<div class="tip">
				<input type="hidden" value="${stroeWarehouse.id}" /> 
					<span>${stroeWarehouse.name}产品类别信息</span>
				</div>
				<table class="list" name="allList" width="100%">
					<thead>
						<tr>
							<th>品类</th>
							<th>名称</th>
							<th>规格</th>
							<th>描述</th>
							<th>库存数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<input type="hidden" name="stroeType.id" value="${stroeType.id}" />
							<td align="center"><hpin:id2nameDB id='${stroeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao' /></td>
							<td align="center">${stroeType.name}</td>
							<td align="center">${stroeType.standards}</td>
							<td align="center">${stroeType.descripe}</td>
							<td align="center">${storeAll.count}</td>
						</tr>
					</tbody>
				</table>
			</div> 
			<div class="divider"></div>
			<div class="tip">
				<span>发货信息录入</span>
			</div>
			<div class="searchBar">
				<table width="80%">
					<tr><td><label>关联申请单：</label></td>
						<td>
							<input tabindex="1" type="text" name="produce.remark3" readonly="readonly" value="${produce.remark3 }" bringBackName="storeApplication.batNo"/>
							<a class="btnLook"  href="${path}/warehouse/storeApplication!lookUpStoreApplication.action"   lookupGroup="storeApplication" style="float: left;">查找带回</a>
						</td>
						<td><label>数量：</label></td>
						<td><input tabindex="2" type="text" class="required number" name="produce.remark" id="count"  value="${produce.remark}" onblur="test()"/></td>
						<input type="hidden" value="${id}" name="id"/>
						<input type="hidden" name="produce.type" value="1" />
						<input type="hidden" name="produce.owncompannyId" bringBackName="ownedCompanyId"/>
						<input type="hidden" name="produce.owncompannyName" bringBackName="ownedCompany"/>
						<input  type="hidden" name="produce.applicationId" value="${produce.applicationId}" bringBackName="storeApplication.id"/>
						<input type="hidden" name="produce.businessId" value="${ produce.businessId}"  bringBackName="storeApplication.receiveId" />
						<input type="hidden" bringBackName="storeApplication.bannyCompanyId" name="produce.bannyCompannyId"  />
					</tr>
					<tr>
						<td><label>收件人联系方式：</label></td>
						<td><input tabindex="5" name="produce.receiveTel" type="text" bringBackName="storeApplication.receiveTel" class="required" value="${produce.receiveTel}" /></td>
						<td><label>收件人姓名：</label></td>
						<td><input tabindex="6" name="produce.receiveName" bringBackName="storeApplication.receiveName" type="text" class="textInput" value="${produce.receiveName}" /></td>
						
					</tr>
					<tr>
						<td width="15%"><label>公司名称：</label></td>
						<td width="35%"><input tabindex="7" name="produce.bannyCompannyName" type="text" class="required code" bringBackName="storeApplication.bannyCompanyName" /></td>
						<%-- <td width="15%"><label>场次信息：</label></td>
						<td><input tabindex="8" name="produce.eventNos" type="text" class="textInput" value="${produce.eventNos}" /></td> --%>
						<td><label>使用时间：</label></td>
						<td><input id="customerDate" tabindex="8" name="produce.useTime" datefmt="yyyy-MM-dd" bringBackName="storeApplication.useDate" value="${fn:substring(produce.useTime,0,14)}" type="text" class="date" /> 
							<a class="inputDateButton" href="javascript:;">选择</a>
						</td>
					</tr>
					<tr>
						<td><label>业务人员：</label></td>
						<td><input tabindex="9" name="produce.businessName"   bringBackName="storeApplication.createUserName" type="text" class="required" value="${produce.businessName}" /> </td>
						<td><label>快递费：</label></td>
						<td> <input tabindex="10" name="produce.emsPrice" onblur="testPrice();" id="price1" type="text" class="textInput number" value="${produce.emsPrice}" /> </td>
					</tr>
					
					<tr>
						<td><label>成本：</label></td>
						<td> <input tabindex="11" name="produce.basePrice" type="text" onblur="testPrice();" id="price2" class="textInput number required" value="${produce.basePrice}"/> </td>
						<td><label>总额：</label></td>
						<td> <input tabindex="12" name="produce.totalPrice" type="text" class="textInput number" id="total" value="${produce.totalPrice}" /> </td>
					</tr>
					<tr>
						<td><label>项目负责人：</label></td>
						<td><input tabindex="13" name="produce.projectBusinessName"   bringBackName="storeApplication.remark1" type="text" class="required" value="${produce.projectBusinessName}" /> </td>
						<td><label>项目归属：</label></td>
						<td><input tabindex="13" name="produce.projectBelone"   bringBackName="storeApplication.remark2" type="text" class="required" value="${produce.projectBelone}" /> </td>
					</tr>
					<tr>
						<td><label>项目编码：</label></td>
						<td><input tabindex="13" name="produce.projectCode"  bringBackName="storeApplication.remark3" type="text" class="required" value="${produce.projectCode}" /> </td>
					<c:choose>
					<c:when test="${remark1=='1010701' or remark1=='1010706' }">
						<td><label>卡ID：</label></td>
						<td> <input tabindex="13" name="produce.eventNos" type="text"  class="required textInput"  value="${produce.eventNos}" /> </td>
					</tr>
					<tr>
						<td><label>卡号段开始：</label></td>
						<td> <input readonly="readonly" tabindex="13" name="produce.cardStart" type="text" id="start" class="required textInput" onblur="test();" value="${stroeType.cardStart}" /> </td>
						<td><label>卡号截至：</label></td>
						<td> <input readonly="readonly" tabindex="14" name="produce.cardEnd" type="text" class="required textInput" id="end" value="${produce.cardEnd}" onblur="test();"/> </td>
					</c:when>
					</c:choose>
					</tr>
					<tr>
						<td><label>派发地址：</label></td>
						<td><textarea tabindex="15" name="produce.address" bringBackName="storeApplication.address" cols="4" rows="2">${produce.address}</textarea></td>
						<td><label>需求说明：</label></td>
						<td><textarea tabindex="16" name="produce.requireDetail" bringBackName="storeApplication.requires" cols="4" rows="2">${produce.requireDetail }</textarea></td>
					</tr>
					<tr>
						<td><label>快递单号：</label></td>
						<td><input tabindex="17" name="produce.emsNo" type="text" bringBackName="" onchange="findEmsName(this.value)" class="textInput" value="${produce.emsNo}" /></td>
						<td><label>快递公司：</label></td>
						<td><input tabindex="18" name="produce.emsName" type="text" bringBackName="" class="textInput" id="emsName" value="${produce.emsName}" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="formBar" style="width:99%">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">部分发货已完成</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm()">全部发货已完成</button>
							<!-- <button type="submit">结束</button> -->
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
