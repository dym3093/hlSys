<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript">

function getCostDetail(obj,belong){
	var name = encodeURI($.trim($(obj).parents("tr").children().eq(2).children().val()));
	var conferenceId = $("#conferenceId",navTab.getCurrentPanel()).val();
	var conferenceNo = $("#conferenceNo",navTab.getCurrentPanel()).val();
	var costId = $.trim($(obj).parents("tr").children().eq(1).children().val());
	var travelCost = $.trim($(obj).parents("tr").children().eq(3).children().val());
	var instructorCost = $.trim($(obj).parents("tr").children().eq(4).children().val());
	$.pdialog.open("${path}/venueStaffSett/conferCostMan!getCostDetail.action?name="+name+"&conferenceId="+conferenceId+"&conferenceNo="+conferenceNo+"&belong="+belong+"&travelCost="+travelCost+"&instructorCost="+instructorCost+"&costId="+costId,
			"getCostDetail", "费用明细",{width:1000,height:500,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
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
			  					<input id="proType" type="text" name="proCode" value="<hpin:id2nameDB id="${shipPro.projectType}" beanId="projectTypeDao"/>" readonly="readonly">
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
		          	<table id="addConference" class="list " width="100%"><!-- addButton="新增人员费用" --> 
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
							</tr>
		        		</thead>
		        		
		        		<tbody id="newPersonInfo">
	            			<c:forEach items="${costList}" var="entity" varStatus="status">
								<tr >
				  					<td align="center">
				  						<input type="text" size="5" value="${status.count}" name="" style="width:30px;" readonly="readonly"/>
				  					</td>
									<td align="center" hidden="hidden">
										<input id="id" type='text' class="textInput" value='${entity.id}' name='costList.id' maxlength='20' style='width: 80px;' readonly="readonly"/>
									</td>
				 					<td align="center">
				 						<input id="staff" type='text' value='${entity.staff }' name='costList.staff' maxlength='50' style='width: 80px;' readonly="readonly"/>
				 					</td>
									<td align="center">
										<input id="travelCost" type='text' class="textInput" value='${entity.travelCost==null?0:entity.travelCost }' name='costList.travelCost' maxlength='20' style='width: 80px;' readonly="readonly"/>
									</td>
									<td align="center">
										<input id="instructorCost" type='text' class="textInput" value='${entity.instructorCost==null?0:entity.instructorCost}' name='costList.instructorCost' maxlength='20' style='width: 80px;' readonly="readonly"/>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" onclick="getCostDetail(this,0)">${entity.cityTrafficCost==null?0:entity.cityTrafficCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" onclick="getCostDetail(this,1)">${entity.provinceTrafficCost==null?0:entity.provinceTrafficCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" onclick="getCostDetail(this,2)">${entity.hotelCost==null?0:entity.hotelCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" onclick="getCostDetail(this,3)">${entity.sampleCost==null?0:entity.sampleCost}</a>
									</td>
									<td align="center">
										<a style="cursor: pointer;color: blue;" onclick="getCostDetail(this,4)">${entity.otherCost==null?0:entity.otherCost}</a>
									</td>
									<td align="center">
										<a style="color: blue;" name="costList.amount">${entity.amount==null?0:entity.amount}</a>
									</td>
									<td align="center" hidden="hidden">${entity.amount==null?0:entity.amount}</td>
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