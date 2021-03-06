<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>

<script type="text/javascript" language="javascript">
//加载时默认选中单选条件和输入框显示
	$(document).ready(function(){
		py_ready(2);
// 		var hiddenText = $("input[name='stateInput']").val();
// 		if(hiddenText==''){
// 			$("select[name='filter_and_state_EQ_I']",navTab.getCurrentPanel()).find("option[value='']").attr("selected",true);
// 		}else if(hiddenText=='0'){
// 			$("select[name='filter_and_state_EQ_I']",navTab.getCurrentPanel()).find("option[value='0']").attr("selected",true);
// 		}else if(hiddenText=='1'){
// 			$("select[name='filter_and_state_EQ_I']",navTab.getCurrentPanel()).find("option[value='1']").attr("selected",true);
// 		}else if(hiddenText=='2'){
// 			$("select[name='filter_and_state_EQ_I']",navTab.getCurrentPanel()).find("option[value='2']").attr("selected",true);
// 		}
	});

	//保存下拉选中的value
	$("select[name='filter_and_state_EQ_I']",navTab.getCurrentPanel()).change(function(){
		var select = $("select[name='filter_and_state_EQ_I'][option:selected]",navTab.getCurrentPanel()).val();			//获取选中的下拉的值
		$("input[name='stateInput']").val(select);
	});


	function submitForm() {
		$(".pageForm", navTab.getCurrentPanel()).submit();
	
	}

	function showViewPath(path){
		//var viewPath = path;
		//alert(viewPath);
		var viewPath = $("#viewPath").val();
		if(viewPath!=null){
			window.open(viewPath);
		}
	}
	
	$("#checkAll",navTab.getCurrentPanel()).change(function (){//全选
		if($(this).attr("checked")=="checked"){
			$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",true);
		}else{
			$(this).parents("table").children().eq(1).find(":checkbox").attr("checked",false);
		}
	});
	
	function getCheckedValue() {
		var idArr = [];
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(
			function(index, items) {
				idArr[index] = items.value;
		});
		
		return idArr;
	}
	
	function pdf2jpg() {	//转图片
		var idArr = getCheckedValue();
		if (idArr.length == 0) {
			alertMsg.info("请选择要转图片的任务");
			return;
		}
		py_show();
		var imgTaskIds = idArr.join();
		$.ajax({
			type:"post",
			cache:false,
			data:{"imgTaskIds":imgTaskIds},
			url : "../reportdetail/erpReportdetailImgTask!pdf2Jpg.action",
			success : function(data) {
				var result= eval("("+data+")").result;
				if(result){
					alertMsg.correct("提交成功");
					py_hide();
					return navTabSearch($("#pagerFindForm",navTab.getCurrentPanel()));
				}else{
					py_hide();
					alertMsg.error("提交失败");
				}
			},
			error : function() {
				alertMsg.error("服务发生异常，请稍后再试！");
				py_hide();
				return;
			}
		});
	}
	
	function showPictures(obj) {	//查看图片
		var str = "?taskId=" + obj;
		$.pdialog.open("${path}/reportdetail/erpReportdetailImgTask!showPictures.action" + str, "showPictures", "图片查看", 
				{width:700,height:600,max:false,mask:true,mixable:false,minable:false,resizable:false,drawable:true,fresh:true});
	}
	
</script>

