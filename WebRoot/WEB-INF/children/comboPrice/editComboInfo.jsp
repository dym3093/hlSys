<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>

<style type="text/css">
	.bt{
		margin-left: 2px;
		margin-top: 4px;
		cursor:pointer;
	}
	.pointer{
		cursor:not-allowed;
	}
	.tip1{
		margin-left: 30px;
		margin-top: 10px;
		font-weight: bold;
	}
	.title{
		margin-left: 20px;
		margin-top: 30px;
		margin-bottom: 30px;
	}

	
</style>
<script type="text/javascript" language="javascript">

	$(function(){
		$(":input").each(function(k,v){
			if($(this).val()!=""){
				$(this).attr("readonly","true");
				$(this).addClass("pointer");		//cursor:not-allowed;
			}
		});
		$("th").css({"font-weight":"bold","font-size":"12px",});
		
	});

	function setPrice(obj){	
		$(obj).parent("td").prevAll().find(":text").removeClass("pointer");
		$(obj).parent("td").prevAll().find(":text").removeAttr("readonly");
		$(obj).parent("td").prevAll().find(":text").css("background-color","white");
//		$(obj).prevAll(":input").focus();
	}

	/**
	 * 保存
	 */
	function setComboPrice(){
		var count =0;
		var dataJson="["; 
		var value="",no="";
		var array = new Array();		//存放被改动数据的行号（即页面上的序号）
		$("#content tr td").find(":text").each(function(index1,item1){
			$("#content2 tr td").find(":text").each(function(index2,item2){
				if(index1==index2 && $.trim($(item1).val())!=$.trim($(item2).val())){
					value=$.trim($(item1).parent().parent().children().eq(0).text());
					if(value!=no){
						no=value;		
						array.push(value);
					}
					count++;
				}
			});
		});
		if(count==0){
			alertMsg.info("没有做更改,无需保存");
			return false;
		}
		for(var i=0;i<array.length;i++){
			$("#content tr td").find("span").each(function (index1, item1){ 
 				var id=$.trim($(this).text());
 				var $obj = $(item1).parent().parent().children().find(":text");
				if(id===array[i]){
					var combo=$.trim($(item1).parent().next().text());
				    var nickName=$.trim($obj.eq(0).val());
				    var payedPrice=$.trim($obj.eq(1).val());
				    var guidancePrice=$.trim($obj.eq(2).val());
				    var settlementPrice=$.trim($obj.eq(3).val());
				    dataJson += "{"+"\"combo\":\""+combo+"\","+"\"nickName\":\""+nickName+"\","+"\"payedPrice\":\""+payedPrice+"\","+"\"guidancePrice\":\""+guidancePrice+"\","+"\"settlementPrice\":\""+settlementPrice+"\"},"; 
				}
			});
		}
	
		if (dataJson.lastIndexOf(",")) {  
			dataJson = dataJson.substring(0,dataJson.length -1);  
		dataJson += "]";  
		}   
		var company = $.trim($("#company").text());
			$.ajax({
				type:"post",
				cache:false,
				dateType:"json",
				url:"${path}/children/erpGroupOrderCombo!setComboPrice.action",
				data:{"dataJson":dataJson,"company":company},
				success:function(data){
					var resp= eval("("+data+")");
					if(resp.result>=1){
						alertMsg.correct('保存成功!');
						$(":input").each(function(k,v){
							if($(this).val()!=""){
								$("#tableCombo").find(":text").attr("readonly", true).css("background-color", "RGB(246,246,246)").addClass("pointer");
								$("#content tr td").find(":text").each(function(index1,item1){
									$("#content2 tr td").find(":text").each(function(index2,item2){
										if(index1==index2 && $.trim($(item1).val())!=$.trim($(item2).val())){
											$(item2).val($.trim($(item1).val()));
										}
									});
								});
							}
						});
//						return navTabSearch(this);
					}else{
						alertMsg.error('保存失败！');
					}
				},
				error:function(){
					alertMsg.error('保存失败或没有对套餐进行更改！');
				}
			});
		}	

	
	//关闭弹窗
	function isClose(){
		navTab.closeCurrentTab();
	}

