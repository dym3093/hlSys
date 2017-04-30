/**
 * @author : 胡五音
 * 
 * 配置计划检索房源js
 * 
 */
 
//为js取出的String对象截取尾部的空格

String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 根据查询条件判断房源是否存在，存在则提交form
 * @param {} _form
 */
function checkIsExist(_form){
	var keyWords = document.getElementById("keyWords").value.Trim();
	if (keyWords.length == 0) {
		alert("请先按照要求输入查询条件！");
	}else{
		Ext.Ajax.request({
			url : "disposePlan!isExistOfFangyuan.action",
			params : {
				keyWords : keyWords
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.isExist == true) {
					submitForm(_form) ;
				}
				if(responseArray.isExist == false) {
					alert("您要检索的房源信息不存在，请确认房源已经录入！") ;
				}
			}
		})
	}
}

