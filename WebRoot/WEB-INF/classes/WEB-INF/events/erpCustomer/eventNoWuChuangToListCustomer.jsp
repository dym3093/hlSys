<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

	
    
<div class="pageFormContent" layoutH="0">
<div class="pageContent">
	<h1 class="press">${events.eventsNo}场次信息详情</h1>
	 <div class="tip"><span>场次信息</span></div>
		<table>
			<tr>
				<td><label>场次号：</label></td>
				<td><span>${events.eventsNo }</span></td>
				<td><label>场次日期：</label></td>
				<td><span>${fn:substring(events.eventDate,0,19)}</span></td>
				<td><label>场次地址：</label></td>
				<td><span>${events.address}</span></td>
			</tr>
			<tr>
				<td><label>支公司：</label></td>
				<td><span style="width: 300px;">${events.branchCompany }</span></td>
				<td><label>总公司：</label></td>
				<td><span  style="width: 300px;">${events.ownedCompany }</span></td>
				<td><label>项目编码：</label></td>
				<td><span>${shipPro.projectCode }</span></td>
			</tr>
			<tr>
				<td><label>项目名称：</label></td>
				<td><span style="width: 300px;">${shipPro.projectName }</span></td>
				<td><label>项目对接人：</label></td>
				<td><span>${shipPro.projectOwner }</span></td>
				<td><label>级别：</label></td>
				<td><span><hpin:id2nameDB id='${events.level2 }' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></span></td>
			</tr>
			<tr>
				<td><label>预定人数：</label></td>
				<td><span>${events.headcount}</span></td>
				<td><label>录入人数：</label></td>
				<td>
					<span style="padding-top:10px;">
    					<a title="已录人数" target="navTab" 
							href="${path}/events/erpCustomer!toEventNoListCustomer.action?id=${events.id}">${nowHeadcount }</a>
					</span>	
				</td>
				<td><label>报告人数：</label></td>
				<td><span>${pdfcount }</span></td>
			</tr>
			<tr>
				<td><label>入门礼检测餐套：</label></td>
				<td><span>${events.comboName}</span></td>
				<td><label>异常人数：</label></td>
				<td>
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
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		
	      <input name="customer.eventsNo" class="eventsNo" type="hidden"  value="${events.eventsNo }" />
	      <input name="id" type="hidden"  value="${events.id }" />
	      
<form   id="form1" name="form1"   class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);"  action="${path}/events/erpEvents!modifyEventsByExpress.action" method="post">
<input name="id" type="hidden"  value="${events.id }" />
 <input type="hidden" value="1" name="isBtn">
<div style="display: none;" class="pageContent" id="expressid">
        <div class="divider"></div>
        <p>
          <label>快递号：</label>
          <input name="events.etrackingNumber" id="etrackingNumber" type="text" class="required"  value="${events.etrackingNumber }" />
        </p>
        <p>
			<label>日期：</label>
			<input id="edate" name="events.edate" datefmt="yyyy-MM-dd HH:mm:ss" value="${fn:substring(events.eventDate,0,14)}00:00"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
          <label>寄件人：</label>
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
	<form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/events/erpCustomer!toEventWuChuangNoListCustomer.action'){this.action = '${path}/events/erpCustomer!toEventWuChuangNoListCustomer.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toEventWuChuangNoListCustomer.action" method="post">
		<input type="hidden" name="filter_and_eventsNo_EQ_S" value="${events.eventsNo}"/>
		<input type="hidden" name="id" value="${events.id}"/>
		<input type="hidden" name="qrcodeId" value="${qrcodeId}"/>
		<input type="hidden" name="qrcode" value="${qrcode}"/>
		
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
		 
		 <!-- 权限控制,如果是项目类型为无创检测,才会使用; -->
		 <c:if test="${(projectType.projectType == 'PCT_004' || projectType.projectType == 'PCT_005') && pushStatus != '1' }">
			 <li><a id="customerInfoSure" class="icon" href="javascript:void(0);"><span>客户信息推送</span></a></li>
		 </c:if>
		 
		 
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTables"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th export="true" columnEnName="samipleDateStr" columnChName="采样日期">采样日期</th>
				<th export="true" columnEnName="code" columnChName="客户检测码">客户检测码</th>
				<th export="true" columnEnName="name" columnChName="姓名">姓名</th>
				<th export="true" columnEnName="sex" columnChName="性别">性别</th>
				<th export="true" columnEnName="age" columnChName="年龄">年龄</th>
			    <th export="true" columnEnName="idno" columnChName="身份证号">身份证号</th>
				<th export="true" columnEnName="phone" columnChName="电话">电话</th>
				<th export="true" columnEnName="otherCompanyId" 
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName="二维码对应支公司">二维码对应支公司</th>
				<th  export="true" columnEnName="branchCompanyId" columnChName="设备所在支公司" 
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao">设备所在支公司</th>
				<th export="true" columnEnName="setmealName" columnChName="套餐名">套餐名</th>
				<th export="true" columnEnName="salesMan" columnChName="营销员">营销员</th>
				<th export="true" columnEnName="salesManNo" columnChName="营销员工号">营销员工号</th>
				<th export="true" columnEnName="height" columnChName="身高">身高</th>
				<th export="true" columnEnName="weight" columnChName="体重">体重</th>
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
							${status.count }
						</td>
						<td align="center">	${fn:substring(erpCustomer.samplingDate,0,10)}</td>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">${erpCustomer.sex}</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.otherCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" /></td>
						<td align="center"><hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" /></td>
						<td align="center">${erpCustomer.setmealName}</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<td align="center">	${erpCustomer.salesManNo}</td>
						<td align="center">	${erpCustomer.height}</td>
						<td align="center">	${erpCustomer.weight}</td>
						
						<c:if test="${currentUser.userType!='查询用户'||currentUser.accountName=='data'}">
						<td align="center">
							<c:if test="${currentUser.userType=='1' || currentUser.userType =='' || currentUser.userType == null }">
								<a class="button" onclick="edit('${erpCustomer.id}',${customerException})" style="cursor:pointer;">
									<span>
										<c:if test="${!customerException }">变更</c:if>
										<c:if test="${customerException }">处理</c:if>
									</span>
								</a>
							</c:if>
							<!-- 当该场次已经推送后才会出现该按钮.避免错误操作; -->
							<c:if test="${projectType.projectType == 'PCT_004' && pushStatus == '1' }">
							<a class="button" onclick="pushSingleNumEvent('${erpCustomer.id}')" style="cursor:pointer; margin-left: 2px;">
								<span>推送</span>
							</a>
							</c:if>
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 
</div>

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
					alertMsg.error(data.message);
				}
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
}

function changeProduct3(ids) {
	var obj=document.getElementById("expressid").style.display;
	if(obj=="none") {
		$("#expressid",navTab.getCurrentPanel()).show();
	}else{
		$("#expressid",navTab.getCurrentPanel()).hide();
	}	
}
function changeProduct4(ids) {
	if(confirm("确定要寄快递？")){
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
		document.form2.isBtn.value="2";
		$(".pageFormend",navTab.getCurrentPanel()).submit();

}

/**
 * add by henry.xu
 */
$("#customerInfoSure", navTab.getCurrentPanel()).on("click", function() {
	$(this).remove();
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
	
/**
 * add by henry.xu 20170414
 * 点击推送按钮，调用知康的接口，将当前此条客户信息推送给知康
 */
function pushSingleNumEvent(customerId) {
	
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"eventsId":"${events.id}", "customerId":customerId},
		url: "${path}/events/erpCustomer!pushSingleNumEvent.action",
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
}

</script>