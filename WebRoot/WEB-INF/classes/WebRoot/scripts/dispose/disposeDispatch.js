/**
 * @author 胡五音
 * 
 * 服务派工单前端js
 */
 
//为js取出的String对象截取尾部的空格
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

//限制文本框只能输入正整数
function replaceStr(obj){
	obj.value = obj.value.replace(/\D/g,'') ;
}

/**
 * 根据数量文本框的值变化，动态组装Struts参数
 * @param {} _index
 */
function setValue(_index){
	var programId = $$("programId" + _index).value ;
	var programSign = $$("programSign" + _index).value ;
	var disposeItemId = $$("select-" + _index).value ;
	var value = programId + "-" + programSign + "-" + disposeItemId ;
	$$("strList[" + _index + "]").value = value ;
}

/**
 * 提交保存工单
 * @param {} _form
 */
function checkAndSubmit(_form){
	var selectes = Ext.query("*[class=hwy]") ;
	var flag = true ;
	for(var i = 0 ; i < selectes.length ; i ++){
		var str = $$("strList[" + i + "]").value.split("-")[2] ;
		if(str == '' || str == null || str == 'null'){
			flag = false ;
			break ;
		}
	}
	if(flag){
		submitForm(_form) ;
	}else{
		alert("请选择品牌与型号！") ;
	}
}

/**
 * 保存派工单反馈信息
 * @param {} _form
 */
function saveFeedback(_form){
	submitForm(_form) ;
}