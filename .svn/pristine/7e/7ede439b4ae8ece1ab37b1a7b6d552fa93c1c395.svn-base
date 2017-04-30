// //////////通用FORM校验///////////////
// ////////2006-06-27//////////////
var PARMASSPLITKEY = "---";
var VALUESPLITKEY = ":";
var TITLENAME = "name";
var CHECKTYPE = "check";
var INFORMATION = "info";
var INFORMATIONSPLITKEY = "||";

// 调试开关
var DEBUG_TAG = false;
// 根据调试开关提示信息
function AlertDebug(value) {
	if (DEBUG_TAG) {
		alert(value);
	}
}
/*
 * 数组对象CheckArray 对array对象重新包装 @array数组 @num数组中元素个数
 * @getArrayOfIndex(index)取得array[index]的值 @getNum取得数组数据的个数
 * @setArrayOfIndex(index,data)设置array[index]的值
 * @delArrayOfIndex(index)删除array[index]的值 @add(data)加入数据
 */
function CheckArray() {
	this.array = new Array();
	this.num = 0;
	this.GetArrayOfIndex = function(index) {
		if (index < this.num) {// 位置编号小于数组中数据个数
			return this.array[index];
			// 取得array[index]的值
		} else {
			return null;
			// 如果编号大于数组数据个数返回null
		}
	}
	this.GetNum = function() {
		return this.num;
	}
	this.SetArrayOfIndex = function(index, data) {
		if (index < this.num) {// 附值必须满足index在数组容量内
			this.array[index] = data;
		}
	}
	this.DelArrayOfIndex = function(index) {
		if (index < this.num) {// 删除数据index必须在数组数据个数内
			for (i = index; i < this.num; i++) {
				this.array[i] = this.array[i + 1];
				// 将index以后的数据前移
			}
			this.num--;
			// 减少个数
		}
	}
	this.AddData = function(data) {
		this.array[this.num] = data;
		this.num++;
	}

}
// 封装的校验对象
function CheckFunctionObject() {
	this.tag = "undifine";
	// 标示
	this.info = "";
	// 校验方法句柄可由外部获得
	this.check = check;
	function check(tag, form, obj, value) {
		var ret = true;
		return ret;
	}
	// 提示信息句柄可由外部获得
	this.message = message;
	function message(tag, form, obj, value) {
		return this.info;
	}
}
// 校验方法对象集合
var FORM_CHECK_FUNCTIONS = new CheckArray();
// 增加校验方法
function AddCheckFunctionObject(tag, info, check, message) {
	var checkfunctionobject = new CheckFunctionObject();
	checkfunctionobject.tag = tag;
	checkfunctionobject.info = info;
	if (check != null) {
		checkfunctionobject.check = check;
	}
	if (message != null) {
		checkfunctionobject.message = message;
	}
	FORM_CHECK_FUNCTIONS.AddData(checkfunctionobject);
	var num = FORM_CHECK_FUNCTIONS.GetNum();
	AlertDebug("如果这个值：" + (FORM_CHECK_FUNCTIONS.GetNum() - num) + "=0正确加入！");
}
// 获得对象的值
// todo::可以更加完善
function GetObjValue(obj) {
	var ret = "";
	if (obj != null) {
		ret = obj.value;
	}
	return ret;
}
// 获得检测的方法对象
function GetCheckFunctionObject(tag) {
	// 默认返回检测对象 即检测任何值都返回正确
	var ret = new CheckFunctionObject();
	for ( var i = 0; i < FORM_CHECK_FUNCTIONS.GetNum(); i++) {
		var obj = FORM_CHECK_FUNCTIONS.GetArrayOfIndex(i);
		// 返回标示相同的对象
		if (obj.tag == tag) {
			return obj;
		}
		// 扩展：用于检测标志上带有检测条件的情况，会引起意想不到错误
		// 示例：检验标志长度大于100，检验方法只要提供“长度”便可校验这一类的数据
		if (tag.indexOf(obj.tag) == 0) {
			return obj;
		}
	}
	return ret;
}
// 校验某个对象如出错返回错误信息否则返回null
function CheckObject(form, obj) {
	// 校验类型
	var checktype = "";
	// 自定义错误信息
	var streorr = "";
	// 校验对象名称
	var title = "";
	if (typeof obj.lang != "undefined") {
		var temps = obj.lang.split(PARMASSPLITKEY);
		for (var i = 0; i < temps.length; i++) {
			var temp = temps[i];
			var titletag = TITLENAME + VALUESPLITKEY;
			var checktag = CHECKTYPE + VALUESPLITKEY;
			var infotag = INFORMATION + VALUESPLITKEY;
			if (temp.indexOf(titletag) == 0) {
				title = temp.substring(titletag.length);
			}
			if (temp.indexOf(checktag) == 0) {
				checktype = temp.substring(checktag.length);
			}
			if (temp.indexOf(infotag) == 0) {
				streorr = temp.substring(infotag.length);
			}
		}
		AlertDebug("获得的字符串解析后的值：名称[" + title + "];校验类型[" + checktype
				+ "];自定义信息[" + streorr + "]");
	}
	// 存储错误信息
	var retInfo = null;
	// 检测类型表达式操作数组“!”非“&”与“|”或，优先级从左至右递减
	var oparray = new CheckArray();
	// 检测类型数组
	var typearray = new CheckArray();
	// 检测类型值数组
	var valuearray = new CheckArray();

	// ------------------------------------------------------------------------------------------------------------------//
	// 1 分离检测类型。得到输入的判定条件
	var strtype = "";
	// 检验表单元素类型，从而进行相应判断
	if ((typeof checktype != "undefined") && (checktype != "")) {
		strtype = checktype;
	} else {
		// 未设置检测类型便不进行检验返回默认值即可
		return retInfo;
	}
	AlertDebug("需要校验的类型表达式为" + strtype);
	// 获得需要校验的值
	var value = GetObjValue(obj);
	AlertDebug("需要校验的值为" + value);
	/*
	 * 例子: "!空&金额"意思为输入为必填的金额 分离后为 oparray[0]=0; oparray[1]=1; typearray[0]="空"
	 * typearray[1]="金额"
	 */
	var strtemp = "";
	var x = 0;
	var t = 0;
	for (var i = 0; i < strtype.length; i++) {
		if ((strtype.charAt(i) == '!') || (strtype.charAt(i) == '&')
				|| (strtype.charAt(i) == '|')) {
			switch (strtype.charAt(i)) {// 将操作符转换为可看出优先级的数字,0最高
				case '!' :
					t = 0;
					break;
				case '&' :
					t = 1;
					break;
				case '|' :
					t = 2;
					break;
			}
			oparray.AddData(t);
			// 将操作符加入数组
			strtemp = strtype.substring(x, i);
			if (strtemp != "") {
				typearray.AddData(strtemp);
				strtemp = "";
			}
			x = i + 1;
		}
	}
	// 如果剩余则为检测类型，例如上例中金额
	strtemp = strtype.substring(x, strtype.length);
	if (strtemp != "") {
		typearray.AddData(strtemp);
		strtemp = "";
	}
	// 分离完毕
	AlertDebug("操作符数组长度：" + oparray.GetNum() + "比检测类型数组长度："
			+ typearray.GetNum());

	// 2 检测检测方法中的各种类型，将结果加入valuearray中
	for (var j = 0; j < typearray.GetNum(); j++) {
		// 获得校验对象
		var checkFunctionObject = GetCheckFunctionObject(typearray
				.GetArrayOfIndex(j));
		// 对输入数据带检测类型逐个进行检测，结果放入valuearray中
		valuearray.AddData(checkFunctionObject.check(typearray
						.GetArrayOfIndex(j), form, obj, value));
		AlertDebug("此次校验的结果：信息["
				+ checkFunctionObject.info
				+ "]值["
				+ checkFunctionObject.check(typearray.GetArrayOfIndex(j), form,
						obj, value) + "]");
	}

	// 3 根据操作符判断最终检测结果
	var infoNum = 0;
	var kk = 0;
	while (oparray.GetNum() != 0) {
		kk = kk + 1;
		var op = -1;
		var ww = 0;
		// 获取优先级最靠前最高的操作符
		for (i = 0; i < oparray.GetNum(); i++) {
			if ((oparray.GetArrayOfIndex(i) < oparray.GetArrayOfIndex(i + 1))
					|| (i == oparray.GetNum() - 1)) {
				op = oparray.GetArrayOfIndex(i);
				ww = i;
				oparray.DelArrayOfIndex(i);
				i = oparray.GetNum();
			}
		}
		// 根据操作符换算结果数组
		switch (op) {
			case 0 :
				AlertDebug("现在进行“非”运算，原值是:" + valuearray.GetArrayOfIndex(ww));
				valuearray.SetArrayOfIndex(ww, !valuearray.GetArrayOfIndex(ww));
				AlertDebug("进行“非”运算后值是:" + valuearray.GetArrayOfIndex(ww));
				break;
			// 如果是非操作，则根据操作符所在位数进行单目运算，将结果存入当前位置
			case 1 :
				AlertDebug("现在进行“与”运算，原值是:" + valuearray.GetArrayOfIndex(ww));
				valuearray.SetArrayOfIndex(ww, valuearray.GetArrayOfIndex(ww)
								&& valuearray.GetArrayOfIndex(ww + 1));
				AlertDebug("进行“与”运算后值是:" + valuearray.GetArrayOfIndex(ww));
				valuearray.DelArrayOfIndex(ww + 1);
				break;
			// 如果是与操作，进行双目运算，将结果存入第一元素位置，然后删除第二元素
			case 2 :
				AlertDebug("现在进行“或”运算，原值是:" + valuearray.GetArrayOfIndex(ww));
				valuearray.SetArrayOfIndex(ww,
						(valuearray.GetArrayOfIndex(ww) || valuearray
								.GetArrayOfIndex(ww + 1)));
				AlertDebug("进行“或”运算后值是:" + valuearray.GetArrayOfIndex(ww));
				valuearray.DelArrayOfIndex(ww + 1);
				break;
			// 如果是或操作，进行双目运算，将结果存入第一元素位置，然后删除第二元素
		}
		if (!valuearray.GetArrayOfIndex(0)) {
			if (infoNum == 0) {
				infoNum = kk;
			}
		}
	}
	// 检测运算后的值如果正确返回正确信息。
	if (valuearray.GetArrayOfIndex(0)) {
		return retInfo;
	}

	// 4 返回错误信息
	// 取得自定义错误提示信息
	if ((typeof streorr != "undefined") && ("" != streorr)) {
		var streorrArray = streorr.split('||');
		if (streorrArray.length > 1) {
			return streorrArray[infoNum - 1];
		}
		return streorr;
	}

	// 根据判定条件自动生成错误提示，取得输入名称
	var strtitle = "";
	if (typeof title != "undefined") {
		strtitle = title;
	}
	var strautoeorr = strtitle + "输入有误! 参考格式：";
	var opstr = "";
	var y = 0;
	var strtemp2 = "";
	for (var i = 0; i < strtype.length; i++) {
		if (strtemp2 != "") {
			AlertDebug("此时截取的字符串为：" + strtemp2);
			var checkFunctionObject = GetCheckFunctionObject(strtemp2);
			strautoeorr = strautoeorr
					+ checkFunctionObject.message(strtemp2, form, obj, value);
			strtemp2 = "";
		}
		strautoeorr += opstr;
		opstr = "";
		if ((strtype.charAt(i) == '!') || (strtype.charAt(i) == '&')
				|| (strtype.charAt(i) == '|')) {
			switch (strtype.charAt(i)) {// 将操作符转换为可看出优先级的数字,0最高
				case '!' :
					opstr = "不能为";
					break;
				case '&' :
					opstr = "并且为";
					break;
				case '|' :
					opstr = "或者为";
					break;
			}
			strtemp2 = strtype.substring(y, i);
			y = i + 1;
		}
	}
	strtemp2 = strtype.substring(y);
	AlertDebug("此时剩下的字符串为：" + strtemp2);
	var checkFunctionObject = GetCheckFunctionObject(strtemp2);
	strautoeorr = strautoeorr
			+ checkFunctionObject.message(strtemp2, form, obj, value);
	return strautoeorr;
	// 结束单项校验 测试ok
}
// 选中对象并且设置焦点
// todo::根据对象类型选中对象
function SelectObject(obj) {
	try {
		obj.select();
	} catch (e) {
	}
	try {
		obj.setfocus();
	} catch (e) {
	}
	try {
		obj.focus();
	} catch (e) {
	}
}
// 校验表格
function CheckFormFunction(inputform) {
	var str = "";
	for (var kk = 0; kk < inputform.elements.length; kk++) {
		var formelement = inputform.elements[kk];
		if(formelement.disabled){
			  continue;
		}
		var strInfo = CheckObject(inputform, formelement);
		if (strInfo != null) {
			// 如果出错处理出错信息
			SelectObject(formelement);
			alert(strInfo);
			return false;
		}
	}
	return true;
}
// 下面为扩展的校验方法
/*
 * 扩展方法: 1、设置校验的标识符以及提示信息 2、编写一个校验方法 3、将这些加入校验数组中
 */
