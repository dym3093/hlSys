<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
  <style type="text/css">
	   .suggest_out{background-color:#FFFFFF;color:black}
	   .suggest_over{background-color:#ECE9D8}
    </style> 
<script type="text/javascript">
	var deptId;
	var companyId;
function test1(code1){
	deptId=code1;	
	$("#name").val("");
}
function test2(code2){
	companyId=code2;
	
}

function goto2(){	
	var qurl="${path}/um/user!lookUpUserByDeptId.action?deptId="+deptId+"&companyId="+companyId;
	$('.btnLook').attr('href',qurl); 
	}
	
function searchbannyCompany(){
	
    var plc=$("#name").val();
    var url="${path}/resource/customerRelationShip!test.action";
    var pars="branchCommany="+plc+"&deptId="+deptId;
    $.ajax({
      async:true,
      cache:false,
      type:"POST",
      url:url,
      data:pars,
      success:success
    });
    function success(doc,state,concent){
    	
      var jsonData=concent.responseText;
      loadtitle(jsonData);
      
    }
  };
  function loadtitle(data)
  {
   if(!data)
   {
    return;
   }
   var json=eval("("+data+")");
   var title=json.storeApplicationBranchCommany;
   var length=title.length;
   if(length!=0)
   {
    $("#suggest").css('display',"block");	     
     var suggest="<ul>";
     for(var i=0;i<length;i++)
     {
      suggest+="<li style='margin:0;padding:3px;' onclick='getValue(this.innerHTML)' onkeydown='suggestOver(this)' onmouseover='suggestOver(this)' onmouseout='suggestOut(this)' class='suggest_out'>"
      +title[i].branchCommany+"</li>";
     }
     suggest+="</ul>";
     $("#suggest").html(suggest);
   }
   
  }
   function getValue(obj)
   {
      $("#name").val(obj);
      $("#suggest").css('display',"none");	  
   }
   
   function suggestOver(obj)
   {
    obj.className="suggest_over";
   }
   function suggestOut(obj)
   {
    obj.className="suggest_out";
   }

   //add by DengYouming 2016-07-11
   var toStoreProduceDetail = function(){
		//获取当前页面id
		var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
		$.pdialog.open("${path}/warehouse/storeApplication!toStoreProduceDetail.action?navTabId="+navTabId, "toStoreProduceDetail", "品类列表", 
				{width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
		
   };

   //删除行
   var delCurrentTr = function(me){
	 $(me).parents("tr").remove();  
	 if( $("#data_grid").find("tbody tr").length>0){
		 $("#data_grid").find("tbody tr").each(function(i){
			 $(this).children("td").eq(0).html(i+1);
			 var details = $(this).find("input[name*='detailList']");			 
			 for(var j=0; j<details.length; j++){
				 var attrName = $(details[j]).attr("name");
				 var numStr = attrName.charAt(attrName.indexOf("[")+1);
				 var attrNameNew = attrName.replace(numStr,i);
				 $(details[j]).attr("name", attrNameNew);
			 }
		 });
	 }else{
		 $("#applyInfo").css("display","none");
	 }
   };
   
   var contains = function(arr, obj){
		for(p in arr){
			if(arr[p]==obj) return true;
		}
		return false;
   };
   
   var addTr = function(data){  
		if(data!=null&&data!=""&&data!=undefined){
			$("#applyInfo").css("display","block");
			var seqNum = 0;
			var nameArr = [];
			if($("#data_grid").find("tbody tr")!=null){
				seqNum = $("#data_grid").find("tbody tr").length;
				$("#data_grid").find("tbody tr").each(function(){
					var prevName = $(this).children("td").eq(1).text();
					nameArr.push(prevName);
				});
			}			
			var count = 0;
			for(var i=0; i<data.length; i++){
				if(!contains(nameArr, data[i].name)){
					count++;
					$("#data_grid").find("tbody").append("<tr><td align='center'><input type='hidden' name='detailList["+(seqNum+count-1)+"].storeTypeId' value='"+data[i].id+"' />"+(seqNum+count)+"</td>"+
							"<td align='center'><input type='hidden' name='detailList["+(seqNum+count-1)+"].prdouceName' value='"+data[i].name+"' />"+data[i].name+"</td>"+
							"<td align='center'>"+data[i].num+"</td>"+
							"<td align='center'><input type='text' name='detailList["+(seqNum+count-1)+"].applyNum' value='"+data[i].num+"' />"+
												"<input type='hidden' name='detailList["+(seqNum+count-1)+"].typeBigCode' value='"+data[i].typeBigCode+"' />"+
												"<input type='hidden' name='detailList["+(seqNum+count-1)+"].standards' value='"+data[i].standards+"' />"+
												"<input type='hidden' name='detailList["+(seqNum+count-1)+"].cardStart' value='"+data[i].cardStart+"' />"+
												"<input type='hidden' name='detailList["+(seqNum+count-1)+"].cardEnd' value='"+data[i].cardEnd+"' />"+
												"<input type='hidden' name='detailList["+(seqNum+count-1)+"].cardCount' value='"+data[i].cardCount+"' /></td>"+
							"<td align='center'><input type='button' name='configId' value='删除' onclick='delCurrentTr(this);'/></td></tr>");
				}
			}
		}
   }; 
  
   var addOptionContent = function(me, selector){
	 var optText = $(me).children("option[selected='selected']").html().trim(); 
	 $(selector).val(optText);
   };
   
  
   var setNewAddress = function(){
	 var addr = "";
	 var addr_org = $("#addressOrg").val();	 
	 var province = $("#province").val();
	 var city = $("#city").val();
	 addr = province+city+addr_org;
	 $("#address").val(addr);
   };
   
   var check = function(formObj){
	   if($("#data_grid").find("tbody tr").length<1){
		   alertMsg.error("请选择品类！");
		   //return;
	   }else{
		   return validateCallback(formObj, navTabAjaxDone);
	   }
   };
		
	//根据项目编码自动获取项目名称和项目负责人
	var findProjInfo = function(){		
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path }/warehouse/storeApplication!getCrmBaseInfoByProCode.action",
		      data: {"proNum":$("[name='storeApplication.remark3']").val()},
		      success: function(data){ 
		    	  var json =  data.result;
		    	  $("[name='storeApplication.remark1']").val(json.projectOwner);
		    	  $("[name='storeApplication.remark2']").val(json.projectName);
		      	 
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
		      }    

		});
	};
	
	var testApple = function(){		
		$.ajax({
		      type: "post",
		      cache :false,
		      dataType: "json",
		      url: "${path }/warehouse/storeApplication!appleStoreApplication.action",
		      //data: {"proNum":$("[name='storeApplication.remark3']").val()},
		      success: function(data){ 
		    	alert('Success!');
		      },
		      error :function(XMLHttpRequest, textStatus, errorThrown){
		      }    

		});
	};
    
</script>
<div class="pageContent" style="overflow: hidden;">
<form id="pagerFindForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" style="overflow: hidden;"   
	action="${path}/warehouse/storeApplication!appleStoreApplication.action" method="post" >
    <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
    <input type = "hidden" name = "storeApplication.id" value="${storeApplication.id }" />
    <input name="storeApplication.status" type="hidden" value="0"/>
    <div class="pageFormContent" layoutH="56" style="overflow: hidden;">
        <div class="tip"><span>申请信息</span></div>
         <p>
          <label>总公司名称：</label>         
 		    <select class="required" id="ownedCompany" name="storeApplication.ownedCompanyId"  rel="iselect" 
	 		    loadUrl="${path}/um/dept!treeRegion.action?defaultID=40289b6a5206079d0152061530000007"  ref="name"  
	 		    refUrl="${path}/resource/customerRelationShip!treeRegion.action?defaultID="  onchange="addOptionContent(this, '#ownedCompany');">
	   		</select>
	   		<input id="ownedCompany" name="storeApplication.ownedCompany" type="hidden" value="${ storeApplication.ownedCompany }" />
        </p>
        <p>
          <label>支公司名称：</label>
          <select id="name" name="storeApplication.bannyCompanyId"  rel="iselect" class="required" onchange="addOptionContent(this, '#bannyCompanyName');">
   		  </select>
          <input id="bannyCompanyName" name="storeApplication.bannyCompanyName" type="hidden" value="${ storeApplication.bannyCompanyName }" />
        </p>
        <p>
            <label>项目编码：</label>
            <input name="storeApplication.remark3" type="text" class="required"   value="${ storeApplication.remark3 }"  onblur="findProjInfo()"/>
            <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
        </p>
        <p>
            <label>项目负责人：</label>
            <input name="storeApplication.remark1" type="text" class="required"   value="${ storeApplication.remark1 }"  readonly="readonly" style="background-color:#eee;"/>
            <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
        </p>
        <p>
            <label>项目名称：</label>
            <input name="storeApplication.remark2" type="text" class="required"   value="${ storeApplication.remark2 }"  readonly="readonly" style="background-color:#eee;"/>
            <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
        </p>
        <p>
          <label>收件人：</label>
          <input name="storeApplication.receiveName" type="text" class="required"   value="${ storeApplication.receiveName }" />
         <!-- <a class="btnLook"  href=""  onclick="goto()"  lookupGroup="storeApplication" width="300" height="500">查找带回</a> -->
        </p>
        <p>
          <label>联系电话：</label>
          <input name="storeApplication.receiveTel" type="text" class="required phone"    value="${ storeApplication.receiveTel }" />
        </p>
        <p>
       		<label>省份：</label>
			<select id="provinceId" class="required" name="storeApplication.provinceId" rel="iselect" loadUrl="${path}/system/region!treeRegion.action"
						ref="cityId" refUrl="${path}/system/region!treeRegionDispose.action?parentId=" onchange="addOptionContent(this, '#province');">
			</select>
			<input id="province" type="hidden" name="storeApplication.province" />
		</p>
		<p>
			<label>城市：</label>
			<select id="cityId" class="required" name="storeApplication.cityId" rel="iselect" onchange="addOptionContent(this, '#city');">
			</select>
			<input id="city" type="hidden" name="storeApplication.city" />
    	</p>
        <p>
          <label>详细地址:</label>
          <input id="addressOrg" name="addressOrg" type="text" class="required" onblur="setNewAddress();"/>
          <input id="address" name="storeApplication.address" type="hidden" />
       <!--    <a class="btnLook"  href="${path}/warehouse/storeApplication!lookUpStoreApplication.action"   lookupGroup="storeApplication" style="float: left;">查找带回</a>  -->
        </p>
       
        <p>
        	 <label>期望日期：</label>
          <input type="text" name="storeApplication.useDate" class="required" style="width: 170px;" onFocus="WdatePicker({minDate:'${useDate}'})"  readonly="readonly" value="${useDate}"/>
          <a class="inputDateButton" href="javascript:;">选择</a>
         
        </p>        
      
        <p class="nowrap"><label style="height: 60px;">需求说明：</label>
           <textarea cols="35" ows="2" style="width:570px;" class="required" name="storeApplication.requires">${storeApplication.requires}</textarea>
        
        </p>
        <p>
		   <label style="height: auto">派往角色：</label>
		   <input name="storeApplication.businessId" type="hidden" value="40289b6a5206079d01520619b31e0008"/>
		   <input name="storeApplication.businessName" type="hidden" value="远盟本部"/>
		   <span style="color:red">远盟基因业务部</span>
		 </p>
      	<br/>
        <p>
           <label>选择品类：</label>
           <input type="button" name="configId" value="选择" onclick="toStoreProduceDetail();" />
      	</p>
      	<br/>
      	<p></p><p></p>
      	<p>
	    	<fieldset id="applyInfo" style="width:100%; display:none;">
				<legend>【<span id="status_text" style="color:#ff0000;">申请品类信息明细</span>】</legend>
		  	    <table id="data_grid" class="list">
			  	    <thead>
						<tr>
							<th width="60">序号</th>
							<th width="300" columnEnName="prdouceName"  columnChName="品类名称" >品类名称</th>
							<th width="200" columnEnName="num"  columnChName="库存数量" >库存数量</th>
							<th width="200" columnEnName="applyNum" columnChName="申请数量" >申请数量</th>
							<th width="100" columnEnName="operation" columnChName="操作" name="configId">操作</th>
						</tr>					
					</thead>
					<tbody>
					</tbody>
		   		</table>
	   		</fieldset>
		</p>
    </div>
    
    <div class="formBar" style="width:98%">
    	<ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
            	<!-- <input type="button" value="申请提交" onclick="saveStoreApplication()"/> -->
              <button type="submit">提交申请</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button id="clearText" type="reset" >重置</button>
            </div>
          </div>
        </li>
        </ul>
    </div>
    
  </form>
  
</div>