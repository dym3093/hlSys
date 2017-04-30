<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">

	//清除查找框中的所有内容
	$("#clearText",$.pdialog.getCurrent()).click(function(){
		$("#pagerFindForm",$.pdialog.getCurrent()).find("input").each(function(){
			if($(this).attr("name")!="configId"){
				$(this).val("");
			}
		});
		$("#pagerFindForm",$.pdialog.getCurrent()).find("select").each(function(){
			$(this).val("");
		});
	});	

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
	
	var willPrintId=[];
	$('input[name="ids"]:checked').each(function(){
		willPrintId.push($(this).val());
	});
	if(0==willPrintId.length){
		alert("请选择需要打印的批次！");
		return;
	}
	var paramStr = willPrintId.join();
	$("#printA").attr("href","../reportdetail/erpPrintBatch!isPrintBatch.action?ids="+paramStr+"");
	
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
	var province = "";
	$.ajax({	//初始化页面时的省份
		type: "post",
		cache :false,
		dateType:"json",
		url: "../reportdetail/erpPrintBatch!getProvinceRequest.action",
		success: function(data){
			$("#province").empty();
			var s= eval("("+data+")");
			var option="";
			var provinces = s.province;
			for(var i=0;i<provinces.length;i++){
				option += "<option value='"+provinces[i].provinceid+"'>"+provinces[i].provincename+"</option>";
			}
			//将节点插入到下拉列表中
			$("#province").append(option);
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	provinceNo = $("#province :selected").val();	//获取加载页面时选中的省份
	$("#province").click(function(){
		click = $("#province :selected").val();		//获取点击下拉时选中的省份
		if(provinceNo!=click){
			$.ajax({
				type: "post",
				cache :false,
				dateType:"json",
				data:{"provinceNo":click},
				url: "../reportdetail/erpPrintBatch!getCityRequest.action",
				success: function(data){
					$("#city").empty();
					var s= eval("("+data+")");
					var option="";
					var citys = s.city;
					if(typeof(citys)!="undefined"){
						for(var j=0;j<citys.length;j++){
							option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
						}
					}
					//将节点插入到下拉列表中
					$("#city").append(option);
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
				url: "../reportdetail/erpPrintBatch!getCityRequest.action",
				success: function(data){
					$("#city").empty();
					var s= eval("("+data+")");
					var option="";
					var citys = s.city;
					if(typeof(citys)!="undefined"){
						for(var j=0;j<citys.length;j++){
							option += "<option value='"+citys[j].cityid+"'>"+citys[j].cityname+"</option>";
						}
					}
					//将节点插入到下拉列表中
					$("#city").append(option);
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	});
	
	
});


//add by dengym

//添加批次到打印任务
function addBatchToPrintTask(){
	var printTaskId = $("#taskID").val();
	
	var printTaskNo = $("#taskNO").val();
	
	var printBatchIds = [];

	$('input:checkbox[name="ids"]:checked').each(function(n){
		   printBatchIds.push($(this).val());
	});

	var paramStr = printBatchIds.join();

//	alert("printBatchIds:"+printBatchIds);
	$.ajax({
		type: "post",
		cache :false,
		dateType:"json",
		data:{"printTaskId":printTaskId,"printTaskNo":printTaskNo,"printBatchIds":paramStr},
		url: "../reportdetail/erpPrintTask!addBatchToPrintTask.action",
		success: function(data){
			var resp = eval('('+data+')');
			if(resp.result=='success'){
				alertMsg.correct("保存成功！");
				$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alertMsg.error("保存失败！");
			}
			
		},
		error :function(data){
			alertMsg.error("保存失败！");
		}
	});
}

	
</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
  <form id="pagerFindForm" onsubmit="if(this.action != '${path}/reportdetail/erpPrintBatch!listUnPrintBatch.action'){this.action = '${path}/reportdetail/erpPrintBatch!listUnPrintBatch.action' ;} ;return dialogSearch(this);" action="${path}/reportdetail/erpPrintBatch!listUnPrintBatch.action" method="post"  rel="pagerForm" id="pagerFindForm">
  <div class="searchBar">
    <input type="hidden" id="taskID" name="id" value="${printTaskId}"/>
    <input type="hidden" id="taskNO" name="printTaskNo" value="${printTaskNo}"/>
    <table class="pageFormContent">
      <tr>
        <td><label>批次号：</label>
          <input type="text" name="filter_and_printBatchNo_LIKE_S" value="${filter_and_printBatchNo_LIKE_S}"/></td>
        <td><label>套餐：</label>
          <input type="text" name="filter_and_combo_LIKE_S" value="${filter_and_combo_LIKE_S}"/></td>
      </tr>
      <tr>
        <td><label>起始日期：</label>
          <input type="text" name="filter_and_createTime_GEST_T"  id="d1" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"  readonly="readonly" value="${filter_and_createTime_GEST_T}" />
          <a class="inputDateButton" href="javascript:;" >选择</a></td>
        <td><label>结束日期：</label>
          <input type="text" name="filter_and_createTime_LEET_T" id="d2" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})" readonly="readonly" value="${filter_and_createTime_LEET_T}" />
          <a class="inputDateButton" href="javascript:;">选择</a></td>
      </tr>
      <tr>
        <td><label>场次：</label>
          <input type="text" name="filter_and_events_LIKE_S" value="${filter_and_events_LIKE_S}"/></td>
        <td><label>支公司：</label>
          <input type="text" name="filter_and_branchCompany_LIKE_S" bringbackname="customerRelationShip.branchCommany" value="${filter_and_branchCompany_LIKE_S}" readonly="readonly"/>
          
          <!-- <input type="hidden" id="id" name="conference.branchCompanyId" bringbackname="customerRelationShip.id" value=""/> --> 
          <a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShipTwo.action" lookupGroup="customerRelationShip">查找带回</a> <img src="${path}/images/clear.png" title="清除公司信息" id="customerRelation" style="padding-top: 6px;"/></td>
      </tr>
      <tr>
        <td><label>省： </label>
          <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
          <%-- <select id="province" name="filter_and_province_EQ_S" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		 			    <option value="${filter_and_province_EQ_S}"></option> 
		 			 </select> --%>
          <select id="province" name="filter_and_province_EQ_S" rel="iselect" style="width:190px;margin:5px;">
            <option value="${filter_and_province_EQ_S}"></option>
          </select></td>
        <td><label>市：</label>
          <select id="city" name="filter_and_city_EQ_S" rel="iselect" style="width:190px;margin:5px;">
            <option value="">---请选择---</option>
            <option value="${filter_and_city_EQ_S}"></option>
          </select></td>
        <td><div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">查找</button>
            </div>
          </div>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="button" id="clearText">重置</button>
            </div>
          </div></td>
      </tr>
    </table>
  </div>
  </form>
</div>
<div class="pageContent">
  <div class="panelBar">
    <c:if test="${currentUser.accountName!='parkson'}">
      <ul class="toolBar">
        <li><a class="delete" id="printA" href="javascript:void(0);" onclick="addBatchToPrintTask()"><span>确认添加</span></a></li>
        <!-- target="dialog" <button onclick="dj()">打印任务</button>  -->
      </ul>
    </c:if>
  </div>
  <table class="list" width="100%" layoutH="170" id="exportExcelTable">
    <thead>
      <tr>
        <th>全选
          <input type="checkbox" name="select" id="select1" onclick="selectall()"/></th>
        <!-- <th width="35">序号</th> -->
        <th  export = "true" columnEnName = "printBatchNo" columnChName = "批次号" >批次号</th>
        <th  export= "true" columnEnName = "province" columnChName = "省" id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
        <th  export= "true" columnEnName = "city" columnChName = "市" id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
        <th  export = "true" columnEnName = "branchCompany" id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao" columnChName = "支公司" >支公司</th>
        <!--<th  export = "true" columnEnName = "events" columnChName = "场次" >场次</th>-->
        <th  export = "true" columnEnName = "combo" columnChName = "套餐" >套餐</th>
        <th  export = "true" columnEnName = "count" columnChName = "数量" >数量</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${page.results}" var="erpPrintBatch" varStatus="status">
        <tr target="sid_user" rel="${erpPrintBatch.id }">
          <td align="center"><c:if test="${currentUser.accountName!='parkson'}">
              <input type="checkbox" name="ids" value="${erpPrintBatch.id}">
            </c:if>
            ${ status.count } </td>
          <td align="center">
            <%-- <a title="批次信息" target="navTab" width="1000"  href="${path}/reportdetail/erpPrintBatch!printBatchDetail.action?id=${erpPrintBatch.id}&printBatchNo=${erpPrintBatch.printBatchNo}">${erpPrintBatch.printBatchNo}</a> --%>
            ${erpPrintBatch.printBatchNo}</td>
          <td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.province }"/></td>
          <td align="center"><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${erpPrintBatch.city }"/></td>
          <td align="center"><hpin:id2nameDB id="${erpPrintBatch.branchCompany}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
          <td align="center"> ${erpPrintBatch.combo}</td>
          <td align="center"> ${erpPrintBatch.count}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
