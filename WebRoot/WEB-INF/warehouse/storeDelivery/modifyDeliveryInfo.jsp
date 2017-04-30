<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	.deliveryDiv{
		height:220px;
 		overflow-y:auto;
	}
	.deliveryDiv2{
		height:120px;
 		overflow-y:auto;
	}
</style>
<script type="text/javascript">
	$(function(){
		var count=$("#count").val();
		if(count==undefined){
			alertMsg.info("没有申请信息");
			$("#delivery2").children().find("button").attr("disabled",true);
		}
	});	
	
	function deliveryThings(obj){
		var num = $.trim($(obj).parent().prev().children().val());	//输入的发货数量
		if(num==""){
			alertMsg.info("请输入发货数量");
			return;
		}
		var count=0;
		var num2=0;		//申请数量
		var type = $(obj).parent().parent().children().eq(2).text();	//获取品类名称
		var name = $(obj).parent().parent().children().eq(3).text();	//获取名称
		var wareHouse = encodeURI($(obj).parent().parent().children().eq(1).text());	//仓库名
		var batchNo = $("input[name='storeApplication.batNo']").val();	//storeTypeId
		var applyed = 0;		//页面上已发货的数量
		var apply = 0;	//页面上申请数量
		$("#delivery1").children().each(function(index,items){
				applyed+=parseInt($(items).children().eq(7).text());
				apply+=parseInt($(items).children().eq(6).text());
			if($(items).children().eq(2).text()==type && $(items).children().eq(3).text()==name){
				num2=parseInt($(items).children().eq(6).text());
				count++;
			};
		});
		if(count==0){
			alertMsg.info("派发申请中没有该品类的物品");
			return;
		}
		applyed=parseInt(applyed)+parseInt(num);
		var flag = 0;
		var typeCode = $.trim($(obj).parent().next().text());
		var id = $.trim($(obj).parent().next().next().text());
		var storeTypeId = $.trim($(obj).parent().next().next().next().text());
		var applyedId = $("#applyedId").val();
		getAppledCount(name,batchNo);
		var applyedCount = $("#applyedCount").val();
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id,"storeTypeId":storeTypeId},
			url: "${path}/warehouse/storeDelivery!getCurrentCount.action",
			success: function(data){
				var count =eval("("+data+")").count;
				if(applyed==apply){//用于区别是否是部分发货
					flag=1;
				}
				if(count<num){
					alertMsg.error("当前剩余库存："+count+",请重新输入");
					return;
				}else if((parseInt(applyedCount)+parseInt(num))>num2){
					alertMsg.info("发货数量超过申请数量,请重新输入");
					return;
				}else{
					$.pdialog.open(
						"${path}/warehouse/storeDelivery!addDeliveryInfo.action?batchNo="+batchNo+"&id="+id+"&typeCode="+typeCode+"&remark="+num+"&wareHouse="+wareHouse+"&storeTypeId="+storeTypeId+"&flag="+flag+"&applyedId="+applyedId, 
						"addDeliveryInfo", "发货信息",{mask:true,mixable:true,minable:true,resizable:true,drawable:true,width:700,height:300});
				}
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
		if (num2 < num) {
			alertMsg.info("发货数量超过申请数量,请重新输入");
			return;
		}
	}
	
	function getAppledCount(name,batNo){		//获取已发货数量
		$.ajax({
			type: "post",
			cache :false,
			async:false,
			dateType:"json",
			data:{"name":name,"batNo":batNo},
			url: "${path}/warehouse/storeDelivery!getApplyedCount.action",
			success: function(data){
				$("#applyedCount").val(eval("("+data+")").count);
 			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
</script>

<div class="tip"  style="width:99.3%;"><span>申请信息</span></div>
<div class="pageHeader"> 
	<%-- <form class="pageForm" onsubmit="if(this.action != '${path}/warehouse/storeDelivery!modifyDeliveryInfo.action?id=${id}&batNo=${batNo}'){this.action = '${path}/warehouse/storeDelivery!modifyDeliveryInfo.action?id=${id}&batNo=${batNo}' ;} ;return navTabSearch(this);"
	 	action="${path}/warehouse/storeDelivery!modifyDeliveryInfo.action?id=${id}&batNo=${batNo}" method="post"> --%>
	    <input type="hidden" name="id" id="applyedId" value="${id}"/><!-- id -->
	    <input type="hidden" name="batNo" id="applyedBatNo" value="${batNo}"/><!-- 批次号 -->
    <!-- </form> -->
    <input type="hidden" id="applyedCount"/><!-- 已发货的数量 -->
        <table class="pageFormContent"  id="applyInfo1">
			<tbody>
				<tr>
					<td>
						<label>总公司名称：</label>
          				<input name="storeApplication.batNo" type="hidden" readonly="readonly"  value="${ storeApplication.batNo }" />
          				<input name="storeApplication.ownedCompany" type="text" readonly="readonly"  value="${ storeApplication.ownedCompany }" />
					</td>
					<td>
						<label>支公司名称：</label>
          				<input name="storeApplication.bannyCompanyName" type="text" readonly="readonly"  value="${ storeApplication.bannyCompanyName }" />
					</td>
					<td>
						<label>收件人：</label>
          				<input name="storeApplication.receiveName" type="text" readonly="readonly"  value="${ storeApplication.receiveName }" />
					</td>
				</tr>
				
				<tr>
					<td>
						<label>项目负责人：</label>
              			<input name="storeApplication.remark1" type="text"    readonly="readonly" value="${ storeApplication.remark1 }" />
					</td>
					<td>
						<label>项目名称：</label><!-- 【发货】页面“申请信息”内容中字段调整：“项目归属”改名为“项目名称” modified by henry.xu 20160905 -->
              			<input name="storeApplication.remark2" type="text"    readonly="readonly" value="${ storeApplication.remark2 }" />
					</td>
					<td>
						<label>项目编码：</label>
              			<input name="storeApplication.remark3" type="text"    readonly="readonly" value="${ storeApplication.remark3 }" />
					</td>
				</tr>
				
				<tr>
					<td>
						<label>联系电话：</label>
          				<input name="storeApplication.receiveTel" type="text" readonly="readonly"  value="${ storeApplication.receiveTel }" />
					</td>
					<td>
						<label>详细地址：</label>
         				<input name="storeApplication.address" type="text" readonly="readonly"  value="${ storeApplication.address }" />
					</td>
					<td>
						<label>期望日期：</label>
          				<input type="text" name="storeApplication.useDate" readonly="readonly"  value="${fn:substring(storeApplication.useDate,0,10)}"/>
					</td>
				</tr>
					
				<tr>
					<td>
						<label>派往角色：</label>
					   	<input name="storeApplication.businessId" type="hidden" value="40289b6a5206079d01520619b31e0008"/>
					   	<input name="storeApplication.businessName" type="hidden" value="远盟本部"/>
					   	<span style="color:red;display:inline-block;margin-top:8px;">远盟基因业务部</span>
					</td>
					<td colspan="2">
						<label style="height: 40px;">需求说明：</label>
           				<textarea cols="30" ows="2" style="width:512px; height:30px;" readonly="readonly" name="storeApplication.requires">${storeApplication.requires}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
</div>	
		<div class="deliveryDiv2">
		<table id="applyInfo2" class="list">
			<thead>
				<tr>
					<th width="50" nowrap="true">序号</th>
					<th width="120" nowrap="true">品类</th>
					<th width="200" nowrap="true">名称</th>
					<th width="200" nowrap="true">规格</th>
					<th width="258" nowrap="true">描述</th>
					<th width="111" nowrap="true">申请数量</th>
					<th width="111" nowrap="true">已发货数量</th>
				</tr>
			</thead>
			
			<tbody id="delivery1">
				<c:forEach items="${applyDetailsList}" var="applyInfo" varStatus="status">
					<tr>
						<td width="50" align="center" nowrap="nowrap" hidden="hidden"><input id="count" type="text" value="${status.count}"/></td>
						<td width="50" align="center" nowrap="nowrap">${status.count}</td>
						<td width="120" align="center" nowrap="nowrap">${applyInfo.typeName}</td>
						<td width="200" align="center" nowrap="nowrap">${applyInfo.prdouceName}</td>
						<td width="200" align="center" nowrap="nowrap">${applyInfo.standards}</td>
						<td width="258" align="center" nowrap="nowrap">${applyInfo.descripe}</td>
						<td width="111" align="center" nowrap="nowrap">${applyInfo.applyNum}</td>
						<td width="111" align="center" nowrap="nowrap">${applyInfo.applyedCount}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
        </div>
        
		<div class="deliveryDiv">
            <table class="list" >
                <thead>
                    <tr>
                        <th size="50" width="100">序号</th>
                        <th size="150" width="100">仓库</th>
                        <th size="100" width="100">品类</th>
                        <th size="200" width="100">名称</th>
                        <th size="200" width="100">规格</th>
                        <th size="100" width="100">当前库存</th>
                        <th size="60" width="100">发货数量</th>
                        <th size="60" width="97">操作</th>
                    </tr>
                </thead>
                <tbody id="delivery2">
                <c:forEach items="${list}" var="wareHouseInfo" varStatus="status">
                    <tr>
						<td width="50" align="center" nowrap="nowrap">${status.count}</td>
						<td width="150" align="center" nowrap="nowrap">${wareHouseInfo.wareHouse}</td>
						<td width="100" align="center" nowrap="nowrap">${wareHouseInfo.type}</td>
						<td width="200" align="center" nowrap="nowrap">${wareHouseInfo.name}</td>
						<td width="200" align="center" nowrap="nowrap">${wareHouseInfo.specifications}</td>
						<td width="100" align="center" nowrap="nowrap">${wareHouseInfo.stock}</td>
						<td width="60" align="center" nowrap="nowrap"><input style="width: 80px;" id="deliveryNum" type="text"/></td>
						<td width="60" align="center" nowrap="nowrap"><button id="bt_send" type="button" onclick="deliveryThings(this)">发货</button></td>
						<td width="100" align="center" nowrap="nowrap" hidden="hidden">${wareHouseInfo.typeCode}</td>
						<td width="100" align="center" nowrap="nowrap" hidden="hidden">${wareHouseInfo.wareHouseId}</td>
						<td width="97" align="center" nowrap="nowrap" hidden="hidden">${wareHouseInfo.storeTypeId}</td>
					</tr>
				</c:forEach>
                </tbody>
            </table>
      </div>
