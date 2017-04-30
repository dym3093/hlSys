<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/application!saveOrUpdate.action" method="post" >
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="application.id" value="${application.id }" />
    <input type="hidden" id="fromsubmit" value="0" />
    <div class="pageFormContent" layoutH="5" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
        <table width="100%">
        	<tbody>
        		<tr>
        			<td style="text-align: right;">总公司名称：</td>
        			<td style="text-align: left">
        				${application.ownerCompanyName }
        			</td>
        			<td style="text-align: right;">支公司名称：</td>
        			<td style="text-align: left">
        				${application.bannyCompanyName }
        			</td>
        			<td style="text-align: right;">项目编码：</td>
        			<td style="text-align: left">
			       		${application.projectCode }
        			</td>
        			<td style="text-align: right;">项目名称：</td>
        			<td style="text-align: left">
        				${application.projectName }
        			</td>
        		</tr>
        		<tr>
        			
        			<td style="text-align: right;">项目负责人：</td>
        			<td style="text-align: left">
        				${application.projectOwner }
        			</td>
        			<td style="text-align: right;">项目对接人：</td>
        			<td style="text-align: left">
        				${application.linkName }
        			</td>
        			<td style="text-align: right;">对接人电话：</td>
        			<td style="text-align: left">
        				${application.linkTel }
        			</td>
        			<td style="text-align: right;">收件人姓名：</td>
        			<td style="text-align: left">
        				${application.receiveName }
        			</td>
        		</tr>
        		<tr>
        			
        			<td style="text-align: right;">收件人联系电话：</td>
        			<td style="text-align: left">
        				${application.receiveTel }
        			</td>
        			<td style="text-align: right;">期望日期：</td>
        			<td style="text-align: left">
        				<fmt:formatDate value='${application.hopeDate}' pattern='yyyy-MM-dd'/>
        			</td>
        			<td style="text-align: right;">寄送地址：</td>
        			<td colspan="3" style="text-align: left">${application.address }</td>
        		</tr>
        		<tr>
        			<td style="text-align: right;">需求说明：</td>
        			<td colspan="7" style="text-align: left">
        				${application.requirement}
        			</td>
        		</tr>
        		<tr>
        			<td><label style="height: auto">派往角色：</td>
        			<td colspan="5" style="text-align: left">
					   <span style="color:red">远盟基因业务部</span>
        			</td>
        		</tr>
        	</tbody>
        </table>
        
      	<br/>
      	<!--
      	<dl class="nowrap">
			<dt style="background-color: #fff">选择品类：</dt>
			<dd>
				 <a class="button" href="javascript:void(0);" onclick="addProduct();"><span>添加产品信息</span></a>
			</dd>
		</dl> -->
      	<br/>
    	<fieldset id="applyInfo" style="width:100%;">
    		<div class="tip"><span>申请产品列表</span></div>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="60px">序号</th>
						<th width="120px">产品套餐名称</th>
						<th width="180px">品类名称</th>
						<th width="180px">产品类别</th>
						<th>产品规格</th>
						<th width="180px">申请数量</th>
						<th width="100px">发货数量</th>
					</tr>					
				</thead>
				<tbody id="detail_tbody_id">
					<c:forEach items="${details }" var="item" varStatus="stat">
						<tr typeName="${item.productTypeName }" proName="${item.productName }" appCount="${item.applicationCount }" >
							<td style="text-align: center;">${stat.count }</td>
							<td>${item.productComboName }</td>
							<td>${item.productName }</td>
							<td>${item.productTypeName }</td>
							<td>${item.standard }</td>
							<td>${item.applicationCount }</td>
							<td name="sendCount">${item.sendCount == null ? "0" : item.sendCount }</td>
						</tr>
					</c:forEach>
				</tbody>
	   		</table>
   		</fieldset>
   		<br/>
    	<fieldset id="applyInfo" style="width:100%;">
   		<div class="tip"><span>入库表中相应的产品</span></div>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="35px">序号</th>
						<th width="100px">仓库</th>
						<th width="80px">产品名称</th>
						<th width="80px">产品类别</th>
						<th width="180px">产品规格</th>
						<th width="100px">当前库存</th>
						<th width="100px">单价</th>
						<th width="100px">发货数量</th>
						<th width="100px">卡号开始</th>
						<th width="100px">卡号结束</th>
						<th width="100px">快递公司</th>
						<th width="100px">快递单号</th>
						<th width="100px">快递费</th>
						<th width="60px">操作</th>
					</tr>					
				</thead>
				<tbody id="sendOut_tbody_id">
					<c:forEach items="${productList }" var="item" varStatus="stat">
						<tr typeName="${item.productTypeName }" proName="${item.productName }">
							<td>${stat.count }</td>
							<td>${item.storegeName }</td>
							<td>${item.productName }</td>
							<td>${item.productTypeName }</td>
							<td>${item.standard }</td>
							<td>${item.useableQuantity }</td>
							<td>${item.price }</td>
							<td>
								<input type="hidden" name="id" value="${item.id }">
								<input type="hidden" name="storegeInId" value="${item.storegeInId }">
								<input type="hidden" name="applicationId" value="${item.applicationId }">
								<input type="hidden" name="standard" value="${item.standard }">
								<input type="hidden" name="price" value="${item.price }">
								<input type="hidden" name="productId" value="${item.productId }">
								<input type="hidden" name="productName" value="${item.productName }">
								<input type="hidden" name="userCard" value="">
								<input type="text" name="quantity" value="${item.quantity }" onkeyup="this.value=this.value.replace(/[^\d|chun]/g,'')" cardStart="${item.userCardNum }" userQuantity="${item.useableQuantity }" class="required digits" style="width: 80px;"/>
							</td>
							<td><input type="text" name="cardStart" value="${item.userCardNum }" style="width: 80px;" readOnly="readonly"/></td>
							<td><input type="text" name="cardEnd" value="${item.cardEnd }" style="width: 80px;" readOnly="readonly"/></td>
							<td>
								<input type="text" name="expressName" value="" style="width: 80px;"/>
							</td>
							<td>
								<input type="text" name="expressNo" value="" style="width: 80px;" />
							</td>
							<td>
								<input type="text" name="expressMoney" value="" style="width: 80px;" class="number"/>
							</td>
							<td>
								<a class="button" href="javascript:void(0);" onclick="sendGoods(this, '${stat.count }')">
									<span>发货</span>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
	   		</table>
   		</fieldset>
    </div>
    
  </form>
  
