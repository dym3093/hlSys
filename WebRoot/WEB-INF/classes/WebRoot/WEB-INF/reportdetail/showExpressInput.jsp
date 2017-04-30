<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	
</style>
<script type="text/javascript" language="javascript">
	//关闭弹窗
	function closeDialog(){
		$.pdialog.closeCurrent();
	}
	
	function setAveragePrice(){
		var expressPrice = parseFloat($.trim($("#expressPrice").val()));
		var count = parseInt($.trim($("#count").val()));
		if(isNaN(expressPrice) || isNaN(count)){
			alertMsg.error("请输入正确的价格或人数");
			return;
		}
		var price = expressPrice/count;
		$("#averagePrice").val(price.toFixed(2));
	}
	
	function insertExpressInput(){
		var id = $.trim($("#printTaskId").val());
		var expressCompany = $.trim($("#expressCompany").val());
		var expressNo = $.trim($("#expressNo").val());
		var expressPrice = $.trim($("#expressPrice").val());
		var count = $.trim($("#count").val());
		var averagePrice = $.trim($("#averagePrice").val());
	    if(expressCompany=="" || expressNo=="" || expressPrice=="" || count=="" || averagePrice==""){
	    	alertMsg.error("你还有字段没有输入，请重新检查输入");
	    	return;
	    }
	    var printTaskNo = encodeURI($.trim($("#printTaskNo",$.pdialog.getCurrent()).val()));
	    var printState = $.trim($("#printState",$.pdialog.getCurrent()).val());
	    var printCompanyId = $.trim($("#printCompanyId",$.pdialog.getCurrent()).val());
	    var startTime = $.trim($("#startTime",$.pdialog.getCurrent()).val());
	    var endTime = $.trim($("#endTime",$.pdialog.getCurrent()).val());
	    var args ="?";
	    if(printTaskNo!=""){
	    	args += "printTaskNo="+printTaskNo;
	    }
	    if(args!="?" && printState!=""){
	    	args += "&printState="+printState;
	    }else if(printState!=""){
	    	args += "printState="+printState;
	    }
	    if(args!="?" && printCompanyId!=""){
	    	args += "&printCompanyId="+printCompanyId;
	    }else if(printCompanyId!=""){
	    	args += "printCompanyId="+printCompanyId;
	    }
	    if(args!="?" && startTime!=""){
	    	args += "&startTime="+startTime;
	    }else if(startTime!=""){
	    	args += "startTime="+startTime;
	    }
	    if(args!="?" && endTime!=""){
	    	args += "&endTime="+endTime;
	    }else if(endTime!=""){
	    	args += "endTime="+endTime;
	    }
		$.ajax({
			type:"post",
			cache:false,
			data:{"id":id,"expressCompany":expressCompany,"expressNo":expressNo,"expressPrice":expressPrice,"count":count,"averagePrice":averagePrice},
			url:"../reportdetail/erpPrintTask!insertExpressInfo.action",
			success : function(data) {
				var str= eval("("+data+")");
				if(str.count==1){
					alertMsg.correct("修改成功");
				}else{
					alertMsg.error("修改失败");
				}
				navTab.closeCurrentTab();
				navTab.openTab("listPrintTaskExpress",
						"${path}/reportdetail/erpPrintTask!listPrintTaskExpress.action"+args, { title:"打印任务快递补录", fresh:true, data:{} });	
				$.pdialog.closeCurrent();
			},
			error : function() {
				alert("服务器出错,请稍后再试");
				return;
			}
		});		
	}
	
</script>
<div>
	<table>
		<tr hidden="hidden">
			<td>
				<label>ID：</label>
				<input id="printTaskId" type="text" name="filter_and_id_LIKE_S" value="${id}" />
			</td>
			<td>
				<input id="printTaskNo" type="text" name="filter_and_printTaskNo_LIKE_S" value="${printTaskNo}" />
				<input id="printState" type="text" name="filter_and_printState_EQ_S" value="${printState}" />
				<input id="printCompanyId" type="text" name="filter_and_printcompanyId_EQ_S" value="${printCompanyId}" />
				<input id="startTime" type="text" name="filter_and_createTime_GEST_T" value="${startTime}" />
				<input id="endTime" type="text" name="filter_and_createTime_LEET_T" value="${endTime}" />
			</td>
		</tr>
		
		<tr>
			<td style="padding-left:12px;">
				<label>快递公司：</label>
				<input id="expressCompany" type="text" name="filter_and_expressCompany_LIKE_S" value="${expressCompany}" />
			</td>
			<td style="padding-left:21px;">
				<label>快递单号：</label>
				<input id="expressNo" type="text" name="filter_and_expressNo_LIKE_S" value="${expressNo}" />
			</td>
		</tr>
		
		<tr>
			<td style="padding-left:12px;">
				<label>快递费用：</label>
				<input id="expressPrice" type="text" name="filter_and_expressPrice_LIKE_S" value="${expressPrice}" />
			</td>
			<td></td>
		</tr>
		
		<tr>
			<td>
				<label>总合计人数：</label>
				<input id="count" type="text" name="filter_and_count_LIKE_S" value="${count}" onblur="setAveragePrice()"/>
			</td>
			<td style="padding-left:10px;">
				<label>每人均摊费：</label>
				<input id="averagePrice" type="text" name="filter_and_averagePrice_LIKE_S" value="${averagePrice}" />
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td  style="padding-left:152px;">
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="insertExpressInput()">提交</button></div></div>
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
			</td>
		</tr>
	</table>
</div>
