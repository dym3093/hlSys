<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
JSONArray seCustomer =(JSONArray) request.getAttribute("seCustomer");
%>

<style>
	#settlementPrice {
		display: none;
	}
</style>

<script type="text/javascript" language="javascript">

 	$(function() {
		$("#settlementButton").click(function() {
			$("#settlement").css("display", "none");
			$("#settlementPrice").css("display", "block");
		});
	
		$("#settlementPriceButton").click(function() {
			$("#settlement").css("display", "block");
			$("#settlementPrice").css("display", "none");
		});
	}); 
	
	function toDiaLog(id){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/settlementManagement/erpPrintCompanySettleTask!toModifyPriceEnd.action?id="+id+"&navTabId="+navTabId, "modifyPrice", "修改价格",{width:800,height:600});
	}
	
	function cusDiaLog(id){
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/settlementManagement/erpPrintCompanySettleTask!toModifyCustomerInfo.action?id="+id+"&navTabId="+navTabId, "modifyPrice", "修改信息",{width:800,height:600});
	}
	
	//重新注入（可结算）
	function confirmCustomerInfo(id){
		 
		 $.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id},
			url: "${path}/settlementManagement/erpPrintCompanySettleTask!getSettlementPriceById.action",
			success: function(data){
				$("#tableTbody2").empty();
				var s= eval("("+data+")");
				var td="";
				var seCustomers = s.settlementCustomer
				for(var i=0;i<seCustomers.length;i++){
					var dialogA='';
					if(seCustomers[i].status=='2'){
						dialogA="<div class='panelBar'><ul class='toolBar'><a class='edit' href='javascript:void(0)' onclick='toDiaLog(\""+seCustomers[i].id+"\")'><span>修改</span></a></ul></div>";
					}
					td += "<tr id="+seCustomers[i].id+">"+
					"<td>"+seCustomers[i].code+"</td>"+
					"<td>"+seCustomers[i].name+"</td>"+
					"<td>"+seCustomers[i].sex+"</td>"+
					"<td>"+seCustomers[i].age+"</td>"+
					"<td>"+seCustomers[i].contact+"</td>"+
					"<td>"+seCustomers[i].branch_company+"</td>"+
					/* "<div>"+${erpCustomer.items}+"</div></td>"+ */
					"<td><a href='javascript:alert(\""+seCustomers[i].items+"\");'>查看内容</a></td>"+
					
					"<td>"+seCustomers[i].sample_type+"</td>"+
					"<td>"+seCustomers[i].censorship_doctor+"</td>"+
					"<td>"+seCustomers[i].censorship_company+"</td>"+
					"<td>"+seCustomers[i].submitter+"</td>"+
					"<td>"+seCustomers[i].censorshiptime+"</td>"+
					"<td>"+seCustomers[i].receivetime+"</td>"+
					"<td>"+seCustomers[i].import_status+"</td>"+
					"<td>"+seCustomers[i].statusView+"</td>"+
					"<td>"+seCustomers[i].setmeal_name+"</td>"+	
					"<td>"+seCustomers[i].setmeal_price+"</td>"+
					"<td>"+dialogA+"</td></tr>";
				}
				//将节点插入到下拉列表中
				$("#tableTbody2").append(td);
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});  
	}
	
	
	//根据状态显示不同会员信息
	function updateState(id,status){
		$.ajax({
			type: "post",
			cache :false,
			dateType:"json",
			data:{"id":id,"status":status},
			url: "${path}/settlementManagement/erpPrintCompanySettleTask!showCustomerByStatus.action",
			success: function(data){
				$("#tableTbody1").empty();
				var s= eval("("+data+")");
				var td="";
				var seCustomers = s.settlementCustomer;
				for(var i=0;i<seCustomers.length;i++){
					var dialogA='';
					if(seCustomers[i].status=='1'){
						dialogA="<div class='panelBar'><ul class='toolBar'><a class='edit' href='javascript:void(0)' onclick='cusDiaLog(\""+seCustomers[i].id+"\")'><span>修改信息</span></a></ul></div>";
					}
					td += "<tr id="+seCustomers[i].id+">"+
					"<td>"+seCustomers[i].code+"</td>"+
					"<td>"+seCustomers[i].name+"</td>"+
					"<td>"+seCustomers[i].sex+"</td>"+
					"<td>"+seCustomers[i].age+"</td>"+
					"<td>"+seCustomers[i].contact+"</td>"+
					"<td>"+seCustomers[i].setmeal_name+"</td>"+
					"<td>"+seCustomers[i].branch_company+"</td>"+
					/* "<div>"+${erpCustomer.items}+"</div></td>"+ */
					"<td><a href='javascript:alert(\""+seCustomers[i].items+"\");'>查看内容</a></td>"+
					
					"<td>"+seCustomers[i].sample_type+"</td>"+
					"<td>"+seCustomers[i].censorship_doctor+"</td>"+
					"<td>"+seCustomers[i].censorship_company+"</td>"+
					"<td>"+seCustomers[i].submitter+"</td>"+
					"<td>"+seCustomers[i].censorshiptime+"</td>"+
					"<td>"+seCustomers[i].receivetime+"</td>"+
					"<td>"+seCustomers[i].import_status+"</td>"+
					"<td>"+seCustomers[i].statusView+"</td>"+
					"<td>"+dialogA+"</td></tr>";
				}
				//将节点插入到下拉列表中
				$("#tableTbody1").append(td);
			},
			error :function(){
				alert("服务发生异常，请稍后再试！");
				return;
			}
		});
	}
	
	function confirmSett(settlementId){
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/settlementManagement/erpPrintCompanySettleTask!confirmSettlement.action",
			data: {'id': settlementId},
			success: function(data){
				if(data.result=="success"){
					alertMsg.correct("结算成功！");
					navTab.closeCurrentTab();
					//$.pdialog.closeCurrent();
					return navTabSearch(this);
				}else{
					alertMsg.error("结算失败！");
				}	
			}
		});
	}
	
