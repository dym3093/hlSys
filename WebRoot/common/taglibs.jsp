<%@ taglib uri="widget/tags-web" prefix="web"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri = "/WEB-INF/hpin.tld" prefix="hpin" %>

<script src="${path}/jquery/json2.js" type="text/javascript"></script>
<script src="${path}/scripts/plugin/circle.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("input[util='num']",navTab.getCurrentPanel()).change(function(){
		var value = $(this).val();
		var index = value.indexOf(".");
		if(""==$(this).val()){
			return ;
		}
		if(index>0){
			if(value.substring(index+1,value.length).length==1){
				$(this).val($(this).val()+"0");
			}else if(value.substring(index,value.length).length>2){
				$(this).val($(this).val().substring(0,index+3));
			}
		}else{
			$(this).val(value+".00");
		}
	});
	$("input[util='num']",$.pdialog.getCurrent()).change(function(){
		var value = $(this).val();
		var index = value.indexOf(".");
		if(""==$(this).val()){
			return ;
		}
		if(index>0){
			if(value.substring(index+1,value.length).length==1){
				$(this).val($(this).val()+"0");
			}else if(value.substring(index,value.length).length>2){
				$(this).val($(this).val().substring(0,index+3));
			}
		}else{
			$(this).val(value+".00");
		}
	});
	$("#clearText",navTab.getCurrentPanel()).click(function(){
		$("#pagerFindForm",navTab.getCurrentPanel()).find("input").each(function(){
			if($(this).attr("name")!="configId"){
				$(this).val("");
			}
		});
		$("#pagerFindForm",navTab.getCurrentPanel()).find("select").each(function(){
			$(this).val("");
		});
		$("#pagerFindForm",navTab.getCurrentPanel()).find("textarea").each(function(){
			if($(this).attr("name")!="configId"){
				$(this).val("");
			}
		});
	});
	$("a[target=navTab],a[target=dialog]",navTab.getCurrentPanel()).each(function(){
	    var href = $(this).attr("href");
	    var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	    if(navTabId.lastIndexOf("menu_")>=0){
			if(href.lastIndexOf("&")>0){
				$(this).attr("href",href+"&navTabId="+navTabId);
			}else if(href.lastIndexOf("?")>0){
				$(this).attr("href",href+"&navTabId="+navTabId);
			}else if(href.length>30&&href.lastIndexOf("#")<0){
				$(this).attr("href",href+"?navTabId="+navTabId);
			}
		}	    
	});
	$("td[showTitle='true']",navTab.getCurrentPanel()).each(function(){
		var tagliba =$(this).find("a")[0];
		if(undefined == tagliba){
			var value = $(this).text().trim();
			if(value.length>18){
				$(this).text(value.substring(0,18)+"...");
			}
			$(this).attr("title",value);
		}else{
			var value = $(tagliba).text().trim();
			if(value.length>18){
				$(tagliba).text(value.substring(0,18)+"...");
			}
			$(tagliba).attr("title",value);
		}
		
	});
	if("${fn:length(page.results)}"==0&&"${showWarn}"!="false"){
		if($("div[class='dialog']").attr("style")==undefined){
			$("#exportTable",navTab.getCurrentPanel()).find("tbody").html("<tr><td colspan='50' align='center'>\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a</td></tr>");
			$("#exportExcelTable",navTab.getCurrentPanel()).find("tbody").html("<tr><td colspan='50' align='center'>\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a</td></tr>");
		}else{
			$("#exportTable",$.pdialog.getCurrent()).find("tbody").html("<tr><td colspan='50' align='center'>\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a</td></tr>");
			$("#exportExcelTable",$.pdialog.getCurrent()).find("tbody").html("<tr><td colspan='50' align='center'>\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a</td></tr>");
		}
			
	}
});
</script>
