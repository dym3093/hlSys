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
function changeExpress() {
		if(confirm("确定要寄快递？")){
			var ids=$(".").val();
			//如果是true 
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("addExpress", "../genepack/detail!toModifygenepack.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
}
function edit(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifygenepack", "../genepack/erpGenepackDetail!toAddGenepackDetail.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}
//变更用于复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifygenepack", "../genepack/erpGenepackDetail!toAddGenepackDetail.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
function changeProduct3(ids) {
	/*var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择要寄快递的场次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../genepack/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	//if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../genepack/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
	*/
		var obj=document.getElementById("expressid").style.display;
	if(obj=="none") {
		$("#expressid",navTab.getCurrentPanel()).show();
	}else{
		$("#expressid",navTab.getCurrentPanel()).hide();
	}	
	//}
}
function changeProduct4(ids) {
	/*var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择要寄快递的场次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../genepack/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../genepack/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
	*/
		//document.getElementById("expressid").style.display='block';
	var o=document.getElementById("expressid").style.display;
	alert(o);
	if(o=="none") {
		$("#expressid",navTab.getCurrentPanel()).show();
	}else{
		$("#expressid",navTab.getCurrentPanel()).hide();
	}	
	}
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
		document.form2.isBtn.value="2";
		$(".pageFormend",navTab.getCurrentPanel()).submit();

}
</script>
	
    
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${genepackDelivery.deliverybatchno}批次发货详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>发货信息</span></div>
		<p>
			<label>批次号：</label>
			<span>${genepackDelivery.deliverybatchno }</span>
		</p>
		<p>
			<label>快递单号：</label>
			<span>${genepackDelivery.deliveryexpressno}</span>
		</p>
		<p>
	    	<label>快递日期：</label>
	    	<span>${fn:substring(genepackDelivery.deliverydate,0,10) }</span>
	    </p>
	     <p>
	    	<label>物品数量：</label>
	    	<span>${genepackDelivery.packcount}</span>
		</p>
	      <input name="genepackDelivery.deliverybatchno"  type="hidden"  value="${genepackDelivery.deliverybatchno}" />
	      <input name="id" type="hidden"  value="${genepackDelivery.id }" />
	      
<!---->
<div class="formBar" style="width:98%">
 <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button onclick="changeProduct3('${events.id}')">添加基因包</button>
            </div>
          </div>
        </li>
 </ul>
<p></p>
</div>
<form id="form1" name="form1"   class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/genepack/erpGenepackDelivery!updateGenepack.action" method="post">
 <input type="hidden" value="1" name="isBtn">
<div style="display: none;" class="pageContent" id="expressid">
        <div class="divider"></div>
        <p>
          <label>所发货物批次号：</label>
          <input  name="genepack.batchno"  type="text" value="${genepack.batchno}" bringBackName="genepack.batchno"  class="required" readonly="readonly"/>
          <a class="btnLook"  href="${path}/genepack/erpGenepackDelivery!toLookUpGenepack.action"   lookupGroup="genepack" style="float: left;">查找带回</a>
        </p>
        <p>
          <label>货架号：</label>
          <input name="genepack.shelvesno"  type="text" value="${genepack.shelvesno}" bringBackName="genepack.shelvesno"  class="required" readonly="readonly"/>
        </p>
        <p>
          <label>保险公司：</label>
          <input name="genepack.sendercompany"  type="text" value="${genepack.sendercompany}" bringBackName="genepack.sendercompany" class="required" readonly="readonly"/>
        </p>
        <p>
          <label>远盟联系人：</label>
          <input name="genepack.ymsalesman"  type="text" value="${genepack.ymsalesman}" bringBackName="genepack.ymsalesman"  class="required" readonly="readonly"/>
        </p>
         <p>
			<label>上架日期：</label>
			<%-- <input id="updates" style="width:110px;" name="genepack.updates" bringBackName="genepack.updates" readonly="readonly" datefmt="yyyy-MM-dd"  class="date required" value="${genepack.updates}"  type="text" class="date required" readonly="readonly"/> --%>
            <!-- <a class="inputDateButton" href="javascript:;">选择</a> -->
           <input name="genepack.updates" type="text" value="${genepack.updates}" bringBackName="genepack.updates"  class="required" readonly="readonly">
		</p>
		<p>
			<label>下架日期：</label>
           <input id="downdates" style="width: 110px;" name="genepack.downdates" datefmt="yyyy-MM-dd HH:mm" value="${fn:substring(genepackDelivery.deliverydate,0,10)}" type="text" class="date required" />
		            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		 <input name="genepack.deliverybatchno"  type="hidden" value="${genepackDelivery.deliverybatchno}"  class="required" />
		 <input name="genepack.id" bringBackName="genepack.id"  type="hidden" value="${genepack.id}"  class="required" />
       
        
		<div class="divider"></div>
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
 </div>
<!---->
</form>	      
<div class="tip"><span>发货物品清单</span></div>
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		 <ul class="toolBar">
			<li><a class="delete" href="${path}/genepack/erpGenepackDetail!clearGenepackDetail.action" rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
		 <%-- <li><a class="add" href="${path }genepack/erpExpress!toAddExpress.action?id=${genepack.id}" target="dialog"   style="cursor:pointer;"><span>寄快递</span></a></li>
			<li><a class="add" href="${path}/genepack/detail!toAddCustomer.action?=${genepack.id}" target="navTab" rel="add"><span>添加</span></a></li>
			--%>
			<%-- <li><a class="add" href="${path}/genepack/detail!importCustomer.action?id=${genepack.id}" target="navTab" rel="add"><span>导入</span></a></li> --%>
	<form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/genepack/erpGenepackDelivery!toGenepackDelivery.action'){this.action = '${path}/genepack/erpGenepackDelivery!toGenepackDelivery.action' ;} ;return navTabSearch(this);" action="${path}/genepack/erpGenepackDelivery!toGenepackDelivery.action" method="post">
		<input type="hidden" name="filter_and_deliverybatchno_EQ_S" value="${genepackDelivery.deliverybatchno}"/>
		<input type="hidden" name="id" value="${genepackDelivery.id}"/>
	</form> 
			
		 <web:exportExcelTag pagerFormId="pagerFindForms" 
								pagerMethodName="findByPageDelivery" 
								actionAllUrl="org.hpin.genepack.web.GenepackDeliveryAction" 
								informationTableId="exportExcelTables"
								fileName="genepackDelivery"></web:exportExcelTag>  
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export= "true" columnEnName = "expressno" columnChName = "快递单号" >快递单号</th>
				<th  export= "true" columnEnName = "expresscompany" columnChName = "快递公司" >快递公司</th>
				<th  export= "true" columnEnName = "ymsalesman" columnChName = "远盟联系人">远盟联系人</th>
				<th  export= "true" columnEnName = "sender" columnChName = "寄件人" >寄件人</th>
				<th  export= "true" columnEnName = "senderphone" columnChName = "寄件人电话">寄件人电话</th>
				<th  export= "true" columnEnName = "senderaddress" columnChName = "寄件地址" >寄件地址</th>
				<th  export= "true" columnEnName = "sendercompany" columnChName = "寄件单位" >寄件单位</th>
				<th  export= "true" columnEnName = "updates" columnChName = "上架日期" >上架日期</th>
				<th  export= "true" columnEnName = "downdates" columnChName = "下架日期" >下架日期</th>
				<th  export= "true" columnEnName = "shelvesno" columnChName = "货架号" >货架号</th>
				<th  export= "true" columnEnName = "deliverybatchno" columnChName = "发货批次号" >发货批次号</th>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="genepack" varStatus="status">
					<tr target="sid_user" rel="${genepack.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${genepack.id}">
							</c:if>
							${ status.count }
						</td>
						<td align="center">
							<%-- <a title="场次信息" target="dialog" width="1000"  href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${genepack.id}">${genepack.eventsNo}</a> --%>
							<a title="基因信息" target="navTab" width="1000"  href="${path}/genepack/erpGenepackDetail!toGenepackDetail.action?id=${genepack.id}">${genepack.batchno}</a>
						</td>
						<td align="center">	${genepack.expressno}</td>
						<td align="center">	${genepack.expresscompany}</td>
						<td align="center">	${genepack.ymsalesman}</td>
						<td align="center">	${genepack.sender}</td>
						<td align="center">	${genepack.senderphone}</td>
						<td align="center">	${genepack.senderaddress}</td>
						<td align="center">	${genepack.sendercompany}</td>
						<td align="center">	${fn:substring(genepack.updates,0,19)}</td>
						<td align="center">	${fn:substring(genepack.downdates,0,19)}</td>
						<td align="center">	${genepack.shelvesno}</td>
						<td align="center">	${genepack.deliverybatchno}</td>
						
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
