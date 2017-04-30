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
<div class="tip"><span>库存信息清单</span></div>
	<table class="list" width="100%;" layoutH="70" > 
		<thead>
			<tr>
				<!-- <th nowrap="nowrap">品类</th> -->
				<th nowrap="nowrap">品名</th>
				<th nowrap="nowrap">库存数量</th>
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
				<th nowrap="nowrap">备注</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "type" columnChName = "调拨类型(1调出,0入库)" >调拨类型</th>
				<th nowrap="nowrap">操作</th>--> 
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="${produce.id}">
				<td align="center" nowrap="nowrap">${name}</td>
				<td align="center" nowrap="nowrap">
					<span>${produce.remark}</span>
					<span>
						<input hidden="true" type="text" value="${produce.remark}" />
					</span>
				</td>
				<td align="center" nowrap="nowrap">${produce.basePrice}</td>
				<td align="center" nowrap="nowrap">${produce.totalPrice}</td>
				<c:choose>
				<c:when test="${remark1=='1010701' }">
					<td align="center" nowrap="nowrap">${produce.cardStart}</td>
					<td align="center" nowrap="nowrap">${produce.cardEnd}</td>
				</c:when>
				</c:choose>
				<td align="center" nowrap="nowrap">${produce.projectBelone}</td>
				<td align="center" nowrap="nowrap">${produce.projectCode}</td>
				<td align="center" nowrap="nowrap">${produce.createUserName}</td>
				<td align="center" nowrap="nowrap">
					<span>${produce.note}</span>
					<input  hidden="true" type="text" value="${produce.note}" style="width:100%;" />
				</td>
		 	</tr>
		</tbody>
	</table>
	</div>
</div> 