</script>
<div class="tip"><span>${erpPrintCompanySettleTask.taskNo}任务详情</span></div>
<div class="pageHeader">
	<div class="searchBar" style="background:white">
		<table class="pageFormContent">
			<tr>
				<td>
					<label>创建日期：</label> 
					<span>${erpPrintCompanySettleTask.createTime}</span>
				</td>
				<td>
					<label>打印公司：</label> 
					<span>${erpPrintCompanySettleTask.printCompany}</span>
				</td>
				<td>
					<label>支公司数：</label> 
					<span>${bCompanyNum}</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>套餐数：</label> 
					<span>${comboNum}</span>
				</td>
				<td>
					<label>Excel总数：</label> 
					<span>${excelNum}</span>
				</td>
                <td> 
					<label>读取数量：</label> 
					<span>${readNum}</span>
				</td>
                <td> 
					<label>异常数量：</label> 
					<a style="color:#F00;text-decoration:underline;" href="javascript:updateState('${settlementId}',5)">${exceptionNum}</a>
				</td>
			</tr>
            
            <tr>
				<td> 
					<label>可结算数量：</label> 
					<a style="color:#F00;text-decoration:underline;" href="javascript:updateState('${settlementId}',2)">${settlementNum}</a>
				</td>
				<td>
					<label>信息有误：</label> 
					<a style="color:#F00;text-decoration:underline;" href="javascript:updateState('${settlementId}',1)">${errorNum}</a>
				</td>
                <td>
					<label>信息不存在：</label> 
					<a style="color:#F00;text-decoration:underline;" href="javascript:updateState('${settlementId}',4)">${nullNum}</a>
				</td>
                <td> 
					<label>已经结算：</label> 
					<a style="color:#F00;text-decoration:underline;" href="javascript:updateState('${settlementId}',3)">${okSettlementNum}</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="tip"><span>客户信息结算清单</span></div>
