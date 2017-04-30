<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/application!saveOrUpdate.action" method="post" >
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="application.id" value="${application.id }" />
    
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
						<th width="180px">产品名称</th>
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
    	<fieldset id="applyInfo" style="width:100%; border: 0px dotted #B8D0D6;">
   		<div class="tip"><span>入库表中相应的产品</span></div>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="35px">序号</th>
						<th width="100px">仓库</th>
						<th width="80px">产品名称</th>
						<th width="80px">产品类别</th>
						<th width="180px">产品规格</th>
						<th width="100px">单价</th>
						<th width="100px">发货数量</th>
						<th width="100px">卡号开始</th>
						<th width="100px">卡号结束</th>
						<th width="100px">快递公司</th>
						<th width="100px">快递单号</th>
						<th width="100px">快递费</th>
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
							<td>${item.price }</td>
							<td>${item.quantity }</td>
							<td>${item.cardStart }</td>
							<td>${item.cardEnd }</td>
							<td>${item.expressName }</td>
							<td>${item.expressNo }</td>
							<td>${item.expressMoney }</td>
						</tr>
					</c:forEach>
				</tbody>
	   		</table>
   		</fieldset>
    </div>
    
  </form>
  
</div>
