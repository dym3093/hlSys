<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript">
$(document).ready(function(){
	keepAlive();
	$("#addTableRow").live("click",function(){
		$("#addConference").find("tr:last").children().find("input[name*='Cost'], a[name*='amount']").val(0);
		keepAlive();
	});
});

function keepAlive(){
//	$("#addConference").find("tr:last").children().find("input[name*='Cost'], a[name*='amount']").val(0);
	var inputName = "";
	var name = "";
	var arr = new Array();
	$("td").delegate("input[name*='staff']", "change", function(){
		$(this).parents("tr").children().find("input[name*='staff']").each(function(index,items){
			inputName = $(items).val();		//获取输入的名字
		});
		$("#newPersonInfo").children().find("input[name*='staff']").each(function(key,value){
			name = $(value).val();	//已有的名字
			arr[key]=name;
		});
	});
	
	$("td").delegate("input[name*='staff']", "change", function(){
		var count = 0;
		for(var i=0;i<arr.length;i++){	//用于判断重复
			if(inputName==arr[i]){
				count++;	
			}
		}
		if(count>=2){
			alertMsg.error("已有该人员的明细,请重新输入");
			$(this).parents("tr").children().find("input[name*='staff']").val("");
			return;
		}
	});
	$("td").delegate("input[name*='Cost']", "change", function(){
		if($.trim($(this).parents("tr").children().eq(2).children().val())==""){//获取姓名
			alertMsg.error("请先输入姓名");
			$(this).parents("tr").children().find("input[name*='Cost']").val(0);
			return;
		}
		$(this).parents("tr").children().find("input[name*='Cost']").each(function(i){
			if($.trim($(this).val())==""){
				$(this).val(0);
			}
		});
		var amount = new Number(0);
		$(this).parents("tr").children().find("input[name*='Cost'],a[name*='Cost']").each(function(i){
			amount +=Number($(this).val());
			amount +=Number($(this).text());
		});
		$(this).parents("tr").children().find("a[name*='amount']").text(amount);
		var produceCost = new Number(0);
		$("input[name*='amount']").each(function(i){
			produceCost +=Number($(this).val());
		});
		$("input[name='total']").val(produceCost);
	}); 
}

function addRow(){
	var length = $("#newPersonInfo tr").length;
	var row = "<tr>"+
		"<td align='center'><input type='text' class='textInput' size='5' value='"+(length+1)+"' name='' style='width:30px;' readonly='readonly'/></td>"+
		"<td align='center' hidden='hidden' ><input type='text' class='textInput' value='0' name='costList.id' maxlength='20' style='width: 80px;'/></td>"+
		"<td align='center'><input type='text' class='textInput' value='${entity.staff}' name='costList.staff' maxlength='50' style='width: 80px;'/></td>"+
		"<td align='center'><input type='text' class='textInput' value='${entity.travelCost }' name='costList.travelCost' maxlength='20' style='width: 80px;'/></td>"+
		"<td align='center'><input type='text' class='textInput' value='${entity.instructorCost}' name='costList.instructorCost' maxlength='20' style='width:80px;'/></td>"+
		"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.cityTrafficCost' onclick='addCostDetail(this,0)'>0</a></td>"+
		"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.provinceTrafficCost' onclick='addCostDetail(this,1)'>0</a></td>"+
		"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.hotelCost' onclick='addCostDetail(this,2)'>0</a></td>"+
		"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.sampleCost' onclick='addCostDetail(this,3)'>0</a></td>"+
		"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.otherCost' onclick='addCostDetail(this,4)'>0</a></td>"+
		"<td align='center'><a style='color: blue;'  name='costList[${status.count-1}].amount'>0</a></td>"+
		"<td><a class='btnDel ' onclick='removeRow(this)' href='javascript:void(0)'>删除</a></td>"+
		"</tr>";
	$("#newPersonInfo").append(row);
}

