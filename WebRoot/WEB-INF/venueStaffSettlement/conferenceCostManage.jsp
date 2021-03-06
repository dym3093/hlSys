<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$(document).ready(function(){
		py_ready(2); 
	});
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
});

$(document).ready(function(){		//默认选中下拉
	var select= $('input[name="selectCost"]').val();
	if(select=="0"){
		$('select[name="filter_and_produceCost_LT_D"]').find("option[value='0']").attr("selected",true);

	}else if(select=="1"){
		$('select[name="filter_and_produceCost_LT_D"]').find("option[value='1']").attr("selected",true);
	}
});

function toAddConferenceStaffCost(){	//会议费用录入
	var checkbox = $("input:checkbox[name='ids']:checked",navTab.getCurrentPanel());
	if($(checkbox).length!=1){
		alertMsg.error("请选择一条要录入的会议");
		return;
	}
	var conferenceId = $(checkbox).val();
	navTab.openTab("editSettlement", "${path}/venueStaffSett/conferCostMan!addConferenceCostInfo.action", 
			{ title:"人员费用录入", fresh:true, data:{"conferenceId":conferenceId} });
}

function showUpdloadFile(obj){
	var title = "费用导入";
	var uploadType = 0;
	if($(obj).text() == "单项费用导入"){
		title = "单项费用导入";
		uploadType = 1;
	}
	$.pdialog.open("${path}/venueStaffSett/conferCostMan!showUploadFile.action?uploadType="+uploadType, "showUploadFile", title, 
			{width:500,height:200,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}

function listExportExceptionInfo(){
	navTab.openTab("listExportExceptionInfo", "${path}/venueStaffSett/conferCostMan!listExportExceptionInfo.action", 
			{ title:"异常数据查看", fresh:true, data:{} });
}

function export2Excel(){
//	dataJson += "{\"id\":"+id+","+"\"staff\":"+staff+","+"\"travelCost\":"+travelCost+","+"\"instructorCost\":"+instructorCost+"},";
	var input = $("#pagerFindForm",navTab.getCurrentPanel()).serialize();
	var jsonArray = input.split("&");
	var jsonData = "[";
	var data = "";
	for(var i=0;i<jsonArray.length;i++){
		if(jsonArray[i].indexOf("confQuery")!=-1){
			data = jsonArray[i].split("="); 
			//去除空格变加号的问题
			jsonData += "{\""+data[0]+"\":\""+data[1].replace(/\+/g, "")+"\"},";
		}
	}
	py_show();
	jsonData = jsonData.substring(0,jsonData.length -1);  
	jsonData +="]";
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		data:{"jsonData":jsonData},
		url: "${path}/venueStaffSett/conferCostMan!export2Excel.action",
		success: function(data){
			window.open(eval("("+data+")").path,"_blank");
			py_hide();
		},
		error :function(){
			py_hide();
			alert("服务发生异常，请稍后再试！");
			py_hide();
			return;
		}
	});
	
}
	function listConferenceCostDetailExc(){	//单项费用异常查看
		navTab.openTab("listConferenceCostDetailExc", "${path}/venueStaffSett/conferCostMan!listConferenceCostDetailExc.action", 
				{ title:"单项异常查看", fresh:true, data:{} });
	}
	
	function exportCostDetail(){//导出详细费用
		var input = $("#pagerFindForm",navTab.getCurrentPanel()).serialize();
		var jsonArray = input.split("&");
		var jsonData = "[";
		var data = "";
		for(var i=0;i<jsonArray.length;i++){
			if(jsonArray[i].indexOf("confQuery")!=-1){
				data = jsonArray[i].split("="); 
				//去除空格变加号的问题
				jsonData += "{\""+data[0]+"\":\""+data[1].replace(/\+/g, "")+"\"},";
			}
		}
		py_show();
		jsonData = jsonData.substring(0,jsonData.length -1);  
		jsonData +="]";
		
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			data:{"jsonData":jsonData},
			url: "${path}/venueStaffSett/conferCostMan!exportCostDetail.action",
			success: function(data){
				window.open(eval("("+data+")").path,"_blank");
				py_hide();
			},
			error :function(){
				py_hide();
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		
	}
	
</script>
<div id="mc" class="py_theMb">
	<div class="py_bakpic"><!--图片--></div>
</div>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/venueStaffSett/conferCostMan!listConferCostMan.action'){this.action = '${path}/venueStaffSett/conferCostMan!listConferCostMan.action' ;} ;return navTabSearch(this);" action="${path}/venueStaffSett/conferCostMan!listConferCostMan.action" method="post"  rel="pagerForm" >
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>会议号：</label> 
					<input id="conferecNo" type="text" name="confQuery.conferenceNo" value="${confQuery.conferenceNo}"/>
				</td>
				<td>
					<label>会议类型：</label> 
					<select id="conferenceType" name="confQuery.conferenceType"  rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10109" relUrl="${path}/hpin/sysDictType!treeRegion.action">
		 		 		<option value="${confQuery.conferenceType}"></option>
		 			</select>
				</td>
				<td>
					<label>支公司：</label> 
					<input id="branchCompany" type="text" name="confQuery.branchCompany" bringbackname="customerRelationShip.branchCommany" value="${confQuery.branchCompany}" readonly="readonly"/>
					<!-- <input type="hidden" id="id" name="conference.branchCompanyId" bringbackname="customerRelationShip.id" value=""/> -->
				  	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a>
				  	<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>
				</td>
				
			</tr>
			
			<tr>
				<td>
					<label>项目编码：</label> 
					<input id="projectCode" type="text" name="confQuery.projectCode" bringbackname="customerRelationShip.proCode" value="${confQuery.projectCode}"/>
				</td>
				<td>
					<label>项目负责人：</label> 
					<input id="proUser" type="text" name="confQuery.projectOwner" bringbackname="customerRelationShip.proUser" value="${confQuery.projectOwner}"/>
				</td>
				<td>
					<label>省：</label> 
					 <select id="province" name="confQuery.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		 			    <option value="${confQuery.province}"></option> 
		 			 </select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>起始日期：</label> 
					<input type="text" name="confQuery.startDate"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${confQuery.startDate}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td>
					<label>结束日期：</label> 
					<input type="text" name="confQuery.endDate" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${confQuery.endDate}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>市：</label>
					<select id="city" name="confQuery.city" rel="iselect">
						    <option value="${confQuery.city}"></option> 
         			</select>
         		</td>
			</tr>
			<tr>
				<td>
					<label>项目类型：</label>
					<select id="projectType" name="confQuery.projectType" style="width: 170px;" rel="iselect">
						<option value="">请选择</option>
						<c:forEach items="${proTypes }" var="item">
							<option value="${item.id }" ${item.id == confQuery.projectType ? "selected":"" }>${item.projectTypeName }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>是否产生费用：</label>
					<select id="isExistCost" name="confQuery.isExistCost" style="width: 170px;" rel="iselect">
						<option value="">请选择</option>
						<option value="0" ${isExistCost=="0"?"selected='selected'":""}>无</option>
						<option value="1" ${isExistCost=="1"?"selected='selected'":""}>有</option>
					</select>
				</td>
				<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
				<td></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<web:security tag="VFM_DetailImport">
				<li style="margin-left:5px;"><a class="add" onclick="toAddConferenceStaffCost()" style="cursor: pointer;"><span>明细录入</span></a></li>
			</web:security>
			<web:security tag="VFM_FeeImport">
				<li style="margin-left:5px;"><a class="icon" onclick="showUpdloadFile(this)" style="cursor: pointer;"><span>费用导入</span></a></li> 
			</web:security>
			<web:security tag="VFM_ExceptionSearch">
				<li style="margin-left:5px;"><a class="edit" onclick="listExportExceptionInfo()" style="cursor: pointer;"><span>异常数据查看</span></a></li>
			</web:security>
			<web:security tag="VFM_ExportDetailInfo">
				<li style="margin-left:5px;"><a class="edit" onclick="export2Excel()" style="cursor: pointer;"><span>导出会议费用</span></a></li>
			</web:security>		
			<li style="margin-left:5px;"><a class="edit" onclick="showUpdloadFile(this)" style="cursor: pointer;"><span>单项费用导入</span></a></li>
			<li style="margin-left:5px;"><a class="icon" onclick="listConferenceCostDetailExc()" style="cursor: pointer;"><span>单项异常查看</span></a></li>
			<li style="margin-left:5px;"><a class="edit" onclick="exportCostDetail()" style="cursor: pointer;"><span>单项费用导出</span></a></li>	
		</ul>
		<jsp:include page="/common/pagination.jsp"/>
	</div>
	<table class="list" width="100%" layoutH="180" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "conferenceNo" columnChName = "会议号" >会议号</th>
				<th  export= "true" columnEnName = "conferenceDate" columnChName = "会议日期" >会议日期</th>
				<!-- <th  export= "true" columnEnName = "provice" columnChName = "省" >省</th> -->
				<th  export= "true" columnEnName = "provice" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th  export = "true" columnEnName = "branchCompanyId" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "ownedCompanyId" id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"  columnChName = "所属公司" >所属公司</th>
				<th  export = "true" columnEnName = "address" columnChName = "地点" >地点</th>
				<th  export = "true" columnEnName = "settNumbers" columnChName = "结算人数" >结算人数</th>
				<th  export = "true" columnEnName = "produceCost" columnChName = "产生费用" >产生费用</th>
				<th  export = "true" columnEnName = "conferenceType" columnChName = "会议类型" >会议类型</th>
				<th>项目类型</th>
				<th  export = "false" columnEnName = "" columnChName = "" hidden="hidden">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpConference" varStatus="status">
					<tr target="sid_user" rel="${erpConference.id }">
						<td align="center">
								<input type="checkbox" name="ids" value="${erpConference.id}">
							${ status.count }
						</td>
						<td align="center">
							<a title="会议费用人员信息" target="navTab" width="1000"  href="${path}/venueStaffSett/conferCostMan!getConferenceCostInfo.action?conferenceId=${erpConference.id}">${erpConference.conferenceNo}</a>
						</td>
						<td align="center"><fmt:formatDate value="${erpConference.conferenceDate }" pattern="yyyy-MM-dd"/></td>
						<td align="center" width="60" nowrap="true">${erpConference.provice}</td>
						<td align="center" width="60" nowrap="true">${erpConference.city}</td>
						<td align="center" width="200" nowrap="true">${erpConference.branchCompany}</td>
 						<td align="center" width="160" nowrap="true">${erpConference.ownedCompany}</td>
						<td align="center">${erpConference.address}</td>
						<td align="center">
							<a title="会议费用人员信息" target="navTab" width="1000"  href="${path}/venueStaffSett/conferCostMan!getConferenceCostInfo.action?conferenceId=${erpConference.id}">${erpConference.settNumbers}</a>
						</td>
						<td align="center">
							<a title="会议费用人员信息" target="navTab" width="1000"  href="${path}/venueStaffSett/conferCostMan!getConferenceCostInfo.action?conferenceId=${erpConference.id}">${erpConference.produceCost}</a>
						</td>
						<td align="center">${erpConference.conferenceType}</td>
						<td align="center">${erpConference.projectTypeName}</td>
						<td align="center" hidden="hidden">
						<div class="panelBar">
							<ul class="toolBar">
								<li><a class="add" href="${path}/venueStaffSett/conferCostMan!toAddConferenceStaffCost.action?id=${erpConference.id}&conferenceNo=${erpConference.conferenceNo}"
										target="navTab" rel="add"><span>人员费用录入</span></a></li>
							</ul>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 