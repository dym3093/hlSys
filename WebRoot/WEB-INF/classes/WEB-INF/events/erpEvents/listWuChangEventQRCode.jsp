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

<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<div class="searchBar" id="manyQue">
		<form id="pagerFindForm" 
			onsubmit="if(this.action != '${path}/events/erpQRCode!listWuChangEventQRCode.action'){this.action = '${path}/events/erpQRCode!listWuChangEventQRCode.action' ;} ;return navTabSearch(this);"
			action="${path}/events/erpQRCode!listWuChangEventQRCode.action" method="post"
			rel="pagerForm" id="pagerFindForm">
			<table class="pageFormContent" style="overflow-y:hidden">
				<tr>
					<td style="margin-top:-10px">
						<label>场次号：</label> 
						<input type="text" name="params.eventsNo" value="${params.eventsNo}" /></td>
					<td>
						<label>支公司：</label> <%-- <input type="text" name="filter_and_branchCompany_LIKE_S" value="${filter_and_branchCompany_LIKE_S}"/> --%>
						<input type="text" id="id" name="params.banchCompanyName" value="${params.banchCompanyName}" /> 
					</td>
					<td>
						<label>所属公司：</label> 
						<input type="text"id="ownedCompanyId" name="params.ownedCompanyName" value="${params.ownedCompanyName}" /> 
					</td>
				</tr>
				<tr>
					<td>
						<label>起始日期：</label>
					    <input type="text" name="params.startDate" id="d1" style="width: 170px;"
						onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d2\')}'})"
						readonly="readonly" value="${params.startDate}" />
						<a class="inputDateButton" href="javascript:;">选择</a></td>
					<td>
						<label>结束日期：</label> 
						<input type="text" name="params.endDate" id="d2" style="width: 170px;"
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d1\')}'})"
						readonly="readonly" value="${params.endDate}" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						<label>级别：</label> 
						<select id="level" name="params.level" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10103">
							<option value="${params.level}"></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>省：</label>
						<select id="province" name="params.provinceId"
							rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
							ref="city" refUrl="${path}/system/region!treeRegionDispose.action?parentId=">
							<option value="${params.provinceId}"></option>
						</select>
					</td>
					<td>
						<label>市：</label> 
						<select id="city" name="params.cityId" rel="iselect">
							<option value="${params.cityId}"></option>
						</select>
					</td>
					<td>
						<label>二维码状态：</label>
						<select id="level" name="params.QRCodeStatus" rel="iselect">
							<option value="">请选择</option>
							<option value="0" ${params.QRCodeStatus == '0' ? 'selected': '' }>未生成</option>
							<option value="1" ${params.QRCodeStatus == '1' ? 'selected': '' }>处理中</option>
							<option value="2" ${params.QRCodeStatus == '2' ? 'selected': '' }>已生成</option>
							<option value="3" ${params.QRCodeStatus == '3' ? 'selected': '' }>已失效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="margin-top:-10px">
						<label>批次号：</label> 
						<input type="text" name="params.batchNo" value="${params.batchNo}" />
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
	</form>
	</div>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<c:if test="${currentUser.userType!='查询用户'}">
			<ul class="toolBar">
				<li><a class="delete" id="deleteProduct" rel="ids"  title="确定要删除吗?" href="javascript:;"><span>删除</span></a></li>
				<li><a class="icon" id="loseEfficacy" rel="ids" title="失效"><span>失效</span></a></li>
				<li><a class="edit" id="exportCustomer" rel="ids" title="导出客户信息" onclick="exportCustomer()"><span>导出客户信息</span></a></li>
			</ul>
		</c:if>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="170" id="exportExcelTable">
		<thead>
			<tr>
				<th>全选<input type="checkbox" class="checkboxCtrl" group="ids" /></th>
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
				<th>推送状态</th> <!--  export="true" columnEnName="pushStatus" columnChName="推送状态" -->
				<c:if test="${currentUser.userType!='查询用户'}">
					<th export="false" columnEnName="" columnChName="">操作</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.results}" var="erpQRCode" varStatus="status">
				<tr target="sid_user" rel="${erpQRCode.id }">
					<td align="center" >
						<input type="checkbox" name="ids" value="${erpQRCode.id}">
						${ status.count }
					</td>
					<td align="center"> <a title="场次信息" target="navTab" width="1000"
						href="${path}/events/erpCustomer!toEventWuChuangNoListCustomer.action?id=${erpQRCode.eventsId}&qrcodeId=${erpQRCode.id}">${erpQRCode.eventsNo}</a> </td>
					<td align="center">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${erpQRCode.eventsDate}"/>
					</td>
					<td align="center">${erpQRCode.provinceName }</td>
					<td align="center">${erpQRCode.cityName }</td>
					<td align="center">${erpQRCode.banchCompanyName}</td>
					<td align="center">${erpQRCode.ownedCompanyName}</td>
					<td align="center">${erpQRCode.combo}</td>
					<td align="center">${erpQRCode.levelName}</td>
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
					<td align="center">
						<c:if test="${erpQRCode.pushStatus == '0'}">未推送</c:if>
						<c:if test="${erpQRCode.pushStatus == '1' }"><span style="color: green;">推送成功</span></c:if>
						<c:if test="${erpQRCode.pushStatus == '-1' }"><span style="color: red;">推送失败</span></c:if>
					</td>
					<c:if test="${currentUser.userType!='查询用户'}">
						<td align="center">
							<div class="panelBar" style="width:220px;">
								<ul class="toolBar" >
									<c:if test="${erpQRCode.QRCodeStatus=='0'||erpQRCode.QRCodeStatus=='3'}">
										<li><a class="add"
											href="${path}/events/erpQRCode!toCreateQRCode.action?QRCodeId=${erpQRCode.id}"
											target="dialog" rel="add" title="生成"><span>生成</span></a></li>
									</c:if>
									<li><a class="delete" name="aloneDelete" id="${erpQRCode.id}" href="javascript:;" ><span>删除</span></a></li>
									<c:if test="${erpQRCode.QRCodeStatus=='2'}">
										<li><a class="icon" name="aloneChange" id="${erpQRCode.id}" href="javascript:;" ><span>失效</span></a></li>
									</c:if>
									<c:if test="${erpQRCode.QRCodeLocalPath !=null&&erpQRCode.QRCodeStatus!='3'}">
										<input id="viewPath" type="hidden" value="${erpQRCode.QRCodeLocalPath}"/>
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
<script type="text/javascript" language="javascript">
	$(function() {

		$("#button", navTab.getCurrentPanel()).click(function() {
			$("#codeQue", navTab.getCurrentPanel()).css("display", "block");
			$("#manyQue", navTab.getCurrentPanel()).css("display", "none");
		});

		$("#back", navTab.getCurrentPanel()).click(function() {
			$("#codeQue", navTab.getCurrentPanel()).css("display", "none");
			$("#manyQue", navTab.getCurrentPanel()).css("display", "block");
		});
		
		/*失效按钮单机事件;*/
		$("#loseEfficacy", navTab.getCurrentPanel()).on("click", function() {
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
				alertMsg.warn('请选择至少一条信息！');
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
							alertMsg.error("服务发生异常，请稍后再试！");
							return;
						}
					});
				}
			}
		});
		
		/*删除*/
		$("#deleteProduct", navTab.getCurrentPanel()).on("click", function() {
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
		});
		
		/*单个删除*/
		$("a[name='aloneDelete']", navTab.getCurrentPanel()).on("click", function() {
			var QRCodeId = $(this).attr("id");
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
						alertMsg.error("服务发生异常，请稍后再试！");
						return;
					}
				});
			}
		});
		
		//单个失效
		$("a[name='aloneChange']", navTab.getCurrentPanel()).on("click", function() {
			var QRCodeId =  $(this).attr("id");
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
		});
		
	});

    /**
	 * 根据场次号批量导出客户信息
	 * @author Damian
	 * @since 2017-02-22 10:55
     */
	var exportCustomer = function () {
        var eventsNoArr = [];
        var eventsNo = "";
        $("input[name='ids']:checked", navTab.getCurrentPanel()).each(function(i){
            eventsNo = $(this).parent("td").siblings().eq(0).text().trim();
            eventsNoArr.push(eventsNo);
        });

        if(eventsNoArr.length<1){
            alertMsg.info("请选择要导出客户的场次！");
            return;
        }
        //组合
        var eventsNoStr = eventsNoArr.join();

        alertMsg.confirm('<span style="color:#FF0000;">确认导出【'+eventsNoArr.length+'】个场次的会员数据?</span><br/>', {
            okCall: function(){
                window.open("${path}/events/erpCustomer!exportByEvents.action?eventsNo="+eventsNoStr);
            }
        });
    }

</script>