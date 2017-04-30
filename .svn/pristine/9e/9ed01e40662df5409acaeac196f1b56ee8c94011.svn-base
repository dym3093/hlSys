<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<script type="text/javascript" language="javascript">
function changeExpress() {
		if(confirm("确定要寄快递？")){
			var ids=$(".eventsNo").val();
			//如果是true 
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("addExpress", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"快递", fresh:false, data:{} });
		}
}
function edit(ids,customerException) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		if(!customerException){
			navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
		}else{
			navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyException.action?id="+ids+"&navTabId="+navTabId, { title:"处理", fresh:false, data:{} });
		}
}
function deleteById(ids){
	if (confirm("确认删除?")) {
		$.ajax({	
			type: "post",
			cache :false,
			data:{"exceptionId":ids},
			url: "../events/erpCustomer!deleteCustomerExption.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.status=='200'){
					alertMsg.correct(data.message);
					return navTabSearch(this);
				}else if(data.status=='300'){
					alertMsg.error(data.error);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
}

//全选
function selectAll(){
	var checkBoxs = $("input[name='ids']");
	for(var i = 0;i<checkBoxs.length;i++){
		$(checkBoxs[i]).attr("checked","checked");
	}
}
function findRepeatInfo(eventsNo){
	$.ajax({	
		type: "post",
		cache :false,
		data :{"eventsNo":eventsNo},
		url: "../events/erpCustomer!findRepeatInfo.action",
		success: function(data){
			var data= eval("("+data+")");
			if(data.status=='200'){
				var headInfo = "条形码，姓名，身份证号，手机号，性别，年龄\n";
				var msg = data.message;
				if(msg!=""){
					msg = headInfo+msg.replace("|","\n");
					alert(msg);
				}
				return navTabSearch(this);
			}else if(data.status=='300'){
				alertMsg.error(data.error);
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
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

	$("#customerInfoSure", navTab.getCurrentPanel()).on("click", function() {
		$.ajax({
			type: "post",
			cache : false,
			async : false,
			data:{"id":"${events.id}"},
			url: "${path}/events/erpCustomer!customerInfoSure.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.result) {
					alertMsg.correct("执行成功!");
				} else {
					alertMsg.warn(data.message);
				}
			},
			error :function(){
				alertMsg.warn("服务发生异常，请稍后再试！");
				return;
			}
		});
	});

</script>
	
    
<div class="pageFormContent" layoutH="0">
<div class="pageContent">
	<h1 class="press">${events.eventsNo}场次信息详情</h1>
	 <div class="tip"><span>场次信息</span></div>
		<p>
			<label>场次号：</label>
			<span>${events.eventsNo }</span>
		</p>
		<p>
			<label>场次日期：</label>
			<span>${fn:substring(events.eventDate,0,19)}</span>
		</p>
		<p>
	    	<label>场次地址：</label>
	    	<span>${events.address}</span>
	    </p>
	    <p>
	    	<label>支公司：</label>
	    	<span style="width: 300px;">${events.branchCompany }</span>
	    </p>
	    <p>
	    	<label>总公司：</label>
	    	<span  style="width: 300px;">${events.ownedCompany }</span>
	    </p>
	    <p>
	    	<label>项目编码：</label>
	    	<span>${shipPro.projectCode }</span>
	    </p>
	    <p>
	    	<label>项目名称：</label>
	    	<span style="width: 300px;">${shipPro.projectName }</span>
	    </p>
	    <p>
	    	<label>项目对接人：</label>
	    	<span>${shipPro.projectOwner }</span>
	    </p>
	    <p>
	    	<label>级别：</label>
	    	<span><hpin:id2nameDB id='${events.level2 }' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></span>
	    </p>
	   
	    <p>
	    	<label>预定人数：</label>
	    	<span>${events.headcount}</span>
		</p>
		<p>
	    	<label>录入人数：</label>
	    	<span style="padding-top:10px;">
    				<a title="已录人数" target="navTab" <c:if test="${exceptCount == 0}"> onclick="findRepeatInfo('${events.eventsNo }')" </c:if>
						href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${events.id}">${nowHeadcount }</a>
			</span>	
	    </p>
		<p>
	    	<label>报告人数：</label>
	    	<span>${pdfcount }</span>
	    </p>
	     <p>
	    	<label>入门礼检测餐套：</label>
	    	<span>${events.comboName}</span>
		</p>
		<p>
	    	<label>异常人数：</label>
	    	<span>
	    		<c:if test="${exceptCount == 0}">
	    			<span style="padding-top:10px;">
	    				<a title="异常人数" target="navTab" 
							href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${events.id}">${exceptCount }</a>
					</span>	
	    		</c:if>
	    		<c:if test="${exceptCount!=0}">
	    			<span style="padding-top:10px;">
	    				<a title="异常人数" target="navTab"
							href="${path}/events/erpCustomer!listCustomerException.action?ids=${events.id}">${exceptCount }</a>
					</span>	
	    		</c:if>
	    	</span>
		</p>
	      <input name="customer.eventsNo" class="eventsNo" type="hidden"  value="${events.eventsNo }" />
	      <input name="id" type="hidden"  value="${events.id }" />
	      
<!---->
<c:if test="${currentUser.userType!='查询用户'}">
<div class="formBar" >
 <ul>
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
              <a style="line-height: 25px;"class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add">添加客户信息</a>
            </div>
          </div>
        </li>
         <li>
          <form   id="form2" name="form2"   class="pageFormend"  onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpEvents!modifyEventsByExpress.action" method="post">
<input name="id" type="hidden"  value="${events.id }" />
 <input type="hidden" value="1" name="isBtn"> 
          <div class="button">
            <div class="buttonContent">
 
              <!-- <button type="button" onclick="submitForm()">客户信息采集结束</button> -->
                   <button type="button" onclick="submitForm()">客户信息采集结束</button>
             <%--  <a class="add" href="${path}/events/erpCustomer!customerSamplingEnd.action?eventsNo=${events.id}" target="navTab" rel="add">客户信息采集结束</a> --%>
       
            </div>
          </div>  </form>  
        </li>
      </ul>
<p></p>
</div>
</c:if>
<form   id="form1" name="form1"   class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);"  action="${path}/events/erpEvents!modifyEventsByExpress.action" method="post">
<input name="id" type="hidden"  value="${events.id }" />
 <input type="hidden" value="1" name="isBtn">
<div style="display: none;" class="pageContent" id="expressid">
        <div class="divider"></div>
        <p>
          <label>快递号：</label>
          <%-- <input name="express.trackingNumber" id="trackingNumber" type="text" class="required"  value="${express.trackingNumber }" /> --%>
          <input name="events.etrackingNumber" id="etrackingNumber" type="text" class="required"  value="${events.etrackingNumber }" />
        </p>
        <p>
			<label>日期：</label>
			<%-- <input id="edate" name="express.edate" datefmt="yyyy-MM-dd HH:mm:ss" value="${express.edate }"  type="text" class="date required" /> --%>
			<input id="edate" name="events.edate" datefmt="yyyy-MM-dd HH:mm:ss" value="${fn:substring(events.eventDate,0,14)}00:00"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
          <label>寄件人：</label>
<%--           <input name="express.name"  type="text" value="${express.name}" /> --%>
          <input name="events.ename"  type="text" value="${events.ename}" />
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

<div class="tip"><span>客户信息清单</span></div>
	<form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/events/erpCustomer!toEventNoListCustomer.action'){this.action = '${path}/events/erpCustomer!toEventNoListCustomer.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toEventNoListCustomer.action" method="post">
		<input type="hidden" name="filter_and_eventsNo_EQ_S" value="${events.eventsNo}"/>
		<input type="hidden" name="id" value="${events.id}"/>
		
	</form>
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.userType!='查询用户'||currentUser.accountName=='data'}">
		 <ul class="toolBar">
		 <%-- <li><a class="add" href="${path }events/erpExpress!toAddExpress.action?id=${events.id}" target="dialog"   style="cursor:pointer;"><span>寄快递</span></a></li>
			<li><a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			--%>
			<li><a class="add" href="${path}/events/erpCustomer!importCustomerOriginal.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
			
		 <web:exportExcelTag pagerFormId="pagerFindForms" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.events.web.ErpCustomerAction" 
								informationTableId="exportExcelTables"
								fileName="erpCustomerAction"></web:exportExcelTag>  
		<c:if test="${customerException }">
		 <web:security tag="EM_delEvents">
				<li><a class="delete" href="${path}/events/erpCustomer!delCusException.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
		 </web:security>
		 </c:if>
		<%--  <c:if test="${customerException }">
				<li><a class="icon" href="javascript:void(0)" onclick="selectAll()"><span>全选</span></a></li>
		 </c:if> --%>
		 <!-- 权限控制,如果是项目类型为无创检测,才会使用; -->
		<%--  <c:if test="${projectType.projectType == 'PCT_004' }">
			 <li><a id="customerInfoSure" class="icon" href="javascript:void(0);"><span>客户信息确认</span></a></li>
		 </c:if> --%>
		 
		 
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="98%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="50" nowrap="true">全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
				<!-- <th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th> -->
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<!-- <th  export = "true" columnEnName = "sex" columnChName = "性别" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">性别</th> -->
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
			<!-- 	<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th> -->
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<c:if test="${!customerException }">
					<th  export = "true" columnEnName = "branchCompany" columnChName = "支公司" >支公司</th>
				</c:if>
				
				<!-- <th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" id2NameBeanId = "org.ymjy.combo.dao.ComboDao">套餐名</th> -->
				
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<!-- <th  export = "true" columnEnName = "sampleType" columnChName = "样本类型" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">样本类型</th> -->
				<th  export = "true" columnEnName = "familyHistory" columnChName = "家族疾病史" >家族疾病史</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
				<c:if test="${!customerException }">
					<th  export = "true" columnEnName = "salesManNo" columnChName = "营销员工号" >营销员工号</th>
				</c:if>
				<th  export = "true" columnEnName = "department" columnChName = "部门" >部门</th>
				<c:if test="${!customerException }">
					<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" >采样日期</th>
				</c:if>
				<c:if test="${!customerException }">
					<th  export = "true" columnEnName = "pdffilepath" columnChName = "PDF报告状态" >PDF报告状态</th>
				</c:if>
				<th  export = "true" columnEnName = "note" columnChName = "备注" >备注</th>
				<c:if test="${customerException }">
					<th  export = "true" columnEnName = "result" columnChName = "失败原因" >失败原因</th>
				</c:if>
			<c:if test="${currentUser.userType!='查询用户'}">
				<th  export = "false" columnEnName = "" columnChName = "" >操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="center">
							<c:if test="${customerException}">
							<input type="checkbox" name="ids" value="${erpCustomer.id}">
							</c:if>
						${ status.count }</td>
						<%-- <td align="center">	${erpCustomer.eventsNo}</td> --%>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	
						<%-- <hpin:id2nameDB id='${erpCustomer.sex}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/> --%>
						${erpCustomer.sex}
						</td>
						<td align="center">	${erpCustomer.age}</td>
						<%-- <td align="center">	${erpCustomer.idno}</td> --%>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<c:if test="${!customerException }">
							<td align="center">	${erpCustomer.branchCompany}</td>
						</c:if>
						<td align="center">
							<%-- <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${erpCustomer.setmealName}"/> --%>
						${erpCustomer.setmealName}
						</td>
						<%-- <td align="center">	${erpCustomer.sampleType}</td> --%>
						<td align="center">	${erpCustomer.familyHistory}</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<c:if test="${!customerException }">
							<td align="center">	${erpCustomer.salesManNo}</td>
						</c:if>
						<td align="center">	${erpCustomer.department}</td>
						<c:if test="${!customerException }">
							<td align="center">	${fn:substring(erpCustomer.samplingDate,0,14)}00:00</td>
						</c:if>
						<c:if test="${!customerException }">
							<td align="center">	
								<c:if test="${fn:length(erpCustomer.pdffilepath)<53}">
									没有报告
								</c:if>
								<c:if test="${fn:length(erpCustomer.pdffilepath)>53}">
									<c:if test="${currentUser.userType=='1'}">
									    <a href="${erpCustomer.pdffilepath}" target="blank">${fn:substring(erpCustomer.pdffilepath,55,-1)}</a> 
									</c:if>
									<c:if test="${currentUser.userType!='1'}">
									          有报告 
									</c:if>
								</c:if>
							</td>
						</c:if>
						<td align="center">	${erpCustomer.note}</td>
						<c:if test="${customerException }">
							<td align="center">	${erpCustomer.result}</td>
						</c:if>
					<c:if test="${currentUser.userType!='查询用户'||currentUser.accountName=='data'}">
						<td align="center">
							<c:if test="${currentUser.userType=='1' || currentUser.userType =='' || currentUser.userType == null }">
							<div class="panelBar">
								 <ul class="toolBar">
								 	<li><a class="edit" onclick="edit('${erpCustomer.id}',${customerException})" style="cursor:pointer;">
								 		<span>
									 		<c:if test="${!customerException }">变更</c:if>
									 		<c:if test="${customerException }">处理</c:if>
								 		</span>
								 	</a></li>
								 	<%-- <c:if test="${customerException }">
								 	<li><a class="delete" onclick="deleteById('${erpCustomer.id}')" style="cursor:pointer;">
								 		<span>
									 		删除
								 		</span>
								 	</a></li>
								 	</c:if>	 --%>		
								 	<%-- <li><a class="delete" href="${path}/events/erpCustomer!delOneCustomer.action?id=${erpCustomer.id}"  postType="string" title="确定要删除吗?" target="navTab"><span>删除</span></a></li> --%>
								 </ul>
							</div>
							 </c:if>
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>
