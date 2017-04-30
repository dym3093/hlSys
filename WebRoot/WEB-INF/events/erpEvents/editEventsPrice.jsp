<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script src="${path}/jquery/json2.js" type="text/javascript"></script>  
  
<script type="text/javascript">

//关闭弹窗
function isClose(){
	$.pdialog.closeCurrent();
	return navTabSearch(this);	//刷新页面
}

//保存场次套餐价格
function saveAllEvntsPrice(me){
	
	var arr = [];
	
	if(me=="all"){
		$("#priceTable tbody tr ").each(function(i,n){
			var td1 = $(this).children().eq(0);
			var combo = td1[0].innerText.split("：")[0];
			var td2 = $(this).children().eq(1);
			var comboPrice = td2.children("#comboPrice").val();	
	
			//		console.log(combo+","+comboPrice+","+td2.children("#id").val()+","+td2.children("#comboId").val()+","+td2.children("#eventsNo").val());
		
			if(comboPrice!=null&&comboPrice!=""&&comboPrice!='undefided'&&comboPrice!=undefined){
				arr.push({
						'id': td2.children("#id").val(),						
						'combo': combo,
						'comboId': td2.children("#comboId").val(),
						'eventsNo': td2.children("#eventsNo").val(),
						'comboPrice': comboPrice						
					});
			}
		});
	}else{
	  	var tr = $(me).parents("tr");
		var combo = tr.children()[0].innerText.split("：")[0];
		var td2 =tr.children().eq(1);
		var comboPrice = td2.children("#comboPrice").val();
		
		if(comboPrice!=null&&comboPrice!=""&&comboPrice!='undefided'&&comboPrice!=undefined){
			arr.push({
					'id': td2.children("#id").val(),						
					'combo': combo,
					'comboId': td2.children("#comboId").val(),
					'eventsNo': td2.children("#eventsNo").val(),
					'comboPrice': comboPrice						
				});
		}
	}
			
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/erpEvents!saveEvntsComboPrice.action",
		data: {"data": JSON.stringify(arr),"opera":" part"},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("保存成功！");
				if(me=="all"){
					$.pdialog.closeCurrent();
					return navTabSearch(this);	//刷新页面
				}else{
					
				}
			}else{
				alertMsg.error("保存失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}

function saveEvntsPrice(me){
	
	var arr = [];
    var tr = $(me).parents("tr");
    var combo = tr.children()[0].innerText.split("：")[0];
    var td2 =tr.children().eq(1);
    var comboPrice = td2.children("#comboPrice").val();
    console.log(combo+", "+comboPrice);
    alert(combo+", "+comboPrice);

	
	return;
	
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/events/erpEvents!saveEvntsComboPrice.action",
		data: {"data": JSON.stringify(arr)},
		success: function(data){
			if(data.result=="success"){
				alertMsg.correct("保存成功！");
				$.pdialog.closeCurrent();
				return navTabSearch(this);	//刷新页面
			}else{
				alertMsg.error("保存失败！");
			}	
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}

//验证
function checkValid(me, validName){
	var flag = false;
	switch(validName){
		case 'number':
			var price = me.value;
			if(price!=null&&price!=""&&price!="undefined"&&price!=undefined){
				var regNum = /[0-9]/;
				if(regNum.test(price)){
					flag = true;
				}else{
					alert("请输入数字！");
				}
			}
		break;
	}
	return flag;
}

</script>

<style>
table td{
word-break: keep-all;
white-space:nowrap;
}
table th{
word-break: keep-all;
white-space:nowrap;
}
</style>
<div class="pageHeader" style="background:white;height:98%;" align="center">
	<form style="width:100%;height:95%">
		<input type="hidden" id="eventsId" name="eventsId" value="${eventsId}"/>
		<input type="hidden" id="navTabId" name="navTabId" value="${navTabId}"/>
		<table id="priceTable" class="list" style="width:100%;">
			<thead>
				<tr>
					<th export="true" columnEnName="combo" columnChName="套餐名称">套餐名称</th>
					<th export="true" columnEnName="comboPrice" columnChName="套餐价格">套餐价格</th>
					<th export="true" columnEnName="opera" columnChName="操作">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${ empty page.results}">
						<tr align="center"><td colspan="3"><span style="color:#ff0000;font-size:25px;">未查到该场次价格信息</span></td></tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${page.results}" var="entity" varStatus="status">
							<tr>
								<td align="right" style="width:30%;">
									<label>${entity.combo}：</label> 
								</td>
								<td align="left" style="width:40%;">
									<input type="hidden" id="id" name="id" value="${entity.id}"/>
									<input type="hidden" id="comboId" name="comboId" value="${entity.comboId}"/>
									<input type="hidden" id="eventsNo" name="eventsNo" value="${entity.eventsNo}"/>
									<input type="text" id="comboPrice" name="comboPrice" style="width:180px;" onblur="checkValid(this,'number');" alt="请输入数字" value="${entity.comboPrice}"/>
									<span style="margin:15px 8px;background-color:#ebf0f5;font-size:12px;">(元)</span>
								</td>
								
								<td align="right" style="width:30%;" >
									<div class="panelBar">
										<ul class="toolBar">
											<li align="right"><a class="edit" style="cursor:pointer;"	href="#" onclick="saveAllEvntsPrice(this)"><span>修改</span></a></li>
										</ul>							
				                    </div>				
								</td>
								
							</tr>		
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div align="center" class="panelBar">
			<ul class="toolBar">
				<li><a class="edit" style="cursor:pointer;"	href="#" onclick="saveAllEvntsPrice('all')"><span>全部修改</span></a></li>
				<li><a class="delete" style="cursor:pointer;"	href="#" onclick="isClose()"><span>取消</span></a></li>
			</ul>							
		</div>	
	</form>
</div>
