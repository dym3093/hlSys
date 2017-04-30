<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String userRoles = (String)request.getAttribute("userRoles");
String userId = (String)request.getAttribute("userId");
%>
<script src="${path}/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	$("#modifyType").change(function(){
		var modifyType=$("#modifyType").val();
		if(modifyType=="1"){
			$("input[name='cususername']").removeAttr("disabled");
			$("input[name='cususerage']").removeAttr("disabled");
			$("#cususersex").removeAttr("disabled");
			$("input[name='pdfusername']").attr("disabled","disabled");
			$("input[name='pdfuserage']").attr("disabled","disabled");
			$("#pdfusersex").attr("disabled","disabled");
			$("#pdffile").attr("disabled","disabled");
			
		}
		if(modifyType=="2"){
			$("input[name='cususername']").attr("disabled","disabled");
			$("input[name='cususerage']").attr("disabled","disabled");
			$("#cususersex").attr("disabled","disabled");
			$("input[name='pdfusername']").removeAttr("disabled");
			$("input[name='pdfuserage']").removeAttr("disabled");
			$("#pdfusersex").removeAttr("disabled");
			$("#pdffile").removeAttr("disabled");
		}
	});
});
function modifyInfo(){
	var unmatchid=$("input[name='unmatchid']").val();
	var modifyType=$("#modifyType").val();
	var datas;
	var modifyname;
	var modifysex;
	var modifyage;
	var tomodifyid;
	if(modifyType==""){
		alert("请选择需要修改的信息");
		$("#modifyType").focus();
		return;
	}
	if(modifyType=="1"){
		modifyname=$("input[name='cususername']").val();
		modifysex=$("#cususersex").val();
		modifyage=$("input[name='cususerage']").val();
		tomodifyid=$("input[name='cusid']").val();
		if(modifyname==""){
			alert("姓名不能为空！");
			$("input[name='cususername']").focus();
			return;
		}
		if(modifysex==""){
			alert("性别不能为空！");
			$("input[name='cususersex']").focus();
			return;
		}
		if(modifyage==""){
			alert("年龄不能为空！");
			$("input[name='cususerage']").focus();
			return;
		}
		$.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "${path}/report/unMatch!updateInfo.action",
			data: {"id":unmatchid,"modifyType":modifyType,"modifyname":modifyname,"modifysex":modifysex,"modifyage":modifyage,"tomodifyid":tomodifyid},
			success: function(data){
				if(data.result=="success"){
					alert("更改成功！");
					$.pdialog.closeCurrent();
					return navTabSearch(this);
					//navTab.closeCurrentTab();
					//return navTabSearch(this);
					
				}else{
					alert("更改失败！");
				}	
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
	}
	if(modifyType=="2"){
		var isupload = $("input[name='isUpload']:checked").val();
		if($("#pdffile").val().length > 0) {
        	fileUpload();
        }else{
			if(isupload==0){
				fileUpload();
			}else{
				alert("请上传文件");
				return;
			}
		}
	}
};

function fileUpload(){
	var unmatchid=$("input[name='unmatchid']").val();
	var modifyname=$("input[name='pdfusername']").val();
	var modifysex=$("#pdfusersex").val();
	var modifyage=$("input[name='pdfuserage']").val();
	var pdfid = $("input[name='pdfid']").val();
	var isupload = $("input[name='isUpload']:checked").val();
	if(modifyname==""){
		alert("姓名不能为空！");
		$("input[name='pdfusername']").focus();
		return;
	}
	if(modifysex==""){
		alert("性别不能为空！");
		$("input[name='pdfusersex']").focus();
		return;
	}
	if(modifyage==""){
		alert("年龄不能为空！");
		$("input[name='pdfuserage']").focus();
		return;
	}
	$.ajaxFileUpload({
		url:'${path}/report/unMatch!updatePdfInfo.action',
        type:'post',
        data:{"id":unmatchid,"modifyType":modifyType,"modifyname":modifyname,"modifysex":modifysex,"modifyage":modifyage,'tomodifyid':pdfid,"isupload":isupload},
        secureuri: false,
        fileElementId: 'pdffile',
        dataType: 'json',
		success:function(data,status){
			if(data.result=="success"){
				alert("更改成功！");
				$.pdialog.closeCurrent();
				return navTabSearch(this);
			}else{
				alert("更改失败！");
			}
		},
		error:function (data,status,e){
        	alert("服务发生异常，请稍后再试！");
        }	
	});
};
</script>

<div class="pageFormContent" layoutH="50" >
  <div class="pageContent">
  <div class="pageHeader">
    <div class="searchBar">
      <div class="tip"><span>更改信息</span></div>
      <table class="pageFormContent">
      	<tr>
        	<td colspan="7"><label style="width:80px">修改类型</label>
            	<select id="modifyType" style="margin-left:10px;margin-top:5px;">
        			<option value="">请选择</option>
        			<option value="1">会员信息</option>
            		<option value="2">PDF信息</option>
        		</select>
            </td>
        </tr>
        <tr id="customer" style="background-color:#F8F8F8">
          <td>会员信息</td>
          <td style="color:#F00">${unmatchbean.cususername}</td>
          <td style="color:#F00">${unmatchbean.cususersex}</td>
          <td style="color:#F00">${unmatchbean.cususerage}</td>
          <td>更改为</td>
          <td><input style="width:80px !important" type="text" name="cususername" value="姓名" onfocus="this.value=''" onblur="if(this.value==''){this.value='姓名'}"></td>
          <td>
          	<select id="cususersex" name="cususersex" style="width:45px;" >
            	<option value="男">男</option>
                <option value="女">女</option>
            </select>
          </td>
          <td><input style="width:80px !important" type="text" name="cususerage" value="年龄" onfocus="this.value=''" onblur="if(this.value==''){this.value='年龄'}"></td>
        </tr>
        <tr id="pdf">
          <td>PDF信息</td>
          <td style="color:#F00">${unmatchbean.pdfusername}</td>
          <td style="color:#F00">${unmatchbean.pdfusersex}</td>
          <td style="color:#F00">${unmatchbean.pdfuserage}</td>
          <td>更改为</td>
          <td><input style="width:80px !important" type="text" name="pdfusername" value="姓名" onfocus="this.value=''" onblur="if(this.value==''){this.value='姓名'}"></td>
          <td>
          	<select id="pdfusersex" name="pdfusersex" style="width:45px;">
            	<option value="男">男</option>
                <option value="女">女</option>
            </select>
		  </td>
          <td><input style="width:80px !important" type="text" name="pdfuserage" value="年龄" onfocus="this.value=''" onblur="if(this.value==''){this.value='年龄'}"></td>
        </tr>
        <tr id="upFile" style="background-color:#F8F8F8">
        	<td colspan="2">上传PDF文件</td>
            <td colspan="6"><input type="file" id="pdffile" name="pdffile"></td>
        </tr>
        <tr>
        	<td><input type="radio" name="isUpload" value="0">已上传文件</td>
            <td><input type="radio" name="isUpload" value="1">未上传文件</td>
        </tr>
        <tr>
        	<td colspan="7"></td>
            <td>
            	<div class="buttonActive">
        			<div class="buttonContent">
          				<button onclick="modifyInfo()">提交</button>
        			</div>
      			</div>
            </td>
        </tr>
      </table>
      <input type="hidden" name="pdfid" value="${unmatchbean.pdfid}">
      <input type="hidden" name="cusid" value="${unmatchbean.cusid}">
      <input type="hidden" name="unmatchid" value="${unmatchbean.id}">
    </div>
  </div>
</div>
