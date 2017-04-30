/**
 * 根据注解下拉框的值，获取当前被注解的属性的值
 * @param obj 注解列表下拉框对象
 * @param inputId 显示注解标注的实体属性的值的输入框
 * @author 胡五音
 * @create 2012-07-10
 */
function selectField(obj , path){
	var td1 = obj.parentNode.parentNode.childNodes[1] ;
	var td1Input = $(td1).children("input")[0] ; 
	var td2 = obj.parentNode.parentNode.childNodes[2] ;
	var oId = document.getElementById('objId').value ;
	if(obj.value == '' || obj.value == null){
		td1Input.value = '' ;
		td2.innerHTML = '<input type = "text" readOnly = "readOnly"/>' ;
	}else{
		Ext.Ajax.request({
			url : path + "/stock/customer!getFieldValueByAnnotation.action" ,
			params : {
				objId : oId ,
				value : obj.value
			} ,
			success : function(response , options){
				var responseArray = Ext.util.JSON.decode(response.responseText) ;
				td1Input.value = responseArray.fieldValue ;
				var data = responseArray.dictValue ;
				var htmlStr = "" ;
				if(data.length > 0){
					htmlStr = "<select id = 'newValue' name = 'newValue'>"
								+ "<option></option>" ;
					for(var i = 0 ; i < data.length ; i ++){
						htmlStr = htmlStr + "<option value = '" + data[i].dictCode + "'>" + data[i].dictName + "</option>" ;
					}
				}else{
					if(responseArray.isDate == 'true'){
						htmlStr = "<input type = 'text' class = 'wDate' onFocus = 'WdatePicker()' name = 'newValue' id = 'newValue' >" ;
					}else{
						htmlStr = "<input type = 'text' name = 'newValue' id = 'newValue' >" ;
					}
				}
				td2.innerHTML = htmlStr ;
			}
		})
	}
}