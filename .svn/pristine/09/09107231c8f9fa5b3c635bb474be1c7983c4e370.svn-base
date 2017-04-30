<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript">
	$(document).ready(function(){
		keepAlive();
		$("#addNonConferenceRow").live("click",function(){
			$("#addNonConference",navTab.getCurrentPanel()).find("tr:last").children().find("input[name*='Cost'], a[name*='amount']").val(0);
			keepAlive();
		});
	});
	
	function keepAlive(){
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
	
	function pickedFunc(){//防止月份
		var y = $dp.cal.getP('y');
		var m = $dp.cal.getP('M');
		var d = parseInt($dp.cal.getP('d'));
		
		return $("#month",navTab.getCurrentPanel()).val(y+m);
// 		if(26<=d && d<=31 && m==12){
// 			y++;
// 			return $("#month",navTab.getCurrentPanel()).val(y+="01");
// 		}else if(26<=d && d<=31){
// 			m++;
// 			return $("#month",navTab.getCurrentPanel()).val(y+=("0"+m));
// 		}else{
// 			return $("#month",navTab.getCurrentPanel()).val(y+m);
// 		}
	}
	
	function addRow(){
		var length = $("#newPersonInfo tr",navTab.getCurrentPanel()).length;
		var row = "<tr>"+
			"<td align='center'><input type='text' class='textInput' size='5' value='"+(length+1)+"' name='' style='width:30px;' readonly='readonly'/></td>"+
			"<td align='center' hidden='hidden' ><input type='text' class='textInput' value='0' name='costList.id' maxlength='20' style='width: 60px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.staff}' name='costList.staff' maxlength='50' style='width: 80px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.travelCost }' name='costList.travelCost' maxlength='20' style='width: 80px;'/></td>"+
			"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.cityCost' onclick='addCostDetail(this,0)'>0</a></td>"+
			"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.provinceCost' onclick='addCostDetail(this,1)'>0</a></td>"+
			"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.hotelCost' onclick='addCostDetail(this,2)'>0</a></td>"+
			"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.laborCost' onclick='addCostDetail(this,3)'>0</a></td>"+
			"<td align='center'><a style='cursor: pointer;color: blue;' name='costList.otherCost' onclick='addCostDetail(this,4)'>0</a></td>"+
			"<td align='center'><a style='color: blue;'  name='costList[${status.count-1}].amount'>0</a></td>"+
			"<td><a class='btnDel ' onclick='removeCostRow(this)' href='javascript:void(0)'>删除</a></td>"+
			"</tr>";
		$("#newPersonInfo",navTab.getCurrentPanel()).append(row);
	}
	
	function removeCostRow(obj){//删除行
		if(confirm("确定要删除该用户的费用信息？")){
			var costId = $(obj).parents("tr").children().eq(1).children().val();
			var name = $(obj).parents("tr").children().eq(2).children().val();
			var nonConferenceId = $("#nonConferenceId",navTab.getCurrentPanel()).val();
			var pageStatus = getPageStatus();
			var title = "添加";
			var action = "${path}/venueStaffSett/nonConference!addNonConference.action";
			if (1 == pageStatus) {
				title = "修改";
				action = "${path}/venueStaffSett/nonConference!updateNonsConference.action";
			}
			if(costId==0){
				$(obj).parents("tr").remove();
			}else{
				$.ajax({	
					type: "post",
					cache :false,
					dateType:"json",
					data:{"costId": costId, "nonConferenceId": nonConferenceId, "pageStatus": pageStatus},
					url: "${path}/venueStaffSett/nonConference!removeCostRow.action",
					success: function(data){
						var str = eval("("+data+")");
						if (str.count==1) {
							$(obj).parents("tr").remove();
							alertMsg.correct("删除成功");
							navTab.reloadFlag("menu_2c90543a59c42af40159c44167e50002");
							navTab.openTab("addNonConference", action, 
									{ title:title, fresh:true, data:{"nonConferenceId":nonConferenceId} });
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

	function getProjectInfo(){
		var projectCode = $("#projectCode",navTab.getCurrentPanel()).val();
		var projectName = $("#projectName",navTab.getCurrentPanel()).val();
		var projectUser = $("#projectUser",navTab.getCurrentPanel()).val();
		var projectType = $("#projectType",navTab.getCurrentPanel()).val();
		var startTime = $("#d1",navTab.getCurrentPanel()).val();
		var endTime = $("#d2",navTab.getCurrentPanel()).val();
		var month = $("#month",navTab.getCurrentPanel()).val();
		var note = $("#note",navTab.getCurrentPanel()).val();
		
		var OASerial = $("#OASerial",navTab.getCurrentPanel()).val();
		if(projectCode == "" || projectName == "" || projectUser == "" || startTime == "" ||
				endTime == "" || month=="" || note == "" || OASerial == ""){
			alertMsg.warn("字段不全,请检查后重新输入");
			return;
		} 
		var projectInfo = "[{\"projectCode\":'"+projectCode+"',"+"\"projectName\":'"+projectName+"',"+"\"projectUser\":'"+
			projectUser+"',"+"\"projectType\":'"+projectType+"',"+"\"startTime\":'"+startTime+"',"+
			"\"endTime\":'"+endTime+"',"+"\"note\":'"+note+"',"+"\"OASerial\":'"+OASerial+"',"+"\"month\":'"+month+"'}]";
		return projectInfo;
	}

	function getNonConferenceCostInfo(){
		if($("#newPersonInfo tr",navTab.getCurrentPanel()).length==0){
			alertMsg.error("没有费用需要提交");
			return;
		}
		var costId = "";
		var staff = "";
		var travelCost = 0;
		var dataJson = "[";
		var count = 0;
		var count2 = 0;
		$("#newPersonInfo tr",navTab.getCurrentPanel()).each(function(index,items){
			costId = $(items).children().eq(1).children().val();			//id
			if(costId==""){
				costId=0;
			}
			staff = $(items).children().eq(2).children().val();			//姓名
			if(staff==""){
				count2++;
			}
			travelCost = $(items).children().eq(3).children().val();	//出差补助
			if(isNaN(travelCost)){
				count++;
			}
			dataJson += "{\"costId\":'"+costId+"',"+"\"staff\":'"+staff+"',"+"\"travelCost\":'"+travelCost+"'},";
		});
		if (dataJson.lastIndexOf(",")) {  
			dataJson = dataJson.substring(0,dataJson.length -1);  
			dataJson +="]";
		}  
		
		if(count!=0){
			alertMsg.error("请输入正确的费用");
			return;
		}
		if(count2!=0){
			alertMsg.error("请输入姓名");
			return;
		}
		return dataJson;
	}

	function saveConferenceCostInfo(){//提交保存
		
		var dataJson = getNonConferenceCostInfo();
		var projectInfo = getProjectInfo();
		var nonConferenceId = $("#nonConferenceId").val();
		if(dataJson == undefined || projectInfo == undefined){
			return;
		}
		var listNavTabId = getListNavTabId();
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"dataJson":dataJson,"projectInfo":projectInfo,"nonConferenceId":nonConferenceId},
			url: "${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action",
			success: function(data){
				var str = eval("("+data+")");
				if(str.count==1){
					alertMsg.correct("提交成功");
	 				navTab.reloadFlag("menu_2c90543a59c42af40159c44167e50002");//刷新前一个tab页面
	 				navTab.reload("${path}/venueStaffSett/nonConference!addNonConference.action?nonConferenceId="+str.nonConferenceId,{navTabId:"addNonConference"});
	 				
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
		var dataJson = getNonConferenceCostInfo();
		var projectInfo = getProjectInfo();
		if(dataJson == undefined || projectInfo == undefined){
			return;
		}
//		var costInfo = getNonConferenceCostInfo(obj);
		var nonConferenceId= $("#nonConferenceId",navTab.getCurrentPanel()).val();
		var costId = $.trim($(obj).parents("tr").children().eq(1).children().val());
		var name = $.trim($(obj).parents("tr").children().eq(2).children().val());
		var travelCost = $.trim($(obj).parents("tr").children().eq(3).children().val());
		var amount = $.trim($(obj).parents("tr").children().eq(9).children().text());
		var cost = $(obj).text();
		var pageStatus = getPageStatus();
		var title = "添加";
		if (1 == pageStatus) {
			title = "修改";
		}
		if(isNaN(travelCost) || travelCost==""){
			alertMsg.error("请输入正确的价格");
			return;
		}
		if(name==""){
			alertMsg.error("请输入姓名");
			return;
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
		var str = "?belong=" + belong +"&travelCost="+travelCost+"&costId="+costId+"&name="+encodeURI(name)+"&projectInfo="+encodeURI(projectInfo)
			+"&nonConferenceId="+nonConferenceId+"&amount="+amount+"&cost="+cost+"&pageStatus="+pageStatus;
		$.pdialog.open("${path}/venueStaffSett/nonConference!addNonConferenceCostDetail.action" + str,
				"addNonConferenceCostDetail", title,{width:1000,height:500,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
	
	function getNonConferenceCostInfo(obj){
		var costId = "";
		var staff = "";
		var travelCost = 0;
		var dataJson = "[";
		var count = 0;
		var amount = 0;
		var sequence = 0;
		var sequenceNum = $.trim($(obj).parents("tr").children().eq(0).children().val());
		$("#newPersonInfo tr",navTab.getCurrentPanel()).each(function(index,items){
			costId = $(items).children().eq(1).children().val();			//id
			if(costId==""){
				costId=0;
			}
			sequence = $(items).children().eq(0).children().val();		//序号
			if(sequence!=sequenceNum){
				staff = $(items).children().eq(2).children().val();			//姓名
				travelCost = $(items).children().eq(3).children().val();	//出差补助
				amount = $(items).children().eq(9).children().text();	//小计
				dataJson += "{\"costId\":'"+costId+"',"+"\"staff\":'"+staff+"',"+"\"amount\":'"+amount+"',"+"\"travelCost\":'"+travelCost+"'},";
			}
		});
		if (dataJson.lastIndexOf(",")) {  
			dataJson = dataJson.substring(0,dataJson.length -1);  
			dataJson +="]";
		}  
		return dataJson;
	}
	function getNavTabId(){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		return navTabId;
	}
	function getListNavTabId(){
		var listNavTabId = $("#listNavTabId",navTab.getCurrentPanel()).text();
		return listNavTabId;
	}
	function closeTab() {
		navTab.closeCurrentTab();
	}
	function getPageStatus() {
		var pageStatus = $("#pageStatus",navTab.getCurrentPanel()).text();
		return pageStatus; 
	}
	
</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<div>
		<div id="listNavTabId" hidden="hidden">${listNavTabId}</div>
		<div id="pageStatus" hidden="hidden">${pageStatus}</div>
		<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action'){this.action = '${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action" method="post"  rel="pagerForm" id="pagerFindForm">
			<div class="tip"><span>会议基本信息</span></div>
			<div class="pageHeader">
				<div class="searchBar">
					<div hidden="hidden"><input id="nonConferenceId" name="nonConferenceId" type="text" value="${nonConference.id}"></div>
					<table class="pageFormContent">
						<tr>
					        <td>
					        	<label>项目编码：</label>
					        	<input id="customerRelationShipProId" name="events.customerRelationShipProId" bringbackname="customerRelationShipPro.id" type="hidden" value=""  />
					        	<input id="projectCode" name="projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${nonConference.projectCode}"/>
					        	<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!findCustomerRelshipPro.action" callback="combo_callback" lookupGroup="customerRelationShipPro">查找带回</a>
								<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
					        </td>
					        <td>
					        	<label>项目名称：</label>
					        	<input id="projectName" name="projectName" bringbackname="customerRelationShipPro.projectName" readonly="readonly" class="required textInput" type="text" value="${nonConference.projectName}"/>
					        </td>
					       	<td>
					        	<label>项目负责人：</label>
					        	<input id="projectUser" name="projectUser" bringbackname="customerRelationShipPro.projectOwner" readonly="readonly" class="required textInput" type="text" value="${nonConference.projectUser}"/>
					        </td>
					       	<td hidden="hidden">
					        	<label>项目类型：</label>
					        	<input id="projectType" name="projectType" bringbackname="customerRelationShipPro.projectType" readonly="readonly" class="required textInput" type="text" value="${nonConference.projectType}"/>
					        </td>
			  			</tr>
			  			
			  			<tr>
			  				<td>
								<label>开始日期：</label> <input type="text"
								name="startTime" id="d1"
								style="width: 170px;"
								onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}',onpicked:pickedFunc})"
								readonly="readonly" value="${fn:substring(nonConference.startTime,0,10)}" /><a class="inputDateButton">选择</a>
							</td>
							<td><label>结束日期：</label> <input type="text"
								name="endTime" id="d2"
								style="width: 170px;"
								onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
								readonly="readonly" value="${fn:substring(nonConference.endTime,0,10)}" /><a
								class="inputDateButton" href="javascript:;">选择</a></td>
							<td>
			  					<label>月份：</label>
			  					<input id="month" type="text" name="month" value="${nonConference.month}" class="required">
			  				</td>
			  			</tr>
			  			
			  			<tr>
			  				<td>
			  					<label>OA流水号：</label>
			  					<input id="OASerial" type="text" name="OASerial" value="${nonConference.OASerial}" class="required">
			  				</td>
			  				<td colspan="2">
			  					<label>备注：</label>
			  					<input id="note" type="text" name="note" style="width: 513px;" value="${nonConference.note}" class="required">
			  				</td>
			  			</tr>
					</table>
				</div>
			</div>
			
			<div style="overflow:auto;" layoutH="170">
	        	<div class="tip"><span>人员费用信息</span></div>
	        	<div style="overflow-y:scroll;height: 300px;">
	        		<button id="addNonConferenceRow" type="button" onclick="addRow()">新增人员费用</button>
		          	<table id="addNonConference" class="list " width="100%" ><!-- addButton="新增人员费用" --> 
						<thead>
							<tr>
				                <th type="num" name="num" defaultVal="#count-1#" filedStyle="width: 30px">序号</th>
								<th type='text' name='costList.id' filedStyle='width: 60px' hidden="hidden">id</th>
								<th type='text' name='costList.staff' filedStyle='width: 80px'>姓名</th>
								<th type='text' name='costList.travelCost' filedStyle='width: 80px'>出差补助</th>
								<th name='costList.cityCost' filedStyle='width: 80px'>市内交通费</th>
								<th name='costList.provinceCost' filedStyle='width: 80px'>往返交通费</th>
								<th name='costList.hotelCost' filedStyle='width: 80px'>住宿费</th>
								<th name='costList.laborCost' filedStyle='width: 80px'>劳务费</th>
								<th name='costList.otherCost' filedStyle='width: 80px'>其他</th>
								<th name='costList.amount' filedStyle='width: 80px'>小计</th>
				                <th type="del" width="28">操作</th>
							</tr>
		        		</thead>
		        		
		        		<tbody id="newPersonInfo">
	            			<c:forEach items="${nonConferenceCostList}" var="entity" varStatus="status">
								<tr >
				  					<td align="center">
				  						<input type="text" size="5" value="${status.count}" name="" style="width:30px;" readonly="readonly"/>
				  					</td>
									<td align="center" hidden="hidden">
										<input id="id" type="text" class="textInput" value="${entity.id}" name="costList.id" maxlength="20" style="width: 80px;"/>
									</td>
				 					<td align="center">
				 						<input id="staff" type="text" value="${entity.name}" name="costList.staff" maxlength="50" style="width: 80px;"/>
				 					</td>
									<td align="center">
										<input id="travelCost" type="text" class="textInput" value="${entity.travelCost}" name="costList.travelCost" maxlength="20" style="width: 80px;"/>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.cityCost" onclick="addCostDetail(this,0)">${entity.cityCost==null?0:entity.cityCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.provinceCost" onclick="addCostDetail(this,1)">${entity.provinceCost==null?0:entity.provinceCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.hotelCost" onclick="addCostDetail(this,2)">${entity.hotelCost==null?0:entity.hotelCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.laborCost" onclick="addCostDetail(this,3)">${entity.laborCost==null?0:entity.laborCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.otherCost" onclick="addCostDetail(this,4)">${entity.otherCost==null?0:entity.otherCost}</a>
									</td>
									<td align="center">
										<a style="color: blue;" name="costList.amount">${entity.amount == null ? 0 : entity.amount}</a>
									</td>
									<td><a class="btnDel " onclick="removeCostRow(this)" href="javascript:void(0)">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	     	</div>
	     	
	     	<div class="formBar" style="width:98%">
		    	<ul>
		        	<li>
		          		<div class="buttonActive"> <div class="buttonContent"><button type="button" onclick="saveConferenceCostInfo()">保存</button></div></div>
		        	<li>
		        	<li>
		          		<div class="buttonActive"> <div class="buttonContent"><button type="button" onclick="closeTab()">返回</button></div></div>
		        	<li>
		      	</ul>
		    </div>
		</form>
	</div>
</body>
</html>