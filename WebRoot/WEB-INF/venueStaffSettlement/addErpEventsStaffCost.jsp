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
	
	$("button[type='button']:contains('新增人员费用')").live('click',function(){
		

		$("#addStaff").find("tr:last").children().find("input[name*='Cost'], input[name*='amount']").val(0);
		
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
		
	 	$("td").delegate("a[href='javascript:void(0)']","click",function(){
			$("td").find("input[name*='Cost']").change();
		});
	 	
	});
	 
	function count(){
		//delEntity();
		$("td input[name*='Cost']").change();
	}
	
});

</script>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
<div class="pageContent">
  <form method="post" action="${path }/venueStaffSettlement/erpEventsStaffCost!saveErpEventsStaffCost.action?id=${obj.id}&eventsNo=${obj.eventsNo}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" novalidate="novalidate" class="pageForm required-validate">
  <input type="hidden" name="navTabId" value="${ navTabId }"/>
  
  <div class="pageFormContent" layoutH="56">
      <div>
       	<input type="hidden" name="obj.createTime" value="${obj.createTime}"/>
	   	<input type="hidden" name="obj.id" value="${obj.id}"/>
        <div class="tip"><span>场次基本信息</span></div>
		
		<p>
          <label>场次号：</label>
          <input name="obj.eventsNo" maxlength="60" type="text" readonly="readonly"
          	beanName="Customer" propertyName="eventsNo" id="eventsNo"   value="${ obj.eventsNo }"/>
        </p>
        <p>
          <label>场次日期：</label>
          <input name="obj.eventDate" maxlength="60" type="text" readonly="readonly"  beanName="obj" propertyName="eventDate"   value="${ obj.eventDate }"/>
        </p>
        <p>
          <label>人数：</label>
          <input name="" maxlength="60" type="text" readonly="readonly"  beanName="obj" propertyName="settNumbers"   value="${ obj.settNumbers }"/>
        </p>
        <p>
          <label>场次批号：</label>
          <input name="obj.batchNo" maxlength="60" type="text" readonly="readonly"  beanName="obj" propertyName="batchNo"   value="${ obj.batchNo }"/>
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
        <p>
		  <label>费用(元)：</label>
		  <input type="text" name="obj.produceCost" readonly="readonly" maxlength="30" value="${obj.produceCost}"/>
        </p>
       
      </div>
      
      <div class="divider"></div>
      
      <div style="overflow:auto;" layoutH="170">
        <div class="tip"><span>人员费用信息</span></div>
        <div >
           <table id="addStaff" class="list nowrap itemDetail" addButton="新增人员费用" width="100%">
            <thead>
              <tr>
                 <th type="num" name="num" defaultVal="#index#" filedStyle="width: 30px">序号</th>
				<th type='text' name='entityList[#index#].staff' filedStyle='width: 100px'>姓名</th>
				<th type='text' name='entityList[#index#].position' filedStyle='width: 100px'>职位</th>
				<th type='text' name='entityList[#index#].trafficCost' filedStyle='width: 80px'>交通费(元)</th>
				<th type='text' name='entityList[#index#].hotelCost' filedStyle='width: 80px'>住宿费(元)</th>
				<th type='text' name='entityList[#index#].mealsCost' filedStyle='width: 80px'>餐费(元)</th>
				<th type='text' name='entityList[#index#].courseCost' filedStyle='width: 80px'>课酬费(元)</th>
				<th type='text' name='entityList[#index#].miscellaneousCost' filedStyle='width: 80px'>公杂费(元)</th>
				<th type='text' name='entityList[#index#].serviceCost' filedStyle='width: 80px'>劳务费(元)</th>
				<th type='text' name='entityList[#index#].materialCost' filedStyle='width: 80px'>物资费(元)</th>
				<th type='text' name='entityList[#index#].inspectionCost' filedStyle='width: 80px'>检测费(元)</th>
				<th type='text' name='entityList[#index#].expressCost' filedStyle='width: 80px'>快递费(元)</th>
				<th type='text' name='entityList[#index#].publishCost' filedStyle='width: 80px'>打印费(元)</th>
				<th type='text' name='entityList[#index#].samplingCost' filedStyle='width: 80px'>采样费(元)</th>
				<th type='text' name='entityList[#index#].otherCost' filedStyle='width: 80px'>其他费用(元)</th>
				<th type='text' name='entityList[#index#].amount' filedStyle='width: 80px'>小计(元)</th>
				<th type='text' name='entityList[#index#].remark' filedStyle='width: 80px'>备注</th>
				
                <th type="del" width="28">操作</th>
              </tr>
            </thead>
	        <tbody id="staffBody">
            <c:forEach items="${page.results }" var="entity" varStatus="status">
				<tr>
  					<td><input type="text" size="5" value="${status.count }" name="" style="width:30px;" readonly="readonly"/></td>
			
 					<td>
 						<input type='text' value='${entity.staff }' name='entityList[${status.count-1}].staff' maxlength='50' style='width: 100px;'/>
 					</td>
 					<td><input type='text' class="textInput" value='${entity.position }' name='entityList[${status.count-1}].position' maxlength='50' style='width: 100px;'/></td>
 					<td><input type='text' class="textInput" value='${entity.trafficCost }' name='entityList[${status.count-1}].trafficCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.hotelCost }' name='entityList[${status.count-1}].hotelCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.mealsCost }' name='entityList[${status.count-1}].mealsCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.courseCost }' name='entityList[${status.count-1}].courseCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.miscellaneousCost }' name='entityList[${status.count-1}].miscellaneousCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.serviceCost }' name='entityList[${status.count-1}].serviceCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.materialCost }' name='entityList[${status.count-1}].materialCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.inspectionCost }' name='entityList[${status.count-1}].inspectionCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.expressCost }' name='entityList[${status.count-1}].expressCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.publishCost }' name='entityList[${status.count-1}].publishCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.samplingCost }' name='entityList[${status.count-1}].samplingCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.otherCost }' name='entityList[${status.count-1}].otherCost' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.amount }' name='entityList[${status.count-1}].amount' maxlength='20' style='width: 80px;'/></td>
					<td><input type='text' class="textInput" value='${entity.remark }' name='entityList[${status.count-1}].remark' maxlength='300' style='width: 80px;'/></td>
					
					<td><a class="btnDel" href="javascript:void(0)">删除</a></td>
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