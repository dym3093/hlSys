<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

String id = (String)request.getAttribute("id");
%>

<style>
span.error {
	overflow: hidden;
	width: 165px;
	height: 20px;
	padding: 0 3px;
	line-height: 20px;
	background: #F00;
	color: #FFF;
	top: 5px;
	left: 318px;
	margin-top: 5px;
}
</style>

<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/jquery/json2.js" type="text/javascript"></script>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<form id="pagerEventTaskForm" method="post" action="${path}/settlementManagement/erpSettlementTaskBX!eventsAdd.action" rel="pagerForm">
	<input type="hidden" id="settlementTaskId" name="settlementTaskId" value="${settlementTaskId }"/>
	<input type="hidden" name="navTabId" value="${navTabId }"/>
</form>

<div class="pageContent" style="height: 478px;">
	<!-- 数据显示 -->
	<table class="list" width="100%" layoutH="40" id="eventsTaskTableId">
		<thead>
			<tr>
				<th width="35px">序号</th>
				<th width="165px">场次号</th>
				<th width="150px">套餐名称</th>
				<th width="60px">套餐人数</th>
				<th width="180px">设置折扣</th>
				<th width="180px">变更说明</th>
				<th width="80px">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="obj" varStatus="status">
				<tr target="sid_user" rel="${obj.id }">
					<td>
						${status.count }
						<input name="id" value="${obj.id }" type="hidden"/>
						<input name="combo" value="${obj.combo }" type="hidden"/>
						<input name="eventsNo" value="${obj.eventsNo }" type="hidden"/>
						<input name="comboPrice" value="${obj.comboPrice }" type="hidden"/>
						<input name="settlementTaskId" value="${settlementTaskId }" type="hidden"/>
						<input type="hidden" name="comboId" value="${obj.comboId }" class="textInput"/>
					</td>
					<td>${obj.eventsNo }</td>
					<td>
						<!-- 超过十个字截取; -->
						<c:if test="${fn:length(obj.combo) > 10 }">
							<span title="${obj.combo }">${fn:substring(obj.combo, 0, 10) }...</span>
						</c:if>
						<c:if test="${fn:length(obj.combo)<=10 }">
							${obj.combo }
						</c:if>
					</td>
					<td>${obj.userNum }</td>
					<td>
						<form action="#" name="form01" class="pageForm required-validate">
							<input type="text" disabled="disabled" onblur="discountBlur(this)" name="discount" maxlenth="6" class="required number valid" value="${obj.discount }" class="textInput" onkeyup="value=value.replace(/[^\d.]/g,'')"/>%
						</form>
					</td>
					<td>
 						<form action="#" name="form02" class="pageForm required-validate">
							<input type="text" disabled="disabled" name="remark" maxlength="200" class="required valid" title="${obj.remark }" value="${obj.remark }" class="textInput"/>
						</form>
					</td>
					<td>
						<a name="save" onclick="saveClick(this);" class="button" href="javascript:void(0);" style="margin-left: 5px;"><span>保存</span></a>
						<a name="edit" onclick="editClick(this)" class="button" href="javascript:void(0);" style="margin-left: 10px;"><span>修改</span></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页条 -->
	<div class="panelBar">
		<jsp:include page="/common/pagination_dialog.jsp" />
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("input[name='discount']", $.pdialog.getCurrent()).each(function() {
			var $tr = $(this).parent().parent().parent();
			var remark = $tr.find("input[name='remark']").val();
			if($(this).val()== null || $(this).val()=='' || (parseFloat($(this).val()) < 1 && remark == null && remark =='')) {
				$tr.find("a[name='edit']").hide();
			} else {
				$tr.find("a[name='save']").hide();
			}
		});
		
	});
	
	function discountBlur(inp) {
		var discount = $(inp).val();
		if(discount !=null && discount != '') {
			discount = parseFloat(discount).toFixed(2);
		}
		$(inp).val(discount);
	}
	
	function editClick(btn) {
		var $tr = $(btn).parent().parent();
		$tr.find("input[name='discount']").removeAttr("disabled");
		$tr.find("input[name='remark']").removeAttr("disabled");
		$tr.find("a[name='save']").show();
		$tr.find("a[name='edit']").hide();
	}
	
	function saveClick(btn) {
		var $tr = $(btn).parent().parent();
		if($tr.find("form[name='form01']").valid() 
			&& $tr.find("form[name='form02']").valid()) {
			$tr.find("a[name='edit']").show();
			$tr.find("a[name='save']").hide();
			save($tr);
		} 
	}
	
	function save($tr) {
		//$th=$(tr)
		var discount   = $tr.find("input[name='discount']").val();
		var remark     = $tr.find("input[name='remark']").val();
		var id         = $tr.find("input[name='id']").val();
		var combo      = $tr.find("input[name='combo']").val();
		var comboId    = $tr.find("input[name='comboId']").val();
		var comboPrice = $tr.find("input[name='comboPrice']").val();
		var eventsNo   = $tr.find("input[name='eventsNo']").val();
		var settlementTaskId = $("#settlementTaskId").val();
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path}/settlementManagement/erpSettlementTaskBX!ajaxSaveOrUpdateEventsTask.action",
		      data: {"id":id,
		    	  "combo":combo,
		    	  "comboId":comboId,
		    	  "comboPrice":comboPrice,
		    	  "eventsNo":eventsNo,
		    	  "remark":remark,
		    	  "discount":discount,
		    	  "settlementTaskId": settlementTaskId
		      },
		      success: function(data){ 
		          if(data.statusCode == "200") { //成功
		        	  $tr.find("a[name='edit']").show();
		        	  $tr.find("input[name='discount']").attr("disabled", "disabled");
		      		  $tr.find("input[name='remark']").attr("disabled", "disabled");
		      		  $tr.find("input[name='remark']").attr("title", $tr.find("input[name='remark']").val());
		      		  $tr.find("a[name='save']").hide();
		          } else if(data.statusCode=="300") {
		        	  $tr.find("a[name='save']").show();
		        	  alertMsg.error("数据保存出错!");
		          }
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
	        	  $tr.find("a[name='save']").show();
		    	  alertMsg.error("数据保存出错!");
		      }    
		});
		
	}
	
</script>
