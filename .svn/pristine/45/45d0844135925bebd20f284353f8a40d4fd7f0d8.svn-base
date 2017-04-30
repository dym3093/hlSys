/**
 * Created by pengying on 2016/5/10.
 */
/**
 * 初加载是调用的方法
 * 1:进度条
 * 0:转圈
 */ 
function py_ready(a) {
	var py_mbdiv = $(".py_theMb", navTab.getCurrentPanel());
	var py_bigdiv = py_mbdiv.parent();
	var py_pic = $(".py_bakpic", navTab.getCurrentPanel());
	py_mbdiv.hide();
	var py_mbw = py_bigdiv.get(0).clientWidth;
	var py_mbh = py_bigdiv.get(0).clientHeight;
	py_mbdiv.css("width", py_mbw);
	py_mbdiv.css("height", py_mbh);
	if (a == 1) {
		//console.log("a=1,出现进度条");
		py_pic.append("<div class='tiao'></div>");
		py_pic.append("<div class='thetxt'></div>");
		$(".tiao", navTab.getCurrentPanel()).attr("style", "background:url('../images/loading/loading.gif') no-repeat;");
		py_pic.attr("style", "background:url('../images/loading/bak.png') no-repeat;");
		py_pic.css("width", "288");
		py_pic.css("height", "34");
	} else if (a == 0) {
		//console.log("a=0,出现蒙层");
		py_pic.attr("style", "background:url('../images/loading/load-icon.gif');no-repeat;");
		//py_pic.append('<img src="../images/loading/load-icon.gif" />');
		py_pic.css("width", "32");
		py_pic.css("height", "32");
	} else if(a == 2) {
		//console.log("a=0,出现蒙层");
		py_pic.attr("style", "background:url('../images/loading/loading.png');no-repeat;");
		//py_pic.append('<img src="../images/loading/load-icon.gif" />');
		py_pic.css("width", "170");
		py_pic.css("height", "46");
	}
	var py_picw = py_pic.css("width");
	var py_pich = py_pic.css("height");
	// marginleft
	var H_mbw = parseInt(py_mbdiv.css("width")) / 2;
	var H_picw = parseInt(py_picw) / 2;
	py_pic.css("margin-left", H_mbw - H_picw);
	// margintop
	var H_mbh = parseInt(py_mbdiv.css("height")) / 2;
	var H_pich = parseInt(py_pich) / 2;
	py_pic.css("margin-top", H_mbh - H_pich);
}

function readdialog(a) {
	var py_mbdiv = $(".py_theMb", $.pdialog.getCurrent());
	var py_bigdiv = py_mbdiv.parent();
	var py_pic = $(".py_bakpic", $.pdialog.getCurrent());
	py_mbdiv.hide();
	var py_mbw = py_bigdiv.get(0).clientWidth;
	var py_mbh = py_bigdiv.get(0).clientHeight;
	py_mbdiv.css("width", py_mbw);
	py_mbdiv.css("height", py_mbh);
	if (a == 1) {
		//console.log("a=1,出现进度条");
		py_pic.append("<div class='tiao'></div>");
		py_pic.append("<div class='thetxt'></div>");
		$(".tiao", $.pdialog.getCurrent()).attr("style", "background:url('../images/loading/loading.gif') no-repeat;");
		py_pic.attr("style", "background:url('../images/loading/bak.png') no-repeat;");
		py_pic.css("width", "288");
		py_pic.css("height", "34");
	} else if (a == 0) {
		//console.log("a=0,出现蒙层");
		py_pic.attr("style", "background:url('../images/loading/load-icon.gif');no-repeat;");
		//py_pic.append('<img src="../images/loading/load-icon.gif" />');
		py_pic.css("width", "32");
		py_pic.css("height", "32");
	} else if(a == 2) {
		//console.log("a=0,出现蒙层");
		py_pic.attr("style", "background:url('../images/loading/loading.png');no-repeat;");
		//py_pic.append('<img src="../images/loading/load-icon.gif" />');
		py_pic.css("width", "170");
		py_pic.css("height", "46");
	}
	var py_picw = py_pic.css("width");
	var py_pich = py_pic.css("height");
	// marginleft
	var H_mbw = parseInt(py_mbdiv.css("width")) / 2;
	var H_picw = parseInt(py_picw) / 2;
	py_pic.css("margin-left", H_mbw - H_picw);
	// margintop
	var H_mbh = parseInt(py_mbdiv.css("height")) / 2;
	var H_pich = parseInt(py_pich) / 2;
	py_pic.css("margin-top", H_mbh - H_pich);
}

