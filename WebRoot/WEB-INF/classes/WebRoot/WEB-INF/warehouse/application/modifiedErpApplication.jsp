<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/application!saveOrUpdate.action" method="post" >
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="application.id" value="${application.id }" />
    
    <div class="pageFormContent" layoutH="35" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
        <table width="100%">
        	<tbody>
        		<tr>
        			<td><label>总公司名称：</label></td>
        			<td>
        				<select id="ownedCompany" name="application.ownerCompanyId" class="required" rel="iselect" 
			 		    loadUrl="${path}/um/dept!treeRegion.action?defaultID=40289b6a5206079d0152061530000007" onchange="selectChangeEvent(this);"
			 		    ref="bannyCompanyId" refUrl="${path}/resource/customerRelationShip!treeRegion.action?defaultID=" >
				 		    <option value="${application.ownerCompanyId }"></option>
				   		</select>
				   		<input id="ownerCompanyName" name="application.ownerCompanyName" value="${application.ownerCompanyName }" type="hidden"/>
        			</td>
        			<td><label>支公司名称：</label></td>
        			<td>
        				<select id="bannyCompanyId" name="application.bannyCompanyId" rel="iselect" class="required" onchange="bannyCompanyChangeEvent(this);">
        					 <option value="${application.bannyCompanyId }"></option>
        				</select>
        				<input id="bannyCompanyName" name="application.bannyCompanyName" value="${application.bannyCompanyName }" type="hidden"/>
        			</td>
        			<td><label>项目编码：</label></td>
        			<td>
			       		<input id="projectCode" name="application.projectCode" bringbackname="customerRelationShipPro.projectCode" readonly="readonly" class="required textInput" type="text" value="${application.projectCode }"/>
			       		<a id="customerRelshipPro" class="btnLook" href="${path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId=${application.bannyCompanyId }" lookupGroup="customerRelationShipPro">查找带回</a>
						<img src="${path}/images/clear.png" title="清除公司信息" style="cursor: pointer; margin-top: 6px;" id="customerRelationPro" />
        			</td>
        		</tr>
        		<tr>
        			<td><label>项目名称：</label></td>
        			<td>
        				<input id="projectName" name="application.projectName" type="text" class="required" value="${application.projectName }" bringbackname="customerRelationShipPro.projectName" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			<td><label>项目负责人：</label></td>
        			<td>
        				<input id="projectOwner" name="application.projectOwner" value="${application.projectOwner }" bringbackname="customerRelationShipPro.projectOwner" type="text" class="required" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			<td><label>项目对接人：</label></td>
        			<td>
        				<input id="linkName" name="application.linkName" value="${application.linkName }" bringbackname="customerRelationShipPro.linkName" type="text" class="required" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        		</tr>
        		<tr>
        			<td><label>对接人电话：</label></td>
        			<td>
        				<input id="linkTel" name="application.linkTel" value="${application.linkTel }" bringbackname="customerRelationShipPro.linkTel" type="text" readonly="readonly" style="background-color:#eee;"/>
        			</td>
        			<td><label>收件人姓名：</label></td>
        			<td>
        				<input id="receiveName" name="application.receiveName" type="text" class="required" value="${application.receiveName }" />
        			</td>
        			<td><label>收件人联系电话：</label></td>
        			<td>
        				<input id="receiveTel" name="application.receiveTel" type="text" class="phone" value="${application.receiveTel }" />
        			</td>
        		</tr>
        		<tr>
        			<td><label>期望日期：</label></td>
        			<td>
        				<input type="text" name="application.hopeDateBak" class="required" style="width: 170px;" onFocus="WdatePicker({minDate:'${application.hopeDate}'})"  readonly="readonly" value="${application.hopeDate}"/>
        				<a class="inputDateButton" href="javascript:;">选择</a>
        			</td>
        			<td><label>寄送地址:</label></td>
        			<td colspan="3"><input id="" id="addressOrg" name="application.address" value="${application.address }" type="text" class="required" /></td>
        		
        		</tr>
        		<tr>
        			<td><label style="height: 60px;">需求说明：</label></td>
        			<td colspan="5">
        				<textarea name="application.requirement" cols="35" ows="2" style="width:570px;" class="required">${application.requirement}</textarea>
        			</td>
        		</tr>
        		<tr>
        			<td><label style="height: auto">派往角色：</label></td>
        			<td colspan="5">
					   <span style="color:red">远盟基因业务部</span>
        			</td>
        		</tr>
        	</tbody>
        </table>
        
      	<br/>
      	
      	<dl class="nowrap">
			<dt style="background-color: #fff"><label>选择品类：</label></dt>
			<dd>
				<a class="button" href="javascript:void(0);" onclick="addProduct();"><span>添加产品套餐</span></a>
			</dd>
		</dl>
      	<br/>
    	<fieldset id="applyInfo" style="width:100%;">
			<legend>【<span id="status_text" style="color:#ff0000;">产品套餐列表明细</span>】</legend>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="60px">序号</th>
						<th>产品套餐名称</th>
						<th width="200px">产品名称</th>
						<th width="200px">产品类别</th>
						<th width="300px">产品规格</th>
						<th width="200px">申请数量</th>
						<th width="50px">操作</th>
					</tr>					
				</thead>
				<tbody id="application_tbody_id">
					<c:forEach items="${details }" var="item" varStatus="stat">
						<tr style='text-align: center;' count="${stat.index }" proComboId="${item.proComboId }">
							<td style="text-align: center;">
								<input type="hidden" name="detail[${stat.index }].id"value=""/>
								<input type='hidden' name='details[${stat.index }].applicationId' value=''/>
								<input type='hidden' name='details[${stat.index }].productId' value='${item.productId }'/>
								<input type='hidden' name='details[${stat.index }].proComboId' value='${item.proComboId }'/>
								<span name="numNo">${stat.count }</span>
							</td>
							<td style="text-align: left;">${item.productComboName }</td>
							<td style="text-align: left;">${item.productName }</td>
							<td style="text-align: left;">${item.productTypeName }</td>
							<td style="text-align: left;">${item.standard }</td>
							<td style="text-align: left;"><input type="text" name="details[${stat.index }].applicationCountInt" value="${item.applicationCount }" class="required textInput digits"/></td>
							<td><a href='javascript: void(0);' class='btnDel' style='margin-left: 17px;'>删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
	   		</table>
   		</fieldset>
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
	
	//明细数据删除操作
	$(".btnDel").live("click", function(){
		$(this).parent().parent().remove();
		//处理序号;
		var trCount = 0;
		$("#application_tbody_id", navTab.getCurrentPanel()).find("tr").each(function(index) {
			trCount = index+1;
			$(this).find("span[name='numNo']").html(trCount);
		}) ;
		
	});
});

