<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/hpin/dictMail!listDictMail.action'){this.action = '${path }/hpin/dictMail!listDictMail.action' ;} ;return navTabSearch(this);" 
	action="${ path }/hpin/dictMail!listDictMail.action" method="post" rel = "pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
		<input type="hidden" name="navTabId" value="${dictMailBase.id}"/>
			<tr>
				<td><label>用户名：</label></td>
				<td><input type="text" name="params.userName"  value="${params.userName}"/></td>
				<td><label>是否启用：</label></td>
				<td><select id="statusYm" name="params.isStatus" class="required" style="height:20px;margin:5px;width:200px;">
						<option value="">--全部--</option> 
							<option value="0" ${params.isStatus =='0'? "selected" : "" }>启用</option> 
							
							<option value="1" ${params.isStatus =='1'? "selected" : "" }>关闭</option>
							
					</select>
				</td>
				<td style="padding-left:40px;">
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
	<%-- <web:security tag="customerRelationShipAdmin"> --%>
		<ul class="toolBar">
			<li><a class="add" href="${path }/hpin/dictMail!addDictMail.action" target="navTab"><span>添加</span></a></li> 
			<li><a name="editDictMail" class="edit" href="javascript:void(0);" ><span>修改</span></a></li>
			<li class=""><a class="delete" href="javascript:void(0);" onclick="delDictMail();"><span>删除</span></a></li>
			<%-- <web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="findByPage" 
						actionAllUrl="org.hpin.base.dict.web.DictMailAction" 
						informationTableId="exportExcelTable"
						fileName="DictMailBase">
			</web:exportExcelTag>  --%>
		</ul>
	 <%--</web:security> --%>
		<jsp:include page="/common/pagination.jsp" />	
	</div>
	<table class="list" width="100%" layoutH="160" id="exportExcelTable">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "userName" columnChName = "用户名" >用户名</th>
				<th  export = "true" columnEnName = "mailAddress" columnChName = "邮箱地址" >邮箱地址</th>
				<th  export = "true" columnEnName = "dictConcat" columnChName = "使用字典"> 使用字典</th>
				<th  export = "true" columnEnName = "isStatus"  columnChName = "启用状态">启用状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.results}" var="dictMailBase" varStatus="status">
			<tr target="sid_user" rel="${dictMailBase.id}">
				<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${dictMailBase.id}">
							</c:if>
							${status.count }
						</td>
						<td align="center">	${dictMailBase.userName}</td>
						<td align="center">	${dictMailBase.mailAddress}</td>
						<td align="center">	${dictMailBase.dictConcat}</td>
						<td align="center">
							<c:choose>
								<c:when test="${dictMailBase.isStatus==0}">启用</c:when>
								<c:when test="${dictMailBase.isStatus==1}">关闭</c:when>
							</c:choose>
						</td>
		 	</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">	
$(function() {
	$("a[name='editDictMail']", navTab.getCurrentPanel()).on("click", function() {
		var count = 0;
		var ids = "";
		
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
			
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要修改的数据！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条信息进行修改！");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("dictMailUpdate", "${path}/hpin/dictMail!addDictMail.action?id="+ids+"&navTabId="+navTabId, { title:"邮箱字典修改", fresh:false, data:{} });
	});
});

/**删除*/
function delDictMail() {
	var count = 0;
	var ids = "";
	
	$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
		if(ids.length > 0) {
			ids += ", " +val.value  ;
		} else {
			ids += val.value
		}
		count ++;
		
	});
	
	if(count == 0) {
		alertMsg.warn("请选择你要删除的信息！");
		return;
	}
	
	/*验证该数据是否被引用,如果被引用则不能删除;*/
	alertMsg.confirm("确定要删除选中的数据吗?", {
		okCall: function(){
			$.ajax({	//初始化页面时的省份
				type: "post",
				cache :false,
				data:{"ids":ids},
				url: "${path }/hpin/dictMail!deleteDictMail.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.result){
						alertMsg.info(data.message);
					}else {
						alertMsg.error(data.message);
					}
					
					navTabPageBreak();
				},
				error :function(){
					alertMsg.alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	});
	
}
</script>
