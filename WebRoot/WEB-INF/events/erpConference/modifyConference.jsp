<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
	$("#customerRelationPro").click(function() {
		//清空数据;
		clearPro();
	});
	$("#isDeleted").change(function (){
		var val = $(this).val();
		changeClReason(val);
	});
	changeClReason("${conference.isDeleted}");
});

	function submitForm(){
		var ed=document.getElementById("conferenceDate").value;
		var year=ed.substring(0,4);
		var month=ed.substring(5,7);
		var day=ed.substring(8,10);
		var hour=ed.substring(11,13);
		var min=ed.substring(14,16);
		
		var conferenceDateStr=document.getElementById("conferenceDateStr").value;
		var year1=conferenceDateStr.substring(0,4);
		var month1=conferenceDateStr.substring(5,7);
		var day1=conferenceDateStr.substring(8,10);
		var hour1=conferenceDateStr.substring(11,13);
		var min1=conferenceDateStr.substring(14,16);
		/* alert(year==year1);
		alert(month==month1);
		alert(day==day1);
		alert(hour==hour1 );
		alert(min==min1); */
		
		if(year==year1&&month==month1&&day==day1&&hour==hour1&&min==min1){
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}else{
			if(confirm("修改会议日期后是否更改客户采样日期？")){
				document.getElementById("isUpdateCustDate").value="1";
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}else{
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}
		}
		/*		if(year==year1){
		$(".pageForm",navTab.getCurrentPanel()).submit();
	}else{alert(3);
		if(confirm("修改场次日期后是否更改客户采样日期？")){
			document.getElementById("isUpdateCustDate").value="1";
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}else{
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}
	}
	/*	if(!(year==year1 && month==month1 && day==day1 && day==day1 && hour==hour1 && min==min1)){
			if(confirm("修改场次日期后是否更改客户采样日期？")){
				document.getElementById("isUpdateCustDate").value="1";
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}else{
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}
		}else{
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}*/
	}
	
	function closeNavTab(){
		navTab.closeCurrentTab();
	}
	
	
	/*
	 * 回调函数;
	 */
	function pro_callback() {
		
		var branchCompanyId = $("#branchCompanyId").val();
		$("#customerRelshipPro").attr("href", "${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+branchCompanyId);
		//清空数据;
		clearPro();
		return ;
	}
	 
	function clearPro() {
		$("#customerRelationShipProId").val("");
		$("#projectCode").val("");
		$("#projectName").val("");
		$("#projectOwner").val("");
		$("#ymSalesman").val("");
	}
	function changeClReason(val){
		if(val=="2"){
			$("#td_cancelReason").removeProp("hidden");
		}else{
			$("#td_cancelReason").prop("hidden","hidden");
		}
	}
</script>
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>

<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpConference!modifyConference.action" method="post">
		<input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
	    <input type="hidden" id="isUpdateCustDate" name="isUpdateCustDate" value="0"/>					  
	   	<div class="pageFormContent" layoutH="56" style="overflow: hidden;" >
	    	<h1 class="press">会议信息管理</h1>
	        <input type="hidden" name ="conference.id" value="${conference.id}"/>
	        <div class="divider"></div>
	        <input id="conferenceDateStr" type="hidden" name="conferenceDateStr" value="${conferenceDateStr}" />
	        <div class="tip"><span>会议信息变更</span></div>
	        
	        <table>
		        <tr>
			        <td>
						<label>会议日期：</label>
						<input id="conferenceDate" style="width: 170px;" name="conference.conferenceDate" datefmt="yyyy-MM-dd HH:mm:ss"  value="${fn:substring(conference.conferenceDate,0,14)}00:00" type="text" class="date required" />
			            <a class="inputDateButton" href="javascript:;">选择</a>
					</td>
			       	<td>
			          <label>会议号：</label>
			          <input name="conference.conferenceNo" id="conferenceNo" type="text" class="required" readonly="readonly"  value="${conference.conferenceNo }" />
			        </td>
			        <td>
			        	<label>会议地址：</label>
			          	<input name="conference.address"  type="text" value="${conference.address}" />
			        </td>
		        </tr>
		        
		        <tr>
		        	<td>
		          		<label>公司名称：</label>
				        <%-- <input name="conference.branchCompany" type="text" value="${company }"  onkeydown="if(conference.keyCode==13) return false;"/> --%>
				        <input type="hidden" id="id" name="conference.branchCompanyId" bringbackname="customerRelationShip.id" value="${conference.branchCompanyId}"/>
						<input type="text" name="conference.branchCompany" bringbackname="customerRelationShip.branchCommany" readonly="readonly" value="<hpin:id2nameDB id="${conference.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/>"/>
						<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a>
						<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>
		        	</td>
		        	<td>
						<label>所属公司：</label>
			          	<input value="<hpin:id2nameDB id="${conference.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao"/>"
			          	name="conference.ownedCompany" 
			          	bringbackname="customerRelationShip.customerNameSimple" type="text" 
			          	readonly="readonly"  onkeydown="if(conference.keyCode==13) return false;"/>
			          	
						<input type="hidden" name="conference.ownedCompanyId"  
						bringbackname="customerRelationShip.ownedCompany" value="${conference.ownedCompanyId}"/>
					</td>
		        	<td>
		          		<label>会议类型：</label>
						<select id="conferenceType" name="conference.conferenceType" value="${conference.conferenceType}" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10109" relUrl="${path}/hpin/sysDictType!treeRegion.action">
				 			<option value="${ conference.conferenceType}"></option>
				 		</select>
		        	</td>
				</tr>
				
				<tr>
					<td>
						<label>项目编码：</label>
		        		<input id="customerRelationShipProId" name="conference.customerRelationShipProId" bringbackname="customerRelationShipPro.id" type="hidden" value="${conference.customerRelationShipProId }"  />
		        		<input id="projectCode" name="conference.proCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${shipPro.projectCode}"/>
		        		<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId=${conference.branchCompanyId }" lookupGroup="customerRelationShipPro">查找带回</a>
						<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
						
					</td>
					<td>
						<label>项目名称：</label>
						<input id="projectName" type="text" name="conference.proBelong" bringbackname="customerRelationShip.proBelong" value="${shipPro.projectName}" readonly="readonly"/>
					</td>
					<td>
						<label>项目负责人：</label>
						<input id="projectOwner" type="text" name="conference.proUser" bringbackname="customerRelationShip.proUser" value="${shipPro.projectOwner}" readonly="readonly"/>
					</td>
		        </tr>
		        
		        <tr>
		        	<td>
		        		<label>项目类型：</label>
		        		<input id="protype_input_id" class="textInput" value="<hpin:id2nameDB id="${shipPro.projectType}" beanId="projectTypeDao"/>" bringbackname="customerRelationShipPro.projectTypeName" readonly="readonly"/>
	        		</td>
		        
		        	<td>
						<label>省份：</label>
						<input type="hidden" name="conference.provice" bringbackname="customerRelationShip.provinceNum" value="${conference.provice}"/>
						<input type="text" bringbackname="customerRelationShip.province" value="<hpin:id2nameDB id="${conference.provice}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly"  >
					</td>
					<td>
						<label>城市：</label>
						<input type="hidden" name="conference.city" bringbackname="customerRelationShip.cityNum" value="${conference.city}"/>
						<input type="text" bringbackname="customerRelationShip.city" value="<hpin:id2nameDB id="${conference.city}" beanId="org.hpin.base.region.dao.RegionDao"/>"readonly="readonly"  >
					</td>
					<td></td>
				</tr>
				<tr>
		        	<td>
		        		<label>会议状态：</label>
		        		<select id="isDeleted" name="conference.isDeleted" style="margin-top: 5px; left: 0px; height: 21px; padding: 0px; margin-left: 5px; width: 193px;" >
				 			<option value="0" <c:if test="${conference.isDeleted == 0}">selected</c:if> >正常</option>
				 			<option value="2" <c:if test="${conference.isDeleted == 2}">selected</c:if> >取消</option>
				 		</select>
	        		</td>
		        
		        	<td id="td_cancelReason">
						<label>取消原因：</label>
						<input type="text" name="conference.cancelReason"  value="${conference.cancelReason}"/>
					</td>
					<td>
					</td>
					<td></td>
				</tr>
			</table>
			
			<div class="divider"></div>
	      	</div>
	    	<div class="formBar" style="width:98%">
				<ul>
		        	<li>
		          		<div class="buttonActive"><div class="buttonContent"><button type="button"  onclick="submitForm()">更新</button></div></div>
		        	</li>
		        	<!-- <li>
		        		<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
		        	</li> -->
		        	<li>
		          		<div class="button"><div class="buttonContent"><button type="button" onclick="closeNavTab()">返回</button></div></div>
		        	</li>
				</ul>
	    </div>
	</form>
</div>
