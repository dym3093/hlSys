<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
.press {
	display:block;
	line-height:1.5em;
	overflow:visible;
	font-size:22px;
	text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0;
	text-align:center;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	 //$(".idno",navTab.getCurrentPanel()).addClass("required");
	 
	 // 因用户反映bug : 会场管理-》客户信息 修改客户信息后不能自动刷新
	 // 把此种获取navTabId方式注释，直接从后台获取    modify by DengYouming 2016-05-27
/* 	 var navTabId = navTab._getTabs().filter('.selected').attr('tabid');
	 $("input[name='navTabId']").val(navTabId); */  
});

var idtype;
$("#documentType",navTab.getCurrentPanel()).change(function(){
		 idtype= $(this).val();
		if(idtype=='1010101'){
		   $(".idno",navTab.getCurrentPanel());
		   $(".idno").val('');
		   $(".idno").focus();
		}else{
			$(".idno",navTab.getCurrentPanel()); 
		}
	  });
	  
	  
function validatemobile()
   {
   	var mobile=$(".phone").val(); 
   	if(mobile!='')  {    
       if(mobile.length!=11)
       {
           alert('手机号码位数错误！');
           $(".phone").val('');
		        $(".phone").focus();
           return ;
       }
       
       var myreg = /^(((13[0-9]{1})|150|151|152|153|155|156|157|158|159|177|180|181|182|183|185|186|187|188|189)+\d{8})$/;
       if(!myreg.test(mobile))
       {
           alert('请输入有效的手机号码！');
           $(".phone").val('');
		        $(".phone").focus();
           return ;
       }
     }
   }
 function IdentityCodeValid(code) {
           var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
           var tip = "";
           var pass= true;
           
           if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i){
               tip = "身份证号格式错误";
               pass = false;
           }
           
          else if(!city[code.substr(0,2)]){
               tip = "地址编码错误";
               pass = false;
           }
           else{
               //18位身份证需要验证最后一位校验位
               if(code.length == 18){
                   code = code.split('');
                   //∑(ai×Wi)(mod 11)
                   //加权因子
                   var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                   //校验位
                   var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                   var sum = 0;
                   var ai = 0;
                   var wi = 0;
                   for (var i = 0; i < 17; i++)
                   {
                       ai = code[i];
                       wi = factor[i];
                       sum += ai * wi;
                   }
                   var last = parity[sum % 11];
                   if(parity[sum % 11] != code[17]){
                       tip = "校验位错误";
                       pass =false;
                   }
               }
           }
           if(!pass) alert(tip);
           return pass;
       }
	//验证台湾的身份证  -- 10位
	function checkTaiwan(code){
		var city = {
				A :"台北市10", 
				B :"台中市11", 
				C :"基隆市12",
				D :"台南市13", 
				E :"高雄市14", 
				F :"台北县15", 
				G :"宜兰县16", 
				H :"桃园县17 ",
				I :"嘉义市34", 
				J :"新竹县18", 
				K :"苗栗县19", 
				L :"台中县20", 
				M :"南投县21", 
				N :"彰化县22", 
				O :"新竹市35", 
				P :"云林县23", 
				Q :"嘉义县24", 
				R :"台南县25", 
				S :"高雄县26", 
				T :"屏东县27", 
				U :"花莲县28", 
				V :"台东县29", 
				W :"金门县30", 
				X :"澎湖县31", 
				Y :"阳明山32", 
				Z :"连江县33", 
			};
			var tip = "";
			var pass = true;

			if (!code
					|| !/^[A-Z]\d{9}$/i) {
				tip = "身份证号格式错误";
				pass = false;
			} else {
				//10位身份证需要验证最后一位校验位
				var x = city[code.substring(0,1)].substring(3,5);
				code = x + code.substring(1,10);
				code = code.split('');
				//∑(ai×Wi)(mod 11)
				//加权因子
				var factor = [1,9,8,7,6,5,4,3,2,1];
				var sum = 0;
				var ai = 0;
				var wi = 0;
				for (var i = 0; i < 10; i++) {
					ai = code[i];
					wi = factor[i];
					sum += ai * wi;
				}
				var last = 10 - (sum % 10);
				if (last != code[10]) {
					tip = "校验位错误";
					pass = false;
				}
			}
			if (!pass)
				alert(tip);
			return pass;
	}
