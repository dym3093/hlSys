<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript" src="${path }/scripts/plugin/shieldLayer_plugin.js"></script>
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
			var jyId = $("#jyId",navTab.getCurrentPanel()).val();
			var jyPage = $("#jyPage",navTab.getCurrentPanel()).val();
			navTab.openTab("modifyEvents", "../events/erpCustomer!toModifyEvents.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{"jyId":jyId,"jyPage":jyPage} });
		}
	}
	$(document).ready(function(){
		//导出加载等待问题;
		shieldReadyNavTab(2, navTab.getCurrentPanel()); //进度条显示初始化;
	});
	
	//2.单机导出进入后台查看数据;指定当前页面标示; report_eventsSalesman 当前操作类表加上界面加上累加数字;
	//注意该showTheMb()方法在java中处理生成;
	function showTheMb() {
		//获取后台改界面是否存在上次未导出数据;  当前处理不管是否存在直接清空,重新下载;
		var pageType = navTab.getCurrentPanel();
		var result = dealSessionPre("events_listAllCustomer");//(预留提示处理;)
		//if(result == "notexist") {
		//	shieldShow(pageType);
			//没200毫秒调用一次;当用户关闭该页面再次进入时;自动重新计算flag值;不考虑以前的;
		//	dealSessionProcess("events_listAllCustomer", pageType);
		//} else {
		//	 alertMsg.warn("请您耐心等待上次导出,完成后才能开始本次导出功能1!");
		//	 return false;
		//}
		return true;
		
	}
	
	var toDownloadPDFByEventsNo = function(){
		//打开导入窗口
		$.pdialog.open("${path}/events/erpCustomer!toDownloadPDFByEventsNo.action", "download", "下载PDF",
			{width:600,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true} );	
	};
	
	var toDownloadPDFByCustomerInfo = function(){
		
	};
	function clearInput(){
		$(':input','#pagerFindForm',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('selected');  
	}
	
	/*	
	 *	@author CQ
	 *	@since 2017年4月5日18:46:44
	 *	更新样本状态 
	 */
	function showDeletedStatusDialogPage(obj) {
		var checkedObj = getCheckedObj();
		if ($(checkedObj).length == 0) {
			alertMsg.warn("请选择要更改样本异常的客户!" );
			return;
		}
		var tabId = getNavTabId();
		var customerId = $(checkedObj).val();
		var str = "?tabId=" + tabId + "&customerId=" + customerId + "&deletedStatus=" + obj;
		$.pdialog.open("${path}/events/erpCustomer!showDeletedStatusDialogPage.action" + str, "showDeletedStatusDialogPage", "样本异常",
				{width:360,height:200,mask:true,mixable:false,minable:true,resizable:true,drawable:true,fresh:true} );
	}
	
	function getCheckedObj() {
		var checkedObj =$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel());
		return checkedObj;
	}
	
	function getNavTabId(){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		return navTabId;
	}
