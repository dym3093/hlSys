<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	style="overflow: hidden;"   
	action="${path}/warehouse/application!saveOrUpdateYgbx.action" method="post" >
	
    <input type="hidden" name="navTabId" id="navTabId" value="${navTabId }" />
    <input type="hidden" name="application.id" value="${application.id }" />
    
    <div class="pageFormContent" layoutH="35" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
        <table width="100%">
        	<tbody>
        		<tr>
        			<td><label>总公司名称：</label></td>
        			<td>
        				<input type="text" name="application.ownerCompanyName" value="${dept.deptName }" readonly="readonly"/>
				   		<input name="application.ownerCompanyId" type="hidden" value="${userInfo.deptId }"/>
        			</td>
        			<td><label>支公司名称：</label></td>
        			<td>
        				<input type="text" name="application.bannyCompanyName" value="${ship.branchCommany }" readonly="readonly"/>
        				<input type="hidden" name="application.bannyCompanyId" value="${userInfo.jobNumber }"/>
        			</td>
        			<td><label>期望日期：</label></td>
        			<td>
        				<input type="text" name="application.hopeDateBak" class="required" style="width: 170px;" onFocus="WdatePicker({minDate:'${application.hopeDate}'})"  readonly="readonly" value="${application.hopeDate}"/>
        				<a class="inputDateButton" href="javascript:;">选择</a>
        			</td>
        		</tr>
        		<tr>
        			<td><label>收件人姓名：</label></td>
        			<td>
        				<input id="receiveName" name="application.receiveName" type="text" class="required" value="${application.receiveName }" />
        			</td>
        			<td><label>收件人联系电话：</label></td>
        			<td>
        				<input id="receiveTel" name="application.receiveTel" type="text" class="phone" value="${application.receiveTel }" />
        			</td>
        			<td><label>寄送地址：</label></td>
        			<td><input id="" id="addressOrg" name="application.address" value="${application.address==null || application.address== '' ? ship.address : "" }" type="text" class="required" /></td>
        		</tr>
        		<tr>
        			<td><label>省份：</label></td>
        			<td>
        				<select id="provinceId" class="required" name="" rel="iselect" 
							loadUrl="${path}/system/region!treeRegion.action"
							ref="cityId" refUrl="${path}/system/region!treeRegionDispose.action?parentId=" onchange="selectChangeEvent(this);">
							
						</select>
						<input id="proviceName" name="application.proviceName" type="hidden" value="${application.proviceName }"/>
        			</td>
        			<td><label>城市：</label></td>
        			<td>
        				<select id="cityId" name="" class="required" rel="iselect" onchange="selectChangeEvent(this);">
        					
        				</select>
						<input id="cityName" name="application.cityName" type="hidden" value="${application.cityName }"/>
        			</td>
        			
        		</tr>
        		<tr>
        			<td><label style="height: 60px;">需求说明：</label></td>
        			<td colspan="5">
        				<textarea  name="application.requirement" cols="35" ows="2" style="width:570px;" class="required">${application.requirement}</textarea>
        			</td>
        		</tr>
        	</tbody>
        </table>
        
      	<br/>
      	
      	<dl class="nowrap">
			<dt style="background-color: #fff"><label>选择品类：</label></dt>
			<dd>
				<a class="button" href="javascript:void(0);" id="addProduct"><span>添加产品套餐</span></a>
			</dd>
		</dl>
      	<br/>
    	<fieldset id="applyInfo" style="width:100%;">
			<legend>【<span id="status_text" style="color:#ff0000;">产品套餐列表明细</span>】</legend>
	  	    <table id="data_grid" class="list" style="width: 100%">
		  	    <thead>
					<tr>
						<th width="60px">序号</th>
						<th>套餐名称</th>
						<th width="210px">申请数量</th>
						<th width="50px">操作</th>
					</tr>					
				</thead>
				<tbody id="application_tbody_id">
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
        <li>
          <div class="button">
            <div class="buttonContent">
              <button id="clearText" type="reset" >重置</button>
            </div>
          </div>
        </li>
        </ul>
    </div>
    
  </form>
  
</div>

<script type="text/javascript">
	

$(function() {
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
	/*
	 * 添加产品信息列表
	 */
	$("#addProduct", navTab.getCurrentPanel()).on("click", function() {
		//获取当前页面id
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/warehouse/application!addYgbxProduct.action?params.isClose=0&params.hiddenName=yg&navTabId="+navTabId, "addProductDetail", "产品信息列表", 
				{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}) ;

});
 
/*
 * select选中改变事件;
 */
function selectChangeEvent(obj) {
	 var optText = $(obj).children("option[selected='selected']").html().trim(); 
	 $(obj).parent().parent().find("input[type='hidden']").val(optText);
}



</script>