<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	
function removeCostDetail(obj){//删除行
	
	if(confirm("确定要删除该用户的费用信息？")){
		var costDetailId = $(obj).parents("tr").children().eq(1).children().val();
		var belong = $("#belong",$.pdialog.getCurrent()).val();
		var costId = $("#costId",$.pdialog.getCurrent()).val();
		var nonConferenceId= $("#nonConferenceId",$.pdialog.getCurrent()).val();
		var pageStatus = getPageStatus();
		if(costDetailId==0){
			$(obj).parents("tr").remove();
		}else{
			$.ajax({	
				type: "post",
				cache :false,
				dateType:"json",
				data:{
					"costDetailId":costDetailId,
					"belong":belong,
					"costId":costId,
					"nonConferenceId":nonConferenceId,
					"pageStatus": pageStatus,
					},
				url: "${path}/venueStaffSett/nonConference!removeCostDetail.action",
				success: function(data){
					var str = eval("("+data+")");
					if(str.count==1){
						$(obj).parents("tr").remove();
						$("#amount",$.pdialog.getCurrent()).val(str.amount);
						alertMsg.correct("删除成功");
						navTab.reloadFlag("menu_2c90543a59c42af40159c44167e50002");
						if (0 == pageStatus) {
							navTab.reload("${path}/venueStaffSett/nonConference!addNonConference.action?nonConferenceId="+str.nonConferenceId,{navTabId:"addNonConference"});
							
						} else {
							navTab.reload("${path}/venueStaffSett/nonConference!updateNonConference.action?nonConferenceId="+str.nonConferenceId,{navTabId:"addNonConference"});
						}
						
					}else{
						alertMsg.error("删除失败");
					}
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	}
}

function addDetailRow(){
		var length = $("#addDetailCost tr",$.pdialog.getCurrent()).length;
		var row = "<tr >"+
			"<td align='center'><input type='text' class='textInput' size='5' value='"+(length+1)+"' name='' style='width:40px;' readonly='readonly'/></td>"+
			"<td align='center' hidden='hidden'><input type='text' class='textInput' size='5' value='0'  name='costDetailList.id' style='width:100px;' readonly='readonly'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${cost.days}' name='costDetailList.dyas' maxlength='50' style='width: 40px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.flight}' name='costDetailList.flight' maxlength='100' style='width: 120px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.descripe}' name='costDetailList.descripe' maxlength='120' style='width: 120px;'/></td>"+
			"<td align='center'><input type='text' class='textInput' value='${entity.cost }' name='costDetailList.cost' maxlength='20' style='width: 40px;'/></td>"+
			"<td align='center'><a class='btnDel' href='javascript:void(0)' onclick='removeCostDetail(this)'>删除</a></td>"+
			"</tr>";
		$("#addDetailCost",$.pdialog.getCurrent()).append(row);
}

function saveConferenceCostDetail(){
	if($("#addDetailCost tr",$.pdialog.getCurrent()).length==0){
		alertMsg.error("没有费用需要提交");
		return;
	}
	var costDetailId = "";
	var days = "";
	var flight = "";
	var descripe = "";
	var cost = 0;
	var count = 0;
	var dataJson = "[";
	$("#addDetailCost tr",$.pdialog.getCurrent()).each(function(index,items){
		costDetailId = $(items).children().eq(1).children().val();			//id
		days = $.trim($(items).children().eq(2).children().val());			//天数
		flight = $.trim($(items).children().eq(3).children().val());	//航班
		descripe = $.trim($(items).children().eq(4).children().val());//描述
		cost = $.trim($(items).children().eq(5).children().val());	//金额
		if(isNaN(cost) || cost==""){
			count++;
		}
		dataJson += "{'costDetailId':'"+costDetailId+"',"+"'days':'"+days+"',"+"'flight':'"+flight+"',"+"'descripe':'"+descripe+"',"+"'cost':"+cost+"},";
	});
	if (dataJson.lastIndexOf(",")) {  
		dataJson = dataJson.substring(0,dataJson.length -1);  
		dataJson +="]";
	}  
	if(count!=0){
		alertMsg.warn("请输入正确的金额");
		return;
	}
	var belong = $("#belong",$.pdialog.getCurrent()).val();
	var travelCost = $("#travelCost",$.pdialog.getCurrent()).val();
	var costId= $("#costId",$.pdialog.getCurrent()).val();
	var name= $("#name",$.pdialog.getCurrent()).val();
	var projectInfo= $("#projectInfo",$.pdialog.getCurrent()).text();
	var nonConferenceId= $("#nonConferenceId",$.pdialog.getCurrent()).val();
	var amount= $("#amount",$.pdialog.getCurrent()).val();
	var clickCost= $("#clickCost",$.pdialog.getCurrent()).val();
	var pageStatus = getPageStatus();
	var title = "添加";
	if (1 == pageStatus) {
		title = "修改";
	}
//	var costInfo= $("#costInfo",$.pdialog.getCurrent()).val();
	$.ajax({	
		type: "post",
		cache :false,
		dateType:"json",
		data:
			{"dataJson":dataJson,"belong":belong,"travelCost":travelCost,"costId":costId, "pageStatus": pageStatus,
				"name":name,"projectInfo":projectInfo,"nonConferenceId":nonConferenceId,"amount":amount,"cost":clickCost	
			},
		url: "${path}/venueStaffSett/nonConference!saveConferenceCostDetailInfo.action",
		success: function(data){
			var str = eval("("+data+")");
			if(str.count==1){
				alertMsg.correct("提交成功");
				navTab.closeCurrentTab();
				
				if (0 == pageStatus) {
					navTab.openTab("addNonConference", "${path}/venueStaffSett/nonConference!addNonConference.action", 
							{ title: title, fresh:true, data:{"nonConferenceId": str.nonConferenceId}});
					
				} else {
					navTab.openTab("addNonConference", "${path}/venueStaffSett/nonConference!updateNonConference.action", 
							{ title: title, fresh:true, data:{"nonConferenceId": str.nonConferenceId}});
					
				}
				$.pdialog.closeCurrent();
				
			}else{
				alertMsg.error("提交失败");
			}
		},
		error :function(){
			alert("服务发生异常，请稍后再试！");
			return;
		}
	});
}

function closeDialog(){
	$.pdialog.closeCurrent();
}
function getPageStatus() {
	var pageStatus = $("#pageStatus", $.pdialog.getCurrent()).text();
	return pageStatus; 
}
</script>
</head>
<body>
	<div class="pageContent">
		<div  style="height: 410px; padding: 0px;">
	    	<div hidden="hidden"> <!-- class="divider" -->
	    		<input id="belong" type="text" name="belong" value="${belong}"/>
	    		<input id="travelCost" type="text" name="travelCost" value="${travelCost}"/>
	    		<input id="costId" type="text" name="costId" value="${costId}"/>
	    		<input id="name" type="text" name="name" value="${name}"/>
	    		<input id="nonConferenceId" type="text" name="nonConferenceId" value="${nonConferenceId}"/>
	    		<input id="amount" type="text" name="amount" value="${amount}"/>
	    		<input id="clickCost" type="text" name="cost" value="${clickCost}"/>
	    		<div id="pageStatus" hidden="hidden">${pageStatus}</div>
	    		<%-- <input id="costInfo" type="text" name="costInfo" value="${costInfo}"/> --%>
	    		<span id="projectInfo">${projectInfo}</span>
	    	</div>
	      	<div>
	        	<div class="tip"><span>明细信息</span></div>
	        	<button id="addDetailInfo" type="button" onclick="addDetailRow();" >新增人员费用</button>
	        	<span style="color:red;">如果进行删除操作可以不用点击提交即可更新</span>
	        	<div style="overflow:scroll;height: 360px;">
	           		<table class="list" width="100%">
	            		<thead>
	              			<tr>
	                 			<th type="num" name="num" defaultVal="#index#" filedStyle="width: 30px"width="40">序号</th>
								<th type='text' name='costDetailList.id' filedStyle='width: 100px' hidden="hidden">id</th>
								<th type='text' name='costDetailList.days' filedStyle='width: 100px'width="40">天数</th>
								<th type='text' name='costDetailList.flight' filedStyle='width: 80px' width="120">航班/车次(其他类别可不填写)</th>
								<th type='text' name='costDetailList.descripe' filedStyle='width: 80px'width="120">描述</th>
								<th type='text' name='costDetailList.cost' filedStyle='width: 80px'width="40">金额</th>
				                <th type="del" width="28">操作</th>
	              			</tr>
	            		</thead>
	            		
		        		<tbody id="addDetailCost">
	            			<c:forEach items="${costDetail}" var="cost" varStatus="status">
								<tr >
	  								<td align="center">
	  									<input type="text" size="5" value="${status.count}" name="" style="width:40px;" readonly="readonly"/>
	  								</td>
	  								<td align="center" hidden="hidden">
	  									<input type="text" class="textInput" size="5" value="${cost.id}"  name='costDetailList.id' style="width:100px;" readonly="readonly"/>
	  								</td>
				 					<td align="center">
				 						<input type='text' class="textInput" value='${cost.days}' name='costDetailList.dyas' maxlength='50' style='width: 40px;'/>
				 					</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.flight}' name='costDetailList.flight' maxlength='50' style='width: 120px;'/>
									</td>
									<td align="center">
										<input type='text' class="textInput" value='${cost.descripe}' name='costDetailList.descripe' maxlength='20' style='width: 120px;'/>
									</td>
									<td align="center" >
										<input type='text' class="textInput" value='${cost.cost }' name='costDetailList.cost' maxlength='20' style='width: 40px;'/>
									</td>
									<td align="center" hidden="hidden">${cost.cost }</td>
									<td align="center">
										<a class="btnDel" onclick="removeCostDetail(this)" href="javascript:void(0)">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
	          		</table>
	        	</div>
	      	</div>
		</div>
	    <div class="formBar" style="width:98%">
	   		<ul>
	        	<li>
	          		<div class="button"><div class="buttonContent"><button type="button" onclick="saveConferenceCostDetail()">提交</button></div></div>
	        	</li>
	        	<li>
	          		<div class="button"><div class="buttonContent"><button type="button" onclick="closeDialog()">返回</button></div></div>
	        	</li>
	      	</ul>
	    </div>
	</div>  



</body>
</html>