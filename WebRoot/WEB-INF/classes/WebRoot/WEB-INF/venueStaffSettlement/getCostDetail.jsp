<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">

function closeDialog(){
	$.pdialog.closeCurrent();
}
</script>
</head>
<body>
	<div class="pageContent">
		<div class="pageFormContent" style="padding:0;" layoutH="56">
	    	<div class="divider"> <!-- class="divider" -->
	    		<input id="costDetailId" type="text" name="conferenceId" value="${conferenceId}">
	    		<input id="conferenceNo" type="text" name="conferenceNo" value="${conferenceNo}">
	    		<input id="name" type="text" name="name" value="${name}">
	    		<input id="belong" type="text" name="belong" value="${belong}">
	    		<input id="travelCost" type="text" name="travelCost" value="${travelCost}">
	    		<input id="instructorCost" type="text" name="instructorCost" value="${instructorCost}">
	    		<input id="costId" type="text" name="costId" value="${costId}">
	    	</div>
	      	<div style="overflow:auto;" layoutH="170">
	        	<div  style="overflow:scroll;height: 360px;">
	           		<table class="list" width="100%">
	            		<thead>
	              			<tr>
	                 			<th type="num" name="num" defaultVal="#index#" filedStyle="width: 30px">序号</th>
								<th type='text' name='costDetailList.id' filedStyle='width: 100px' hidden="hidden">id</th>
								<th type='text' name='costDetailList.days' filedStyle='width: 100px'>天数</th>
								<th type='text' name='costDetailList.flight' filedStyle='width: 100px'>航班/车次(其他类别可不填写)</th>
								<th type='text' name='costDetailList.descripe' filedStyle='width: 80px'>描述</th>
								<th type='text' name='costDetailList.cost' filedStyle='width: 80px'>金额</th>
								<th hidden="hidden" type='text' name='costDetailList.price' filedStyle='width: 80px'>金额</th>
	              			</tr>
	            		</thead>
	            		
		        		<tbody id="addDetailCost">
	            			<c:forEach items="${costDetail}" var="cost" varStatus="status">
								<tr >
	  								<td align="center">
	  									<input type="text" size="5" value="${status.count}" name="" style="width:30px;" readonly="readonly"/>
	  								</td>
	  								<td align="center" hidden="hidden">
	  									<input type="text" class="textInput" size="5" value="${cost.id}"  name='costDetailList.id' style="width:100px;" readonly="readonly"/>
	  								</td>
				 					<td align="center">
				 						<input type='text' class="textInput" value='${cost.days}' name='costDetailList.dyas' maxlength='50' style='width: 200px;' readonly="readonly"/>
				 					</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.flight}' name='costDetailList.flight' maxlength='50' style='width: 200px;' readonly="readonly"/>
									</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.descripe}' name='costDetailList.descripe' maxlength='20' style='width: 200px;' readonly="readonly"/>
									</td>
									<td align="center" >
										<input type='text' class="textInput" value='${cost.cost }' name='costDetailList.cost' maxlength='20' style='width: 200px;' readonly="readonly"/>
									</td>
									<td align="center" hidden="hidden">${cost.cost }</td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	      	</div>
		<div>
	</div>
	</div>
	</div>
</body>
</html>