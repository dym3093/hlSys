<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageFormContent" layoutH="40">
<div class="pageContent"> 
<div class="tip"><span>${name}调拨信息清单</span></div>
	<table class="list" width="98%" layoutH="70" > 
		<thead>
			<tr>
				<th nowrap="nowrap">序号</th>
				<!-- <th nowrap="nowrap">名称</th> -->
				<th nowrap="nowrap">批次号</th>
				<th nowrap="nowrap">公司名称</th>
				<th nowrap="nowrap">远盟业务员</th>
				<th nowrap="nowrap">数量</th>
				<c:choose>
					<c:when test="${remark1=='1010701' or remark1=='1010706' }">
						<th nowrap="nowrap">卡ID</th>
						<th nowrap="nowrap">卡号开始</th>
					    <th nowrap="nowrap">卡号截至</th>
			   		</c:when>
				</c:choose>
				<th nowrap="nowrap">快递单号</th> 
				<th nowrap="nowrap">快递名称</th>
				<th nowrap="nowrap">收件人姓名</th>
				<th nowrap="nowrap">收件人电话</th>
				<th nowrap="nowrap">快递费</th>
				<th nowrap="nowrap">成本</th>
				<th nowrap="nowrap">总额</th>
				<th nowrap="nowrap">派发地址</th>
				<th nowrap="nowrap">需求说明</th>
				<th nowrap="nowrap">使用时间</th>
				<!-- <th nowrap="nowrap" export = "true" columnEnName = "type" columnChName = "调拨类型(1调出,0入库)" >调拨类型</th>
				<th nowrap="nowrap">操作</th>--> 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${produces }" var="storeProduce" varStatus="status" >
			<tr target="sid_user" rel="${storeProduce.id}">
				<td align="center">${status.count}</td>
				<td align="center" nowrap="nowrap">${storeProduce.remark3}</td>
				<td align="center" nowrap="nowrap">${storeProduce.bannyCompannyName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.businessName}</td>
				<%-- <td align="center" nowrap="nowrap">${storeProduce.eventNos}</td> --%>
				<td align="center" nowrap="nowrap">${storeProduce.remark}</td>
				<c:choose>
					<c:when test="${remark1=='1010701' or remark1=='1010706'}">
					<td align="center" nowrap="nowrap">${storeProduce.eventNos}</td>
					<td align="center" nowrap="nowrap">${storeProduce.cardStart}</td>
					<td align="center" nowrap="nowrap">${storeProduce.cardEnd}</td>
					</c:when>
				</c:choose>
				<td align="center" nowrap="nowrap"><a title="快递信息" target="dialog"  href="${path}/events/express!listExpress.action?postid=${storeProduce.emsNo }">${storeProduce.emsNo }</td>
				<td align="center" nowrap="nowrap">${storeProduce.emsName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.receiveName}</td>
				<td align="center" nowrap="nowrap">${storeProduce.receiveTel}</td>
				<td align="center" nowrap="nowrap">${storeProduce.emsPrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.basePrice}</td>
				<td align="center" nowrap="nowrap">${storeProduce.totalPrice}</td>
				<%-- <td align="center" nowrap="nowrap"><hpin:id2nameDB  beanId="org.hpin.base.dict.dao.SysDictTypeDao" id="${stores.typeBigCode}"/></td> --%>
				<td align="center" nowrap="nowrap">${storeProduce.address}</td>
				<td align="center" nowrap="nowrap">${storeProduce.requireDetail}</td>
				<td align="center" nowrap="nowrap">${fn:substring(storeProduce.useTime,0,10)}</td>
				<%-- <c:choose>
					<c:when test="${storeProduce.type==1}">
						<td align="center" nowrap="nowrap">调出</td>
					</c:when>
					<c:when test="${storeProduce.type==0}">
						<td align="center" nowrap="nowrap">入库</td>
					</c:when>
					<c:otherwise><td align="center" nowrap="nowrap"></td></c:otherwise>
				</c:choose>
				<td align="center" nowrap="nowrap"><a class="edit" href="${path }/warehouse/warehouse!modifyPullProduce.action?id=${storeProduce.id }" target="dialog"><span>修改</span></a></td> --%>
		 	</tr>
		 </c:forEach>
		</tbody>
	</table>
	</div>
</div> 
