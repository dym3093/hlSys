<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return iframeCallback(this, testAjax);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/prepCustomer!preExcelImport.action" method="post" enctype="multipart/form-data">
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="params.project" value="YA"/>
    
    <div class="pageFormContent" layoutH="35" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
        <table width="100%">
        	<tbody>
        		<tr>
        			<td><label>支公司名称：</label></td>
        			<td>
			        	<input id="branchCompanyId" name="params.branchCompanyId" bringbackname="customer.id" type="hidden" value=""  />
			        	<input id="branchCompany" name="params.branchCompany" bringbackname="customer.branchCommanyName" readonly="readonly" class="required textInput" type="text" value=""/>
			        	<a class="btnLook" href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action" callback="pro_callback" lookupGroup="customer">查找带回</a>
						<img src="${path}/images/clear.png" style="cursor: pointer; margin-top: 6px;" title="清除公司信息" id="customerRelation"/>
        			</td>
        			<td><label>项目编码：</label></td>
        			<td>
			       		<input id="projectCode" name="params.projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${application.projectCode }"/>
			       		<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action" lookupGroup="customerRelationShipPro">查找带回</a>
						<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
        			</td>
        			<td><label>项目名称：</label></td>
        			<td>
        				<input id="projectName" name="params.projectName" type="text" class="required" value="${application.projectName }" bringbackname="customerRelationShipPro.projectName" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			
        		</tr>
        		<tr>
        			<td><label>项目负责人：</label></td>
        			<td>
        				<input id="projectOwner" name="params.projectOwner" value="${application.projectOwner }" bringbackname="customerRelationShipPro.projectOwner" type="text" class="required" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			<td><label>项目对接人：</label></td>
        			<td>
        				<input id="linkName" name="params.linkName" value="${application.linkName }" bringbackname="customerRelationShipPro.linkName" type="text" class="required" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			<td><label>对接人电话：</label></td>
        			<td>
        				<input id="linkTel" name="params.linkTel" value="${application.linkTel }" bringbackname="customerRelationShipPro.linkTel" type="text" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        		</tr>
        	</tbody>
        </table>
        
      	<br/>
      	
      	<dl class="nowrap">
			<dt style="background-color: #fff"><label>导入EXCEL：</label></dt>
			<dd>
				<input type="file" class="required valid" id="affix" name="affix">
			</dd>
		</dl>
      	<br/>
    </div>
    
    <div class="formBar" style="width:98%">
    	<ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">提交申请</button>
            </div>
          </div>
        </li>
        </ul>
    </div>
    
  </form>
  
</div>

<script type="text/javascript">

$(function() {
	//清除功能;
	$("#customerRelationPro", navTab.getCurrentPanel()).click(function() {
		$("#projectCode", navTab.getCurrentPanel()).val("");
		$("#projectName", navTab.getCurrentPanel()).val("");
		$("#projectOwner", navTab.getCurrentPanel()).val("");
		$("#linkName", navTab.getCurrentPanel()).val("");
		$("#linkTel", navTab.getCurrentPanel()).val("");
	});
});

/*
 * 回调函数;
 */
function pro_callback() {
	
	var branchCompanyId = $("#branchCompanyId", navTab.getCurrentPanel()).val();
	$("#customerRelshipPro", navTab.getCurrentPanel()).attr("href", "${ path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+branchCompanyId);
	$("select[name='events.level2']", navTab.getCurrentPanel()).attr("refUrl", "${path}/resource/customerRelationShip!findComboByJobNumber.action?companyId="+$("#branchCompanyId").val()+"_");
	
	//清空数据;
	clearPro();
	return ;
}

 function testAjax(json) {
 	var data = json;
 	
 	if(data.statusCode == "200") {
 		alertMsg.correct(data.message);
 		navTab.reloadFlag(data.navTabId);
 		navTab.closeCurrentTab();
 	} else  {
 		alertMsg.error(data.message);
 	}
 	
 }
</script>