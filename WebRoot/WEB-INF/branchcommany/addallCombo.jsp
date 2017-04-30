<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#_submit").click(function(){
	    var count = 0; 
		var elementsTree = $("#elementsTree div:[class$='ckbox checked'][input:[type='checkbox'][name='elementName']");
		var groupTree = $("#groupTree div:[class$='ckbox checked'][input:[type='checkbox'][name='groupName']");
		elementsTree.each(function(){
			$(this).attr("checked","checked");
			count++;
		});
		groupTree.each(function(){
			$(this).attr("checked","checked");
			count++;
		});
		if(count==0){
			alert("请选择服务元素或服务组合");
			return false;
		}
	});
});
</script>
<html>
  <body>
  <form rel = "pagerForm" action="${ path }/resource/productServe!saveProductServe.action" class="pageForm required-validate" onsubmit="return validateCallback(this, tabAjaxDone);" method="post">
	  	<input type="hidden" name="productId" value="${ productId }"/>
		<div id="elementsTree" layoutH="40" style="float:left; overflow:auto; width:49%; height:400px; border:solid 1px #CCC; line-height:18px; background:#fff;">
		  <div class="tip" style="width: auto;"><span>服务元素</span></div>
		  <c:forEach items="${elementList}" var="element1" varStatus="status">
		  	<ul class="tree treeFolder treeCheck expand">
			<%-- <c:if test="${element1.serviceLevel==1}"> --%>
				<li rel="${element1.id}"><a href="javascript:"><span>${element1.comboName}</span></a><!-- (${element1.productName}) -->
			      <%-- <ul>
			        <c:forEach items="${elementList}" var="element2" varStatus="status">
					  <c:if test="${element2.serviceLevel==2 && element2.parentId==element1.id}">
						<li><a href="javascript:" tname="elementName" tvalue="${element2.id}" rel="${ element2.id }"><span>${element2.serviceName}(${element2.serviceNum})</span></a></li>
					  </c:if>
				    </c:forEach>
			      </ul> --%>
				</li>
			  <%-- </c:if> --%>
	  		</ul>
		   </c:forEach>
		</div> 
		<%-- <div id="groupTree" layoutH="40" style="float:left; overflow:auto; width:49%; height:400px; border:solid 1px #CCC; line-height:18px; background:#fff;">
		  <div class="tip" style="width: auto;"><span> 服务组合</span></div>
		  <c:forEach items="${groupList}" var="group" varStatus="status">
		  	<ul id="elementsTree" class="tree treeFolder treeCheck expand"  oncheck="groupElement">
				<li rel="${group.id}"><a href="javascript:" ><span>${group.groupName}(${group.groupNum})</span></a>
			      <ul>
			        <c:forEach items="${group.relationSet}" var="relation" varStatus="status">
				        <c:if test="${relation.serviceElementsLevel=='2'}">
							<li><a href="javascript:" tname="groupName" tvalue="${group.id},${relation.element.id}" rel="${relation.id}"><span>${relation.element.serviceName}(${relation.element.serviceNum})</span></a></li>
					    </c:if>
				    </c:forEach>
			      </ul>
				</li>
	  		</ul>
		   </c:forEach>
		</div>  --%>
	<div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button id="_submit" type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
		</ul>
	</div>
	</div>
</form>
  </body>
</html>