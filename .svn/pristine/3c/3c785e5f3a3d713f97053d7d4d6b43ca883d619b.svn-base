<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	select{
		margin-left:5px;
		margin-top:5px;
		width: 192px;
	}	
	tdHidden{
		white-space:nowrap;
	}
</style>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
	
	var selectGeCompanyId = $('input[name="selectGeCompanyId"]').val();
	if(selectGeCompanyId!="undefined"){
		$('select[name="filter_and_geneCompanyId_EQ_S"]').find("option[value='"+selectGeCompanyId+"']").attr("selected",true);
	}
});

$(document).ready(function(){
	getGeneCompanySelectedInput();
	getComboSelectedInput();
	
});

function getGeneCompanySelectedInput(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/combo/erpComboPrice!listCombo.action",
		success: function(data){
			var selectGeCompanyId = $('input[name="selectGeCompanyId"]').val();
			$("#geneCompany",navTab.getCurrentPanel()).empty();
			var s= eval("("+data+")");
			var companyOption="<option value=''>---请选择---</option>";
			var combos = s.combo;
			var arr1 = new Array();
			for(var i=0;i<combos.length;i++){	
				if(combos[i].geneCompanyId==''){
					continue;
				}
				//基因公司
				if(selectGeCompanyId==combos[i].geneCompanyId){
					if(arr1.indexOf(combos[i].geneCompanyId)==-1){
						arr1.push(combos[i].geneCompanyId);
						companyOption += "<option value='"+combos[i].geneCompanyId+"' selected='selected'>"+combos[i].geneCompany+"</option>";
					}
				}else{
					if(arr1.indexOf(combos[i].geneCompanyId)==-1){
						arr1.push(combos[i].geneCompanyId);
						companyOption += "<option value='"+combos[i].geneCompanyId+"'>"+combos[i].geneCompany+"</option>";
					}
				}
			}
			//将节点插入到下拉列表中
			$("select[name='filter_and_geneCompanyId_EQ_S']",navTab.getCurrentPanel()).append(companyOption);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function getComboSelectedInput(){
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/combo/erpComboPrice!setComboSelectedInput.action",
		success: function(data){
			var selectComboId = $('input[name="selectComboId"]').val();
			$("#comboName",navTab.getCurrentPanel()).empty();
			var s= eval("("+data+")");
			var comboption="<option value=''>---请选择---</option>";
			var combos = s.combos;
			var arr1 = new Array();
			for(var i=0;i<combos.length;i++){	
				//套餐
				if(selectComboId==combos[i].id){
					comboption += "<option value='"+combos[i].id+"' selected='selected'>"+combos[i].comboName+"</option>";
				}else{
					comboption += "<option value='"+combos[i].id+"'>"+combos[i].comboName+"</option>";
				}
				
			}
			//将节点插入到下拉列表中
			$("select[name='filter_and_comboId_EQ_S']",navTab.getCurrentPanel()).append(comboption);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

//变更用于复选框
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("modifyConference", "../events/erpConference!toModifyConference.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	}
}
//变更用于单行
function changeProduct(id) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.dialog("modifyConference", "../events/erpConference!toModifyConference.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });     
	
}

function add(){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/combo/erpComboPrice!toAddErpComboPrice.action?navTabId="+navTabId, "addComboPrice", "添加价格套餐",{width:800,height:600});
}

//将选中的状态放入input
$('select[name="filter_and_comboId_EQ_S"]').change(function (){
	var selectComboId = $('select[name="filter_and_comboId_EQ_S"] option:selected').val();
	$('input[name="selectComboId"]').val(selectComboId);
});

//将选中的状态放入input
$('select[name="filter_and_geneCompanyId_EQ_S"]').change(function (){
	var selectGeCompanyId = $('select[name="filter_and_geneCompanyId_EQ_S"] option:selected').val();
	$('input[name="selectGeCompanyId"]').val(selectGeCompanyId);
});

</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/combo/erpComboPrice!listErpComboPrice.action'){this.action = '${path}/combo/erpComboPrice!listErpComboPrice.action' ;} ;return navTabSearch(this);" action="${path}/combo/erpComboPrice!listErpComboPrice.action" method="post">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>套餐名：</label> 
					<input type="hidden" name="selectComboId" value="${selectComboId}"/>
					<select id="comboName" name="filter_and_comboId_EQ_S" >
		 			</select>
				</td>
				<td>
					<label>基因公司：</label> 
					<input type="hidden" name="selectGeCompanyId" value="${selectGeCompanyId}"/>
					<select id="geneCompany" name="filter_and_geneCompanyId_EQ_S">
		 			</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
			
         		<td>
         			<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
		<div class="panelBar">
		<ul class="toolBar">
			<%-- <li><a class="add" href="${path}/combo/erpComboPrice!toAddErpComboPrice.action"  target="dialog"><span>增加套餐</span></a></li> --%>
			<li><a class="add" href="javascript:void(0);"  onclick="add('${erpComboPrice.id}')";><span>增加套餐</span></a></li>
			<li><a class="delete" href="${path}/combo/erpComboPrice!delErpComboPrice.action"  rel="ids" postType="string" title="确定要下架吗?" target="selectedTodo"><span>下架套餐</span></a></li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable" style="table-layout:fixed;"> 
			<thead>
			<tr>
				<th width="40">序号</th>
				<th  export = "true" columnEnName = "comboName" columnChName = "套餐名" >套餐名</th>
				<th  export = "true" columnEnName = "comboContent" columnChName = "套餐内容" >套餐内容</th>
				<th  export= "true" columnEnName = "geneCompany" columnChName = "基因公司" >基因公司</th>
				<th  export = "true" columnEnName = "price" columnChName = "价格" >价格</th>
				<th  export = "true" columnEnName = "createTime" columnChName = "创建时间" >创建时间</th>
				<th  export = "false" columnEnName = "" columnChName = "" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpComboPrice" varStatus="status">
					<tr target="sid_user" rel="${erpComboPrice.id}">
						<td align="center" nowrap="true">
							<input type="checkbox" name="ids" value="${erpComboPrice.id}">
							${status.count}
						</td>
						<td align="center" nowrap="true" width="100">${erpComboPrice.comboName}</td>
						<td align="center" nowrap="true" width="300" class="tdHidden" style="overflow:hidden;text-overflow:ellipsis;" title="${erpComboPrice.comboContent}">${erpComboPrice.comboContent}</td>
						<td align="center" nowrap="true" width="100">${erpComboPrice.geneCompany}</td>
						<td align="center" nowrap="true" width="100">${erpComboPrice.price}</td>
						<td align="center" nowrap="true" width="100">${fn:substring(erpComboPrice.createTime,0,19)}</td>
						<td align="center" nowrap="true" width="100">
							<div class="panelBar">
									<ul class="toolBar">
										<li><a class="edit" href="${path}/combo/erpComboPrice!toModifyErpComboPrice.action?id=${erpComboPrice.id}" target="dialog"><span>更改价格</span></a></li>
									</ul>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 