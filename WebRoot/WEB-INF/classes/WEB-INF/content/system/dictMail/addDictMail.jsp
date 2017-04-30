<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
	List list =(List)request.getAttribute("list");
%>
<style>
<!--
	span.error {top: 9px;}
-->
</style>
<div class="pageContent">
	<form id="pagerFindForm" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="${path}/hpin/dictMail!saveOrUpdate.action" method="post" theme="simple"> 
    	<input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />					  
	    <input type="hidden" name="dictMailBase.id" id="dictMailBaseId" value="${dictMailBase.id }"/>
	    
	    <div class="pageFormContent" layoutH="40" style="overflow: hidden;">
	        <h1 class="press">用户邮箱管理</h1>
	        <div class="divider"></div>
	        <div class="tip"><span>录入</span></div>
			<div class="divider"></div>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">用户名：</dt>
				<dd>
					<input name="dictMailBase.userName" value="${dictMailBase.userName }" class="required textInput"  maxlength="100"/>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">邮箱：</dt>
				<dd>
					<input name="dictMailBase.mailAddress" value="${dictMailBase.mailAddress }" class="required email exitMailAddress textInput" />
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">是否启用：</dt>
				<dd>
					<select id="isStatus" name="dictMailBase.isStatus" class="required" rel="iselect" >
					<option value="0" ${dictMailBase.isStatus == '0' ? 'selected':'' }>启用</option>
					<option value="1" ${dictMailBase.isStatus == '1' ? 'selected':'' }>关闭</option>
				</select>
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt style="background-color:#FFFFFF;">字典项：</dt>
				<dd id="checkbox">	<table>
				<tr>														
				<c:forEach items="${list }" var="item" varStatus="status">	<td>	
					<input type="checkbox" name="dictMailSysdicts.sysDicttyoeDictId"
					<c:forEach items="${dictMailSysdicts }" var="initem">
					<c:if test="${initem.sysDicttyoeDictId == item.dictId}">
				 	checked="checked" 
					</c:if>
					</c:forEach>
					 value="${item.dictId }" />${item.dictName }	</td>
				<c:if test="${status.count% 4==0}" >									
							</tr><tr>
				</c:if>	
				</c:forEach></tr>	</table>		
				</dd>
			</dl>
				
		</div>
		
	    <div class="formBar" style="width:98%">
	    	<ul>
		        <li>
		            <div class="button">
			            <div class="buttonContent">
			              <button type="submit">保存</button>
			            </div>
		          </div>
		        </li>
		        <li>
		          <div class="button">
		            <div class="buttonContent">
		              <button type="reset">重置</button>
		            </div>
		          </div>
		        </li>
	        </ul>
	    </div>
	</form>
</div>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	 /* $.ajax({
		 type: 'post', //可选get
		 url: '${path}/hpin/sysDictType!treeRegionDispose.action', //这里是接收数据的程序
		 data:{"parentId":10120}, //传给后台的数据，多个参数用&连接
		 dataType: 'json', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
			 success: function(data) {
			 //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
			 //document.getElementById("eventsNo").val = msg;
			 //alert(data);
			 var obj=eval(data);
			 addcheckbox(obj,"50px","150px","checkbox");
		 },
			 error: function() {
				 
			 }
	 	}) */
	
});

/* function addcheckbox(obj, height, width, id) {  
    var i;  
    var insertDiv = document.getElementById(id);  
    insertDiv.innerHTML = "";  
    var chkinfo;  
    var chkDIV;  
    //var txtinfo;    
    //解析从服务器获得的数据,循环添加复选框    
    for (i = 0; i < obj.length; i++) {  
        //为每一个复选框创建一个DIV    
        chkDIV = document.createElement("div");  
        //每一个复选框用input创建，类型为checkBox    
        chkinfo = document.createElement("input");  
        chkinfo.name = "checkboxid";  
        chkinfo.id = obj[i].id;  
        chkinfo.type = "checkbox";  
        //将每一个chinesename为复选框赋值    
        chkinfo.value = obj[i].text;  
        //将复选框添加到Div中    
        chkDIV.appendChild(chkinfo);  
        //为Div设置样式    
        chkDIV.style.height = height;  
        chkDIV.style.width = width;  
        chkDIV.style.float = "left";  
        chkDIV.align = "left";  
        chkDIV.appendChild(document.createTextNode(obj[i].text));  
        //将创建的div添加到前台预留的DIV下    
        insertDiv.appendChild(chkDIV);  
    }  
}   */
function mail(){
	var accemail=document.getElementById("accemail");
	if(!accemail.value==""){
		 var reg =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var bool=reg.test(accemail.value);
		if(bool==false){
			alert("邮箱格式错误，请重新输入！");
			accemail.focus();
			return;
		}
	}
}

$.validator.addMethod("exitMailAddress", function(value, element) {
	
	var id = $("#dictMailBaseId", navTab.getCurrentPanel()).val();
	var exits = true;
	//后台请求查看是否已经存在了
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"id":id, "mailAddress": value},
		url: "${path }/hpin/dictMail!exitsObject.action",
		success: function(data){
			var data= eval("("+data+")");
			exits = !data.result; 
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	return this.optional(element) || exits;
}, "输入的邮箱已存在!");



</script>