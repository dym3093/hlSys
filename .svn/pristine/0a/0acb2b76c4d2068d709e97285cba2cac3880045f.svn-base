<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function changeProduct() {
	var ids = '';
	var count = 0;
	var status = '';
	$('input:checkbox[name="ids"]:checked',navTab.getCurrentPanel()).each(function(i, n) {
		ids = n.value;
		count += count+1;
	});
	if(count == 0) {
		alert('请选择你要变更的信息！');
		return ;
	}
	else if(count > 1) {
		alert('只能选择一条信息进行变更！');
		return ;		
	}else{
		 	var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
			navTab.openTab("listproduct", "../healthMsg/healthInfoAction!modifyHealth.action?id="+ids+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
	}
	
	}
</script>
<div class="tip">
	<span>查询</span>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${path }/healthMsg/healthInfoAction!listHealthInfo.action"  method="post" rel="pagerForm" id="pagerFindForm">
		<div class="searchBar">
			<table class="pageFormContent">
				<tr>
					<td>
						<label>
							病症：
						</label>
						<input type="text" name="filter_and_disorder_LIKE_S"
							value="${filter_and_disorder_LIKE_S }" />
					</td>
					
			<td>
						<label>
							首诊分科:
						</label>
						<input type="text" name="filter_and_department_LIKE_S"
							value="${filter_and_department_LIKE_S }" />
					</td>
					
					
			   <td>
						<label>所属人群：</label>
							<select id="crowd" name="filter_and_crowd_EQ_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10107">
								<option value="${ filter_and_crowd_EQ_S }"></option>
							</select>
					</td>
			
					
					<%-- <td>
						<label>科室：</label>
							<select id="depart" name="filter_and_department_EQ_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10105"  ref="dthin"  refUrl="${path}/hpin/sysDictType!treeRegionDispose.action?parentId=">
								<option value="${ filter_and_department_EQ_S }"></option>
							</select>
					</td>
				
					
				 <tr>	
				 	<td>
						<label>科室明细：</label>
							<select id="dthin" name="filter_and_departmentthin_EQ_S" rel="iselect">
								<option value="${ filter_and_departmentthin_EQ_S }"></option>
							</select>
					</td> --%>
				 	
				 	
		      <td>
						<label>身体部位：</label>
							<select id="body" name="filter_and_bodyParts_EQ_S" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10106"  ref="thin"  refUrl="${path}/hpin/sysDictType!treeRegionDispose.action?parentId=">
								<option value="${ filter_and_bodyParts_EQ_S }"></option>
							</select>
					</td>
					</tr>
					<tr>
					 <td>
						<label>部位明细：</label>
							<select id="thin" name="filter_and_bodyPartsthin_EQ_S" rel="iselect">
								<option value="${ filter_and_bodyPartsthin_EQ_S }"></option>
							</select>
					</td>
					<td>
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
		<!--	<li><a class="add" href="${path }/healthMsg/healthInfoAction!uploadFile.action?infoid=402880e5480fed0401480ff5ab620003" target="navTab"><span>测试导入</span></a></li>-->
			
			<li><a class="add" href="${path }/healthMsg/healthInfoAction!addHealth.action" target="navTab"><span>添加</span></a></li>
		<!--	<li><a class="add" href="${path }/healthMsg/healthInfoAction!addHealthDetail.action" target="navTab"><span>添加测试</span></a></li>
			<li><a class="edit" href="${path}/healthMsg/healthInfoAction!modifyHealth.action?id={sid_user}" target="navTab"><span>修改</span></a></li>-->
			<li><a class="edit" onclick="changeProduct()" style="cursor:pointer;"><span>变更</span></a></li>
			<li><a class="delete" href="${path}/healthMsg/healthInfoAction!deleteHealthInfo.action"  rel="ids" postType="string" title="确定要删除吗?" target="selectedTodo"><span>删除</span></a></li>
							
				<web:exportExcelTag pagerFormId="pagerFindForm"
					pagerMethodName="findByPage"
					actionAllUrl="org.hpin.healthMsg.web.HealthInfoAction"
					informationTableId="exportExcelTable" 
					fileName="HealthMsg"></web:exportExcelTag>
				</ul>	
				<li><input type="checkbox" class="checkboxCtrl" group="ids" />全选</li>
					
			
		<jsp:include page="/common/pagination.jsp" />
	</div>
	<table class="list" width="100%" layoutH="150" id="exportExcelTable">
		<thead>
			<tr>
			<th width="40px"><nobr>序号</nobr></th>
			
			<th export = "true" columnEnName = "disorder" columnChName = "病症">病症</th>
			<th export = "true" columnEnName = "department" columnChName = "首诊分科">首诊分科</th>
				<th export = "true" columnEnName = "crowd" columnChName = "所属人群" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">所属人群</th>
			<!-- <th export = "true" columnEnName = "department" columnChName = "科室" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">科室</th>
			<th export = "true" columnEnName = "departmentthin" columnChName = "科室明细" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">科室明细</th> -->
			<th export = "true" columnEnName = "bodyParts" columnChName = "身体部位" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">身体部位</th>
			<th export = "true" columnEnName = "bodyPartsthin" columnChName = "部位明细" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">部位明细</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ page.results }" var="healthInfo"  varStatus="status">
				<tr target="sid_user" rel="${healthInfo.id }">
					<td width="40px" align="center">
						<input type="checkbox" name="ids" value="${ healthInfo.id }" >${ status.count }
					</td>
					
					<td align="center" nowrap="nowrap"><a href="${ path }/healthMsg/healthInfoAction!browHealth.action?id=${ healthInfo.id }"  target="navTab">${healthInfo.disorder}</a></td>
					<td align="center" nowrap="nowrap">${healthInfo.department}</a></td>
			    <td align="center">
						<hpin:id2nameDB id="${healthInfo.crowd}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td>
					<%-- <td align="center">
						<hpin:id2nameDB id="${healthInfo.department}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td>
						<td align="center">
						<hpin:id2nameDB id="${healthInfo.departmentthin}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td> --%>
				  <td align="center">
						<hpin:id2nameDB id="${healthInfo.bodyParts}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td>
					 <td align="center">
						<hpin:id2nameDB id="${healthInfo.bodyPartsthin}" beanId="org.hpin.base.dict.dao.SysDictTypeDao"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
