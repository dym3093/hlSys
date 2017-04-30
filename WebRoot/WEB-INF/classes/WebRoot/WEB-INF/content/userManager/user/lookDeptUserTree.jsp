<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
	<script type="text/javascript">
	function drowTree(data){
        var tree = "<ul class='tree treeFolder'>";
        $.each(data, function(i, item) {
         	tree += "<li><a>"+item.name+"</a><ul><ul>";
         	tree += userStr(item.user,item.name);
         	tree += "</ul>";
         	$.each(item.dept, function(i, item) {
             	tree += "<li><a>"+item.name+"</a><ul><ul>";
             	tree += userStr(item.user,item.name);
             	tree += "</ul>";
             	$.each(item.dept, function(i, item) {
                 	tree += "<li><a>"+item.name+"</a>";
                 	tree += "<ul>";
                 	tree += userStr(item.user,item.name);
                 	tree += "</ul>";
                 	$.each(item.dept, function(i, item) {
                     	tree += "<li><a>"+item.name+"</a></li>";
                    });
                 	tree += "</li>";
                });
             	tree += "</li>";
            });
         	tree += "</li>";
        });
        return tree+"</ul>";
    }
    
    function userStr(user,name){
   		var  tree = "";
    	$.each(user, function(i, item) {
         	tree += "<li><a href=\"javascript:\" onclick=\"$.bringBack({linkMan:'"+item.name+"', jobNumber:'"+item.jobNumber+"',position:'"+name+"'})\">"+item.name+"</a></li>";
        });
        return tree;
    }
    
	</script>
  <div class="pageContent" >
	<div class="pageFormContent" layoutH="25" id="lookDeptUserTree">
	</div> 
	</div>
<script type="text/javascript">
jQuery(function($) {
    var data = <%=request.getAttribute("data")%>;
    $("#lookDeptUserTree").html(drowTree(data).replaceAll("<ul></ul>",""));
});
</script>	
	