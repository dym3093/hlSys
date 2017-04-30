<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<div class="pageHeader">
	<form
		id="pagerFindForm" 
		action="${path }/statistics/queryaction!queryReport.action" 
		method="post" 
		rel="pagerForm" 
		onsubmit="this.action='${path }/statistics/queryaction!queryReport.action'; return navTabSearch(this); ">
        <input type="hidden" name="configId" value="${actionMap.configId }" />
	
	<div class="searchBar">
	  <table class="pageFormContent num1_tab">
		<tr>
            <td><label>月份：</label></td>
      		<td><input name="filter_and_month_like_S"  id="mo" value="${actionMap.filter_and_month_like_S }"  placeholder="月份格式如201702" class="textInput"></td>
      		<td></td>
      		<td></td>
      		<td>
		        <div style="width:100px;" class="subBar">	
		            <ul>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >查找</button></div></div></li>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="clearText">重置</button></div></div></li>
					</ul>
				</div>
		  	</td>
		</tr>
	  
	  </table>
	</div>
	</form>
</div>
  

<div class="tabs">
	<div style="margin-bottom: 0px;"class="tip">
		<span>非会场费用</span>
	</div>
    <div class="tabsContent" style="background-color:#FFF">
	<div style="width:100%">
		<div id="jbsxBox" class="unitBox">
			<div class="pageContent">
				<div class="panelBar">
					<jsp:include page="/common/pagination.jsp" />
					 <ul class="toolBar">
					<c:if test="${page.results != null && !empty page.results }">
						<web:exportExcelTag4Jdbc 
										pagerFormId="pagerFindForm" 
										pagerMethodName="queryResults" 
										actionAllUrl="org.hpin.statistics.briefness.web.QueryAction" 
										informationTableId="exportTable"
										fileName="stat142"
										configId="stat142"></web:exportExcelTag4Jdbc>
					</c:if>
					</ul>
				</div>					
				<table class="list" width="100%" layoutH="130" id = "exportTable"> 
				   <thead>
					<tr>
					    <th>序号</th>   
					    <th nowrap="nowrap" export = "true" columnIndex = "0" columnChName = "月份">月份</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "1" columnChName = "项目编码">项目编码</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "2" columnChName = "项目负责人">项目负责人</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "3" columnChName = "项目名称">项目名称</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "4" columnChName = "项目类型">项目类型</th>
					    <th nowrap="nowrap" export = "true" columnIndex = "5" columnChName = "非会场费用">非会场费用</th>				    
					</tr>
				  </thead>
				  <tbody>
					
					<c:forEach items="${page.results }" var="members" varStatus="status">
					  <tr>
						 <td align="center">
							${status.count }
						</td> 
						<td align="center">  ${members[0]}</td>
						<td align="center">  ${members[1]}</td>
						<td align="center">  ${members[2]}</td>
						<td align="center">  ${members[3]}</td>
						<td align="center">  ${members[4]}</td>
						<td align="center">  ${members[5]}</td>
					  </tr>
					</c:forEach>
					</tbody>
				  </table>
			  </div>
		</div>
	</div>
  </div>
</div>
<!-- <script>
$('input').each(function() {
		Date.prototype.format = function(format) { 
       var date = { 
              "M+": this.getMonth() + 1, 
              "d+": this.getDate(), 
              "h+": this.getHours(), 
              "m+": this.getMinutes(), 
              "s+": this.getSeconds(), 
              "q+": Math.floor((this.getMonth() + 3) / 3), 
              "S+": this.getMilliseconds() 
       }; 
       if (/(y+)/i.test(format)) { 
              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)); 
       } 
       for (var k in date) { 
              if (new RegExp("(" + k + ")").test(format)) { 
                     format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length)); 
              } 
       } 
       return format; 
}  
        var default_value = new Date().format('yyyyMM');
        var a=document.getElementById("mo").value
        if(a==''||a==null){
        document.getElementById("mo").value=default_value;  
        }else{
        document.getElementById("mo").value=a;
        }
        $(this).focus(function(){  
            if(this.value!='') {  
                this.value = '';  
            }  
        }); 
        $(this).blur(function(){  
            if(this.value == '') {  
                this.value = a;  
            }  
        });  
  
    });
</script> -->