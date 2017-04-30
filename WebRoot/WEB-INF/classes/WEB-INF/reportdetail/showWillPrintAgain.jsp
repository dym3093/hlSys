<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("input[name='dealType']").change(function(){
		var type = $("input[name='dealType']:checked").val();
		if(type=='0'){
			$("#needbatchno").show();
			$("#nobatchno").hide();
			$("#createPrint").hide();
		}
		if(type=='1'){
			$("#needbatchno").hide();
			$("#nobatchno").show();
			$("#createPrint").hide();
		}
		if(type=='2'){
			$("#needbatchno").hide();
			$("#nobatchno").hide();
			$("#createPrint").show();
		}
	});
	 py_ready();
});

function doWillPrintAgain1(){
	var batchno = $("#batchno",navTab.getCurrentPanel()).val();
	if(batchno==''){
		alert("批次号不能为空");
		return;
	}
	py_show();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/report/unMatch!toWillPrintBatchAgain.action",
		data: {"batchno":batchno},
		success: function(data){
			if (data.result=='success') {
				alertMsg.correct("更新打印数据成功！");
				$("#updata1").hide();
				$("#printBtn1").show();
				py_hide();
			} else if (data.result == 0) {
				alertMsg.warn("当前没有数据更新！");
				py_hide();
			} else {
				alertMsg.warn("更新数据失败！");
				py_hide();
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			py_hide();
		}
	});
}

function doWillPrintAgain2(){
	py_show();
	var state = $("#printType",navTab.getCurrentPanel()).val();
	$("#doWill2").attr("disabled","disabled");
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/report/unMatch!issueContentInfo2WillPrint.action?state="+state,
		success: function(data){
			//var res= eval("("+data+")");
			if(data.result=='success'){
				alertMsg.correct("更新打印数据成功！");
				//$("#updata2").hide();
				//$("#printBtn2").show();
				//$("#datares").text(res.datares);
				py_hide();
			}else{
				alertMsg.warn("当前没有数据更新！");
				py_hide();
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			py_hide();
		}
	});
}

function createWillPrintAgain(){
	//$("#createWill1").attr("disabled","disabled");
	$("#createWill2").attr("disabled","disabled");
	py_show();
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "${path}/report/unMatch!createWillPrintBatchAgain.action",
		success: function(data){
			//var res= eval("("+data+")");
			if(data.result=='success'){
				alertMsg.correct("打印批次生成成功！");
				py_hide();
			}else{
				alertMsg.warn("打印批次生成失败！");
				py_hide();
			}
		},
		error :function(data){
			alertMsg.error("服务发生异常，请稍后再试！");
			py_hide();
		}
	});
}
</script>
<!--蒙版 start-->
<div class="py_theMb" ><img class="py_loadpic"src="${path}/images/circle.jpg" alt="加载中……"/></div>
<!--蒙版 end-->
        <div class="tip"><span>手工生成打印批次</span></div>
        <div class="pageContent">
        <table class="pageFormContent">
          <tr>
          	<td>
          		<input type="radio" name="dealType" value="0" checked="checked"><span>需要批次号</span>
          		<!-- <input type="radio" name="dealType" value="1"><span>不需要批次号</span> -->
          		<input type="radio" name="dealType" value="2"><span>生成打印批次</span>
          	</td>
            <td></td>
          </tr>
         <tr id="needbatchno">
            <td>
            	<label>批次号：</label> 
				<input id="batchno" type="text" value=""/> 
            </td>
            <td>
            <div id="updata1" class="buttonActive">
                <div class="buttonContent">
                  <button id="doWill1" type="button" onclick="doWillPrintAgain1()">更新匹配数据</button>
                </div>
              </div></td>
            <!-- <td>
            <div id="printBtn1" class="buttonActive" style="display:none">
                <div class="buttonContent">
                  <button id="createWill1" type="button" onclick="createWillPrintAgain()">生成打印批次</button>
                </div>
              </div></td> -->
          </tr>
          <tr id="nobatchno" style="display:none">
          	<td>
            	<label>数据类型：</label>
            	<select id="printType" name="printType" style="float:none;margin:5px;">
                	<option value="1" selected="selected">全部数据</option>
                    <option value="2">异常数据</option>
                    <option value="3">未匹配数据</option>
                    <option value="4">会员信息多条</option>
                </select>
            </td>
            <td>
            <div id="updata2" class="buttonActive">
                <div class="buttonContent">
                  <button id="doWill2" type="button" onclick="doWillPrintAgain2()">更新匹配数据</button>
                </div>
              </div>
              </td>
          </tr>
          <tr id="createPrint" style="display:none;">
           <td></td>
           <td style="margin-left:10px;">
            <div id="printBtn2" class="buttonActive">
                <div class="buttonContent">
                  <button id="createWill2" type="button" onclick="createWillPrintAgain()">生成打印批次</button>
                </div>
              </div></td>
          </tr>
        </table>
      </div>
</div>
