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
.show{
	display: none;
}
</style>

<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpEvents!addEvents.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />					  
    <div class="pageFormContent" layoutH="56" style="overflow: hidden;">
        <h1 class="press">场次信息管理</h1>
        <div class="divider"></div>
        <div class="tip"><span>场次信息录入</span></div>
        <table>
        <input name="events.nowHeadcount" readonly="readonly" type="hidden" value="0" />
        	<tr>
        		<td>
					<label>场次日期：</label>
					<!--  onpropertychange="myFunction()" remove by DengYouming 2016-06-01 -->
					<input id="eventDate" style="width: 110px;" name="events.eventDate" datefmt="yyyy-MM-dd"  value="${now }" type="text" class="date required" />
		            <a class="inputDateButton" href="javascript:;">选择</a>
		            <select name ="events.hour" style="margin:6px;">
		            	<option value="9">上午场</option>
		            	<option value="14">下午场</option>
		            	<option value="20">晚场</option>
		            </select>
              </td>
			  <td>
		          <label>场次号：</label>
		          <input name="events.eventsNo" id="eventsNo" type="text" class="required"  readonly="readonly"  value="${events.eventsNo }"  />
        	  </td>
        	  <td></td>
         </tr>
         <tr>
         	<td>
          <label>场次地址：</label>
          <input tabindex="1" name="events.address"  type="text" value="${events.address}"  class="required"  />
        </td>
        <td>
        	<label>公司名称：</label>
        	<input id="branchCompanyId" name="events.branchCompanyId" bringbackname="customer.id" type="hidden" value=""  />
        	<input id="branchCompany" name="events.branchCompany" bringbackname="customer.branchCommanyName" readonly="readonly" class="required textInput" type="text" value=""/>
        	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" callback="pro_callback" lookupGroup="customer">查找带回</a>
			<img src="${path}/images/clear.png" style="cursor: pointer; margin-top: 6px;" title="清除公司信息" id="customerRelation"/>
        </td>
        <td></td>
        </tr>
		<tr>
		  	<td>
	          	<label>预计人数：</label>
	            <input tabindex="2" name="events.headcount" class="required number" type="text" value="${events.headcount }"  />
            </td>
	        <td>
	        	<label>总公司：</label>
	        	<input id="ownedCompanyId" name="events.ownedCompanyId" bringbackname="customer.ownedCompanyId" type="hidden" value=""/>
	        	<input id="ownedCompany" name="events.ownedCompany" bringbackname="customer.ownedCompanyName" readonly="readonly"  type="text" value=""  />
	        </td>
	        <td></td>
        </tr>
        <tr>
        	<td>
        		<label>项目编码：</label>
        		<input id="customerRelationShipProId" name="events.customerRelationShipProId" bringbackname="customerRelationShipPro.id" type="hidden" value=""  />
        		<input id="projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value=""/>
        		<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action" callback="combo_callback" lookupGroup="customerRelationShipPro">查找带回</a>
				<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
        	</td>
        	<td>
        		<label>项目名称：</label>
        		<input id="projectName" bringbackname="customerRelationShipPro.projectName" readonly="readonly" class="required textInput" type="text" value=""/>
        	</td>
        	<td></td>
        </tr>
        <tr>
        
       		<td>
        		<label>项目负责人：</label>
        		<input id="projectOwner" bringbackname="customerRelationShipPro.projectOwner" readonly="readonly" class="required textInput" type="text" value=""/>
        	</td>
       
	        <td>
				<label>省份：</label>
				<span id="show1">
					<select id="province1" name="filter_and_province_EQ_S" value=""
								rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
								ref="city"
								refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
						<option value="${filter_and_province_EQ_S}"></option>
					</select>
				</span>
				<span id="show2">
					<select id="province2" name="filter_and_province_EQ_S"
								rel="iselect" loadUrl="${path}/system/region!getJYRegion.action"
								ref="city"
								refUrl="${path}/system/region!getJYRegionChildren.action?parentId=">
						<option value="${filter_and_province_EQ_S}"></option>
					</select>
				</span>
			</td>
			<td></td>
		</tr>
		
		<tr>
		    <td>
            	<label>级别：</label>
            	<select id="level2" tabindex="3" name="events.level2"
            	rel="iselect" class="required" onclick="getSelectedVal()" 
            	loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103" >
					<option value="${events.level2}"></option>
				</select>
        	</td>
			<td>
				<label>城市：</label>
				<select class="city1" id="city" name="filter_and_city_EQ_S" rel="iselect"
						ref="area"
						refUrl="${path}/system/region!getJYRegionChildren.action?parentId=">>
					<option value="${filter_and_city_EQ_S}"></option>
				</select>
			</td>
			<td>
				<span id="show3">
					<label>区县：</label>
					<select id="area" name="filter_and_area_EQ_S" rel="iselect">
							<option value="${filter_and_area_EQ_S}"></option>
					</select>
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<label>入门礼检测套餐：</label>
				<select id="comboByJobNumberId" name="events.comboName" style="margin-top: 4px; margin-left: 6px; width: 194px;">
					<option value="">请选择</option>
				</select>
				
			</td>
			
			<td>
				 <label>远盟营销员：</label>
          		<input id="ymSalesman" name="events.ymSalesman" bringbackname="customerRelationShipPro.linkName" type="text" class="required"  value="" readonly="readonly"   />
			</td>
			<td>	
				<span id="show4">
					<label>街道：</label>
	          		<input id="address" name="filter_and_address_EQ_S" type="text"/>
          		</span>
			</td>
		</tr>
		<tr>
			<td>
        		<label>批次号：</label>
          		<input tabindex="5" name="events.batchNo" class="required" type="text" value="${events.batchNo }"/>
			</td>
			<td>
				<span id="show7">
					<label>护士人数：</label>
	          		<input id="nurseNumber" tabindex="6" name="events.nurseNumber" type="text" value="${events.nurseNumber}" />
	          	</span>
			</td>
			<td></td>
		</tr>
		</table>
		<div class="divider"></div>
      </div>
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
        </li>
        <li>
          <div class="button"> <div class="buttonContent"><button type="reset">重置</button></div></div>
        </li>
      </ul>
    </div>
