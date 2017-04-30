<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>

<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpEvents!modifyEvents.action" method="post">
	<input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
	<input type="hidden" id="isUpdateCustDate" name="isUpdateCustDate" value="0"/>					  
	<div class="pageFormContent" layoutH="40" style="overflow: hidden;">
        <h1 class="press">场次信息管理</h1>
        <input type="hidden" name ="events.id" value="${events.id}"/>
        <div class="divider"></div><input id="eventDateStr" type="hidden" name="eventDateStr" value="${eventDateStr}" />
        <div class="tip"><span>场次信息变更</span></div>
        <p>
			<label>场次日期：</label>
			<input id="eventDate" style="width: 170px;" name="events.eventDate" datefmt="yyyy-MM-dd HH:mm:ss"  value="${fn:substring(events.eventDate,0,14)}00:00" type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
      
         <p>
          <label>场次地址：</label>
          <input name="events.address"  type="text" value="${events.address}" />
        </p>
      
		<p>
          <label>预计人数：</label>
          <input name="events.headcount"  class="required number"  type="text" value="${events.headcount }" />
        </p>
       <p>
          <label>批次号：</label>
          <input name="events.batchNo"  type="text" value="${events.batchNo }"  class="required"/>
        </p>
         <p>
          <label>远盟对接人：</label>
          <input name="events.ymSalesman" id="ymSalesman" type="text" class="required" value="${events.ymSalesman}" />
        </p>
        <p>
          <label>级别：</label>
           <select id="level2" name="events.level2"  rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103" refUrl="" class="required">
				<option value="${events.level2}"></option>
			</select>
         
        </p>
          <p>
          <label>场次号：</label>
          <input name="events.eventsNo" id="eventsNo" type="text" class="required" readonly="readonly"  value="${events.eventsNo }" />
        </p>
        <p>
			<label>省份：</label>
			<input type="text"  value="<hpin:id2nameDB id="${events.provice}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly">
			<input type="hidden" name ="events.provice" value="${events.provice}"/>
		</p>
          <input name="events.nowHeadcount" readonly="readonly" type="hidden" value="0" />
       
        
		<p>
			<label>城市：</label>
			<input type="text"    value="<hpin:id2nameDB id="${events.city}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly"/>
			<input type="hidden" name ="events.city" value="${events.city}"/>
			
		</p>
          <p>
			<label>公司名称：</label>
        	<input id="branchCompanyId" name="events.branchCompanyId" bringbackname="customer.id" type="hidden" value="${events.branchCompanyId }"  />
        	<input id="branchCompany" name="events.branchCompany" bringbackname="customer.branchCommanyName" readonly="readonly" class="required textInput" type="text" value="<hpin:id2nameDB id="${events.branchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/>"/>
        	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" callback="pro_callback" lookupGroup="customer">查找带回</a>
			<img src="${path}/images/clear.png" style="cursor: pointer; margin-top: 6px;" title="清除公司信息" id="customerRelation"/>          
        </p>
		<p>
			<label>总公司：</label>
	        <input name="events.ownedCompanyId" bringbackname="customer.ownedCompanyId" type="hidden" value="${events.ownedCompanyId}"/>
	        <input name="events.ownedCompany" bringbackname="customer.ownedCompanyName" readonly="readonly"  type="text" value="<hpin:id2nameDB id="${events.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao"/>"  />
        </p>
		<p>
			<label>项目编码：</label>
        	<input id="customerRelationShipProId" name="events.customerRelationShipProId" bringbackname="customerRelationShipPro.id" type="hidden" value="${events.customerRelationShipProId }"  />
        	<input id="projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${shipPro.projectCode }"/>
        	<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId=${events.branchCompanyId }" callback="combo_callback" lookupGroup="customerRelationShipPro" >查找带回</a>
			<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
		</p>
		<p>
			<label>项目名称：</label>
        	<input id="projectName" bringbackname="customerRelationShipPro.projectName" readonly="readonly" class="required textInput" type="text" value="${shipPro.projectName }"/>
		</p>
		<p>
			<label>项目负责人：</label>
        	<input id="projectOwner" bringbackname="customerRelationShipPro.projectOwner" readonly="readonly" class="required textInput" type="text" value="${shipPro.projectOwner }"/>
		</p>
        
        
        <p>
			<label>入门礼检测套餐：</label>
			<select id="comboByJobNumberId" name="events.comboName" style="width: 194px; margin-top: 4px; margin-left: 6px;">
				<option value="">请选择</option>
			</select> 
		</p>
		<c:if test="${null!=events.nurseNumber}">
	        <p>
				<label>护士人数：</label>
				<input name="events.nurseNumber" id="nurseNumber" type="text" class="required" value="${events.nurseNumber}" />
			</p> 
		</c:if> 
		<div class="divider"></div>
      </div>
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <!-- <button type="submit">更新</button> -->
              <button type="button"  onclick="submitForm()">更新</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
        </li>
        <li>
          <div class="button"><div class="buttonContent"><button type="button" onclick="javascript:history.go(-1)">返回</button></div></div>
        </li>
      </ul>
    </div>
</form>
</div>
<script type="text/javascript">
	function submitForm(){
		var ed=document.getElementById("eventDate").value;
		var year=ed.substring(0,4);
		var month=ed.substring(5,7);
		var day=ed.substring(8,10);
		var hour=ed.substring(11,13);
		var min=ed.substring(14,16);
		
		var eventDateStr=document.getElementById("eventDateStr").value;
		var year1=eventDateStr.substring(0,4);
		var month1=eventDateStr.substring(5,7);
		var day1=eventDateStr.substring(8,10);
		var hour1=eventDateStr.substring(11,13);
		var min1=eventDateStr.substring(14,16);
		/* alert(year==year1);
		alert(month==month1);
		alert(day==day1);
		alert(hour==hour1 );
		alert(min==min1); */
		
		if(year==year1&&month==month1&&day==day1&&hour==hour1&&min==min1){
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}else{
			if(confirm("修改场次日期后是否更改客户采样日期？")){
				document.getElementById("isUpdateCustDate").value="1";
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}else{
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}
		}
		/*		if(year==year1){
		$(".pageForm",navTab.getCurrentPanel()).submit();
	}else{alert(3);
		if(confirm("修改场次日期后是否更改客户采样日期？")){
			document.getElementById("isUpdateCustDate").value="1";
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}else{
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}
	}
	/*	if(!(year==year1 && month==month1 && day==day1 && day==day1 && hour==hour1 && min==min1)){
			if(confirm("修改场次日期后是否更改客户采样日期？")){
				document.getElementById("isUpdateCustDate").value="1";
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}else{
				$(".pageForm",navTab.getCurrentPanel()).submit();
			}
		}else{
			$(".pageForm",navTab.getCurrentPanel()).submit();
		}*/
	}
	
	/*
	 * 回调函数;
	 */
	function pro_callback() {
		
		var branchCompanyId = $("#branchCompanyId").val();
		$("#customerRelshipPro").attr("href", "${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+branchCompanyId);
		$("select[name='events.level2']").attr("refUrl", "${path}/resource/customerRelationShip!findComboByJobNumber.action?companyId="+$("#branchCompanyId").val()+"_");
		//清空数据;
		$("#customerRelationShipProId").val("");
		$("#projectCode").val("");
		$("#projectName").val("");
		$("#projectOwner").val("");
		return ;
	}
	 /**
	 * create by henry.xu 20160920
	 */
	 $(function() {
		 $("#superiorOrgId").click(function(){
				$(this).parent().find("input").each(function(){
					$(this).val("");
				});
			});	
			
			$("#customerRelationPro").click(function() {
				//清空数据;
				$("#customerRelationShipProId").val("");
				$("#projectCode").val("");
				$("#projectName").val("");
				$("#projectOwner").val("");
			}); 
			
			//初始化时加载;
			relaodCombo();
	 });
	
	 /*
		create by henry.xu 20161122;
		* 项目编码选择后触发回调函数
		*/
		function combo_callback(obj) {
			var id = $(obj).attr("id");
			if(id.indexOf("comboByJobNumberId")>-1) {
				relaodCombo()
			}	
				
				
			return;
		}
		
	function relaodCombo() {
		//通过后台获取入门礼检测套餐
		var proId = $("#customerRelationShipProId", navTab.getCurrentPanel()).val();
		//ajax整理
		$.ajax({
			type: "post",
			cache : false,
			async : false,
			data:{"id":proId},
			url: "${path}/resource/customerRelationShip!findAllComboByJobNumber.action",
			success: function(data){
				
				var data= eval("("+data+")");
				var optionStr = "<option value=''>请选择</option>";
				if(data.result != null) {
					for(var i=0; i<data.result.length; i++) {
						if("${events.comboName }"==data.result[i].id) {
							optionStr += "<option value='"+data.result[i].id+"' selected>"+data.result[i].text+"</option>";
							
						} else {
							optionStr += "<option value='"+data.result[i].id+"'>"+data.result[i].text+"</option>";
						}
					}
					
				}
				$("#comboByJobNumberId", navTab.getCurrentPanel()).html("");
				$("#comboByJobNumberId", navTab.getCurrentPanel()).append(optionStr);
			},
			error :function(){
				alertMsg.alert("服务发生异常，请稍后再试！");
				
			}
		});
	}
</script>