<%@ page language="java" contentType="text/html; charset=UTF-8; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.hpin.common.util.StrUtils"%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	//导出加载等待问题;
	readdialog(1); //进度条显示初始化;
});

 function downexcel(fileName){
	window.location = "${path}/resource/downLoadExcel.action?filename="+fileName;
  }
 
 function uploadFile(){
	 	var value = $("input[name='affix']").val();
		var id = $("input[name='id']").val();
		$.ajaxFileUpload({
			url:'${path}/settlementManagement/erpReceiptInfo!importReceiptInfoExcel.action',
			type:'post',
			data:{"id":id,"filePath":value},
			secureuri: false,
			fileElementId: 'affix',
			fileType:'fileType', 
			dataType: 'json',
			success:function(data,status){
				if(data.statusCode=="200"){
					alertMsg.correct(data.message);
					$.pdialog.closeCurrent();
					return navTabSearch(this);
				}
				if(data.statusCode=="300"){
					alertMsg.error(data.message);
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		}); 
	 }
 
 </script>


<!--蒙版 start-->
<!--必须要的部分-->
    <div class="py_theMb"><!--蒙版-->
        <div class="py_bakpic"><!--图片-->
    </div>
    </div>
    <!--必须要的部分-->
<!--蒙版 end-->
<div class="pageFormContent" id="import">
  <div> 
    <form method="post" action="${path}/settlementManagement/erpReceiptInfo!toImportReceiptInfoExcel.action" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data">
      <!-- 打印任务ID -->
      <input name="id" type="hidden" value="${id}"/>
      <input name="navTabId" type="hidden" value="${navTabId}"/>
      <div class="formBar">
        <ul>
          <li style="width:450px">
            <span style="float:left;line-height:25px;">回款数据表：</span>
            <input style="float:left" type="file" class="required valid" id="affix" name="affix">
            <div class="buttonActive" style="margin-left:10px;">
              <div class="buttonContent">
                <button type="button" id="sc" onclick="uploadFile()">上传</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </form>
  </div>
</div>
