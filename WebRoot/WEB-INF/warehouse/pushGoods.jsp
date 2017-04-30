<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
 <style type="text/css">
 .pageFormContent .textInput{width:87px;}
 
.gray_button {background-color: #ddd; color: #888;}

</style>
<script type="text/javascript">
	var validFlag = true;
	function validCardNum(){
		var count = new Number($("#count").val());
		var start = new Number($("#startNo").val());
		var end = new Number($("#endNo").val());
		var flag = (end-start+1)==count ? true:false;
		var tipMsg = "";
		if(!flag){
			tipMsg = "请按 【本次入库数量】=【卡号截至】-【卡号开始】+1  的规则填写！";
		}
		validFlag = flag;
		showTip(validFlag&&flag,tipMsg);
	}
	
	function validNotNull(me){
		var flag = true;
		var tipMsg = "";
		var val = $(me).val();
		var label = $(me).parent("td").prev().text();
		var content = "";
		if(label.indexOf(":")!=-1){
			content = label.split(":")[0];
		}
		if(label.indexOf("：")!=-1){
			content = label.split("：")[0];
		}
		console.log("label: "+label+", content: "+content);
		if(val.trim()==""||val==null||val==undefined){
			flag = false;
			tipMsg = "请填写【"+content+"】中的内容";
		}
		showTip(validFlag&&flag,tipMsg);
	}
	
	function showTip(flag, tipMsg){
		if(flag){
			$("button[type='submit']").removeAttr('disabled').removeClass("gray_button");
		}else{
			$("button[type='submit']").attr('disabled', 'disabled').addClass("gray_button");
		}
		$("#tip").text(tipMsg);
	}
	
	function testPrice(){
		var con = $.trim($("#count").val());
		var start = $.trim($("#price").val());
		var all = parseInt(con)*parseFloat(start);
		$("#total").val(all.toFixed(2));
	}
	function hectorAjaxDone(json){
		DWZ.ajaxDone(json);
		if(json.statusCode == DWZ.statusCode.ok){
			if ("forward"== json.callbackType){
	            navTab.reload(json.forwardUrl);
	      }
			$.pdialog.closeCurrent();
		}
	}
</script>
<div class="pageContent">
<form id="form1" name="form1" class="pageForm required-validate" onsubmit="return validateCallback(this, hectorAjaxDone);" action="${path}/warehouse/warehouse!pushGoods.action" method="post">
<div class="pageFormContent" layoutH="77">
<div class="tip">
	<input type="hidden" value="${stroeWarehouse.id}" />
	<span>${stroeWarehouse.name}产品类别信息</span>
</div>
<table  width="80%" >
		<tr>
			<input type="hidden" name="stroeType.id" value="${stroeType.id}"/>
				<td width="20%;"><label>类别：</label></td>
				<td  align="center" width="20%;"><hpin:id2nameDB id='${stroeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
				<td width="20%"><label>品名：</label></td>
				<td width="20%" align="center"><input type="hidden" name="storeDetail.typeSmallName" value="${stroeType.name}"/>${stroeType.name}</td>
			</tr>
			<tr>
				<td><label>规格：</label></td>
				<td  align="center">${stroeType.standards}</td>
				<td><label>描述：</label></td>
				<td  align="center">${stroeType.descripe}</td>
			</tr>
			<tr>	
				<td><label>库存数量：</label></td>
				<td  align="center">${storeAll.count}</td>
				<td><label>本次入库数量：</label></td>
				<td  align="center"><input type="text" name="storeDetail.count" id="count"  class="required digits" /></td>
			</tr>
			<c:choose>
				<c:when test="${remark1=='1010701' or remark1=='1010706'}">
				<tr>
					<td><label>卡号：</label></td>
					<td  align="center"><input type="text" class="required"  name="storeDetail.remark1" id="start" onblur="validNotNull(this);"/></td>
					<td></td>
					<td  align="center"></td>
				</tr>	
				<tr>
					<td><label>卡号开始：</label></td>
					<td  align="center"><input type="text"  class="required" name="storeDetail.remark2" id="startNo" onblur="validCardNum();" /></td>
					<td><label>卡号截至：</label></td>
					<td  align="center"><input type="text"  class="required"  name="storeDetail.remark3" id="endNo" onblur="validCardNum();"/></td>
				</tr>
				</c:when>
			</c:choose>
			<!-- <td  align="center"><input type="text"  name="storeDetail.remark2" id="start" onblur="test();" /></td>
			<td  align="center"><input type="text" name="storeDetail.remark3" id="end"/></td> -->
			<tr>
				<td><label>单价：</label></td>
				<td  align="center"><input type="text" class="required number" name="storeDetail.price" id="price" onblur="testPrice()" /></td>
				<td><label>总价：</label></td>
				<td  align="center"><input type="text" class="number" name="storeDetail.totalPrice" id="total"/></td>
			</tr>
			<tr>
				<td><label>项目负责人：</label></td>
				<td  align="center"><input type="text" name="storeDetail.remark" /></td>
				<td><label>项目名称：</label></td>
				<td  align="center"><input type="text" name="storeDetail.remark1" /></td>
			</tr>
			<tr>
				<td colspan="4">
				</td>
			</tr>
			<tr>
				<td colspan="1">
					<label style="font-size:1.5;">【注意】：</label>	
				</td>
				<td colspan="3">
					<span id="tip" style="color:red;"></span>
				</td>
			</tr>
	<!-- </tbody> -->
</table>
</div>
		 <div class="formBar" style="width:98%" align="right">
		 <button type="submit" >保存</button>
      <!-- <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit" disabled="disabled" class="gray_button" >保存</button>
            </div>
          </div>
        </li>
      </ul> -->
    </div>
    </form>
</div>
