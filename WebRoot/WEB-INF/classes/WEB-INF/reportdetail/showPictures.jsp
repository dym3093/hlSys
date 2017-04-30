<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>

<style type="text/css">
	.imgPosition{
		display:block;
		width: 500px;
		margin:0 auto;
		height:500px;
		background-color: #CCC;
	}
</style>
<script type="text/javascript" language="javascript">

	
</script>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpReportdetailImgTask!showPictures.action')
		{this.action = '${path}/reportdetail/erpReportdetailImgTask!showPictures.action' ;} ;
		return dialogSearch(this);" action="${path}/reportdetail/erpReportdetailImgTask!showPictures.action" method="post" rel="pagerForm">
		<input type="hidden" name="taskId" value="${taskId}"/>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>	
	<div style="overflow: auto;">
		<div class="imgPosition"><!-- style="height:500px; overflow: auto;" -->
			<c:forEach items="${page.results}" var="imgInfo" varStatus="status">
				<img alt="哎呦, 报告加载出错了..." src="${fn:replace(imgInfo.imgPath, '/home/ymdata', 'http://img.healthlink.cn:8099')}"
					 style="width: 500px; height: 600px;">
				
			</c:forEach>
		</div>
	
	</div>
</div> 