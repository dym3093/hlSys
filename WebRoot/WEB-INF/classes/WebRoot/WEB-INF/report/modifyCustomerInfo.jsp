<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
});

</script>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/reportdetail/reportPassBackShenyou!editCustomerInfo.action" method="post">
	<input type = "hidden"  name= "navTabId" id = "navTabId" value="${navTabId }" />					  
    <div class="pageFormContent" layoutH="56">
		<h1 class="press">报告信息修改</h1>
        <div class="divider"></div>
        	<input name="id" type="hidden" class="required"  value="${customerInfo.id }" />
		<p>
	        <label>条形码：</label>
	        <input  type="text"  value="${customerInfo.code }" readonly="readonly" />
        </p>
        
        <p>
	        <label>姓名：</label>
	        <input name="name" type="text"  value="${customerInfo.name }" class="required" maxlength="30" />
        </p>

        <p>
			<label>性别：</label>
			<input  type="text"  value="${customerInfo.sex }" readonly="readonly" />
		</p>
		
        <p>
	        <label>手机号：</label>
	      	<input  type="text"  value="${customerInfo.phone }" readonly="readonly" />
        </p>
       
		<p>
			<label>套餐名称：</label> 
			<input  type="text"  value="${customerInfo.combo }" readonly="readonly" />
		</p>
		
		<p>
			<label>批次号：</label>
			<input  type="text"  value="${customerInfo.batch }" readonly="readonly" />
		</p>
		
		<p>
			<label>收样日期：</label>
			<input  type="text"  value="${fn:substring(customerInfo.RECEIVED_DATE,0,19)}" readonly="readonly" />
		</p>
		
		<p>
			<label>报告发布日期：</label>
			<input  type="text"  value="${fn:substring(customerInfo.REPORT_LAUNCH_DATE,0,19)}" readonly="readonly" />
		</p>
		 <p>
	        <label>报告ID：</label>
	        <input  type="text"  value="${customerInfo.REPORT_ID }" readonly="readonly" />
        </p>
        
		<p>
	        <label>报告编号：</label>
	        <input  type="text"  value="${customerInfo.REPORT_NUM }" readonly="readonly" />
        </p>
      </div>
      
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">修改</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
</form>
</div>
