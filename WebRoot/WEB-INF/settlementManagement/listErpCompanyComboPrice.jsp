<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

/* $(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
	function showCompanyInfo(obj){
		var companyName=obj.parentNode.innerText;
		var companyId = $(obj).attr("companyId");	
	}
}); */

$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
	var idArr = [];
	var id;
	$("input[name='ids']:checked").each(function(i){
		idArr.push($(this).val());
		id = $(this).val();
	});
	
	/* function showCompanyInfo(obj){
		var companyName=obj.parentNode.innerText;
		var companyId = $(obj).attr("companyId");	
	} */
});

$(document).ready(function(){
	$.ajax({	//初始化页面时套餐
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/combo/erpComboPrice!listCombo.action",
		success: function(data){
			$("#comboName").empty();
			var s= eval("("+data+")");
			var option="";
			var combos = s.combo;
			for(var i=0;i<combos.length;i++){		
				option += "<option value='"+combos[i].id+"'>"+combos[i].comboName+"</option>";
			}
			//将节点插入到下拉列表中
			$("select[name='filter_and_comboId_EQ_S']", navTab.getCurrentPanel()).append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
});

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

//跳转新增页面
function add(){
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/settlementManagement/erpCompanyComboPrice!toAddErpCompanyComboPrice.action?navTabId="+navTabId, "addComboPrice", "添加价格套餐",
	{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:800,height:300});
}

//跳转修改页面
function update(obj){
	
	var idArr = [];
	var id;
	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		idArr.push($(this).val());
		id = $(this).val();
	});

	if(idArr.length!=1){
		alertMsg.info("一次只能对一条记录进行修改！");
		return;
	}
	if (!obj.checked) {  
        return;  
    }  
    var tr = obj.parentNode.parentNode;  
    var tds = tr.cells;  
    var str = "you click ";  
    for(var i = 0;i < tds.length;i++){  
        if (i < 3) {  
            str = str + tds[i].innerHTML + "--";  
        }  
    }  
    alert(str);  
//	navTab.openTab("showCompanyInfo", "../settlementManagement/erpCompanyComboPrice!toEditErpCompanyComboPrice.action", { title:"修改套餐价格", fresh:false,data:{} });
	
// 	$.pdialog.open("${path}/settlementManagement/erpCompanyComboPrice!toAddErpCompanyComboPrice.action?navTabId="+navTabId+"&id="+id, "updateComboPrice", "修改价格套餐",
// 	{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:800,height:300});
}
	function editComboPrice(obj){
		var idArr = [];
		var id;
		$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
			idArr.push($(this).val());
			id = $(this).val();
		});

		if(idArr.length>=2){
			alertMsg.info("一次只能对一条记录进行修改！");
			return;
		}else if(idArr.length==0){
			alertMsg.info("请选择要修改的支公司套餐！");
		}else{
			var company=encodeURI($(":checked+a", navTab.getCurrentPanel()).attr("company"));
			var parentCompany=encodeURI($(":checked+a", navTab.getCurrentPanel()).attr("parentCompany"));
			var province=encodeURI($(":checked+a", navTab.getCurrentPanel()).attr("province"));
			var city=encodeURI($(":checked+a", navTab.getCurrentPanel()).attr("city"));
			var id=encodeURI($(":checked+a", navTab.getCurrentPanel()).attr("companyId"));
			var branchCompanyId = $("input:checkbox[name='ids']:checked",navTab.getCurrentPanel()).val();
			navTab.openTab("showCompanyInfo", 
					"../settlementManagement/erpCompanyComboPrice!toEditErpCompanyComboPrice.action?company="+company+"&parentCompany="+parentCompany+"&province="+province+"&city="+city+"&id="+id, 
					{ title:"修改套餐价格", fresh:false,data:{"branchCompanyId":branchCompanyId} });
		}
	}



