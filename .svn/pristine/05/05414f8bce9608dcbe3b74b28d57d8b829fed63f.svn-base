<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function changeStyle(me){
	$(me).toggle(function(){
		$(this).removeAttr("readonly");
		//this.toggleClass("style");
	},
	function(){
		$(this).attr("readonly","readonly");
	});
	$(me).toggleClass("style");
}

function showInput(me){
	$(me).siblings("input[type='text']").removeAttr("hidden");
	$(me).siblings("input[type='button']").removeAttr("disabled");
	$(me).parent("span").prev("span").attr("hidden", "true");
	$(me).parents("td").siblings(":last").find("input[type='text']").removeAttr("hidden");
	$(me).parents("td").siblings(":last").find("span").attr("hidden", "true");
}

function updateStoreProduceById(id,me,type){
	var pushNum = $(me).siblings("input[type='text']").val().trim();
	var note = $(me).parents("td").siblings(":last").find("input[type='text']").val().trim();
	
	if(pushNum==""||pushNum==null||pushNum==undefined ){
		alertMsg.info("无效数据!");
		return;
	}
	var regNum =/^\d*$/;
	if(!regNum.test(pushNum)){
		alertMsg.info("【数量】输入框只能输入整数!");
		return;
	}
	if(note==""||note==null||note==undefined){
		alertMsg.info("请填写备注!");
		return;
	}
	$.ajax({
		url: '${path}/warehouse/warehouse!updateStoreProduceById.action',
		method: 'post',
		dataType: 'json',
		cache: false,
		data: {'id':id, 'pushNum':pushNum, 'type':type, 'note':note},
		success: function(data){
			if(data.statusCode==200){
				alertMsg.correct(data.message);
				$.pdialog.reload(data.forwardUrlMe,{});
				navTab.reload(data.forwardUrl,{});
				$(me).siblings("input[type='text']").attr("hidden","true");
				$(me).parent("span").prev("span").removeAttr("hidden");
				$(me).attr("disabled",'true');
				$(me).parents("td").siblings(":last").find("input[type='text']").attr("hidden");
				$(me).parents("td").siblings(":last").find("span").removeAttr("hidden");
			}else{
				alertMsg.error(data.message);
			}
		},
		error: function(data){
			alertMsg.error(data.message);
		}
	});
}

</script>

<div class="pageFormContent" layoutH="40">
<div class="pageContent"> 
<div class="tip"><span>入库信息清单</span></div>
	<table class="list" width="100%;" layoutH="70" > 
		<thead>
			<tr>
				<th>序号</th>
				<!-- <th nowrap="nowrap">品类</th> -->
				<th nowrap="nowrap">品名</th>
				<th nowrap="nowrap">数量</th>
				<th nowrap="nowrap">单价</th>
				<th nowrap="nowrap">总额</th>
				<c:choose>
					<c:when test="${remark1=='1010701' }">
						<th nowrap="nowrap">卡号开始</th>
						<th owrap="nowrap">卡号截止</th>
					</c:when>
				</c:choose>
				<th nowrap="nowrap">项目归属</th>
				<th nowrap="nowrap">项目名称</th>
				<th nowrap="nowrap">操作人员</th>
				<th nowrap="nowrap">入库日期</th>
				<th nowrap="nowrap">备注</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "type" columnChName = "调拨类型(1调出,0入库)" >调拨类型</th>
				<th nowrap="nowrap">操作</th>--> 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${produces }" var="storeProduce" varStatus="status" >
			<tr target="sid_user" rel="${storeProduce.id}">
				<td align="center">${status.count}</td>
				<td align="center" nowrap="nowrap">${name}</td>
				<td align="center" nowrap="nowrap">
					<span>${storeProduce.remark}</span>
					<span>
						<input hidden="true" type="text" value="${storeProduce.remark}" />
						<input type="button" value="更改数量" style="margin-left:10px;margin-right:5px;" onclick="showInput(this);" />
						<input type="button" value="保存" disabled="disabled" onclick="updateStoreProduceById('${storeProduce.id}', this, '0')"/>
					</span>
				</td>
				<td align="center" nowrap="nowrap">${storeProduce.basePrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.totalPrice}</td>
				<c:choose>
				<c:when test="${remark1=='1010701' }">
					<td align="center" nowrap="nowrap">${storeProduce.cardStart}</td>
					<td align="center" nowrap="nowrap">${storeProduce.cardEnd}</td>
				</c:when>
				</c:choose>
				<td align="center" nowrap="nowrap">${storeProduce.projectBelone}</td>
				<td align="center" nowrap="nowrap">${storeProduce.projectCode}</td>
				<td align="center" nowrap="nowrap">${storeProduce.createUserName}</td>
				<td align="center" nowrap="nowrap">${fn:substring(storeProduce.createTime,0,10)}</td>
				<td align="center" nowrap="nowrap">
					<span>${storeProduce.note}</span>
					<input  hidden="true" type="text" value="${storeProduce.note}" style="width:100%;" />
				</td>
				<%-- <c:choose>
					<c:when test="${storeProduce.type==1}">
						<td align="center" nowrap="nowrap">调出</td>
					</c:when>
					<c:when test="${storeProduce.type==0}">
						<td align="center" nowrap="nowrap">入库</td>
					</c:when>
					<c:otherwise><td align="center" nowrap="nowrap"></td></c:otherwise>
				</c:choose> 
				<td align="center" nowrap="nowrap"><a class="add" href="${path }/warehouse/warehouse!browProduceDetail.action?id=${storeProduce.id }" target="navTab"><span>详情</span></a></td>
				
				<td align="center" nowrap="nowrap"><a class="edit" href="${path }/warehouse/warehouse!modifyPushProduce.action?id=${storeProduce.id }" target="dialog"><span>修改</span></a></td>--%>
		 	</tr>
		 </c:forEach>
		</tbody>
	</table>
	</div>
</div> 
