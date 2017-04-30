/**
 * @author : 胡五音
 * 
 * 配置方案前台js
 * 
 */
 
var values = "@.@" ;
 
// 为js取出的String对象截取尾部的空格
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function getFangyuanInfo() {
	var keyType = document.getElementById("keys").value;
	var keyWords = document.getElementById("keyWords").value.Trim();
	if (keyType.length == 0 || keyWords.length == 0) {
		alert("请先按照要求输入查询条件！");
	} else {
		Ext.Ajax.request({
			url : "disposeProgram!getInfoOfHouseProgram.action",
			params : {
				keyType : keyType,
				keyWords : keyWords
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.isExist == true) {
					var sCount = responseArray.sCount;
					var tCount = responseArray.tCount;
					var cCount = responseArray.cCount;
					var wCount = responseArray.wCount;
					var yCount = responseArray.yCount;
					var htmlInfo = "<input type = 'radio' id = 'houseInfo' onclick = 'getRoomsDisposeProgram()'/>"
							+ sCount
							+ "室"
							+ tCount
							+ "厅"
							+ cCount
							+ "厨"
							+ wCount
							+ "卫"
							+ yCount
							+ "阳台" + "<input type = 'hidden' name = 'houseInfo' value = '" + sCount
							+ "室"
							+ tCount
							+ "厅"
							+ cCount
							+ "厨"
							+ wCount
							+ "卫"
							+ yCount
							+ "阳台'/>";
					$$("info").innerHTML = htmlInfo;
				}
				if (responseArray.isExist == false) {
					alert("根据您所录入的查询条件，查询结果为空！");
				}
			}
		})
	}
}

/**
 * 根据房源查询信息组装配置方案页面
 */
function getRoomsDisposeProgram(){
		$$("showDiv").style.display = 'block';
		var houseCode = $$("houseCode").value.Trim();
		var hireContractId = $$("hireContractId").value.Trim();
		var buttonControl = $$("buttonControl").value ;
		Ext.Ajax.request({
			url : "disposeProgram!getRoomsDisposeProgram.action",
			params : {
				houseCode : houseCode,
				hireContractId : hireContractId , 
				buttonControl : buttonControl 
			},
			success : function(response, options) {
				var str=response.responseText;
				var responseArray = Ext.util.JSON.decode(str);
				values = responseArray.paramsValue ;
				str=responseArray.htmlInfo ;
				str=str.replace(/____/g,'"');
				$$("htmlForm").innerHTML =str  ;
			}
		})
	}
	
/**
 * 前台checkbox选中事件，绑定
 * @param {} stanId
 * @param {} roomId
 * @param {} count
 */	
function setValue(hireContractCode , stanId , roomId , count){
	if(document.getElementById(stanId + "_" + roomId + "_").checked){
		document.getElementById(roomId + "_" + stanId).disabled = false ;
		if(values.indexOf("@.@" + hireContractCode + "-" + roomId + "-" + stanId + "-") == -1 ){
			values = values + hireContractCode + "-" + roomId + "-" + stanId + "-" + count + "@.@" ;
		}
	}else{
		document.getElementById(roomId + "_" + stanId).disabled = true ;
		var value = values.split("@.@" + hireContractCode + "-" + roomId + "-" + stanId) ;
		var valueLast = value[1].split("@.@") ;
		var returnStr = "@.@" ;
		if(valueLast.length > 2){
			for(var i = 1 ; i < valueLast.length - 1 ; i ++){
				returnStr = returnStr + valueLast[i] + "@.@"  ;
			}
			values = returnStr.substring(0 ,returnStr.length - 3) + value[0] + "@.@" ;
		}else{
			values = value[0] + "@.@" ;
		}
	}
}	


/**
 *根据前台页面数量变化绑定(收房合同编号-房间Id-配置标准Id-数量)
 * @param {} roomId
 * @param {} stanId
 * @param {} count
 */
function setCountValue(hireContractCode , roomId , stanId , count){
	var value = values.split("@.@" + hireContractCode + "-" + roomId + "-" + stanId) ;
	var valueLast = value[1].split("@.@") ;
	var returnStr = "" ;
	if(valueLast.length > 2){
		for(var i = 1 ; i < value.length - 1  ; i ++ ){
			returnStr = returnStr  + valueLast[i] + "@.@" ;
		}
		values = value[0] + "@.@" + returnStr + hireContractCode + "-" + roomId + "-" + stanId + "-" + count + "@.@" ;
	}else{
		values = value[0] + "@.@" + hireContractCode + "-" + roomId + "-" + stanId + "-" + count + "@.@" ;
	}
}


/**
 * Ajax提交保存房源的配置方案(选中项)
 */
function saveRoomsProgram(){
	if(values.length < 2){
		alert("请先选择配置方案，再提交！") ;
	}else{
		var houseCode = document.getElementById("houseCode").value.Trim();
		Ext.Ajax.request({
			url : "disposeProgram!saveDisposeProgramOfRooms.action" ,
			params : {
				keyWords : houseCode ,
				values : values 
			},
			success : function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if(responseArray.success == true){
					alert("保存成功!") ;
				}else{
					alert("保存失败!") ;
				}
			}
		})
	}
}

//限制输入框只能输入正整数
function replaceStr(obj){
	obj.value = obj.value.replace(/\D/g,'') ;
}
