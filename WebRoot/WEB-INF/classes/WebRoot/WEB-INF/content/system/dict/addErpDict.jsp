<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>

<script type="text/javascript">

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
	return navTabSearch(this);	//刷新页面
}

function isEmptyString(content){
	return keyName==""||keyName==null||keyName==undefined;
}

//保存场次套餐价格
function save(){
	
	var arr = [];
	
	var keyName = $("#keyName").val();
	var valueName = $("#valueName").val();
	var typeFilter = $("#typeFilter").val();
	var remark = $("#remark").val().replace(/(^\s*)|(\s*$)/g, ""); 
	
	if(isEmptyString(keyName)){
		alertMsg.error("请填写带星号字段！");
	}
	if(isEmptyString(valueName)){
		alertMsg.error("请填写带星号字段！");
	}
	if(isEmptyString(typeFilter)){
		alertMsg.error("请填写带星号字段！");
	}

	arr.push({
		'keyName':keyName,
		'valueName':valueName,
		'typeFilter':typeFilter,
		'remark':remark.trim()
	});
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/hpin/erpDict!save.action",
		data: {"data": JSON.stringify(arr)},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("保存成功！");
				$.pdialog.closeCurrent();
				return navTabSearch(this);	//刷新页面
			}else{
				alertMsg.error("保存失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}


//验证
function checkValid(me, validName){
	var flag = false;
	switch(validName){
		case 'number':
			var price = me.value;
			if(price!=null&&price!=""&&price!="undefined"&&price!=undefined){
				var regNum = /[0-9]/;
				if(regNum.test(price)){
					flag = true;
				}else{
					alert("请输入数字！");
				}
			}
		break;
	}
	return flag;
}

</script>

<div class="pageHeader" style="background:white;" >
		<form class="pageForm required-validate" id="addForm" >
			<input type="hidden" name="id" value="${entity.id}" /> 
			<%-- <input type="hidden" name="createTime" value="${entity.createTime}" />
			<input type="hidden" name="createUserId" value="${entity.createUserId}" />
			<input type="hidden" name="createUser" value="${entity.createUser}" />
			<input type="hidden" name="updateTime" value="${entity.updateTime}" />
			<input type="hidden" name="updateUserId" value="${entity.updateUserId}" />
			<input type="hidden" name="updateUser" value="${entity.updateUser}" />
			<input type="hidden" name="status" value="${entity.status}" />  --%>
		<table class="pageFormContent">
			<tr>
				<td>
                	<label for="keyName">键：</label>
                	<input id="keyName" type="text" name="keyName" value="${entity.keyName}" class="required" />  
				</td>
				<td>
					<label for="valueName">值：</label> 
                    <input  id="valueName" type="text" name="valueName" value="${entity.valueName}" class="required"/>
				</td>
	  		</tr>
	  		<tr>
	  			<td colspan="2">
					<label for="typeFilter">类型：</label> 
					<input  id="typeFilter" type="text" name="typeFilter" value="${entity.typeFilter}" class="required" />	         
					<span style="margin:5px;color:#ff0000">注：建议填   类名_属性   的样式</span>           
				</td>
			</tr>
	  		<tr>
	  			<td colspan="2">
		  			<p class="nowrap"><label style="height:80px;" for="remark">备注：</label>
						<textarea id="remark" style="width:565px;" name="remark" >
							${entity.remark}
						</textarea>
					</p>
				</td>		
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;"><span style="color:#ff0000">注:【备注】填写请勿超过300字</span></td>
			</tr>
            <tr>
            	<td></td>
            	<td >
            		<div align="center" style="text-align:center;">
				      	<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save()">保存</button></div></div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="isClose()">取消</button></div></div>
					</div>
				</td>
            </tr>
		</table>		
	</form>
</div>