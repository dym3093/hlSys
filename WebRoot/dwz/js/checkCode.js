$(document).ready(function(){
	$("#checkCode",navTab.getCurrentPanel()).blur(function(){
		var code = $(this).val();
		$.ajax({
	      type: "post",
	      cache :false,
	      dataType: "json",
	      url: "../common/ajaxCheckCode!checkCode.action",
	      data: {"beanName":$(this).attr("beanName"),"propertyName":$(this).attr("propertyName"),"propertyValue":code},
	      success: function(data){ 
	      	 $.each(data,function(name,value){ 
	        	 if(value==true){
	           		alert("编码已存在");
	           		$("#checkCode",navTab.getCurrentPanel()).val("");
	           		$("#checkCode",navTab.getCurrentPanel()).focus();
	        	 }
	        });
	      },
	      error :function(XMLHttpRequest, textStatus, errorThrown){
	      }    
		 });
	});
	/** 医院名称排重*/
	$("#checkhisName",navTab.getCurrentPanel()).blur(function(){
		var hisName = $(this).val();
		$.ajax({
	      type: "post",
	      cache :false,
	      dataType: "json",
	      url: "../common/ajaxCheckCode!checkNameByHis.action",
	      data: {"beanName":$(this).attr("beanName"),"propertyName":$(this).attr("propertyName"),"propertyValue":hisName},
	      success: function(data){ 
	      	 $.each(data,function(name,value){ 
	        	 if(value==true){
	           		alert("医院名称已存在");
	           		$("#checkhisName",navTab.getCurrentPanel()).val("");
	           		$("#checkhisName",navTab.getCurrentPanel()).focus();
	        	 }
	        });
	      },
	      error :function(XMLHttpRequest, textStatus, errorThrown){
	      }    
		 });
	});
});
