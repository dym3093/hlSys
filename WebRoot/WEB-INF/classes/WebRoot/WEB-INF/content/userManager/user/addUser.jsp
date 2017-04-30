<%@ page language="java" contentType="text/html; charset=UTF-8"%>		
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<script type="text/javascript" language="javascript" src="${path}/scripts/jquery.js"></script>
		<script type="text/javascript" src="${path}/scripts/lhgdialog/lhgdialog.min.js"></script>
		<script type="text/javascript">
			function xgmn() {
				$.pdialog.open("${path}/security/security!showUpdatePassword.action", "", "修改密码",  {width:400,height:280,mask:true,mixable:true,minable:true,resizable:true,close:"function"});
			}
			$(function(){
				
			})
			
			function changesourceType(value) {
				if (value == '1') {
					$(".jobNumber").show();
					$(".ctiPassword").show();
					$(".customServiceRole").show();
					$(".customServiceType").show();
				}
				else {
					$(".jobNumber").hide();
					
					$(".ctiPassword").hide();
					$(".customServiceRole").hide();
					$(".customServiceType").hide();
					
					$(".jobNumber").val("");
					$(".ctiPassword").val("");
					$(".customServiceRole").val("");
					$(".customServiceType").val("");
				}
				}
			</script>
			<script type="text/javascript">
$(function(){
	$("#navMenu ul li:first").addClass("selected");
	$.post($("#navMenu ul li:first>a").attr("href"), {}, function(html){
		$("#sidebar").append(html);
	},"html");
	$("#navMenu ul li:first").trigger("click");
	$("#navMenu ul li:first>a").trigger("click");
	DWZ.init("${path}/dwz/dwz.frag.xml", {
		loginUrl:"${path}/dwz/login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
//		statusCode:{ok:200, error:300, timeout:301}, //【可选】
//		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
//		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${path}/dwz/themes"});
			//setTimeout(function() {$("#sidebar .toggleCollapse div").trigger("click");}, 10);
		}
	});
	
	
	//create by henry.xu 20161221; 动态获取省市数据;
	
	
});
function trim(str){ //删除左右两端的空格  
    return str.replace(/(^\s*)|(\s*$)/g, "");  
} 
function doit(){
	var v = $('#isCustomServicea option:selected').text();
    $('#ctiPassword').val(v);
}
function test(){
	var a=document.getElementsByName("name");
	var value="";
	
	$("input[id='roleNames']").val(value);
	for(i=0;i<a.length;i++){
		if(document.getElementsByName("name")[i].checked==true){
			if(value==""){
				 value= value+trim(a[i].nextSibling.nodeValue);
				 //value=value+document.getElementsByName("name")[i].value;
				 // value=value+document.getElementsByName("name")[i].innerHTML;
			}
			else{
				value = value+","+trim(a[i].nextSibling.nodeValue);
				  //value=value+","+document.getElementsByName("name")[i].value;
				  // value=value+","+document.getElementsByName("name")[i].innerHTML;
				}
		}
	}
	$("input[id='roleNames']").val(value);
}
function xgmm() {
	$.pdialog.open("${path}/security/security!showUpdatePassword.action", "", "修改密码",  {width:400,height:280,mask:true,mixable:true,minable:true,resizable:true,close:"function"});
}