<div id="mc" class="py_theMb">
	<div class="py_bakpic"><!--图片--></div>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/reportdetail/erpReportdetailImgTask!listReportdetailImgtask.action'){this.action = '${path}/reportdetail/erpReportdetailImgTask!listReportdetailImgtask.action' ;} ;return navTabSearch(this);"
			action="${path}/reportdetail/erpReportdetailImgTask!listReportdetailImgtask.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td ><label>条码：</label> </td>
					<td ><input type="text" name="filter_and_code_LIKE_S" value="${filter_and_code_LIKE_S}" /></td>
					
					<td ><label>姓名：</label> </td>
					<td ><input type="text" name="filter_and_userName_LIKE_S" value="${filter_and_userName_LIKE_S}" /></td>
					
					<td ><label>身份证：</label> </td>
					<td ><input type="text" name="filter_and_idNo_EQ_S" value="${filter_and_idNo_EQ_S}" />
					</td>
				</tr>
				<%-- <tr>
					<td><label>起始日期：</label> <input type="text" name="filter_and_expiryDate_GEST_T" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${filter_and_expiryDate_GEST_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="filter_and_expiryDate_LEET_T" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${filter_and_expiryDate_LEET_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
				</tr> --%>
				<tr>
					<td ><label>批次号：</label> </td>
					<td ><input type="text" name="filter_and_batchNo_LIKE_S" value="${filter_and_batchNo_LIKE_S}" /></td>
					
					<td><label>生成状态：</label></td>
					<input type="hidden" name="stateInput" value="${stateInput}"/>
					<td>
						<select id="state" name="filter_and_state_EQ_I" rel="iselect" style="margin:1px;width:173px;">
							<option value="" >--请选择--</option>
							<option value="0" ${filter_and_state_EQ_I == 0 ? 'selected':'' }>未生成</option>
							<option value="1" ${filter_and_state_EQ_I == 1 ? 'selected':'' }>已生成</option>
							<option value="2" ${filter_and_state_EQ_I == 2 ? 'selected':'' }>转换异常</option>
						</select>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
	</form>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<!-- 
			<li><a class="delete" onclick="deleteProduct()" rel="ids"  title="确定要删除吗?" href="javascript:;"><span>删除</span></a></li>
			<li><a class="icon" onclick="changeProduct()" rel="ids" title="失效"><span>失效</span></a></li>
			-->
			<li><a class="icon" onclick="pdf2jpg()" style="cursor:pointer;"><span>转图片</span></a></li>
		</ul> 
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="120" id="exportExcelTable">
		<thead>
			<tr>
				<th width="50" nowrap="true"><input type="checkbox" id="checkAll"/>序号</th>
				<th export="true" columnEnName="code" columnChName="条码">条码</th>
				<th export="true" columnEnName="userName" columnChName="姓名">姓名</th>
				<th export="true" columnEnName="idNo" columnChName="身份证号">身份证号</th>
				<th export="true" columnEnName="phoneNo" columnChName="电话">电话</th>
				<th export="true" columnEnName="birthday" columnChName="生日">生日</th>
				<th export="true" columnEnName="pdfName" columnChName="报告名">报告名</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">批次号</th>
				<th export="true" columnEnName="createTime" columnChName="创建时间">创建时间</th>
				<th export="true" columnEnName="ageRangeStatus" columnChName="年龄范围">年龄范围</th>
				<th export="true" columnEnName="state" columnChName="状态">状态</th>
				<th export="false" columnEnName="picLook" columnChName="操作">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="reportdetailImgTask" varStatus="status">
				<tr target="sid_user" rel="${reportdetailImgTask.id }">
					<td align="center">
						<input  type="checkbox" name="ids" value="${reportdetailImgTask.id}">
						${ status.count }
					</td>
					<td align="center">${reportdetailImgTask.code}</td>
					<td align="center">${reportdetailImgTask.userName}</td>
					<td align="center">${reportdetailImgTask.idNo }</td>
					<td align="center">${reportdetailImgTask.phoneNo }</td>
					<td align="center">${reportdetailImgTask.birthday}</td>
					<td align="center">${reportdetailImgTask.pdfName}</td>
					<td align="center">${reportdetailImgTask.batchNo}</td>
					<td align="center">${fn:substring(reportdetailImgTask.createTime, 0, 19)}</td>
					<td align="center">${reportdetailImgTask.ageRangeStatus}</td>
					<c:choose>
						<c:when test="${reportdetailImgTask.state == 0}">
							<td align="center">未生成</td>
						</c:when >
						<c:when test="${reportdetailImgTask.state == 1}">
							<td align="center">已生成</td>
						</c:when >
						<c:when test="${reportdetailImgTask.state == 2}">
							<td align="center" style="color:red">转换异常</td>
						</c:when >
						<c:when test="${reportdetailImgTask.state == 3}">
							<td align="center" style="color:green">正在转换...</td>
						</c:when >
					</c:choose>
					<td align="center">
						<c:choose>
							<c:when test="${reportdetailImgTask.state == 1}">
								<button id="picLook" type="button" style="width:45px; height:26px;" onclick="showPictures('${reportdetailImgTask.id}')">查看</button>
							</c:when >
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
