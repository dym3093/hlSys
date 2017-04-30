<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>用户信息</span></div>
			<p>
			
				<label>用户名：</label>
				<input type="text" value="${purchaseRecord.userName}" readonly="readonly"/>
			</p>
			<p>
				<label>民族：</label>
				<input type="text" value="${purchaseRecord.nation}" readonly="readonly"/>
			</p>
			<p>
				<label>性别：</label>
				<input type="text" value="<hpin:id2nameDB id="${ purchaseRecord.sex }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>电话：</label>
				<input type="text" value="${purchaseRecord.tel}" readonly="readonly"/>
			</p>
			<p>
				<label>电子邮件：</label>
				<input type="text" value="${purchaseRecord.email}" readonly="readonly"/>
			</p>
			<p>
				<label>证件类型：</label>
				<input type="text" value="<hpin:id2nameDB id="${ purchaseRecord.idcardType }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>证件号码：</label>
				<input type="text" value="${purchaseRecord.idcardNum}" readonly="readonly"/>
			</p>
			<p>
				<label>云图编号：</label>
				<input type="text" value="${purchaseRecord.ytCode}" readonly="readonly"/>
			</p>
			<p>
				<label>注册时间：</label>
				<input type="text" value="${ fn:substring( purchaseRecord.createTime,0,19 )}" readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
