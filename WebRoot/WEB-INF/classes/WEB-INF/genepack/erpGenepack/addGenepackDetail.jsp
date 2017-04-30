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
<div class="pageFormContent" layoutH="56">
<div class="pageContent">
	<h1 class="press">${genepack.batchno}批次信息详情</h1>
	<div class="divider"></div>
	 <div class="tip"><span>基因快递包信息</span></div>
<div class="pageContent">
<form   id="form1" name="form1"   class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/genepack/erpGenepackDetail!addGenepackDetail.action" method="post">
<input name="genepackDetail.batchno" type="hidden"  value="${genepackDetail.batchno}" />
<input name="genepackDetail.id" type="hidden"  value="${genepackDetail.id}" />
 <input type="hidden" value="1" name="isBtn">
<div  class="pageContent" >
        <div class="divider"></div>
          <p>
			<label>活动日期：</label>
			<input id="actiondate" style="width:110px;" name="genepackDetail.actiondate" readonly="readonly" datefmt="yyyy-MM-dd HH:mm"  class="date required" value="${fn:substring(genepackDetail.actiondate,0,16)}"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
           
		</p>
    <p>
          <label>活动场次：</label>
            <select name ="genepackDetail.actionevents" style="margin:6px;">
		            	<option value="">请选择</option>
		            	<option value="上午场" <c:if test="${genepackDetail.actionevents=='上午场'}">selected="selected"</c:if> >上午场</option>
		            	<option value="下午场" <c:if test="${genepackDetail.actionevents=='下午场'}">selected="selected"</c:if> >下午场</option>
		            	<option value="晚场" <c:if test="${genepackDetail.actionevents=='晚场'}">selected="selected"</c:if> >晚场</option>
		            </select>
        </p>
      <p>
          <label>有无纸制表格：</label>
          <select name ="genepackDetail.ispapertable" style="margin:6px;" id="ispapertable" class="required">
		            	<option value="">请选择</option>
		            	<option value="有" <c:if test="${genepackDetail.ispapertable=='有'}">selected="selected"</c:if> >有</option>
		            	<option value="无" <c:if test="${genepackDetail.ispapertable=='无'}">selected="selected"</c:if>>无</option>
		            </select>
        </p>
         <p>
          <label>样本数量：</label>
          <input name="genepackDetail.samplecount"  type="text" value="${genepackDetail.samplecount}"  class="required digits" />
        </p>
		<p>
          <label>条码1：</label>
          <input name="genepackDetail.code1"  type="text" value="${genepackDetail.code1}"  class="required"/>
        </p>
        <p>
          <label>条码2：</label>
          <input name="genepackDetail.code2"  type="text" value="${genepackDetail.code2}"  class="required"/>
        </p>
       
        
		<div class="divider"></div>
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
 </div>
<!---->
</form>	
</div>
</div></div>
