<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript" language="javascript" src="${path}/scripts/jquery.js"></script>
		<script type="text/javascript" src="${path}/scripts/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">

$(function(){
	proviceChange();	
});

	function changesourceType(value) {
		if (value == '1') {
			$(".jobNumber").show();
			$(".ctiPassword").show();
			$(".customServiceRole").show();
			$(".customServiceType").show();
		} else {
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
  
	function doit(){
		var v = $('#isCustomServicea option:selected').text();
	   $('#ctiPassword').val(v);
	}
	
	function proviceChange() {
		var parentId = $("#provinceId").val();
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
					if(data[i].id == '${user.city }') {
						opt += "<option value='"+data[i].id+"' selected='selected' >"+data[i].text+"</option>";
					} else {
						opt += "<option value='"+data[i].id+"' >"+data[i].text+"</option>";
					}
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
		    content: 'url:${path }/resource/customerRelationShip!findCompanyByOwnerCompanyId.action?params.wonedCompanyId=${user.deptId}&params.companyId=${user.jobNumber}',
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
					<web:location value="用户管理-修改用户" />
				</td>
				<td class="back">
					<web:back url="user!listUser.action?deptId=${user.deptId }" />
				</td>
			</tr>
		</table>
		<div class="form-content">
			<form id="_form" class="form"
				action="${path}/um/user!updateUser.action" method="post">
				<web:transfer />
				<!-- 去掉是否启用行,加入隐藏域   modified by henry.xu 20160809 -->
				<input type="hidden" name="user.isEnable" value="1"/>
				<input type="hidden" name="user.deptId" value="${user.deptId}">
				<input type="hidden" name="user.deptName" value="${user.deptName}">
				<table>
				<tr>
					<td class="label"><label>总公司：</label></td>
					<td>
						<input type="text" value="${user.deptName }" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="label"><label>登录名：</label></td>
					<td>
						<input type="text" value="${user.accountName}" disabled="disabled">
					</td>
				</tr>
					<tr>
						<td class="label"><label><font class="star">*</font>用户姓名：</label></td>
						<td>
							<input type="text" name="user.userName" value="${user.userName}">
						</td>
					</tr>
					<tr>
						<td class="label"><label><font class="star">*</font>用户口令：</label></td>
						<td>
							<input type="password" name="user.password" value="${user.password}" style="width: 153px;">
						</td>
					</tr>
					
					<tr>
						<td class="label">
							<label>数据权限：</label>
						</td>
						<td>
							<select name="user.dataPriv" rel="iselect" class="required" style="width:153px;">
								<option value="priv_01" ${user.dataPriv == "priv_01" ? "selected" : "" }>部门数据</option>
								<option value="priv_02" ${user.dataPriv == "priv_02" ? "selected" : "" }>个人数据</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label"><label>电子邮箱：</label></td>
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
							        <option id="branch" value="${customer.id }" ${customer.id == user.jobNumber ? "selected" : '' }>${customer.branchCommany}</option>
							    </c:forEach>
							</select>
							<input type="hidden" name="user.extension" id="ctiPassword" /> --%>
							<input id="companyName" type="text" name="user.extension" readonly="readonly" value="${user.extension }"/>
							<input id="companyId" type="hidden" name="user.jobNumber" value="${user.jobNumber }"/>
							<input type="button" value="查找" onclick="dialogCompany();"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>远盟对接人：</label>
						</td>
						<td>
							<input type="text" name="user.ymSaleMan" id="ymSaleMan" value="${user.ymSaleMan }"/>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label>项目编码：</label>
						</td>
						<td>
							<input type="text" name="user.projectCode" id="projectCode" value="${user.projectCode}"/>
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
	        				<select id="provinceId" class="required" name="user.provice" rel="iselect" style="width: 153px; " onchange="proviceChange();">
								<option value="">请选择</option>
								<c:forEach items="${provices }" var="item">
									<option value="${item.id }" ${user.provice == item.id ? "selected": "" }>${item.regionName }</option>
								</c:forEach>
							</select>
	        			</td>
	        			
	        		</tr>
	        		<tr>
	        			<td><label>城市：</label></td>
	        			<td>
	        				<select id="cityId" name="user.city" class="required" rel="iselect" style="width: 153px; " >
	        				</select>
	        			</td>
	        		</tr>

					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="保 存"
								onclick="doit(); submitForm(this.form)" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>
				</table>
				<input type="hidden" name="user.id" value="${user.id }">
			</form>
		</div>
	</body>
</html>
