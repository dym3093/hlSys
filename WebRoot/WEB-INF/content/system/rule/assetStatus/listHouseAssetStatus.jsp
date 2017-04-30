<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/ext.jsp"%>
		<%@ include file="/common/common.jsp"%>
		<style>
			.td_title{
				background:url(../images/bg_header.jpg) repeat-x;
				height:24px;
				font-family:"新宋体";
				font-size:12px;
				color:#000;
				font-weight:bold;
			}
			
			.th_find{
				background-color : white  ;
			}
	</style>
	
	<script type="text/javascript">
		function showOrHide(str , index){
			var check = document.getElementById(str) ;
			if(check.checked){
				$("#" + str + "zt").show() ;
				$("td:eq("+ index +")", $("tr")).show();
			}else{
				$("#" + str + "zt").hide() ;
				$("td:eq("+ index +")", $("tr")).hide();
			}
		}
	</script>
	
	</head>
	<body>
		<table class="navigation">
			<tr>
				<td class="location">
					<web:location value="库存管理-房屋状态" />
				</td>
			</tr>
		</table>
		<form name="searchForm" class="search_form" method="post"
			action="assetStatus!listHouseAssetStatus.action">
			<table  width="100%">
				<tr>
					<td class = "label">
						<label>
							房屋编号：
						</label>
					</td>
					<td class = "th_find">
						<input type="text" name="filter_and_house__houseCode_LIKE_S"
							 value="${filter_and_house__houseCode_LIKE_S}" />
					</td>

					<td class = "label">
						<label>
							房源编号：
						</label>
					</td>
					<td class = "th_find">
						<input type="text" name="filter_and_house__houseSourceCode_LIKE_S"
							 value="${filter_and_house__houseSourceCode_LIKE_S}" />
					</td>

					<td class = "label">
						<label>
							房间编号：
						</label>
					</td>
					<td class = "th_find">
						<input type="text" name="filter_and_room__roomCode_LIKE_S"
							 value="${filter_and_room__roomCode_LIKE_S}" />
					</td>

					<td class = "th_find">
						&nbsp;
					</td>
				</tr>

				<tr>
					<td colspan="2" class = "th_find" style="text-align: center;">
						<input type = "checkbox" id = "xs" value = "销售状态" checked="checked" onclick="showOrHide('xs' , 4)">销售状态&nbsp;&nbsp;
						<input type = "checkbox" id = "wl" value = "物理状态" checked="checked" onclick="showOrHide('wl' , 5)">物理状态&nbsp;&nbsp;
						<input type = "checkbox" id = "gl" value = "管理状态" checked="checked" onclick="showOrHide('gl' , 6)">管理状态&nbsp;&nbsp;
					</td>
					<td class = "label">
						<label>
							更新开始时间：
						</label>
					</td>
					<td class = "th_find">
						<input type="text" name="filter_and_createTime_GE_T" 
							value="${filter_and_createTime_GE_T}" class="Wdate"
							readonly="true" onClick="WdatePicker()" />
					</td>
					<th class = "label">
						<label>
							更新结束时间：
						</label>
					</td>
					<td class = "th_find">
						<input type="text" name="filter_and_createTime_LE_T" 
							value="${filter_and_createTime_LE_T}" class="Wdate"
							readonly="true" onClick="WdatePicker()" />
					</td>
				</tr>
				<tr>
					<td align="right" colspan="6" class = "th_find">
						<input type="hidden" name="_pageNum" value="${page.pageNum}" />
						<input type="submit" class="button" value="查 询" />
						<input type="button" class="button" value="清 空"
							onclick="clearSearchForm(this.form)" />
					</td>
				</tr>
			</table>
		</form>

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="page-td">
						<web:pager />
					</td>
					<td class="handle-td">

					</td>
				</tr>
			</table>

			<table class="grid-table" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="tr_title">
					<th width="2%">
						<nobr>
							序号
						</nobr>
					</th>
					<th width="6%">
						<nobr>
							房屋编号
						</nobr>
					</th>
					<th width="6%">
						<nobr>
							房源编号
						</nobr>
					</th>

					<th width="12%">
						<nobr>
							行政地址
						</nobr>
					</th>

					<th width="10%" id = "xszt">
						<nobr>
							销售状态
						</nobr>
					</th>
					
					<th width="10%" id = "wlzt">
						<nobr>
							物理状态
						</nobr>
					</th>
					
					<th width="10%" id = "glzt">
						<nobr>
							管理状态
						</nobr>
					</th>

					<th width="8%">
						<nobr>
							合同编号
						</nobr>
					</th>

					<th width="8%">
						<nobr>
							更新时间
						</nobr>
					</th>

					<th width="4%">
						<nobr>
							历史版本
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="houseStatus"
					varStatus="status">
					<tr <c:if test = "${status.count%2==0}">class="td_jg"</c:if>>
						<td class="no_td">
							<nobr>
								<span id="t0" style="border: 1 black solid; cursor: hand;"
									onClick="if(t1${status.index}.style.display=='none'){t1${status.index}.style.display='';innerText='-';}else{t1${status.index}.style.display='none';innerText='+';}">+</span>
								&nbsp;
							</nobr>
						</td>
						<td>
							<nobr>
								${houseStatus.house.houseCode}
							</nobr>
						</td>

						<td>
							<nobr>
								${houseStatus.house.houseSourceCode}
							</nobr>
						</td>

						<td title="${houseStatus.house.executiveAddress}">
							<nobr>
								<web:strTruncate field="${houseStatus.house.executiveAddress}"
									length="16" />
							</nobr>
						</td>

						<td>
							<nobr>
								${houseStatus.statusOfSell}
							</nobr>
						</td>
						
						<td>
							<nobr>
								${houseStatus.statusOfPhysical}
							</nobr>
						</td>

						<td>
							<nobr>
								${houseStatus.statusOfManagement}
							</nobr>
						</td>


						<td>
							<nobr>
								${houseStatus.contractCode}(
								<web:ShowDictName identifier="contract_type"
									code="${houseStatus.contractTypeCode}" />
								)
							</nobr>
						</td>
						<td>
							<nobr>
								<fmt:formatDate value="${houseStatus.createTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</nobr>
						</td>

						<td>
							<nobr>
								<web:detail
									url="assetStatus!listHistoryHouseAssetStatus.action?houseId=${houseStatus.houseId}"
									value="查看" isParent="true" />
							</nobr>
						</td>
					</tr>
					<tbody id="t1${status.index}" style="display: none;">
						<tr>
							<td>
								&nbsp;
							</td>
							<td  class = "td_title">
								<nobr>
									房间编号
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									房间名称
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									房间类型
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									销售状态
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									物理状态
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									管理状态
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									使用面积	
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									是否隔断
								</nobr>
							</td>
							<td  class = "td_title">
								<nobr>
									销售价格
								</nobr>
							</td>
						</tr>
						<c:forEach items="${houseStatus.roomStatusBoList }" var = "roomStatus" varStatus="status2">
							<tr>
								<td>
									&nbsp;
								</td>
								<td>
									${roomStatus.room.roomCode }
								</td>
								<td>
									${roomStatus.room.roomName }
								</td>
								<td>
									<web:ShowDictName identifier="room_type" code="${roomStatus.room.roomTypeCode }"></web:ShowDictName>
								</td>
								<td>
									${roomStatus.statusOfSell }
								</td>
								<td>
									${roomStatus.statusOfPhysical }
								</td>
								<td>
									${roomStatus.statusOfManagement }
								</td>
								<td>
									${roomStatus.room.usageArea }
								</td>
								<td>
									<web:showYesNoName value="${roomStatus.room.isSeparate }" aliasNo="否" aliasYes="是"></web:showYesNoName> 
								</td>
								<td>
									${roomStatus.room.sellPrice }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:forEach>
			</table>
		</div>
	</body>
</html>