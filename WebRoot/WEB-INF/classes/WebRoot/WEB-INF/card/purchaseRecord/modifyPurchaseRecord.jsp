<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/purchaseRecord/purchaseRecordAction!updatePurchaseRecord.action" method="post" novalidate="novalidate">
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<input type="hidden" name="purchaseRecord.id" value="${ purchaseRecord.id }">
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>持卡信息：</span></div>
			<p>
				<label>用户名：</label>
				<input type="text" name="purchaseRecord.userName" maxlength="50" size="30" value="${ purchaseRecord.userName }"/>
			</p>
			<p>
				<label>民族：</label> 
				<input type="text" name="purchaseRecord.nation" maxlength="50" size="30" value="${ purchaseRecord.nation }">
			</p>
			<p>
				<label>性别：</label>
				 <select id="sex" name="purchaseRecord.sex" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10101"">
				 <option value="${ purchaseRecord.sex }"></option>
				 </select>
			</p>
			<p>
				<label>电话：</label>
				<input type="text" class="textInput phone" name="purchaseRecord.tel" maxlength="50" size="30" value="${ purchaseRecord.tel }" />
			</p>
			<p>
				<label>电子邮件：</label>
				<input type="text" class="textInput email" name="purchaseRecord.email" maxlength="200" size="30" value="${ purchaseRecord.email }" />
			</p>
			<p>
				<label>证件类型：</label>
				 <select id="idcardType" name="purchaseRecord.idcardType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10102"">
				 <option value="${ purchaseRecord.idcardType }"></option>
				 </select>
			</p>
			<p>
				<label>证件号码：</label>
				<input type="text" class="textInput" name="purchaseRecord.idcardNum" maxlength="50" size="30" value="${ purchaseRecord.idcardNum }"/>
			</p>
			<p>
				<label>云图编号：</label>
				<input type="text" class="textInput" name="purchaseRecord.ytCode" maxlength="50" size="30" value="${ purchaseRecord.ytCode }"/>
			</p>
			<p>
				<label>注册时间：</label>
				<input type="text" name="purchaseRecord.createTime"  id="d5644365" style="width: 170px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d23464327\')}'})" class="required" readonly="readonly" value="${ fn:substring( purchaseRecord.createTime,0,19 )}" /><a class="inputDateButton" href="javascript:;" >选择</a>
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
