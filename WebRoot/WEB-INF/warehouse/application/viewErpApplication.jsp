<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/application!saveOrUpdate.action" method="post" >
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="application.id" value="${application.id }" />
    
    <div class="pageFormContent" layoutH="35" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
        <table width="80%">
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
        		</tr>
        		<tr>
        			<td style="text-align: right;">项目名称：</td>
        			<td style="text-align: left">
        				${application.projectName }
        			</td>
        			<td style="text-align: right;">项目负责人：</td>
        			<td style="text-align: left">
        				${application.projectOwner }
        			</td>
        			<td style="text-align: right;">项目对接人：</td>
        			<td style="text-align: left">
        				${application.linkName }
        			</td>
        		</tr>
        		<tr>
        			<td style="text-align: right;">对接人电话：</td>
        			<td style="text-align: left">
        				${application.linkTel }
        			</td>
        			<td style="text-align: right;">收件人姓名：</td>
        			<td style="text-align: left">
        				${application.receiveName }
        			</td>
        			<td style="text-align: right;">收件人联系电话：</td>
        			<td style="text-align: left">
        				${application.receiveTel }
        			</td>
        		</tr>
        		<tr>
        			<td style="text-align: right;">期望日期：</td>
        			<td style="text-align: left">
        				<fmt:formatDate value='${application.hopeDate}' pattern='yyyy-MM-dd'/>
        			</td>
        			<td style="text-align: right;">寄送地址:</td>
        			<td colspan="3" style="text-align: left">${application.address }</td>
        		
        		</tr>
        		<tr>
        			<td style="text-align: right;">需求说明：</td>
        			<td colspan="5" style="text-align: left">
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
    	<fieldset id="applyInfo" style="width:100%;  border: 0px dotted #B8D0D6;">
			<legend>【<span id="status_text" style="color:#ff0000;">产品信息列表明细</span>】</legend>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="60px">序号</th>
						<th width="180px">产品套餐名称</th>
						<th width="200px">品类名称</th>
						<th width="200px">产品类别</th>
						<th>产品规格</th>
						<th width="200px">申请数量</th>
						<th width="100px">发货数量</th>
					</tr>					
				</thead>
				<tbody id="application_tbody_id">
					<c:forEach items="${details }" var="item" varStatus="stat">
						<tr>
							<td style="text-align: center;">${stat.count }</td>
							<td>${item.productComboName }</td>
							<td>${item.productName }</td>
							<td>${item.productTypeName }</td>
							<td>${item.standard }</td>
							<td>${item.applicationCount }</td>
							<td>${item.sendCount == null ? "0" : item.sendCount }</td>
						</tr>
					</c:forEach>
				</tbody>
	   		</table>
   		</fieldset>
    </div>
    
    
  </form>
  
</div>

<script type="text/javascript">
$(function() {
	//清除功能;
	$("#customerRelationPro", navTab.getCurrentPanel()).click(function() {
		
	});
	
	//明细数据删除操作
	$(".btnDel").live("click", function(){
		$(this).parent().parent().remove();
		//处理序号;
		var trCount = 0;
		$("#application_tbody_id", navTab.getCurrentPanel()).find("tr").each(function(index) {
			trCount = index+1;
			$(this).find("td[name='numNo']").html(trCount);
		}) ;
		
	});
});

/*
 * 支公司改变后获取值,并设置给项目编码选择;
 */
function bannyCompanyChangeEvent(obj) {
	var bannyCompanyId = $(obj).val();
	//访问地址;
	var hrefUrl = "${path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+bannyCompanyId;
	$("#customerRelshipPro", navTab.getCurrentPanel()).attr("href", hrefUrl);
	selectChangeEvent(obj);
}
 
/*
 * select选中改变事件;
 */
function selectChangeEvent(obj) {
	 var optText = $(obj).children("option[selected='selected']").html().trim(); 
	 $(obj).parent().parent().find("input[type='hidden']").val(optText);
}

/*
 * 添加产品信息列表
 */
function addProduct() {
	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/warehouse/application!addProduct.action?navTabId="+navTabId, "addProductDetail", "产品信息列表", 
			{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}




</script>