</form>
</div>

<script type="text/javascript">
$(function(){
	function myFunction() {
		$.ajax({
			 type: "post", //可选get
			 cache: false,
			 dataType: "text", //服务器返回的数据类型 可选XML ,Json jsonp script html text等
			 url: "${path}/events/erpEvents!createNo.action", //这里是接收数据的程序
			 data: {"data": $("#eventDate").val()} , //传给后台的数据，多个参数用&连接
			 success: function(data) {
				 //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
				 //document.getElementById("eventsNo").val = msg;
				 $("#eventsNo").val(data);
			 },
		     error :function(XMLHttpRequest, textStatus, errorThrown){
		     }
		 	});	 
	}
	myFunction();
});
	
	$(function(){
		$("#show2").addClass("show");//不显示
		$("#show3").addClass("show");
		$("#show4").addClass("show");
		$("#show6").addClass("show");
		$("#show7").addClass("show");
		
		$("#customerRelation", navTab.getCurrentPanel()).click(function(){
			$("#branchCompanyId", navTab.getCurrentPanel()).val("");
			$("#branchCompany", navTab.getCurrentPanel()).val("");
			$("#ownedCompanyId", navTab.getCurrentPanel()).val("");
			$("#ownedCompany", navTab.getCurrentPanel()).val("");
			
		});	
		
		$("#customerRelationPro", navTab.getCurrentPanel()).click(function() {
			//清空数据;
			clearPro();
		});
		
	});
	
	function getSelectedVal(){
		$("select[name='events.level2']").change(function() {
			var text=$("select[name='events.level2'] option:selected").text();
			var province=$("select[name='filter_and_province_EQ_S']");
			if(text.indexOf("癌筛")<0){
				$("#show2").addClass("show").children().find("select").attr("disabled",true);
				$("#show3").addClass("show").children().find("select").attr("disabled",true);
				$("#show4").addClass("show").children().find("select").attr("disabled",true);
				$("#show2").children().find("input").removeClass("required");	//癌筛的省必填
				$("#show3").children().find("input").removeClass("required");//区县必填
				$(".city1").next().removeClass("required")//城市必填
				$("#address").removeClass("required").val("");
				$("#show1").removeClass("show").children().find("select").attr("disabled",false);
				$("#show7").addClass("show");//护士
				$("#nurseNumber").removeClass("required").val("");
			}else{
				$("#show1").addClass("show").children().find("select").attr("disabled",true);	//不是癌筛的省
				$("#show2").removeClass("show").children().find("select").attr("disabled",false);	//癌筛的省
				$("#show3").removeClass("show").children().find("select").attr("disabled",false);	//区县
				$("#show2").children().find("input").addClass("required");	//癌筛的省必填
				$("#show3").children().find("input").addClass("required");//区县必填
				$(".city1").next().addClass("required");//城市必填
				$("#show4").removeClass("show").children().find("select").attr("disabled",false);	//街道
				$("#address").addClass("required");		//街道的input
				$("#show7").removeClass("show");//护士
				$("#nurseNumber").addClass("required");
			}
		});
	}
	
	
	/*
	 * 回调函数;
	 */
	function pro_callback() {
		
		var branchCompanyId = $("#branchCompanyId", navTab.getCurrentPanel()).val();
		$("#customerRelshipPro", navTab.getCurrentPanel()).attr("href", "${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+branchCompanyId);
		$("select[name='events.level2']", navTab.getCurrentPanel()).attr("refUrl", "${path}/resource/customerRelationShip!findComboByJobNumber.action?companyId="+$("#branchCompanyId").val()+"_");
		
		//清空数据;
		clearPro();
		return ;
	}
	/*
	create by henry.xu 20161122;
	* 项目编码选择后触发回调函数
	*/
	function combo_callback(obj) {
		var id = $(obj).attr("id");
		if(id.indexOf("comboByJobNumberId")>-1) {
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
					var optionStr = "";
					
					if(data.result != null) {
						for(var i=0; i<data.result.length; i++) {
							optionStr += "<option value='"+data.result[i].id+"'>"+data.result[i].text+"</option>";
						}
						
					}
					
					$("#comboByJobNumberId", navTab.getCurrentPanel()).append(optionStr);
				},
				error :function(){
					alertMsg.alert("服务发生异常，请稍后再试！");
					
				}
			});
		}	
			
			
		return;
	}
	 
	function clearPro() {
		$("#customerRelationShipProId", navTab.getCurrentPanel()).val("");
		$("#projectCode", navTab.getCurrentPanel()).val("");
		$("#projectName", navTab.getCurrentPanel()).val("");
		$("#projectOwner", navTab.getCurrentPanel()).val("");
		$("#ymSalesman", navTab.getCurrentPanel()).val("");
	}
</script>
