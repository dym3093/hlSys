<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function(){
	//导出加载等待问题;
	py_ready(2); //进度条显示初始化;
});

function uploadFile(){
	if($(":file",$.pdialog.getCurrent()).val()==""){
		alertMsg.info("请先选择上传的EXCEL文件");
		return false;
	};
	var isSubmit = $("#isSubmit",$.pdialog.getCurrent());
	if($(isSubmit).val()==1){
		alertMsg.warn("请勿重复提交");
		return ;
	}
	$(isSubmit).val(1);
	py_show();
	if($("#uploadType",$.pdialog.getCurrent()).val() == 0){
		$.ajaxFileUpload({
			url:"${path}/venueStaffSett/conferCostMan!updloadFile.action",
			type:'post',
			secureuri: false,
			fileElementId: 'file',
			dataType: 'json',
			success:function(data){
				py_hide();
				alertMsg.correct("成功导入【"+data.count3+"】条数据,重复【"+data.count2+"】条，【"+data.count+"】没有该场次");
				navTab.closeCurrentTab();
				navTab.openTab("listConferCostMan", "${path}/venueStaffSett/conferCostMan!listConferCostMan.action", 
						{ title:"会场费用结算", fresh:true, data:{} });
				$.pdialog.closeCurrent();
			},
			error:function (data,status,e){
				py_hide();
				alert("服务发生异常，请稍后再试！");
			}
		});
		
	} else {
		$.ajaxFileUpload({
			url:"${path}/venueStaffSett/conferCostMan!uploadDetailCost.action",
			type:'post',
			secureuri: false,
			fileElementId: 'file',
			dataType: 'json',
			success:function(data){
				py_hide();
				alertMsg.correct("成功导入【"+data.count1+"】条数据,【"+data.count2+"】条没有对应的费用名称,【"+data.count0+"】没有该场次");
				navTab.closeCurrentTab();
				navTab.openTab("listConferCostMan", "${path}/venueStaffSett/conferCostMan!listConferCostMan.action", 
						{ title:"会场费用结算", fresh:true, data:{} });
				$.pdialog.closeCurrent();
			},
			error:function (data,status,e){
				py_hide();
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
}

function closeDialog(){
	$.pdialog.closeCurrent();
}
</script>
</head>
<body>
	<div id="mc" class="py_theMb">
		<div class="py_bakpic"><!--图片--></div>
	</div>
	<div>
		<input id="uploadType" type = "hidden" value="${uploadType}"/> 
		<div  style="height: 410px; padding: 0px;">
	    	<div> <!-- class="divider" -->
	    		<input id="file" type="file" name="file" value=""/>
	    		<button type="button" onclick="uploadFile()">上传</button>
	    		<button type="button" onclick="closeDialog()">返回</button>
	    	</div>
		</div>
	</div>  
	<div>
		<input id="isSubmit" value="0"/>
	</div>


</body>
</html>