<div class="pageContent">
		<div id="settlement">
		<c:if test="${erpPrintCompanySettleTask.status!='2'}">
			<div class="panelBar" style="height: 40px">
				<div class="formBar" style="width:98%">
				
			     <ul>
			      <li><label>起始日期：</label> <input type="text" /></li>
				<li><label>起始日期：</label> <input type="text" /></li>
				<li><label>测试下拉：</label></li>
				<li><select>
					<option>是</option>
					<option>否</option>
					<option>是</option>
					<option>否</option>
				</select></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div></li>
				</ul>	
				<ul>
			      <li>
			      	<div class="buttonActive">
			            <div class="buttonContent">
			              <button type="submit" id="settlementButton" onclick="confirmCustomerInfo('${settlementId}')">信息确定</button>
			            </div>
			      	</div>
			      
			      </li>
			      </ul>
			    </div>
			</div>
		</c:if>
		<table id="settlementTable1" class="list" width="100%"layoutH="170" >
			<thead>
				<tr>
					<th>条码</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>联系方式</th>
                    <th>套餐</th>
					<th>支公司</th>
					<th>检查项目</th>
					<th>样本类型</th>
					<th>送检医生</th>
					<th>送检单位</th>
					<th>提交者</th>
					<th>送检日期</th>
					<th>接收日期</th>
					<th>导入状态</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="tableTbody1">
				<c:forEach items="${page.results}" var="erpCustomer">
					<tr>
						<td >${erpCustomer.code}</td>
						<td>${erpCustomer.name}</td>
						<td>${erpCustomer.sex}</td>
						<td>${erpCustomer.age}</td>
						<td>${erpCustomer.contact}</td>
						<td>${erpCustomer.setmeal_name}</td>
						<td>${erpCustomer.branch_company}</td>
						<td>
							 <%-- <button type="button" onclick="showItems('${erpCustomer.items}')">查看检查项目</button> --%>
							 <a href='javascript:alert("${erpCustomer.items}");'>查看内容</a>
						</td>
						<td>${erpCustomer.sample_type}</td>
						<td>${erpCustomer.censorship_doctor}</td>
						<td>${erpCustomer.censorship_company}</td>
						<td>${erpCustomer.submitter}</td>
						<td>${erpCustomer.censorship_time}</td>
						<td>${erpCustomer.receive_time}</td>
						<td>${erpCustomer.import_status}</td>
						<td>
							<c:if test="${erpCustomer.status==1}">信息有误</c:if>
							<c:if test="${erpCustomer.status==2}">可结算</c:if>
							<c:if test="${erpCustomer.status==3}">已结算</c:if>
							<c:if test="${erpCustomer.status==4}">信息不存在</c:if>
						</td>
						<td>
							<c:if test="${erpCustomer.status==1}">
								<div class="panelBar">
									<ul class="toolBar">
										<li><a class="edit" href="${path}/settlementManagement/erpPrintCompanySettleTask!toModifyCustomerInfo.action?id=${erpCustomer.id}"  target="dialog" rel="edit"><span>修改信息</span></a></li>
									</ul>
								</div>
							</c:if>
								
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			
	    </div>
	    <%-- <form id="confirmSettlement" action="${path}/settlementManagement/erpPrintCompanySettleTask!confirmSettlement.action?id=${settlementId}" method="post" rel="pagerForm"> --%>
	    <div id="settlementPrice">
	    <div class="panelBar" style="height: 40px">
			<div class="formBar" style="width:98%">
		      <ul>
		      <li>
		      <div class="buttonActive">
		            <div class="buttonContent">
		              <button type="button" id="settlementPriceButton">返回</button>
		            </div>
		      </div>
		      <%-- <c:if test="${settlementNum}!=0"> --%>
	      		<div class="buttonActive">
		            <div class="buttonContent">
		              <!-- <button type="button" onclick="validateCallback('#confirmSettlement', navTabAjaxDone)">确定结算</button> -->
		              <button type="button" onclick="confirmSett('${settlementId}')">确定结算</button>
		            </div>
		        </div>
		     <%--  </c:if> --%>
		      </li>
		      </ul>
		    </div>
		</div>
        
	    <table id="settlementTable2" class="list" width="100%" layoutH="170"> 
			<thead>
				<tr>
					<th  >条码</th>
					<th  >姓名</th>
					<th  >性别</th>
					<th  >年龄</th>
					<th  >联系方式</th>
					<th   >支公司</th>
					<th   >检查项目</th>
					<th   >样本类型</th>
					<th   >送检医生</th>
					<th  >送检单位</th>
					<th  >提交者</th>
					<th  >送检日期</th>
					<th   >接收日期</th>
					<th  >导入状态</th>
					<th   >状态</th>
					<th   >套餐名字</th>
					<th   >套餐价格</th>
					<th  >操作</th>
				</tr>
		</thead>
		<tbody id="tableTbody2">
		</tbody>
		</table>
		</div>
        <!-- </form> -->
	</div> 