</script>
<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/events/erpCustomer!toListAllCustomer.action'){this.action = '${path}/events/erpCustomer!toListAllCustomer.action' ;} ;return navTabSearch(this);" action="${path}/events/erpCustomer!toListAllCustomer.action" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent" >
			<tr>
				<td>
					<label>场次号：</label> 
					<input type="text" name="filter_and_eventsNo_LIKE_S" value="${filter_and_eventsNo_LIKE_S}"/>
				</td>
				<td>
					<label>姓名：</label> 
					<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}"/>
				</td>
				<td>
					<label>条形码：</label> 
					<input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<!--
				<td>
					<label>报告状态：</label> 
					<select name="reportState" style="width:195px;margin-top:6px;margin-left:5px; line-height: 25px;">
						<option value="" selected='selected'>---请选择---</option>
						<option value="0" ${reportState == '0' ? "selected='selected'" : ''}>已出报告</option>
						<option value="1" ${reportState == '1' ? "selected='selected'" : ''}>未出报告</option>
					</select>
				</td> -->
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
				<td>
					<label>手机号：</label> 
					<input type="text" name="filter_and_phone_LIKE_S" value="${filter_and_phone_LIKE_S}"/>
				</td>
				<td>
					<label>套餐名：</label> 
					<input type="text" name="filter_and_setmealName_LIKE_S" value="${filter_and_setmealName_LIKE_S}"/><input type="hidden" name="id" value="${events.id}"/>
				</td>
				
				<td></td>
			</tr>
			<tr>
				<td>
					<label>身份证号：</label> 
					<input type="text" name="filter_and_idno_LIKE_S" value="${filter_and_idno_LIKE_S}"/>
				</td>
				<td><label>采样起始日期：</label> 
					<input type="text" name="filter_and_samplingDate_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_samplingDate_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>采样结束日期：</label> 
					<input type="text" name="filter_and_samplingDate_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_samplingDate_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				
				<td>
				</td>
			</tr>
			<tr>
				<td >
					<label>检测机构：</label>
					<select id="testInstitution" name="filter_and_testInstitution_LIKE_S" style="width:195px;margin-top:5px;margin-left:5px">
						<c:if test="${filter_and_testInstitution_LIKE_S==''}">
							<option value="" selected='selected'>---请选择---</option>
							<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
							<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
						</c:if>
						<c:if test="${filter_and_testInstitution_LIKE_S!=''}">
							<c:if test="${filter_and_testInstitution_LIKE_S=='南方'}">
								<option value=""  selected='selected'>---请选择---</option>
								<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
								<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
							</c:if>
							<c:if test="${filter_and_testInstitution_LIKE_S!='南方'}">
								<option value=""  selected='selected'>---请选择---</option>
								<option value="金域" ${filter_and_testInstitution_LIKE_S == '金域' ? "selected='selected'" : ''}>金域检测</option>
								<option value="南方" ${filter_and_testInstitution_LIKE_S == '南方' ? "selected='selected'" : ''}>南方检测</option>
							</c:if>
						</c:if>
					</select>
				</td>
				<td>
					<label>导入开始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="createTime1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>导入结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="createUserName2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			<tr>
				<td>
					<label>省：</label> <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
						<select id="province" name="filter_and_provice_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provice_EQ_S}"></option>
					</select>
				</td>
				<td>
					<label>市：</label> <select id="city"
						name="filter_and_city_EQ_S" rel="iselect">
							<option value="${filter_and_city_EQ_S}"></option>
					</select>
				</td>
				<td>
					<label>样本状态：</label> 
					<select id="deleteStatus" name="filter_and_isDeleted_EQ_I" rel="iselect">
						<option value="">---请选择---</option>
						<option value="0" ${filter_and_isDeleted_EQ_I == 0 ? 'selected' : ''}>正常样本</option>
						<option value="2" ${filter_and_isDeleted_EQ_I == 2 ? 'selected' : ''}>异常样本</option>
					</select>
				</td>
				<td style="padding-left: 12px;">
					<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
				</td>
			</tr>
			<tr hidden="hidden">
				<td><!-- 用于标识金域报告修改信息 -->
					<input id="jyId" type="text" name="jyId" value="${jyId}"/>
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
			<li>
				<!-- <a class="add" href="${path}/events/erpCustomer!toAddCustomer.action?eventsNo=${events.id}" target="navTab" rel="add"><span>添加</span></a>
				 -->
				
			</li>
			<web:security tag="CI_deleteInfo">
			<li><a class="delete" href="${path}/events/erpCustomer!delCustomer.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			</web:security>
			<%-- <c:if test="${currentUser.userType=='1'}"> --%>
			<web:security tag="CI_updateInfo">
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			</web:security>
			<li><a class="edit" onclick="showDeletedStatusDialogPage(2)" style="cursor:pointer;"><span>异常样本</span></a></li>
			<li><a class="icon" onclick="showDeletedStatusDialogPage(0)" style="cursor:pointer;"><span>取消异常样本</span></a></li>
			<%-- </c:if> --%>
			<web:security tag="CI_ExportInfos">
			<web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPageAll" 
								actionAllUrl="org.hpin.events.web.ErpCustomerAction" 
								informationTableId="exportExcelTable"
								fileName="firstaidResourceInfo"></web:exportExcelTag>
			</web:security>
			<c:if test="${currentUser.roleNames=='系统管理员'}">
				<li><a class="add" onclick="toDownloadPDFByEventsNo()" style="cursor:pointer;" ><span>根据场次号下载金域PDF</span></a></li>
				<li><a class="add" href="${path}/events/erpCustomer!toSaveScheduleInfo.action" target="dialog" title="添加任务信息" width="900" height="480"
				     mask="true", maxable="true", minable="true", resizable="true", drawable="true" style="cursor:pointer;" ><span>添加定时任务</span></a></li>
			</c:if>
			
		</ul>
		</c:if>
		<%-- </web:security> --%>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="210" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "eventsNo" columnChName = "场次号" >场次号</th>
				<th  export= "true" columnEnName = "code" columnChName = "条形码" >条形码</th>
				<th  export = "true" columnEnName = "name" columnChName = "姓名" >姓名</th>
				<th  export = "true" columnEnName = "sex" columnChName = "性别" >性别</th>
				<th  export = "true" columnEnName = "age" columnChName = "年龄" >年龄</th>
				<th  export = "true" columnEnName = "idno" columnChName = "身份证号" >身份证号</th>
				<th  export = "true" columnEnName = "phone" columnChName = "电话" >电话</th>
				<th  export = "true" columnEnName = "department" columnChName = "部门" >部门</th>
				<th  export = "true" columnEnName = "branchCompanyId" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "setmealName" columnChName = "套餐名" >套餐名</th>
				<th  export = "true" columnEnName = "salesMan" columnChName = "营销员" >营销员</th>
				<th  export = "true" columnEnName = "pdffilepath" columnChName = "所属公司" >所属公司</th>
				<th  export = "true" columnEnName = "samplingDate" columnChName = "采样日期" style="width: 80px;">采样日期</th>
				<th  export = "true" columnEnName = "createTime" columnChName = "导入日期" >导入日期</th>
				<th  export = "true" columnEnName = "statusYm" columnChName = "客户状态" >客户状态</th>
				<th  export = "true" columnEnName = "pdffilepath" columnChName = "PDF报告地址" >PDF报告查看</th>
				<th  export = "true" columnEnName = "note" columnChName = "note" >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpCustomer" varStatus="status">
					<tr target="sid_user" rel="${erpCustomer.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${erpCustomer.id}">
							</c:if>
							${ status.count }
						</td>
						<td align="center">	${erpCustomer.eventsNo}</td>
						<td align="center">	${erpCustomer.code}</td>
						<td align="center">	${erpCustomer.name}</td>
						<td align="center">	${erpCustomer.sex}</td>
						<td align="center">	${erpCustomer.age}</td>
						<td align="center">	${erpCustomer.idno}</td>
						<td align="center">	${erpCustomer.phone}</td>
						<td align="center">	${erpCustomer.department}</td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
						<td align="center">
						<%-- <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${erpCustomer.setmealName}"/> --%>
						${erpCustomer.setmealName}
						</td>
						<td align="center">	${erpCustomer.salesMan}</td>
						<td align="center">	<hpin:id2nameDB id="${erpCustomer.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center">	<fmt:formatDate value='${erpCustomer.samplingDate }' pattern='yyyy-MM-dd'/></td>
						<td align="center">	${fn:substring(erpCustomer.createTime,0,19)}</td>
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
						<td align="center">
							<c:if test="${fn:length(erpCustomer.pdffilepath)>53}">
								<web:security tag="customerReport">
									<a href="${erpCustomer.pdffilepath}" target="blank">${fn:substring(erpCustomer.pdffilepath,55,-1)}</a>
								</web:security>
							</c:if>
						</td>
						<td align="center">	${erpCustomer.note}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 