<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.mc{
		width: 1265px; 
		height: 1027px; 
		position: absolute; 
		top: 0px; 
		left: 0px; 
		background-color: rgb(45, 45, 45); 
		z-index: 5000; 
		opacity: 0.2; 
		display: none; 
		background-position: initial initial; 
		background-repeat: initial initial;
	}
</style>

<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function(){
		//导出加载等待问题;
		py_ready(2); //进度条显示初始化;
	});
	
	function uploadFile(){
		if($(":file").val()==""){
			alertMsg.info("请先选择上传的EXCEL文件");
			return false;
		};
		py_show();
		$.ajaxFileUpload({
			url:'${path}/reportdetail/erpReportPrintManagement!uploadFile.action',
			type:'post',
			secureuri: false,
			fileElementId: 'file',
			dataType: 'json',
			success:function(data){
				py_hide();
				var str = "共导入【"+data.count+"】条数据,重复【"+data.count2+"】条数据,";
				if(confirm(str+"确定该批次的导入信息不打印?")){
					$.ajax({	
						type: "post",
						cache :false,
						dateType:"json",
						data:{"batchNo":data.batchNo,"count":data.count,"isConfirm":1},
						url: "${path}/reportdetail/erpReportPrintManagement!updateState.action",
						success: function(data){
							if(eval("("+data+")").state==1){
			 					alertMsg.correct("确认成功");
			 				}else{
			 					alertMsg.warn("确认异常");
			 				}
							return navTabSearch($("#reportDetailManagement"));
			 			},
						error :function(){
							alert("服务发生异常，请稍后再试！");
							return;
						}
					});
				}else{
					$.ajax({	
						type: "post",
						cache :false,
						dateType:"json",
						data:{"batchNo":data.batchNo,"count":data.count,"isConfirm":2},
						url: "${path}/reportdetail/erpReportPrintManagement!updateState.action",
						success: function(data){
			 				if(eval("("+data+")").state==1){
			 					alertMsg.correct("确认成功");
			 				}else{
			 					alertMsg.warn("确认异常");
			 				}
			 				return navTabSearch($("#reportDetailManagement"));
			 			},
						error :function(){
							alert("服务发生异常，请稍后再试！");
							return;
						}
					});
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	
	function checked(){		//上传文件之前先检查
		if($("#file").val()==""){
			alert("请先选择要上传的文件！");
			return false;
		}
	}
	
	function showNoReportPrint(){
		var tabId = $("#tabId",navTab.getCurrentPanel()).val();
		$.pdialog.open("${path}/reportdetail/erpReportPrintManagement!showNoReportPrint.action?tabId="+tabId, "showNoReportPrint", "添加不打印任务", 
				{width:"450",height:"200",max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
	
	function clearInput(){
		$(':input','#reportDetailManagement',navTab.getCurrentPanel())  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('');
	}
	
// 	function cs(){
// 		$.ajax({	
// 			type: "post",
// 			cache :false,
// 			dateType:"json",
// 			url: "${path}/reportdetail/erpReportPrintManagement!cs.action",
// 			success: function(data){
//  				alert(1);
//  			},
// 			error :function(){
// 				alert("服务发生异常，请稍后再试！");
// 				return;
// 			}
// 		});
// 	}
	
</script>
<div id="mc" class="py_theMb">
	<div class="py_bakpic"><!--图片-->
   </div>
</div>
<span class="title"></span>
<!-- <button type="button" onclick ="cs()">测试</button> -->
<div class="pageHeader">
	<input id="tabId" type="hidden" value="${tabId}">
	<div class="pageHeader">
		<form id="reportDetailManagement" onsubmit="if(this.action != '${path}/reportdetail/erpReportPrintManagement!reportDetailManagement.action'){this.action = '${path}/reportdetail/erpReportPrintManagement!reportDetailManagement.action' ;} ;return navTabSearch(this);" 
			action="${path}/reportdetail/erpReportPrintManagement!reportDetailManagement.action" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="pageFormContent" id="tableId">
					<tr>
						<td>
							<label>姓名：</label> 
							<input type="text" name="filter_and_name_LIKE_S" value="${filter_and_name_LIKE_S}" />
						</td>
						<td>
							<label>条形码：</label> 
							<input type="text" name="filter_and_code_LIKE_S"  value="${filter_and_code_LIKE_S}" />
						</td>
						<td>
		         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="clearInput()">重置</button></div></div>
						</td>
						<td>
							<div>
								<lable class="tip3">不打印任务导入：</lable>
								<input type="file" id="file" name="file"/><button type="button" onclick="uploadFile()">上传</button>
							</div>
						</td>
					</tr>
					
				</table>
			</div>
		</form>
	</div>

	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" style="margin-left: 5px;">
	            <li><a class="add" onclick="showNoReportPrint()"><span>新增</span></a></li> 
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>	
		<table class="list" width="100%" layoutH="150" id="showNoReportPrint"> 
			<thead>
				<tr>
					<th width="40">序号</th>
					<th width="100" export = "true" columnEnName = "id" columnChName = "批次号" > 批次号 </th> 
					<th width="100" export = "true" columnEnName = "code" columnChName = "编码" > 编码 </th> 
					<th width="100" export = "true" columnEnName = "name" columnChName = "姓名" > 姓名 </th>
					<th width="100" export = "true" columnEnName = "gender" columnChName = "姓别" > 姓别 </th>
					<th width="100" export = "true" columnEnName = "createTime" columnChName = "创建时间 " > 创建时间 </th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="entity" varStatus="status">
						<tr>
							<td align="center" width="40">${status.count}</td>
							<td align="center" nowrap="true">${entity.batchNo}</td>  
							<td align="center" nowrap="true">${entity.code}</td>  
							<td align="center" nowrap="true">${entity.name}</td>  
							<td align="center" nowrap="true">${entity.gender}</td>  
							<td align="center" nowrap="true">${fn:substring(entity.createTime,0,19)}</td>
						</tr>
					</c:forEach>
				</tbody>
		</table>
	</div>
