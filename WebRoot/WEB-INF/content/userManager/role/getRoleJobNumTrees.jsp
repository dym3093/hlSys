<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
  function selUsers() {
	var oId=''; var oName='';
	var divs = $("#roleTrees div:[class$='ckbox checked'] input[type='checkbox']");
	divs.each(function(i,n){
	  if(n.value != '') {
		  var val = n.value;
		  var eleId = val.split(',');
		  if (eleId[0] != '0') {
			  oId   += ',' + "'" + eleId[0] + "'";
			  oName += ',' + eleId[1];
		  }
	  }
	});
	if (oName != '') {
		oName = oName.substring(1);
		$("#xietiaoyuan",navTab.getCurrentPanel()).val(oName);
	}
	if (oId != '') {
		oId = oId.substring(1);
		$("#jobNum",navTab.getCurrentPanel()).val(oId);
	}
	if (oId == '') {
		alert('请选择协调员！');
		return;
	}else {
		$.pdialog.closeCurrent();
	}
}
</script>
  <div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	  <ul id="roleTrees" class="tree treeFolder treeCheck expand">
	  <c:forEach items="${role.roleList}" var="role1" varStatus="status">
		<c:if test="${role1.parentId ne '0'}">
			<li><a href="javascript:" tname="roleName" tvalue="0,${role1.name}")"><span>${role1.name}</span></a>
		      <ul>
		        <c:forEach items="${role.userRoleList}" var="userRole1" varStatus="status">
		          <c:if test="${role1.id==userRole1.roleId}">
		          <li><a href="javascript:" tname="userName" tvalue="${userRole1.jobNumber},${userRole1.userName}"><span>${userRole1.userName}</span></a></li>
		          </c:if>
		        </c:forEach>
		      </ul>
			</li>
		  </c:if>
	    </c:forEach>
	  </ul>
  </div> 
  <div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:selUsers();">确认</button></div></div></li>
	</ul>
  </div>
</div>