<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="${path}/resource/customerRelationShip!queryCombo.action" 
	rel="pagerForm"
	onsubmit="return dialogSearch(this);"
	method="post" >
<input type="hidden" name="navTabId" value="${navTabId }"/>
<input type="hidden" name="id" value="${id }"/>
<div class="pageHeader">
	<div class="searchBar">
		<table class="pageFormContent" style="border-width: 0;">
			<tr>
				<td align="right">套餐名称：</td>
				<td align="left">
					<input name="params.comboName" type="text" value="${params.comboName }" flag="find" style="width: 150px;"/>
				</td>
				<td align="right">项目类别：</td>
				<td align="left">
					<select name="params.comboType" style="width: 150px;">
						<option value="">--全部--</option>
						<option value="PCT_001" <c:if test ="${params.comboType=='PCT_001' }"> selected="selected"</c:if> >健康管理-基因</option>
						<option value="PCT_002" <c:if test ="${params.comboType=='PCT_002' }"> selected="selected"</c:if> >健康管理-癌筛</option>
						<option value="PCT_003" <c:if test ="${params.comboType=='PCT_003' }"> selected="selected"</c:if> >分子检测</option>
						<option value="PCT_004" <c:if test ="${params.comboType=='PCT_004' }"> selected="selected"</c:if> >健康管理-无创-生物电</option>
						<option value="PCT_005" <c:if test ="${params.comboType=='PCT_005' }"> selected="selected"</c:if> >健康管理-无创-微磁</option>
						<option value="PCT_006" <c:if test ="${params.comboType=='PCT_006' }"> selected="selected"</c:if> >其他</option>
					</select>
				</td>
				<td>
					<div class="subBar">
						<ul style="float: left">
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							<li><a id="resetDo" href="javascript:;" class="button"><span>重置</span></a></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
	<div class="pageContent">
    	<input type="hidden" name="navTabId" id = "navTabId" value="${navTabId}" />
    	
		<div class="pageFormContent" layoutH="60" style="height: 330px;">
		  	<div class="panelBar">
				<jsp:include page="/common/paginationDialog.jsp" />	
			</div>
	  		<table class="list" width="100%">
	  			<thead>
	  				<tr>
		  				<th width="45px">序号</th>
		  				<th>套餐名称</th>
		  				<th>项目类型</th>
		  			</tr>
	  			</thead>
	  			
	  			<tbody>
	  				<c:forEach items="${page.results }" var="obj" varStatus="statu">
	  					<tr>
	  						<td>
	  							${statu.count }
	  							<input name="comboId" type="checkbox" value="${obj.id }"/>
	  						</td>
	  						<td>${obj.comboName }</td>
	  						<td>
	  							<c:if test="${obj.projectTypes=='PCT_001'}">健康管理-基因</c:if>
								<c:if test="${obj.projectTypes=='PCT_002'}">健康管理-癌筛</c:if>
								<c:if test="${obj.projectTypes=='PCT_003'}">分子检测</c:if>
								<c:if test="${obj.projectTypes=='PCT_004'}">健康管理-无创-生物电</c:if>
								<c:if test="${obj.projectTypes=='PCT_005'}">健康管理-无创-微磁</c:if>
								<c:if test="${obj.projectTypes=='PCT_006'}">其他</c:if>
	  						</td>
	  					</tr>
	  				</c:forEach>
	  			</tbody>
	  		</table>
    </div> 
    <div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="sureDeal">确认</button>
					</div>
				</div>
			</li>
		</ul>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	//重置;
	$("#resetDo", $.pdialog.getCurrent()).on("click", function() {
		$("input[flag='find']", $.pdialog.getCurrent()).val("");
		$("select", $.pdialog.getCurrent()).val("");
	});
	
	$("#sureDeal", $.pdialog.getCurrent()).on("click", function() {
		//判断是否选中;
		//判断是否选中数据;
		var count = 0;
		var ids = "";
		var status = "";
		
		$("input:checkbox[name='comboId']:checked", $.pdialog.getCurrent()).each(function(index, val) {
			ids += val.value + "," ;
			count ++;
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要更改的支公司信息！");
			return;
		}
		//后台值保存;
		$.ajax({
			type: "post",
			cache : false,
			async : false,
			data:{"ids":ids, "id": "${id}"},
			url: "${path }/resource/customerRelationShip!saveProCombo.action",
			success: function(data){
				var dt = eval("("+data+")");
				if(dt.result) {
					//保存后刷新选中navTab;
					navTab.reloadFlag("${navTabId}");
					$.pdialog.closeCurrent();
				} else  {
					alertMsg.warn("同一个项目中不能添加相同的套餐！");
				}
			},
			error :function(){
				alertMsg.warn("服务发生异常，请稍后再试！");
				return;
			}
		});
			
	});
});




</script>