function py_showDialog() {
	$(".py_theMb", $.pdialog.getCurrent()).show();// 显示的蒙版
}
//点击某元素加载页面消失调用的方法
function py_hideDialog() {
	$(".py_theMb", $.pdialog.getCurrent()).hide();// 显示的蒙版
}

// 点击某元素加载页面出现调用的方法
function py_show() {
	$(".py_theMb", navTab.getCurrentPanel()).show();// 显示的蒙版
}
// 点击某元素加载页面消失调用的方法
function py_hide() {
	$(".py_theMb", navTab.getCurrentPanel()).hide();// 显示的蒙版
}

function py_hideParm(param) {
	$(".py_theMb", param).hide();// 显示的蒙版
}

function sadd(f) {
	var tbox = $(".py_bakpic", navTab.getCurrentPanel());
	var pro = $(".tiao", navTab.getCurrentPanel());
	pro.css("width", f + "%");
	$(".thetxt").html(f + "%");
}

function saddDialog(f) {
	var tbox = $(".py_bakpic", $.pdialog.getCurrent());
	var pro = $(".tiao", $.pdialog.getCurrent());
	pro.css("width", f + "%");
	$(".thetxt").html(f + "%");
}

//从后台获取进度数
function getProcess(){
	 $.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "../serverProcess/getServerProcess!getProcess.action",
			success: function(data){
				var num = parseInt(data.serverProcess);
				if(num>99){
					sadd(99);
				}else{
					sadd(num);
				}
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
}

//从后台获取进度数
function getProcessDialog(){
	 $.ajax({
			type: "post",
			cache :false,
			dataType: "json",
			url: "../serverProcess/getServerProcess!getProcess.action",
			success: function(data){
				var num = parseInt(data.serverProcess);
				if(num>99){
					saddDialog(99);
				}else{
					saddDialog(num);
				}
			},
			error :function(data){
				alert("服务发生异常，请稍后再试！");
			}
		});
}

/*
 * create by henry.xu 20160818;
 * 导出从后台获取进度数;决定是否关闭遮挡层;
 */
function getExportProcess(param){
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "../serverProcess/getServerProcess!getExportProcess.action",
		success: function(data){
			var flag = data.serverExportProcess;
			//当为1时,隐藏遮挡层;
			if(flag == "1") {
				delExportOutProcess();
				py_hideParm(param);
			} else {
				setTimeout(function() {getExportProcess(param)},1000); //设置每隔200毫秒循环执行getExportProcess();
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}

/*
 * create by henry.xu 20160818;
 * 导入从后台获取进度数;决定是否关闭遮挡层;
 */
function getExportInProcess(param, iCount){
	$.ajax({
		type: "post",
		cache :false,
		dataType: "json",
		url: "../serverProcess/getServerProcess!getExportInProcess.action",
		success: function(data){
			var flag = data.serverInExportProcess;
			//当为1时,隐藏遮挡层;
			if(flag == "1") {
				sadd(100); //设置进度条100;
				py_hideParm(param);
				delExportInProcess();
				delProcess();
				clearInterval(iCount);
			} else {
				setTimeout(function () {getExportInProcess(param, iCount);},200); //设置每隔200毫秒循环执行getExportProcess();
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
}


/*
 * 获取导出session中的值;
 */
function getSessionExportOutVal() {
	var result = true;
	//获取session中的值;如果存在,则提示;
	$.ajax({
		type: "post",
		cache :false,
		async: false, //同步请求
		dataType: "json",
		url: "../serverProcess/getServerProcess!getSessionExportOutVal.action",
		success: function(data){
			var flag = data.serverExportProcess;
			if(flag == "1"  || flag == "0") { //表示有导出在使用;
				result = false;
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
	
	return result;
}

/*
 * 获取导出session中的值;
 */
function getSessionExportInVal() {
	var result = true;
	//获取session中的值;如果存在,则提示;
	$.ajax({
		type: "post",
		cache :false,
		async: false, //同步请求
		dataType: "json",
		url: "../serverProcess/getServerProcess!getSessionExportInVal.action",
		success: function(data){
			var flag = data.serverInExportProcess;
			if(flag == "1"  || flag == "0") { //表示有导出在使用;
				result = false;
			}
		},
		error :function(data){
			alert("服务发生异常，请稍后再试！");
		}
	});
	
	return result;
}
//导出session删除
function delExportOutProcess() {
	$.get("../serverProcess/getServerProcess!delExportOutProcess.action");
}
//导入session删除
function delExportInProcess() {
	$.get("../serverProcess/getServerProcess!delExportInProcess.action");
}
//当前session的进度数清零
function delProcess(){
	$.get("../serverProcess/getServerProcess!delProcess.action");
}

