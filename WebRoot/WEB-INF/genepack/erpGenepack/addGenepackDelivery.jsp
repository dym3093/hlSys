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

</script>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/genepack/erpGenepackDelivery!addGenepackDelivery.action" method="post">
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <div class="pageFormContent" layoutH="56">
        <h1 class="press">批次信息管理</h1>
        <div class="divider"></div>
        <div class="tip"><span>基因物品批次信息录入</span></div>
        <table>
        	<tr>
        		<td>
					<label>批次号：</label>
					<input name="genepackDelivery.deliverybatchno" class="required" type="text" value="${genepackDelivery.deliverybatchno }" />
					<input name="genepackDelivery.id" type="hidden" value="${genepackDelivery.id }" />
              </td>
			  <td>
		          <label>快递单号：</label>
		          <input name="genepackDelivery.deliveryexpressno"  type="text" value="${genepackDelivery.deliveryexpressno}" />
        	  </td>
         </tr>
		<tr>
		<td>
          <label>物品数量：</label>
		 <input name="genepackDelivery.packcount" class="required"  type="text" value="${genepackDelivery.packcount }" />
		</td>
		 <td>
			<label>快递日期：</label>
			<%-- <input id="updates" style="width: 110px;" name="genepackDelivery.updates" datefmt="yyyy-MM-dd HH:mm"   value="${fn:substring(genepackDelivery.updates,0,16)}" type="text" class="date required" /> --%>
			<input id="deliverydate" style="width: 110px;" name="genepackDelivery.deliverydate" datefmt="yyyy-MM-dd HH:mm"   
			<c:if test="${not empty genepackDelivery.deliverydate}">
				value="${fn:substring(genepackDelivery.deliverydate,0,16)}" 
			</c:if>
			<c:if test="${empty genepackDelivery.deliverydate}">
				value="${now}" 
			</c:if>
			type="text" class="date required" />
		            <a class="inputDateButton" href="javascript:;">选择</a>
		</td>
		</tr>
		<tr>
		<td>
			<label>备注：</label>
			<input name="genepackDelivery.note" type="text" value="${genepackDelivery.note}" />
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
