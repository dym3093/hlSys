<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		
		<script type="text/javascript" src="${path}/scripts/two-trees.js"></script>
		
		<style type="text/css">
body{
	width:920px;
}
#tree,#tree2,#tree3 {
	float: left;
	margin: 5px;
	border: 1px solid #c3daf9;
	width: 250px;
	height: 398px;
}

.folder .x-tree-node-icon {
	background: transparent
		url(../scripts/ext/resources/images/default/tree/folder.gif);
}

.x-tree-node-expanded .x-tree-node-icon {
	background: transparent
		url(../scripts/ext/resources/images/default/tree/folder-open.gif);
}
.right_bg{
	float:left;
	width:128px;
	height:400px;
	background: transparent no-repeat left center url(../images/right.png);
}

</style>
<script type="text/javascript">
var ownerId = "${ownerId}";
var assignType = "${assignType}";
</script>
	</head>
	<body>
		<h1>
			<s:set name='assignTypeNames' value='#{1:"部门",2:"用户",3:"角色"}'/>
			为${assignTypeNames[assignType] }(${ownerName}(${ownerId }))分配权限
		</h1>
		<div id="tree"></div>
		<div class="right_bg"></div>
		<div id="tree2"></div>
		<c:if test="${assignType==2}">
			<div id="tree3"></div>
		</c:if>
	</body>
</html>