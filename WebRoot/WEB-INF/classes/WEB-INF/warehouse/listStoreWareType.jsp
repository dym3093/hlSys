<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
function edit(ids) {
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		var wareId=$("#wareId").val();
		navTab.openTab("ModifyType", "../warehouse/warehouse!toModifyTypeById.action?id="+ids+"&wareId="+wareId+"&navTabId="+navTabId, { title:"变更", fresh:false, data:{} });
}
</script>

      
        <input type="hidden" name="navTabId" value="${navTabId}"/>
       
        <div class="tip"><span>类别信息</span></div>
        <div class="pageHeader">
            <table class="pageFormContent xxmes" id="lineHeight">
                <tr>
                    <td><label>仓库名称：</label><input type="hidden" name ="stroeWarehouse.id" id="wareId" value="${stroeWarehouse.id}"/></td>
                    <td><span>${stroeWarehouse.name}</span></td>
                    <td><label>省份：</label></td>
                    <td><span><hpin:id2nameDB id="${stroeWarehouse.province}" beanId="org.hpin.base.region.dao.RegionDao"/></span></td>
                    <td><label>城市：</label></td>
                    <td><span><hpin:id2nameDB id="${stroeWarehouse.city}" beanId="org.hpin.base.region.dao.RegionDao"/></span></td>
                    <td><label>联系人：</label></td>
                    <td><span>${stroeWarehouse.director}</span></td>
                </tr>
                <tr>
                    <td><label>联系电话：</label></td>
                    <td><span>${stroeWarehouse.tel}</span></td>
                    <td><label>详细地址：</label></td>
                    <td><span>${stroeWarehouse.address}</span></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>
       
        
        <div class="tip"><span>${stroeWarehouse.name}类别信息清单</span></div>
        <form rel="pagerForm" id="pagerFindForms" onsubmit="if(this.action != '${path}/warehouse/warehouse!browStoreWarehouse.action?id=${stroeWarehouse.id}'){this.action = '${path}/warehouse/warehouse!browStoreWarehouse.action?id=${stroeWarehouse.id} }' ;} ;return navTabSearch(this);" action="${path}/warehouse/warehouse!browStoreWarehouse.action?id=${stroeWarehouse.id}" method="post">
        </form>
        <div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="${path}/warehouse/warehouse!toAddStoreType.action?stroeWarehouseId=${stroeWarehouse.id}" target="navTab" ><span>添加品类</span></a></li>
            </ul>
            <jsp:include page="/common/pagination.jsp" />
        </div>
        <input name="idsp" type="hidden"  value="${stroeWarehouse.id}" />
        <table class="list" width="100%;" layoutH="170" id="exportExcelTables">
            <thead>
            <tr>
                <th width="5%">序号</th>
                <th width="10%;" export = "true" columnEnName = "remark1" columnChName = "类别" id2NameBeanId = "org.hpin.base.dict.dao.SysDictTypeDao">类别</th>
                <th width="10%;" export = "true" columnEnName = "name" columnChName = "名称" >名称</th>
                <th  width="15%;" export = "true" columnEnName = "standards" columnChName = "规格" >规格</th>
                <th  width="15%;" export = "true" columnEnName = "descripe" columnChName = "描述" >描述</th>
                <th width="7%;" export = "true" columnEnName = "numPush" columnChName = "入库数量" >入库数量</th>
                <th width="7%;" export = "true" columnEnName = "numPull" columnChName = "出库数量" >出库数量</th>
                <th width="8%;" export = "true" columnEnName = "num" columnChName = "库存数量" >库存数量</th>
                <th  width="15%;" export = "false" columnEnName = "" columnChName = "" >操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="storeType" varStatus="status">
                <tr target="sid_user" rel="${storeType.id }">
                    <td align="center"><input type="hidden" name="storeType.remark" value="${storeType.remark}">${status.count}</td>
                    <%-- <td align="center">${erpCustomer.eventsNo}</td> --%>
                    <td align="center"><hpin:id2nameDB id='${storeType.remark1}' beanId='org.hpin.base.dict.dao.SysDictTypeDao'/></td>
                    <td align="center">	${storeType.name}</td>
                    <td align="center">	${storeType.standards}</td>
                    <td align="center">	${storeType.descripe}</td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${storeType.numPush==0}">0</c:when>
                            <c:otherwise>
                                <a href="${path}/warehouse/warehouse!browPushStoreProduceByTypeId.action?id=${storeType.id}&type=0"  width="1200" height="500" style="color:#0000FF"
                                   target="dialog" mask="true", maxable="true", minable="true", resizable="true", drawable="true" title="${storeType.name}">
                                    ${storeType.numPush}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${storeType.numPull==0}">0</c:when>
                            <c:otherwise>
                                <a href="${path}/warehouse/warehouse!browPullStoreProduceByTypeId.action?id=${storeType.id}&type=1"  width="1100" height="480" style="color:#0000FF" target="dialog" title="${storeType.name}">
                                    ${storeType.numPull}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center"><c:choose>
                            <c:when test="${storeType.num==0}">0</c:when>
                            <c:otherwise>
                                <a href="${path}/warehouse/warehouse!stockByTypeId.action?id=${storeType.id}&type=1"  width="1200" height="500" style="color:#0000FF"
                                   target="dialog" mask="true", maxable="true", minable="true", resizable="true", drawable="true" title="${storeType.name}">
                                    ${storeType.num}</a>
                            </c:otherwise>
                        </c:choose>	</td>
                    <%-- <td align="center">	${storeType.createUserName}</td>
                    <td align="center"> <hpin:id2nameDB  beanId="org.ymjy.combo.dao.ComboDao" id="${erpCustomer.setmealName}"/> ${erpCustomer.setmealName}</td>
                    <td align="center">	${fn:substring(storeType.createTime,0,19)}</td> --%>
                    <td align="center">
                        <div class="panelBar">
                            <ul class="toolBar">
                                <li><a class="edit" onclick="edit('${storeType.id}')" style="cursor:pointer;"><span>变更</span></a></li>
                                <%-- <li><a class="delete" href="${path}/warehouse/warehouse!delByTypeId.action?id=${storeType.id}" postType="string" title="确定要删除吗?" target="ajaxTodo"><span>删除</span></a></li> --%>
                                <li>
                                    <a class="add" id="pull" href="${path }/warehouse/warehouse!toPushGoodsByTypeId.action?id=${storeType.id}" target="dialog" width="500" height="460"><span>入库</span></a>
                                </li>
                                <c:choose>
                                    <c:when test="${storeType.num>0}">
                                        <li>
                                            <a class="delete" id="push" href="${path }/warehouse/warehouse!toPullGoodsByTypeId.action?id=${storeType.id}" target="navTab" rel="PullGoods"><span>派发</span>

                                                <%-- $("#sendTo120",navTab.getCurrentPanel()).click(function(){
                                                $.pdialog.open("${path}/expeditor/vehicleAlam!browForSend.action?id=${ vehicleAlam.id }", "${ vehicleAlam.id }", "推送报警信息到120", {width:800,height:480,mask:true,mixable:true,minable:true,resizable:true,close:"function"});
                                                }); --%>
                                            </a>
                                        </li>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

