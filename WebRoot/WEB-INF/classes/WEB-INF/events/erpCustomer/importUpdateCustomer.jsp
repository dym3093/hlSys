<%@ page language="java" contentType="text/html; charset=UTF-8; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.hpin.common.util.StrUtils"%>
<script type="text/javascript" language="javascript">
 function downexcel(fileName){
	window.location = "${path}/resource/downLoadExcel.action?filename="+fileName;
  }
 
 function uploadFile(){
		$.ajaxFileUpload({
			url:'${path}/events/erpCustomerUpdate!uploadCustomerInfo.action',
			type:'post',
			secureuri: false,
			fileElementId: 'affixExcel',
			dataType: 'json',
			success:function(data,status){
				if(data.statusCode=="200"){
					alert(data.message);
					//$.pdialog.closeCurrent();
				}else{
					alert(data.message);
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		});
	 }
 
 function upload(){
	 $.ajax({
			url:'${path}/events/erpCustomerUpdate!uploadCustomerInfo.action',
			type:'post',
			success:function(data,status){
				if(data.statusCode=="200"){
					alert(data.message);
					//$.pdialog.closeCurrent();
				}else{
					alert(data.message);
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		});
 }
 
 function uploadPdf(){
	 $.ajax({
			url:'${path}/events/erpCustomerUpdate!uploadCustomerPdf.action',
			type:'post',
			success:function(data,status){
				if(data.statusCode=="200"){
					alert(data.message);
					//$.pdialog.closeCurrent();
				}else{
					alert(data.message);
				}
			},
			error:function (data,status,e){
				alert("服务发生异常，请稍后再试！");
			}
		});
 }
 function uploadExcel(){
	 $.ajax({
			url:'${path}/events/erpCustomerUpdate!readExcelPath.action',
			type:'post',
			success:function(data,status){
				if(data.statusCode=="200"){
					alert(data.message);
					//$.pdialog.closeCurrent();
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
	<div class="tip"><span>查找Excel修改客户信息</span></div>
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="button" id="sc" onclick="upload()">修改客户信息Excel</button>
		</div>
	</div>
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="button" id="sc" onclick="uploadPdf()">修改客户信息PDF</button>
		</div>
	</div>
	
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="button" id="sc" onclick="uploadExcel()">修改客户信息指定的Excel</button>
		</div>
	</div>
  </div>
  