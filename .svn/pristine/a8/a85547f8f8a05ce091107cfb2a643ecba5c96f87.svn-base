<%@ page language="java" contentType="text/html; charset=UTF-8; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.hpin.common.util.StrUtils"%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
 function uploadFile(){
		$.ajaxFileUpload({
			url:'${path}/resource/preSalesmanMgr!addSalesmanInfoBX.action',
			type:'post',
			secureuri: false,
			fileElementId: 'salesManExc',
			fileType:'fileType', 
			dataType: 'json',
			success:function(data,status){
				if(data.statusCode=="200"){
					$.pdialog.closeCurrent();
					alertMsg.info(data.message);
					return navTabSearch(this);
				}else{
					alertMsg.info(data.message);
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		}); 
	 }
 
 </script>
<div class="pageFormContent" id="import">
  <div> 
    <form method="post" action="${path}/resource/preSalesmanMgr!addSalesmanInfoBX.action" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data">
      <div class="formBar">
        <ul>
          <li style="width:450px">
            <span style="float:left;line-height:25px;">预留营销员信息表：</span>
            <input style="float:left" type="file" class="required valid" id="salesManExc" name="salesManExc">
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
