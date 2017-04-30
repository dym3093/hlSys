<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<div class="pageContent" >
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a id="tab1" href="${path}/healthMsg/healthInfoAction!addHealthInfo.action?id=${id}&navTabId=${ navTabId }" class="j-ajax"><span>症状信息</span></a></li>
		    	<li><a id="tab2"  href="${path }/healthMsg/healthInfoAction!addHealthDetail.action?infoid=${id}&navTabId=${navTabId}&flag=1"  class="j-ajax"><span>录入信息</span></a></li>
				  <li><a id="tab3"  href="${path }/healthMsg/healthInfoAction!uploadFile.action?infoid=${id}&navTabId=${navTabId}" class="j-ajax"><span>导入信息</span></a></li>
				</ul>
				
			</div>
		</div>
		<div class="tabsContent" style="background-color:#FFF">
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter" >
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>