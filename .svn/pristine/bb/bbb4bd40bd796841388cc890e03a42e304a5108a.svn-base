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
function myFunction() {
	 $.ajax({
		 type: 'post', //可选get
		 url: '${path}/events/erpEvents!createNo.action', //这里是接收数据的程序
		 data: 'data=' + $("#eventDate").val(), //传给后台的数据，多个参数用&连接
		 dataType: 'text', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
			 success: function(data) {
			 //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
			 //document.getElementById("eventsNo").val = msg;
			 $("#eventsNo").val(data);
		 },
			 error: function() {
				 
			 }
	 	})
	 }
</script>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpExpress!addExpress.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />					  
    <div class="pageFormContent" layoutH="56">
        <h1 class="press">快递信息</h1>
        <div class="divider"></div>
        <div class="tip"><span>快递信息录入</span></div>
        <p>
          <label>场次号：</label>
          <input name="express.eventsNo" id="eventsNo" type="text" class="required" readonly="readonly"  value="${events.eventsNo }" />
        </p>
        <p>
          <label>快递号：</label>
          <input name="express.trackingNumber" id="trackingNumber" type="text" class="required"  value="${express.trackingNumber }" />
        </p>
        <p>
			<label>日期：</label>
			<input id="eventDate" name="express.eventDate" datefmt="yyyy-MM-dd HH:mm:ss" value="${express.eventDate }"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
          <label>寄件人：</label>
         <%--  <input name="express.name"  type="text" value="${events.createUserName}" readonly="readonly"/> --%>
          <input name="express.name"  type="text" value="${express.name}" readonly="readonly"/>
        </p>
        
		<div class="divider"></div>
      </div>
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
</form>
</div>
