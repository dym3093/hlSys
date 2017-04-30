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
		navTab.openTab("modifyCustomer01", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomer!toListCustomerNoReport.action'){this.action = '${path}/events/erpCustomer!toListCustomerNoReport.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toListCustomerNoReport.action" method="post"  rel="pagerForm" id="pagerFindForm">
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
					<input type="hidden" name="id" value="${id}"/>
				</td>
				<td>
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
			<li><a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${id}" target="navTab" rel="add"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<c:if test="${currentUser.userType=='1'}">
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			</c:if>
			<li><a class="add" href="${path}/events/erpCustomer!importCustomer.action?id=${id}" target="navTab" rel="add"><span>导入</span></a></li>
		 <web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPageNoReport" 
								actionAllUrl="org.hpin.events.web.ErpCustomerAction" 
								informationTableId="exportExcelTable"
								fileName="erpCustomerNoReport"></web:exportExcelTag> 
		
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
				<th  export = "true" columnEnName = "branchCompany" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "ownedCompany" columnChName = "总公司" >总公司</th>
				<th  export = "true" columnEnName = "provice"  columnChName = "省" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">省</th>
				<th  export = "true" columnEnName = "city" columnChName = "市" id2NameBeanId = "org.hpin.base.region.dao.RegionDao">市</th>
		   <!-- <th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" id2NameBeanId = "org.ymjy.combo.dao.ComboDao">套餐名</th> -->
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名">套餐名</th>
				<th  export = "true" columnEnName = "sampleType" columnChName = "样本类型" id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">样本类型</th>
				<th  export = "true" columnEnName = "familyHistory" columnChName = "家族疾病史" >家族疾病史</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" >采样日期</th>
				<!-- <th  export = "true" columnEnName = "CREATE_TIME" columnChName = "创建日期" >创建日期</th> -->
				<th  export = "true" columnEnName = "pdffilepath" columnChName = "PDF报告状态" >PDF报告状态</th>
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
						<td align="center">	 
							<hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/>
						</td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center">	<hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpCustomer.provice }"/></td>
						<td align="center">	<hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpCustomer.city }"/></td>
						
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
						<td align="center">	
							<c:if test="${fn:length(erpCustomer.pdffilepath)<53}">
								没有报告
							</c:if>
							<c:if test="${fn:length(erpCustomer.pdffilepath)>53}">
							有报告
								<%-- <a href="${erpCustomer.pdffilepath}" target="blank">${fn:substring(erpCustomer.pdffilepath,53,-1)}</a> --%>
							</c:if>
						</td>
						<td align="center">	${erpCustomer.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 