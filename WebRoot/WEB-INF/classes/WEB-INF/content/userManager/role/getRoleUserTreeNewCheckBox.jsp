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
			  oId   += ',' + eleId[0];
			  oName += ',' + eleId[1];
		  }
	  }
	});
	if (oName != '') {
		oName = oName.substring(1);
		$("input[username='username']",navTab.getCurrentPanel()).val(oName);
	}
	if (oId != '') {
		oId = oId.substring(1);
		$("input[accountname='accountname']",navTab.getCurrentPanel()).val(oId);
	}
	if (oId == '') {
		alert('请选择打分人员！');
		return;
	}else {
		$.pdialog.closeCurrent();
	}
}
</script>
  <div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	  <ul id="roleTrees" class="tree treeFolder treeCheck expand">
	  <li><a><span>用户选择</span></a>
		  	<ul>
			  	<c:forEach items="${userList }" var="user" varStatus="status">
			  		<li><a href="javascript:" tname="userName" tvalue="${user.accountName},${user.userName}" onclick="$.bringBack({accountName:'${user.accountName}', userName:'${user.userName}'})"><span>${user.userName}</span></a></li>
			  	</c:forEach>
		  	</ul>
	  	</li>
	  </ul>
  </div> 
  <div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:selUsers();">确认</button></div></div></li>
	</ul>
  </div>
</div>