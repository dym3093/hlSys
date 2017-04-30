<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
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
<script src="${path}/dwz/js/checkCode.js" type="text/javascript"></script>
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
});

function myFunction() {
	 $.ajax({
		 type: 'post', //可选get
		 url: '${path}/events/erpConference!createNo.action', //这里是接收数据的程序
		 data: 'data=' + $("#conferenceDate").val(), //传给后台的数据，多个参数用&连接
		 dataType: 'text', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
			 success: function(data) {
			 //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
			 //document.getElementById("eventsNo").val = msg;
			 $("#conferenceNo").val(data);
		 },
			 error: function() {
				 
			 }
	 	})
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
</script>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpConference!addConfernece.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />					  
    <div class="pageFormContent" layoutH="50" style="overflow: hidden;">
        <h1 class="press">会议信息管理</h1>
        <div class="divider"></div>
        <div class="tip"><span>会议信息录入</span></div>
		<table>
			<input name="conference.nowHeadcount" readonly="readonly" type="hidden" value="0" />
        	<tr>
				<td>
					<label>会议日期：</label>
					<input id="conferenceDate" style="width: 110px;" name="conference.conferenceDate" datefmt="yyyy-MM-dd"  value="${now}" onpropertychange="myFunction()"  type="text" class="date required" />
		            <a class="inputDateButton" href="javascript:;">选择</a>
		            <select name ="conference.hour" style="margin:6px;">
		            	<option value="9">上午场</option>
		            	<option value="14">下午场</option>
		            	<option value="20">晚场</option>
		            </select>
				</td>
				<td>
					<label>会议号：</label>
					<input name="conference.conferenceNo" id="conferenceNo" type="text" class="required"  readonly="readonly"  value="${conferenceNo}"  onkeydown="if(conference.keyCode==13) return false;"/>
				</td>
				<td>
	          		<label>会议地址：</label>
	          		<input tabindex="1" name="conference.address"  type="text" value="${conference.address}" onkeydown="if(conference.keyCode==13) return false;"/>
	        	</td>
			</tr>
         
			<tr>
	        	<td>
					<label>公司名称：</label>
		          	<input type="hidden" id="branchCompanyId" name="conference.branchCompanyId" bringbackname="customerRelationShip.id" value=""/>
				  	<input type="text" name="conference.branchCompany" bringbackname="customerRelationShip.branchCommany" readonly="readonly"/>
				  	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" callback="pro_callback" lookupGroup="customerRelationShip">查找带回</a>
				  	<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>
	        	</td>
	        	<td>
	         		<label>所属公司：</label>
		          	<input id="customerNameSimple" name="conference.ownedCompany" bringbackname="customerRelationShip.customerNameSimple" type="text" readonly="readonly"  onkeydown="if(conference.keyCode==13) return false;"/>
				  	<input type="hidden" name="conference.ownedCompanyId"  bringbackname="customerRelationShip.ownedCompany" value=""/>
				</td>
	        	<td>
	         		<label>会议类型：</label>
					<%-- <input type="text" name="conference.conferenceType" value="<hpin:id2nameDB id="${conferenceType}" beanId="org.hpin.base.region.dao.RegionDao"/>"  onkeydown="if(conference.keyCode==13) return false;"> --%>
					<select id="conferenceType" name="conference.conferenceType" value="${ conference.conferenceType}" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10109" relUrl="${path}/hpin/sysDictType!treeRegion.action">
			 			<option value="${ conference.conferenceType}"></option>
			 		</select>
	        	</td>
	        </tr>
        
			<tr>
				<td>
				
					<label>项目编码：</label>
	        		<input id="customerRelationShipProId" name="conference.customerRelationShipProId" bringbackname="customerRelationShipPro.id" type="hidden" value=""  />
	        		<input id="projectCode" name="conference.proCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value=""/>
	        		<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action" lookupGroup="customerRelationShipPro">查找带回</a>
					<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
				</td>
				<td>
					<label>项目名称：</label>
					<input id="projectName" type="text" name="conference.proBelong" bringbackname="customerRelationShipPro.projectName" value="" readonly="readonly"/>
				</td>
				<td>
					<label>项目负责人：</label>
					<input id="projectOwner" type="text" name="conference.proUser" bringbackname="customerRelationShipPro.projectOwner" value="" readonly="readonly"/>
				</td>
			</tr>
		
	        <tr>
	        	<td>
	        		<label>项目类型：</label>
	        		<input id="protype_input_id" class="textInput" value="" bringbackname="customerRelationShipPro.projectTypeName" readonly="readonly"/>
	        	</td>
	        	<td>
					<label>省份：</label>
					<input type="hidden" bringbackname="customerRelationShip.provinceNum" name="conference.provice" value=""/>
					<input type="text" bringbackname="customerRelationShip.province" value="" readonly="readonly"  >
				</td>
				<td>
					<label>城市：</label>
					<input type="hidden" name="conference.city" bringbackname="customerRelationShip.cityNum" value=""/>
					<input type="text" value="" bringbackname="customerRelationShip.city" readonly="readonly"  >
				</td>
			</tr>
		</table>
	<div class="divider"></div>
	</div>
	
    <div class="formBar" style="width:98%">
		<ul>
	        <li>
	        	<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
	        </li>
        	<li>
          		<div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
        	</li>
		</ul>
    </div>
</form>
</div>
