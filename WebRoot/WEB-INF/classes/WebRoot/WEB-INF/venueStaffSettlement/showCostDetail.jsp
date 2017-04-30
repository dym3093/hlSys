<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	
function removeDetailInfo(obj){//删除行
	
	if(confirm("确定要删除该用户的费用信息？")){
		var costDetailId = $(obj).parents("tr").children().eq(1).children().val();
		var name = $("#name").val();
		var conferenceId = $("#conferenceId").val();
		var price = $(obj).parent().prev().text();
		var belong = $("#belong").val();
		var costId = $("#costId").val();
		
		if(costDetailId==0){
			$(obj).parents("tr").remove();
		}else{
			$.ajax({	
				type: "post",
				cache :false,
				dateType:"json",
				data:{"costDetailId":costDetailId,"name":name,"conferenceId":conferenceId,"price":price,"belong":belong,"costId":costId},
				url: "${path}/venueStaffSett/conferCostMan!removeDetailInfo.action",
				success: function(data){
					var str = eval("("+data+")");
					if(str.count==1){
						$(obj).parents("tr").remove();
						navTab.closeCurrentTab();
						navTab.openTab("editSettlement", "${path}/venueStaffSett/conferCostMan!addConferenceCostInfo.action", 
								{ title:"人员费用录入", fresh:false, data:{"conferenceId":conferenceId} });
					}else{
						alertMsg.error("删除失败");
					}
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	}
}

function addDetailRow(){
		var length = $("#addDetailCost tr").length;
		var row = "<tr >"+
			"<td align='center'><input type='text' class='textInput' size='5' value='"+(length+1)+"' name='' style='width:30px;' readonly='readonly'/></td>"+
			"<td align='center' hidden='hidden'><input type='text' class='textInput' size='5' value='0'  name='costDetailList.id' style='width:100px;' readonly='readonly'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${cost.days}' name='costDetailList.days' maxlength='10' style='width: 200px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.flight}' name='costDetailList.flight' maxlength='20' style='width: 200px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.descripe}' name='costDetailList.descripe' maxlength='100' style='width: 200px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.cost }' name='costDetailList.cost' maxlength='20' style='width: 200px;'/></td>"+
			"<td align='center' hidden='hidden'>0</td>"+
			"<td align='center'><a class='btnDel' href='javascript:void(0)' onclick='removeDetailInfo(this)'>删除</a></td>"+
			"</tr>";
		$("#addDetailCost").append(row);
}

function saveConferenceCostDetail(){
	if($("#addDetailCost tr").length==0){
		alertMsg.error("没有费用需要提交");
		return;
	}
	var costDetailId = "";
	var days = "";
	var flight = "";
	var descripe = "";
	var cost = 0;
	var count = 0;
	var dataJson = "[";
	$("#addDetailCost tr").each(function(index,items){
		costDetailId = $(items).children().eq(1).children().val();			//id
		days = $.trim($(items).children().eq(2).children().val());			//天数
		flight = $.trim($(items).children().eq(3).children().val());	//航班
		descripe = $.trim($(items).children().eq(4).children().val());//描述
		cost = $.trim($(items).children().eq(5).children().val());	//金额
		price = $.trim($(items).children().eq(6).text());	//原始价格
		if(isNaN(cost) || cost==""){
			count++;
		}
		dataJson += "{'costDetailId':'"+costDetailId+"',"+"'days':'"+days+"',"+"'flight':'"+flight+"',"+"'descripe':'"+descripe+"',"+"'price':"+price+","+"'cost':"+cost+"},";
	});
	if (dataJson.lastIndexOf(",")) {  
		dataJson = dataJson.substring(0,dataJson.length -1);  
		dataJson +="]";
	}  
	if(count!=0){
		alertMsg.error("请输入正确的金额");
		return;
	}
	var submitState = $("#submitState2").val();
	if(submitState==1){
		alertMsg.error("请勿重复提交");
		return;
	}
	$("#submitState2").val(1);
	var conferenceId = $("#costDetailId").val();
	var conferenceNo = $("#conferenceNo").val();
	var name = $("#name").val();
	var belong = $("#belong").val();
	var travelCost = $("#travelCost2").val();
	var instructorCost = $("#instructorCost2").val();
	var costId= $("#costId").val();
	var amout= $("#amout").val();
	var thisCost= $("#thisCost").val();
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		data:{"dataJson":dataJson,"conferenceId":conferenceId,"conferenceNo":conferenceNo,"name":name,"belong":belong,"instructorCost":instructorCost,"travelCost":travelCost,"costId":costId,"thisCost":thisCost,"amout":amout},
		url: "${path}/venueStaffSett/conferCostMan!saveConferenceCostDetail.action",
		success: function(data){
			var str = eval("("+data+")");
			if(str.count==1){
				alertMsg.correct("提交成功");
				navTab.closeCurrentTab();
				navTab.openTab("editSettlement", "${path}/venueStaffSett/conferCostMan!addConferenceCostInfo.action", 
						{ title:"人员费用录入", fresh:false, data:{"conferenceId":conferenceId} });
				$.pdialog.closeCurrent();
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function closeDialog(){
	$.pdialog.closeCurrent();
}
</script>
</head>
<body>
	<div class="pageContent">
		<div  style="height: 410px; padding: 0px;">
	    	<div hidden="hidden"> <!-- class="divider" -->
	    		<input id="costDetailId" type="text" name="conferenceId" value="${conferenceId}"/>
	    		<input id="conferenceNo" type="text" name="conferenceNo" value="${conferenceNo}"/>
	    		<input id="name" type="text" name="name" value="${name}"/>
	    		<input id="belong" type="text" name="belong" value="${belong}"/>
	    		<input id="travelCost2" type="text" name="travelCost" value="${travelCost}"/>
	    		<input id="instructorCost2" type="text" name="instructorCost" value="${instructorCost}"/>
	    		<input id="costId" type="text" name="costId" value="${costId}"/>
	    		<input id="amout" type="text" name="amout" value="${amout}"/>
	    		<input id="thisCost" type="text" name="thisCost" value="${thisCost}"/>
	    		<input id="submitState2" type="text" value="0" readonly="readonly"/>
	    	</div>
	      	<div>
	        	<div class="tip"><span>明细信息</span></div>
	        	<button id="addDetailInfo" type="button" onclick="addDetailRow();" >新增人员费用</button>
	        	<span style="color:red;">如果进行删除操作可以不用点击提交即可更新</span>
	        	<div style="overflow:scroll;height: 360px;">
	           		<table class="list" width="100%">
	            		<thead>
	              			<tr>
	                 			<th type="num" name="num" defaultVal="#index#" filedStyle="width: 30px">序号</th>
								<th type='text' name='costDetailList.id' filedStyle='width: 100px' hidden="hidden">id</th>
								<th type='text' name='costDetailList.days' filedStyle='width: 100px'>天数</th>
								<th type='text' name='costDetailList.flight' filedStyle='width: 100px'>航班/车次(其他类别可不填写)</th>
								<th type='text' name='costDetailList.descripe' filedStyle='width: 80px'>描述</th>
								<th type='text' name='costDetailList.cost' filedStyle='width: 80px'>金额</th>
								<th hidden="hidden" type='text' name='costDetailList.price' filedStyle='width: 80px'>金额</th>
				                <th type="del" width="28">操作</th>
	              			</tr>
	            		</thead>
	            		
		        		<tbody id="addDetailCost">
	            			<c:forEach items="${costDetail}" var="cost" varStatus="status">
								<tr >
	  								<td align="center">
	  									<input type="text" size="5" value="${status.count}" name="" style="width:30px;" readonly="readonly"/>
	  								</td>
	  								<td align="center" hidden="hidden">
	  									<input type="text" class="textInput" size="5" value="${cost.id}"  name='costDetailList.id' style="width:100px;" readonly="readonly"/>
	  								</td>
				 					<td align="center">
				 						<input type='text' class="textInput" value='${cost.days}' name='costDetailList.days' maxlength='10' style='width: 200px;'/>
				 					</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.flight}' name='costDetailList.flight' maxlength='20' style='width: 200px;'/>
									</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.descripe}' name='costDetailList.descripe' maxlength='100' style='width: 200px;'/>
									</td>
									<td align="center" >
										<input type='text' class="textInput" value='${cost.cost }' name='costDetailList.cost' maxlength='20' style='width: 200px;'/>
									</td>
									<td align="center" hidden="hidden">${cost.cost }</td>
									<td align="center">
										<a class="btnDel" onclick="removeDetailInfo(this)" href="javascript:void(0)">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	      	</div>
		</div>
	    <div class="formBar" style="width:98%">
	   		<ul>
	        	<li>
	          		<div class="button"><div class="buttonContent"><button type="button" onclick="saveConferenceCostDetail()">提交</button></div></div>
	        	</li>
	        	<li>
	          		<div class="button"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
	        	</li>
	      	</ul>
	    </div>
	</div>  



</body>
</html>