/*
 * 支公司改变后获取值,并设置给项目编码选择;
 */
function bannyCompanyChangeEvent(obj) {
	var bannyCompanyId = $(obj).val();
	//访问地址;
	var hrefUrl = "${path }/resource/customerRelationShip!customerRelshipPro.action?customerRelShipId="+bannyCompanyId;
	$("#customerRelshipPro", navTab.getCurrentPanel()).attr("href", hrefUrl);
	
	//清空数据;
	$("#projectCode", navTab.getCurrentPanel()).val("");
	$("#projectName", navTab.getCurrentPanel()).val("");
	$("#projectOwner", navTab.getCurrentPanel()).val("");
	$("#linkName", navTab.getCurrentPanel()).val("");
	$("#linkTel", navTab.getCurrentPanel()).val("");
	
	
	
	
	
	
	
	selectChangeEvent(obj);
}
 
/*
 * select选中改变事件;
 */
function selectChangeEvent(obj) {
	 var optText = $(obj).children("option[selected='selected']").html().trim(); 
	 $(obj).parent().parent().find("input[type='hidden']").val(optText);
}

/*
 * 添加产品信息列表
 */
function addProduct() {
	//获取当前页面id
	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	$.pdialog.open("${path}/warehouse/application!addProduct.action?navTabId="+navTabId, "addProductDetail", "产品信息列表", 
			{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
}

</script>