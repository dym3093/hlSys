<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

//清除内容
$(document).ready(function(){
	
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
	$.fn.serializeJson=function(){  
		var serializeObj={};  
		var array=this.serializeArray();  
		$(array).each(function(){  
			if(serializeObj[this.name]){  
				if($.isArray(serializeObj[this.name])){  
					serializeObj[this.name].push(this.value);  
				}else{  
					serializeObj[this.name]=[serializeObj[this.name],this.value];  
				}  
			}else{  
				serializeObj[this.name]=this.value;   
			}  
		});  
		return serializeObj;  
	}; 
	
	function selectCombo(){
		var comboId = $("select[name='combo'] option:selected").val();
		var combo = $("select[name='combo'] option:selected").text();
		$("#comboId").val(comboId);
		$("#combo").val(combo);
		alert(comboId+", "+combo);
	}

});

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
}

//验证
function vialdForm(){
	var flag = true;
	$("[class='required']").each(function(i, n){
		var obj = $(this);
		var val = obj.val();
		if(val==""||val==null){
			//label文本
			var inputId = obj.attr("id");
			//alert(inputId);
			var qTxt = "label[for='"+inputId+"']";
			//alert(qTxt);
			var text = $(""+qTxt).html();
			alert("请填写【"+text+"】");
			flag = false;
		}
	});
	return flag;
}

/**
 * 保存
 */
function save(){
	
	
	var temp = $("#remark").val();
	var remark = new String(temp).replace(/(^\s*)|(\s*$)/g, "");
	
	var entity = {
			"company": $("#company").val(),
			"companyId": $("#companyId").val(),
			"combo": $("#combo  option:selected").text(),
			"comboId": $("#combo  option:selected").val(),
			"comboPrice": $("#comboPrice").val(),
			"remark": remark
	};

	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/settlementManagement/erpCompanyComboPrice!saveErpCompanyComboPrice.action",
		data:{"ErpCompanyComboPrice": JSON.stringify(entity)},
		success: function(data){
			var resp= eval("("+data+")");
			if(resp.result=='success'){
				alertMsg.correct('保存成功!');
				$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alertMsg.error('保存失败');
			}
		},
		error :function(){
			alertMsg.error('保存失败');
		}
	});
		
}


//根据公司ID查找套餐
function searchCombo(){
	
	//var optVal = $("#combo option").val();  ||optVal==""||optVal==null||optVal==undefined
		
		var companyId = $("#companyId").val();
		
		if(companyId!=null&&companyId.length>0){
			$.ajax({		
				url: "${path}/settlementManagement/erpCompanyComboPrice!listComboByCompanyId.action",	
				type: "post",
				cache :false,
				dateType:"json",
				data: {"companyId": companyId},
				success: function(data){
					/*$("#combo").empty();*/
					$("select[name='combo']").empty();
					var s= eval("("+data+")");
					var option="";
					var objs = s.list;
					for(var i=0;i<objs.length;i++){		
						option += "<option value='"+objs[i].id+"'>"+objs[i].comboName+"</option>";
					}
					//alert(option);
					//将节点插入到下拉列表中
					$("select[name='combo']").append(option);
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}else{
			alertMsg.info("请先选择公司！");
		}
}


</script>
<!-- <div class="tip"><span>添加保险公司套餐价格</span></div> -->
<div class="pageHeader" style="background:white;" >
		<form class="pageForm required-validate" id="addForm" >
		<table class="pageFormContent">
			<tr>
				<td>
                	<label for="company">公司：</label>
                	<input type="hidden" name="id" value="${entity.id}" /> 
                	<input type="text" id="company" name="company" bringbackname="customerRelationShip.branchCommany" value="${entity.company}" class="required" />  
                    <input type="hidden" id="companyId"  name="companyId" bringbackname="customerRelationShip.id" value="${entity.companyId}" onchange="searchCombo()"/>              
				  	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a>
				  	<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>						
				</td>
				<td>  
					<label for="combo">套餐：</label> 
					<select id="combo" name="combo" class="equired" style="height:20px;margin:5px;width:200px;" >
						<option value=''>---请选择---</option>
		 			</select>
                 </td>
	  		</tr>
	  		<tr>
	  			<td>
					<label for="comboPrice">价格：</label> 
					<input  id="comboPrice" type="text" name="comboPrice" value="${entity.comboPrice}" class="required" onblur="if(this.value==''||this.value==null){alertMsg.error('请填写价格！');}"/>	                    
				</td>
				<td>
				</td>
			</tr>
	  		<tr>
	  			<td colspan="2">
		  			<p class="nowrap"><label style="height:60px;" for="remark">备注：</label>
						<textarea style="width:565px;" id="remark" name="remark" >
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