function strSum(str){
	 var sum = 0;
	 var size=str.length;
	 //substr
	 for (var i=0; i<size; i++)
 {
    sum=sum+parseInt(str.substr(i,1));
 }
  return sum;
	}       
function barcode() {
	var barcode=$(".code").val();
	if (strSum(barcode.substring(0, 5)) % 2 == 0) { 
		 alert("条形码不满足规则，请核对！");
		 $(".code").focus();
		 return false;
	}
	if (strSum(barcode.substring(6, 11)) % 2 == 1) { 
		 alert("条形码不满足规则，请核实！");
		 $(".code").focus();
		 return false;
	}
	barcode=barcode.substring(0, 5)+barcode.substring(6, 11);
	if (strSum(barcode) % 11 != 1) { 
		 alert("条形码不满足规则，请核实！");
		 $(".code").focus();
		 return false;
	} 

}

function myIdno() {
	var idCard = $(".idno").val();
	idCard = idCard.trim();
	if (idtype == '1010106') {
		return;
	}
	if (idCard.length != 18 && idCard.length != 15 && idCard.length != 10 && idCard != '') {
		alert('位数错误');
		$(".idno").val('');
		$(".idno").focus();
		$(".idno").select();

		return;
	}
	
	if(idCard.length == 15){
		idCard = transformatIdFrom15To18(idCard);
	}
	if (idCard.length == 18) {
		var isno = IdentityCodeValid(idCard);
		if (isno) {
			var sex;
			//获取性别
			if (parseInt(idCard.substr(16, 1)) % 2 == 1) {
				sex = "男";
			} else {
				sex = "女";
			}
			var radiovar = document.getElementsByName("customer.sex");
			for (var i = 0; i < radiovar.length; i++) {
				if (radiovar[i].value == sex)//这个2即为你所要找的input的值
					radiovar[i].checked = "checked";
			}
			//$("input[type='radio'][value=sex]").attr("checked","checked" );
			//$("#sex").val(sex);
			//获取年龄
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var day = myDate.getDate();
			var age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
			if (idCard.substring(10, 12) < month
					|| idCard.substring(10, 12) == month
					&& idCard.substring(12, 14) <= day) {
				age++;
			}
			$("input[name='customer.age']").val(age);
			//IdentityCodeValid(idCard);
		}else {
			$(".idno").val('');
			$(".idno").focus();
		}
	}else if(idCard.length == 10){
		var flag = checkTaiwan(idCard);
		console.log(idCard);
		if (flag) {
			var sex;
			//获取性别
			if (parseInt(idCard.substr(1, 1)) == 1) {
				sex = "男";
			} else {
				sex = "女";
			}
			var radiovar = document.getElementsByName("customer.sex");
			for (var i = 0; i < radiovar.length; i++) {
				if (radiovar[i].value == sex)//这个2即为你所要找的input的值
					radiovar[i].checked = "checked";
			}
		}else {
			$(".idno").val('');
			$(".idno").focus();
		}
   }
}



