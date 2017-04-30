<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/taglibs.jsp"%>
		
<script type="text/javascript" charset="utf-8" src="${path}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${path}/scripts/base/hpin.js"></script>
  <script type="text/javascript">hpin.appPath = "${path}";</script>
  <link rel="stylesheet" type="text/css" href="${path}/styles/default/theme.css" />

  <!-- EXT LIBS verson 1.1 -->
  <script type="text/javascript" src="${path}/scripts/ext/ext/ext-base.js"></script>
  <script type="text/javascript" src="${path}/scripts/ext/ext/ext-all.js"></script>
  <script type="text/javascript" src="${path}/scripts/adapter/ext-ext.js"></script>
  <script type="text/javascript" src="${path}/scripts/ext/ext/source/locale/ext-lang-zh_CN.js"></script>
  <link rel="stylesheet" type="text/css" href="${path}/scripts/ext/ext/resources/css/ext-all.css" />
  
  <link rel="stylesheet" type="text/css" href="${path}/styles/default/ext-adpter.css" />
  <!-- EXT LIBS END -->

	</head>
<body>
<script type="text/javascript">
Ext.onReady(function(){
	
	xbox_tree = new xbox({
		showChkFldId:"deptname",
		treeRootText:"部门人员",
		treeRootId:"1",
		saveChkFldId:"deptid",
		treeDataUrl:"../assign/assignPriv!getUser.action",
		treeRootId:-100,
		treeChkMode:'',treeChkType:'user',
		btnId:"depttree"
	});
}); // end of Ext.onReady

</script>

<input type="button" id="depttree">
<input type="text" id="deptname">
<input type="text" id="deptid">
</body>
</html>
