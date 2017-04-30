<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	 $(".idno",navTab.getCurrentPanel()).addClass("required");
	 var standards = $("#standards");
	 $(standards).next().val($(standards).val());
});
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
<form id="form1" name="form1" class="pageForm required-validate" onsubmit="return validateCallback(this, addAjaxDone);" action="${path}/warehouse/warehouse!modifyType.action?id=${stroeType.id}" method="post">
    <input type="hidden" value="1" name="isBtn">
    <h1 class="press">${stroeWarehouse.name}类别信息维护</h1>
    <div class="divider"></div>
    <div class="tip"><span>仓库信息</span></div>
    <div class="pageheader">
        <table class="pageFormContent xxmes" id="lineHeight">
            <tr>
                <td><label>仓库名称：</label><input type="hidden" name ="stroeWarehouse.id" id="wareId" value="${stroeWarehouse.id}"/></td>
                <td><span>${stroeWarehouse.name}</span></td>
                <td><label>省份：</label></td>
                <td><span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${stroeWarehouse.province}"/></span></td>
                <td><label>城市：</label> </td>
                <td><span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${stroeWarehouse.city}"/></span></td>
                <td><label>联系人：</label> </td>
                <td><span>${stroeWarehouse.director}</span></td>
            </tr>
            <tr>
                <td><label>详细地址：</label> </td>
                <td><span>${stroeWarehouse.address}</span></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
   
    <div class="tip"><span>类别信息录入</span></div>
    <div class="searchBar">
        <table  width="100%" class="pageFormContent" >
            <%-- <input name="id" type="hidden"  value="${stroeType.id }" /> --%>
			<tr>
            	<td width="15%" >
                    <label>产品大类：</label>
                </td>
                <td width="35%">
                    <select id="remark1" name="stroeType.remark1" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107">
                        <option value="${stroeType.remark1}"></option>
                    </select>
                </td>
                <td width="15%">
                    <label>名称：</label>
                </td>
                <td width="35%" >
                    <input tabindex="3" name="stroeType.name"  type="text"  value="${stroeType.name}"  class="required name" />
                </td>
            </tr>
            
            <tr>
                <td>
                    <label>规格：</label>
                </td>
                <td>
                	<input id="standards" type="hidden" value="${stroeType.standards}"  disabled="disabled"/>
                    <textarea style="width:100%;" tabindex="3" class="required standards" name="stroeType.standards" value="${stroeType.standards}" style="width: 95%;" cols="3" rows="2"></textarea>
                </td>
                <td>
                    <label>描述：</label>
                </td>
                <td>
                    <textarea style="width:100%;" name="stroeType.descripe" cols="3" rows="2">${stroeType.descripe}</textarea>
                </td>
            </tr>
            
            <tr>
                <td  colspan=4 align=right >
                    <div class="subBar" style="float:right;margin-right:-50px; ">
                        <!--<li>
                               <div class="buttonActive">
                                <div class="buttonContent">
                                  <button type="close">关闭</button>
                                </div>
                              </div>
                             </li> 
                        <li>-->
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" onclick="submitForm()">确定</button>
                                </div>
                            </div>
                       
                    </div>
                </td>
            </tr>
        </table>
    </div>

</form>