<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>客户联系人信息</span></div>
			<p>
			
				<label>客户全称：</label>
				<input type="text" value="${customerLink.customer.customerName}" readonly="readonly"/>
			</p>
			<p>
				<label>姓名：</label>
				<input type="text" value="${customerLink.linkMan}" readonly="readonly"/>
			</p>
			<p>
				<label>性别：</label>
				<input type="text" value="<hpin:id2nameDB id="${customerLink.sex}" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>职务：</label>
				<input type="text" value="${customerLink.position}" readonly="readonly"/>
			</p>
			<p>
				<label>所属部门：</label>
				<input type="text" value="${customerLink.department}" readonly="readonly"/>
			</p>
			<p>
				<label>座机：</label>
				<input type="text" value="${customerLink.tel}" readonly="readonly"/>
			</p>
			<p>
				<label>手机：</label>
				<input type="text" value="${customerLink.phone}" readonly="readonly"/>
			</p>
			<p>
				<label>传真：</label>
				<input type="text" value="${customerLink.fax}" readonly="readonly"/>
			</p>
			<p>
				<label>Email：</label>
				<input type="text" value="${customerLink.email}" readonly="readonly"/>
			</p>
			<p>
				<label>QQ：</label>
				<input type="text" value="${customerLink.qq}" readonly="readonly"/>
			</p>
			<p>
				<label>与远盟对接职能：</label>
				<input type="text" value="<c:forEach items="${fn:split(customerLink.functions,',')}" var="functions" begin="0" 
					  end="${fn:length(fn:split(customerLink.functions,','))}" varStatus="stat">
					<hpin:id2nameDB id="${ functions }" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
					</c:forEach>" readonly="readonly"/>
			</p>
			<p>
				<label>个人账号：</label>
				<input type="text" value="${customerLink.account}" readonly="readonly"/>
			</p>
			<p>
				<label>户名：</label>
				<input type="text" value="${customerLink.personName}" readonly="readonly"/>
			</p>
			<p>
				<label>开户行：</label>
				<input type="text" value="${customerLink.bank}" readonly="readonly"/>
			</p>
			<p class="nowrap">
				<label>与远盟对接职能：</label>
				<span style="border: 0;width:75%"><c:forEach items="${fn:split(customerLink.functions,',')}" var="functions" begin="0" 
				  end="${fn:length(fn:split(customerLink.functions,','))}" varStatus="stat">
				<hpin:id2nameDB id="${ functions }" beanId="org.hpin.system.dict.dao.HpinSystemDictTypeDao"/>
				</c:forEach></span>
			</p>
			<p class="nowrap">
			<label>备注：</label>
			<textarea name="customerLink.remark" cols="100" rows="2" readonly="readonly">${ customerLink.remark }</textarea>
		</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
