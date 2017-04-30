<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
$(document).ready(function(){
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
	}); */
	 
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
		    	  //20170414 eidt by machuan
		    	  $thParent.find(".projectType").unbind();
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
	$("#ownedCompanyName1").val($("select[id^=ownedCompany]").find("option:selected").text());
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

function setDefaultValue(select,projectType){
	$(select).find("option[value='"+projectType+"']").attr("selected","selected");
}

</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
<div class="pageContent">
  <form method="post" action="${path }/resource/customerRelationShip!updateCustomer.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" novalidate="novalidate" class="pageForm required-validate">
  <input type="hidden" name="navTabId" value="${ navTabId }"/>
  <div class="pageFormContent" layoutH="50" style="overflow: hidden;">
      <div>
       <input type="hidden" name="customerRelationShip.createTime" value="${customerRelationShip.createTime}"/>
	   <input type="hidden" name="customerRelationShip.id" value="${customerRelationShip.id}"/>
        <div class="tip"><span>公司基本信息</span></div>
      
         <table class="pageFormContent">
        	<tr>
        		<td>
        	    	<label>总公司名称：</label>
		            <select id="ownedCompany" name="customerRelationShip.ownedCompany"  rel="iselect" onchange="test1(this.value)"  loadUrl="${path}/um/dept!treeRegion.action?defaultID=40289b6a5206079d0152061530000007" >
						 <option value="${customerRelationShip.ownedCompany}"></option>
					</select>
					<input type="hidden" id="ownedCompany1" value=""/>
					<input type="hidden" id="ownedCompanyName1" name="customerRelationShip.customerNameSimple" value=""/>
        		</td>
        		<td>
	        		<label>省 份：</label>
		            <select id="province" name="customerRelationShip.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
				    <option value="${ customerRelationShip.province }"></option>
				    </select>
        		</td>
        		<td>
        			 <label>城 市：</label>
			         <select id="city" name="customerRelationShip.city" rel="iselect" onchange="test(this.value)">
			         <option value="${ customerRelationShip.city }"></option>
			         </select> 
        		    <input type="hidden" vlaue="" id="city1"/>
        		</td>
        	</tr>
        	<tr>
        		<td>
        		    <label>公司名称：</label>
          			<input name="customerRelationShip.branchCommany" maxlength="60" type="text" onblur="checkcustomerNames(this.value)" beanName="CustomerRelationShip" propertyName="branchCommany" id="checkcustomerName" class="required textInput" value="${customerRelationShip.branchCommany }"/>
        		</td>
        		<td></td>
        		<td></td>
        	</tr>
        	<%-- <tr>
        		<td colspan="2">
					<label>套餐：</label>
		            <input type="hidden" id="morecpId" name="customerRelationShip.orgNature" value="${customerRelationShip.orgNature}">
		            <input type="hidden" id="morecpName" name="customerRelationShip.combo" value="${customerRelationShip.combo}">
				    <input type="text" style="width:540px;" class="Input required disabled max"  maxlength="800" bringBackName = "customerRelationShip.combo" readonly="readonly" id="morecpNameId" value="${customerRelationShip.combo}"/>
				    <a id="a_combo_id" class="btnLook" href="${ path }/resource/customerRelationShip!productTree.action?orgNature=${customerRelationShip.orgNature}" target="dialog" width="600" height="450" lookupGroup="customer">查找带回</a>
				    <img src="${path}/images/clear.png" title="清除" id="superiorOrgId" style="padding-top: 6px;"/>
        		</td>
        		<td></td>
        		<td>
        			<label>收件人：</label>
          			<input name="customerRelationShip.recipients" class="textInput required" maxlength="200" type="text" id="recipients" value="${customerRelationShip.recipients }"/>
        		</td> 
        	</tr>--%>
        	<tr>
        		<td colspan="2">
        			<label>详细地址：</label>
					<input name = "customerRelationShip.address" class="Input required" type="text" style="width:540px;"  maxlength="200"  id="address" value="${customerRelationShip.address}"/>
        		</td>
        		<td></td>
        		<%-- <td>
        			<label>收件人电话：</label>
          			<input name="customerRelationShip.recipientsTel" class="textInput required phone" maxlength="200" type="text" id="recipientsTel"  value="${customerRelationShip.recipientsTel }"/>
        		</td> --%>
        	</tr>
        	<%-- <tr>
        		<td colspan="3">
        			<label>邮寄地址：</label>
					<input name = "customerRelationShip.mailAddr" class="Input required" type="text" style="width:540px;" maxlength="200" id="mailAddr" value="${customerRelationShip.mailAddr}"/>
        		</td>
        	</tr> --%>
        </table>
      </div>
      <div class="divider"></div>
      <div>
        <div class="tip"><span>保险公司联系人</span></div>
        <div></div>
  <table class="list nowrap itemDetail" addButton="新增" width="100%">
          <thead>
            <tr>
           		<th type="num" name="linkList[#index#].num" defaultVal="#index#" style="width:40px;" filedStyle="width: 40px"> 序号</th>
				<th type="text" name="linkList[#index#].department" filedStyle="width: 200px">部门</th>
				<th type="text" name="linkList[#index#].linkMan" fieldClass="required" filedStyle="width: 220px">收件人姓名</th>
				<th type="text" name="linkList[#index#].phone" fieldClass="phone" filedStyle="width: 200px">收件人电话</th>
				<th type="text" name="linkList[#index#].functions" filedStyle="width: 200px">寄送地址</th>
				<th type="del" width="28px">操作</th>
            </tr>
          </thead>
          <tbody>
           <c:forEach items="${customerRelationShip.customerRelationshipLinkSet}" var="link" varStatus="status">
          <tr>
	          <td>
	        <%--  <input type="hidden" name="linkList['${status.count-1}'].id" value="${link.id}"/>  --%>
	          <input type="text"  value="${link.num}" name="linkList[${status.count-1}].num" style="width:40px;" readonly="readonly" size="5"></td>
	          <td><input class="textInput" type="text" value="${link.department}" name="linkList[${status.count-1}].department" maxlength="100" style="width: 200px;"></td>
	          <td><input class="textInput" type="text" value="${link.linkMan}" name="linkList[${status.count-1}].linkMan" maxlength="50" style="width: 220px;"></td>
	          <td><input type="text" value="${link.phone }" value="${link.phone}" name="linkList[${status.count-1}].phone" maxlength="32" style="width: 200px;"></td>
	          <td><input class="textInput" type="text" value="${link.functions}" name="linkList[${status.count-1}].functions" maxlength="32" style="width: 200px;"></td>
	          <td><a class="btnDel " href="javascript:void(0)">删除</a></td>
          </tr>
          </c:forEach>
          
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
					<th type="text" name="shipPros[#index#].projectName" filedStyle="width: 100px"  fieldAttrs="{'readonly':'readonly'}" fieldClass="projectName required">项目名称</th>
					<th type="text" name="shipPros[#index#].projectOwner" filedStyle="width: 80px"  fieldAttrs="{'readonly':'readonly'}" fieldClass="projectOwner required">项目负责人</th>
					<th type="enum"  name="shipPros[#index#].projectType" enumurl="${path }/resource/customerRelationShip!findProTypes.action" fieldClass="required">项目类型</th>
					<th type="text" name="shipPros[#index#].linkName" filedStyle="width: 90px" fieldClass="required">远盟对接人</th>
					<th type="text" name="shipPros[#index#].linkTel" filedStyle="width: 100px" >远盟对接人电话</th>
					<th type="text" name="shipPros[#index#].mailAddress" filedStyle="width: 150px" fieldClass="required">邮件地址</th>
					<th type="text" name="shipPros[#index#].reception" filedStyle="width: 60px" fieldClass="required">收件人</th>
					<th type="text" name="shipPros[#index#].receptionTel" filedStyle="width: 100px" fieldClass="required">收件人电话</th>
					<th type="text" name="shipPros[#index#].batchPre" filedStyle="width: 100px">批次前缀</th>
					<th type="enum" name="shipPros[#index#].isSeal" enumurl="${path }/resource/customerRelationShip!findIsSeal.action" filedStyle="width: 80px"  >是否封存</th>
					<th type="del" width="30px">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shipPros }" var="pro" varStatus="status">
					<tr class="unitBox">
						<td>
							<input type="hidden" name="shipPros[${status.index }].id" value="${pro.id }"/>
							<input type="text" name="shipPros[${status.index }].projectCode" value="${pro.projectCode }" style="width: 100px;" class="required onblurClass"/>
						</td>
						<td>
							<input type="text" name="shipPros[${status.index }].projectName" readonly="readonly" value="${pro.projectName }" style="width: 100px;" class="required projectName"/>
						</td>
						<td>
							<input type="text" name="shipPros[${status.index }].projectOwner" readonly="readonly" value="${pro.projectOwner }" style="width: 80px;" class="required projectOwner"/>
						</td>
						<td>
							<select name="shipPros[${status.index }].projectType" onchange="setDefaultValue(this,'${ pro.projectType}')" style="width:100px;" class="required projectType">
								<c:forEach items="${proTypes }" var="prtype">
									<option value="${prtype.id }" ${prtype.id == pro.projectType ? 'selected' : '' }>${prtype.projectTypeName }</option>
								</c:forEach>
							</select>
						</td>
						<td><input type="text" name="shipPros[${status.index }].linkName" value="${pro.linkName }" style="width: 90px;" class="required"/></td>
						<td><input type="text" name="shipPros[${status.index }].linkTel" value="${pro.linkTel }" style="width: 100px;"/></td>
						<td><input type="text" name="shipPros[${status.index }].mailAddress" value="${pro.mailAddress }" style="width: 150px;" class="required"/></td>
						<td><input type="text" name="shipPros[${status.index }].reception" value="${pro.reception }" style="width: 60px;" class="required"/></td>
						<td><input type="text" name="shipPros[${status.index }].receptionTel" value="${pro.receptionTel }" style="width: 100px;" class="required"/></td>
						<td><input type="text" name="shipPros[${status.index }].batchPre" value="${pro.batchPre }" style="width: 100px;" /></td>
						<td>
							<select name="shipPros[${status.index }].isSeal" style="width: 100px;">
								<option value="0" ${pro.isSeal=="0"? "selected" : "" }>否</option>
								<option value="1" ${pro.isSeal=="1"? "selected" : "" }>是</option>
							</select>
						</td>
						<td><a href="javascript:void(0);" class="btnDel">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	     
	</div>
	
    </div>
      
    
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
       <!--  <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
            </div>
          </div>
        </li> -->
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
</div>
</body>
</html>