<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" language="javascript">
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
		navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomer!toListCustomer.action'){this.action = '${path}/events/erpCustomer!toListCustomer.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toListCustomer.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
					<input type="hidden" name="filter_and_eventsNo_LIKE_S" value="${events.eventsNo}"/>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
				</td>
				<td>
					<label>套餐名：</label> 
					<input type="text" name="filter_and_setmealName_LIKE_S" value="${filter_and_setmealName_LIKE_S}"/>
					<input type="hidden" name="id" value="${events.id}"/>
				</td>
				<td><label>客户状态：</label>
					<select id="statusYm" name="filter_and_statusYm_IN_S" class="required" style="height:20px;margin:5px;width:200px;">
						<option value="" 	<c:if test="${empty filter_and_statusYm_IN_S}">selected="selected"</c:if> >-- 全部  --</option>
						<option value="150" <c:if test="${filter_and_statusYm_IN_S== 150}">selected="selected"</c:if> >样本已获取</option>
						<option value="200" <c:if test="${filter_and_statusYm_IN_S== 200}">selected="selected"</c:if> >样本已送检</option>
						<option value="300" <c:if test="${filter_and_statusYm_IN_S== 300}">selected="selected"</c:if> >电子报告已出具</option>
						<option value="400" <c:if test="${filter_and_statusYm_IN_S== 400}">selected="selected"</c:if> >报告已下载</option>
						<option value="500" <c:if test="${filter_and_statusYm_IN_S== 500}">selected="selected"</c:if> >报告已打印</option>
						<option value="600" <c:if test="${filter_and_statusYm_IN_S== 600}">selected="selected"</c:if> >报告已寄送</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>采样起始日期：</label> 
					<input type="text" name="filter_and_samplingDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_samplingDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>采样结束日期：</label> 
					<input type="text" name="filter_and_samplingDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_samplingDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
				
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<%-- <web:security tag="noemeruser_enter"> --%>
		<c:if test="${currentUser.userType!='查询用户'}">
		<ul class="toolBar">
			<li><a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<c:if test="${currentUser.userType=='1'}">
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			</c:if>
			<li><a class="add" href="${path}/events/erpCustomer!importCustomer.action?id=${events.id}" target="navTab" rel="add"><span>导入</span></a></li>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.events.web.ErpCustomerAction" 
								informationTableId="exportExcelTable"
								fileName="erpCustomerAction"></web:exportExcelTag> 
		
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别">性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "department" columnChName = "部门" >部门</th>
				<th  export = "true" columnEnName = "branchCompanyId" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "ownedCompanyId" id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"  columnChName = "总公司" >总公司</th>
				<th  export = "true" columnEnName = "provice"  columnChName = "省" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省</th>
				<th  export = "true" columnEnName = "city" columnChName = "市" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">市</th>
		   <!-- <th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" id2NameBeanId = "org.ymjy.combo.dao.ComboDao">套餐名</th> -->
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名">套餐名</th>
				<th  export = "true" columnEnName = "sampleType" columnChName = "样本类型" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">样本类型</th>
				<th  export = "true" columnEnName = "familyHistory" columnChName = "家族疾病史" >家族疾病史</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" >采样日期</th>
				<th  export = "true" columnEnName = "statusYm" columnChName = "客户状态" >客户状态</th>
				<!-- <th  export = "true" columnEnName = "CREATE_TIME" columnChName = "创建日期" >创建日期</th> -->
				<%-- <th  export = "true" columnEnName = "pdffilepath" columnChName = "PDF报告状态" >PDF报告状态</th> --%>
				<th  export = "true" columnEnName = "note" columnChName = "备注" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="center"><input type="checkbox" name="ids" value="${erpCustomer.id}">${ status.count }</td>
						<td align="center">	${erpCustomer.eventsNo}</td>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	${erpCustomer.sex}</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center">	${erpCustomer.department}</td>	
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao"/></td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.provice }" beanId="org.hpin.base.region.dao.RegionDao" /></td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.city }" beanId="org.hpin.base.region.dao.RegionDao" /></td>
						
						<td align="center">
							<%-- <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${erpCustomer.setmealName}"/> --%>
							${erpCustomer.setmealName}
						</td>
						<td align="center">	
							<%-- ${erpCustomer.sampleType} --%>
							<hpin:id2nameDB id='${erpCustomer.sampleType}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/>
						</td>
						<td align="center">	${erpCustomer.familyHistory}</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<td align="center">	${fn:substring(erpCustomer.samplingDate,0,14)}00:00</td>
						<%--
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
						--%>
						<td align="center">
							<c:choose>
								<c:when test="${erpCustomer.statusYm==110}">采样盒已寄出</c:when>
								<c:when test="${erpCustomer.statusYm==140}">样本采集中</c:when>
								<c:when test="${erpCustomer.statusYm==150}">样本已获取</c:when>
								<c:when test="${erpCustomer.statusYm==200}">样本已寄送 </c:when>
								<c:when test="${erpCustomer.statusYm==300}">电子报告已出具</c:when>
								<c:when test="${erpCustomer.statusYm==400}">报告已下载</c:when>
								<c:when test="${erpCustomer.statusYm==500}">报告已打印</c:when>
								<c:when test="${erpCustomer.statusYm==600}">报告已寄送</c:when>
							</c:choose>
						</td>
						<td align="center">	${erpCustomer.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 