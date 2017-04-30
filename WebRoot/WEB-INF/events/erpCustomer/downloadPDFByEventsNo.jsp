<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" language="javascript">

$("#clearText",$.pdialog.getCurrent()).click(function(){
	$("#pagerFindForm",$.pdialog.getCurrent()).find("input").each(function(){
		if($(this).attr("name")!="configId"){
			$(this).val("");
		}
	});
	$("#pagerFindForm",$.pdialog.getCurrent()).find("select").each(function(){
		$(this).val("");
	});
});

var downloadPDFByEventsNo = function(){
	var eventsNo = $("#eventsNo").val();
	
	if(eventsNo.trim()==""||eventsNo==null){
		alertMsg.error("请填写场次号!");
		return ;
	}
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/erpCustomer!downloadPDFByEventsNo.action",
		data: {'eventsNo': eventsNo},
		success: function(data){
			if(data.result=="success"){
				alert(data.msg);
				
			}else{
				alert(data.msg);
			}	
		},
		error :function(data){
			alert(data.msg);
		}
	});	
};

</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<input name="settleId" type="hidden" value="${settleId }" />
	<input name="navTabId" type="hidden" value="${navTabId }" />

	<form id="pagerFindForm" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label style="height:60px;width:90px" for="eventsNo">场次号：</label>
					<textarea style="width:520px;" id="eventsNo" name="eventsNo" ></textarea> 
				</td>
			</tr>			
			<tr>
				<td colspan="4"> <div class="subBar">
           			 <ul>
			       		<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="downloadPDFByEventsNo()">下载</button></div></div></li>
			      		<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">清除</button></div></div></li>
			      </ul>
			   </div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
