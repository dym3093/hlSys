<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

function selectall()
{
   var mycheckbox=document.getElementById('select1');
var checkboxs=document.getElementsByName('ids');
for(var i=0;i<checkboxs.length;i++)
{
    checkboxs[i].checked=mycheckbox.checked;
}
}

$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
});

function dj(){
	/*
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
		status = $(this).parent().next().text();
	});
	alert(ids);
	$.pdialog.reload("../reportdetail/erpPrintBatch!isPrintBatch.action",ids,"lookPrintTask.jsp");
	*/
	var willPrintId=[];
	$('input[name="ids"]:checked').each(function(){
		willPrintId.push($(this).val());
	});
	if(0==willPrintId.length){
		alert("请选择需要打印的批次！");
		return;
	}
	
	var paramStr = willPrintId.join();
	//$.pdialog.reload("../reportdetail/erpPrintBatch!isPrintBatch.action",params,"lookPrintTask.jsp");
	$.pdialog.open("../reportdetail/erpPrintBatch!isPrintBatch.action?ids="+paramStr, "lookPrintTask.jsp", "打印任务",{width:800,height:600});
	/*
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		data:{"printids":params},
		url: "/ymjy/statistics/toPrintAction!dealToPrint.action",
		success: function(data){
			$("#res").empty();
			var jsonObj = data.results;
			if(jsonObj.lenght==0){
				$("#titlename").text("当前没有需要进行会员匹配的数据");
				return;
			}
			var trcontent;
			$.each(jsonObj, function (i, item) {
				trcontent +="<tr id='"+item+"'><td>"+(i+1)+"</td><td>"+item+"</td><td name='itemres' style='width:400px'></td><td><input name='startbnt' type='button' value='开始匹配' onclick='startMatch("+item+")'></td></tr>"
			});
			$("#res").append(trcontent);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});*/
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
		navTab.openTab("modifyConference", "../reportdetail/erpPrintBatch!isPrintBatch.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
	
	
	/*
	var willPrintId=[];
	$('input[name="ids"]:checked').each(function(){
		willPrintId.push($(this).val());
	});
	if(0==willPrintId.length){
		alert("请选择需要打印的批次！");
		return;
	}
	var params = JSON.stringify(willPrintId);
	alert(params);
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		data:{"printids":params},
		url: "../reportdetail/erpPrintBatch!isPrintBatch.action",
		success: function(data){
			$("#res").empty();
			var jsonObj = data.results;
			if(jsonObj.lenght==0){
				//$("#titlename").text("当前没有需要进行会员匹配的数据");
				alert("添加打印任务失败");
				return;
			}
		}
	});
	*/
}
//变更用于单行
function changeProduct2(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.openTab("modifyConference", "../reportdetail/erpPrintBatch!toModifyPrintBatch.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	
}
/*
//删除用于单行
function changeDel(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	navTab.openTab("delConference", "../events/erpConference!delConference.action?id="+ids+"&navTabId="+navTabId, { title:"删除", fresh:false, data:{} });     
}
*/
$(document).ready(function(){	//页面加载后完成
	var click = "";
		var provinceNo = "";
		var seleProvince = $("input[name='provinceTwo']").val();
		var seleCity = $("input[name='cityTwo']").val();
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			data:{"flag":"flag"},
			dateType:"json",
			url: "${path}/reportdetail/erpPrintBatch!getProvinceRequest.action",
			success: function(data){
				$("#provinceAlready").empty();
				var s= eval("("+data+")");
				var option="<option value=''>---请选择---</option>";
				var provinces = s.province;
				for(var i=0;i<provinces.length;i++){	
					if(seleProvince==provinces[i].provinceid){
						$("input[name='filter_and_province_EQ_S']").val(provinces[i].provinceid);	//给隐藏的input（分页里面生成的）赋value
						option += "<option value='"+provinces[i].provinceid+"' selected='selected'>"+provinces[i].provincename+"</option>";
					}else{
						option += "<option value='"+provinces[i].provinceid+"'>"+provinces[i].provincename+"</option>";
					}
				}
				//将节点插入到下拉列表中
				$("#provinceAlready").append(option);
				
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		if(seleProvince!=""){	//查找以后将选中的市默认选中
			$.ajax({
				type: "post",
				cache :false,
				dateType:"json",
				data:{"provinceNo":seleProvince,"flag":"flag"},
				url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
				success: function(data){
					$("#cityAlready").empty();
					var s= eval("("+data+")");
					var option="<option value=''>---请选择---</option>";
					var citys = s.city;
					if(typeof(citys)!="undefined"){
						for(var j=0;j<citys.length;j++){
							if(seleCity==citys[j].cityid){
								$("input[name='filter_and_city_EQ_S']").val(citys[j].cityid);
								option += "<option value='"+citys[j].cityid+"' selected='selected'>"+citys[j].cityname+"</option>";
							}else{
								option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
							}
						}
					}
					//将节点插入到下拉列表中
					$("#cityAlready").append(option);
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
		//provinceNo = $("#province :selected").val();	//获取加载页面时选中的省份
		$("#provinceAlready").change(function(){
			click = $("#provinceAlready :selected").val();		//获取点击下拉时选中的省份
			if(provinceNo!=click){
				$.ajax({
					type: "post",
					cache :false,
					dateType:"json",
					data:{"provinceNo":click,"flag":"flag"},
					url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
					success: function(data){
						$("#cityAlready").empty();
						var s= eval("("+data+")");
						var option="<option value=''>---请选择---</option>";
						var citys = s.city;
						if(typeof(citys)!="undefined"){
							for(var j=0;j<citys.length;j++){
								option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
							}
						}
						//将节点插入到下拉列表中
						$("#cityAlready").append(option);
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
					data:{"provinceNo":provinceNo,"flag":"flag"},
					url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
					success: function(data){
						$("#cityAlready").empty();
						var s= eval("("+data+")");
						var option="<option value=''>---请选择---</option>";
						var citys = s.city;
						if(typeof(citys)!="undefined"){
							for(var j=0;j<citys.length;j++){
								option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
							}
						}
						//将节点插入到下拉列表中
						$("#cityAlready").append(option);
					},
					error :function(){
						alert("服务发生异常，请稍后再试！");
						return;
					}
				});
			}
		});
		
		
	});

//参数1：接收的数据
//参数2：接收的状态
function getCityCallback(data,status){	//请求成功后回调函数处理
	$("#city2").empty();
	$(data).find("allarea").find("area").each(function(index,ele){
		//解析XML格式数据
		var id = $(ele).find("id").text();
		var title = $(ele).find("title").text();
		
		//创建节点
		var option = $("<option>"+title+"</option>");
		
		//设置节点属性
		option.attr("value",id);
		
		//将节点插入到下拉列表中
		$("#city").append(option);
	});
}
	
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpPrintBatch!llistAlreadyPrintBatch.action'){this.action = '${path}/reportdetail/erpPrintBatch!listAlreadyPrintBatch.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpPrintBatch!listAlreadyPrintBatch.action" method="post"  rel="pagerForm" id="pagerFindForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>批次号：</label> 
					<input type="text" name="filter_and_printBatchNo_LIKE_S" value="${filter_and_printBatchNo_LIKE_S}"/>
				</td>
				
				<td>
					<label>套餐：</label> 
					<input type="text" name="filter_and_combo_LIKE_S" value="${filter_and_combo_LIKE_S}"/>
				</td>
			
				<td><label>起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				</tr>
			<tr>
				<td><label>结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
				</td>
	
				<td><label>场次：</label> 
					<input type="text" name="filter_and_events_LIKE_S" value="${filter_and_events_LIKE_S}"/>
				</td>
				<td>
					<label>支公司：</label> 
					<input type="text" name="filter_and_branchCompany_LIKE_S" bringbackname="customerRelationShip.branchCommany" value="${filter_and_branchCompany_LIKE_S}" readonly="readonly"/>
					<!-- <input type="hidden" id="id" name="conference.branchCompanyId" bringbackname="customerRelationShip.id" value=""/> -->
				  	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a>
				  	<img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/>
				</td>
				</tr>
			<tr>
			
			<tr>
				<td>
					<label>省：</label> 
					<input type="hidden" name="provinceTwo" value="${provinceTwo}"/>
					<input type="hidden" name="cityTwo" value="${cityTwo}"/>
					 <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
					 <%-- <select id="province" name="filter_and_province_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		 			    <option value="${filter_and_province_EQ_S}"></option> 
		 			 </select> --%>
		 			 <select id="provinceAlready" name="filter_and_province_EQ_S" style="margin:5px;width:193px;" >
		 			    <option value="">---请选择---</option>
		 			 </select>
				</td>
				<td>
					<label>市</label>
					<select id="cityAlready" name="filter_and_city_EQ_S" style="margin:5px;width:193px;">
						    <option value="">---请选择---</option>
         			</select>
         			
         		</td>
         		<td>
         		<label></label><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
					</label>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th><input type="checkbox" name="select" id="select1" group="ids" class="checkboxCtrl" onclick="selectall()"/>全选</th>
				<!-- <th width="35">序号</th> -->
				<th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" >批次号</th>
				<th  export= "true" columnEnName = "province" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<th  export = "true" columnEnName = "branchCompany" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
				<th  export = "true" columnEnName = "events" columnChName = "场次" >场次</th>
				<th  export = "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
				<th  export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpPrintBatch" varStatus="status">
					<tr target="sid_user" rel="${erpPrintBatch.id }">
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${erpPrintBatch.id}">
							</c:if>
							${ status.count }
						</td> 
						
						<td align="center">
						<%-- 
						<a title="批次信息" target="navTab" width="1000"  href="${path}/reportdetail/erpPrintBatch!printBatchDetail.action?id=${erpPrintBatch.id}&printBatchNo=${erpPrintBatch.printBatchNo}">${erpPrintBatch.printBatchNo}</a>
						--%>
						${erpPrintBatch.printBatchNo} 
						</td>
						<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
						<td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
						<td align="center"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
		
						<td align="center">	${erpPrintBatch.events}</td>
						<td align="center">	${erpPrintBatch.combo}</td>
						<td align="center">	${erpPrintBatch.count}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 