<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<div class="pageContent" >
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a id="tab1" href="${path}/healthMsg/healthInfoAction!browHealthInfo.action?id=${id}" class="j-ajax"><span>症状信息</span></a></li>
		    	<li><a id="tab2"  href="${path }/healthMsg/healthInfoAction!browHealthDetail.action?infoid=${id}"  class="j-ajax"><span>录入信息</span></a></li>
				
				</ul>
				
			</div>
		</div>
		<div class="tabsContent" style="background-color:#FFF">
			<div></div>
			<div></div>
			
		</div>
		<div class="tabsFooter" >
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>