<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
	<div class="tip">
		<span>查询</span>
	</div>

	<div class="pageHeader" style="overflow: hidden;">
		<form id="pagerFindForm" onsubmit="this.action='${path}/warehouse/storegeIn!listInfo.action' ; return navTabSearch(this);" 
			action="" 
			method="post"
			rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>
							<label>仓库名称：</label>
							<input type="text" name="params.storegeName" class="textInput" value="${params.storegeName }">
						</td>
						<td>
							<label>开始时间：</label>	
							<input type="text" name="params.startDate"  id="d1" style="readonly="readonly" value="${params.startDate}" onFocus="WdatePicker({minDate:'2015-01-01',maxDate:'#F{$dp.$D(\'d2\')}'});" />
					  		<a class="inputDateButton" href="javascript:void(0);" >选择</a>
						</td>	
						<td>
							<label>结束时间：</label>	
							<input type="text" name="params.endDate" id="d2" style="readonly="readonly" value="${params.endDate}"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'});"/>
							<a class="inputDateButton" href="javascript:void(0);">选择</a>
						</td>
					</tr>
					<tr>
						<td>
							<label>产品名称：</label>
							<input type="text" name="params.productName" class="textInput" value="${params.productName }">
						</td>
						<td>
							<div class="subBar">
								<ul style="float: left">
									<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
									<li><a onclick="resetDo();" href="javascript:;" class="button"><span>重置</span></a></li>
								</ul>
							</div>
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
			
		</div>
		</form>
	</div>

	<div class="pageContent j-resizeGrid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class=""><a class="edit" href="javascript:void(0);" onclick="update();"><span>修改</span></a></li>
				<li class=""><a class="edit" href="javascript:void(0);" onclick="checkOut();"><span>结帐</span></a></li>
				<li class="line">line</li>
				<web:exportExcelTag 
						pagerFormId="pagerFindForm" 
						pagerMethodName="exportExcel" 
						actionAllUrl="org.hpin.warehouse.web.ErpStoregeInAction" 
						informationTableId="exportExcelTable"
						fileName="storegeInData">
				</web:exportExcelTag>
				<li class="line">line</li>
			</ul>
			<jsp:include page="/common/pagination.jsp" />
		</div>
			<table class="list" style="width:100%; overflow: auto" layoutH="108" id="exportExcelTable">
				<thead>
					<tr>
						<th style="width: 45px; ">序号</th>
						<th style="width: 200px; " nowrap="nowrap" export="true" columnEnName="storegeName" columnChName="仓库名称">仓库名称</th>
						<th style="width: 200px; " nowrap="nowrap" export="true" columnEnName="productName" columnChName="产品名称">产品名称</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="price" columnChName="单价">单价</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="quantity" columnChName="数量">数量</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="amount" columnChName="总金额">总金额</th>
						<th style="width: 180px; " nowrap="nowrap" export="true" columnEnName="standard" columnChName="规格">规格</th>
						<th nowrap="nowrap" export="true" columnEnName="describe" columnChName="描述">描述</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="useableQuantity" columnChName="可用数量">可用数量</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="createTime" columnChName="入库日期">入库日期</th>
						<th style="width: 120px; "  nowrap="nowrap" export="true" columnEnName="serialNo" columnChName="流水号">流水号</th>
						<th style="width: 120px; "  nowrap="nowrap" export="true" columnEnName="serialNoOA" columnChName="OA流水号">OA流水号</th>
						<th style="width: 120px; "  nowrap="nowrap" export="true" columnEnName="statusName" columnChName="结算状态">结算状态</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="supplierName" columnChName="供货商">供货商</th>
						<th style="width: 80px; "  nowrap="nowrap" export="true" columnEnName="storegeStatus" columnChName="入库状态">入库状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results }" var="item" varStatus="stat">
						<tr target="sid_user" rel="${item.id }" >
							<td><input type="checkbox" name="ids" class="" value="${item.id }" quantity="${item.quantity }" useableQuantity="${item.useableQuantity }"/>${stat.count }</td>
							<td>${item.storegeName }</td>
							<td>${item.productName }</td>
							<td>${item.price }</td>
							<td>${item.quantity }</td>
							<td>${item.amount }</td>
							<td>${item.standard }</td>
							<td>${item.describe }</td>
							<td>${item.useableQuantity }</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
							<td>${item.serialNo}</td>
							<td>${item.serialNoOA}</td>
							<td style="text-align: center">
								<c:choose>
									<c:when test="${item.settleStatus==0}" >
										未结算
									</c:when>
									<c:when test="${item.settleStatus==1}" >
										已结算
									</c:when>
									<c:otherwise>
										未知状态
									</c:otherwise>
								</c:choose>
							</td>
							<td>${item.supplierName }</td>
							<td><c:if test="${item.storegeStatus==1 }">正常入库</c:if><c:if test="${item.storegeStatus==0 }">退货入库</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
	</div>

<script type="text/javascript">
	
	function resetDo() {
		$("input", navTab.getCurrentPanel()).each(function() {
			$(this).val("");
		});
	}

	/*修改*/
	function update(){
		var count = 0;
		var ids = "";
		var useableQuantity = 0;
		var quantity = 0;
		$("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
			ids = val.value ;
			count ++;
			quantity = $(this).attr("quantity");
			useableQuantity = $(this).attr("useableQuantity");
		});
		
		if(count == 0) {
			alertMsg.warn("请选择你要变更的信息！");
			return;
		} else if (count > 1) {
			alertMsg.warn("只能选择一条信息进行修改！");
			return;
		}
		//当可用数量和数量相等,可进行修改;
		if(quantity != useableQuantity) {
			alertMsg.warn("该数据已有出库,不能进行修改操作!");
			return;
		}
		
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		navTab.openTab("storegeInUpdate", "../warehouse/storegeIn!edit.action?id="+ids+"&navTabId="+navTabId, { title:"入库信息修改", fresh:false, data:{} });
	}

	//结帐
    function checkOut(){
        var count = 0;
        var ids = "";
        var settleStatus = 1; //默认已经结帐
        $("input:checkbox[name='ids']:checked", navTab.getCurrentPanel()).each(function(index, val) {
            ids = val.value ;
            count ++;
            settleStatus= $(this).attr("settleStatus");
        });

        if(count == 0) {
            alertMsg.warn("请选择你要变更的信息！");
            return;
        } else if (count > 1) {
            alertMsg.warn("只能选择一条信息进行修改！");
            return;
        }
        //只能处理未结帐的数据
        if (settleStatus==1) {
            alertMsg.warn("该数据已结帐，不能重复结帐!");
            return;
        }

        var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
        navTab.openTab("storegeInCheckOut", "../warehouse/storegeIn!toCheckOut.action?id="+ids+"&navTabId="+navTabId, { title:"结帐", fresh:false, data:{} });
    }

</script>