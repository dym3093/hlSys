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
function addAjaxDone(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
          if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
                navTab.reloadFlag(json.navTabId);
          } else { //重新载入当前navTab页面
                navTabPageBreak();
          }
          if ("closeCurrent" == json.callbackType) {
                setTimeout(function(){navTab.closeCurrentTab();}, 100);
          } else if ("forward" == json.callbackType) {
                navTab.reload(json.forwardUrl);
          }
    }
}

function mySubmit(_form){
	alert();
	_form.onsubmit=validateCallback(form, navTabAjaxDone);
	_form.submit();
	$("#pagerFindForm",navTab.getCurrentPanel()).submit();
}

function submitForm(){
		document.form1.isBtn.value="2";
		$(".pageForm",navTab.getCurrentPanel()).submit();

}
</script>
<div class="pageContent">
<form id="form1" name="form1" class="pageForm required-validate" onsubmit="return validateCallback(this, addAjaxDone);" action="${path}/warehouse/warehouse!saveStoreType.action?navTabId=${stroeWarehouse.id}" method="post">
    <input type="hidden" value="1" name="isBtn">				  
    <div class="pageFormContent" layoutH="56">
       <%--  <h1 class="press">${stroeWarehouse.name}类别信息维护</h1>
        <div class="divider"></div>
        <div class="tip"><span>仓库信息</span></div>
        <p>
	        <label>仓库名称：</label>
	        
	        <span>${stroeWarehouse.name}</span>
        </p>
        <p>
          <label>省份：</label>
          <span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${stroeWarehouse.province}"/></span>
        </p>
         <p>
          <label>城市</label>
          <span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${stroeWarehouse.city}"/></span>
        </p>
         <p>
          <label>联系人：</label>
          <span>${stroeWarehouse.director}</span>
        </p>
        <p>
          <label>联系电话：</label>
          <span>${stroeWarehouse.tel}</span>
        </p>
        <p>
          <label>详细地址：</label>
          <span>${stroeWarehouse.address}</span>
        </p> --%>
		<div class="tip"><span>${stroeWarehouse.name}类别信息录入</span></div>
		<div class="searchBar">
		  <table  width="80%"  > 
		<input name="id" type="hidden"  value="${stroeWarehouse.id}" />
		  <tr>
			  <td width="15%" >
	        <label>品类：</label>
	        </td>
       
        <td width="35%">
	         <select id="remark1" tabindex="1" name="stroeType.remark1"  rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107">
				<option value="${stroeType.remark1}"></option>
			</select>
        </td>
        <td width="15%">
	        <label>名称：</label>
	    </td>
        <td width="35%">				
			<input tabindex="2" name="stroeType.name" type="text"  value="${stroeType.name}" style="width: 95%;"  class="required name" />
        </td>
       </tr>
     <tr>  
        <td width="15%">
			<label>规格：</label>
			</td>
       
        <td width="35%">
        	<textarea tabindex="3" class="required standards" name="stroeType.standards" value="${stroeType.standards}" style="width: 95%;" cols="5" rows="4"></textarea>
        </td>
        <td> 
			<label>描述：</label>
			</td>
       
        <td>
			<textarea tabindex="4"  name="stroeType.descripe" style="width: 95%;" cols="5" rows="4"></textarea>
		   </td>
		</tr>
		 </td>
	
			</tr>
		<tr>
			<td  colspan=4 align=right >
				<div class="subBar" style="float:right; ">
				 <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">下一条</button>
            </div> 
          </div>
         </li>
         	<li>
          <div class="button">
            <div class="buttonContent">
              <button type="button" onclick="submitForm()">保存</button>
            </div>
          </div>
        </li>
      </div>
			</td>
		</tr>
   </table>  
      
    </div>
    </div>
</form>
</div>
