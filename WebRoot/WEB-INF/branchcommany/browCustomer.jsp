<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style type="text/css">
#table2 td {
	TEXT-ALIGN: center;
	BACKGROUND-COLOR: #f7f7f7;
	padding: 4, 4, 4, 4;
}

#table2 table {
	FONT-SIZE: 12pt;
	cellspacing: 10;
	cellpadding: 10;
	padding: 30, 30, 30, 30;
	width: 100%;
}

#table2 input {
	BACKGROUND-COLOR: #f7f7f7;
	border: none;
}
</style>
<script type="text/javascript">
	$("input", navTab.getCurrentPanel()).each(function() {
		$(this).attr("readonly", "readonly");
	});	
	
	$("select", navTab.getCurrentPanel()).each(function() {
		$(this).attr("disabled", "disabled");
	});
</script>

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
        		</td>
        		<td>
	        		<label>省 份：</label>
		            <select id="province" name="customerRelationShip.province" rel="iselect" loadUrl="${path}/system/region!treeRegion.action" ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
				    <option value="${ customerRelationShip.province }"></option>
				    </select>
        		</td>
        		<td>
        			 <label>城 市：</label>
			         <select id="city" name="customerRelationShip.city" rel="iselect" onchange="test(this.value)" >
			         <option value="${ customerRelationShip.city }"></option>
			         </select> 
        		    <input type="hidden" vlaue="" id="city1"/>
        		</td>
        	</tr>
        	<tr>
        		<td>
        		    <label>公司名称：</label>
          			<input name="customerRelationShip.branchCommany" maxlength="60" type="text" beanName="CustomerRelationShip" propertyName="branchCommany" id="checkcustomerName" class="textInput" value="${customerRelationShip.branchCommany }"/>
        		</td>
        		<td></td>
        		<td></td>
        	</tr>
        	<%-- <tr>
        		<td colspan="2">
					<label>套餐：</label>
		            <input type="hidden" id="morecpId" name="customerRelationShip.orgNature" value="${customerRelationShip.orgNature}">
		            <input type="hidden" id="morecpName" name="customerRelationShip.combo" value="${customerRelationShip.combo}">
				    <input type="text" style="width:540px;" class="Input disabled max"  maxlength="800" bringBackName = "customerRelationShip.combo" readonly="readonly" id="morecpNameId" value="${customerRelationShip.combo}"/>
				    <a id="a_combo_id" class="btnLook" href="${ path }/resource/customerRelationShip!productTree.action?orgNature=${customerRelationShip.orgNature}" target="dialog" width="600" height="450" lookupGroup="customer">查找带回</a>
				    <img src="${path}/images/clear.png" title="清除" id="superiorOrgId" style="padding-top: 6px;"/>
        		</td>
        		<td></td>
        	</tr> --%>
        	<tr>
        		<td colspan="2">
        			<label>详细地址：</label>
					<input name = "customerRelationShip.address" class="Input" type="text" style="width:540px;"  maxlength="200"  id="address" value="${customerRelationShip.address}"/>
        		</td>
        		<td></td>
        	</tr>
        	<%-- <tr>
        		<td colspan="3">
        			<label>邮寄地址：</label>
					<input name = "customerRelationShip.mailAddr" class="Input" type="text" style="width:540px;" maxlength="200" id="mailAddr" value="${customerRelationShip.mailAddr}"/>
        		</td>
        	</tr> --%>
        </table>
      </div>
      <div class="divider"></div>
      <div>
        <div class="tip"><span>保险公司联系人</span></div>
        <div></div>
  <table class="list nowrap" addButton="" width="100%">
          <thead>
            <tr>
           		<th type="num" name="linkList[#index#].num" defaultVal="#index#" style="width:40px;" filedStyle="width: 40px"> 序号</th>
				<th type="text" name="linkList[#index#].department" filedStyle="width: 200px">部门</th>
				<th type="text" name="linkList[#index#].linkMan" fieldClass="required" filedStyle="width: 220px">收件人姓名</th>
				<th type="text" name="linkList[#index#].phone" fieldClass="phone" filedStyle="width: 200px">收件人电话</th>
				<th type="text" name="linkList[#index#].functions" filedStyle="width: 200px">寄送地址</th>
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
		<table class="list nowrap" addButton="" style="clear: both;" width="100%">
			<thead>
				<tr>
					<th type="text" name="shipPros[#index#].projectCode" filedStyle="width: 100px" >项目编码</th>
					<th type="text" name="shipPros[#index#].projectName" filedStyle="width: 100px" >项目名称</th>
					<th type="text" name="shipPros[#index#].projectOwner" filedStyle="width: 80px" >项目负责人</th>
					<th type="enum"  name="shipPros[#index#].projectType" enumurl="${path }/resource/customerRelationShip!findProTypes.action" >项目类型</th>
					<th type="text" name="shipPros[#index#].linkName" filedStyle="width: 90px" >远盟对接人</th>
					<th type="text" name="shipPros[#index#].linkTel" filedStyle="width: 100px" >远盟对接人电话</th>
					<th type="text" name="shipPros[#index#].mailAddress" filedStyle="width: 150px" >邮件地址</th>
					<th type="text" name="shipPros[#index#].reception" filedStyle="width: 80px" >收件人</th>
					<th type="text" name="shipPros[#index#].receptionTel" filedStyle="width: 100px" >收件人电话</th>
					<th type="text" name="shipPros[#index#].batchPre" >批次前缀</th>
					<th type="text" name="shipPros[#index#].receptionTel" filedStyle="width: 100px" >是否封存</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shipPros }" var="pro" varStatus="status">
					<tr class="unitBox">
						<td>
							<input type="hidden" name="shipPros[${status.index }].id" value="${pro.id }"/>
							<input type="text" name="shipPros[${status.index }].projectCode" value="${pro.projectCode }" style="width: 100px;" />
						</td>
						<td>
							<input type="text" name="shipPros[${status.index }].projectName" value="${pro.projectName }" style="width: 100px;" />
						</td>
						<td>
							<input type="text" name="shipPros[${status.index }].projectOwner" value="${pro.projectOwner }" style="width: 80px;" />
						</td>
						<td>
							<select name="shipPros[${status.index }].projectType" style="width:160px;" class="projectType">
								<c:forEach items="${proTypes }" var="prtype">
									<option value="${prtype.id }" ${prtype.id == pro.projectType ? 'selected' : '' }>${prtype.projectTypeName }</option>
								</c:forEach>
							</select>
						</td>
						<td><input type="text" name="shipPros[${status.index }].linkName" value="${pro.linkName }" style="width: 90px;" /></td>
						<td><input type="text" name="shipPros[${status.index }].linkTel" value="${pro.linkTel }" style="width: 100px;"/></td>
						<td><input type="text" name="shipPros[${status.index }].mailAddress" value="${pro.mailAddress }" style="width: 150px;" /></td>
						<td><input type="text" name="shipPros[${status.index }].reception" value="${pro.reception }" style="width: 80px;" /></td>
						<td><input type="text" name="shipPros[${status.index }].receptionTel" value="${pro.receptionTel }" style="width: 100px;" /></td>
						<td><input type="text" name="shipPros[${status.index }].batchPre" value="${pro.batchPre }" style="width:75px;"/></td>
						<td>
							<select name="shipPros[${status.index }].isSeal" style="width: 100px;">
								<option value="0" ${pro.isSeal=="0"? "selected" : "" }>否</option>
								<option value="1" ${pro.isSeal=="1"? "selected" : "" }>是</option>
							</select>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	     
	</div>
	
    </div>
  </form>
</div>
</div>
