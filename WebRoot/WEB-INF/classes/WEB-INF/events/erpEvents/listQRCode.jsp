<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String userRoles = (String) request.getAttribute("userRoles");
	String userId = (String) request.getAttribute("userId");
%>
<style>
#codeQue {
	display: none;
}
</style>

<script type="text/javascript" language="javascript">
//加载时默认选中单选条件和输入框显示
	$(document).ready(function(){
		var hiddenText = $("input[name='QRCodeStatusInput']").val();
		if(hiddenText==''){
			$("select[name='filter_and_QRCodeStatus_EQ_S']").find("option[value='']").attr("selected",true);
		}else if(hiddenText=='0'){
			$("select[name='filter_and_QRCodeStatus_EQ_S']").find("option[value='0']").attr("selected",true);
		}else if(hiddenText=='1'){
			$("select[name='filter_and_QRCodeStatus_EQ_S']").find("option[value='1']").attr("selected",true);
		}else if(hiddenText=='2'){
			$("select[name='filter_and_QRCodeStatus_EQ_S']").find("option[value='2']").attr("selected",true);
		}else if(hiddenText=='3'){
			$("select[name='filter_and_QRCodeStatus_EQ_S']").find("option[value='3']").attr("selected",true);
		}
	});

	$(function() {

		$("#button").click(function() {
			$("#codeQue").css("display", "block");
			$("#manyQue").css("display", "none");
		});

		$("#back").click(function() {
			$("#codeQue").css("display", "none");
			$("#manyQue").css("display", "block");
		});
		
	});
	
	//保存下拉选中的value
	$("select[name='filter_and_QRCodeStatus_EQ_S']").change(function(){
		var select = $("select[name='filter_and_QRCodeStatus_EQ_S'][option:selected]").val();			//获取选中的下拉的值
		$("input[name='QRCodeStatusInput']").val(select);
	});

	//设置失效复选框
	function changeProduct() {
		var id = '';
		var ids='';
		var count = 0;
		var status = '';
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					id = n.value;
					count += count + 1;
					status = $(this).parent().next().text();
					ids = id+","+ids;
				});
		if (count == 0) {
			alert('请选择至少一条信息！');
			return;
		}else {
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			if (confirm("确认设置失效?")) {
				$.ajax({	//初始化页面时的省份
					type: "post",
					cache :false,
					data:{"ids":ids.substring(0, ids.length-1)},
					url: "../events/erpQRCode!invalidQRCode.action",
					success: function(data){
						var data= eval("("+data+")");
						if(data.status=='200'){
							alertMsg.correct(data.message);
							return navTabSearch(this);
						}else if(data.status=='300'){
							alertMsg.error(data.message);
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
	//用于删除复选框
	function deleteProduct() {
		var id='';
		var ids = '';
		var count = 0;
		var status = '';
		$('input:checkbox[name="ids"]:checked', navTab.getCurrentPanel()).each(
				function(i, n) {
					id = n.value;
					count += count + 1;
					status = $(this).parent().next().text();
					ids = id+","+ids;
				});
		if (count == 0) {
			alert('请选择至少一条信息！');
			return;
		}else {
			var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			if (confirm("确认删除?")) {
				$.ajax({	//初始化页面时的省份
					type: "post",
					cache :false,
					data:{"ids":ids.substring(0, ids.length-1)},
					url: "../events/erpQRCode!deleteQRCode.action",
					success: function(data){
						var data= eval("("+data+")");
						if(data.status=='200'){
							alertMsg.correct(data.message);
							return navTabSearch(this);
						}else if(data.status=='300'){
							alertMsg.error(data.message);
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
	
	function oneDelete(id){
		var QRCodeId = id;
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		if (confirm("确认删除?")) {
			$.ajax({	//初始化页面时的省份
				type: "post",
				cache :false,
				data:{"ids":QRCodeId},
				url: "../events/erpQRCode!deleteQRCode.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.status=='200'){
						alertMsg.correct(data.message);
						return navTabSearch(this);
					}else if(data.status=='300'){
						alertMsg.error(data.message);
					}
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
 	}
	
	//单个失效
	function oneChange(id){
		var QRCodeId = id;
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		if (confirm("确认设置失效?")) {
			$.ajax({	//初始化页面时的省份
				type: "post",
				cache :false,
				data:{"ids":QRCodeId},
				url: "../events/erpQRCode!invalidQRCode.action",
				success: function(data){
					var data= eval("("+data+")");
					if(data.status=='200'){
						alertMsg.correct(data.message);
						return navTabSearch(this);
					}else if(data.status=='300'){
						alertMsg.error(data.message);
					}
				},
				error :function(){
					alert("服务发生异常，请稍后再试！");
					return;
				}
			});
		}
	}

	function submitForm() {
		$(".pageForm", navTab.getCurrentPanel()).submit();
	
	}

	function showViewPath(path){
		//var viewPath = path;
		//alert(viewPath);
		var viewPath = $("#viewPath").val();
		if(viewPath!=null){
			window.open(viewPath);
		}
	}

</script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/events/erpQRCode!listQRCode.action'){this.action = '${path}/events/erpQRCode!listQRCode.action' ;} ;return navTabSearch(this);"
			action="${path}/events/erpQRCode!listQRCode.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td style="margin-top:-10px"><label>场次号：</label> <input type="text"
						name="filter_and_eventsNo_LIKE_S"
						value="${filter_and_eventsNo_LIKE_S}" /></td>
					<td><label>支公司：</label> <%-- <input type="text" name="filter_and_branchCompany_LIKE_S" value="${filter_and_branchCompany_LIKE_S}"/> --%>
						<input type="hidden" id="id"
						name="filter_and_banchCompanyId_EQ_S" bringbackname="customer.id"
						value="${filter_and_banchCompanyId_EQ_S}" /> 
						<input type="text"
						id="branchCompany" name="aaaa"
						bringbackname="customer.branchCommanyName" value="${aaaa}"
						readonly="readonly" /> <a class="btnLook"
						href="${ path }/resource/customerRelationShip!findCustomerRelationShip.action"
						lookupGroup="customer" target="dialog" width="800" height="480">查找带回</a>
						<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					</td>
					<td><label>所属公司：</label> <input type="hidden"	id="ownedCompanyId" name="filter_and_ownedCompanyId_EQ_S" bringbackname="dept.ownedCompany"	value="${filter_and_ownedCompanyId_EQ_S}" /> 
					<input type="text"	id="ownedCompany" name="bbbb" bringbackname="dept.customerNameSimple" value="${bbbb}" readonly="readonly" /> 
					<a class="btnLook" href="${ path }/resource/customerRelationShip!lookDept.action" lookupGroup="dept" target="dialog" width="800" height="480">查找带回</a>
						<%-- <img src="${path}/images/clear.png" title="清除公司信息" id="customer" style="padding-top: 6px;"/> --%>
					</td>
				</tr>
				<tr>
					<td><label>起始日期：</label> <input type="text" name="filter_and_expiryDate_GEST_T" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${filter_and_expiryDate_GEST_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td><label>结束日期：</label> <input type="text"
						name="filter_and_expiryDate_LEET_T" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${filter_and_expiryDate_LEET_T}" /><a
						class="inputDateButton" href="javascript:;">选择</a></td>
					<td>
						<label>级别：</label> 
						<%-- <input type="text" name="filter_and_level2_LIKE_S" value="${filter_and_level2_LIKE_S}"/> --%>
						<select id="level" name="filter_and_level_LIKE_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
							<option value="${filter_and_level_LIKE_S}"></option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>省：</label> <%-- <input type="text" name="filter_and_address_LIKE_S" value="${filter_and_address_LIKE_S}"/>  --%>
						<select id="province" name="filter_and_provinceId_EQ_S"
						rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="city"
						refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${filter_and_provinceId_EQ_S}"></option>
					</select></td>
					<td><label>市：</label> <select id="city"
						name="filter_and_cityId_EQ_S" rel="iselect">
							<option value="${filter_and_cityId_EQ_S}"></option>
					</select></td>
					<td>
						<label>二维码状态：</label>
						<input type="hidden" name="QRCodeStatusInput" value="${QRCodeStatusInput}"/>
						<select id="level" name="filter_and_QRCodeStatus_EQ_S" rel="iselect">
							<%-- <option value="${filter_and_QRCodeStatus_EQ_S}"></option> --%>
							<option value="">请选择</option>
							<option value="0">未生成</option>
							<option value="1">处理中</option>
							<option value="2">已生成</option>
							<option value="3">已失效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="margin-top:-10px">
						<label>批次号：</label> 
						<input type="text" name="filter_and_batchNo_LIKE_S" value="${filter_and_batchNo_LIKE_S}" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<!-- <table class="pageFormContent" style="overflow-y:hidden">
			<tr>
				<td></td><td></td>
				<td><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">查找</button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" id="clearText">重置</button>
						</div>
					</div>
					<div class="buttonActive">
					<div class="buttonContent">
						<button id="button" type="button">条形码查找场次</button>
					</div>
				</div>
				</td>
				
			</tr>
		</table> -->
	</form>
	</div>
	<%-- <div class="searchBar" id="codeQue">
		<table class="pageFormContent">
			<tr>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button id="back">返回</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<form id="pagerFindForm"
			onsubmit="if(this.action != '${path}/events/erpQRCode!findQRCodedeByCode.action'){this.action = '${path}/events/erpQRCode!findQRCodedeByCode.action' ;} ;return navTabSearch(this);"
			method="post" rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent">
				<tr>
					<td><label>条形码：</label> <input type="text"
						name="filter_and_code_EQ_S" value="${filter_and_code_EQ_S}" /></td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查找</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="clearText">重置</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form> --%>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				<li><a class="delete" onclick="deleteProduct()" rel="ids"  title="确定要删除吗?" href="javascript:;"><span>删除</span></a></li>
				<li><a class="icon" onclick="changeProduct()" rel="ids" title="失效"><span>失效</span></a></li>
			</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th width="35">序号</th>
				<th export="true" columnEnName="eventsNo" columnChName="场次号">场次号</th>
				<th export="true" columnEnName="eventDate" columnChName="场次日期">场次日期</th>
				<!-- <th  export= "true" columnEnName = "provice" columnChName = "省" >省</th> -->
				<th export="true" columnEnName="provice" columnChName="省"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">省</th>
				<th export="true" columnEnName="city" columnChName="市"
					id2NameBeanId="org.hpin.base.region.dao.RegionDao">市</th>
				<!-- <th  export= "true" columnEnName = "city" columnChName = "市" >市</th> -->
				<th export="true" columnEnName="branchCompanyId"
					id2NameBeanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"
					columnChName="支公司">支公司</th>
				<th export="true" columnEnName="ownedCompanyId"
					id2NameBeanId="org.hpin.base.usermanager.dao.UserDao"
					columnChName="所属公司">所属公司</th>
				<th export="true" columnEnName="comboName" columnChName="套餐">套餐</th>
				<th export="true" columnEnName="level2" columnChName="级别"
					id2NameBeanId="org.hpin.base.dict.dao.SysDictTypeDao">级别</th>
				<th export="true" columnEnName="headcount" columnChName="预计人数">预计人数</th>
				<th export="true" columnEnName="nowHeadcount" columnChName="已录人数">批次号</th>
				<th export="true" columnEnName="pdfcount" columnChName="已出报告数">二维码状态</th>
				<th export="true" columnEnName="nopdfcount" columnChName="未出报告数">关键字</th>
				<th export="true" columnEnName="batchNo" columnChName="批次号">有效期</th>
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpQRCode" varStatus="status">
				<tr target="sid_user" rel="${erpQRCode.id }">
					<td align="center">
						<input type="checkbox" name="ids" value="${erpQRCode.id}">
						${ status.count }
					</td>
					<td align="center">${erpQRCode.eventsNo}</td>
					<td align="center">${erpQRCode.eventsDate}</td>
					<td align="center">${erpQRCode.provinceName }</td>
					<td align="center">${erpQRCode.cityName }</td>
					<td align="center">	<hpin:id2nameDB id="${erpQRCode.banchCompanyId}" beanId="org.hpin.base.customerrelationship.dao.CustomerRelationshipDao"/></td>
					<td align="center">	<hpin:id2nameDB id="${erpQRCode.ownedCompanyId}" beanId="org.hpin.base.usermanager.dao.UserDao" /></td>
					<td align="center">${erpQRCode.combo}</td>
					<td align="center"><hpin:id2nameDB id='${erpQRCode.level}'
							beanId='org.hpin.base.dict.dao.SysDictTypeDao' /></td>
					<td align="center">${erpQRCode.expectNum}</td>
					<td align="center">${erpQRCode.batchNo}</td>
					<c:choose>
						<c:when test="${erpQRCode.QRCodeStatus=='0'}">
							<td align="center">未生成</td>
						</c:when >
						<c:when test="${erpQRCode.QRCodeStatus=='1'}">
							<td align="center">处理中</td>
						</c:when >
						<c:when test="${erpQRCode.QRCodeStatus=='2'}">
							<td align="center">已生成</td>
						</c:when >
						<c:when test="${erpQRCode.QRCodeStatus=='3'}">
							<td align="center" style="color:red">已失效</td>
						</c:when >
					</c:choose>
					<td align="center">${erpQRCode.keyword}</td>
					<td align="center">${erpQRCode.expiryDate}</td>
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">
							<div class="panelBar" style="width:220px;">
								<ul class="toolBar" >
									<c:if test="${erpQRCode.QRCodeStatus=='0'||erpQRCode.QRCodeStatus=='3'}">
										<li><a class="add"
											href="${path}/events/erpQRCode!toCreateQRCode.action?QRCodeId=${erpQRCode.id}"
											target="dialog" rel="add" title="生成"><span>生成</span></a></li>
									</c:if>
									<li><a class="delete" onclick="oneDelete('${erpQRCode.id}')" href="javascript:;" ><span>删除</span></a></li>
									<c:if test="${erpQRCode.QRCodeStatus=='2'}">
										<li><a class="icon" onclick="oneChange('${erpQRCode.id}')" href="javascript:;" ><span>失效</span></a></li>
									</c:if>
									<c:if test="${erpQRCode.QRCodeLocalPath !=null&&erpQRCode.QRCodeStatus!='3'}">
										<input id="viewPath" type="hidden" value="${erpQRCode.QRCodeLocalPath}"/>
										<%-- <li><a class="add" href="javascript:;" onclick="showViewPath('${erpQRCode.QRCodeLocalPath}')"><span>查看</span></a></li> --%>
										<li><a class="add" target="_blank" href="${erpQRCode.QRCodeLocalPath}?r=1472179530843" ><span>查看</span></a></li>
									</c:if>
									
                        		</ul>
							</div>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
