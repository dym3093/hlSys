<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<form onsubmit="if(this.action != '${path }/cardMsg/cardMsgAction!listCardMsg.action'){this.action = '${path }/cardMsg/cardMsgAction!listCardMsg.action' ;} ;return navTabSearch(this);" 
	action="${path }/cardMsg/cardMsgAction!listCardMsg.action"  method="post" rel="pagerForm" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>
							持卡人姓名：
						</label>
						<input type="text" name="filter_and_userName_LIKE_S"
							value="${filter_and_userName_LIKE_S }" />
					</td>
					<td>
						<label>性别：</label>
							<select id="sex" name="filter_and_sex_EQ_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10101">
								<option value="${ filter_and_sex_EQ_S }"></option>
							</select>
					</td>
					<td>
					<label>卡号：</label>
							<input type="text" name="filter_and_cardNum_LIKE_S"
							value="${filter_and_cardNum_LIKE_S }" />
					</td>
					</tr>
					<tr>
					<td>
					<label>是否激活：</label>
					<select id="status" name="filter_and_status_EQ_I" rel="iselect" >
						    <option value="">请选择</option>
								<option value=1>是</option>
								<option value=0>否</option>
							</select>
					</td>
					<td>
				<label>激活截止时间：</label>
				<input type="text" name="filter_and_registerEndtime_GEST_T"  id="d787451" style="width: 170px;" onFocus="WdatePicker({minDate:'2010-01-01',maxDate:'#F{$dp.$D(\'d543661\')}'})" class="required" readonly="readonly" value="${filter_and_registerEndtime_GEST_T}" /><a class="inputDateButton" href="javascript:;" >选择</a>
			</td>
			<td>
				<label>至：</label>
				<input type="text" name="filter_and_registerEndtime_LEET_T" id="d543661" style="width: 170px;" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d787451\')}'})" readonly="readonly" value="${filter_and_registerEndtime_LEET_T}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</td>
					<td>
						<div class="buttonActive" style="margin-left: 10px">
							<div class="buttonContent">
								<button type="submit">
									查找
								</button>
							</div>
						</div>
						<div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div>
						</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
			<li><a class="add" href="${path}/cardMsg/cardMsgAction!addCardMsgs.action" target="navTab"><span>生成卡号</span></a></li>
			<li><a class="edit" href="${path}/cardMsg/cardMsgAction!modifyCardMsg.action?id={sid_user}" target="navTab"><span>修改</span></a></li>
				<web:exportExcelTag pagerFormId="pagerFindForm"
					pagerMethodName="findByPage"
					actionAllUrl="org.hpin.cardMsg.web.CardMsgAction"
					informationTableId="exportExcelTable" 
					fileName="CardMsg"></web:exportExcelTag>
			</ul>
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="150" id="exportExcelTable">
		<thead>
			<tr>
			<th width="40px"><nobr>序号</nobr></th>
			<th export = "true" columnEnName = "userName" columnChName = "持卡人姓名">持卡人姓名</th>
			<th export = "true" columnEnName = "cardNum" columnChName = "卡号">卡号</th>
			<th export = "true" columnEnName = "nation" columnChName = "民族">民族</th>
			<th export = "true" columnEnName = "sex" columnChName = "性别" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">性别</th>
			<th export = "true" columnEnName = "tel" columnChName = "电话">电话</th>
			<th export = "true" columnEnName = "email" columnChName = "电子邮件">电子邮件</th>
			<th export = "true" columnEnName = "idcardNum" columnChName = "证件号码">证件号码</th>
			<th export = "true" columnEnName = "registerEndtime" columnChName = "激活截止时间">激活截止时间</th>
			<th export = "true" columnEnName = "createTime" columnChName = "创建时间">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ page.results }" var="cardMsg"
				varStatus="status">
				<tr target="sid_user" rel="${cardMsg.id }">
					<td width="40px" align="center">
						<input type="checkbox" name="ids" value="${ cardMsg.id }" >${ status.count }
					</td>
					<td align="center" nowrap="nowrap">
					${cardMsg.userName}
					</td>
					<td align="center" nowrap="nowrap"><a href="${ path }/cardMsg/cardMsgAction!browCardMsg.action?id=${ cardMsg.id }" 
					style="color:#0000FF" target="dialog">${cardMsg.cardNum}</a></td>
					<td align="center" nowrap="nowrap">${cardMsg.nation}</td>
					<td align="center">
						<hpin:id2nameDB id="${cardMsg.sex}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td>
					<td align="center" nowrap="nowrap">${cardMsg.tel}</td>
					<td align="center" nowrap="nowrap">${cardMsg.email}</td>
					<td align="center" nowrap="nowrap">${cardMsg.idcardNum}</td>
					<td align="center" nowrap="nowrap">${fn:substring( cardMsg.registerEndtime,0,10 )}</td>
					<td align="center" nowrap="nowrap">${fn:substring( cardMsg.createTime,0,19 )}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
