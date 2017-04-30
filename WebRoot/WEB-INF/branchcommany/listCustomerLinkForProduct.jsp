<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>  

<div class="tip"><span>查询</span>
<a class="add" href="${ path }/resource/customerLink!addCustomerLinks.action?mainId=${ productId }&modelType=02&customerId=${customerId}" rel="${ productId }" id="tjlxr"  target="dialog"><span>添加联系人</span></a>
</div>
<div class="pageContent">
<form rel = "pagerForm" onsubmit="return validateCallback(this, tabAjaxDone);" action="${path }/resource/linkmanRelation!saveLinkmanRelation.action" class="pageForm required-validate"  method="post">
<input type="hidden" name="mainId" value="${ productId }">
<input type="hidden" name="modelType" value="02">
 <div class="pageFormContent" layoutH="90">
	<table class="table" width="100%" >
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>客户全称</th>
				<th>职务</th>
				<th>与远盟对接职能</th>
				<th>座机</th>
				<th>手机</th>
				<th>传真</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ customerList }" var="customerLink" varStatus="status">
			<tr  target="sid_user" rel="${customerLink.id }">
				<td align="center"><input type="checkbox" name="ids" value="${ customerLink.id }" >${ status.count }</td>
				<td align="center"><a href="${ path }/resource/customerLink!browCustomerLink.action?id=${ customerLink.id }" style="color:#0000FF" target="dialog">${customerLink.linkMan}</a></td>
				<td align="center"><hpin:id2nameDB id="${customerLink.sex}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/></td>
				<td><a href="${ path }/resource/customer!browCustomer.action?id=${ customerLink.customerId }" width="800" height="480" style="color:#0000FF" target="navTab">${ customerLink.customer.customerName }</a></td>
				<td align="center"><hpin:id2nameDB id="${customerLink.position}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/></td>
				<td align="center">
					<c:forEach items="${fn:split(customerLink.functions,',')}" var="functions" begin="0" 
					  end="${fn:length(fn:split(customerLink.functions,','))}" varStatus="stat">
					<hpin:id2nameDB id="${ functions }" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
					</c:forEach>
				</td>
				<td align="center">${customerLink.tel }</td>
				<td align="center">${customerLink.phone }</td>
				<td align="center">${customerLink.fax }</td>
				<td align="center">${customerLink.email }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<div class="formBar">
	<ul class="toolBar"> 
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
	</ul>
</div>
</form>
</div>