<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/resource/customerLink!listCustomerLink.action'){this.action = '${path }/resource/customerLink!listCustomerLink.action' ;} ;return navTabSearch(this);" id = "pagerFindForm" action="${path }/resource/customerLink!listCustomerLink.action" method="post" rel="pagerForm">
	<div class="searchBar">
	<table class="pageFormContent">
			<tr>
				<td>
					<label>姓名：</label><input type="text" name="filter_and_linkMan_LIKE_S" value="${ filter_and_linkMan_LIKE_S }" size="30"/>
				</td>
				<td>
					<label>手机：</label><input type="text" name="filter_and_phone_LIKE_S" value="${ filter_and_phone_LIKE_S }" size="30"/>
				</td>
				<td>
					<label>座机：</label><input type="text" name="filter_and_tel_LIKE_S" value="${ filter_and_tel_LIKE_S }" size="30"/>
				</td>
				</tr>
				<tr>
				<td>
					<label>远盟对接职能：</label>
					<select id="functions" name="filter_and_functions_LIKE_S" rel="iselect" loadUrl="${path}/hpin/hpinSystemDictType!treeRegion.action?defaultID=10103">
				  	  <option value="${filter_and_functions_LIKE_S}"></option>
				    </select>
				</td>
				<td>
					<label>客户全称：</label>
					<input type="text" name="filter_and_customer__customerName_LIKE_S" value="${ filter_and_customer__customerName_LIKE_S }"/>
				</td>
				<td>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
				<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
            </td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	<web:security tag="customerAdmin">
		<ul class="toolBar">
			<li><a class="add" href="${ path }/resource/customerLink!addCustomerLink.action" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="${path}/resource/customerLink!deleteCustomerLink.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
			<li><a class="edit" href="${path}/resource/customerLink!modifyCustomerLink.action?id={sid_user}" target="navTab"><span>修改</span></a></li>
			<web:exportExcelTag pagerFormId="pagerFindForm" 
								pagerMethodName="findByPage" 
								actionAllUrl="org.hpin.customer.web.CustomerLinkAction" 
								informationTableId="exportExcelTable"
								fileName="customerLink"></web:exportExcelTag>
		</ul>
		</web:security>
		<jsp:include page="/common/pagination.jsp"/>
	</div>
	<table class="list" width="98.5%" layoutH="174" id="exportExcelTable">
		<thead>
			<tr>
				<th>序号</th>
				<th export = "true" columnEnName = "linkMan" columnChName = "姓名"><a href="javascript:navSort('order_linkMan','desc');">姓名</a></th>
				<th export = "true" columnEnName = "customerId" columnChName = "客户全称" id2NameBeanId='org.hpin.customer.dao.CustomerDao'>客户全称</th>
				<th export = "true" columnEnName = "position" columnChName = "职务"><a href="javascript:navSort('order_position','desc');">职务</a></th>
				<th export = "true" columnEnName = "functions" columnChName = "与远盟对接职能" id2NameBeanId='org.hpin.system.dict.dao.HpinSystemDictTypeDao'><a href="javascript:navSort('order_functions','desc');">与远盟对接职能</a></th>
				<th export = "true" columnEnName = "tel" columnChName = "座机"><a href="javascript:navSort('order_tel','desc');">座机</a></th>
				<th export = "true" columnEnName = "phone" columnChName = "手机"><a href="javascript:navSort('order_phone','desc');">手机</a></th>
				<th export = "true" columnEnName = "fax" columnChName = "传真"><a href="javascript:navSort('order_fax','desc');">传真</a></th>
				<th export = "true" columnEnName = "email" columnChName = "Email">Email</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${ page.results }" var="customerLink" varStatus="status">
			<tr  target="sid_user" rel="${customerLink.id }">
				<td align="center"><input type="checkbox" name="ids" value="${ customerLink.id }" >${ status.count }</td>
				<td align="center" ><a href="${ path }/resource/customerLink!browCustomerLink.action?id=${ customerLink.id }" style="color:#0000FF" target="dialog">${customerLink.linkMan}</a></td>
				<td showTitle="true"><a href="${ path }/resource/customer!browCustomer.action?id=${ customerLink.customerId }" width="800" height="480" style="color:#0000FF" target="navTab"><hpin:id2nameDB id='${ customerLink.customerId}' beanId='org.hpin.customer.dao.CustomerDao'/></a></td>
				<td align="center">${customerLink.position }</td>
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
