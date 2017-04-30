<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

%>

<style type="text/css">
	.select{
		height:22px; 
		width:193px; 
		margin-top: 4px;
		margin-left:5px;
	}
</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">

$(document).ready(function(){
	$("#customerRelation").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
	});
	
});

function dj(){
	
	var willPrintId=[];
	var printBatchNos="";
	$('input[name="ids"]:checked').each(function(){
		willPrintId.push($(this).val());
		printBatchNos+=$.trim($(this).parent().next().text())+",";
	});
	printBatchNos = printBatchNos.substring(0,printBatchNos.length -1);
	/* alert(willPrintId);
	if(0==willPrintId.length){
		alert("请选择需要打印的批次！");
		$("#printA").attr("href","javascript:void(0);");
		return;
	}else{
		var paramStr = willPrintId.join();
		$("#printA").attr("href","../reportdetail/erpPrintBatch!isPrintBatch.action?ids="+paramStr+"");
	} */
	
	if(0==willPrintId.length){
		alert("请选择需要打印的批次！");
		return;
	}
	var paramStr = willPrintId.join();
	//$.post("../reportdetail/erpPrintBatch!isPrintBatch.action",{ids:paramStr});
	$.pdialog.open("../reportdetail/erpPrintBatch!isPrintBatch.action?ids="+paramStr+"&printBatchNos="+printBatchNos, 
		"printTask", "打印任务",{width:800,height:500,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
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
}
//变更用于单行
function changeProduct2(ids) {
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
    navTab.openTab("modifyConference", "../reportdetail/erpPrintBatch!toModifyPrintBatch.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	
}

$(document).ready(function(){	//页面加载后完成
	var click = "";
	var provinceNo = "";
	var seleProvince = $("input[name='province']",navTab.getCurrentPanel()).val();
	var seleCity = $("input[name='city']",navTab.getCurrentPanel()).val();
	$.ajax({	//初始化页面时的省份
		type: "post",
		cache :false,
		dateType:"json",
		url: "${path}/reportdetail/erpPrintBatch!getProvinceRequest.action",
		success: function(data){
			$("#province",navTab.getCurrentPanel()).empty();
			var s= eval("("+data+")");
			var option="<option value=''>---请选择---</option>";
			var provinces = s.province;
			for(var i=0;i<provinces.length;i++){	
				if(seleProvince==provinces[i].provinceid){
					$("input[name='filter_and_province_EQ_S']",navTab.getCurrentPanel()).val(provinces[i].provinceid);	//给隐藏的input（分页里面生成的）赋value
					option += "<option value='"+provinces[i].provinceid+"' selected='selected'>"+provinces[i].provincename+"</option>";
				}else{
					option += "<option value='"+provinces[i].provinceid+"'>"+provinces[i].provincename+"</option>";
				}
			}
			//将节点插入到下拉列表中
			$("select[name='filter_and_province_EQ_S']",navTab.getCurrentPanel()).append(option);
			
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
			data:{"provinceNo":seleProvince},
			url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
			success: function(data){
				$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).empty();
				var s= eval("("+data+")");
				var option="<option value=''>---请选择---</option>";
				var citys = s.city;
				if(typeof(citys)!="undefined"){
					for(var j=0;j<citys.length;j++){
						if(seleCity==citys[j].cityid){
							$("input[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).val(citys[j].cityid);
							option += "<option value='"+citys[j].cityid+"' selected='selected'>"+citys[j].cityname+"</option>";
						}else{
							option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
						}
					}
				}
				//将节点插入到下拉列表中
				$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).append(option);
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	//provinceNo = $("#province :selected").val();	//获取加载页面时选中的省份
	$("select[name='filter_and_province_EQ_S']",navTab.getCurrentPanel()).change(function(){
		click = $("select[name='filter_and_province_EQ_S'] :selected",navTab.getCurrentPanel()).val();		//获取点击下拉时选中的省份
		if(provinceNo!=click){
			$.ajax({
				type: "post",
				cache :false,
				dateType:"json",
				data:{"provinceNo":click},
				url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
				success: function(data){
					$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).empty();
					var s= eval("("+data+")");
					var option="<option value=''>---请选择---</option>";
					var citys = s.city;
					if(typeof(citys)!="undefined"){
						for(var j=0;j<citys.length;j++){
							option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
						}
					}
					//将节点插入到下拉列表中
					$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).append(option);
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
				data:{"provinceNo":provinceNo},
				url: "${path}/reportdetail/erpPrintBatch!getCityRequest.action",
				success: function(data){
					$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).empty();
					var s= eval("("+data+")");
					var option="<option value=''>---请选择---</option>";
					var citys = s.city;
					if(typeof(citys)!="undefined"){
						for(var j=0;j<citys.length;j++){
							option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
						}
					}
					//将节点插入到下拉列表中
					$("select[name='filter_and_city_EQ_S']",navTab.getCurrentPanel()).append(option);
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	});
	getSelectedInput();//设置套餐和总公司下拉框
	var ownedCompany = $("#owedText",navTab.getCurrentPanel()).val();
	if(ownedCompany!=""){
		var arr1 = new Array();
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/erpPrintBatch!setBranchCompanySelectedInput.action",
			data:{"ownedCompany":ownedCompany},
			success: function(data){
				var company = $("#companyText",navTab.getCurrentPanel()).val();
				$("#companySel",navTab.getCurrentPanel()).empty();
				var option="<option value=''>---请选择---</option>";
				var companys = eval("("+data+")").branchCompany;
				for(var i=0;i<companys.length;i++){	
					if(company==companys[i].branchCompanyId){
						if(arr1.indexOf(companys[i].branchCompanyId)==-1){
							arr1.push(companys[i].branchCompanyId);
							option += "<option value='"+companys[i].branchCompanyId+"' selected='selected'>"+companys[i].branchCompanyId+"</option>";
						}
					}else{
						if(arr1.indexOf(companys[i].branchCompanyId)==-1){
							arr1.push(companys[i].branchCompanyId);
							option += "<option value='"+companys[i].branchCompanyId+"'>"+companys[i].branchCompanyId+"</option>";
						}
					}
				}
					//将节点插入到下拉列表中
					$("#companySel",navTab.getCurrentPanel()).append(option);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
});

function importMoreCodes(){
	var filePath = $("#affix",navTab.getCurrentPanel()).val();
	if ("" == filePath) {
		alertMsg.info("请先选择文件!");
		return ;
	}
	py_show();
	$.ajaxFileUpload({
		url:'${path}/report/reportFileTask!importCodesByExcel.action',
		type:'post',
		secureuri: false,
		fileElementId: 'affix',
		dataType: 'json',
		data:{"filePath":filePath},
		success:function(data,status){
			if(data.result=="success"){
				alertMsg.correct("导入成功！");
				$("#queryCodes",navTab.getCurrentPanel()).val(data.codes);
				py_hide();
			}else{
				alertMsg.warn("导入失败！");
				py_hide();
			}
		},
		error:function (data,status,e){
			alertMsg.error("服务发生异常，请稍后再试！");
			py_hide();
		}
	});	
}
	
	function getSelectedInput(){
		var combo = $("#comboText",navTab.getCurrentPanel()).val();			//套餐
		var ownedCompany = $("#owedText",navTab.getCurrentPanel()).val();	//所属公司
		var dept = $("#dept",navTab.getCurrentPanel()).val();				//部门
		var reportType = $("#reportType",navTab.getCurrentPanel()).val();			//报告类型
		var arr1 = new Array();
		var arr2 = new Array();
		var arr3 = new Array();
		var arr4 = new Array();
		
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/erpPrintBatch!setSelectedInput.action",
			success: function(data){
				$("#owedSel",navTab.getCurrentPanel()).empty();
				var option1="<option value=''>---请选择---</option>";	//总公司
				var option2="<option value=''>---请选择---</option>";	//套餐
				var deptOption = "<option value=''>---请选择---</option>";//部门
				var reportTypeOption = "<option value=''>---请选择---</option>";//报告类型
				var oweds = eval("("+data+")").selectedData;
				var report = "";
				for(var i=0;i<oweds.length;i++){	
					//支公司
					if(ownedCompany==oweds[i].ownedCompany){
						if(arr1.indexOf(oweds[i].ownedCompany)==-1){
							arr1.push(oweds[i].ownedCompany);
							option1 += "<option value='"+oweds[i].ownedCompany+"' selected='selected'>"+oweds[i].ownedCompany+"</option>";
						}
					}else{
						if(arr1.indexOf(oweds[i].ownedCompany)==-1){
							arr1.push(oweds[i].ownedCompany);
							option1 += "<option value='"+oweds[i].ownedCompany+"'>"+oweds[i].ownedCompany+"</option>";
						}
					}
					//套餐
					if(combo==oweds[i].combo){
						if(arr2.indexOf(oweds[i].combo)==-1){
							arr2.push(oweds[i].combo);
							option2 += "<option value='"+oweds[i].combo+"' selected='selected'>"+oweds[i].combo+"</option>";
						}
					}else{
						if(arr2.indexOf(oweds[i].combo)==-1){
							arr2.push(oweds[i].combo);
							option2 += "<option value='"+oweds[i].combo+"'>"+oweds[i].combo+"</option>";
						}
					}
					//部门
					if(dept==oweds[i].dept){
						if(arr2.indexOf(oweds[i].dept)==-1){
							arr2.push(oweds[i].dept);
								deptOption += "<option value='"+oweds[i].dept+"' selected='selected'>"+oweds[i].dept+"</option>";
						}
					}else{
						if(arr2.indexOf(oweds[i].dept)==-1){
							arr2.push(oweds[i].dept);
								deptOption += "<option value='"+oweds[i].dept+"'>"+oweds[i].dept+"</option>";
						}
					};
					//报告类型
					if(reportType===oweds[i].reportType){
						if(arr4.indexOf(oweds[i].reportType)==-1){
							arr4.push(oweds[i].reportType);
							if(oweds[i].reportType==0){
								reportTypeOption += "<option value='"+oweds[i].reportType+"' selected='selected'>基因报告</option>";
							}else if(oweds[i].reportType==1){
								reportTypeOption += "<option value='"+oweds[i].reportType+"' selected='selected'>癌筛报告</option>";
							}else if(oweds[i].reportType==2){
								reportTypeOption += "<option value='"+oweds[i].reportType+"' selected='selected'>1+X报告</option>";
							}else if(oweds[i].reportType==3){
								reportTypeOption += "<option value='"+oweds[i].reportType+"' selected='selected'>无创报告</option>";
							}
						}
					}else{
						if(arr4.indexOf(oweds[i].reportType)==-1){
							arr4.push(oweds[i].reportType);
							if(oweds[i].reportType==0){
								reportTypeOption += "<option value='"+oweds[i].reportType+"'>基因报告</option>";
							}else if(oweds[i].reportType==1){
								reportTypeOption += "<option value='"+oweds[i].reportType+"'>癌筛报告</option>";
							}else if(oweds[i].reportType==2){
								reportTypeOption += "<option value='"+oweds[i].reportType+"'>1+X报告</option>";
							}else if(oweds[i].reportType==3){
								reportTypeOption += "<option value='"+oweds[i].reportType+"'>无创报告</option>";
							}
						}
					}
						
				}	
					
					//将节点插入到下拉列表中
					$("#owedSel",navTab.getCurrentPanel()).append(option1);
					$("#comboSel",navTab.getCurrentPanel()).append(option2);
					$("#deptSel",navTab.getCurrentPanel()).append(deptOption);
					$("#reportTypeSel",navTab.getCurrentPanel()).append(reportTypeOption);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	$("#companySel").change( function() {	//支公司
		var selectVal=$("#companySel option:selected",navTab.getCurrentPanel()).val();
		$("#companyText",navTab.getCurrentPanel()).val(selectVal);
	});
	$("#comboSel").change( function() {	//套餐
		var selectVal=$("#comboSel option:selected",navTab.getCurrentPanel()).val();
		$("#comboText",navTab.getCurrentPanel()).val(selectVal);
	});
	$("#reportTypeSel").change( function() {	//报告类型
		var selectVal=$("#reportTypeSel option:selected",navTab.getCurrentPanel()).val();
		$("#reportType",navTab.getCurrentPanel()).val(selectVal);
	});
	$("#city").change( function() {	//根据城市获取对应的所属公司
		var selectVal=$("#city option:selected",navTab.getCurrentPanel()).val();
		var city = $("input[name='city']",navTab.getCurrentPanel());
		$(city).val(selectVal);
		var arr = new Array();
		$.ajax({	
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/erpPrintBatch!setOwnedCompanyByCity.action",
			data:{"city":$(city).val()},
			success: function(data){
				var ownedCompany = $("#owedText",navTab.getCurrentPanel()).val();
				$("#owedSel",navTab.getCurrentPanel()).empty();
				var option="<option value=''>---请选择---</option>";
				var ownedCompanys = eval("("+data+")").ownedCompany;
				for(var i=0;i<ownedCompanys.length;i++){	
					if(ownedCompany==ownedCompanys[i].ownedCompany){
						if(arr.indexOf(ownedCompanys[i].ownedCompany)==-1){
							arr.push(ownedCompanys[i].ownedCompany);
							option += "<option value='"+ownedCompanys[i].ownedCompany+"' selected='selected'>"+ownedCompanys[i].ownedCompany+"</option>";
						}
					}else{
						if(arr.indexOf(ownedCompanys[i].ownedCompany)==-1){
							arr.push(ownedCompanys[i].ownedCompany);
							option += "<option value='"+ownedCompanys[i].ownedCompany+"'>"+ownedCompanys[i].ownedCompany+"</option>";
						}
					}
				}
					//将节点插入到下拉列表中
					$("#owedSel",navTab.getCurrentPanel()).append(option);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	});
	
	$("#owedSel").change( function() {	//总公司
		var selectVal=$("#owedSel option:selected",navTab.getCurrentPanel()).val();
		$("#owedText",navTab.getCurrentPanel()).val(selectVal);
		var arr = new Array();
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			url: "${path}/reportdetail/erpPrintBatch!setBranchCompanySelectedInput.action",
			data:{"ownedCompany":$("#owedText",navTab.getCurrentPanel()).val()},
			success: function(data){
				var company = $("#companyText",navTab.getCurrentPanel()).val();
				$("#companySel",navTab.getCurrentPanel()).empty();
				var option="<option value=''>---请选择---</option>";
				var companys = eval("("+data+")").branchCompany;
				for(var i=0;i<companys.length;i++){	
					if(company==companys[i].branchCompanyId){
						if(arr.indexOf(companys[i].branchCompanyId)==-1){
							arr.push(companys[i].branchCompanyId);
							option += "<option value='"+companys[i].branchCompanyId+"' selected='selected'>"+companys[i].branchCompanyId+"</option>";
						}
					}else{
						if(arr.indexOf(companys[i].branchCompanyId)==-1){
							arr.push(companys[i].branchCompanyId);
							option += "<option value='"+companys[i].branchCompanyId+"'>"+companys[i].branchCompanyId+"</option>";
						}
					}
				}
					//将节点插入到下拉列表中
					$("#companySel",navTab.getCurrentPanel()).append(option);
				},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	});
</script>
<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpPrintBatch!listPrintBatch.action'){this.action = '${path}/reportdetail/erpPrintBatch!listPrintBatch.action' ;} ;return navTabSearch(this);" action="${path}/reportdetail/erpPrintBatch!listPrintBatch.action" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td><label>套餐：</label></td>
				<td>
					<input type="text" name="filter_and_combo_LIKE_S" id="comboText" key="" value="${filter_and_combo_LIKE_S}" hidden="hidden"/> 
					<select id="comboSel" class="select"></select>
				</td>
				<td><label>所属公司：</label></td>
				<td>
					<input type="text" name="filter_and_ownedCompany_EQ_S" id="owedText" key="" value="${filter_and_ownedCompany_EQ_S}" hidden="hidden"/> 
					<select id="owedSel" class="select"></select>
				</td>
				<td><label>支公司：</label></td>
				<td>
					<input type="text" name="filter_and_branchCompanyId_EQ_S" id="companyText" value="${filter_and_branchCompanyId_EQ_S}" hidden="hidden"/> 
					<select id="companySel" class="select"></select>
				</td>
			</tr>
			
			<tr>
				<td><label>批次号：</label></td>
				<td><input type="text" name="filter_and_printBatchNo_LIKE_S" value="${filter_and_printBatchNo_LIKE_S}"/></td>
				<td hidden="hidden">
					<input type="hidden" name="province" value="${province}"/>
					<input type="hidden" name="city" value="${city}"/>
				</td>
				<td><label>省：</label></td>
				<td><select id="province" name="filter_and_province_EQ_S" style="margin:5px;width:193px;"><option value="">---请选择---</option></select></td>
				<td><label>市：</label></td>
				<td><select id="city" name="filter_and_city_EQ_S" style="margin:5px;width:193px;"><option value="">---请选择---</option></select></td>
			</tr>
			
			<tr>
				<td><label>部门：</label></td>
				<td hidden="hidden"><input id="dept" name="dept" value="${dept}"/></td>
				<td><select id="deptSel" name="filter_and_dept_EQ_S" style="margin:5px;width:193px;"></select></td>
				<td><label>起始日期：</label></td>
				<td><input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a></td>
				<td><label>结束日期：</label></td>
				<td><input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a></td>
				</td>
			</tr>
			
			<tr>
				<td><label>条形码:</label></td>
				<td><input id="queryCodes" type="text" name="queryCodes" value="${queryCodes}" readonly="readonly" /></td>
				<td colspan="2" align="left">
					<input type="file" id="affix" name="affix"/>
					<button type="button" onclick="importMoreCodes()" style="float:right;height: 24px;line-height:100%;">导入条码</button>
				</td>
				<td>
					<label>报告类型:</label>
				</td>
				<td>
					<input id="reportType" name="filter_and_reportType_EQ_I" type="hidden" value="${filter_and_reportType_EQ_I}"/>
					<select id="reportTypeSel"  class="select"></select>
				</td>
				<td colspan="2" style="padding-left:30px;">
					<div class="buttonActive" style="float:right;"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive" style="float:right;"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<c:if test="${not empty noPrintCode}">
	<div style="margin-left:15px;"><span>以下条码未分配打印批次：</span><span style="color:red">${noPrintCode}</span></div>
	</c:if>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
            <li>
				<a class="add" href="javascript:void(0)" onclick="dj()"><span>打印任务</span></a>
            </li>
		</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>	
	<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
			<thead>
			<tr>
				<th width="50" nowrap="true">全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
				
				<!-- <th width="35">序号</th> -->
				<th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" width="120" nowrap="true" >批次号</th>
				<th  export = "true" columnEnName = "createTime" columnChName = "创建日期" width="120" nowrap="true" >创建日期</th>
				<th  export= "true" columnEnName = "province" columnChName = "省" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao" width="60" nowrap="true">省</th>
				<th  export= "true" columnEnName = "city" columnChName = "市" 
					id2NameBeanId="org.hpin.base.region.dao.RegionDao" width="60" nowrap="true">市</th>
				<th  export = "true" columnEnName = "branchCompany" 
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司"  width="200" nowrap="true">支公司</th>
				<th  export = "true" columnEnName = "ownedCompany" 
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "所属公司"  width="160" nowrap="true">所属公司</th>
				<th  export = "true" columnEnName = "dept" columnChName = "部门"  width="80" nowrap="true">部门</th>
				<th  export = "true" columnEnName = "reportType" columnChName = "报告类型"  width="80" nowrap="true">报告类型</th>
				<th  export = "true" columnEnName = "combo" columnChName = "套餐"  width="80" nowrap="true">套餐</th>
				<th  export = "true" columnEnName = "count" columnChName = "数量"  width="60" nowrap="true">数量</th>
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
						
						<td align="center" width="150" nowrap="true">
							<%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/events/erpCustomer!toConferenceNoListCustomer.action?showId=${erpPrintBatch.id}">${erpPrintBatch.printBatchNo}</a> --%>
							<a title="批次信息" target="navTab" href="${path}/reportdetail/erpPrintBatch!printBatchDetail.action?id=${erpPrintBatch.id}&printBatchNo=${erpPrintBatch.printBatchNo}">${erpPrintBatch.printBatchNo}</a>
						<%-- ${erpPrintBatch.printBatchNo} --%>
						</td>
						<td align="center" width="120" nowrap="true">${fn:substring(erpPrintBatch.createTime,0,19)}</td>
						<td align="center" width="60" nowrap="true"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
						<td align="center" width="60" nowrap="true"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
 						<td align="center" width="200" nowrap="true"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
 						<td align="center" width="160" nowrap="true"><hpin:id2nameDB id="${erpPrintBatch.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
						<td align="center" width="80" nowrap="true">${erpPrintBatch.dept=="无"?"":erpPrintBatch.dept}</td>
						<td align="center" width="80" nowrap="true">
							<c:if test="${erpPrintBatch.reportType==0}">基因报告</c:if>
							<c:if test="${erpPrintBatch.reportType==1}">癌筛报告</c:if>
							<c:if test="${erpPrintBatch.reportType==2}">1+X报告</c:if>
							<c:if test="${erpPrintBatch.reportType==3}">无创报告</c:if>
							<c:if test="${erpPrintBatch.reportType==4}">微磁报告</c:if>
						</td>
						<td align="center" width="80" nowrap="true">${erpPrintBatch.combo}</td>
						<td align="center" width="60" nowrap="true">${erpPrintBatch.count}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div> 