<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<style type="text/css">
.righttd{
	padding-left: 50px;
}
</style>
<script type="text/javascript" language="javascript">

</script>
        <div class="tip"><span>打印信息</span></div>
        <div class="pageHeader">
            <table style="margin-left:50px" id="lineHeight">
                <tr>
                    <td>
                        <label>批次号：</label>
                        <span>${printBatchBean.printBatchNo}</span>
                    </td>
                    <td class="righttd">
                        <label>套餐：</label>
                		<span> ${printBatchBean.combo}</span>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <label>省：</label>
                        <span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${printBatchBean.province }"/></span>
                    </td>
                    <td class="righttd">
                        <label>市：</label>
                		<span><hpin:id2nameDB  beanId="org.hpin.base.region.dao.RegionDao" id="${printBatchBean.city }"/></span>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <label>所属公司：</label>
               		 	<span><hpin:id2nameDB  beanId="org.hpin.base.usermanager.dao.UserDao" id="${printBatchBean.ownedCompanyId}"/> </span>
                    </td>
                    <td class="righttd">
                        <label>支公司：</label>
                		<span><hpin:id2nameDB  beanId="org.hpin.statistics.briefness.dao.CustomerRelationshipDao" id="${printBatchBean.branchCompanyId}"/> </span>
                    </td>
                </tr>

            </table>
         </div>
        
        
        <div class="tip"><span>PDF详情清单</span></div>

        <div class="panelBar">
            <jsp:include page="/common/pagination.jsp" />
        </div>

        <table class="list" width="100%" layoutH="170">
            <thead>
            <tr>
                <th width="40">序号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>条码</th>
                <th>创建日期</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="details" varStatus="status">
                <tr>
                    <td align="center">${status.count}</td>
                    <td align="center">${details.userName}</td>
                    <td align="center">${details.gender}</td>
                    <td align="center">${details.age}</td>
                    <td align="center">${details.code}</td>
                    <td align="center">${fn:substring(details.createTime,0,19)}</td> 
                </tr>
            </c:forEach>
            </tbody>
        </table>
