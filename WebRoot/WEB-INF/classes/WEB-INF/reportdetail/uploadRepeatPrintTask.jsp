<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>

<style type="text/css">
	.hide{
		display: none;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">

	$(document).ready(function(){
		py_ready(2);
	});

	function uploadFile(){
		var filePath = $(":file",$.pdialog.getCurrent()).val();
		if(filePath == ""){
			alertMsg.info("请先选择上传的EXCEL文件");
			return false;
		};
		py_show();
		$.ajaxFileUpload({
			url:"${path}/reportdetail/erpRepeatPrintTask!uploadFile.action",
			type:'post',
			secureuri: false,
			fileElementId: 'file',
			dataType: 'json',
			data:{"filePath":filePath},
			success:function(data){
				py_hide();
				$("#codes",$.pdialog.getCurrent()).val(data.code);
			},
			error:function (data,status,e){
				py_hide();
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	
	$("#checkAll",$.pdialog.getCurrent()).change(function (){//全选
		if($(this).attr("checked")=="checked"){
			$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",true);
		}else{
			$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",false);
		}
	});
	
	function confirmRepeatCodes(){//全选或部分选择之后的操作
		var checkbox = $("input:checkbox[name='codes']:checked", $.pdialog.getCurrent());
		if($(checkbox).length==0){
			alertMsg.info("请选择要重复打印的任务");
			return;
		}
		
		var idArr = [];
		var noArr = [];
		$(checkbox).each(
			function(index, items) {
				idArr[index] = items.value;
//				noArr[index] = $(items).parent().next().text();
		});
		var ids = idArr.join();
//		var printTaskNos = noArr.join();
		$.ajax({
			type:"post",
			cache:false,
			data:{"ids":ids},
			url : "../reportdetail/erpRepeatPrintTask!confirmRepeatCodes.action",
			success : function(data) {
				var result= eval("("+data+")").result;
				if(result=="success"){
					alertMsg.correct("添加成功");
					navTab.reload("${path}/reportdetail/erpRepeatPrintTask!listRepeatPrintTask.action", {navTabId: "menu_402881b25775e2b6015775e798e10004"});
					$.pdialog.closeCurrent();
				}else{
					alertMsg.error("添加失败");
				}
			},
			error : function() {
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
</script>
<!--蒙版 start-->
	<div id="mc" class="py_theMb"><div class="py_bakpic"><!--图片--></div></div>
<!--蒙版 end-->
<div class="tip"><span>查询</span></div>

<div class="pageContent">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpRepeatPrintTask!showUploadFile.action'){this.action = '${path}/reportdetail/erpRepeatPrintTask!showUploadFile.action' ;} ;
		return dialogSearch(this);" action="${path}/reportdetail/erpRepeatPrintTask!showUploadFile.action" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>条码：</label> 
						<input id="codes" type="text" name="code" value="${codes}" readonly="readonly">
					</td>
					<td>
						<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					</td>
					<td>
						<input type="file" id="file" name="file" />
						<button type="button" onclick="uploadFile()"
							style="float:right;height: 19.5px;line-height:100%;margin-left: 5px;">导入EXCEL</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul>
			<li><a class="add" onclick="confirmRepeatCodes()" style="cursor:pointer;"><span>确认选择</span></a></li>
		</ul>
		<jsp:include page="/common/paginationDialog.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th>全选<input id="checkAll" type="checkbox" /></th>
				<th>条形码</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>性别</th>
				<th>套餐</th>
				<th>支公司</th>
				<th>所属公司</th>
				<th>部门</th>
				<th>报告类型</th>
				<th>创建时间</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="repeatPrint" varStatus="status">
				<tr target="sid_user" rel="${repeatPrint.id }">
					<td align="center">${status.count}<input type="checkbox" name="codes" value="${repeatPrint.id}"/></td> 
					<td align="center">${repeatPrint.code}</td>
					<td align="center">${repeatPrint.userName}</td>
					<td align="center">${repeatPrint.age}</td>
					<td align="center">${repeatPrint.gender}</td>
					<td align="center">${repeatPrint.combo}</td>
					<td align="center">${repeatPrint.branchCompany}</td>
					<td align="center">${repeatPrint.ownedCompany}</td>
					<td align="center">${repeatPrint.dept}</td>
					<td align="center">
						<c:if test="${repeatPrint.type=='0'}">基因报告</c:if>
						<c:if test="${repeatPrint.type=='2'}">癌筛报告</c:if>
						<c:if test="${repeatPrint.type=='3'}">癌筛报告单</c:if>
						<c:if test="${repeatPrint.type=='4'}">1+X报告</c:if>
						<c:if test="${repeatPrint.type=='5'}">无创报告</c:if>
					</td>
					<td align="center">${fn:substring(repeatPrint.createTime,0,19)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
