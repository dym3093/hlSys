<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" language="javascript">

$(function(){
	if($("table[class='list'] tbody tr").length>0){
		$("table[class='list'] tbody tr").each(function(){
			var actualTotalAmount = $(this).find("td").eq(9).html();
			var totalAmount = $(this).find("td").eq(8).html();
//			console.log("actualTotalAmount: "+actualTotalAmount+" , totalAmount:"+totalAmount);
			var actualTotalNum = new Number(0);
			var totalNum = new Number(0);
			if(actualTotalAmount!=null&&actualTotalAmount.length>0){
				actualTotalNum = new Number(actualTotalAmount);	
			}
			if(totalAmount!=null&&totalAmount.length>0){
				totalNum = new Number(totalAmount);	
			}
			var balance = actualTotalNum-totalNum;
			if(balance>0){
				$(this).find("td").eq(11).children("span").css("color","#ff0000").text(balance);
			}
			if(balance==0){
				$(this).find("td").eq(11).children("span").css("color","#0000ff").text(balance);
			}
			if(balance<0){
				$(this).find("td").eq(11).children("span").css("color","#00ff00").text(balance);
			}
		});
	}
});

</script>

<div class="tip"><span>查询</span></div>
<div class="pageHeader">
	<form id="pagerFindForm" onsubmit="if(this.action != '${path}/settlementManagement/erpSettlementTaskBX!listSettlementByPropertiesKj.action?status_bx=2')
	{this.action = '${path}/settlementManagement/erpSettlementTaskBX!listSettlementByPropertiesKj.action?status_bx=2' ;} ;return navTabSearch(this);"
	action="${path}/settlementManagement/erpSettlementTaskBX!listSettlementByPropertiesKj.action?status_bx=2" method="post"  rel="pagerForm">
	<div class="searchBar">
		<table class="pageFormContent">
			<tr>
				<td><label>任&nbsp;&nbsp;务&nbsp;&nbsp;号：</label></td>
				<td><input type="text" name="filter_and_taskNo_LIKE_S" value="${filter_and_taskNo_LIKE_S}"/></td>
				<td><label>项目编号：</label></td>
				<td><input type="text" name="filter_and_projectNo_LIKE_S" value="${filter_and_projectNo_LIKE_S}"/></td>
				<td><label>项目对接人：</label></td>
				<td><input type="text" name="filter_and_createUser_LIKE_S" value="${filter_and_createUser_LIKE_S}"/></td>
				<td><label>项目负责人：</label></td>
				<td><input type="text" name="filter_and_proUser_LIKE_S" value="${filter_and_proUser_LIKE_S}"/></td>
			</tr>
			<tr>
				<td><label>创建日期：</label></td>
				<td>
					<input type="text" name="filter_and_createTime_GEST_T" value="${filter_and_createTime_GEST_T}" class="date" readonly="readonly" style="background-color:#eee;"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
				<td><label>至：</label></td>
				<td>
					<input type="text" name="filter_and_createTime_LEET_T" value="${filter_and_createTime_LEET_T}" class="date" readonly="readonly" style="background-color:#eee;"/> 
					<a class="inputDateButton" href="javascript:;">选择</a>
				</td>
         		<td class="block_btn"><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div>
					<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div>
		<fieldset>
			<legend>【<span id="status_text" style="color:#ff0000;">${status_text}</span>】的结算任务金额合计:</legend>
			<table class="num2_lab">
				<tr>
					<td><label>应结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${totalAmountCal }" /><%--<span>${totalAmountCal }</span> --%>(元)</td>
					<td><label>实际结算金额合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" value="${actualTotalAmountCal }"/>(元)</td>
					<td><label>结余合计：</label></td>
					<td class="num2_w"><input type="text" readonly="readonly" style="color:${balanceCal>0?'#ff0000': balanceCal==0?'#0000ff':'#00ff00'}" value="${balanceCal }"/>(元)</td>
				</tr>
				<tr>
				<td colspan="6" style="text-align:right;"><b style="color:#ff0000;">注:【结余合计】=【实际结算金额合计】-【应结算金额合计】</b></td>
				</tr>
			</table>
		</fieldset>
	</div>	
