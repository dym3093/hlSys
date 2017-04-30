/**
 * create by henry.xu 20160921
 * 该js用于处理导入/导出遮蔽层公用方法;
 * 建议使用时导入;
 *
 */

//--------------------------------界面方法js调用start------------------------------
/*
 * create by henry.xu 20160818;
 * 导入/导出预处理 同步请求
 * @param 当前要访问默认设置的key值;
 */
function dealSessionPre(param) {
	var result = "notexist"; //默认不存在;
	$.ajax({
		type: "post",
		cache :false,
		async: false, //同步请求
		dataType: "json",
		url: "../shield/shieldProcess!dealSessionPre.action",
		data: {"applyKey": param},
		success: function(data){
			result = data.result;
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
	
	return result;
}

/**
 * 触发设置该导出的参数,为有效,此时已经显示遮蔽层;
 */
function dealSessionProcess(param, pageType) {
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "../shield/shieldProcess!dealSessionProcess.action",
		data: {"applyKey": param},
		success: function(data){
			var flag = data.result;
			//当为无效时,隐藏遮挡层;
			if(flag == "11") {
				deleteSessonProcess(param); //删除session中值;
				shieldHide(pageType); //隐藏;
			} else {
				setTimeout(function() {dealSessionProcess(param, pageType)},1*1000); //设置每隔200毫秒循环执行getExportProcess();
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}
/**
 * 删除session中指定的值;
 * @param param
 */
function deleteSessonProcess(param) {
	$.get("../shield/shieldProcess!deleteSessonProcess.action?applyKey="+param);
}


//--------------------------------界面方法js调用end------------------------------

//--------------------------------界面方法处理start------------------------------
/**
 * 该方法用于NavTab页面; 初加载是调用的方法
 * showStyle 0:转圈  1:进度条  2:图片显示等待;
 * pageType navTab / dialog
 */ 
function shieldReadyNavTab(showStyle, pageType) {
	var objThisPage = judgeReturnObj(pageType);
	var py_mbdiv = $(".py_theMb", objThisPage);
	var py_pic = $(".py_bakpic", objThisPage);
	
	var py_bigdiv = py_mbdiv.parent();
	py_mbdiv.hide(); //遮蔽层默认隐藏;
	
	var py_mbw = py_bigdiv.get(0).clientWidth;
	var py_mbh = py_bigdiv.get(0).clientHeight;
	py_mbdiv.css("width", py_mbw);
	py_mbdiv.css("height", py_mbh);
	
	if (showStyle == 1) {
		py_pic.append("<div class='tiao'></div>");
		py_pic.append("<div class='thetxt'></div>");
		$(".tiao", objThisPage).attr("style", "background:url('../images/loading/loading.gif') no-repeat;");
		py_pic.attr("style", "background:url('../images/loading/bak.png') no-repeat;");
		py_pic.css("width", "288");
		py_pic.css("height", "34");
	} else if (showStyle == 0) {
		py_pic.attr("style", "background:url('../images/loading/load-icon.gif');no-repeat;");
		py_pic.css("width", "32");
		py_pic.css("height", "32");
	} else if(showStyle == 2) {
		py_pic.attr("style", "background:url('../images/loading/loading.png');no-repeat;");
		py_pic.css("width", "170");
		py_pic.css("height", "46");
	}
	var py_picw = py_pic.css("width");
	var py_pich = py_pic.css("height");
	/*
	 * 屏幕位置计算;
	 * */
	// marginleft
	var H_mbw = parseInt(py_mbdiv.css("width")) / 2;
	var H_picw = parseInt(py_picw) / 2;
	py_pic.css("margin-left", H_mbw - H_picw);
	// margintop
	var H_mbh = parseInt(py_mbdiv.css("height")) / 2;
	var H_pich = parseInt(py_pich) / 2;
	py_pic.css("margin-top", H_mbh - H_pich);
}

/**
 * 点击某元素加载页面出现调用的方法
 * @param pageType navTab / dialog
 */
function shieldShow(pageType) {
	$(".py_theMb", judgeReturnObj(pageType)).show();// 显示的蒙版
}

/**
 * 点击某元素加载页面消失调用的方法
 * @param pageType navTab / dialog
 */
function shieldHide(pageType) {
	$(".py_theMb", judgeReturnObj(pageType)).hide();// 显示的蒙版
}

function judgeReturnObj(pageType) {
	return pageType;
}

/**
 * 进度条增长;
 * @param len 增长长度
 * @param pageType navTab / dialog
 */
function processAdd(len, pageType) {
	var tbox = $(".py_bakpic", judgeReturnObj(pageType));
	var pro = $(".tiao", judgeReturnObj(pageType));
	pro.css("width", len + "%");
	$(".thetxt").html(len + "%");
}
//--------------------------------界面方法处理end------------------------------