function proviceChange(obj) {
	var parentId = $(obj).val();
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"parentId":parentId},
		url: "${path}/system/region!treeRegionDispose.action",
		success: function(data){
			var data= eval("("+data+")");
			var opt = "<option value=''>请选择</option>";
			
			for(var i=0; i<data.length; i++) {
				opt += "<option value='"+data[i].id+"'>"+data[i].text+"</option>";
			}
			$("#cityId").html("");
			$("#cityId").html(opt);
			
		},
		error :function(){
			alertMsg.warn("服务发生异常，请稍后再试！");
			return;
		}
	});
	
}
function dialogCompany() {
	var api = frameElement.api;
	var dialog = $.dialog({
	    id: 'testID',
	    title: '支公司选择',
	    width: 400,
	    height: 300,
	    content: 'url:${path }/resource/customerRelationShip!findCompanyByOwnerCompanyId.action?params.wonedCompanyId=${dept.id}',
	    button: [
	        {
	            name: '确定',
	            callback: function () {
	            	$(this.content.document).find("input[name='choiceRadio']").each(function() {
	            		if($(this).attr("checked") == 'checked') {
	            			$("#companyId").val($(this).val());
	            			$("#companyName").val($(this).attr("complanyName"));
	            		}
	            	}); 
	            },
	            focus: true
	        },
	        {
	            name: '关闭'
	        }
	    ]
	});
}
</script>
	
	</head>

	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="用户管理-添加用户" />
				</td>
				<td class="back">
					<web:back url="user!listUser.action?deptId=${dept.id }" />
				</td>
			</tr>
		</table>


		<div class="form-content">
			<form id="_form" class="form" cellspacing="1"
				action="user!saveUser.action" method="post">
				<web:transfer />
				<input type="hidden" name="user.deptId" value="${dept.id}">
				<input type="hidden" name="user.deptName" value="${dept.deptName}">
				<!-- 去掉是否启用行,加入隐藏域   modified by henry.xu 20160809 -->
				<input type="hidden" name="user.isEnable" value="1"/>
				<table>
					<tr>
						<td class="label"><label>总公司：</label></td>
						<td>
							<input type="text" value="${dept.deptName}" disabled="disabled">
						</td>
					</tr>
					<!-- <tr>
						<td class="label">
							<label>是否是座席员：</label>
								
						</td>
						<td>
							<select name="isCustomService" id="isCustomService" tabindex="7" onchange='changesourceType(this.value);'>
											
											<option value="0">否</option>
											<option value="1">是</option>
											<option value=""></option>
							</select>
						</td>
					</tr> -->
					<tr>
						<td class="label">
							<label>
								<font class="star">*</font>登录名：
							</label>
						</td>
						<td>
							<input type="text" name="user.accountName" value="${user.accountName}" maxlength="20"
								class="name:用户登录名---check:!NULL---info:用户登录名不能为空！">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font class="star">*</font>用户姓名：
							</label>
						</td>
						<td>
							<input type="text" name="user.userName" value="${user.userName}" maxlength="50"
								class="name:用户姓名---check:!NULL---info:用户姓名不能为空！">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								<font class="star">*</font>用户口令：
							</label>
						</td>
						<td>
							<input type="text" name="user.password" value="111111" maxlength="10" 
								class="name:用户口令---check:!NULL&长度大于5---info:用户口令不能为空！||用户口令长度6～16位">
							(初始密码111111)

						</td>
					</tr>
					<!-- 注释 去掉是否启用功能; 隐藏域默认设置为1; modified by henry.xu 20160809-->
					<%-- <tr>
						<td class="label">
							<label>
								是否启用：
							</label>
						</td>
						<td>
							<web:yesNoSelect name="user.isEnable" value="${user.isEnable}"
								style="width:173px;" />
						</td>
					</tr> --%>
					<tr>
						<td class="label">
							<label>数据权限：</label>
						</td>
						<td>
							<select name="user.dataPriv" rel="iselect" class="required" style="width:153px;">
								<option value="priv_01">部门数据</option>
								<option value="priv_02">个人数据</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								电子邮箱：
							</label>
						</td>
						<td>
							<input type="text" name="user.email" value="${user.email}" maxlength="200">
						</td>
					</tr>
					
					<tr>
						<td class="label">
							<label>
								邮箱密码：
							</label>
						</td>
						<td>
							<input type="password" name="user.ctiPassword" value="${user.ctiPassword}" maxlength="30" style="width:153px;">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>
								联系方式：
							</label>
						</td>
						<td>
							<input type="text" name="user.mobile" value="${user.mobile}" maxlength="30">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label><!-- <font class="star">*</font> -->公司名称：</label>
						</td>
						<td>
							<%-- <select name="user.jobNumber" id="isCustomServicea" tabindex="7" onchange='changesourceType(this.value);' style="width: 153px;">
								<c:forEach items="${list}" var="customer" varStatus="status">
							        <option id="branch" value="${customer.id }">${customer.branchCommany}</option>
							    </c:forEach>
							</select> --%>
							
							<input id="companyName" type="text" name="user.extension" readonly="readonly" value=""/>
							<input id="companyId" type="hidden" name="user.jobNumber" value=""/>
							<input type="button" value="查找" onclick="dialogCompany();"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>远盟对接人：</label>
						</td>
						<td>
							<input type="text" name="user.ymSaleMan" id="ymSaleMan" />
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>项目编码：</label>
						</td>
						<td>
							<input type="text" name="user.projectCode" id="projectCode" />
							<br />
							<span style="color: #7F7F7F;display: block;line-height: 18px;float: left; font-size: 11px">
								【项目编码】：给保险公司创建用户必须填写项目编码，填写规则为：<br />
								1.1统计报表-保险公司报告下的权限用户项目编码必须有单引号，例如：'SK09890'<br />
								1.2库存管理-基因物品申请-保险公司物料申请权限必须填写，直接填写项目编码即可
							</span>
						</td>
					</tr>
					<tr>
	        			<td><label>省份：</label></td>
	        			<td>
	        				<select id="provinceId" class="required" name="user.provice" rel="iselect" style="width: 153px; " onchange="proviceChange(this);">
								<option value="">请选择</option>
								<c:forEach items="${provices }" var="item">
									<option value="${item.id }">${item.regionName }</option>
								</c:forEach>
							</select>
	        			</td>
	        			
	        		</tr>
	        		<tr>
	        			<td><label>城市：</label></td>
	        			<td>
	        				<select id="cityId" name="user.city" class="required" rel="iselect" style="width: 153px; "  >
	        					<option value=''>请选择</option>
	        				</select>
	        			</td>
	        		</tr>
					
					<%-- <tr class="jobNumber" style="display:none">
						<td class="label">
							<label>
								座席员工号：
							</label>
						</td>
						<td>
							<input type="text" name="user.jobNumber" value="${user.jobNumber}" maxlength="30">
						</td>
					</tr>
					<tr class="ctiPassword" style="display:none">
						<td class="label">
							<label>
								座席员密码：
							</label>
						</td>
						<td>
							<input type="text" name="user.ctiPassword" value="${user.ctiPassword}" maxlength="30">
						</td>
					</tr> --%>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="保 存"
								onclick="doit();submitForm(this.form)" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>

				</table>
			</form>
		</div>
	</body>
</html>