function removeRow(obj){//删除行
	if(confirm("确定要删除该用户的费用信息？")){
		var costId = $(obj).parents("tr").children().eq(1).children().val();
		var name = $(obj).parents("tr").children().eq(2).children().val();
		var conferenceId = $("#conferenceId").val();
		var price = $(obj).parent().prev().text();
		var produceCost = parseFloat($("#produceCost").val());
		var settNumbers = parseInt($("#settNumbers").val());
		if(costId==0){
			$(obj).parents("tr").remove();
		}else{
			$.ajax({	
				type: "post",
				cache :false,
				dateType:"json",
				data:{"costId":costId,"name":name,"conferenceId":conferenceId,"price":price},
				url: "${path}/venueStaffSett/conferCostMan!removeRow.action",
				success: function(data){
					$("#produceCost").val(produceCost-parseFloat(price));
					$("#settNumbers").val(settNumbers-1);
					$(obj).parents("tr").remove();
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	}
}

function saveConferenceCostInfo(){//提交保存
	if($("#newPersonInfo tr").length==0){
		alertMsg.error("没有费用需要提交");
		return;
	}
	var costId = "";
	var staff = "";
	var travelCost = 0;
	var instructorCost = 0;
	var dataJson = "[";
	var count = 0;
	var conferenceId = $("#conferenceId").val();
	$("#newPersonInfo tr").each(function(index,items){
		costId = $(items).children().eq(1).children().val();			//id
		if(costId==""){
			costId=0;
		}
		staff = $(items).children().eq(2).children().val();			//姓名
		travelCost = $(items).children().eq(3).children().val();	//出差补助
		instructorCost = $(items).children().eq(4).children().val();//讲师费
		if(isNaN(travelCost) || isNaN(instructorCost)){
			count++;
		}
		dataJson += "{\"costId\":'"+costId+"',"+"\"staff\":'"+staff+"',"+"\"travelCost\":'"+travelCost+"',"+"\"instructorCost\":'"+instructorCost+"'},";
	});
	if (dataJson.lastIndexOf(",")) {  
		dataJson = dataJson.substring(0,dataJson.length -1);  
		dataJson +="]";
	}  
	if(count!=0){
		alertMsg.error("请输入正确的费用");
		return;
	}
	var submitState = $("#submitState").val();
	if(submitState==1){
		alertMsg.error("请勿重复提交");
		return;
	}
	$("#submitState").val(1);
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		data:{"dataJson":dataJson,"conferenceId":conferenceId},
		url: "${path}/venueStaffSett/conferCostMan!saveConferenceCostInfo.action",
		success: function(data){
			if(eval("("+data+")").count==1){
				alertMsg.correct("提交成功");
				navTab.reloadFlag("editSettlement");
				navTab.reload("${path}/venueStaffSett/conferCostMan!addConferenceCostInfo.action?conferenceId="+conferenceId);
			}else{
				alertMsg.error("提交失败");
			}
				
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function addCostDetail(obj,belong){
	var name = encodeURI($.trim($(obj).parents("tr").children().eq(2).children().val()));
	if(name==""){
		alertMsg.error("请先输入姓名");
		return;
	}
	var conferenceId = $("#conferenceId").val();
	var conferenceNo = $("#conferenceNo").val();
	
	var costId = $.trim($(obj).parents("tr").children().eq(1).children().val());
	var travelCost = $.trim($(obj).parents("tr").children().eq(3).children().val());
	var instructorCost = $.trim($(obj).parents("tr").children().eq(4).children().val());
	if(isNaN(travelCost) || isNaN(instructorCost)){
		alertMsg.error("请输入正确的价格");
		return;
	}
	if(travelCost==""){
		travelCost=0;
	}
	if(instructorCost==""){
		instructorCost=0;
	}
	var title = "";
	if(belong==0){
		title = "市内交通费明细";
	}else if(belong==1){
		title = "往返交通费明细";
	}else if(belong==2){
		title = "住宿费明细";
	}else if(belong==3){
		title = "劳务费明细";
	}else if(belong==4){
		title = "其他费用明细";
	}
	
	$.pdialog.open("${path}/venueStaffSett/conferCostMan!showCostDetail.action?name="+name+"&conferenceId="+conferenceId+"&conferenceNo="+conferenceNo+"&belong="+belong+"&travelCost="+travelCost+"&instructorCost="+instructorCost+"&costId="+costId,
			"showCostDetail", title,{width:1000,height:500,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}

</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<div>
		<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/conferCostMan!saveConferenceCostInfo.action'){this.action = '${path}/venueStaffSett/conferCostMan!saveConferenceCostInfo.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/conferCostMan!saveConferenceCostInfo.action" method="post"  rel="pagerForm" id="pagerFindForm">
			<input id="conferenceId" type="hidden" name="id" value="${conference.id}" readonly="readonly">
			<input id="submitState" type="hidden" name="id" value="0" readonly="readonly">
			<div class="tip"><span>会议基本信息</span></div>
			<div class="pageHeader">
				<div class="searchBar">
					<table>
						<tr>
			  				<td>
			  					<label>会议号：</label>
			  					<input id="conferenceNo" type="text" name="conferenceNo" value="${conference.conferenceNo}" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>会议日期：</label>
			  					<input id="conferenceDate" type="text" name="conferenceDate" value="${conference.conferenceDate}" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>会议类型：</label>
			  					<input id="conferenceType" type="text" name="conferenceType"
			  					 value="<hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${conference.conferenceType}"/>" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>项目编码：</label>
			  					<input id="proCode" type="text" name="proCode" value="${shipPro.projectCode}" readonly="readonly">
			  				</td>
			  			</tr>
			  			
			  			<tr>
			  				<td>
			  					<label>项目负责人：</label>
			  					<input id="proUser" type="text" name="proUser" value="${shipPro.projectOwner}" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>项目类型：</label>
			  					<input id="protype" type="text" name="protype" value="<hpin:id2nameDB id="${shipPro.projectType}" beanId="projectTypeDao"/>" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>支公司：</label>
			  					<input id="branchCompany" type="text" name="branchCompany" value="${conference.branchCompany}" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>所属公司：</label>
			  					<input id="owedCompany" type="text" name="owedCompany" value="${conference.ownedCompany}" readonly="readonly">
			  				</td>
			  			</tr>
			  			
			  			<tr>
			  				<td>
			  					<label>省份：</label>
			  					<input id="province" type="text" name="province" value="<hpin:id2nameDB id="${conference.provice}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>城市：</label>
			  					<input id="city" type="text" name="city" value="<hpin:id2nameDB id="${conference.city}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>结算人数：</label>
			  					<input id="settNumbers" type="text" name="settNumbers" value="${conference.settNumbers}" readonly="readonly">
			  				</td>
			  				<td>
			  					<label>产生费用：</label>
			  					<input id="produceCost" type="text" name="produceCost" value="${conference.produceCost}" readonly="readonly">
			  				</td>
	  					</tr>
					</table>
				</div>
			</div>
			
			<div style="overflow:auto;" layoutH="170">
	        	<div class="tip"><span>人员费用信息</span></div>
	        	<div style="overflow-y:scroll;height: 300px;">
	        		<button id="addTableRow" type="button" onclick="addRow()">新增人员费用</button>
		          	<table id="addConference" class="list " width="100%" ><!-- addButton="新增人员费用" --> 
						<thead>
							<tr>
				                <th type="num" name="num" defaultVal="#count-1#" filedStyle="width: 30px">序号</th>
								<th type='text' name='costList.id' filedStyle='width: 80px' hidden="hidden">id</th>
								<th type='text' name='costList.staff' filedStyle='width: 80px'>姓名</th>
								<th type='text' name='costList.travelCost' filedStyle='width: 80px'>出差补助</th>
								<th type='text' name='costList.instructorCost' filedStyle='width: 80px'>讲师费</th>
								<th name='costList.cityTrafficCost' filedStyle='width: 80px'>市内交通费</th>
								<th name='costList.provinceTrafficCost' filedStyle='width: 80px'>往返交通费</th>
								<th name='costList.hotelCost' filedStyle='width: 80px'>住宿费</th>
								<th name='costList.sampleCost' filedStyle='width: 80px'>劳务费</th>
								<th name='costList.otherCost' filedStyle='width: 80px'>其他</th>
								<th name='costList.amount' filedStyle='width: 80px'>小计</th>
								<th name='costList.price' filedStyle='width: 80px' hidden="hidden">原始价格</th>
				                <th type="del" width="28">操作</th>
							</tr>
		        		</thead>
		        		
		        		<tbody id="newPersonInfo">
	            			<c:forEach items="${costList}" var="entity" varStatus="status">
								<tr >
				  					<td align="center">
				  						<input type="text" size="5" value="${status.count}" name="" style="width:30px;" readonly="readonly"/>
				  					</td>
									<td align="center" hidden="hidden">
										<input id="id" type="text" class="textInput" value="${entity.id}" name="costList.id" maxlength="20" style="width: 80px;"/>
									</td>
				 					<td align="center">
				 						<input id="staff" type="text" value="${entity.staff }" name="costList.staff" maxlength="50" style="width: 80px;"/>
				 					</td>
									<td align="center">
										<input id="travelCost" type="text" class="textInput" value="${entity.travelCost}" name="costList.travelCost" maxlength="20" style="width: 80px;"/>
									</td>
									<td align="center">
										<input id="instructorCost" type="text" class="textInput" value="${entity.instructorCost}" name="costList.instructorCost" maxlength="20" style="width: 80px;"/>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.cityTrafficCost" onclick="addCostDetail(this,0)">${entity.cityTrafficCost==null?0:entity.cityTrafficCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.provinceTrafficCost" onclick="addCostDetail(this,1)">${entity.provinceTrafficCost==null?0:entity.provinceTrafficCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.hotelCost" onclick="addCostDetail(this,2)">${entity.hotelCost==null?0:entity.hotelCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.sampleCost" onclick="addCostDetail(this,3)">${entity.sampleCost==null?0:entity.sampleCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.otherCost" onclick="addCostDetail(this,4)">${entity.otherCost==null?0:entity.otherCost}</a>
									</td>
									<td align="center">
										<a style="color: blue;" name="costList.amount">${entity.amount==null?0:entity.amount}</a>
									</td>
									<td align="center" hidden="hidden">${entity.amount==null?0:entity.amount}</td>
									<td><a class="btnDel " onclick="removeRow(this)" href="javascript:void(0)">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	     	</div>
	     	
	     	<div class="formBar" style="width:98%">
		    	<ul>
		        	<li>
		          		<div class="buttonActive"> <div class="buttonContent"><button type="button" onclick="saveConferenceCostInfo()">保存</button></div></div></li>
		        	<li>
		      	</ul>
		    </div>
		</form>
	</div>
</body>
</html>