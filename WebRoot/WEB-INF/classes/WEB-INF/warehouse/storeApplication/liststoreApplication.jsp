<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
	select{
		margin-left:5px;
		margin-top:5px;
		width: 192px;
	}	
</style>
<script type="text/javascript">
function changeBar() {
	var ids = '';
	var count = 0;
	var status = 0;
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).attr("status");
	});
	if(count == 0) {
		alert('请选择一条信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息！');
		return ;		
	}
	else {
		 if(status >= 2) {
		 	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("liststoreApplication", "../warehouse/storeApplication!modifystoreApplication.action?id="+ids+"&navTabId="+navTabId, { title:"跟踪", fresh:false, data:{} });		
     }else {
			alert('只有完成制码的信息才能变更！');
			return ;
	    }
   }
}

var pullStoreApplication = function(id, batNo){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	alert(batNo);
	navTab.openTab("pullStoreApplication", "${path}/warehouse/storeApplication!pullStoreApplication.action", { title:"派发申请【"+batNo+"】", fresh:false, data:{id:id , navTabId:navTabId,batNo:batNo } });		
};
	
	$("#statusSel").change( function() {	//发货状态
		var selectVal=$("#statusSel option:selected").val();
		$(":input[status='os']").val(selectVal);
	});
	
	$(function(){	
		$("#companyText").val("");
		$("#owedText").val("");
//		$(":input[status='os']").val("");
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/warehouse/storeDelivery!setOwedCompanySelected.action",
			success: function(data){
				var owed = $("#owedTxt").val();
				$("#owedSel").empty();
				var option="<option value=''>---请选择---</option>";
				var oweds = eval("("+data+")").owedCompany;
				for(var i=0;i<oweds.length;i++){	
					if(owed==oweds[i].CUSTOMER_NAME_SIMPLE){
						option += "<option value='"+oweds[i].CUSTOMER_NAME_SIMPLE+"' selected='selected'>"+oweds[i].CUSTOMER_NAME_SIMPLE+"</option>";
					}else{
						option += "<option value='"+oweds[i].CUSTOMER_NAME_SIMPLE+"'>"+oweds[i].CUSTOMER_NAME_SIMPLE+"</option>";
					}
				}
					//将节点插入到下拉列表中
					$("#owedSel").append(option);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
			
	});
	
	$(function(){	//发货状态
		var selectStatus = $(":input[status='os']").val();
		if(selectStatus!=""){
			$("#statusSel").find("option[value='"+selectStatus+"']").attr("selected",true);
		}
	});
	
	$("#statusSel").change( function() {	//发货状态
		var selectVal=$("#statusSel option:selected").val();
		$(":input[status='os']").val(selectVal);
	});
	
	$("#companySel").change( function() {	//支公司
		var selectVal=$("#companySel option:selected").text();
		$("#companyText").val(selectVal);
	});
	
	$("#owedSel").change( function() {	//总公司
		var selectVal=$("#owedSel option:selected").text();
		$("#owedText").val(selectVal);
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/warehouse/storeDelivery!setCompanySelected.action",
			data:{"owedComapny":$("#owedText").val()},
			success: function(data){
				var company = $("#companyText").val();
				$("#companySel").empty();
				var option="<option value=''>---请选择---</option>";
				var companys = eval("("+data+")").company;
				for(var i=0;i<companys.length;i++){	
					if(company==companys[i].CUSTOMER_NAME_SIMPLE){
						option += "<option value='"+companys[i].BRANCH_COMMANY+"' selected='selected'>"+companys[i].BRANCH_COMMANY+"</option>";
					}else{
						option += "<option value='"+companys[i].BRANCH_COMMANY+"'>"+companys[i].BRANCH_COMMANY+"</option>";
					}
				}
					//将节点插入到下拉列表中
					$("#companySel").append(option);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	});
	
	var addOptionContent1 = function(obj){
		var optText = $.trim($(obj).children("option[selected='selected']").html()); 
		$("#ownedCompany").val(optText);
	};	
	var addOptionContent2 = function(obj){
		var optText = $.trim($(obj).children("option[selected='selected']").html()); 
		$("#bannyCompanyName").val(optText);
	};	
</script>

<html>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/warehouse/storeApplication!liststoreApplication.action'){this.action = '${path }/warehouse/storeApplication!liststoreApplication.action' ;} ;return navTabSearch(this);" action="${path }/warehouse/storeApplication!liststoreApplication.action"  method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>总公司名称：</label>
					<input type="text" name="filter_and_ownedCompany_LIKE_S" id="owedText" value="${filter_and_ownedCompany_LIKE_S}" hidden="hidden"/> 
					<select id="owedSel"></select>
				</td>	
				<td>
					<label>支公司名称：</label>
					<input type="text" name="filter_and_bannyCompanyName_LIKE_S" id="companyText" value="${filter_and_bannyCompanyName_LIKE_S}" hidden="hidden"/> 
			        <select id="companySel"></select>
				</td>		
				<td>
					<label>发货状态：</label>
					<input type="hidden" status="os" name="filter_and_status_LIKE_S" value="${filter_and_status_EQ_S}"/> 
					<select id="statusSel">
						<option value="">全部</option> 
						<option value="0">待发货</option> 
						<option value="1">部分发货</option>
						<option value="2">已发货</option>
         			</select>
				</td>		
			</tr>	
			
			<tr>
				<td>
					<label>申请人姓名：</label>
					<input type="text"  name="filter_and_receiveName_LIKE_S" value="${ filter_and_createUserName_LIKE_S }"/>
				</td>		
				<td>
					<label>申请人电话：</label>
					<input type="text"  name="filter_and_receiveTel_LIKE_S" value="${ filter_and_receiveTel_LIKE_S }"/>
					
				</td>
				<td>
					<label>申请单号：</label>
					<input type="text"  name="filter_and_batNo_LIKE_S" value="${ filter_and_batNo_LIKE_S }"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>申请开始日期：</label>				
				  	<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>	
				<td>
					<label>申请截止日期：</label>					
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td  style="padding-left:229px;">
					<div class="buttonActive"><div class="buttonContent"><button type="submit"">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${path}/warehouse/storeApplication!addstoreApplication.action" target="navTab"><span>申请</span></a></li>
			<!-- <li>
				<a class="add" href="${path}/warehouse/storeApplication!toSendMail.action" target="dialog" title="发送邮件" width="600" height="200" >
				<span>手动发送邮件</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170">
		<thead>
			<tr>
				<th width="40px"><nobr>序号</nobr></th>
				<th><nobr>申请单号</nobr></th>
				<th><nobr>总公司名称</nobr></th>
				<th><nobr>支公司名称</nobr></th>
                <th><nobr>项目编号</nobr></th>
                <th><nobr>项目名称</nobr></th>
                <th><nobr>项目负责人</nobr></th>
				<th><nobr>项目对接人</nobr></th>
				<th><nobr>对接人电话</nobr></th>
				<th><nobr>申请时间</nobr></th>
				<th><nobr>期望日期</nobr></th>
				<th><nobr>申请人</nobr></th>
				<th><nobr>状态</nobr></th>
				<!-- <th><nobr>操作</nobr></th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="storeApplication" varStatus="status">
			<tr >
				<td align="center">${ status.count }</td>
				<td align="center" nowrap="nowrap">
					<a href="${ path }/warehouse/storeApplication!browstoreApplication.action?id=${storeApplication.id}&batNo=${storeApplication.batNo}" target="navTab" rel="${storeApplication.id }">${ storeApplication.batNo }</a>	
				</td>	<!-- 申请单号 -->
				<td align="center" nowrap="nowrap">
					<hpin:id2nameDB id="${storeApplication.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" />
				</td>		<!-- 总公司名称 -->
				<td align="center" nowrap="nowrap">
					<hpin:id2nameDB id="${storeApplication.bannyCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/>
				</td>	<!-- 支公司名称 -->
                <td align="center" nowrap="nowrap">${ storeApplication.remark3}</td>			<!-- 项目编号 -->
                <td align="center" nowrap="nowrap">${ storeApplication.remark2}</td>			<!-- 项目名称 -->
                <td align="center" nowrap="nowrap">${ storeApplication.remark1}</td>			<!-- 项目负责人 -->
				<td align="center" nowrap="nowrap">${ storeApplication.receiveName}</td>		<!-- 项目对接人 -->
                <td align="center" nowrap="nowrap">${ storeApplication.receiveTel}</td>			<!-- 对接人电话 -->
				<td align="center" nowrap="nowrap">${ fn:substring(storeApplication.createTime , 0 , 10 )}</td> <!-- 申请日期 -->
                <td align="center" nowrap="nowrap">${ fn:substring(storeApplication.useDate , 0 , 10 )}</td>	<!-- 期望日期 -->
				<td align="center" nowrap="nowrap">${ storeApplication.createUserName }</td>					<!-- 申请人 -->
				<td align="center" nowrap="nowrap">
				    <c:choose>			  	  	  			
			  	  	    <c:when test="${ storeApplication.status==0 }">待发货</c:when>
			  	  	    <c:when test="${ storeApplication.status==1 }">部分发货</c:when>	 
			  	  	    <c:when test="${ storeApplication.status==2 }">已发货</c:when>	 
			     </c:choose>	
				</td>																							<!-- 状态 -->
			  
			   <%-- <td align="center" nowrap="nowrap">
			   		<c:choose>			  	  	  
			  	  	    <c:when test="${ storeApplication.status==0 || storeApplication.status==1}">
			  	  	    	<input type="button" value="发货" onclick="pullStoreApplication('${storeApplication.id }', '${storeApplication.batNo}')"/>
			  	  	    </c:when>
						<c:otherwise>
							<span style="color:#ff0000">已发货</span>
						</c:otherwise> 
			      	</c:choose>
					    <c:choose>			  	  	  
				  	  	      <c:when test="${ checkflag=='1' }">
				  	  	<a href="${ path }/warehouse/storeApplication!modifystoreApplication.action?id=${storeApplication.id }" target="navTab" rel="${storeApplication.id }">处理</a>	    	
				  	  	    	</c:when>			  	  	   	  	  	    	  	  	    
				     </c:choose>	
				</td> --%>							
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

</html>