//批量删除
function delComboBatch(){

	var idArr = [];

	$("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
		idArr.push($(this).val());
	});

	if(idArr.length<1){
		alertMsg.info("请选择要删除的记录！");
		return;
	}
	
	var ids = idArr.join();

	alertMsg.confirm('<span style="color:#FF0000;">确认删除【'+idArr.length+'】条结算任务?</span>', {
			okCall: function(){
				$.ajax({
					url: '../settlementManagement/erpCompanyComboPrice!deleteErpCompanyComboPriceBatch.action',
					method: 'post',
					cache: false,
					data: {'ids':ids},
					success: function(data){
						var resp= eval("("+data+")");
						if(resp.result=='success'){
							alertMsg.correct('删除成功！');
							return navTabSearch(this);
						}else{
							alertMsg.error('删除失败！');
						}
					},
					error: function(resp){
						alertMsg.error('删除失败！');
					}
				}); 
			}		
		});
}	
	
	function showCompanyInfo(obj){
		var companyName=encodeURI(obj.parentNode.innerText);
		var companyId = encodeURI($(obj).attr("companyid"));
//		navTab.dialog("modifyConference", "../events/erpConference!toModifyConference.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} }); 
//		navTab.openTab("showCompanyInfo", "../settlementManagement/erpCompanyComboPrice!showCompanyInfo.action?companyName="+companyName+"&companyId="+companyId, 
//				{ title:companyName+"套餐价格", fresh:false,data:{} });
  		$.pdialog.open("${path}/settlementManagement/erpCompanyComboPrice!showCompanyInfo.action?companyName="+companyName+"&companyId="+companyId, "showCompanyInfo","公司套餐情况",
  			{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:800,height:400});
	}
	
	function getResult(){
		var result=$("input[type='text']", navTab.getCurrentPanel()).map(function(){
			var str = $(this).val();
			var label=$(this).prev().text();
			if(str!=1 && str!=""){
				return label+str;
			}
			
		}).get().join("&");
		$.ajax({
			type:"post",
			cache:false,
			dateType:"json",
			url:"${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action",
			data:{"result":result},
			success: function(resp){
				//return navTabSearch(this);
			},
			error: function(resp){
				
			}
		});
	}
	
</script>

