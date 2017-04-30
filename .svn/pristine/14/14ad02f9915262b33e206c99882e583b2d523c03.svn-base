<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="welcomePage" value="/welcome.jsp" />
<c:if test="${not empty sessionScope.menu[0]}">
	<c:choose>
  	  <c:when test="${fn:startsWith(sessionScope.menu[0].url, ':')}">
        <c:set var="welcomePage" value="${scheme}://${serverName}${sessionScope.menu[0].url}" />      
      </c:when>
      <c:otherwise>       
        <c:set var="welcomePage" value="${sessionScope.menu[0].url}" />
      </c:otherwise>
    </c:choose>
    
  
</c:if>
<ul id="menu">
  <c:forEach var='menu' items='${sessionScope.menu}'>
    <c:choose>
      <c:when test="${fn:startsWith(menu.url, ':')}">
        <c:set var="url" value="${scheme}://${serverName}${menu.url}" />      
      </c:when>
      <c:otherwise>
        <c:set var="url">
          <c:url value="${menu.url}" />          
        </c:set>
      </c:otherwise>
    </c:choose>
    <li><a href="${url}" target="mainFrame"><span>|&nbsp;&nbsp;${menu.text}</span></a></li>
  </c:forEach>
</ul>