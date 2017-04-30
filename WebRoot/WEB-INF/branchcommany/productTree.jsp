<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#productTree",$.pdialog.getCurrent()).change(function(){
		if($(this).val()==1){
			$("#name",$.pdialog.getCurrent()).attr("name","productName");
		}else{
			$("#name",$.pdialog.getCurrent()).attr("name","customerName");
		}
	});
	
	$(".ckbox").live("click", function() {
		alert(123);
	});
});

/*
 * modified by henry.xu 20160817;
 */
function selProducts() {
	var oId = $("#orgNatureId").val();
	var oName = $("#orgNatureNameId").val();
	//当未选中数据提示请选择;否则退出界面保存值;
	if (oId == '') {
		alert('请选择产品！');
		return;
	}else {
		
		$("input[id='morecpName']",navTab.getCurrentPanel()).val(oName); //设置names;
		$("input[id='morecpNameId']",navTab.getCurrentPanel()).val(oName); //设置names;
		$("input[id='morecpId']",navTab.getCurrentPanel()).val(oId); //设置ids
		
		//处理新增几面中待会套餐,回显问题 ; create by henry.xu 20160816
		var $aComboId = $("#a_combo_id");
		var href = $aComboId.attr("href");
		//当该对象存在时;
		if(href) {
			var arrstr = href.split("?"); //分组数据;取[0]
			//重新赋值给href跳转;
			$aComboId.attr("href", arrstr[0]+
					"?orgNature=" + $("input[id='morecpId']",navTab.getCurrentPanel()).val());
					
		}
		
		//close当前弹出框;
		$.pdialog.closeCurrent();
	}
}
  
/*
 * tree的回调函数;
 * 用于每次处理选中或者取消选中数据;
 * create by henry.xu 20160817
 */
function checkboxMethod(json) {
	var checkFlag = json.checked; //判断该复选框是否选中;
	var oldId = $("#orgNatureId").val(); //已存在的id
	var oldName = $("#orgNatureNameId").val(); //以前存在的name;
	var divs = null;  //存放选中未选中集合变量;
		
	//如果选中执行隐藏域添加;否则执行取消;
	if(checkFlag) {
		divs = $("#productTrees div:[class$='ckbox checked'] input[type='checkbox']");
		divs.each(function(i,n){
			var val = n.value;
			//当选中值不为空时
			if(val != null && val != "") {
				var valArr = val.split(','); //拆分为数组
				//当oldId存在值时执行添加,否则赋值;
				if(oldId != null  && oldId != "" && valArr.length > 0) {
					if(oldId.indexOf(valArr[0]) < 0 ) {
						oldId   += "," + valArr[0];
						oldName += "," + valArr[1];
					}
				} else if(valArr.length > 0) {
					oldId   = valArr[0];
					oldName = valArr[1];
				}
			}
		});
		//将拼接好的数据存放在隐藏域中;
		$("#orgNatureId").val(oldId);
		$("#orgNatureNameId").val(oldName);
	} else { //remove已存在的数据; 不能重新遍历获取数据,因为要保留原来选中数据;
		var newArr = [];
		var newNameArr = [];
		divs = $("#productTrees div:[class$='ckbox unchecked'] input[type='checkbox']");
		divs.each(function(i,n){
			var val = n.value;
			//当选中值不为空时
			if(val != null && val != "") {
				var valArr = val.split(',');
				//当oldId存在值时执行添加,否则赋值;
				if(oldId != null && valArr.length > 0 && oldId.indexOf(valArr[0]) >= 0) {
					//id简单的删除数据量;
					var oldArr = oldId.split(',');
					var len = oldArr.length;
					for(var i=0; i<len; i++) { 
						if(oldArr[i] != valArr[0]) {
							newArr.push(oldArr[i]);
						}
					}
					//name简单的删除数据量;
					var oldNameArr = oldName.split(',');
					len = oldNameArr.length;
					for(var i=0; i<len; i++) {
						if(oldNameArr[i] != valArr[1]) {
							newNameArr.push(oldNameArr[i]);
						}
					}
					//将数组join为字符串并存放在隐藏域中;
					$("#orgNatureId").val(newArr.join(','));
					$("#orgNatureNameId").val(newNameArr.join(','));
				} 
			}
		});
	}
}

</script>


   <%--  <div class="pageHeader">
	<form onsubmit="return dwzSearch(this, 'dialog');" action="${path }/resource/product!productTree.action"  method="post" rel = "pagerForm">
		<input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId }" />
		<table class="pageFormContent">
			<tr>
				<td>
					<label style="width: auto;">
						
					
					
					<select name="type" id="productTree" style="width: 100px; margin-top: 5px;">
						<option value="1" <c:if test="${ type==1 }">selected="selected"</c:if>>产品名称</option>
						<option value="2" <c:if test="${ type==2 }">selected="selected"</c:if>>客户名称</option>
					</select> ：
					</label>
					<input id="name" name="${ type==2 ? 'customerName' : 'productName' }"  value="${ name }">
				</td>
				<td>
				<div class="buttonActive"><div class="buttonContent"><button type="submit"">查找</button></div></div>
				</td>
			</tr>
		</table>
	</form>
</div> --%>
<form action="${path }/resource/customerRelationShip!productTree.action" onsubmit="return dialogSearch(this);" method="post" >
<input type="hidden" id="orgNatureId" name="orgNature" value="${orgNature }"/>
<input type="hidden" id="orgNatureNameId" name="orgNatureName" value="${orgNatureName }"/>
<div class="pageHeader">
	<div class="searchBar">
		<table class="pageFormContent" style="border-width: 0;">
			<tr>
				<td align="right">
					套餐名称：
				</td>
				<td align="left">
					<input name="comboName" type="text" value="${comboName }"/>
				</td>
				<td align="right">
					项目类别：
				</td>
				<td align="left">
					<select name="projectTypes" >
						<option value="">--全部--</option>
						<option value="PCT_001" <c:if test ="${projectTypes=='PCT_001' }"> selected="selected"</c:if> >基因项目</option>
						<option value="PCT_002" <c:if test ="${projectTypes=='PCT_002' }"> selected="selected"</c:if> >癌筛项目</option>
					</select>
				</td>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">查询</button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="reset">重置</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
  <div class="pageContent">
  <input type = "hidden" name = "navTabId" id = "navTabId" value="${navTabId}" />
	<div class="pageFormContent" layoutH="58" style="height: 330px;">
	  <ul id="productTrees" class="tree treeFolder treeCheck expand" oncheck="checkboxMethod">
	  <c:forEach  items="1212" var="customer5" varStatus="status">
		<li><a href="javascript:" tvalue=""><span>ALL</span></a>
		
	      <ul>
	        <c:forEach items="${customerList}" var="customer" varStatus="status">	   
	            <c:set var = "isChecked" value="0"></c:set>       
	            <c:forEach items="${orgNature }" var = "type" varStatus="statusType">	
			          	<c:if test="${type == customer.id}">
			          		<c:set var = "isChecked" value="1"></c:set>
			          	</c:if>
			         </c:forEach>
			    <li ><a href="javascript:" tname="customerName" tvalue="${customer.id},${customer.comboName}" <c:if test = "${isChecked == 1 }">checked</c:if>><span>${customer.comboName} .........${ fn:substring(customer.productName , 0 , 35 )}</span></a></li>	                   	          
	        </c:forEach>
	      </ul>
		</li>
	    </c:forEach>
	 
	  </ul>
    </div> 
    <div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="javascript:selProducts();">确认</button>
					</div>
				</div>
			</li>
		</ul>
    </div>
</div>