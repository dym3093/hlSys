<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	textarea{
		resize:none;
	}
	select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
	
	.secondForm{
		display: none;
	}
	
</style>
<script type="text/javascript" language="javascript">
	$(function(){
		$("input+span").css("margin-top","100px");
		var gender=$("#gender").val();
		if(gender=='女'){
			$(":radio").eq(1).attr("checked","checked");
		}else{
			$(":radio").eq(0).attr("checked","checked");
		}
		$(":radio").bind("click", function(){
			var val=$(this).val();
			$(this).parent().children(":text").val(val);
		});
		if($("#table1 #sampleCode").val()==""){	//采样快递编号
			$("#table1 #sampleCode").attr("readonly",true);
		}
		if($("#table1 #reportCode").val()==""){	//报告快递编号
			$("#table1 #reportCode").attr("readonly",true);
		}
		if($("#table1 #code").val()==""){	//条形码
			$("#table1 #code").attr("readonly",true);
		}
	});
	
	function validateOrder(){	//查看是否有信息修改，有则提交更改的input
		var count=0;		//统计有多少变动的值
		var textArea1=$.trim($("#textArea1").val());	
		var textArea2=$.trim($("#textArea2").val());
		if(textArea1!=textArea2){
			count++;
		}
		var lable="";	//获取改动的input的id值(和数据对应)
		var str=$("input[id='id']").attr("id")+","+$.trim($("input[id='id']").val())+",";		//根据该值进行更新数据
		$("#table1 tr td").each(function(index,data){
			textArea1=$.trim($(data).find("textarea").val());
			$(data).find(":text").each(function(index1,data1){
//				alert($(data1).val()+","+index);
				lable=$(this).attr("id");
				$("#table2 tr td").each(function(key,item){
					$(item).find(":text").each(function(index2,data2){
						if(index==key && $.trim($(data1).val())!=$.trim($(data2).val())){
//							alert(key+","+$.trim($(data1).val()));
							str+=lable+","+$.trim($(data1).val())+",";
							count++;
//							alert(key+","+$(data1).val()+","+$(data2).val());
						}
					});
				});
			});
		});
		if (str!=undefined && str.lastIndexOf(",")) {  
			str = str.substring(0,str.length-1);  
		} 
		if(count==0){
			alertMsg.info("未做更改，无需保存");
			return false;
		}else{
			$.ajax({	
	 			type: "post",
	 			cache :false,
	 			dateType:"json",
	 			url: "${path}/children/erpOrderInfo!updateCustomerOrderInfo.action",
	 			data:{"json":str},
	 			success: function(data){
	  				if(eval("("+data+")").count>=1){
	  					alertMsg.info("订单更新成功");
	  					$("#table1").find("input").attr("disabled", true).css("background-color", "RGB(246,246,246)");
	  					$("#table1").find("textarea").attr("disabled", true).css("background-color", "RGB(246,246,246)");
	  					$("#table1").find("button").attr("disabled", true).css("cursor", "not-allowed");
// 	  					$("#table1 tr td").each(function(index,data){
// 	  						textArea1=$.trim($(data).find("textarea").val());
// 	  						$(data).find(":text").each(function(index1,data1){
// 	  							lable=$(this).attr("id");
// 	  							$("#table2 tr td").each(function(key,item){
// 	  								$(item).find(":text").each(function(index2,data2){
// 	  									if(index==key && $.trim($(data1).val())!=$.trim($(data2).val())){
// 	  										$(data2).val($.trim($(data1).val()));
// 	  									}
// 	  								});
// 	  							});
// 	  						});
// 	  					});
	  				}else{
	  					alertMsg.error("更新失败");
	  				}
	  			},
	 			error :function(){
	 				alertMsg.error("服务器发生错误,请稍后再试");
	 			}
	 		});
		}
	}
	