</div>
<div class="pageContent">
		<div class="panelBar">
		<c:if test="${currentUser.accountName!='parkson'}">
		<ul class="toolBar">
			<c:if test="${currentUser.roleNames=='系统管理员'}">
            </c:if>
		</ul>		
		<jsp:include page="/common/pagination.jsp" />
		</c:if>
		
	</div>	
	
	<div>
		<table class="list" width="100%" layoutH="170" id="exportExcelTable"> 
				<thead>
				<tr>
					<th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
					<th  export = "true" columnEnName = "taskNo"  columnChName = "任务号" >任务号</th>
					<th  export=  "true" columnEnName = "taskName" columnChName = "任务名" >任务名</th>
					<th  export = "true" columnEnName = "createTime" columnChName = "创建日期" >创建日期</th>
					<th  export = "true" columnEnName = "createUser" columnChName = "项目对接人" >项目对接人</th>
					<th  export=  "true" columnEnName = proUser columnChName = "项目负责人" >项目负责人</th>	
					<th  export=  "true" columnEnName = companyNum columnChName = "支公司数" >支公司数</th>						
					<th  export = "true" columnEnName = "setMealNum" columnChName = "套餐数" >套餐数</th>
					<th  export = "true" columnEnName = "totalAmount" columnChName = "本次应结金额(元)" >本次应结金额(元)</th>
					<th  export = "true" columnEnName = "actualTotalAmount" columnChName = "实际结算金额(元)" >实际结算金额(元)</th>
					<th  export = "true" columnEnName = "paymentType" columnChName = "收款方式" >收款方式</th>
					<th  export = "true" columnEnName = "actualTotalAmount" columnChName = "结余(元)" >结余(元)</th>
					<th  export = "true" columnEnName = "status" columnChName = "状态" >状态</th>
				</tr>
				
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="entity" varStatus="status">
					<tr target="sid_user" > <!-- rel="${entity.id }" -->
						<td align="center">
							<c:if test="${currentUser.accountName!='parkson'}">
								<input type="checkbox" name="ids" value="${entity.id}">
							</c:if>
							${status.count}
						</td> 
						<td align="center">
							<a style="color:#6495ED" href="#" onclick="viewSettlementBX('${entity.id}')">${entity.taskNo}</a>
						</td>
						<td align="center">${entity.taskName}</td>
						<td align="center">${fn:substring(entity.createTime,0,10)}</td>
						<td align="center">${entity.createUser}</td>
						<td align="center">${entity.proUser}</td>						
						<td align="center">${entity.companyNum}</td>
						<td align="center">${entity.setMealNum}</td>
						<td align="center">${entity.totalAmount}</td>
						<td align="center">${entity.actualTotalAmount}</td>
						<td align="center">
							<c:choose>
								<c:when test="${entity.paymentType=='transferAccounts'}"> 公对公银行转账 </c:when>
								<c:when test="${entity.paymentType=='personalEBank'}"> 个人网银 </c:when>
								<c:when test="${entity.paymentType=='Alipay'}"> 支付宝 </c:when>
								<c:when test="${entity.paymentType=='POS'}"> POS机刷卡 </c:when>
								<c:otherwise>  <span style="color:#ff0000">无</span> </c:otherwise>
							</c:choose>
						</td>
						<td align="center" ><span></span></td>
						<td align="center">
							<c:choose>
								<c:when test="${entity.status=='0'}"> 未收款 </c:when>
								<c:when test="${entity.status=='1'}"> 待收款 </c:when>
								<c:when test="${entity.status=='2'}"> 已收款 </c:when>
								<c:when test="${entity.status=='3'}"> 变更收款 </c:when>
								<c:otherwise>  <span style="color:#ff0000">状态异常</span> </c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div> 



