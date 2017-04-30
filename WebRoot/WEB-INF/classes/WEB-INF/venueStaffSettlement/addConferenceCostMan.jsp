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
	});
	
	$("button[type='button']:contains('新增人员费用')").live('click',function(){
		
		$("#addConference").find("tr:last").children().find("input[name*='Cost'], input[name*='amount']").val(0);
		
		$("td").delegate("input[name*='Cost']", "change", function(){
			//费用累加·
			var amount = new Number(0);
			$(this).parents("tr").children().find("input[name*='Cost']").each(function(i){
				amount +=Number($(this).val());
			});
			$(this).parents("tr").children().find("input[name*='amount']").val(amount);
			//总费用
			var produceCost = new Number(0);
			$("input[name*='amount']").each(function(i){
				produceCost +=Number($(this).val());
			});
			$("input[name='total']").val(produceCost);
		}); 
	});
	
});
/**页面-客户名称验证 **/
function checkcustomerNames(code){
	var checkHisName_a =$(".checkHisName_a").val();
	if(!(checkHisName_a==code)){
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "../common/ajaxCheckCode!checkNameByHis.action",
		      data: {"beanName":$("#checkcustomerName").attr("beanName"),"propertyName":$("#checkcustomerName").attr("propertyName"),"propertyValue":code},
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
}

//验证数字
function validateNum(me){
	var cost = me.value;
	if(me!=null&&me!=""&&me!=undefined&&me!="undefined"){
		 var numReg = new RegExp("^[0-9]*$");
		if(!cost.match(numReg)){
			alertMsg.error("请输入数字！");
		}
	}
}



</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
<div class="pageContent">
  <form method="post" action="${path }/venueStaffSett/conferCostMan!saveConferenceStaffCost.action?id=${obj.id}&conferenceNo=${obj.conferenceNo}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" novalidate="novalidate" class="pageForm required-validate">
  <input type="hidden" name="navTabId" value="${ navTabId }"/>
  
  <div class="pageFormContent" style="padding:0;" layoutH="56">
      <div>
       	<input type="hidden" name="obj.createTime" value="${obj.createTime}"/>
	   	<input type="hidden" name="obj.id" value="${obj.id}"/>
        <div class="tip"><span>会议基本信息</span></div>
		
		<p>
          <label>会议号：</label>
          <input name="obj.conferenceNo" maxlength="60" type="text" readonly="readonly"
          	beanName="Customer" propertyName="conferenceNo" id="conferenceNo"   value="${ obj.conferenceNo }"/>
        </p>
        <p>
          <label>会议日期：</label>
          <input name="obj.conferenceDate" maxlength="60" type="text" readonly="readonly"  beanName="obj" propertyName="conferenceDate"   value="${ obj.conferenceDate }"/>
        </p>
        <p>
          <label>人数：</label>
          <input name="" maxlength="60" type="text" readonly="readonly"  beanName="obj" propertyName="settNumbers"   value="${ obj.settNumbers}"/>
        </p>
       <p>
          <label>省 份：</label>
          <input type="text" name="obj.provice" value="<hpin:id2nameDB id="${obj.provice}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly"/>
        </p>
        <p>
          <label>城 市：</label>
          <input type="text" name="obj.city" value="<hpin:id2nameDB id="${obj.city}" beanId="org.hpin.base.region.dao.RegionDao"/>" readonly="readonly">
        </p>
        <p>
          <label>所属公司：</label>
          <input type="text" name="obj.ownedCompany" readonly="readonly" maxlength="20" value="${obj.ownedCompany}"/>
        </p>
        <p>
          <label>支公司：</label>
          <input type="text" name="obj.branchCompany" readonly="readonly" maxlength="30" value="${obj.branchCompany}"/>
        </p>
       
      </div>
      
      <div class="divider"></div>
      
      <div style="overflow:auto;" layoutH="170">
        <div class="tip"><span>人员费用信息</span></div>
        <div >
           <table id="addConference" class="list nowrap itemDetail" addButton="新增人员费用" width="100%">
            <thead>
              <tr>
                 <th type="num" name="num" defaultVal="#index#" filedStyle="width: 30px">序号</th>
				<th type='text' name='entityList[#index#].staff' filedStyle='width: 100px'>姓名</th>
				<th type='text' name='entityList[#index#].position' filedStyle='width: 100px'>职位</th>
				<th type='text' name='entityList[#index#].trafficCost' filedStyle='width: 80px'>交通费(元)</th>
				<th type='text' name='entityList[#index#].hotelCost' filedStyle='width: 80px'>住宿费(元)</th>
				<th type='text' name='entityList[#index#].mealsCost' filedStyle='width: 80px'>餐费(元)</th>
				<th type='text' name='entityList[#index#].miscellaneousCost' filedStyle='width: 80px'>公杂费(元)</th>
				<th type='text' name='entityList[#index#].serviceCost' filedStyle='width: 80px'>劳务费(元)</th>
				<th type='text' name='entityList[#index#].expressCost' filedStyle='width: 80px'>快递费(元)</th>
				<th type='text' name='entityList[#index#].otherCost' filedStyle='width: 80px'>其他费用(元)</th>
				<th type='text' name='entityList[#index#].amount' filedStyle='width: 80px'>小计(元)</th>
				<th type='text' name='entityList[#index#].remark' filedStyle='width: 80px'>备注</th>
                <th type="del" width="28">操作</th>
              </tr>
            </thead>
	        <tbody >
            <c:forEach items="${page.results}" var="entity" varStatus="status">
				<tr >
  					<td><input type="text" size="5" value="${status.count }" name="" style="width:30px;" readonly="readonly"/></td>
			
 					<td>
 						<input type='text' value='${entity.staff }' name='entityList[${status.count-1}].staff' maxlength='50' style='width: 100px;'/>
 					</td>
					<td><input type='text' class="textInput" value='${entity.position }' name='entityList[${status.count-1}].position' maxlength='50' style='width: 100px;'/></td>
					<td><input type='text' class="textInput" value='${entity.trafficCost }' name='entityList[${status.count-1}].trafficCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.hotelCost }' name='entityList[${status.count-1}].hotelCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.mealsCost }' name='entityList[${status.count-1}].mealsCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.miscellaneousCost }' name='entityList[${status.count-1}].miscellaneousCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.serviceCost }' name='entityList[${status.count-1}].serviceCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.expressCost }' name='entityList[${status.count-1}].expressCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.otherCost }' name='entityList[${status.count-1}].otherCost' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.amount }' name='entityList[${status.count-1}].amount' maxlength='20' style='width: 100px;'/></td>
					<td><input  type='text' class="textInput" value='${entity.remark }' name='entityList[${status.count-1}].remark' maxlength='300' style='width: 100px;'/></td>
					<td><a class="btnDel " href="javascript:void(0)">删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
          </table>
        </div>
      </div>
      <div>
      </div>
    
    </div>
    <div class="formBar" style="width:98%">
     <div align="center">
    	<span><label for="total" style="color:#DC143C;">新增人员费用合计：</label><span id="total"><input  type="text" name="total" value="0"/></span>(元)</span>
     </div>
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
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
</body>
</html>