<!-- 	onsubmit="if(this.action != '${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action'){this.action = '${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action' ;} ;return navTabSearch(this);" 
		action="${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action" method="post"	 -->

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" rel="pagerForm" onsubmit="if(this.action != '${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action'){this.action = '${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action' ;} ;return navTabSearch(this);" 
		action="${path}/settlementManagement/erpCompanyComboPrice!listErpCompanyComboPrice.action" method="post"	
		>
	<div class="searchBar">
		<table class="pageFormContent" id="tableId">
			<!-- <tr>
				<td>
					<label>套餐名：</label> 
					<input type="text" name="filter_and_combo_LIKE_S" value="" />
					<select id="comboName" name="filter_and_comboId_EQ_S"  rel="iselect" readonly="readonly">
						<option value=''>---请选择---</option>
		 			</select>
				</td>
				<td>
					<label>套餐价格：</label> 
					<input type="text" name="filter_and_comboPrice_LIKE_S"  value="" />
				</td>
			</tr> -->
			<tr>
				<td>
					<label value="支公司">支公司：</label>
					<input type="text" name="filter_and_company_LIKE_S"  value="${filter_and_company_LIKE_S}" class="condition"/>
				</td>
				<td><label value="起始日期">起始日期：</label> 
					<input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" class="condition" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
				</td>
				<td><label value="结束日期">结束日期：</label> 
					<input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" class="condition" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
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
		<ul class="toolBar" style="margin-left: 15px;">
            <li><a class="edit" id="editCombo" onclick="editComboPrice(this)" ><span>修改套餐</span></a></li>
			<!-- <li style="margin-left:10px;"><a class="add" href="javascript:void(0);"  onclick="add();"><span>增加套餐</span></a></li> -->
			<!-- <li style="margin-left:10px;"><a class="delete" href="javascript:void(0);"  onclick="delComboBatch();" ><span>下架套餐</span></a></li> -->
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="50">序号</th>
				<th width="320" export = "true" columnEnName = "company" columnChName = "支公司" > 支公司 </th> 
				<th  export = "true" columnEnName = "companyId" columnChName = "支公司ID" hidden="hidden" > 支公司ID </th>
				<th width="320" export = "true" columnEnName = "companyBelong" columnChName = "总公司" > 总公司 </th> 
				<th width="100" export = "true" columnEnName = "comboCount" columnChName = "套餐数量 " > 套餐数量 </th> 
				<!-- <th width="360"  export = "true" columnEnName = "combo" columnChName = "套餐名称" > 套餐名称 </th>  -->
				<!-- <th width="120" export = "true" columnEnName = "comboPrice" columnChName = "套餐价格" > 套餐价格(元) </th>  -->
				<th width="200" export = "true" columnEnName = "createUser" columnChName = "创建人姓名" > 创建人姓名 </th> 
				<th width="200" export = "true" columnEnName = "province" columnChName = "省" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao"> 省 </th> 
				<th width="200" export = "true" columnEnName = "city" columnChName = "市" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao"> 市 </th> 
				<th width="200" export = "true" columnEnName = "createTime" columnChName = "创建时间" > 创建时间 </th> 
				<!-- <th  export = "true" columnEnName = "createUserId" columnChName = "创建人ID" > 创建人ID </th> --> 
				<!-- <th  export = "true" columnEnName = "updateTime" columnChName = "修改时间" > 修改时间 </th> 
				<th  export = "true" columnEnName = "updateUserId" columnChName = "修改人ID" > 修改人ID </th> 
				<th  export = "true" columnEnName = "updateUser" columnChName = "修改人姓名" > 修改人姓名 </th> 
				<th  export = "true" columnEnName = "status" columnChName = "状态" > 状态 </th> 
				<th  export = "true" columnEnName = "version" columnChName = "版本号" > 版本号 </th>  -->
				<!-- <th export = "true" columnEnName = "remark" columnChName = "备注" > 备注 </th> 	 -->		
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.rn}" id="rows">
						<td align="center">
							<input type="checkbox" name="ids" value="${entity.companyId}">${status.count}
							<a display="none" company="${entity.company}" ParentCompany="${entity.customerNameSimple}" province="${entity.province }" city="${entity.city }" companyId="${entity.companyId} "></a>
						</td>
						<%-- <td align="center"> ${entity.combo} </td> --%>
						<%-- <td align="center"> ${entity.company} </td> --%>
						<td align="center"> 
							<a style="color: blue;cursor: pointer;"
								companyid="${entity.companyId}" id="companyInfo" onclick="showCompanyInfo(this)"> 
								<hpin:id2nameDB id="${entity.companyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" />	
							</a>
						</td>
						<td align="center" hidden="hidden"> ${entity.companyId} </td>
						<td align="center"> ${entity.customerNameSimple} </td>  <!-- 总公司名字 -->
						<td align="center"> ${entity.comboCount} </td>
						<%-- <td align="center"> ${entity.combo} </td>  --%>
						<td align="center"> ${entity.salesMan} </td>
						<td align="center"> 
							<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.province }" />
						</td>
						<td align="center"> 
							<hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${entity.city }" />
						</td>
						<td align="center"> ${fn:substring(entity.createTime,0,10)} </td>
						<%-- <td align="center"> ${entity.createUserId} </td> --%>
						<%-- <td align="center"> ${entity.updateTime} </td>
						<td align="center"> ${entity.updateUserId} </td>
						<td align="center"> ${entity.updateUser} </td>
						<td align="center"> ${entity.status} </td>
						<td align="center"> ${entity.version} </td> --%>
						<%-- <td align="left"> ${entity.remark} </td> --%>
					
					<%-- 	<td align="center">
							<div class="panelBar">
									<ul class="toolBar">
										<li><a class="edit" href="${path}/combo/erpComboPrice!toModifyErpComboPrice.action?id=${erpComboPrice.id}" target="dialog"><span>更改价格</span></a></li>
									</ul>
							</div>
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 