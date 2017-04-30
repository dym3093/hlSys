<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.table{table-layout:fixed}
.table th{overflow:hidden;text-overflow:ellipsis;white-space:nowrap}
</style>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/warehouse/warehouse!modifyWarehouse.action" method="post" theme="simple">
    <input type="hidden" name="navTabId" value="${navTabId}"/>
    <div class="pageFormContent" layoutH="50" style="overflow: hidden;">
      <div>
        <div class="tip"><span>仓库基本信息</span></div>
         <p >
          <label>仓库名称：</label>
         <input id="warehouseId" name="stroeWarehouse.id" type="hidden" value="${id}"/>
          <input name="stroeWarehouse.name" maxlength="60" type="text"  beanName="StoreWarehouse" propertyName="name" id="checkcustomerName" class="required textInput exits" value="${stroeWarehouse.name}"/>
        </p>
        
         <p >
          <label>省 份：</label>
           <select id="province" name="stroeWarehouse.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
           <option value="${stroeWarehouse.province}"></option>
		  </select>
        </p>
        <p >
          <label>城 市：</label>
         <select id="city" name="stroeWarehouse.city" rel="iselect">
         <option value="${stroeWarehouse.city}"></option>
         </select> 
        </p>
        <p>
          <label>联系人：</label>
          <input type="text" name="stroeWarehouse.director" maxlength="30" size="30" value="${stroeWarehouse.director}"/>
        </p>
        <p>
          <label>手机号：</label>
          <input type="text" name="stroeWarehouse.tel" class="textInput phone" value="${stroeWarehouse.tel}" />
        </p>
        <p class="nowrap" >
			<label>详细地址：</label>
			<textarea  name="stroeWarehouse.address"  cols="2" rows="2">${stroeWarehouse.address}</textarea>
		</p>
		<p class="nowrap" >
			<label>服务范围：</label>
			<textarea  name="stroeWarehouse.remark1" cols="2" rows="2">${stroeWarehouse.remark1}</textarea>
		</p>
		<p class="nowrap" >
			<label>描述：</label>
			<textarea  name="stroeWarehouse.remark2" cols="2" rows="2">${stroeWarehouse.remark2}</textarea>
		</p>
    </div>
    </div>
    <div class="formBar" >
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">确定</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="button" class="close">关闭</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </form>
</div>

<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
/**页面-客户名称验证 **/
$.validator.addMethod("exits", function(value, element) {
	
	var id = $("#warehouseId").val();
	var exits = true;
	//后台请求查看是否已经存在了
	$.ajax({
		type: "post",
		cache : false,
		async : false,
		data:{"id":id, "name": value},
		url: "${path }/warehouse/warehouse!validNameRepeat.action",
		success: function(data){
			var data= eval("("+data+")");
			exits = data.result; 
		},
		error :function(){
			alertMsg.alert("服务发生异常，请稍后再试！");
			return;
		}
	});
	
	return this.optional(element) || exits;
}, "输入的产品名称已存在!");
</script>