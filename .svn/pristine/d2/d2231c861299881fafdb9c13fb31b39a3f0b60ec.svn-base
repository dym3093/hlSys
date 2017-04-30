<%@ page language="java" contentType="text/html; charset=UTF-8; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.hpin.common.util.StrUtils"%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
 function downexcel(fileName){
	window.location = "${path}/resource/downLoadExcel.action?filename="+fileName;
  }
 
 function uploadFile(){
		var time =  $("input[name='importTime']").val();
		$.ajaxFileUpload({
			url:'${path}/physical/phyReport!uploadReportInfo.action',
			type:'post',
			secureuri: false,
			fileElementId: 'affixExcel',
			data:{"time":time},
			dataType: 'json',
			success:function(data,status){
				if(data.statusCode=="200"){
					alert(data.message);
					$.pdialog.closeCurrent();
					/* $("input[name='setMealNum']").val(data.setMealNum);
					$("input[name='totalPersonNum']").val(data.totalPersonNum);
					$("input[name='successNum']").val(data.successNum);
					$("input[name='abnormalNum']").val(data.abnormalNum); */
				}else{
					alert(data.message);
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
    <form method="post" action="${path}/settlementManagement/erpSettlementTaskJY!uploadJYCheckInfo.action" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data">
    <input type="hidden" name="" value=""/>
      <input name="navTabId" type="hidden" value="${navTabId}"/>
      <div class="formBar">
        <ul>
          <li>
          	<span style="float:left;line-height:25px;">导入日期：</span> 
					<input type="text" name="importTime" style="width: 120px;" 
					class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly" value="${importTime}" onclick="gb()"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
          </li>
          <li style="width:400px">
            <span style="float:left;line-height:25px;">基因检测客户信息导入：</span>
            <input style="float:left" type="file" class="required valid" id="affixExcel" name="affixExcel">
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
