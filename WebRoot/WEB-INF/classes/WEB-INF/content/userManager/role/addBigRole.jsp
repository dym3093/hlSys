<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
<%@ include file="/common/taglibs.jsp"%>
  <link href="${path}/styles/style.css" rel="stylesheet" type="text/css" />
  <%-- 
  <link href="${path}/css/default/default.css" rel="stylesheet" type="text/css" />
  --%>
  <script src="${path}/scripts/CheckForm.js"></script>
  <script type="text/javascript" language="javascript" src="${path}/scripts/common.js"></script>
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
  
<script type="text/javascript">
Ext.onReady(function(){
	
	xbox_tree = new xbox({
		//showChkFldId:"users",
		treeRootText:"部门人员",
		treeRootId:"0",
		//saveChkFldId:"userIds",
		treeDataUrl:"../assign/assignPriv!getUser.action",
		//treeRootId:-100,
		treeChkMode:'',treeChkType:'user',
		btnId:"users",
		callback: callback
	});
	
	module_tree = new xbox({
		treeRootText : "系统功能树" ,
		treeRootId : "0" ,
		treeDataUrl : "../um/role!getRoleTree.action" ,
		treeChkMode : "" ,
		treeChkType : "user" ,
		btnId : "modules" ,
		callback : callback4Module
	}) ;
}); 

function callback4Module(jsonData , content){
	module_tree.reset() ;
	var moduleIds = document.getElementById("moduleIds").value ;
	for(var i = 0 ; i < jsonData.length ; i ++){
		var obj = jsonData[i] ;
		if(isExist(obj.id , 'moduleIds')){
			continue;
		}
		if(moduleIds.indexOf(obj.id+",")!=0 && moduleIds.indexOf(","+obj.id)!=(moduleIds.length-obj.id.length-1) && moduleIds.indexOf(","+obj.id+",")<0)
			document.getElementById("moduleDiv").innerHTML += '<div>&nbsp;&nbsp;'+obj.name+'<input type="hidden" value="'+obj.id+'"><a href="javascript:void(0)" onclick="deleteUser(this , \'moduleDiv\' , \'moduleIds\')">删除</a></div>';
	}
	dealIds("moduleDiv" , "moduleIds");
	return false;
}

function callback(jsonData,content){
	xbox_tree.reset();
	var userIds = document.getElementById("userIds").value;
	for(var i=0;i<jsonData.length;i++){
		var obj = jsonData[i];
		if(isExist(obj.id , 'userIds')){
			continue;
		}
		if(userIds.indexOf(obj.id+",")!=0 && userIds.indexOf(","+obj.id)!=(userIds.length-obj.id.length-1) && userIds.indexOf(","+obj.id+",")<0)
			document.getElementById("userDiv").innerHTML += '<div>&nbsp;&nbsp;'+obj.name+'<input type="hidden" value="'+obj.id+'"><a href="javascript:void(0)" onclick="deleteUser(this , \'userDiv\' , \'userIds\')">删除</a></div>';
	}
	dealIds("userDiv" , "userIds");
	return false;
}
function deleteUser(obj , flag , hiddenInput){
	obj.parentNode.parentNode.removeChild(obj.parentNode);
	dealIds(flag , hiddenInput);
}
function isExist(ele , flag){
	var inputs = document.getElementById(flag).getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].value==ele){
			return true;
			break ;
		}
	}
}
function dealIds(flag , hiddenInput){
	var inputs = document.getElementById(flag).getElementsByTagName("input");
	var ids = "";
	for(var i=0;i<inputs.length;i++){
		ids += inputs[i].value + ",";
	}
	if(ids.length>0){
		ids = ids.substring(0,ids.length-1);
	}
	document.getElementById(hiddenInput).value = ids;
}

function _submitForm(_form){
	var userIds = document.getElementById("userIds").value ;
	var moduleIds = document.getElementById("moduleIds").value ;
	if(userIds == '' || userIds == null){
		alert("请选择人员信息！") ;
		return false ;
	}
	if(moduleIds == '' || moduleIds == null){
		alert("请选择系统功能信息！") ;
		return false ;
	}
	submitForm(_form) ;
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

#moduleDiv{
	width:500px;
}
#moduleDiv div{
	padding-left:10px;
	width:100px;
	line-height:22px;
	float:left;
}
#moduleDiv div a{
	cursor:pointer;
	float:right;
}
</style>
	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-添加角色" />
				</td>
				<td class="back">
					<web:back url="bigRole!listBigRole.action" />
				</td>
			</tr>
		</table>

		<form action="bigRole!saveBigRole.action" method="post" id="_form"
			class="form">
			<table width="100%">
				<tr>
					<td class="label" width="30%" style="text-align: right;">
						<label>
							<font class="star">*</font>角色名称：
						</label>
					</td>
					<td width="70%">
						<input type="text" name="bigRole.roleName" maxlength="50"
							lang="check:!NULL---info:角色名称不能为空！">
					</td>
				</tr>

				<tr>
					<td class="label" style="text-align: right;">
						<label>
							<font class="star">*</font>编码：
						</label>
					</td>
					<td>
						<input type="text" name="bigRole.roleCode" maxlength="50"
							lang="check:!NULL---info:编码名称不能为空！">
					</td>
				</tr>

				<tr>
					<td class="label" style="text-align: right;">
						<label>
							说明：
						</label>
					</td>
					<td>
						<textarea rows="10" cols="55" name="bigRole.desc"></textarea>
					</td>
				</tr>
				<tr>
					<td class="label" style="text-align: right;">
						<label>
							功能点列表：
						</label>
						<input type="button" id="modules" value="添加功能模块">
					</td>
					<td>
						<div id="moduleDiv"></div>
					</td>
				</tr>
				<tr>
					<td class="label" style="text-align: right;">
						<label>
							人员列表：
						</label>
						<input type="button" id="users" value="添加人员">
					</td>
					<td>
						<div id="userDiv"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="button" value="保 存"
							onclick="_submitForm(this.form)" />
						<input type="reset" class="button" value="重置" />
					</td>
				</tr>
			</table>
			<input type="hidden" id="userIds" name="userIds" value="${userIds }">
			<input type = "hidden" id = "moduleIds" name = "moduleIds" value="${moduleIds }" />
		</form>
	</body>
</html>
