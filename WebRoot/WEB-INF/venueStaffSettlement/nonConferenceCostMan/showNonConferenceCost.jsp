<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript">

	function showNonConferenceCostDetail(obj,belong){
		var nonConferenceId= $("#nonConferenceId",navTab.getCurrentPanel()).val();
		var costId = $.trim($(obj).parents("tr").children().eq(1).children().val());
		var name = $.trim($(obj).parents("tr").children().eq(2).children().val());
		var travelCost = $.trim($(obj).parents("tr").children().eq(3).children().val());
		var amount = $.trim($(obj).parents("tr").children().eq(9).children().text());
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
		
		$.pdialog.open("${path}/venueStaffSett/nonConference!showNonConferenceCostDetail.action?belong="+belong
				+"&travelCost="+travelCost+"&costId="+costId+"&name="+name+"&nonConferenceId="+nonConferenceId+"&amount="+amount,
				"showNonConferenceCostDetail", title,{width:1000,height:500,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<div>
		<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action'){this.action = '${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/nonConference!saveConferenceCostInfo.action" method="post"  rel="pagerForm" id="pagerFindForm">
			<div class="tip"><span>会议基本信息</span></div>
			<div class="pageHeader">
				<div class="searchBar">
					<div hidden="hidden"><input id="nonConferenceId" name="nonConferenceId" type="text" value="${nonConference.id}"></div>
					<table class="pageFormContent">
						<tr>
					        <td>
					        	<label>项目编码：</label>
					        	<input id="projectCode" name="projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${nonConference.projectCode}"/>
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
								onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
								readonly="readonly" value="${fn:substring(nonConference.startTime,0,10)}" class="required textInput"/>
							</td>
							<td><label>结束日期：</label> <input type="text"
								name="endTime" id="d2"
								onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
								readonly="readonly" value="${fn:substring(nonConference.endTime,0,10)}" class="required textInput"/>
							<td>
			  					<label>月份：</label>
			  					<input id="month" type="text" name="month" value="${nonConference.month}" readonly="readonly" class="required textInput">
			  				</td>
			  			</tr>
			  			
			  			<tr>
			  				<td>
			  					<label>OA流水号：</label>
			  					<input id="OASerial" type="text" name="OASerial" value="${nonConference.OASerial}" readonly="readonly" class="required">
			  				</td>
			  				<td colspan="2">
			  					<label>备注：</label>
			  					<input id="note" type="text" name="note" style="width: 513px;" value="${nonConference.note}" readonly="readonly" class="required">
			  				</td>
			  			</tr>
					</table>
				</div>
			</div>
			
			<div style="overflow:auto;" layoutH="170">
	        	<div class="tip"><span>人员费用信息</span></div>
	        	<div style="overflow-y:scroll;height: 300px;">
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
				 						<input id="staff" type="text" value="${entity.name}" name="costList.staff" maxlength="50" style="width: 80px;" readonly="readonly"/>
				 					</td>
									<td align="center">
										<input id="travelCost" type="text" class="textInput" value="${entity.travelCost}" name="costList.travelCost" maxlength="20" style="width: 80px;" readonly="readonly"/>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.cityCost" onclick="showNonConferenceCostDetail(this,0)">${entity.cityCost==null?0:entity.cityCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.provinceCost" onclick="showNonConferenceCostDetail(this,1)">${entity.provinceCost==null?0:entity.provinceCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.hotelCost" onclick="showNonConferenceCostDetail(this,2)">${entity.hotelCost==null?0:entity.hotelCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.laborCost" onclick="showNonConferenceCostDetail(this,3)">${entity.laborCost==null?0:entity.laborCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" name="costList.otherCost" onclick="showNonConferenceCostDetail(this,4)">${entity.otherCost==null?0:entity.otherCost}</a>
									</td>
									<td align="center">
										<a style="color: blue;" name="costList.amount">${entity.amount==null?0:entity.amount}</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	     	</div>
		</form>
	</div>
</body>
</html>