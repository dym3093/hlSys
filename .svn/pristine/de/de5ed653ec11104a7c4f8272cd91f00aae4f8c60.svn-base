<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 

<style type="text/css">
	select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>   
<script type="text/javascript">

//用于选中复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[id="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择打印任务！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条打印任务！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyConference", "../reportdetail/erpPrintBatch!toModifyPrintBatch.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
}

//关闭弹窗
function closeDialog(){
//	navTabSearch(this);	//刷新页面
	$.pdialog.closeCurrent();
}

$(function() {
	/*
	 * 初始化的时候获取打印公司;
	 * [数据来源即库存管理中的仓库名称]
	 * create by henry.xu 20160811
	 */
	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/warehouse/warehouse!findAllCustomer.action",
		success: function(data){
			var printCompany = $('input[name="selectWareId"]').val();
			$("#printCompanyId").empty();
			var wares= eval("("+data+")");
			option="<option value=''>---请选择---</option>";
			for(var i=0;i<wares.length;i++){	
				option += "<option value='" + wares[i].id + "#" + wares[i].text+"' >"+wares[i].text+"</option>";
			}
			//将节点插入到下拉列表中
			$("select[name='printCompanyIdVsprintCompanyId']").append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	$(":radio").bind("click", function(){
		var val=$(this).val();
		$("#isSlh").val(val);
	});
});

$("#printCompanySel").change( function() {	//状态下拉框
	var selectVal=$("#printCompanySel option:selected").val();
	$("#printCompanyText").val(selectVal);
});

function submitPrintTask(){
	var isSubmit = $("#isSubmit").val();
	if(isSubmit!=0){
		alertMsg.error("请勿重复提交");
		return;
	}
	$("#isSubmit").val(1);
	var printTaskNo = $(":text[name='printTaskNo']",$.pdialog.getCurrent()).val();
	var printBatchId = $("#printBatchId",$.pdialog.getCurrent()).val();
	var projectCode = $("#projectCode",$.pdialog.getCurrent()).val();
	var dept = $("#dept",$.pdialog.getCurrent()).val();
	var taskName = $(":text[name='taskName']",$.pdialog.getCurrent()).val();
	var batchNum = $(":text[name='batchNum']",$.pdialog.getCurrent()).val();
	var sumCount = $(":text[name='sumCount']",$.pdialog.getCurrent()).val();
	var companyId = $(":text[name='companyId']",$.pdialog.getCurrent()).val();
	var printBatchNos = $(":text[name='printBatchNos']",$.pdialog.getCurrent()).val();
	var printCompanyArray = $("#printCompanyText",$.pdialog.getCurrent()).val();
	var expectTime = $(":text[name='expectTime']",$.pdialog.getCurrent()).val();
	var isSlh = $("#isSlh").val();
	if(printCompanyArray==""){
		alertMsg.error("请选择打印公司");
		return;
	}else if(printTaskNo==""){
		alertMsg.error("请填写任务编号");
		return;
	}
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		data:{"printTaskNo":printTaskNo,"printBatchId":printBatchId,"taskName":taskName,"batchNum":batchNum,
			"sumCount":sumCount,"companyId":companyId,"printBatchNos":printBatchNos,"printCompanyArray":printCompanyArray,
			"expectTime":expectTime,"isSlh":isSlh,"projectCode":projectCode,"dept":dept},
		url: "${path}/reportdetail/erpPrintTask!toAddPrintTask.action",
		success: function(data){
			var str = eval("("+data+")");
			if(str.count==1){
				alertMsg.correct("提交成功");
				navTabSearch(this);
				$.pdialog.closeCurrent();
			}else if(str.count==2){
				alertMsg.error("该打印公司没有套餐【"+str.combo+"】的价格");
			}else{
				alertMsg.error("提交失败");
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

</script>
<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div class="pageHeader" style="background:white">
	<form onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/reportdetail/erpPrintTask!toAddPrintTask.action" method="post">
			<table class="pageFormContent">
			<tr>
				<td>
					<label>任务编号：</label> 
					<input type="text" name="printTaskNo" value="${printTaskNo}" class="required"/>
					<input type="hidden" name="threeNum" value="${threeNum}"/>
				</td>
				<td>
					<label>任务名称：</label> 
					<input type="text" name="taskName" value="${taskName}" readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td>
					<label>批次数量：</label> 
					<input type="text" name="batchNum" value="${batchNum}" readonly="readonly"/>
				</td>
				<td align="center">
					<label>创建时间：</label> 
					<input type="text" name="createTime" value="${createTime}" readonly="readonly"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label>打印公司：</label> 
					<input id="printCompanyText" type="hidden" name="printCompanyArray"  readonly="readonly"/>
					<select id="printCompanySel" name="printCompanyIdVsprintCompanyId"  required="required" style="width:193px;">
					</select>
				</td>
				<td>
					<label>十里河寄出：</label> 
					<input id="isSlh"  type="text" name="isSlh" value="0" hidden="hidden"/>
					<input type="radio" name="slhExpress" value="0" checked="checked"/>否
					<input type="radio" name="slhExpress" value="1"/>是
				</td>
			</tr>
			
			<tr>
				<td>
					<label>报告总数量：</label> 
					<input type="text" name="sumCount" value="${sumCount}" readonly="readonly"/>
				</td>
				<td>
					<label>预计时间：</label> 
					<input type="text" name="expectTime" value="${expectTime}" readonly="readonly"/>
				</td>
				<td hidden="hidden">
					<label>支公司ID</label>
					<input type="text" name="companyId" value="${companyId}"/>
				</td>
				<td hidden="hidden">
					<label>批次号</label>
					<input type="text" name="printBatchNos" value="${printBatchNos}"/>
				</td>
				<td hidden="hidden">
					<label>防止多次点击确认提交</label>
					<input id="isSubmit" type="text" name="isSubmit" value="0" readonly="readonly"/>
				</td>
				<td hidden="hidden">
					<label>批次号</label>
					<input type="text" name="printBatchNos" value="${printBatchNos}"/>
				</td>
				<td hidden="hidden">
					<label>部门</label>
					<input id="dept" type="text" name="dept" value="${dept}"/>
				</td>
				<td hidden="hidden">
					<label>项目编码</label>
					<input id="projectCode" type="text" name="projectCode" value="${projectCode}"/>
				</td>
				<td hidden="hidden">
					<label>批次ID</label>
					<input id="printBatchId" type="hidden" name="printBatchId" value="${printBatchId}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitPrintTask()">确认</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="pageContent">

<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<!-- <th width="35">序号</th> -->
				<th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" >批次号</th>
				<th  export= "true" columnEnName = "province" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th  export = "true" columnEnName = "branchCompany" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "branchCompany" columnChName = "部门" >部门</th>
				<th  export = "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
				<th  export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpPrintBatch" varStatus="status">
				<tr>
					<td align="center">	${erpPrintBatch.printBatchNo}</td>
					<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
					<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
					<td align="center"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
					<td align="center">	${erpPrintBatch.dept=="无"?"":erpPrintBatch.dept}</td>
					<td align="center">	${erpPrintBatch.combo}</td>
					<td align="center">	${erpPrintBatch.count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>