</script>
<div class="tip"><span>套餐信息</span></div>
<div class="title">
	<span class="tip1">支公司：</span><span id="company">${company }</span>
	<span class="tip1">总公司：</span><span>${totalCompany}</span>
	<span class="tip1">省：</span><span><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${province}"/>${province}</span>
	<span class="tip1">市：</span><span><hpin:id2nameDB beanId="org.hpin.base.region.dao.RegionDao" id="${city}"/>${city}</span>
</div>
<div class="pageHeader" style="background:white;" ></div>
<div class="pageHeader" style="background:white;" >
	<div class="panelBar" style="display: none;">
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<form class="pageForm required-validate" id="addForm" 
		onsubmit="if(this.action != '${path}/children/erpGroupOrderCombo!setComboPrice.action')
		{this.action = '${path}/children/erpGroupOrderCombo!setComboPrice.action' ;} ;" 
		action="${path}/children/erpGroupOrderCombo!setComboPrice.action" method="post">
		<table class="pageFormContent" id="tableCombo">
			<thead>
				<tr>
					<th width="50">序号</th>
					<th width="200" export = "true" columnEnName = "combo" columnChName = " 套餐名 " > 套餐名 </th> 
					<th width="200" export = "true" columnEnName = "nickName" columnChName = "昵称(别名)" > 昵称(别名) </th>
					<th width="200" export = "true" columnEnName = "payed_price" columnChName = "客户支付价格" > 客户支付价格 </th> 
					<th width="200" export = "true" columnEnName = "guidancePrice" columnChName = "指导价格 " > 指导价格 </th> 
					<th width="200" export = "true" columnEnName = "settlemenPrice" columnChName = "渠道结算价格" > 渠道结算价格 </th> 
					<th width="150" export = "true" columnEnName = "operation" columnChName = "操作 " > 操作 </th> 
				</tr>
		  	</thead>
	  		
		  	<tbody id="content"> 		  		
		  		<c:forEach items="${page.results}" var="entity" varStatus="status">
				  	<tr target="sid_user" rel="${entity.id}" id="rows">
						<td align="center"> <span id="idNo">${status.count}</span> </td>
						<td align="center"> <span id="combo">${entity.combo}</span> </td>
						<td align="center"> <input type="text" name="nickName" value="${entity.comboNickname}"/> </td>  
						<td align="center"> <input type="text" name="payedPrice" value="${entity.payedPrice}"/> </td>
						<td align="center"> <input type="text" name="guidancePrice"  value="${entity.guidancePrice}"/> </td>
						<td align="center"> <input type="text" name="settlementPrice"  value="${entity.settlementPrice}"/></td>
						<td align="center"> <button type="button" onclick="setPrice(this)">修改</button> </td>
					</tr>
 				</c:forEach> 
			</tbody>
				
			<tfoot>
		        <tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		       </tr>
		        <tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td>
		            	<div align="center" style="text-align:center;">
						    <div class="buttonActive"><div class="buttonContent"><button type="button" onclick="setComboPrice()">保存</button></div></div>
							<div class="buttonActive" style="margin-left: 12px;"><div class="buttonContent"><button type="button" onclick="isClose()">返回</button></div></div>
						</div>
		            </td>
		            <td></td>
		       </tr>
			</tfoot>	
		</table>		
	</form>
</div>

<div style="display:none;">
	<div class="pageHeader" style="background:white;" >
		<table>
			<tbody id="content2"> 		  		
			  	<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" rel="${entity.id}" id="rows">
						<td align="center"> <input type="text" name="nickName" value="${entity.comboNickname}"/> </td>  
						<td align="center"> <input type="text" name="payedPrice" value="${entity.payedPrice}"/> </td>
						<td align="center"> <input type="text" name="guidancePrice"  value="${entity.guidancePrice}"/> </td>
						<td align="center"> <input type="text" name="settlementPrice"  value="${entity.settlementPrice}"/></td>
					</tr>
	 			</c:forEach> 
			</tbody>
		</table>
	</div>
</div>
