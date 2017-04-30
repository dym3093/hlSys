<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String[] external = new String[]{"false","true"};
	pageContext.setAttribute("external",external);
%>
<div class="accordion" fillSpace="sideBar">
	<c:forEach items="${subMenus}" var="menu" varStatus="status">
		<c:if test="${fn:contains(userUrls , menu.url)}">
		<div class="accordionHeader">
			<h2><span>Folder</span>${menu.name }</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
					<c:if test="${menu.isLeaf != 1}">
						<c:forEach items="${menu.childrenSet}" var="child" varStatus="status2">
							<c:if test="${fn:contains(userUrls , child.url)}">
								<li>
									<a href="${pageContext.request.contextPath }/${child.url }" target="navTab" rel="menu_${child.id}" external="${external[child.isFrame] }">${child.name }</a>
									<c:if test="${child.isLeaf != 1}">
										<ul>
											<c:forEach items="${child.childrenSet}" var = "child1" varStatus="status3">
												<c:if test="${fn:contains(userUrls , child1.url)}">
													<li>
														<a href="${pageContext.request.contextPath }/${child1.url }" target="navTab" rel="menu_${child1.id}" external="${external[child1.isFrame] }">${child1.name }</a>
														<c:if test="${child1.isLeaf != 1}">
															<ul>
																<c:forEach items="${child1.childrenSet}" var="child2" varStatus="status4">
																	<c:if test="${fn:contains(userUrls , child2.url)}">
																		<li>
																			<a href="${pageContext.request.contextPath }/${child2.url }" target="navTab" rel="menu_${child2.id}" external="${external[child1.isFrame] }">${child2.name }</a>
																			<c:if test="${child2.isLeaf != 1}">
																				<ul>
																					<c:forEach items="${child2.childrenSet}" var="child3" varStatus="status5">
																						<c:if test="${fn:contains(userUrls , child3.url)}">
																						<li>
																							<a href="${pageContext.request.contextPath }/${child3.url }" target="navTab" rel="menu_${child3.id}" external="${external[child3.isFrame] }">${child3.name }</a>
																							<c:if test="${child3.isLeaf != 1}">
																								<ul>
																									<c:forEach items="${child3.childrenSet}" var="child4" varStatus="status6">
																										<c:if test="${fn:contains(userUrls , child4.url)}">
																										<li>
																											<a href="${pageContext.request.contextPath }/${child4.url }" target="navTab" rel="menu_${child4.id}" external="${external[child4.isFrame] }">${child4.name }</a>
																											<c:if test="${child4.isLeaf != 1}">
																												<ul>
																													<c:forEach items="${child4.childrenSet}" var = "child5" varStatus="status7">
																														<c:if test="${fn:contains(userUrls , child5.url)}">
																														<li>
																															<a href="${pageContext.request.contextPath }/${child5.url }" target="navTab" rel="menu_${child5.id}" external="${external[child5.isFrame] }">${child5.name }</a>
																															<c:if test="${child5.isLeaf != 1}">
																																<ul>
																																	<c:forEach items="${child5.childrenSet}" var = "child6" varStatus="status8">
																																		<c:if test="${fn:contains(userUrls , child6.url)}">
																																		<li>
																																			<a href="${pageContext.request.contextPath }/${child6.url }" target="navTab" rel="menu_${child6.id}" external="${external[child6.isFrame] }">${child6.name }</a>
																																			<c:if test="${child6.isLeaf != 1}">
																																				<ul>
																																					<c:forEach items="${child6.childrenSet}" var="child7" varStatus="status9">
																																						<c:if test="${fn:contains(userUrls , child7.url)}">
																																						<li>
																																							<a href="${pageContext.request.contextPath }/${child7.url }" target="navTab" rel="menu_${child7.id}" external="${external[child7.isFrame] }">${child7.name }</a>
																																							<c:if test="${child7.isLeaf != 1}">
																																								<ul>
																																									<c:forEach items = "${child7.childrenSet}" var = "child8" varStatus="status10">
																																										<c:if test="${fn:contains(userUrls , child8.url)}">
																																										<li>
																																											<a href="${pageContext.request.contextPath }/${child8.url }" target="navTab" rel="menu_${child8.id}" external="${external[child8.isFrame] }">${child8.name }</a>
																																										</li>
																																										</c:if>
																																									</c:forEach>
																																								</ul>
																																							</c:if>
																																						</li>
																																						</c:if>
																																					</c:forEach>
																																				</ul>
																																			</c:if>																																		
																																		</li>
																																		</c:if>
																																	</c:forEach>
																																</ul>
																															</c:if>																														
																														</li>
																														</c:if>
																													</c:forEach>
																												</ul>
																											</c:if>
																										</li>
																										</c:if>
																									</c:forEach>
																								</ul>
																							</c:if>
																						</li>
																						</c:if>
																					</c:forEach>
																				</ul>
																			</c:if>
																		</li>
																	</c:if>
																</c:forEach>
															</ul>
														</c:if>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</c:if>									
								</li>
							</c:if>
						</c:forEach>
				</c:if>
			</ul>
		</div>
		</c:if>
	</c:forEach>
</div>


