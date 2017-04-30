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
	<h1 class="press">${genepack.batchno}批次信息详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>基因快递包信息</span></div>
		<p>
			<label>批次号：</label>
			<span>${genepack.batchno }</span>
		</p>
		<p>
			<label>快递单号：</label>
			<span>${genepack.expressno}</span>
		</p>
		<p>
	    	<label>快递公司：</label>
	    	<span>${genepack.expresscompany}</span>
	    </p>
	    <p>
	    	<label>远盟联系电话：</label>
	    	<span>${genepack.ymsalesman }</span>
	    </p>
	    <p>
	    	<label>寄件人电话：</label>
	    	<span>${genepack.senderphone }</span>
	    </p>
	   
	    <p>
	    	<label>寄件人地址：</label>
	    	<span>${genepack.senderaddress}</span>
		</p>
		<p>
	    	<label>寄件人单位：</label>
	    	<span>${genepack.sendercompany }</span>
	    </p>
		<p>
	    	<label>上架日期：</label>
	    	<span>${genepack.updates }</span>
	    </p>
	     <p>
	    	<label>货架号：</label>
	    	<span>${genepack.shelvesno}</span>
		</p>
	      <input name="genepack.batchno"  type="hidden"  value="${genepack.batchno}" />
	      <input name="id" type="hidden"  value="${genepack.id }" />
	      
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
<form   id="form1" name="form1"   class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/genepack/erpGenepackDetail!addGenepackDetail.action" method="post">
<input name="genepackDetail.batchno" type="hidden"  value="${genepack.batchno}" />
 <input type="hidden" value="1" name="isBtn">
<div style="display: none;" class="pageContent" id="expressid">
        <div class="divider"></div>
          <p>
			<label>活动日期：</label>
			<input id="actiondate" style="width:110px;" name="genepackDetail.actiondate" readonly="readonly" datefmt="yyyy-MM-dd"  class="date required" value="${genepackDetail.actiondate}"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
           
		</p>
        <p>
          <label>活动场次：</label>
            <select name ="genepackDetail.actionevents" style="margin:6px;">
		            	<option value="">请选择</option>
		            	<option value="上午场">上午场</option>
		            	<option value="下午场">下午场</option>
		            	<option value="晚场">晚场</option>
		            </select>
        </p>
      <p>
          <label>有无纸制表格：</label>
          <select name ="genepackDetail.ispapertable" style="margin:6px;" id="ispapertable" class="required">
		            	<option value="">请选择</option>
		            	<option value="有">有</option>
		            	<option value="无">无</option>
		            </select>
        </p>
         <p>
          <label>样本数量：</label>
          <input name="genepackDetail.samplecount"  type="text" value="${genepackDetail.samplecount}"  class="required digits" />
        </p>
		<p>
          <label>条码1：</label>
          <input name="genepackDetail.code1"  type="text" value="${genepackDetail.code1}"  class="required"/>
        </p>
        <p>
          <label>条码2：</label>
          <input name="genepackDetail.code2"  type="text" value="${genepackDetail.code2}"  class="required"/>
        </p>
       
        
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
<div class="tip"><span>检测包清单</span></div>
	<%-- <form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/genepack/erpGe!toEventNoListCustomer.action'){this.action = '${path}/genepack/detail!toEventNoListCustomer.action' ;} ;return navTabSearch(this);" action="${path}/genepack/detail!toEventNoListCustomer.action" method="post">
		<input type="hidden" name="filter_and__EQ_S" value="${genepack.id}"/>
		<input type="hidden" name="id" value="${genepack.id}"/>
		
	</form> --%>
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.userType!='查询用户'}">
		 <ul class="toolBar">
			<li><a class="delete" href="${path}/genepack/erpGenepackDetail!delGenepackDetail.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
		 <%-- <li><a class="add" href="${path }genepack/erpExpress!toAddExpress.action?id=${genepack.id}" target="dialog"   style="cursor:pointer;"><span>寄快递</span></a></li>
			<li><a class="add" href="${path}/genepack/detail!toAddCustomer.action?=${genepack.id}" target="navTab" rel="add"><span>添加</span></a></li>
			--%>
			<%-- <li><a class="add" href="${path}/genepack/detail!importCustomer.action?id=${genepack.id}" target="navTab" rel="add"><span>导入</span></a></li> --%>
			
		 <web:exportExcelTag pagerFormId="pagerFindForms" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.genepack.web.GenepackDetailAction" 
								informationTableId="exportExcelTables"
								fileName="detailAction"></web:exportExcelTag>  
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export= "true" columnEnName = "batchno" columnChName = "批次号" >批次号</th>
				<th  export = "true" columnEnName = "actiondate" columnChName = "活动日期" >活动日期</th>
				<th  export = "true" columnEnName = "actionevents" columnChName = "活动场次" >活动场次</th>
				<th  export = "true" columnEnName = "code1" columnChName = "条码1" >条码1</th>
				<th  export = "true" columnEnName = "code2" columnChName = "条码2" >条码2</th>
				<th  export = "true" columnEnName = "samplecount" columnChName = "样本数量" >样本数量</th>
				<th  export = "true" columnEnName = "ispapertable" columnChName = "有无纸制表格" >有无纸制表格</th>
			<c:if test="${currentUser.userType!='查询用户'}">
				<th  export = "false" columnEnName = "" columnChName = "" >操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="detail" varStatus="status">
					<tr target="sid_user" rel="${detail.id }">
						<td align="center">
							<input type="checkbox" name="ids" value="${detail.id}"> 
						${ status.count }</td>
						<%-- <td align="center">	${detail.}</td> --%>
						<td align="center">	${detail.batchno}</td>
						<td align="center">	${detail.actiondate}</td>
						<td align="center">	
						<%-- <hpin:id2nameDB id='${detail.sex}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/> --%>
						${detail.actionevents}
						</td>
						<td align="center">	${detail.code1}</td>
						<%-- <td align="center">	${detail.idno}</td> --%>
						<td align="center">	${detail.code2}</td>
						<td align="center">	${detail.samplecount}</td>
						<td align="center">
							<%-- <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${detail.setmealName}"/> --%>
						${detail.ispapertable}
						</td>
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">	
							<div class="panelBar">
								 <ul class="toolBar">
								 	<li><a class="edit" onclick="edit('${detail.id}')" style="cursor:pointer;"><span>变更</span></a></li>
								 <%-- 	<li><a class="delete" href="${path}/genepack/erpGenepackDetail!delGenepackDetail.action?id=${detail.id}"  postType="string" title="确定要删除吗?" target="navTab"><span>删除</span></a></li> --%>
								 </ul>
							</div>
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
