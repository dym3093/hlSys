<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
    
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>    
    
<script type="text/javascript">
function changeTab2(){
	//alert("tab2--"+$("#infoid",navTab.getCurrentPanel()).val());
	var url = "${path}/healthMsg/healthInfoAction!addHealthDetail.action?infoid="+$("#infoid",navTab.getCurrentPanel()).val()+"&navTabId=${navTabId}";
	$("#tab2",navTab.getCurrentPanel()).attr("href",url);
}
function changeTab3(){
	//alert("tab3--"+$("#infoid",navTab.getCurrentPanel()).val());
	var url = "${path}/healthMsg/healthInfoAction!uploadFile.action?infoid="+$("#infoid",navTab.getCurrentPanel()).val()+"&navTabId=${navTabId}";
	$("#tab3",navTab.getCurrentPanel()).attr("href",url);
}
</script>
<div class="pageContent" >
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul style = "position:relative;"><!--标签名 链接到新页面,自己定义文件夹名-->
				 	<div id = "divTab2" style = "position:absolute;left:70px;height:30px;width:70px;z-index:1000;filter:alpha(opacity=30);-moz-opacity:0.3;-khtml-opacity:0.3;opacity:0.3;background-color:#FFFFFF;"></div>
					<div id = "divTab3" style = "position:absolute;left:140px;height:30px;width:70px;z-index:10000;filter:alpha(opacity=50);-moz-opacity:0.5;-khtml-opacity:0.5;opacity:0.5;background-color:#FFFFFF;"></div>
					<li><a id = "tab1" href="${path}/healthMsg/healthInfoAction!addHealthInfo.action?navTabId=${ navTabId }" class="j-ajax"><span>症状信息</span></a></li>
		    	<li onclick="changeTab2()"><a id="tab2"  href="${path}/healthMsg/healthInfoAction!addHealthDetail.action"  class="j-ajax"><span>录入信息</span></a></li>
		    	<li onclick="changeTab3()"><a id="tab3"  href="${path}/healthMsg/healthInfoAction!uploadFile.action"  class="j-ajax"><span>导入信息</span></a></li>
					<input type="hidden" name="" id="infoid" initValue="" value="" />
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