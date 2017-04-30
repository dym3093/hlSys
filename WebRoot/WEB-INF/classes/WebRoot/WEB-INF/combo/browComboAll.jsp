<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageContent" style="overflow: hidden;">
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul><!--标签名 链接到新页面,自己定义文件夹名-->
					<li><a href="${path }/combo/comboAction!browCombo.action?id=${comboId}&browState=${browState}&navTabId=${navTabId}" class="j-ajax"><span>套餐基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="background-color:#FFFFFF;overflow: hidden" >
           	<div></div>
           	<div></div>
			<div></div>
			<div></div>
        </div>
		
		<div class="tabsFooter" >
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>