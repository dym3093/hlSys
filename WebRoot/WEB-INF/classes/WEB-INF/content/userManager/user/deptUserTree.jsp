<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
 <%@ include file="/common/taglibs.jsp"%>
  <link href="${path}/styles/style.css" rel="stylesheet" type="text/css" />
  <script src="${path}/scripts/CheckForm.js"></script>
  <script type="text/javascript" language="javascript" src="${path}/scripts/common.js"></script>
		
  <script type="text/javascript" charset="utf-8" src="${path}/scripts/base/hpin.js"></script>
  <link rel="stylesheet" type="text/css" href="${path}/styles/default/theme.css" />
  <!-- EXT LIBS verson 1.1 -->
  <script type="text/javascript" src="${path}/scripts/ext/ext/ext-base.js"></script>
  <script type="text/javascript" src="${path}/scripts/ext/ext/ext-all.js"></script>
  <script type="text/javascript" src="${path}/scripts/adapter/ext-ext.js"></script>
  <script type="text/javascript" src="${path}/scripts/ext/ext/source/locale/ext-lang-zh_CN.js"></script>
  <link rel="stylesheet" type="text/css" href="${path}/scripts/ext/ext/resources/css/ext-all.css" />
  <link rel="stylesheet" type="text/css" href="${path}/styles/default/ext-adpter.css" />
  
<script type="text/javascript">
Ext.onReady(function(){
	
	xbox_tree = new xbox({
		//showChkFldId:"users",
		treeRootText:"部门人员",
		treeRootId:"0",
		//saveChkFldId:"userIds",
		treeDataUrl:"../um/dept!deptUserTree.action",
		//treeRootId:0,
		treeChkMode:'',
		treeChkType:'user',
		//btnId:"users",
		callback: callback
	});
}); // end of Ext.onReady
function callback(jsonData,content){
	xbox_tree.reset();
	//var userIds = document.getElementById("userIds").value;
	for(var i=0;i<jsonData.length;i++){
		var obj = jsonData[i];
		if(isExist(obj.id)){
			continue;
		}
		//if(userIds.indexOf(obj.id+",")!=0 && userIds.indexOf(","+obj.id)!=(userIds.length-obj.id.length-1) && userIds.indexOf(","+obj.id+",")<0)
			//document.getElementById("userDiv").innerHTML += '<div>'+obj.name+'<input type="hidden" value="'+obj.id+'"><a href="javascript:void(0)" onclick="deleteUser(this)">删除</a></div>';
	}
	dealUserIds();
	return false;
}
function deleteUser(obj){
	obj.parentNode.parentNode.removeChild(obj.parentNode);
	dealUserIds();
}

function isExist(ele){
	var inputs = document.getElementById("userDiv").getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].value==ele){
			return true;
		}
	}
}
function dealUserIds(){
	var inputs = document.getElementById("userDiv").getElementsByTagName("input");
	var ids = "";
	for(var i=0;i<inputs.length;i++){
		ids += inputs[i].value + ",";
	}
	if(ids.length>0){
		ids = ids.substring(0,ids.length-1);
	}
	//document.getElementById("userIds").value = ids;
}
</script>
<style type="text/css">
div{left:auto;top:auto;}
#userDiv{
	width:500px;
}
#userDiv div{
	padding-left:10px;
	width:100px;
	line-height:22px;
	float:left;
}
#userDiv div a{
	cursor:pointer;
	float:right;
}
</style>
	</head>
	<body>
	  <table>
		<tr>
			<td>
				<div id="userDiv"></div>
			</td>
		</tr>
	  </table>
	</body>
</html>
