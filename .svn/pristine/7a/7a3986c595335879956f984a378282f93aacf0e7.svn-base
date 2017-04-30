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
			var ids=$(".eventsNo").val();
			//如果是true 
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("addExpress", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
}
function edit(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
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
		alert('请选择要寄快递的批次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../events/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	//if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../events/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
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
		alert('请选择要寄快递的批次！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条！');
		return ;		
	}else {
		if(confirm("确定要寄快递？")){
			 //如果是true 
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("addExpress", "../events/erpExpress!toAddExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
	}*/
	if(confirm("确定要寄快递？")){
		 //如果是true 
	/*var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("addExpress", "../events/erpExpress!addExpress.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
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

function myform(op){
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
		document.pagerFindForms.ismatch.value=op;
		$(".pageForm",navTab.getCurrentPanel()).submit();

}
</script>

   
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${reportdetailBatch.batchno }批次信息详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>批次信息</span></div>
		<p>
			<label>批次号：</label>
			<span>${reportdetailBatch.batchno }</span>
		</p>
		<p>
			<label>批次日期：</label>
			<span>${fn:substring(reportdetailBatch.batchdate,0,14)}00:00</span>
		</p>
		<p>
	    	<label>验证是否正确：</label>
	    	<span>
	    		<c:if test="${reportdetailBatch.reportfilenocodecount==0 && reportdetailBatch.codenoreportfilecount==0}">本批次正确</c:if>
	    		<c:if test="${reportdetailBatch.reportfilenocodecount>0 || reportdetailBatch.codenoreportfilecount>0}">本批次有误</c:if>
			
			</span>
		</p>
		<p>
	    	<label>Excel名单总数：</label>
	    	<span>
	    	<a title="Excel名单数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action?id=${reportdetailBatch.id}&ismatch=2">${reportdetailBatch.xlscodecount}</a>
	    	
	    	</span>
	    </p>
	    
	    <p>
	    	<label>本次导入：</label>
	    	<span>
	    	<a title="Excel名单数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action?id=${reportdetailBatch.id}&ismatch=1">${reportdetailBatch.xlspdfcount }</a>
	    	
	    	</span>
	    </p>
	    <p>
	    	<label>Excel多余名单数：</label>
	    	<span>
	    	<a title="Excel名单数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action?id=${reportdetailBatch.id}&ismatch=0">${reportdetailBatch.codenoreportfilecount }</a>
	    	
	    	</span>
	    </p>
	    <p>
	    	<label>基因报告文件总数：</label>
	    	<span>
	    		<a title="基因报告数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!listReportdetailPDF.action?id=${reportdetailBatch.id}">
	    		${reportdetailBatch.pdffilecount }
	    		</a>
	    	</span>
	    </p>
	    <p>
	    	<label>正确报告文件数：</label>
	    	<span>
	    	<a title="基因报告数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!listReportdetailPDF.action?id=${reportdetailBatch.id}&ismatchpdf=1">
	    		${reportdetailBatch.xlspdfcount }
	    	</a>
	    	</span>
		</p>
	    <p>
	    	<label>多余报告文件数：</label>
	    	<span>
	    	<a title="基因报告数" target="navTab" width="1000"  href="${path}/reportdetail/erpReportdetailBatch!listReportdetailPDF.action?id=${reportdetailBatch.id}&ismatchpdf=0">
	    		${reportdetailBatch.reportfilenocodecount}
	    	</a>
	    	</span>
		</p>
	    
		
	      <input name="id" type="hidden"  value="${reportdetailBatch.id }" />
	      


<div class="formBar" style="width:98%">
<!-- <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button onclick="changeProduct3('${events.id}')">寄快递</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add">添加客户信息</a>
            </div>
          </div>
        </li>
         <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">客户信息采集结束</button>
            </div>
          </div>
        </li>
      </ul>-->
      </div>

<!-- <p></p> -->

<div class="tip"><span>批次信息清单</span></div>
	
	<form rel="pagerForm" id="pagerFindForms1" class=".pageForm" name="pagerFindForms" onsubmit="if(this.action != '${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action'){this.action = '${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpReportdetailBatch!toReportdetailBatch.action" method="post">
		<input type="hidden" name="id" value="${reportdetailBatch.id}"/>
	
		<c:if test="${reportdetailBatch.ismatch==2}"><input type="hidden" name="filter_and_ismatch_LIKE_S" value=""/></c:if>
		<c:if test="${reportdetailBatch.ismatch!=2}"><input type="hidden" name="filter_and_ismatch_LIKE_S" value="${reportdetailBatch.ismatch}"/></c:if>
		
	</form>
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		 <ul class="toolBar">
		 <%-- <li><a class="add" href="${path }events/erpExpress!toAddExpress.action?id=${events.id}" target="dialog"   style="cursor:pointer;"><span>寄快递</span></a></li>
			<li><a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			<li><a class="add" href="${path}/events/erpCustomer!importCustomer.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
			--%>
			
		 <web:exportExcelTag pagerFormId="pagerFindForms1" 
								pagerMethodName="findByPage1" 
								actionAllUrl="org.hpin.reportdetail.web.ErpReportdetailBatchAction" 
								informationTableId="exportExcelTables"
								fileName="erpReportdetail"></web:exportExcelTag>  
		</ul>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export= "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export = "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别">性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "branchcompany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "project" columnChName = "套餐名">套餐名</th>
				<th  export = "true" columnEnName = "sampletype" columnChName = "样本类型" >样本类型</th>
				<th  export = "true" columnEnName = "salesman" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "samplingdate" columnChName = "采样日期" >采样日期</th>
				<th  export = "true" columnEnName = "filepath" columnChName = "Excel文件路径" >Excel文件路径</th>
				<th  export = "true" columnEnName = "ismatch" columnChName = "是否匹配" >是否匹配</th>
				<!-- <th  export = "false" columnEnName = "" columnChName = "" >操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="reportdetail" varStatus="status">
					<tr target="sid_user" rel="${reportdetailBatch.id }">
						<td align="center">
							<!--  <input type="checkbox" name="ids" value="${reportdetailBatch.id}"> -->
						${ status.count }</td>
						<td align="center">	${reportdetail.batchno}</td>
						<td align="center">	${reportdetail.code}</td>
						<td align="center">	${reportdetail.name}</td>
						<td align="center">	${reportdetail.sex}</td>
						<td align="center">	${reportdetail.age}</td>
						<td align="center">	${reportdetail.phone}</td>
						<td align="center">	${reportdetail.branchcompany}</td>
						<td align="center">	${reportdetail.project}</td>
						<td align="center">	${reportdetail.sampletype}</td>
						<td align="center">	${reportdetail.salesman}</td>
						<td align="center">	${fn:substring(reportdetail.samplingdate,0,14)}00:00</td>
						<td align="center">	${reportdetail.filepath}</td>
						<td align="center">	
							<c:if test="${reportdetail.ismatch==1}">是</c:if><c:if test="${reportdetail.ismatch==0}">没报告文件</c:if>
						</td>
						
						<%-- <td align="center">	
							<div class="panelBar">
								 <ul class="toolBar">
								 	<li><a class="edit" onclick="edit('${erpCustomer.id}')" style="cursor:pointer;"><span>变更</span></a></li>
								 	<li><a class="delete" href="${path}/events/erpCustomer!delOneCustomer.action?id=${erpCustomer.id}"  postType="string" title="确定要删除吗?" target="navTab"><span>删除</span></a></li>
								 </ul>
							</div>
						</td> --%>
						
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
