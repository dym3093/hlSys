<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
	.toolBar li:hover{background:none;}
	.deliverDiv{
		height: 230px;
 		overflow-x: auto;
	}
</style>
<div class="tip" style="width:99.3%;"><span>申请信息</span></div>
<div class="pageHeader">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/business/barCodeBat!saveBarCodeBat.action" method="post">
        <table class="pageFormContent" id="applyInfo1">
			<tbody>
				<tr>
					<td>
						<label>总公司名称：</label>
          				<input name="storeApplication.ownedCompany" type="text" readonly="readonly"  value="${ storeApplication.ownedCompany }" />
					</td>
					<td>
						<label>支公司名称：</label>
          				<input name="storeApplication.bannyCompanyName" type="text" readonly="readonly"  value="${ storeApplication.bannyCompanyName }" />
					</td>
					<td>
						<label>项目编码：</label>
              			<input name="storeApplication.remark3" type="text"    readonly="readonly" value="${ storeApplication.remark3 }" />
					</td>
				</tr>
				
				<tr>
					<td>
						<label>项目负责人：</label>
              			<input name="storeApplication.remark1" type="text"    readonly="readonly" value="${ storeApplication.remark1 }" />
					</td>
					<td>
						<label>项目名称：</label>
              			<input name="storeApplication.remark3" type="text"    readonly="readonly" value="${ storeApplication.remark3 }" />
					</td>
					
					<td>
						<label>收件人：</label>
          				<input name="storeApplication.receiveName" type="text" readonly="readonly"  value="${ storeApplication.receiveName }" />
					</td>
				</tr>
				
				<tr>
					<td>
						<label>联系电话：</label>
          				<input name="storeApplication.receiveTel" type="text" readonly="readonly"  value="${ storeApplication.receiveTel }" />
					</td>
					<td>
						<label>详细地址:</label>
         				<input name="storeApplication.address" type="text" readonly="readonly"  value="${ storeApplication.address }" />
					</td>
					<td>
						<label>期望日期：</label>
          				<input type="text" name="storeApplication.useDate" readonly="readonly"  value="${fn:substring(storeApplication.useDate,0,10)}"/>
					</td>
				</tr>
					
				<tr>
					<td>
						<label style="height: auto">派往角色：</label>
					   	<input name="storeApplication.businessId" type="hidden" value="40289b6a5206079d01520619b31e0008"/>
					   	<input name="storeApplication.businessName" type="hidden" value="远盟本部"/>
					   	<span style="color:red">远盟基因业务部</span>
					</td>
					<td colspan="2">
						<label style="height: 60px;">需求说明：</label>
           				<textarea cols="35" ows="2" style="width:512px;height：30px;" readonly="readonly" name="storeApplication.requires">${storeApplication.requires}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	 </form>
</div>		
		
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
			
			<tbody>
				<c:forEach items="${applyDetailsList}" var="applyInfo" varStatus="status">
					<tr>
						<td width="50" align="center" nowrap="nowrap" hidden="hidden"><input id="count" type="text" value="${status.count}"/></td>
						<td width="60" align="center" nowrap="nowrap">${status.count}</td>
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
		
		
     <div class="pageContent">  
		<div class="panelBar">
			<ul class="toolBar">
				<li><div class="tab_i"><i style="font-style:normal">发货情况</i></div></li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
	</div>
		<div style="overflow-x:auto;">
            <table class="list" >
                <thead>
                    <tr>
                        <th width="50" nowrap="true">序号</th>
                        <th width="200" nowrap="true">仓库</th>
                        <th width="200" nowrap="true">品类</th>
                        <th width="200" nowrap="true">名称</th>
                        <th width="150" nowrap="true">规格</th>
                        <th width="100" nowrap="true">发货数量</th>
                        <th width="100" nowrap="true">快递公司</th>
                        <th width="200" nowrap="true">快递单号</th>
                        <th width="150" nowrap="true">发货时间</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.results}" var="entity" varStatus="status">
                    <tr target="sid_user" rel="1">
                    	<td width="50" align="center" nowrap="true">${status.count }</td>
						<td width="200" align="center" nowrap="true">${entity.wareHouse}</td>
						<td width="200" align="center" nowrap="true">${entity.type}</td>
						<td width="200" align="center" nowrap="true">${entity.name}</td>
						<td width="150" align="center" nowrap="true">${entity.specifications}</td>
						<td width="100" align="center" nowrap="true">${entity.deliveryNum}</td>
						<td width="100" align="center" nowrap="true">${entity.expressCompany}</td>
						<td width="200" align="center" nowrap="true">${entity.expressNum}</td>
						<td width="100" align="center" nowrap="true">${fn:substring(entity.deliveryDate,0,19)}</td>
					</tr>
				</c:forEach>
                </tbody>
            </table>
      </div>
  </form>
  
