<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/daterangepicker/js/jquery-ui-1.7.1.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/daterangepicker/js/daterangepicker.jQuery.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/daterangepicker/css/ui.daterangepicker.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/daterangepicker/css/redmond/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
<script type="text/javascript">	
$(document).ready(function(){
	$('input[rel=doubledate]').daterangepicker();
});
</script>
