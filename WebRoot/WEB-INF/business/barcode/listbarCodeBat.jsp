<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			navTab.openTab("listbarcodebat", "../business/barCodeBat!modifybarCodeBat.action?id="+ids+"&navTabId="+navTabId, { title:"跟踪", fresh:false, data:{} });		
     }else {
			alert('只有完成制码的信息才能变更！');
			return ;
	    }
   }
}
</script>
<html>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/business/barCodeBat!listbarCodeBat.action'){this.action = '${path }/business/barCodeBat!listbarCodeBat.action' ;} ;return navTabSearch(this);" action="${path }/business/barCodeBat!listbarCodeBat.action"  method="post" rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>制码批次号：</label>
					<input type="text" id="batNO" name="filter_and_batNO_LIKE_S" value="${ filter_and_batNO_LIKE_S }"/>
				</td>			
				<td>
					<label>开始日期：</label>
					<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }">					
				  <input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})" class="required" readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>截止日期：</label>					
				<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				
				<td>
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
			<li><a class="add" href="${path}/business/barCodeBat!addbarCodeBat.action" target="navTab"><span>制码</span></a></li>
			<li><a class="edit" onclick="changeBar()" style="cursor:pointer;"><span>跟踪</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170">
		<thead>
			<tr>
				<th width="40px"><nobr>序号</nobr></th>
				<th><nobr>批次号</nobr></th>
				<th><nobr>实际生成数量</nobr></th>
				<th><nobr>制码数量</nobr></th>
				
				<th><nobr>状态</nobr></th>
				<th><nobr>创建时间</nobr></th>
				<th><nobr>创建人</nobr></th>
			<!--	<th><nobr>公共内容</nobr></th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="barCodeBat" varStatus="status">
			<tr target="sid_user" rel="${barCodeBat.id }">
				<td align="center"><input type="checkbox" name="ids" value="${barCodeBat.id }" status="${ barCodeBat.status }">${ status.count }</td>
				
				<td>
					<a href="${ path }/business/barCodeBat!browbarCodeBat.action?id=${barCodeBat.id }" target="navTab" rel="${barCodeBat.id }">${barCodeBat.batNO }</a>						 	
			  </td>
			  <td align="center" nowrap="nowrap">
			  	<c:choose>
						  <c:when test="${ barCodeBat.status>=2 }">
						    	  <a href="${ path }/business/barCodeInfo!listbarCodeInfo.action?filter_and_batNO_EQ_S=${barCodeBat.batNO }" target="navTab" rel="${barCodeBat.batNO }">${barCodeBat.batActualCount }</a>
						 	</c:when>	  
						  <c:otherwise>
				      	     ${barCodeBat.batActualCount }
				     </c:otherwise>	  	    
				 </c:choose>			
			  	</td>
				<td align="center" nowrap="nowrap">${barCodeBat.batCount }</td>
				
				<td align="center" nowrap="nowrap">
					    <c:choose>			  	  	  
				  	  	    <c:when test="${ barCodeBat.status==0 }">待制码</c:when>
				  	  	    <c:when test="${ barCodeBat.status==1 }">制码中</c:when>
				  	  	    <c:when test="${ barCodeBat.status==2 }">制码完成</c:when>
				  	  	    <c:when test="${ barCodeBat.status==3 }">制码已封装</c:when>	  	  	    
				     </c:choose>	
					</td>
				<td align="center" nowrap="nowrap">${ fn:substring(barCodeBat.createTime , 0 , 19 )}</td> 
				<td align="center" nowrap="nowrap">${ barCodeBat.createUserId }</td>
			<!--	<td align="center" nowrap="nowrap">${ barCodeBat.remark }</td> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

</html>