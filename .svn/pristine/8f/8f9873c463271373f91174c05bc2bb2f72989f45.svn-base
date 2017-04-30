<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${path}/dwz/js/checkCode.js" type="text/javascript"></script>
<script type="text/javascript">
/**页面-客户名称验证 **/
function checkcustomerNames(code){
	$.ajax({
	      type: "post",
	      cache :false,
	      dataType: "json",
	      url: "../common/ajaxCheckCode!checkCode.action",
	      data: {"beanName":$("#checkcustomerName").attr("beanName"),
	    	  "propertyName":$("#checkcustomerName").attr("propertyName"),
	    	  "propertyValue":code
	    	 },
	      success: function(data){ 
	      	 $.each(data,function(name,value){ 
	        	 if(value==true){
	           		alert("客户名称已存在");
	           		$("#checkcustomerName").val("");
	           		$("#checkcustomerName").focus();
	        	 }
	        });
	      },
	      error :function(XMLHttpRequest, textStatus, errorThrown){
	      }    
	});
}
</script>
<style type="text/css">
.table{table-layout:fixed}
.table th{overflow:hidden;text-overflow:ellipsis;white-space:nowrap}
</style>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path }/warehouse/warehouse!saveWarehouse.action" method="post" theme="simple">
    <input type="hidden" name="navTabId" value="${navTabId}"/>
    <div class="pageFormContent" layoutH="70">
      <div>
        <div class="tip"><span>仓库基本信息</span></div>
         <p >
          <label>仓库名称：</label>
          <input name="stroeWarehouse.name" maxlength="60" type="text" onblur="checkcustomerNames(this.value)" beanName="StoreWarehouse" propertyName="name" id="checkcustomerName" class="required textInput" value="${stroeWarehouse.name}"/>
        </p>
         <p >
          <label>省 份：</label>
           <select id="province" name="stroeWarehouse.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
		  </select>
        </p>
        <p >
          <label>城 市：</label>
         <select id="city" name="stroeWarehouse.city" rel="iselect">
         </select> 
        </p>
        <p>
          <label>联系人：</label>
          <input type="text" name="stroeWarehouse.director" maxlength="30" size="30" value="${stroeWarehouse.director }"/>
        </p>
        <p>
          <label>手机号：</label>
          <input type="text" name="stroeWarehouse.tel" class="textInput phone" value="${stroeWarehouse.tel}" />
        </p>
        <p class="nowrap" >
			<label>详细地址：</label>
			<textarea  name="stroeWarehouse.address"  cols="2" rows="2"></textarea>
		</p>
		<p class="nowrap" >
			<label>服务范围：</label>
			<textarea  name="stroeWarehouse.remark1" cols="2" rows="2"></textarea>
		</p>
		<p class="nowrap" >
			<label>描述：</label>
			<textarea  name="stroeWarehouse.remark2" cols="2" rows="2"></textarea>
		</p>
    </div>
    </div>
    <div class="formBar" >
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