// 	$(function(){
// 		$.ajax({	
// 			type: "post",
// 			cache :false,
// 			dateType:"json",
// 			url: "${path}/children/erpOrderInfo!getSelectedInput.action",
// 			success: function(data){
//  				var selectComboId = $("#table1 #comboText").val();
// 				$("#comboSel").empty();
// 				var s= eval("("+data+")");
//   				var option="<option value=''>---请选择---</option>";
//   				var combos = eval("("+data+")").combo;
// 				for(var i=0;i<combos.length;i++){	
// 					alert(selectComboId);
// 					if(selectComboId==combos[i].COMBO){
// 						option += "<option value='"+combos[i].COMBO+"' selected='selected'>"+combos[i].COMBO+"</option>";
// 					}else{
// 						option += "<option value='"+combos[i].COMBO+"'>"+combos[i].COMBO+"</option>";
// 					}
// 				}
//  				$("#table1 #comboSel").append(option);
//  			},
// 			error :function(){
// 				alert("服务发生异常，请稍后再试！");
// 				return;
// 			}
// 		});
// 	});
	
// 	$("#table1 #comboSel").change( function() {	//套餐下拉框
// 		var selectVal=$("#table1 #comboSel option:selected").text();
// 		$("#table1 #comboText").val(selectVal);
// 	});
	
// 	$("input[name='sex']:checked").change( function() {	//状态下拉框
// 		var selectVal=$(this).val();
// 		alert(selectVal);
// 		$(this).parent().children(":input").val(selectVal);
// 	});
	
	function closeTab(){
		navTab.closeCurrentTab();
	}
	
</script>

<div class="tip"><span>订单信息修改</span></div>
<div class="pageHeader">	<!-- return validateOrder(); -->
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/children/erpOrderInfo!updateCustomerOrderInfo.action'){this.action = '${path}/children/erpOrderInfo!updateCustomerOrderInfo.action' ;} ;return navTabSearch(this);" 
		action="${path}/children/erpOrderInfo!updateCustomerOrderInfo.action" method="post"	>
	<div class="searchBar">
		<table class="pageFormContent" id="table1">
			<tr>
				<td>
					<label>批次号：</label> 
					<input id="id" type="text" name="filter_and_id_LIKE_S"  value="${id}"  readonly="readonly"/>
				</td>
				<td>
					<label>订单号：</label> 
					<input id="orderNo"  type="text" name="filter_and_orderNo_LIKE_S"  value="${orderNo}" readonly="readonly"/>
				</td>
				<td>
					<label>订单日期：</label>
					<input id="orderDate"  type="text" name="filter_and_orderDate_LIKE_T"  value="${fn:substring(orderDate,0,10)}" readonly="readonly"/>
				</td>
				<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>姓名：</label>
					<input id="name"  type="text" name="filter_and_name_LIKE_S"  value="${name}" required="required"/>
				</td>
				<td>
					<label>性别：</label>
					<input id="sex"  type="text" name="filter_and_sex_LIKE_S" id="gender" value="${sex}" hidden="hidden"/>
					<input type="radio" name="sex" value="男"/><span>男</span>
					<input type="radio" name="sex" value="女"/><span>女</span>
				</td>
				<td>
					<label>年龄：</label>
					<input id="age"  type="text" name="filter_and_age_LIKE_S"  value="${age}"  required="required"/>
				</td>
				<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>套餐：</label> 
					<input type="text" name="filter_and_setMealName_LIKE_S"  value="${setMealName}" id="comboText" hidden="hidden"/>
					<select id="comboSel" disabled="true">
						<option value="${setMealName}">${setMealName}</option>
					</select>
				</td>
				<td>
					<label>监护人姓名：</label> 
					<input id="guardianName"  type="text" name="filter_and_guardianName_LIKE_S"  value="${guardianName}"   required="required"/>
				</td>
				<td>
					<label>监护人手机：</label>
					<input id="guardianPhone"  type="text" name="filter_and_guardianPhone_LIKE_S"  value="${guardianPhone}"   required="required"/>
				</td>
         		<td> </td>
			</tr>
			
			<tr>
				<td>
					<label>关系：</label>
					<input id="relationship"  type="text" name="filter_and_relationship_LIKE_S"  value="${relationship}"  required="required"/>
				</td>
				<td>
					<label>身份证号：</label>
					<input id="idNo"  type="text" name="filter_and_idNo_LIKE_S"  value="${idNo}"  required="required"/>
				</td>
				<td>
					<label>地址：</label>
					<input id="reportAddress"  type="text" name="filter_and_reportAddress_LIKE_S"  value="${reportAddress}"  required="required"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td>
					<label>体重：</label>
					<input id="weight"  type="text" name="filter_and_weight_LIKE_S"  value="${weight}"  />
				</td>
				<td>
					<label>身高：</label>
					<input id="height"  type="text" name="filter_and_height_LIKE_S"  value="${height}"  />
				</td>
				<td>
					<label>家族疾病史：</label>
					<input id="familyHistory"  type="text" name="filter_and_familyHistory_LIKE_S"  value="${familyHistory}"  />
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td colspan="2">
					<label>备注：</label>
					<textarea style="width: 495px;" id="textArea1">${note}</textarea>
				</td>
				<td></td>
			</tr>	
			
			<tr>
				<td>
					<label>条码</label>
					<input id="code" type="text" value="${code}"/>
				</td>
				<td>
					<label>采样快递编号</label>
					<input id="sampleCode"  type="text" value="${sampleCode}"/>
				</td>
				<td>
					<label>报告快递编号</label>
					<input id="reportCode"  type="text" value="${reportCode}"/>
				</td>
				<td> 
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="validateOrder()">保存</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeTab()">返回</button></div></div>
				</td>
			</tr>	
		</table>
	</div>
	</form>
