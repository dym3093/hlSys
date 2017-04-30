<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>持卡人信息</span></div>
			<p>
			
				<label>卡号：</label>
				<input type="text" value="${cardMsg.cardNum}" readonly="readonly"/>
			</p>
			<p>
				<label>密码：</label>
				<input type="text" value="${cardMsg.password}" readonly="readonly"/>
			</p>
			<p>
				<label>持卡人姓名：</label>
				<input type="text" value="${cardMsg.userName}" readonly="readonly"/>
			</p>
			<p>
				<label>民族：</label>
				<input type="text" value="${cardMsg.nation}" readonly="readonly"/>
			</p>
			<p>
				<label>性别：</label>
				<input type="text" value="<hpin:id2nameDB id="${ cardMsg.sex }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>电话：</label>
				<input type="text" value="${cardMsg.tel}" readonly="readonly"/>
			</p>
			<p>
				<label>电子邮件：</label>
				<input type="text" value="${cardMsg.email}" readonly="readonly"/>
			</p>
			<p>
				<label>证件类型：</label>
				<input type="text" value="<hpin:id2nameDB id="${ cardMsg.idcardType }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>证件号码：</label>
				<input type="text" value="${cardMsg.idcardNum}" readonly="readonly"/>
			</p>
			<%-- <p>
				<label>状态：</label>
				<input type="text" value="${cardMsg.status}" readonly="readonly"/>
			</p> --%>
			<p>
				<label>卡号激活时间：</label>
				<input type="text" value="${ fn:substring( cardMsg.createTime,0,19 )}" readonly="readonly"/>
			</p>
			<p>
				<label>服务开始时间：</label>
				<input type="text" value="${ fn:substring( cardMsg.startTime,0,10 )}" readonly="readonly"/>
			</p>
			<p>
				<label>服务截止时间：</label>
				<input type="text" value="${ fn:substring( cardMsg.endTime,0,10 )}" readonly="readonly"/>
			</p>
			<p>
				<label>服务人员与购卡人员是否一致：</label>
				<input type="text" value="<hpin:id2nameDB id="${ cardMsg.samePerson }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>卡号类型：</label>
				<input type="text" value="<hpin:id2nameDB id="${ cardMsg.type }" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>" readonly="readonly"/>
			</p>
			<p>
				<label>注册截止时间：</label>
				<input type="text" value="${ fn:substring( cardMsg.registerEndtime,0,19 )}" readonly="readonly"/>
			</p>
			<p class="nowrap">
			<label>备注：</label>
			<textarea name="cardMsg.remark" cols="100" rows="2" maxlength="500" readonly="readonly">${ cardMsg.remark }</textarea>
		     </p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
