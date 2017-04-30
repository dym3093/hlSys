<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script>

	
</script>	
	
<div class="pageContent">
	<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/cardMsg/cardMsgAction!saveCardMsgs.action" method="post" novalidate="novalidate">
		<input type="hidden" name="navTabId" value="${ navTabId }"/>
		<div class="pageFormContent" layoutH="56">
		<div class="tip"><span>持卡信息：</span></div>
		   <p>
				<label>选择产品：</label>
				 <select id="industry" name="channel" rel="iselect" >
								<option value="LD">联动优势</option>
								<option value="PT">普天</option>
							</select>
				  
			</p>
			<p>
				<label>注册数量：</label>
				<input type="text" name="number" maxlength="50" size="30"/>
			</p>
			
			<p>
				<label>截止延迟月份：</label>
				<input type="text" name="month" maxlength="2" size="2"/>
			</p>
			<p>
				<label>注册截止时间：</label>
				<input type="text" name="cardMsg.registerEndtime"  style="width: 170px;" onFocus="WdatePicker()" readonly="readonly" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
