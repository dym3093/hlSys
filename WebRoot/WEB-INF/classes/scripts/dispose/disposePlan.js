/**
 * @author : 胡五音
 * 
 * 配置计划前台js
 * 
 */

//限制文本框只能输入正整数
function replaceStr(obj){
	obj.value = obj.value.replace(/\D/g,'') ;
}

/**
 * 根据checkbox选择事件控制"品牌"和"数量"是否可用
 * @param {} id
 */
function setDisOrNo(id){
	if($$(id).checked){
		$$(id + "_pp").disabled = false ;
		$$(id + "_sl").disabled = false ;
	} else{
		$$(id + "_pp").value = "" ;
		$$(id + "_sl").value = "" ;
		$$(id + "_pp").disabled = true ;
		$$(id + "_sl").disabled = true ;
	}
}

/**
 * 判断_form是否有不满足提交条件的选项，有则返回错误信息，没有就提交表单
 * @param {} _form
 */
function checkAndSubmit(_form){
	var startDate = $$("stockDispatch.stockStartDate").value ;
	var endDate1 = $$("stockDispatch.stockExpectedEndDate").value ;
	var endDate2 = $$("stockDispatch.stockRealEndDate").value ;
	var acceptDate = $$("stockDispatch.acceptanceDate").value ;
	if(startDate > endDate1 || startDate > endDate2){
		alert("\"开工日期\"不能晚于\"预完工日期\"或\"实际完工日期\"!") ;
	}else{
		if(endDate2 > acceptDate){
			alert("\"验收日期\"不能早于\"实际完工日期\"!") ;
		}else{
			submitForm(_form) ;
		}
	}
}

/**
 * 发送Email
 */
function sendEmail(){
	alert("发送Email功能完善中。。。。") ;
}

/**
 * 导出Excel
 */
function exportEmail(){
	alert("导出Excel功能完善中。。。。") ;
}