// 空
var tag0 = "NULL";
var info0 = "空";
function check0(tag, form, obj, value) {
	try {
		;
		var str = value.replace(/(^\s*)|(\s*$)/g, "");
		if (str == "" ) {//|| str == "-1"
			return true;
		}
	} catch (e) {
		return true;
	}
	return false;
}
AddCheckFunctionObject(tag0, info0, check0);
// 整形
var tag1 = "INT";
var info1 = "整数(由数字和负号组成,例：0,1,-55)";
function check1(tag, form, obj, value) {
	if (value == "") {
		return true;
	}
	
	var sExp = new RegExp("^0|(\-?[1-9][0-9]*)$");
	// 获得该类型正则表达式
	return sExp.test(value);
}
AddCheckFunctionObject(tag1, info1, check1, null);
// 年龄
var tag2 = "AGE";
var info2 = "年龄(在1-139之间的整数)";
function check2(tag, form, obj, value) {
	var sExp = new RegExp("(^[1-9]$)|(^[1-9]\\d$)|(^1[0-3]\\d$)");
	// 获得该类型正则表达式
	return sExp.test(value);
}
AddCheckFunctionObject(tag2, info2, check2, null);
// 邮箱
var tag3 = "EMAIL";
var info3 = "E-Mail(例:china@china.cn)";
function check3(tag, form, obj, value) {
	if (value == "") {
		return true;
	}
	var sExp = new RegExp("^((\\w)+\\.?){1,3}@((\\w)+\\.){1,3}((cn)|(com)|(org)|(net))$");
	// 获得该类型正则表达式
	return sExp.test(value);
}
AddCheckFunctionObject(tag3, info3, check3, null);
// 电邮
var tag4 = "SAMPLE";
function check4(tag, form, obj, value) {
	if (value == "") {
		return false;
	}
	if (typeof obj.sample != "undefined") {
		var sample = obj.sample;
		if (sample.indexOf(value) > -1) {
			return true;
		}
	}
	return false;
}
function message4(tag, form, obj, value) {
	if (typeof obj.sample != "undefined") {
		var sample = obj.sample;
		return "枚举值(" + sample + ")";
	}
	alert("没有设置枚举值");
	return "[没有设置枚举值]";
}
AddCheckFunctionObject(tag4, "", check4, message4);

