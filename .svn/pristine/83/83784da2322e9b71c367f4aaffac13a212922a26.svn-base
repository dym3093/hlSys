<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
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

	
    
<div class="pageFormContent" layoutH="56" style="overflow: hidden">
	<h1 class="press">${conference.conferenceNo}会议信息详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>会议信息</span></div>
	 <table>
	 <tr>
		<td style="text-align: right;">会议号：</td>
		<td style="text-align: left;">${conference.conferenceNo }</td>
		<td style="text-align: right;">会议日期：</td>
		<td style="text-align: left;">${fn:substring(conference.conferenceDate,0,19)}</td>
		
		</tr>
		
		<tr>
			<td style="text-align: right;">会议地址： </td>
		    <td>${conference.address}</td>
	    	<td style="text-align: right;">支公司：</td>
	    	<td>${conference.branchCompany }</td>
	    </tr>
	    
	    <tr>
		    <td style="text-align: right;">所属公司：</td>
		    <td>${conference.ownedCompany }</td>
		    <td style="text-align: right;">会议类型：</td>
		    <td><hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${conference.conferenceType }"/></td>
	    </tr>
		
		<tr>
		
		<tr>
			<td style="text-align: right;">项目编码：</td>
		    <td>${shipPro.projectCode}</td>
			<td style="text-align: right;">项目名称：</td>
		    <td>${shipPro.projectName}</td>
		</tr>
		<tr>
			<td style="text-align: right;">项目负责人：</td>
		    <td>${shipPro.projectOwner}</td>
		    <td>项目类型：</td>
			<td><hpin:id2nameDB id="${shipPro.projectType}" beanId="projectTypeDao"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">省份：</td>
		    <td><hpin:id2nameDB id="${conference.provice}" beanId="org.hpin.base.region.dao.RegionDao"/></td>
			<td style="text-align: right;">城市：</td>
		    <td><hpin:id2nameDB id="${conference.city}" beanId="org.hpin.base.region.dao.RegionDao"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">会议状态：</td>
		    <td>${conference.isDeleted == 0?"正常":"取消"}</td>
		    <c:if test="${conference.isDeleted == 2}">
			<td style="text-align: right;">取消原因：</td>
		    <td>${conference.cancelReason}</td>
		    </c:if>
		</tr>
	      <input name="customer.conferenceNo" class="conferenceNo" type="hidden"  value="${conference.conferenceNo }" />
	      <input name="id" type="hidden"  value="${conference.id }" />
	     </table> 

</div>
