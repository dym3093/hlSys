<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

</script>

<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<div>&nbsp;</div>
	<h1 class="press"><span style="color:#3399FF;font-size:15px;font-family:'宋体'">${printTask.printTaskNo}打印任务</span></h1>
	<div class="divider"></div>
	<div class="tip"><span>打印任务信息</span></div>
	<table>
		<tr>
		<td>
			<label>打印公司：</label>
			${erpPrintTask.printCompany}
		</td>
		<td>
			<label>提交打印时间：</label>
			${erpPrintTask.createTime}
		</td>
		<td>
			<label>打印数量：</label>
				${erpPrintTask.batchNum}
		</td>
		</tr>
		
		<tr>
		<td>
			<label>返回时间：</label>
				${erpPrintTask.backTime}
		</td>
		<td>
			<label>打印状态：</label>
				<c:if test="${erpPrintTask.printState==0}">未打印</c:if>
				<c:if test="${erpPrintTask.printState==1}">已打印</c:if>
				<c:if test="${erpPrintTask.printState==2}">打印中</c:if>
		</td>
		<td>
			<label>下载：</label>
		</td>
		<td>
			<div class="buttonActive"><div class="buttonContent"><button >下载</button></div></div>
		</td>
		</tr>
		
		<tr><td></td></tr>
		
		<tr>
			<td>
				<div class="buttonActive"><div class="buttonContent"><button >打印任务明细</button></div></div>
			</td>
		</tr>
		</table>
	
	<!-- <div class="panelBar">
		<ul class="toolBar">
			<a href="javascript:void(0)" onclick="downloadAllExcel('')">下载</a>
		</ul> 
		<jsp:include page="/common/pagination.jsp" />
	</div> -->
	
	
	
	<div id="printConfirm" style="display:${erpPrintTask.printState==1?'block':'none'};">
		<div class="divider"></div>
		<div class="tip"><span>打印确认</span></div>
		<form id="pagerFindForm" onsubmit="if(this.action != ''){this.action = '' ;}" action="" method="post"  rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
				<td>
					<label>完成时间：</label>
					<input id="conferenceDate" style="width: 110px;" name="conference.conferenceDate" datefmt="yyyy-MM-dd"  value="${now }" onpropertychange="myFunction()"  type="text" class="date required" readonly="readonly"/>
				    <a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td>
					<label>快递时间：</label>
					<input id="conferenceDate" style="width: 110px;" name="conference.conferenceDate" datefmt="yyyy-MM-dd"  value="${now }" onpropertychange="myFunction()"  type="text" class="date required" readonly="readonly"/>
				    <a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				</tr>
				
				<tr>
					<td>
						<label>快递单号：</label>
						<input type="text" name="" value="" readonly="readonly"/>
						<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a>
						  	<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td></td>
					<td>
						<div class="buttonActive"><div class="buttonContent"><button type="submit">确认打印</button></div></div>
					</td>
				</tr>
			</table>
		</form>
	</div>	
	
	
	<div id="printConfirm" style="display:${erpPrintTask.printState==1?'block':'none'};">	
		<div class="divider"></div>
		<div class="tip"><span>快递信息</span></div>
			<table class="pageFormContent">
			<tr>
				<td>
					<label>快递签收人：</label>
					<span></span>
				</td>
			</tr>
			<tr>
				<td>
					<label>收件地址：</label>
					<span></span>
				</td>
			</tr>
			</table>
	</div>	
		
	</div> 
</div>
