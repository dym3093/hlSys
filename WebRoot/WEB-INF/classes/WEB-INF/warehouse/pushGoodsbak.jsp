<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
 <style type="text/css">
 .pageFormContent .textInput{width:87px;}
</style>
<script type="text/javascript">
	function test(){
		var con = new Number($("#count").val());
		var start = new Number($("#start").val());
		$("#end").val(con+start);
	}
	function testPrice(){
		var con = new Number($("#count").val());
		var start = new Number($("#price").val());
		$("#total").val(con*start);
	}
</script>
<div class="pageContent">
<form id="form1" name="form1" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/warehouse/warehouse!pushGoods.action" method="post">
<div id="table2" class="pageFormContent" layoutH="56">
<div>
<%-- <div class="tip"><span>仓库基本信息</span></div>
<p>
	<label>仓库名称：</label>
	<input type="hidden" value="${stroeWarehouse.id}" />
	<input type="text" value="${stroeWarehouse.name}" readonly="readonly"/>
</p>
<p>
	<label>省 份：</label>
	<input type="text" value="<hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly"/>
</p>
<p>
	<label>城 市：</label>
	<input type="text" value="<hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly">
</p>
 <p>
     <label>联系人：</label>
     <input type="text" name="stroeWarehouse.director" maxlength="30" size="30" readonly="readonly" value="${stroeWarehouse.director}"/>
</p>
<p>
     <label>联系电话：</label>
     <input type="text" name="stroeWarehouse.tel" maxlength="30" size="30" readonly="readonly"  value="${stroeWarehouse.tel}"/>
</p>
<p class="nowrap" style="height: 61px;">
    <label>详细地址：</label>
	<textarea name="stroeWarehouse.address" cols="4" readonly="readonly" rows="2">${stroeWarehouse.address}</textarea>
</p>
</div>
<div class="divider"></div>
<div> --%>
<div class="tip">
<input type="hidden" value="${stroeWarehouse.id}" />
<span>${stroeWarehouse.name}产品类别信息</span>
</div>
<table class="list" width="100%">
	<thead>
		<tr>
			<th width="10%;">类别</th>
			<th width="10%;">品名</th>
			<th width="10%;">规格</th>
			<th width="10%;">描述</th>
			<th width="10%;">库存数量</th>
			<th width="10%;">本次入库数量</th>
			<c:choose>
				<c:when test="${remark1=='1010701' }">
					<th width="10%;">卡号开始</th>
					<th width="10%;">卡号截止</th>
				</c:when>
			</c:choose>
			<th width="10%;">单价</th>
			<th width="10%;">总价</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<input type="hidden" name="stroeType.id" value="${stroeType.id}"/>
			<td  align="center"><hpin:id2nameDB id='${stroeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
			<td  align="center"><input type="hidden" name="storeDetail.typeSmallName" value="${stroeType.name}"/>${stroeType.name}</td>
			<td  align="center">${stroeType.standards}</td>
			<td  align="center">${stroeType.descripe}</td>
			<td  align="center">${storeAll.count}</td>
			<td  align="center"><input type="text" name="storeDetail.count" id="count"  class="required digits"/></td>
			<c:choose>
				<c:when test="${remark1=='1010701' }">
				<td  align="center"><input type="text"  name="storeDetail.remark2" id="start" onblur="test();" /></td>
				<td  align="center"><input type="text" name="storeDetail.remark3" id="end"/></td>
				</c:when>
			</c:choose>
			<!-- <td  align="center"><input type="text"  name="storeDetail.remark2" id="start" onblur="test();" /></td>
			<td  align="center"><input type="text" name="storeDetail.remark3" id="end"/></td> -->
			<td  align="center"><input type="text" class="digits" name="storeDetail.price" id="price" onblur="testPrice()" /></td>
			<td  align="center"><input type="text" class="digits" name="storeDetail.totalPrice" id="total"/></td>
		</tr>
	</tbody>
</table>
</div>
</div>
		 <div class="formBar" style="width:99%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
    </form>
</div>