// 日期
var tag5 = "DATE";
var info5 = "日期(格式参照:1981-10-01 01:01:00或1981-10-01)";
function check5(tag, form, obj, value) {
	var sExp = new RegExp("(^[1-9]\\d{3}((-\\d{2}){1,2}(\\s[0-2][0-9]\\:[0-5][0-9]\\:[0-5][0-9])?)$)");
	// 获得该类型正则表达式
	return sExp.test(value);
}
AddCheckFunctionObject(tag5, info5, check5, null);
// 长度
var tag6 = "长度";
function check6(tag, form, obj, value) {
	var ret = false;
	try {
		var length = parseInt(tag.substring(4));
		var type = tag.substring(2, 4);
		// todo：录入的数据需要做转码防止html注入，根据需要调整
		var truevalue = value;
		truevalue = truevalue.replace("&", "&amp;");
		truevalue = truevalue.replace("<", "&lt;");
		truevalue = truevalue.replace(">", "&gt;");
		truevalue = truevalue.replace("\n", "<br>");
		var num = GetStrLength(truevalue);
		if ("大于" == type) {
			return num > length;
		}
		if ("等于" == type) {
			return num == length;
		}
		if ("小于" == type) {
			return num < length;
		}
	} catch (e) {
		alert("长度校验参数设置异常");
		return "[长度校验参数设置异常]";
	}
	return ret;
}
function message6(tag, form, obj, value) {
	try {
		var num = GetStrLength(value);
		var truevalue = value;
		truevalue = truevalue.replace("&", "&amp;");
		truevalue = truevalue.replace("<", "&lt;");
		truevalue = truevalue.replace(">", "&gt;");
		truevalue = truevalue.replace("\n", "<br>");
		var truenum = GetStrLength(truevalue);
		var info = "";
		if (truenum > num) {
			info += ",&,<,>,换行等特殊字符会令长度增加";
		}
		return "长度不正确(您输入长度为" + truenum + info + ",建议" + tag + ")";
	} catch (e) {
		alert("长度校验参数设置异常");
		return "[长度校验参数设置异常]";
	}
}
AddCheckFunctionObject(tag6, "", check6, message6);
// 正则表达式
var tag7 = "正则";
var info7 = "";
function check7(tag, form, obj, value) {
	var sExp = new RegExp(tag.substring(2));
	// 获得该类型正则表达式
	return sExp.test(value);
}
AddCheckFunctionObject(tag7, info7, check7, null);