</script>
<div class="pageContent">
<form class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" action="${path}/events/erpCustomer!addCustomer.action" method="post">
	<input type = "hidden"  name= "navTabId" id = "navTabId" value="${navTabId }" />					  
    <div class="pageFormContent" layoutH="56">
		<h1 class="press">客户信息修改</h1>
        <div class="divider"></div>
        	<input name="customer.id" type="hidden" class="required"  value="${customer.id }" />
        	<input name="customer.eventsNo" type="hidden" class="required"  value="${customer.eventsNo }" />
		<p>
	        <label>条形码：</label>
	        <input name="customer.code" type="text" class="required"  value="${customer.code }" />
        </p>
        
        <p>
	        <label>姓名：</label>
	        <input name="customer.name" type="text"  value="${customer.name }" class="required" />
        </p>
        
        <p>
	        <label>证件类型：</label>
			<%-- <select id="documentType" name="customer.documentType" class="documentType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10101" >
				<option value="${customer.documentType}"></option>
			</select> --%>
				身份证<input type="radio" name="customer.documentType" id="documentType"  <c:if test="${customer.documentType=='1010101' }"> checked="checked" </c:if>  value="1010101"  />
			&nbsp;其他<input type="radio" name="customer.documentType" id="documentType" <c:if test="${customer.documentType=='1010106' }"> checked="checked" </c:if> value="1010106" />
        </p>
        
        <p>
			<label>证件号码：</label>
			<input type="text"  name="customer.idno" class="idno"  maxlength="18"  onBlur="myIdno()" value="${customer.idno}"/>
		</p>
		
        <p>
			<label>性别：</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			男<input type="radio" name="customer.sex" id="sex" class="required sex" value="男" <c:if test="${customer.sex=='男' }"> checked="checked" </c:if>/>
			&nbsp;&nbsp;&nbsp;		
			女<input type="radio" name="customer.sex" id="sex" class="required sex" value="女" <c:if test="${customer.sex=='女' }"> checked="checked" </c:if>/>
		</p>
		
		<p>
			<label>年龄：</label>
			<input name="customer.age" type="text"  value="${customer.age }" class="required digits" />
		</p>
		
		<p>
	        <label>远盟对接人：</label>
	       <input tabindex="4"  type="text"  name="customer.ymSalesman" value="${customer.ymSalesman}" />
        </p>
        
        <p>
	        <label>电话：</label>
	        <input name="customer.phone" type="text" class="phone"  value="${customer.phone }" />
        </p>
       
		<p>
			<label>套餐名称：</label> 
	<%-- <input type="hidden" name="customer.setmealName" id="comboId" bringbackname="combo.id" value="${combo.id }"/>
	<input type="text" size="30" name="" class="required" bringbackname="combo.comboName" readonly="readonly"  value="<hpin:id2nameDB  beanId='org.ymjy.combo.dao.ComboDao' id='${customer.setmealName}'/>"/>
	<a class="btnLook" href="${ path }/combo/comboAction!lookUpCombo.action" lookupGroup="combo">查找带回</a>	
	<input type="hidden" name="navTabId" value="${ navTabId }"/> --%>
	
			<%-- <select id="combo" name="customer.setmealName" rel="iselect" loadUrl="${path}/resource/customerRelationShip!findAllCombo.action">
				<option value="${customer.setmealName}"></option>
			</select> --%>
			<select id="combo" name="customer.setmealName" rel="iselect" loadUrl="${path}/resource/customerRelationShip!findComboByEventNo.action?eventsno=${customer.eventsNo}"  class="required"  >
				<option value="${customer.setmealName}"></option>
			</select> 
		</p>
		
		<%-- <p>
			<label>支公司：</label>
			<input type="text"  name="customer.branchCompany" readonly="readonly" value="${customer.branchCompany}"/>
		</p>
		<p>
			<label>所属公司：</label>
			<input type="text"  name="customer.ownedCompany" value="${customer.ownedCompany}"/>
		</p> --%>
		<p>
			<label>营销员：</label>
			<input type="text"  name="customer.salesMan"  value="${customer.salesMan}"/>
		</p>
		
		<p>
			<label>样本类型：</label>
			<select id="sampleType" name="customer.sampleType" rel="iselect" loadUrl="${path}/hpin/sysDictType!treeRegion.action?defaultID=10106" >
				<option value="${customer.sampleType}"></option>
			</select>
		</p>
		
		<p>
			<label>家族疾病史：</label>
			<input name="customer.familyHistory" value="${customer.familyHistory}" />
		</p>
		 <p>
	        <label>备注：</label>
	        <input name="customer.note" type="text" value="${customer.note }" />
        </p>
        
		<p>
			<label>采样日期：</label>
			<input id="customerDate" name="customer.samplingDate" value="<fmt:formatDate value='${customer.samplingDate }' pattern='yyyy-MM-dd'/>"  type="text" class="date required" />
            <a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		
		<p>
	        <label>部门：</label>
	        <input name="customer.department" type="text" value="${customer.department }" />
        </p>
        <p>
	        <label>营销员工号：</label>
	        <input name="customer.salesManNo" type="text" value="${customer.salesManNo }" />
        </p>
        <p>
	        <label>检测机构：</label>
	        <select id="testInstitutionselect" class="required testInstitutionSelect"
				name=customer.testInstitution rel="iselect"
				loadUrl="${path}/resource/geneTestInstitution!findGeneTestInstitution.action">
					<option value="${customer.testInstitution}"></option>
			</select>
        </p>
      </div>
      <div>
			<input id="jyId" name="jyId" type="text" value="${jyId}" />
      </div>
      
    <div class="formBar" style="width:98%">
      <ul>
        <li>
          <div class="buttonActive">
            <div class="buttonContent">
              <button type="submit">保存</button>
            </div>
          </div>
        </li>
        <li>
          <div class="button">
            <div class="buttonContent">
              <button type="reset">重置</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
</form>
</div>
