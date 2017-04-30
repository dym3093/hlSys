<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${path}/dwz/js/checkCode.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#superiorOrgId").click(function(){
		$(this).parent().find("input").each(function(){
			$(this).val("");
		});
		//设置链接为空
		$("#a_combo_id").attr("href", "${ path }/resource/customerRelationShip!productTree.action");
		
	});
	/*
	 *项目编码移开后根据输入编码通过接口获取数据(项目名称, 项目对接人);
	 *create by henry.xu 20160909
	 
	$("#proCode").blur(function() {
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path }/warehouse/storeApplication!getCrmBaseInfoByProCode.action",
		      data: {"proNum":$("#proCode").val()},
		      success: function(data){
		    	  var json =  data.result;
		    	  $("#proUser").val(json.projectOwner); //项目对接人
		    	  $("#proBelong").val(json.projectName); //项目名称;
		      	 
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
		      }    
		});
	});*/
	
	$(".onblurClass").live("blur", function() {
		var $th = $(this);
		var proNum = $th.val(); //项目编码;
		var count = 0; //判断该项目编码出现次数;大于1了提示不能重复录入相同项目编码;
		
		//减少交互次数
		if(proNum== null || proNum == '') {
			return;
		}
		
		$(".onblurClass").each(function(){
			if($(this).val() == proNum) {
				count ++;
			}
		});
		
		if(count > 1) {
			alert("不能重复输入相同的项目编码"+proNum+"!");
			$th.val("");
			return;
		}
		
		var $thParent = $th.parent().parent();
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path }/warehouse/storeApplication!getCrmBaseInfoByProCode.action",
		      data: {"proNum":proNum},
		      success: function(data){
		    	  var json =  data.result;
		    	  $thParent.find(".projectOwner").val(json.projectOwner);//项目对接人
		    	  $thParent.find(".projectName").val(json.projectName); //项目名称;
		    	  //20170413 edit by machuan
		    	//  $thParent.find(".projectType").val(json.projectType); //项目类型;
		    	  $thParent.find(".projectType").find("option[value='"+json.projectType+"']").attr("selected","selected");
		    	  $thParent.find(".projectType").bind("change",function(){
		    		 $(this).find("option[value='"+json.projectType+"']").attr("selected","selected");
		    	  })
		    	  /*
		    	   *处理项目类型name问题;因为不能向后台传参select的name属性;所以只有在这儿做处理;
		    	   */
		    	  var thName = $th.attr("name");
		    	  var newName = thName.substring(0, thName.indexOf("."))+".projectType";
		    	  $thParent.find("select[name='noname']").attr("name", newName);
		    	  
		    	  /*
		    	   * 处理是否封存name问题;同上;
		    	   */
		    	  var newIsSealName = thName.substring(0, thName.indexOf("."))+".isSeal";
		    	  $thParent.find("select[name='sealName']").attr("name", newIsSealName);
		    	  
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
		      }    
		});
	});
});