// 正数
var tag8 = "POSITIVE";
var info8 = "正数";
function check8(tag8, form, obj, value) {
	if (value == "") {
		return true;
	}
	if(value>=0){
		return true;
	}else{
		return false;
	}
}
AddCheckFunctionObject(tag8, info8, check8, null);
// 除0以外的正数
var tag9= "NOZERO";
var info9 = "";
function check9(tag9, form, obj, value) {
	if (value == "") {
		return true;
	}
	if(value>0){
		return true;
	}else{
		return false;
	}
}
AddCheckFunctionObject(tag9, info9, check9, null);



// 百分比
var tag10 = "PERCENT";
var info10 = "百分比";
function check10(tag10, form, obj, value) {
	if (value == "") {
		return true;
	}
	if (value >= 0 && value <= 100) {
		return true;
	}
	return false;
}
AddCheckFunctionObject(tag10, info10, check10, null);

// 手机验证
var tag11 = "MOBILE";
var info11 = "手机验证";
function check11(tag11, form, obj, value) {
	if (value == "") {
		return true;
	}
	return (value.search(/^((\(\d{3}\))|(\d{3}\-))?13\d{9}|18\d{9}|15\d{9}$/) != -1);
}
AddCheckFunctionObject(tag11, info11, check11, null);

// 邮政编码
var tag12 = "POSTCODE";
var info12 = "邮政编码";
function check12(tag12, form, obj, value) {
	if (value == "") {
		return true;
	}
	return (value.search(/^[1-9]\d{5}$/) != -1);
}
AddCheckFunctionObject(tag12, info12, check12, null);

var tag13 = "CHNUM";
var info13 = "编号";
function check13(tag13,form,obj,value){
	if(! /^[A-Za-z0-9]+$/.test(value)){
				return false;
	}
	return true;
}
AddCheckFunctionObject(tag13,info13,check13,null);

// DEBUG_TAG = true;

// ////////////////常用的校验方法//////////////////////
function GetStrLength(str) {
	num = str.length
	var arr = str.match(/[^\x00-\x80]/ig)
	if (arr != null)
		num += arr.length
	return num
}