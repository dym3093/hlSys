<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/cardMsg/cardMsgAction!updateCardMsg.action" method="post" novalidate="novalidate">
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<input type="hidden" name="cardMsg.id" value="${ cardMsg.id }">
		<input type="hidden" name="cardMsg.status" value="${ cardMsg.status }">
		<input type="hidden" name="cardMsg.type" value="${ cardMsg.type }">
		<input type="hidden" name="cardMsg.password" value="${ cardMsg.password }">
		<input type="hidden" name="cardMsg.batchNo" value="${ cardMsg.batchNo }">
		<input type="hidden" name="cardMsg.pruchaseId" value="${ cardMsg.pruchaseId }">
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>持卡信息：</span></div>
			<p>
				<label>卡号：</label>
				<input type="text" name="cardMsg.cardNum" maxlength="50" size="30" readonly="readonly" value="${ cardMsg.cardNum }"/>
			</p>
			<p>
				<label>持卡人姓名：</label> 
				<input type="text" name="cardMsg.userName" maxlength="200" size="30" value="${ cardMsg.userName }">
			</p>
			<p>
				<label>民族：</label>
				<input class="" type="text" name="cardMsg.nation" maxlength="50" size="30" value="${ cardMsg.nation }"/>
			</p>
			<p>
				<label>性别：</label>
				 <select id="sex" name="cardMsg.sex" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10101"">
				 <option value="${ cardMsg.sex }"></option>
				 </select>
			</p>
			<p>
				<label>电话：</label>
				<input type="text" class="textInput phone" name="cardMsg.tel" maxlength="50" size="30" value="${ cardMsg.tel }" />
			</p>
			<p>
				<label>电子邮件：</label>
				<input type="text" class="textInput email" name="cardMsg.email" maxlength="200" size="30" value="${ cardMsg.email }" />
			</p>
			<p>
				<label>证件类型：</label>
				 <select name="cardMsg.idcardType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10102"">
				 <option value="${ cardMsg.idcardType }"></option>
				 </select>
			</p>
			<p>
				<label>证件号码：</label>
				<input type="text" class="textInput" name="cardMsg.idcardNum" maxlength="50" size="30" value="${ cardMsg.idcardNum }"/>
			</p>
			 <p>
				<label>服务开始时间：</label>
				<input type="text" name="cardMsg.startTime"  id="d39191" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d29182\')}'})" class="required" readonly="readonly" value="${ fn:substring( cardMsg.startTime,0,10 )}"/><a class="inputDateButton" href="javascript:;" >选择</a>
			</p>
			<p>
				<label>服务截止日期：</label>
				<input type="text" name="cardMsg.endTime" id="d29182" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d39191\')}'})" readonly="readonly" value="${ fn:substring( cardMsg.endTime,0,10 )}"  /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>服务人员与购卡人员是否一致：</label>
				 <select name="cardMsg.samePerson" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103"">
				 <option value="${ cardMsg.samePerson }"></option>
				 </select>
			</p>
			<p>
				<label>注册截止时间：</label>
				<input type="text" name="cardMsg.registerEndtime"  style="width: 170px;" onFocus="WdatePicker()" readonly="readonly" value="${ fn:substring( cardMsg.registerEndtime,0,10 )}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<input type="hidden" name="cardMsg.createTime" value="${ cardMsg.createTime }">
			
			<p class="nowrap">
			<label>备注：</label>
			<textarea name="cardMsg.remark" cols="100" rows="2" maxlength="500">${ cardMsg.remark }</textarea>
		     </p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
