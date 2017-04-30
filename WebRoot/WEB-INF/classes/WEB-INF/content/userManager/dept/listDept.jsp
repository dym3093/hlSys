<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
  <head>
	<title>部门列表</title>
	 <script type="text/javascript">
      function addDept(){
        var id = parent.document.getElementById("parentId").value;
        location.href='../um/dept!addDept.action?parentId='+id;
      }
      $(document).ready(function(){
  		if("${flush}" =="true"){
  			parent.navTab.reload();
  		}
      });
      
    </script>
  </head>
  <body>
    <table class="navigation">
	  <tr>
		<td class="location">
		  <web:location value="组织管理-部门管理" />
		</td>
	  </tr>
    </table>
   <div class="content">
	  <table class="handle-table">
		<tr>
		  <td class="page-td"><web:pager /></td>
		  <td class="handle-td">
			<input type="button" class="button" style="cursor:pointer" value="添 加 " onclick="addDept()" />&nbsp;
			<input type="submit" class="button" style="cursor:pointer" value="修 改" onclick="updateAction('${path}/um/dept!modifyDept.action','id','id')" />&nbsp;
			<input type="submit" class="button" style="cursor:pointer" value="删 除" onclick="deleteAction('${path}/um/dept!deleteDept.action','ids','id')" />
		  </td>
		</tr>
	  </table>
	  <table class="grid-table">
	    <tr class="tr_title">
		  <th width="10%">序号</th>
		  <th width="15%"><nobr>部门编号</nobr></th>
		  <th width="30%"><nobr>部门全称</nobr></th>
		  <th width="40%"><nobr>地点</nobr></th>
	    </tr>
		<c:forEach items="${page.results}" var="dept" varStatus="status">
		<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
		  <td class="no_td">
			${status.count}
			<input type="checkbox" name="id" id="id" value="${dept.id}" />
		  </td>
		  <td><nobr>${dept.deptCode}</nobr></td>
		  <td><nobr>${dept.deptName}</nobr></td>
		  <td><nobr>${dept.deptAddress}</nobr></td>
		</tr>
		</c:forEach>
	  </table>
	</div>
  </body>
</html>