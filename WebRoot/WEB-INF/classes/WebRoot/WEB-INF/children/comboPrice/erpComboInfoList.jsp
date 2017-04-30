<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>

<script src="${path}/jquery/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	//更改套餐信息
	function editComboInfo(){
		var idArr = [];
		var id;
		$("input[name='ids']:checked").each(function(i){
			idArr.push($(this).val());
		});

		if(idArr.length>=2){
			alertMsg.info("一次只能对一条记录进行修改！");
			return;
		}else if(idArr.length==0){
			alertMsg.info("请选择要修改的支公司套餐！");
			return;
		}
		var company=$("input[name='ids']:checked").parent("td").nextAll().eq(0).text();
		var totalCompany=$("input[name='ids']:checked").parent("td").nextAll().eq(1).text();
		var province=$("input[name='ids']:checked").parent("td").nextAll().eq(4).text();
		var city=$("input[name='ids']:checked").parent("td").nextAll().eq(5).text();
		navTab.openTab("editComboInfo", 
				"../children/erpGroupOrderCombo!getComboInfo.action",
				{ title:"修改套餐价格", fresh:false,data:{"company":company,"totalCompany":totalCompany,"province":province,"city":city} });
		
	}
	
	
</script>

<div class="tip"><span>套餐信息查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" 
		onsubmit="if(this.action != '${path}/children/erpGroupOrderCombo!getErpGroupComboInfoList.action'){this.action = '${path}/children/erpGroupOrderCombo!getErpGroupComboInfoList.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpGroupOrderCombo!getErpGroupComboInfoList.action" method="post"	>
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<tr>
				<td >
					<label>支公司：</label> 
					<input type="text" name="filter_and_branch_commany_LIKE_S" value="${filter_and_branch_commany_LIKE_S }" />
				</td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="filter_and_create_Time_GEST_T"  id="d1" style="width: 170px;" 
						class="condition" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  
						readonly="readonly" value="${filter_and_create_Time_GEST_T}" />
						<a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="filter_and_create_Time_LEET_T" id="d2" style="width: 170px;" 
						class="condition" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" 
						readonly="readonly" value="${filter_and_create_Time_LEET_T}" />
						<a class="inputDateButton" href="javascript:;">选择</a>
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
		<ul class="toolBar" >
            <li style="margin-left: 10px;"><a class="edit" onclick="editComboInfo()"><span>修改套餐</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="50">序号</th>
				<th width="320" export = "true" columnEnName = "company" columnChName = "支公司" > 支公司 </th> 
				<th width="320" export = "true" columnEnName = "customerNameSimple" columnChName = "总公司" > 总公司 </th>
				<th width="200" export = "true" columnEnName = "combocount" columnChName = "套餐数量" > 套餐数量 </th> 
				<th width="150" export = "true" columnEnName = "salesman" columnChName = "创建人 " > 创建人 </th> 
				<th width="200" export = "true" columnEnName = "province" columnChName = "省" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao"> 省 </th> 
				<th width="200" export = "true" columnEnName = "city" columnChName = "市" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao"> 市 </th> 
				<th width="150" export = "true" columnEnName = "createtime" columnChName = "创建时间 " > 创建时间 </th> 
				<!-- <th width="150" export = "true" columnEnName = "note" columnChName = "备注 " > 备注 </th>  -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr target="sid_user" rel="${entity.rn}" id="rows">
					<td align="center">
						<input type="checkbox" name="ids" value="${entity.rn}">${status.count}
					</td>
					<td align="center" title="${entity.company}">  ${entity.company} </td>
					<td align="center" title="${entity.customerNameSimple}">  ${entity.customerNameSimple} </td>  
					<td align="center"> ${entity.combocount} </td>
					<td align="center" title="${entity.salesman}"> ${entity.salesman} </td>
					<td align="center"> 
						<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.province }" />
					</td>
					<td align="center">
						<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.city }" />
					</td>
					<td align="center"> ${fn:substring(entity.createtime,0,10)} </td>
						<%-- <td align="center" title="${entity.note}"> ${entity.note} </td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div> 