</div>

<script type="text/javascript">
/**
 * 发货功能事件;
 */
	function sendGoods(element, count) {
		var fromsubmit = $("#fromsubmit").val();
		if(fromsubmit != 0) {
			alertMsg.warn("重复提交无效!");
			return ;
		}
		$("#fromsubmit").val("1");
	 	$(element).attr("class", "buttonDisabled");
		var $thisTr = $(element).parent().parent();
		
		var typeName = $thisTr.attr("typeName");
		var proName = $thisTr.attr("proName");
		
		var id = $thisTr.find("input[name='id']").val();
		var storegeInId = $thisTr.find("input[name='storegeInId']").val();
		var applicationId = $thisTr.find("input[name='applicationId']").val();
		var standard = $thisTr.find("input[name='standard']").val();
		var price = $thisTr.find("input[name='price']").val();
		var productId = $thisTr.find("input[name='productId']").val();
		var productName = $thisTr.find("input[name='productName']").val();
		var quantity = $thisTr.find("input[name='quantity']").val();
		var expressName = $thisTr.find("input[name='expressName']").val();
		var expressNo = $thisTr.find("input[name='expressNo']").val();
		var expressMoney = $thisTr.find("input[name='expressMoney']").val();
		var cardStart = $thisTr.find("input[name='cardStart']").val();
		var cardEnd = $thisTr.find("input[name='cardEnd']").val();
		var userCard = $thisTr.find("input[name='userCard']").val();
		
		//首先验证该行所有数据通过验证;
		if(quantity == null || quantity == '') {
			alertMsg.error("请输入发货数量!");
			$("#fromsubmit").val("0");
			$(element).attr("class", "button");
			return ;
		}
		
		if(!new RegExp(/^\d+$/).test(quantity)) {
			alertMsg.error("发货数量请输入整数!");
			$("#fromsubmit").val("0");
			$(element).attr("class", "button");
			return ;
		}
		
		if(isNaN(expressMoney) && !new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(expressMoney)) {
			alertMsg.error("输入的快递费格式错误,请修改后在发货!");
			$("#fromsubmit").val("0");
			$(element).attr("class", "button");
			return ;
		}
		var currentVal = $(element).parent().parent().find("input[name='quantity']").val();
		var typeName = $(element).parent().parent().attr("typeName");
		var proName = $(element).parent().parent().attr("proName");
		var flag = true; 
		//和计算,不能大于明细中申请数量-发货数量
		$("#detail_tbody_id", navTab.getCurrentPanel()).find("tr").each(function() {
			var typeNameDetail = $(this).attr("typeName");
			var proNameDetail = $(this).attr("proName");
			var appCountDetail = parseInt($(this).attr("appCount"));
			var sendCountDetail = parseInt($(this).find("td[name='sendCount']").html());
			var count = appCountDetail - sendCountDetail;
			
			//当产品名称和类型名称相等;
			if(typeNameDetail == typeName && proNameDetail == proName) {
				count = count - parseInt(currentVal);
				console.info(count);
				if(count < 0) {
					alertMsg.warn("同一产品发货数量之和不能大于该产品申请数量减去发货数量之差!");
					flag = false;
					return ;
				}
			}
			
		});
		
		if(!flag) {
			$("#fromsubmit").val("0");
			$(element).attr("class", "button");
			return;
		}
		
		//ajax访问后台数据;发货;
		var  params = {
				"id":id, 
				"storegeOut.storegeInId":storegeInId, 
				"storegeOut.standard":standard, 
				"storegeOut.applicationId":applicationId, 
				"storegeOut.priceBak":price, 
				"storegeOut.productId":productId, 
				"storegeOut.productName":productName, 
				"storegeOut.quantity": quantity, 
				"storegeOut.cardStart": cardStart, 
				"storegeOut.cardEnd": cardEnd, 
				"storegeOut.userCard": userCard, 
				"storegeOut.expressName": expressName, 
				"storegeOut.expressNo": expressNo, 
				"storegeOut.expressMoneyBak":expressMoney};
		
		
		
		$.ajax({	//初始化页面时的省份
			type: "post",
			cache :false,
			data: params,
			url: "${path }/warehouse/storegeOut!sendSave.action",
			success: function(data){
				var data= eval("("+data+")");
				if(data.statusCode == "200") {
					navTab.reloadFlag(navTab._getTabs().filter('.selected').attr('tabid'));
					navTab.reloadFlag("${navTabId}");
				}
				
			},
			error :function(){
				$("#fromsubmit").val("0");
				$(element).attr("class", "button");
				alertMsg.alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	
	
	}




	$(function() {
		//快递费用;
		$("input[name='expressMoney']").blur(function() {
			var price = $(this).val();
			
			if(price == null || price == '') {
				return ;
			}
			
			if(isNaN(price)) {
				return ;
			}
			$(this).val(parseFloat(price).toFixed(2));
		}) ;
		
		//数据判断.发货数量不能大于当前库存, 累计和不能 大于 申请数量-已发货数量;
		$("input[name='quantity']").blur(function(){
			//获取当前值;
			var currentVal = $(this).val();
			var flag = true;
			var typeName = $(this).parent().parent().attr("typeName");
			var proName = $(this).parent().parent().attr("proName");
			
			if(currentVal!= null && currentVal != '') {
				//当前库存比较;不能大于库存;
				userQuantity = $(this).attr("userQuantity");
				if(parseInt(currentVal) > parseInt(userQuantity)) {
					alertMsg.warn("发货数量不能大于当前库存!");
					return ;
				}

				//和计算,不能大于明细中申请数量-发货数量
				$("#detail_tbody_id", navTab.getCurrentPanel()).find("tr").each(function() {
					var typeNameDetail = $(this).attr("typeName");
					var proNameDetail = $(this).attr("proName");
					var appCountDetail = parseInt($(this).attr("appCount"));
					var sendCountDetail = parseInt($(this).find("td[name='sendCount']").html());
					var count = appCountDetail - sendCountDetail;
					
					//当产品名称和类型名称相等;
					if(typeNameDetail == typeName && proNameDetail == proName) {
						count = count - parseInt(currentVal);
						if(count < 0) {
							alertMsg.warn("同一产品发货数量之和不能大于该产品申请数量减去发货数量之差!");
							flag = false;
							return ;
						}
					}
					
				});
				//当存在开始卡号, 卡号结束计算;
				var cardStart = $(this).parent().parent().find("input[name='cardStart']").val();
				if(flag && cardStart != null && cardStart.length == 12) {
					if(parseInt(currentVal) > 0) {
						//截取后六位;
						var cardNum = parseInt(cardStart.substring(6, 12));
						var num = parseInt(cardNum) + parseInt(currentVal);
						if(num > 0) {
							num --;
						}
						var str = dealCardNum(num);
						$(this).parent().parent().find("input[name='cardEnd']", navTab.getCurrentPanel()).val(cardStart.substring(0, 6) + str + "" + num);
						//可使用卡号更新; userCard
						num += 1;
						str = dealCardNum(num);
						$(this).parent().parent().find("input[name='userCard']", navTab.getCurrentPanel()).val(cardStart.substring(0, 6) + str + "" + num);
				
					}
					
				}
				
			}
			
		});
	});
	
	function dealCardNum(num) {
		var len = (num+"").length;
		if( len < 6) { //前面补0; 当超过999999时暂时不考虑;
			var count = 6 - len;
			var i = 0; 
			var str = "";
			while(i < count) {
				str += "0";
				i++;
			}
			
			return str;
		}
		
		return "";
	}

</script>