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
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/genepack/erpGenepack!addGenepack.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <input name="genepack.id" type="hidden" value="${genepack.id }" />					  
    <div class="pageFormContent" layoutH="56">
        <h1 class="press">批次信息管理</h1>
        <div class="divider"></div>
        <div class="tip"><span>基因物品批次信息录入</span></div>
        <table>
        	<tr>
        		<td>
					<label>批次号：</label>
					<input name="genepack.batchno" class="required" type="text" value="${genepack.batchno }" />
              </td>
			  <td>
		          <label>快递单号：</label>
		          <input name="genepack.expressno"  type="text" value="${genepack.expressno}" />
        	  </td>
         </tr>
         <tr>
         	<td>
          <label>快递公司：</label>
          <input name="genepack.expresscompany" class="required" type="text" value="${genepack.expresscompany }" />
        </td>
        <td>
          <label>远盟联系人：</label>
          <input name="genepack.ymsalesman" class="required"  type="text" value="${genepack.ymsalesman}" />
        </td>
        </tr>
		<tr>
		 <td>
          <label>寄件人：</label>
          <input name="genepack.sender"   type="text" value="${genepack.sender}" />
          </td>
		  <td>
          <label>寄件人电话：</label>
          <input name="genepack.senderphone"   type="text" value="${genepack.senderphone }" />
          </td>
       
        </tr>
        <tr>
         <td>
          <label>寄件人地址：</label>
          <input name="genepack.senderaddress" class="required"  type="text" value="${genepack.senderaddress }" />
        </td>
        <td>
          <label>寄件人单位：</label>
           <input name="genepack.sendercompany" class="required"  type="text" value="${genepack.sendercompany}" />
        </td>
       
		</tr>
		<tr>
		<td>
          <label>货架号：</label>
		 <input name="genepack.shelvesno" class="required"  type="text" value="${genepack.shelvesno }" />
		</td>
		 <td>
			<label>上架日期：</label>
			<%-- <input id="updates" style="width: 110px;" name="genepack.updates" datefmt="yyyy-MM-dd HH:mm"   value="${fn:substring(genepack.updates,0,16)}" type="text" class="date required" /> --%>
			<input id="updates" style="width: 110px;" name="genepack.updates" datefmt="yyyy-MM-dd HH:mm"   
			<c:if test="${not empty genepack.updates}">
				value="${fn:substring(genepack.updates,0,16)}" 
			</c:if>
			<c:if test="${empty genepack.updates}">
				value="${now}" 
			</c:if>
			type="text" class="date required" />
		            <a class="inputDateButton" href="javascript:;">选择</a>
		</td>
		</tr>
		</table>
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