function test1(code1){
	$("#ownedCompany1").val(code1);
}
function test(code2){
	$("#city1").val(code2);
}
/**页面-客户名称验证 **/
function checkcustomerNames(code){
	var cityValue=$("#city1").val();
	var ownedCompanyValue=$("#ownedCompany1").val();
	$.ajax({
	      type: "post",
	      cache :false,
	      dataType: "json",
	      url: "../common/ajaxCheckCode!checkNameByHis.action",
	      data: {"beanName":$("#checkcustomerName").attr("beanName"),
	    	  "propertyName":$("#checkcustomerName").attr("propertyName"),
	    	  "propertyValue":code,
	    	  "propertyName2":"city",
	    	  "propertyValue2":cityValue,
	    	  "propertyName3":"ownedCompany",
	    	  "propertyValue3":ownedCompanyValue},
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
.onblurClass {}
.projectOwner {}
.projectName {}
</style>

<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	action="${path }/resource/customerRelationShip!savecustomerRelationShip.action" 
	method="post" theme="simple">
	
    <input type="hidden" name="navTabId" value="${ navTabId }"/>
    <div class="pageFormContent" layoutH="0" style="overflow: hidden;">
      <div>
        <div class="tip"><span>公司基本信息</span></div>
        <table class="pageFormContent">
        	<tr>
        		<td>
        	    	<label>总公司名称：</label>
		            <select id="ownedCompany" name="customerRelationShip.ownedCompany"  rel="iselect" onchange="test1(this.value)"  loadUrl="${path}/um/dept!treeRegion.action?defaultID=40289b6a5206079d0152061530000007"  class="required">
						 <option value="${customerRelationShip.ownedCompany}"></option>
					</select>
        		</td>
        		<td>
	        		<label>省 份：</label>
		            <select id="province" name="customerRelationShip.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=" class="required">
				    </select>
        		</td>
        		<td>
        			 <label>城 市：</label>
			         <select id="city" name="customerRelationShip.city" rel="iselect" onchange="test(this.value)"  class="required">
			         </select> 
        		</td>
					<input type="hidden" id="ownedCompany1" value=""/>
        		    <input type="hidden" vlaue="" id="city1"/>
        	</tr>
        	<tr>
        		<td>
        		    <label>公司名称：</label>
          			<input name="customerRelationShip.branchCommany" maxlength="60" type="text" onblur="checkcustomerNames(this.value)" beanName="CustomerRelationShip" propertyName="branchCommany" id="checkcustomerName" class="required textInput" value="${customerRelationShip.branchCommany }"/>
        		</td>
        		<td></td>
        		<td></td>
        	</tr>
        	<!-- 根据需求,屏蔽该功能; modified by henry.xu 20161117 -->
        	<%-- <tr>
        		<td colspan="2">
        		
        			<label>套餐：</label>
		            <input type="hidden" id="morecpId" name="customerRelationShip.orgNature" value="${customerRelationShip.orgNature}">
		            <input type="hidden" id="morecpName" name="customerRelationShip.combo" value="${customerRelationShip.combo}">
				    <input type="text" style="width:540px;" class="Input required disabled max"  maxlength="800" bringBackName = "customerRelationShip.combo" readonly="readonly" id="morecpName" value="${customerRelationShip.combo}"/>
				    <a id="a_combo_id" class="btnLook" href="${ path }/resource/customerRelationShip!productTree.action" target="dialog" width="800" height="450" lookupGroup="customer">查找带回</a>
				    <img src="${path}/images/clear.png" title="清除" id="superiorOrgId" style="padding-top: 6px;"/>
        		</td>
        		<td>
        			<label>收件人：</label>
          			<input name="customerRelationShip.recipients" class="textInput required" maxlength="200" type="text" id="recipients" value="${customerRelationShip.recipients }"/>
        		</td>
        	</tr> --%>
        	<tr>
        		<td colspan="2">
        			<label>详细地址：</label>
					<input name = "customerRelationShip.address" class="Input required" type="text" style="width:540px;"  maxlength="200"  id="address" value="${customerRelationShip.address}"/>
        		</td>
        		<td></td>
        	</tr>
        </table>
        
      <div class="divider"></div>
      <div>
        <div class="tip"><span>保险公司联系人</span></div>
  		<table class="list nowrap itemDetail" addButton="新增" width="100%">
          <thead>
            <tr>
                <th type="num" name="linkList[#index#].num" defaultVal="#index#" size="5" style="width:40px;" filedStyle="width: 40px";">序号</th>
				<th type="text" name="linkList[#index#].department" filedStyle="width: 300px"" fieldClass="required">部门</th>
				<th type="text" name="linkList[#index#].functions" filedStyle="width: 300px" fieldClass="required">寄送地址</th>
				<th type="text" name="linkList[#index#].linkMan" filedStyle="width: 320px" fieldClass="required">收件人姓名</th>
				<th type="text" name="linkList[#index#].phone" filedStyle="width: 300px" fieldClass="required phone textInput">收件人电话</th>
				<th type="del" width="28px">操作</th>
            </tr>
          </thead>
          <tbody>
           
          </tbody>
        </table>
         
      </div>
	<div class="divider" style="margin-top: 15px;"></div>
	<div inited="1000" style="display: block;">
		<div class="tip"><span>项目信息列表</span></div>
		<table class="list nowrap itemDetail" addButton="新增" style="clear: both;" width="100%">
			<thead>
				<tr>
					<th type="text" name="shipPros[#index#].projectCode" filedStyle="width: 100px" fieldClass="required onblurClass">项目编码</th>
					<th type="text" name="shipPros[#index#].projectName" filedStyle="width: 100px;" fieldAttrs="{'readonly':'readonly'}" fieldClass="required projectName" >项目名称</th>
					<th type="text" name="shipPros[#index#].projectOwner" filedStyle="width: 80px;" fieldAttrs="{'readonly':'readonly'}"  fieldClass="required projectOwner">项目负责人</th>
					<th type="enum" name="shipPros[#index#].projectType" enumurl="${path }/resource/customerRelationShip!findProTypes.action" fieldClass="required projectType">项目类型</th>
					<th type="text" name="shipPros[#index#].linkName" filedStyle="width: 90px" fieldClass="required">远盟对接人</th>
					<th type="text" name="shipPros[#index#].linkTel" filedStyle="width: 100px" >远盟对接人电话</th>
					<th type="text" name="shipPros[#index#].mailAddress" filedStyle="width: 150px" fieldClass="required">邮件地址</th>
					<th type="text" name="shipPros[#index#].reception" filedStyle="width: 60px" fieldClass="required">收件人</th>
					<th type="text" name="shipPros[#index#].receptionTel" filedStyle="width: 100px" fieldClass="required">收件人电话</th>
					<th type="text" name="shipPros[#index#].batchPre" filedStyle="width: 100px">批次前缀</th>
					<th type="enum" name="shipPros[#index#].isSeal" enumurl="${path }/resource/customerRelationShip!findIsSeal.action" filedStyle="width: 60px"  >是否封存</th>
					<th type="del" width="30px">操作</th>
				</tr>
			</thead>
			<tbody>
			       
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