<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");

String id = (String)request.getAttribute("id");
%>

<style>
span.error {
	overflow: hidden;
	width: 165px;
	height: 20px;
	padding: 0 3px;
	line-height: 20px;
	background: #F00;
	color: #FFF;
	top: 5px;
	left: 318px;
	margin-top: 5px;
}

</style>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/jquery/json2.js" type="text/javascript"></script>
<!-- 验证提示需要; -->
<script src="${path}/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

	<form id="pagerFindForm"
	method="post" 
	class="pageForm required-validate"
	action="${path}/settlementManagement/erpSettlementTaskBX!saveProceeds.action" 
	onsubmit="return validateCallback(this, navTabAjaxDone);"
	rel="pagerFindForm">
		<input type="hidden" id="navTabId" name="navTabId" value="${navTabId }"/>
		<input type="hidden" id="id" name="id" value="${proceeds.id }" />
		<input type="hidden" id="settlementId" name="settlementId" value="${taskBX.id }"/>
		
		<div class="pageContent" style="width: 100%; overflow:hidden;">
			
			<div class="pageFormContent" layouth="97" style="overflow:auto;">
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">项目负责人：</dt>
					<dd>
						<input type="text" id="proLeader" name="proLeader" value="${taskBX.salesManYM }" readonly="readonly"/>
					</dd>
				</dl>
				
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">所属公司：</dt>
					<dd>
						<input type="text" id="ownedCompanyName" name="ownedCompanyName" value="${taskBX.ownedCompany }" readonly="readonly"/>
						<input type="hidden" id="ownedCompany" name="ownedCompany" value="${taskBX.ownedCompanyId }"/>
					</dd>
				</dl>
				
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">支公司：</dt>
					<dd>
						<input type="text" id="branchName" name="branchName" value="${taskBX.branchCompany }" readonly="readonly"/>
						<input type="hidden" id="branchId" name="branchId" value="${taskBX.branchCompanyId }"/>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">实际结算金额：</dt>
					<dd>
						<input type="text" id="actulSettAmount" name="actulSettAmount" value="${taskBX.actualTotalAmount }" readonly="readonly"/>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">收款方式：</dt>
					<dd>
						<input type="text" id="procMethod" name="procMethod" 
						value="<c:if test="${taskBX.paymentType eq 'transferAccounts'}">公对公银行转账</c:if><c:if test="${taskBX.paymentType eq 'personalEBank' }">个人网银</c:if><c:if test="${taskBX.paymentType eq 'Alipay'  }">支付宝</c:if><c:if test="${taskBX.paymentType eq 'POS' }">POS机刷卡</c:if>" readonly="readonly"/>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">收款时间：</dt>
					<dd>
						<input type="text" name="procTime" dateFmt="yyyy-MM-dd" value='<fmt:formatDate value="${proceeds.procTime}" pattern="yyyy-MM-dd"/>'  class="date" readonly="readonly"/>
						<a class="inputDateButton" href="javascript:;">选择</a>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">收款银行：</dt>
					<dd>
					<!--  -->
						<select svalue="${proceeds.procBankId }" class="required combox" id="procBankId" name="procBankId" style="width:195px; margin-top:7px; margin-left:5px;">
							<option value="">请选择</option>
							<c:forEach items="${banks }" var="bank" varStatus="st">
								<option value="${bank.id }" ${bank.id==proceeds.procBankId ? "selected":""}>${bank.bankName }</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">财务费用：</dt>
					<dd>
						<input type="text" id="financialCost" name="financialCost" value="${proceeds.financialCost!=null ? proceeds.financialCost : '0.00' }" class="required number textInput valid" maxlength="20" onkeyup="value=value.replace(/[^\d.]/g,'')"  />
					</dd>
				</dl>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">入账金额：</dt>
					<dd>
						<input type="text" id="netAmount" name="netAmount" value="${proceeds.netAmount!=null ? proceeds.netAmount : '0.00' }" readonly="readonly" />
					</dd>
				</dl>
				<c:if test="${proceeds.id == null }">
				<dl class="nowrap" name="dealAdd">
					<dt style="background-color:#FFFFFF;">银行流水号：</dt>
					<dd>
						<input type="text" id="bankNum" name="bankNum" value="" />
						<a class="btnAdd" href="javascript:void(0);" style="margin-top: 5px;"/>
					</dd>
				</dl>
				</c:if>
				
				<c:if test="${proceeds.id != null }">
				<c:forEach items="${proceeds.bankNum }" var="str" varStatus="stat">
					<dl class="nowrap" name="dealAdd">
						<dt style="background-color:#FFFFFF;">${stat.count == 1 ? "银行流水号：":"" }</dt>
						<dd>
							<input type="text" id="bankNum" name="bankNum" value="${str }" />
							<c:if test="${stat.count == 1 }">
								<a class="btnAdd" href="javascript:void(0);" style="margin-top: 5px;"/>
							</c:if>
							<c:if test="${stat.count > 1 }">
								<a class="btnDel" href="javascript:void(0);" style="margin-top: 5px;"/>
							</c:if>
						</dd>
					</dl>
				</c:forEach>
				</c:if>
				<dl class="nowrap">
					<dt style="background-color:#FFFFFF;">入账说明：</dt>
					<dd>
						<input type="text" id="postExplain" name="postExplain" value="${proceeds.postExplain }" />
					</dd>
				</dl>
			</div>
			
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">确认收款</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">返回</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			
		</div>
	</form>
	

<script type="text/javascript">
	
	$(function() {
		//失去事件触发算法
		financialCostBlur();
		//添加按钮事件
		btnAdd();
		//删除按钮事件
		btnDel();
	})
	
	/**
	 * 添加行事件注册, 删除该流水号;
	 * 
	 */
	function btnDel() {
		$(".btnDel").live("click", function(){
			$(this).parent().parent().remove();
		});
	}
	
	/**
	 * 添加行事件注册, 最多可以存在10行(需求限定);
	 * 
	 */
	function btnAdd() {
		$(".btnAdd").on("click", function() {
			//判断,当该行有10行的时候提示不在增加;
			var num = 0;
			$("dl[name='dealAdd']").each(function(index) {
				num ++;
			});
			if(num >=10) {
				alertMsg.warn('银行流水号只能添加十个！');
				return ;
			}
			
			var dlStr = "<dl class='nowrap' name='dealAdd'>" +
				"<dt></dt>" +
				"<dd>" +
				"<input type='text' class='textInput' id='bankNum' name='bankNum' value='' />" +
				"<a class='btnDel' href='javascript:void(0);' style='margin-top: 5px;'  /> " +
				"</dd>" +
			"</dl>";
			
			$("dl[name='dealAdd']").last().after(dlStr);
		});
		
	}
	
	/**
	 * 财务费用改变触发事件;
	 */
	function financialCostBlur() {
		$("#financialCost").on("blur",function() {
			var cost = $("#financialCost").val(); //财务费用值;
			if(!cost) {
				cost = 0.00;
			}
			var tocost = parseFloat(cost).toFixed(2);
			//当输入的数字满足,进行计算; 计算规则: 入账金额 = 实际结算金额 - 财务费用
			if(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(cost)) {
				var acAmount = $("#actulSettAmount").val();
				if(!acAmount) {
					acAmount = 0.0;
				}
				
				var postAmount = parseFloat(acAmount)-parseFloat(tocost);
				$("#netAmount").val(postAmount.toFixed(2));
			}
			$("#financialCost").val(tocost);
	        
		});
	}
</script>