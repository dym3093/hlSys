<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
#table2 td {
	TEXT-ALIGN: center;
	BACKGROUND-COLOR: #f7f7f7;
	padding: 4, 4, 4, 4;
}
#table2 table {
	FONT-SIZE: 12pt;
	cellspacing: 10;
	cellpadding: 10;
	padding: 30, 30, 30, 30;
	width: 100%;
}
</style>
<div class="pageContent">
	<form id="form" name="form" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/a/b!c.action" method="post">
		<div id="table" class="pageFormContent" layoutH="56">
			<div>
				<div class="tip">
					<span>仓库基本信息</span>
				</div>
				<p>
					<label>仓库名称：</label> <input type="hidden" value="${stroeWarehouse.id}" /> 
					<input type="text" value="${stroeWarehouse.name}" readonly="readonly" />
				</p>
				<p>
					<label>省 份：</label> <input type="text"
						value="<hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/>"
						readonly="readonly" />
				</p>
				<p>
					<label>城 市：</label> <input type="text"
						value="<hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/>"
						readonly="readonly">
				</p>
				<p>
					<label>联系人：</label> <input type="text"
						name="stroeWarehouse.director" maxlength="30" size="30"
						readonly="readonly" value="${stroeWarehouse.director}" />
				</p>
				<p>
					<label>联系电话：</label> <input type="text" name="stroeWarehouse.tel"
						maxlength="30" size="30" readonly="readonly"
						value="${stroeWarehouse.tel}" />
				</p>
				<p class="nowrap" style="height: 61px;">
					<label>详细地址：</label>
					<textarea name="stroeWarehouse.address" cols="4"
						readonly="readonly" rows="2">${stroeWarehouse.address}</textarea>
				</p>
			</div>
			<div class="divider"></div>
			<div>
				<div class="tip">
					<span>产品类别信息</span>
				</div>
				<table class="list" name="allList" width="100%">
					<thead>
						<tr>
							<th>大类</th>
							<th>小类</th>
							<th>规格</th>
							<th>描述</th>
							<th>库存数量</th>
							<th>单价</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<input type="hidden" name="stroeType.id" value="${stroeType.id}" />
							<td align="center"><hpin:id2nameDB id='${stroeType.remark1}'
									beanId='org.hpin.base.dict.dao.SysDictTypeDao' /></td>
							<td align="center">${stroeType.name}</td>
							<td align="center">${stroeType.standards}</td>
							<td align="center">${stroeType.descripe}</td>
							<td align="center">${storeAll.count}</td>
							<td align="center">${storeDetail.price}</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="divider"></div>
			<div class="tip">
				<span>发货信息录入</span>
			</div>
			<div class="searchBar">
				<table width="80%">
					<tr>
						<td width="15%"><label>公司名称：</label></td>
						<td width="35%"><input type="hidden"
							name="produce.bannyCompannyId" value="produce.bannyCompannyId" />
							<input tabindex="1" name="produce.bannyCompannyName" type="text"
							class="required code" value="${produce.bannyCompannyName}" /></td>

						<td width="15%"><label>场次信息：</label></td>
						<td><input tabindex="2" name="produce.eventNos" type="text"
							class="textInput" value="${produce.eventNos}" /></td>
					</tr>
					<tr>
						<td><label>快递单号：</label></td>

						<td><input tabindex="3" name="produce.emsNo" type="text"
							class="textInput" value="${produce.emsNo}" /></td>
						<td><label>快递公司：</label></td>

						<td><input tabindex="4" name="produce.emsName" type="text"
							class="textInput" value="${produce.emsName}" /></td>
					</tr>
					<tr>
						<td><label>派发地址：</label></td>

						<td><textarea tabindex="5" name="produce.address" cols="4"
								rows="2"></textarea></td>

						<td><label>接受人姓名：</label></td>

						<td><input tabindex="6" name="produce.receiveName"
							type="text" class="textInput" value="${produce.receiveName}" /></td>
					</tr>
					<tr>
						<td><label>接收人联系方式：</label></td>

						<td><input tabindex="7" name="produce.receiveTel" type="text"
							class="required" value="${customer.receiveTel}" /></td>


						<td><label>业务人员：</label></td>

						<td>
							<%--   <select id="comboselect" class="required comboSelect" name="customer.setmealName"  rel="iselect"  loadUrl="${path}/resource/customerRelationShip!findAllCombo.action">
				 <option value="${events.comboName}"></option>
			  </select> --%> <input tabindex="8" name="produce.businessName"
							type="text" class="required" value="${produce.businessName}" /> <input
							type="hidden" name="produce.businessId"
							value="${ produce.businessId}" />

						</td>
					</tr>
					<tr>
						<td><label>关联申请单：</label></td>

						<td><input tabindex="9" type="text"
							name="produce.applicationId" value="${produce.applicationId}" />
						</td>
						<td><label>数量：</label></td>

						<td><input tabindex="10" type="text" name="produce.remark"
							value="${produce.remark}" /></td>

						<input type="hidden" name="produce.type" value="1" />
						</td>
					</tr>
					<tr>
						<td><label>使用时间：</label></td>

						<td><input id="customerDate" name="produce.useTime"
							datefmt="yyyy-MM-dd"
							value="${fn:substring(produce.useTime,0,19)}" type="text"
							class="date" /> <a class="inputDateButton" href="javascript:;">选择</a>
						</td>

						<input type="hidden" name="produce.type" value="1" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="formBar" style="width:99%">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">发货</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