</div>
 <!-- class="secondForm" -->
<div class="secondForm" >
	<div class="pageHeader">
		<table class="pageFormContent" id="table2">
				<tr>
					<td>
						<label>批次号：</label> 
						<input type="text" name="filter_and_id_LIKE_S"  value="${id}"  readonly="readonly"/>
					</td>
					<td>
						<label>订单号：</label> 
						<input type="text" name="filter_and_orderNo_LIKE_S"  value="${orderNo}" readonly="readonly"/>
					</td>
					<td>
						<label>订单日期：</label>
						<input type="text" name="filter_and_orderDate_LIKE_T"  value="${fn:substring(orderDate,0,10)}" readonly="readonly"/>
					</td>
					<td> </td>
				</tr>
				
				<tr>
					<td>
						<label>姓名：</label>
						<input type="text" name="filter_and_name_LIKE_S"  value="${name}" required="required"/>
					</td>
					<td>
						<label>性别：</label>
						<input type="text" name="filter_and_sex_LIKE_S" id="gender" value="${sex}" />
					</td>
					<td>
						<label>年龄：</label>
						<input type="text" name="filter_and_age_LIKE_S"  value="${age}"  required="required"/>
					</td>
					<td> </td>
				</tr>
				
				<tr>
					<td>
						<label>套餐：</label> 
						<input type="text" name="filter_and_setMealName_LIKE_S"  value="${setMealName}" id="comboText"/>
					</td>
					<td>
						<label>监护人姓名：</label> 
						<input type="text" name="filter_and_guardianName_LIKE_S"  value="${guardianName}"   required="required"/>
					</td>
					<td>
						<label>监护人手机：</label>
						<input type="text" name="filter_and_guardianPhone_LIKE_S"  value="${guardianPhone}"   required="required"/>
					</td>
	         		<td> </td>
				</tr>
				
				<tr>
					<td>
						<label>关系：</label>
						<input type="text" name="filter_and_relationship_LIKE_S"  value="${relationship}"  required="required"/>
					</td>
					<td>
						<label>身份证号：</label>
						<input type="text" name="filter_and_idNo_LIKE_S"  value="${idNo}"  required="required"/>
					</td>
					<td>
						<label>地址：</label>
						<input type="text" name="filter_and_reportAddress_LIKE_S"  value="${reportAddress}"  required="required"/>
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td>
						<label>体重：</label>
						<input type="text" name="filter_and_weight_LIKE_S"  value="${weight}"  />
					</td>
					<td>
						<label>身高：</label>
						<input type="text" name="filter_and_height_LIKE_S"  value="${height}"  />
					</td>
					<td>
						<label>家族疾病史：</label>
						<input type="text" name="filter_and_familyHistory_LIKE_S"  value="${familyHistory}"  />
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<label>备注：</label>
						<textarea style="width: 495px;" id="textArea2">${note}</textarea>
					</td>
					<td></td>
				</tr>	
				
				<tr>
					<td>
					<label>条码</label>
					<input id="code" type="text" value="${code}"/>
				</td>
				<td>
					<label>采样快递编号</label>
					<input id="sampleCode"  type="text" value="${sampleCode}"/>
				</td>
				<td>
					<label>报告快递编号</label>
					<input id="reportCode"  type="text" value="${reportCode}"/>
				</td>
					<td> <button onclick="validateOrder()">点我</button></td>
				</tr>	
			</table>
	</div>
</div>